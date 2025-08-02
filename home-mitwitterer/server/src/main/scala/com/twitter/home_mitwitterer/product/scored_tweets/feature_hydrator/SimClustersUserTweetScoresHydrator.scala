package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.EarlybirdFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mitwitterer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.generated.client.ml.featureStore.SimClustersUserInterestedInTweetEmbeddingDotProduct20M145K2020OnUserTweetEdgeClientColumn
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object SimClustersUserInterestedInTweetEmbeddingDataRecordFeature
    etwittertends DataRecordOptionalFeature[TweetCandidate, Double]
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
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

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
