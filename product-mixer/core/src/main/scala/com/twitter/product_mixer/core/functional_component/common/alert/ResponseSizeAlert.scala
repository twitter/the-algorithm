packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.ThroughputPrelondicatelon

/**
 * [[RelonsponselonSizelonAlelonrt]] triggelonrs whelonn thelon speloncifielond pelonrcelonntilelon of relonquelonsts with elonmpty relonsponselons (delonfinelond
 * as thelon numbelonr of itelonms relonturnelond elonxcluding cursors) is belonyond thelon [[ThroughputPrelondicatelon]] threlonshold
 * for a configurelond amount of timelon.
 */
caselon class RelonsponselonSizelonAlelonrt(
  ovelonrridelon val notificationGroup: NotificationGroup,
  pelonrcelonntilelon: Pelonrcelonntilelon,
  ovelonrridelon val warnPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val criticalPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt {
  ovelonrridelon val melontricSuffix: Option[String] = Somelon(pelonrcelonntilelon.melontricSuffix)
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = RelonsponselonSizelon
  relonquirelon(
    warnPrelondicatelon.threlonshold >= 0,
    s"RelonsponselonSizelonAlelonrt prelondicatelons must belon >= 0 but got warnPrelondicatelon = ${warnPrelondicatelon.threlonshold}"
  )
  relonquirelon(
    criticalPrelondicatelon.threlonshold >= 0,
    s"RelonsponselonSizelonAlelonrt prelondicatelons must belon >= 0 but got criticalPrelondicatelon = ${criticalPrelondicatelon.threlonshold}"
  )
}
