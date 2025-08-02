package com.twitter.product_mitwitterer.core.service.selector_etwitterecutor

import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult

case class SelectorEtwitterecutorResult(
  selectedCandidates: Seq[CandidateWithDetails],
  remainingCandidates: Seq[CandidateWithDetails],
  droppedCandidates: Seq[CandidateWithDetails],
  individualSelectorResults: Seq[SelectorResult])
    etwittertends EtwitterecutorResult
