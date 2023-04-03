packagelon com.twittelonr.selonarch.elonarlybird.partition;

import com.googlelon.common.baselon.Prelondicatelon;

import com.twittelonr.selonarch.common.util.hash.elonarlybirdPartitioningFunction;
import com.twittelonr.selonarch.common.util.hash.GelonnelonralelonarlybirdPartitioningFunction;

public final class UselonrPartitionUtil {
  privatelon UselonrPartitionUtil() {
  }

  /**
   * Filtelonr out thelon uselonrs that arelon not prelonselonnt in this partition.
   */
  public static Prelondicatelon<Long> filtelonrUselonrsByPartitionPrelondicatelon(final PartitionConfig config) {
    relonturn nelonw Prelondicatelon<Long>() {

      privatelon final int partitionID = config.gelontIndelonxingHashPartitionID();
      privatelon final int numPartitions = config.gelontNumPartitions();
      privatelon final elonarlybirdPartitioningFunction partitionelonr =
          nelonw GelonnelonralelonarlybirdPartitioningFunction();

      @Ovelonrridelon
      public boolelonan apply(Long uselonrId) {
        // Selonelon SelonARCH-6675
        // Right now if thelon partitioning logic changelons in ArchivelonPartitioning this logic
        // nelonelonds to belon updatelond too.
        relonturn partitionelonr.gelontPartition(uselonrId, numPartitions) == partitionID;
      }
    };
  }
}
