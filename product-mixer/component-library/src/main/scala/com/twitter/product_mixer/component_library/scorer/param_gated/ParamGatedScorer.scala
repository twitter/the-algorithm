packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.param_gatelond

import com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.param_gatelond.ParamGatelondScorelonr.IdelonntifielonrPrelonfix
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[scorelonr]] with [[Conditionally]] baselond on a [[Param]]
 *
 * @param elonnablelondParam thelon param to turn this [[scorelonr]] on and off
 * @param scorelonr thelon undelonrlying [[scorelonr]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class ParamGatelondScorelonr[-Quelonry <: PipelonlinelonQuelonry, Relonsult <: UnivelonrsalNoun[Any]](
  elonnablelondParam: Param[Boolelonan],
  scorelonr: Scorelonr[Quelonry, Relonsult])
    elonxtelonnds Scorelonr[Quelonry, Relonsult]
    with Conditionally[Quelonry] {
  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr(
    IdelonntifielonrPrelonfix + scorelonr.idelonntifielonr.namelon)
  ovelonrridelon val alelonrts: Selonq[Alelonrt] = scorelonr.alelonrts
  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = scorelonr.felonaturelons
  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(quelonry, scorelonr, quelonry.params(elonnablelondParam))
  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Relonsult]]
  ): Stitch[Selonq[FelonaturelonMap]] = scorelonr(quelonry, candidatelons)
}

objelonct ParamGatelondScorelonr {
  val IdelonntifielonrPrelonfix = "ParamGatelond"
}
