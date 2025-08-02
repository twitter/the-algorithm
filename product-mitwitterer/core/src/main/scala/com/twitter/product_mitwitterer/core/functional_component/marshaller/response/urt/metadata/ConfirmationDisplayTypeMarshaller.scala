package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ConfirmationDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Inline
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ConfirmationDisplayTypeMarshaller @Inject() () {

  def apply(confirmationDisplayType: ConfirmationDisplayType): urt.ConfirmationDisplayType =
    confirmationDisplayType match {
      case Inline => urt.ConfirmationDisplayType.Inline
      case BottomSheet => urt.ConfirmationDisplayType.BottomSheet
    }
}
