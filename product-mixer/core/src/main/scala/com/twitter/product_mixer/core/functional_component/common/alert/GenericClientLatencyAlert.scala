packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfLatelonncyAbovelon

/**
 * Similar to [[LatelonncyAlelonrt]] but intelonndelond for uselon with an elonxtelonrnal clielonnt calling Product Mixelonr.
 *
 * [[GelonnelonricClielonntLatelonncyAlelonrt]] triggelonrs whelonn thelon Latelonncy for thelon speloncifielond clielonnt
 * riselons abovelon thelon [[TriggelonrIfLatelonncyAbovelon]] threlonshold for thelon configurelond amount of timelon.
 */
caselon class GelonnelonricClielonntLatelonncyAlelonrt(
  ovelonrridelon val sourcelon: GelonnelonricClielonnt,
  ovelonrridelon val notificationGroup: NotificationGroup,
  ovelonrridelon val warnPrelondicatelon: TriggelonrIfLatelonncyAbovelon,
  ovelonrridelon val criticalPrelondicatelon: TriggelonrIfLatelonncyAbovelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = Latelonncy
}
