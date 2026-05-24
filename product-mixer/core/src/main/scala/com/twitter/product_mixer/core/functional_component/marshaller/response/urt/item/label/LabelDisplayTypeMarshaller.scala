package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.label

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.label._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabelDisplayTypeMarshaller @Inject() () {

  def apply(labelDisplayType: LabelDisplayType): urt.LabelDisplayType = labelDisplayType match {
    case InlineHeaderLabelDisplayType => urt.LabelDisplayType.InlineHeader
    case OtherRepliesSectionHeaderLabelDisplayType => urt.LabelDisplayType.OtherRepliesSectionHeader
  }
}
