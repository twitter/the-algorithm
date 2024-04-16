package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.funct onal_component.decorator.Ho QueryTypePred cates
 mport com.tw ter.ho _m xer.funct onal_component.decorator.bu lder.Ho T etTypePred cates
 mport com.tw ter.ho _m xer.model.Ho Features.AccountAgeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.V deoDurat onMsFeature
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.L stT etsProduct
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect.Cl entEvent
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect.EventNa space
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes. nject on.scr be. nject onScr beUt l

pr vate[s de_effect] sealed tra  Cl entEventsBu lder {
  pr vate val Follow ngSect on = So ("latest")
  pr vate val For Sect on = So ("ho ")
  pr vate val L stT etsSect on = So ("l st")
  pr vate val Subscr bedSect on = So ("subscr bed")

  protected def sect on(query: P pel neQuery): Opt on[Str ng] = {
    query.product match {
      case Follow ngProduct => Follow ngSect on
      case For Product => For Sect on
      case L stT etsProduct => L stT etsSect on
      case Subscr bedProduct => Subscr bedSect on
      case ot r => throw new UnsupportedOperat onExcept on(s"Unknown product: $ot r")
    }
  }

  protected def count(
    cand dates: Seq[Cand dateW hDeta ls],
    pred cate: FeatureMap => Boolean = _ => true,
    queryFeatures: FeatureMap = FeatureMap.empty
  ): Opt on[Long] = So (cand dates.v ew.count( em => pred cate( em.features ++ queryFeatures)))

  protected def sum(
    cand dates: Seq[Cand dateW hDeta ls],
    pred cate: FeatureMap => Opt on[ nt],
    queryFeatures: FeatureMap = FeatureMap.empty
  ): Opt on[Long] =
    So (cand dates.v ew.flatMap( em => pred cate( em.features ++ queryFeatures)).sum)
}

pr vate[s de_effect] object ServedEventsBu lder extends Cl entEventsBu lder {

  pr vate val ServedT etsAct on = So ("served_t ets")
  pr vate val ServedUsersAct on = So ("served_users")
  pr vate val  njectedComponent = So (" njected")
  pr vate val PromotedComponent = So ("promoted")
  pr vate val WhoToFollowComponent = So ("who_to_follow")
  pr vate val WhoToSubscr beComponent = So ("who_to_subscr be")
  pr vate val W hV deoDurat onComponent = So ("w h_v deo_durat on")
  pr vate val V deoDurat onSumEle nt = So ("v deo_durat on_sum")
  pr vate val NumV deosEle nt = So ("num_v deos")

  def bu ld(
    query: P pel neQuery,
     njectedT ets: Seq[ emCand dateW hDeta ls],
    promotedT ets: Seq[ emCand dateW hDeta ls],
    whoToFollowUsers: Seq[ emCand dateW hDeta ls],
    whoToSubscr beUsers: Seq[ emCand dateW hDeta ls]
  ): Seq[Cl entEvent] = {
    val baseEventNa space = EventNa space(
      sect on = sect on(query),
      act on = ServedT etsAct on
    )
    val overallServedEvents = Seq(
      Cl entEvent(baseEventNa space, eventValue = count( njectedT ets ++ promotedT ets)),
      Cl entEvent(
        baseEventNa space.copy(component =  njectedComponent),
        eventValue = count( njectedT ets)),
      Cl entEvent(
        baseEventNa space.copy(component = PromotedComponent),
        eventValue = count(promotedT ets)),
      Cl entEvent(
        baseEventNa space.copy(component = WhoToFollowComponent, act on = ServedUsersAct on),
        eventValue = count(whoToFollowUsers)),
      Cl entEvent(
        baseEventNa space.copy(component = WhoToSubscr beComponent, act on = ServedUsersAct on),
        eventValue = count(whoToSubscr beUsers)),
    )

    val t etTypeServedEvents = Ho T etTypePred cates.Pred cateMap.map {
      case (t etType, pred cate) =>
        Cl entEvent(
          baseEventNa space.copy(component =  njectedComponent, ele nt = So (t etType)),
          eventValue = count( njectedT ets, pred cate, query.features.getOrElse(FeatureMap.empty))
        )
    }.toSeq

    val suggestTypeServedEvents =  njectedT ets
      .flatMap(_.features.getOrElse(SuggestTypeFeature, None))
      .map {
         nject onScr beUt l.scr beComponent
      }
      .groupBy( dent y).map {
        case (suggestType, group) =>
          Cl entEvent(
            baseEventNa space.copy(component = suggestType),
            eventValue = So (group.s ze.toLong))
      }.toSeq

    // V deo durat on events
    val numV deosEvent = Cl entEvent(
      baseEventNa space.copy(component = W hV deoDurat onComponent, ele nt = NumV deosEle nt),
      eventValue = count( njectedT ets, _.getOrElse(V deoDurat onMsFeature, None).nonEmpty)
    )
    val v deoDurat onSumEvent = Cl entEvent(
      baseEventNa space
        .copy(component = W hV deoDurat onComponent, ele nt = V deoDurat onSumEle nt),
      eventValue = sum( njectedT ets, _.getOrElse(V deoDurat onMsFeature, None))
    )
    val v deoEvents = Seq(numV deosEvent, v deoDurat onSumEvent)

    overallServedEvents ++ t etTypeServedEvents ++ suggestTypeServedEvents ++ v deoEvents
  }
}

