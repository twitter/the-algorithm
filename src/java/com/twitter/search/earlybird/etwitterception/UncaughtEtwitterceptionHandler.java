package com.twitter.search.earlybird.etwitterception;

import com.twitter.util.AbstractMonitor;

public class UncaughtEtwitterceptionHandler etwittertends AbstractMonitor {
  private final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;

  public UncaughtEtwitterceptionHandler() {
    this.criticalEtwitterceptionHandler = new CriticalEtwitterceptionHandler();
  }

  public void setShutdownHook(Runnable shutdown) {
    this.criticalEtwitterceptionHandler.setShutdownHook(shutdown);
  }

  @Override
  public boolean handle(Throwable e) {
    criticalEtwitterceptionHandler.handle(this, e);

    // We return true here because we handle all etwitterceptions.
    return true;
  }
}
