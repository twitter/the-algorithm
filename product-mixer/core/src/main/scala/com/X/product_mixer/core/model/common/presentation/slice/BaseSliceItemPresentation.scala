package com.X.product_mixer.core.model.common.presentation.slice

import com.X.product_mixer.core.model.common.presentation.ItemPresentation
import com.X.product_mixer.core.model.marshalling.response.slice.SliceItem

trait BaseSliceItemPresentation extends ItemPresentation {
  def sliceItem: SliceItem
}
