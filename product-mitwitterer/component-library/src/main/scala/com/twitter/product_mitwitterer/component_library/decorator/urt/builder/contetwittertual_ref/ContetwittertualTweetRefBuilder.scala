package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.contetwittertual_ref

import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.ContetwittertualTweetRef
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.contetwittertual_ref.TweetHydrationContetwittert

case class ContetwittertualTweetRefBuilder[-Candidate <: BaseTweetCandidate](
  tweetHydrationContetwittert: TweetHydrationContetwittert) {

  def apply(candidate: Candidate): Option[ContetwittertualTweetRef] =
    Some(ContetwittertualTweetRef(candidate.id, Some(tweetHydrationContetwittert)))
}
