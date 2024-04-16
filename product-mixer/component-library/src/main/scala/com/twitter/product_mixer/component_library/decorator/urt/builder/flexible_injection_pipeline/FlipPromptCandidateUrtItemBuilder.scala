package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne

 mport com.tw ter.onboard ng. nject ons.thr ftscala. nject on
 mport com.tw ter.onboard ng. nject ons.{thr ftscala => onboard ngthr ft}
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne.Onboard ng nject onConvers ons._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BasePromptCand date
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPromptCarouselT leFeature
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPrompt nject onsFeature
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPromptOffset nModuleFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverFullCoverD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverHalfCoverD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCoverContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCoverContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ader magePrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. nl nePrompt ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssagePrompt em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt.Prompt em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Fl pPromptCand dateUrt emBu lder {
  val Fl pPromptCl entEvent nfoEle nt: Str ng = "fl p-prompt- ssage"
}

case class Fl pPromptCand dateUrt emBu lder[-Query <: P pel neQuery]()
    extends Cand dateUrtEntryBu lder[Query, BasePromptCand date[Any], T  l ne em] {

  overr de def apply(
    query: Query,
    promptCand date: BasePromptCand date[Any],
    cand dateFeatures: FeatureMap
  ): T  l ne em = {
    val  nject on = cand dateFeatures.get(Fl pPrompt nject onsFeature)

     nject on match {
      case onboard ngthr ft. nject on. nl nePrompt(cand date) =>
         ssagePrompt em(
           d = promptCand date. d.toStr ng,
          sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
          cl entEvent nfo = bu ldCl entEvent nfo( nject on),
          feedbackAct on nfo = cand date.feedback nfo.map(convertFeedback nfo),
           sP nned = So (cand date. sP nnedEntry),
          content = get nl nePrompt ssageContent(cand date),
           mpress onCallbacks = cand date. mpress onCallbacks.map(_.map(convertCallback).toL st)
        )
      case onboard ngthr ft. nject on.FullCover(cand date) =>
        FullCover(
           d = promptCand date. d.toStr ng,
          // Note that sort  ndex  s not used for Covers, as t y are not T  l neEntry and do not have entry d
          sort ndex = None,
          cl entEvent nfo =
            So (Onboard ng nject onConvers ons.convertCl entEvent nfo(cand date.cl entEvent nfo)),
          content = getFullCoverContent(cand date)
        )
      case onboard ngthr ft. nject on.HalfCover(cand date) =>
        HalfCover(
           d = promptCand date. d.toStr ng,
          // Note that sort  ndex  s not used for Covers, as t y are not T  l neEntry and do not have entry d
          sort ndex = None,
          cl entEvent nfo =
            So (Onboard ng nject onConvers ons.convertCl entEvent nfo(cand date.cl entEvent nfo)),
          content = getHalfCoverContent(cand date)
        )
      case  nject on.T lesCarousel(_) =>
        val offset nModuleOpt on =
          cand dateFeatures.get(Fl pPromptOffset nModuleFeature)
        val offset nModule =
          offset nModuleOpt on.getOrElse(throw Fl pPromptOffset nModuleM ss ng)
        val t leOpt on =
          cand dateFeatures.get(Fl pPromptCarouselT leFeature)
        val t le = t leOpt on.getOrElse(throw Fl pPromptCarouselT leM ss ng)
        T lesCarouselConvers ons.convertT le(t le, offset nModule)
      case onboard ngthr ft. nject on.RelevancePrompt(cand date) =>
        Prompt em(
           d = promptCand date. d.toStr ng,
          sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
          cl entEvent nfo = bu ldCl entEvent nfo( nject on),
          content = RelevancePromptConvers ons.convertContent(cand date),
           mpress onCallbacks = So (cand date. mpress onCallbacks.map(convertCallback).toL st)
        )
      case _ => throw new UnsupportedFl pPromptExcept on( nject on)
    }
  }

  pr vate def get nl nePrompt ssageContent(
    cand date: onboard ngthr ft. nl nePrompt
  ):  ssageContent = {
    cand date. mage match {
      case So ( mage) =>
         ader magePrompt ssageContent(
           ader mage = convert mage( mage),
           aderText = So (cand date. aderText.text),
          bodyText = cand date.bodyText.map(_.text),
          pr maryButtonAct on = cand date.pr maryAct on.map(convertButtonAct on),
          secondaryButtonAct on = cand date.secondaryAct on.map(convertButtonAct on),
           aderR chText = So (convertR chText(cand date. aderText)),
          bodyR chText = cand date.bodyText.map(convertR chText),
          act on =
            None
        )
      case None =>
         nl nePrompt ssageContent(
           aderText = cand date. aderText.text,
          bodyText = cand date.bodyText.map(_.text),
          pr maryButtonAct on = cand date.pr maryAct on.map(convertButtonAct on),
          secondaryButtonAct on = cand date.secondaryAct on.map(convertButtonAct on),
           aderR chText = So (convertR chText(cand date. aderText)),
          bodyR chText = cand date.bodyText.map(convertR chText),
          soc alContext = cand date.soc alContext.map(convertSoc alContext),
          userFacep le = cand date.promptUserFacep le.map(convertUserFaceP le)
        )
    }
  }

  pr vate def getFullCoverContent(
    cand date: onboard ngthr ft.FullCover
  ): FullCoverContent =
    FullCoverContent(
      d splayType = CoverFullCoverD splayType,
      pr maryText = convertR chText(cand date.pr maryText),
      pr maryCoverCta = convertCoverCta(cand date.pr maryButtonAct on),
      secondaryCoverCta = cand date.secondaryButtonAct on.map(convertCoverCta),
      secondaryText = cand date.secondaryText.map(convertR chText),
       mageVar ant = cand date. mage.map( mg => convert mageVar ant( mg. mage)),
      deta ls = cand date.deta lText.map(convertR chText),
      d sm ss nfo = cand date.d sm ss nfo.map(convertD sm ss nfo),
       mageD splayType = cand date. mage.map( mg => convert mageD splayType( mg. mageD splayType)),
       mpress onCallbacks = cand date. mpress onCallbacks.map(_.map(convertCallback).toL st)
    )

  pr vate def getHalfCoverContent(
    cand date: onboard ngthr ft.HalfCover
  ): HalfCoverContent =
    HalfCoverContent(
      d splayType =
        cand date.d splayType.map(convertHalfCoverD splayType).getOrElse(CoverHalfCoverD splayType),
      pr maryText = convertR chText(cand date.pr maryText),
      pr maryCoverCta = convertCoverCta(cand date.pr maryButtonAct on),
      secondaryCoverCta = cand date.secondaryButtonAct on.map(convertCoverCta),
      secondaryText = cand date.secondaryText.map(convertR chText),
      cover mage = cand date. mage.map(convertCover mage),
      d sm ss ble = cand date.d sm ss ble,
      d sm ss nfo = cand date.d sm ss nfo.map(convertD sm ss nfo),
       mpress onCallbacks = cand date. mpress onCallbacks.map(_.map(convertCallback).toL st)
    )

  pr vate def bu ldCl entEvent nfo(
     nject on:  nject on
  ): Opt on[Cl entEvent nfo] = {
     nject on match {
      //To keep par y bet en T  l neM xer and Product M xer,  nl ne prompt sw c s sets t  prompt product  dent f er as t  component and no ele nt. Also  ncludes cl entEventDeta ls
      case onboard ngthr ft. nject on. nl nePrompt(cand date) =>
        val cl entEventDeta ls: Cl entEventDeta ls =
          Cl entEventDeta ls(
            conversat onDeta ls = None,
            t  l nesDeta ls = So (T  l nesDeta ls( nject onType = So (" ssage"), None, None)),
            art cleDeta ls = None,
            l veEventDeta ls = None,
            com rceDeta ls = None
          )
        So (
          Cl entEvent nfo(
            component = cand date. nject on dent f er,
            ele nt = None,
            deta ls = So (cl entEventDeta ls),
            act on = None,
            ent yToken = None))
      // To keep par y bet en TLM and PM   swap component and ele nts.
      case onboard ngthr ft. nject on.RelevancePrompt(cand date) =>
        So (
          Cl entEvent nfo(
            //  dent f er  s pref xed w h onboard ng per TLM
            component = So ("onboard ng_" + cand date. nject on dent f er),
            ele nt = So ("relevance_prompt"),
            deta ls = None,
            act on = None,
            ent yToken = None
          ))

      case _ => None
    }
  }

}

class UnsupportedFl pPromptExcept on( nject on: onboard ngthr ft. nject on)
    extends UnsupportedOperat onExcept on(
      "Unsupported t  l ne  em " + TransportMarshaller.getS mpleNa ( nject on.getClass))

object Fl pPromptOffset nModuleM ss ng
    extends NoSuchEle ntExcept on(
      "Fl pPromptOffset nModuleFeature must be set for t  T lesCarousel FL P  nject on  n PromptCand dateS ce")

object Fl pPromptCarouselT leM ss ng
    extends NoSuchEle ntExcept on(
      "Fl pPromptCarouselT leFeature must be set for t  T lesCarousel FL P  nject on  n PromptCand dateS ce")
