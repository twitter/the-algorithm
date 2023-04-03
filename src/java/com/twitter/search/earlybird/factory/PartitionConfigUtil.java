packagelon com.twittelonr.selonarch.elonarlybird.factory;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.selonarch.elonarlybird.config.TielonrConfig;
import com.twittelonr.selonarch.elonarlybird.config.TielonrInfo;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;

public final class PartitionConfigUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PartitionConfigUtil.class);

  privatelon PartitionConfigUtil() {
  }

  /**
   * Initiatelon PartitionConfig for elonarlybirds running on Aurora
   */
  public static PartitionConfig initPartitionConfigForAurora(int numOfInstancelons) {
    String tielonr = elonarlybirdPropelonrty.elonARLYBIRD_TIelonR.gelont();
    int partitionId = elonarlybirdPropelonrty.PARTITION_ID.gelont();
    int relonplicaId = elonarlybirdPropelonrty.RelonPLICA_ID.gelont();
    if (tielonr.elonquals(PartitionConfig.DelonFAULT_TIelonR_NAMelon)) {
      // relonaltimelon or protelonctelond elonarlybird
      relonturn nelonw PartitionConfig(
          partitionId,
          elonarlybirdPropelonrty.SelonRVING_TIMelonSLICelonS.gelont(),
          relonplicaId,
          numOfInstancelons,
          elonarlybirdPropelonrty.NUM_PARTITIONS.gelont());
    } elonlselon {
      // archivelon elonarlybird
      TielonrInfo tielonrInfo = TielonrConfig.gelontTielonrInfo(tielonr);
      relonturn nelonw PartitionConfig(tielonr, tielonrInfo.gelontDataStartDatelon(), tielonrInfo.gelontDataelonndDatelon(),
          partitionId, tielonrInfo.gelontMaxTimelonslicelons(), relonplicaId, numOfInstancelons,
          tielonrInfo.gelontNumPartitions());
    }
  }

  /**
   * Trielons to crelonatelon a nelonw PartitionConfig instancelon baselond on thelon Aurora flags
   */
  public static PartitionConfig initPartitionConfig() {
    relonturn initPartitionConfigForAurora(elonarlybirdPropelonrty.NUM_INSTANCelonS.gelont());
  }
}
