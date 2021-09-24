package com.takeoff.atmcli.util;

import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.service.AccountService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Timer to check if the user is active. Logs out the user after N minutes
 */

@Component
public class InactivityTimer {

  @Value("${atm.logout_period}")
  private long logOutPeriod;

  @Autowired
  AccountService accountService;

  @Scheduled(fixedRateString = "${atm.rate}")
  public void run() {

    Account active = accountService.getCurrent();
    if (active == null) {
      return;
    }
    boolean expired = (Instant.now().toEpochMilli() - active.getRecentActivity().get()) >= logOutPeriod;
    if (active != null && expired) {
      accountService.getCurrent().setAuthorized(false);
      accountService.setCurrentAccount(null);
    }
  }
}
