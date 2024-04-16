package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class AuthorCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(cand dateFeatures: FeatureMap): Opt on[Ch ldFeedbackAct on] = {
    Cand datesUt l.getOr g nalAuthor d(cand dateFeatures).flatMap { author d =>
      FeedbackUt l.bu ldUserSeeFe rCh ldFeedbackAct on(
        user d = author d,
        na sByUser d = cand dateFeatures.getOrElse(ScreenNa sFeature, Map.empty[Long, Str ng]),
        promptExternalStr ng = externalStr ngs.showFe rT etsStr ng,
        conf rmat onExternalStr ng = externalStr ngs.showFe rT etsConf rmat onStr ng,
        engage ntType = t.FeedbackEngage ntType.T et,
        str ngCenter = str ngCenter,
         nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None)
      )
    }
  }
}
