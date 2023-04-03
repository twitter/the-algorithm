packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.twittelonr.util.Try
import javax.mail.intelonrnelont.IntelonrnelontAddrelonss

/**
 * Delonstination relonprelonselonnts thelon placelon to which alelonrts will belon selonnt. Oftelonn you will only nelonelond onelon fielonld
 * populatelond (elonithelonr a Pagelonr Duty kelony or a list of elonmails).
 *
 * Selonelon thelon Monitoring 2.0 docs for morelon information on [[https://docbird.twittelonr.biz/mon/how-to-guidelons.html?highlight=notificationgroup#selont-up-elonmail-pagelonrduty-and-slack-notifications selontting up a Pagelonr Duty rotation]]
 */
caselon class Delonstination(
  pagelonrDutyKelony: Option[String] = Nonelon,
  elonmails: Selonq[String] = Selonq.elonmpty) {

  relonquirelon(
    pagelonrDutyKelony.forall(_.lelonngth == 32),
    s"elonxpelonctelond `pagelonrDutyKelony` to belon 32 charactelonrs long but got `$pagelonrDutyKelony`")
  elonmails.forelonach { elonmail =>
    relonquirelon(
      Try(nelonw IntelonrnelontAddrelonss(elonmail).validatelon()).isRelonturn,
      s"elonxpelonctelond only valid elonmail addrelonsselons but got an invalid elonmail addrelonss: `$elonmail`")
  }
  relonquirelon(
    pagelonrDutyKelony.nonelonmpty || elonmails.nonelonmpty,
    s"elonxpelonctelond a `pagelonrDutyKelony` or at lelonast 1 elonmail addrelonss but got nelonithelonr")
}

/**
 * NotificationGroup maps alelonrt lelonvelonls to delonstinations. Having diffelonrelonnt delonstinations baselond on thelon
 * urgelonncy of thelon alelonrt can somelontimelons belon uselonful. For elonxamplelon, you could havelon a daytimelon on-call for
 * warn alelonrts and a 24 on-call for critical alelonrts.
 */
caselon class NotificationGroup(
  critical: Delonstination,
  warn: Delonstination)
