package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage. nl nePromptCand dateUrt emStr ngCenterBu lder. nl nePromptCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date. nl nePromptCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. nl nePrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssagePrompt em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  nl nePromptCand dateUrt emStr ngCenterBu lder {
  val  nl nePromptCl entEvent nfoEle nt: Str ng = " ssage"
}

case class  nl nePromptCand dateUrt emStr ngCenterBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query,  nl nePromptCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query,  nl nePromptCand date]
  ] = None,
   aderTextBu lder: BaseStr[Query,  nl nePromptCand date],
  bodyTextBu lder: Opt on[BaseStr[Query,  nl nePromptCand date]] = None,
   aderR chTextBu lder: Opt on[BaseR chTextBu lder[Query,  nl nePromptCand date]] = None,
  bodyR chTextBu lder: Opt on[BaseR chTextBu lder[Query,  nl nePromptCand date]] = None,
  pr mary ssageTextAct onBu lder: Opt on[
     ssageTextAct onBu lder[Query,  nl nePromptCand date]
  ] = None,
  secondary ssageTextAct onBu lder: Opt on[
     ssageTextAct onBu lder[Query,  nl nePromptCand date]
  ] = None,
  soc alContextBu lder: Opt on[BaseSoc alContextBu lder[Query,  nl nePromptCand date]] = None,
  userFaceP leBu lder: Opt on[
    UserFaceP leBu lder
  ] = None)
    extends Cand dateUrtEntryBu lder[Query,  nl nePromptCand date,  ssagePrompt em] {

  overr de def apply(
    query: Query,
     nl nePromptCand date:  nl nePromptCand date,
    cand dateFeatures: FeatureMap
  ):  ssagePrompt em =
     ssagePrompt em(
       d =  nl nePromptCand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
         nl nePromptCand date,
        cand dateFeatures,
        So ( nl nePromptCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query,  nl nePromptCand date, cand dateFeatures)),
       sP nned = None,
      content =  nl nePrompt ssageContent(
         aderText =  aderTextBu lder.apply(query,  nl nePromptCand date, cand dateFeatures),
        bodyText = bodyTextBu lder.map(_.apply(query,  nl nePromptCand date, cand dateFeatures)),
        pr maryButtonAct on = pr mary ssageTextAct onBu lder.map(
          _.apply(query,  nl nePromptCand date, cand dateFeatures)),
        secondaryButtonAct on = secondary ssageTextAct onBu lder.map(
          _.apply(query,  nl nePromptCand date, cand dateFeatures)),
         aderR chText =
           aderR chTextBu lder.map(_.apply(query,  nl nePromptCand date, cand dateFeatures)),
        bodyR chText =
          bodyR chTextBu lder.map(_.apply(query,  nl nePromptCand date, cand dateFeatures)),
        soc alContext =
          soc alContextBu lder.flatMap(_.apply(query,  nl nePromptCand date, cand dateFeatures)),
        userFacep le = userFaceP leBu lder.map(_.apply())
      ),
       mpress onCallbacks = None
    )
}
