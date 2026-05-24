package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.unified_counter.service.UecAggTotalOnTweetClientColumn
import com.twitter.strato.graphql.unified_counter.thriftscala.UecAggTotalRequest
import com.twitter.strato.graphql.unified_counter.thriftscala.UecAggTotalResponse
import com.twitter.strato.graphql.unified_counter.thriftscala.UecAnalyticsEngagementTypes
import com.twitter.strato.graphql.unified_counter.thriftscala.DataVersion
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines

object UecAggTweetTotalFeature extends Feature[TweetCandidate, Option[UecAggTotalResponse]]

class UecAggTweetTotalFeatureHydrator(
  uecAggTotalOnTweetClientColumn: UecAggTotalOnTweetClientColumn,
  candidatePipelinesToInclude: Option[Set[CandidatePipelineIdentifier]] = None,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UecAggTweetTotal")

  override val features: Set[Feature[_, _]] =
    Set(UecAggTweetTotalFeature)

  private val failureCounter = statsReceiver.counter("UecAggTweetTotalFeatureHydratorFailedCount")
  private val skippedCandidatesCounter =
    statsReceiver.counter("UecAggTweetTotalFeatureHydratorSkippedCandidates")
  private val inCandidateScopeCandidatesCounter =
    statsReceiver.counter("UecAggTweetTotalFeatureHydratorInCandidateScopeCandidates")

  def isInCandidateScope(features: FeatureMap): Boolean = {
    candidatePipelinesToInclude
      .map(candidatePipelines =>
        features.get(CandidatePipelines).exists(candidatePipelines.contains(_))).getOrElse(false)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val filteredCandidates = candidates.filter { candidate =>
      val keepCandidate = isInCandidateScope(candidate.features)

      if (keepCandidate) inCandidateScopeCandidatesCounter.incr()
      else skippedCandidatesCounter.incr()

      keepCandidate
    }

    if (filteredCandidates.isEmpty) {
      Stitch.value(candidates.map(_ => FeatureMap(UecAggTweetTotalFeature, None)))
    } else {
      val request = UecAggTotalRequest(
        engagements = Some(Seq(UecAnalyticsEngagementTypes.Displayed)),
        dataVersion = Some(DataVersion.ColdContentExplore),
        longCache = Some(true)
      )

      val tweetIds = filteredCandidates.map(_.candidate.id).distinct
      Stitch
        .collect {
          tweetIds.map { tweetId =>
            uecAggTotalOnTweetClientColumn.fetcher
              .fetch(tweetId, request)
              .map { response =>
                tweetId -> response.v
              }
              .handle {
                case _: Throwable =>
                  failureCounter.incr()
                  tweetId -> None
              }
          }
        }.map { tweetIdsToResponseList =>
          val tweetIdsToResponseMap = tweetIdsToResponseList.toMap

          candidates.map { candidate =>
            val shouldHydrate = filteredCandidates.exists(_.candidate.id == candidate.candidate.id)

            if (shouldHydrate) {
              val tweetId = candidate.candidate.id
              FeatureMap(
                UecAggTweetTotalFeature,
                tweetIdsToResponseMap.getOrElse(tweetId, None)
              )
            } else {
              FeatureMap(UecAggTweetTotalFeature, None)
            }
          }
        }
    }
  }
}
