package com.twitter.product_mixer.component_library.pipeline.candidate.ads

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails

/**
 * Get organic item candidates from the set of previous candidates
 */
trait GetOrganicItemIds {

  def apply(previousCandidates: Seq[CandidateWithDetails]): Option[Seq[Long]]
}

/**
 * Get organic items from specified pipelines
 */
case class PipelineScopedOrganicItemIds(pipelines: CandidateScope) extends GetOrganicItemIds {

  def apply(previousCandidates: Seq[CandidateWithDetails]): Option[Seq[Long]] =
    Some(previousCandidates.filter(pipelines.contains).map(_.candidateIdLong))
}

/**
 * Get an empty list of organic item candidates
 */
case object EmptyOrganicItemIds extends GetOrganicItemIds {

  def apply(previousCandidates: Seq[CandidateWithDetails]): Option[Seq[Long]] = None
}
