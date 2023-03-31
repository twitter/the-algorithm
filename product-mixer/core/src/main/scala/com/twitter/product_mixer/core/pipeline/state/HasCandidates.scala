package com.twitter.product_mixer.core.pipeline.state

import com.twitter.product_mixer.core.model.common.UniversalNoun

trait HasCandidates[Candidate <: UniversalNoun[Any], T] {
  def candidates: Seq[Candidate]
  def updateCandidates(newCandidates: Seq[Candidate]): T
}
