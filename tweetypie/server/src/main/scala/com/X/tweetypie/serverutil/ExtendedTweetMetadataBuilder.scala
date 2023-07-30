package com.X.tweetypie.serverutil

import com.X.tweetypie.getCashtags
import com.X.tweetypie.getHashtags
import com.X.tweetypie.getMedia
import com.X.tweetypie.getMentions
import com.X.tweetypie.getText
import com.X.tweetypie.getUrls
import com.X.tweetypie.thriftscala.ExtendedTweetMetadata
import com.X.tweetypie.thriftscala.ShortenedUrl
import com.X.tweetypie.thriftscala.Tweet
import com.X.tweetypie.tweettext.Offset
import com.X.tweetypie.tweettext.TextEntity
import com.X.tweetypie.tweettext.Truncator
import com.X.tweetypie.tweettext.TweetText
import com.X.tweetypie.thriftscala.entities.Implicits._

/**
 * Computes the appropriate truncation index to support rendering on legacy clients.
 */
object ExtendedTweetMetadataBuilder {
  import TweetText._

  def apply(tweet: Tweet, selfPermalink: ShortenedUrl): ExtendedTweetMetadata = {

    def entityRanges[T: TextEntity](entities: Seq[T]): Seq[(Int, Int)] =
      entities.map(e => (TextEntity.fromIndex(e).toInt, TextEntity.toIndex(e).toInt))

    val allEntityRanges =
      Offset.Ranges.fromCodePointPairs(
        entityRanges(getUrls(tweet)) ++
          entityRanges(getMentions(tweet)) ++
          entityRanges(getMedia(tweet)) ++
          entityRanges(getHashtags(tweet)) ++
          entityRanges(getCashtags(tweet))
      )

    val text = getText(tweet)

    val apiCompatibleTruncationIndex =
      // need to leave enough space for ellipsis, space, and self-permalink
      Truncator.truncationPoint(
        text = text,
        maxDisplayLength = OriginalMaxDisplayLength - selfPermalink.shortUrl.length - 2,
        atomicUnits = allEntityRanges
      )

    ExtendedTweetMetadata(
      apiCompatibleTruncationIndex = apiCompatibleTruncationIndex.codePointOffset.toInt
    )
  }
}
