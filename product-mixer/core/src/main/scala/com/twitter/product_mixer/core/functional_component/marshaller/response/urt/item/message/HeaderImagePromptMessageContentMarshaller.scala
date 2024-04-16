package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ader magePrompt ssageContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ader magePrompt ssageContentMarshaller @ nject() (
   ssage mageMarshaller:  ssage mageMarshaller,
   ssageTextAct onMarshaller:  ssageTextAct onMarshaller,
   ssageAct onMarshaller:  ssageAct onMarshaller,
  r chTextMarshaller: R chTextMarshaller) {

  def apply(
     ader magePrompt ssageContent:  ader magePrompt ssageContent
  ): urt. ssageContent =
    urt. ssageContent. ader magePrompt(
      urt. ader magePrompt(
         ader mage =  ssage mageMarshaller( ader magePrompt ssageContent. ader mage),
         aderText =  ader magePrompt ssageContent. aderText,
        bodyText =  ader magePrompt ssageContent.bodyText,
        pr maryButtonAct on =
           ader magePrompt ssageContent.pr maryButtonAct on.map( ssageTextAct onMarshaller(_)),
        secondaryButtonAct on =
           ader magePrompt ssageContent.secondaryButtonAct on.map( ssageTextAct onMarshaller(_)),
        act on =  ader magePrompt ssageContent.act on.map( ssageAct onMarshaller(_)),
         aderR chText =  ader magePrompt ssageContent. aderR chText.map(r chTextMarshaller(_)),
        bodyR chText =  ader magePrompt ssageContent.bodyR chText.map(r chTextMarshaller(_))
      )
    )
}
