package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

/**
 * Filters Snowflake ID-compatible content that are older than some configurable threshold,
 * but only for those candidates that match the includeRule.
 *
 * Candidates not matching the includeRule will always be kept.
 *
 * @param maxAgeParam Feature Switch configurable for convenience
 * @tparam Candidate The type of the candidates
 */
case class CustomSnowflakeIdAgeFilterWithIncludeRule[Candidate <: UniversalNoun[Long]](
  maxAgeParam: Param[Duration],
  includeRule: Option[CandidateWithFeatures[Candidate] => Boolean] = None)
    extends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SnowflakeIdAgeWithIncludeRule")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val maxAge = query.params(maxAgeParam)

    val (keptCandidates, removedCandidates) = candidates.partition { filterCandidate =>
      includeRule match {
        case Some(rule) if rule(filterCandidate) =>
          SnowflakeId.timeFromIdOpt(filterCandidate.candidate.id) match {
            case Some(creationTime) =>
              query.queryTime.since(creationTime) <= maxAge
            case _ => false
          }
        case _ =>
          true
      }
    }

    Stitch.value(
      FilterResult(
        kept = keptCandidates.map(_.candidate),
        removed = removedCandidates.map(_.candidate)
      )
    )
  }
}
