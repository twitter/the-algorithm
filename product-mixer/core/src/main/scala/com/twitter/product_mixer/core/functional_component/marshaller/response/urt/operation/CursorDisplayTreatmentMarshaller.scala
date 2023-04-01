package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorDisplayTreatment
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CursorDisplayTreatmentMarshaller @Inject() () {

  def apply(treatment: CursorDisplayTreatment): urt.CursorDisplayTreatment =
    urt.CursorDisplayTreatment(
      actionText = treatment.actionText,
      labelText = treatment.labelText
    )
}
