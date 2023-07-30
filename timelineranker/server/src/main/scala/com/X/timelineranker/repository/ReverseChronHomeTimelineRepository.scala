package com.X.timelineranker.repository

import com.X.timelineranker.model.ReverseChronTimelineQuery
import com.X.timelineranker.model.Timeline
import com.X.timelineranker.parameters.revchron.ReverseChronTimelineQueryContextBuilder
import com.X.timelineranker.source.ReverseChronHomeTimelineSource
import com.X.util.Future

/**
 * A repository of reverse-chron home timelines.
 *
 * It does not cache any results therefore forwards all calls to the underlying source.
 */
class ReverseChronHomeTimelineRepository(
  source: ReverseChronHomeTimelineSource,
  contextBuilder: ReverseChronTimelineQueryContextBuilder) {
  def get(query: ReverseChronTimelineQuery): Future[Timeline] = {
    contextBuilder(query).flatMap(source.get)
  }
}
