package model;

import oop.model.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Consumer;

public class EntityTariff implements Tariff, Cloneable {

    private LinkedList<Service> list;

    public EntityTariff() {
        list = new LinkedList<>();
    }

    public EntityTariff(Service[] services) {
        list = new LinkedList<>(Arrays.asList(services));
    }

    private boolean addService(Service service) {
        list.addLast(Objects.requireNonNull(service, "service must not be null"));
        return true;
    }

    private boolean addService(int index, Service service) {
        list.add(index, Objects.requireNonNull(service, "service must not be null"));
        return true;
    }

    private Service getService(int index) {
        return list.get(index);
    }

    @Override
    public boolean add(Service service) {
        return addService(service);
    }

    @Override
    public boolean add(int index, Service service) {
        return addService(index, service);
    }

    @Override
    public Service get(int index) {
        return getService(index);
    }

    @Override
    public Service get(String serviceName) {
        Objects.requireNonNull(serviceName, "serviceName must not be null");

        for (Service service : list) {
            if (service.getName().equals(serviceName))
                return service;
        }

        throw new NoSuchElementException("serviceName not found");
    }

    @Override
    public boolean hasService(String serviceName) {
        Objects.requireNonNull(serviceName, "serviceName must not be null");

        for (Service service : list) {
            if (service.getName().equals(serviceName))
                return true;
        }
        return false;
    }

    @Override
    public Service set(int index, Service service) {
        return setService(index, service);
    }

    @Override
    public Service remove(int index) {
        return removeService(index);
    }

    private Service setService(int index, Service service) {
        return list.set(index, Objects.requireNonNull(service, "service must not be null"));
    }

    private Service removeService(int index) {
        return list.remove(index);
    }

    @Override
    public Service remove(String name) {
        Objects.requireNonNull(name, "name must not be null");

        for (Service service : list) {
            if (service.getName().equals(name)) {
                list.remove(service);
                return service;
            }
        }

        throw new NoSuchElementException("name not found");
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Service[] getServices() {
        Service[] services = new Service[list.size()];
        return list.toArray(services);
    }

    @Override
    public Service[] getServices(ServiceTypes type) {
        Objects.requireNonNull(type, "type must not be null");

        ArrayList<Service> result = new ArrayList<>();
        Consumer<Service> consumer = new Consumer<Service>() {
            @Override
            public void accept(Service service) {
                if (service.getType() == type)
                    result.add(service);
            }
        };

        forEach(consumer);
        return result.toArray(new Service[0]);
    }

    @Override
    public Service[] sortedServicesByCost() {
        Service[] services = new Service[list.size()];
        list.sort(new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }
        });
        return list.toArray(services);
    }

    @Override
    public double cost() {
        double cost = 0;

        for (Service service : list) {
            Period period = Period.between(service.getActivationDate(), LocalDate.now());
            if (period.getMonths() < 1) {
                cost += service.getCost() * period.getDays() / LocalDate.now().lengthOfMonth();
            }
            else {
                cost += service.getCost();
            }
        }

        return cost;
    }

    @Override
    public Tariff clone() throws CloneNotSupportedException {
        EntityTariff tariff = new EntityTariff();

        for (Service service : list) {
            tariff.list.add(service.clone());
        }

        return tariff;
    }

    @Override
    public boolean remove(Service service) {
        return list.remove(service);
    }

    @Override
    public int indexOf(Service service) {
        return list.indexOf(service);
    }

    @Override
    public int lastIndexOf(Service service) {
        return list.lastIndexOf(service);
    }

    @Override
    public int hashCode() {
        int result = 71;

        for (Service service : list) {
            result *= service.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            EntityTariff tariff = (EntityTariff) obj;
            if (list.size() == tariff.list.size()) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).equals(tariff.list.get(i)))
                        return false;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Service service : list) {
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
            return current_index < list.size();
        }

        @Override
        public Service next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return list.get(current_index++);
        }

    }

}
