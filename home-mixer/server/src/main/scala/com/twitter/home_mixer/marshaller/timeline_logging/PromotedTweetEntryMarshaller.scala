package com.twitter.home_mixer.marshaller.timeline_logging

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object PromotedTweetEntryMarshaller {

  def apply(entry: TweetItem, position: Int): thriftlog.PromotedTweetEntry = {
    thriftlog.PromotedTweetEntry(
      id = entry.id,
      advertiserId = entry.promotedMetadata.map(_.advertiserId).getOrElse(0L),
      insertPosition = position,
      impressionId = entry.promotedMetadata.flatMap(_.impressionString),
      displayType = Some(entry.displayType.toString)
    )
  }
}
