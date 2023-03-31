package com.twitter.follow_recommendations.products.home_timeline_tweet_recs.configapi

import com.twitter.timelines.configapi.Param

object HomeTimelineTweetRecsParams {
  object EnableProduct extends Param[Boolean](false)
}
