package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TimelinesScoreInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelinesScoreInfoMarshaller @Inject() () {

  def apply(timelinesScoreInfo: TimelinesScoreInfo): urt.TimelinesScoreInfo =
    urt.TimelinesScoreInfo(score = timelinesScoreInfo.score)
}
