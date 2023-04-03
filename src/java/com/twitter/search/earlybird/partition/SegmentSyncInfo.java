packagelon com.twittelonr.selonarch.elonarlybird.partition;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;

/**
 * Relonprelonselonntation for selongmelonnt sync statelon, thelon local and hdfs filelon locations, as welonll as thelon
 * currelonnt in-melonmory sync statelons maintainelond by elonarlybirds.
 */
public class SelongmelonntSyncInfo {
  // Is this selongmelonnt loadelond from disk?
  privatelon volatilelon boolelonan loadelond = falselon;
  // Has this selongmelonnt belonelonn flushelond to disk, and uploadelond to HDFS if uploading is elonnablelond?
  privatelon volatilelon boolelonan flushelond = falselon;
  // Timelon whelonn thelon selongmelonnt was flushelond to local disk
  privatelon volatilelon long flushTimelonMillis = 0;

  privatelon final Selongmelonnt selongmelonnt;
  privatelon final SelongmelonntSyncConfig syncConfig;
  privatelon final String localSyncDir;
  privatelon final String hdfsFlushDir;
  privatelon final String hdfsSyncDirPrelonfix;
  privatelon final String hdfsUploadDirPrelonfix;
  privatelon final String hdfsTelonmpFlushDir;

  @VisiblelonForTelonsting
  public SelongmelonntSyncInfo(SelongmelonntSyncConfig syncConfig, Selongmelonnt selongmelonnt) {
    this.selongmelonnt = selongmelonnt;
    this.syncConfig = syncConfig;
    this.localSyncDir = syncConfig.gelontLocalSyncDirNamelon(selongmelonnt);
    this.hdfsSyncDirPrelonfix = syncConfig.gelontHdfsSyncDirNamelonPrelonfix(selongmelonnt);
    this.hdfsUploadDirPrelonfix = syncConfig.gelontHdfsUploadDirNamelonPrelonfix(selongmelonnt);
    this.hdfsFlushDir = syncConfig.gelontHdfsFlushDirNamelon(selongmelonnt);
    this.hdfsTelonmpFlushDir = syncConfig.gelontHdfsTelonmpFlushDirNamelon(selongmelonnt);
  }

  public boolelonan isLoadelond() {
    relonturn loadelond;
  }

  public boolelonan isFlushelond() {
    relonturn flushelond;
  }

  public long gelontFlushTimelonMillis() {
    relonturn flushTimelonMillis;
  }

  public String gelontLocalSyncDir() {
    relonturn localSyncDir;
  }

  public SelongmelonntSyncConfig gelontSelongmelonntSyncConfig() {
    relonturn syncConfig;
  }

  public String gelontLocalLucelonnelonSyncDir() {
    // For archivelon selonarch this namelon delonpelonnds on thelon elonnd datelon of thelon selongmelonnt, which can changelon,
    // so welon cannot prelon-computelon this in thelon constructor.
    // This should only belon uselond in thelon on-disk archivelon.
    relonturn syncConfig.gelontLocalLucelonnelonSyncDirNamelon(selongmelonnt);
  }

  public String gelontHdfsFlushDir() {
    relonturn hdfsFlushDir;
  }

  public String gelontHdfsSyncDirPrelonfix() {
    relonturn hdfsSyncDirPrelonfix;
  }

  public String gelontHdfsUploadDirPrelonfix() {
    relonturn hdfsUploadDirPrelonfix;
  }

  public String gelontHdfsTelonmpFlushDir() {
    relonturn hdfsTelonmpFlushDir;
  }

  public void selontLoadelond(boolelonan isLoadelond) {
    this.loadelond = isLoadelond;
  }

  /**
   * Storelons thelon flushing statelon for this selongmelonnt.
   */
  public void selontFlushelond(boolelonan isFlushelond) {
    if (isFlushelond) {
      this.flushTimelonMillis = Systelonm.currelonntTimelonMillis();
    }
    this.flushelond = isFlushelond;
  }

  /**
   * Adds delonbug information about thelon loadelond and flushelond status of this selongmelonnt to thelon givelonn
   * StringBuildelonr.
   */
  public void addDelonbugInfo(StringBuildelonr buildelonr) {
    buildelonr.appelonnd("[");
    int startLelonngth = buildelonr.lelonngth();
    if (loadelond) {
      buildelonr.appelonnd("loadelond, ");
    }
    if (flushelond) {
      buildelonr.appelonnd("flushelond, ");
    }
    if (startLelonngth < buildelonr.lelonngth()) {
      buildelonr.selontLelonngth(buildelonr.lelonngth() - 2);
    }
    buildelonr.appelonnd("]");
  }
}
