package com.twitter.product_mixer.core.service.candidate_feature_hydrator_executor

import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.service.ExecutorResult

case class CandidateFeatureHydratorExecutorResult[+Result <: UniversalNoun[Any]](
  results: Seq[CandidateWithFeatures[Result]],
  individualFeatureHydratorResults: Map[
    _ <: ComponentIdentifier,
    BaseIndividualFeatureHydratorResult[Result]
  ]) extends ExecutorResult

sealed trait BaseIndividualFeatureHydratorResult[+Result <: UniversalNoun[Any]]
case class FeatureHydratorDisabled[+Result <: UniversalNoun[Any]]()
    extends BaseIndividualFeatureHydratorResult[Result]
case class IndividualFeatureHydratorResult[+Result <: UniversalNoun[Any]](
  result: Seq[CandidateWithFeatures[Result]])
    extends BaseIndividualFeatureHydratorResult[Result]
