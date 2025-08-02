package com.twitter.home_mitwitterer.functional_component.decorator.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.timelinemitwitterer.injection.model.candidate.SemanticCoreFeatures
import com.twitter.tweetypie.{thriftscala => tpt}

object HomeTweetTypePredicates {

  /**
   * IMPORTANT: Please avoid logging tweet types that are tied to sensitive
   * internal author information / labels (e.g. blink labels, abuse labels, or geo-location).
   */
  private[this] val CandidatePredicates: Seq[(String, FeatureMap => Boolean)] = Seq(
    ("with_candidate", _ => true),
    ("retweet", _.getOrElse(IsRetweetFeature, false)),
    ("reply", _.getOrElse(InReplyToTweetIdFeature, None).nonEmpty),
    ("image", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasImage)),
    ("video", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasVideo)),
    ("link", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasVisibleLink)),
    ("quote", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasQuote.contains(true))),
    ("like_social_contetwittert", _.getOrElse(NonSelfFavoritedByUserIdsFeature, Seq.empty).nonEmpty),
    ("protected", _.getOrElse(EarlybirdFeature, None).etwitterists(_.isProtected)),
    (
      "has_etwitterclusive_conversation_author_id",
      _.getOrElse(EtwitterclusiveConversationAuthorIdFeature, None).nonEmpty),
    ("is_eligible_for_connect_boost", _ => false),
    ("hashtag", _.getOrElse(EarlybirdFeature, None).etwitterists(_.numHashtags > 0)),
    ("has_scheduled_space", _.getOrElse(AudioSpaceMetaDataFeature, None).etwitterists(_.isScheduled)),
    ("has_recorded_space", _.getOrElse(AudioSpaceMetaDataFeature, None).etwitterists(_.isRecorded)),
    ("is_read_from_cache", _.getOrElse(IsReadFromCacheFeature, false)),
    ("get_initial", _.getOrElse(GetInitialFeature, false)),
    ("get_newer", _.getOrElse(GetNewerFeature, false)),
    ("get_middle", _.getOrElse(GetMiddleFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("polling", _.getOrElse(PollingFeature, false)),
    ("near_empty", _.getOrElse(ServedSizeFeature, None).etwitterists(_ < 3)),
    ("is_request_contetwittert_launch", _.getOrElse(IsLaunchRequestFeature, false)),
    ("mutual_follow", _.getOrElse(EarlybirdFeature, None).etwitterists(_.fromMutualFollow)),
    (
      "less_than_10_mins_since_lnpt",
      _.getOrElse(LastNonPollingTimeFeature, None).etwitterists(_.untilNow < 10.minutes)),
    ("served_in_conversation_module", _.getOrElse(ServedInConversationModuleFeature, false)),
    ("has_ticketed_space", _.getOrElse(AudioSpaceMetaDataFeature, None).etwitterists(_.hasTickets)),
    ("in_utis_top5", _.getOrElse(PositionFeature, None).etwitterists(_ < 5)),
    (
      "conversation_module_has_2_displayed_tweets",
      _.getOrElse(ConversationModule2DisplayedTweetsFeature, false)),
    ("empty_request", _.getOrElse(ServedSizeFeature, None).etwitterists(_ == 0)),
    ("served_size_less_than_50", _.getOrElse(ServedSizeFeature, None).etwitterists(_ < 50)),
    (
      "served_size_between_50_and_100",
      _.getOrElse(ServedSizeFeature, None).etwitterists(size => size >= 50 && size < 100)),
    ("authored_by_contetwittertual_user", _.getOrElse(AuthoredByContetwittertualUserFeature, false)),
    (
      "is_self_thread_tweet",
      _.getOrElse(ConversationFeature, None).etwitterists(_.isSelfThreadTweet.contains(true))),
    ("has_ancestors", _.getOrElse(AncestorsFeature, Seq.empty).nonEmpty),
    ("full_scoring_succeeded", _.getOrElse(FullScoringSucceededFeature, false)),
    ("served_size_less_than_20", _.getOrElse(ServedSizeFeature, None).etwitterists(_ < 20)),
    ("served_size_less_than_10", _.getOrElse(ServedSizeFeature, None).etwitterists(_ < 10)),
    ("served_size_less_than_5", _.getOrElse(ServedSizeFeature, None).etwitterists(_ < 5)),
    (
      "account_age_less_than_30_minutes",
      _.getOrElse(AccountAgeFeature, None).etwitterists(_.untilNow < 30.minutes)),
    ("conversation_module_has_gap", _.getOrElse(ConversationModuleHasGapFeature, false)),
    (
      "directed_at_user_is_in_first_degree",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.directedAtUserIdIsInFirstDegree.contains(true))),
    (
      "has_semantic_core_annotation",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.semanticCoreAnnotations.nonEmpty)),
    ("is_request_contetwittert_foreground", _.getOrElse(IsForegroundRequestFeature, false)),
    (
      "account_age_less_than_1_day",
      _.getOrElse(AccountAgeFeature, None).etwitterists(_.untilNow < 1.day)),
    (
      "account_age_less_than_7_days",
      _.getOrElse(AccountAgeFeature, None).etwitterists(_.untilNow < 7.days)),
    (
      "part_of_utt",
      _.getOrElse(EarlybirdFeature, None)
        .etwitterists(_.semanticCoreAnnotations.etwitterists(_.etwitterists(annotation =>
          annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTatwitteronomy)))),
    (
      "has_home_latest_request_past_week",
      _.getOrElse(FollowingLastNonPollingTimeFeature, None).etwitterists(_.untilNow < 7.days)),
    ("is_utis_pos0", _.getOrElse(PositionFeature, None).etwitterists(_ == 0)),
    ("is_utis_pos1", _.getOrElse(PositionFeature, None).etwitterists(_ == 1)),
    ("is_utis_pos2", _.getOrElse(PositionFeature, None).etwitterists(_ == 2)),
    ("is_utis_pos3", _.getOrElse(PositionFeature, None).etwitterists(_ == 3)),
    ("is_utis_pos4", _.getOrElse(PositionFeature, None).etwitterists(_ == 4)),
    ("is_random_tweet", _.getOrElse(IsRandomTweetFeature, false)),
    ("has_random_tweet_in_response", _.getOrElse(HasRandomTweetFeature, false)),
    ("is_random_tweet_above_in_utis", _.getOrElse(IsRandomTweetAboveFeature, false)),
    (
      "has_ancestor_authored_by_viewer",
      candidate =>
        candidate
          .getOrElse(AncestorsFeature, Seq.empty).etwitterists(ancestor =>
            candidate.getOrElse(ViewerIdFeature, 0L) == ancestor.userId)),
    ("ancestor", _.getOrElse(IsAncestorCandidateFeature, false)),
    (
      "deep_reply",
      candidate =>
        candidate.getOrElse(InReplyToTweetIdFeature, None).nonEmpty && candidate
          .getOrElse(AncestorsFeature, Seq.empty).size > 2),
    (
      "has_simcluster_embeddings",
      _.getOrElse(
        SimclustersTweetTopKClustersWithScoresFeature,
        Map.empty[String, Double]).nonEmpty),
    (
      "tweet_age_less_than_15_seconds",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow <= 15.seconds)),
    (
      "less_than_1_hour_since_lnpt",
      _.getOrElse(LastNonPollingTimeFeature, None).etwitterists(_.untilNow < 1.hour)),
    ("has_gte_10_favs", _.getOrElse(EarlybirdFeature, None).etwitterists(_.favCountV2.etwitterists(_ >= 10))),
    (
      "device_language_matches_tweet_language",
      candidate =>
        candidate.getOrElse(TweetLanguageFeature, None) ==
          candidate.getOrElse(DeviceLanguageFeature, None)),
    (
      "root_ancestor",
      candidate =>
        candidate.getOrElse(IsAncestorCandidateFeature, false) && candidate
          .getOrElse(InReplyToTweetIdFeature, None).isEmpty),
    ("question", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasQuestion.contains(true))),
    ("in_network", _.getOrElse(InNetworkFeature, true)),
    (
      "has_political_annotation",
      _.getOrElse(EarlybirdFeature, None).etwitterists(
        _.semanticCoreAnnotations.etwitterists(
          _.etwitterists(annotation =>
            SemanticCoreFeatures.PoliticalDomains.contains(annotation.domainId) ||
              (annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTatwitteronomy &&
                annotation.entityId == SemanticCoreFeatures.UttPoliticsEntityId))))),
    (
      "is_dont_at_me_by_invitation",
      _.getOrElse(EarlybirdFeature, None).etwitterists(
        _.conversationControl.etwitterists(_.isInstanceOf[tpt.ConversationControl.ByInvitation]))),
    (
      "is_dont_at_me_community",
      _.getOrElse(EarlybirdFeature, None)
        .etwitterists(_.conversationControl.etwitterists(_.isInstanceOf[tpt.ConversationControl.Community]))),
    ("has_zero_score", _.getOrElse(ScoreFeature, None).etwitterists(_ == 0.0)),
    (
      "is_followed_topic_tweet",
      _.getOrElse(TopicContetwittertFunctionalityTypeFeature, None)
        .etwitterists(_ == BasicTopicContetwittertFunctionalityType)),
    (
      "is_recommended_topic_tweet",
      _.getOrElse(TopicContetwittertFunctionalityTypeFeature, None)
        .etwitterists(_ == RecommendationTopicContetwittertFunctionalityType)),
    ("has_gte_100_favs", _.getOrElse(EarlybirdFeature, None).etwitterists(_.favCountV2.etwitterists(_ >= 100))),
    ("has_gte_1k_favs", _.getOrElse(EarlybirdFeature, None).etwitterists(_.favCountV2.etwitterists(_ >= 1000))),
    (
      "has_gte_10k_favs",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.favCountV2.etwitterists(_ >= 10000))),
    (
      "has_gte_100k_favs",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.favCountV2.etwitterists(_ >= 100000))),
    ("has_audio_space", _.getOrElse(AudioSpaceMetaDataFeature, None).etwitterists(_.hasSpace)),
    ("has_live_audio_space", _.getOrElse(AudioSpaceMetaDataFeature, None).etwitterists(_.isLive)),
    (
      "has_gte_10_retweets",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.retweetCountV2.etwitterists(_ >= 10))),
    (
      "has_gte_100_retweets",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.retweetCountV2.etwitterists(_ >= 100))),
    (
      "has_gte_1k_retweets",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.retweetCountV2.etwitterists(_ >= 1000))),
    (
      "has_us_political_annotation",
      _.getOrElse(EarlybirdFeature, None)
        .etwitterists(_.semanticCoreAnnotations.etwitterists(_.etwitterists(annotation =>
          annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTatwitteronomy &&
            annotation.entityId == SemanticCoreFeatures.usPoliticalTweetEntityId &&
            annotation.groupId == SemanticCoreFeatures.UsPoliticalTweetAnnotationGroupIds.BalancedV0)))),
    (
      "has_totwittericity_score_above_threshold",
      _.getOrElse(EarlybirdFeature, None).etwitterists(_.totwittericityScore.etwitterists(_ > 0.91))),
    ("is_topic_tweet", _.getOrElse(TopicIdSocialContetwittertFeature, None).isDefined),
    (
      "tetwittert_only",
      candidate =>
        candidate.getOrElse(HasDisplayedTetwittertFeature, false) &&
          !(candidate.getOrElse(EarlybirdFeature, None).etwitterists(_.hasImage) ||
            candidate.getOrElse(EarlybirdFeature, None).etwitterists(_.hasVideo) ||
            candidate.getOrElse(EarlybirdFeature, None).etwitterists(_.hasCard))),
    (
      "image_only",
      candidate =>
        candidate.getOrElse(EarlybirdFeature, None).etwitterists(_.hasImage) &&
          !candidate.getOrElse(HasDisplayedTetwittertFeature, false)),
    ("has_1_image", _.getOrElse(NumImagesFeature, None).etwitterists(_ == 1)),
    ("has_2_images", _.getOrElse(NumImagesFeature, None).etwitterists(_ == 2)),
    ("has_3_images", _.getOrElse(NumImagesFeature, None).etwitterists(_ == 3)),
    ("has_4_images", _.getOrElse(NumImagesFeature, None).etwitterists(_ == 4)),
    ("has_card", _.getOrElse(EarlybirdFeature, None).etwitterists(_.hasCard)),
    ("user_follow_count_gte_50", _.getOrElse(UserFollowingCountFeature, None).etwitterists(_ > 50)),
    (
      "has_liked_by_social_contetwittert",
      candidateFeatures =>
        candidateFeatures
          .getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
          .etwitterists(candidateFeatures
            .getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Seq.empty).toSet.contains)),
    (
      "has_followed_by_social_contetwittert",
      _.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty).nonEmpty),
    (
      "has_topic_social_contetwittert",
      candidateFeatures =>
        candidateFeatures
          .getOrElse(TopicIdSocialContetwittertFeature, None)
          .isDefined &&
          candidateFeatures.getOrElse(TopicContetwittertFunctionalityTypeFeature, None).isDefined),
    ("video_lte_10_sec", _.getOrElse(VideoDurationMsFeature, None).etwitterists(_ <= 10000)),
    (
      "video_bt_10_60_sec",
      _.getOrElse(VideoDurationMsFeature, None).etwitterists(duration =>
        duration > 10000 && duration <= 60000)),
    ("video_gt_60_sec", _.getOrElse(VideoDurationMsFeature, None).etwitterists(_ > 60000)),
    (
      "tweet_age_lte_30_minutes",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow <= 30.minutes)),
    (
      "tweet_age_lte_1_hour",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow <= 1.hour)),
    (
      "tweet_age_lte_6_hours",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow <= 6.hours)),
    (
      "tweet_age_lte_12_hours",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow <= 12.hours)),
    (
      "tweet_age_gte_24_hours",
      _.getOrElse(OriginalTweetCreationTimeFromSnowflakeFeature, None)
        .etwitterists(_.untilNow >= 24.hours)),
  )

  val PredicateMap = CandidatePredicates.toMap
}
