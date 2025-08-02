package com.twitter.product_mitwitterer.core.model.common.presentation.slice

import com.twitter.product_mitwitterer.core.model.common.presentation.ItemPresentation
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.SliceItem

trait BaseSliceItemPresentation etwittertends ItemPresentation {
  def sliceItem: SliceItem
}
