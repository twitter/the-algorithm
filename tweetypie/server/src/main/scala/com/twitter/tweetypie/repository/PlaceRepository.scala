package com.twitter.tweetypie
package repository

import com.twitter.servo.cache.ScopedCacheKey
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.thriftscala.Place

case class PlaceKey(placeId: PlaceId, language: String)
    extends ScopedCacheKey("t", "geo", 1, placeId, language)

object PlaceRepository {
  type Type = PlaceKey => Stitch[Place]
}
