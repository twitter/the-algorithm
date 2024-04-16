package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.rtf.safety_level

 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.Conversat onFocalT etSafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.Conversat on njectedT etSafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.Conversat onReplySafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.SafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.T  l neFocalT etSafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.T  l neHo PromotedHydrat onSafetyLevel
 mport com.tw ter.spam.rtf.{thr ftscala => thr ft}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class SafetyLevelMarshaller @ nject() () {

  def apply(safetyLevel: SafetyLevel): thr ft.SafetyLevel = safetyLevel match {
    case Conversat onFocalT etSafetyLevel => thr ft.SafetyLevel.Conversat onFocalT et
    case Conversat onReplySafetyLevel => thr ft.SafetyLevel.Conversat onReply
    case Conversat on njectedT etSafetyLevel => thr ft.SafetyLevel.Conversat on njectedT et
    case T  l neFocalT etSafetyLevel => thr ft.SafetyLevel.T  l neFocalT et
    case T  l neHo PromotedHydrat onSafetyLevel =>
      thr ft.SafetyLevel.T  l neHo PromotedHydrat on
  }
}
