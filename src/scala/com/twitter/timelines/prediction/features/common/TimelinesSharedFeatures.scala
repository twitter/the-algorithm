package com.twitter.timelines.prediction.features.common

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.Feature.Discrete
import com.twitter.ml.api.Feature.SparseBinary
import com.twitter.ml.api.Feature.SparseContinuous
import com.twitter.ml.api.Feature.Text
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.TypedAggregateGroup
import scala.collection.JavaConverters._

object TimelinesSharedFeatures extends TimelinesSharedFeatures("")
object InReplyToTweetTimelinesSharedFeatures extends TimelinesSharedFeatures("in_reply_to_tweet")

/**
 * Defines shared features
 */
class TimelinesSharedFeatures(prefix: String) {
  private def name(featureName: String): String = {
    if (prefix.nonEmpty) {
      s"$prefix.$featureName"
    } else {
      featureName
    }
  }

  // meta
  val EXPERIMENT_META = new SparseBinary(
    name("timelines.meta.experiment_meta"),
    Set(ExperimentId, ExperimentName).asJava)

  // historically used in the "combined models" to distinguish in-network and out of network tweets.
  // now the feature denotes which adapter (recap or rectweet) was used to generate the datarecords.
  // and is used by the data collection pipeline to split the training data.
  val INJECTION_TYPE = new Discrete(name("timelines.meta.injection_type"))

  // Used to indicate which injection module is this
  val INJECTION_MODULE_NAME = new Text(name("timelines.meta.injection_module_name"))

  val LIST_ID = new Discrete(name("timelines.meta.list_id"))
  val LIST_IS_PINNED = new Binary(name("timelines.meta.list_is_pinned"))

  // internal id per each PS request. mainly to join back commomn features and candidate features later
  val PREDICTION_REQUEST_ID = new Discrete(name("timelines.meta.prediction_request_id"))
  // internal id per each TLM request. mainly to deduplicate re-served cached tweets in logging
  val SERVED_REQUEST_ID = new Discrete(name("timelines.meta.served_request_id"))
  // internal id used for join key in kafka logging, equal to servedRequestId if tweet is cached,
  // else equal to predictionRequestId
  val SERVED_ID = new Discrete(name("timelines.meta.served_id"))
  val REQUEST_JOIN_ID = new Discrete(name("timelines.meta.request_join_id"))

  // Internal boolean flag per tweet, whether the tweet is served from RankedTweetsCache: TQ-14050
  // this feature should not be trained on, blacklisted in feature_config: D838346
  val IS_READ_FROM_CACHE = new Binary(name("timelines.meta.is_read_from_cache"))

  // model score discounts
  val PHOTO_DISCOUNT = new Continuous(name("timelines.score_discounts.photo"))
  val VIDEO_DISCOUNT = new Continuous(name("timelines.score_discounts.video"))
  val TWEET_HEIGHT_DISCOUNT = new Continuous(name("timelines.score_discounts.tweet_height"))
  val TOXICITY_DISCOUNT = new Continuous(name("timelines.score_discounts.toxicity"))

