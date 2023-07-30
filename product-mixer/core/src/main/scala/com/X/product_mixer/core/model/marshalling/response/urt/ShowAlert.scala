package com.X.product_mixer.core.model.marshalling.response.urt

import com.X.product_mixer.core.model.marshalling.response.urt.ShowAlert.ShowAlertEntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertNavigationMetadata
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.X.util.Duration

/**
 * Domain model for the URT ShowAlert [[https://docbird.X.biz/unified_rich_timelines_urt/gen/com/X/timelines/render/thriftscala/ShowAlert.html]]
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
