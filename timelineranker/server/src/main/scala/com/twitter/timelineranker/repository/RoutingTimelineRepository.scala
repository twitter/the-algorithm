package com.twitter.timelineranker.repository

import com.twitter.timelineranker.model._
import com.twitter.util.Future

class RoutingTimelineRepository(
  reverseChronTimelineRepository: ReverseChronHomeTimelineRepository,
  rankedTimelineRepository: RankedHomeTimelineRepository)
    extends TimelineRepository {

  override def get(query: TimelineQuery): Future[Timeline] = {
    query match {
      case q: ReverseChronTimelineQuery => reverseChronTimelineRepository.get(q)
      case q: RankedTimelineQuery => rankedTimelineRepository.get(q)
      case _ =>
        throw new IllegalArgumentException(
          s"Query types other than RankedTimelineQuery and ReverseChronTimelineQuery are not supported. Found: $query"
        )
    }
  }

  override def get(queries: Seq[TimelineQuery]): Seq[Future[Timeline]] = {
    queries.map(get)
  }
}
