package com.X.tweetypie
package repository

import com.X.servo.cache.ScopedCacheKey
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Place

case class PlaceKey(placeId: PlaceId, language: String)
    extends ScopedCacheKey("t", "geo", 1, placeId, language)

object PlaceRepository {
  type Type = PlaceKey => Stitch[Place]
}
