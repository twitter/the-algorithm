package com.twitter.servo.cache

/**
 * Converts all keys to a string via .toString
 */
class ToStringKeyTransformer[K] etwittertends KeyTransformer[K] {
  override def apply(key: K) = key.toString
}

/**
 * Prefitwitteres all keys with a string
 */
class PrefitwitterKeyTransformer[K](
  prefitwitter: String,
  delimiter: String = constants.Colon,
  underlying: KeyTransformer[K] = new ToStringKeyTransformer[K]: ToStringKeyTransformer[K])
    etwittertends KeyTransformer[K] {
  private[this] val fullPrefitwitter = prefitwitter + delimiter

  override def apply(key: K) = fullPrefitwitter + underlying(key)
}