  // engagements
  val ENGAGEMENT_TYPE = new Discrete(name("timelines.engagement.type"))
  val PREDICTED_IS_FAVORITED =
    new Continuous(name("timelines.engagement_predicted.is_favorited"), Set(EngagementScore).asJava)
  val PREDICTED_IS_RETWEETED =
    new Continuous(name("timelines.engagement_predicted.is_retweeted"), Set(EngagementScore).asJava)
  val PREDICTED_IS_QUOTED =
    new Continuous(name("timelines.engagement_predicted.is_quoted"), Set(EngagementScore).asJava)
  val PREDICTED_IS_REPLIED =
    new Continuous(name("timelines.engagement_predicted.is_replied"), Set(EngagementScore).asJava)
  val PREDICTED_IS_OPEN_LINKED = new Continuous(
    name("timelines.engagement_predicted.is_open_linked"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_OPEN_LINK = new Continuous(
    name("timelines.engagement_predicted.is_good_open_link"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_PROFILE_CLICKED = new Continuous(
    name("timelines.engagement_predicted.is_profile_clicked"),
    Set(EngagementScore).asJava
  )
  val PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED = new Continuous(
    name("timelines.engagement_predicted.is_profile_clicked_and_profile_engaged"),
    Set(EngagementScore).asJava
  )
  val PREDICTED_IS_CLICKED =
    new Continuous(name("timelines.engagement_predicted.is_clicked"), Set(EngagementScore).asJava)
  val PREDICTED_IS_PHOTO_EXPANDED = new Continuous(
    name("timelines.engagement_predicted.is_photo_expanded"),
    Set(EngagementScore).asJava
  )
  val PREDICTED_IS_FOLLOWED =
    new Continuous(name("timelines.engagement_predicted.is_followed"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DONT_LIKE =
    new Continuous(name("timelines.engagement_predicted.is_dont_like"), Set(EngagementScore).asJava)
  val PREDICTED_IS_VIDEO_PLAYBACK_50 = new Continuous(
    name("timelines.engagement_predicted.is_video_playback_50"),
    Set(EngagementScore).asJava
  )
  val PREDICTED_IS_VIDEO_QUALITY_VIEWED = new Continuous(
    name("timelines.engagement_predicted.is_video_quality_viewed"),
    Set(EngagementScore).asJava
  )
  val PREDICTED_IS_GOOD_CLICKED_V1 = new Continuous(
    name("timelines.engagement_predicted.is_good_clicked_convo_desc_favorited_or_replied"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_CLICKED_V2 = new Continuous(
    name("timelines.engagement_predicted.is_good_clicked_convo_desc_v2"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_8_SEC = new Continuous(
    name("timelines.engagement_predicted.is_tweet_detail_dwelled_8_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_15_SEC = new Continuous(
    name("timelines.engagement_predicted.is_tweet_detail_dwelled_15_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_25_SEC = new Continuous(
    name("timelines.engagement_predicted.is_tweet_detail_dwelled_25_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_30_SEC = new Continuous(
    name("timelines.engagement_predicted.is_tweet_detail_dwelled_30_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_CLICKED_WITH_DWELL_SUM_GTE_60S = new Continuous(
    name(
      "timelines.engagement_predicted.is_good_clicked_convo_desc_favorited_or_replied_or_dwell_sum_gte_60_secs"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FAVORITED_FAV_ENGAGED_BY_AUTHOR = new Continuous(
    name("timelines.engagement_predicted.is_favorited_fav_engaged_by_author"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_REPORT_TWEET_CLICKED =
    new Continuous(
      name("timelines.engagement_predicted.is_report_tweet_clicked"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_NEGATIVE_FEEDBACK = new Continuous(
    name("timelines.engagement_predicted.is_negative_feedback"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_NEGATIVE_FEEDBACK_V2 = new Continuous(
    name("timelines.engagement_predicted.is_negative_feedback_v2"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_WEAK_NEGATIVE_FEEDBACK = new Continuous(
    name("timelines.engagement_predicted.is_weak_negative_feedback"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_STRONG_NEGATIVE_FEEDBACK = new Continuous(
    name("timelines.engagement_predicted.is_strong_negative_feedback"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_DWELLED_IN_BOUNDS_V1 = new Continuous(
    name("timelines.engagement_predicted.is_dwelled_in_bounds_v1"),
    Set(EngagementScore).asJava)
  val PREDICTED_DWELL_NORMALIZED_OVERALL = new Continuous(
    name("timelines.engagement_predicted.dwell_normalized_overall"),
    Set(EngagementScore).asJava)
  val PREDICTED_DWELL_CDF =
    new Continuous(name("timelines.engagement_predicted.dwell_cdf"), Set(EngagementScore).asJava)
  val PREDICTED_DWELL_CDF_OVERALL = new Continuous(
    name("timelines.engagement_predicted.dwell_cdf_overall"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED =
    new Continuous(name("timelines.engagement_predicted.is_dwelled"), Set(EngagementScore).asJava)

  val PREDICTED_IS_HOME_LATEST_VISITED = new Continuous(
    name("timelines.engagement_predicted.is_home_latest_visited"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_BOOKMARKED = new Continuous(
    name("timelines.engagement_predicted.is_bookmarked"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_SHARED =
    new Continuous(name("timelines.engagement_predicted.is_shared"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SHARE_MENU_CLICKED = new Continuous(
    name("timelines.engagement_predicted.is_share_menu_clicked"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_PROFILE_DWELLED_20_SEC = new Continuous(
    name("timelines.engagement_predicted.is_profile_dwelled_20_sec"),
    Set(EngagementScore).asJava)

  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_5_SEC = new Continuous(
    name("timelines.engagement_predicted.is_fullscreen_video_dwelled_5_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_10_SEC = new Continuous(
    name("timelines.engagement_predicted.is_fullscreen_video_dwelled_10_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_20_SEC = new Continuous(
    name("timelines.engagement_predicted.is_fullscreen_video_dwelled_20_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_30_SEC = new Continuous(
    name("timelines.engagement_predicted.is_fullscreen_video_dwelled_30_sec"),
    Set(EngagementScore).asJava)

  // Please use this timestamp, not the `meta.timestamp`, for the actual served timestamp.
  val SERVED_TIMESTAMP =
    new Discrete("timelines.meta.timestamp.served", Set(PrivateTimestamp).asJava)

  // timestamp when the engagement has occurred. do not train on these features
  val TIMESTAMP_FAVORITED =
    new Discrete("timelines.meta.timestamp.engagement.favorited", Set(PublicTimestamp).asJava)
  val TIMESTAMP_RETWEETED =
    new Discrete("timelines.meta.timestamp.engagement.retweeted", Set(PublicTimestamp).asJava)
  val TIMESTAMP_REPLIED =
    new Discrete("timelines.meta.timestamp.engagement.replied", Set(PublicTimestamp).asJava)
  val TIMESTAMP_PROFILE_CLICKED = new Discrete(
    "timelines.meta.timestamp.engagement.profile_clicked",
    Set(PrivateTimestamp).asJava)
  val TIMESTAMP_CLICKED =
    new Discrete("timelines.meta.timestamp.engagement.clicked", Set(PrivateTimestamp).asJava)
  val TIMESTAMP_PHOTO_EXPANDED =
    new Discrete("timelines.meta.timestamp.engagement.photo_expanded", Set(PrivateTimestamp).asJava)
  val TIMESTAMP_DWELLED =
    new Discrete("timelines.meta.timestamp.engagement.dwelled", Set(PrivateTimestamp).asJava)
  val TIMESTAMP_VIDEO_PLAYBACK_50 = new Discrete(
    "timelines.meta.timestamp.engagement.video_playback_50",
    Set(PrivateTimestamp).asJava)
  // reply engaged by author
  val TIMESTAMP_REPLY_FAVORITED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.reply_favorited_by_author",
    Set(PublicTimestamp).asJava)
  val TIMESTAMP_REPLY_REPLIED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.reply_replied_by_author",
    Set(PublicTimestamp).asJava)
  val TIMESTAMP_REPLY_RETWEETED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.reply_retweeted_by_author",
    Set(PublicTimestamp).asJava)
  // fav engaged by author
  val TIMESTAMP_FAV_FAVORITED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.fav_favorited_by_author",
    Set(PublicTimestamp).asJava)
  val TIMESTAMP_FAV_REPLIED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.fav_replied_by_author",
    Set(PublicTimestamp).asJava)
  val TIMESTAMP_FAV_RETWEETED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.fav_retweeted_by_author",
    Set(PublicTimestamp).asJava)
  val TIMESTAMP_FAV_FOLLOWED_BY_AUTHOR = new Discrete(
    "timelines.meta.timestamp.engagement.fav_followed_by_author",
    Set(PublicTimestamp).asJava)
  // good click
  val TIMESTAMP_GOOD_CLICK_CONVO_DESC_FAVORITED = new Discrete(
    "timelines.meta.timestamp.engagement.good_click_convo_desc_favorited",
    Set(PrivateTimestamp).asJava)
  val TIMESTAMP_GOOD_CLICK_CONVO_DESC_REPLIIED = new Discrete(
    "timelines.meta.timestamp.engagement.good_click_convo_desc_replied",
    Set(PrivateTimestamp).asJava)
  val TIMESTAMP_GOOD_CLICK_CONVO_DESC_PROFILE_CLICKED = new Discrete(
    "timelines.meta.timestamp.engagement.good_click_convo_desc_profiile_clicked",
    Set(PrivateTimestamp).asJava)
  val TIMESTAMP_NEGATIVE_FEEDBACK = new Discrete(
    "timelines.meta.timestamp.engagement.negative_feedback",
    Set(PrivateTimestamp).asJava)
  val TIMESTAMP_REPORT_TWEET_CLICK =
    new Discrete(
      "timelines.meta.timestamp.engagement.report_tweet_click",
      Set(PrivateTimestamp).asJava)
  val TIMESTAMP_IMPRESSED =
    new Discrete("timelines.meta.timestamp.engagement.impressed", Set(PublicTimestamp).asJava)
  val TIMESTAMP_TWEET_DETAIL_DWELLED =
    new Discrete(
      "timelines.meta.timestamp.engagement.tweet_detail_dwelled",
      Set(PublicTimestamp).asJava)
  val TIMESTAMP_PROFILE_DWELLED =
    new Discrete("timelines.meta.timestamp.engagement.profile_dwelled", Set(PublicTimestamp).asJava)
  val TIMESTAMP_FULLSCREEN_VIDEO_DWELLED =
    new Discrete(
      "timelines.meta.timestamp.engagement.fullscreen_video_dwelled",
      Set(PublicTimestamp).asJava)
  val TIMESTAMP_LINK_DWELLED =
    new Discrete("timelines.meta.timestamp.engagement.link_dwelled", Set(PublicTimestamp).asJava)

  // these are used to dup and split the negative instances during streaming processing (kafka)
  val TRAINING_FOR_FAVORITED =
    new Binary("timelines.meta.training_data.for_favorited", Set(EngagementId).asJava)
  val TRAINING_FOR_RETWEETED =
    new Binary("timelines.meta.training_data.for_retweeted", Set(EngagementId).asJava)
  val TRAINING_FOR_REPLIED =
    new Binary("timelines.meta.training_data.for_replied", Set(EngagementId).asJava)
  val TRAINING_FOR_PROFILE_CLICKED =
    new Binary("timelines.meta.training_data.for_profile_clicked", Set(EngagementId).asJava)
  val TRAINING_FOR_CLICKED =
    new Binary("timelines.meta.training_data.for_clicked", Set(EngagementId).asJava)
  val TRAINING_FOR_PHOTO_EXPANDED =
    new Binary("timelines.meta.training_data.for_photo_expanded", Set(EngagementId).asJava)
  val TRAINING_FOR_VIDEO_PLAYBACK_50 =
    new Binary("timelines.meta.training_data.for_video_playback_50", Set(EngagementId).asJava)
  val TRAINING_FOR_NEGATIVE_FEEDBACK =
    new Binary("timelines.meta.training_data.for_negative_feedback", Set(EngagementId).asJava)
  val TRAINING_FOR_REPORTED =
    new Binary("timelines.meta.training_data.for_reported", Set(EngagementId).asJava)
  val TRAINING_FOR_DWELLED =
    new Binary("timelines.meta.training_data.for_dwelled", Set(EngagementId).asJava)
  val TRAINING_FOR_SHARED =
    new Binary("timelines.meta.training_data.for_shared", Set(EngagementId).asJava)
  val TRAINING_FOR_SHARE_MENU_CLICKED =
    new Binary("timelines.meta.training_data.for_share_menu_clicked", Set(EngagementId).asJava)

  // Warning: do not train on these features
  val PREDICTED_SCORE = new Continuous(name("timelines.score"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_FAV = new Continuous(name("timelines.score.fav"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_RETWEET =
    new Continuous(name("timelines.score.retweet"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_REPLY =
    new Continuous(name("timelines.score.reply"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_OPEN_LINK =
    new Continuous(name("timelines.score.open_link"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_GOOD_OPEN_LINK =
    new Continuous(name("timelines.score.good_open_link"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_PROFILE_CLICK =
    new Continuous(name("timelines.score.profile_click"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DETAIL_EXPAND =
    new Continuous(name("timelines.score.detail_expand"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_PHOTO_EXPAND =
    new Continuous(name("timelines.score.photo_expand"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_PLAYBACK_50 =
    new Continuous(name("timelines.score.playback_50"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_VIDEO_QUALITY_VIEW =
    new Continuous(name("timelines.score.video_quality_view"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DONT_LIKE =
    new Continuous(name("timelines.score.dont_like"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_PROFILE_CLICKED_AND_PROFILE_ENGAGED =
    new Continuous(
      name("timelines.score.profile_clicked_and_profile_engaged"),
      Set(EngagementScore).asJava)
  val PREDICTED_SCORE_GOOD_CLICKED_V1 =
    new Continuous(name("timelines.score.good_clicked_v1"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_GOOD_CLICKED_V2 =
    new Continuous(name("timelines.score.good_clicked_v2"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DWELL =
    new Continuous(name("timelines.score.dwell"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DWELL_CDF =
    new Continuous(name("timelines.score.dwell_cfd"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DWELL_CDF_OVERALL =
    new Continuous(name("timelines.score.dwell_cfd_overall"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_DWELL_NORMALIZED_OVERALL =
    new Continuous(name("timelines.score.dwell_normalized_overall"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_NEGATIVE_FEEDBACK =
    new Continuous(name("timelines.score.negative_feedback"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_NEGATIVE_FEEDBACK_V2 =
    new Continuous(name("timelines.score.negative_feedback_v2"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_WEAK_NEGATIVE_FEEDBACK =
    new Continuous(name("timelines.score.weak_negative_feedback"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_STRONG_NEGATIVE_FEEDBACK =
    new Continuous(name("timelines.score.strong_negative_feedback"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_REPORT_TWEET_CLICKED =
    new Continuous(name("timelines.score.report_tweet_clicked"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_UNFOLLOW_TOPIC =
    new Continuous(name("timelines.score.unfollow_topic"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_FOLLOW =
    new Continuous(name("timelines.score.follow"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_RELEVANCE_PROMPT_YES_CLICKED =
    new Continuous(
      name("timelines.score.relevance_prompt_yes_clicked"),
      Set(EngagementScore).asJava)
  val PREDICTED_SCORE_BOOKMARK =
    new Continuous(name("timelines.score.bookmark"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_SHARE =
    new Continuous(name("timelines.score.share"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_SHARE_MENU_CLICK =
    new Continuous(name("timelines.score.share_menu_click"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_PROFILE_DWELLED =
    new Continuous(name("timelines.score.good_profile_dwelled"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_TWEET_DETAIL_DWELLED =
    new Continuous(name("timelines.score.tweet_detail_dwelled"), Set(EngagementScore).asJava)
  val PREDICTED_SCORE_FULLSCREEN_VIDEO_DWELL =
    new Continuous(name("timelines.score.fullscreen_video_dwell"), Set(EngagementScore).asJava)

  // hydrated in TimelinesSharedFeaturesAdapter that recap adapter calls
  val ORIGINAL_AUTHOR_ID = new Discrete(name("entities.original_author_id"), Set(UserId).asJava)
  val SOURCE_AUTHOR_ID = new Discrete(name("entities.source_author_id"), Set(UserId).asJava)
  val SOURCE_TWEET_ID = new Discrete(name("entities.source_tweet_id"), Set(TweetId).asJava)
  val TOPIC_ID = new Discrete(name("entities.topic_id"), Set(SemanticcoreClassification).asJava)
  val INFERRED_TOPIC_IDS =
    new SparseBinary(name("entities.inferred_topic_ids"), Set(SemanticcoreClassification).asJava)
  val INFERRED_TOPIC_ID = TypedAggregateGroup.sparseFeature(INFERRED_TOPIC_IDS)

  val WEIGHTED_FAV_COUNT = new Continuous(
    name("timelines.earlybird.weighted_fav_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val WEIGHTED_RETWEET_COUNT = new Continuous(
    name("timelines.earlybird.weighted_retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val WEIGHTED_REPLY_COUNT = new Continuous(
    name("timelines.earlybird.weighted_reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val WEIGHTED_QUOTE_COUNT = new Continuous(
    name("timelines.earlybird.weighted_quote_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val EMBEDS_IMPRESSION_COUNT_V2 = new Continuous(
    name("timelines.earlybird.embeds_impression_count_v2"),
    Set(CountOfImpression).asJava)
  val EMBEDS_URL_COUNT_V2 = new Continuous(
    name("timelines.earlybird.embeds_url_count_v2"),
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val DECAYED_FAVORITE_COUNT = new Continuous(
    name("timelines.earlybird.decayed_favorite_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val DECAYED_RETWEET_COUNT = new Continuous(
    name("timelines.earlybird.decayed_retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val DECAYED_REPLY_COUNT = new Continuous(
    name("timelines.earlybird.decayed_reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val DECAYED_QUOTE_COUNT = new Continuous(
    name("timelines.earlybird.decayed_quote_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val FAKE_FAVORITE_COUNT = new Continuous(
    name("timelines.earlybird.fake_favorite_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val FAKE_RETWEET_COUNT = new Continuous(
    name("timelines.earlybird.fake_retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val FAKE_REPLY_COUNT = new Continuous(
    name("timelines.earlybird.fake_reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val FAKE_QUOTE_COUNT = new Continuous(
    name("timelines.earlybird.fake_quote_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val QUOTE_COUNT = new Continuous(
    name("timelines.earlybird.quote_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)

  // Safety features
  val LABEL_ABUSIVE_FLAG =
    new Binary(name("timelines.earlybird.label_abusive_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_ABUSIVE_HI_RCL_FLAG =
    new Binary(name("timelines.earlybird.label_abusive_hi_rcl_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_DUP_CONTENT_FLAG =
    new Binary(name("timelines.earlybird.label_dup_content_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_NSFW_HI_PRC_FLAG =
    new Binary(name("timelines.earlybird.label_nsfw_hi_prc_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_NSFW_HI_RCL_FLAG =
    new Binary(name("timelines.earlybird.label_nsfw_hi_rcl_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_SPAM_FLAG =
    new Binary(name("timelines.earlybird.label_spam_flag"), Set(TweetSafetyLabels).asJava)
  val LABEL_SPAM_HI_RCL_FLAG =
    new Binary(name("timelines.earlybird.label_spam_hi_rcl_flag"), Set(TweetSafetyLabels).asJava)

  // Periscope features
  val PERISCOPE_EXISTS = new Binary(
    name("timelines.earlybird.periscope_exists"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val PERISCOPE_IS_LIVE = new Binary(
    name("timelines.earlybird.periscope_is_live"),
    Set(PrivateBroadcastMetrics, PublicBroadcastMetrics).asJava)
  val PERISCOPE_HAS_BEEN_FEATURED = new Binary(
    name("timelines.earlybird.periscope_has_been_featured"),
    Set(PrivateBroadcastMetrics, PublicBroadcastMetrics).asJava)
  val PERISCOPE_IS_CURRENTLY_FEATURED = new Binary(
    name("timelines.earlybird.periscope_is_currently_featured"),
    Set(PrivateBroadcastMetrics, PublicBroadcastMetrics).asJava
  )
  val PERISCOPE_IS_FROM_QUALITY_SOURCE = new Binary(
    name("timelines.earlybird.periscope_is_from_quality_source"),
    Set(PrivateBroadcastMetrics, PublicBroadcastMetrics).asJava
  )

  val VISIBLE_TOKEN_RATIO = new Continuous(name("timelines.earlybird.visible_token_ratio"))
  val HAS_QUOTE = new Binary(
    name("timelines.earlybird.has_quote"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val IS_COMPOSER_SOURCE_CAMERA = new Binary(
    name("timelines.earlybird.is_composer_source_camera"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)

  val EARLYBIRD_SCORE = new Continuous(
    name("timelines.earlybird_score"),
    Set(EngagementScore).asJava
  ) // separating from the rest of "timelines.earlybird." namespace

  val DWELL_TIME_MS = new Continuous(
    name("timelines.engagement.dwell_time_ms"),
    Set(EngagementDurationAndTimestamp, ImpressionMetadata, PrivateTimestamp).asJava)

  val TWEET_DETAIL_DWELL_TIME_MS = new Continuous(
    name("timelines.engagement.tweet_detail_dwell_time_ms"),
    Set(EngagementDurationAndTimestamp, ImpressionMetadata, PrivateTimestamp).asJava)

  val PROFILE_DWELL_TIME_MS = new Continuous(
    name("timelines.engagement.profile_dwell_time_ms"),
    Set(EngagementDurationAndTimestamp, ImpressionMetadata, PrivateTimestamp).asJava)

  val FULLSCREEN_VIDEO_DWELL_TIME_MS = new Continuous(
    name("timelines.engagement.fullscreen_video_dwell_time_ms"),
    Set(EngagementDurationAndTimestamp, ImpressionMetadata, PrivateTimestamp).asJava)

  val LINK_DWELL_TIME_MS = new Continuous(
    name("timelines.engagement.link_dwell_time_ms"),
    Set(EngagementDurationAndTimestamp, ImpressionMetadata, PrivateTimestamp).asJava)

  val ASPECT_RATIO_DEN = new Continuous(
    name("tweetsource.tweet.media.aspect_ratio_den"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val ASPECT_RATIO_NUM = new Continuous(
    name("tweetsource.tweet.media.aspect_ratio_num"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val BIT_RATE = new Continuous(
    name("tweetsource.tweet.media.bit_rate"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HEIGHT_2 = new Continuous(
    name("tweetsource.tweet.media.height_2"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HEIGHT_1 = new Continuous(
    name("tweetsource.tweet.media.height_1"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HEIGHT_3 = new Continuous(
    name("tweetsource.tweet.media.height_3"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HEIGHT_4 = new Continuous(
    name("tweetsource.tweet.media.height_4"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val RESIZE_METHOD_1 = new Discrete(
    name("tweetsource.tweet.media.resize_method_1"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val RESIZE_METHOD_2 = new Discrete(
    name("tweetsource.tweet.media.resize_method_2"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val RESIZE_METHOD_3 = new Discrete(
    name("tweetsource.tweet.media.resize_method_3"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val RESIZE_METHOD_4 = new Discrete(
    name("tweetsource.tweet.media.resize_method_4"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val VIDEO_DURATION = new Continuous(
    name("tweetsource.tweet.media.video_duration"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val WIDTH_1 = new Continuous(
    name("tweetsource.tweet.media.width_1"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val WIDTH_2 = new Continuous(
    name("tweetsource.tweet.media.width_2"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val WIDTH_3 = new Continuous(
    name("tweetsource.tweet.media.width_3"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val WIDTH_4 = new Continuous(
    name("tweetsource.tweet.media.width_4"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val NUM_MEDIA_TAGS = new Continuous(
    name("tweetsource.tweet.media.num_tags"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val MEDIA_TAG_SCREEN_NAMES = new SparseBinary(
    name("tweetsource.tweet.media.tag_screen_names"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val STICKER_IDS = new SparseBinary(
    name("tweetsource.tweet.media.sticker_ids"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)

  val NUM_COLOR_PALLETTE_ITEMS = new Continuous(
    name("tweetsource.v2.tweet.media.num_color_pallette_items"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val COLOR_1_RED = new Continuous(
    name("tweetsource.v2.tweet.media.color_1_red"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val COLOR_1_BLUE = new Continuous(
    name("tweetsource.v2.tweet.media.color_1_blue"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val COLOR_1_GREEN = new Continuous(
    name("tweetsource.v2.tweet.media.color_1_green"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val COLOR_1_PERCENTAGE = new Continuous(
    name("tweetsource.v2.tweet.media.color_1_percentage"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val MEDIA_PROVIDERS = new SparseBinary(
    name("tweetsource.v2.tweet.media.providers"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val IS_360 = new Binary(
    name("tweetsource.v2.tweet.media.is_360"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val VIEW_COUNT =
    new Continuous(name("tweetsource.v2.tweet.media.view_count"), Set(MediaContentMetrics).asJava)
  val IS_MANAGED = new Binary(
    name("tweetsource.v2.tweet.media.is_managed"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val IS_MONETIZABLE = new Binary(
    name("tweetsource.v2.tweet.media.is_monetizable"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val IS_EMBEDDABLE = new Binary(
    name("tweetsource.v2.tweet.media.is_embeddable"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val CLASSIFICATION_LABELS = new SparseContinuous(
    name("tweetsource.v2.tweet.media.classification_labels"),
    Set(MediaFile, MediaProcessingInformation).asJava)

  val NUM_STICKERS = new Continuous(
    name("tweetsource.v2.tweet.media.num_stickers"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val NUM_FACES = new Continuous(
    name("tweetsource.v2.tweet.media.num_faces"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val FACE_AREAS = new Continuous(
    name("tweetsource.v2.tweet.media.face_areas"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_SELECTED_PREVIEW_IMAGE = new Binary(
    name("tweetsource.v2.tweet.media.has_selected_preview_image"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_TITLE = new Binary(
    name("tweetsource.v2.tweet.media.has_title"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_DESCRIPTION = new Binary(
    name("tweetsource.v2.tweet.media.has_description"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_VISIT_SITE_CALL_TO_ACTION = new Binary(
    name("tweetsource.v2.tweet.media.has_visit_site_call_to_action"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_APP_INSTALL_CALL_TO_ACTION = new Binary(
    name("tweetsource.v2.tweet.media.has_app_install_call_to_action"),
    Set(MediaFile, MediaProcessingInformation).asJava)
  val HAS_WATCH_NOW_CALL_TO_ACTION = new Binary(
    name("tweetsource.v2.tweet.media.has_watch_now_call_to_action"),
    Set(MediaFile, MediaProcessingInformation).asJava)

  val NUM_CAPS =
    new Continuous(name("tweetsource.tweet.text.num_caps"), Set(PublicTweets, PrivateTweets).asJava)
  val TWEET_LENGTH =
    new Continuous(name("tweetsource.tweet.text.length"), Set(PublicTweets, PrivateTweets).asJava)
  val TWEET_LENGTH_TYPE = new Discrete(
    name("tweetsource.tweet.text.length_type"),
    Set(PublicTweets, PrivateTweets).asJava)
  val NUM_WHITESPACES = new Continuous(
    name("tweetsource.tweet.text.num_whitespaces"),
    Set(PublicTweets, PrivateTweets).asJava)
  val HAS_QUESTION =
    new Binary(name("tweetsource.tweet.text.has_question"), Set(PublicTweets, PrivateTweets).asJava)
  val NUM_NEWLINES = new Continuous(
    name("tweetsource.tweet.text.num_newlines"),
    Set(PublicTweets, PrivateTweets).asJava)
  val EMOJI_TOKENS = new SparseBinary(
    name("tweetsource.v3.tweet.text.emoji_tokens"),
    Set(PublicTweets, PrivateTweets).asJava)
  val EMOTICON_TOKENS = new SparseBinary(
    name("tweetsource.v3.tweet.text.emoticon_tokens"),
    Set(PublicTweets, PrivateTweets).asJava)
  val NUM_EMOJIS = new Continuous(
    name("tweetsource.v3.tweet.text.num_emojis"),
    Set(PublicTweets, PrivateTweets).asJava)
  val NUM_EMOTICONS = new Continuous(
    name("tweetsource.v3.tweet.text.num_emoticons"),
    Set(PublicTweets, PrivateTweets).asJava)
  val POS_UNIGRAMS = new SparseBinary(
    name("tweetsource.v3.tweet.text.pos_unigrams"),
    Set(PublicTweets, PrivateTweets).asJava)
  val POS_BIGRAMS = new SparseBinary(
    name("tweetsource.v3.tweet.text.pos_bigrams"),
    Set(PublicTweets, PrivateTweets).asJava)
  val TEXT_TOKENS = new SparseBinary(
    name("tweetsource.v4.tweet.text.tokens"),
    Set(PublicTweets, PrivateTweets).asJava)

  // Health features model scores (see go/toxicity, go/pblock, go/pspammytweet)
  val PBLOCK_SCORE =
    new Continuous(name("timelines.earlybird.pblock_score"), Set(TweetSafetyScores).asJava)
  val TOXICITY_SCORE =
    new Continuous(name("timelines.earlybird.toxicity_score"), Set(TweetSafetyScores).asJava)
  val EXPERIMENTAL_HEALTH_MODEL_SCORE_1 =
    new Continuous(
      name("timelines.earlybird.experimental_health_model_score_1"),
      Set(TweetSafetyScores).asJava)
  val EXPERIMENTAL_HEALTH_MODEL_SCORE_2 =
    new Continuous(
      name("timelines.earlybird.experimental_health_model_score_2"),
      Set(TweetSafetyScores).asJava)
  val EXPERIMENTAL_HEALTH_MODEL_SCORE_3 =
    new Continuous(
      name("timelines.earlybird.experimental_health_model_score_3"),
      Set(TweetSafetyScores).asJava)
  val EXPERIMENTAL_HEALTH_MODEL_SCORE_4 =
    new Continuous(
      name("timelines.earlybird.experimental_health_model_score_4"),
      Set(TweetSafetyScores).asJava)
  val PSPAMMY_TWEET_SCORE =
    new Continuous(name("timelines.earlybird.pspammy_tweet_score"), Set(TweetSafetyScores).asJava)
  val PREPORTED_TWEET_SCORE =
    new Continuous(name("timelines.earlybird.preported_tweet_score"), Set(TweetSafetyScores).asJava)

  // where record was displayed e.g. recap vs ranked timeline vs recycled
  // (do NOT use for training in prediction, since this is set post-scoring)
  // This differs from TimelinesSharedFeatures.INJECTION_TYPE, which is only
  // set to Recap or Rectweet, and is available pre-scoring.
  // This also differs from TimeFeatures.IS_TWEET_RECYCLED, which is set
  // pre-scoring and indicates if a tweet is being considered for recycling.
  // In contrast, DISPLAY_SUGGEST_TYPE == RecycledTweet means the tweet
  // was actually served in a recycled tweet module. The two should currently
  // have the same value, but need not in future, so please only use
  // IS_TWEET_RECYCLED/CANDIDATE_TWEET_SOURCE_ID for training models and
  // only use DISPLAY_SUGGEST_TYPE for offline analysis of tweets actually
  // served in recycled modules.
  val DISPLAY_SUGGEST_TYPE = new Discrete(name("recap.display.suggest_type"))

  // Candidate tweet source id - related to DISPLAY_SUGGEST_TYPE above, but this is a
  // property of the candidate rather than display location so is safe to use
  // in model training, unlike DISPLAY_SUGGEST_TYPE.
  val CANDIDATE_TWEET_SOURCE_ID =
    new Discrete(name("timelines.meta.candidate_tweet_source_id"), Set(TweetId).asJava)

  // Was at least 50% of this tweet in the user's viewport for at least 500 ms,
  // OR did the user engage with the tweet publicly or privately
  val IS_LINGER_IMPRESSION =
    new Binary(name("timelines.engagement.is_linger_impression"), Set(EngagementsPrivate).asJava)

  // Features to create rollups
  val LANGUAGE_GROUP = new Discrete(name("timelines.tweet.text.language_group"))

  // The final position index of the tweet being trained on in the timeline
  // served from TLM (could still change later in TLS-API), as recorded by
  // PositionIndexLoggingEnvelopeTransform.
  val FINAL_POSITION_INDEX = new Discrete(name("timelines.display.final_position_index"))

  // The traceId of the timeline request, can be used to group tweets in the same response.
  val TRACE_ID = new Discrete(name("timelines.display.trace_id"), Set(TfeTransactionId).asJava)

  // Whether this tweet was randomly injected into the timeline or not, for exploration purposes
  val IS_RANDOM_TWEET = new Binary(name("timelines.display.is_random_tweet"))

  //  Whether this tweet was reordered with softmax ranking for explore/exploit, and needs to
  //  be excluded from exploit only holdback
  val IS_SOFTMAX_RANKING_TWEET = new Binary(name("timelines.display.is_softmax_ranking_tweet"))

  // Whether the user viewing the tweet has disabled ranked timeline.
  val IS_RANKED_TIMELINE_DISABLER = new Binary(
    name("timelines.user_features.is_ranked_timeline_disabler"),
    Set(AnnotationValue, GeneralSettings).asJava)

  // Whether the user viewing the tweet was one of those released from DDG 4205 control
  // as part of http://go/shrink-4205 process to shrink the quality features holdback.
  val IS_USER_RELEASED_FROM_QUALITY_HOLDBACK = new Binary(
    name("timelines.user_features.is_released_from_quality_holdback"),
    Set(ExperimentId, ExperimentName).asJava)

  val INITIAL_PREDICTION_FAV =
    new Continuous(name("timelines.initial_prediction.fav"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_RETWEET =
    new Continuous(name("timelines.initial_prediction.retweet"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_REPLY =
    new Continuous(name("timelines.initial_prediction.reply"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_OPEN_LINK =
    new Continuous(name("timelines.initial_prediction.open_link"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_PROFILE_CLICK =
    new Continuous(name("timelines.initial_prediction.profile_click"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_VIDEO_PLAYBACK_50 = new Continuous(
    name("timelines.initial_prediction.video_playback_50"),
    Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_DETAIL_EXPAND =
    new Continuous(name("timelines.initial_prediction.detail_expand"), Set(EngagementScore).asJava)
  val INITIAL_PREDICTION_PHOTO_EXPAND =
    new Continuous(name("timelines.initial_prediction.photo_expand"), Set(EngagementScore).asJava)

  val VIEWER_FOLLOWS_ORIGINAL_AUTHOR =
    new Binary(name("timelines.viewer_follows_original_author"), Set(Follow).asJava)

  val IS_TOP_ONE = new Binary(name("timelines.position.is_top_one"))
  val IS_TOP_FIVE =
    new Binary(name(featureName = "timelines.position.is_top_five"))
  val IS_TOP_TEN =
    new Binary(name(featureName = "timelines.position.is_top_ten"))

  val LOG_POSITION =
    new Continuous(name(featureName = "timelines.position.log_10"))

}
