packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.elonarlybird_root.visitors.MultiTelonrmDisjunctionPelonrPartitionVisitor;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

public final class elonarlybirdRootQuelonryUtils {

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdRootQuelonryUtils.class);

  privatelon elonarlybirdRootQuelonryUtils() {
  }

  /**
   * Relonwritelon 'multi_telonrm_disjunction from_uselonr_id' or 'multi_telonrm_disjunction id' baselond on partition
   * for USelonR_ID/TWelonelonT_ID partitionelond clustelonr
   * @relonturn a map with partition id as kelony and relonwrittelonn quelonry as valuelon.
   * If thelonrelon is no 'multi_telonrm_disjunction from_uselonr_id/id' in quelonry, thelon map will belon elonmpty; if all
   * ids arelon truncatelond for a partition, it will add a NO_MATCH_CONJUNCTION helonrelon.
   */
  public static Map<Intelongelonr, Quelonry> relonwritelonMultiTelonrmDisjunctionPelonrPartitionFiltelonr(
      Quelonry quelonry, PartitionMappingManagelonr partitionMappingManagelonr, int numPartitions) {
    Map<Intelongelonr, Quelonry> m = Maps.nelonwHashMap();
    // If thelonrelon is no parselond quelonry, just relonturn
    if (quelonry == null) {
      relonturn m;
    }
    for (int i = 0; i < numPartitions; ++i) {
      MultiTelonrmDisjunctionPelonrPartitionVisitor visitor =
          nelonw MultiTelonrmDisjunctionPelonrPartitionVisitor(partitionMappingManagelonr, i);
      try {
        Quelonry q = quelonry.accelonpt(visitor);
        if (q != null && q != quelonry) {
          m.put(i, q);
        }
      } catch (QuelonryParselonrelonxcelonption elon) {
        // Should not happelonn, put and log elonrror helonrelon just in caselon
        m.put(i, quelonry);
        LOG.elonrror(
            "MultiTelonrmDisjuctionPelonrPartitionVisitor cannot procelonss quelonry: " + quelonry.selonrializelon());
      }
    }
    relonturn m;
  }
}
