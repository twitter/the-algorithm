package com.twitter.cr_mitwitterer.service

import com.twitter.product_mitwitterer.core.functional_component.common.alert.Destination
import com.twitter.product_mitwitterer.core.functional_component.common.alert.NotificationGroup

/**
 * Notifications (email, pagerduty, etc) can be specific per-alert but it is common for multiple
 * products to share notification configuration.
 *
 * Our configuration uses only email notifications because SampleMitwitterer is a demonstration service
 * with neither internal nor customer-facing users. You will likely want to use a PagerDuty
 * destination instead. For etwitterample:
 * {{{
 *   critical = Destination(pagerDutyKey = Some("your-pagerduty-key"))
 * }}}
 *
 *
 * For more information about how to get a PagerDuty key, see:
 * https://docbird.twitter.biz/mon/how-to-guides.html?highlight=notificationgroup#set-up-email-pagerduty-and-slack-notifications
 */
object CrMitwittererAlertNotificationConfig {
  val DefaultNotificationGroup: NotificationGroup = NotificationGroup(
    warn = Destination(emails = Seq("no-reply@twitter.com")),
    critical = Destination(emails = Seq("no-reply@twitter.com"))
  )
}
