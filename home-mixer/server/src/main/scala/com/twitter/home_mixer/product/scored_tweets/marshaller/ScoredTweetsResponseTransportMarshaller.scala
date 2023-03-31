package com.twitter.home_mixer.product.scored_tweets.marshaller

import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.model.common.identifier.TransportMarshallerIdentifier

/**
 * Marshall the domain model into our transport (Thrift) model.
 */
object ScoredTweetsResponseTransportMarshaller
    extends TransportMarshaller[ScoredTweetsResponse, t.ScoredTweetsResponse] {

  override val identifier: TransportMarshallerIdentifier =
    TransportMarshallerIdentifier("ScoredTweetsResponse")

  override def apply(input: ScoredTweetsResponse): t.ScoredTweetsResponse = {
    val scoredTweets = input.scoredTweets.map { tweet =>
      t.ScoredTweet(
        tweetId = tweet.tweetId,
        authorId = tweet.authorId,
        score = tweet.score,
        suggestType = Some(tweet.suggestType),
        sourceTweetId = tweet.sourceTweetId,
        sourceUserId = tweet.sourceUserId,
        quotedTweetId = tweet.quotedTweetId,
        quotedUserId = tweet.quotedUserId,
        inReplyToTweetId = tweet.inReplyToTweetId,
        inReplyToUserId = tweet.inReplyToUserId,
        directedAtUserId = tweet.directedAtUserId,
        inNetwork = tweet.inNetwork,
        favoritedByUserIds = tweet.favoritedByUserIds,
        followedByUserIds = tweet.followedByUserIds,
        topicId = tweet.topicId,
        topicFunctionalityType = tweet.topicFunctionalityType,
        ancestors = tweet.ancestors,
        isReadFromCache = tweet.isReadFromCache,
        streamToKafka = tweet.streamToKafka
      )
    }
    t.ScoredTweetsResponse(scoredTweets)
  }
}
