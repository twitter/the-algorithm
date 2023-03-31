package com.twitter.home_mixer.product.scored_tweets.model

import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.timelineservice.suggests.{thriftscala => st}
import com.twitter.tweetconvosvc.tweet_ancestor.{thriftscala => ta}
import com.twitter.timelines.render.{thriftscala => urt}

case class ScoredTweet(
  tweetId: Long,
  authorId: Long,
  score: Option[Double],
  suggestType: st.SuggestType,
  sourceTweetId: Option[Long],
  sourceUserId: Option[Long],
  quotedTweetId: Option[Long],
  quotedUserId: Option[Long],
  inReplyToTweetId: Option[Long],
  inReplyToUserId: Option[Long],
  directedAtUserId: Option[Long],
  inNetwork: Option[Boolean],
  favoritedByUserIds: Option[Seq[Long]],
  followedByUserIds: Option[Seq[Long]],
  topicId: Option[Long],
  topicFunctionalityType: Option[urt.TopicContextFunctionalityType],
  ancestors: Option[Seq[ta.TweetAncestor]],
  isReadFromCache: Option[Boolean],
  streamToKafka: Option[Boolean])

case class ScoredTweetsResponse(scoredTweets: Seq[ScoredTweet]) extends HasMarshalling
