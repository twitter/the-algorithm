package com.X.product_mixer.component_library.feature.featurestorev1

import com.X.ml.api.transform.FeatureRenameTransform
import com.X.ml.featurestore.catalog.entities
import com.X.ml.featurestore.lib.EntityId
import com.X.ml.featurestore.lib.TweetId
import com.X.ml.featurestore.lib.entity.Entity
import com.X.ml.featurestore.lib.entity.EntityWithId
import com.X.ml.featurestore.lib.feature.TimelinesAggregationFrameworkFeatureGroup
import com.X.ml.featurestore.lib.feature.{Feature => FSv1Feature}
import com.X.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featurestorev1._
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.FSParam

object FeatureStoreV1TweetCandidateTweetIdFeature {
  def apply[Query <: PipelineQuery, Candidate <: BaseTweetCandidate, Value](
    feature: FSv1Feature[TweetId, Value],
    legacyName: Option[String] = None,
    defaultValue: Option[Value] = None,
    enabledParam: Option[FSParam[Boolean]] = None
  ): FeatureStoreV1CandidateFeature[Query, Candidate, _ <: EntityId, Value] =
    FeatureStoreV1CandidateFeature(
      feature,
      TweetCandidateTweetIdEntity,
      legacyName,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1TweetCandidateTweetIdAggregateFeature {
  def apply[Query <: PipelineQuery, Candidate <: BaseTweetCandidate](
    featureGroup: TimelinesAggregationFrameworkFeatureGroup[TweetId],
    enabledParam: Option[FSParam[Boolean]] = None,
    keepLegacyNames: Boolean = false,
    featureNameTransform: Option[FeatureRenameTransform] = None
  ): FeatureStoreV1CandidateFeatureGroup[Query, Candidate, _ <: EntityId] =
    FeatureStoreV1CandidateFeatureGroup(
      featureGroup,
      TweetCandidateTweetIdEntity,
      enabledParam,
      keepLegacyNames,
      featureNameTransform
    )
}

object TweetCandidateTweetIdEntity
    extends FeatureStoreV1CandidateEntity[PipelineQuery, BaseTweetCandidate, TweetId] {
  override val entity: Entity[TweetId] = entities.core.Tweet

  override def entityWithId(
    query: PipelineQuery,
    tweet: BaseTweetCandidate,
    existingFeatures: FeatureMap
  ): EntityWithId[TweetId] =
    entity.withId(TweetId(tweet.id))
}
