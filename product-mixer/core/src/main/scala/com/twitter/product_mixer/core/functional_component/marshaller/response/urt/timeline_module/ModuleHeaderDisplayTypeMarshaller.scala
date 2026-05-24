package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ClassicNoDivider
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ContextEmphasis
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeaderDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleHeaderDisplayTypeMarshaller @Inject() () {

  def apply(displayType: ModuleHeaderDisplayType): urt.ModuleHeaderDisplayType =
    displayType match {
      case Classic => urt.ModuleHeaderDisplayType.Classic
      case ContextEmphasis => urt.ModuleHeaderDisplayType.ContextEmphasis
      case ClassicNoDivider => urt.ModuleHeaderDisplayType.ClassicNoDivider
    }

}
