package com.X.product_mixer.core.functional_component.candidate_source

import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch

/**
 * A [[CandidateSource]] that always returns [[result]] regardless of the input
 */
case class StaticCandidateSource[Candidate](
  override val identifier: CandidateSourceIdentifier,
  result: Seq[Candidate])
    extends CandidateSource[Any, Candidate] {

  def apply(request: Any): Stitch[Seq[Candidate]] = Stitch.value(result)
}
