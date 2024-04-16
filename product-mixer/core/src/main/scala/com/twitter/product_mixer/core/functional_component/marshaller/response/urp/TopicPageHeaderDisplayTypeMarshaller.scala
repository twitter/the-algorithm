package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Bas cTop cPage aderD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Personal zedTop cPage aderD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPage aderD splayType
 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cPage aderD splayTypeMarshaller @ nject() () {

  def apply(
    top cPage aderD splayType: Top cPage aderD splayType
  ): urp.Top cPage aderD splayType = top cPage aderD splayType match {
    case Bas cTop cPage aderD splayType => urp.Top cPage aderD splayType.Bas c
    case Personal zedTop cPage aderD splayType => urp.Top cPage aderD splayType.Personal zed
  }
}
