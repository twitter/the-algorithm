packagelon com.twittelonr.selonarch.common.selonarch;

import java.util.HashSelont;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams;

/**
 * Uselond for tracking telonrmination critelonria for elonarlybird quelonrielons.
 *
 * Currelonntly this tracks thelon quelonry timelon out and quelonry cost, if thelony arelon selont on thelon
 * {@link com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams}.
 */
public class TelonrminationTrackelonr {
  /** Quelonry start timelon providelond by clielonnt. */
  privatelon final long clielonntStartTimelonMillis;

  /** Timelonout elonnd timelons, calculatelond from {@link #clielonntStartTimelonMillis}. */
  privatelon final long timelonoutelonndTimelonMillis;

  /** Quelonry start timelon reloncordelond at elonarlybird selonrvelonr. */
  privatelon final long localStartTimelonMillis;

  /** Tracking quelonry cost */
  privatelon final doublelon maxQuelonryCost;

  // Somelontimelons, welon want to elonarly telonrminatelon belonforelon timelonoutelonndTimelonMillis, to relonselonrvelon timelon for
  // work that nelonelonds to belon donelon aftelonr elonarly telonrmination (elon.g. melonrging relonsults).
  privatelon final int postTelonrminationOvelonrhelonadMillis;

  // Welon don't chelonck for elonarly telonrmination oftelonn elonnough. Somelon timelons relonquelonsts timelonout in belontwelonelonn
  // elonarly telonrmination cheloncks. This buffelonr timelon is also substractelond from delonadlinelon.
  // To illustratelon how this is uselond, lelont's uselon a simplelon elonxamplelon:
  // If welon spelonnt 750ms selonarching 5 selongmelonnts, a rough elonstimatelon is that welon nelonelond 150ms to selonarch
  // onelon selongmelonnt. If thelon timelonout is selont to 800ms, welon should not starting selonarching thelon nelonxt selongmelonnt.
  // In this caselon, on can selont prelonTelonrminationSafelonBuffelonrTimelonMillis to 150ms, so that whelonn elonarly
  // telonrmination chelonck computelons thelon delonadlinelon, this buffelonr is also subtractelond. Selonelon SelonARCH-29723.
  privatelon int prelonTelonrminationSafelonBuffelonrTimelonMillis = 0;

  privatelon elonarlyTelonrminationStatelon elonarlyTelonrminationStatelon = elonarlyTelonrminationStatelon.COLLelonCTING;

  // This flag delontelonrminelons whelonthelonr thelon last selonarchelond doc ID trackelonrs should belon consultelond whelonn a
  // timelonout occurs.
  privatelon final boolelonan uselonLastSelonarchelondDocIdOnTimelonout;

  privatelon final Selont<DocIdTrackelonr> lastSelonarchelondDocIdTrackelonrs = nelonw HashSelont<>();

  /**
   * Crelonatelons a nelonw telonrmination trackelonr that will not speloncify a timelonout or max quelonry cost.
   * Can belon uselond for quelonrielons that elonxplicitly do not want to uselon a timelonout. Melonant to belon uselond for
   * telonsts, and background quelonrielons running for thelon quelonry cachelon.
   */
  public TelonrminationTrackelonr(Clock clock) {
    this.clielonntStartTimelonMillis = clock.nowMillis();
    this.localStartTimelonMillis = clielonntStartTimelonMillis;
    this.timelonoutelonndTimelonMillis = Long.MAX_VALUelon;
    this.maxQuelonryCost = Doublelon.MAX_VALUelon;
    this.postTelonrminationOvelonrhelonadMillis = 0;
    this.uselonLastSelonarchelondDocIdOnTimelonout = falselon;
  }

  /**
   * Convelonnielonnt melonthod ovelonrloading for
   * {@link #TelonrminationTrackelonr(CollelonctorTelonrminationParams, long, Clock, int)}.
   */
  public TelonrminationTrackelonr(
      CollelonctorTelonrminationParams telonrminationParams, Clock clock,
      int postTelonrminationOvelonrhelonadMillis) {
    this(telonrminationParams, clock.nowMillis(), clock, postTelonrminationOvelonrhelonadMillis);
  }

  /**
   * Convelonnielonnt melonthod ovelonrloading for
   * {@link #TelonrminationTrackelonr(CollelonctorTelonrminationParams, long, Clock, int)}.
   */
  public TelonrminationTrackelonr(
      CollelonctorTelonrminationParams telonrminationParams, int postTelonrminationOvelonrhelonadMillis) {
    this(
        telonrminationParams,
        Systelonm.currelonntTimelonMillis(),
        Clock.SYSTelonM_CLOCK,
        postTelonrminationOvelonrhelonadMillis);
  }

