package com.twitter.timelines.prediction.common.aggregates

import com.twitter.ml.api.matcher.FeatureMatcher
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.TypedAggregateGroup
import scala.collection.JavaConverters._

object FeatureSelectorConfig {
  val BasePairsToStore = Seq(
    ("twitter_wide_user_aggregate.pair", "*"),
    ("twitter_wide_user_author_aggregate.pair", "*"),
    ("user_aggregate_v5.continuous.pair", "*"),
    ("user_aggregate_v7.pair", "*"),
    ("user_author_aggregate_v2.pair", "recap.earlybird.*"),
    ("user_author_aggregate_v2.pair", "recap.searchfeature.*"),
    ("user_author_aggregate_v2.pair", "recap.tweetfeature.embeds*"),
    ("user_author_aggregate_v2.pair", "recap.tweetfeature.link_count*"),
    ("user_author_aggregate_v2.pair", "engagement_features.in_network.*"),
    ("user_author_aggregate_v2.pair", "recap.tweetfeature.is_reply.*"),
    ("user_author_aggregate_v2.pair", "recap.tweetfeature.is_retweet.*"),
    ("user_author_aggregate_v2.pair", "recap.tweetfeature.num_mentions.*"),
    ("user_author_aggregate_v5.pair", "*"),
    ("user_author_aggregate_tweetsource_v1.pair", "*"),
    ("user_engager_aggregate.pair", "*"),
    ("user_mention_aggregate.pair", "*"),
    ("user_request_context_aggregate.dow.pair", "*"),
    ("user_request_context_aggregate.hour.pair", "*"),
    ("user_aggregate_v6.pair", "*"),
    ("user_original_author_aggregate_v1.pair", "*"),
    ("user_original_author_aggregate_v2.pair", "*"),
    ("original_author_aggregate_v1.pair", "*"),
    ("original_author_aggregate_v2.pair", "*"),
    ("author_topic_aggregate.pair", "*"),
    ("user_list_aggregate.pair", "*"),
    ("user_topic_aggregate.pair", "*"),
    ("user_topic_aggregate_v2.pair", "*"),
    ("user_inferred_topic_aggregate.pair", "*"),
    ("user_inferred_topic_aggregate_v2.pair", "*"),
    ("user_media_annotation_aggregate.pair", "*"),
    ("user_media_annotation_aggregate.pair", "*"),
    ("user_author_good_click_aggregate.pair", "*"),
    ("user_engager_good_click_aggregate.pair", "*")
  )
  val PairsToStore = BasePairsToStore ++ Seq(
    ("user_aggregate_v2.pair", "*"),
    ("user_aggregate_v5.boolean.pair", "*"),
    ("user_aggregate_tweetsource_v1.pair", "*"),
  )


  val LabelsToStore = Seq(
    "any_label",
    "recap.engagement.is_favorited",
    "recap.engagement.is_retweeted",
    "recap.engagement.is_replied",
    "recap.engagement.is_open_linked",
    "recap.engagement.is_profile_clicked",
    "recap.engagement.is_clicked",
    "recap.engagement.is_photo_expanded",
    "recap.engagement.is_video_playback_50",
    "recap.engagement.is_video_quality_viewed",
    "recap.engagement.is_replied_reply_impressed_by_author",
    "recap.engagement.is_replied_reply_favorited_by_author",
    "recap.engagement.is_replied_reply_replied_by_author",
    "recap.engagement.is_report_tweet_clicked",
    "recap.engagement.is_block_clicked",
    "recap.engagement.is_mute_clicked",
    "recap.engagement.is_dont_like",
    "recap.engagement.is_good_clicked_convo_desc_favorited_or_replied",
    "recap.engagement.is_good_clicked_convo_desc_v2",
    "itl.engagement.is_favorited",
    "itl.engagement.is_retweeted",
    "itl.engagement.is_replied",
    "itl.engagement.is_open_linked",
    "itl.engagement.is_profile_clicked",
    "itl.engagement.is_clicked",
    "itl.engagement.is_photo_expanded",
    "itl.engagement.is_video_playback_50"
  )

  val PairGlobsToStore = for {
    (prefix, suffix) <- PairsToStore
    label <- LabelsToStore
  } yield FeatureMatcher.glob(prefix + "." + label + "." + suffix)

  val BaseAggregateV2FeatureSelector = FeatureMatcher
    .none()
    .or(
      FeatureMatcher.glob("meta.user_id"),
      FeatureMatcher.glob("meta.author_id"),
      FeatureMatcher.glob("entities.original_author_id"),
      FeatureMatcher.glob("entities.topic_id"),
      FeatureMatcher
        .glob("entities.inferred_topic_ids" + TypedAggregateGroup.SparseFeatureSuffix),
      FeatureMatcher.glob("timelines.meta.list_id"),
      FeatureMatcher.glob("list.id"),
      FeatureMatcher
        .glob("engagement_features.user_ids.public" + TypedAggregateGroup.SparseFeatureSuffix),
      FeatureMatcher
        .glob("entities.users.mentioned_screen_names" + TypedAggregateGroup.SparseFeatureSuffix),
      FeatureMatcher.glob("user_aggregate_v2.pair.recap.engagement.is_dont_like.*"),
      FeatureMatcher.glob("user_author_aggregate_v2.pair.any_label.recap.tweetfeature.has_*"),
      FeatureMatcher.glob("request_context.country_code"),
      FeatureMatcher.glob("request_context.timestamp_gmt_dow"),
      FeatureMatcher.glob("request_context.timestamp_gmt_hour"),
      FeatureMatcher.glob(
        "semantic_core.media_understanding.high_recall.non_sensitive.entity_ids" + TypedAggregateGroup.SparseFeatureSuffix)
    )

  val AggregatesV2ProdFeatureSelector = BaseAggregateV2FeatureSelector
    .orList(PairGlobsToStore.asJava)

  val ReducedPairGlobsToStore = (for {
    (prefix, suffix) <- BasePairsToStore
    label <- LabelsToStore
  } yield FeatureMatcher.glob(prefix + "." + label + "." + suffix)) ++ Seq(
    FeatureMatcher.glob("user_aggregate_v2.pair.any_label.*"),
    FeatureMatcher.glob("user_aggregate_v2.pair.recap.engagement.is_favorited.*"),
    FeatureMatcher.glob("user_aggregate_v2.pair.recap.engagement.is_photo_expanded.*"),
    FeatureMatcher.glob("user_aggregate_v2.pair.recap.engagement.is_profile_clicked.*")
  )
}
