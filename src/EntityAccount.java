package model;

import oop.model.Service;

import java.time.LocalDate;
import java.util.Objects;

public class EntityAccount extends AbstractAccount implements Account {

    private String name;

    public EntityAccount(long number, String name) {
        super(number, new EntityTariff(), LocalDate.now());

        EntityTariff tariff = new EntityTariff();
        tariff.add(new Service());
        setTariff(tariff );

        this.name = name;
    }

    public EntityAccount(long number, String name, Tariff tariff, LocalDate registrationDate) {
        super(number, tariff, registrationDate);
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public int hashCode() {
        return 53 * super.hashCode() * name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            EntityAccount account = (EntityAccount) obj;
            return name.equals(account.name)
                    && super.equals(obj);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Entity account:\nentity: %s\n%s", name, super.toString());
    }
}
