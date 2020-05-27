package oop.model;

import model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class AccountsManager implements Iterable<Account> {

    private Account[] accounts;
    private int size;

    public AccountsManager(int size) {
        this.size = size;
        accounts = new Account[size];
    }

    public AccountsManager(Account[] accounts) {
        size = accounts.length;
        this.accounts = accounts;
    }

    public boolean add(Account account) throws DublicateAccountNumberException{
        Objects.requireNonNull(account, "account must not be null");

        for (int i = 0; i < size; i++) {
            if (accounts[i] == null) {
                accounts[i] = account;
                return true;
            }
            else if (accounts[i].getNumber() == account.getNumber()) {
                throw new DublicateAccountNumberException();
            }
        }

        return false;
    }

    public boolean add(int index, Account account) throws DublicateAccountNumberException {
        Objects.checkIndex(index, size);
        Objects.requireNonNull(account, "account must not be null");

        if (accounts[index] == null) {
            accounts[index] = account;
            return true;
        }
        else if (accounts[index].getNumber() == account.getNumber()) {
            throw new DublicateAccountNumberException();
        }

        return false;
    }

    public Account get(int index) {
        Objects.checkIndex(index, size);
        return accounts[index];
    }

    public Account set(int index, Account account) throws DublicateAccountNumberException {
        Objects.checkIndex(index, size);
        Objects.requireNonNull(account, "account must not be null");

        if (accounts[index].getNumber() == account.getNumber())
            throw new DublicateAccountNumberException();

        accounts[index] = account;
        return accounts[index];
    }

    public Account remove(int index) {
        Objects.checkIndex(index, size);

        Account account = accounts[index];
        Account[] temp = new Account[size - 1];
        System.arraycopy(accounts, 0, temp, 0, index);

        if (index != accounts.length - 1)
            System.arraycopy(accounts, index+1, temp, index, accounts.length-index-1);

        size--;
        accounts = temp;

        return account;
    }

    public int size() {
        return size;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public Account[] getAccounts(ServiceTypes type) {
        Objects.requireNonNull(type, "type must not be null");

        ArrayList<Account> list = new ArrayList<>();
        Consumer<Account> consumer = new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                if (account != null && account.getTariff().getServices(type).length != 0)
                    list.add(account);
            }
        };

        forEach(consumer);
        return list.toArray(new Account[0]);
    }

    public Account[] getIndividualAccount() {
        ArrayList<Account> list = new ArrayList<>();
        Consumer<Account> consumer = new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                if (account instanceof IndividualAccount)
                    list.add(account);
            }
        };

        forEach(consumer);

        return list.toArray(new Account[0]);
    }

    public Account[] getEntityAccount() {
        ArrayList<Account> list = new ArrayList<>();
        Consumer<Account> consumer = new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                if (account instanceof EntityAccount)
                    list.add(account);
            }
        };

        forEach(consumer);

        return list.toArray(new Account[0]);
    }

    public Account getAccount(long accountNumber) {
        if (accountNumber < 0xE8D4A51001L || accountNumber > 0x738D7EA4C67FFFL) {
            throw new IllegalAccountNumber();
        }

        for (Account account : accounts) {
            if (Objects.nonNull(account) && account.getNumber() == accountNumber) {
                return account;
            }
        }

        throw new NoSuchElementException("accountNumber not found");
    }

    public Tariff getTariff(long accountNumber) {
        if (accountNumber < 0xE8D4A51001L || accountNumber > 0x738D7EA4C67FFFL) {
            throw new IllegalAccountNumber();
        }

        for (Account account : accounts) {
            if (account != null && account.getNumber() == accountNumber) {
                return account.getTariff();
            }
        }

        throw new NoSuchElementException("accountNumber not found");
    }

    public Tariff setTariff(long accountNumber, Tariff tariff) {
        Objects.requireNonNull(tariff, "tariff must not be null");

        if (accountNumber < 0xE8D4A51001L || accountNumber > 0x738D7EA4C67FFFL) {
            throw new IllegalAccountNumber();
        }

        for (Account account : accounts) {
            if (account != null && account.getNumber() == accountNumber) {
                account.setTariff(tariff);
                return account.getTariff();
            }
        }

        throw new NoSuchElementException("accountNumber not found");
    }

    public boolean remove (Account account) {
        Objects.requireNonNull(account, "account must not be null");

        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return remove(i) != null;
            }
        }

        return false;
    }

    public int indexOf(Account account) {
        Objects.requireNonNull(account, "account must not be null");

        for (int i = 0; i < size; i++) {
            if (accounts[i] != null && accounts[i].equals(account)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Account account : accounts) {
            if (account != null)
                builder.append(account.toString());
        }

        return builder.toString();
    }

    @Override
    public Iterator<Account> iterator() {
        return new AccountIterator();
    }

    private class AccountIterator implements Iterator<Account> {

        private int current_index = 0;

        @Override
        public boolean hasNext() {
            return current_index < accounts.length;
        }

        @Override
        public Account next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return accounts[current_index++];
        }

    }

}
