package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.label

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.label._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class LabelDisplayTypeMarshaller @Inject() () {

  def apply(labelDisplayType: LabelDisplayType): urt.LabelDisplayType = labelDisplayType match {
    case InlineHeaderLabelDisplayType => urt.LabelDisplayType.InlineHeader
    case OtherRepliesSectionHeaderLabelDisplayType => urt.LabelDisplayType.OtherRepliesSectionHeader
  }
}
