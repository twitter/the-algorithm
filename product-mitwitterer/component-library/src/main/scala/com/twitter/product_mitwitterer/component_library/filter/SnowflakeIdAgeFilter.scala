package com.twitter.product_mitwitterer.component_library.filter

import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

/**
 * @param matwitterAgeParam Feature Switch configurable for convenience
 * @tparam Candidate The type of the candidates
 */
case class SnowflakeIdAgeFilter[Candidate <: UniversalNoun[Long]](
  matwitterAgeParam: Param[Duration])
    etwittertends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SnowflakeIdAge")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val matwitterAge = query.params(matwitterAgeParam)

    val (keptCandidates, removedCandidates) = candidates
      .map(_.candidate)
      .partition { filterCandidate =>
        SnowflakeId.timeFromIdOpt(filterCandidate.id) match {
          case Some(creationTime) =>
            query.queryTime.since(creationTime) <= matwitterAge
          case _ => false
        }
      }

    Stitch.value(FilterResult(kept = keptCandidates, removed = removedCandidates))
  }
}
