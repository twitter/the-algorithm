packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import java.io.IOelonxcelonption;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Welonight implelonmelonntation that adds telonrmination support for an undelonrlying quelonry.
 * Melonant to belon uselond in conjunction with {@link TelonrminationQuelonry}.
 */
public class TelonrminationQuelonryWelonight elonxtelonnds Welonight {
  privatelon final Welonight innelonr;
  privatelon final QuelonryTimelonout timelonout;

  TelonrminationQuelonryWelonight(TelonrminationQuelonry quelonry, Welonight innelonr, QuelonryTimelonout timelonout) {
    supelonr(quelonry);
    this.innelonr = innelonr;
    this.timelonout = Prelonconditions.chelonckNotNull(timelonout);
  }

  @Ovelonrridelon
  public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc)
      throws IOelonxcelonption {
    relonturn innelonr.elonxplain(contelonxt, doc);
  }

  @Ovelonrridelon
  public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    Scorelonr innelonrScorelonr = innelonr.scorelonr(contelonxt);
    if (innelonrScorelonr != null) {
      relonturn nelonw TelonrminationQuelonryScorelonr(this, innelonrScorelonr, timelonout);
    }

    relonturn null;
  }

  @Ovelonrridelon
  public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
    innelonr.elonxtractTelonrms(telonrms);
  }

  @Ovelonrridelon
  public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
    relonturn innelonr.isCachelonablelon(ctx);
  }
}
