package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.BottomS et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orBlockUser
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class BlockUserCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(cand dateFeatures: FeatureMap): Opt on[Ch ldFeedbackAct on] = {
    val user dOpt =
       f (cand dateFeatures.getOrElse( sRet etFeature, false))
        cand dateFeatures.getOrElse(S ceUser dFeature, None)
      else cand dateFeatures.getOrElse(Author dFeature, None)

    user dOpt.flatMap { user d =>
      val screenNa sMap = cand dateFeatures.getOrElse(ScreenNa sFeature, Map.empty[Long, Str ng])
      val userScreenNa Opt = screenNa sMap.get(user d)
      userScreenNa Opt.map { userScreenNa  =>
        val prompt = str ngCenter.prepare(
          externalStr ngs.blockUserStr ng,
          Map("userna " -> userScreenNa )
        )
        Ch ldFeedbackAct on(
          feedbackType = R chBehav or,
          prompt = So (prompt),
          conf rmat on = None,
          feedbackUrl = None,
          hasUndoAct on = So (true),
          conf rmat onD splayType = So (BottomS et),
          cl entEvent nfo = None,
           con = So ( con.No),
          r chBehav or = So (R chFeedbackBehav orBlockUser(user d)),
          subprompt = None
        )
      }
    }
  }
}
