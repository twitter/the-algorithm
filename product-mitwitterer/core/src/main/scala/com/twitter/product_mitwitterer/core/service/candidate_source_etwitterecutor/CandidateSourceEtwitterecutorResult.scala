package com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult

case class CandidateSourceEtwitterecutorResult[Candidate <: UniversalNoun[Any]](
  candidates: Seq[FetchedCandidateWithFeatures[Candidate]],
  candidateSourceFeatureMap: FeatureMap)
    etwittertends EtwitterecutorResult
