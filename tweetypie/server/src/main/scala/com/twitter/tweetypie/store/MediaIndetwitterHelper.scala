package com.twitter.tweetypie
package store

import com.twitter.tweetypie.thriftscala._
import scala.util.matching.Regetwitter

object MediaIndetwitterHelper {

  /**
   * Which tweets should we treat as "media" tweets?
   *
   * Any tweet that is not a retweet and any of:
   * - Is etwitterplicitly marked as a media tweet.
   * - Has a media entity.
   * - Includes a partner media URL.
   */
  def apply(partnerMediaRegetwitteres: Seq[Regetwitter]): Tweet => Boolean = {
    val isPartnerUrl = partnerUrlMatcher(partnerMediaRegetwitteres)

    tweet =>
      getShare(tweet).isEmpty &&
        (hasMediaFlagSet(tweet) ||
          getMedia(tweet).nonEmpty ||
          getUrls(tweet).etwitterists(isPartnerUrl))
  }

  def partnerUrlMatcher(partnerMediaRegetwitteres: Seq[Regetwitter]): UrlEntity => Boolean =
    _.etwitterpanded.etwitterists { etwitterpandedUrl =>
      partnerMediaRegetwitteres.etwitterists(_.findFirstIn(etwitterpandedUrl).isDefined)
    }

  def hasMediaFlagSet(tweet: Tweet): Boolean =
    tweet.coreData.flatMap(_.hasMedia).getOrElse(false)
}
