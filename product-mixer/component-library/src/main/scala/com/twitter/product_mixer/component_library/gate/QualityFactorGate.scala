packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.stitch.Stitch

/**
 * A Gatelon that only continuelons if thelon quality factor valuelon of thelon pipelonlinelon is abovelon thelon givelonn
 * threlonshold. This is uselonful for disabling an elonxpelonnsivelon function whelonn thelon pipelonlinelon is undelonr prelonssurelon
 * (quality factor is low).
 */
caselon class QualityFactorGatelon(pipelonlinelonIdelonntifielonr: ComponelonntIdelonntifielonr, threlonshold: Doublelon)
    elonxtelonnds Gatelon[PipelonlinelonQuelonry with HasQualityFactorStatus] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr(
    s"${pipelonlinelonIdelonntifielonr.namelon}QualityFactor")

  ovelonrridelon delonf shouldContinuelon(
    quelonry: PipelonlinelonQuelonry with HasQualityFactorStatus
  ): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.gelontQualityFactorCurrelonntValuelon(pipelonlinelonIdelonntifielonr) >= threlonshold)
}
