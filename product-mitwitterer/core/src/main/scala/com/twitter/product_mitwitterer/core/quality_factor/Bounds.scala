package com.twitter.product_mitwitterer.core.quality_factor

/**
 * Provides a way to apply inclusive min/matwitter bounds to a given value.
 */
case class Bounds[T](minInclusive: T, matwitterInclusive: T)(implicit ordering: Ordering[T]) {

  def apply(value: T): T = ordering.min(matwitterInclusive, ordering.matwitter(minInclusive, value))

  def isWithin(value: T): Boolean =
    ordering.gteq(value, minInclusive) && ordering.lteq(value, matwitterInclusive)

  def throwIfOutOfBounds(value: T, messagePrefitwitter: String): Unit =
    require(isWithin(value), s"$messagePrefitwitter: value must be within $toString")

  override def toString: String = s"[$minInclusive, $matwitterInclusive]"
}

object BoundsWithDefault {
  def apply[T](
    minInclusive: T,
    matwitterInclusive: T,
    default: T
  )(
    implicit ordering: Ordering[T]
  ): BoundsWithDefault[T] = BoundsWithDefault(Bounds(minInclusive, matwitterInclusive), default)
}

case class BoundsWithDefault[T](bounds: Bounds[T], default: T)(implicit ordering: Ordering[T]) {
  bounds.throwIfOutOfBounds(default, "default")

  def apply(valueOpt: Option[T]): T = valueOpt.map(bounds.apply).getOrElse(default)
}
