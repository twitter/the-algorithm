package com.twitter.timelineranker.repository

import com.twitter.timelineranker.model.Timeline
import com.twitter.timelineranker.model.TimelineQuery
import com.twitter.util.Future

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
