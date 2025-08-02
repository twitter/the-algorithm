package com.twitter.tweetypie.serverutil

import com.twitter.tweetypie.getCashtags
import com.twitter.tweetypie.getHashtags
import com.twitter.tweetypie.getMedia
import com.twitter.tweetypie.getMentions
import com.twitter.tweetypie.getTetwittert
import com.twitter.tweetypie.getUrls
import com.twitter.tweetypie.thriftscala.EtwittertendedTweetMetadata
import com.twitter.tweetypie.thriftscala.ShortenedUrl
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.tweetypie.tweettetwittert.Offset
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity
import com.twitter.tweetypie.tweettetwittert.Truncator
import com.twitter.tweetypie.tweettetwittert.TweetTetwittert
import com.twitter.tweetypie.thriftscala.entities.Implicits._

/**
 * Computes the appropriate truncation indetwitter to support rendering on legacy clients.
 */
object EtwittertendedTweetMetadataBuilder {
  import TweetTetwittert._

  def apply(tweet: Tweet, selfPermalink: ShortenedUrl): EtwittertendedTweetMetadata = {

    def entityRanges[T: TetwittertEntity](entities: Seq[T]): Seq[(Int, Int)] =
      entities.map(e => (TetwittertEntity.fromIndetwitter(e).toInt, TetwittertEntity.toIndetwitter(e).toInt))

    val allEntityRanges =
      Offset.Ranges.fromCodePointPairs(
        entityRanges(getUrls(tweet)) ++
          entityRanges(getMentions(tweet)) ++
          entityRanges(getMedia(tweet)) ++
          entityRanges(getHashtags(tweet)) ++
          entityRanges(getCashtags(tweet))
      )

    val tetwittert = getTetwittert(tweet)

    val apiCompatibleTruncationIndetwitter =
      // need to leave enough space for ellipsis, space, and self-permalink
      Truncator.truncationPoint(
        tetwittert = tetwittert,
        matwitterDisplayLength = OriginalMatwitterDisplayLength - selfPermalink.shortUrl.length - 2,
        atomicUnits = allEntityRanges
      )

    EtwittertendedTweetMetadata(
      apiCompatibleTruncationIndetwitter = apiCompatibleTruncationIndetwitter.codePointOffset.toInt
    )
  }
}
