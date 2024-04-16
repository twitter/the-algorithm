package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.gener c_summary_ em

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummaryContext
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Gener cSummaryContextMarshaller @ nject() (
  r chTextMarshaller: R chTextMarshaller,
  hor zon conMarshaller: Hor zon conMarshaller) {

  def apply(gener cSummary emContext: Gener cSummaryContext): urt.Gener cSummaryContext =
    urt.Gener cSummaryContext(
      text = r chTextMarshaller(gener cSummary emContext.text),
       con = gener cSummary emContext. con.map(hor zon conMarshaller(_))
    )
}
