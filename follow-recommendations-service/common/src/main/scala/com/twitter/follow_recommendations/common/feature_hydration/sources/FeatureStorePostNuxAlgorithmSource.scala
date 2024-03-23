package com.ExTwitter.follow_recommendations.common.feature_hydration.sources

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.inject.Inject
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.TimeoutException
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.constants.CandidateAlgorithmTypeConstants
import com.ExTwitter.follow_recommendations.common.feature_hydration.adapters.CandidateAlgorithmAdapter.remapCandidateSource
import com.ExTwitter.follow_recommendations.common.feature_hydration.adapters.PostNuxAlgorithmIdAdapter
import com.ExTwitter.follow_recommendations.common.feature_hydration.adapters.PostNuxAlgorithmTypeAdapter
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.ExTwitter.follow_recommendations.common.feature_hydration.sources.Utils.adaptAdditionalFeaturesToDataRecord
import com.ExTwitter.follow_recommendations.common.feature_hydration.sources.Utils.randomizedTTL
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasSimilarToContext
import com.ExTwitter.hermit.constants.AlgorithmFeedbackTokens.AlgorithmToFeedbackTokenMap
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.DataRecordMerger
import com.ExTwitter.ml.api.FeatureContext
import com.ExTwitter.ml.api.IRecordOneToOneAdapter
import com.ExTwitter.ml.featurestore.catalog.datasets.customer_journey.PostNuxAlgorithmIdAggregateDataset
import com.ExTwitter.ml.featurestore.catalog.datasets.customer_journey.PostNuxAlgorithmTypeAggregateDataset
import com.ExTwitter.ml.featurestore.catalog.entities.onboarding.{WtfAlgorithm => OnboardingWtfAlgoId}
import com.ExTwitter.ml.featurestore.catalog.entities.onboarding.{
  WtfAlgorithmType => OnboardingWtfAlgoType
}
import com.ExTwitter.ml.featurestore.catalog.features.customer_journey.CombineAllFeaturesPolicy
import com.ExTwitter.ml.featurestore.lib.EntityId
import com.ExTwitter.ml.featurestore.lib.WtfAlgorithmId
import com.ExTwitter.ml.featurestore.lib.WtfAlgorithmType
import com.ExTwitter.ml.featurestore.lib.data.PredictionRecord
import com.ExTwitter.ml.featurestore.lib.data.PredictionRecordAdapter
import com.ExTwitter.ml.featurestore.lib.dataset.DatasetId
import com.ExTwitter.ml.featurestore.lib.dataset.online.Hydrator.HydrationResponse
import com.ExTwitter.ml.featurestore.lib.dataset.online.OnlineAccessDataset
import com.ExTwitter.ml.featurestore.lib.dynamic.ClientConfig
import com.ExTwitter.ml.featurestore.lib.dynamic.DynamicFeatureStoreClient
import com.ExTwitter.ml.featurestore.lib.dynamic.DynamicHydrationConfig
import com.ExTwitter.ml.featurestore.lib.dynamic.FeatureStoreParamsConfig
import com.ExTwitter.ml.featurestore.lib.dynamic.GatedFeatures
import com.ExTwitter.ml.featurestore.lib.entity.EntityWithId
import com.ExTwitter.ml.featurestore.lib.feature.BoundFeature
import com.ExTwitter.ml.featurestore.lib.feature.BoundFeatureSet
import com.ExTwitter.ml.featurestore.lib.online.DatasetValuesCache
import com.ExTwitter.ml.featurestore.lib.online.FeatureStoreRequest
import com.ExTwitter.ml.featurestore.lib.online.OnlineFeatureGenerationStats
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import java.util.concurrent.TimeUnit
import scala.collection.JavaConverters._

