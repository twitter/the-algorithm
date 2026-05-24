package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.twitter_list

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.List
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.ListTile
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.ListWithPin
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.ListWithSubscribe
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.TwitterListDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterListDisplayTypeMarshaller @Inject() () {

  def apply(twitterListDisplayType: TwitterListDisplayType): urt.TwitterListDisplayType =
    twitterListDisplayType match {
      case List => urt.TwitterListDisplayType.List
      case ListTile => urt.TwitterListDisplayType.ListTile
      case ListWithPin => urt.TwitterListDisplayType.ListWithPin
      case ListWithSubscribe => urt.TwitterListDisplayType.ListWithSubscribe
    }
}
