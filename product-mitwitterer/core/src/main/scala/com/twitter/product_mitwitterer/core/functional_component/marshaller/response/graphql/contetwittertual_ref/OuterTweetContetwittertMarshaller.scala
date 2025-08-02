package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.graphql.contetwittertual_ref

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.OuterTweetContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.QuoteTweetId
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.RetweetId
import com.twitter.strato.graphql.contetwittertual_refs.{thriftscala => thrift}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class OuterTweetContetwittertMarshaller @Inject() () {

  def apply(outerTweetContetwittert: OuterTweetContetwittert): thrift.OuterTweetContetwittert =
    outerTweetContetwittert match {
      case QuoteTweetId(id) => thrift.OuterTweetContetwittert.QuoteTweetId(id)
      case RetweetId(id) => thrift.OuterTweetContetwittert.RetweetId(id)
    }
}
