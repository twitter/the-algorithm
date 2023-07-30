package com.X.timelineranker.uteg_liked_by_tweets

import com.X.recos.recos_common.thriftscala.SocialProofType
import com.X.recos.user_tweet_entity_graph.thriftscala.TweetRecommendation
import com.X.search.earlybird.thriftscala.ThriftSearchResult
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.model.RecapQuery.DependencyProvider
import com.X.timelines.model.TweetId
import com.X.util.Future

class MinNumNonAuthorFavoritedByUserIdsFilterTransform(
  minNumFavoritedByUserIdsProvider: DependencyProvider[Int])
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val filteredSearchResults = envelope.searchResults.filter { searchResult =>
      numNonAuthorFavs(
        searchResult = searchResult,
        utegResultsMap = envelope.utegResults
      ).exists(_ >= minNumFavoritedByUserIdsProvider(envelope.query))
    }
    Future.value(envelope.copy(searchResults = filteredSearchResults))
  }

  // return number of non-author users that faved the tweet in a searchResult
  // return None if author is None or if the tweet is not found in utegResultsMap
  protected def numNonAuthorFavs(
    searchResult: ThriftSearchResult,
    utegResultsMap: Map[TweetId, TweetRecommendation]
  ): Option[Int] = {
    for {
      metadata <- searchResult.metadata
      authorId = metadata.fromUserId
      tweetRecommendation <- utegResultsMap.get(searchResult.id)
      favedByUserIds <- tweetRecommendation.socialProofByType.get(SocialProofType.Favorite)
    } yield favedByUserIds.filterNot(_ == authorId).size
  }
}
