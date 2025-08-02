package com.twitter.home_mitwitterer.functional_component.filter

import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.FSBoundedParam

case class DropMatwitterCandidatesFilter[Candidate <: UniversalNoun[Any]](
  matwitterCandidatesParam: FSBoundedParam[Int])
    etwittertends Filter[PipelineQuery, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("DropMatwitterCandidates")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val matwitterCandidates = query.params(matwitterCandidatesParam)
    val (kept, removed) = candidates.map(_.candidate).splitAt(matwitterCandidates)

    Stitch.value(FilterResult(kept, removed))
  }
}
