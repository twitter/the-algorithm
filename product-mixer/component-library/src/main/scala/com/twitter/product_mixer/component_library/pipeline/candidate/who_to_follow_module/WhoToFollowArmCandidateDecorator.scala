package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.account_recom ndat ons_m xer.WhoToFollowModuleFooterFeature
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.account_recom ndat ons_m xer.WhoToFollowModule aderFeature
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt em nModuleDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.user.UserCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Cl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Stat cUrlBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.promoted.FeaturePromoted tadataBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.soc al_context.WhoToFollowSoc alContextBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.str ngcenter.StrStat c
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.ModuleFooterBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Module aderBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.T  l neModuleBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.DeepL nk
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object WhoToFollowArmCand dateDecorator {
  val Cl entEventComponent = "suggest_who_to_follow"
  val EntryNa spaceStr ng = "who-to-follow"
}

case class WhoToFollowArmCand dateDecorator[-Query <: P pel neQuery](
  moduleD splayTypeBu lder: BaseModuleD splayTypeBu lder[Query, UserCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, UserCand date]
  ]) extends Cand dateDecorator[Query, UserCand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[UserCand date]]
  ): St ch[Seq[Decorat on]] = {
    val cl entEventDeta lsBu lder = WhoToFollowCl entEventDeta lsBu lder(Track ngTokenFeature)
    val cl entEvent nfoBu lder = Cl entEvent nfoBu lder[Query, UserCand date](
      WhoToFollowArmCand dateDecorator.Cl entEventComponent,
      So (cl entEventDeta lsBu lder))
    val promoted tadataBu lder = FeaturePromoted tadataBu lder(Ad mpress onFeature)
    val soc alContextBu lder =
      WhoToFollowSoc alContextBu lder(Soc alTextFeature,  rm ContextTypeFeature)
    val user emBu lder = UserCand dateUrt emBu lder(
      cl entEvent nfoBu lder = cl entEvent nfoBu lder,
      promoted tadataBu lder = So (promoted tadataBu lder),
      soc alContextBu lder = So (soc alContextBu lder))
    val user emDecorator = Urt emCand dateDecorator(user emBu lder)

    val whoToFollowModuleBu lder = {
      val whoToFollow aderOpt = query.features.map(_.get(WhoToFollowModule aderFeature))
      val whoToFollowFooterOpt = query.features.flatMap(_.get(WhoToFollowModuleFooterFeature))
      val whoToFollowModule aderBu lder = whoToFollow aderOpt.flatMap(_.t le).map { t le =>
        Module aderBu lder(textBu lder = StrStat c(t le),  sSt cky = So (true))
      }
      val whoToFollowModuleFooterBu lder = whoToFollowFooterOpt.flatMap(_.act on).map { act on =>
        ModuleFooterBu lder(
          textBu lder = StrStat c(act on.t le),
          urlBu lder = So (Stat cUrlBu lder(act on.act onUrl, DeepL nk)))
      }

      T  l neModuleBu lder(
        entryNa space = EntryNa space(WhoToFollowArmCand dateDecorator.EntryNa spaceStr ng),
        cl entEvent nfoBu lder = cl entEvent nfoBu lder,
        d splayTypeBu lder = moduleD splayTypeBu lder,
         aderBu lder = whoToFollowModule aderBu lder,
        footerBu lder = whoToFollowModuleFooterBu lder,
        feedbackAct on nfoBu lder = feedbackAct on nfoBu lder,
      )
    }

    Urt em nModuleDecorator(
      user emDecorator,
      whoToFollowModuleBu lder
    ).apply(query, cand dates)
  }
}
