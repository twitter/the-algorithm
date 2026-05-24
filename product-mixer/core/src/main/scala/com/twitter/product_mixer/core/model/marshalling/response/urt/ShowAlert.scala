package com.twitter.product_mixer.core.model.marshalling.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowAlert.ShowAlertEntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertNavigationMetadata
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.twitter.util.Duration

/**
 * Domain model for the URT ShowAlert [[https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/ShowAlert.html]]
 *
 * @note the text field (id: 2) has been deliberately excluded as it's been deprecated since 2018. Use RichText instead.
 */
case class ShowAlert(
  override val id: String,
  override val sortIndex: Option[Long],
  alertType: ShowAlertType,
  triggerDelay: Option[Duration],
  displayDuration: Option[Duration],
  clientEventInfo: Option[ClientEventInfo],
  collapseDelay: Option[Duration],
  userIds: Option[Seq[Long]],
  richText: Option[RichText],
  iconDisplayInfo: Option[ShowAlertIconDisplayInfo],
  colorConfig: ShowAlertColorConfiguration,
  displayLocation: ShowAlertDisplayLocation,
  navigationMetadata: Option[ShowAlertNavigationMetadata],
) extends TimelineItem {
  override val entryNamespace: EntryNamespace = ShowAlertEntryNamespace

  // Note that sort index is not used for ShowAlerts, as they are not TimelineEntry and do not have entryId
  override def withSortIndex(newSortIndex: Long): TimelineEntry =
    copy(sortIndex = Some(newSortIndex))

  // Not used for ShowAlerts
  override def feedbackActionInfo: Option[FeedbackActionInfo] = None
}

object ShowAlert {
  val ShowAlertEntryNamespace: EntryNamespace = EntryNamespace("show-alert")
}
