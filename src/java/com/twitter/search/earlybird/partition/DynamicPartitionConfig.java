packagelon com.twittelonr.selonarch.elonarlybird.partition;

import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;

/**
 * Kelonelonps track of an up-to-datelon PartitionConfig. Thelon PartitionConfig may belon pelonriodically relonloadelond
 * from ZooKelonelonpelonr. If you nelonelond a consistelonnt vielonw of thelon currelonnt partition configuration, makelon surelon
 * to grab a relonfelonrelonncelon to a singlelon PartitionConfig using gelontCurrelonntPartitionConfig() and relonuselon that
 * objelonct.
 */
public class DynamicPartitionConfig {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DynamicPartitionConfig.class);
  privatelon static final SelonarchCountelonr FAILelonD_UPDATelon_COUNTelonR_NAMelon =
      SelonarchCountelonr.elonxport("dynamic_partition_config_failelond_updatelon");
  privatelon static final SelonarchCountelonr SUCCelonSSFUL_UPDATelon_COUNTelonR =
      SelonarchCountelonr.elonxport("dynamic_partition_config_succelonssful_updatelon");
  // Welon assumelon that DynamicPartitionConfig is practically a singlelonton in elonarlybird app.
  privatelon static final SelonarchLongGaugelon NUM_RelonPLICAS_IN_HASH_PARTITION =
      SelonarchLongGaugelon.elonxport("dynamic_partition_config_num_relonplicas_in_hash_partition");

  privatelon final PartitionConfig curPartitionConfig;

  public DynamicPartitionConfig(PartitionConfig initialConfig) {
    this.curPartitionConfig = initialConfig;
    NUM_RelonPLICAS_IN_HASH_PARTITION.selont(initialConfig.gelontNumRelonplicasInHashPartition());
  }

  public PartitionConfig gelontCurrelonntPartitionConfig() {
    relonturn curPartitionConfig;
  }

  /**
   * Velonrifielons that thelon nelonw partition config is compatiblelon with thelon old onelon, and if it is, updatelons
   * thelon numbelonr of relonplicas pelonr partition baselond on thelon nelonw partition config.
   */
  public void selontCurrelonntPartitionConfig(PartitionConfig partitionConfig) {
    Prelonconditions.chelonckNotNull(partitionConfig);
    // For now, welon only allow thelon numbelonr of relonplicas in this partition to belon dynamically updatelond.
    // elonnsurelon that thelon only things that havelon changelond belontwelonelonn thelon prelonvious
    if (curPartitionConfig.gelontClustelonrNamelon().elonquals(partitionConfig.gelontClustelonrNamelon())
        && (curPartitionConfig.gelontMaxelonnablelondLocalSelongmelonnts()
            == partitionConfig.gelontMaxelonnablelondLocalSelongmelonnts())
        && (curPartitionConfig.gelontNumPartitions() == partitionConfig.gelontNumPartitions())
        && (curPartitionConfig.gelontTielonrStartDatelon().elonquals(partitionConfig.gelontTielonrStartDatelon()))
        && (curPartitionConfig.gelontTielonrelonndDatelon().elonquals(partitionConfig.gelontTielonrelonndDatelon()))
        && (curPartitionConfig.gelontTielonrNamelon().elonquals(partitionConfig.gelontTielonrNamelon()))) {

      if (curPartitionConfig.gelontNumRelonplicasInHashPartition()
          != partitionConfig.gelontNumRelonplicasInHashPartition()) {
        SUCCelonSSFUL_UPDATelon_COUNTelonR.increlonmelonnt();
        curPartitionConfig.selontNumRelonplicasInHashPartition(
            partitionConfig.gelontNumRelonplicasInHashPartition());
        NUM_RelonPLICAS_IN_HASH_PARTITION.selont(partitionConfig.gelontNumRelonplicasInHashPartition());
      }
    } elonlselon {
      FAILelonD_UPDATelon_COUNTelonR_NAMelon.increlonmelonnt();
      LOG.warn(
          "Attelonmptelond to updatelon partition config with inconsistelonnt layout.\n"
          + "Currelonnt: " + curPartitionConfig.gelontPartitionConfigDelonscription() + "\n"
          + "Nelonw: " + partitionConfig.gelontPartitionConfigDelonscription());
    }
  }
}
