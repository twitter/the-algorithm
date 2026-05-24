package com.twitter.servo.cache

/**
 * Converts all keys to a string via .toString
 */
class ToStringKeyTransformer[K] extends KeyTransformer[K] {
  override def apply(key: K) = key.toString
}

/**
 * Prefixes all keys with a string
 */
class PrefixKeyTransformer[K](
  prefix: String,
  delimiter: String = constants.Colon,
  underlying: KeyTransformer[K] = new ToStringKeyTransformer[K]: ToStringKeyTransformer[K])
    extends KeyTransformer[K] {
  private[this] val fullPrefix = prefix + delimiter

  override def apply(key: K) = fullPrefix + underlying(key)
}
