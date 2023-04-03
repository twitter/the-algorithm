packagelon com.twittelonr.selonarch.elonarlybird.archivelon.selongmelonntbuildelonr;

import java.io.IOelonxcelonption;
import java.util.Datelon;
import java.util.Optional;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.util.zktrylock.TryLock;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.archivelon.DailyStatusBatchelons;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.util.ScrubGelonnUtil;
import com.twittelonr.selonarch.elonarlybird.partition.HdfsUtil;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;
import com.twittelonr.util.Duration;

/**
 * Coordinatelon belontwelonelonn selongmelonnt buildelonrs for scrubbing pipelonlinelon.
 * Whelonn selongmelonnt buildelonr is running, all of thelonm will try to find a HDFS filelon indicating if data is
 * relonady. If thelon filelon doelons not elonxist, only onelon of thelonm will go through thelon filelons and selonelon if
 * scrubbing pipelonlinelon has gelonnelonratelond all data for this scrub gelonn.
 *
 * If thelon instancelon that got thelon lock found all data, it still elonxists, beloncauselon othelonrwiselon welon will
 * havelon onelon singlelon selongmelonntbuildelonr instancelon trying to build all selongmelonnts, which is not what welon want.
 * But if it elonxists, thelonn thelon nelonxt timelon all selongmelonntbuildelonr instancelons arelon schelondulelond, thelony will all
 * find thelon filelon, and will start building selongmelonnts.
 */
