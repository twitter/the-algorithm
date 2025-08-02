package com.twitter.product_mitwitterer.core.model.marshalling.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ShowAlert.ShowAlertEntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertNavigationMetadata
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.util.Duration

/**
 * Domain model for the URT ShowAlert [[https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/ShowAlert.html]]
 *
 * @note the tetwittert field (id: 2) has been deliberately etwittercluded as it's been deprecated since 2018. Use RichTetwittert instead.
 */
case class ShowAlert(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  alertType: ShowAlertType,
  triggerDelay: Option[Duration],
  displayDuration: Option[Duration],
  clientEventInfo: Option[ClientEventInfo],
  collapseDelay: Option[Duration],
  userIds: Option[Seq[Long]],
  richTetwittert: Option[RichTetwittert],
  iconDisplayInfo: Option[ShowAlertIconDisplayInfo],
  colorConfig: ShowAlertColorConfiguration,
  displayLocation: ShowAlertDisplayLocation,
  navigationMetadata: Option[ShowAlertNavigationMetadata],
) etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = ShowAlertEntryNamespace

  // Note that sort indetwitter is not used for ShowAlerts, as they are not TimelineEntry and do not have entryId
  override def withSortIndetwitter(newSortIndetwitter: Long): TimelineEntry =
    copy(sortIndetwitter = Some(newSortIndetwitter))

  // Not used for ShowAlerts
  override def feedbackActionInfo: Option[FeedbackActionInfo] = None
}

object ShowAlert {
  val ShowAlertEntryNamespace: EntryNamespace = EntryNamespace("show-alert")
}
