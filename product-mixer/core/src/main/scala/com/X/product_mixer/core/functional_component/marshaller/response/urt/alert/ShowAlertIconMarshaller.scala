package com.X.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.X.product_mixer.core.model.marshalling.response.urt.alert.DownArrow
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIcon
import com.X.product_mixer.core.model.marshalling.response.urt.alert.UpArrow
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

@Singleton
class ShowAlertIconMarshaller @Inject() () {

  def apply(alertIcon: ShowAlertIcon): urt.ShowAlertIcon = alertIcon match {
    case UpArrow => urt.ShowAlertIcon.UpArrow
    case DownArrow => urt.ShowAlertIcon.DownArrow
  }
}
