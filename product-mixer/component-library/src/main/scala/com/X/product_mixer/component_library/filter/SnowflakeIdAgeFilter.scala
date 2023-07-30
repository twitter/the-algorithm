package com.X.product_mixer.component_library.filter

import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.snowflake.id.SnowflakeId
import com.X.stitch.Stitch
import com.X.timelines.configapi.Param
import com.X.util.Duration

/**
 * @param maxAgeParam Feature Switch configurable for convenience
 * @tparam Candidate The type of the candidates
 */
case class SnowflakeIdAgeFilter[Candidate <: UniversalNoun[Long]](
  maxAgeParam: Param[Duration])
    extends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SnowflakeIdAge")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val maxAge = query.params(maxAgeParam)

    val (keptCandidates, removedCandidates) = candidates
      .map(_.candidate)
      .partition { filterCandidate =>
        SnowflakeId.timeFromIdOpt(filterCandidate.id) match {
          case Some(creationTime) =>
            query.queryTime.since(creationTime) <= maxAge
          case _ => false
        }
      }

    Stitch.value(FilterResult(kept = keptCandidates, removed = removedCandidates))
  }
}
