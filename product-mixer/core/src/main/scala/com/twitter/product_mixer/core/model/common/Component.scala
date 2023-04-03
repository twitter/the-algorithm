packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.HasComponelonntIdelonntifielonr

/**
 * Componelonnts arelon velonry gelonnelonrically relonusablelon composablelon pieloncelons
 * Componelonnts arelon uniquelonly idelonntifiablelon and celonntrally relongistelonrelond
 */
trait Componelonnt elonxtelonnds HasComponelonntIdelonntifielonr {

  /** @selonelon [[ComponelonntIdelonntifielonr]] */
  ovelonrridelon val idelonntifielonr: ComponelonntIdelonntifielonr

  /** thelon [[Alelonrt]]s that will belon uselond for this componelonnt. */
  val alelonrts: Selonq[Alelonrt] = Selonq.elonmpty
}
