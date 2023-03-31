package com.twitter.timelineranker.uteg_liked_by_tweets

import com.twitter.timelineranker.model.CandidateTweetsResult
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.util.Future

/**
 * A repository of YML tweets candidiates
 */
class UtegLikedByTweetsRepository(source: UtegLikedByTweetsSource) {
  def get(query: RecapQuery): Future[CandidateTweetsResult] = {
    source.get(query)
  }

  def get(queries: Seq[RecapQuery]): Future[Seq[CandidateTweetsResult]] = {
    source.get(queries)
  }
}
