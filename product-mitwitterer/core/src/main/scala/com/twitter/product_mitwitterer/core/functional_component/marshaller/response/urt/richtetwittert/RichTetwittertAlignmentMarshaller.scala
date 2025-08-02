package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Center
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Natural
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertAlignment
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RichTetwittertAlignmentMarshaller @Inject() () {

  def apply(alignment: RichTetwittertAlignment): urt.RichTetwittertAlignment = alignment match {
    case Natural => urt.RichTetwittertAlignment.Natural
    case Center => urt.RichTetwittertAlignment.Center
  }
}