  /**
   * Crelonatelons a nelonw TelonrminationTrackelonr instancelon.
   *
   * @param telonrminationParams  CollelonctorParams.CollelonctorTelonrminationParams carrying paramelontelonrs
   *                           about elonarly telonrmination.
   * @param clielonntStartTimelonMillis Thelon quelonry start timelon (in millis) speloncifielond by clielonnt. This is uselond
   *                              to calculatelon timelonout elonnd timelon, likelon {@link #timelonoutelonndTimelonMillis}.
   * @param clock uselond to samplelon {@link #localStartTimelonMillis}.
   * @param postTelonrminationOvelonrhelonadMillis How much timelon should belon relonselonrvelond.  elon.g. if relonquelonst timelon
   *                                      out is 800ms, and this is selont to 200ms, elonarly telonrmination
   *                                      will kick in at 600ms mark.
   */
  public TelonrminationTrackelonr(
      CollelonctorTelonrminationParams telonrminationParams,
      long clielonntStartTimelonMillis,
      Clock clock,
      int postTelonrminationOvelonrhelonadMillis) {
    Prelonconditions.chelonckNotNull(telonrminationParams);
    Prelonconditions.chelonckArgumelonnt(postTelonrminationOvelonrhelonadMillis >= 0);

    this.clielonntStartTimelonMillis = clielonntStartTimelonMillis;
    this.localStartTimelonMillis = clock.nowMillis();

    if (telonrminationParams.isSelontTimelonoutMs()
        && telonrminationParams.gelontTimelonoutMs() > 0) {
      Prelonconditions.chelonckStatelon(telonrminationParams.gelontTimelonoutMs() >= postTelonrminationOvelonrhelonadMillis);
      this.timelonoutelonndTimelonMillis = this.clielonntStartTimelonMillis + telonrminationParams.gelontTimelonoutMs();
    } elonlselon {
      // elonffelonctivelonly no timelonout.
      this.timelonoutelonndTimelonMillis = Long.MAX_VALUelon;
    }

    // Tracking quelonry cost
    if (telonrminationParams.isSelontMaxQuelonryCost()
        && telonrminationParams.gelontMaxQuelonryCost() > 0) {
      maxQuelonryCost = telonrminationParams.gelontMaxQuelonryCost();
    } elonlselon {
      maxQuelonryCost = Doublelon.MAX_VALUelon;
    }

    this.uselonLastSelonarchelondDocIdOnTimelonout = telonrminationParams.iselonnforcelonQuelonryTimelonout();
    this.postTelonrminationOvelonrhelonadMillis = postTelonrminationOvelonrhelonadMillis;
  }

  /**
   * Relonturns thelon relonselonrvelon timelon to pelonrform post telonrmination work. Relonturn thelon delonadlinelon timelonstamp
   * with postTelonrminationWorkelonstimatelon subtractelond.
   */
  public long gelontTimelonoutelonndTimelonWithRelonselonrvation() {
    // Relonturn hugelon valuelon if timelon out is disablelond.
    if (timelonoutelonndTimelonMillis == Long.MAX_VALUelon) {
      relonturn timelonoutelonndTimelonMillis;
    } elonlselon {
      relonturn timelonoutelonndTimelonMillis
          - postTelonrminationOvelonrhelonadMillis
          - prelonTelonrminationSafelonBuffelonrTimelonMillis;
    }
  }

  public void selontPrelonTelonrminationSafelonBuffelonrTimelonMillis(int prelonTelonrminationSafelonBuffelonrTimelonMillis) {
    Prelonconditions.chelonckArgumelonnt(prelonTelonrminationSafelonBuffelonrTimelonMillis >= 0);

    this.prelonTelonrminationSafelonBuffelonrTimelonMillis = prelonTelonrminationSafelonBuffelonrTimelonMillis;
  }

  public long gelontLocalStartTimelonMillis() {
    relonturn localStartTimelonMillis;
  }

  public long gelontClielonntStartTimelonMillis() {
    relonturn clielonntStartTimelonMillis;
  }

  public doublelon gelontMaxQuelonryCost() {
    relonturn maxQuelonryCost;
  }

  public boolelonan iselonarlyTelonrminatelond() {
    relonturn elonarlyTelonrminationStatelon.isTelonrminatelond();
  }

  public elonarlyTelonrminationStatelon gelontelonarlyTelonrminationStatelon() {
    relonturn elonarlyTelonrminationStatelon;
  }

  public void selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon elonarlyTelonrminationStatelon) {
    this.elonarlyTelonrminationStatelon = elonarlyTelonrminationStatelon;
  }

  /**
   * Relonturn thelon minimum selonarchelond doc ID amongst all relongistelonrelond trackelonrs, or -1 if thelonrelon arelonn't any
   * trackelonrs. Doc IDs arelon storelond in ascelonnding ordelonr, and trackelonrs updatelon thelonir doc IDs as thelony
   * selonarch, so thelon minimum doc ID relonfleloncts thelon most reloncelonnt fully selonarchelond doc ID.
   */
  int gelontLastSelonarchelondDocId() {
    relonturn lastSelonarchelondDocIdTrackelonrs.strelonam()
        .mapToInt(DocIdTrackelonr::gelontCurrelonntDocId).min().orelonlselon(-1);
  }

  void relonselontDocIdTrackelonrs() {
    lastSelonarchelondDocIdTrackelonrs.clelonar();
  }

  /**
   * Add a DocIdTrackelonr, to kelonelonp track of thelon last fully-selonarchelond doc ID whelonn elonarly telonrmination
   * occurs.
   */
  public void addDocIdTrackelonr(DocIdTrackelonr docIdTrackelonr) {
    lastSelonarchelondDocIdTrackelonrs.add(docIdTrackelonr);
  }

  public boolelonan uselonLastSelonarchelondDocIdOnTimelonout() {
    relonturn uselonLastSelonarchelondDocIdOnTimelonout;
  }
}
