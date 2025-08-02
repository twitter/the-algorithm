package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehaviorRevealByCount
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
