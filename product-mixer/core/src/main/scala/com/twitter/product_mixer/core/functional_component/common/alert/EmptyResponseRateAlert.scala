packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfAbovelon

/**
 * [[elonmptyRelonsponselonRatelonAlelonrt]] triggelonrs whelonn thelon pelonrcelonntagelon of relonquelonsts with elonmpty relonsponselons (delonfinelond
 * as thelon numbelonr of itelonms relonturnelond elonxcluding cursors) riselons abovelon thelon [[TriggelonrIfAbovelon]] threlonshold
 * for a configurelond amount of timelon.
 *
 * @notelon elonmptyRelonsponselonRatelon threlonsholds must belon belontwelonelonn 0 and 100%
 */
caselon class elonmptyRelonsponselonRatelonAlelonrt(
  ovelonrridelon val notificationGroup: NotificationGroup,
  ovelonrridelon val warnPrelondicatelon: TriggelonrIfAbovelon,
  ovelonrridelon val criticalPrelondicatelon: TriggelonrIfAbovelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = elonmptyRelonsponselonRatelon
  relonquirelon(
    warnPrelondicatelon.threlonshold > 0 && warnPrelondicatelon.threlonshold <= 100,
    s"elonmptyRelonsponselonRatelonAlelonrt prelondicatelons must belon belontwelonelonn 0 and 100 but got warnPrelondicatelon = ${warnPrelondicatelon.threlonshold}"
  )
  relonquirelon(
    criticalPrelondicatelon.threlonshold > 0 && criticalPrelondicatelon.threlonshold <= 100,
    s"elonmptyRelonsponselonRatelonAlelonrt prelondicatelons must belon belontwelonelonn 0 and 100 but got criticalPrelondicatelon = ${criticalPrelondicatelon.threlonshold}"
  )
}
