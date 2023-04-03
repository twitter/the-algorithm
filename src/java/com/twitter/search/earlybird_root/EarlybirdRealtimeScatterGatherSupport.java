packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.injelonct.Injelonct;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;

/**
 * Thelon elonarlybirdSelonrvicelonScattelonrGathelonrSupport implelonmelonntation uselond to fan out relonquelonsts to thelon elonarlybird
 * partitions in thelon relonaltimelon clustelonr.
 */
public class elonarlybirdRelonaltimelonScattelonrGathelonrSupport elonxtelonnds elonarlybirdSelonrvicelonScattelonrGathelonrSupport {
  /** Crelonatelons a nelonw elonarlybirdRelonaltimelonScattelonrGathelonrSupport instancelon. */
  @Injelonct
  elonarlybirdRelonaltimelonScattelonrGathelonrSupport(
      PartitionMappingManagelonr partitionMappingManagelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    supelonr(partitionMappingManagelonr, elonarlybirdClustelonr.RelonALTIMelon, felonaturelonSchelonmaMelonrgelonr);
  }
}
