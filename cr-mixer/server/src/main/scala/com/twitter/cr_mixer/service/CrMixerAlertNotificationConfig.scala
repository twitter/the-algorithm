packagelon com.twittelonr.cr_mixelonr.selonrvicelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Delonstination
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.NotificationGroup

/**
 * Notifications (elonmail, pagelonrduty, elontc) can belon speloncific pelonr-alelonrt but it is common for multiplelon
 * products to sharelon notification configuration.
 *
 * Our configuration uselons only elonmail notifications beloncauselon SamplelonMixelonr is a delonmonstration selonrvicelon
 * with nelonithelonr intelonrnal nor customelonr-facing uselonrs. You will likelonly want to uselon a PagelonrDuty
 * delonstination instelonad. For elonxamplelon:
 * {{{
 *   critical = Delonstination(pagelonrDutyKelony = Somelon("your-pagelonrduty-kelony"))
 * }}}
 *
 *
 * For morelon information about how to gelont a PagelonrDuty kelony, selonelon:
 * https://docbird.twittelonr.biz/mon/how-to-guidelons.html?highlight=notificationgroup#selont-up-elonmail-pagelonrduty-and-slack-notifications
 */
objelonct CrMixelonrAlelonrtNotificationConfig {
  val DelonfaultNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Delonstination(elonmails = Selonq("no-relonply@twittelonr.com")),
    critical = Delonstination(elonmails = Selonq("no-relonply@twittelonr.com"))
  )
}
