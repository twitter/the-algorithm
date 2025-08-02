package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.graphql.contetwittertual_ref

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.ContetwittertualTweetRef
import com.twitter.strato.graphql.contetwittertual_refs.{thriftscala => thrift}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ContetwittertualTweetRefMarshaller @Inject() (
  tweetHydrationContetwittertMarshaller: TweetHydrationContetwittertMarshaller) {

  def apply(contetwittertualTweetRef: ContetwittertualTweetRef): thrift.ContetwittertualTweetRef =
    thrift.ContetwittertualTweetRef(
      id = contetwittertualTweetRef.id,
      hydrationContetwittert =
        contetwittertualTweetRef.hydrationContetwittert.map(tweetHydrationContetwittertMarshaller(_)))
}
