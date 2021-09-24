package com.takeoff.atmcli.service;

import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {

  void addAll(List<Account> accounts);

  Map<String, Account> getAccounts();

  boolean withdraw(Account account, BigDecimal amount);

  boolean deposit(Account account, BigDecimal amount);

  List<Transaction> history(Account account);

  BigDecimal balance(Account account);

  Account getCurrent();

  void setCurrentAccount(Account account);

  boolean logout(Account account);

  boolean withdrawAvailability(Account account, BigDecimal amount);

  List<Account> loadAccounts(String file);

  void clearAccounts();
}
