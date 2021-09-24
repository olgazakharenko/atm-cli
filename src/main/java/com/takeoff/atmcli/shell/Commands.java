package com.takeoff.atmcli.shell;

import com.takeoff.atmcli.model.Transaction;
import com.takeoff.atmcli.service.AccountService;
import com.takeoff.atmcli.service.Atm;
import com.takeoff.atmcli.service.impl.Authorization;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {

  @Autowired
  private Authorization auth;
  @Autowired
  private AccountService accountService;
  @Autowired
  private Atm atm;

  @ShellMethod(value = "Authorize account.", group = "Account")
  @ShellMethodAvailability("authorizeAvailability")
  public void authorize(@ShellOption String userId, @ShellOption String pin) {
   auth.authorise(userId, pin);
  }

  @ShellMethod(value = "Returns the account’s transaction history.", group = "Account")
  @ShellMethodAvailability("isAuthorized")
  public void history() {
    List<Transaction> history = accountService.history(accountService.getCurrent());

    if (history.isEmpty()) {
      System.out.println("No history found");
    } else {
      history.stream().forEach(t -> System.out.println(t.toString()));
    }
  }

  @ShellMethod(value = "Returns the account’s current balance.", group = "Account")
  @ShellMethodAvailability("isAuthorized")
  public void balance() {
    System.out.println(accountService.balance(accountService.getCurrent()).toString());
  }

  @ShellMethod(value = "Deactivates the currently authorized account.", group = "Account")
  @ShellMethodAvailability("isAuthorized")
  public void logout() {
    accountService.logout(accountService.getCurrent());
  }

  @ShellMethod(value = "Removes value from the authorized account.", group = "Atm")
  @ShellMethodAvailability("isAuthorized")
  public void withdraw(@ShellOption BigDecimal amount) {
    //verify if withdraw is possible from account and atm
    if (accountService.withdrawAvailability(accountService.getCurrent(), amount) &&
        atm.withdrawAvailability(amount)) {

      //verify if atm has enough money or adjust
      BigDecimal withdrawAmount = atm.adjustWithdrawAmount(amount);

      //update atm balance after successful account withdraw
      if (accountService.withdraw(accountService.getCurrent(), withdrawAmount)) {
        atm.withdraw(withdrawAmount);
      }
    }
  }

  @ShellMethod(value = "Adds value to the authorized account.", group = "Atm")
  @ShellMethodAvailability("isAuthorized")
  public void deposit(@ShellOption BigDecimal amount) {
    if (accountService.deposit(accountService.getCurrent(), amount)) {
      atm.deposit(amount);
    }
  }

  private Availability authorizeAvailability() {
    return accountService.getCurrent() != null
        ? Availability.unavailable("you are already authorized. Log out for re-authorization")
        : Availability.available();
  }

  private Availability isAuthorized() {
    return accountService.getCurrent() == null
        ? Availability.unavailable("Authorization required.")
        : Availability.available();
  }
}