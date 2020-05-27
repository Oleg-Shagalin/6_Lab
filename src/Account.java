package model;

import oop.model.IndividualsTariff;

import java.time.LocalDate;

public interface Account {

    long getNumber();
    Tariff getTariff();
    void setTariff(Tariff tariff);
    LocalDate getRegistrationDate();
}
