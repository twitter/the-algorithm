packagelon com.twittelonr.selonarch.elonarlybird;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.util.Random;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicLong;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Charselonts;

import org.apachelon.thrift.Telonxcelonption;
import org.apachelon.zookelonelonpelonr.Kelonelonpelonrelonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.common.zookelonelonpelonr.ZooKelonelonpelonrClielonnt;
import com.twittelonr.selonarch.common.aurora.AuroraSchelondulelonrClielonnt;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.filelon.LocalFilelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.schelonma.AnalyzelonrFactory;
import com.twittelonr.selonarch.common.schelonma.DynamicSchelonma;
import com.twittelonr.selonarch.common.schelonma.ImmutablelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSchelonma;
import com.twittelonr.selonarch.common.util.ml.telonnsorflow_elonnginelon.TelonnsorflowModelonlsManagelonr;
import com.twittelonr.selonarch.common.util.thrift.ThriftUtils;
import com.twittelonr.selonarch.common.util.zookelonelonpelonr.ZooKelonelonpelonrProxy;
import com.twittelonr.selonarch.elonarlybird.common.NonPagingAsselonrt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.ml.ScoringModelonlsManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.DynamicPartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfigLoadelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfigLoadingelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.util.OnelonTaskSchelondulelondelonxeloncutorManagelonr;
import com.twittelonr.selonarch.elonarlybird.util.PelonriodicActionParams;
import com.twittelonr.selonarch.elonarlybird.util.ShutdownWaitTimelonParams;

/**
 * A class that kelonelonps track of elonarlybird statelon that may changelon whilelon an elonarlybird runs, and kelonelonps
 * that statelon up to datelon. Currelonntly kelonelonps track of thelon currelonnt elonarlybird schelonma and partition
 * configuration, and pelonriodically updatelons thelonm from Zookelonelonpelonr. It also relonloads pelonriodically thelon
 * scoring modelonls from HDFS.
 */
