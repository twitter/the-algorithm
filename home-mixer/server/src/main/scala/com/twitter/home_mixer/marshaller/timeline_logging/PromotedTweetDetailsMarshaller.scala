package com.ExTwitter.home_mixer.marshaller.timeline_logging

import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.ExTwitter.timelines.timeline_logging.{thriftscala => thriftlog}

object PromotedTweetDetailsMarshaller {

  def apply(entry: TweetItem, position: Int): thriftlog.PromotedTweetDetails = {
    thriftlog.PromotedTweetDetails(
      advertiserId = Some(entry.promotedMetadata.map(_.advertiserId).getOrElse(0L)),
      insertPosition = Some(position),
      impressionId = entry.promotedMetadata.flatMap(_.impressionString)
    )
  }
}
