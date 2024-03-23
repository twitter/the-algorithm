package com.ExTwitter.product_mixer.component_library.filter

import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.snowflake.id.SnowflakeId
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.util.Duration

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
