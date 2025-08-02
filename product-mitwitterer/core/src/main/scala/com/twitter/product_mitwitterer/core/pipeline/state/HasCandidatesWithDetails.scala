package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.functional_component.decorator.Decoration
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails

trait HasCandidatesWithDetails[T] {
  def candidatesWithDetails: Seq[CandidateWithDetails]
  def updateCandidatesWithDetails(newCandidates: Seq[CandidateWithDetails]): T

  def updateDecorations(decoration: Seq[Decoration]): T
}