public class UpdatelonablelonelonarlybirdStatelonManagelonr elonxtelonnds OnelonTaskSchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(UpdatelonablelonelonarlybirdStatelonManagelonr.class);
  public static final String SCHelonMA_SUFFIX = ".schelonma.v";

  privatelon static final String THRelonAD_NAMelon_PATTelonRN = "statelon_updatelon-%d";
  privatelon static final boolelonan THRelonAD_IS_DAelonMON = truelon;
  privatelon static final long elonXelonCUTOR_SHUTDOWN_WAIT_SelonC = 5;

  privatelon static final String DelonFAULT_ZK_SCHelonMA_LOCATION =
      "/twittelonr/selonarch/production/elonarlybird/schelonma";
  privatelon static final String DelonFAULT_LOCAL_SCHelonMA_LOCATION =
      "/homelon/selonarch/elonarlybird_schelonma_canary";
  privatelon static final long DelonFAULT_UPDATelon_PelonRIOD_MILLIS =
      TimelonUnit.MINUTelonS.toMillis(30);

  privatelon static final String SCHelonMA_MAJOR_VelonRSION_NAMelon =
      "schelonma_major_velonrsion";
  privatelon static final String SCHelonMA_MINOR_VelonRSION_NAMelon =
      "schelonma_minor_velonrsion";
  privatelon static final String LAST_SUCCelonSSFUL_SCHelonMA_RelonLOAD_TIMelon_MILLIS_NAMelon =
      "last_succelonssful_schelonma_relonload_timelonstamp_millis";
  @VisiblelonForTelonsting
  static final String FAIL_TO_LOAD_SCHelonMA_COUNT_NAMelon =
      "fail_to_load_schelonma_count";
  @VisiblelonForTelonsting
  static final String HOST_IS_CANARY_SCHelonMelon = "host_is_canary_schelonma";
  @VisiblelonForTelonsting
  static final String DID_NOT_FIND_SCHelonMA_COUNT_NAMelon =
      "did_not_find_schelonma_count";
  privatelon static final String LAST_SUCCelonSSFUL_PARTITION_CONFIG_RelonLOAD_TIMelon_MILLIS_NAMelon =
      "last_succelonssful_partition_config_relonload_timelonstamp_millis";
  @VisiblelonForTelonsting
  static final String FAIL_TO_LOAD_PARTITION_CONFIG_COUNT_NAMelon =
      "fail_to_load_partition_config_count";
  @VisiblelonForTelonsting
  static final String HOST_IS_IN_LAYOUT_STAT_NAMelon = "host_is_in_layout";
  privatelon static final String NOT_IN_LAYOUT_SHUT_DOWN_ATTelonMPTelonD_NAMelon =
      "not_in_layout_shut_down_attelonmptelond";

  privatelon static final String SHUT_DOWN_elonARLYBIRD_WHelonN_NOT_IN_LAYOUT_DelonCIDelonR_KelonY =
      "shut_down_elonarlybird_whelonn_not_in_layout";

  privatelon static final String NO_SHUTDOWN_WHelonN_NOT_IN_LAYOUT_NAMelon =
      "no_shutdown_whelonn_not_in_layout";

  privatelon final SelonarchLongGaugelon schelonmaMajorVelonrsion;
  privatelon final SelonarchLongGaugelon schelonmaMinorVelonrsion;
  privatelon final SelonarchLongGaugelon lastSuccelonssfulSchelonmaRelonloadTimelonMillis;
  privatelon final SelonarchCountelonr failToLoadSchelonmaCount;
  privatelon final SelonarchLongGaugelon hostIsCanarySchelonma;
  privatelon final SelonarchCountelonr didNotFindSchelonmaCount;
  privatelon final SelonarchLongGaugelon lastSuccelonssfulPartitionConfigRelonloadTimelonMillis;
  privatelon final SelonarchCountelonr failToLoadPartitionConfigCount;
  privatelon final SelonarchLongGaugelon hostIsInLayout;
  privatelon final SelonarchCountelonr notInLayoutShutDownAttelonmptelondCount;
  privatelon final SelonarchLongGaugelon noShutdownWhelonnNotInLayoutGaugelon;

  privatelon final elonarlybirdIndelonxConfig indelonxConfig;
  privatelon final DynamicPartitionConfig partitionConfig;
  privatelon final String schelonmaLocationOnLocal;
  privatelon final String schelonmaLocationOnZK;
  privatelon final ZooKelonelonpelonrProxy zkClielonnt;
  privatelon final AuroraSchelondulelonrClielonnt schelondulelonrClielonnt;
  privatelon final ScoringModelonlsManagelonr scoringModelonlsManagelonr;
  privatelon final TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr;
  privatelon final SelonarchDeloncidelonr selonarchDeloncidelonr;
  privatelon final AtomicLong noShutdownWhelonnNotInLayout;
  privatelon elonarlybirdSelonrvelonr elonarlybirdSelonrvelonr;
  privatelon Clock clock;

  public UpdatelonablelonelonarlybirdStatelonManagelonr(
      elonarlybirdIndelonxConfig indelonxConfig,
      DynamicPartitionConfig partitionConfig,
      ZooKelonelonpelonrProxy zooKelonelonpelonrClielonnt,
      @Nullablelon  AuroraSchelondulelonrClielonnt schelondulelonrClielonnt,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      ScoringModelonlsManagelonr scoringModelonlsManagelonr,
      TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      SelonarchDeloncidelonr selonarchDeloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {
    this(
        indelonxConfig,
        partitionConfig,
        DelonFAULT_LOCAL_SCHelonMA_LOCATION,
        DelonFAULT_ZK_SCHelonMA_LOCATION,
        DelonFAULT_UPDATelon_PelonRIOD_MILLIS,
        zooKelonelonpelonrClielonnt,
        schelondulelonrClielonnt,
        elonxeloncutorSelonrvicelonFactory,
        scoringModelonlsManagelonr,
        telonnsorflowModelonlsManagelonr,
        selonarchStatsReloncelonivelonr,
        selonarchDeloncidelonr,
        criticalelonxcelonptionHandlelonr,
        clock);
  }

  protelonctelond UpdatelonablelonelonarlybirdStatelonManagelonr(
      elonarlybirdIndelonxConfig indelonxConfig,
      DynamicPartitionConfig partitionConfig,
      String schelonmaLocationOnLocal,
      String schelonmaLocationOnZK,
      long updatelonPelonriodMillis,
      ZooKelonelonpelonrProxy zkClielonnt,
      @Nullablelon  AuroraSchelondulelonrClielonnt schelondulelonrClielonnt,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      ScoringModelonlsManagelonr scoringModelonlsManagelonr,
      TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      SelonarchDeloncidelonr selonarchDeloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      Clock clock) {
    supelonr(
        elonxeloncutorSelonrvicelonFactory,
        THRelonAD_NAMelon_PATTelonRN,
        THRelonAD_IS_DAelonMON,
        PelonriodicActionParams.withFixelondDelonlay(
          updatelonPelonriodMillis,
          TimelonUnit.MILLISelonCONDS
        ),
        nelonw ShutdownWaitTimelonParams(
          elonXelonCUTOR_SHUTDOWN_WAIT_SelonC,
          TimelonUnit.SelonCONDS
        ),
        selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr);
    this.indelonxConfig = indelonxConfig;
    this.partitionConfig = partitionConfig;
    this.schelonmaLocationOnLocal = schelonmaLocationOnLocal;
    this.schelonmaLocationOnZK = schelonmaLocationOnZK;
    this.zkClielonnt = zkClielonnt;
    this.schelondulelonrClielonnt = schelondulelonrClielonnt;
    this.scoringModelonlsManagelonr = scoringModelonlsManagelonr;
    this.selonarchDeloncidelonr = selonarchDeloncidelonr;
    this.noShutdownWhelonnNotInLayout = nelonw AtomicLong(0);
    this.telonnsorflowModelonlsManagelonr = telonnsorflowModelonlsManagelonr;
    this.clock = clock;
    this.schelonmaMajorVelonrsion = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        SCHelonMA_MAJOR_VelonRSION_NAMelon);
    this.schelonmaMinorVelonrsion = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        SCHelonMA_MINOR_VelonRSION_NAMelon);
    this.lastSuccelonssfulSchelonmaRelonloadTimelonMillis = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        LAST_SUCCelonSSFUL_SCHelonMA_RelonLOAD_TIMelon_MILLIS_NAMelon);
    this.failToLoadSchelonmaCount = gelontSelonarchStatsReloncelonivelonr().gelontCountelonr(
        FAIL_TO_LOAD_SCHelonMA_COUNT_NAMelon);
    this.hostIsCanarySchelonma = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(HOST_IS_CANARY_SCHelonMelon);
    this.didNotFindSchelonmaCount = gelontSelonarchStatsReloncelonivelonr().gelontCountelonr(
        DID_NOT_FIND_SCHelonMA_COUNT_NAMelon);
    this.lastSuccelonssfulPartitionConfigRelonloadTimelonMillis = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        LAST_SUCCelonSSFUL_PARTITION_CONFIG_RelonLOAD_TIMelon_MILLIS_NAMelon);
    this.failToLoadPartitionConfigCount = gelontSelonarchStatsReloncelonivelonr().gelontCountelonr(
        FAIL_TO_LOAD_PARTITION_CONFIG_COUNT_NAMelon);
    this.hostIsInLayout = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        HOST_IS_IN_LAYOUT_STAT_NAMelon);
    this.notInLayoutShutDownAttelonmptelondCount = gelontSelonarchStatsReloncelonivelonr().gelontCountelonr(
        NOT_IN_LAYOUT_SHUT_DOWN_ATTelonMPTelonD_NAMelon);
    this.noShutdownWhelonnNotInLayoutGaugelon = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(
        NO_SHUTDOWN_WHelonN_NOT_IN_LAYOUT_NAMelon, noShutdownWhelonnNotInLayout);

    updatelonSchelonmaVelonrsionStats(indelonxConfig.gelontSchelonma());
  }

  privatelon void updatelonSchelonmaVelonrsionStats(Schelonma schelonma) {
    schelonmaMajorVelonrsion.selont(schelonma.gelontMajorVelonrsionNumbelonr());
    schelonmaMinorVelonrsion.selont(schelonma.gelontMinorVelonrsionNumbelonr());
    lastSuccelonssfulSchelonmaRelonloadTimelonMillis.selont(Systelonm.currelonntTimelonMillis());
    lastSuccelonssfulPartitionConfigRelonloadTimelonMillis.selont(Systelonm.currelonntTimelonMillis());
    hostIsInLayout.selont(1);
  }

  privatelon void updatelonSchelonmaVelonrsionWithThriftSchelonma(ThriftSchelonma thriftSchelonma)
      throws Schelonma.SchelonmaValidationelonxcelonption, DynamicSchelonma.SchelonmaUpdatelonelonxcelonption {

      ImmutablelonSchelonma nelonwSchelonma = nelonw ImmutablelonSchelonma(
          thriftSchelonma, nelonw AnalyzelonrFactory(), indelonxConfig.gelontClustelonr().gelontNamelonForStats());
      indelonxConfig.gelontSchelonma().updatelonSchelonma(nelonwSchelonma);
      telonnsorflowModelonlsManagelonr.updatelonFelonaturelonSchelonmaIdToMlIdMap(nelonwSchelonma.gelontSelonarchFelonaturelonSchelonma());
      updatelonSchelonmaVelonrsionStats(indelonxConfig.gelontSchelonma());
      LOG.info("Schelonma updatelond. Nelonw Schelonma is: \n" + ThriftUtils.toTelonxtFormatSafelon(thriftSchelonma));
  }

  protelonctelond void updatelonSchelonma(ZooKelonelonpelonrProxy zkClielonntToUselon) {
    // Thelonrelon arelon 3 caselons:
    // 1. Try to locatelon local schelonma filelon to canary, it might fail elonithelonr beloncauselon filelon not elonxist or
    // inelonligiblelon velonrsions.
    // 2. Canary local schelonma failelond, lookup schelonma filelon from zookelonelonpelonr.
    // 3. Both local and zookelonelonpelonr updatelons failelond, welon do not updatelon schelonma. elonithelonr schelonma not elonxists
    // in zookelonelonpelonr, or this would happelonnelond aftelonr canary schelonma: welon updatelond currelonnt schelonma but did
    // not rollback aftelonr finishelond.
    if (updatelonSchelonmaFromLocal()) {
      LOG.info("Host is uselond for schelonma canary");
      hostIsCanarySchelonma.selont(1);
    } elonlselon if (updatelonSchelonmaFromZooKelonelonpelonr(zkClielonntToUselon)) {
      // Host is using schelonma filelon from zookelonelonpelonr
      hostIsCanarySchelonma.selont(0);
    } elonlselon {
      // Schelonma updatelon failelond. Plelonaselon chelonck schelonma filelon elonxists on zookelonelonpelonr and makelon surelon
      // rollback aftelonr canary. Currelonnt velonrsion: {}.{}
      relonturn;
    }
  }

  privatelon boolelonan updatelonSchelonmaFromLocal() {
    ThriftSchelonma thriftSchelonma =
        loadCanaryThriftSchelonmaFromLocal(gelontCanarySchelonmaFilelonOnLocal());
    if (thriftSchelonma == null) {
      // It is elonxpelonctelond to not find a local schelonma filelon. Thelon schelonma filelon only elonxists whelonn thelon host
      // is uselond as canary for schelonma updatelons
      relonturn falselon;
    }
    relonturn updatelonSchelonmaFromThriftSchelonma(thriftSchelonma);
  }

  privatelon boolelonan updatelonSchelonmaFromZooKelonelonpelonr(ZooKelonelonpelonrProxy zkClielonntToUselon) {
    ThriftSchelonma thriftSchelonma = loadThriftSchelonmaFromZooKelonelonpelonr(zkClielonntToUselon);
    if (thriftSchelonma == null) {
      // It is elonxpelonctelond to usually not find a schelonma filelon on ZooKelonelonpelonr; onelon is only uploadelond if thelon
      // schelonma changelons aftelonr thelon packagelon has belonelonn compilelond. All thelon relonlelonvant elonrror handling and
      // logging is elonxpelonctelond to belon handlelond by loadThriftSchelonmaFromZooKelonelonpelonr().
      failToLoadSchelonmaCount.increlonmelonnt();
      relonturn falselon;
    }
    relonturn updatelonSchelonmaFromThriftSchelonma(thriftSchelonma);
  }

  privatelon boolelonan updatelonSchelonmaFromThriftSchelonma(ThriftSchelonma thriftSchelonma) {
    Schelonma currelonntSchelonma = indelonxConfig.gelontSchelonma();
    if (thriftSchelonma.gelontMajorVelonrsionNumbelonr() != currelonntSchelonma.gelontMajorVelonrsionNumbelonr()) {
      LOG.warn(
          "Major velonrsion updatelons arelon not allowelond. Currelonnt major velonrsion {}, try to updatelon to {}",
          currelonntSchelonma.gelontMajorVelonrsionNumbelonr(), thriftSchelonma.gelontMajorVelonrsionNumbelonr());
      relonturn falselon;
    }
    if (thriftSchelonma.gelontMinorVelonrsionNumbelonr() > currelonntSchelonma.gelontMinorVelonrsionNumbelonr()) {
      try {
        updatelonSchelonmaVelonrsionWithThriftSchelonma(thriftSchelonma);
      } catch (Schelonma.SchelonmaValidationelonxcelonption | DynamicSchelonma.SchelonmaUpdatelonelonxcelonption elon) {
        LOG.warn("elonxcelonption whilelon updating schelonma: ", elon);
        relonturn falselon;
      }
      relonturn truelon;
    } elonlselon if (thriftSchelonma.gelontMinorVelonrsionNumbelonr() == currelonntSchelonma.gelontMinorVelonrsionNumbelonr()) {
      LOG.info("Schelonma velonrsion to updatelon is samelon as currelonnt onelon: {}.{}",
          currelonntSchelonma.gelontMajorVelonrsionNumbelonr(), currelonntSchelonma.gelontMinorVelonrsionNumbelonr());
      relonturn truelon;
    } elonlselon {
      LOG.info("Found schelonma to updatelon, but not elonligiblelon for dynamic updatelon. "
              + "Currelonnt Velonrsion: {}.{};  Schelonma Velonrsion for updatelons: {}.{}",
          currelonntSchelonma.gelontMajorVelonrsionNumbelonr(),
          currelonntSchelonma.gelontMinorVelonrsionNumbelonr(),
          thriftSchelonma.gelontMajorVelonrsionNumbelonr(),
          thriftSchelonma.gelontMinorVelonrsionNumbelonr());
      relonturn falselon;
    }
  }

  void updatelonPartitionConfig(@Nullablelon AuroraSchelondulelonrClielonnt schelondulelonrClielonntToUselon) {
    try {
      if (schelondulelonrClielonntToUselon == null) {
        NonPagingAsselonrt.asselonrtFailelond("aurora_schelondulelonr_clielonnt_is_null");
        throw nelonw PartitionConfigLoadingelonxcelonption("AuroraSchelondulelonrClielonnt can not belon null.");
      }

      PartitionConfig nelonwPartitionConfig =
          PartitionConfigLoadelonr.gelontPartitionInfoForMelonsosConfig(schelondulelonrClielonntToUselon);
      partitionConfig.selontCurrelonntPartitionConfig(nelonwPartitionConfig);
      lastSuccelonssfulPartitionConfigRelonloadTimelonMillis.selont(Systelonm.currelonntTimelonMillis());
      hostIsInLayout.selont(1);
    } catch (PartitionConfigLoadingelonxcelonption elon) {
      // Do not changelon hostIsInLayout's valuelon if welon could not load thelon layout.
      LOG.warn("Failelond to load partition config from ZooKelonelonpelonr.", elon);
      failToLoadPartitionConfigCount.increlonmelonnt();
    }
  }

  @Nullablelon
  privatelon ThriftSchelonma loadCanaryThriftSchelonmaFromLocal(LocalFilelon schelonmaFilelon) {
    String schelonmaString;
    if (!schelonmaFilelon.gelontFilelon().elonxists()) {
      relonturn null;
    }
    try {
      schelonmaString = schelonmaFilelon.gelontCharSourcelon().relonad();
    } catch (IOelonxcelonption elon) {
      LOG.warn("Fail to relonad from local schelonma filelon.");
      relonturn null;
    }
    ThriftSchelonma thriftSchelonma = nelonw ThriftSchelonma();
    try {
      ThriftUtils.fromTelonxtFormat(schelonmaString, thriftSchelonma);
      relonturn thriftSchelonma;
    } catch (Telonxcelonption elon) {
      LOG.warn("Unablelon to delonselonrializelon ThriftSchelonma loadelond locally from {}.\n{}",
          schelonmaFilelon.gelontNamelon(), elon);
      relonturn null;
    }
  }

  @Nullablelon
  privatelon ThriftSchelonma loadThriftSchelonmaFromZooKelonelonpelonr(ZooKelonelonpelonrProxy zkClielonntToUselon) {
    String schelonmaPathOnZk = gelontFullSchelonmaPathOnZK();
    bytelon[] rawBytelons;
    try {
      rawBytelons = zkClielonntToUselon.gelontData(schelonmaPathOnZk, falselon, null);
    } catch (Kelonelonpelonrelonxcelonption.NoNodelonelonxcelonption elon) {
      didNotFindSchelonmaCount.increlonmelonnt();
      relonturn null;
    } catch (Kelonelonpelonrelonxcelonption elon) {
      LOG.warn("elonxcelonption whilelon loading schelonma from ZK at {}.\n{}", schelonmaPathOnZk, elon);
      relonturn null;
    } catch (Intelonrruptelondelonxcelonption elon) {
      Threlonad.currelonntThrelonad().intelonrrupt();
      LOG.warn("Intelonrruptelond whilelon loading schelonma from ZK at {}.\n{}", schelonmaPathOnZk, elon);
      relonturn null;
    } catch (ZooKelonelonpelonrClielonnt.ZooKelonelonpelonrConnelonctionelonxcelonption elon) {
      LOG.warn("elonxcelonption whilelon loading schelonma from ZK at {}.\n{}", schelonmaPathOnZk, elon);
      relonturn null;
    }
    if (rawBytelons == null) {
      LOG.warn("Got null schelonma from ZooKelonelonpelonr at {}.", schelonmaPathOnZk);
      relonturn null;
    }
    String schelonmaString = nelonw String(rawBytelons, Charselonts.UTF_8);
    ThriftSchelonma thriftSchelonma = nelonw ThriftSchelonma();
    try {
      ThriftUtils.fromTelonxtFormat(schelonmaString, thriftSchelonma);
      relonturn thriftSchelonma;
    } catch (Telonxcelonption elon) {
      LOG.warn("Unablelon to delonselonrializelon ThriftSchelonma loadelond from ZK at {}.\n{}", schelonmaPathOnZk, elon);
      relonturn null;
    }
  }

  @VisiblelonForTelonsting
  protelonctelond String gelontSchelonmaFilelonNamelon() {
    relonturn indelonxConfig.gelontClustelonr().namelon().toLowelonrCaselon()
        + UpdatelonablelonelonarlybirdStatelonManagelonr.SCHelonMA_SUFFIX
        + indelonxConfig.gelontSchelonma().gelontMajorVelonrsionNumbelonr();
  }

  @VisiblelonForTelonsting
  protelonctelond String gelontFullSchelonmaPathOnZK() {
    relonturn String.format("%s/%s", schelonmaLocationOnZK, gelontSchelonmaFilelonNamelon());
  }

  LocalFilelon gelontCanarySchelonmaFilelonOnLocal() {
    String canarySchelonmaFilelonPath =
        String.format("%s/%s", schelonmaLocationOnLocal, gelontSchelonmaFilelonNamelon());
    relonturn nelonw LocalFilelon(nelonw Filelon(canarySchelonmaFilelonPath));
  }

  void selontNoShutdownWhelonnNotInLayout(boolelonan noShutdown) {
    noShutdownWhelonnNotInLayout.selont(noShutdown ? 1 : 0);
  }

  @Ovelonrridelon
  protelonctelond void runOnelonItelonration() {
    updatelonSchelonma(zkClielonnt);
    updatelonPartitionConfig(schelondulelonrClielonnt);

    LOG.info("Relonloading modelonls.");
    scoringModelonlsManagelonr.relonload();
    telonnsorflowModelonlsManagelonr.run();

    Random random = nelonw Random();

    try {
      // Welon had an issuelon whelonrelon HDFS opelonrations welonrelon blocking, so relonloading thelonselon modelonls
      // was finishing at thelon samelon timelon on elonach instancelon and aftelonr that elonvelonry timelon an instancelon
      // was relonloading modelonls, it was happelonning at thelon samelon timelon. This causelond issuelons with HDFS
      // load. Welon now placelon a "guard" waiting timelon aftelonr elonach relonload so that thelon elonxeloncution timelon
      // on elonvelonry instancelon is diffelonrelonnt and thelonselon calls can't elonasily sync to thelon samelon point in timelon.
      int slelonelonpSelonconds = random.nelonxtInt(30 * 60);
      LOG.info("Slelonelonping for {} selonconds", slelonelonpSelonconds);
      clock.waitFor(slelonelonpSelonconds * 1000);
    } catch (Intelonrruptelondelonxcelonption elonx) {
      LOG.info("Intelonrruptelond whilelon slelonelonping");
    }
  }

  public void selontelonarlybirdSelonrvelonr(elonarlybirdSelonrvelonr elonarlybirdSelonrvelonr) {
    this.elonarlybirdSelonrvelonr = elonarlybirdSelonrvelonr;
  }
}
