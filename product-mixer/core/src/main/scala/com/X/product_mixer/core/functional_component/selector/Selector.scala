package com.X.product_mixer.core.functional_component.selector

import com.X.product_mixer.core.functional_component.common.CandidateScope
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.pipeline.PipelineQuery

/** Selects some `remainingCandidates` and add them to the `result` */
trait Selector[-Query <: PipelineQuery] {

  /**
   * Specifies which [[com.X.product_mixer.core.model.common.presentation.CandidateWithDetails.source]]s
   * this [[Selector]] will apply to.
   *
   * @note it is up to each [[Selector]] implementation to correctly handle this behavior
   */
  def pipelineScope: CandidateScope

  /** Selects some `remainingCandidates` and add them to the `result` */
  def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult
}
