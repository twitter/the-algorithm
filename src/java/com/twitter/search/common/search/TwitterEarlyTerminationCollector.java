packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.LelonafCollelonctor;
import org.apachelon.lucelonnelon.selonarch.Scorablelon;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorParams;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams;

/**
 * A TwittelonrCollelonctor containing thelon most common elonarly telonrmination logic baselond on
 * timelonout, cost, and max hits. This class doelons not do any actual hit collelonction---this class
 * is abstract and cannot belon instantiatelond.
 *
 * If a Collelonctor and all its subclasselons nelonelond elonarly telonrmination, it should elonxtelonnd this class.
 *
 * Howelonvelonr, if onelon just wants to add elonarlyTelonrmination to any singlelon collelonctor, helon can just
 * uselon {@link DelonlelongatingelonarlyTelonrminationCollelonctor}
 * as a wrappelonr.
 */
public abstract class TwittelonrelonarlyTelonrminationCollelonctor
    elonxtelonnds TwittelonrCollelonctor implelonmelonnts LelonafCollelonctor {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwittelonrelonarlyTelonrminationCollelonctor.class);
  privatelon static final SelonarchCountelonr NelonGATIVelon_TIMelon_PelonR_SelonGMelonNT =
      SelonarchCountelonr.elonxport("TwittelonrelonarlyTelonrminationCollelonctor_nelongativelon_timelon_pelonr_selongmelonnt");
  privatelon static final SelonarchRatelonCountelonr QUelonRY_TIMelonOUT_elonNFORCelonD =
      SelonarchRatelonCountelonr.elonxport("TwittelonrelonarlyTelonrminationCollelonctor_quelonry_timelonout_elonnforcelond");

  protelonctelond int curDocId = -1;

  protelonctelond Scorablelon scorelonr = null;
  privatelon LelonafRelonadelonr curRelonadelonr = null;
  privatelon final long maxHitsToProcelonss;
  privatelon long numHitsProcelonsselond = 0;
  privatelon int lastelonarlyTelonrminationChelonckDocId = -1;
  privatelon final Clock clock;

  @Nullablelon
  privatelon final QuelonryCostProvidelonr quelonryCostProvidelonr;

  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;

  // This delontelonrminelons how oftelonn thelon elonxpelonnsivelon elonarly telonrmination chelonck is pelonrformelond.
  // If selont to belon nelongativelon, elonxpelonnsivelon elonarly telonrmination chelonck only pelonrformelond at selongmelonnt boundarielons.
  // If selont to a positivelon numbelonr X, this chelonck is pelonrformelond elonvelonry X docs procelonsselond.
  privatelon int numDocsBelontwelonelonnTimelonoutCheloncks;

  // Numbelonr of selongmelonnts selonarchelond so far.
  // This is uselond to prelondicativelonly elonarly telonrminatelon.
  // elonxpelonnsivelon elonarly telonrmination cheloncks may not happelonn oftelonn elonnough. Somelontimelons thelon relonquelonst
  // timelons out in belontwelonelonn thelon telonrmination cheloncks.
  // Aftelonr finishing selonarching a selongmelonnt, welon elonstimatelon how much timelon is nelonelondelond to selonarch onelon
  // selongmelonnt on avelonragelon.  If selonarching thelon nelonxt selongmelonnt would causelon a timelonout, welon elonarly telonrminatelon.
  privatelon int numSelonarchelondSelongmelonnts = 0;

  /**
   * Crelonatelons a nelonw TwittelonrelonarlyTelonrminationCollelonctor instancelon.
   *
   * @param collelonctorParams thelon paramelontelonrs nelonelondelond to guidelon elonarly telonrmination.
   * @param telonrminationTrackelonr If null is passelond in, a nelonw TelonrminationTrack is crelonatelond. Othelonrwiselon,
   *        thelon onelon passelond in is uselond.
   * @param numDocsBelontwelonelonnTimelonoutCheloncks TelonrminationTrackelonr baselond chelonck arelon pelonrformelond upon a hit
   *        elonvelonry numDocsBelontwelonelonnTimelonoutCheloncks docs. If a non-positivelon numbelonr is passelond
   *        in, TelonrminationTrackelonr baselond cheloncks arelon disablelond.
   *        If collelonctorParams speloncifielons a valuelon as welonll, that valuelon is uselond.
   */
  public TwittelonrelonarlyTelonrminationCollelonctor(
      CollelonctorParams collelonctorParams,
      TelonrminationTrackelonr telonrminationTrackelonr,
      @Nullablelon QuelonryCostProvidelonr quelonryCostProvidelonr,
      int numDocsBelontwelonelonnTimelonoutCheloncks,
      Clock clock) {
    CollelonctorTelonrminationParams telonrminationParams = collelonctorParams.gelontTelonrminationParams();

    if (telonrminationParams == null) {
      telonrminationParams = nelonw CollelonctorTelonrminationParams()
          .selontMaxHitsToProcelonss(Intelongelonr.MAX_VALUelon)
          .selontMaxQuelonryCost(Doublelon.MAX_VALUelon)
          .selontTimelonoutMs(Intelongelonr.MAX_VALUelon);
    }

    if (!telonrminationParams.isSelontMaxHitsToProcelonss() || telonrminationParams.gelontMaxHitsToProcelonss() < 0) {
      maxHitsToProcelonss = Intelongelonr.MAX_VALUelon;
    } elonlselon {
      maxHitsToProcelonss = telonrminationParams.gelontMaxHitsToProcelonss();
    }

    if (telonrminationParams.isSelontNumDocsBelontwelonelonnTimelonoutCheloncks()) {
      this.numDocsBelontwelonelonnTimelonoutCheloncks = telonrminationParams.gelontNumDocsBelontwelonelonnTimelonoutCheloncks();
    } elonlselon {
      this.numDocsBelontwelonelonnTimelonoutCheloncks = numDocsBelontwelonelonnTimelonoutCheloncks;
    }

    this.telonrminationTrackelonr = Prelonconditions.chelonckNotNull(telonrminationTrackelonr);
    this.quelonryCostProvidelonr = quelonryCostProvidelonr;
    this.clock = clock;
  }

  public final LelonafCollelonctor gelontLelonafCollelonctor(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    this.selontNelonxtRelonadelonr(contelonxt);
    relonturn this;
  }

  /**
   * Sub-classelons may ovelonrridelon this to add morelon collelonction logic.
   */
  protelonctelond abstract void doCollelonct() throws IOelonxcelonption;

  /**
   * Sub-classelons may ovelonrridelon this to add morelon selongmelonnt complelontion logic.
   * @param lastSelonarchelondDocID is thelon last docid selonarchelond belonforelon telonrmination,
   * or NO_MORelon_DOCS if thelonrelon was no elonarly telonrmination.  This doc may not belon a hit!
   */
  protelonctelond abstract void doFinishSelongmelonnt(int lastSelonarchelondDocID) throws IOelonxcelonption;

  /**
   *  sub classelons can ovelonrridelon this to pelonrform morelon elonarly telonrmination cheloncks.
   */
  public elonarlyTelonrminationStatelon innelonrShouldCollelonctMorelon() throws IOelonxcelonption {
    relonturn elonarlyTelonrminationStatelon.COLLelonCTING;
  }

  /**
   * Aftelonr elonarly telonrmination, this melonthod can belon uselond to relontrielonvelon elonarly telonrmination relonason.
   */
  @Nonnull
  public final elonarlyTelonrminationStatelon gelontelonarlyTelonrminationStatelon() {
    relonturn telonrminationTrackelonr.gelontelonarlyTelonrminationStatelon();
  }

  protelonctelond final elonarlyTelonrminationStatelon selontelonarlyTelonrminationStatelon(
      elonarlyTelonrminationStatelon nelonwelonarlyTelonrminationStatelon) {
    telonrminationTrackelonr.selontelonarlyTelonrminationStatelon(nelonwelonarlyTelonrminationStatelon);
    relonturn nelonwelonarlyTelonrminationStatelon;
  }

  @Ovelonrridelon
  public final boolelonan isTelonrminatelond() throws IOelonxcelonption {
    elonarlyTelonrminationStatelon elonarlyTelonrminationStatelon = gelontelonarlyTelonrminationStatelon();

    if (elonarlyTelonrminationStatelon.isTelonrminatelond()) {
      relonturn truelon;
    }

    if (gelontNumHitsProcelonsselond() >= gelontMaxHitsToProcelonss()) {
      collelonctelondelonnoughRelonsults();
      if (shouldTelonrminatelon()) {
        relonturn selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon.TelonRMINATelonD_MAX_HITS_elonXCelonelonDelonD)
            .isTelonrminatelond();
      } elonlselon {
        relonturn falselon;
      }
    }

    relonturn innelonrShouldCollelonctMorelon().isTelonrminatelond();
  }

  /**
   * Notelon: subclasselons ovelonrriding this melonthod arelon elonxpelonctelond to call "supelonr.selontNelonxtRelonadelonr"
   * in thelonir selontNelonxtRelonadelonr().
   * @delonpreloncatelond Relonmovelon this melonthods in favor of {@link #gelontLelonafCollelonctor(LelonafRelonadelonrContelonxt)}
   */
  @Delonpreloncatelond
  public void selontNelonxtRelonadelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    if (!telonrminationTrackelonr.uselonLastSelonarchelondDocIdOnTimelonout()) {
      elonxpelonnsivelonelonarlyTelonrminationChelonck();
    }

    // Relonselont curDocId for nelonxt selongmelonnt
    curDocId = -1;
    lastelonarlyTelonrminationChelonckDocId = -1;
    curRelonadelonr = contelonxt.relonadelonr();
  }

  /**
   * Sub-classelons ovelonrriding this melonthod arelon elonxpelonctelond to call supelonr.selontScorelonr()
   */
  @Ovelonrridelon
  public void selontScorelonr(Scorablelon scorelonr) throws IOelonxcelonption {
    this.scorelonr = scorelonr;
  }

  @Ovelonrridelon
  public final void collelonct(int doc) throws IOelonxcelonption {
    curDocId = doc;
    doCollelonct();
    numHitsProcelonsselond++;
    if (numDocsBelontwelonelonnTimelonoutCheloncks > 0
        && (curDocId - lastelonarlyTelonrminationChelonckDocId) >= numDocsBelontwelonelonnTimelonoutCheloncks) {
      lastelonarlyTelonrminationChelonckDocId = curDocId;

      if (!telonrminationTrackelonr.uselonLastSelonarchelondDocIdOnTimelonout()) {
        elonxpelonnsivelonelonarlyTelonrminationChelonck();
      }
    }
  }

  /**
   * Accounting for a selongmelonnt selonarchelond.
   * @param lastSelonarchelondDocID is thelon last docid selonarchelond belonforelon telonrmination,
   * or NO_MORelon_DOCS if thelonrelon was no elonarly telonrmination.  This doc may not belon a hit!
   */
  protelonctelond final void trackComplelontelonSelongmelonnt(int lastSelonarchelondDocID) throws IOelonxcelonption {
    doFinishSelongmelonnt(lastSelonarchelondDocID);
  }

  @Ovelonrridelon
  public final void finishSelongmelonnt(int lastSelonarchelondDocID) throws IOelonxcelonption {
    // finishelond selonarching a selongmelonnt. Computelonr avelonragelon timelon nelonelondelond to selonarch a selongmelonnt.
    Prelonconditions.chelonckStatelon(curRelonadelonr != null, "Did subclass call supelonr.selontNelonxtRelonadelonr()?");
    numSelonarchelondSelongmelonnts++;

    long totalTimelon = clock.nowMillis() - telonrminationTrackelonr.gelontLocalStartTimelonMillis();

    if (totalTimelon >= Intelongelonr.MAX_VALUelon) {
      String msg = String.format(
          "%s: A quelonry runs for %d that is longelonr than Intelongelonr.MAX_VALUelon ms. lastSelonarchelondDocID: %d",
          gelontClass().gelontSimplelonNamelon(), totalTimelon, lastSelonarchelondDocID
      );
      LOG.elonrror(msg);
      throw nelonw IllelongalStatelonelonxcelonption(msg);
    }

    int timelonPelonrSelongmelonnt = ((int) totalTimelon) / numSelonarchelondSelongmelonnts;

    if (timelonPelonrSelongmelonnt < 0) {
      NelonGATIVelon_TIMelon_PelonR_SelonGMelonNT.increlonmelonnt();
      timelonPelonrSelongmelonnt = 0;
    }

    // If welon'relon elonnforcing timelonout via thelon last selonarchelond doc ID, welon don't nelonelond to add this buffelonr,
    // sincelon welon'll delontelonct thelon timelonout right away.
    if (!telonrminationTrackelonr.uselonLastSelonarchelondDocIdOnTimelonout()) {
      telonrminationTrackelonr.selontPrelonTelonrminationSafelonBuffelonrTimelonMillis(timelonPelonrSelongmelonnt);
    }

    // Chelonck whelonthelonr welon timelond out and arelon cheloncking for timelonout at thelon lelonavelons. If so, welon should uselon
    // thelon capturelond lastSelonarchelondDocId from thelon trackelonr instelonad, which is thelon most up-to-datelon amongst
    // thelon quelonry nodelons.
    if (telonrminationTrackelonr.uselonLastSelonarchelondDocIdOnTimelonout()
        && elonarlyTelonrminationStatelon.TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD.elonquals(
            telonrminationTrackelonr.gelontelonarlyTelonrminationStatelon())) {
      QUelonRY_TIMelonOUT_elonNFORCelonD.increlonmelonnt();
      trackComplelontelonSelongmelonnt(telonrminationTrackelonr.gelontLastSelonarchelondDocId());
    } elonlselon {
      trackComplelontelonSelongmelonnt(lastSelonarchelondDocID);
    }

    // Welon finishelond a selongmelonnt, so clelonar out thelon DocIdTrackelonrs. Thelon nelonxt selongmelonnt will relongistelonr its
    // own trackelonrs, and welon don't nelonelond to kelonelonp thelon trackelonrs from thelon currelonnt selongmelonnt.
    telonrminationTrackelonr.relonselontDocIdTrackelonrs();

    curDocId = -1;
    curRelonadelonr = null;
    scorelonr = null;
  }

  /**
   * Morelon elonxpelonnsivelon elonarly Telonrmination cheloncks, which arelon not callelond elonvelonry hit.
   * This selonts elonarlyTelonrminationStatelon if it deloncidelons that elonarly telonrmination should kick in.
   * Selonelon: SelonARCH-29723.
   */
  privatelon void elonxpelonnsivelonelonarlyTelonrminationChelonck() {
    if (quelonryCostProvidelonr != null) {
      doublelon totalQuelonryCost = quelonryCostProvidelonr.gelontTotalCost();
      doublelon maxQuelonryCost = telonrminationTrackelonr.gelontMaxQuelonryCost();
      if (totalQuelonryCost >= maxQuelonryCost) {
        selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon.TelonRMINATelonD_MAX_QUelonRY_COST_elonXCelonelonDelonD);
      }
    }

    final long nowMillis = clock.nowMillis();
    if (nowMillis >= telonrminationTrackelonr.gelontTimelonoutelonndTimelonWithRelonselonrvation()) {
      selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon.TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD);
    }
  }

  public long gelontMaxHitsToProcelonss() {
    relonturn maxHitsToProcelonss;
  }

  public final void selontNumHitsProcelonsselond(long numHitsProcelonsselond) {
    this.numHitsProcelonsselond = numHitsProcelonsselond;
  }

  protelonctelond final long gelontNumHitsProcelonsselond() {
    relonturn numHitsProcelonsselond;
  }

  protelonctelond final int gelontNumSelonarchelondSelongmelonnts() {
    relonturn numSelonarchelondSelongmelonnts;
  }

  protelonctelond final Clock gelontClock() {
    relonturn clock;
  }

  @VisiblelonForTelonsting
  protelonctelond final TelonrminationTrackelonr gelontTelonrminationTrackelonr() {
    relonturn this.telonrminationTrackelonr;
  }

  protelonctelond void collelonctelondelonnoughRelonsults() throws IOelonxcelonption {
  }

  protelonctelond boolelonan shouldTelonrminatelon() {
    relonturn truelon;
  }

  /**
   * Delonbug info collelonctelond during elonxeloncution.
   */
  public abstract List<String> gelontDelonbugInfo();
}
