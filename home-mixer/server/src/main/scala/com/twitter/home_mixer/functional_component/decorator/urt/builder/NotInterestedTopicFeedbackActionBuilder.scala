package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Perspect veF lteredL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top cContextFunct onal yTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RecW hEducat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orMarkNot nterestedTop c
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Not nterestedTop cFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on] = {
    val  sOutOfNetwork = !cand dateFeatures.getOrElse( nNetworkFeature, true)
    val val dFollo dByUser ds =
      cand dateFeatures.getOrElse(SGSVal dFollo dByUser dsFeature, N l)
    val val dL kedByUser ds =
      cand dateFeatures
        .getOrElse(SGSVal dL kedByUser dsFeature, N l)
        .f lter(
          cand dateFeatures.getOrElse(Perspect veF lteredL kedByUser dsFeature, N l).toSet.conta ns)

     f ( sOutOfNetwork && val dL kedByUser ds. sEmpty && val dFollo dByUser ds. sEmpty) {
      val top c dSoc alContext = cand dateFeatures.getOrElse(Top c dSoc alContextFeature, None)
      val top cContextFunct onal yType =
        cand dateFeatures.getOrElse(Top cContextFunct onal yTypeFeature, None)

      (top c dSoc alContext, top cContextFunct onal yType) match {
        case (So (top c d), So (top cContextFunct onal yType))
             f top cContextFunct onal yType == Recom ndat onTop cContextFunct onal yType ||
              top cContextFunct onal yType == RecW hEducat onTop cContextFunct onal yType =>
          So (
            FeedbackAct on(
              feedbackType = R chBehav or,
              prompt = None,
              conf rmat on = None,
              ch ldFeedbackAct ons = None,
              feedbackUrl = None,
              hasUndoAct on = So (true),
              conf rmat onD splayType = None,
              cl entEvent nfo = None,
               con = None,
              r chBehav or =
                So (R chFeedbackBehav orMarkNot nterestedTop c(top c d = top c d.toStr ng)),
              subprompt = None,
              encodedFeedbackRequest = None
            )
          )
        case _ => None
      }
    } else {
      None
    }
  }
}
