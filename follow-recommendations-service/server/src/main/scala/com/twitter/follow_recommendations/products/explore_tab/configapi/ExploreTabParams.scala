package com.tw ter.follow_recom ndat ons.products.explore_tab.conf gap 

 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .FSParam

object ExploreTabParams {
  object EnableProduct extends Param[Boolean](false)
  object EnableProductForSoftUser
      extends FSParam[Boolean]("explore_tab_enable_product_for_soft_user", false)
}
