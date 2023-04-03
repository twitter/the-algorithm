packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond

import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.param_gatelond.ParamGatelondCandidatelonFelonaturelonHydrator.IdelonntifielonrPrelonfix
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[CandidatelonFelonaturelonHydrator]] with [[Conditionally]] baselond on a [[Param]]
 *
 * @param elonnablelondParam thelon param to turn this [[CandidatelonFelonaturelonHydrator]] on and off
 * @param candidatelonFelonaturelonHydrator thelon undelonrlying [[CandidatelonFelonaturelonHydrator]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class ParamGatelondCandidatelonFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry,
  -Relonsult <: UnivelonrsalNoun[Any]
](
  elonnablelondParam: Param[Boolelonan],
  candidatelonFelonaturelonHydrator: CandidatelonFelonaturelonHydrator[Quelonry, Relonsult])
    elonxtelonnds CandidatelonFelonaturelonHydrator[Quelonry, Relonsult]
    with Conditionally[Quelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    IdelonntifielonrPrelonfix + candidatelonFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = candidatelonFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = candidatelonFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(quelonry, candidatelonFelonaturelonHydrator, quelonry.params(elonnablelondParam))

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: Relonsult,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = candidatelonFelonaturelonHydrator.apply(quelonry, candidatelon, elonxistingFelonaturelons)
}

objelonct ParamGatelondCandidatelonFelonaturelonHydrator {
  val IdelonntifielonrPrelonfix = "ParamGatelond"
}
