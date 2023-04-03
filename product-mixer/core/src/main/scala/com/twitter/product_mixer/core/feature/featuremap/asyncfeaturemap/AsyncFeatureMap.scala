packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap

import com.fastelonrxml.jackson.databind.annotation.JsonSelonrializelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.stitch.Stitch

import scala.collelonction.immutablelon.Quelonuelon

/**
 * An intelonrnal relonprelonselonntation of an async [[FelonaturelonMap]] containing [[Stitch]]s of [[FelonaturelonMap]]s
 * which arelon alrelonady running in thelon background.
 *
 * Async felonaturelons arelon addelond by providing thelon [[PipelonlinelonStelonpIdelonntifielonr]] of thelon [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonBuildelonr.Stelonp Stelonp]]
 * belonforelon which thelon async [[Felonaturelon]]s arelon nelonelondelond, and a [[Stitch]] of thelon async [[FelonaturelonMap]].
 * It's elonxpelonctelond that thelon [[Stitch]] has alrelonady belonelonn startelond and is running in thelon background.
 *
 * Whilelon not elonsselonntial to it's corelon belonhavior, [[AsyncFelonaturelonMap]] also kelonelonps track of thelon [[FelonaturelonHydratorIdelonntifielonr]]
 * and thelon Selont of [[Felonaturelon]]s which will belon hydratelond for elonach [[Stitch]] of a [[FelonaturelonMap]] it's givelonn.
 *
 * @param asyncFelonaturelonMaps thelon [[FelonaturelonMap]]s for [[PipelonlinelonStelonpIdelonntifielonr]]s which havelon not belonelonn relonachelond yelont
 *
 * @notelon [[PipelonlinelonStelonpIdelonntifielonr]]s must only relonfelonr to [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonBuildelonr.Stelonp Stelonp]]s
 *       in thelon currelonnt [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon Pipelonlinelon]].
 *       Only plain [[FelonaturelonMap]]s arelon passelond into undelonrlying [[com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt Componelonnt]]s and
 *       [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon Pipelonlinelon]]s so [[AsyncFelonaturelonMap]]s arelon scopelond
 *       for a speloncific [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon Pipelonlinelon]] only.
 */
