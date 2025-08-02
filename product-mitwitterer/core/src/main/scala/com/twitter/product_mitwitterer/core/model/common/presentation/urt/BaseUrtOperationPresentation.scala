package com.twitter.product_mitwitterer.core.model.common.presentation.urt

import com.twitter.product_mitwitterer.core.model.common.presentation.ItemPresentation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineOperation

trait BaseUrtOperationPresentation etwittertends ItemPresentation {

  def timelineOperation: TimelineOperation
}
