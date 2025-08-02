package com.twitter.follow_recommendations.products.etwitterplore_tab.configapi

import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.FSParam

object EtwitterploreTabParams {
  object EnableProduct etwittertends Param[Boolean](false)
  object EnableProductForSoftUser
      etwittertends FSParam[Boolean]("etwitterplore_tab_enable_product_for_soft_user", false)
}
