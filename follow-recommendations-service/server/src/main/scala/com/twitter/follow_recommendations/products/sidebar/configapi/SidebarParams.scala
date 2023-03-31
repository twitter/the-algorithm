package com.twitter.follow_recommendations.products.sidebar.configapi

import com.twitter.timelines.configapi.Param

object SidebarParams {
  object EnableProduct extends Param[Boolean](false)

  object DefaultMaxResults extends Param[Int](20)
}
