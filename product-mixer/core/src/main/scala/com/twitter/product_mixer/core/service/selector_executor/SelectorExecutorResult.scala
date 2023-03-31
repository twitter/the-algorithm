package com.twitter.product_mixer.core.service.selector_executor

import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.service.ExecutorResult

case class SelectorExecutorResult(
  selectedCandidates: Seq[CandidateWithDetails],
  remainingCandidates: Seq[CandidateWithDetails],
  droppedCandidates: Seq[CandidateWithDetails],
  individualSelectorResults: Seq[SelectorResult])
    extends ExecutorResult
