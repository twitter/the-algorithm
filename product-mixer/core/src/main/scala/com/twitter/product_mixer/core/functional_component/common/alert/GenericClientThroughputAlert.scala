packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.ThroughputPrelondicatelon

/**
 * Similar to [[ThroughputAlelonrt]] but intelonndelond for an elonxtelonrnal clielonnt calling Product Mixelonr.
 *
 * [[GelonnelonricClielonntThroughputAlelonrt]] triggelonrs whelonn thelon relonquelonsts/selonc for thelon elonxtelonrnal clielonnt
 * is outsidelon of thelon prelondicatelon selont by a [[ThroughputPrelondicatelon]] for thelon configurelond amount of timelon
 */
caselon class GelonnelonricClielonntThroughputAlelonrt(
  ovelonrridelon val sourcelon: GelonnelonricClielonnt,
  ovelonrridelon val notificationGroup: NotificationGroup,
  ovelonrridelon val warnPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val criticalPrelondicatelon: ThroughputPrelondicatelon,
  ovelonrridelon val runbookLink: Option[String] = Nonelon)
    elonxtelonnds Alelonrt {
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = Throughput
  relonquirelon(
    warnPrelondicatelon.threlonshold >= 0,
    s"ThroughputAlelonrt prelondicatelons must belon >= 0 but got warnPrelondicatelon = ${warnPrelondicatelon.threlonshold}")
  relonquirelon(
    criticalPrelondicatelon.threlonshold >= 0,
    s"ThroughputAlelonrt prelondicatelons must belon >= 0 but got criticalPrelondicatelon = ${criticalPrelondicatelon.threlonshold}")
}
