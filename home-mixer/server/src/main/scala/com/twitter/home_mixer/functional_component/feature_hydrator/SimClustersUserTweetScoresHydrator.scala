package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.generated.client.ml.featureStore.SimClustersUserInterestedInTweetEmbeddingDotProduct20M145K2020OnUserTweetEdgeClientColumn
import com.twitter.util.Future
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

  override val features: Set[Feature[_, _]] =
    Set(SimClustersUserInterestedInTweetEmbeddingDataRecordFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")
  private val keySkipCounter = scopedStatsReceiver.counter("key/skip")

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(SimClustersUserInterestedInTweetEmbeddingDataRecordFeature, None)
    .build()
  private val MinFavToHydrate = 9
  private val batchSize = 64

  def getFeatureMaps(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    userId: Long
  ): Future[Seq[FeatureMap]] = {
    val featureMapStitch = Stitch.traverse(candidates) { candidate =>
      val ebFeatures = candidate.features.getOrElse(EarlybirdFeature, None)
      val favCount = ebFeatures.flatMap(_.favCountV2).getOrElse(0)

      if (ebFeatures.isEmpty || favCount >= MinFavToHydrate) {
        simClustersColumn.fetcher
          .fetch((userId, candidate.candidate.id), Unit)
          .map {
            case Fetch.Result(response, _) =>
              if (response.nonEmpty) keyFoundCounter.incr()
              else keyNotFoundCounter.incr()
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
    Stitch.run(featureMapStitch)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getFeatureMaps(_, query.getRequiredUserId),
      batchSize,
      offload = true)
  }
}
