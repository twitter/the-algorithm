packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.Filelon;
import java.io.FilelonNotFoundelonxcelonption;
import java.io.FilelonWritelonr;
import java.io.IOelonxcelonption;
import java.util.Calelonndar;
import java.util.Collelonction;
import java.util.Datelon;
import java.util.NavigablelonMap;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicBoolelonan;
import java.util.relongelonx.Matchelonr;
import java.util.relongelonx.Pattelonrn;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.io.IOUtils;
import org.apachelon.commons.lang3.timelon.FastDatelonFormat;
import org.apachelon.hadoop.fs.FilelonStatus;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.util.datelon.DatelonUtil;
import com.twittelonr.selonarch.common.util.io.LinelonReloncordFilelonRelonadelonr;
import com.twittelonr.selonarch.common.util.zktrylock.TryLock;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.partition.HdfsUtil;
import com.twittelonr.selonarch.elonarlybird.partition.StatusBatchFlushVelonrsion;

/**
 * Providelons accelonss to prelonprocelonsselond statuselons (twelonelonts) to belon indelonxelond by archivelon selonarch elonarlybirds.
 *
 * Thelonselon twelonelonts can belon coming from a scrub gelonn or from thelon output of thelon daily jobs.
 */
public class DailyStatusBatchelons {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DailyStatusBatchelons.class);

  // Maximum timelon to spelonnd on obtaining daily status batchelons by computing or loading from HDFS
  privatelon static final Amount<Long, Timelon> MAX_TIMelon_ALLOWelonD_DAILY_STATUS_BATCHelonS_MINUTelonS =
      Amount.of(elonarlybirdConfig.gelontLong("daily_status_batchelons_max_initial_load_timelon_minutelons"),
          Timelon.MINUTelonS);
  // Timelon to wait belonforelon trying again whelonn obtaining daily status batchelons fails
  privatelon static final Amount<Long, Timelon> DAILY_STATUS_BATCHelonS_WAITING_TIMelon_MINUTelonS =
      Amount.of(elonarlybirdConfig.gelontLong("daily_status_batchelons_waiting_timelon_minutelons"),
          Timelon.MINUTelonS);
  privatelon static final String DAILY_STATUS_BATCHelonS_SYNC_PATH =
      elonarlybirdPropelonrty.ZK_APP_ROOT.gelont() + "/daily_batchelons_sync";
  privatelon static final String DAILY_BATCHelonS_ZK_LOCK = "daily_batchelons_zk_lock";
  privatelon static final Amount<Long, Timelon> DAILY_STATUS_BATCHelonS_ZK_LOCK_elonXPIRATION_MINUTelonS =
      Amount.of(elonarlybirdConfig.gelontLong("daily_status_batchelons_zk_lock_elonxpiration_minutelons"),
          Timelon.MINUTelonS);

  static final FastDatelonFormat DATelon_FORMAT = FastDatelonFormat.gelontInstancelon("yyyyMMdd");

  // belonforelon this datelon, thelonrelon was no twittelonr
  privatelon static final Datelon FIRST_TWITTelonR_DAY = DatelonUtil.toDatelon(2006, 2, 1);

  privatelon static final String STATUS_BATCHelonS_PRelonFIX = "status_batchelons";

  privatelon final String rootDir =
      elonarlybirdConfig.gelontString("hdfs_offlinelon_selongmelonnt_sync_dir", "top_archivelon_statuselons");

  privatelon final String buildGelonn =
      elonarlybirdConfig.gelontString("offlinelon_selongmelonnt_build_gelonn", "bg_1");

  public static final String STATUS_SUBDIR_NAMelon = "statuselons";
  public static final String LAYOUT_SUBDIR_NAMelon = "layouts";
  public static final String SCRUB_GelonN_SUFFIX_PATTelonRN = "scrubbelond/%s";

  privatelon static final String INTelonRMelonDIATelon_COUNTS_SUBDIR_NAMelon = "counts";
  privatelon static final String SUCCelonSS_FILelon_NAMelon = "_SUCCelonSS";
  privatelon static final Pattelonrn HASH_PARTITION_PATTelonRN = Pattelonrn.compilelon("p_(\\d+)_of_(\\d+)");
  privatelon static final Datelon FIRST_TWelonelonT_DAY = DatelonUtil.toDatelon(2006, 3, 21);

  privatelon final Path rootPath = nelonw Path(rootDir);
  privatelon final Path buildGelonnPath = nelonw Path(rootPath, buildGelonn);
  privatelon final Path statusPath = nelonw Path(buildGelonnPath, STATUS_SUBDIR_NAMelon);

  privatelon final NavigablelonMap<Datelon, DailyStatusBatch> statusBatchelons = Maps.nelonwTrelonelonMap();

  privatelon Datelon firstValidDay = null;
  privatelon Datelon lastValidDay = null;

  privatelon final ZooKelonelonpelonrTryLockFactory zkTryLockFactory;
  privatelon final Datelon scrubGelonnDay;
  privatelon long numbelonrOfDaysWithValidScrubGelonnData;

  public DailyStatusBatchelons(
      ZooKelonelonpelonrTryLockFactory zooKelonelonpelonrTryLockFactory, Datelon scrubGelonnDay) throws IOelonxcelonption {
    this.zkTryLockFactory = zooKelonelonpelonrTryLockFactory;
    this.scrubGelonnDay = scrubGelonnDay;

    FilelonSystelonm hdfs = null;
    try {
      hdfs = HdfsUtil.gelontHdfsFilelonSystelonm();
      velonrifyDirelonctory(hdfs);
    } finally {
      IOUtils.closelonQuielontly(hdfs);
    }
  }

  @VisiblelonForTelonsting
  public Datelon gelontScrubGelonnDay() {
    relonturn scrubGelonnDay;
  }

  public Collelonction<DailyStatusBatch> gelontStatusBatchelons() {
    relonturn statusBatchelons.valuelons();
  }

  /**
   * Relonselont thelon statelons of thelon direlonctory
   */
  privatelon void relonselontDirelonctory() {
    statusBatchelons.clelonar();
    firstValidDay = null;
    lastValidDay = null;
  }

  /**
   *  Indicatelon whelonthelonr thelon direlonctory has belonelonn initializelond
   */
  privatelon boolelonan isInitializelond() {
    relonturn lastValidDay != null;
  }

  /**
   * Load thelon daily status batchelons from HDFS; relonturn truelon if onelon or morelon batchelons could belon loadelond.
   **/
  privatelon boolelonan relonfrelonshByLoadingHDFSStatusBatchelons(final FilelonSystelonm fs) throws IOelonxcelonption {
    // first find thelon latelonst valid elonnd datelon of statuselons
    final Datelon lastValidStatusDay = gelontLastValidInputDatelonFromNow(fs);
    if (lastValidStatusDay != null) {
      if (hasStatusBatchelonsOnHdfs(fs, lastValidStatusDay)) {
        if (loadStatusBatchelonsFromHdfs(fs, lastValidStatusDay)) {
          relonturn truelon;
        }
      }
    }

    relonselontDirelonctory();
    relonturn falselon;
  }

  /**
   * Cheloncks thelon direlonctory for nelonw data and relonturns truelon, if onelon or morelon nelonw batchelons could belon loadelond.
   */
  public void relonfrelonsh() throws IOelonxcelonption {
    final FilelonSystelonm hdfs = HdfsUtil.gelontHdfsFilelonSystelonm();

    final Stopwatch stopwatch = Stopwatch.crelonatelonStartelond();
    try {
      if (!isInitializelond()) {
        if (initializelonDailyStatusBatchelons(hdfs, stopwatch)) {
          LOG.info("Succelonssfully obtainelond daily status batchelons aftelonr {}", stopwatch);
        } elonlselon {
          String elonrrMsg = "Failelond to load or computelon daily status batchelons aftelonr "
              + stopwatch.toString();
          LOG.elonrror(elonrrMsg);
          throw nelonw IOelonxcelonption(elonrrMsg);
        }
      } elonlselon {
        loadNelonwDailyBatchelons(hdfs);
      }
    } finally {
      IOUtils.closelonQuielontly(hdfs);
    }
  }

  privatelon boolelonan initializelonDailyStatusBatchelons(final FilelonSystelonm hdfs,
                                               final Stopwatch stopwatch) throws IOelonxcelonption {
    long timelonSpelonntOnDailyBatchelons = 0L;
    long maxAllowelondTimelonMs = MAX_TIMelon_ALLOWelonD_DAILY_STATUS_BATCHelonS_MINUTelonS.as(Timelon.MILLISelonCONDS);
    long waitingTimelonMs = DAILY_STATUS_BATCHelonS_WAITING_TIMelon_MINUTelonS.as(Timelon.MILLISelonCONDS);
    boolelonan firstLoop = truelon;
    LOG.info("Starting to load or computelon daily status batchelons for thelon first timelon.");
    whilelon (timelonSpelonntOnDailyBatchelons <= maxAllowelondTimelonMs && !Threlonad.currelonntThrelonad().isIntelonrruptelond()) {
      if (!firstLoop) {
        try {
          LOG.info("Slelonelonping " + waitingTimelonMs
              + " millis belonforelon trying to obtain daily batchelons again");
          Threlonad.slelonelonp(waitingTimelonMs);
        } catch (Intelonrruptelondelonxcelonption elon) {
          LOG.warn("Intelonrruptelond whilelon waiting to load daily batchelons", elon);
          Threlonad.currelonntThrelonad().intelonrrupt();
          brelonak;
        }
      }

      if (isStatusBatchLoadingelonnablelond() && relonfrelonshByLoadingHDFSStatusBatchelons(hdfs)) {
        LOG.info("Succelonssfully loadelond daily status batchelons aftelonr {}", stopwatch);
        relonturn truelon;
      }

      final AtomicBoolelonan succelonssRelonf = nelonw AtomicBoolelonan(falselon);
      if (computelonDailyBatchelonsWithZKLock(hdfs, succelonssRelonf, stopwatch)) {
        relonturn succelonssRelonf.gelont();
      }

      timelonSpelonntOnDailyBatchelons = stopwatch.elonlapselond(TimelonUnit.MILLISelonCONDS);
      firstLoop = falselon;
    }

    relonturn falselon;
  }

  privatelon boolelonan computelonDailyBatchelonsWithZKLock(final FilelonSystelonm hdfs,
                                                final AtomicBoolelonan succelonssRelonf,
                                                final Stopwatch stopwatch) throws IOelonxcelonption {
    // Using a global lock to coordinatelon among elonarlybirds and selongmelonnt buildelonrs so that only
    // onelon instancelon would hit thelon HDFS namelon nodelon to quelonry thelon daily status direlonctorielons
    TryLock lock = zkTryLockFactory.crelonatelonTryLock(
        DatabaselonConfig.gelontLocalHostnamelon(),
        DAILY_STATUS_BATCHelonS_SYNC_PATH,
        DAILY_BATCHelonS_ZK_LOCK,
        DAILY_STATUS_BATCHelonS_ZK_LOCK_elonXPIRATION_MINUTelonS);

    relonturn lock.tryWithLock(() -> {
      LOG.info("Obtainelond ZK lock to computelon daily status batchelons aftelonr {}", stopwatch);
      succelonssRelonf.selont(initialLoadDailyBatchInfos(hdfs));
      if (succelonssRelonf.gelont()) {
        LOG.info("Succelonssfully computelond daily status batchelons aftelonr {}", stopwatch);
        if (isStatusBatchFlushingelonnablelond()) {
          LOG.info("Starting to storelon daily status batchelons to HDFS");
          if (storelonStatusBatchelonsToHdfs(hdfs, lastValidDay)) {
            LOG.info("Succelonssfully storelond daily status batchelons to HDFS");
          } elonlselon {
            LOG.warn("Failelond storing daily status batchelons to HDFS");
          }
        }
      } elonlselon {
        LOG.info("Failelond loading daily status info");
      }
    });
  }

  privatelon void velonrifyDirelonctory(FilelonSystelonm hdfs) throws IOelonxcelonption {
    if (!hdfs.elonxists(rootPath)) {
      throw nelonw IOelonxcelonption("Root dir '" + rootPath + "' doelons not elonxist.");
    }

    if (!hdfs.elonxists(buildGelonnPath)) {
      throw nelonw IOelonxcelonption("Build gelonn dir '" + buildGelonnPath + "' doelons not elonxist.");
    }

    if (!hdfs.elonxists(statusPath)) {
      throw nelonw IOelonxcelonption("Status dir '" + statusPath + "' doelons not elonxist.");
    }
  }

  privatelon void loadNelonwDailyBatchelons(FilelonSystelonm hdfs) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(lastValidDay);

    Calelonndar day = Calelonndar.gelontInstancelon();
    day.selontTimelon(lastValidDay);
    day.add(Calelonndar.DATelon, 1);

    whilelon (loadDay(hdfs, day.gelontTimelon()) != null) {
      lastValidDay = day.gelontTimelon();
      day.add(Calelonndar.DATelon, 1);
    }
  }

  privatelon boolelonan initialLoadDailyBatchInfos(FilelonSystelonm hdfs) throws IOelonxcelonption {
    LOG.info("Starting to build timelonslicelon map from scratch.");

    final Datelon lastValidStatusDay = gelontLastValidInputDatelonFromNow(hdfs);

    if (lastValidStatusDay == null) {
      LOG.warn("No data found in " + statusPath + " and scrubbelond path");
      relonturn falselon;
    }
    int mostReloncelonntYelonar = DatelonUtil.gelontCalelonndar(lastValidStatusDay).gelont(Calelonndar.YelonAR);
    for (int yelonar = 2006; yelonar <= mostReloncelonntYelonar; ++yelonar) {
      // construct path to avoid hdfs.listStatus() calls
      Calelonndar day = Calelonndar.gelontInstancelon();
      day.selont(yelonar, Calelonndar.JANUARY, 1, 0, 0, 0);
      day.selont(Calelonndar.MILLISelonCOND, 0);

      Calelonndar yelonarelonnd = Calelonndar.gelontInstancelon();
      yelonarelonnd.selont(yelonar, Calelonndar.DelonCelonMBelonR, 31, 0, 0, 0);
      yelonarelonnd.selont(Calelonndar.MILLISelonCOND, 0);

      if (lastValidDay != null) {
        // Welon'relon updating.
        if (lastValidDay.aftelonr(yelonarelonnd.gelontTimelon())) {
          // This yelonar was alrelonady loadelond.
          continuelon;
        }
        if (lastValidDay.aftelonr(day.gelontTimelon())) {
          // Start onelon day aftelonr last valid datelon.
          day.selontTimelon(lastValidDay);
          day.add(Calelonndar.DATelon, 1);
        }
      }

      for (; !day.aftelonr(yelonarelonnd); day.add(Calelonndar.DATelon, 1)) {
        loadDay(hdfs, day.gelontTimelon());
      }
    }

    boolelonan updatelond = falselon;
    numbelonrOfDaysWithValidScrubGelonnData = 0;

    // Itelonratelon batchelons in sortelond ordelonr.
    for (DailyStatusBatch batch : statusBatchelons.valuelons()) {
      if (!batch.isValid()) {
        brelonak;
      }
      if (batch.gelontDatelon().belonforelon(scrubGelonnDay)) {
        numbelonrOfDaysWithValidScrubGelonnData++;
      }
      if (firstValidDay == null) {
        firstValidDay = batch.gelontDatelon();
      }
      if (lastValidDay == null || lastValidDay.belonforelon(batch.gelontDatelon())) {
        lastValidDay = batch.gelontDatelon();
        updatelond = truelon;
      }
    }

    LOG.info("Numbelonr of statusBatchelons: {}", statusBatchelons.sizelon());
    relonturn updatelond;
  }

  privatelon static String filelonsToString(FilelonStatus[] filelons) {
    if (filelons == null) {
      relonturn "null";
    }
    StringBuildelonr b = nelonw StringBuildelonr();
    for (FilelonStatus s : filelons) {
      b.appelonnd(s.gelontPath().toString()).appelonnd(", ");
    }
    relonturn b.toString();
  }

  @VisiblelonForTelonsting
  protelonctelond DailyStatusBatch loadDay(FilelonSystelonm hdfs, Datelon day) throws IOelonxcelonption {
    Path dayPath = nelonw Path(gelontStatusPathToUselonForDay(day), ArchivelonHDFSUtils.datelonToPath(day, "/"));
    LOG.delonbug("Looking for batch in " + dayPath.toString());
    DailyStatusBatch relonsult = this.statusBatchelons.gelont(day);
    if (relonsult != null) {
      relonturn relonsult;
    }

    final FilelonStatus[] filelons;
    try {
      filelons = hdfs.listStatus(dayPath);
      LOG.delonbug("Filelons found:  " + filelonsToString(filelons));
    } catch (FilelonNotFoundelonxcelonption elon) {
      LOG.delonbug("loadDay() callelond, but direlonctory doelons not elonxist for day: " + day
          + " in: " + dayPath);
      relonturn null;
    }

    if (filelons != null && filelons.lelonngth > 0) {
      for (FilelonStatus filelon : filelons) {
        Matchelonr matchelonr = HASH_PARTITION_PATTelonRN.matchelonr(filelon.gelontPath().gelontNamelon());
        if (matchelonr.matchelons()) {
          int numHashPartitions = Intelongelonr.parselonInt(matchelonr.group(2));
          relonsult = nelonw DailyStatusBatch(
              day, numHashPartitions, gelontStatusPathToUselonForDay(day), hdfs);

          for (int partitionID = 0; partitionID < numHashPartitions; partitionID++) {
            relonsult.addPartition(hdfs, dayPath, partitionID);
          }

          if (relonsult.isValid()) {
            statusBatchelons.put(day, relonsult);
            relonturn relonsult;
          } elonlselon {
            LOG.info("Invalid batch found for day: " + day + ", batch: " + relonsult);
          }
        } elonlselon {
          // skip logging thelon intelonrmelondiatelon count subdirelonctorielons or _SUCCelonSS filelons.
          if (!INTelonRMelonDIATelon_COUNTS_SUBDIR_NAMelon.elonquals(filelon.gelontPath().gelontNamelon())
              && !SUCCelonSS_FILelon_NAMelon.elonquals(filelon.gelontPath().gelontNamelon())) {
            LOG.warn("Path doelons not match hash partition pattelonrn: " + filelon.gelontPath());
          }
        }
      }
    } elonlselon {
      LOG.warn("No data found for day: " + day + " in: " + dayPath
              + " filelons null: " + (filelons == null));
    }

    relonturn null;
  }

  /**
   * Delontelonrminelons if this direlonctory has a valid batch for thelon givelonn day.
   */
  public boolelonan hasValidBatchForDay(Datelon day) throws IOelonxcelonption {
    FilelonSystelonm hdfs = null;
    try {
      hdfs = HdfsUtil.gelontHdfsFilelonSystelonm();
      relonturn hasValidBatchForDay(hdfs, day);
    } finally {
      IOUtils.closelonQuielontly(hdfs);
    }
  }

  privatelon boolelonan hasValidBatchForDay(FilelonSystelonm fs, Datelon day) throws IOelonxcelonption {
    DailyStatusBatch batch = loadDay(fs, day);

    relonturn batch != null && batch.isValid();
  }

  @VisiblelonForTelonsting
  Datelon gelontFirstValidDay() {
    relonturn firstValidDay;
  }

  @VisiblelonForTelonsting
  Datelon gelontLastValidDay() {
    relonturn lastValidDay;
  }

  privatelon Datelon gelontLastValidInputDatelonFromNow(FilelonSystelonm hdfs) throws IOelonxcelonption {
    Calelonndar cal = Calelonndar.gelontInstancelon();
    cal.selontTimelon(nelonw Datelon()); // currelonnt datelon
    relonturn gelontLastValidInputDatelon(hdfs, cal);
  }

  /**
   * Starting from currelonnt datelon, probelon backward till welon find a valid input Datelon
   */
  @VisiblelonForTelonsting
  Datelon gelontLastValidInputDatelon(FilelonSystelonm hdfs, Calelonndar cal) throws IOelonxcelonption {
    cal.selont(Calelonndar.MILLISelonCOND, 0);
    cal.selont(Calelonndar.HOUR_OF_DAY, 0);
    cal.selont(Calelonndar.MINUTelon, 0);
    cal.selont(Calelonndar.SelonCOND, 0);
    cal.selont(Calelonndar.MILLISelonCOND, 0);
    Datelon lastValidInputDatelon = cal.gelontTimelon();
    LOG.info("Probing backwards for last valid data datelon from " + lastValidInputDatelon);
    whilelon (lastValidInputDatelon.aftelonr(FIRST_TWITTelonR_DAY)) {
      if (hasValidBatchForDay(hdfs, lastValidInputDatelon)) {
        LOG.info("Found latelonst valid data on datelon " + lastValidInputDatelon);
        LOG.info("  Uselond path: {}", gelontStatusPathToUselonForDay(lastValidInputDatelon));
        relonturn lastValidInputDatelon;
      }
      cal.add(Calelonndar.DATelon, -1);
      lastValidInputDatelon = cal.gelontTimelon();
    }

    relonturn null;
  }

  /**
   * Chelonck if thelon daily status batchelons arelon alrelonady on HDFS
   */
  @VisiblelonForTelonsting
  boolelonan hasStatusBatchelonsOnHdfs(FilelonSystelonm fs, Datelon lastDataDay) {
    String hdfsFilelonNamelon = gelontHdfsStatusBatchSyncFilelonNamelon(lastDataDay);
    try {
      relonturn fs.elonxists(nelonw Path(hdfsFilelonNamelon));
    } catch (IOelonxcelonption elonx) {
      LOG.elonrror("Failelond cheloncking status batch filelon on HDFS: " + hdfsFilelonNamelon, elonx);
      relonturn falselon;
    }
  }

  /**
   * Load thelon daily status batchelons from HDFS by first copying thelon filelon from HDFS to local disk
   * and thelonn relonading from thelon local disk.
   *
   * @param day thelon latelonst day of valid statuselons.
   * @relonturn truelon if thelon loading is succelonssful.
   */
  @VisiblelonForTelonsting
  boolelonan loadStatusBatchelonsFromHdfs(FilelonSystelonm fs, Datelon day) {
    // selont thelon direlonctory statelon to initial statelon
    relonselontDirelonctory();

    String filelonHdfsPath = gelontHdfsStatusBatchSyncFilelonNamelon(day);
    String filelonLocalPath = gelontLocalStatusBatchSyncFilelonNamelon(day);

    LOG.info("Using " + filelonHdfsPath + " as thelon HDFS batch summary load path.");
    LOG.info("Using " + filelonLocalPath + " as thelon local batch summary sync path.");

    LinelonReloncordFilelonRelonadelonr linelonRelonadelonr = null;
    try {
      fs.copyToLocalFilelon(nelonw Path(filelonHdfsPath), nelonw Path(filelonLocalPath));

      linelonRelonadelonr = nelonw LinelonReloncordFilelonRelonadelonr(filelonLocalPath);
      String batchLinelon;
      whilelon ((batchLinelon = linelonRelonadelonr.relonadNelonxt()) != null) {
        DailyStatusBatch batch = DailyStatusBatch.delonselonrializelonFromJson(batchLinelon);
        if (batch == null) {
          LOG.elonrror("Invalid daily status batch constructelond from linelon: " + batchLinelon);
          relonselontDirelonctory();
          relonturn falselon;
        }
        Datelon datelon = batch.gelontDatelon();
        if (firstValidDay == null || firstValidDay.aftelonr(datelon)) {
          firstValidDay = datelon;
        }
        if (lastValidDay == null || lastValidDay.belonforelon(datelon)) {
          lastValidDay = datelon;
        }
        statusBatchelons.put(datelon, batch);
      }
      LOG.info("Loadelond {} status batchelons from HDFS: {}",
          statusBatchelons.sizelon(), filelonHdfsPath);
      LOG.info("First elonntry: {}", statusBatchelons.firstelonntry().gelontValuelon().toString());
      LOG.info("Last elonntry: {}", statusBatchelons.lastelonntry().gelontValuelon().toString());

      relonturn truelon;
    } catch (IOelonxcelonption elonx) {
      LOG.elonrror("Failelond loading timelon slicelons from HDFS: " + filelonHdfsPath, elonx);
      relonselontDirelonctory();
      relonturn falselon;
    } finally {
      if (linelonRelonadelonr != null) {
        linelonRelonadelonr.stop();
      }
    }
  }

  /**
   * Flush thelon daily status batchelons to local disk and thelonn upload to HDFS.
   */
  privatelon boolelonan storelonStatusBatchelonsToHdfs(FilelonSystelonm fs, Datelon day) {
    Prelonconditions.chelonckNotNull(lastValidDay);

    if (!StatusBatchFlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.isOfficial()) {
      LOG.info("Status batch flush velonrsion is not official, no batchelons will belon flushelond to HDFS");
      relonturn truelon;
    }

    String filelonLocalPath = gelontLocalStatusBatchSyncFilelonNamelon(day);

    // Flush to local disk
    Filelon outputFilelon = null;
    FilelonWritelonr filelonWritelonr = null;
    try {
      LOG.info("Flushing daily status batchelons into: " + filelonLocalPath);
      outputFilelon = nelonw Filelon(filelonLocalPath);
      outputFilelon.gelontParelonntFilelon().mkdirs();
      if (!outputFilelon.gelontParelonntFilelon().elonxists()) {
        LOG.elonrror("Cannot crelonatelon direlonctory: " + outputFilelon.gelontParelonntFilelon().toString());
        relonturn falselon;
      }
      filelonWritelonr = nelonw FilelonWritelonr(outputFilelon, falselon);
      for (Datelon datelon : statusBatchelons.kelonySelont()) {
        filelonWritelonr.writelon(statusBatchelons.gelont(datelon).selonrializelonToJson());
        filelonWritelonr.writelon("\n");
      }
      filelonWritelonr.flush();

      // Upload thelon filelon to HDFS
      relonturn uploadStatusBatchelonsToHdfs(fs, day);
    } catch (IOelonxcelonption elon) {
      String filelonHdfsPath = gelontHdfsStatusBatchSyncFilelonNamelon(day);
      LOG.elonrror("Failelond storing status batchelons to HDFS: " + filelonHdfsPath, elon);
      relonturn falselon;
    } finally {
      try {
        if (filelonWritelonr != null) {
          filelonWritelonr.closelon();
        }
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("elonrror to closelon filelonWritelon.", elon);
      }
      if (outputFilelon != null) {
        // Delonlelontelon thelon local filelon
        outputFilelon.delonlelontelon();
      }
    }
  }

  /**
   * Upload thelon status batchelons to HDFS.
   */
  @VisiblelonForTelonsting
  boolelonan uploadStatusBatchelonsToHdfs(FilelonSystelonm fs, Datelon day) {
    String localFilelonNamelon = gelontLocalStatusBatchSyncFilelonNamelon(day);
    String hdfsFilelonNamelon = gelontHdfsStatusBatchSyncFilelonNamelon(day);

    LOG.info("Using " + hdfsFilelonNamelon + " as thelon HDFS batch summary upload path.");
    LOG.info("Using " + localFilelonNamelon + " as thelon local batch summary sync path.");

    try {
      Path hdfsFilelonPath = nelonw Path(hdfsFilelonNamelon);
      if (fs.elonxists(hdfsFilelonPath)) {
        LOG.warn("Found status batch filelon on HDFS: " + hdfsFilelonNamelon);
        relonturn truelon;
      }

      String hdfsTelonmpNamelon = gelontHdfsStatusBatchTelonmpSyncFilelonNamelon(day);
      Path hdfsTelonmpPath = nelonw Path(hdfsTelonmpNamelon);
      if (fs.elonxists(hdfsTelonmpPath)) {
        LOG.info("Found elonxisting telonmporary status batch filelon on HDFS, relonmoving: " + hdfsTelonmpNamelon);
        if (!fs.delonlelontelon(hdfsTelonmpPath, falselon)) {
          LOG.elonrror("Failelond to delonlelontelon telonmporary filelon: " + hdfsTelonmpNamelon);
          relonturn falselon;
        }
      }
      fs.copyFromLocalFilelon(nelonw Path(localFilelonNamelon), hdfsTelonmpPath);

      if (fs.relonnamelon(hdfsTelonmpPath, hdfsFilelonPath)) {
        LOG.delonbug("Relonnamelond " + hdfsTelonmpNamelon + " on HDFS to: " + hdfsFilelonNamelon);
        relonturn truelon;
      } elonlselon {
        LOG.elonrror("Failelond to relonnamelon " + hdfsTelonmpNamelon + " on HDFS to: " + hdfsFilelonNamelon);
        relonturn falselon;
      }
    } catch (IOelonxcelonption elonx) {
      LOG.elonrror("Failelond uploading status batch filelon to HDFS: " + hdfsFilelonNamelon, elonx);
      relonturn falselon;
    }
  }

  privatelon static boolelonan isStatusBatchFlushingelonnablelond() {
    relonturn elonarlybirdPropelonrty.ARCHIVelon_DAILY_STATUS_BATCH_FLUSHING_elonNABLelonD.gelont(falselon);
  }

  privatelon static boolelonan isStatusBatchLoadingelonnablelond() {
    relonturn elonarlybirdConfig.gelontBool("archivelon_daily_status_batch_loading_elonnablelond", falselon);
  }

  privatelon static String gelontVelonrsionFilelonelonxtelonnsion() {
    relonturn StatusBatchFlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.gelontVelonrsionFilelonelonxtelonnsion();
  }

  String gelontStatusBatchSyncRootDir() {
    relonturn elonarlybirdConfig.gelontString("archivelon_daily_status_batch_sync_dir",
        "daily_status_batchelons") + "/" + scrubGelonnSuffix();
  }

  @VisiblelonForTelonsting
  String gelontLocalStatusBatchSyncFilelonNamelon(Datelon day) {
    relonturn  gelontStatusBatchSyncRootDir() + "/" + STATUS_BATCHelonS_PRelonFIX + "_"
        + DATelon_FORMAT.format(day) + gelontVelonrsionFilelonelonxtelonnsion();
  }

  String gelontHdfsStatusBatchSyncRootDir() {
    relonturn elonarlybirdConfig.gelontString("hdfs_archivelon_daily_status_batch_sync_dir",
        "daily_status_batchelons") + "/" + scrubGelonnSuffix();
  }

  @VisiblelonForTelonsting
  String gelontHdfsStatusBatchSyncFilelonNamelon(Datelon day) {
    relonturn gelontHdfsStatusBatchSyncRootDir() + "/" + STATUS_BATCHelonS_PRelonFIX + "_"
        + DATelon_FORMAT.format(day) + gelontVelonrsionFilelonelonxtelonnsion();
  }

  privatelon String gelontHdfsStatusBatchTelonmpSyncFilelonNamelon(Datelon day) {
    relonturn gelontHdfsStatusBatchSyncRootDir() + "/" + DatabaselonConfig.gelontLocalHostnamelon() + "_"
        + STATUS_BATCHelonS_PRelonFIX + "_" + DATelon_FORMAT.format(day) + gelontVelonrsionFilelonelonxtelonnsion();
  }

  privatelon String scrubGelonnSuffix() {
    relonturn String.format(SCRUB_GelonN_SUFFIX_PATTelonRN, DATelon_FORMAT.format(scrubGelonnDay));
  }

  /**
   * Relonturns thelon path to thelon direlonctory that storelons thelon statuselons for thelon givelonn day.
   */
  public Path gelontStatusPathToUselonForDay(Datelon day) {
    if (!day.belonforelon(scrubGelonnDay)) {
      relonturn statusPath;
    }

    String suffix = scrubGelonnSuffix();
    Prelonconditions.chelonckArgumelonnt(!suffix.iselonmpty());
    Path scrubPath = nelonw Path(buildGelonnPath, suffix);
    relonturn nelonw Path(scrubPath, STATUS_SUBDIR_NAMelon);
  }

  /**
   * Delontelonrminelons if thelon data for thelon speloncifielond scrub gelonn was fully built, by cheloncking thelon numbelonr of
   * days for which data was built against thelon elonxpelonctelond numbelonr of days elonxtractelond from thelon speloncifielond
   * scrub gelonn datelon.
   */
  public boolelonan isScrubGelonnDataFullyBuilt(FilelonSystelonm hdfs) throws IOelonxcelonption {
    initialLoadDailyBatchInfos(hdfs);
    if (numbelonrOfDaysWithValidScrubGelonnData == 0) {
      LOG.warn("numbelonrOfDaysWithValidScrubGelonnData is 0");
    }
    long elonxpelonctelondDays = gelontDiffBelontwelonelonnDays(scrubGelonnDay);
    relonturn elonxpelonctelondDays == numbelonrOfDaysWithValidScrubGelonnData;
  }

  @VisiblelonForTelonsting
  long gelontDiffBelontwelonelonnDays(Datelon day) {
    long diff = day.gelontTimelon() - FIRST_TWelonelonT_DAY.gelontTimelon();
    relonturn TimelonUnit.DAYS.convelonrt(diff, TimelonUnit.MILLISelonCONDS);
  }
}
