package com.X.product_mixer.component_library.model.presentation.urt

import com.X.product_mixer.core.model.common.presentation.urt.BaseUrtItemPresentation
import com.X.product_mixer.core.model.common.presentation.urt.BaseUrtModulePresentation
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem

case class UrtItemPresentation(
  override val timelineItem: TimelineItem,
  override val modulePresentation: Option[BaseUrtModulePresentation] = None)
    extends BaseUrtItemPresentation
