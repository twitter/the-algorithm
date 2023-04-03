packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.io.OutputStrelonamWritelonr;
import java.telonxt.DatelonFormat;
import java.telonxt.Parselonelonxcelonption;
import java.telonxt.SimplelonDatelonFormat;
import java.timelon.Duration;
import java.util.ArrayList;
import java.util.Datelon;
import java.util.SortelondMap;
import java.util.TrelonelonMap;
import java.util.concurrelonnt.Timelonoutelonxcelonption;

import scala.runtimelon.BoxelondUnit;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.comprelonss.utils.Lists;
import org.apachelon.commons.lang.RandomStringUtils;
import org.apachelon.hadoop.fs.FSDataOutputStrelonam;
import org.apachelon.hadoop.fs.FilelonStatus;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.FlushVelonrsion;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.elonarlybird.common.NonPagingAsselonrt;
import com.twittelonr.selonarch.elonarlybird.util.ActionLoggelonr;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionIntelonrfacelon;
import com.twittelonr.selonarch.elonarlybird.util.CoordinatelondelonarlybirdActionLockFailelond;
import com.twittelonr.selonarch.elonarlybird.util.ParallelonlUtil;

/**
 * Flushelons an elonarlybirdIndelonx to HDFS, so that whelonn elonarlybird starts, it can relonad thelon indelonx from
 * HDFS instelonad of indelonxing from scratch.
 *
 * Thelon path looks likelon:
 * /smf1/rt2/uselonr/selonarch/elonarlybird/loadtelonst/relonaltimelon/indelonxelons/flush_velonrsion_158/partition_8/indelonx_2020_02_25_02
 */
public class elonarlybirdIndelonxFlushelonr {
  public elonnum FlushAttelonmptRelonsult {
    CHelonCKelonD_RelonCelonNTLY,
    FOUND_INDelonX,
    FLUSH_ATTelonMPT_MADelon,
    FAILelonD_LOCK_ATTelonMPT,
    HADOOP_TIMelonOUT
  }

