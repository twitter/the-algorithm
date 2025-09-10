package com.twitter.tweet_mixer.marshaller.response

import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.model.common.identifier.TransportMarshallerIdentifier
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Marshals a domain response into a Thrift response.
 *
 * NOTE: You will most likely not need to modify this.
 */
@Singleton
class TweetMixerResponseTransportMarshaller @Inject() (
  tweetMixerProductResponseMarshaller: TweetMixerProductResponseMarshaller)
    extends TransportMarshaller[TweetMixerResponse, t.TweetMixerRecommendationResponse] {

  val identifier: TransportMarshallerIdentifier =
    TransportMarshallerIdentifier("TweetMixerResponse")

  def apply(tweetMixerResponse: TweetMixerResponse): t.TweetMixerRecommendationResponse = {
    tweetMixerProductResponseMarshaller(tweetMixerResponse.tweetMixerProductResponse)
  }
}
