package com.twitter.product_mixer.component_library.model.candidate.suggestion

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion.SpellingActionType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion.TextResult

/**
 * Canonical SpellingSuggestionCandidate model. Always prefer this version over all other variants.
 *
 * @note Any additional fields should be added as a [[com.twitter.product_mixer.core.feature.Feature]]
 *       on the candidate's [[com.twitter.product_mixer.core.feature.featuremap.FeatureMap]]. If the
 *       features come from the candidate source itself (as opposed to hydrated via a
 *       [[com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator]]),
 *       then [[com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig.featuresFromCandidateSourceTransformers]]
 *       can be used to extract features from the candidate source response.
 *
 * @note This class should always remain `final`. If for any reason the `final` modifier is removed,
 *       the equals() implementation must be updated in order to handle class inheritor equality
 *       (see note on the equals method below)
 */
final class SpellingSuggestionCandidate private (
  override val id: String,
  val textResult: TextResult,
  val spellingActionType: Option[SpellingActionType],
  val originalQuery: Option[String])
    extends UniversalNoun[String] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[SpellingSuggestionCandidate]

  /**
   * High performance implementation of equals method that leverages:
   *  - Referential equality short circuit
   *  - Cached hashcode equality short circuit
   *  - Field values are only checked if the hashCodes are equal to handle the unlikely case
   *    of a hashCode collision
   *  - Removal of check for `that` being an equals-compatible descendant since this class is final
   *
   * @note `candidate.canEqual(this)` is not necessary because this class is final
   * @see [[http://www.artima.com/pins1ed/object-equality.html Programming in Scala,
   *      Chapter 28]] for discussion and design.
   */
  override def equals(that: Any): Boolean =
    that match {
      case candidate: SpellingSuggestionCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id && textResult == candidate.textResult && spellingActionType == candidate.spellingActionType && originalQuery == candidate.originalQuery))
        )
      case _ =>
        false
    }

  /**
   * Leverage domain-specific constraints (see notes below) to safely construct and cache the
   * hashCode as a val, such that it is instantiated once on object construction. This prevents the
   * need to recompute the hashCode on each hashCode() invocation, which is the behavior of the
   * Scala compiler case class-generated hashCode() since it cannot make assumptions regarding field
   * object mutability and hashCode implementations.
   *
   * @note Caching the hashCode is only safe if all of the fields used to construct the hashCode
   *       are immutable. This includes:
   *       - Inability to mutate the object reference on for an existing instantiated candidate
   *       (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       data structure), assuming stable hashCode implementations for these objects
   *
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int =
    31 * (
      31 * (
        31 * (
          id.##
        ) + textResult.##
      ) + spellingActionType.##
    ) + originalQuery.##
}

object SpellingSuggestionCandidate {
  def apply(
    id: String,
    textResult: TextResult,
    spellingActionType: Option[SpellingActionType],
    originalQuery: Option[String]
  ): SpellingSuggestionCandidate =
    new SpellingSuggestionCandidate(id, textResult, spellingActionType, originalQuery)
}
