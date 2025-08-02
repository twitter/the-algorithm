package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tweet_composer

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet_composer.TweetComposerItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetComposerItemMarshaller @Inject() (
  tweetComposerDisplayTypeMarshaller: TweetComposerDisplayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(tweetComposer: TweetComposerItem): urt.TimelineItemContent =
    urt.TimelineItemContent.TweetComposer(
      urt.TweetComposer(
        displayType = tweetComposerDisplayTypeMarshaller(tweetComposer.displayType),
        tetwittert = tweetComposer.tetwittert,
        url = urlMarshaller(tweetComposer.url)
      )
    )
}
