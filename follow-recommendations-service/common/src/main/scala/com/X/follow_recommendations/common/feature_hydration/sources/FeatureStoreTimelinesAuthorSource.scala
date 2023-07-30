package com.X.follow_recommendations.common.feature_hydration.sources

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.inject.Inject
import com.X.finagle.TimeoutException
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.X.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.X.follow_recommendations.common.feature_hydration.sources.Utils.adaptAdditionalFeaturesToDataRecord
import com.X.follow_recommendations.common.feature_hydration.sources.Utils.randomizedTTL
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasSimilarToContext
import com.X.ml.api.DataRecord
import com.X.ml.api.FeatureContext
import com.X.ml.api.IRecordOneToOneAdapter
import com.X.ml.featurestore.catalog.datasets.timelines.AuthorFeaturesEntityDataset
import com.X.ml.featurestore.catalog.entities.core.{Author => AuthorEntity}
import com.X.ml.featurestore.catalog.entities.core.{AuthorTopic => AuthorTopicEntity}
import com.X.ml.featurestore.catalog.entities.core.{CandidateUser => CandidateUserEntity}
import com.X.ml.featurestore.catalog.entities.core.{User => UserEntity}
import com.X.ml.featurestore.lib.EdgeEntityId
import com.X.ml.featurestore.lib.EntityId
import com.X.ml.featurestore.lib.TopicId
import com.X.ml.featurestore.lib.UserId
import com.X.ml.featurestore.lib.data.PredictionRecord
import com.X.ml.featurestore.lib.data.PredictionRecordAdapter
import com.X.ml.featurestore.lib.dataset.DatasetId
import com.X.ml.featurestore.lib.dataset.online.Hydrator.HydrationResponse
import com.X.ml.featurestore.lib.dataset.online.OnlineAccessDataset
import com.X.ml.featurestore.lib.dynamic.ClientConfig
import com.X.ml.featurestore.lib.dynamic.DynamicFeatureStoreClient
import com.X.ml.featurestore.lib.dynamic.DynamicHydrationConfig
import com.X.ml.featurestore.lib.dynamic.FeatureStoreParamsConfig
import com.X.ml.featurestore.lib.dynamic.GatedFeatures
import com.X.ml.featurestore.lib.feature.BoundFeature
import com.X.ml.featurestore.lib.feature.BoundFeatureSet
import com.X.ml.featurestore.lib.online.DatasetValuesCache
import com.X.ml.featurestore.lib.online.FeatureStoreRequest
import com.X.ml.featurestore.lib.online.OnlineFeatureGenerationStats
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import java.util.concurrent.TimeUnit
import com.X.conversions.DurationOps._
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.product_mixer.core.model.marshalling.request.HasClientContext

class FeatureStoreTimelinesAuthorSource @Inject() (
  serviceIdentifier: ServiceIdentifier,
  stats: StatsReceiver)
    extends FeatureSource {
  import FeatureStoreTimelinesAuthorSource._

  val backupSourceStats = stats.scope("feature_store_hydration_timelines_author")
  val adapterStats = backupSourceStats.scope("adapters")
  override def id: FeatureSourceId = FeatureSourceId.FeatureStoreTimelinesAuthorSourceId
  override def featureContext: FeatureContext = getFeatureContext

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
    AuthorFeaturesEntityDataset
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

  private val adapter: IRecordOneToOneAdapter[PredictionRecord] =
    PredictionRecordAdapter.oneToOne(
      BoundFeatureSet(allFeatures),
      OnlineFeatureGenerationStats(backupSourceStats)
    )

  override def hydrateFeatures(
    target: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    target.getOptionalUserId
      .map { targetUserId =>
        val featureRequests = candidates.map { candidate =>
          val userEntityId = UserEntity.withId(UserId(targetUserId))
          val candidateEntityId = CandidateUserEntity.withId(UserId(candidate.id))
          val similarToUserId = target.similarToUserIds.map(id => AuthorEntity.withId(UserId(id)))
          val topicProof = candidate.reason.flatMap(_.accountProof.flatMap(_.topicProof))
          val authorTopicEntity = if (topicProof.isDefined) {
            backupSourceStats.counter("candidates_with_topic_proof").incr()
            Set(
              AuthorTopicEntity.withId(
                EdgeEntityId(UserId(candidate.id), TopicId(topicProof.get.topicId))))
          } else Nil

          val entities =
            Seq(userEntityId, candidateEntityId) ++ similarToUserId ++ authorTopicEntity
          FeatureStoreRequest(entities)
        }

        val predictionRecordsFut = dynamicFeatureStoreClient(featureRequests, target)
        val candidateFeatureMap = predictionRecordsFut.map { predictionRecords =>
          // we can zip predictionRecords with candidates as the order is preserved in the client
          candidates
            .zip(predictionRecords).map {
              case (candidate, predictionRecord) =>
                candidate -> adaptAdditionalFeaturesToDataRecord(
                  adapter.adaptToDataRecord(predictionRecord),
                  adapterStats,
                  FeatureStoreSource.featureAdapters)
            }.toMap
        }
        Stitch
          .callFuture(candidateFeatureMap)
          .within(target.params(FeatureStoreSourceParams.GlobalFetchTimeout))(
            com.X.finagle.util.DefaultTimer)
          .rescue {
            case _: TimeoutException =>
              Stitch.value(Map.empty[CandidateUser, DataRecord])
          }
      }.getOrElse(Stitch.value(Map.empty[CandidateUser, DataRecord]))
  }
}

object FeatureStoreTimelinesAuthorSource {
  private val DatasetCacheScope = "feature_store_local_cache_timelines_author"
  private val DefaultCacheMaxKeys = 20000

  import FeatureStoreFeatures._

  val allFeatures: Set[BoundFeature[_ <: EntityId, _]] =
    similarToUserTimelinesAuthorAggregateFeatures ++
      candidateUserTimelinesAuthorAggregateFeatures ++
      authorTopicFeatures

  val getFeatureContext: FeatureContext =
    BoundFeatureSet(allFeatures).toFeatureContext

  val dynamicHydrationConfig: DynamicHydrationConfig[HasParams] =
    DynamicHydrationConfig(
      Set(
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(authorTopicFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreSourceParams.EnableSeparateClientForTimelinesAuthors) &
              HasParams.paramGate(FeatureStoreSourceParams.EnableAuthorTopicAggregateFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(similarToUserTimelinesAuthorAggregateFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreSourceParams.EnableSeparateClientForTimelinesAuthors) &
              HasParams.paramGate(FeatureStoreSourceParams.EnableSimilarToUserFeatures)
        ),
        GatedFeatures(
          boundFeatureSet = BoundFeatureSet(candidateUserTimelinesAuthorAggregateFeatures),
          gate =
            HasParams
              .paramGate(FeatureStoreSourceParams.EnableSeparateClientForTimelinesAuthors) &
              HasParams.paramGate(
                FeatureStoreSourceParams.EnableCandidateUserTimelinesAuthorAggregateFeatures)
        ),
      ))
}
