packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.reloncommelonndation

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ReloncommelonndationPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Arrow

/**
 * A Reloncommelonndation Pipelonlinelon
 *
 * This is an abstract class, as welon only construct thelonselon via thelon [[ReloncommelonndationPipelonlinelonBuildelonr]].
 *
 * A [[ReloncommelonndationPipelonlinelon]] is capablelon of procelonssing relonquelonsts (quelonrielons) and relonturning relonsponselons (relonsults)
 * in thelon correlonct format to direlonctly selonnd to uselonrs.
 *
 * @tparam Quelonry thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Candidatelon thelon typelon of thelon candidatelons
 * @tparam Relonsult thelon final marshallelond relonsult typelon
 */
abstract class ReloncommelonndationPipelonlinelon[
  Quelonry <: PipelonlinelonQuelonry,
  Candidatelon <: UnivelonrsalNoun[Any],
  Relonsult]
    elonxtelonnds Pipelonlinelon[Quelonry, Relonsult] {
  ovelonrridelon privatelon[corelon] val config: ReloncommelonndationPipelonlinelonConfig[Quelonry, Candidatelon, _, Relonsult]
  ovelonrridelon val arrow: Arrow[Quelonry, ReloncommelonndationPipelonlinelonRelonsult[Candidatelon, Relonsult]]
  ovelonrridelon val idelonntifielonr: ReloncommelonndationPipelonlinelonIdelonntifielonr
}
