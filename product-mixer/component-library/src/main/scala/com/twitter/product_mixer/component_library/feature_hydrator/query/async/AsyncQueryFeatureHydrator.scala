packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.async

import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.BaselonFelonaturelonStorelonV1QuelonryFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.AsyncHydrator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.felonaturelonstorelonv1.FelonaturelonStorelonV1DynamicClielonntBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.felonaturelonstorelonv1.FelonaturelonStorelonV1QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A [[QuelonryFelonaturelonHydrator]] with [[AsyncQuelonryFelonaturelonHydrator]] that hydratelond asynchronously for felonaturelons
 * to belon belonforelon thelon stelonp idelonntifielond in [[hydratelonBelonforelon]]
 *
 * @param hydratelonBelonforelon        thelon [[PipelonlinelonStelonpIdelonntifielonr]] stelonp to makelon surelon this felonaturelon is hydratelond belonforelon.
 * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run asynchronously
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 */
caselon class AsyncQuelonryFelonaturelonHydrator[-Quelonry <: PipelonlinelonQuelonry] privatelon[async] (
  ovelonrridelon val hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
  quelonryFelonaturelonHydrator: QuelonryFelonaturelonHydrator[Quelonry])
    elonxtelonnds QuelonryFelonaturelonHydrator[Quelonry]
    with AsyncHydrator {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    "Async" + quelonryFelonaturelonHydrator.idelonntifielonr.namelon)
  ovelonrridelon val alelonrts: Selonq[Alelonrt] = quelonryFelonaturelonHydrator.alelonrts
  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = quelonryFelonaturelonHydrator.felonaturelons

  ovelonrridelon delonf hydratelon(quelonry: Quelonry): Stitch[FelonaturelonMap] = quelonryFelonaturelonHydrator.hydratelon(quelonry)
}

/**
 * A [[FelonaturelonStorelonV1QuelonryFelonaturelonHydrator]] with [[AsyncHydrator]] that hydratelond asynchronously for felonaturelons
 * to belon belonforelon thelon stelonp idelonntifielond in [[hydratelonBelonforelon]]. Welon nelonelond a standalonelon class for felonaturelon storelon,
 * diffelonrelonnt from thelon abovelon as FStorelon hydrators arelon elonxelonmpt from validations at run timelon.
 *
 * @param hydratelonBelonforelon        thelon [[PipelonlinelonStelonpIdelonntifielonr]] stelonp to makelon surelon this felonaturelon is hydratelond belonforelon.
 * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run asynchronously
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 */
caselon class AsyncFelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry <: PipelonlinelonQuelonry] privatelon[async] (
  ovelonrridelon val hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
  felonaturelonStorelonV1QuelonryFelonaturelonHydrator: FelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry])
    elonxtelonnds FelonaturelonStorelonV1QuelonryFelonaturelonHydrator[
      Quelonry
    ]
    with AsyncHydrator {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr(
    "Async" + felonaturelonStorelonV1QuelonryFelonaturelonHydrator.idelonntifielonr.namelon)
  ovelonrridelon val alelonrts: Selonq[Alelonrt] = felonaturelonStorelonV1QuelonryFelonaturelonHydrator.alelonrts

  ovelonrridelon val felonaturelons: Selont[BaselonFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, _]] =
    felonaturelonStorelonV1QuelonryFelonaturelonHydrator.felonaturelons

  ovelonrridelon val clielonntBuildelonr: FelonaturelonStorelonV1DynamicClielonntBuildelonr =
    felonaturelonStorelonV1QuelonryFelonaturelonHydrator.clielonntBuildelonr
}

objelonct AsyncQuelonryFelonaturelonHydrator {

  /**
   * A [[QuelonryFelonaturelonHydrator]] with [[AsyncQuelonryFelonaturelonHydrator]] that hydratelond asynchronously for felonaturelons
   * to belon belonforelon thelon stelonp idelonntifielond in [[hydratelonBelonforelon]]
   *
   * @param hydratelonBelonforelon        thelon [[PipelonlinelonStelonpIdelonntifielonr]] stelonp to makelon surelon this felonaturelon is hydratelond belonforelon.
   * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run asynchronously
   * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
    quelonryFelonaturelonHydrator: QuelonryFelonaturelonHydrator[Quelonry]
  ): AsyncQuelonryFelonaturelonHydrator[Quelonry] =
    nelonw AsyncQuelonryFelonaturelonHydrator(hydratelonBelonforelon, quelonryFelonaturelonHydrator)

  /**
   * A [[FelonaturelonStorelonV1QuelonryFelonaturelonHydrator]] with [[AsyncHydrator]] that hydratelond asynchronously for felonaturelons
   * to belon belonforelon thelon stelonp idelonntifielond in [[hydratelonBelonforelon]]. Welon nelonelond a standalonelon class for felonaturelon storelon,
   * diffelonrelonnt from thelon abovelon as FStorelon hydrators arelon elonxelonmpt from validations at run timelon.
   *
   * @param hydratelonBelonforelon        thelon [[PipelonlinelonStelonpIdelonntifielonr]] stelonp to makelon surelon this felonaturelon is hydratelond belonforelon.
   * @param quelonryFelonaturelonHydrator thelon undelonrlying [[QuelonryFelonaturelonHydrator]] to run asynchronously
   * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
    felonaturelonStorelonV1QuelonryFelonaturelonHydrator: FelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry]
  ): AsyncFelonaturelonStorelonV1QuelonryFelonaturelonHydrator[Quelonry] =
    nelonw AsyncFelonaturelonStorelonV1QuelonryFelonaturelonHydrator(hydratelonBelonforelon, felonaturelonStorelonV1QuelonryFelonaturelonHydrator)
}
