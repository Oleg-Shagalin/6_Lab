package model;

import oop.model.Service;

public interface Tariff extends Iterable<Service> {

    boolean add(Service service);
    boolean add(int index, Service service);
    Service get(int index);
    Service get(String serviceName);
    boolean hasService(String serviceName);
    Service set(int index, Service service);
    Service remove(int index);
    Service remove(String name);
    int size();
    Service[] getServices();
    Service[] getServices(ServiceTypes type);
    Service[] sortedServicesByCost();
    double cost();
    String toString();
    int hashCode();
    boolean equals(Object obj);
    Tariff clone() throws CloneNotSupportedException;
    boolean remove(Service service);
    int indexOf(Service service);
    int lastIndexOf(Service service);

}
