package com.X.product_mixer.core.functional_component.marshaller.response.graphql.contextual_ref

import com.X.product_mixer.core.model.marshalling.response.urt.contextual_ref.OuterTweetContext
import com.X.product_mixer.core.model.marshalling.response.urt.contextual_ref.QuoteTweetId
import com.X.product_mixer.core.model.marshalling.response.urt.contextual_ref.RetweetId
import com.X.strato.graphql.contextual_refs.{thriftscala => thrift}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OuterTweetContextMarshaller @Inject() () {

  def apply(outerTweetContext: OuterTweetContext): thrift.OuterTweetContext =
    outerTweetContext match {
      case QuoteTweetId(id) => thrift.OuterTweetContext.QuoteTweetId(id)
      case RetweetId(id) => thrift.OuterTweetContext.RetweetId(id)
    }
}
