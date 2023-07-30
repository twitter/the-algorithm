package com.X.timelineranker.repository

import com.X.timelineranker.model.Timeline
import com.X.timelineranker.model.TimelineQuery
import com.X.util.Future

/**
 * A repository of ranked home timelines.
 */
class RankedHomeTimelineRepository extends TimelineRepository {
  def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]] = {
    queries.map { _ =>
      Future.exception(new UnsupportedOperationException("ranked timelines are not yet supported."))
    }
  }
}
