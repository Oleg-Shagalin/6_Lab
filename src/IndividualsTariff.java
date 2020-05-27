package oop.model;

import model.ServiceTypes;
import model.Tariff;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Consumer;

public class IndividualsTariff implements Tariff, Cloneable {

    @MagicConstant(intValues = {SERVICE_CHARGE})
    private Service[] services;
    private int size = 0;
    public static final int SERVICE_CHARGE = 50;

    public IndividualsTariff() {
        services = new Service[8];
    }

    public IndividualsTariff(int size) {
        services = new Service[size];
    }

    public IndividualsTariff(Service[] services) {
        this.services = services;

        for (Service service : services) {
            if (service != null)
                size++;
        }

    }

    public boolean add(Service service) {
        Objects.requireNonNull(service, "service must not be null");

        for (int i = 0; i < services.length; i++) {
            if (services[i] == null) {
                services[i] = service;
                size++;
                return true;
            }
        }

        increaseArray();
        return add(service);
    }

    private void increaseArray() {
        Service[] temp = new Service[services.length * 2];
        System.arraycopy(services, 0, temp, 0, services.length);
        services = temp;
    }

    public boolean add(int index, Service service) {
        Objects.checkIndex(index, services.length);

        if (services[index] == null) {
            services[index] = Objects.requireNonNull(service, "service must not be null");
            size++;
            return true;
        }

        return false;
    }

    public Service get(int index) {
        Objects.checkIndex(index, services.length);
        return services[index];
    }

    public Service get(String serviceName) {
        Objects.requireNonNull(serviceName, "serviceName must not be null");

        for (Service service : services) {
            if (service != null && service.getName().equals(serviceName))
                return service;
        }

        throw new NoSuchElementException("serviceName not found");
    }

    public boolean hasService(String serviceName) {
        Objects.requireNonNull(serviceName, "serviceName must not be null");

        for (Service service : services) {
            if (service != null && service.getName().equals(serviceName))
                return true;
        }

        return false;
    }

    public Service set(int index, Service service) {
        Objects.checkIndex(index, services.length);
        Objects.requireNonNull(service, "service must not be null");

        if (services[index] == null)
            size++;

        services[index] = service;
        return services[index];

    }

    public Service remove(int index) {
        Objects.checkIndex(index, services.length);

        Service service = services[index];

        if (index != services.length - 1) {
            System.arraycopy(services, index + 1, services, index, services.length - index - 1);
        }

        if (services[services.length - 1] != null)
            services[services.length - 1] = null;

        if (service != null)
            size--;

        return service;

    }

    public Service remove(String serviceName) {
        Objects.requireNonNull(serviceName, "serviceName must not be null");

        for (int i = 0; i < services.length; i++) {
            if (services[i] != null && services[i].getName().equals(serviceName)) {
                return remove(i);
            }
        }

        throw new NoSuchElementException("serviceName not found");
    }

    public int size() {
        return size;
    }

    public Service[] getServices() {
        Service[] temp = new Service[size];
        for (int i = 0, j = 0; i < services.length; i++) {
            if (services[i] != null) {
                temp[j] = services[i];
                j++;
            }
        }

        return temp;
    }

    @Override
    public Service[] getServices(ServiceTypes type) {
        Objects.requireNonNull(type, "type must not be null");

        ArrayList<Service> list = new ArrayList<>();
        Consumer<Service> consumer = new Consumer<Service>() {
            @Override
            public void accept(Service service) {
                if (service != null && service.getType() == type)
                    list.add(service);
            }
        };

        forEach(consumer);
        return list.toArray(new Service[0]);
    }

    public Service[] sortedServicesByCost() {
        Service[] temp = getServices();
        Arrays.sort(temp);
        return temp;
    }

    public double cost() {
        double cost = 0;

        for (Service service : services) {
            if (Objects.nonNull(service)) {
                Period period = Period.between(service.getActivationDate(), LocalDate.now());
                if (period.getMonths() < 1) {
                    cost += service.getCost() * period.getDays() / LocalDate.now().lengthOfMonth();
                }
                else {
                    cost += service.getCost();
                }
            }
        }

        return cost + SERVICE_CHARGE;
    }

    @Override
    public int hashCode() {
        int result = 31;

        for (Service service : services) {
            if (service != null)
                result *= service.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            IndividualsTariff tariff = (IndividualsTariff) obj;
            if (size == tariff.size) {
                Service[] services1 = getServices();
                Service[] services2 = tariff.getServices();
                for (int i = 0; i < size; i++) {
                    if (!services1[i].equals(services2[i]))
                        return false;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public Tariff clone() throws CloneNotSupportedException {
        IndividualsTariff tariff = (IndividualsTariff) super.clone();

        for (int i = 0; i < services.length; i++) {
            if (services[i] != null)
                tariff.services[i] = services[i].clone();
        }

        return tariff;
    }

    @Override
    public boolean remove(Service service) {
        Objects.requireNonNull(service, "service must not be null");

        for (int i = 0; i < services.length; i++) {
            if (services[i] != null && services[i].equals(service)) {
                return remove(i) != null;
            }
        }

        return false;
    }

    @Override
    public int indexOf(Service service) {
        Objects.requireNonNull(service, "service must not be null");

        for (int i = 0; i < services.length; i++) {
            if (services[i] != null && services[i].equals(service)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Service service) {
        Objects.requireNonNull(service, "service must not be null");

        for (int i = services.length - 1; i >= 0; i--) {
            if (services[i] != null && services[i].equals(service)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Service service : services) {
            if (service != null)
                builder.append(service.toString());
        }

        return String.format("services:\n%s", builder.toString());
    }

    @Override
    public Iterator<Service> iterator() {
        return new ServiceIterator();
    }

    private class ServiceIterator implements Iterator<Service> {

        private int current_index = 0;

        @Override
        public boolean hasNext() {
            return current_index < services.length;
        }

        @Override
        public Service next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return services[current_index++];
        }

    }

}
