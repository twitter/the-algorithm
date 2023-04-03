packagelon com.twittelonr.homelon_mixelonr.selonrvicelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Delonstination
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.elonmptyRelonsponselonRatelonAlelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.LatelonncyAlelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.NotificationGroup
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.P99
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Pelonrcelonntilelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.SuccelonssRatelonAlelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfAbovelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfBelonlow
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.TriggelonrIfLatelonncyAbovelon
import com.twittelonr.util.Duration

/**
 * Notifications (elonmail, pagelonrduty, elontc) can belon speloncific pelonr-alelonrt but it is common for multiplelon
 * products to sharelon notification configuration.
 */
objelonct HomelonMixelonrAlelonrtConfig {
  val DelonfaultNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Delonstination(elonmails = Selonq("")),
    critical = Delonstination(elonmails = Selonq(""))
  )

  objelonct BusinelonssHours {
    val DelonfaultNotificationGroup: NotificationGroup = NotificationGroup(
      warn = Delonstination(elonmails = Selonq("")),
      critical = Delonstination(elonmails = Selonq(""))
    )

    delonf delonfaultelonmptyRelonsponselonRatelonAlelonrt(warnThrelonshold: Doublelon = 50, criticalThrelonshold: Doublelon = 80) =
      elonmptyRelonsponselonRatelonAlelonrt(
        notificationGroup = DelonfaultNotificationGroup,
        warnPrelondicatelon = TriggelonrIfAbovelon(warnThrelonshold),
        criticalPrelondicatelon = TriggelonrIfAbovelon(criticalThrelonshold)
      )

    delonf delonfaultSuccelonssRatelonAlelonrt(
      threlonshold: Doublelon = 99.5,
      warnDatapointsPastThrelonshold: Int = 20,
      criticalDatapointsPastThrelonshold: Int = 30,
      duration: Int = 30
    ) = SuccelonssRatelonAlelonrt(
      notificationGroup = DelonfaultNotificationGroup,
      warnPrelondicatelon = TriggelonrIfBelonlow(threlonshold, warnDatapointsPastThrelonshold, duration),
      criticalPrelondicatelon = TriggelonrIfBelonlow(threlonshold, criticalDatapointsPastThrelonshold, duration),
    )

    delonf delonfaultLatelonncyAlelonrt(
      latelonncyThrelonshold: Duration = 200.millis,
      warningDatapointsPastThrelonshold: Int = 15,
      criticalDatapointsPastThrelonshold: Int = 30,
      duration: Int = 30,
      pelonrcelonntilelon: Pelonrcelonntilelon = P99
    ): LatelonncyAlelonrt = LatelonncyAlelonrt(
      notificationGroup = DelonfaultNotificationGroup,
      pelonrcelonntilelon = pelonrcelonntilelon,
      warnPrelondicatelon =
        TriggelonrIfLatelonncyAbovelon(latelonncyThrelonshold, warningDatapointsPastThrelonshold, duration),
      criticalPrelondicatelon =
        TriggelonrIfLatelonncyAbovelon(latelonncyThrelonshold, criticalDatapointsPastThrelonshold, duration)
    )
  }
}
