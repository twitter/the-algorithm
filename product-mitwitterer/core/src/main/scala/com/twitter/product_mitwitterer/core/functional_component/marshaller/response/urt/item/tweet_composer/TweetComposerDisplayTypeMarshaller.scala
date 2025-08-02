package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tweet_composer

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet_composer.Reply
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet_composer.TweetComposerDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet_composer.TweetComposerSelfThread
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetComposerDisplayTypeMarshaller @Inject() () {

  def apply(displayType: TweetComposerDisplayType): urt.TweetComposerDisplayType =
    displayType match {
      case TweetComposerSelfThread => urt.TweetComposerDisplayType.SelfThread
      case Reply => urt.TweetComposerDisplayType.Reply
    }
}
