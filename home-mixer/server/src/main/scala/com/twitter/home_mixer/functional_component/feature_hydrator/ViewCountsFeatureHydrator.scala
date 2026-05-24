package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.ViewCountFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.generated.client.viewcounts.ViewCountOnTweetClientColumn
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewCountsFeatureHydrator @Inject() (
  viewCountsColumn: ViewCountOnTweetClientColumn,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ViewCounts")

  override val features: Set[Feature[_, _]] =
    Set(ViewCountFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")

  private val DefaultFeatureMap = FeatureMap(ViewCountFeature, None)
  private val batchSize = 64

  def getFeatureMaps(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Future[Seq[FeatureMap]] = {
    val featureMapStitch = Stitch.traverse(candidates) { candidate =>
      viewCountsColumn.fetcher
        .fetch(candidate.candidate.id, Unit)
        .map {
          case Fetch.Result(response, _) =>
            if (response.nonEmpty) keyFoundCounter.incr()
            else keyNotFoundCounter.incr()
            FeatureMap(ViewCountFeature, response)
          case _ =>
            keyFailureCounter.incr()
            DefaultFeatureMap
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
      getFeatureMaps,
      batchSize,
      offload = true)
  }
}
