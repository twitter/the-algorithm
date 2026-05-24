package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationIdFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.twitter.timelinemixer.injection.model.candidate.SemanticCoreFeatures
import com.twitter.tweetypie.{thriftscala => tpt}

object HomeTweetTypePredicates {

  /**
   * The predicates defined in this file are used purely for metrics tracking purposes to 
   * measure how often we serve posts with various attributes.
   */
  private val CandidatePredicates: Seq[(String, FeatureMap => Boolean)] = Seq(
    ("with_candidate", _ => true),
    ("retweet", _.getOrElse(IsRetweetFeature, false)),
    ("reply", _.getOrElse(InReplyToTweetIdFeature, None).nonEmpty),
    ("image", _.getOrElse(HasImageFeature, false)),
    ("video", _.getOrElse(HasVideoFeature, false)),
    ("link", _.getOrElse(EarlybirdFeature, None).exists(_.hasVisibleLink)),
    ("quote", _.getOrElse(EarlybirdFeature, None).exists(_.hasQuote.contains(true))),
    ("like_social_context", _.getOrElse(NonSelfFavoritedByUserIdsFeature, Seq.empty).nonEmpty),
    ("protected", _.getOrElse(AuthorIsProtectedFeature, false)),
    (
      "has_exclusive_conversation_author_id",
      _.getOrElse(ExclusiveConversationAuthorIdFeature, None).nonEmpty),
    ("is_eligible_for_connect_boost", _ => false),
    ("hashtag", _.getOrElse(EarlybirdFeature, None).exists(_.numHashtags > 0)),
    ("has_scheduled_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.isScheduled)),
    ("has_recorded_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.isRecorded)),
    ("is_read_from_cache", _.getOrElse(IsReadFromCacheFeature, false)),
    ("get_initial", _.getOrElse(GetInitialFeature, false)),
    ("get_newer", _.getOrElse(GetNewerFeature, false)),
    ("get_middle", _.getOrElse(GetMiddleFeature, false)),
    ("get_older", _.getOrElse(GetOlderFeature, false)),
    ("pull_to_refresh", _.getOrElse(PullToRefreshFeature, false)),
    ("polling", _.getOrElse(PollingFeature, false)),
    ("near_empty", _.getOrElse(ServedSizeFeature, None).exists(_ < 3)),
    ("is_request_context_launch", _.getOrElse(IsLaunchRequestFeature, false)),
    ("mutual_follow", _.getOrElse(EarlybirdFeature, None).exists(_.fromMutualFollow)),
    (
      "less_than_10_mins_since_lnpt",
      _.getOrElse(LastNonPollingTimeFeature, None).exists(_.untilNow < 10.minutes)),
    ("served_in_conversation_module", _.getOrElse(ServedInConversationModuleFeature, false)),
    ("has_ticketed_space", _.getOrElse(AudioSpaceMetaDataFeature, None).exists(_.hasTickets)),
    ("in_utis_top5", _.getOrElse(PositionFeature, None).exists(_ < 5)),
    (
      "conversation_module_has_2_displayed_tweets",
      _.getOrElse(ConversationModule2DisplayedTweetsFeature, false)),
    ("empty_request", _.getOrElse(ServedSizeFeature, None).exists(_ == 0)),
    ("served_size_less_than_50", _.getOrElse(ServedSizeFeature, None).exists(_ < 50)),
    (
      "served_size_between_50_and_100",
      _.getOrElse(ServedSizeFeature, None).exists(size => size >= 50 && size < 100)),
    ("authored_by_contextual_user", _.getOrElse(AuthoredByContextualUserFeature, false)),
    ("is_self_thread_tweet", _.getOrElse(IsSelfThreadFeature, false)),
    ("has_ancestors", _.getOrElse(AncestorsFeature, Seq.empty).nonEmpty),
    ("full_scoring_succeeded", _.getOrElse(FullScoringSucceededFeature, false)),
    ("served_size_less_than_20", _.getOrElse(ServedSizeFeature, None).exists(_ < 20)),
    ("served_size_less_than_10", _.getOrElse(ServedSizeFeature, None).exists(_ < 10)),
    ("served_size_less_than_5", _.getOrElse(ServedSizeFeature, None).exists(_ < 5)),
    (
      "account_age_less_than_30_minutes",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 30.minutes)),
    ("conversation_module_has_gap", _.getOrElse(ConversationModuleHasGapFeature, false)),
    (
      "directed_at_user_is_in_first_degree",
      _.getOrElse(EarlybirdFeature, None).exists(_.directedAtUserIdIsInFirstDegree.contains(true))),
    (
      "has_semantic_core_annotation",
      _.getOrElse(EarlybirdFeature, None).exists(_.semanticCoreAnnotations.nonEmpty)),
    ("is_request_context_foreground", _.getOrElse(IsForegroundRequestFeature, false)),
    (
      "account_age_less_than_1_day",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 1.day)),
    (
      "account_age_less_than_7_days",
      _.getOrElse(AccountAgeFeature, None).exists(_.untilNow < 7.days)),
    (
      "part_of_utt",
      _.getOrElse(EarlybirdFeature, None)
        .exists(_.semanticCoreAnnotations.exists(_.exists(annotation =>
          annotation.domainId == SemanticCoreFeatures.UnifiedTwitterTaxonomy)))),
    (
      "has_home_latest_request_past_week",
      _.getOrElse(FollowingLastNonPollingTimeFeature, None).exists(_.untilNow < 7.days)),
    ("is_utis_pos0", _.getOrElse(PositionFeature, None).exists(_ == 0)),
    ("is_utis_pos1", _.getOrElse(PositionFeature, None).exists(_ == 1)),
    ("is_utis_pos2", _.getOrElse(PositionFeature, None).exists(_ == 2)),
    ("is_utis_pos3", _.getOrElse(PositionFeature, None).exists(_ == 3)),
    ("is_utis_pos4", _.getOrElse(PositionFeature, None).exists(_ == 4)),
    ("is_random_tweet", _ => false),
    ("has_random_tweet_in_response", _ => false),
    ("is_random_tweet_above_in_utis", _ => false),
    (
      "has_ancestor_authored_by_viewer",
      candidate =>
        candidate
          .getOrElse(AncestorsFeature, Seq.empty).exists(ancestor =>
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
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ <= 15.seconds.inMillis)),
    (
      "less_than_1_hour_since_lnpt",
      _.getOrElse(LastNonPollingTimeFeature, None).exists(_.untilNow < 1.hour)),
    ("has_gte_10_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 10))),
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
    ("question", _.getOrElse(EarlybirdFeature, None).exists(_.hasQuestion.contains(true))),
    ("in_network", _.getOrElse(InNetworkFeature, true)),
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
    (
      "is_followed_topic_tweet",
      _.getOrElse(TopicContextFunctionalityTypeFeature, None)
        .exists(_ == BasicTopicContextFunctionalityType)),
    (
      "is_recommended_topic_tweet",
      _.getOrElse(TopicContextFunctionalityTypeFeature, None)
        .exists(_ == RecommendationTopicContextFunctionalityType)),
    ("has_gte_100_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 100))),
    ("has_gte_1k_favs", _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 1000))),
    (
      "has_gte_10k_favs",
      _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 10000))),
    (
      "has_gte_100k_favs",
      _.getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= 100000))),
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
    ("is_topic_tweet", _.getOrElse(TopicIdSocialContextFeature, None).isDefined),
    (
      "text_only",
      candidate =>
        candidate.getOrElse(HasDisplayedTextFeature, false) &&
          !(candidate.getOrElse(HasImageFeature, false) ||
            candidate.getOrElse(HasVideoFeature, false) ||
            candidate.getOrElse(EarlybirdFeature, None).exists(_.hasCard))),
    (
      "image_only",
      candidate =>
        candidate.getOrElse(HasImageFeature, false) &&
          !candidate.getOrElse(HasDisplayedTextFeature, false)),
    ("has_1_image", _.getOrElse(NumImagesFeature, None).exists(_ == 1)),
    ("has_2_images", _.getOrElse(NumImagesFeature, None).exists(_ == 2)),
    ("has_3_images", _.getOrElse(NumImagesFeature, None).exists(_ == 3)),
    ("has_4_images", _.getOrElse(NumImagesFeature, None).exists(_ == 4)),
    ("has_card", _.getOrElse(EarlybirdFeature, None).exists(_.hasCard)),
    ("user_follow_count_gte_50", _.getOrElse(UserFollowingCountFeature, None).exists(_ > 50)),
    (
      "has_liked_by_social_context",
      candidateFeatures =>
        candidateFeatures
          .getOrElse(ValidLikedByUserIdsFeature, Seq.empty).nonEmpty),
    (
      "has_followed_by_social_context",
      _.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty).nonEmpty),
    (
      "has_topic_social_context",
      candidateFeatures =>
        candidateFeatures
          .getOrElse(TopicIdSocialContextFeature, None)
          .isDefined &&
          candidateFeatures.getOrElse(TopicContextFunctionalityTypeFeature, None).isDefined),
    ("video_lte_10_sec", _.getOrElse(VideoDurationMsFeature, None).exists(_ <= 10000)),
    (
      "video_bt_10_60_sec",
      _.getOrElse(VideoDurationMsFeature, None).exists(duration =>
        duration > 10000 && duration <= 60000)),
    ("video_gt_60_sec", _.getOrElse(VideoDurationMsFeature, None).exists(_ > 60000)),
    (
      "tweet_age_lte_30_minutes",
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ <= 30.minutes.inMillis)),
    (
      "tweet_age_lte_1_hour",
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ <= 1.hour.inMillis)),
    (
      "tweet_age_lte_6_hours",
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ <= 6.hours.inMillis)),
    (
      "tweet_age_lte_12_hours",
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ <= 12.hours.inMillis)),
    (
      "tweet_age_gte_24_hours",
      _.getOrElse(TweetAgeFeature, None)
        .exists(_ >= 24.hours.inMillis)),
    ("author_is_blue_verified", _.getOrElse(AuthorIsBlueVerifiedFeature, false)),
    ("author_is_gold_verified", _.getOrElse(AuthorIsGoldVerifiedFeature, false)),
    ("author_is_gray_verified", _.getOrElse(AuthorIsGrayVerifiedFeature, false)),
    ("author_is_legacy_verified", _.getOrElse(AuthorIsLegacyVerifiedFeature, false)),
    ("author_is_creator", _.getOrElse(AuthorIsCreatorFeature, false)),
    (
      "viral_content_creator_in_network",
      candidate =>
        candidate.getOrElse(ViralContentCreatorFeature, false) &&
          candidate.getOrElse(InNetworkFeature, true)),
    (
      "viral_content_creator_out_of_network",
      candidate =>
        candidate.getOrElse(ViralContentCreatorFeature, false) &&
          !candidate.getOrElse(InNetworkFeature, true)),
    (
      "grok_content_creator_in_network",
      candidate =>
        candidate.getOrElse(GrokContentCreatorFeature, false) &&
          candidate.getOrElse(InNetworkFeature, true)),
    (
      "grok_content_creator_out_of_network",
      candidate =>
        candidate.getOrElse(GrokContentCreatorFeature, false) &&
          !candidate.getOrElse(InNetworkFeature, true)),
    (
      "gork_content_creator_in_network",
      candidate =>
        candidate.getOrElse(GorkContentCreatorFeature, false) &&
          candidate.getOrElse(InNetworkFeature, true)),
    (
      "gork_content_creator_out_of_network",
      candidate =>
        candidate.getOrElse(GorkContentCreatorFeature, false) &&
          !candidate.getOrElse(InNetworkFeature, true)),
    ("has_location", _.getOrElse(LocationIdFeature, None).isDefined),
    ("article", _.getOrElse(IsArticleFeature, false)),
    (
      "grok_category_news",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_sports",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    ("grok_category_entertainment", _ => false),
    (
      "grok_category_business_&_finance",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_technology",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_gaming",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_movies_&_tv",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_music",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_travel",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_food",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_fashion",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_health_&_fitness",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_anime",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_celebrity",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_cryptocurrency",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_science",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_memes",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    ("grok_category_art", _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_religion",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_shopping",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_cars",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_aviation",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_motorcycles",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_beauty",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_nature_&_outdoors",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_pets",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_relationships",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_home_&_garden",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_career",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_dance",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_education",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_podcasts",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    (
      "grok_category_streaming",
      _.getOrElse(GrokTopCategoryFeature, None).contains(<removed_id>)),
    ("grok_is_gore", _.getOrElse(GrokIsGoreFeature, None).getOrElse(false)),
    ("grok_is_nsfw", _.getOrElse(GrokIsNsfwFeature, None).getOrElse(false)),
    ("grok_is_spam", _.getOrElse(GrokIsSpamFeature, None).getOrElse(false)),
    ("grok_is_violent", _.getOrElse(GrokIsViolentFeature, None).getOrElse(false)),
    ("grok_is_low_quality", _.getOrElse(GrokIsLowQualityFeature, None).getOrElse(false)),
    ("grok_is_ocr", _.getOrElse(GrokIsOcrFeature, None).getOrElse(false)),    
    (
      "grok_politics_neutral", // Purely for metrics tracking. Does not affect the recommendations.
      _.getOrElse(GrokPoliticalInclinationFeature, None).contains(hmt.PoliticalInclination.Neutral)
    ),
    (
      "grok_politics_left", // Purely for metrics tracking. Does not affect the recommendations.
      _.getOrElse(GrokPoliticalInclinationFeature, None).contains(hmt.PoliticalInclination.Left)
    ),
    (
      "grok_politics_right", // Purely for metrics tracking. Does not affect the recommendations.
      _.getOrElse(GrokPoliticalInclinationFeature, None).contains(hmt.PoliticalInclination.Right)
    ),
    ("is_slop_lte_0", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ <= 0.0)),
    ("is_slop_lte_0_2", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ <= 0.2)),
    ("is_slop_gt_0", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ > 0.0)),
    ("is_slop_gt_0_2", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ > 0.2)),
    ("is_slop_gt_0_4", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ > 0.4)),
    ("is_slop_gt_0_6", _.getOrElse(SlopAuthorScoreFeature, None).exists(_ > 0.6)),
    (
      "unique_author_ratio_lte_50_pct",
      features => {
        val uniqueAuthorCount = features.getOrElse(UniqueAuthorCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(1)
        uniqueAuthorCount.toDouble / servedSize <= 0.5
      }
    ),
    ("unique_author_lte_5", _.getOrElse(UniqueAuthorCountFeature, None).exists(_ <= 5)),
    ("unique_author_lte_10", _.getOrElse(UniqueAuthorCountFeature, None).exists(_ <= 10)),
    ("unique_author_lte_15", _.getOrElse(UniqueAuthorCountFeature, None).exists(_ <= 15)),
    (
      "single_author_gte_25_pct",
      features => {
        val maxCount = features.getOrElse(MaxSingleAuthorCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(0)
        servedSize > 0 && maxCount.toDouble / servedSize >= 0.25
      }),
    (
      "single_author_gte_50_pct",
      features => {
        val maxCount = features.getOrElse(MaxSingleAuthorCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(0)
        servedSize > 0 && maxCount.toDouble / servedSize >= 0.5
      }),
    (
      "unique_category_ratio_lte_50_pct",
      features => {
        val uniqueAuthorCount = features.getOrElse(UniqueCategoryCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(1)
        uniqueAuthorCount.toDouble / servedSize <= 0.5
      }
    ),
    ("unique_category_lte_5", _.getOrElse(UniqueCategoryCountFeature, None).exists(_ <= 5)),
    ("unique_category_lte_10", _.getOrElse(UniqueCategoryCountFeature, None).exists(_ <= 10)),
    ("unique_category_lte_15", _.getOrElse(UniqueCategoryCountFeature, None).exists(_ <= 15)),
    (
      "single_category_gte_25_pct",
      features => {
        val maxCount = features.getOrElse(MaxSingleCategoryCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(0)
        servedSize > 0 && maxCount.toDouble / servedSize >= 0.25
      }),
    (
      "single_category_gte_50_pct",
      features => {
        val maxCount = features.getOrElse(MaxSingleCategoryCountFeature, None).getOrElse(0)
        val servedSize = features.getOrElse(ServedSizeFeature, None).getOrElse(0)
        servedSize > 0 && maxCount.toDouble / servedSize >= 0.5
      }),
    ("is_grokslopscore_low_1", _.getOrElse(GrokSlopScoreFeature, None).contains(1L)),
    ("is_grokslopscore_med_2", _.getOrElse(GrokSlopScoreFeature, None).contains(2L)),
    ("is_grokslopscore_high_3", _.getOrElse(GrokSlopScoreFeature, None).contains(3L)),
    ("is_boosted", _.getOrElse(IsBoostedCandidateFeature, false)),
    (
      "has_source_signal_tweet_favorite",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetFavorite"))),
    (
      "has_source_signal_retweet",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("Retweet"))),
    (
      "has_source_signal_reply",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("Reply"))),
    (
      "has_source_signal_tweet_bookmark_v1",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetBookmarkV1"))),
    (
      "has_source_signal_original_tweet",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("OriginalTweet"))),
    (
      "has_source_signal_account_follow",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("AccountFollow"))),
    (
      "has_source_signal_tweet_share_v1",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetShareV1"))),
    (
      "has_source_signal_tweet_photo_expand",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetPhotoExpand"))),
    (
      "has_source_signal_search_tweet_click",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("SearchTweetClick"))),
    (
      "has_source_signal_profile_tweet_click",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("ProfileTweetClick"))),
    (
      "has_source_signal_tweet_video_open",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetVideoOpen"))),
    (
      "has_source_signal_video_view_90d_quality_v1_all_surfaces",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("VideoView90dQualityV1AllSurfaces"))),
    (
      "has_source_signal_video_view_90d_quality_v2",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("VideoView90dQualityV2"))),
    (
      "has_source_signal_video_view_90d_quality_v2_visibility_75",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("VideoView90dQualityV2Visibility75"))),
    (
      "has_source_signal_video_view_90d_quality_v2_visibility_100",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("VideoView90dQualityV2Visibility100"))),
    (
      "has_source_signal_video_view_90d_quality_v3",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("VideoView90dQualityV3"))),
    (
      "has_source_signal_account_block",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("AccountBlock"))),
    (
      "has_source_signal_account_mute",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("AccountMute"))),
    (
      "has_source_signal_tweet_report",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetReport"))),
    (
      "has_source_signal_tweet_dont_like",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetDontLike"))),
    (
      "has_source_signal_tweet_report_v2",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetReportV2"))),
    (
      "has_source_signal_tweet_dont_like_v2",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("TweetDontLikeV2"))),
    (
      "has_source_signal_notification_open_and_click_v1",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("NotificationOpenAndClickV1"))),
    (
      "has_source_signal_feedback_notrelevant",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("FeedbackNotrelevant"))),
    (
      "has_source_signal_feedback_relevant",
      _.getOrElse(SourceSignalFeature, None).exists(_.signalType.contains("FeedbackRelevant"))),
    (
      "has_source_signal_high_quality_source_tweet",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("HighQualitySourceTweet"))),
    (
      "has_source_signal_high_quality_source_user",
      _.getOrElse(SourceSignalFeature, None)
        .exists(_.signalType.contains("HighQualitySourceUser"))),
  )

  val PredicateMap = CandidatePredicates.toMap
}
