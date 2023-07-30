package com.X.search.earlybird.exception;

import com.X.finagle.Failure;
import com.X.util.AbstractMonitor;

public class EarlybirdFinagleServerMonitor extends AbstractMonitor {
  private final CriticalExceptionHandler criticalExceptionHandler;

  public EarlybirdFinagleServerMonitor(CriticalExceptionHandler criticalExceptionHandler) {
    this.criticalExceptionHandler = criticalExceptionHandler;
  }

  @Override
  public boolean handle(Throwable e) {
    if (e instanceof Failure) {
      // skip Finagle failure
      return true;
    }

    criticalExceptionHandler.handle(this, e);

    // We return true here because we handle all exceptions.
    return true;
  }
}
