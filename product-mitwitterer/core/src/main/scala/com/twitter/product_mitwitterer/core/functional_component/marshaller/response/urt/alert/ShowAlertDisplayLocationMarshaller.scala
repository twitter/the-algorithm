package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.Top
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.Bottom
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class ShowAlertDisplayLocationMarshaller @Inject() () {

  def apply(alertDisplayLocation: ShowAlertDisplayLocation): urt.ShowAlertDisplayLocation =
    alertDisplayLocation match {
      case Top => urt.ShowAlertDisplayLocation.Top
      case Bottom => urt.ShowAlertDisplayLocation.Bottom
    }

}
