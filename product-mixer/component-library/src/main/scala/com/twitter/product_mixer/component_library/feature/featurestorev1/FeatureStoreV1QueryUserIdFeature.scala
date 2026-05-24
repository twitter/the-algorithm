package com.twitter.product_mixer.component_library.feature.featurestorev1

import com.twitter.ml.api.transform.FeatureRenameTransform
import com.twitter.ml.featurestore.catalog.entities
import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.ml.featurestore.lib.UserId
import com.twitter.ml.featurestore.lib.entity.Entity
import com.twitter.ml.featurestore.lib.entity.EntityWithId
import com.twitter.ml.featurestore.lib.feature.TimelinesAggregationFrameworkFeatureGroup
import com.twitter.ml.featurestore.lib.feature.{Feature => FSv1Feature}
import com.twitter.product_mixer.core.feature.featurestorev1._
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam
import scala.reflect.ClassTag
object FeatureStoreV1QueryUserIdFeature {
  def apply[Query <: PipelineQuery, Value](
    feature: FSv1Feature[UserId, Value],
    legacyName: Option[String] = None,
    defaultValue: Option[Value] = None,
    enabledParam: Option[FSParam[Boolean]] = None
  ): FeatureStoreV1Feature[Query, Query, _ <: EntityId, Value]
    with FeatureStoreV1QueryFeature[Query, _ <: EntityId, Value] =
    FeatureStoreV1QueryFeature(feature, QueryUserIdEntity, legacyName, defaultValue, enabledParam)
}

object FeatureStoreV1QueryUserIdAggregateFeature {
  def apply[Query <: PipelineQuery](
    featureGroup: TimelinesAggregationFrameworkFeatureGroup[UserId],
    enabledParam: Option[FSParam[Boolean]] = None,
    keepLegacyNames: Boolean = false,
    featureNameTransform: Option[FeatureRenameTransform] = None
  ): FeatureStoreV1QueryFeatureGroup[Query, _ <: EntityId] =
    FeatureStoreV1QueryFeatureGroup(
      featureGroup,
      QueryUserIdEntity,
      enabledParam,
      keepLegacyNames,
      featureNameTransform)((implicitly[ClassTag[UserId]]))
}

object QueryUserIdEntity extends FeatureStoreV1QueryEntity[PipelineQuery, UserId] {
  override val entity: Entity[UserId] = entities.core.User

  override def entityWithId(query: PipelineQuery): EntityWithId[UserId] =
    entity.withId(UserId(query.getUserIdLoggedOutSupport))
}