@JsonSelonrializelon(using = classOf[AsyncFelonaturelonMapSelonrializelonr])
privatelon[corelon] caselon class AsyncFelonaturelonMap(
  asyncFelonaturelonMaps: Map[PipelonlinelonStelonpIdelonntifielonr, Quelonuelon[
    (FelonaturelonHydratorIdelonntifielonr, Selont[Felonaturelon[_, _]], Stitch[FelonaturelonMap])
  ]]) {

  delonf ++(right: AsyncFelonaturelonMap): AsyncFelonaturelonMap = {
    val map = Map.nelonwBuildelonr[
      PipelonlinelonStelonpIdelonntifielonr,
      Quelonuelon[(FelonaturelonHydratorIdelonntifielonr, Selont[Felonaturelon[_, _]], Stitch[FelonaturelonMap])]]
    (asyncFelonaturelonMaps.kelonysItelonrator ++ right.asyncFelonaturelonMaps.kelonysItelonrator).forelonach { kelony =>
      val currelonntThelonnRightAsyncFelonaturelonMaps =
        asyncFelonaturelonMaps.gelontOrelonlselon(kelony, Quelonuelon.elonmpty) ++
          right.asyncFelonaturelonMaps.gelontOrelonlselon(kelony, Quelonuelon.elonmpty)
      map += (kelony -> currelonntThelonnRightAsyncFelonaturelonMaps)
    }
    AsyncFelonaturelonMap(map.relonsult())
  }

  /**
   * Relonturns a nelonw [[AsyncFelonaturelonMap]] which now kelonelonps track of thelon providelond `felonaturelons`
   * and will makelon thelonm availablelon whelonn calling [[hydratelon]] with `hydratelonBelonforelon`.
   *
   * @param felonaturelonHydratorIdelonntifielonr thelon [[FelonaturelonHydratorIdelonntifielonr]] of thelon [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator FelonaturelonHydrator]]
   *                                  which thelonselon [[Felonaturelon]]s arelon from
   * @param hydratelonBelonforelon             thelon [[PipelonlinelonStelonpIdelonntifielonr]] belonforelon which thelon [[Felonaturelon]]s nelonelond to belon hydratelond
   * @param felonaturelonsToHydratelon         a Selont of thelon [[Felonaturelon]]s which will belon hydratelond
   * @param felonaturelons                  a [[Stitch]] of thelon [[FelonaturelonMap]]
   */
  delonf addAsyncFelonaturelons(
    felonaturelonHydratorIdelonntifielonr: FelonaturelonHydratorIdelonntifielonr,
    hydratelonBelonforelon: PipelonlinelonStelonpIdelonntifielonr,
    felonaturelonsToHydratelon: Selont[Felonaturelon[_, _]],
    felonaturelons: Stitch[FelonaturelonMap]
  ): AsyncFelonaturelonMap = {
    val felonaturelonMapList =
      asyncFelonaturelonMaps.gelontOrelonlselon(hydratelonBelonforelon, Quelonuelon.elonmpty) :+
        ((felonaturelonHydratorIdelonntifielonr, felonaturelonsToHydratelon, felonaturelons))
    AsyncFelonaturelonMap(asyncFelonaturelonMaps + (hydratelonBelonforelon -> felonaturelonMapList))
  }

  /**
   * Thelon currelonnt statelon of thelon [[AsyncFelonaturelonMap]] elonxcluding thelon [[Stitch]]s.
   */
  delonf felonaturelons: Map[PipelonlinelonStelonpIdelonntifielonr, Selonq[(FelonaturelonHydratorIdelonntifielonr, Selont[Felonaturelon[_, _]])]] =
    asyncFelonaturelonMaps.mapValuelons(_.map {
      caselon (felonaturelonHydratorIdelonntifielonr, felonaturelons, _) => (felonaturelonHydratorIdelonntifielonr, felonaturelons)
    })

  /**
   * Relonturns a [[Somelon]] containing a [[Stitch]] with a [[FelonaturelonMap]] holding thelon [[Felonaturelon]]s that arelon
   * supposelond to belon hydratelond at `idelonntifielonr` if thelonrelon arelon [[Felonaturelon]]s to hydratelon at `idelonntifielonr`
   *
   * Relonturns [[Nonelon]] if thelonrelon arelon no [[Felonaturelon]]s to hydratelon at thelon providelond `idelonntifielonr`,
   * this allows for delontelonrmining if thelonrelon is work to do without running a [[Stitch]].
   *
   * @notelon this only hydratelons thelon [[Felonaturelon]]s for thelon speloncific `idelonntifielonr`, it doelons not hydratelon
   *       [[Felonaturelon]]s for elonarlielonr Stelonps.
   * @param idelonntifielonr thelon [[PipelonlinelonStelonpIdelonntifielonr]] to hydratelon [[Felonaturelon]]s for
   */
  delonf hydratelon(
    idelonntifielonr: PipelonlinelonStelonpIdelonntifielonr
  ): Option[Stitch[FelonaturelonMap]] =
    asyncFelonaturelonMaps.gelont(idelonntifielonr) match {
      caselon Somelon(Quelonuelon((_, _, felonaturelonMap))) =>
        // if thelonrelon is only 1 `FelonaturelonMap` welon dont nelonelond to do a collelonct so just relonturn that Stitch
        Somelon(felonaturelonMap)
      caselon Somelon(felonaturelonMapList) =>
        // if thelonrelon arelon multiplelon `FelonaturelonMap`s welon nelonelond to collelonct and melonrgelon thelonm togelonthelonr
        Somelon(
          Stitch
            .collelonct(felonaturelonMapList.map { caselon (_, _, felonaturelonMap) => felonaturelonMap })
            .map { felonaturelonMapList => FelonaturelonMap.melonrgelon(felonaturelonMapList) })
      caselon Nonelon =>
        // No relonsults for thelon providelond `idelonntifielonr` so relonturn `Nonelon`
        Nonelon
    }
}

privatelon[corelon] objelonct AsyncFelonaturelonMap {
  val elonmpty: AsyncFelonaturelonMap = AsyncFelonaturelonMap(Map.elonmpty)

  /**
   * Builds thelon an [[AsyncFelonaturelonMap]] from a Selonq of [[Stitch]] of [[FelonaturelonMap]]
   * tuplelond with thelon relonlelonvant melontadata welon uselon to build thelon neloncelonssary statelon.
   *
   * This is primarily for convelonnielonncelon, sincelon in most caselons an [[AsyncFelonaturelonMap]]
   * will belon built from thelon relonsult of individual [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator FelonaturelonHydrator]]s
   * and combining thelonm into thelon correlonct intelonrnal statelon.
   */
  delonf fromFelonaturelonMaps(
    asyncFelonaturelonMaps: Selonq[
      (FelonaturelonHydratorIdelonntifielonr, PipelonlinelonStelonpIdelonntifielonr, Selont[Felonaturelon[_, _]], Stitch[FelonaturelonMap])
    ]
  ): AsyncFelonaturelonMap =
    AsyncFelonaturelonMap(
      asyncFelonaturelonMaps
        .groupBy { caselon (_, hydratelonBelonforelon, _, _) => hydratelonBelonforelon }
        .mapValuelons(felonaturelonMaps =>
          Quelonuelon(felonaturelonMaps.map {
            caselon (hydratorIdelonntifielonr, _, felonaturelonsToHydratelon, stitch) =>
              (hydratorIdelonntifielonr, felonaturelonsToHydratelon, stitch)
          }: _*)))
}
