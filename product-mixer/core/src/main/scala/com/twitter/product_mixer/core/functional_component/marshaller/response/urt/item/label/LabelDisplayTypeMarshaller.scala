package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.label

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.label._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class LabelD splayTypeMarshaller @ nject() () {

  def apply(labelD splayType: LabelD splayType): urt.LabelD splayType = labelD splayType match {
    case  nl ne aderLabelD splayType => urt.LabelD splayType. nl ne ader
    case Ot rRepl esSect on aderLabelD splayType => urt.LabelD splayType.Ot rRepl esSect on ader
  }
}
