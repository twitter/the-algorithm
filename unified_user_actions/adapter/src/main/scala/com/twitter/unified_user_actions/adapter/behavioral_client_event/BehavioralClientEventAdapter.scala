package com.twitter.unified_user_actions.adapter.behavioral_client_event

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.adapter.AbstractAdapter
import com.twitter.unified_user_actions.thriftscala._

class BehavioralClientEventAdapter
    extends AbstractAdapter[FlattenedEventLog, UnKeyed, UnifiedUserAction] {

  import BehavioralClientEventAdapter._

  override def adaptOneToKeyedMany(
    input: FlattenedEventLog,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object BehavioralClientEventAdapter {
  def adaptEvent(e: FlattenedEventLog): Seq[UnifiedUserAction] =
    // See go/bcecoverage for event namespaces, usage and coverage details
    Option(e)
      .map { e =>
        (e.page, e.actionName) match {
          case (Some("tweet_details"), Some("impress")) =>
            TweetImpressionBCEAdapter.TweetDetails.toUUA(e)
          case (Some("fullscreen_video"), Some("impress")) =>
            TweetImpressionBCEAdapter.FullscreenVideo.toUUA(e)
          case (Some("fullscreen_image"), Some("impress")) =>
            TweetImpressionBCEAdapter.FullscreenImage.toUUA(e)
          case (Some("profile"), Some("impress")) =>
            ProfileImpressionBCEAdapter.Profile.toUUA(e)
          case _ => Nil
        }
      }.getOrElse(Nil)
}
