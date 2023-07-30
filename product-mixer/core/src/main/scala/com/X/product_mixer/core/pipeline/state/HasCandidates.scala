package com.X.product_mixer.core.pipeline.state

import com.X.product_mixer.core.model.common.UniversalNoun

trait HasCandidates[Candidate <: UniversalNoun[Any], T] {
  def candidates: Seq[Candidate]
  def updateCandidates(newCandidates: Seq[Candidate]): T
}
