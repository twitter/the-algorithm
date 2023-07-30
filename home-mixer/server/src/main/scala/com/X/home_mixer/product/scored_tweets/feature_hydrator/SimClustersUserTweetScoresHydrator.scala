package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.dal.personal_data.{thriftjava => pd}
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.X.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.stitch.Stitch
import com.X.strato.catalog.Fetch
import com.X.strato.generated.client.ml.featureStore.SimClustersUserInterestedInTweetEmbeddingDotProduct20M145K2020OnUserTweetEdgeClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object SimClustersUserInterestedInTweetEmbeddingDataRecordFeature
    extends DataRecordOptionalFeature[TweetCandidate, Double]
    with DoubleDataRecordCompatible {
  override val featureName: String =
    "user-tweet.recommendations.sim_clusters_scores.user_interested_in_tweet_embedding_dot_product_20m_145k_2020"
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredInterests)
}

@Singleton
class SimClustersUserTweetScoresHydrator @Inject() (
  simClustersColumn: SimClustersUserInterestedInTweetEmbeddingDotProduct20M145K2020OnUserTweetEdgeClientColumn,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SimClustersUserTweetScores")

  override val features: Set[Feature[_, _]] = Set(
    SimClustersUserInterestedInTweetEmbeddingDataRecordFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyLossCounter = scopedStatsReceiver.counter("key/loss")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")
  private val keySkipCounter = scopedStatsReceiver.counter("key/skip")

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(SimClustersUserInterestedInTweetEmbeddingDataRecordFeature, None)
    .build()
  private val MinFavToHydrate = 9

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    Stitch.run {
      Stitch.collect {
        candidates.map { candidate =>
          val ebFeatures = candidate.features.getOrElse(EarlybirdFeature, None)
          val favCount = ebFeatures.flatMap(_.favCountV2).getOrElse(0)
          
          if (ebFeatures.isEmpty || favCount >= MinFavToHydrate) {
            simClustersColumn.fetcher
              .fetch((query.getRequiredUserId, candidate.candidate.id), Unit)
              .map {
                case Fetch.Result(response, _) =>
                  if (response.nonEmpty) keyFoundCounter.incr() else keyLossCounter.incr()
                  FeatureMapBuilder()
                    .add(SimClustersUserInterestedInTweetEmbeddingDataRecordFeature, response)
                    .build()
                case _ =>
                  keyFailureCounter.incr()
                  DefaultFeatureMap
              }
          } else {
            keySkipCounter.incr()
            Stitch.value(DefaultFeatureMap)
          }
        }
      }
    }
  }
}
