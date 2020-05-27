package oop.model;

import model.ServiceTypes;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class Service implements Cloneable, Comparable<Service> {

    private String name;
    private double cost;
    private ServiceTypes type;
    private LocalDate activationDate;

    public Service() {
        name = "интернет 100мб\\сек";
        cost = 300;
        type = ServiceTypes.INTERNET;
        activationDate = LocalDate.now();
    }

    public Service(String name, double cost, ServiceTypes type, LocalDate activationDate) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.activationDate = Objects.requireNonNull(activationDate, "activationDate must not be null");

        if (cost < 0 || activationDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Negative value of the cost or\n" +
                    "activation date from the future.");

        this.cost = cost;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public ServiceTypes getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(cost) * name.hashCode() * type.hashCode() * activationDate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            Service service = (Service) obj;
            return cost == service.cost
                    && type == service.type
                    && name.equals(service.name)
                    && activationDate.isEqual(service.activationDate);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("%.40s \\\\%.2fp. \\\\%s", name, cost, activationDate.toString()); //“<name (40 символов)>\\<cost>р.”
    }

    @Override
    public Service clone() throws CloneNotSupportedException {
        return new Service(name, cost, type, activationDate);
    }

    @Override
    public int compareTo(@NotNull Service o) {
        return Double.compare(cost, o.cost);
    }

}
