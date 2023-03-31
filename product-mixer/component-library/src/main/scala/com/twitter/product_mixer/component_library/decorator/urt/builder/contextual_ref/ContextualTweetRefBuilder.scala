package com.twitter.product_mixer.component_library.decorator.urt.builder.contextual_ref

import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.ContextualTweetRef
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext

case class ContextualTweetRefBuilder[-Candidate <: BaseTweetCandidate](
  tweetHydrationContext: TweetHydrationContext) {

  def apply(candidate: Candidate): Option[ContextualTweetRef] =
    Some(ContextualTweetRef(candidate.id, Some(tweetHydrationContext)))
}
