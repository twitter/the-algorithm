package com.X.product_mixer.component_library.model.presentation.urt

import com.X.product_mixer.core.model.common.presentation.urt.BaseUrtModulePresentation
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineModule

final case class UrtModulePresentation(
  override val timelineModule: TimelineModule)
    extends BaseUrtModulePresentation