class FeatureStorePostNuxAlgorithmSource @Inject() (
  serviceIdentifier: ServiceIdentifier,
  stats: StatsReceiver)
    extends FeatureSource {
  import FeatureStorePostNuxAlgorithmSource._

  val backupSourceStats = stats.scope("feature_store_hydration_post_nux_algorithm")
  val adapterStats = backupSourceStats.scope("adapters")
  override def id: FeatureSourceId = FeatureSourceId.FeatureStorePostNuxAlgorithmSourceId
  override def featureContext: FeatureContext = getFeatureContext

  private val dataRecordMerger = new DataRecordMerger

  val clientConfig: ClientConfig[HasParams] = ClientConfig(
    dynamicHydrationConfig = dynamicHydrationConfig,
    featureStoreParamsConfig =
      FeatureStoreParamsConfig(FeatureStoreParameters.featureStoreParams, Map.empty),
    /**
     * The smaller one between `timeoutProvider` and `FeatureStoreSourceParams.GlobalFetchTimeout`
     * used below takes effect.
     */
    timeoutProvider = Function.const(800.millis),
    serviceIdentifier = serviceIdentifier
  )

  private val datasetsToCache = Set(
    PostNuxAlgorithmIdAggregateDataset,
    PostNuxAlgorithmTypeAggregateDataset,
  ).asInstanceOf[Set[OnlineAccessDataset[_ <: EntityId, _]]]

  private val datasetValuesCache: DatasetValuesCache =
    DatasetValuesCache(
      Caffeine
        .newBuilder()
        .expireAfterWrite(randomizedTTL(12.hours.inSeconds), TimeUnit.SECONDS)
        .maximumSize(DefaultCacheMaxKeys)
        .build[(_ <: EntityId, DatasetId), Stitch[HydrationResponse[_]]]
        .asMap,
      datasetsToCache,
      DatasetCacheScope
    )

  private val dynamicFeatureStoreClient = DynamicFeatureStoreClient(
    clientConfig,
    backupSourceStats,
    Set(datasetValuesCache)
  )

  private val adapterToDataRecord: IRecordOneToOneAdapter[PredictionRecord] =
    PredictionRecordAdapter.oneToOne(
      BoundFeatureSet(allFeatures),
      OnlineFeatureGenerationStats(backupSourceStats)
    )

  // These two calculate the rate for each feature by dividing it by the number of impressions, then
  // apply a log transformation.
  private val transformAdapters = Seq(PostNuxAlgorithmIdAdapter, PostNuxAlgorithmTypeAdapter)
  override def hydrateFeatures(
    target: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    target.getOptionalUserId
      .map { _: Long =>
        val candidateAlgoIdEntities = candidates.map { candidate =>
          candidate.id -> candidate.getAllAlgorithms
            .flatMap { algo =>
              AlgorithmToFeedbackTokenMap.get(remapCandidateSource(algo))
            }.map(algoId => OnboardingWtfAlgoId.withId(WtfAlgorithmId(algoId)))
        }.toMap

        val candidateAlgoTypeEntities = candidateAlgoIdEntities.map {
          case (candidateId, algoIdEntities) =>
            candidateId -> algoIdEntities
              .map(_.id.algoId)
              .flatMap(algoId => CandidateAlgorithmTypeConstants.getAlgorithmTypes(algoId.toString))
              .distinct
              .map(algoType => OnboardingWtfAlgoType.withId(WtfAlgorithmType(algoType)))
        }

        val entities = {
          candidateAlgoIdEntities.values.flatten ++ candidateAlgoTypeEntities.values.flatten
        }.toSeq.distinct
        val requests = entities.map(entity => FeatureStoreRequest(Seq(entity)))

        val predictionRecordsFut = dynamicFeatureStoreClient(requests, target)
        val candidateFeatureMap = predictionRecordsFut.map {
          predictionRecords: Seq[PredictionRecord] =>
            val entityFeatureMap: Map[EntityWithId[_], DataRecord] = entities
              .zip(predictionRecords).map {
                case (entity, predictionRecord) =>
                  entity -> adaptAdditionalFeaturesToDataRecord(
                    adapterToDataRecord.adaptToDataRecord(predictionRecord),
                    adapterStats,
                    transformAdapters)
              }.toMap

            // In case we have more than one algorithm ID, or type, for a candidate, we merge the
            // resulting DataRecords using the two merging policies below.
            val algoIdMergeFn =
              CombineAllFeaturesPolicy(PostNuxAlgorithmIdAdapter.getFeatures).getMergeFn
            val algoTypeMergeFn =
              CombineAllFeaturesPolicy(PostNuxAlgorithmTypeAdapter.getFeatures).getMergeFn

            val candidateAlgoIdFeaturesMap = candidateAlgoIdEntities.mapValues { entities =>
              val features = entities.flatMap(e => Option(entityFeatureMap.getOrElse(e, null)))
              algoIdMergeFn(features)
            }

            val candidateAlgoTypeFeaturesMap = candidateAlgoTypeEntities.mapValues { entities =>
              val features = entities.flatMap(e => Option(entityFeatureMap.getOrElse(e, null)))
              algoTypeMergeFn(features)
            }

            candidates.map { candidate =>
              val idDrOpt = candidateAlgoIdFeaturesMap.getOrElse(candidate.id, None)
              val typeDrOpt = candidateAlgoTypeFeaturesMap.getOrElse(candidate.id, None)

              val featureDr = (idDrOpt, typeDrOpt) match {
                case (None, Some(typeDataRecord)) => typeDataRecord
                case (Some(idDataRecord), None) => idDataRecord
                case (None, None) => new DataRecord()
                case (Some(idDataRecord), Some(typeDataRecord)) =>
                  dataRecordMerger.merge(idDataRecord, typeDataRecord)
                  idDataRecord
              }
              candidate -> featureDr
            }.toMap
        }
        Stitch
          .callFuture(candidateFeatureMap)
          .within(target.params(FeatureStoreSourceParams.GlobalFetchTimeout))(
            com.ExTwitter.finagle.util.DefaultTimer)
          .rescue {
            case _: TimeoutException =>
              Stitch.value(Map.empty[CandidateUser, DataRecord])
          }
      }.getOrElse(Stitch.value(Map.empty[CandidateUser, DataRecord]))
  }
}

