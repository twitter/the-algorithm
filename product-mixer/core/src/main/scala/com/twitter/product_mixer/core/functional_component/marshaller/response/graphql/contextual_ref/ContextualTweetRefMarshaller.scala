package com.twitter.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref

import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.ContextualTweetRef
import com.twitter.strato.graphql.contextual_refs.{thriftscala => thrift}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextualTweetRefMarshaller @Inject() (
  tweetHydrationContextMarshaller: TweetHydrationContextMarshaller) {

  def apply(contextualTweetRef: ContextualTweetRef): thrift.ContextualTweetRef =
    thrift.ContextualTweetRef(
      id = contextualTweetRef.id,
      hydrationContext =
        contextualTweetRef.hydrationContext.map(tweetHydrationContextMarshaller(_)))
}
