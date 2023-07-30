package com.X.product_mixer.core.functional_component.transformer

/**
 * Populates a [[com.X.product_mixer.core.feature.featuremap.FeatureMap]] with Features
 * that are available in the [[CandidateSourceResult]]
 */
trait CandidateFeatureTransformer[-CandidateSourceResult]
    extends FeatureTransformer[CandidateSourceResult]
