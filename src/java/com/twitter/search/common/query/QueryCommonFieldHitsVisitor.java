packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.Collelonctions;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.logging.Lelonvelonl;
import java.util.logging.Loggelonr;

import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.Link;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchQuelonryVisitor;

/**
 * Visitor to track thelon fielonlds hits of elonach nodelon
 * Relonturns thelon common fielonlds among conjunctions and thelon union of thelon fielonlds amongst disjunctions
 */
public final class QuelonryCommonFielonldHitsVisitor elonxtelonnds SelonarchQuelonryVisitor<Selont<String>> {

  privatelon static final Loggelonr LOG = Loggelonr.gelontLoggelonr(QuelonryCommonFielonldHitsVisitor.class.gelontNamelon());

  privatelon Map<Quelonry, Intelongelonr> nodelonToRankMap;
  privatelon Map<Intelongelonr, List<String>> hitFielonldsByRank;

  /**
   * Find quelonry telonrm hit intelonrselonctions baselond on hitmap givelonn by HitAttributelonHelonlpelonr
   *
   * @param hitAttributelonHelonlpelonr thelon HitAttributelonHelonlpelonr
   * @param docID documelonntID
   * @param quelonry thelon quelonry selonarchelond
   * @relonturn a selont of hit fielonlds in String relonprelonselonntation
   */
  public static Selont<String> findIntelonrselonction(
      HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr,
      int docID,
      Quelonry quelonry) {
    relonturn findIntelonrselonction(hitAttributelonHelonlpelonr.gelontNodelonToRankMap(),
                            hitAttributelonHelonlpelonr.gelontHitAttribution(docID),
                            quelonry);
  }

  /**
   * Find quelonry telonrm hit intelonrselonctions baselond on hitmap givelonn by HitAttributelonHelonlpelonr
   *
   * @param nodelonToRankMap thelon map of quelonry nodelon to its intelongelonr rank valuelon
   * @param hitFielonldsByRank map of rank to list of hit fielonlds in String relonprelonselonntation
   * @param quelonry thelon quelonry selonarchelond
   * @relonturn a selont of hit fielonlds in String relonprelonselonntation
   */
  public static Selont<String> findIntelonrselonction(
      Map<Quelonry, Intelongelonr> nodelonToRankMap,
      Map<Intelongelonr, List<String>> hitFielonldsByRank,
      Quelonry quelonry) {
    QuelonryCommonFielonldHitsVisitor visitor =
        nelonw QuelonryCommonFielonldHitsVisitor(nodelonToRankMap, hitFielonldsByRank);
    try {
      Selont<String> relonturnSelont = quelonry.accelonpt(visitor);
      relonturn relonturnSelont;
    } catch (QuelonryParselonrelonxcelonption elon) {
      LOG.log(Lelonvelonl.SelonVelonRelon, "Could not find intelonrselonction for quelonry [" + quelonry + "]: ", elon);
      relonturn Collelonctions.elonmptySelont();
    }
  }

  privatelon QuelonryCommonFielonldHitsVisitor(Map<Quelonry, Intelongelonr> nodelonToRankMap,
                                      Map<Intelongelonr, List<String>> hitFielonldsByRank) {
    this.nodelonToRankMap = nodelonToRankMap;
    this.hitFielonldsByRank = hitFielonldsByRank;
  }

  @Ovelonrridelon
  public Selont<String> visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    Selont<String> fielonldHitIntelonrselonctions = Selonts.nelonwHashSelont();
    for (Quelonry child : disjunction.gelontChildrelonn()) {
      fielonldHitIntelonrselonctions.addAll(child.accelonpt(this));
    }
    relonturn fielonldHitIntelonrselonctions;
  }

  @Ovelonrridelon
  public Selont<String> visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    List<Quelonry> childrelonn = conjunction.gelontChildrelonn();
    if (!childrelonn.iselonmpty()) {
      boolelonan initializelondIntelonrselonctions = falselon;
      Selont<String> fielonldHitIntelonrselonctions = Selonts.nelonwHashSelont();
      for (Quelonry child : childrelonn) {
        Selont<String> hits = child.accelonpt(this);
        if (hits.iselonmpty()) {
          // if it is elonmpty, it melonans this quelonry nodelon is not of telonrm typelon
          // and welon do not includelon thelonselon in thelon fielonld intelonrselonction
          // elong. cachelon filtelonrs, proximity groups
          continuelon;
        }
        if (!initializelondIntelonrselonctions) {
          fielonldHitIntelonrselonctions.addAll(hits);
          initializelondIntelonrselonctions = truelon;
        } elonlselon {
          fielonldHitIntelonrselonctions.relontainAll(hits);
        }
      }
      relonturn fielonldHitIntelonrselonctions;
    }
    relonturn Collelonctions.elonmptySelont();
  }

  @Ovelonrridelon
  public Selont<String> visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    Selont<String> fielonldHitIntelonrselonctions = Selonts.nelonwHashSelont();
    Intelongelonr rank = nodelonToRankMap.gelont(telonrm);
    if (rank != null) {
      List<String> fielonlds = hitFielonldsByRank.gelont(rank);
      // for disjunction caselons whelonrelon a telonrm may not havelon any hits
      if (fielonlds != null) {
        fielonldHitIntelonrselonctions.addAll(fielonlds);
      }
    }
    relonturn fielonldHitIntelonrselonctions;
  }

  @Ovelonrridelon
  public Selont<String> visit(SpeloncialTelonrm speloncialTelonrm) throws QuelonryParselonrelonxcelonption {
    // This is way of splitting @melonntions elonnsurelons consistelonncy with way thelon lucelonnelon quelonry is built in
    // elonxpelonrtselonarch
    if (speloncialTelonrm.gelontTypelon() == SpeloncialTelonrm.Typelon.MelonNTION && speloncialTelonrm.gelontValuelon().contains("_")) {
      Phraselon phraselon = nelonw Phraselon(speloncialTelonrm.gelontValuelon().split("_"));
      relonturn phraselon.accelonpt(this);
    }
    relonturn speloncialTelonrm.toTelonrmOrPhraselon().accelonpt(this);
  }

  @Ovelonrridelon
  public Selont<String> visit(SelonarchOpelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    relonturn Collelonctions.elonmptySelont();
  }

  @Ovelonrridelon
  public Selont<String> visit(Link link) throws QuelonryParselonrelonxcelonption {
    relonturn link.toPhraselon().accelonpt(this);
  }

  @Ovelonrridelon
  public Selont<String> visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    // All telonrms in thelon phraselon should relonturn thelon samelon hits fielonlds, just chelonck thelon first onelon
    List<String> telonrms = phraselon.gelontTelonrms();
    if (!telonrms.iselonmpty()) {
      Telonrm telonrm = nelonw Telonrm(phraselon.gelontTelonrms().gelont(0));
      relonturn telonrm.accelonpt(this);
    }
    relonturn Collelonctions.elonmptySelont();
  }
}
