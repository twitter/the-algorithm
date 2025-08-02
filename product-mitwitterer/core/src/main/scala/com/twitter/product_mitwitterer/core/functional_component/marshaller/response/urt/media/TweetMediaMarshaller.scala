package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.media

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.TweetMedia
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetMediaMarshaller @Inject() () {

  def apply(tweetMedia: TweetMedia): urt.TweetMedia = urt.TweetMedia(
    tweetId = tweetMedia.tweetId,
    momentId = tweetMedia.momentId
  )
}
