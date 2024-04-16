package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Ret eterCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(cand dateFeatures: FeatureMap): Opt on[Ch ldFeedbackAct on] = {
    val  sRet et = cand dateFeatures.getOrElse( sRet etFeature, false)

     f ( sRet et) {
      cand dateFeatures.getOrElse(Author dFeature, None).flatMap { ret eter d =>
        FeedbackUt l.bu ldUserSeeFe rCh ldFeedbackAct on(
          user d = ret eter d,
          na sByUser d = cand dateFeatures.getOrElse(ScreenNa sFeature, Map.empty[Long, Str ng]),
          promptExternalStr ng = externalStr ngs.showFe rRet etsStr ng,
          conf rmat onExternalStr ng = externalStr ngs.showFe rRet etsConf rmat onStr ng,
          engage ntType = t.FeedbackEngage ntType.Ret et,
          str ngCenter = str ngCenter,
           nject onType = cand dateFeatures.getOrElse(SuggestTypeFeature, None)
        )
      }
    } else None
  }
}
