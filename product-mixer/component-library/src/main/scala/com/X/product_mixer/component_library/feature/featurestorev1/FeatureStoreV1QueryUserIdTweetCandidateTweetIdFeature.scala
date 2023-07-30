package com.X.product_mixer.component_library.feature.featurestorev1

import com.X.ml.api.transform.FeatureRenameTransform
import com.X.ml.featurestore.catalog.entities
import com.X.ml.featurestore.lib.EdgeEntityId
import com.X.ml.featurestore.lib.EntityId
import com.X.ml.featurestore.lib.TweetId
import com.X.ml.featurestore.lib.UserId
import com.X.ml.featurestore.lib.entity.Entity
import com.X.ml.featurestore.lib.entity.EntityWithId
import com.X.ml.featurestore.lib.feature.TimelinesAggregationFrameworkFeatureGroup
import com.X.ml.featurestore.lib.feature.{Feature => FSv1Feature}
import com.X.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featurestorev1._
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.FSParam
import scala.reflect.ClassTag

object FeatureStoreV1QueryUserIdTweetCandidateTweetIdFeature {
  def apply[Query <: PipelineQuery, Candidate <: BaseTweetCandidate, Value](
    feature: FSv1Feature[EdgeEntityId[UserId, TweetId], Value],
    legacyName: Option[String] = None,
    defaultValue: Option[Value] = None,
    enabledParam: Option[FSParam[Boolean]] = None
  ): FeatureStoreV1CandidateFeature[Query, Candidate, _ <: EntityId, Value] =
    FeatureStoreV1CandidateFeature(
      feature,
      QueryUserIdTweetCandidateTweetIdEntity,
      legacyName,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1QueryUserIdTweetCandidateTweetIdAggregateFeature {
  def apply[Query <: PipelineQuery, Candidate <: BaseTweetCandidate](
    featureGroup: TimelinesAggregationFrameworkFeatureGroup[EdgeEntityId[UserId, TweetId]],
    enabledParam: Option[FSParam[Boolean]] = None,
    keepLegacyNames: Boolean = false,
    featureNameTransform: Option[FeatureRenameTransform] = None
  ): FeatureStoreV1CandidateFeatureGroup[Query, TweetCandidate, _ <: EntityId] =
    FeatureStoreV1CandidateFeatureGroup(
      featureGroup,
      QueryUserIdTweetCandidateTweetIdEntity,
      enabledParam,
      keepLegacyNames,
      featureNameTransform
    )(implicitly[ClassTag[EdgeEntityId[UserId, TweetId]]])
}

object QueryUserIdTweetCandidateTweetIdEntity
    extends FeatureStoreV1CandidateEntity[
      PipelineQuery,
      BaseTweetCandidate,
      EdgeEntityId[UserId, TweetId]
    ] {
  override val entity: Entity[EdgeEntityId[UserId, TweetId]] = entities.core.UserTweet

  override def entityWithId(
    query: PipelineQuery,
    tweet: BaseTweetCandidate,
    existingFeatures: FeatureMap
  ): EntityWithId[EdgeEntityId[UserId, TweetId]] =
    entity.withId(EdgeEntityId(UserId(query.getUserIdLoggedOutSupport), TweetId(tweet.id)))
}
