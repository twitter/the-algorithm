package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.operation

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorDisplayTreatment
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CursorDisplayTreatmentMarshaller @Inject() () {

  def apply(treatment: CursorDisplayTreatment): urt.CursorDisplayTreatment =
    urt.CursorDisplayTreatment(
      actionTetwittert = treatment.actionTetwittert,
      labelTetwittert = treatment.labelTetwittert
    )
}
