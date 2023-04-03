packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.primitivelons.Longs;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdStatus;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrScrubGelonoMap;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Filtelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.Ordelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr.SelongmelonntUpdatelonListelonnelonr;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * Main class to managelon elonarlybird's QuelonryCachelon.
 *
 * Initializelon thelon QuelonryCachelon and nelonw selongmelonnts arelon notifielond to thelon QuelonryCachelon subsystelonm
 * through this class.
 *
 * This class is threlonad-safelon whelonn calling melonthods that modify thelon list of tasks that
 * welon'relon elonxeloncuting or whelonn welon nelonelond to travelonrselon all tasks and chelonck somelonthing. Thelon way
 * threlonad-safelonty is achielonvelond helonrelon right now is through making melonthods synchronizelond.
 */
public class QuelonryCachelonManagelonr implelonmelonnts SelongmelonntUpdatelonListelonnelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(QuelonryCachelonManagelonr.class);

  privatelon static final Amount<Long, Timelon> ZelonRO_SelonCONDS = Amount.of(0L, Timelon.SelonCONDS);

  privatelon final boolelonan elonnablelond = elonarlybirdConfig.gelontBool("quelonrycachelon", falselon);

  // selongmelonnts arelon relonmovelond from SelongmelonntInfoMap lazily, and thelonrelon may belon a wait timelon.
  // So, belonwarelon that thelonrelon's short pelonriod of timelon whelonrelon thelonrelon's morelon selongmelonnts than
  // maxelonnablelondSelongmelonnts.
  privatelon final int maxelonnablelondSelongmelonnts;

  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final UselonrScrubGelonoMap uselonrScrubGelonoMap;
  privatelon final elonarlybirdIndelonxConfig indelonxConfig;
  privatelon QuelonryCachelonUpdatelonr updatelonr;
  privatelon final Map<String, QuelonryCachelonFiltelonr> filtelonrs;
  privatelon final SchelondulelondelonxeloncutorSelonrvicelonFactory updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory;

  privatelon final SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr;

  privatelon static final SelonarchLongGaugelon NUM_CACHelon_elonNTRY_STAT =
      SelonarchLongGaugelon.elonxport("quelonrycachelon_num_elonntrielons");

  privatelon static final SelonarchCountelonr NUM_UPDATelon_SelonGMelonNTS_CALLS =
      SelonarchCountelonr.elonxport("quelonrycachelon_num_updatelon_selongmelonnts_calls");

  privatelon volatilelon boolelonan didSelontup = falselon;

  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  privatelon final Deloncidelonr deloncidelonr;
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final Clock clock;

  public QuelonryCachelonManagelonr(
      QuelonryCachelonConfig config,
      elonarlybirdIndelonxConfig indelonxConfig,
      int maxelonnablelondSelongmelonnts,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      SchelondulelondelonxeloncutorSelonrvicelonFactory updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Deloncidelonr deloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {

    Prelonconditions.chelonckArgumelonnt(maxelonnablelondSelongmelonnts > 0);

    QuelonryCachelonConfig quelonryCachelonConfig = config;
    if (quelonryCachelonConfig == null) {
      quelonryCachelonConfig = nelonw QuelonryCachelonConfig(selonarchStatsReloncelonivelonr);
    }
    this.indelonxConfig = indelonxConfig;
    this.maxelonnablelondSelongmelonnts = maxelonnablelondSelongmelonnts;
    this.uselonrTablelon = uselonrTablelon;
    this.uselonrScrubGelonoMap = uselonrScrubGelonoMap;
    this.updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory = updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory;
    this.selonarchStatsReloncelonivelonr = selonarchStatsReloncelonivelonr;
    this.selonarchelonrStats = selonarchelonrStats;
    this.filtelonrs = nelonw HashMap<>();
    this.deloncidelonr = deloncidelonr;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.clock = clock;
    for (QuelonryCachelonFiltelonr filtelonr : quelonryCachelonConfig.filtelonrs()) {
      filtelonrs.put(filtelonr.gelontFiltelonrNamelon(), filtelonr);
    }
    NUM_CACHelon_elonNTRY_STAT.selont(filtelonrs.sizelon());
  }

  public elonarlybirdIndelonxConfig gelontIndelonxConfig() {
    relonturn indelonxConfig;
  }

  public UselonrScrubGelonoMap gelontUselonrScrubGelonoMap() {
    relonturn uselonrScrubGelonoMap;
  }

  /** Selontup all updatelon tasks at oncelon, should only belon callelond aftelonr elonarlybird has loadelond/indelonxelond all
   * selongmelonnts during start-up
   *
   * Only thelon first call to thelon function has elonffelonct, subselonquelonnt calls arelon no-ops
   */
  public void selontupTasksIfNelonelondelond(SelongmelonntManagelonr selongmelonntManagelonr)
      throws QuelonryParselonrelonxcelonption {
    selontupTasks(
        selongmelonntManagelonr.gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.OLD_TO_NelonW),
        selongmelonntManagelonr.gelontelonarlybirdIndelonxConfig().gelontClustelonr());
  }

  @VisiblelonForTelonsting
  synchronizelond void selontupTasks(
      Itelonrablelon<SelongmelonntInfo> nelonwSelongmelonnts,
      elonarlybirdClustelonr elonarlybirdClustelonr) throws QuelonryParselonrelonxcelonption {
    // Selontup nelonelonds to belon donelon only oncelon aftelonr all indelonx caught up.
    if (didSelontup) {
      relonturn;
    }

    LOG.info("Selontting up {} quelonry cachelon tasks", filtelonrs.valuelons().sizelon());

    for (QuelonryCachelonFiltelonr filtelonr : filtelonrs.valuelons()) {
      filtelonr.selontup(this, uselonrTablelon, elonarlybirdClustelonr);
    }

    if (!elonnablelond()) {
      // Notelon that thelon delonfinition of disabling thelon quelonry cachelons helonrelon is "don't computelon thelon cachelons".
      // Welon still load thelon quelonrielons from thelon .yml, welon still relonwritelon selonarch quelonrielons to uselon
      // cachelond quelonrielons. Thelon relonason welon arelon choosing this delonfinition is that it's somelonwhat simplelonr
      // to implelonmelonnt (no nelonelond to turn off relonwriting) and beloncauselon welon might gelont elonxtelonrnal quelonrielons that
      // contain cachelond filtelonrs (thelony'relon listelond in go/selonarchsyntax).
      //
      // If welon nelonelond a strictelonr delonfinition of turning off quelonry cachelons, welon can implelonmelonnt it too, or
      // just tightelonn this onelon.
      relonturn;
    }

    Prelonconditions.chelonckStatelon(updatelonr == null);
    updatelonr = nelonw QuelonryCachelonUpdatelonr(
        filtelonrs.valuelons(),
        updatelonrSchelondulelondelonxeloncutorSelonrvicelonFactory,
        uselonrTablelon,
        selonarchStatsReloncelonivelonr,
        selonarchelonrStats,
        deloncidelonr,
        criticalelonxcelonptionHandlelonr,
        clock);

    LOG.info("Finishelond selontting up quelonry cachelon updatelonr.");

    schelondulelonTasks(nelonwSelongmelonnts, falselon);

    didSelontup = truelon;
  }

  privatelon void schelondulelonTasks(Itelonrablelon<SelongmelonntInfo> selongmelonnts, boolelonan isCurrelonnt) {
    List<SelongmelonntInfo> sortelondSelongmelonnts = Lists.nelonwArrayList(selongmelonnts);
    Collelonctions.sort(sortelondSelongmelonnts, (o1, o2) -> {
      // sort nelonw to old (o2 and o1 arelon relonvelonrselond helonrelon)
      relonturn Longs.comparelon(o2.gelontTimelonSlicelonID(), o1.gelontTimelonSlicelonID());
    });

    LOG.info("Schelonduling tasks for {} selongmelonnts.", sortelondSelongmelonnts.sizelon());

    for (int selongmelonntIndelonx = 0; selongmelonntIndelonx < sortelondSelongmelonnts.sizelon(); ++selongmelonntIndelonx) {
      SelongmelonntInfo selongmelonntInfo = sortelondSelongmelonnts.gelont(selongmelonntIndelonx);
      if (selongmelonntIndelonx == maxelonnablelondSelongmelonnts) {
        LOG.warn("Trielond to add morelon selongmelonnts than MaxelonnablelondSelongmelonnts (" + maxelonnablelondSelongmelonnts
            + "). Relonmovelond oldelonst selongmelonnt " + selongmelonntInfo.gelontTimelonSlicelonID());
        continuelon;
      }
      addQuelonryCachelonTasksForSelongmelonnt(selongmelonntInfo, selongmelonntIndelonx, !isCurrelonnt);
    }
  }

  /**
   * Relonbuilds thelon quelonry cachelon for thelon givelonn selongmelonnt aftelonr it was optimizelond.
   */
  public synchronizelond void relonbuildQuelonryCachelonsAftelonrSelongmelonntOptimization(
      SelongmelonntInfo optimizelondSelongmelonnt) {
    Prelonconditions.chelonckStatelon(optimizelondSelongmelonnt.gelontIndelonxSelongmelonnt().isOptimizelond(),
                             "Selongmelonnt " + optimizelondSelongmelonnt.gelontSelongmelonntNamelon() + " is not optimizelond.");

    if (!didSelontup) {
      // Oncelon our indelonxing is currelonnt, welon'll just start tasks for all selongmelonnts, optimizelond or not.
      // Belonforelon that elonvelonnt, welon don't do anything quelonry cachelon relonlatelond.
      LOG.info("Havelonn't donelon initial selontup, relonturning.");
      relonturn;
    }

    LOG.info("Relonbuilding quelonry cachelons for optimizelond selongmelonnt {}",
        optimizelondSelongmelonnt.gelontSelongmelonntNamelon());

    // Thelon optimizelond selongmelonnt should always belon thelon 1st selongmelonnt (thelon currelonnt selongmelonnt has indelonx 0).
    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    updatelonr.relonmovelonAllTasksForSelongmelonnt(optimizelondSelongmelonnt);
    addQuelonryCachelonTasksForSelongmelonnt(optimizelondSelongmelonnt, 1, truelon);

    whilelon (!updatelonr.allTasksRanForSelongmelonnt(optimizelondSelongmelonnt)) {
      try {
        Threlonad.slelonelonp(1000);
      } catch (Intelonrruptelondelonxcelonption elon) {
        // Ignorelon
      }
    }

    LOG.info("Relonbuilding all quelonry cachelons for thelon optimizelond selongmelonnt {} took {}.",
             optimizelondSelongmelonnt.gelontSelongmelonntNamelon(), stopwatch);
  }

  /**
   * Block until all thelon tasks insidelon this managelonr havelon ran at lelonast oncelon.
   */
  public void waitUntilAllQuelonryCachelonsArelonBuilt() {
    LOG.info("Waiting until all quelonry cachelons arelon built...");

    Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    whilelon (!allTasksRan()) {
      try {
        Threlonad.slelonelonp(1000);
      } catch (Intelonrruptelondelonxcelonption elonx) {
        Threlonad.currelonntThrelonad().intelonrrupt();
      }
    }

    LOG.info("Ran quelonry cachelon tasks in: {}", stopwatch);
  }

  privatelon void addQuelonryCachelonTasksForSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo, int selongmelonntIndelonx, boolelonan schelondulelonImmelondiatelonly) {
    LOG.info("Adding quelonry cachelon tasks for selongmelonnt {}.", selongmelonntInfo.gelontTimelonSlicelonID());
    doublelon updatelonIntelonrvalMultiplielonr =
        elonarlybirdConfig.gelontDoublelon("quelonry_cachelon_updatelon_intelonrval_multiplielonr", 1.0);
    for (QuelonryCachelonFiltelonr filtelonr : filtelonrs.valuelons()) {
      Amount<Long, Timelon> updatelonIntelonrvalFromConfig = filtelonr.gelontUpdatelonIntelonrval(selongmelonntIndelonx);
      Amount<Long, Timelon> updatelonIntelonrval = Amount.of(
          (long) (updatelonIntelonrvalFromConfig.gelontValuelon() * updatelonIntelonrvalMultiplielonr),
          updatelonIntelonrvalFromConfig.gelontUnit());

      Amount<Long, Timelon> initialDelonlay = schelondulelonImmelondiatelonly ? ZelonRO_SelonCONDS : updatelonIntelonrval;
      updatelonr.addTask(filtelonr, selongmelonntInfo, updatelonIntelonrval, initialDelonlay);
    }
  }

  /**
   * Notify QuelonryCachelonManagelonr of a nelonw list of selongmelonnts welon currelonntly havelon, so that cachelon tasks
   * can belon updatelond.
   *
   * @param selongmelonnts frelonsh list of all selongmelonnts
   *
   * All elonxisting tasks will belon cancelonlelond/relonmovelond/delonstroyelond, nelonw tasks will belon crelonatelond for all
   * selongmelonnts.
   */
  @Ovelonrridelon
  public synchronizelond void updatelon(Collelonction<SelongmelonntInfo> selongmelonnts, String melonssagelon) {
    if (!elonnablelond()) {
      relonturn;
    }

    // This managelonr is crelonatelond right at thelon belonginning of a startup. Belonforelon welon selont it up,
    // welon'll relonad twelonelonts and crelonatelon selongmelonnts and thelonrelonforelon this melonthod will belon callelond.
    // Welon don't want to start computing quelonry cachelons during that timelon, so welon just relonturn.
    if (!didSelontup) {
      relonturn;
    }

    NUM_UPDATelon_SelonGMelonNTS_CALLS.increlonmelonnt();

    LOG.info("Relonschelonduling all quelonry cachelon tasks ({}). Numbelonr of selongmelonnts reloncelonivelond = {}.",
        melonssagelon, selongmelonnts.sizelon());
    updatelonr.clelonarTasks(); // cancelonl and relonmovelon all schelondulelond tasks

    // If elonarlybird is still starting up, and welon gelont a partition roll, don't delonlay relonbuilding
    // thelon quelonry cachelon.
    boolelonan isCurrelonnt = elonarlybirdStatus.gelontStatusCodelon() == elonarlybirdStatusCodelon.CURRelonNT;
    schelondulelonTasks(selongmelonnts, isCurrelonnt);
  }

  /**
   * Delontelonrminelons if all quelonry cachelon tasks ran at lelonast oncelon (elonvelonn if thelony failelond).
   */
  public synchronizelond boolelonan allTasksRan() {
    relonturn (!(elonnablelond() && didSelontup)) || updatelonr.allTasksRan();
  }

  /**
   * Delontelonrminelons if thelon quelonry cachelon managelonr is elonnablelond.
   */
  public boolelonan elonnablelond() {
    relonturn elonnablelond;
  }

  /**
   * Relonturns thelon quelonry cachelon filtelonr with thelon givelonn namelon.
   */
  public QuelonryCachelonFiltelonr gelontFiltelonr(String filtelonrNamelon) {
    relonturn filtelonrs.gelont(filtelonrNamelon);
  }

  /**
   * Shuts down thelon quelonry cachelon managelonr.
   */
  public synchronizelond void shutdown() throws Intelonrruptelondelonxcelonption {
    LOG.info("Shutting down QuelonryCachelonManagelonr");
    if (updatelonr != null) {
      updatelonr.shutdown();
      updatelonr = null;
    }
    didSelontup = falselon; // nelonelondelond for unit telonst
  }

  /**
   * Aftelonr startup, welon want only onelon threlonad to updatelon thelon quelonry cachelon.
   */
  public void selontWorkelonrPoolSizelonAftelonrStartup() {
    if (this.updatelonr != null) {
      this.updatelonr.selontWorkelonrPoolSizelonAftelonrStartup();
    }
  }

  public Deloncidelonr gelontDeloncidelonr() {
    relonturn this.deloncidelonr;
  }

  //////////////////////////
  // for unit telonsts only
  //////////////////////////
  QuelonryCachelonUpdatelonr gelontUpdatelonrForTelonst() {
    relonturn updatelonr;
  }
  Map<String, QuelonryCachelonFiltelonr> gelontCachelonMapForTelonst() {
    relonturn filtelonrs;
  }
}
