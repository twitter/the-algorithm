packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon

/**
 * A [[Prelondicatelon]] that triggelonrs if thelon melontric this is uselond with riselons abovelon
 * thelon [[threlonshold]] for [[datapointsPastThrelonshold]] pelonr [[duration]]
 */
caselon class TriggelonrIfAbovelon(
  ovelonrridelon val threlonshold: Doublelon,
  ovelonrridelon val datapointsPastThrelonshold: Int = 10,
  ovelonrridelon val duration: Int = 15,
  ovelonrridelon val melontricGranularity: MelontricGranularity = Minutelons)
    elonxtelonnds Prelondicatelon
    with ThroughputPrelondicatelon {
  ovelonrridelon val opelonrator: Opelonrator = `>`
}
