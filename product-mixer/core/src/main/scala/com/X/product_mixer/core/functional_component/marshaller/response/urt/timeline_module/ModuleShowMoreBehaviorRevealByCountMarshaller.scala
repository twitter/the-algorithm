package com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

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
