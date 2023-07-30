package com.X.timelineranker.core

import com.X.search.common.constants.thriftscala.ThriftLanguage
import com.X.search.common.features.thriftscala.ThriftTweetFeatures
import com.X.timelineranker.recap.model.ContentFeatures
import com.X.timelines.clients.gizmoduck.UserProfileInfo
import com.X.timelines.model.TweetId
import com.X.timelines.util.FutureUtils
import com.X.util.Future

case class HydratedCandidatesAndFeaturesEnvelope(
  candidateEnvelope: CandidateEnvelope,
  userLanguages: Seq[ThriftLanguage],
  userProfileInfo: UserProfileInfo,
  features: Map[TweetId, ThriftTweetFeatures] = Map.empty,
  contentFeaturesFuture: Future[Map[TweetId, ContentFeatures]] = FutureUtils.EmptyMap,
  tweetSourceTweetMap: Map[TweetId, TweetId] = Map.empty,
  inReplyToTweetIds: Set[TweetId] = Set.empty)
