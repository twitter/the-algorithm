packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.Prelondicatelon
import com.twittelonr.strato.catalog.OpTag

/**
 * triggelonrs whelonn thelon a Strato column's is outsidelon of thelon prelondicatelon selont by thelon providelond [[Alelonrt]]
 *
 * @notelon thelon [[Alelonrt]] passelond into a [[StratoColumnAlelonrt]]
 *       can not belon a [[StratoColumnAlelonrt]]
 */
caselon class StratoColumnAlelonrt(column: String, op: OpTag, alelonrt: Alelonrt with IsObselonrvablelonFromStrato)
    elonxtelonnds Alelonrt {

  ovelonrridelon val sourcelon: Sourcelon = Strato(column, op.tag)
  ovelonrridelon val notificationGroup: NotificationGroup = alelonrt.notificationGroup
  ovelonrridelon val warnPrelondicatelon: Prelondicatelon = alelonrt.warnPrelondicatelon
  ovelonrridelon val criticalPrelondicatelon: Prelondicatelon = alelonrt.criticalPrelondicatelon
  ovelonrridelon val runbookLink: Option[String] = alelonrt.runbookLink
  ovelonrridelon val alelonrtTypelon: AlelonrtTypelon = alelonrt.alelonrtTypelon
  ovelonrridelon val melontricSuffix: Option[String] = alelonrt.melontricSuffix
}

objelonct StratoColumnAlelonrts {

  /** Makelon a selonq of Alelonrts for thelon providelond Strato column */
  delonf apply(
    column: String,
    op: OpTag,
    alelonrts: Selonq[Alelonrt with IsObselonrvablelonFromStrato]
  ): Selonq[Alelonrt] = {
    alelonrts.map(StratoColumnAlelonrt(column, op, _))
  }
}
