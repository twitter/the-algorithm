package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ModuleShowMoreBehaviorRevealByCountMarshaller @Inject() () {

  def apply(
    moduleShowMoreBehaviorRevealByCount: ModuleShowMoreBehaviorRevealByCount
  ): urt.ModuleShowMoreBehavior =
    urt.ModuleShowMoreBehavior.RevealByCount(
      urt.ModuleShowMoreBehaviorRevealByCount(
        initialItemsCount = moduleShowMoreBehaviorRevealByCount.initialItemsCount,
        showMoreItemsCount = moduleShowMoreBehaviorRevealByCount.showMoreItemsCount
      )
    )
}
