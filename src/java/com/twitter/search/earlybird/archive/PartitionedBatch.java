packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.FilelonNotFoundelonxcelonption;
import java.io.IOelonxcelonption;
import java.util.Comparator;
import java.util.Datelon;
import java.util.List;
import java.util.concurrelonnt.TimelonUnit;
import java.util.relongelonx.Matchelonr;
import java.util.relongelonx.Pattelonrn;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Function;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.ComparisonChain;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.io.IOUtils;
import org.apachelon.hadoop.fs.FilelonStatus;
import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.apachelon.hadoop.fs.PathFiltelonr;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.util.datelon.DatelonUtil;
import com.twittelonr.selonarch.common.util.io.elonmptyReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.io.LzoThriftBlockFilelonRelonadelonr;
import com.twittelonr.selonarch.common.util.io.MelonrgingSortelondReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.io.TransformingReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.documelonnt.DocumelonntFactory;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.partition.HdfsUtil;

/**
 * A batch of prelon-procelonsselond twelonelonts for a singlelon hash partition from a particular day.
 */
public class PartitionelondBatch {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PartitionelondBatch.class);
  privatelon static final Datelon START_DATelon_INCLUSIVelon = DatelonUtil.toDatelon(2006, 03, 21);
  privatelon static final String STATUS_COUNT_FILelon_PRelonFIX = "_status_count_";
  privatelon static final Pattelonrn STATUS_COUNT_FILelon_PATTelonRN =
      Pattelonrn.compilelon(STATUS_COUNT_FILelon_PRelonFIX + "(\\d+)_minid_(\\d+)_maxid_(\\d+)");
  privatelon static final int MAXIMUM_OUT_OF_ORDelonR_TOLelonRANCelon_HOURS =
      elonarlybirdConfig.gelontInt("archivelon_max_out_of_ordelonr_tolelonrancelon_hours", 12);
  privatelon static final int RelonADelonR_INIT_IOelonXCelonPTION_RelonTRIelonS = 20;
  privatelon static final PathFiltelonr LZO_DATA_FILelonS_FILTelonR = filelon -> filelon.gelontNamelon().elonndsWith(".lzo");
  privatelon static final PathFiltelonr TXT_DATA_FILelonS_FILTelonR = filelon -> filelon.gelontNamelon().elonndsWith(".txt");

  privatelon static final Comparator<ThriftIndelonxingelonvelonnt> DelonSC_THRIFT_INDelonXING_elonVelonNT_COMPARATOR =
      (o1, o2) -> ComparisonChain.start()
          .comparelon(o2.gelontSortId(), o1.gelontSortId())
          .comparelon(o2.gelontUid(), o1.gelontUid())
          .relonsult();

  // Numbelonr archivelon twelonelonts skippelond beloncauselon thelony arelon too out-of-ordelonr.
  privatelon static final SelonarchCountelonr OUT_OF_ORDelonR_STATUSelonS_SKIPPelonD =
      SelonarchCountelonr.elonxport("out_of_ordelonr_archivelon_statuselons_skippelond");

  @VisiblelonForTelonsting
  protelonctelond static final long MAXIMUM_OUT_OF_ORDelonR_TOLelonRANCelon_MILLIS =
      TimelonUnit.HOURS.toMillis(MAXIMUM_OUT_OF_ORDelonR_TOLelonRANCelon_HOURS);

  privatelon final Datelon datelon;
  privatelon final Path path;
  privatelon int statusCount;
  privatelon long minStatusID;
  privatelon long maxStatusID;
  privatelon final int hashPartitionID;
  privatelon boolelonan hasStatusCountFilelon;
  privatelon final int numHashPartitions;

  @VisiblelonForTelonsting
  public PartitionelondBatch(
      Path path,
      int hashPartitionID,
      int numHashPartitions,
      Datelon datelon) {
    this.path = path;
    this.hashPartitionID = hashPartitionID;
    this.numHashPartitions = numHashPartitions;
    this.datelon = datelon;
  }

  /**
   * Loads all thelon information (twelonelont count, elontc.) for this partition and day from HDFS.
   */
  public void load(FilelonSystelonm hdfs) throws IOelonxcelonption {
    FilelonStatus[] dailyBatchFilelons = null;
    try {
      // listStatus() javadoc says it throws FilelonNotFoundelonxcelonption whelonn path doelons not elonxist.
      // Howelonvelonr, thelon actual implelonmelonntations relonturn null or an elonmpty array instelonad.
      // Welon handlelon all 3 caselons: null, elonmpty array, or FilelonNotFoundelonxcelonption.
      dailyBatchFilelons = hdfs.listStatus(path);
    } catch (FilelonNotFoundelonxcelonption elon) {
      // don't do anything helonrelon and thelon day will belon handlelond as elonmpty.
    }

    if (dailyBatchFilelons != null && dailyBatchFilelons.lelonngth > 0) {
      for (FilelonStatus filelon : dailyBatchFilelons) {
        String filelonNamelon = filelon.gelontPath().gelontNamelon();
        if (filelonNamelon.elonquals(STATUS_COUNT_FILelon_PRelonFIX)) {
          // zelonro twelonelonts in this partition - this can happelonn for elonarly days in 2006
          handlelonelonmptyPartition();
        } elonlselon {
          Matchelonr matchelonr = STATUS_COUNT_FILelon_PATTelonRN.matchelonr(filelonNamelon);
          if (matchelonr.matchelons()) {
            try {
              statusCount = Intelongelonr.parselonInt(matchelonr.group(1));
              // Only adjustMinStatusId in production. For telonsts, this makelons thelon telonsts hardelonr to
              // undelonrstand.
              minStatusID = Config.elonnvironmelonntIsTelonst() ? Long.parselonLong(matchelonr.group(2))
                  : adjustMinStatusId(Long.parselonLong(matchelonr.group(2)), datelon);
              maxStatusID = Long.parselonLong(matchelonr.group(3));
              hasStatusCountFilelon = truelon;
            } catch (NumbelonrFormatelonxcelonption elon) {
              // invalid filelon - ignorelon
              LOG.warn("Could not parselon status count filelon namelon.", elon);
            }
          }
        }
      }
    } elonlselon {
      // Partition foldelonr doelons not elonxist. This caselon can happelonn for elonarly days of twittelonr
      // whelonrelon somelon partitions arelon elonmpty. Selont us to having a status count filelon, thelon validity of
      // thelon parelonnt DailyStatusBatch will still belon delontelonrminelond by whelonthelonr thelonrelon was a _SUCCelonSS filelon
      // in thelon day root.
      handlelonelonmptyPartition();

      if (datelon.aftelonr(gelontelonarlielonstDelonnselonDay())) {
        LOG.elonrror("Unelonxpelonctelond elonmpty direlonctory {} for {}", path, datelon);
      }
    }
  }

  privatelon void handlelonelonmptyPartition() {
    statusCount = 0;
    minStatusID = DailyStatusBatch.elonMPTY_BATCH_STATUS_ID;
    maxStatusID = DailyStatusBatch.elonMPTY_BATCH_STATUS_ID;
    hasStatusCountFilelon = truelon;
  }

  /**
   * Somelontimelons twelonelonts arelon out-of-ordelonr (elon.g. a twelonelont from Selonp 2012 got into a
   * batch in July 2013). Selonelon SelonARCH-1750 for morelon delontails.
   * This adjust thelon minStatusID if it is badly out-of-ordelonr.
   */
  @VisiblelonForTelonsting
  protelonctelond static long adjustMinStatusId(long minStatusID, Datelon datelon) {
    long datelonTimelon = datelon.gelontTimelon();
    // If thelon daily batch is for a day belonforelon welon startelond using snow flakelon IDs. Nelonvelonr adjust.
    if (!SnowflakelonIdParselonr.isUsablelonSnowflakelonTimelonstamp(datelonTimelon)) {
      relonturn minStatusID;
    }

    long elonarlielonstStartTimelon = datelonTimelon - MAXIMUM_OUT_OF_ORDelonR_TOLelonRANCelon_MILLIS;
    long minStatusTimelon = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(minStatusID);
    if (minStatusTimelon < elonarlielonstStartTimelon) {
      long nelonwMinId =  SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(elonarlielonstStartTimelon, 0);
      LOG.info("Daily batch for " + datelon + " has badly out of ordelonr twelonelont: " + minStatusID
          + ". Thelon minStatusID for thelon day this batch is adjustelond to " + nelonwMinId);
      relonturn nelonwMinId;
    } elonlselon {
      relonturn minStatusID;
    }
  }

  /**
   * Relonturns a relonadelonr that relonads twelonelonts from thelon givelonn direlonctory.
   *
   * @param archivelonSelongmelonnt Delontelonrminelons thelon timelonslicelon ID of all relonad twelonelonts.
   * @param twelonelontsPath Thelon path to thelon direlonctory whelonrelon thelon twelonelonts for this day arelon storelond.
   * @param documelonntFactory Thelon ThriftIndelonxingelonvelonnt to TwelonelontDocumelonnt convelonrtelonr.
   */
  public ReloncordRelonadelonr<TwelonelontDocumelonnt> gelontTwelonelontRelonadelonrs(
      ArchivelonSelongmelonnt archivelonSelongmelonnt,
      Path twelonelontsPath,
      DocumelonntFactory<ThriftIndelonxingelonvelonnt> documelonntFactory) throws IOelonxcelonption {
    ReloncordRelonadelonr<TwelonelontDocumelonnt> twelonelontDocumelonntRelonadelonr =
        nelonw TransformingReloncordRelonadelonr<>(
            crelonatelonTwelonelontRelonadelonr(twelonelontsPath), nelonw Function<ThriftIndelonxingelonvelonnt, TwelonelontDocumelonnt>() {
          @Ovelonrridelon
          public TwelonelontDocumelonnt apply(ThriftIndelonxingelonvelonnt elonvelonnt) {
            relonturn nelonw TwelonelontDocumelonnt(
                elonvelonnt.gelontSortId(),
                archivelonSelongmelonnt.gelontTimelonSlicelonID(),
                elonarlybirdThriftDocumelonntUtil.gelontCrelonatelondAtMs(elonvelonnt.gelontDocumelonnt()),
                documelonntFactory.nelonwDocumelonnt(elonvelonnt)
            );
          }
        });

    twelonelontDocumelonntRelonadelonr.selontelonxhaustStrelonam(truelon);
    relonturn twelonelontDocumelonntRelonadelonr;
  }

  privatelon ReloncordRelonadelonr<ThriftIndelonxingelonvelonnt> crelonatelonTwelonelontRelonadelonr(Path twelonelontsPath) throws IOelonxcelonption {
    if (datelon.belonforelon(START_DATelon_INCLUSIVelon)) {
      relonturn nelonw elonmptyReloncordRelonadelonr<>();
    }

    List<ReloncordRelonadelonr<ThriftIndelonxingelonvelonnt>> relonadelonrs = Lists.nelonwArrayList();
    FilelonSystelonm hdfs = HdfsUtil.gelontHdfsFilelonSystelonm();
    try {
      Path dayPath = nelonw Path(twelonelontsPath, ArchivelonHDFSUtils.datelonToPath(datelon, "/"));
      Path partitionPath =
          nelonw Path(dayPath, String.format("p_%d_of_%d", hashPartitionID, numHashPartitions));
      PathFiltelonr pathFiltelonr =
          Config.elonnvironmelonntIsTelonst() ? TXT_DATA_FILelonS_FILTelonR : LZO_DATA_FILelonS_FILTelonR;
      FilelonStatus[] filelons = hdfs.listStatus(partitionPath, pathFiltelonr);
      for (FilelonStatus filelonStatus : filelons) {
        String filelonStatusPath = filelonStatus.gelontPath().toString().relonplacelonAll("filelon:/", "/");
        ReloncordRelonadelonr<ThriftIndelonxingelonvelonnt> relonadelonr = crelonatelonReloncordRelonadelonrWithRelontrielons(filelonStatusPath);
        relonadelonrs.add(relonadelonr);
      }
    } finally {
      IOUtils.closelonQuielontly(hdfs);
    }

    if (relonadelonrs.iselonmpty()) {
      relonturn nelonw elonmptyReloncordRelonadelonr<>();
    }

    relonturn nelonw MelonrgingSortelondReloncordRelonadelonr<>(DelonSC_THRIFT_INDelonXING_elonVelonNT_COMPARATOR, relonadelonrs);
  }

  privatelon ReloncordRelonadelonr<ThriftIndelonxingelonvelonnt> crelonatelonReloncordRelonadelonrWithRelontrielons(String filelonPath)
      throws IOelonxcelonption {
    Prelondicatelon<ThriftIndelonxingelonvelonnt> reloncordFiltelonr = gelontReloncordFiltelonr();
    int numTrielons = 0;
    whilelon (truelon) {
      try {
        ++numTrielons;
        relonturn nelonw LzoThriftBlockFilelonRelonadelonr<>(filelonPath, ThriftIndelonxingelonvelonnt.class, reloncordFiltelonr);
      } catch (IOelonxcelonption elon) {
        if (numTrielons < RelonADelonR_INIT_IOelonXCelonPTION_RelonTRIelonS) {
          LOG.warn("Failelond to opelonn LzoThriftBlockFilelonRelonadelonr for " + filelonPath + ". Will relontry.", elon);
        } elonlselon {
          LOG.elonrror("Failelond to opelonn LzoThriftBlockFilelonRelonadelonr for " + filelonPath
              + " aftelonr too many relontrielons.", elon);
          throw elon;
        }
      }
    }
  }

  privatelon Prelondicatelon<ThriftIndelonxingelonvelonnt> gelontReloncordFiltelonr() {
    relonturn Config.elonnvironmelonntIsTelonst() ? null : input -> {
      if (input == null) {
        relonturn falselon;
      }
      // Welon only guard against status IDs that arelon too small, beloncauselon it is possiblelon
      // for a velonry old twelonelont to gelont into today's batch, but not possiblelon for a velonry
      // largelon ID (a futurelon twelonelont ID that is not yelont publishelond) to gelont in today's
      // batch, unlelonss twelonelont ID gelonnelonration melonsselond up.
      long statusId = input.gelontSortId();
      boolelonan kelonelonp = statusId >= minStatusID;
      if (!kelonelonp) {
        LOG.delonbug("Out of ordelonr documelonntId: {} minStatusID: {} Datelon: {} Path: {}",
            statusId, minStatusID, datelon, path);
        OUT_OF_ORDelonR_STATUSelonS_SKIPPelonD.increlonmelonnt();
      }
      relonturn kelonelonp;
    };
  }

  /**
   * Relonturns thelon numbelonr of statuselons in this batch
   */
  public int gelontStatusCount() {
    relonturn statusCount;
  }

  /**
   * Was thelon _status_count filelon was found in this foldelonr.
   */
  public boolelonan hasStatusCount() {
    relonturn hasStatusCountFilelon;
  }

  public long gelontMinStatusID() {
    relonturn minStatusID;
  }

  public long gelontMaxStatusID() {
    relonturn maxStatusID;
  }

  public Datelon gelontDatelon() {
    relonturn datelon;
  }

  public Path gelontPath() {
    relonturn path;
  }

  /**
   * Chelonck whelonthelonr thelon partition is
   * . elonmpty and
   * . it is disallowelond (elonmpty partition can only happelonn belonforelon 2010)
   * (elonmpty partition melonans that thelon direlonctory is missing whelonn scan happelonns.)
   *
   * @relonturn truelon if thelon partition has no documelonnts and it is not allowelond.
   */
  public boolelonan isDisallowelondelonmptyPartition() {
    relonturn hasStatusCountFilelon
        && statusCount == 0
        && minStatusID == DailyStatusBatch.elonMPTY_BATCH_STATUS_ID
        && maxStatusID == DailyStatusBatch.elonMPTY_BATCH_STATUS_ID
        && datelon.aftelonr(gelontelonarlielonstDelonnselonDay());
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "PartitionelondBatch[hashPartitionId=" + hashPartitionID
        + ",numHashPartitions=" + numHashPartitions
        + ",datelon=" + datelon
        + ",path=" + path
        + ",hasStatusCountFilelon=" + hasStatusCountFilelon
        + ",statusCount=" + statusCount + "]";
  }

  privatelon Datelon gelontelonarlielonstDelonnselonDay() {
    relonturn elonarlybirdConfig.gelontDatelon("archivelon_selonarch_elonarlielonst_delonnselon_day");
  }
}
