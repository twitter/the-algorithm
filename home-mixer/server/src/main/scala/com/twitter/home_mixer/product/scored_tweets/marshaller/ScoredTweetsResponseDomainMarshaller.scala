package com.twitter.home_mixer.product.scored_tweets.marshaller

import com.twitter.home_mixer.model.HomeFeatures.UserActionsContainsExplicitSignalsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsSizeFeature
import com.twitter.home_mixer.product.scored_tweets.model.QueryMetadata
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.model.common.identifier.DomainMarshallerIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails

/**
 * Creates a domain model of the Scored Tweets product response from the set of candidates selected
 */
object ScoredTweetsResponseDomainMarshaller
    extends DomainMarshaller[ScoredTweetsQuery, ScoredTweetsResponse] {

  override val identifier: DomainMarshallerIdentifier =
    DomainMarshallerIdentifier("ScoredTweetsResponse")

  override def apply(
    query: ScoredTweetsQuery,
    selections: Seq[CandidateWithDetails]
  ): ScoredTweetsResponse = ScoredTweetsResponse(
    scoredTweets = selections,
    queryMetadata = Some(
      QueryMetadata(
        userActionsSize = query.features.get.getOrElse(UserActionsSizeFeature, None),
        userActionsContainsExplicitSignals =
          Some(query.features.get.getOrElse(UserActionsContainsExplicitSignalsFeature, false))
      ))
  )
}
