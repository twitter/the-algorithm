package com.twitter.product_mitwitterer.core.functional_component.candidate_source

import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch

/**
 * A [[CandidateSource]] that always returns [[result]] regardless of the input
 */
case class StaticCandidateSource[Candidate](
  override val identifier: CandidateSourceIdentifier,
  result: Seq[Candidate])
    etwittertends CandidateSource[Any, Candidate] {

  def apply(request: Any): Stitch[Seq[Candidate]] = Stitch.value(result)
}
