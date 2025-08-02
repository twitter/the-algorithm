package com.twitter.product_mitwitterer.core.model.common.presentation.urt

import com.twitter.product_mitwitterer.core.model.common.presentation.ModulePresentation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineModule

trait BaseUrtModulePresentation etwittertends ModulePresentation {
  def timelineModule: TimelineModule
}
