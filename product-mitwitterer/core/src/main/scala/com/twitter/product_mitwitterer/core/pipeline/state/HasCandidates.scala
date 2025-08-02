package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun

trait HasCandidates[Candidate <: UniversalNoun[Any], T] {
  def candidates: Seq[Candidate]
  def updateCandidates(newCandidates: Seq[Candidate]): T
}
