import model.*;
import oop.model.*;

import java.time.LocalDate;

public class TestLab6 {

    public static void lab6tests() {
        Tariff individualsTariff = new IndividualsTariff();
        individualsTariff.add(new Service());
        individualsTariff.set(3, new Service("asd", 123, ServiceTypes.MAIL, LocalDate.now().minusDays(12)));
        individualsTariff.add(new Service("asd", 173, ServiceTypes.MAIL, LocalDate.now()));
        Service[] service = individualsTariff.sortedServicesByCost();
        service = individualsTariff.getServices(ServiceTypes.MAIL);

        Tariff entityTariff = new EntityTariff();
        entityTariff.add(new Service());
        entityTariff.add(new Service("asd", 123, ServiceTypes.MAIL, LocalDate.now().minusDays(13)));
        service = entityTariff.sortedServicesByCost();
        service = entityTariff.getServices(ServiceTypes.MAIL);

        Account account = new IndividualAccount(0xE8D4A51001L,
                new Person("Ivan", "Ivanov"), (IndividualsTariff) individualsTariff, LocalDate.now());
        Account entityAccount = new EntityAccount(0x738D7EA4C67FFFL,"Petr", entityTariff, LocalDate.now());
        Account[] accounts = new Account[3];
        accounts[0] = account;
        accounts[1] = entityAccount;
        AccountsManager manager = new AccountsManager(accounts);
        accounts = manager.getAccounts(ServiceTypes.INTERNET);
        accounts = manager.getIndividualAccount();
        accounts = manager.getEntityAccount();
    }

    @org.junit.Test
    public void startTests() {
        lab6tests();
    }

}
