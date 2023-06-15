package com.twitter.tweetypie
package repository

import com.twitter.servo.cache.ScopedCacheKey
import com.twitter.stitch.Stitch
import com.twitter.util.Base64Long

case class GeoScrubTimestampKey(userId: UserId)
    extends ScopedCacheKey("t", "gs", 1, Base64Long.toBase64(userId))

object GeoScrubTimestampRepository {
  type Type = UserId => Stitch[Time]

  def apply(getLastGeoScrubTime: UserId => Stitch[Option[Time]]): Type =
    userId => getLastGeoScrubTime(userId).lowerFromOption()
}
