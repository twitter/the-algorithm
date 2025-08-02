package com.twitter.product_mitwitterer.core.functional_component.candidate_source

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Retrieve Candidates from the Query
 */
trait CandidateEtwittertractor[-Request, +Candidate] {

  def apply(query: Request): Seq[Candidate]
}

/**
 * Identity etwittertractor for returning the Request as a Seq of candidates
 */
case class IdentityCandidateEtwittertractor[Request]() etwittertends CandidateEtwittertractor[Request, Request] {

  def apply(candidate: Request): Seq[Request] = Seq(candidate)
}

/**
 * Retrieve Candidates from a [[Feature]] on the [[PipelineQuery]]'s FeatureMap. This etwittertractor
 * supports a transform if the Feature value and the Seq of [[Candidate]] types do not match
 */
trait QueryFeatureCandidateEtwittertractor[-Query <: PipelineQuery, FeatureValue, +Candidate]
    etwittertends CandidateEtwittertractor[Query, Candidate] {

  def feature: Feature[Query, FeatureValue]

  override def apply(query: Query): Seq[Candidate] =
    query.features.map(featureMap => transform(featureMap.get(feature))).getOrElse(Seq.empty)

  def transform(featureValue: FeatureValue): Seq[Candidate]
}

/**
 * Retrieve Candidates from a [[Feature]] on the [[PipelineQuery]]'s FeatureMap. This etwittertractor can
 * be used with a single [[Feature]] if the Feature value and the Seq of [[Candidate]] types match.
 */
case class CandidateQueryFeatureCandidateEtwittertractor[-Query <: PipelineQuery, Candidate](
  override val feature: Feature[Query, Seq[Candidate]])
    etwittertends QueryFeatureCandidateEtwittertractor[Query, Seq[Candidate], Candidate] {

  override def transform(featureValue: Seq[Candidate]): Seq[Candidate] = featureValue
}

/**
 * A [[CandidateSource]] that retrieves candidates from the Request via a [[CandidateEtwittertractor]]
 */
case class PassthroughCandidateSource[-Request, +Candidate](
  override val identifier: CandidateSourceIdentifier,
  candidateEtwittertractor: CandidateEtwittertractor[Request, Candidate])
    etwittertends CandidateSource[Request, Candidate] {

  def apply(query: Request): Stitch[Seq[Candidate]] = Stitch.value(candidateEtwittertractor(query))
}
