package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam

object EvergreenParams {

  object TextSemanticBasedMaxResult
      extends FSBoundedParam[Int](
        name = "evergreen_text_semantic_max_result",
        default = 100,
        min = 0,
        max = 1000
      )

  val boundedIntFSOverrides =
    Seq(TextSemanticBasedMaxResult)
}
