package com.twitter.timelineranker.entity_tweets

import com.twitter.timelineranker.model.CandidateTweetsResult
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.util.Future

/**
 * A repository of entity tweets candidates.
 *
 * For now, it does not cache any results therefore forwards all calls to the underlying source.
 */
class EntityTweetsRepository(source: EntityTweetsSource) {
  def get(query: RecapQuery): Future[CandidateTweetsResult] = {
    source.get(query)
  }

  def get(queries: Seq[RecapQuery]): Future[Seq[CandidateTweetsResult]] = {
    source.get(queries)
  }
}
