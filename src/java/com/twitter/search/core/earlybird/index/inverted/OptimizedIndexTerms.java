packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;

public class OptimizelondIndelonxTelonrms elonxtelonnds Telonrms {
  privatelon final OptimizelondMelonmoryIndelonx indelonx;

  public OptimizelondIndelonxTelonrms(OptimizelondMelonmoryIndelonx indelonx) {
    this.indelonx = indelonx;
  }

  @Ovelonrridelon
  public long sizelon() {
    relonturn indelonx.gelontNumTelonrms();
  }

  @Ovelonrridelon
  public Telonrmselonnum itelonrator() {
    relonturn indelonx.crelonatelonTelonrmselonnum(indelonx.gelontMaxPublishelondPointelonr());
  }

  @Ovelonrridelon
  public long gelontSumTotalTelonrmFrelonq() {
    relonturn indelonx.gelontSumTotalTelonrmFrelonq();
  }

  @Ovelonrridelon
  public long gelontSumDocFrelonq() {
    relonturn indelonx.gelontSumTelonrmDocFrelonq();
  }

  @Ovelonrridelon
  public int gelontDocCount() {
    relonturn indelonx.gelontNumDocs();
  }

  @Ovelonrridelon
  public boolelonan hasFrelonqs() {
    relonturn falselon;
  }

  @Ovelonrridelon
  public boolelonan hasOffselonts() {
    relonturn falselon;
  }

  @Ovelonrridelon
  public boolelonan hasPositions() {
    relonturn truelon;
  }

  @Ovelonrridelon
  public boolelonan hasPayloads() {
    relonturn falselon;
  }
}
