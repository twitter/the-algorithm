package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tweet

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TimelinesScoreInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelinesScoreInfoMarshaller @Inject() () {

  def apply(timelinesScoreInfo: TimelinesScoreInfo): urt.TimelinesScoreInfo =
    urt.TimelinesScoreInfo(score = timelinesScoreInfo.score)
}
