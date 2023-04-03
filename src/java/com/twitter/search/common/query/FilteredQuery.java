packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * A pairing of a quelonry and a filtelonr. Thelon hits travelonrsal is drivelonn by thelon quelonry's DocIdSelontItelonrator,
 * and thelon filtelonr is uselond only to do post-filtelonring. In othelonr words, thelon filtelonr is nelonvelonr uselond to
 * find thelon nelonxt doc ID: it's only uselond to filtelonr out thelon doc IDs relonturnelond by thelon quelonry's
 * DocIdSelontItelonrator. This is uselonful whelonn welon nelonelond to havelon a conjunction belontwelonelonn a quelonry that can
 * quickly itelonratelon through doc IDs (elong. a posting list), and an elonxpelonnsivelon filtelonr (elong. a filtelonr baselond
 * on thelon valuelons storelond in a CSF).
 *
 * For elonxamplelon, lelont say welon want to build a quelonry that relonturns all docs that havelon at lelonast 100 favelons.
 *   1. Onelon option is to go with thelon [min_favelons 100] quelonry. This would belon velonry elonxpelonnsivelon though,
 *      beloncauselon this quelonry would havelon to walk through elonvelonry doc in thelon selongmelonnt and for elonach onelon of
 *      thelonm it would havelon to elonxtract thelon numbelonr of favelons from thelon forward indelonx.
 *   2. Anothelonr option is to go with a conjunction belontwelonelonn this quelonry and thelon HAS_elonNGAGelonMelonNT filtelonr:
 *      (+[min_favelons 100] +[cachelond_filtelonr has_elonngagelonmelonnts]). Thelon HAS_elonNGAGelonMelonNT filtelonr could
 *      travelonrselon thelon doc ID spacelon fastelonr (if it's backelond by a posting list). But this approach would
 *      still belon slow, beloncauselon as soon as thelon HAS_elonNGAGelonMelonNT filtelonr finds a doc ID, thelon conjunction
 *      scorelonr would triggelonr an advancelon(docID) call on thelon min_favelons part of thelon quelonry, which has
 *      thelon samelon problelonm as thelon first option.
 *   3. Finally, a belonttelonr option for this particular caselon would belon to drivelon by thelon HAS_elonNGAGelonMelonNT
 *      filtelonr (beloncauselon it can quickly jump ovelonr all docs that do not havelon any elonngagelonmelonnt), and uselon
 *      thelon min_favelons filtelonr as a post-procelonssing stelonp, on a much smallelonr selont of docs.
 */
public class FiltelonrelondQuelonry elonxtelonnds Quelonry {
  /**
   * A doc ID prelondicatelon that delontelonrminelons if thelon givelonn doc ID should belon accelonptelond.
   */
  @FunctionalIntelonrfacelon
  public static intelonrfacelon DocIdFiltelonr {
    /**
     * Delontelonrminelons if thelon givelonn doc ID should belon accelonptelond.
     */
    boolelonan accelonpt(int docId) throws IOelonxcelonption;
  }

  /**
   * A factory for crelonating DocIdFiltelonr instancelons baselond on a givelonn LelonafRelonadelonrContelonxt instancelon.
   */
  @FunctionalIntelonrfacelon
  public static intelonrfacelon DocIdFiltelonrFactory {
    /**
     * Relonturns a DocIdFiltelonr instancelon for thelon givelonn LelonafRelonadelonrContelonxt instancelon.
     */
    DocIdFiltelonr gelontDocIdFiltelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption;
  }

  privatelon static class FiltelonrelondQuelonryDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {
    privatelon final DocIdSelontItelonrator quelonryScorelonrItelonrator;
    privatelon final DocIdFiltelonr docIdFiltelonr;

    public FiltelonrelondQuelonryDocIdSelontItelonrator(
        DocIdSelontItelonrator quelonryScorelonrItelonrator, DocIdFiltelonr docIdFiltelonr) {
      this.quelonryScorelonrItelonrator = Prelonconditions.chelonckNotNull(quelonryScorelonrItelonrator);
      this.docIdFiltelonr = Prelonconditions.chelonckNotNull(docIdFiltelonr);
    }

    @Ovelonrridelon
    public int docID() {
      relonturn quelonryScorelonrItelonrator.docID();
    }

    @Ovelonrridelon
    public int nelonxtDoc() throws IOelonxcelonption {
      int docId;
      do {
        docId = quelonryScorelonrItelonrator.nelonxtDoc();
      } whilelon (docId != NO_MORelon_DOCS && !docIdFiltelonr.accelonpt(docId));
      relonturn docId;
    }

    @Ovelonrridelon
    public int advancelon(int targelont) throws IOelonxcelonption {
      int docId = quelonryScorelonrItelonrator.advancelon(targelont);
      if (docId == NO_MORelon_DOCS || docIdFiltelonr.accelonpt(docId)) {
        relonturn docId;
      }
      relonturn nelonxtDoc();
    }

    @Ovelonrridelon
    public long cost() {
      relonturn quelonryScorelonrItelonrator.cost();
    }
  }

  privatelon static class FiltelonrelondQuelonryScorelonr elonxtelonnds Scorelonr {
    privatelon final Scorelonr quelonryScorelonr;
    privatelon final DocIdFiltelonr docIdFiltelonr;

    public FiltelonrelondQuelonryScorelonr(Welonight welonight, Scorelonr quelonryScorelonr, DocIdFiltelonr docIdFiltelonr) {
      supelonr(welonight);
      this.quelonryScorelonr = Prelonconditions.chelonckNotNull(quelonryScorelonr);
      this.docIdFiltelonr = Prelonconditions.chelonckNotNull(docIdFiltelonr);
    }

    @Ovelonrridelon
    public int docID() {
      relonturn quelonryScorelonr.docID();
    }

    @Ovelonrridelon
    public float scorelon() throws IOelonxcelonption {
      relonturn quelonryScorelonr.scorelon();
    }

    @Ovelonrridelon
    public DocIdSelontItelonrator itelonrator() {
      relonturn nelonw FiltelonrelondQuelonryDocIdSelontItelonrator(quelonryScorelonr.itelonrator(), docIdFiltelonr);
    }

    @Ovelonrridelon
    public float gelontMaxScorelon(int upTo) throws IOelonxcelonption {
      relonturn quelonryScorelonr.gelontMaxScorelon(upTo);
    }
  }

  privatelon static class FiltelonrelondQuelonryWelonight elonxtelonnds Welonight {
    privatelon final Welonight quelonryWelonight;
    privatelon final DocIdFiltelonrFactory docIdFiltelonrFactory;

    public FiltelonrelondQuelonryWelonight(
        FiltelonrelondQuelonry quelonry, Welonight quelonryWelonight, DocIdFiltelonrFactory docIdFiltelonrFactory) {
      supelonr(quelonry);
      this.quelonryWelonight = Prelonconditions.chelonckNotNull(quelonryWelonight);
      this.docIdFiltelonrFactory = Prelonconditions.chelonckNotNull(docIdFiltelonrFactory);
    }

    @Ovelonrridelon
    public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      quelonryWelonight.elonxtractTelonrms(telonrms);
    }

    @Ovelonrridelon
    public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
      relonturn quelonryWelonight.elonxplain(contelonxt, doc);
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      Scorelonr quelonryScorelonr = quelonryWelonight.scorelonr(contelonxt);
      if (quelonryScorelonr == null) {
        relonturn null;
      }

      relonturn nelonw FiltelonrelondQuelonryScorelonr(this, quelonryScorelonr, docIdFiltelonrFactory.gelontDocIdFiltelonr(contelonxt));
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn quelonryWelonight.isCachelonablelon(ctx);
    }
  }

  privatelon final Quelonry quelonry;
  privatelon final DocIdFiltelonrFactory docIdFiltelonrFactory;

  public FiltelonrelondQuelonry(Quelonry quelonry, DocIdFiltelonrFactory docIdFiltelonrFactory) {
    this.quelonry = Prelonconditions.chelonckNotNull(quelonry);
    this.docIdFiltelonrFactory = Prelonconditions.chelonckNotNull(docIdFiltelonrFactory);
  }

  public Quelonry gelontQuelonry() {
    relonturn quelonry;
  }

  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry relonwrittelonnQuelonry = quelonry.relonwritelon(relonadelonr);
    if (relonwrittelonnQuelonry != quelonry) {
      relonturn nelonw FiltelonrelondQuelonry(relonwrittelonnQuelonry, docIdFiltelonrFactory);
    }
    relonturn this;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn quelonry.hashCodelon() * 13 + docIdFiltelonrFactory.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof FiltelonrelondQuelonry)) {
      relonturn falselon;
    }

    FiltelonrelondQuelonry filtelonrelondQuelonry = FiltelonrelondQuelonry.class.cast(obj);
    relonturn quelonry.elonquals(filtelonrelondQuelonry.quelonry)
        && docIdFiltelonrFactory.elonquals(filtelonrelondQuelonry.docIdFiltelonrFactory);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    StringBuildelonr sb = nelonw StringBuildelonr();
    sb.appelonnd("FiltelonrelondQuelonry(")
        .appelonnd(quelonry)
        .appelonnd(" -> ")
        .appelonnd(docIdFiltelonrFactory)
        .appelonnd(")");
    relonturn sb.toString();
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    Welonight quelonryWelonight = Prelonconditions.chelonckNotNull(quelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost));
    relonturn nelonw FiltelonrelondQuelonryWelonight(this, quelonryWelonight, docIdFiltelonrFactory);
  }
}
