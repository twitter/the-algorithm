package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class ShowAlertColorConfigurationMarshaller @Inject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(colorConfiguration: ShowAlertColorConfiguration): urt.ShowAlertColorConfiguration =
    urt.ShowAlertColorConfiguration(
      background = rosettaColorMarshaller(colorConfiguration.background),
      tetwittert = rosettaColorMarshaller(colorConfiguration.tetwittert),
      border = colorConfiguration.border.map(rosettaColorMarshaller(_)),
    )
}
