package com.X.timelineranker.recap_hydration

import com.X.timelineranker.model.CandidateTweetsResult
import com.X.timelineranker.model.RecapQuery
import com.X.util.Future

/**
 * A repository of recap hydration results.
 *
 * For now, it does not cache any results therefore forwards all calls to the underlying source.
 */
class RecapHydrationRepository(source: RecapHydrationSource) {
  def hydrate(query: RecapQuery): Future[CandidateTweetsResult] = {
    source.hydrate(query)
  }

  def hydrate(queries: Seq[RecapQuery]): Future[Seq[CandidateTweetsResult]] = {
    source.hydrate(queries)
  }
}
