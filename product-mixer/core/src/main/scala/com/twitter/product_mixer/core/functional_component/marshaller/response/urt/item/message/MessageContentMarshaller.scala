package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.CompactPrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. nl nePrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ader magePrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssageContentMarshaller @ nject() (
   nl nePrompt ssageContentMarshaller:  nl nePrompt ssageContentMarshaller,
   ader magePrompt ssageContentMarshaller:  ader magePrompt ssageContentMarshaller,
  compactPrompt ssageContentMarshaller: CompactPrompt ssageContentMarshaller) {

  def apply( ssageContent:  ssageContent): urt. ssageContent =  ssageContent match {
    case  nl nePrompt ssageContent:  nl nePrompt ssageContent =>
       nl nePrompt ssageContentMarshaller( nl nePrompt ssageContent)
    case  ader magePrompt ssageContent:  ader magePrompt ssageContent =>
       ader magePrompt ssageContentMarshaller( ader magePrompt ssageContent)
    case compactPrompt ssageContent: CompactPrompt ssageContent =>
      compactPrompt ssageContentMarshaller(compactPrompt ssageContent)
  }
}
