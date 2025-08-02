package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails

/**
 * Given a [[CandidateWithDetails]] return the corresponding [[Bucket]]
 * it should be associated with when used in a `pattern` or `ratio`
 * in [[InsertAppendPatternResults]] or [[InsertAppendRatioResults]]
 */
trait Bucketer[Bucket] {
  def apply(candidateWithDetails: CandidateWithDetails): Bucket
}

object Bucketer {

  /** A [[Bucketer]] that buckets by [[CandidateWithDetails.source]] */
  val ByCandidateSource: Bucketer[CandidatePipelineIdentifier] =
    (candidateWithDetails: CandidateWithDetails) => candidateWithDetails.source
}
