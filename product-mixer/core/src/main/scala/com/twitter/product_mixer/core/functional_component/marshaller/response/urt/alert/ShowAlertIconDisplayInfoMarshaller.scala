package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo

@Singleton
class ShowAlertIconDisplayInfoMarshaller @Inject() (
  showAlertIconMarshaller: ShowAlertIconMarshaller,
  rosettaColorMarshaller: RosettaColorMarshaller,
) {

  def apply(alertIconDisplayInfo: ShowAlertIconDisplayInfo): urt.ShowAlertIconDisplayInfo =
    urt.ShowAlertIconDisplayInfo(
      icon = showAlertIconMarshaller(alertIconDisplayInfo.icon),
      tint = rosettaColorMarshaller(alertIconDisplayInfo.tint),
    )

}
