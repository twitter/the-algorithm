package com.X.product_mixer.component_library.feature.featurestorev1

import com.X.ml.api.transform.FeatureRenameTransform
import com.X.ml.featurestore.catalog.entities
import com.X.ml.featurestore.lib.EdgeEntityId
import com.X.ml.featurestore.lib.EntityId
import com.X.ml.featurestore.lib.UserId
import com.X.ml.featurestore.lib.entity.Entity
import com.X.ml.featurestore.lib.entity.EntityWithId
import com.X.ml.featurestore.lib.feature.TimelinesAggregationFrameworkFeatureGroup
import com.X.ml.featurestore.lib.feature.{Feature => FSv1Feature}
import com.X.product_mixer.component_library.model.candidate.TweetAuthorIdFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featurestorev1._
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.FSParam
import scala.reflect.ClassTag

object FeatureStoreV1QueryUserIdTweetCandidateAuthorIdFeature {
  def apply[Query <: PipelineQuery, Value](
    feature: FSv1Feature[EdgeEntityId[UserId, UserId], Value],
    legacyName: Option[String] = None,
    defaultValue: Option[Value] = None,
    enabledParam: Option[FSParam[Boolean]] = None
  ): FeatureStoreV1CandidateFeature[Query, TweetCandidate, _ <: EntityId, Value] =
    FeatureStoreV1CandidateFeature(
      feature,
      QueryUserIdTweetCandidateAuthorIdEntity,
      legacyName,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1QueryUserIdTweetCandidateAuthorIdAggregateFeature {
  def apply[Query <: PipelineQuery](
    featureGroup: TimelinesAggregationFrameworkFeatureGroup[EdgeEntityId[UserId, UserId]],
    enabledParam: Option[FSParam[Boolean]] = None,
    keepLegacyNames: Boolean = false,
    featureNameTransform: Option[FeatureRenameTransform] = None
  ): FeatureStoreV1CandidateFeatureGroup[Query, TweetCandidate, _ <: EntityId] =
    FeatureStoreV1CandidateFeatureGroup(
      featureGroup,
      QueryUserIdTweetCandidateAuthorIdEntity,
      enabledParam,
      keepLegacyNames,
      featureNameTransform
    )(implicitly[ClassTag[EdgeEntityId[UserId, UserId]]])
}

object QueryUserIdTweetCandidateAuthorIdEntity
    extends FeatureStoreV1CandidateEntity[
      PipelineQuery,
      TweetCandidate,
      EdgeEntityId[UserId, UserId]
    ] {
  override val entity: Entity[EdgeEntityId[UserId, UserId]] = entities.core.UserAuthor

  override def entityWithId(
    query: PipelineQuery,
    tweet: TweetCandidate,
    existingFeatures: FeatureMap
  ): EntityWithId[EdgeEntityId[UserId, UserId]] =
    entity.withId(
      EdgeEntityId(
        UserId(query.getUserIdLoggedOutSupport),
        UserId(existingFeatures.get(TweetAuthorIdFeature))))
}
