package com.twitter.tweetypie.caching

/**
 * Encodes the possible states of a value loaded from memcached.
 *
 * @see [[ValueSerializer]] and [[CacheOperations]]
 */
sealed trait CacheResult[+V]

object CacheResult {

  /**
   * Signals that the value could not be successfully loaded from
   * cache. `Failure` values should not be written back to cache.
   *
   * This value may result from an error talking to the memcached
   * instance or it may be returned from the Serializer when the value
   * should not be reused, but should also not be overwritten.
   */
  final case class Failure(e: Throwable) extends CacheResult[Nothing]

  /**
   * Signals that the cache load attempt was successful, but there was
   * not a usable value.
   *
   * When processing a `Miss`, the value should be written back to
   * cache if it loads successfully.
   */
  case object Miss extends CacheResult[Nothing]

  /**
   * Signals that the value was found in cache.
   *
   * It is not necessary to load the value from the original source.
   */
  case class Fresh[V](value: V) extends CacheResult[V]

  /**
   * Signals that the value was found in cache.
   *
   * This value should be used, but it should be refreshed
   * out-of-band.
   */
  case class Stale[V](value: V) extends CacheResult[V]
}