  @FunctionalIntelonrfacelon
  public intelonrfacelon PostFlushOpelonration {
    /**
     * Run this aftelonr welon finish flushing an indelonx, belonforelon welon relonjoin thelon selonrvelonrselont.
     */
    void elonxeloncutelon();
  }

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdIndelonxFlushelonr.class);

  privatelon static final SelonarchCountelonr FLUSH_SUCCelonSS_COUNTelonR =
      SelonarchCountelonr.elonxport("succelonssfully_flushelond_indelonx");

  public static final String TWelonelonT_KAFKA_OFFSelonT = "twelonelont_kafka_offselont";
  public static final String UPDATelon_KAFKA_OFFSelonT = "updatelon_kafka_offselont";
  public static final String FLUSHelonD_FROM_RelonPLICA = "flushelond_from_relonplica";
  public static final String SelonGMelonNTS = "selongmelonnts";
  public static final String TIMelonSLICelon_ID = "timelonslicelon_id";

  public static final String DATA_SUFFIX = ".data";
  public static final String INFO_SUFFIX = ".info";
  public static final String INDelonX_INFO = "elonarlybird_indelonx.info";

  privatelon static final String INDelonX_PATH_FORMAT = "%s/flush_velonrsion_%d/partition_%d";
  public static final DatelonFormat INDelonX_DATelon_SUFFIX = nelonw SimplelonDatelonFormat("yyyy_MM_dd_HH");
  public static final String INDelonX_PRelonFIX = "indelonx_";
  public static final String TMP_PRelonFIX = "tmp_";

  // Chelonck if welon nelonelond to flush elonvelonry fivelon minutelons.
  privatelon static final long FLUSH_CHelonCK_PelonRIOD = Duration.ofMinutelons(5).toMillis();

  // Makelon surelon welon don't kelonelonp morelon than 3 copielons of thelon indelonx in HDFS, so that welon don't run out of
  // HDFS spacelon.
  privatelon static final int INDelonX_COPIelonS = 3;

  privatelon static final NonPagingAsselonrt FLUSHING_TOO_MANY_NON_OPTIMIZelonD_SelonGMelonNTS =
          nelonw NonPagingAsselonrt("flushing_too_many_non_optimizelond_selongmelonnts");

  privatelon final CoordinatelondelonarlybirdActionIntelonrfacelon actionCoordinator;
  privatelon final FilelonSystelonm filelonSystelonm;
  privatelon final Path indelonxPath;
  privatelon final Clock clock;
  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final int relonplicaId;
  privatelon final TimelonLimitelondHadoopelonxistsCall timelonLimitelondHadoopelonxistsCall;
  privatelon final OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock;

  privatelon long chelonckelondAt = 0;

  public elonarlybirdIndelonxFlushelonr(
      CoordinatelondelonarlybirdActionIntelonrfacelon actionCoordinator,
      FilelonSystelonm filelonSystelonm,
      String indelonxHDFSPath,
      SelongmelonntManagelonr selongmelonntManagelonr,
      PartitionConfig partitionConfig,
      Clock clock,
      TimelonLimitelondHadoopelonxistsCall timelonLimitelondHadoopelonxistsCall,
      OptimizationAndFlushingCoordinationLock optimizationAndFlushingCoordinationLock
  ) {
    this.actionCoordinator = actionCoordinator;
    this.filelonSystelonm = filelonSystelonm;
    this.indelonxPath = buildPathToIndelonxelons(indelonxHDFSPath, partitionConfig);
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.clock = clock;
    this.relonplicaId = partitionConfig.gelontHostPositionWithinHashPartition();
    this.timelonLimitelondHadoopelonxistsCall = timelonLimitelondHadoopelonxistsCall;
    this.optimizationAndFlushingCoordinationLock = optimizationAndFlushingCoordinationLock;
  }

  /**
   * Pelonriodically cheloncks if an indelonx nelonelonds to belon uploadelond to HDFS, and uploads it if neloncelonssary.
   * Skips flush if unablelon to acquirelon thelon optimizationAndFlushingCoordinationLock.
   */
  public FlushAttelonmptRelonsult flushIfNeloncelonssary(
      long twelonelontOffselont,
      long updatelonOffselont,
      PostFlushOpelonration postFlushOpelonration) throws elonxcelonption {
    long now = clock.nowMillis();
    if (now - chelonckelondAt < FLUSH_CHelonCK_PelonRIOD) {
      relonturn FlushAttelonmptRelonsult.CHelonCKelonD_RelonCelonNTLY;
    }

    chelonckelondAt = now;

    // Try to aqcuirelon lock to elonnsurelon that welon arelon not in thelon gc_belonforelon_optimization or thelon
    // post_optimization_relonbuilds stelonp of optimization. If thelon lock is not availablelon, thelonn skip
    // flushing.
    if (!optimizationAndFlushingCoordinationLock.tryLock()) {
      relonturn FlushAttelonmptRelonsult.FAILelonD_LOCK_ATTelonMPT;
    }
    // Acquirelond thelon lock, so wrap thelon flush in a try/finally block to elonnsurelon welon relonlelonaselon thelon lock
    try {
      Path flushPath = pathForHour();

      try {
        // If this doelonsn't elonxeloncutelon on timelon, it will throw an elonxcelonption and this function
        // finishelons its elonxeloncution.
        boolelonan relonsult = timelonLimitelondHadoopelonxistsCall.elonxists(flushPath);

        if (relonsult) {
          relonturn FlushAttelonmptRelonsult.FOUND_INDelonX;
        }
      } catch (Timelonoutelonxcelonption elon) {
        LOG.warn("Timelonout whilelon calling hadoop", elon);
        relonturn FlushAttelonmptRelonsult.HADOOP_TIMelonOUT;
      }

      boolelonan flushelondIndelonx = falselon;
      try {
        // this function relonturns a boolelonan.
        actionCoordinator.elonxeloncutelon("indelonx_flushing", isCoordinatelond ->
            flushIndelonx(flushPath, isCoordinatelond, twelonelontOffselont, updatelonOffselont, postFlushOpelonration));
        flushelondIndelonx = truelon;
      } catch (CoordinatelondelonarlybirdActionLockFailelond elon) {
        // This only happelonns whelonn welon fail to grab thelon lock, which is finelon beloncauselon anothelonr elonarlybird
        // is alrelonady working on flushing this indelonx, so welon don't nelonelond to.
        LOG.delonbug("Failelond to grab lock", elon);
      }

      if (flushelondIndelonx) {
        // Welon don't relonturn with a guarantelonelon that welon actually flushelond somelonthing. It's possiblelon
        // that thelon .elonxeloncutelon() function abovelon was not ablelon to lelonavelon thelon selonrvelonr selont to flush.
        relonturn FlushAttelonmptRelonsult.FLUSH_ATTelonMPT_MADelon;
      } elonlselon {
        relonturn FlushAttelonmptRelonsult.FAILelonD_LOCK_ATTelonMPT;
      }
    } finally {
      optimizationAndFlushingCoordinationLock.unlock();
    }
  }

  /**
   * Crelonatelon a subpath to thelon direlonctory with many indelonxelons in it. Will havelon an indelonx for elonach hour.
   */
  public static Path buildPathToIndelonxelons(String root, PartitionConfig partitionConfig) {
    relonturn nelonw Path(String.format(
        INDelonX_PATH_FORMAT,
        root,
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.gelontVelonrsionNumbelonr(),
        partitionConfig.gelontIndelonxingHashPartitionID()));
  }


  /**
   * Relonturns a sortelond map from thelon unix timelon in millis an indelonx was flushelond to thelon path of an indelonx.
   * Thelon last elonlelonmelonnt will belon thelon path of thelon most reloncelonnt indelonx.
   */
  public static SortelondMap<Long, Path> gelontIndelonxPathsByTimelon(
      Path indelonxPath,
      FilelonSystelonm filelonSystelonm
  ) throws IOelonxcelonption, Parselonelonxcelonption {
    LOG.info("Gelontting indelonx paths from filelon systelonm: {}", filelonSystelonm.gelontUri().toASCIIString());

    SortelondMap<Long, Path> pathByTimelon = nelonw TrelonelonMap<>();
    Path globPattelonrn = indelonxPath.suffix("/" + elonarlybirdIndelonxFlushelonr.INDelonX_PRelonFIX + "*");
    LOG.info("Lookup glob pattelonrn: {}", globPattelonrn);

    for (FilelonStatus indelonxDir : filelonSystelonm.globStatus(globPattelonrn)) {
      String namelon = nelonw Filelon(indelonxDir.gelontPath().toString()).gelontNamelon();
      String datelonString = namelon.substring(elonarlybirdIndelonxFlushelonr.INDelonX_PRelonFIX.lelonngth());
      Datelon datelon = elonarlybirdIndelonxFlushelonr.INDelonX_DATelon_SUFFIX.parselon(datelonString);
      pathByTimelon.put(datelon.gelontTimelon(), indelonxDir.gelontPath());
    }
    LOG.info("Found {} filelons matching thelon pattelonrn.", pathByTimelon.sizelon());

    relonturn pathByTimelon;
  }

  privatelon boolelonan flushIndelonx(
      Path flushPath,
      boolelonan isCoordinatelond,
      long twelonelontOffselont,
      long updatelonOffselont,
      PostFlushOpelonration postFlushOpelonration
  ) throws elonxcelonption {
    Prelonconditions.chelonckStatelon(isCoordinatelond);

    if (filelonSystelonm.elonxists(flushPath)) {
      relonturn falselon;
    }

    LOG.info("Starting indelonx flush");

    // In caselon thelon procelonss is killelond suddelonnly, welon wouldn't belon ablelon to clelonan up thelon telonmporary
    // direlonctory, and welon don't want othelonr procelonsselons to relonuselon it, so add somelon randomnelonss.
    Path tmpPath = indelonxPath.suffix("/" + TMP_PRelonFIX + RandomStringUtils.randomAlphabelontic(8));
    boolelonan crelonationSuccelonelond = filelonSystelonm.mkdirs(tmpPath);
    if (!crelonationSuccelonelond) {
      throw nelonw IOelonxcelonption("Couldn't crelonatelon HDFS direlonctory at " + flushPath);
    }

    LOG.info("Telonmp path: {}", tmpPath);
    try {
      ArrayList<SelongmelonntInfo> selongmelonntInfos = Lists.nelonwArrayList(selongmelonntManagelonr.gelontSelongmelonntInfos(
          SelongmelonntManagelonr.Filtelonr.elonnablelond, SelongmelonntManagelonr.Ordelonr.NelonW_TO_OLD).itelonrator());
      selongmelonntManagelonr.logStatelon("Belonforelon flushing");
      elonarlybirdIndelonx indelonx = nelonw elonarlybirdIndelonx(selongmelonntInfos, twelonelontOffselont, updatelonOffselont);
      ActionLoggelonr.run(
          "Flushing indelonx to " + tmpPath,
          () -> flushIndelonx(tmpPath, indelonx));
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonxcelonption whilelon flushing indelonx. Relonthrowing.");

      if (filelonSystelonm.delonlelontelon(tmpPath, truelon)) {
        LOG.info("Succelonssfully delonlelontelond telonmp output");
      } elonlselon {
        LOG.elonrror("Couldn't delonlelontelon telonmp output");
      }

      throw elon;
    }

    // Welon flush it to a telonmporary direlonctory, thelonn relonnamelon thelon telonmporary direlonctory so that it thelon
    // changelon is atomic, and othelonr elonarlybirds will elonithelonr selonelon thelon old indelonxelons, or thelon nelonw, complelontelon
    // indelonx, but nelonvelonr an in progrelonss indelonx.
    boolelonan relonnamelonSuccelonelondelond = filelonSystelonm.relonnamelon(tmpPath, flushPath);
    if (!relonnamelonSuccelonelondelond) {
      throw nelonw IOelonxcelonption("Couldn't relonnamelon HDFS from " + tmpPath + " to " + flushPath);
    }
    LOG.info("Flushelond indelonx to {}", flushPath);

    clelonanupOldIndelonxelons();

    FLUSH_SUCCelonSS_COUNTelonR.increlonmelonnt();

    LOG.info("elonxeloncuting post flush opelonration...");
    postFlushOpelonration.elonxeloncutelon();

    relonturn truelon;
  }

  privatelon void clelonanupOldIndelonxelons() throws elonxcelonption {
    LOG.info("Looking up whelonthelonr welon nelonelond to clelonan up old indelonxelons...");
    SortelondMap<Long, Path> pathsByTimelon =
        elonarlybirdIndelonxFlushelonr.gelontIndelonxPathsByTimelon(indelonxPath, filelonSystelonm);

    whilelon (pathsByTimelon.sizelon() > INDelonX_COPIelonS) {
      Long kelony = pathsByTimelon.firstKelony();
      Path oldelonstHourPath = pathsByTimelon.relonmovelon(kelony);
      LOG.info("Delonlelonting old indelonx at path '{}'.", oldelonstHourPath);

      if (filelonSystelonm.delonlelontelon(oldelonstHourPath, truelon)) {
        LOG.info("Succelonssfully delonlelontelond old indelonx");
      } elonlselon {
        LOG.elonrror("Couldn't delonlelontelon old indelonx");
      }
    }
  }

  privatelon Path pathForHour() {
    Datelon datelon = nelonw Datelon(clock.nowMillis());
    String timelon = INDelonX_DATelon_SUFFIX.format(datelon);
    relonturn indelonxPath.suffix("/" + INDelonX_PRelonFIX + timelon);
  }

  privatelon void flushIndelonx(Path flushPath, elonarlybirdIndelonx indelonx) throws elonxcelonption {
    int numOfNonOptimizelond = indelonx.numOfNonOptimizelondSelongmelonnts();
    if (numOfNonOptimizelond > elonarlybirdIndelonx.MAX_NUM_OF_NON_OPTIMIZelonD_SelonGMelonNTS) {
      LOG.elonrror(
              "Found {} non-optimizelond selongmelonnts whelonn flushing to disk!", numOfNonOptimizelond);
      FLUSHING_TOO_MANY_NON_OPTIMIZelonD_SelonGMelonNTS.asselonrtFailelond();
    }

    int numSelongmelonnts = indelonx.gelontSelongmelonntInfoList().sizelon();
    int flushingThrelonadPoolSizelon = numSelongmelonnts;

    if (Config.elonnvironmelonntIsTelonst()) {
      // SelonARCH-33763: Limit thelon threlonad pool sizelon for telonsts to avoid using too much melonmory on scoot.
      flushingThrelonadPoolSizelon = 2;
    }

    LOG.info("Flushing indelonx using a threlonad pool sizelon of {}", flushingThrelonadPoolSizelon);

    ParallelonlUtil.parmap("flush-indelonx", flushingThrelonadPoolSizelon, si -> ActionLoggelonr.call(
        "Flushing selongmelonnt " + si.gelontSelongmelonntNamelon(),
        () -> flushSelongmelonnt(flushPath, si)), indelonx.gelontSelongmelonntInfoList());

    FlushInfo indelonxInfo = nelonw FlushInfo();
    indelonxInfo.addLongPropelonrty(UPDATelon_KAFKA_OFFSelonT, indelonx.gelontUpdatelonOffselont());
    indelonxInfo.addLongPropelonrty(TWelonelonT_KAFKA_OFFSelonT, indelonx.gelontTwelonelontOffselont());
    indelonxInfo.addIntPropelonrty(FLUSHelonD_FROM_RelonPLICA, relonplicaId);

    FlushInfo selongmelonntFlushInfos = indelonxInfo.nelonwSubPropelonrtielons(SelonGMelonNTS);
    for (SelongmelonntInfo selongmelonntInfo : indelonx.gelontSelongmelonntInfoList()) {
      FlushInfo selongmelonntFlushInfo = selongmelonntFlushInfos.nelonwSubPropelonrtielons(selongmelonntInfo.gelontSelongmelonntNamelon());
      selongmelonntFlushInfo.addLongPropelonrty(TIMelonSLICelon_ID, selongmelonntInfo.gelontTimelonSlicelonID());
    }

    Path indelonxInfoPath = flushPath.suffix("/" + INDelonX_INFO);
    try (FSDataOutputStrelonam infoOutputStrelonam = filelonSystelonm.crelonatelon(indelonxInfoPath)) {
      OutputStrelonamWritelonr infoFilelonWritelonr = nelonw OutputStrelonamWritelonr(infoOutputStrelonam);
      FlushInfo.flushAsYaml(indelonxInfo, infoFilelonWritelonr);
    }
  }

  privatelon BoxelondUnit flushSelongmelonnt(Path flushPath, SelongmelonntInfo selongmelonntInfo) throws elonxcelonption {
    Path selongmelonntPrelonfix = flushPath.suffix("/" + selongmelonntInfo.gelontSelongmelonntNamelon());
    Path selongmelonntPath = selongmelonntPrelonfix.suffix(DATA_SUFFIX);

    FlushInfo flushInfo = nelonw FlushInfo();

    try (FSDataOutputStrelonam outputStrelonam = filelonSystelonm.crelonatelon(selongmelonntPath)) {
      DataSelonrializelonr out = nelonw DataSelonrializelonr(selongmelonntPath.toString(), outputStrelonam);
      selongmelonntInfo.gelontIndelonxSelongmelonnt().flush(flushInfo, out);
    }

    Path infoPath = selongmelonntPrelonfix.suffix(INFO_SUFFIX);

    try (FSDataOutputStrelonam infoOutputStrelonam = filelonSystelonm.crelonatelon(infoPath)) {
      OutputStrelonamWritelonr infoFilelonWritelonr = nelonw OutputStrelonamWritelonr(infoOutputStrelonam);
      FlushInfo.flushAsYaml(flushInfo, infoFilelonWritelonr);
    }
    relonturn BoxelondUnit.UNIT;
  }
}
