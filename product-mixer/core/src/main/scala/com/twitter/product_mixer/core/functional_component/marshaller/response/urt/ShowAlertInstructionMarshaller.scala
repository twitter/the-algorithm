package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertColorConfigurationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertDisplayLocationMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertIconDisplayInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertNavigationMetadataMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.alert.ShowAlertTypeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowAlertInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowAlertInstructionMarshaller @Inject() (
  showAlertTypeMarshaller: ShowAlertTypeMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  richTextMarshaller: RichTextMarshaller,
  showAlertIconDisplayInfoMarshaller: ShowAlertIconDisplayInfoMarshaller,
  showAlertColorConfigurationMarshaller: ShowAlertColorConfigurationMarshaller,
  showAlertDisplayLocationMarshaller: ShowAlertDisplayLocationMarshaller,
  showAlertNavigationMetadataMarshaller: ShowAlertNavigationMetadataMarshaller,
) {

  def apply(instruction: ShowAlertInstruction): urt.ShowAlert = urt.ShowAlert(
    alertType = showAlertTypeMarshaller(instruction.showAlert.alertType),
    triggerDelayMs = instruction.showAlert.triggerDelay.map(_.inMillis.toInt),
    displayDurationMs = instruction.showAlert.displayDuration.map(_.inMillis.toInt),
    clientEventInfo = instruction.showAlert.clientEventInfo.map(clientEventInfoMarshaller(_)),
    collapseDelayMs = instruction.showAlert.collapseDelay.map(_.inMillis.toInt),
    userIds = instruction.showAlert.userIds,
    richText = instruction.showAlert.richText.map(richTextMarshaller(_)),
    iconDisplayInfo =
      instruction.showAlert.iconDisplayInfo.map(showAlertIconDisplayInfoMarshaller(_)),
    colorConfig = showAlertColorConfigurationMarshaller(instruction.showAlert.colorConfig),
    displayLocation = showAlertDisplayLocationMarshaller(instruction.showAlert.displayLocation),
    navigationMetadata =
      instruction.showAlert.navigationMetadata.map(showAlertNavigationMetadataMarshaller(_)),
  )
}
