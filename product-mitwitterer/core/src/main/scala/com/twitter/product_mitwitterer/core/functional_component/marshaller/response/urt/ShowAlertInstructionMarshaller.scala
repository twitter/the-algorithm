package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert.ShowAlertColorConfigurationMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert.ShowAlertDisplayLocationMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert.ShowAlertIconDisplayInfoMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert.ShowAlertNavigationMetadataMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.alert.ShowAlertTypeMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ShowAlertInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ShowAlertInstructionMarshaller @Inject() (
  showAlertTypeMarshaller: ShowAlertTypeMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller,
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
    richTetwittert = instruction.showAlert.richTetwittert.map(richTetwittertMarshaller(_)),
    iconDisplayInfo =
      instruction.showAlert.iconDisplayInfo.map(showAlertIconDisplayInfoMarshaller(_)),
    colorConfig = showAlertColorConfigurationMarshaller(instruction.showAlert.colorConfig),
    displayLocation = showAlertDisplayLocationMarshaller(instruction.showAlert.displayLocation),
    navigationMetadata =
      instruction.showAlert.navigationMetadata.map(showAlertNavigationMetadataMarshaller(_)),
  )
}
