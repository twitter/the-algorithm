package com.twitter.product_mixer.core.model.common.presentation

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import scala.collection.immutable.ListSet

/**
 * A list set of all the candidate pipelines a candidate originated from. This is typically a
 * single element set, but merging candidates across pipelines using
 * [[com.twitter.product_mixer.component_library.selector.CombineFeatureMapsCandidateMerger]]
 * will merge sets for the candidate. The last element of the set is the first pipeline identifier
 * as we prepend new ones since we want O(1) access for the last element.
 */
object CandidatePipelines extends Feature[UniversalNoun[Any], ListSet[CandidatePipelineIdentifier]]

/**
 * A list set of all the candidate sources a candidate originated from. This is typically a
 * single element set, but merging candidates across pipelines using
 * [[com.twitter.product_mixer.component_library.selector.CombineFeatureMapsCandidateMerger]]
 * will merge sets for the candidate. The last element of the set is the first source identifier
 * as we prepend new ones since we want O(1) access for the last element.
 */
object CandidateSources extends Feature[UniversalNoun[Any], ListSet[CandidateSourceIdentifier]]

/**
 * The source position relative to all candidates the originating candidate source a candidate
 * came from. When merged with other candidates, the position from the first candidate source
 * takes priority.
 */
object CandidateSourcePosition extends Feature[UniversalNoun[Any], Int]
