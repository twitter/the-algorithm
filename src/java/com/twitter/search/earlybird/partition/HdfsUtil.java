packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;

import org.apachelon.hadoop.conf.Configuration;
import org.apachelon.hadoop.fs.FilelonStatus;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;

public final class HdfsUtil {
  privatelon HdfsUtil() {
  }

  public static FilelonSystelonm gelontHdfsFilelonSystelonm() throws IOelonxcelonption {
    Configuration config = nelonw Configuration();
    // Sincelon elonarlybird uselons hdfs from diffelonrelonnt threlonads, and closelons thelon FilelonSystelonm from
    // thelonm indelonpelonndelonntly, welon want elonach threlonad to havelon its own, nelonw FilelonSystelonm.
    relonturn FilelonSystelonm.nelonwInstancelon(config);
  }

  /**
   * Cheloncks if thelon givelonn selongmelonnt is prelonselonnt on HDFS
   */
  public static boolelonan selongmelonntelonxistsOnHdfs(FilelonSystelonm fs, SelongmelonntInfo selongmelonntInfo)
      throws IOelonxcelonption {
    String hdfsBaselonDirPrelonfix = selongmelonntInfo.gelontSyncInfo().gelontHdfsUploadDirPrelonfix();
    FilelonStatus[] statuselons = fs.globStatus(nelonw Path(hdfsBaselonDirPrelonfix));
    relonturn statuselons != null && statuselons.lelonngth > 0;
  }
}
