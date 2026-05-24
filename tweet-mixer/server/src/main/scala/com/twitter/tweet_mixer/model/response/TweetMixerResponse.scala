package com.twitter.tweet_mixer.model.response

import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.model.marshalling.HasLength

case class TweetMixerResponse(tweetMixerProductResponse: TweetMixerProductResponse)
    extends HasMarshalling
    with HasLength {
  override def length: Int = {
    tweetMixerProductResponse match {
      case response: HasLength => response.length
      case _ => 1
    }
  }
}
