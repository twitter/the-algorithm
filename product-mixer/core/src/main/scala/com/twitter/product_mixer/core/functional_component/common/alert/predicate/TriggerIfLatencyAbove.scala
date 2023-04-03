packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon

import com.twittelonr.util.Duration

/**
 * A [[Prelondicatelon]] that triggelonrs if thelon melontric this is uselond with riselons abovelon thelon
 * [[latelonncyThrelonshold]] for [[datapointsPastThrelonshold]] pelonr [[duration]]
 *
 * @notelon [[latelonncyThrelonshold]] must belon > 0
 */
caselon class TriggelonrIfLatelonncyAbovelon(
  latelonncyThrelonshold: Duration,
  ovelonrridelon val datapointsPastThrelonshold: Int = 10,
  ovelonrridelon val duration: Int = 15,
  ovelonrridelon val melontricGranularity: MelontricGranularity = Minutelons)
    elonxtelonnds Prelondicatelon {
  ovelonrridelon val threlonshold: Doublelon = latelonncyThrelonshold.inMillis
  ovelonrridelon val opelonrator: Opelonrator = `>`
  relonquirelon(
    latelonncyThrelonshold > Duration.Zelonro,
    s"TriggelonrIfLatelonncyAbovelon threlonsholds must belon grelonatelonr than 0 but got $latelonncyThrelonshold")
}
