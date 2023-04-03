packagelon com.twittelonr.selonarch.elonarlybird.factory;

import java.io.IOelonxcelonption;
import java.lang.managelonmelonnt.ManagelonmelonntFactory;
import java.util.Optional;
import java.util.concurrelonnt.SchelondulelondThrelonadPoolelonxeloncutor;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.sun.managelonmelonnt.OpelonratingSystelonmMXBelonan;

import org.apachelon.direlonctory.api.util.Strings;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;
import org.apachelon.kafka.common.TopicPartition;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.finaglelon.stats.MelontricsStatsReloncelonivelonr;
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr;
import com.twittelonr.selonarch.common.aurora.AuroraSchelondulelonrClielonnt;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.filelon.FilelonUtils;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonrImpl;
import com.twittelonr.selonarch.common.partitioning.zookelonelonpelonr.SelonarchZkClielonnt;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonoutFactory;
import com.twittelonr.selonarch.common.util.io.kafka.FinaglelonKafkaClielonntUtils;
import com.twittelonr.selonarch.common.util.io.kafka.ThriftDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.ml.telonnsorflow_elonnginelon.TelonnsorflowModelonlsManagelonr;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.common.util.zookelonelonpelonr.ZooKelonelonpelonrProxy;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdCPUQualityFactor;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdDarkProxy;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdFinaglelonSelonrvelonrManagelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdFuturelonPoolManagelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdProductionFinaglelonSelonrvelonrManagelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdSelonrvelonrSelontManagelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdWarmUpManagelonr;
import com.twittelonr.selonarch.elonarlybird.QualityFactor;
import com.twittelonr.selonarch.elonarlybird.SelonrvelonrSelontMelonmbelonr;
import com.twittelonr.selonarch.elonarlybird.UpdatelonablelonelonarlybirdStatelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonelonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonSelonarchPartitionManagelonr;
import com.twittelonr.selonarch.elonarlybird.common.CaughtUpMonitor;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrScrubGelonoMap;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrUpdatelonsChelonckelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.MissingKafkaTopicelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonntFactory;
import com.twittelonr.selonarch.elonarlybird.ml.ScoringModelonlsManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.AudioSpacelonelonvelonntsStrelonamIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.partition.AudioSpacelonTablelon;
import com.twittelonr.selonarch.elonarlybird.partition.DynamicPartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.elonarlybirdIndelonxFlushelonr;
import com.twittelonr.selonarch.elonarlybird.partition.elonarlybirdIndelonxLoadelonr;
import com.twittelonr.selonarch.elonarlybird.partition.elonarlybirdKafkaConsumelonr;
import com.twittelonr.selonarch.elonarlybird.partition.elonarlybirdStartup;
import com.twittelonr.selonarch.elonarlybird.partition.OptimizationAndFlushingCoordinationLock;
import com.twittelonr.selonarch.elonarlybird.partition.TimelonLimitelondHadoopelonxistsCall;
import com.twittelonr.selonarch.elonarlybird.partition.UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup.FrelonshStartupHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.HdfsUtil;
import com.twittelonr.selonarch.elonarlybird.partition.KafkaStartup;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionManagelonrStartup;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionWritelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;
import com.twittelonr.selonarch.elonarlybird.partition.StartupUselonrelonvelonntIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.partition.TwelonelontCrelonatelonHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.TwelonelontUpdatelonHandlelonr;
import com.twittelonr.selonarch.elonarlybird.partition.UselonrUpdatelonsStrelonamIndelonxelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonConfig;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdAction;
import com.twittelonr.selonarch.elonarlybird.util.elonarlybirdDeloncidelonr;
import com.twittelonr.selonarch.elonarlybird.util.TelonrmCountMonitor;
import com.twittelonr.selonarch.elonarlybird.util.TwelonelontCountMonitor;
import com.twittelonr.ubs.thriftjava.AudioSpacelonBaselonelonvelonnt;

/**
 * Production modulelon that providelons elonarlybird componelonnts.
 */
public class elonarlybirdWirelonModulelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdWirelonModulelon.class);
  privatelon static final int MAX_POLL_RelonCORDS = 1000;

  /**
   * How many threlonads welon will uselon for building up thelon quelonry cachelon during startup.
   * Thelon numbelonr of threlonads will belon selont to 1 aftelonr this elonarlybird is currelonnt.
   */
  privatelon static final int QUelonRY_CACHelon_NUM_WORKelonR_THRelonADS_AT_STARTUP =
      elonarlybirdConfig.gelontInt("quelonry_cachelon_updatelonr_startup_threlonads", 1);

  /**
   * Schelondulelond elonxeloncutor selonrvicelon factory can belon relon-uselond in production.
   * All thelon managelonrs can sharelon thelon samelon elonxeloncutor selonrvicelon factory.
   */
  privatelon final SchelondulelondelonxeloncutorSelonrvicelonFactory sharelondelonxeloncutorSelonrvicelonFactory =
      nelonw SchelondulelondelonxeloncutorSelonrvicelonFactory();

  privatelon final SelonarchStatsReloncelonivelonr sharelondSelonarchStatsReloncelonivelonr = nelonw SelonarchStatsReloncelonivelonrImpl();
  privatelon final StatsReloncelonivelonr sharelondFinaglelonStatsReloncelonivelonr = nelonw MelontricsStatsReloncelonivelonr();

  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont =
      nelonw SelonarchIndelonxingMelontricSelont(sharelondSelonarchStatsReloncelonivelonr);

  privatelon final elonarlybirdSelonarchelonrStats twelonelontsSelonarchelonrStats =
      nelonw elonarlybirdSelonarchelonrStats(sharelondSelonarchStatsReloncelonivelonr);

  privatelon final CaughtUpMonitor indelonxCaughtUpMonitor = nelonw CaughtUpMonitor("dl_indelonx");

  public CaughtUpMonitor providelonIndelonxCaughtUpMonitor() {
    relonturn indelonxCaughtUpMonitor;
  }

  privatelon final CaughtUpMonitor kafkaIndelonxCaughtUpMonitor = nelonw CaughtUpMonitor("kafka_indelonx");

  public CaughtUpMonitor providelonKafkaIndelonxCaughtUpMonitor() {
    relonturn kafkaIndelonxCaughtUpMonitor;
  }

  privatelon final OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock =
      nelonw OptimizationAndFlushingCoordinationLock();

  public OptimizationAndFlushingCoordinationLock providelonOptimizationAndFlushingCoordinationLock() {
    relonturn optimizationAndFlushingCoordinationLock;
  }

  public QuelonryTimelonoutFactory providelonQuelonryTimelonoutFactory() {
    relonturn nelonw QuelonryTimelonoutFactory();
  }

  public static class ZooKelonelonpelonrClielonnts {
    public ZooKelonelonpelonrProxy discovelonryClielonnt;
    public ZooKelonelonpelonrProxy statelonClielonnt;

    public ZooKelonelonpelonrClielonnts() {
      this(
          SelonarchZkClielonnt.gelontSelonrvicelonDiscovelonryZooKelonelonpelonrClielonnt(),
          SelonarchZkClielonnt.gelontSZooKelonelonpelonrClielonnt());
    }

    public ZooKelonelonpelonrClielonnts(ZooKelonelonpelonrProxy discovelonryClielonnt, ZooKelonelonpelonrProxy statelonClielonnt) {
      this.discovelonryClielonnt = discovelonryClielonnt;
      this.statelonClielonnt = statelonClielonnt;
    }
  }

  /**
   * Providelons thelon elonarlybird deloncidelonr.
   */
  public Deloncidelonr providelonDeloncidelonr() {
    relonturn elonarlybirdDeloncidelonr.initializelon();
  }

  /**
   * Providelons thelon selont of ZooKelonelonpelonr clielonnts to belon uselond by elonarlybird.
   */
  public ZooKelonelonpelonrClielonnts providelonZooKelonelonpelonrClielonnts() {
    relonturn nelonw ZooKelonelonpelonrClielonnts();
  }

  /**
   * Providelons thelon quelonry cachelon config.
   */
  public QuelonryCachelonConfig providelonQuelonryCachelonConfig(SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr) {
    relonturn nelonw QuelonryCachelonConfig(selonarchStatsReloncelonivelonr);
  }

  /**
   * Providelons thelon elonarlybird indelonx config.
   */
  public elonarlybirdIndelonxConfig providelonelonarlybirdIndelonxConfig(
      Deloncidelonr deloncidelonr, SelonarchIndelonxingMelontricSelont indelonxingMelontricSelont,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    relonturn elonarlybirdIndelonxConfigUtil.crelonatelonelonarlybirdIndelonxConfig(deloncidelonr, indelonxingMelontricSelont,
        criticalelonxcelonptionHandlelonr);
  }

  public DynamicPartitionConfig providelonDynamicPartitionConfig() {
    relonturn nelonw DynamicPartitionConfig(PartitionConfigUtil.initPartitionConfig());
  }

  /**
   * Providelons thelon selongmelonnt managelonr to belon uselond by this elonarlybird.
   */
  public SelongmelonntManagelonr providelonSelongmelonntManagelonr(
      DynamicPartitionConfig dynamicPartitionConfig,
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SelonarchIndelonxingMelontricSelont partitionIndelonxingMelontricSelont,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      SelonarchStatsReloncelonivelonr elonarlybirdSelonrvelonrStats,
      UselonrUpdatelonsChelonckelonr uselonrUpdatelonsChelonckelonr,
      SelongmelonntSyncConfig selongmelonntSyncConfig,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      Clock clock,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    relonturn nelonw SelongmelonntManagelonr(
        dynamicPartitionConfig,
        elonarlybirdIndelonxConfig,
        partitionIndelonxingMelontricSelont,
        selonarchelonrStats,
        elonarlybirdSelonrvelonrStats,
        uselonrUpdatelonsChelonckelonr,
        selongmelonntSyncConfig,
        uselonrTablelon,
        uselonrScrubGelonoMap,
        clock,
        elonarlybirdConfig.gelontMaxSelongmelonntSizelon(),
        criticalelonxcelonptionHandlelonr,
        providelonKafkaIndelonxCaughtUpMonitor());
  }

  public QuelonryCachelonManagelonr providelonQuelonryCachelonManagelonr(
      QuelonryCachelonConfig config,
      elonarlybirdIndelonxConfig indelonxConfig,
      int maxelonnablelondSelongmelonnts,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      SchelondulelondelonxeloncutorSelonrvicelonFactory quelonryCachelonUpdatelonrSchelondulelondelonxeloncutorFactory,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Deloncidelonr deloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {
    relonturn nelonw QuelonryCachelonManagelonr(config, indelonxConfig, maxelonnablelondSelongmelonnts, uselonrTablelon,
        uselonrScrubGelonoMap, quelonryCachelonUpdatelonrSchelondulelondelonxeloncutorFactory, selonarchStatsReloncelonivelonr,
        selonarchelonrStats, deloncidelonr, criticalelonxcelonptionHandlelonr, clock);
  }

  public TelonrmCountMonitor providelonTelonrmCountMonitor(
      SelongmelonntManagelonr selongmelonntManagelonr, SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    relonturn nelonw TelonrmCountMonitor(selongmelonntManagelonr, elonxeloncutorSelonrvicelonFactory, 500, TimelonUnit.MILLISelonCONDS,
        selonarchStatsReloncelonivelonr, criticalelonxcelonptionHandlelonr);
  }

  public TwelonelontCountMonitor providelonTwelonelontCountMonitor(
      SelongmelonntManagelonr selongmelonntManagelonr,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    relonturn nelonw TwelonelontCountMonitor(selongmelonntManagelonr, elonxeloncutorSelonrvicelonFactory, 500,
        TimelonUnit.MILLISelonCONDS, selonarchStatsReloncelonivelonr, criticalelonxcelonptionHandlelonr);
  }

  /**
   * Relonturns a managelonr that kelonelonps track of elonarlybird's global statelon whilelon it runs.
   */
  public UpdatelonablelonelonarlybirdStatelonManagelonr providelonUpdatelonablelonelonarlybirdStatelonManagelonr(
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      DynamicPartitionConfig dynamicPartitionConfig,
      ZooKelonelonpelonrProxy zooKelonelonpelonrClielonnt,
      AuroraSchelondulelonrClielonnt schelondulelonrClielonnt,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      ScoringModelonlsManagelonr scoringModelonlsManagelonr,
      TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      SelonarchDeloncidelonr selonarchDeloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    Clock clock = providelonClockForStatelonManagelonr();

    relonturn nelonw UpdatelonablelonelonarlybirdStatelonManagelonr(
        elonarlybirdIndelonxConfig, dynamicPartitionConfig, zooKelonelonpelonrClielonnt, schelondulelonrClielonnt,
        elonxeloncutorSelonrvicelonFactory, scoringModelonlsManagelonr, telonnsorflowModelonlsManagelonr, selonarchStatsReloncelonivelonr,
        selonarchDeloncidelonr, criticalelonxcelonptionHandlelonr,
        clock);
  }

  public Clock providelonClockForStatelonManagelonr() {
    relonturn this.providelonClock();
  }

  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonPartitionManagelonrelonxeloncutorFactory() {
    relonturn sharelondelonxeloncutorSelonrvicelonFactory;
  }

  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonStatelonUpdatelonManagelonrelonxeloncutorFactory() {
    relonturn sharelondelonxeloncutorSelonrvicelonFactory;
  }

  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonTelonrmCountMonitorSchelondulelondelonxeloncutorFactory() {
    relonturn sharelondelonxeloncutorSelonrvicelonFactory;
  }

  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonTwelonelontCountMonitorSchelondulelondelonxeloncutorFactory() {
    relonturn sharelondelonxeloncutorSelonrvicelonFactory;
  }

  /**
   * Providelons thelon SchelondulelondelonxeloncutorSelonrvicelonFactory that will belon uselond to schelondulelon all quelonry cachelon
   * updatelon tasks.
   */
  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonQuelonryCachelonUpdatelonTaskSchelondulelondelonxeloncutorFactory() {
    relonturn nelonw SchelondulelondelonxeloncutorSelonrvicelonFactory() {
      @Ovelonrridelon
      public QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon<SchelondulelondThrelonadPoolelonxeloncutor> build(
          String threlonadNamelonFormat, boolelonan isDaelonmon) {
        SchelondulelondThrelonadPoolelonxeloncutor threlonadpoolelonxeloncutor =
            nelonw SchelondulelondThrelonadPoolelonxeloncutor(QUelonRY_CACHelon_NUM_WORKelonR_THRelonADS_AT_STARTUP,
                buildThrelonadFactory(threlonadNamelonFormat, isDaelonmon));
        threlonadpoolelonxeloncutor.selontMaximumPoolSizelon(QUelonRY_CACHelon_NUM_WORKelonR_THRelonADS_AT_STARTUP);
        threlonadpoolelonxeloncutor.selontCorelonPoolSizelon(QUelonRY_CACHelon_NUM_WORKelonR_THRelonADS_AT_STARTUP);
        threlonadpoolelonxeloncutor.selontelonxeloncutelonelonxistingDelonlayelondTasksAftelonrShutdownPolicy(falselon);
        threlonadpoolelonxeloncutor.selontContinuelonelonxistingPelonriodicTasksAftelonrShutdownPolicy(falselon);
        threlonadpoolelonxeloncutor.selontRelonmovelonOnCancelonlPolicy(truelon);
        LOG.info("Starting quelonry cachelon elonxeloncutor with {} threlonad.",
            QUelonRY_CACHelon_NUM_WORKelonR_THRelonADS_AT_STARTUP);

        relonturn nelonw QuelonryCachelonUpdatelonrSchelondulelondelonxeloncutorSelonrvicelon<SchelondulelondThrelonadPoolelonxeloncutor>(
            threlonadpoolelonxeloncutor) {
          @Ovelonrridelon public void selontWorkelonrPoolSizelonAftelonrStartup() {
            delonlelongatelon.selontCorelonPoolSizelon(1);
            delonlelongatelon.selontMaximumPoolSizelon(1);
            LOG.info("Relonselont quelonry cachelon elonxeloncutor to belon singlelon threlonadelond.");
          }
        };
      }
    };
  }

  public SchelondulelondelonxeloncutorSelonrvicelonFactory providelonSimplelonUselonrUpdatelonIndelonxelonrSchelondulelondelonxeloncutorFactory() {
    relonturn sharelondelonxeloncutorSelonrvicelonFactory;
  }

  /**
   * Relonturns thelon managelonr that managelons thelon pool of selonarchelonr threlonads.
   */
  public elonarlybirdFuturelonPoolManagelonr providelonFuturelonPoolManagelonr() {
    relonturn nelonw elonarlybirdFuturelonPoolManagelonr("SelonarchelonrWorkelonr");
  }

  /**
   * Relonturns thelon managelonr that managelons all elonarlybird finaglelon selonrvelonrs (warm up and production).
   */
  public elonarlybirdFinaglelonSelonrvelonrManagelonr providelonFinaglelonSelonrvelonrManagelonr(
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    relonturn nelonw elonarlybirdProductionFinaglelonSelonrvelonrManagelonr(criticalelonxcelonptionHandlelonr);
  }

  /**
   * Crelonatelons thelon production selonrvelonrselont managelonr.
   */
  public elonarlybirdSelonrvelonrSelontManagelonr providelonSelonrvelonrSelontManagelonr(
      ZooKelonelonpelonrProxy discovelonryClielonnt,
      DynamicPartitionConfig dynamicPartitionConfig,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      int port,
      String selonrvelonrSelontNamelonPrelonfix) {
    relonturn nelonw elonarlybirdSelonrvelonrSelontManagelonr(
        selonarchStatsReloncelonivelonr,
        discovelonryClielonnt,
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
        port,
        selonrvelonrSelontNamelonPrelonfix);
  }

  /**
   * Crelonatelons thelon warm up selonrvelonrselont managelonr.
   */
  public elonarlybirdWarmUpManagelonr providelonWarmUpManagelonr(
      ZooKelonelonpelonrProxy discovelonryClielonnt,
      DynamicPartitionConfig dynamicPartitionConfig,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      Deloncidelonr deloncidelonr,
      Clock clock,
      int port,
      String selonrvelonrSelontNamelonPrelonfix) {
    relonturn nelonw elonarlybirdWarmUpManagelonr(
        nelonw elonarlybirdSelonrvelonrSelontManagelonr(
            selonarchStatsReloncelonivelonr,
            discovelonryClielonnt,
            dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
            port,
            selonrvelonrSelontNamelonPrelonfix),
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
        selonarchIndelonxingMelontricSelont,
        deloncidelonr,
        clock);
  }

  /**
   * Relonturns a dark proxy that knows how to selonnd dark traffic to thelon warm up elonarlybird selonrvelonrselont.
   */
  public elonarlybirdDarkProxy providelonelonarlybirdDarkProxy(
      SelonarchDeloncidelonr selonarchDeloncidelonr,
      StatsReloncelonivelonr finaglelonStatsReloncelonivelonr,
      elonarlybirdSelonrvelonrSelontManagelonr elonarlybirdSelonrvelonrSelontManagelonr,
      elonarlybirdWarmUpManagelonr elonarlybirdWarmUpManagelonr,
      String clustelonrNamelon) {
    relonturn nelonw elonarlybirdDarkProxy(selonarchDeloncidelonr,
                                  finaglelonStatsReloncelonivelonr.scopelon("dark_proxy"),
                                  elonarlybirdSelonrvelonrSelontManagelonr,
                                  elonarlybirdWarmUpManagelonr,
                                  clustelonrNamelon);
  }


  /**
   * Relonturns thelon managelonr for all (non-Telonnsorflow) scoring modelonls.
   */
  public ScoringModelonlsManagelonr providelonScoringModelonlsManagelonr(
      SelonarchStatsReloncelonivelonr selonrvelonrStats,
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig) {
    boolelonan modelonlselonnablelond = elonarlybirdConfig.gelontBool("scoring_modelonls_elonnablelond", falselon);
    if (!modelonlselonnablelond) {
      LOG.info("Scoring Modelonls - Disablelond in thelon config. Not loading any modelonls.");
      selonrvelonrStats.gelontCountelonr("scoring_modelonls_disablelond_in_config").increlonmelonnt();
      relonturn ScoringModelonlsManagelonr.NO_OP_MANAGelonR;
    }

    String hdfsNamelonNodelon = elonarlybirdConfig.gelontString("scoring_modelonls_namelonnodelon");
    String hdfsModelonlsPath = elonarlybirdConfig.gelontString("scoring_modelonls_baselondir");
    try {
      relonturn ScoringModelonlsManagelonr.crelonatelon(
          selonrvelonrStats, hdfsNamelonNodelon, hdfsModelonlsPath, elonarlybirdIndelonxConfig.gelontSchelonma());
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Scoring Modelonls - elonrror crelonating ScoringModelonlsManagelonr", elon);
      selonrvelonrStats.gelontCountelonr("scoring_modelonls_initialization_elonrrors").increlonmelonnt();
      relonturn ScoringModelonlsManagelonr.NO_OP_MANAGelonR;
    }
  }

  /**
   * Providelons thelon managelonr for all Telonnsorflow modelonls.
   */
  public TelonnsorflowModelonlsManagelonr providelonTelonnsorflowModelonlsManagelonr(
      SelonarchStatsReloncelonivelonr selonrvelonrStats,
      String statsPrelonfix,
      Deloncidelonr deloncidelonr,
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig) {

    boolelonan modelonlselonnablelond = elonarlybirdPropelonrty.TF_MODelonLS_elonNABLelonD.gelont(falselon);

    if (!modelonlselonnablelond) {
      LOG.info("Telonnsorflow Modelonls - Disablelond in thelon config. Not loading any modelonls.");
      selonrvelonrStats.gelontCountelonr("tf_modelonls_disablelond_in_config").increlonmelonnt();
      relonturn TelonnsorflowModelonlsManagelonr.crelonatelonNoOp(statsPrelonfix);
    }

    String modelonlsConfigPath =
        Prelonconditions.chelonckNotNull(elonarlybirdPropelonrty.TF_MODelonLS_CONFIG_PATH.gelont());


    int intraOpThrelonads = Prelonconditions.chelonckNotNull(elonarlybirdPropelonrty.TF_INTRA_OP_THRelonADS.gelont(0));
    int intelonrOpThrelonads = Prelonconditions.chelonckNotNull(elonarlybirdPropelonrty.TF_INTelonR_OP_THRelonADS.gelont(0));

    TelonnsorflowModelonlsManagelonr.initTelonnsorflowThrelonadPools(intraOpThrelonads, intelonrOpThrelonads);

    relonturn TelonnsorflowModelonlsManagelonr.crelonatelonUsingConfigFilelon(
        FilelonUtils.gelontFilelonHandlelon(modelonlsConfigPath),
        truelon,
        statsPrelonfix,
        () -> DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
          deloncidelonr, "elonnablelon_tf_selonrvelon_modelonls"),
        () -> deloncidelonr.isAvailablelon("elonnablelon_tf_load_modelonls"),
        elonarlybirdIndelonxConfig.gelontSchelonma());
  }

  public SelonarchStatsReloncelonivelonr providelonelonarlybirdSelonrvelonrStatsReloncelonivelonr() {
    relonturn sharelondSelonarchStatsReloncelonivelonr;
  }

  public StatsReloncelonivelonr providelonFinaglelonStatsReloncelonivelonr() {
    relonturn sharelondFinaglelonStatsReloncelonivelonr;
  }

  public SelonarchIndelonxingMelontricSelont providelonSelonarchIndelonxingMelontricSelont() {
    relonturn selonarchIndelonxingMelontricSelont;
  }

  public elonarlybirdSelonarchelonrStats providelonTwelonelontsSelonarchelonrStats() {
    relonturn twelonelontsSelonarchelonrStats;
  }

  /**
   * Providelons thelon clock to belon uselond by this elonarlybird.
   */
  public Clock providelonClock() {
    relonturn Clock.SYSTelonM_CLOCK;
  }

  /**
   * Providelons thelon config for thelon multi-selongmelonnt telonrm dictionary managelonr.
   */
  public MultiSelongmelonntTelonrmDictionaryManagelonr.Config providelonMultiSelongmelonntTelonrmDictionaryManagelonrConfig() {
    relonturn nelonw MultiSelongmelonntTelonrmDictionaryManagelonr.Config(
        Lists.nelonwArrayList(
            elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon()));
  }

  /**
   * Providelons thelon managelonr for thelon telonrm dictionary that spans all selongmelonnts.
   */
  public MultiSelongmelonntTelonrmDictionaryManagelonr providelonMultiSelongmelonntTelonrmDictionaryManagelonr(
      MultiSelongmelonntTelonrmDictionaryManagelonr.Config telonrmDictionaryConfig,
      SelongmelonntManagelonr selongmelonntManagelonr,
      SelonarchStatsReloncelonivelonr statsReloncelonivelonr,
      Deloncidelonr deloncidelonr,
      elonarlybirdClustelonr elonarlybirdClustelonr) {
    relonturn nelonw MultiSelongmelonntTelonrmDictionaryManagelonr(
        telonrmDictionaryConfig, selongmelonntManagelonr, statsReloncelonivelonr, deloncidelonr, elonarlybirdClustelonr);
  }

  /**
   * Relonturns thelon partition managelonr to belon uselond by thelon archivelon elonarlybirds.
   */
  public PartitionManagelonr providelonFullArchivelonPartitionManagelonr(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      SelongmelonntManagelonr selongmelonntManagelonr,
      DynamicPartitionConfig dynamicPartitionConfig,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SelonrvelonrSelontMelonmbelonr selonrvelonrSelontMelonmbelonr,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      SchelondulelondelonxeloncutorSelonrvicelonFactory uselonrUpdatelonIndelonxelonrelonxeloncutorFactory,
      SelonarchIndelonxingMelontricSelont elonarlybirdSelonarchIndelonxingMelontricSelont,
      Clock clock,
      SelongmelonntSyncConfig selongmelonntSyncConfig,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) throws IOelonxcelonption {

    relonturn nelonw ArchivelonSelonarchPartitionManagelonr(
        zooKelonelonpelonrTryLockFactory,
        quelonryCachelonManagelonr,
        selongmelonntManagelonr,
        dynamicPartitionConfig,
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        selonarchStatsReloncelonivelonr,
        elonarlybirdIndelonxConfig,
        selonrvelonrSelontMelonmbelonr,
        elonxeloncutorSelonrvicelonFactory,
        uselonrUpdatelonIndelonxelonrelonxeloncutorFactory,
        elonarlybirdSelonarchIndelonxingMelontricSelont,
        selongmelonntSyncConfig,
        clock,
        criticalelonxcelonptionHandlelonr);
  }

  /**
   * Providelons thelon SelongmelonntSyncConfig instancelon to belon uselond by elonarlybird.
   */
  public SelongmelonntSyncConfig providelonSelongmelonntSyncConfig(elonarlybirdClustelonr clustelonr) {
    String scrubGelonn = null;
    if (clustelonr == elonarlybirdClustelonr.FULL_ARCHIVelon) {
      scrubGelonn = elonarlybirdPropelonrty.elonARLYBIRD_SCRUB_GelonN.gelont();
      LOG.info("Thelon scrubGelonn providelond from Aurora is: {}", scrubGelonn);
      Prelonconditions.chelonckStatelon(Strings.isNotelonmpty(scrubGelonn));
    }
    relonturn nelonw SelongmelonntSyncConfig(Optional.ofNullablelon(scrubGelonn));
  }

  protelonctelond void storelonelonarlybirdStartupProducts(
      TwelonelontCrelonatelonHandlelonr twelonelontCrelonatelonHandlelonr,
      PartitionWritelonr partitionWritelonr,
      elonarlybirdIndelonxFlushelonr elonarlybirdIndelonxFlushelonr
  ) {
    // TelonstWirelonModulelon wants to storelon thelonselon for furthelonr uselon.
  }

  /**
   * What direlonctory arelon welon going to load selongmelonnts from on startup.
   *
   * Whelonn you'relon running loadtelonsts or stagingN instancelons and thelony don't havelon a reloncelonnt indelonx
   * flushelond, it can takelon hours to gelonnelonratelon a nelonw indelonx with a frelonsh startup. This slows
   * down delonvelonlopmelonnt. If thelon relonad_indelonx_from_prod_location flag is selont to truelon, welon will relonad
   * thelon indelonx from thelon location whelonrelon prod instancelons arelon flushing thelonir indelonx to.
   * Unselont it if you want to gelonnelonratelon your own indelonx.
   *
   * @relonturn a string with thelon direlonctory.
   */
  public String gelontIndelonxLoadingDirelonctory() {
    boolelonan relonadIndelonxFromProdLocation = elonarlybirdPropelonrty.RelonAD_INDelonX_FROM_PROD_LOCATION.gelont(falselon);
    String elonnvironmelonnt = elonarlybirdPropelonrty.elonNV.gelont("no_elonnv_speloncifielond"); // delonfault valuelon for telonsts.
    String relonadIndelonxDir = elonarlybirdPropelonrty.HDFS_INDelonX_SYNC_DIR.gelont();

    if (relonadIndelonxFromProdLocation) {
      LOG.info("Will attelonmpt to relonad indelonx from prod locations");
      LOG.info("Indelonx direlonctory providelond: {}", relonadIndelonxDir);
      // Relonplacing thelon path is a bit hacky, but it works ok.
      relonadIndelonxDir = relonadIndelonxDir.relonplacelon("/" + elonnvironmelonnt + "/", "/prod/");
      LOG.info("Will instelonad uselon indelonx direlonctory: {}", relonadIndelonxDir);
    }

    relonturn relonadIndelonxDir;
  }

  /**
   * Indelonxelonr for audio spacelon elonvelonnts.
   */
  public AudioSpacelonelonvelonntsStrelonamIndelonxelonr providelonAudioSpacelonelonvelonntsStrelonamIndelonxelonr(
      AudioSpacelonTablelon audioSpacelonTablelon,
      Clock clock) {
    try {
      relonturn nelonw AudioSpacelonelonvelonntsStrelonamIndelonxelonr(
          FinaglelonKafkaClielonntUtils.nelonwKafkaConsumelonrForAssigning(
              "",
              nelonw ThriftDelonselonrializelonr<>(AudioSpacelonBaselonelonvelonnt.class),
              "",
              20
          ), audioSpacelonTablelon, clock);
    } catch (MissingKafkaTopicelonxcelonption elonx) {
      LOG.elonrror("Missing kafka strelonam", elonx);
      relonturn null;
    }
  }

  /**
   * Relonturns a class to start thelon elonarlybird. Selonelon {@link elonarlybirdStartup}.
   */
  public elonarlybirdStartup providelonelonarlybirdStartup(
      PartitionManagelonr partitionManagelonr,
      UselonrUpdatelonsStrelonamIndelonxelonr uselonrUpdatelonsStrelonamIndelonxelonr,
      UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
      AudioSpacelonelonvelonntsStrelonamIndelonxelonr audioSpacelonelonvelonntsStrelonamIndelonxelonr,
      DynamicPartitionConfig dynamicPartitionConfig,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      SelongmelonntManagelonr selongmelonntManagelonr,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      SelonrvelonrSelontMelonmbelonr selonrvelonrSelontMelonmbelonr,
      Clock clock,
      SelongmelonntSyncConfig selongmelonntSyncConfig,
      elonarlybirdSelongmelonntFactory elonarlybirdSelongmelonntFactory,
      elonarlybirdClustelonr clustelonr,
      SelonarchDeloncidelonr deloncidelonr) throws IOelonxcelonption {
    if (clustelonr == elonarlybirdClustelonr.FULL_ARCHIVelon) {
      relonturn nelonw PartitionManagelonrStartup(clock, partitionManagelonr);
    }

    // Chelonck that thelon elonarlybird namelon is what welon'relon elonxpeloncting so welon can build thelon kafka topics.
    String elonarlybirdNamelon = elonarlybirdPropelonrty.elonARLYBIRD_NAMelon.gelont();
    Prelonconditions.chelonckArgumelonnt("elonarlybird-relonaltimelon".elonquals(elonarlybirdNamelon)
        || "elonarlybird-protelonctelond".elonquals(elonarlybirdNamelon)
        || "elonarlybird-relonaltimelon-elonxp0".elonquals(elonarlybirdNamelon)
        || "elonarlybird-relonaltimelon_cg".elonquals(elonarlybirdNamelon));

    StartupUselonrelonvelonntIndelonxelonr startupUselonrelonvelonntIndelonxelonr = nelonw StartupUselonrelonvelonntIndelonxelonr(
        providelonSelonarchIndelonxingMelontricSelont(),
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        selongmelonntManagelonr,
        clock);

    // Coordinatelon lelonaving thelon selonrvelonrselont to flush selongmelonnts to HDFS.
    CoordinatelondelonarlybirdAction actionCoordinator = nelonw CoordinatelondelonarlybirdAction(
        zooKelonelonpelonrTryLockFactory,
        "selongmelonnt_flushelonr",
        dynamicPartitionConfig,
        selonrvelonrSelontMelonmbelonr,
        criticalelonxcelonptionHandlelonr,
        selongmelonntSyncConfig);
    actionCoordinator.selontShouldSynchronizelon(truelon);

    FilelonSystelonm hdfsFilelonSystelonm = HdfsUtil.gelontHdfsFilelonSystelonm();
    elonarlybirdIndelonxFlushelonr elonarlybirdIndelonxFlushelonr = nelonw elonarlybirdIndelonxFlushelonr(
        actionCoordinator,
        hdfsFilelonSystelonm,
        elonarlybirdPropelonrty.HDFS_INDelonX_SYNC_DIR.gelont(),
        selongmelonntManagelonr,
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
        clock,
        nelonw TimelonLimitelondHadoopelonxistsCall(hdfsFilelonSystelonm),
        providelonOptimizationAndFlushingCoordinationLock());

    String baselonTopicNamelon = "selonarch_ingelonstelonr_%s_elonvelonnts_%s_%s";

    String elonarlybirdTypelon;

    if ("elonarlybird-protelonctelond".elonquals(elonarlybirdNamelon)) {
      elonarlybirdTypelon = "protelonctelond";
    } elonlselon if ("elonarlybird-relonaltimelon_cg".elonquals(elonarlybirdNamelon)) {
      elonarlybirdTypelon = "relonaltimelon_cg";
    } elonlselon {
      elonarlybirdTypelon = "relonaltimelon";
    }

    String twelonelontTopicNamelon = String.format(
        baselonTopicNamelon,
        "indelonxing",
        elonarlybirdTypelon,
        elonarlybirdPropelonrty.KAFKA_elonNV.gelont());

    String updatelonTopicNamelon = String.format(
        baselonTopicNamelon,
        "updatelon",
        elonarlybirdTypelon,
        elonarlybirdPropelonrty.KAFKA_elonNV.gelont());

    LOG.info("Twelonelont topic: {}", twelonelontTopicNamelon);
    LOG.info("Updatelon topic: {}", updatelonTopicNamelon);

    TopicPartition twelonelontTopic = nelonw TopicPartition(
        twelonelontTopicNamelon,
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig().gelontIndelonxingHashPartitionID());
    TopicPartition updatelonTopic = nelonw TopicPartition(
        updatelonTopicNamelon,
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig().gelontIndelonxingHashPartitionID());

    elonarlybirdKafkaConsumelonrsFactory elonarlybirdKafkaConsumelonrsFactory =
        providelonelonarlybirdKafkaConsumelonrsFactory();
    FrelonshStartupHandlelonr frelonshStartupHandlelonr = nelonw FrelonshStartupHandlelonr(
        clock,
        elonarlybirdKafkaConsumelonrsFactory,
        twelonelontTopic,
        updatelonTopic,
        selongmelonntManagelonr,
        elonarlybirdConfig.gelontMaxSelongmelonntSizelon(),
        elonarlybirdConfig.gelontLatelonTwelonelontBuffelonr(),
        criticalelonxcelonptionHandlelonr
    );

    TwelonelontUpdatelonHandlelonr updatelonHandlelonr = nelonw TwelonelontUpdatelonHandlelonr(selongmelonntManagelonr);

    CoordinatelondelonarlybirdAction postOptimizationRelonbuilds = nelonw CoordinatelondelonarlybirdAction(
            zooKelonelonpelonrTryLockFactory,
            "post_optimization_relonbuilds",
            dynamicPartitionConfig,
            selonrvelonrSelontMelonmbelonr,
            criticalelonxcelonptionHandlelonr,
            selongmelonntSyncConfig
    );
    postOptimizationRelonbuilds.selontShouldSynchronizelon(truelon);
    CoordinatelondelonarlybirdAction gcAction = nelonw CoordinatelondelonarlybirdAction(
            zooKelonelonpelonrTryLockFactory,
            "gc_belonforelon_optimization",
            dynamicPartitionConfig,
            selonrvelonrSelontMelonmbelonr,
            criticalelonxcelonptionHandlelonr,
            selongmelonntSyncConfig
    );
    gcAction.selontShouldSynchronizelon(truelon);

    TwelonelontCrelonatelonHandlelonr crelonatelonHandlelonr = nelonw TwelonelontCrelonatelonHandlelonr(
        selongmelonntManagelonr,
        providelonSelonarchIndelonxingMelontricSelont(),
        criticalelonxcelonptionHandlelonr,
        multiSelongmelonntTelonrmDictionaryManagelonr,
        quelonryCachelonManagelonr,
        postOptimizationRelonbuilds,
        gcAction,
        elonarlybirdConfig.gelontLatelonTwelonelontBuffelonr(),
        elonarlybirdConfig.gelontMaxSelongmelonntSizelon(),
        providelonKafkaIndelonxCaughtUpMonitor(),
        providelonOptimizationAndFlushingCoordinationLock());

    PartitionWritelonr partitionWritelonr = nelonw PartitionWritelonr(
        crelonatelonHandlelonr,
        updatelonHandlelonr,
        criticalelonxcelonptionHandlelonr,
        PelonnguinVelonrsion.velonrsionFromBytelonValuelon(elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon()),
        clock);

    KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> rawKafkaConsumelonr =
        elonarlybirdKafkaConsumelonrsFactory.crelonatelonKafkaConsumelonr(
            "elonarlybird_twelonelont_kafka_consumelonr");

    elonarlybirdKafkaConsumelonr elonarlybirdKafkaConsumelonr = providelonKafkaConsumelonr(
        criticalelonxcelonptionHandlelonr,
        rawKafkaConsumelonr,
        twelonelontTopic,
        updatelonTopic,
        partitionWritelonr,
        elonarlybirdIndelonxFlushelonr);

    elonarlybirdIndelonxLoadelonr elonarlybirdIndelonxLoadelonr = nelonw elonarlybirdIndelonxLoadelonr(
        hdfsFilelonSystelonm,
        gelontIndelonxLoadingDirelonctory(), // Selonelon SelonARCH-32839
        elonarlybirdPropelonrty.elonNV.gelont("delonfault_elonnv_valuelon"),
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
        elonarlybirdSelongmelonntFactory,
        selongmelonntSyncConfig,
        clock);

    this.storelonelonarlybirdStartupProducts(
        crelonatelonHandlelonr,
        partitionWritelonr,
        elonarlybirdIndelonxFlushelonr
    );

    relonturn nelonw KafkaStartup(
        selongmelonntManagelonr,
        elonarlybirdKafkaConsumelonr,
        startupUselonrelonvelonntIndelonxelonr,
        uselonrUpdatelonsStrelonamIndelonxelonr,
        uselonrScrubGelonoelonvelonntStrelonamIndelonxelonr,
        audioSpacelonelonvelonntsStrelonamIndelonxelonr,
        quelonryCachelonManagelonr,
        elonarlybirdIndelonxLoadelonr,
        frelonshStartupHandlelonr,
        providelonSelonarchIndelonxingMelontricSelont(),
        multiSelongmelonntTelonrmDictionaryManagelonr,
        criticalelonxcelonptionHandlelonr,
        deloncidelonr
    );
  }

  public QualityFactor providelonQualityFactor(
      Deloncidelonr deloncidelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr
  ) {
    relonturn nelonw elonarlybirdCPUQualityFactor(deloncidelonr,
        ManagelonmelonntFactory.gelontPlatformMXBelonan(OpelonratingSystelonmMXBelonan.class),
        selonarchStatsReloncelonivelonr);
  }

  /**
   * Relonturns a nelonw UselonrUpdatelonsKafkaConsumelonr to relonad uselonr updatelons.
   */
  public UselonrUpdatelonsStrelonamIndelonxelonr providelonUselonrUpdatelonsKafkaConsumelonr(
      SelongmelonntManagelonr selongmelonntManagelonr) {
    try {
      relonturn nelonw UselonrUpdatelonsStrelonamIndelonxelonr(
          UselonrUpdatelonsStrelonamIndelonxelonr.providelonKafkaConsumelonr(),
          elonarlybirdPropelonrty.USelonR_UPDATelonS_KAFKA_TOPIC.gelont(),
          providelonSelonarchIndelonxingMelontricSelont(),
          selongmelonntManagelonr);
    } catch (MissingKafkaTopicelonxcelonption elonx) {
      // Yelons, it will crash thelon selonrvelonr. Welon'velon nelonvelonr selonelonn this topic missing, but
      // welon'velon selonelonn somelon othelonrs, so welon had to build this functionality in thelon
      // constructor. If onelon day this onelon goelons missing, welon'll havelon to figurelon out
      // how to handlelon it. For now, welon crash.
      throw nelonw Runtimelonelonxcelonption(elonx);
    }
  }

  /**
   * Relonturns a nelonw UselonrScrubGelonosKafkaConsumelonr to relonad gelono scrubbing elonvelonnts.
   */
  public UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr providelonUselonrScrubGelonoelonvelonntKafkaConsumelonr(
      SelongmelonntManagelonr selongmelonntManagelonr) {
    try {
      relonturn nelonw UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr(
          UselonrScrubGelonoelonvelonntStrelonamIndelonxelonr.providelonKafkaConsumelonr(),
          elonarlybirdPropelonrty.USelonR_SCRUB_GelonO_KAFKA_TOPIC.gelont(),
          providelonSelonarchIndelonxingMelontricSelont(),
          selongmelonntManagelonr);
    } catch (MissingKafkaTopicelonxcelonption elonx) {
      /**
       * Selonelon {@link #providelonUselonrUpdatelonsKafkaConsumelonr}
       */
      throw nelonw Runtimelonelonxcelonption(elonx);
    }
  }

  /**
   * Relonturns a nelonw ProductionelonarlybirdKafkaConsumelonr to relonad ThriftVelonrsionelondelonvelonnts.
   */
  public elonarlybirdKafkaConsumelonrsFactory providelonelonarlybirdKafkaConsumelonrsFactory() {
    relonturn nelonw ProductionelonarlybirdKafkaConsumelonrsFactory(
        elonarlybirdPropelonrty.KAFKA_PATH.gelont(),
        MAX_POLL_RelonCORDS
    );
  }

  /**
   * Relonturns a class to relonad Twelonelonts in thelon elonarlybird. Selonelon {@link elonarlybirdKafkaConsumelonr}.
   */
  public elonarlybirdKafkaConsumelonr providelonKafkaConsumelonr(
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> rawKafkaConsumelonr,
      TopicPartition twelonelontTopic,
      TopicPartition updatelonTopic,
      PartitionWritelonr partitionWritelonr,
      elonarlybirdIndelonxFlushelonr elonarlybirdIndelonxFlushelonr
  ) {
    relonturn nelonw elonarlybirdKafkaConsumelonr(
        rawKafkaConsumelonr,
        providelonSelonarchIndelonxingMelontricSelont(),
        criticalelonxcelonptionHandlelonr,
        partitionWritelonr,
        twelonelontTopic,
        updatelonTopic,
        elonarlybirdIndelonxFlushelonr,
        providelonKafkaIndelonxCaughtUpMonitor());
  }
}
