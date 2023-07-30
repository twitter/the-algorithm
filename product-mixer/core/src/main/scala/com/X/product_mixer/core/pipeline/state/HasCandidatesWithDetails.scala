package com.X.product_mixer.core.pipeline.state

import com.X.product_mixer.core.functional_component.decorator.Decoration
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails

trait HasCandidatesWithDetails[T] {
  def candidatesWithDetails: Seq[CandidateWithDetails]
  def updateCandidatesWithDetails(newCandidates: Seq[CandidateWithDetails]): T

  def updateDecorations(decoration: Seq[Decoration]): T
}
