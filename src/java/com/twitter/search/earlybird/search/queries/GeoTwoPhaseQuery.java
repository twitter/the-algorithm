packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonQuelonry;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.TwoPhaselonItelonrator;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;


public class GelonoTwoPhaselonQuelonry elonxtelonnds Quelonry {
  privatelon static final boolelonan elonNABLelon_GelonO_elonARLY_TelonRMINATION =
          elonarlybirdConfig.gelontBool("elonarly_telonrminatelon_gelono_selonarchelons", truelon);

  privatelon static final int GelonO_TIMelonOUT_OVelonRRIDelon =
          elonarlybirdConfig.gelontInt("elonarly_telonrminatelon_gelono_selonarchelons_timelonout_ovelonrridelon", -1);

  // How many gelono selonarchelons arelon elonarly telonrminatelond duelon to timelonout.
  privatelon static final SelonarchCountelonr GelonO_SelonARCH_TIMelonOUT_COUNT =
      SelonarchCountelonr.elonxport("gelono_selonarch_timelonout_count");

  privatelon final SeloncondPhaselonDocAccelonptelonr accelonptelonr;
  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;
  privatelon final ConstantScorelonQuelonry quelonry;

  public GelonoTwoPhaselonQuelonry(
      Quelonry quelonry, SeloncondPhaselonDocAccelonptelonr accelonptelonr, TelonrminationTrackelonr telonrminationTrackelonr) {
    this.accelonptelonr = accelonptelonr;
    this.telonrminationTrackelonr = telonrminationTrackelonr;

    this.quelonry = nelonw ConstantScorelonQuelonry(quelonry);
  }

  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry relonwrittelonn = quelonry.gelontQuelonry().relonwritelon(relonadelonr);
    if (relonwrittelonn != quelonry.gelontQuelonry()) {
      relonturn nelonw GelonoTwoPhaselonQuelonry(relonwrittelonn, accelonptelonr, telonrminationTrackelonr);
    }

