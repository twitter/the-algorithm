package com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ClassicNoDivider
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ContextEmphasis
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeaderDisplayType
import com.X.timelines.render.{thriftscala => urt}
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
