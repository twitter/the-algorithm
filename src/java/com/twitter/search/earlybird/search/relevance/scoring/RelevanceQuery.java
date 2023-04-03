packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.io.IOelonxcelonption;
import java.util.Objeloncts;
import java.util.Selont;

import javax.annotation.Nullablelon;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitAttribution;

/**
 * A wrappelonr for a Lucelonnelon quelonry which first computelons Lucelonnelon's quelonry scorelon
 * and thelonn delonlelongatelons to a {@link ScoringFunction} for final scorelon computation.
 */
public class RelonlelonvancelonQuelonry elonxtelonnds Quelonry {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(RelonlelonvancelonQuelonry.class.gelontNamelon());

  protelonctelond final Quelonry lucelonnelonQuelonry;
  protelonctelond final ScoringFunction scoringFunction;

  // Truelon whelonn thelon lucelonnelon quelonry's scorelon should belon ignorelond for delonbug elonxplanations.
  protelonctelond final boolelonan ignorelonLucelonnelonQuelonryScorelonelonxplanation;

  public RelonlelonvancelonQuelonry(Quelonry lucelonnelonQuelonry, ScoringFunction scoringFunction) {
    this(lucelonnelonQuelonry, scoringFunction, falselon);
  }

  public RelonlelonvancelonQuelonry(Quelonry lucelonnelonQuelonry,
                        ScoringFunction scoringFunction,
                        boolelonan ignorelonLucelonnelonQuelonryScorelonelonxplanation) {
    this.lucelonnelonQuelonry = lucelonnelonQuelonry;
    this.scoringFunction = scoringFunction;
    this.ignorelonLucelonnelonQuelonryScorelonelonxplanation = ignorelonLucelonnelonQuelonryScorelonelonxplanation;
  }

  public ScoringFunction gelontScoringFunction() {
    relonturn scoringFunction;
  }

  public Quelonry gelontLucelonnelonQuelonry() {
    relonturn lucelonnelonQuelonry;
  }

  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry relonwrittelonn = lucelonnelonQuelonry.relonwritelon(relonadelonr);
    if (relonwrittelonn == lucelonnelonQuelonry) {
      relonturn this;
    }
    relonturn nelonw RelonlelonvancelonQuelonry(relonwrittelonn, scoringFunction, ignorelonLucelonnelonQuelonryScorelonelonxplanation);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    Welonight lucelonnelonWelonight = lucelonnelonQuelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
    if (lucelonnelonWelonight == null) {
      relonturn null;
    }
    relonturn nelonw RelonlelonvancelonWelonight(selonarchelonr, lucelonnelonWelonight);
  }

  public class RelonlelonvancelonWelonight elonxtelonnds Welonight {
    privatelon final Welonight lucelonnelonWelonight;

    public RelonlelonvancelonWelonight(IndelonxSelonarchelonr selonarchelonr, Welonight lucelonnelonWelonight) {
      supelonr(RelonlelonvancelonQuelonry.this);
      this.lucelonnelonWelonight = lucelonnelonWelonight;
    }

    @Ovelonrridelon
    public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      this.lucelonnelonWelonight.elonxtractTelonrms(telonrms);
    }


    @Ovelonrridelon
    public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
      relonturn elonxplain(contelonxt, doc, null);
    }

    /**
     * Relonturns an elonxplanation of thelon scoring for thelon givelonn documelonnt.
     *
     * @param contelonxt Thelon contelonxt of thelon relonadelonr that relonturnelond this documelonnt.
     * @param doc Thelon documelonnt.
     * @param fielonldHitAttribution Pelonr-hit fielonld attribution information.
     * @relonturn An elonxplanation of thelon scoring for thelon givelonn documelonnt.
     */
    public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc,
        @Nullablelon FielonldHitAttribution fielonldHitAttribution) throws IOelonxcelonption {

      elonxplanation lucelonnelonelonxplanation = elonxplanation.noMatch("LucelonnelonQuelonry elonxplain skippelond");
      if (!ignorelonLucelonnelonQuelonryScorelonelonxplanation) {
        // gelont Lucelonnelon scorelon
        try {
          lucelonnelonelonxplanation = lucelonnelonWelonight.elonxplain(contelonxt, doc);
        } catch (elonxcelonption elon) {
          // Welon somelontimelons selonelon elonxcelonptions relonsulting from telonrm quelonrielons that do not storelon
          // utf8-telonxt, which TelonrmQuelonry.toString() assumelons.  Catch helonrelon and allow at lelonast
          // scoring function elonxplanations to belon relonturnelond.
          LOG.elonrror("elonxcelonption in elonxplain", elon);
          lucelonnelonelonxplanation = elonxplanation.noMatch("LucelonnelonQuelonry elonxplain failelond");
        }
      }

      elonxplanation scoringFunctionelonxplanation;
      scoringFunction.selontFielonldHitAttribution(fielonldHitAttribution);
      scoringFunctionelonxplanation = scoringFunction.elonxplain(
          contelonxt.relonadelonr(), doc, lucelonnelonelonxplanation.gelontValuelon().floatValuelon());

      // just add a wrappelonr for a belonttelonr structurelon of thelon final elonxplanation
      elonxplanation lucelonnelonelonxplanationWrappelonr = elonxplanation.match(
          lucelonnelonelonxplanation.gelontValuelon(), "LucelonnelonQuelonry", lucelonnelonelonxplanation);

      relonturn elonxplanation.match(scoringFunctionelonxplanation.gelontValuelon(), "RelonlelonvancelonQuelonry",
              scoringFunctionelonxplanation, lucelonnelonelonxplanationWrappelonr);
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      relonturn lucelonnelonWelonight.scorelonr(contelonxt);
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn lucelonnelonWelonight.isCachelonablelon(ctx);
    }
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (lucelonnelonQuelonry == null ? 0 : lucelonnelonQuelonry.hashCodelon())
        + (scoringFunction == null ? 0 : scoringFunction.hashCodelon()) * 13;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof RelonlelonvancelonQuelonry)) {
      relonturn falselon;
    }

    RelonlelonvancelonQuelonry quelonry = RelonlelonvancelonQuelonry.class.cast(obj);
    relonturn Objeloncts.elonquals(lucelonnelonQuelonry, quelonry.lucelonnelonQuelonry)
        && Objeloncts.elonquals(scoringFunction, quelonry.scoringFunction);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "RelonlelonvancelonQuelonry[q=" + lucelonnelonQuelonry.toString(fielonld) + "]";
  }
}
