package com.twitter.product_mitwitterer.core.model.common.presentation.urt

import com.twitter.product_mitwitterer.core.model.common.presentation.ItemPresentation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

trait BaseUrtItemPresentation etwittertends ItemPresentation {

  def timelineItem: TimelineItem
}
