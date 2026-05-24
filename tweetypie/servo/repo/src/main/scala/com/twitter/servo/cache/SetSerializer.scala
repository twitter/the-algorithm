package com.twitter.servo.cache

/**
 * A Serializer of `Set[T]`s.
 *
 * @param itemSerializer a Serializer for the individual elements.
 * @param itemSizeEstimate estimated size in bytes of individual elements
 */
class SetSerializer[T](itemSerializer: Serializer[T], itemSizeEstimate: Int = 8)
    extends IterableSerializer[T, Set[T]](() => Set.newBuilder[T], itemSerializer, itemSizeEstimate)
