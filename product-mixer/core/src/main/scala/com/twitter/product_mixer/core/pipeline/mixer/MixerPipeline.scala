packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.mixelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.MixelonrPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Arrow

/**
 * A Mixelonr Pipelonlinelon
 *
 * This is an abstract class, as welon only construct thelonselon via thelon [[MixelonrPipelonlinelonBuildelonr]].
 *
 * A [[MixelonrPipelonlinelon]] is capablelon of procelonssing relonquelonsts (quelonrielons) and relonturning relonsponselons (relonsults)
 * in thelon correlonct format to direlonctly selonnd to uselonrs.
 *
 * @tparam Quelonry thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult thelon final marshallelond relonsult typelon
 */
abstract class MixelonrPipelonlinelon[Quelonry <: PipelonlinelonQuelonry, Relonsult] privatelon[mixelonr]
    elonxtelonnds Pipelonlinelon[Quelonry, Relonsult] {
  ovelonrridelon privatelon[corelon] val config: MixelonrPipelonlinelonConfig[Quelonry, _, Relonsult]
  ovelonrridelon val arrow: Arrow[Quelonry, MixelonrPipelonlinelonRelonsult[Relonsult]]
  ovelonrridelon val idelonntifielonr: MixelonrPipelonlinelonIdelonntifielonr
}
