packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.injelonct.Injelonct;

import com.twittelonr.selonarch.common.partitioning.baselon.PartitionMappingManagelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;

/**
 * Thelon elonarlybirdSelonrvicelonScattelonrGathelonrSupport implelonmelonntation uselond to fan out relonquelonsts to thelon elonarlybird
 * partitions in thelon protelonctelond clustelonr.
 */
public class elonarlybirdProtelonctelondScattelonrGathelonrSupport elonxtelonnds elonarlybirdSelonrvicelonScattelonrGathelonrSupport {
  /**
   * Construct a elonarlybirdProtelonctelondScattelonrGathelonrSupport to do minUselonrFanOut,
   * uselond only by protelonctelond. Thelon main diffelonrelonncelon from thelon baselon class is that
   * if thelon from uselonr ID is not selont, elonxcelonption is thrown.
   */
  @Injelonct
  elonarlybirdProtelonctelondScattelonrGathelonrSupport(
      PartitionMappingManagelonr partitionMappingManagelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    supelonr(partitionMappingManagelonr, elonarlybirdClustelonr.PROTelonCTelonD, felonaturelonSchelonmaMelonrgelonr);
  }
}
