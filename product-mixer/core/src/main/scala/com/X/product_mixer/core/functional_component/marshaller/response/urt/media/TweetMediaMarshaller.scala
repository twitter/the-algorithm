package com.X.product_mixer.core.functional_component.marshaller.response.urt.media

import com.X.product_mixer.core.model.marshalling.response.urt.media.TweetMedia
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMediaMarshaller @Inject() () {

  def apply(tweetMedia: TweetMedia): urt.TweetMedia = urt.TweetMedia(
    tweetId = tweetMedia.tweetId,
    momentId = tweetMedia.momentId
  )
}
