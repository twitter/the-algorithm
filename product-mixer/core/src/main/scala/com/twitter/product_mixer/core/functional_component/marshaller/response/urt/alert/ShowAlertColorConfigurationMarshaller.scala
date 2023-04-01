package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class ShowAlertColorConfigurationMarshaller @Inject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(colorConfiguration: ShowAlertColorConfiguration): urt.ShowAlertColorConfiguration =
    urt.ShowAlertColorConfiguration(
      background = rosettaColorMarshaller(colorConfiguration.background),
      text = rosettaColorMarshaller(colorConfiguration.text),
      border = colorConfiguration.border.map(rosettaColorMarshaller(_)),
    )
}
