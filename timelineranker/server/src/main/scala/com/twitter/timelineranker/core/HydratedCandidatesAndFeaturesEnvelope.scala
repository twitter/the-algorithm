package com.twitter.timelineranker.core

import com.twitter.search.common.constants.thriftscala.ThriftLanguage
import com.twitter.search.common.features.thriftscala.ThriftTweetFeatures
import com.twitter.timelineranker.recap.model.ContentFeatures
import com.twitter.timelines.clients.gizmoduck.UserProfileInfo
import com.twitter.timelines.model.TweetId
import com.twitter.timelines.util.FutureUtils
import com.twitter.util.Future

case class HydratedCandidatesAndFeaturesEnvelope(
  candidateEnvelope: CandidateEnvelope,
  userLanguages: Seq[ThriftLanguage],
  userProfileInfo: UserProfileInfo,
  features: Map[TweetId, ThriftTweetFeatures] = Map.empty,
  contentFeaturesFuture: Future[Map[TweetId, ContentFeatures]] = FutureUtils.EmptyMap,
  tweetSourceTweetMap: Map[TweetId, TweetId] = Map.empty,
  inReplyToTweetIds: Set[TweetId] = Set.empty)
