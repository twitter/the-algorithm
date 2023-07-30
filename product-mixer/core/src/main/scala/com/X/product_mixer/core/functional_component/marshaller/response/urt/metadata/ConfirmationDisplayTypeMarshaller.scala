package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ConfirmationDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Inline
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfirmationDisplayTypeMarshaller @Inject() () {

  def apply(confirmationDisplayType: ConfirmationDisplayType): urt.ConfirmationDisplayType =
    confirmationDisplayType match {
      case Inline => urt.ConfirmationDisplayType.Inline
      case BottomSheet => urt.ConfirmationDisplayType.BottomSheet
    }
}
