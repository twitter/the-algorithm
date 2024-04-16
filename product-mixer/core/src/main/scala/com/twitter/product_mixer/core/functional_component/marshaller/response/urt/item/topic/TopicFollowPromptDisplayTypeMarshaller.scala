package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c. ncent veFocusTop cFollowPromptD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFocusTop cFollowPromptD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFollowPromptD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cFollowPromptD splayTypeMarshaller @ nject() () {

  def apply(
    top cFollowPromptD splayType: Top cFollowPromptD splayType
  ): urt.Top cFollowPromptD splayType =
    top cFollowPromptD splayType match {
      case  ncent veFocusTop cFollowPromptD splayType =>
        urt.Top cFollowPromptD splayType. ncent veFocus
      case Top cFocusTop cFollowPromptD splayType => urt.Top cFollowPromptD splayType.Top cFocus
    }
}