pr vate[s de_effect] object EmptyT  l neEventsBu lder extends Cl entEventsBu lder {
  pr vate val EmptyAct on = So ("empty")
  pr vate val AccountAgeLessThan30M nutesComponent = So ("account_age_less_than_30_m nutes")
  pr vate val ServedNonPromotedT etEle nt = So ("served_non_promoted_t et")

  def bu ld(
    query: P pel neQuery,
     njectedT ets: Seq[ emCand dateW hDeta ls]
  ): Seq[Cl entEvent] = {
    val baseEventNa space = EventNa space(
      sect on = sect on(query),
      act on = EmptyAct on
    )

    // Empty t  l ne events
    val accountAgeLessThan30M nutes = query.features
      .flatMap(_.getOrElse(AccountAgeFeature, None))
      .ex sts(_.unt lNow < 30.m nutes)
    val  sEmptyT  l ne = count( njectedT ets).conta ns(0L)
    val pred cates = Seq(
      None ->  sEmptyT  l ne,
      AccountAgeLessThan30M nutesComponent -> ( sEmptyT  l ne && accountAgeLessThan30M nutes)
    )
    for {
      (component, pred cate) <- pred cates
       f pred cate
    } y eld Cl entEvent(
      baseEventNa space.copy(component = component, ele nt = ServedNonPromotedT etEle nt))
  }
}

pr vate[s de_effect] object QueryEventsBu lder extends Cl entEventsBu lder {

  pr vate val ServedS zePred cateMap: Map[Str ng,  nt => Boolean] = Map(
    ("s ze_ s_empty", _ <= 0),
    ("s ze_at_most_5", _ <= 5),
    ("s ze_at_most_10", _ <= 10),
    ("s ze_at_most_35", _ <= 35)
  )

  def bu ld(
    query: P pel neQuery,
     njectedT ets: Seq[ emCand dateW hDeta ls]
  ): Seq[Cl entEvent] = {
    val baseEventNa space = EventNa space(
      sect on = sect on(query)
    )
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val servedS zeQueryEvents =
      for {
        (queryPred cateNa , queryPred cate) <- Ho QueryTypePred cates.Pred cateMap
         f queryPred cate(queryFeatureMap)
        (servedS zePred cateNa , servedS zePred cate) <- ServedS zePred cateMap
         f servedS zePred cate( njectedT ets.s ze)
      } y eld Cl entEvent(
        baseEventNa space
          .copy(component = So (servedS zePred cateNa ), act on = So (queryPred cateNa )))
    servedS zeQueryEvents.toSeq
  }
}
