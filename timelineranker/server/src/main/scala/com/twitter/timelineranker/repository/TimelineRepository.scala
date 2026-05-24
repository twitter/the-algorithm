package com.twitter.timelineranker.repository

import com.twitter.timelineranker.model.Timeline
import com.twitter.timelineranker.model.TimelineQuery
import com.twitter.util.Future

trait TimelineRepository {
  def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]]
  def get(query: TimelineQuery): Future[Timeline] = get(Seq(query)).head
}

class EmptyTimelineRepository extends TimelineRepository {
  def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]] = {
    queries.map(q => Future.value(Timeline.empty(q.id)))
  }
}
