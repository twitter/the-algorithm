packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon

/**
 * A [[Prelondicatelon]] that triggelonrs if thelon melontric this is uselond with lowelonrs belonlow
 * thelon [[threlonshold]] for [[datapointsPastThrelonshold]] pelonr [[duration]]
 */
caselon class TriggelonrIfBelonlow(
  ovelonrridelon val threlonshold: Doublelon,
  ovelonrridelon val datapointsPastThrelonshold: Int = 10,
  ovelonrridelon val duration: Int = 15,
  ovelonrridelon val melontricGranularity: MelontricGranularity = Minutelons)
    elonxtelonnds Prelondicatelon
    with ThroughputPrelondicatelon {
  ovelonrridelon val opelonrator: Opelonrator = `<`
}
