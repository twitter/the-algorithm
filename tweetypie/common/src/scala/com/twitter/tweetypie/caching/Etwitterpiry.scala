package com.twitter.tweetypie.caching

import com.twitter.util.Duration
import com.twitter.util.Time

/**
 * Helpers for creating common etwitterpiry functions.
 *
 * An etwitterpiry function maps from the value to a time in the future when
 * the value should etwitterpire from cache. These are useful in the
 * implementation of a [[ValueSerializer]].
 */
object Etwitterpiry {

  /**
   * Return a time that indicates to memcached to never etwitterpire this
   * value.
   *
   * This function takes [[Any]] so that it can be used at any value
   * type, since it doesn't etwitteramine the value at all.
   */
  val Never: Any => Time =
    _ => Time.Top

  /**
   * Return function that indicates to memcached that the value should
   * not be used after the `ttl` has elapsed.
   *
   * This function takes [[Any]] so that it can be used at any value
   * type, since it doesn't etwitteramine the value at all.
   */
  def byAge(ttl: Duration): Any => Time =
    _ => Time.now + ttl
}
