package com.X.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.X.product_mixer.core.model.marshalling.response.urt.alert.Top
import com.X.product_mixer.core.model.marshalling.response.urt.alert.Bottom
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

@Singleton
class ShowAlertDisplayLocationMarshaller @Inject() () {

  def apply(alertDisplayLocation: ShowAlertDisplayLocation): urt.ShowAlertDisplayLocation =
    alertDisplayLocation match {
      case Top => urt.ShowAlertDisplayLocation.Top
      case Bottom => urt.ShowAlertDisplayLocation.Bottom
    }

}
