package com.X.timelineranker.repository

import com.X.timelineranker.model.Timeline
import com.X.timelineranker.model.TimelineQuery
import com.X.util.Future

trait TimelineRepository {
  def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]]
  def get(query: TimelineQuery): Future[Timeline] = get(Seq(query)).head
}

class EmptyTimelineRepository extends TimelineRepository {
  def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]] = {
    queries.map(q => Future.value(Timeline.empty(q.id)))
  }
}
