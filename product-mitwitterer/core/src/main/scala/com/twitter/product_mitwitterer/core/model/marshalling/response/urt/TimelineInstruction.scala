package com.twitter.product_mitwitterer.core.model.marshalling.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ContainsFeedbackActionInfos
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.HasFeedbackActionInfo

sealed trait TimelineInstruction

case class AddEntriesTimelineInstruction(entries: Seq[TimelineEntry])
    etwittertends TimelineInstruction
    with ContainsFeedbackActionInfos {
  override def feedbackActionInfos: Seq[Option[FeedbackActionInfo]] =
    entries.flatMap {
      // Order is important, as entries that implement both ContainsFeedbackActionInfos and
      // HasFeedbackActionInfo are etwitterpected to include both when implementing ContainsFeedbackActionInfos
      case containsFeedbackActionInfos: ContainsFeedbackActionInfos =>
        containsFeedbackActionInfos.feedbackActionInfos
      case hasFeedbackActionInfo: HasFeedbackActionInfo =>
        Seq(hasFeedbackActionInfo.feedbackActionInfo)
      case _ => Seq.empty
    }
}

case class ReplaceEntryTimelineInstruction(entry: TimelineEntry)
    etwittertends TimelineInstruction
    with ContainsFeedbackActionInfos {
  override def feedbackActionInfos: Seq[Option[FeedbackActionInfo]] =
    entry match {
      // Order is important, as entries that implement both ContainsFeedbackActionInfos and
      // HasFeedbackActionInfo are etwitterpected to include both when implementing ContainsFeedbackActionInfos
      case containsFeedbackActionInfos: ContainsFeedbackActionInfos =>
        containsFeedbackActionInfos.feedbackActionInfos
      case hasFeedbackActionInfo: HasFeedbackActionInfo =>
        Seq(hasFeedbackActionInfo.feedbackActionInfo)
      case _ => Seq.empty
    }
}

case class AddToModuleTimelineInstruction(
  moduleItems: Seq[ModuleItem],
  moduleEntryId: String,
  moduleItemEntryId: Option[String],
  prepend: Option[Boolean])
    etwittertends TimelineInstruction
    with ContainsFeedbackActionInfos {
  override def feedbackActionInfos: Seq[Option[FeedbackActionInfo]] =
    moduleItems.map(_.item.feedbackActionInfo)
}

case class PinEntryTimelineInstruction(entry: TimelineEntry) etwittertends TimelineInstruction

case class MarkEntriesUnreadInstruction(entryIds: Seq[String]) etwittertends TimelineInstruction

case class ClearCacheTimelineInstruction() etwittertends TimelineInstruction

sealed trait TimelineTerminationDirection
case object TopTermination etwittertends TimelineTerminationDirection
case object BottomTermination etwittertends TimelineTerminationDirection
case object TopAndBottomTermination etwittertends TimelineTerminationDirection
case class TerminateTimelineInstruction(terminateTimelineDirection: TimelineTerminationDirection)
    etwittertends TimelineInstruction

case class ShowCoverInstruction(cover: Cover) etwittertends TimelineInstruction

case class ShowAlertInstruction(showAlert: ShowAlert) etwittertends TimelineInstruction
