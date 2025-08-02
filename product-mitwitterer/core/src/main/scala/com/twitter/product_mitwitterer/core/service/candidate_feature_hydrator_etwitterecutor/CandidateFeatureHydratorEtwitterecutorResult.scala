package com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult

case class CandidateFeatureHydratorEtwitterecutorResult[+Result <: UniversalNoun[Any]](
  results: Seq[CandidateWithFeatures[Result]],
  individualFeatureHydratorResults: Map[
    _ <: ComponentIdentifier,
    BaseIndividualFeatureHydratorResult[Result]
  ]) etwittertends EtwitterecutorResult

sealed trait BaseIndividualFeatureHydratorResult[+Result <: UniversalNoun[Any]]
case class FeatureHydratorDisabled[+Result <: UniversalNoun[Any]]()
    etwittertends BaseIndividualFeatureHydratorResult[Result]
case class IndividualFeatureHydratorResult[+Result <: UniversalNoun[Any]](
  result: Seq[CandidateWithFeatures[Result]])
    etwittertends BaseIndividualFeatureHydratorResult[Result]
