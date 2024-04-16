package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.soc al_context

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}

/**
 * Use t  Bu lder to create Product M xer [[Soc alContext]] objects w n   have a
 * T  l ne Serv ce Thr ft [[Soc alContext]] feature that   want to convert
 */
case class FeatureSoc alContextBu lder(
  soc alContextFeature: Feature[_, Opt on[t.Soc alContext]])
    extends BaseSoc alContextBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {
    cand dateFeatures.getOrElse(soc alContextFeature, None).map {
      case t.Soc alContext.GeneralContext(context) =>
        val contextType = context.contextType match {
          case t.ContextType.L ke => L keGeneralContextType
          case t.ContextType.Follow => FollowGeneralContextType
          case t.ContextType.Mo nt => Mo ntGeneralContextType
          case t.ContextType.Reply => ReplyGeneralContextType
          case t.ContextType.Conversat on => Conversat onGeneralContextType
          case t.ContextType.P n => P nGeneralContextType
          case t.ContextType.TextOnly => TextOnlyGeneralContextType
          case t.ContextType.Facep le => FaceP leGeneralContextType
          case t.ContextType. gaphone =>  gaPhoneGeneralContextType
          case t.ContextType.B rd => B rdGeneralContextType
          case t.ContextType.Feedback => FeedbackGeneralContextType
          case t.ContextType.Top c => Top cGeneralContextType
          case t.ContextType.L st => L stGeneralContextType
          case t.ContextType.Ret et => Ret etGeneralContextType
          case t.ContextType.Locat on => Locat onGeneralContextType
          case t.ContextType.Commun y => Commun yGeneralContextType
          case t.ContextType.SmartBlockExp rat on => SmartblockExp rat onGeneralContextType
          case t.ContextType.Trend ng => Trend ngGeneralContextType
          case t.ContextType.Sparkle => SparkleGeneralContextType
          case t.ContextType.Spaces => SpacesGeneralContextType
          case t.ContextType.ReplyP n => ReplyP nGeneralContextType
          case t.ContextType.NewUser => NewUserGeneralContextType
          case t.ContextType.EnumUnknownContextType(f eld) =>
            throw new UnsupportedOperat onExcept on(s"Unknown context type: $f eld")
        }

        val land ngUrl = context.land ngUrl.map { url =>
          val endpo ntOpt ons = url.urtEndpo ntOpt ons.map { opt ons =>
            UrtEndpo ntOpt ons(
              requestParams = opt ons.requestParams.map(_.toMap),
              t le = opt ons.t le,
              cac  d = opt ons.cac  d,
              subt le = opt ons.subt le
            )
          }

          val urlType = url.urlType match {
            case t.UrlType.ExternalUrl => ExternalUrl
            case t.UrlType.DeepL nk => DeepL nk
            case t.UrlType.UrtEndpo nt => UrtEndpo nt
            case t.UrlType.EnumUnknownUrlType(f eld) =>
              throw new UnsupportedOperat onExcept on(s"Unknown url type: $f eld")
          }

          Url(urlType = urlType, url = url.url, urtEndpo ntOpt ons = endpo ntOpt ons)
        }

        GeneralContext(
          text = context.text,
          contextType = contextType,
          url = context.url,
          context mageUrls = context.context mageUrls.map(_.toL st),
          land ngUrl = land ngUrl
        )
      case t.Soc alContext.Top cContext(context) =>
        val funct onal yType = context.funct onal yType match {
          case t.Top cContextFunct onal yType.Bas c =>
            Bas cTop cContextFunct onal yType
          case t.Top cContextFunct onal yType.Recom ndat on =>
            Recom ndat onTop cContextFunct onal yType
          case t.Top cContextFunct onal yType.RecW hEducat on =>
            RecW hEducat onTop cContextFunct onal yType
          case t.Top cContextFunct onal yType.EnumUnknownTop cContextFunct onal yType(f eld) =>
            throw new UnsupportedOperat onExcept on(s"Unknown funct onal y type: $f eld")
        }

        Top cContext(
          top c d = context.top c d,
          funct onal yType = So (funct onal yType)
        )
      case t.Soc alContext.UnknownUn onF eld(f eld) =>
        throw new UnsupportedOperat onExcept on(s"Unknown soc al context: $f eld")
    }
  }
}
