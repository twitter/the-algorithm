package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.SparseContinuousDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.recommendations.simclusters_v2.InterestedIn20M145K2020OnUserClientColumn
import com.twitter.timelines.prediction.features.simcluster.SimclusterFeatures
import javax.inject.Inject
import javax.inject.Singleton

object SimClustersUserLogFavSparseEmbeddingsDataRecordFeature
    extends DataRecordOptionalFeature[PipelineQuery, Map[String, Double]]
    with SparseContinuousDataRecordCompatible {
  override val featureName: String =
    SimclusterFeatures.SIMCLUSTER_USER_LOG_FAV_CLUSTER_SCORES.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredInterests)
}

object SimClustersUserFollowSparseEmbeddingsDataRecordFeature
    extends DataRecordOptionalFeature[PipelineQuery, Map[String, Double]]
    with SparseContinuousDataRecordCompatible {
  override val featureName: String =
    SimclusterFeatures.SIMCLUSTER_USER_FOLLOW_CLUSTER_SCORES.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredInterests)
}

@Singleton
class SimClustersUserSparseEmbeddingsQueryFeatureHydrator @Inject() (
  interestedIn20M145K2020OnUserClientColumn: InterestedIn20M145K2020OnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SimClustersUserSparseEmbeddingsQuery")

  override val features: Set[Feature[_, _]] =
    Set(
      SimClustersUserLogFavSparseEmbeddingsDataRecordFeature,
      SimClustersUserFollowSparseEmbeddingsDataRecordFeature)

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(SimClustersUserLogFavSparseEmbeddingsDataRecordFeature, None)
    .add(SimClustersUserFollowSparseEmbeddingsDataRecordFeature, None)
    .build()

  override def hydrate(
    query: PipelineQuery,
  ): Stitch[FeatureMap] = {
    val interestedInEmbeddingsOptStitch = interestedIn20M145K2020OnUserClientColumn.fetcher
      .fetch(query.getRequiredUserId)
      .map { result =>
        result.v.map { interestedInEmbeddings =>
          interestedInEmbeddings.clusterIdToScores
        }
      }
    interestedInEmbeddingsOptStitch
      .map { interestedInEmbeddingsOpt =>
        val logFavEmbeddings = interestedInEmbeddingsOpt.map { interestedInEmbeddings =>
          interestedInEmbeddings.map {
            case (key, value) => (key.toString, value.logFavScore.getOrElse(0.0))
          }.toMap
        }
        val followEmbeddings = interestedInEmbeddingsOpt.map { interestedInEmbeddings =>
          interestedInEmbeddings.map {
            case (key, value) => (key.toString, value.followScore.getOrElse(0.0))
          }.toMap
        }
        FeatureMapBuilder()
          .add(SimClustersUserLogFavSparseEmbeddingsDataRecordFeature, logFavEmbeddings)
          .add(SimClustersUserFollowSparseEmbeddingsDataRecordFeature, followEmbeddings)
          .build()
      }.handle { case _ => DefaultFeatureMap }
  }
}
