package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

sealed tra   ssageContent

case class  nl nePrompt ssageContent(
   aderText: Str ng,
  bodyText: Opt on[Str ng],
  pr maryButtonAct on: Opt on[ ssageTextAct on],
  secondaryButtonAct on: Opt on[ ssageTextAct on],
   aderR chText: Opt on[R chText],
  bodyR chText: Opt on[R chText],
  soc alContext: Opt on[Soc alContext],
  userFacep le: Opt on[UserFacep le])
    extends  ssageContent

case class  ader magePrompt ssageContent(
   ader mage:  ssage mage,
   aderText: Opt on[Str ng],
  bodyText: Opt on[Str ng],
  pr maryButtonAct on: Opt on[ ssageTextAct on],
  secondaryButtonAct on: Opt on[ ssageTextAct on],
  act on: Opt on[ ssageAct on],
   aderR chText: Opt on[R chText],
  bodyR chText: Opt on[R chText])
    extends  ssageContent

case class CompactPrompt ssageContent(
   aderText: Str ng,
  bodyText: Opt on[Str ng],
  pr maryButtonAct on: Opt on[ ssageTextAct on],
  secondaryButtonAct on: Opt on[ ssageTextAct on],
  act on: Opt on[ ssageAct on],
   aderR chText: Opt on[R chText],
  bodyR chText: Opt on[R chText])
    extends  ssageContent
