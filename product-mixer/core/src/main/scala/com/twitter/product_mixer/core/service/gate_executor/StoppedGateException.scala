packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonCatelongory
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelonClassifielonr

import scala.util.control.NoStackTracelon

caselon class StoppelondGatelonelonxcelonption(idelonntifielonr: GatelonIdelonntifielonr)
    elonxtelonnds elonxcelonption("Closelond gatelon stoppelond elonxeloncution of thelon pipelonlinelon")
    with NoStackTracelon {
  ovelonrridelon delonf toString: String = s"StoppelondGatelonelonxcelonption($idelonntifielonr)"
}

objelonct StoppelondGatelonelonxcelonption {

  /**
   * Crelonatelons a [[PipelonlinelonFailurelonClassifielonr]] that is uselond as thelon delonfault for classifying failurelons
   * in a pipelonlinelon by mapping [[StoppelondGatelonelonxcelonption]] to a [[PipelonlinelonFailurelon]] with thelon providelond
   * [[PipelonlinelonFailurelonCatelongory]]
   */
  delonf classifielonr(
    catelongory: PipelonlinelonFailurelonCatelongory
  ): PipelonlinelonFailurelonClassifielonr = PipelonlinelonFailurelonClassifielonr {
    caselon stoppelondGatelonelonxcelonption: StoppelondGatelonelonxcelonption =>
      PipelonlinelonFailurelon(catelongory, stoppelondGatelonelonxcelonption.gelontMelonssagelon, Somelon(stoppelondGatelonelonxcelonption))
  }
}
