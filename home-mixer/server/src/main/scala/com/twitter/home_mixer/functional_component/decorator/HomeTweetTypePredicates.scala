package com.twitter.home_mixer.functional_component.decorator

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.timelinemixer.injection.model.candidate.SemanticCoreFeatures
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
    ("image", _.getOrElse(EarlybirdFeature, None).exists(_.hasImage)),
    ("video", _.getOrElse(EarlybirdFeature, None).exists(_.hasVideo)),
    ("link", _.getOrElse(EarlybirdFeature, None).exists(_.hasVisibleLink)),
    ("quote", _.getOrElse(EarlybirdFeature, None).exists(_.hasQuote.contains(true))),
    ("like_social_context", _.getOrElse(NonSelfFavoritedByUserIdsFeature, Seq.empty).nonEmpty),
    ("protected", _.getOrElse(EarlybirdFeature, None).exists(_.isProtected)),
    (
      "has_exclusive_conversation_author_id",
      _.getOrElse(ExclusiveConversationAuthorIdFeature, None).nonEmpty),
    ("is_eligible_for_connect_boost", _.getOrElse(AuthorIsEligibleForConnectBoostFeature, false)),
    ("hashtag", _.getOrElse(EarlybirdFeature, None).exists(_.numHashtags > 0)),
    ("has_scheduled_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.isScheduled)),
    ("has_recorded_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.isRecorded)),
    ("is_read_from_cache", _.getOrElse(IsReadFromCacheFeature, false)),
    (
      "is_self_thread_tweet",
      _.getOrElse(ConversationFeature, None).exists(_.isSelfThreadTweet.contains(true))),
    ("get_initial", _.getOrElse(GetInitialFeature, false)),
    ("get_newer", _.getOrElse(GetNewerFeature, false)),
    ("get_middle", _.getOrElse(GetMiddleFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("polling", _.getOrElse(PollingFeature, false)),
    ("tls_size_20_plus", _ => false),
    ("near_empty", _ => false),
    ("ranked_request", _ => false),
    ("mutual_follow", _.getOrElse(EarlybirdFeature, None).exists(_.fromMutualFollow)),
    ("has_ticketed_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.hasTickets)),
    ("in_utis_top5", _.getOrElse(PositionFeature, None).exists(_ < 5)),
    ("is_utis_pos0", _.getOrElse(PositionFeature, None).exists(_ == 0)),
    ("is_utis_pos1", _.getOrElse(PositionFeature, None).exists(_ == 1)),
    ("is_utis_pos2", _.getOrElse(PositionFeature, None).exists(_ == 2)),
    ("is_utis_pos3", _.getOrElse(PositionFeature, None).exists(_ == 3)),
    ("is_utis_pos4", _.getOrElse(PositionFeature, None).exists(_ == 4)),
    (
      "is_signup_request",
      candidate => candidate.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 30.minutes)),
    ("empty_request", _ => false),
    ("served_size_less_than_5", _.getOrElse(ServedSizeFeature, None).exists(_ < 5)),
    ("served_size_less_than_10", _.getOrElse(ServedSizeFeature, None).exists(_ < 10)),
    ("served_size_less_than_20", _.getOrElse(ServedSizeFeature, None).exists(_ < 20)),
    ("served_size_less_than_50", _.getOrElse(ServedSizeFeature, None).exists(_ < 50)),
    (
      "served_size_between_50_and_100",
      _.getOrElse(ServedSizeFeature, None).exists(size => size >= 50 && size < 100)),
    ("authored_by_contextual_user", _.getOrElse(AuthoredByContextualUserFeature, false)),
    ("has_ancestors", _.getOrElse(AncestorsFeature, Seq.empty).nonEmpty),
    ("full_scoring_succeeded", _.getOrElse(FullScoringSucceededFeature, false)),
    (
      "account_age_less_than_30_minutes",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 30.minutes)),
    (
      "account_age_less_than_1_day",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 1.day)),
    (
      "account_age_less_than_7_days",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 7.days)),
    (
      "directed_at_user_is_in_first_degree",
      _.getOrElse(EarlybirdFeature, None).exists(_.directedAtUserIdIsInFirstDegree.contains(true))),
    ("root_user_is_in_first_degree", _ => false),
    (
      "has_semantic_core_annotation",
      _.getOrElse(EarlybirdFeature, None).exists(_.semanticCoreAnnotations.nonEmpty)),
    ("is_request_context_foreground", _.getOrElse(IsForegroundRequestFeature, false)),
    (
      "part_of_utt",
      _.getOrElse(EarlybirdFeature, None)
        .exists(_.semanticCoreAnnotations.exists(_.exists(annotation =>
          annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTaxonomy)))),
    ("is_random_tweet", _.getOrElse(IsRandomTweetFeature, false)),
    ("has_random_tweet_in_response", _.getOrElse(HasRandomTweetFeature, false)),
    ("is_random_tweet_above_in_utis", _.getOrElse(IsRandomTweetAboveFeature, false)),
    ("is_request_context_launch", _.getOrElse(IsLaunchRequestFeature, false)),
    ("viewer_is_employee", _ => false),
    ("viewer_is_timelines_employee", _ => false),
    ("viewer_follows_any_topics", _.getOrElse(UserFollowedTopicsCountFeature, None).exists(_ > 0)),
    (
      "has_ancestor_authored_by_viewer",
      candidate =>
        candidate
          .getOrElse(AncestorsFeature, Seq.empty).exists(ancestor =>
            candidate.getOrElse(ViewerIdFeature, 0L) == ancestor.userId)),
    ("ancestor", _.getOrElse(IsAncestorCandidateFeature, false)),
    (
      "root_ancestor",
      candidate =>
        candidate.getOrElse(IsAncestorCandidateFeature, false) && candidate
          .getOrElse(InReplyToTweetIdFeature, None).isEmpty),
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
        .exists(_.untilNow <= 15.seconds)),
    ("is_followed_topic_tweet", _ => false),
    ("is_recommended_topic_tweet", _ => false),
    ("is_topic_tweet", _ => false),
    ("preferred_language_matches_tweet_language", _ => false),
    (
      "device_language_matches_tweet_language",
      candidate =>
        candidate.getOrElse(TweetLanguageFeature, None) ==
          candidate.getOrElse(DeviceLanguageFeature, None)),
    ("question", _.getOrElse(EarlybirdFeature, None).exists(_.hasQuestion.contains(true))),
    ("in_network", _.getOrElse(FromInNetworkSourceFeature, true)),
    ("viewer_follows_original_author", _ => false),
    ("has_account_follow_prompt", _ => false),
    ("has_relevance_prompt", _ => false),
    ("has_topic_annotation_haug_prompt", _ => false),
    ("has_topic_annotation_random_precision_prompt", _ => false),
    ("has_topic_annotation_prompt", _ => false),
    (
      "has_political_annotation",
      _.getOrElse(EarlybirdFeature, None).exists(
        _.semanticCoreAnnotations.exists(
          _.exists(annotation =>
            SemanticCoreFeatures.PoliticalDomains.contains(annotation.domainId) ||
              (annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTaxonomy &&
                annotation.entityId == SemanticCoreFeatures.UttPoliticsEntityId))))),
    (
      "is_dont_at_me_by_invitation",
      _.getOrElse(EarlybirdFeature, None).exists(
        _.conversationControl.exists(_.isInstanceOf[tpt.ConversationControl.ByInvitation]))),
    (
      "is_dont_at_me_community",
      _.getOrElse(EarlybirdFeature, None)
        .exists(_.conversationControl.exists(_.isInstanceOf[tpt.ConversationControl.Community]))),
    ("has_zero_score", _.getOrElse(ScoreFeature, None).exists(_ == 0.0)),
    ("is_viewer_not_invited_to_reply", _ => false),
    ("is_viewer_invited_to_reply", _ => false),
    ("has_gte_10_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 10))),
    ("has_gte_100_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 100))),
    ("has_gte_1k_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 1000))),
    (
      "has_gte_10k_favs",
      _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 10000))),
    (
      "has_gte_100k_favs",
      _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 100000))),
    ("above_neighbor_is_topic_tweet", _ => false),
    ("is_topic_tweet_with_neighbor_below", _ => false),
    ("has_audio_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.hasSpace)),
    ("has_live_audio_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.isLive)),
    (
      "has_gte_10_retweets",
      _.getOrElse(EarlybirdFeature, None).exists(_.retweetCountV2.exists(_ >= 10))),
    (
      "has_gte_100_retweets",
      _.getOrElse(EarlybirdFeature, None).exists(_.retweetCountV2.exists(_ >= 100))),
    (
      "has_gte_1k_retweets",
      _.getOrElse(EarlybirdFeature, None).exists(_.retweetCountV2.exists(_ >= 1000))),
    (
      "has_us_political_annotation",
      _.getOrElse(EarlybirdFeature, None)
        .exists(_.semanticCoreAnnotations.exists(_.exists(annotation =>
          annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTaxonomy &&
            annotation.entityId == SemanticCoreFeatures.usPoliticalTweetEntityId &&
            annotation.groupId == SemanticCoreFeatures.UsPoliticalTweetAnnotationGroupIds.BalancedV0)))),
    (
      "has_toxicity_score_above_threshold",
      _.getOrElse(EarlybirdFeature, None).exists(_.toxicityScore.exists(_ > 0.91))),
    (
      "text_only",
      candidate =>
        candidate.getOrElse(HasDisplayedTextFeature, false) &&
          !(candidate.getOrElse(EarlybirdFeature, None).exists(_.hasImage) ||
            candidate.getOrElse(EarlybirdFeature, None).exists(_.hasVideo) ||
            candidate.getOrElse(EarlybirdFeature, None).exists(_.hasCard))),
    (
      "image_only",
      candidate =>
        candidate.getOrElse(EarlybirdFeature, None).exists(_.hasImage) &&
          !candidate.getOrElse(HasDisplayedTextFeature, false)),
    ("has_1_image", _.getOrElse(NumImagesFeature, None).exists(_ == 1)),
    ("has_2_images", _.getOrElse(NumImagesFeature, None).exists(_ == 2)),
    ("has_3_images", _.getOrElse(NumImagesFeature, None).exists(_ == 3)),
    ("has_4_images", _.getOrElse(NumImagesFeature, None).exists(_ == 4)),
    ("has_card", _.getOrElse(EarlybirdFeature, None).exists(_.hasCard)),
    ("3_or_more_consecutive_not_in_network", _ => false),
    ("2_or_more_consecutive_not_in_network", _ => false),
    ("5_out_of_7_not_in_network", _ => false),
    ("7_out_of_7_not_in_network", _ => false),
    ("5_out_of_5_not_in_network", _ => false),
    ("user_follow_count_gte_50", _.getOrElse(UserFollowingCountFeature, None).exists(_ > 50)),
    ("has_liked_by_social_context", _ => false),
    ("has_followed_by_social_context", _ => false),
    ("has_topic_social_context", _ => false),
    ("timeline_entry_has_banner", _ => false),
    ("served_in_conversation_module", _.getOrElse(ServedInConversationModuleFeature, false)),
    (
      "conversation_module_has_2_displayed_tweets",
      _.getOrElse(ConversationModule2DisplayedTweetsFeature, false)),
    ("conversation_module_has_gap", _.getOrElse(ConversationModuleHasGapFeature, false)),
    ("served_in_recap_tweet_candidate_module_injection", _ => false),
    ("served_in_threaded_conversation_module", _ => false)
  )

  val PredicateMap = CandidatePredicates.toMap
}
