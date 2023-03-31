package com.twitter.home_mixer.product.scored_tweets.marshaller

import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweet
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.TopicContextFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.model.common.identifier.DomainMarshallerIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails

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
    scoredTweets = selections.collect {
      case ItemCandidateWithDetails(candidate: TweetCandidate, _, features) =>
        Seq(mkScoredTweet(candidate.id, features))
      case ModuleCandidateWithDetails(candidates, _, _) =>
        candidates.map { candidate => mkScoredTweet(candidate.candidateIdLong, candidate.features) }
    }.flatten
  )

  private def mkScoredTweet(tweetId: Long, features: FeatureMap): ScoredTweet = {
    val topicFunctionalityType = features
      .getOrElse(TopicContextFunctionalityTypeFeature, None)
      .map(TopicContextFunctionalityTypeMarshaller(_))

    ScoredTweet(
      tweetId = tweetId,
      authorId = features.get(AuthorIdFeature).get,
      score = features.get(ScoreFeature),
      suggestType = features.get(SuggestTypeFeature).get,
      sourceTweetId = features.getOrElse(SourceTweetIdFeature, None),
      sourceUserId = features.getOrElse(SourceUserIdFeature, None),
      quotedTweetId = features.getOrElse(QuotedTweetIdFeature, None),
      quotedUserId = features.getOrElse(QuotedUserIdFeature, None),
      inReplyToTweetId = features.getOrElse(InReplyToTweetIdFeature, None),
      inReplyToUserId = features.getOrElse(InReplyToUserIdFeature, None),
      directedAtUserId = features.getOrElse(DirectedAtUserIdFeature, None),
      inNetwork = Some(features.getOrElse(InNetworkFeature, false)),
      favoritedByUserIds = Some(features.getOrElse(FavoritedByUserIdsFeature, Seq.empty)),
      followedByUserIds = Some(features.getOrElse(FollowedByUserIdsFeature, Seq.empty)),
      topicId = features.getOrElse(TopicIdSocialContextFeature, None),
      topicFunctionalityType = topicFunctionalityType,
      ancestors = Some(features.getOrElse(AncestorsFeature, Seq.empty)),
      isReadFromCache = Some(features.getOrElse(IsReadFromCacheFeature, false)),
      streamToKafka = Some(features.getOrElse(StreamToKafkaFeature, false))
    )
  }
}
