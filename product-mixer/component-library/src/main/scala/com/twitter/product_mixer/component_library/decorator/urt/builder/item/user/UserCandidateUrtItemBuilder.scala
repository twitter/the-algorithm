package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.user

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.user.UserCand dateUrt emBu lder.UserCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseUserCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date. sMarkUnreadFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.user.BaseUserReact veTr ggersBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.promoted.BasePromoted tadataBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.UserD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UserCand dateUrt emBu lder {
  val UserCl entEvent nfoEle nt: Str ng = "user"
}

case class UserCand dateUrt emBu lder[Query <: P pel neQuery, UserCand date <: BaseUserCand date](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, UserCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, UserCand date]
  ] = None,
  d splayType: UserD splayType = User,
  promoted tadataBu lder: Opt on[BasePromoted tadataBu lder[Query, UserCand date]] = None,
  soc alContextBu lder: Opt on[BaseSoc alContextBu lder[Query, UserCand date]] = None,
  react veTr ggersBu lder: Opt on[BaseUserReact veTr ggersBu lder[Query, UserCand date]] = None,
  enableReact veBlend ng: Opt on[Boolean] = None)
    extends Cand dateUrtEntryBu lder[Query, UserCand date, User em] {

  overr de def apply(
    query: Query,
    userCand date: UserCand date,
    cand dateFeatures: FeatureMap
  ): User em = {
    val  sMarkUnread = cand dateFeatures.getTry( sMarkUnreadFeature).toOpt on

    User em(
       d = userCand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        userCand date,
        cand dateFeatures,
        So (UserCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, userCand date, cand dateFeatures)),
       sMarkUnread =  sMarkUnread,
      d splayType = d splayType,
      promoted tadata =
        promoted tadataBu lder.flatMap(_.apply(query, userCand date, cand dateFeatures)),
      soc alContext =
        soc alContextBu lder.flatMap(_.apply(query, userCand date, cand dateFeatures)),
      react veTr ggers =
        react veTr ggersBu lder.flatMap(_.apply(query, userCand date, cand dateFeatures)),
      enableReact veBlend ng = enableReact veBlend ng
    )
  }
}
