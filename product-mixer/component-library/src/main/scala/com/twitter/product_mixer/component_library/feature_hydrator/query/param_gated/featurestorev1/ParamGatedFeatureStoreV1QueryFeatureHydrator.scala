packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.param_gatelond.felonaturelonstorelonv1

import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.BaselonFelonaturelonStorelonV1QuelonryFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.felonaturelonstorelonv1.FelonaturelonStorelonV1DynamicClielonntBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.felonaturelonstorelonv1.FelonaturelonStorelonV1QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[QuelonryFelonaturelonHydrator]] with [[Conditionally]] baselond on a [[Param]]
 *
 * @param elonnablelondParam thelon param to turn this [[QuelonryFelonaturelonHydrator]] on and off
 * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class ParamGatelondFelonaturelonStorelonV1QuelonryFelonaturelonHydrator[
  Quelonry <: PipelonlinelonQuelonry,
  Relonsult <: UnivelonrsalNoun[Any]
](
  elonnablelondParam: Param[Boolelonan],
  quelonryFelonaturelonHydrator: FelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry])
    elonxtelonnds FelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry]
    with Conditionally[Quelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    "ParamGatelond" + quelonryFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = quelonryFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[BaselonFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, _]] =
    quelonryFelonaturelonHydrator.felonaturelons

  ovelonrridelon val clielonntBuildelonr: FelonaturelonStorelonV1DynamicClielonntBuildelonr =
    quelonryFelonaturelonHydrator.clielonntBuildelonr
  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(quelonry, quelonryFelonaturelonHydrator, quelonry.params(elonnablelondParam))

  ovelonrridelon delonf hydratelon(quelonry: Quelonry): Stitch[FelonaturelonMap] = quelonryFelonaturelonHydrator.hydratelon(quelonry)
}
