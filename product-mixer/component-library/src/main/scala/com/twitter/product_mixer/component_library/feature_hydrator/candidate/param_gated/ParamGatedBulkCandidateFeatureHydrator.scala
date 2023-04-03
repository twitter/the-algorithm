packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond

import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond.ParamGatelondBulkCandidatelonFelonaturelonHydrator.IdelonntifielonrPrelonfix
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[BulkCandidatelonFelonaturelonHydrator]] with [[Conditionally]] baselond on a [[Param]]
 *
 * @param elonnablelondParam thelon param to turn this [[BulkCandidatelonFelonaturelonHydrator]] on and off
 * @param bulkCandidatelonFelonaturelonHydrator thelon undelonrlying [[BulkCandidatelonFelonaturelonHydrator]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class ParamGatelondBulkCandidatelonFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry,
  Relonsult <: UnivelonrsalNoun[Any]
](
  elonnablelondParam: Param[Boolelonan],
  bulkCandidatelonFelonaturelonHydrator: BulkCandidatelonFelonaturelonHydrator[Quelonry, Relonsult])
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[Quelonry, Relonsult]
    with Conditionally[Quelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    IdelonntifielonrPrelonfix + bulkCandidatelonFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = bulkCandidatelonFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = bulkCandidatelonFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(quelonry, bulkCandidatelonFelonaturelonHydrator, quelonry.params(elonnablelondParam))

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Relonsult]]
  ): Stitch[Selonq[FelonaturelonMap]] = bulkCandidatelonFelonaturelonHydrator(quelonry, candidatelons)
}

objelonct ParamGatelondBulkCandidatelonFelonaturelonHydrator {
  val IdelonntifielonrPrelonfix = "ParamGatelond"
}
