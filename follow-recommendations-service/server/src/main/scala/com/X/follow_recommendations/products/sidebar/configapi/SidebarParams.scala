package com.X.follow_recommendations.products.sidebar.configapi

import com.X.timelines.configapi.Param

object SidebarParams {
  object EnableProduct extends Param[Boolean](false)

  object DefaultMaxResults extends Param[Int](20)
}
