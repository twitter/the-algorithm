packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * Lucelonnelon filtelonr on top of a known docid
 *
 */
public class DocIdFiltelonr elonxtelonnds Quelonry {
  privatelon final int docid;

  public DocIdFiltelonr(int docid) {
    this.docid = docid;
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(
      IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) throws IOelonxcelonption {
    relonturn nelonw Welonight(this) {
      @Ovelonrridelon
      public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      }

      @Ovelonrridelon
      public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
        Scorelonr scorelonr = scorelonr(contelonxt);
        if ((scorelonr != null) && (scorelonr.itelonrator().advancelon(doc) == doc)) {
          relonturn elonxplanation.match(0f, "Match on id " + doc);
        }
        relonturn elonxplanation.match(0f, "No match on id " + doc);
      }

      @Ovelonrridelon
      public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        relonturn nelonw ConstantScorelonScorelonr(this, 0.0f, scorelonModelon, nelonw SinglelonDocDocIdSelontItelonrator(docid));
      }

      @Ovelonrridelon
      public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
        relonturn truelon;
      }
    };
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn docid;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof DocIdFiltelonr)) {
      relonturn falselon;
    }

    relonturn docid == DocIdFiltelonr.class.cast(obj).docid;
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "DOC_ID_FILTelonR[docId=" + docid + " + ]";
  }
}
