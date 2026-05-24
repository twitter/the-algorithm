package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

/**
 * Filters Snowflake ID-compatible content that are older than some configurable threshold.
 *
 * We derive the content age from the Snowflake ID (http://go/snowflake), and non-Snowflake IDs
 * are assumed to be too old.
 *
 * @param maxAgeParam Feature Switch configurable for convenience
 * @tparam Candidate The type of the candidates
 */
case class CustomSnowflakeIdAgeFilter[Candidate <: UniversalNoun[Long]](
  maxAgeParam: Param[Duration])
    extends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SnowflakeIdAge")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val maxAge = query.params(maxAgeParam)

    val (keptCandidates, removedCandidates) = candidates
      .map(c => (c.candidate, c.features.get(CandidatePipelines)))
      .partition { filterCandidate =>
        SnowflakeId.timeFromIdOpt(filterCandidate._1.id) match {
          case Some(creationTime) =>
            query.queryTime.since(creationTime) <= maxAge
          case _ => false // Always deny if non-Snowflake
        }
      }

    Stitch.value(
      FilterResult(kept = keptCandidates.map(_._1), removed = removedCandidates.map(_._1)))
  }
}
