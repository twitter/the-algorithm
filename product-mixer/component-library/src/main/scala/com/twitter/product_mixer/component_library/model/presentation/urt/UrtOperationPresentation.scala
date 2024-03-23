package com.ExTwitter.product_mixer.component_library.model.presentation.urt

import com.ExTwitter.product_mixer.core.model.common.presentation.urt.BaseUrtOperationPresentation
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.TimelineOperation

final case class UrtOperationPresentation(
  override val timelineOperation: TimelineOperation)
    extends BaseUrtOperationPresentation
