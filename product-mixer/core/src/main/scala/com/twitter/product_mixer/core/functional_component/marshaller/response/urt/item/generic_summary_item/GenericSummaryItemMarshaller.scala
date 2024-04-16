package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.gener c_summary_ em

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a. d aMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted.Promoted tadataMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummary em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Gener cSummary emMarshaller @ nject() (
  gener cSummaryD splayTypeMarshaller: Gener cSummaryD splayTypeMarshaller,
  gener cSummaryContextMarshaller: Gener cSummaryContextMarshaller,
  gener cSummaryAct onMarshaller: Gener cSummaryAct onMarshaller,
   d aMarshaller:  d aMarshaller,
  promoted tadataMarshaller: Promoted tadataMarshaller,
  r chTextMarshaller: R chTextMarshaller) {

  def apply(gener cSummary em: Gener cSummary em): urt.T  l ne emContent =
    urt.T  l ne emContent.Gener cSummary(
      urt.Gener cSummary(
         adl ne = r chTextMarshaller(gener cSummary em. adl ne),
        d splayType = gener cSummaryD splayTypeMarshaller(gener cSummary em.d splayType),
        userAttr but on ds = gener cSummary em.userAttr but on ds,
         d a = gener cSummary em. d a.map( d aMarshaller(_)),
        context = gener cSummary em.context.map(gener cSummaryContextMarshaller(_)),
        t  stamp = gener cSummary em.t  stamp.map(_. nM ll seconds),
        onCl ckAct on = gener cSummary em.onCl ckAct on.map(gener cSummaryAct onMarshaller(_)),
        promoted tadata = gener cSummary em.promoted tadata.map(promoted tadataMarshaller(_))
      )
    )
}
