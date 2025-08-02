package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.forward_pivot

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.GetTheLatest
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.GovernmentRequested
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.Misleading
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.SoftInterventionDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.StayInformed
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SoftInterventionDisplayTypeMarshaller @Inject() () {

  def apply(
    softInterventionDisplayType: SoftInterventionDisplayType
  ): urt.SoftInterventionDisplayType =
    softInterventionDisplayType match {
      case GetTheLatest => urt.SoftInterventionDisplayType.GetTheLatest
      case StayInformed => urt.SoftInterventionDisplayType.StayInformed
      case Misleading => urt.SoftInterventionDisplayType.Misleading
      case GovernmentRequested => urt.SoftInterventionDisplayType.GovernmentRequested
    }
}
