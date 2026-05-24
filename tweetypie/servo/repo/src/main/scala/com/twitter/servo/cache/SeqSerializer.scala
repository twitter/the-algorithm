package com.twitter.servo.cache

/**
 * A Serializer of `Seq[T]`s.
 *
 * @param itemSerializer a Serializer for the individual elements.
 * @param itemSizeEstimate estimated size in bytes of individual elements
 */
class SeqSerializer[T](itemSerializer: Serializer[T], itemSizeEstimate: Int = 8)
    extends IterableSerializer[T, Seq[T]](() => Seq.newBuilder[T], itemSerializer, itemSizeEstimate)
