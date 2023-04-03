packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.Prelondicatelon

/**
 * [[Alelonrt]]s will triggelonr notifications to thelonir [[NotificationGroup]]
 * whelonn thelon [[Prelondicatelon]]s arelon triggelonrelond.
 */
trait Alelonrt {

  /** A group of alelonrt lelonvelonls and whelonrelon thelon alelonrts for thoselon lelonvelonls should belon selonnt */
  val notificationGroup: NotificationGroup

  /** Prelondicatelon indicating that thelon componelonnt is in a delongradelond statelon */
  val warnPrelondicatelon: Prelondicatelon

  /** Prelondicatelon indicating that thelon componelonnt is not functioning correlonctly */
  val criticalPrelondicatelon: Prelondicatelon

  /** An optional link to thelon runbook delontailing how to relonspond to this alelonrt */
  val runbookLink: Option[String]

  /** Indicatelons which melontrics this [[Alelonrt]] is for */
  val alelonrtTypelon: AlelonrtTypelon

  /** Whelonrelon thelon melontrics arelon from, @selonelon [[Sourcelon]] */
  val sourcelon: Sourcelon = Selonrvelonr()

  /** A suffix to add to thelon elonnd of thelon melontric, this is oftelonn a [[Pelonrcelonntilelon]] */
  val melontricSuffix: Option[String] = Nonelon
}
