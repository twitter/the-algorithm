package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. nl nePrompt ssageContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  nl nePrompt ssageContentMarshaller @ nject() (
   ssageTextAct onMarshaller:  ssageTextAct onMarshaller,
  r chTextMarshaller: R chTextMarshaller,
  soc alContextMarshaller: Soc alContextMarshaller,
  userFacep leMarshaller: UserFacep leMarshaller) {

  def apply( nl nePrompt ssageContent:  nl nePrompt ssageContent): urt. ssageContent =
    urt. ssageContent. nl nePrompt(
      urt. nl nePrompt(
         aderText =  nl nePrompt ssageContent. aderText,
        bodyText =  nl nePrompt ssageContent.bodyText,
        pr maryButtonAct on =
           nl nePrompt ssageContent.pr maryButtonAct on.map( ssageTextAct onMarshaller(_)),
        secondaryButtonAct on =
           nl nePrompt ssageContent.secondaryButtonAct on.map( ssageTextAct onMarshaller(_)),
         aderR chText =  nl nePrompt ssageContent. aderR chText.map(r chTextMarshaller(_)),
        bodyR chText =  nl nePrompt ssageContent.bodyR chText.map(r chTextMarshaller(_)),
        soc alContext =  nl nePrompt ssageContent.soc alContext.map(soc alContextMarshaller(_)),
        userFacep le =  nl nePrompt ssageContent.userFacep le.map(userFacep leMarshaller(_))
      )
    )
}
