package com.ExTwitter.product_mixer.component_library.model.presentation.slice

import com.ExTwitter.product_mixer.core.model.common.presentation.slice.BaseSliceItemPresentation
import com.ExTwitter.product_mixer.core.model.marshalling.response.slice.SliceItem

case class SliceItemPresentation(override val sliceItem: SliceItem)
    extends BaseSliceItemPresentation
