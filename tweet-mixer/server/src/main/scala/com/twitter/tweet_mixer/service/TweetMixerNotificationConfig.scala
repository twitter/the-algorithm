package com.twitter.tweet_mixer.service

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.core.functional_component.common.alert.Destination
import com.twitter.product_mixer.core.functional_component.common.alert.EmptyResponseRateAlert
import com.twitter.product_mixer.core.functional_component.common.alert.LatencyAlert
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.common.alert.P99
import com.twitter.product_mixer.core.functional_component.common.alert.Percentile
import com.twitter.product_mixer.core.functional_component.common.alert.SuccessRateAlert
import com.twitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfAbove
import com.twitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfBelow
import com.twitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove
import com.twitter.util.Duration

/**
 * Notifications (email, pagerduty, etc) can be specific per-alert but it is common for multiple
 * products to share notification configuration.
 */
object TweetMixerNotificationConfig {

  val AllHours = "AllHours"
  val BusinessHours = "BusinessHours"
  val CoreProductAllHoursNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Destination(emails = Seq("")),
    critical = Destination(emails = Seq("")),
  )
  val CoreProductBusinessHoursNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Destination(emails = Seq("")),
    critical = Destination(emails = Seq("")),
  )

  val ForYouAllHoursNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Destination(emails = Seq("")),
    critical = Destination(emails = Seq("")),
  )
  val ForYouBusinessHoursNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Destination(emails = Seq("")),
    critical = Destination(emails = Seq("")),
  )

  val CoreProductGroupMap = Map(
    AllHours -> CoreProductAllHoursNotificationGroup,
    BusinessHours -> CoreProductBusinessHoursNotificationGroup)
  val ForYouGroupMap = Map(
    AllHours -> ForYouAllHoursNotificationGroup,
    BusinessHours -> ForYouBusinessHoursNotificationGroup)

  def defaultEmptyResponseRateAlert(
    warnThreshold: Double = 50,
    criticalThreshold: Double = 80,
    notificationType: String = BusinessHours
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ) =
    EmptyResponseRateAlert(
      notificationGroup = notificationGroup.get(notificationType).get,
      warnPredicate = TriggerIfAbove(warnThreshold, 30, 30),
      criticalPredicate = TriggerIfAbove(criticalThreshold, 30, 30)
    )

  def defaultSuccessRateAlert(
    threshold: Double = 99.8,
    warnDatapointsPastThreshold: Int = 20,
    criticalDatapointsPastThreshold: Int = 30,
    duration: Int = 30,
    notificationType: String = AllHours
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ) = SuccessRateAlert(
    notificationGroup = notificationGroup.get(notificationType).get,
    warnPredicate = TriggerIfBelow(threshold, warnDatapointsPastThreshold, duration),
    criticalPredicate = TriggerIfBelow(threshold, criticalDatapointsPastThreshold, duration),
  )

  def defaultLatencyAlert(
    latencyThreshold: Duration = 350.millis,
    warningDatapointsPastThreshold: Int = 15,
    criticalDatapointsPastThreshold: Int = 30,
    duration: Int = 30,
    percentile: Percentile = P99,
    notificationType: String = BusinessHours
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): LatencyAlert = LatencyAlert(
    notificationGroup = notificationGroup.get(notificationType).get,
    percentile = percentile,
    warnPredicate =
      TriggerIfLatencyAbove(latencyThreshold, warningDatapointsPastThreshold, duration),
    criticalPredicate =
      TriggerIfLatencyAbove(latencyThreshold, criticalDatapointsPastThreshold, duration)
  )
}
