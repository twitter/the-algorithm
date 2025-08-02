package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.offline_aggregates.PassThroughAdapter
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.offline_aggregates.SparseAggregatesToDenseAdapter
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.MentionScreenNameFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicIdSocialContetwittertFeature
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.TSPInferredTopicFeature
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateType
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig.CombineCountPolicies

object EdgeAggregateFeatures {

  object UserAuthorAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = TimelinesAggregationConfig.userAuthorAggregatesV2 ++ Set(
          TimelinesAggregationConfig.userAuthorAggregatesV5,
          TimelinesAggregationConfig.tweetSourceUserAuthorAggregatesV1,
          TimelinesAggregationConfig.twitterWideUserAuthorAggregates
        ),
        aggregateType = AggregateType.UserAuthor,
        etwittertractMapFn = _.userAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = _.features.getOrElse(AuthorIdFeature, None).toSeq
      )

  object UserOriginalAuthorAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userOriginalAuthorAggregatesV1),
        aggregateType = AggregateType.UserOriginalAuthor,
        etwittertractMapFn = _.userOriginalAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = candidate =>
          CandidatesUtil.getOriginalAuthorId(candidate.features).toSeq
      )

  object UserTopicAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userTopicAggregates,
          TimelinesAggregationConfig.userTopicAggregatesV2,
        ),
        aggregateType = AggregateType.UserTopic,
        etwittertractMapFn = _.userTopicAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(TopicIdSocialContetwittertFeature, None).toSeq
      )

  object UserMentionAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userMentionAggregates),
        aggregateType = AggregateType.UserMention,
        etwittertractMapFn = _.userMentionAggregates,
        adapter = new SparseAggregatesToDenseAdapter(CombineCountPolicies.MentionCountsPolicy),
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(MentionScreenNameFeature, Seq.empty).map(_.hashCode.toLong)
      )

  object UserInferredTopicAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userInferredTopicAggregates,
        ),
        aggregateType = AggregateType.UserInferredTopic,
        etwittertractMapFn = _.userInferredTopicAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.UserInferredTopicCountsPolicy),
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(TSPInferredTopicFeature, Map.empty[Long, Double]).keys.toSeq
      )

  object UserInferredTopicAggregateV2Feature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userInferredTopicAggregatesV2
        ),
        aggregateType = AggregateType.UserInferredTopic,
        etwittertractMapFn = _.userInferredTopicAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.UserInferredTopicV2CountsPolicy),
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(TSPInferredTopicFeature, Map.empty[Long, Double]).keys.toSeq
      )

  object UserMediaUnderstandingAnnotationAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userMediaUnderstandingAnnotationAggregates),
        aggregateType = AggregateType.UserMediaUnderstandingAnnotation,
        etwittertractMapFn = _.userMediaUnderstandingAnnotationAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.UserMediaUnderstandingAnnotationCountsPolicy),
        getSecondaryKeysFn = candidate =>
          CandidatesUtil.getMediaUnderstandingAnnotationIds(candidate.features)
      )

  object UserEngagerAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userEngagerAggregates),
        aggregateType = AggregateType.UserEngager,
        etwittertractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(CombineCountPolicies.EngagerCountsPolicy),
        getSecondaryKeysFn = candidate => CandidatesUtil.getEngagerUserIds(candidate.features)
      )

  object UserEngagerGoodClickAggregateFeature
      etwittertends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userEngagerGoodClickAggregates),
        aggregateType = AggregateType.UserEngager,
        etwittertractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.EngagerGoodClickCountsPolicy),
        getSecondaryKeysFn = candidate => CandidatesUtil.getEngagerUserIds(candidate.features)
      )
}
