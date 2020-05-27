package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractAccount implements Account {
    private long number;
    private Tariff tariff;
    private LocalDate registrationDate;

    protected AbstractAccount(long number, Tariff tariff, LocalDate registrationDate) {
        if (number < 0xE8D4A51001L || number > 0x738D7EA4C67FFFL) {
            throw new IllegalAccountNumber();
        }

        this.number = number;
        this.tariff = Objects.requireNonNull(tariff, "tariff must not be null");

        if (registrationDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Registration date from the future.");

        this.registrationDate = registrationDate;
    }

    @Override
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public long getNumber() {
        return number;
    }

    @Override
    public Tariff getTariff() {
        return tariff;
    }

    @Override
    public void setTariff(Tariff tariff) {
        this.tariff = Objects.requireNonNull(tariff, "tariff must not be null");
    }

    @Override
    public int hashCode() {
        return Long.hashCode(number) * Integer.hashCode(tariff.size()) * registrationDate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            AbstractAccount account = (AbstractAccount) obj;
            return number == account.number
                    && tariff.size() == account.tariff.size()
                    && registrationDate.isEqual(account.registrationDate);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("number: %d\n%s\n%s", number, registrationDate.toString(), tariff.toString());
    }
}
