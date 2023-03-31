package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.model.marshalling.response.urp.PageBody
import com.twitter.product_mixer.core.model.marshalling.response.urp.SegmentedTimelinesPageBody
import com.twitter.product_mixer.core.model.marshalling.response.urp.TimelineKeyPageBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageBodyMarshaller @Inject() (
  timelineKeyMarshaller: TimelineKeyMarshaller,
  segmentedTimelinesMarshaller: SegmentedTimelinesMarshaller) {

  def apply(pageBody: PageBody): urp.PageBody = pageBody match {
    case pageBody: TimelineKeyPageBody =>
      urp.PageBody.Timeline(timelineKeyMarshaller(pageBody.timeline))
    case pageBody: SegmentedTimelinesPageBody =>
      urp.PageBody.SegmentedTimelines(segmentedTimelinesMarshaller(pageBody))
  }
}
