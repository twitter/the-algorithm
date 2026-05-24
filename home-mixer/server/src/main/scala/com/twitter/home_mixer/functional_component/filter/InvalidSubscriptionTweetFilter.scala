package com.twitter.home_mixer.functional_component.filter

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.tracing.Trace
import com.twitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.immutable.ListSet

/**
 * Exclude invalid subscription tweets - cases where the viewer is not subscribed to the author
 *
 * If SGS hydration fails, `SGSInvalidSubscriptionTweetFeature` will be set to None for
 * subscription tweets, so we explicitly filter those tweets out.
 */
@Singleton
case class InvalidSubscriptionTweetFilter @Inject() (
  socialGraphClient: SocialGraph,
  statsReceiver: StatsReceiver)
    extends Filter[PipelineQuery, TweetCandidate]
    with Logging {

  override val identifier: FilterIdentifier = FilterIdentifier("InvalidSubscriptionTweet")

  private val scopedStatsReceiver = statsReceiver.scope(identifier.toString)
  private val servedTypeStatsReceiver = scopedStatsReceiver.scope("ServedType")
  private val validCounter = scopedStatsReceiver.counter("validExclusiveTweet")
  private val invalidCounter = scopedStatsReceiver.counter("invalidExclusiveTweet")

  private val forYouScoredTweetsCandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouScoredTweets")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = Stitch
    .traverse(candidates) { candidate =>
      val exclusiveAuthorId =
        candidate.features.getOrElse(ExclusiveConversationAuthorIdFeature, None)

      if (exclusiveAuthorId.isDefined) {
        val request = sg.ExistsRequest(
          source = query.getRequiredUserId,
          target = exclusiveAuthorId.get,
          relationships =
            Seq(sg.Relationship(sg.RelationshipType.TierOneSuperFollowing, hasRelationship = true)),
        )
        socialGraphClient.exists(request).map(_.exists).map { valid =>
          if (!valid) {
            invalidCounter.incr()
            val candidatePipelines = candidate.features
              .getOrElse(CandidatePipelines, ListSet.empty[CandidatePipelineIdentifier])
            // Temporary debugging code
            if (candidatePipelines.contains(forYouScoredTweetsCandidatePipelineIdentifier)) {
              val servedType =
                candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
              servedTypeStatsReceiver.counter(servedType.name).incr()
              logger.info(
                s"Removing subscription Tweet ${candidate.candidate.id} " +
                  s"for User ${query.getRequiredUserId} from Author $exclusiveAuthorId " +
                  s"with traceId: ${Trace.id.traceId.toLong}"
              )
            }
          } else validCounter.incr()
          valid
        }
      } else Stitch.value(true)
    }.map { validResults =>
      val (kept, removed) = candidates
        .map(_.candidate)
        .zip(validResults)
        .partition { case (candidate, valid) => valid }

      val keptCandidates = kept.map { case (candidate, _) => candidate }
      val removedCandidates = removed.map { case (candidate, _) => candidate }

      FilterResult(kept = keptCandidates, removed = removedCandidates)
    }
}
