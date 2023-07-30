package com.X.product_mixer.core.functional_component.selector

import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails

/**
 * The result of a [[Selector]] where items that were added
 * to the [[result]] are removed from the [[remainingCandidates]]
 */
case class SelectorResult(
  remainingCandidates: Seq[CandidateWithDetails],
  result: Seq[CandidateWithDetails])
