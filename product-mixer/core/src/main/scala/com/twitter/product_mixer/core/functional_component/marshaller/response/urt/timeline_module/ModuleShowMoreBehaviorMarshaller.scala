package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleShowMoreBehaviorMarshaller @Inject() (
  moduleShowMoreBehaviorRevealByCountMarshaller: ModuleShowMoreBehaviorRevealByCountMarshaller) {

  def apply(
    moduleShowMoreBehavior: ModuleShowMoreBehavior
  ): urt.ModuleShowMoreBehavior = moduleShowMoreBehavior match {
    case moduleShowMoreBehaviorRevealByCount: ModuleShowMoreBehaviorRevealByCount =>
      moduleShowMoreBehaviorRevealByCountMarshaller(moduleShowMoreBehaviorRevealByCount)
  }
}