object FeatureStorePostNuxAlgorithmSource {
  private val DatasetCacheScope = "feature_store_local_cache_post_nux_algorithm"
  private val DefaultCacheMaxKeys = 1000 // Both of these datasets have <50 keys total.

  val allFeatures: Set[BoundFeature[_ <: EntityId, _]] =
    FeatureStoreFeatures.postNuxAlgorithmIdAggregateFeatures ++
      FeatureStoreFeatures.postNuxAlgorithmTypeAggregateFeatures

  val algoIdFinalFeatures = CombineAllFeaturesPolicy(
    PostNuxAlgorithmIdAdapter.getFeatures).outputFeaturesPostMerge.toSeq
  val algoTypeFinalFeatures = CombineAllFeaturesPolicy(
    PostNuxAlgorithmTypeAdapter.getFeatures).outputFeaturesPostMerge.toSeq

  val getFeatureContext: FeatureContext =
    new FeatureContext().addFeatures((algoIdFinalFeatures ++ algoTypeFinalFeatures).asJava)

  val dynamicHydrationConfig: DynamicHydrationConfig[HasParams] =
    DynamicHydrationConfig(
      Set(
        GatedFeatures(
          boundFeatureSet =
            BoundFeatureSet(FeatureStoreFeatures.postNuxAlgorithmIdAggregateFeatures),
          gate = HasParams.paramGate(FeatureStoreSourceParams.EnableAlgorithmAggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet =
            BoundFeatureSet(FeatureStoreFeatures.postNuxAlgorithmTypeAggregateFeatures),
          gate = HasParams.paramGate(FeatureStoreSourceParams.EnableAlgorithmAggregateFeatures)
        ),
      ))
}
