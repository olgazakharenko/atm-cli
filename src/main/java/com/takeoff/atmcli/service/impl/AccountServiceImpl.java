package com.takeoff.atmcli.service.impl;

import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.model.Transaction;
import com.takeoff.atmcli.service.AccountService;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
  private static final Logger LOGGER = Logger.getGlobal();

  private Account current;
  private Map<String, Account> accounts = new ConcurrentHashMap<>();

  /**
   * Helper method to load account from the file
   *
   * @param file - path to the file with the list of accounts
   * @return list of Account from the file
   */
  public List<Account> loadAccounts(String file) {
    List<Account> accounts = new ArrayList<>();
    try (Stream<String> stream = Files.lines(Paths.get(file))) {
      stream
          .forEach(s -> {
            String[] vars = s.split(",");
            accounts.add(new Account(vars[0], vars[1], new BigDecimal(vars[2])));
          });
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return accounts;
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }

  @Override
  public void addAll(List<Account> accounts) {
    accounts
        .stream()
        .forEach(a -> this.accounts.put(a.getUserId(), a));
  }

  @Override
  public Map<String, Account> getAccounts() {
    return accounts;
  }

  /**
   * Withdraw requested amount from the account
   *
   * @param amount - amount to be withdrawn
   */
  @Override
  public boolean withdraw(Account account, BigDecimal amount) {
    //In case of overdraft additional fee is charged
    if (isOverdraft(account, amount)) {
      BigDecimal newAmount = amount.add(new BigDecimal("5"));
      updateAccountBalance(account, newAmount.negate());
      System.out.println("You have been charged an overdraft fee of $5.");
    } else {
      updateAccountBalance(account, amount.negate());
    }
    return true;
  }

  /**
   * Helper method to validate if withdraw is possible from the current account
   *
   * @param account
   * @param amount
   * @return
   */
  @Override
  public boolean withdrawAvailability(Account account, BigDecimal amount) {
    if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
      // If the account is already overdrawn, do not perform any checks against the available money
      //in the machine, do not process the withdrawal
      System.out.println("Your account is overdrawn! You may not make withdrawals at this time.");
      return false;
    }
    return true;
  }

  /**
   * Deposit requested amount to the account
   *
   * @param amount - amount to be withdrawn
   */
  @Override
  public boolean deposit(Account account, BigDecimal amount) {
    updateAccountBalance(account, amount);
    return true;
  }

  /**
   * Prints and returns transaction history
   *
   * @param account
   * @return
   */
  @Override
  public List<Transaction> history(Account account) {
    account.getRecentActivity().set(Instant.now().toEpochMilli());
    return account.getHistory();
  }

  /**
   * Returns the balance of the account
   *
   * @param account
   * @return
   */
  @Override
  public BigDecimal balance(Account account) {
    account.getRecentActivity().set(Instant.now().toEpochMilli());
    return account.getBalance();
  }

  /**
   * Returns current active account
   *
   * @return active account
   */
  @Override
  public Account getCurrent() {
    return current;
  }

  @Override
  public void setCurrentAccount(Account account) {
    this.current = account;
  }

  /**
   * Updates isAuthorized flag for current account. Removes current account
   *
   * @param account - current account
   */
  @Override
  public boolean logout(Account account) {
    System.out.println(String.format("Account %s logged out.", account.getUserId()));
    account.setAuthorized(false);
    setCurrentAccount(null);
    return true;
  }

  /**
   * Helper method to update account balance, add transaction to history and print balance
   * Both withdraw and deposit calls this method, with the difference of the amount (negative vs positive)
   *
   * @param account - current account
   * @param amount  - amount to be updated on the account
   */
  private boolean updateAccountBalance(Account account, BigDecimal amount) {
    long current = Instant.now().toEpochMilli();
    account.getRecentActivity().set(current);

    account.setBalance(account.getBalance().add(amount));
    account.updateHistory(new Transaction(new Date(current), amount, account.getBalance()));
    System.out.println(String.format("Amount dispensed: $%s", amount));
    System.out.println(String.format("Current balance: $%s", account.getBalance()));

    return true;
  }

  /**
   * Helper method to check if Overdraft will occur with this transaction
   *
   * @param account - current account
   * @param amount  - amount to be withdrawn
   * @return
   */
  private boolean isOverdraft(Account account, BigDecimal amount) {
    return account.getBalance().compareTo(amount) < 0;
  }
}
