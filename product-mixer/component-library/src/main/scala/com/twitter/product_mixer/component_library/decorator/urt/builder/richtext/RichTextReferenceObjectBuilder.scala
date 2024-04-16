package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.ReferenceObject
 mport com.tw ter.tw tertext.Extractor

tra  R chTextReferenceObjectBu lder {
  def apply(ent y: Extractor.Ent y): Opt on[ReferenceObject]
}
