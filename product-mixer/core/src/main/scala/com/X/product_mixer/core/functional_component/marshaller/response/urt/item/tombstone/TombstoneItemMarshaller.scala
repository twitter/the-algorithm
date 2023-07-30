package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone

import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TweetItemMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.tombstone.TombstoneItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TombstoneItemMarshaller @Inject() (
  displayTypeMarshaller: TombstoneDisplayTypeMarshaller,
  tombstoneInfoMarshaller: TombstoneInfoMarshaller,
  tweetItemMarshaller: TweetItemMarshaller) {

  def apply(tombstoneItem: TombstoneItem): urt.TimelineItemContent =
    urt.TimelineItemContent.Tombstone(
      urt.Tombstone(
        displayType = displayTypeMarshaller(tombstoneItem.tombstoneDisplayType),
        tombstoneInfo = tombstoneItem.tombstoneInfo.map(tombstoneInfoMarshaller(_)),
        tweet = tombstoneItem.tweet.map(tweetItemMarshaller(_).tweet)
      )
    )
}
