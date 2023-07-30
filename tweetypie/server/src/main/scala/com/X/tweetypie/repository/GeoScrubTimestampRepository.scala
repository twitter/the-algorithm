package com.X.tweetypie
package repository

import com.X.servo.cache.ScopedCacheKey
import com.X.stitch.Stitch
import com.X.util.Base64Long

case class GeoScrubTimestampKey(userId: UserId)
    extends ScopedCacheKey("t", "gs", 1, Base64Long.toBase64(userId))

object GeoScrubTimestampRepository {
  type Type = UserId => Stitch[Time]

  def apply(getLastGeoScrubTime: UserId => Stitch[Option[Time]]): Type =
    userId => getLastGeoScrubTime(userId).lowerFromOption()
}
