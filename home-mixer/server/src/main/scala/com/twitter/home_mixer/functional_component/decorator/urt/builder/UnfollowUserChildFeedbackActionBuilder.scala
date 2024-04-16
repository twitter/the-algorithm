package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleFollowUser
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class UnfollowUserCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(cand dateFeatures: FeatureMap): Opt on[Ch ldFeedbackAct on] = {
    val  s nNetwork = cand dateFeatures.getOrElse( nNetworkFeature, false)
    val user dOpt = cand dateFeatures.getOrElse(Author dFeature, None)

     f ( s nNetwork) {
      user dOpt.flatMap { user d =>
        val screenNa sMap =
          cand dateFeatures.getOrElse(ScreenNa sFeature, Map.empty[Long, Str ng])
        val userScreenNa Opt = screenNa sMap.get(user d)
        userScreenNa Opt.map { userScreenNa  =>
          val prompt = str ngCenter.prepare(
            externalStr ngs.unfollowUserStr ng,
            Map("userna " -> userScreenNa )
          )
          val conf rmat on = str ngCenter.prepare(
            externalStr ngs.unfollowUserConf rmat onStr ng,
            Map("userna " -> userScreenNa )
          )
          Ch ldFeedbackAct on(
            feedbackType = R chBehav or,
            prompt = So (prompt),
            conf rmat on = So (conf rmat on),
            feedbackUrl = None,
            hasUndoAct on = So (true),
            conf rmat onD splayType = None,
            cl entEvent nfo = None,
             con = So ( con.Unfollow),
            r chBehav or = So (R chFeedbackBehav orToggleFollowUser(user d)),
            subprompt = None
          )
        }
      }
    } else None
  }
}
