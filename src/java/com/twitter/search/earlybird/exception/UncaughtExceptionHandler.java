package com.twitter.search.earlybird.exception;

import com.twitter.util.AbstractMonitor;

public class UncaughtExceptionHandler extends AbstractMonitor {
  private final CriticalExceptionHandler criticalExceptionHandler;

  public UncaughtExceptionHandler() {
    this.criticalExceptionHandler = new CriticalExceptionHandler();
  }

  public void setShutdownHook(Runnable shutdown) {
    this.criticalExceptionHandler.setShutdownHook(shutdown);
  }

  @Override
  public boolean handle(Throwable e) {
    criticalExceptionHandler.handle(this, e);

    // We return true here because we handle all exceptions.
    return true;
  }
}
