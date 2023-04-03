packagelon com.twittelonr.selonarch.elonarlybird.elonxcelonption;

import com.twittelonr.finaglelon.Failurelon;
import com.twittelonr.util.AbstractMonitor;

public class elonarlybirdFinaglelonSelonrvelonrMonitor elonxtelonnds AbstractMonitor {
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;

  public elonarlybirdFinaglelonSelonrvelonrMonitor(CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
  }

  @Ovelonrridelon
  public boolelonan handlelon(Throwablelon elon) {
    if (elon instancelonof Failurelon) {
      // skip Finaglelon failurelon
      relonturn truelon;
    }

    criticalelonxcelonptionHandlelonr.handlelon(this, elon);

    // Welon relonturn truelon helonrelon beloncauselon welon handlelon all elonxcelonptions.
    relonturn truelon;
  }
}
