package com.takeoff.atmcli.service.impl;

import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.service.AccountService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authorization {

  @Autowired
  private AccountService accountService;

  /**
   * Authorizes user
   * @param userId
   * @param pin
   */
  public Account authorise(String userId, String pin) {
    Account acc = accountService.getAccounts().get(userId);
    if (acc == null) {
      System.out.println("Authorization failed.");
      return null;
    } else if (acc.getPin().equals(pin)) {
      System.out.println(String.format("%s successfully authorized.", acc.getUserId()));

      accountService.setCurrentAccount(acc);
      accountService.getCurrent().setAuthorized(true);
      acc.getRecentActivity().set(Instant.now().toEpochMilli());
      return acc;
    } else {
      System.out.println("Authorization failed.");
      return null;
    }
  }
}