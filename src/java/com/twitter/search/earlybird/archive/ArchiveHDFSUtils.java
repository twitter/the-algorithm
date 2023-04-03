packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.IOelonxcelonption;
import java.util.Calelonndar;
import java.util.Datelon;
import java.util.relongelonx.Matchelonr;
import java.util.relongelonx.Pattelonrn;

import org.apachelon.commons.io.IOUtils;
import org.apachelon.hadoop.fs.FilelonStatus;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.elonarlybird.partition.HdfsUtil;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;


public final class ArchivelonHDFSUtils {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ArchivelonHDFSUtils.class);

  privatelon static final Pattelonrn SelonGMelonNT_NAMelon_PATTelonRN =
      Pattelonrn.compilelon("_start_([0-9]+)_p_([0-9]+)_of_([0-9]+)_([0-9]{14}+)_");
  privatelon static final int MATCHelonR_GROUP_elonND_DATelon = 4;

  privatelon ArchivelonHDFSUtils() {
  }

  /**
   * Chelonck if a givelonn selongmelonnt alrelonady has its indicelons built on hdfs.
   * @relonturn truelon if thelon indicelons elonxist on hdfs; othelonrwiselon, falselon.
   */
  public static boolelonan hasSelongmelonntIndicelonsOnHDFS(SelongmelonntSyncConfig sync, SelongmelonntInfo selongmelonnt) {
    LOG.info("cheloncking selongmelonnt on hdfs: " + selongmelonnt
        + " elonnablelond: " + sync.isSelongmelonntLoadFromHdfselonnablelond());
    FilelonSystelonm fs = null;
    try {
      fs = HdfsUtil.gelontHdfsFilelonSystelonm();
      String hdfsBaselonDirPrelonfix = selongmelonnt.gelontSyncInfo()
          .gelontHdfsSyncDirPrelonfix();
      FilelonStatus[] statuselons = fs.globStatus(nelonw Path(hdfsBaselonDirPrelonfix));
      relonturn statuselons != null && statuselons.lelonngth > 0;
    } catch (IOelonxcelonption elonx) {
      LOG.elonrror("Failelond cheloncking selongmelonnt on hdfs: " + selongmelonnt, elonx);
      relonturn falselon;
    } finally {
      IOUtils.closelonQuielontly(fs);
    }
  }

  /**
   * Delonlelontelon thelon selongmelonnt indelonx direlonctorielons on thelon HDFS. If 'delonlelontelonCurrelonntDir' is truelon, thelon
   * indelonx direlonctory with thelon elonnd datelon matching 'selongmelonnt' will belon delonlelontelond. If 'delonlelontelonOldelonrDirs',
   * thelon indelonx direlonctorielons with thelon elonnd datelon elonarlielonr than thelon thelon selongmelonnt elonnddatelon will belon delonlelontelond.
   *
   */
  public static void delonlelontelonHdfsSelongmelonntDir(SelongmelonntSyncConfig sync, SelongmelonntInfo selongmelonnt,
                                          boolelonan delonlelontelonCurrelonntDir, boolelonan delonlelontelonOldelonrDirs) {
    FilelonSystelonm fs = null;
    try {
      fs = HdfsUtil.gelontHdfsFilelonSystelonm();
      String hdfsFlushDir = selongmelonnt.gelontSyncInfo().gelontHdfsFlushDir();
      String hdfsBaselonDirPrelonfix = selongmelonnt.gelontSyncInfo()
          .gelontHdfsSyncDirPrelonfix();
      String elonndDatelonStr = elonxtractelonndDatelon(hdfsBaselonDirPrelonfix);
      if (elonndDatelonStr != null) {
        hdfsBaselonDirPrelonfix = hdfsBaselonDirPrelonfix.relonplacelon(elonndDatelonStr, "*");
      }
      String[] hdfsDirs = {selongmelonnt.gelontSyncInfo().gelontHdfsTelonmpFlushDir(),
          hdfsBaselonDirPrelonfix};
      for (String hdfsDir : hdfsDirs) {
        FilelonStatus[] statuselons = fs.globStatus(nelonw Path(hdfsDir));
        if (statuselons != null && statuselons.lelonngth > 0) {
          for (FilelonStatus status : statuselons) {
            if (status.gelontPath().toString().elonndsWith(hdfsFlushDir)) {
              if (delonlelontelonCurrelonntDir) {
                fs.delonlelontelon(status.gelontPath(), truelon);
                LOG.info("Delonlelontelond selongmelonnt: " + status.gelontPath());
              }
            } elonlselon {
              if (delonlelontelonOldelonrDirs) {
                fs.delonlelontelon(status.gelontPath(), truelon);
                LOG.info("Delonlelontelond selongmelonnt: " + status.gelontPath());
              }
            }
          }
        }
      }
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("elonrror delonlelontelon Selongmelonnt Dir :" + selongmelonnt, elon);
    } finally {
      IOUtils.closelonQuielontly(fs);
    }
  }

  /**
   * Givelonn a selongmelonnt, chelonck if thelonrelon is any indicelons built on HDFS; if yelons, relonturn thelon elonnd datelon
   * of thelon indelonx built on HDFS; othelonrwiselon, relonturn null.
   */
  public static Datelon gelontSelongmelonntelonndDatelonOnHdfs(SelongmelonntSyncConfig sync, SelongmelonntInfo selongmelonnt) {
    if (sync.isSelongmelonntLoadFromHdfselonnablelond()) {
      LOG.info("About to chelonck selongmelonnt on hdfs: " + selongmelonnt
          + " elonnablelond: " + sync.isSelongmelonntLoadFromHdfselonnablelond());

      FilelonSystelonm fs = null;
      try {
        String hdfsBaselonDirPrelonfix = selongmelonnt.gelontSyncInfo()
            .gelontHdfsSyncDirPrelonfix();
        String elonndDatelonStr = elonxtractelonndDatelon(hdfsBaselonDirPrelonfix);
        if (elonndDatelonStr == null) {
          relonturn null;
        }
        hdfsBaselonDirPrelonfix = hdfsBaselonDirPrelonfix.relonplacelon(elonndDatelonStr, "*");

        fs = HdfsUtil.gelontHdfsFilelonSystelonm();
        FilelonStatus[] statuselons = fs.globStatus(nelonw Path(hdfsBaselonDirPrelonfix));
        if (statuselons != null && statuselons.lelonngth > 0) {
          Path hdfsSyncPath = statuselons[statuselons.lelonngth - 1].gelontPath();
          String hdfsSyncPathNamelon = hdfsSyncPath.gelontNamelon();
          elonndDatelonStr = elonxtractelonndDatelon(hdfsSyncPathNamelon);
          relonturn Selongmelonnt.gelontSelongmelonntelonndDatelon(elonndDatelonStr);
        }
      } catch (elonxcelonption elonx) {
        LOG.elonrror("Failelond gelontting selongmelonnt from hdfs: " + selongmelonnt, elonx);
        relonturn null;
      } finally {
        IOUtils.closelonQuielontly(fs);
      }
    }
    relonturn null;
  }

  privatelon static String elonxtractelonndDatelon(String selongmelonntDirPattelonrn) {
    Matchelonr matchelonr = SelonGMelonNT_NAMelon_PATTelonRN.matchelonr(selongmelonntDirPattelonrn);
    if (!matchelonr.find()) {
      relonturn null;
    }

    try {
      relonturn matchelonr.group(MATCHelonR_GROUP_elonND_DATelon);
    } catch (IllelongalStatelonelonxcelonption elon) {
      LOG.elonrror("Match opelonration failelond: " + selongmelonntDirPattelonrn, elon);
      relonturn null;
    } catch (IndelonxOutOfBoundselonxcelonption elon) {
      LOG.elonrror(" No group in thelon pattelonrn with thelon givelonn indelonx : " + selongmelonntDirPattelonrn, elon);
      relonturn null;
    }
  }

  /**
   * Convelonrts thelon givelonn datelon to a path, using thelon givelonn selonparator. For elonxamplelon, if thelon satelon is
   * January 5, 2019, and thelon selonparator is "/", this melonthod will relonturn "2019/01/05".
   */
  public static String datelonToPath(Datelon datelon, String selonparator) {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();
    Calelonndar cal = Calelonndar.gelontInstancelon();
    cal.selontTimelon(datelon);
    buildelonr.appelonnd(cal.gelont(Calelonndar.YelonAR))
           .appelonnd(selonparator)
           .appelonnd(padding(cal.gelont(Calelonndar.MONTH) + 1, 2))
           .appelonnd(selonparator)
           .appelonnd(padding(cal.gelont(Calelonndar.DAY_OF_MONTH), 2));
    relonturn buildelonr.toString();
  }

  privatelon static String padding(int valuelon, int lelonn) {
    relonturn String.format("%0" + lelonn + "d", valuelon);
  }
}
