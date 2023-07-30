package com.X.product_mixer.core.pipeline.state

import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun

trait HasCandidatesWithFeatures[Candidate <: UniversalNoun[Any], T] {
  def candidatesWithFeatures: Seq[CandidateWithFeatures[Candidate]]
  def updateCandidatesWithFeatures(newCandidates: Seq[CandidateWithFeatures[Candidate]]): T
}
