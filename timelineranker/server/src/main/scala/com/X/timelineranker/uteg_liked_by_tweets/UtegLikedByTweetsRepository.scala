package com.X.timelineranker.uteg_liked_by_tweets

import com.X.timelineranker.model.CandidateTweetsResult
import com.X.timelineranker.model.RecapQuery
import com.X.util.Future

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
