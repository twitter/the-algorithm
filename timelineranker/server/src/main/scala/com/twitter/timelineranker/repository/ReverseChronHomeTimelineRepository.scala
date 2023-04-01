package com.twitter.timelineranker.repository

import com.twitter.timelineranker.model.ReverseChronTimelineQuery
import com.twitter.timelineranker.model.Timeline
import com.twitter.timelineranker.parameters.revchron.ReverseChronTimelineQueryContextBuilder
import com.twitter.timelineranker.source.ReverseChronHomeTimelineSource
import com.twitter.util.Future

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
