package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.twitter_list

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.twitter_list.TwitterListItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TwitterListItemMarshaller @Inject() (
  twitterListDisplayTypeMarshaller: TwitterListDisplayTypeMarshaller) {

  def apply(twitterListItem: TwitterListItem): urt.TimelineItemContent =
    urt.TimelineItemContent.TwitterList(
      urt.TwitterList(
        id = twitterListItem.id,
        displayType = twitterListItem.displayType.map(twitterListDisplayTypeMarshaller(_))
      )
    )
}
