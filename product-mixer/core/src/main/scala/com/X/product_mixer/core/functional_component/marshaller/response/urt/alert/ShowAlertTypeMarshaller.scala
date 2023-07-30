package com.X.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.X.product_mixer.core.model.marshalling.response.urt.alert.Navigate
import com.X.product_mixer.core.model.marshalling.response.urt.alert.NewTweets
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowAlertTypeMarshaller @Inject() () {

  def apply(alertType: ShowAlertType): urt.AlertType = alertType match {
    case NewTweets => urt.AlertType.NewTweets
    case Navigate => urt.AlertType.Navigate
  }
}
