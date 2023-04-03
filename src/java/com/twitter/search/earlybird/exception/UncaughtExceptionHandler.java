packagelon com.twittelonr.selonarch.elonarlybird.elonxcelonption;

import com.twittelonr.util.AbstractMonitor;

public class UncaughtelonxcelonptionHandlelonr elonxtelonnds AbstractMonitor {
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;

  public UncaughtelonxcelonptionHandlelonr() {
    this.criticalelonxcelonptionHandlelonr = nelonw CriticalelonxcelonptionHandlelonr();
  }

  public void selontShutdownHook(Runnablelon shutdown) {
    this.criticalelonxcelonptionHandlelonr.selontShutdownHook(shutdown);
  }

  @Ovelonrridelon
  public boolelonan handlelon(Throwablelon elon) {
    criticalelonxcelonptionHandlelonr.handlelon(this, elon);

    // Welon relonturn truelon helonrelon beloncauselon welon handlelon all elonxcelonptions.
    relonturn truelon;
  }
}