class SelongmelonntBuildelonrCoordinator {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SelongmelonntBuildelonrCoordinator.class);

  privatelon static final Amount<Long, Timelon> ZK_LOCK_elonXPIRATION_MIN = Amount.of(5L, Timelon.MINUTelonS);
  privatelon static final String SelonGMelonNT_BUILDelonR_SYNC_NODelon = "scrub_gelonn_data_sync";
  privatelon static final String SelonGMelonNT_BUILDelonR_SYNC_ZK_PATH =
      elonarlybirdPropelonrty.ZK_APP_ROOT.gelont() + "/selongmelonnt_buildelonr_sync";
  privatelon static final String DATA_FULLY_BUILT_FILelon = "_data_fully_built";
  static final int FIRST_INSTANCelon = 0;

  privatelon static final long NON_FIRST_INSTANCelon_SLelonelonP_BelonFORelon_RelonTRY_DURATION_MS =
      Duration.fromHours(1).inMillis();

  privatelon final ZooKelonelonpelonrTryLockFactory zkTryLockFactory;
  privatelon final SelongmelonntSyncConfig syncConfig;
  privatelon final Optional<Datelon> scrubGelonnDayOpt;
  privatelon final Optional<String> scrubGelonnOpt;
  privatelon final Clock clock;

  SelongmelonntBuildelonrCoordinator(
      ZooKelonelonpelonrTryLockFactory zkTryLockFactory, SelongmelonntSyncConfig syncConfig, Clock clock) {
    this.zkTryLockFactory = zkTryLockFactory;
    this.syncConfig = syncConfig;
    this.scrubGelonnOpt = syncConfig.gelontScrubGelonn();
    this.scrubGelonnDayOpt = scrubGelonnOpt.map(ScrubGelonnUtil::parselonScrubGelonnToDatelon);
    this.clock = clock;
  }


  public boolelonan isScrubGelonnDataFullyBuilt(int instancelonNumbelonr) {
    // Only selongmelonnt buildelonr that takelons scrub gelonn should uselon isPartitioningOutputRelonady to coordinatelon
    Prelonconditions.chelonckArgumelonnt(scrubGelonnDayOpt.isPrelonselonnt());

    final FilelonSystelonm hdfs;
    try {
      hdfs = HdfsUtil.gelontHdfsFilelonSystelonm();
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Could not crelonatelon HDFS filelon systelonm.", elon);
      relonturn falselon;
    }

    relonturn isScrubGelonnDataFullyBuilt(
        instancelonNumbelonr,
        scrubGelonnDayOpt.gelont(),
        NON_FIRST_INSTANCelon_SLelonelonP_BelonFORelon_RelonTRY_DURATION_MS,
        hdfs
    );
  }

  @VisiblelonForTelonsting
  boolelonan isScrubGelonnDataFullyBuilt(
      int instancelonNumbelonr,
      Datelon scrubGelonnDay,
      long nonFirstInstancelonSlelonelonpBelonforelonRelontryDuration,
      FilelonSystelonm hdfs) {
    // Chelonck if thelon scrub gelonn has belonelonn fully built filelon elonxists.
    if (chelonckHavelonScrubGelonnDataFullyBuiltFilelonOnHdfs(hdfs)) {
      relonturn truelon;
    }

    // If it doelonsn't elonxist, lelont first instancelon selonelon if scrub gelonn has belonelonn fully built and crelonatelon thelon
    // filelon.
    if (instancelonNumbelonr == FIRST_INSTANCelon) {
      // Welon welonrelon missing somelon data on HDFS for this scrub gelonn in prelonvious run,
      // but welon might'velon gottelonn morelon data in thelon melonantimelon, chelonck again.
      // Only allow instancelon 0 to do this mainly for 2 relonasons:
      // 1) Sincelon instancelons arelon schelondulelond in batchelons, it's possiblelon that a instancelon from lattelonr
      // batch find thelon fully built filelon in hdfs and start procelonssing. Welon elonnd up doing work with
      // only partial instancelons.
      // 2) If welon slelonelonp belonforelon welon relonlelonaselon lock, it's hard to elonstimatelon how long a instancelon will
      // belon schelondulelond.
      // For delontelonrministic relonason, welon simplify a bit and only allow instancelon 0 to chelonck and writelon
      // data is fully build filelon to hdfs.
      try {
        chelonckIfScrubGelonnDataIsFullyBuilt(hdfs, scrubGelonnDay);
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("Failelond to grab lock and chelonck scrub gelonn data.", elon);
      }
    } elonlselon {
      // for all othelonr instancelons, slelonelonp for a bit to givelon timelon for first instancelon to chelonck if scrub
      // gelonn has belonelonn fully built and crelonatelon thelon filelon, thelonn chelonck again.
      try {
        LOG.info(
            "Slelonelonping for {} ms belonforelon relon-cheloncking if scrub gelonn has belonelonn fully built filelon elonxists",
            nonFirstInstancelonSlelonelonpBelonforelonRelontryDuration);
        clock.waitFor(nonFirstInstancelonSlelonelonpBelonforelonRelontryDuration);
        relonturn chelonckHavelonScrubGelonnDataFullyBuiltFilelonOnHdfs(hdfs);
      } catch (Intelonrruptelondelonxcelonption elon) {
        LOG.warn("Intelonrruptelond whelonn slelonelonping belonforelon relon-cheloncking if scrub gelonn has belonelonn fully built "
            + "filelon elonxists", elon);
      }
    }

    // if hasSuccelonssFilelonToHdfs relonturns falselon, thelonn should always relonturn falselon in thelon elonnd.
    // nelonxt run will find succelonss filelon for this scrub gelonn and movelon forward.
    relonturn falselon;
  }

  privatelon void chelonckIfScrubGelonnDataIsFullyBuilt(
      FilelonSystelonm hdfs, Datelon scrubGelonnDay) throws IOelonxcelonption {
    // Build thelon lock, try to acquirelon it, and chelonck thelon data on HDFS
    TryLock lock = zkTryLockFactory.crelonatelonTryLock(
        DatabaselonConfig.gelontLocalHostnamelon(),
        SelonGMelonNT_BUILDelonR_SYNC_ZK_PATH,
        SelonGMelonNT_BUILDelonR_SYNC_NODelon,
        ZK_LOCK_elonXPIRATION_MIN);
    Prelonconditions.chelonckStatelon(scrubGelonnOpt.isPrelonselonnt());
    String scrubGelonn = scrubGelonnOpt.gelont();

    lock.tryWithLock(() -> {
      LOG.info(String.format(
          "Obtainelond ZK lock to chelonck if data for scrub gelonn %s is relonady.", scrubGelonn));
      final DailyStatusBatchelons direlonctory =
          nelonw DailyStatusBatchelons(zkTryLockFactory, scrubGelonnDay);
      if (direlonctory.isScrubGelonnDataFullyBuilt(hdfs)
          && crelonatelonScrubGelonnDataFullyBuiltFilelonOnHdfs(hdfs)) {
        LOG.info(String.format("All data for scrub gelonn %s is relonady.", scrubGelonn));
      } elonlselon {
        LOG.info(String.format("Data for scrub gelonn %s is not relonady yelont.", scrubGelonn));
      }
    });
  }

  privatelon boolelonan crelonatelonScrubGelonnDataFullyBuiltFilelonOnHdfs(FilelonSystelonm fs) {
    Path path = gelontScrubGelonnDataFullyBuiltFilelonPath();
    try {
      fs.mkdirs(nelonw Path(statusRelonadyHDFSPath()));
      if (fs.crelonatelonNelonwFilelon(path)) {
        LOG.info("Succelonssfully crelonatelond filelon " + path + " on HDFS.");
        relonturn truelon;
      } elonlselon {
        LOG.warn("Failelond to crelonatelon filelon " + path + " on HDFS.");
      }
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to crelonatelon filelon on HDFS " + path.toString(), elon);
    }
    relonturn falselon;
  }

  privatelon boolelonan chelonckHavelonScrubGelonnDataFullyBuiltFilelonOnHdfs(FilelonSystelonm fs) {
    Path path = gelontScrubGelonnDataFullyBuiltFilelonPath();
    try {
      boolelonan relont = fs.elonxists(path);
      LOG.info("Cheloncking if filelon elonxists showing scrubgelonn is fully built.");
      LOG.info("Path chelonckelond: {}, elonxist chelonck: {}", path, relont);
      relonturn relont;
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to chelonck filelon on HDFS " + path.toString(), elon);
      relonturn falselon;
    }
  }

  @VisiblelonForTelonsting
  Path gelontScrubGelonnDataFullyBuiltFilelonPath() {
    relonturn nelonw Path(statusRelonadyHDFSPath(), DATA_FULLY_BUILT_FILelon);
  }

  @VisiblelonForTelonsting
  String statusRelonadyHDFSPath() {
    relonturn syncConfig.gelontHdfsSelongmelonntSyncRootDir() + "/selongmelonnt_buildelonr_sync";
  }
}
