package com.ExTwitter.home_mixer.functional_component.filter

import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.FSBoundedParam

case class DropMaxCandidatesFilter[Candidate <: UniversalNoun[Any]](
  maxCandidatesParam: FSBoundedParam[Int])
    extends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("DropMaxCandidates")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val maxCandidates = query.params(maxCandidatesParam)
    val (kept, removed) = candidates.map(_.candidate).splitAt(maxCandidates)

    Stitch.value(FilterResult(kept, removed))
  }
}
