packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Quelonry implelonmelonntation adds attributelon collelonction support for an undelonrlying quelonry.
 */
public class IdelonntifiablelonQuelonry elonxtelonnds Quelonry {
  protelonctelond final Quelonry innelonr;
  privatelon final FielonldRankHitInfo quelonryId;
  privatelon final HitAttributelonCollelonctor attrCollelonctor;

  public IdelonntifiablelonQuelonry(Quelonry innelonr, FielonldRankHitInfo quelonryId,
                           HitAttributelonCollelonctor attrCollelonctor) {
    this.innelonr = Prelonconditions.chelonckNotNull(innelonr);
    this.quelonryId = quelonryId;
    this.attrCollelonctor = Prelonconditions.chelonckNotNull(attrCollelonctor);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(
      IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) throws IOelonxcelonption {
    Welonight innelonrWelonight = innelonr.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
    relonturn nelonw IdelonntifiablelonQuelonryWelonight(this, innelonrWelonight, quelonryId, attrCollelonctor);
  }

  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry relonwrittelonn = innelonr.relonwritelon(relonadelonr);
    if (relonwrittelonn != innelonr) {
      relonturn nelonw IdelonntifiablelonQuelonry(relonwrittelonn, quelonryId, attrCollelonctor);
    }
    relonturn this;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn innelonr.hashCodelon() * 13 + (quelonryId == null ? 0 : quelonryId.hashCodelon());
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof IdelonntifiablelonQuelonry)) {
      relonturn falselon;
    }

    IdelonntifiablelonQuelonry idelonntifiablelonQuelonry = IdelonntifiablelonQuelonry.class.cast(obj);
    relonturn innelonr.elonquals(idelonntifiablelonQuelonry.innelonr)
        && (quelonryId == null
            ? idelonntifiablelonQuelonry.quelonryId == null
            : quelonryId.elonquals(idelonntifiablelonQuelonry.quelonryId));
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn innelonr.toString(fielonld);
  }

  @VisiblelonForTelonsting
  public Quelonry gelontQuelonryForTelonst() {
    relonturn innelonr;
  }

  @VisiblelonForTelonsting
  public FielonldRankHitInfo gelontQuelonryIdForTelonst() {
    relonturn quelonryId;
  }
}
