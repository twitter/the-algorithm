package com.twitter.product_mixer.component_library.model.candidate

import com.twitter.product_mixer.core.model.common.UniversalNoun

sealed trait BasePromptCandidate[+T] extends UniversalNoun[T]

/**
 * Canonical InlinePromptCandidate model. Always prefer this version over all other variants.
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
final class InlinePromptCandidate private (
  override val id: String)
    extends BasePromptCandidate[String] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[InlinePromptCandidate]

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
      case candidate: InlinePromptCandidate =>
        ((this eq candidate)
          || ((hashCode == candidate.hashCode) && (id == candidate.id)))
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
  override val hashCode: Int = id.##
}

object InlinePromptCandidate {
  def apply(id: String): InlinePromptCandidate = new InlinePromptCandidate(id)
}

/**
 * Canonical CompactPromptCandidate model. Always prefer this version over all other variants.
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
final class CompactPromptCandidate private (
  override val id: Long)
    extends BasePromptCandidate[Long] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[CompactPromptCandidate]

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
      case candidate: CompactPromptCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode) && (id == candidate.id))
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
   *         (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *         data structure), assuming stable hashCode implementations for these objects
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int = id.##
}

object CompactPromptCandidate {
  def apply(id: Long): CompactPromptCandidate = new CompactPromptCandidate(id)
}

/**
 * Canonical FullCoverPromptCandidate model. Always prefer this version over all other variants.
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
final class FullCoverPromptCandidate private (
  override val id: String)
    extends BasePromptCandidate[String] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[FullCoverPromptCandidate]

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
      case candidate: FullCoverPromptCandidate =>
        ((this eq candidate)
          || ((hashCode == candidate.hashCode) && (id == candidate.id)))
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
   *         (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *         data structure), assuming stable hashCode implementations for these objects
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int = id.##
}

object FullCoverPromptCandidate {
  def apply(id: String): FullCoverPromptCandidate = new FullCoverPromptCandidate(id)
}

/**
 * Canonical HalfCoverPromptCandidate model. Always prefer this version over all other variants.
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
final class HalfCoverPromptCandidate private (
  override val id: String)
    extends BasePromptCandidate[String] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[HalfCoverPromptCandidate]

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
      case candidate: HalfCoverPromptCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id))
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
   *         (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *         data structure), assuming stable hashCode implementations for these objects
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int = id.##
}

object HalfCoverPromptCandidate {
  def apply(id: String): HalfCoverPromptCandidate = new HalfCoverPromptCandidate(id)
}

/**
 * Canonical PromptCarouselTileCandidate model. Always prefer this version over all other variants.
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
final class PromptCarouselTileCandidate private (
  override val id: Long)
    extends BasePromptCandidate[Long] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[PromptCarouselTileCandidate]

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
      case candidate: PromptCarouselTileCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id))
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
   *         (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *         data structure), assuming stable hashCode implementations for these objects
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int = id.##
}

object PromptCarouselTileCandidate {
  def apply(id: Long): PromptCarouselTileCandidate = new PromptCarouselTileCandidate(id)
}

/**
 * Canonical RelevancePromptCandidate model. Always prefer this version over all other variants.
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
final class RelevancePromptCandidate private (
  override val id: String,
  val position: Option[Int])
    extends BasePromptCandidate[String] {

  /**
   * @inheritdoc
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[RelevancePromptCandidate]

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
      case candidate: RelevancePromptCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id && position == candidate.position))
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
   *         (i.e. each field is a val)
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *       - Inability to mutate the field object instance itself (i.e. each field is an immutable
   *         data structure), assuming stable hashCode implementations for these objects
   * @note In order for the hashCode to be consistent with object equality, `##` must be used for
   *       boxed numeric types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  override val hashCode: Int =
    31 * (
      id.##
    ) + position.##
}

object RelevancePromptCandidate {
  def apply(
    id: String,
    position: Option[Int] = None
  ): RelevancePromptCandidate =
    new RelevancePromptCandidate(id, position)
}
