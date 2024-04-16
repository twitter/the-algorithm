package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.CompactPrompt ssageContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CompactPrompt ssageContentMarshaller @ nject() (
   ssageTextAct onMarshaller:  ssageTextAct onMarshaller,
   ssageAct onMarshaller:  ssageAct onMarshaller,
  r chTextMarshaller: R chTextMarshaller) {

  def apply(compactPrompt ssageContent: CompactPrompt ssageContent): urt. ssageContent =
    urt. ssageContent.CompactPrompt(
      urt.CompactPrompt(
         aderText = compactPrompt ssageContent. aderText,
        bodyText = compactPrompt ssageContent.bodyText,
        pr maryButtonAct on =
          compactPrompt ssageContent.pr maryButtonAct on.map( ssageTextAct onMarshaller(_)),
        secondaryButtonAct on =
          compactPrompt ssageContent.secondaryButtonAct on.map( ssageTextAct onMarshaller(_)),
        act on = compactPrompt ssageContent.act on.map( ssageAct onMarshaller(_)),
         aderR chText = compactPrompt ssageContent. aderR chText.map(r chTextMarshaller(_)),
        bodyR chText = compactPrompt ssageContent.bodyR chText.map(r chTextMarshaller(_))
      )
    )
}
