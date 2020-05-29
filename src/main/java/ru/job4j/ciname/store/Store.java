package ru.job4j.ciname.store;

import ru.job4j.ciname.models.Account;
import ru.job4j.ciname.models.Hall;

import java.util.List;

public interface Store {

    List<Hall> findAllPlaces();

    Integer createAccount(Account account, Integer line, Integer col);

    Hall findPlaceByLineAndRow(int line, int col);
}
