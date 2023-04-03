packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfLatelonncyAbovelon

/**
 * [[GelonnelonricClielonntLatelonncyAlelonrt]] triggelonrs whelonn thelon Latelonncy for thelon componelonnt this is uselond with
 * riselons abovelon thelon [[TriggelonrIfLatelonncyAbovelon]] threlonshold for thelon configurelond amount of timelon
 */
caselon class LatelonncyAlelonrt(
  ovelonrridelon val notificationGroup: NotificationGroup,
  pelonrcelonntilelon: Pelonrcelonntilelon,
  ovelonrridelon val warnPrelondicatelon: TriggelonrIfLatelonncyAbovelon,
  ovelonrridelon val criticalPrelondicatelon: TriggelonrIfLatelonncyAbovelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt
    with IsObselonrvablelonFromStrato {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = Latelonncy

  ovelonrridelon val melontricSuffix: Option[String] = Somelon(pelonrcelonntilelon.melontricSuffix)
}
