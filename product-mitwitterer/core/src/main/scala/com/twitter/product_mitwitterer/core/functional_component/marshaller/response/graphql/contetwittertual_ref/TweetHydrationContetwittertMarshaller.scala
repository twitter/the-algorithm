package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.graphql.contetwittertual_ref

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.rtf.safety_level.SafetyLevelMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.TweetHydrationContetwittert
import com.twitter.strato.graphql.contetwittertual_refs.{thriftscala => thrift}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetHydrationContetwittertMarshaller @Inject() (
  safetyLevelMarshaller: SafetyLevelMarshaller,
  outerTweetContetwittertMarshaller: OuterTweetContetwittertMarshaller) {

  def apply(tweetHydrationContetwittert: TweetHydrationContetwittert): thrift.TweetHydrationContetwittert =
    thrift.TweetHydrationContetwittert(
      safetyLevelOverride = tweetHydrationContetwittert.safetyLevelOverride.map(safetyLevelMarshaller(_)),
      outerTweetContetwittert =
        tweetHydrationContetwittert.outerTweetContetwittert.map(outerTweetContetwittertMarshaller(_))
    )
}