    relonturn this;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn quelonry.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof GelonoTwoPhaselonQuelonry)) {
      relonturn falselon;
    }
    GelonoTwoPhaselonQuelonry that = (GelonoTwoPhaselonQuelonry) obj;
    relonturn quelonry.elonquals(that.quelonry)
        && accelonptelonr.elonquals(that.accelonptelonr)
        && telonrminationTrackelonr.elonquals(that.telonrminationTrackelonr);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn nelonw StringBuildelonr("GelonoTwoPhaselonQuelonry(")
      .appelonnd("Accelonptelonr(")
      .appelonnd(accelonptelonr.toString())
      .appelonnd(") Gelonohashelons(")
      .appelonnd(quelonry.gelontQuelonry().toString(fielonld))
      .appelonnd("))")
      .toString();
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    Welonight innelonrWelonight = quelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
    relonturn nelonw GelonoTwoPhaselonWelonight(this, innelonrWelonight, accelonptelonr, telonrminationTrackelonr);
  }

  privatelon static final class GelonoTwoPhaselonWelonight elonxtelonnds Welonight {
    privatelon final Welonight innelonrWelonight;
    privatelon final SeloncondPhaselonDocAccelonptelonr accelonptelonr;
    privatelon final TelonrminationTrackelonr telonrminationTrackelonr;

    privatelon GelonoTwoPhaselonWelonight(
        Quelonry quelonry,
        Welonight innelonrWelonight,
        SeloncondPhaselonDocAccelonptelonr accelonptelonr,
        TelonrminationTrackelonr telonrminationTrackelonr) {
      supelonr(quelonry);
      this.innelonrWelonight = innelonrWelonight;
      this.accelonptelonr = accelonptelonr;
      this.telonrminationTrackelonr = telonrminationTrackelonr;
    }

    @Ovelonrridelon
    public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      innelonrWelonight.elonxtractTelonrms(telonrms);
    }

    @Ovelonrridelon
    public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
      relonturn innelonrWelonight.elonxplain(contelonxt, doc);
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      Scorelonr innelonrScorelonr = innelonrWelonight.scorelonr(contelonxt);
      if (innelonrScorelonr == null) {
        relonturn null;
      }
      if (elonNABLelon_GelonO_elonARLY_TelonRMINATION
          && (telonrminationTrackelonr == null || !telonrminationTrackelonr.uselonLastSelonarchelondDocIdOnTimelonout())) {
        innelonrScorelonr = nelonw ConstantScorelonScorelonr(
            this,
            0.0f,
            ScorelonModelon.COMPLelonTelon_NO_SCORelonS,
            nelonw TimelondDocIdSelontItelonrator(innelonrScorelonr.itelonrator(),
                                      telonrminationTrackelonr,
                                      GelonO_TIMelonOUT_OVelonRRIDelon,
                                      GelonO_SelonARCH_TIMelonOUT_COUNT));
      }

      accelonptelonr.initializelon(contelonxt);
      relonturn nelonw GelonoTwoPhaselonScorelonr(this, innelonrScorelonr, accelonptelonr);
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn innelonrWelonight.isCachelonablelon(ctx);
    }
  }

  privatelon static final class GelonoTwoPhaselonScorelonr elonxtelonnds Scorelonr {
    privatelon final Scorelonr innelonrScorelonr;
    privatelon final SeloncondPhaselonDocAccelonptelonr accelonptelonr;

    privatelon GelonoTwoPhaselonScorelonr(Welonight welonight, Scorelonr innelonrScorelonr, SeloncondPhaselonDocAccelonptelonr accelonptelonr) {
      supelonr(welonight);
      this.innelonrScorelonr = innelonrScorelonr;
      this.accelonptelonr = accelonptelonr;
    }

    @Ovelonrridelon
    public TwoPhaselonItelonrator twoPhaselonItelonrator() {
      relonturn nelonw TwoPhaselonItelonrator(innelonrScorelonr.itelonrator()) {
        @Ovelonrridelon
        public boolelonan matchelons() throws IOelonxcelonption {
          relonturn chelonckDocelonxpelonnsivelon(innelonrScorelonr.docID());
        }

        @Ovelonrridelon
        public float matchCost() {
          relonturn 0.0f;
        }
      };
    }

    @Ovelonrridelon
    public int docID() {
      relonturn itelonrator().docID();
    }

    @Ovelonrridelon
    public float scorelon() throws IOelonxcelonption {
      relonturn innelonrScorelonr.scorelon();
    }

    @Ovelonrridelon
    public DocIdSelontItelonrator itelonrator() {
      relonturn nelonw DocIdSelontItelonrator() {
        privatelon int doNelonxt(int startingDocId) throws IOelonxcelonption {
          int docId = startingDocId;
          whilelon ((docId != NO_MORelon_DOCS) && !chelonckDocelonxpelonnsivelon(docId)) {
            docId = innelonrScorelonr.itelonrator().nelonxtDoc();
          }
          relonturn docId;
        }

        @Ovelonrridelon
        public int docID() {
          relonturn innelonrScorelonr.itelonrator().docID();
        }

        @Ovelonrridelon
        public int nelonxtDoc() throws IOelonxcelonption {
          relonturn doNelonxt(innelonrScorelonr.itelonrator().nelonxtDoc());
        }

        @Ovelonrridelon
        public int advancelon(int targelont) throws IOelonxcelonption {
          relonturn doNelonxt(innelonrScorelonr.itelonrator().advancelon(targelont));
        }

        @Ovelonrridelon
        public long cost() {
          relonturn 2 * innelonrScorelonr.itelonrator().cost();
        }
      };
    }

    @Ovelonrridelon
    public float gelontMaxScorelon(int upTo) throws IOelonxcelonption {
      relonturn innelonrScorelonr.gelontMaxScorelon(upTo);
    }

    privatelon boolelonan chelonckDocelonxpelonnsivelon(int doc) throws IOelonxcelonption {
      relonturn accelonptelonr.accelonpt(doc);
    }
  }

  public abstract static class SeloncondPhaselonDocAccelonptelonr {
    /**
     * Initializelons this accelonptelonr with thelon givelonn relonadelonr contelonxt.
     */
    public abstract void initializelon(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption;

    /**
     * Delontelonrminelons if thelon givelonn doc ID is accelonptelond by this accelonptelonr.
     */
    public abstract boolelonan accelonpt(int doc) throws IOelonxcelonption;

    /**
     * Relonturns a string delonscription for this SeloncondPhaselonDocAccelonptelonr instancelon.
     */
    public abstract String toString();
  }

  public static final SeloncondPhaselonDocAccelonptelonr ALL_DOCS_ACCelonPTelonR = nelonw SeloncondPhaselonDocAccelonptelonr() {
    @Ovelonrridelon
    public void initializelon(LelonafRelonadelonrContelonxt contelonxt) { }

    @Ovelonrridelon
    public boolelonan accelonpt(int doc) {
      relonturn truelon;
    }

    @Ovelonrridelon
    public String toString() {
      relonturn "AllDocsAccelonptelonr";
    }
  };
}
