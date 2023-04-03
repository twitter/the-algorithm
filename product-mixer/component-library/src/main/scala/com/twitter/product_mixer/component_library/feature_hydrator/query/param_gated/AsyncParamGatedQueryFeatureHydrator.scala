packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.param_gatelond

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.AsyncHydrator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[QuelonryFelonaturelonHydrator]] with [[Conditionally]] baselond on a [[Param]] that hydratelons asynchronously for felonaturelons
 * to belon belonforelon thelon stelonp idelonntifielond in [[hydratelonBelonforelon]]
 *
 * @param elonnablelondParam thelon param to turn this [[QuelonryFelonaturelonHydrator]] on and off
 * @param hydratelonBelonforelon thelon [[PipelonlinelonStelonpIdelonntifielonr]] stelonp to makelon surelon this felonaturelon is hydratelond belonforelon.
 * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 * @tparam Relonsult Thelon typelon of thelon candidatelons
 */
caselon class AsyncParamGatelondQuelonryFelonaturelonHydrator[
  -Quelonry <: PipelonlinelonQuelonry,
  Relonsult <: UnivelonrsalNoun[Any]
](
  elonnablelondParam: Param[Boolelonan],
  ovelonrridelon val hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
  quelonryFelonaturelonHydrator: QuelonryFelonaturelonHydrator[Quelonry])
    elonxtelonnds QuelonryFelonaturelonHydrator[Quelonry]
    with Conditionally[Quelonry]
    with AsyncHydrator {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    "AsyncParamGatelond" + quelonryFelonaturelonHydrator.idelonntifielonr.namelon)

  ovelonrridelon val alelonrts: Selonq[Alelonrt] = quelonryFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = quelonryFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf onlyIf(quelonry: Quelonry): Boolelonan =
    Conditionally.and(quelonry, quelonryFelonaturelonHydrator, quelonry.params(elonnablelondParam))

  ovelonrridelon delonf hydratelon(quelonry: Quelonry): Stitch[FelonaturelonMap] = quelonryFelonaturelonHydrator.hydratelon(quelonry)
}
