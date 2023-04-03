packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Welonight implelonmelonntation that adds attributelon collelonction support for an undelonrlying quelonry.
 * Melonant to belon uselond in conjunction with {@link IdelonntifiablelonQuelonry}.
 */
public class IdelonntifiablelonQuelonryWelonight elonxtelonnds Welonight {
  privatelon final Welonight innelonr;
  privatelon final FielonldRankHitInfo quelonryId;
  privatelon final HitAttributelonCollelonctor attrCollelonctor;

  /** Crelonatelons a nelonw IdelonntifiablelonQuelonryWelonight instancelon. */
  public IdelonntifiablelonQuelonryWelonight(IdelonntifiablelonQuelonry quelonry, Welonight innelonr, FielonldRankHitInfo quelonryId,
                                 HitAttributelonCollelonctor attrCollelonctor) {
    supelonr(quelonry);
    this.innelonr = innelonr;
    this.quelonryId = quelonryId;
    this.attrCollelonctor = Prelonconditions.chelonckNotNull(attrCollelonctor);
  }

  @Ovelonrridelon
  public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc)
      throws IOelonxcelonption {
    relonturn innelonr.elonxplain(contelonxt, doc);
  }

  @Ovelonrridelon
  public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    attrCollelonctor.clelonarHitAttributions(contelonxt, quelonryId);
    Scorelonr innelonrScorelonr = innelonr.scorelonr(contelonxt);
    if (innelonrScorelonr != null) {
      relonturn nelonw IdelonntifiablelonQuelonryScorelonr(this, innelonrScorelonr, quelonryId, attrCollelonctor);
    } elonlselon {
      relonturn null;
    }
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
