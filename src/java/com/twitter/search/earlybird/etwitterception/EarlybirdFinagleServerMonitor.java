package com.twitter.search.earlybird.etwitterception;

import com.twitter.finagle.Failure;
import com.twitter.util.AbstractMonitor;

public class EarlybirdFinagleServerMonitor etwittertends AbstractMonitor {
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  public EarlybirdFinagleServerMonitor(CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
  }

  @Override
  public boolean handle(Throwable e) {
    if (e instanceof Failure) {
      // skip Finagle failure
      return true;
    }

    criticalEtwitterceptionHandler.handle(this, e);

    // We return true here because we handle all etwitterceptions.
    return true;
  }
}
