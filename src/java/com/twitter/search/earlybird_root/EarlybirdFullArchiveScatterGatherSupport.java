packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.injelonct.Injelonct;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;

/**
 * Thelon elonarlybirdSelonrvicelonScattelonrGathelonrSupport implelonmelonntation uselond to fan out relonquelonsts to thelon elonarlybird
 * partitions in thelon full archivelon tielonrs.
 */
public class elonarlybirdFullArchivelonScattelonrGathelonrSupport elonxtelonnds elonarlybirdSelonrvicelonScattelonrGathelonrSupport {
  /** Crelonatelons a nelonw elonarlybirdFullArchivelonScattelonrGathelonrSupport instancelon. */
  @Injelonct
  elonarlybirdFullArchivelonScattelonrGathelonrSupport(
      PartitionMappingManagelonr partitionMappingManagelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    supelonr(partitionMappingManagelonr, elonarlybirdClustelonr.FULL_ARCHIVelon, felonaturelonSchelonmaMelonrgelonr);
  }
}
