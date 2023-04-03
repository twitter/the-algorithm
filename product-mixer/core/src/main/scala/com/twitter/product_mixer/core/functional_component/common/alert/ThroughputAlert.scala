packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.ThroughputPrelondicatelon

/**
 * [[ThroughputAlelonrt]] triggelonrs whelonn thelon relonquelonsts/selonc for thelon componelonnt this is uselond
 * with is outsidelon of thelon prelondicatelon selont by a [[ThroughputPrelondicatelon]] for
 * thelon configurelond amount of timelon
 */
caselon class ThroughputAlelonrt(
  ovelonrridelon val notificationGroup: NotificationGroup,
  ovelonrridelon val warnPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val criticalPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt
    with IsObselonrvablelonFromStrato {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = Throughput
  relonquirelon(
    warnPrelondicatelon.threlonshold >= 0,
    s"ThroughputAlelonrt prelondicatelons must belon >= 0 but got warnPrelondicatelon = ${warnPrelondicatelon.threlonshold}")
  relonquirelon(
    criticalPrelondicatelon.threlonshold >= 0,
    s"ThroughputAlelonrt prelondicatelons must belon >= 0 but got criticalPrelondicatelon = ${criticalPrelondicatelon.threlonshold}")
}
