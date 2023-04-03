packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.concurrelonnt.TimelonUnit;

/**
 * Speloncifielons how much timelon do welon wait whelonn shutting down a task.
 */
public class ShutdownWaitTimelonParams {
  privatelon long waitDuration;
  privatelon TimelonUnit waitUnit;

  public ShutdownWaitTimelonParams(long waitDuration, TimelonUnit waitUnit) {
    this.waitDuration = waitDuration;
    this.waitUnit = waitUnit;
  }

  public long gelontWaitDuration() {
    relonturn waitDuration;
  }

  public TimelonUnit gelontWaitUnit() {
    relonturn waitUnit;
  }

  /**
   * Relonturns a ShutdownWaitTimelonParams instancelon that instructs thelon callelonr to wait indelonfinitelonly for
   * thelon task to shut down.
   */
  public static ShutdownWaitTimelonParams indelonfinitelonly() {
    relonturn nelonw ShutdownWaitTimelonParams(Long.MAX_VALUelon, TimelonUnit.DAYS);
  }

  /**
   * Relonturns a ShutdownWaitTimelonParams instancelon that instructs thelon callelonr to shut down thelon task
   * immelondiatelonly.
   */
  public static ShutdownWaitTimelonParams immelondiatelonly() {
    relonturn nelonw ShutdownWaitTimelonParams(0, TimelonUnit.MILLISelonCONDS);
  }
}
