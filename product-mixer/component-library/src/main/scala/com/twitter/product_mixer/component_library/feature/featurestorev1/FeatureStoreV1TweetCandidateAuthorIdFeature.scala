package com.ExTwitter.product_mixer.component_library.feature.featurestorev1

import com.ExTwitter.ml.api.transform.FeatureRenameTransform
import com.ExTwitter.ml.featurestore.catalog.entities
import com.ExTwitter.ml.featurestore.lib.EntityId
import com.ExTwitter.ml.featurestore.lib.UserId
import com.ExTwitter.ml.featurestore.lib.entity.Entity
import com.ExTwitter.ml.featurestore.lib.entity.EntityWithId
import com.ExTwitter.ml.featurestore.lib.feature.TimelinesAggregationFrameworkFeatureGroup
import com.ExTwitter.ml.featurestore.lib.feature.{Feature => FSv1Feature}
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetAuthorIdFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featurestorev1._
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.configapi.FSParam
import scala.reflect.ClassTag

object FeatureStoreV1TweetCandidateAuthorIdFeature {
  def apply[Query <: PipelineQuery, Value](
    feature: FSv1Feature[UserId, Value],
    legacyName: Option[String] = None,
    defaultValue: Option[Value] = None,
    enabledParam: Option[FSParam[Boolean]] = None
  ): FeatureStoreV1CandidateFeature[Query, TweetCandidate, _ <: EntityId, Value] =
    FeatureStoreV1CandidateFeature(
      feature,
      TweetCandidateAuthorIdEntity,
      legacyName,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1TweetCandidateAuthorIdAggregateFeature {
  def apply[Query <: PipelineQuery](
    featureGroup: TimelinesAggregationFrameworkFeatureGroup[UserId],
    enabledParam: Option[FSParam[Boolean]] = None,
    keepLegacyNames: Boolean = false,
    featureNameTransform: Option[FeatureRenameTransform] = None
  ): FeatureStoreV1CandidateFeatureGroup[Query, TweetCandidate, _ <: EntityId] =
    FeatureStoreV1CandidateFeatureGroup(
      featureGroup,
      TweetCandidateAuthorIdEntity,
      enabledParam,
      keepLegacyNames,
      featureNameTransform
    )(implicitly[ClassTag[UserId]])
}

object TweetCandidateAuthorIdEntity
    extends FeatureStoreV1CandidateEntity[PipelineQuery, TweetCandidate, UserId] {
  override val entity: Entity[UserId] = entities.core.Author

  override def entityWithId(
    query: PipelineQuery,
    tweet: TweetCandidate,
    existingFeatures: FeatureMap
  ): EntityWithId[UserId] =
    entity.withId(UserId(existingFeatures.get(TweetAuthorIdFeature)))
}
