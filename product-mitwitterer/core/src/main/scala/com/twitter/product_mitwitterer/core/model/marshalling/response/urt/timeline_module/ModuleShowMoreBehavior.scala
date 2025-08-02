package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module

sealed trait ModuleShowMoreBehavior

case class ModuleShowMoreBehaviorRevealByCount(
  initialItemsCount: Int,
  showMoreItemsCount: Int)
    etwittertends ModuleShowMoreBehavior
