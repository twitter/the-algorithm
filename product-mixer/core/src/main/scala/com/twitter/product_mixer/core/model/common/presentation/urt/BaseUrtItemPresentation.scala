package com.twitter.product_mixer.core.model.common.presentation.urt

import com.twitter.product_mixer.core.model.common.presentation.ItemPresentation
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

trait BaseUrtItemPresentation extends ItemPresentation {

  def timelineItem: TimelineItem
}
