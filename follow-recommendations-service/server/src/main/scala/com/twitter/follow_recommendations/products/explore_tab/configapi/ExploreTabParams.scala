package com.twitter.follow_recommendations.products.explore_tab.configapi

import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.FSParam

object ExploreTabParams {
  object EnableProduct extends Param[Boolean](false)
  object EnableProductForSoftUser
      extends FSParam[Boolean]("explore_tab_enable_product_for_soft_user", false)
}
