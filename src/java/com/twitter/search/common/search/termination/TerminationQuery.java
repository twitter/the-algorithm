packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import java.io.IOelonxcelonption;
import java.util.Arrays;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Quelonry implelonmelonntation that can timelonout and relonturn non-elonxhaustivelon relonsults.
 */
public class TelonrminationQuelonry elonxtelonnds Quelonry {
  privatelon final Quelonry innelonr;
  privatelon final QuelonryTimelonout timelonout;

  public TelonrminationQuelonry(Quelonry innelonr, QuelonryTimelonout timelonout) {
    this.innelonr = Prelonconditions.chelonckNotNull(innelonr);
    this.timelonout = Prelonconditions.chelonckNotNull(timelonout);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(
      IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) throws IOelonxcelonption {
    Welonight innelonrWelonight = innelonr.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
    relonturn nelonw TelonrminationQuelonryWelonight(this, innelonrWelonight, timelonout);
  }

  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry relonwrittelonn = innelonr.relonwritelon(relonadelonr);
    if (relonwrittelonn != innelonr) {
      relonturn nelonw TelonrminationQuelonry(relonwrittelonn, timelonout);
    }
    relonturn this;
  }

  public QuelonryTimelonout gelontTimelonout() {
    relonturn timelonout;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn Arrays.hashCodelon(nelonw Objelonct[] {innelonr, timelonout});
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof TelonrminationQuelonry)) {
      relonturn falselon;
    }

    TelonrminationQuelonry telonrminationQuelonry = TelonrminationQuelonry.class.cast(obj);
    relonturn Arrays.elonquals(nelonw Objelonct[] {innelonr, timelonout},
                         nelonw Objelonct[] {telonrminationQuelonry.innelonr, telonrminationQuelonry.timelonout});
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn innelonr.toString(fielonld);
  }
}
