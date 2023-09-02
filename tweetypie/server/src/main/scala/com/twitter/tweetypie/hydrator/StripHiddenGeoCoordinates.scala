package com.twitter.tweetypie
package hydrator

object StripHiddenGeoCoordinates extends Mutation[Tweet] {
  def apply(tweet: Tweet): Option[Tweet] =
    for {
      coreData <- tweet.coreData
      coords <- coreData.coordinates
      if !coords.display
      coreData2 = coreData.copy(coordinates = None)
    } yield tweet.copy(coreData = Some(coreData2))
}
