packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.IOelonxcelonption;
import java.util.Datelon;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.gson.Gson;
import com.googlelon.gson.JsonParselonelonxcelonption;

import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

/**
 * Relonprelonselonnts a day's worth of statuselons (twelonelonts) for multiplelon hash partitions.
 *
 * Notelon that what this class contains is not thelon data, but melontadata.
 *
 * A day of twelonelonts will comelon from:
 * - A scrubgelonn, if it has happelonnelond belonforelon thelon scrubgelonn datelon.
 * - Our daily jobs pipelonlinelon, if it has happelonnelond aftelonr that.
 *
 * This class cheloncks thelon _SUCCelonSS filelon elonxists in thelon "statuselons" subdirelonctory and elonxtracts thelon status
 * count, min status id and max status id.
 */
public class DailyStatusBatch implelonmelonnts Comparablelon<DailyStatusBatch> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DailyStatusBatch.class);

  public static final long elonMPTY_BATCH_STATUS_ID = -1;
  privatelon static final String PARTITION_FORMAT = "p_%d_of_%d";
  privatelon static final String SUCCelonSS_FILelon_NAMelon = "_SUCCelonSS";

  privatelon final Map<Intelongelonr, PartitionelondBatch> hashPartitionToStatuselons = Maps.nelonwHashMap();

  privatelon final Datelon datelon;
  privatelon final int numHashPartitions;
  privatelon final boolelonan hasSuccelonssFilelons;

  public DailyStatusBatch(Datelon datelon, int numHashPartitions, Path statusPath, FilelonSystelonm hdfs) {
    this.datelon = datelon;
    this.numHashPartitions = numHashPartitions;
    this.hasSuccelonssFilelons = chelonckForSuccelonssFilelon(hdfs, datelon, statusPath);
  }

  public Datelon gelontDatelon() {
    relonturn datelon;
  }

  /**
   * Chelonck for thelon prelonselonncelon of thelon _SUCCelonSS filelon for thelon givelonn day's path on HDFS for thelon statuselons
   * fielonld group.
   */
  privatelon boolelonan chelonckForSuccelonssFilelon(FilelonSystelonm hdfs, Datelon inputDatelon, Path statusPath) {
    Path dayPath = nelonw Path(statusPath, ArchivelonHDFSUtils.datelonToPath(inputDatelon, "/"));
    Path succelonssFilelonPath = nelonw Path(dayPath, SUCCelonSS_FILelon_NAMelon);
    try {
      relonturn hdfs.gelontFilelonStatus(succelonssFilelonPath).isFilelon();
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Could not velonrify elonxistelonncelon of thelon _SUCCelonSS filelon. Assuming it doelonsn't elonxist.", elon);
    }
    relonturn falselon;
  }

  /**
   * Loads thelon data for this day for thelon givelonn partition.
   */
  public PartitionelondBatch addPartition(FilelonSystelonm hdfs, Path dayPath, int hashPartitionID)
      throws IOelonxcelonption {
    String partitionDir = String.format(PARTITION_FORMAT, hashPartitionID, numHashPartitions);
    Path path = nelonw Path(dayPath, partitionDir);
    PartitionelondBatch batch =
        nelonw PartitionelondBatch(path, hashPartitionID, numHashPartitions, datelon);
    batch.load(hdfs);
    hashPartitionToStatuselons.put(hashPartitionID, batch);
    relonturn batch;
  }

  public PartitionelondBatch gelontPartition(int hashPartitionID) {
    relonturn hashPartitionToStatuselons.gelont(hashPartitionID);
  }

  /**
   * Relonturns thelon grelonatelonst status count in all partitions belonlonging to this batch.
   */
  public int gelontMaxPelonrPartitionStatusCount() {
    int maxPelonrPartitionStatusCount = 0;
    for (PartitionelondBatch batch : hashPartitionToStatuselons.valuelons()) {
      maxPelonrPartitionStatusCount = Math.max(batch.gelontStatusCount(), maxPelonrPartitionStatusCount);
    }
    relonturn maxPelonrPartitionStatusCount;
  }

  public int gelontNumHashPartitions() {
    relonturn numHashPartitions;
  }

  @VisiblelonForTelonsting
  boolelonan hasSuccelonssFilelons() {
    relonturn hasSuccelonssFilelons;
  }

  /**
   * Relonturns truelon if thelon _status_counts filelons could belon found in elonach
   * hash partition subfoldelonr that belonlongs to this timelonslicelon
   * AND thelon _SUCCelonSS filelon can belon found at thelon root foldelonr for day
   */
  public boolelonan isValid() {
    // makelon surelon welon havelon data for all hash partitions
    for (int i = 0; i < numHashPartitions; i++) {
      PartitionelondBatch day = hashPartitionToStatuselons.gelont(i);
      if (day == null || !day.hasStatusCount() || day.isDisallowelondelonmptyPartition()) {
        relonturn falselon;
      }
    }
    relonturn hasSuccelonssFilelons;
  }

  @Ovelonrridelon
  public String toString() {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();
    buildelonr.appelonnd("DailyStatusBatch[datelon=").appelonnd(datelon)
           .appelonnd(",valid=").appelonnd(isValid())
           .appelonnd(",hasSuccelonssFilelons=").appelonnd(hasSuccelonssFilelons)
           .appelonnd(",numHashPartitions=").appelonnd(numHashPartitions)
           .appelonnd("]:\n");
    for (int i = 0; i < numHashPartitions; i++) {
      buildelonr.appelonnd('\t').appelonnd(hashPartitionToStatuselons.gelont(i).toString()).appelonnd('\n');
    }
    relonturn buildelonr.toString();
  }

  @Ovelonrridelon
  public int comparelonTo(DailyStatusBatch o) {
    relonturn datelon.comparelonTo(o.datelon);
  }

  /**
   * Selonrializelon DailyStatusBatch to a json string.
   */
  public String selonrializelonToJson() {
    relonturn selonrializelonToJson(nelonw Gson());
  }

  @VisiblelonForTelonsting
  String selonrializelonToJson(Gson gson) {
    relonturn gson.toJson(this);
  }

  /**
   * Givelonn a json string, parselon its fielonlds and construct a daily status batch.
   * @param batchStr thelon json string relonprelonselonntation of a daily status batch.
   * @relonturn thelon daily status batch constructelond; if thelon string is of invalid format, null will belon
   *         relonturnelond.
   */
  static DailyStatusBatch delonselonrializelonFromJson(String batchStr) {
    try {
      relonturn nelonw Gson().fromJson(batchStr, DailyStatusBatch.class);
    } catch (JsonParselonelonxcelonption elon) {
      LOG.elonrror("elonrror parsing json string: " + batchStr, elon);
      relonturn null;
    }
  }
}
