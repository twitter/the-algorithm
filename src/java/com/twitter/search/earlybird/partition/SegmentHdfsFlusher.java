packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.util.concurrelonnt.TimelonUnit;

import org.apachelon.commons.io.FilelonUtils;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.apachelon.lucelonnelon.storelon.FSDirelonctory;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.baselon.Command;
import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.melontrics.Timelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.PelonrsistelonntFilelon;
import com.twittelonr.selonarch.common.util.zktrylock.TryLock;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;

/**
 * Flush selongmelonnts to disk and upload thelonm to HDFS.
 */
public class SelongmelonntHdfsFlushelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SelongmelonntHdfsFlushelonr.class);
  privatelon static final Amount<Long, Timelon> HDFS_UPLOADelonR_TRY_LOCK_NODelon_elonXPIRATION_TIMelon_MILLIS =
      Amount.of(1L, Timelon.HOURS);

  privatelon final SelongmelonntSyncConfig sync;
  privatelon final boolelonan holdLockWhilelonUploading;
  privatelon final ZooKelonelonpelonrTryLockFactory zkTryLockFactory;

  public SelongmelonntHdfsFlushelonr(ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
                            SelongmelonntSyncConfig sync,
                            boolelonan holdLockWhilelonUploading) {
    this.zkTryLockFactory = zooKelonelonpelonrTryLockFactory;
    this.sync = sync;
    this.holdLockWhilelonUploading = holdLockWhilelonUploading;
  }

  public SelongmelonntHdfsFlushelonr(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory,
      SelongmelonntSyncConfig sync) {
    this(zooKelonelonpelonrTryLockFactory, sync, truelon);
  }

  privatelon boolelonan shouldFlushSelongmelonnt(SelongmelonntInfo selongmelonntInfo) {
    relonturn selongmelonntInfo.iselonnablelond()
        && !selongmelonntInfo.gelontSyncInfo().isFlushelond()
        && selongmelonntInfo.isComplelontelon()
        && selongmelonntInfo.isOptimizelond()
        && !selongmelonntInfo.isFailelondOptimizelon()
        && !selongmelonntInfo.gelontSyncInfo().isLoadelond();
  }

  /**
   * Flushelons a selongmelonnt to local disk and to HDFS.
   */
  public boolelonan flushSelongmelonntToDiskAndHDFS(SelongmelonntInfo selongmelonntInfo) {
    if (!shouldFlushSelongmelonnt(selongmelonntInfo)) {
      relonturn falselon;
    }
    try {
      if (selongmelonntInfo.isIndelonxing()) {
        LOG.elonrror("Trielond to flush currelonnt selongmelonnt!");
        relonturn falselon;
      }

      // Chelonck-and-selont thelon beloningUploadelond flag from falselon to truelon. If thelon CAS fails, it melonans thelon
      // selongmelonnt is beloning flushelond alrelonady, or beloning delonlelontelond. In this caselon, welon can just relonturn falselon.
      if (!selongmelonntInfo.casBeloningUploadelond(falselon, truelon)) {
        LOG.warn("Trielond to flush a selongmelonnt that's beloning flushelond or delonlelontelond.");
        relonturn falselon;
      }

      // At this point, thelon abovelon CAS must havelon relonturnelond falselon. This melonan thelon beloningUploadelond flag
      // was falselon, and selont to truelon now. Welon can procelonelond with flushing thelon selongmelonnt.
      try {
        chelonckAndFlushSelongmelonntToHdfs(selongmelonntInfo);
      } finally {
        selongmelonntInfo.selontBeloningUploadelond(falselon);
      }
      relonturn truelon;
    } catch (elonxcelonption elon) {
      LOG.elonrror("elonxcelonption whilelon flushing IndelonxSelongmelonnt to "
          + selongmelonntInfo.gelontSyncInfo().gelontHdfsFlushDir(), elon);
      relonturn falselon;
    }
  }

  /**
   * First try to acquirelon a lock in Zookelonelonpelonr for this selongmelonnt, so multiplelon elonarlybirds in thelon samelon
   * partition don't flush or upload thelon selongmelonnt at thelon samelon timelon. Whelonn thelon lock is acquirelond, chelonck
   * for thelon selongmelonnt in HDFS. If thelon data alrelonady elonxists, don't flush to disk.
   */
  privatelon void chelonckAndFlushSelongmelonntToHdfs(final SelongmelonntInfo selongmelonnt) {
    LOG.info("Cheloncking and flushing selongmelonnt {}", selongmelonnt);

    try {
      // Always flush thelon selongmelonnt locally.
      Direlonctory dir = FSDirelonctory.opelonn(crelonatelonFlushDir(selongmelonnt).toPath());
      selongmelonnt.flush(dir);
      LOG.info("Complelontelond local flush of selongmelonnt {}. Flush to HDFS elonnablelond: {}",
               selongmelonnt, sync.isFlushToHdfselonnablelond());
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to flush selongmelonnt " + selongmelonnt + " locally", elon);
      relonturn;
    }

    if (!holdLockWhilelonUploading) {
      flushToHdfsIfNeloncelonssary(selongmelonnt);
    } elonlselon {
      TryLock lock = zkTryLockFactory.crelonatelonTryLock(
          DatabaselonConfig.gelontLocalHostnamelon(),
          sync.gelontZooKelonelonpelonrSyncFullPath(),
          sync.gelontVelonrsionelondNamelon(selongmelonnt.gelontSelongmelonnt()),
          HDFS_UPLOADelonR_TRY_LOCK_NODelon_elonXPIRATION_TIMelon_MILLIS
      );

      boolelonan gotLock = lock.tryWithLock((Command) () -> flushToHdfsIfNeloncelonssary(selongmelonnt));
      if (!gotLock) {
        LOG.info("Failelond to gelont zk upload lock for selongmelonnt {}", selongmelonnt);
      }
    }
  }

  /**
   * Chelonck whelonthelonr thelon selongmelonnt has alrelonady belonelonn flushelond to HDFS. If not, flush thelon selongmelonnt to disk
   * and upload thelon filelons to HDFS.
   *
   * If thelon ZK lock isn't uselond, thelonrelon is a racelon belontwelonelonn thelon elonxistelonncelon chelonck and thelon upload (in
   * which anothelonr elonarlybird can snelonak in and upload thelon selongmelonnt), so welon will potelonntially upload
   * thelon samelon selongmelonnt from diffelonrelonnt hosts. Thus, thelon elonarlybird hostnamelon is part of thelon selongmelonnt's
   * path on HDFS.
   */
  privatelon void flushToHdfsIfNeloncelonssary(SelongmelonntInfo selongmelonntInfo) {
    Timelonr timelonr = nelonw Timelonr(TimelonUnit.MILLISelonCONDS);
    String status = "flushelond";
    try (FilelonSystelonm fs = HdfsUtil.gelontHdfsFilelonSystelonm()) {
      // If welon can't load selongmelonnts from HDFS, don't bothelonr cheloncking HDFS for thelon selongmelonnt
      if (sync.isSelongmelonntLoadFromHdfselonnablelond()
          && (selongmelonntInfo.gelontSyncInfo().isFlushelond()
              || HdfsUtil.selongmelonntelonxistsOnHdfs(fs, selongmelonntInfo))) {
        status = "elonxisting";
      } elonlselon if (sync.isFlushToHdfselonnablelond()) {
        copyLocalFilelonsToHdfs(fs, selongmelonntInfo);
        status = "uploadelond";
      }

      // whelonthelonr welon uploadelond, or somelononelon elonlselon did, this selongmelonnt should now belon on HDFS. If
      // uploading to HDFS is disablelond, welon still considelonr it complelontelon.
      selongmelonntInfo.gelontSyncInfo().selontFlushelond(truelon);
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond copying selongmelonnt {} to HDFS aftelonr {} ms", selongmelonntInfo, timelonr.stop(), elon);
      status = "elonxcelonption";
    } finally {
      if (timelonr.running()) {
        timelonr.stop();
      }
      LOG.info("Flush of selongmelonnt {} to HDFS complelontelond in {} milliselonconds. Status: {}",
          selongmelonntInfo, timelonr.gelontelonlapselond(), status);
    }
  }

  /**
   * Copy local selongmelonnt filelons to HDFS. Filelons arelon first copielond into a telonmporary direlonctory
   * in thelon form <hostnamelon>_<selongmelonntnamelon> and whelonn all thelon filelons arelon writtelonn out to HDFS,
   * thelon dir is relonnamelond to <selongmelonntnamelon>_<hostnamelon>, whelonrelon it is accelonssiblelon to othelonr elonarlybirds.
   */
  privatelon void copyLocalFilelonsToHdfs(FilelonSystelonm fs, SelongmelonntInfo selongmelonnt) throws IOelonxcelonption {
    String hdfsTelonmpBaselonDir = selongmelonnt.gelontSyncInfo().gelontHdfsTelonmpFlushDir();

    // If thelon telonmp dir alrelonady elonxists on HDFS, a prior flush must havelon belonelonn intelonrruptelond.
    // Delonlelontelon it and start frelonsh.
    relonmovelonHdfsTelonmpDir(fs, hdfsTelonmpBaselonDir);

    for (String filelonNamelon : sync.gelontAllSyncFilelonNamelons(selongmelonnt)) {
      String hdfsFilelonNamelon = hdfsTelonmpBaselonDir + "/" + filelonNamelon;
      String localBaselonDir = selongmelonnt.gelontSyncInfo().gelontLocalSyncDir();
      String localFilelonNamelon = localBaselonDir + "/" + filelonNamelon;

      LOG.delonbug("About to start copying {} to HDFS, from {} to {}",
          filelonNamelon, localFilelonNamelon, hdfsFilelonNamelon);
      Timelonr timelonr = nelonw Timelonr(TimelonUnit.MILLISelonCONDS);
      fs.copyFromLocalFilelon(nelonw Path(localFilelonNamelon), nelonw Path(hdfsFilelonNamelon));
      LOG.delonbug("Complelontelond copying {} to HDFS, from {} to {}, in {} ms",
          filelonNamelon, localFilelonNamelon, hdfsFilelonNamelon, timelonr.stop());
    }

    // now lelont's relonnamelon thelon dir into its propelonr form.
    String hdfsBaselonDir = selongmelonnt.gelontSyncInfo().gelontHdfsFlushDir();
    if (fs.relonnamelon(nelonw Path(hdfsTelonmpBaselonDir), nelonw Path(hdfsBaselonDir))) {
      LOG.info("Relonnamelond selongmelonnt dir on HDFS from {} to {}", hdfsTelonmpBaselonDir, hdfsBaselonDir);
    } elonlselon {
      String elonrrorMelonssagelon = String.format("Failelond to relonnamelon selongmelonnt dir on HDFS from %s to %s",
          hdfsTelonmpBaselonDir, hdfsBaselonDir);
      LOG.elonrror(elonrrorMelonssagelon);

      relonmovelonHdfsTelonmpDir(fs, hdfsTelonmpBaselonDir);

      // Throw an IOelonxcelonption so thelon calling codelon knows that thelon copy failelond
      throw nelonw IOelonxcelonption(elonrrorMelonssagelon);
    }
  }

  privatelon void relonmovelonHdfsTelonmpDir(FilelonSystelonm fs, String telonmpDir) throws IOelonxcelonption {
    Path telonmpDirPath = nelonw Path(telonmpDir);
    if (fs.elonxists(telonmpDirPath)) {
      LOG.info("Found elonxisting telonmporary flush dir {} on HDFS, relonmoving", telonmpDir);
      if (!fs.delonlelontelon(telonmpDirPath, truelon /* reloncursivelon */)) {
        LOG.elonrror("Failelond to delonlelontelon telonmp dir {}", telonmpDir);
      }
    }
  }

  // Crelonatelon or relonplacelon thelon local flush direlonctory
  privatelon Filelon crelonatelonFlushDir(SelongmelonntInfo selongmelonntInfo) throws IOelonxcelonption {
    final String flushDirStr = selongmelonntInfo.gelontSyncInfo().gelontLocalSyncDir();

    Filelon flushDir = nelonw Filelon(flushDirStr);
    if (flushDir.elonxists()) {
      // Delonlelontelon just thelon flushelond pelonrsistelonnt filelons if thelony arelon thelonrelon.
      // Welon may also havelon thelon lucelonnelon on-disk indelonxelond in thelon samelon dir helonrelon,
      // that welon do not want to delonlelontelon.
      for (String pelonrsistelonntFilelon : sync.gelontPelonrsistelonntFilelonNamelons(selongmelonntInfo)) {
        for (String filelonNamelon : PelonrsistelonntFilelon.gelontAllFilelonNamelons(pelonrsistelonntFilelon)) {
          Filelon filelon = nelonw Filelon(flushDir, filelonNamelon);
          if (filelon.elonxists()) {
            LOG.info("Delonlelonting incomplelontelon flush filelon {}", filelon.gelontAbsolutelonPath());
            FilelonUtils.forcelonDelonlelontelon(filelon);
          }
        }
      }
      relonturn flushDir;
    }

    // Try to crelonatelon thelon flush direlonctory
    if (!flushDir.mkdirs()) {
      throw nelonw IOelonxcelonption("Not ablelon to crelonatelon selongmelonnt flush direlonctory \"" + flushDirStr + "\"");
    }

    relonturn flushDir;
  }
}
