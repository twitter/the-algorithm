package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.OutOfNetworkTweetCandidate
import com.X.frigate.common.base.TopicCandidate
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.rec_types.RecTypes._
import com.X.frigate.common.util.MrPushCopyObjects
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.util.InlineActionUtil
import com.X.frigate.pushservice.util.PushIbisUtil.mergeModelValues
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.util.Future

trait OutOfNetworkTweetIbis2HydratorForCandidate extends TweetCandidateIbis2Hydrator {
  self: PushCandidate with OutOfNetworkTweetCandidate with TopicCandidate with TweetAuthorDetails =>

  private lazy val useNewOonCopyValue =
    if (target.params(PushFeatureSwitchParams.EnableNewMROONCopyForPush)) {
      Map(
        "use_new_oon_copy" -> "true"
      )
    } else Map.empty[String, String]

  override lazy val tweetDynamicInlineActionsModelValues =
    if (target.params(PushFeatureSwitchParams.EnableOONGeneratedInlineActions)) {
      val actions = target.params(PushFeatureSwitchParams.OONTweetDynamicInlineActionsList)
      InlineActionUtil.getGeneratedTweetInlineActions(target, statsReceiver, actions)
    } else Map.empty[String, String]

  private lazy val ibtModelValues: Map[String, String] =
    Map(
      "is_tweet" -> s"${!(hasPhoto || hasVideo)}",
      "is_photo" -> s"$hasPhoto",
      "is_video" -> s"$hasVideo"
    )

  private lazy val launchVideosInImmersiveExploreValue =
    Map(
      "launch_videos_in_immersive_explore" -> s"${hasVideo && target.params(PushFeatureSwitchParams.EnableLaunchVideosInImmersiveExplore)}"
    )

  private lazy val oonTweetModelValues =
    useNewOonCopyValue ++ ibtModelValues ++ tweetDynamicInlineActionsModelValues ++ launchVideosInImmersiveExploreValue

  lazy val useTopicCopyForMBCGIbis = mrModelingBasedTypes.contains(commonRecType) && target.params(
    PushFeatureSwitchParams.EnableMrModelingBasedCandidatesTopicCopy)
  lazy val useTopicCopyForFrsIbis = frsTypes.contains(commonRecType) && target.params(
    PushFeatureSwitchParams.EnableFrsTweetCandidatesTopicCopy)
  lazy val useTopicCopyForTagspaceIbis = tagspaceTypes.contains(commonRecType) && target.params(
    PushFeatureSwitchParams.EnableHashspaceCandidatesTopicCopy)

  override lazy val modelName: String = {
    if (localizedUttEntity.isDefined &&
      (useTopicCopyForMBCGIbis || useTopicCopyForFrsIbis || useTopicCopyForTagspaceIbis)) {
      MrPushCopyObjects.TopicTweet.ibisPushModelName // uses topic copy
    } else super.modelName
  }

  lazy val exploreVideoParams: Map[String, String] = {
    if (self.commonRecType == CommonRecommendationType.ExploreVideoTweet) {
      Map(
        "is_explore_video" -> "true"
      )
    } else Map.empty[String, String]
  }

  override lazy val customFieldsMapFut: Future[Map[String, String]] =
    mergeModelValues(super.customFieldsMapFut, exploreVideoParams)

  override lazy val tweetModelValues: Future[Map[String, String]] =
    if (localizedUttEntity.isDefined &&
      (useTopicCopyForMBCGIbis || useTopicCopyForFrsIbis || useTopicCopyForTagspaceIbis)) {
      lazy val topicTweetModelValues: Map[String, String] =
        Map("topic_name" -> s"${localizedUttEntity.get.localizedNameForDisplay}")
      for {
        superModelValues <- super.tweetModelValues
        tweetInlineModelValue <- tweetInlineActionModelValue
      } yield {
        superModelValues ++ topicTweetModelValues ++ tweetInlineModelValue
      }
    } else {
      for {
        superModelValues <- super.tweetModelValues
        tweetInlineModelValues <- tweetInlineActionModelValue
      } yield {
        superModelValues ++ mediaModelValue ++ oonTweetModelValues ++ tweetInlineModelValues ++ inlineVideoMediaMap
      }
    }
}
