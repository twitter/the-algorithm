package com.twitter.tweet_mixer.marshaller.request

import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextUnmarshaller
import com.twitter.tweet_mixer.model.request.TweetMixerRequest
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerRequestUnmarshaller @Inject() (
  clientContextUnmarshaller: ClientContextUnmarshaller,
  tweetMixerProductUnmarshaller: TweetMixerProductUnmarshaller,
  tweetMixerProductContextUnmarshaller: TweetMixerProductContextUnmarshaller,
  tweetMixerDebugParamsUnmarshaller: TweetMixerDebugParamsUnmarshaller) {

  def apply(tweetMixerRequest: t.TweetMixerRequest): TweetMixerRequest = {
    TweetMixerRequest(
      clientContext = clientContextUnmarshaller(tweetMixerRequest.clientContext),
      product = tweetMixerProductUnmarshaller(tweetMixerRequest.product),
      productContext =
        tweetMixerRequest.productContext.map(tweetMixerProductContextUnmarshaller(_)),
      // Avoid de-serializing cursors in the request unmarshaller. The unmarshaller should never
      // fail, which is often a possibility when trying to de-serialize a cursor. Cursors can also
      // be product-specific and more appropriately handled in individual product pipelines.
      serializedRequestCursor = tweetMixerRequest.cursor,
      maxResults = tweetMixerRequest.maxResults,
      debugParams = tweetMixerRequest.debugParams.map(tweetMixerDebugParamsUnmarshaller(_)),
    )
  }
}
