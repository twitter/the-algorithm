package com.twitter.product_mixer.component_library.filter

import com.twitter.product_mixer.component_library.filter.ParamGatedFilter.IdentifierPrefix
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

/**
 * A [[Filter]] with [[Conditionally]] based on a [[Param]]
 *
 * @param enabledParam the param to turn this filter on and off
 * @param filter the underlying filter to run when `enabledParam` is true
 * @tparam Query The domain model for the query or request
 * @tparam Candidate The type of the candidates
 */
case class ParamGatedFilter[-Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
  enabledParam: Param[Boolean],
  filter: Filter[Query, Candidate])
    extends Filter[Query, Candidate]
    with Filter.Conditionally[Query, Candidate] {
  override val identifier: FilterIdentifier = FilterIdentifier(
    IdentifierPrefix + filter.identifier.name)
  override val alerts: Seq[Alert] = filter.alerts
  override def onlyIf(query: Query, candidates: Seq[CandidateWithFeatures[Candidate]]): Boolean =
    Conditionally.and(Filter.Input(query, candidates), filter, query.params(enabledParam))
  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = filter.apply(query, candidates)
}

object ParamGatedFilter {
  val IdentifierPrefix = "ParamGated"
}
