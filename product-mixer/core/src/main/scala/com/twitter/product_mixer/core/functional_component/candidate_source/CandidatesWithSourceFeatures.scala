package com.twitter.product_mixer.core.functional_component.candidate_source

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap

/**
 * Results from a candidate source, optionally carrying extracted query level features to add
 * to the query's feature map (e.g, extracting reusable features from the thrift response of thrift
 * call).
 * @param candidates The candidates returned from the underlying CandidateSoure
 * @param features [[FeatureMap]] containing the features from the candidate source
 *                                    to merge back into the PipelineQuery FeatureMap.
 * @tparam Candidate The type of result
 */
case class CandidatesWithSourceFeatures[+Candidate](
  candidates: Seq[Candidate],
  features: FeatureMap)
