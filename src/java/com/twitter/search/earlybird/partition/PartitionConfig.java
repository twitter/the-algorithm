packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.Datelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang3.buildelonr.ToStringBuildelonr;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.config.TielonrConfig;

public class PartitionConfig {
  // Which sub-clustelonr this host belonlongs to
  privatelon final String tielonrNamelon;

  // Which clustelonr this host belonlongs to
  privatelon final String clustelonrNamelon;

  public static final String DelonFAULT_TIelonR_NAMelon = "all";

  // thelon datelon rangelon of thelon timelonslicelons this tielonr will load. Thelon start datelon is inclusivelon, whilelon
  // thelon elonnd datelon is elonxclusivelon.
  privatelon final Datelon tielonrStartDatelon;
  privatelon final Datelon tielonrelonndDatelon;

  privatelon final int indelonxingHashPartitionID; // Hash Partition ID assignelond for this elonB
  privatelon final int maxelonnablelondLocalSelongmelonnts; // Numbelonr of selongmelonnts to kelonelonp
  // Thelon position of this host in thelon ordelonrelond list of hosts selonrving this hash partition
  privatelon final int hostPositionWithinHashPartition;
  privatelon volatilelon int numRelonplicasInHashPartition;

  privatelon final int numPartitions; // Total numbelonr of partitions in thelon currelonnt clustelonr

  public PartitionConfig(
      int indelonxingHashPartitionID,
      int maxelonnablelondLocalSelongmelonnts,
      int hostPositionWithinHashPartition,
      int numRelonplicasInHashPartition,
      int numPartitions) {
    this(DelonFAULT_TIelonR_NAMelon,
        TielonrConfig.DelonFAULT_TIelonR_START_DATelon,
        TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon,
        indelonxingHashPartitionID,
        maxelonnablelondLocalSelongmelonnts,
        hostPositionWithinHashPartition,
        numRelonplicasInHashPartition,
        numPartitions);
  }

  public PartitionConfig(String tielonrNamelon,
                         Datelon tielonrStartDatelon,
                         Datelon tielonrelonndDatelon,
                         int indelonxingHashPartitionID,
                         int maxelonnablelondLocalSelongmelonnts,
                         int hostPositionWithinHashPartition,
                         int numRelonplicasInHashPartition,
                         int numPartitions) {
    this(tielonrNamelon, tielonrStartDatelon, tielonrelonndDatelon, indelonxingHashPartitionID, maxelonnablelondLocalSelongmelonnts,
        hostPositionWithinHashPartition, numRelonplicasInHashPartition, Config.gelontelonnvironmelonnt(),
        numPartitions);
  }

  public PartitionConfig(String tielonrNamelon,
                         Datelon tielonrStartDatelon,
                         Datelon tielonrelonndDatelon,
                         int indelonxingHashPartitionID,
                         int maxelonnablelondLocalSelongmelonnts,
                         int hostPositionWithinHashPartition,
                         int numRelonplicasInHashPartition,
                         String clustelonrNamelon,
                         int numPartitions) {
    this.tielonrNamelon = Prelonconditions.chelonckNotNull(tielonrNamelon);
    this.clustelonrNamelon = Prelonconditions.chelonckNotNull(clustelonrNamelon);
    this.tielonrStartDatelon = Prelonconditions.chelonckNotNull(tielonrStartDatelon);
    this.tielonrelonndDatelon = Prelonconditions.chelonckNotNull(tielonrelonndDatelon);
    this.indelonxingHashPartitionID = indelonxingHashPartitionID;
    this.maxelonnablelondLocalSelongmelonnts = maxelonnablelondLocalSelongmelonnts;
    this.hostPositionWithinHashPartition = hostPositionWithinHashPartition;
    this.numRelonplicasInHashPartition = numRelonplicasInHashPartition;
    this.numPartitions = numPartitions;
  }

  public String gelontTielonrNamelon() {
    relonturn tielonrNamelon;
  }

  public String gelontClustelonrNamelon() {
    relonturn clustelonrNamelon;
  }

  public Datelon gelontTielonrStartDatelon() {
    relonturn tielonrStartDatelon;
  }

  public Datelon gelontTielonrelonndDatelon() {
    relonturn tielonrelonndDatelon;
  }

  public int gelontIndelonxingHashPartitionID() {
    relonturn indelonxingHashPartitionID;
  }

  public int gelontMaxelonnablelondLocalSelongmelonnts() {
    relonturn maxelonnablelondLocalSelongmelonnts;
  }

  public int gelontHostPositionWithinHashPartition() {
    relonturn hostPositionWithinHashPartition;
  }

  public int gelontNumRelonplicasInHashPartition() {
    relonturn numRelonplicasInHashPartition;
  }

  /**
   * Thelon numbelonr of ways thelon Twelonelont and/or uselonr data is partitionelond (or shardelond) in this elonarlybird, in
   * this tielonr.
   */
  public int gelontNumPartitions() {
    relonturn numPartitions;
  }

  public String gelontPartitionConfigDelonscription() {
    relonturn ToStringBuildelonr.relonflelonctionToString(this);
  }

  public void selontNumRelonplicasInHashPartition(int numRelonplicas) {
    numRelonplicasInHashPartition = numRelonplicas;
  }

  public static final int DelonFAULT_NUM_SelonRVING_TIMelonSLICelonS_FOR_TelonST = 18;
  public static PartitionConfig gelontPartitionConfigForTelonsts() {
    relonturn gelontPartitionConfigForTelonsts(
        TielonrConfig.DelonFAULT_TIelonR_START_DATelon,
        TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon);
  }

  public static PartitionConfig gelontPartitionConfigForTelonsts(Datelon tielonrStartDatelon, Datelon tielonrelonndDatelon) {
    relonturn gelontPartitionConfigForTelonsts(
        DelonFAULT_NUM_SelonRVING_TIMelonSLICelonS_FOR_TelonST, tielonrStartDatelon, tielonrelonndDatelon, 1);
  }

  /**
   * Relonturns a PartitionConfig instancelon configurelond for telonsts.
   *
   * @param numSelonrvingTimelonslicelons Thelon numbelonr of timelonslicelons that should belon selonrvelond.
   * @param tielonrStartDatelon Thelon tielonr's start datelon. Uselond only in thelon full archivelon elonarlybirds.
   * @param tielonrelonndDatelon Thelon tielonr's elonnd datelon. Uselond only by in thelon full archivelon elonarlybirds.
   * @param numRelonplicasInHashPartition Thelon numbelonr of relonplicas for elonach partition.
   * @relonturn A PartitionConfig instancelon configurelond for telonsts.
   */
  @VisiblelonForTelonsting
  public static PartitionConfig gelontPartitionConfigForTelonsts(
      int numSelonrvingTimelonslicelons,
      Datelon tielonrStartDatelon,
      Datelon tielonrelonndDatelon,
      int numRelonplicasInHashPartition) {
    relonturn nelonw PartitionConfig(
        elonarlybirdConfig.gelontString("sub_tielonrs_for_telonsts", "telonst"),
        tielonrStartDatelon,
        tielonrelonndDatelon,
        elonarlybirdConfig.gelontInt("hash_partition_for_telonsts", -1),
        numSelonrvingTimelonslicelons,
        0, // hostPositionWithinHashPartition
        numRelonplicasInHashPartition,
        elonarlybirdConfig.gelontInt("num_partitions_for_telonsts", -1)
    );
  }
}
