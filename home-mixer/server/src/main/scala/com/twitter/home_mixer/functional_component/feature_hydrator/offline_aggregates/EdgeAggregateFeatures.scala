package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.TSPInferredTopicFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.offline_aggregates.PassThroughAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.offline_aggregates.SparseAggregatesToDenseAdapter
//  this is the easiest way I could find to refresh the goals when switching maps
//  todo this is dumb
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateType
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig.CombineCountPolicies

object EdgeAggregateFeatures {

  object UserAuthorAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = TimelinesAggregationConfig.userAuthorAggregatesV2 ++ Set(
          TimelinesAggregationConfig.userAuthorAggregatesV5,
          TimelinesAggregationConfig.tweetSourceUserAuthorAggregatesV1,
          TimelinesAggregationConfig.twitterWideUserAuthorAggregates
        ),
        aggregateType = AggregateType.UserAuthor,
        extractMapFn = _.userAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = _.features.getOrElse(AuthorIdFeature, None).toSeq
      )

  object UserOriginalAuthorAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userOriginalAuthorReciprocalEngagementAggregates),
        aggregateType = AggregateType.UserOriginalAuthor,
        extractMapFn = _.userOriginalAuthorAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = candidate =>
          CandidatesUtil.getOriginalAuthorId(candidate.features).toSeq
      )

  object UserTopicAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userTopicAggregates,
          TimelinesAggregationConfig.userTopicAggregatesV2,
        ),
        aggregateType = AggregateType.UserTopic,
        extractMapFn = _.userTopicAggregates,
        adapter = PassThroughAdapter,
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(TopicIdSocialContextFeature, None).toSeq
      )

  object UserMentionAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userMentionAggregates),
        aggregateType = AggregateType.UserMention,
        extractMapFn = _.userMentionAggregates,
        adapter = new SparseAggregatesToDenseAdapter(CombineCountPolicies.MentionCountsPolicy),
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(MentionUserIdFeature, Seq.empty)
      )

  object UserInferredTopicAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userInferredTopicAggregates,
          TimelinesAggregationConfig.userInferredTopicAggregatesV2
        ),
        aggregateType = AggregateType.UserInferredTopic,
        extractMapFn = _.userInferredTopicAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.UserInferredTopicV2CountsPolicy),
        getSecondaryKeysFn = candidate =>
          candidate.features.getOrElse(TSPInferredTopicFeature, Map.empty[Long, Double]).keys.toSeq
      )

  object UserMediaUnderstandingAnnotationAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(
          TimelinesAggregationConfig.userMediaUnderstandingAnnotationAggregates),
        aggregateType = AggregateType.UserMediaUnderstandingAnnotation,
        extractMapFn = _.userMediaUnderstandingAnnotationAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.UserMediaUnderstandingAnnotationCountsPolicy),
        getSecondaryKeysFn = candidate =>
          CandidatesUtil.getMediaUnderstandingAnnotationIds(candidate.features)
      )

  object UserEngagerAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userEngagerAggregates),
        aggregateType = AggregateType.UserEngager,
        extractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(CombineCountPolicies.EngagerCountsPolicy),
        getSecondaryKeysFn = candidate => CandidatesUtil.getEngagerUserIds(candidate.features)
      )

  object UserEngagerGoodClickAggregateFeature
      extends BaseEdgeAggregateFeature(
        aggregateGroups = Set(TimelinesAggregationConfig.userEngagerGoodClickAggregates),
        aggregateType = AggregateType.UserEngager,
        extractMapFn = _.userEngagerAggregates,
        adapter = new SparseAggregatesToDenseAdapter(
          CombineCountPolicies.EngagerGoodClickCountsPolicy),
        getSecondaryKeysFn = candidate => CandidatesUtil.getEngagerUserIds(candidate.features)
      )
}
