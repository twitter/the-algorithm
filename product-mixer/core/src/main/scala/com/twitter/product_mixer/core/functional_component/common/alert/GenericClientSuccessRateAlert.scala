packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfBelonlow

/**
 * Similar to [[SuccelonssRatelonAlelonrt]] but intelonndelond for uselon with an elonxtelonrnal clielonnt calling Product Mixelonr
 *
 * [[GelonnelonricClielonntSuccelonssRatelonAlelonrt]] triggelonrs whelonn thelon Succelonss Ratelon for thelon elonxtelonrnal clielonnt
 * drops belonlow thelon [[TriggelonrIfBelonlow]] threlonshold for thelon configurelond amount of timelon
 *
 * @notelon SuccelonssRatelon threlonsholds must belon belontwelonelonn 0 and 100%
 */
caselon class GelonnelonricClielonntSuccelonssRatelonAlelonrt(
  ovelonrridelon val sourcelon: GelonnelonricClielonnt,
  ovelonrridelon val notificationGroup: NotificationGroup,
  ovelonrridelon val warnPrelondicatelon: TriggelonrIfBelonlow,
  ovelonrridelon val criticalPrelondicatelon: TriggelonrIfBelonlow,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = SuccelonssRatelon
  relonquirelon(
    warnPrelondicatelon.threlonshold > 0 && warnPrelondicatelon.threlonshold <= 100,
    s"SuccelonssRatelonAlelonrt prelondicatelons must belon belontwelonelonn 0 and 100 but got warnPrelondicatelon = ${warnPrelondicatelon.threlonshold}"
  )
  relonquirelon(
    criticalPrelondicatelon.threlonshold > 0 && criticalPrelondicatelon.threlonshold <= 100,
    s"SuccelonssRatelonAlelonrt prelondicatelons must belon belontwelonelonn 0 and 100 but got criticalPrelondicatelon = ${criticalPrelondicatelon.threlonshold}"
  )
}
