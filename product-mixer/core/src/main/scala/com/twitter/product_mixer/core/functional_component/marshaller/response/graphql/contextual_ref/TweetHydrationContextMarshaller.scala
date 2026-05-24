package com.twitter.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref

import com.twitter.product_mixer.core.functional_component.marshaller.response.rtf.safety_level.SafetyLevelMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.twitter.strato.graphql.contextual_refs.{thriftscala => thrift}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetHydrationContextMarshaller @Inject() (
  safetyLevelMarshaller: SafetyLevelMarshaller,
  outerTweetContextMarshaller: OuterTweetContextMarshaller) {

  def apply(tweetHydrationContext: TweetHydrationContext): thrift.TweetHydrationContext =
    thrift.TweetHydrationContext(
      safetyLevelOverride = tweetHydrationContext.safetyLevelOverride.map(safetyLevelMarshaller(_)),
      outerTweetContext =
        tweetHydrationContext.outerTweetContext.map(outerTweetContextMarshaller(_))
    )
}
