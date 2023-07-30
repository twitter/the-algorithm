package com.X.product_mixer.core.model.common.presentation.urt

import com.X.product_mixer.core.model.common.presentation.ModulePresentation
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineModule

trait BaseUrtModulePresentation extends ModulePresentation {
  def timelineModule: TimelineModule
}
