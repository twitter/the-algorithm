package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.forward_pivot

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.CommunityNotes
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.ForwardPivotDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.LiveEvent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.SoftIntervention
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForwardPivotDisplayTypeMarshaller @Inject() () {

  def apply(forwardPivotDisplayType: ForwardPivotDisplayType): urt.ForwardPivotDisplayType =
    forwardPivotDisplayType match {
      case LiveEvent => urt.ForwardPivotDisplayType.LiveEvent
      case SoftIntervention => urt.ForwardPivotDisplayType.SoftIntervention
      case CommunityNotes => urt.ForwardPivotDisplayType.CommunityNotes
    }
}
