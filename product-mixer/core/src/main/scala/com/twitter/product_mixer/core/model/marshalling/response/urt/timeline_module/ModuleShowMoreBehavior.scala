package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

sealed trait ModuleShowMoreBehavior

case class ModuleShowMoreBehaviorRevealByCount(
  initialItemsCount: Int,
  showMoreItemsCount: Int)
    extends ModuleShowMoreBehavior
