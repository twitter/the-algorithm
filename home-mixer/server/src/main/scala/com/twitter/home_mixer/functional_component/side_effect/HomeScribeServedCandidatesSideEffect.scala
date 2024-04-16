package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.marshaller.t  l ne_logg ng.PromotedT etDeta lsMarshaller
 mport com.tw ter.ho _m xer.marshaller.t  l ne_logg ng.T etDeta lsMarshaller
 mport com.tw ter.ho _m xer.marshaller.t  l ne_logg ng.WhoToFollowDeta lsMarshaller
 mport com.tw ter.ho _m xer.model.Ho Features.Get n  alFeature
 mport com.tw ter.ho _m xer.model.Ho Features.GetM ddleFeature
 mport com.tw ter.ho _m xer.model.Ho Features.GetNe rFeature
 mport com.tw ter.ho _m xer.model.Ho Features.GetOlderFeature
 mport com.tw ter.ho _m xer.model.Ho Features.HasDarkRequestFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RequestJo n dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedRequest dFeature
 mport com.tw ter.ho _m xer.model.request.Dev ceContext.RequestContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .Scr beServedCand datesFlag
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableScr beServedCand datesParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseUserCand date
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_subscr be_module.WhoToSubscr beCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beLogEventS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => thr ft}
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * S de effect that logs ho  t  l ne served cand dates to Scr be.
 */
@S ngleton
class Ho Scr beServedCand datesS deEffect @ nject() (
  @Flag(Scr beServedCand datesFlag) enableScr beServedCand dates: Boolean,
  scr beEventPubl s r: EventPubl s r[thr ft.ServedEntry])
    extends Scr beLogEventS deEffect[
      thr ft.ServedEntry,
      P pel neQuery w h HasSeenT et ds w h HasDev ceContext,
      T  l ne
    ]
    w h P pel neResultS deEffect.Cond  onally[
      P pel neQuery w h HasSeenT et ds w h HasDev ceContext,
      T  l ne
    ] {

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("Ho Scr beServedCand dates")

  overr de def only f(
    query: P pel neQuery w h HasSeenT et ds w h HasDev ceContext,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Boolean = enableScr beServedCand dates && query.params(EnableScr beServedCand datesParam)

  overr de def bu ldLogEvents(
    query: P pel neQuery w h HasSeenT et ds w h HasDev ceContext,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Seq[thr ft.ServedEntry] = {
    val t  l neType = query.product match {
      case Follow ngProduct => thr ft.T  l neType.Ho Latest
      case For Product => thr ft.T  l neType.Ho 
      case Subscr bedProduct => thr ft.T  l neType.Ho Subscr bed
      case ot r => throw new UnsupportedOperat onExcept on(s"Unknown product: $ot r")
    }
    val requestProvenance = query.dev ceContext.map { dev ceContext =>
      dev ceContext.requestContextValue match {
        case RequestContext.Foreground => thr ft.RequestProvenance.Foreground
        case RequestContext.Launch => thr ft.RequestProvenance.Launch
        case RequestContext.PullToRefresh => thr ft.RequestProvenance.Ptr
        case _ => thr ft.RequestProvenance.Ot r
      }
    }
    val queryType = query.features.map { featureMap =>
       f (featureMap.getOrElse(GetOlderFeature, false)) thr ft.QueryType.GetOlder
      else  f (featureMap.getOrElse(GetNe rFeature, false)) thr ft.QueryType.GetNe r
      else  f (featureMap.getOrElse(GetM ddleFeature, false)) thr ft.QueryType.GetM ddle
      else  f (featureMap.getOrElse(Get n  alFeature, false)) thr ft.QueryType.Get n  al
      else thr ft.QueryType.Ot r
    }
    val request nfo = thr ft.Request nfo(
      requestT  Ms = query.queryT  . nM ll seconds,
      trace d = Trace. d.trace d.toLong,
      user d = query.getOpt onalUser d,
      cl entApp d = query.cl entContext.app d,
      hasDarkRequest = query.features.flatMap(_.getOrElse(HasDarkRequestFeature, None)),
      parent d = So (Trace. d.parent d.toLong),
      span d = So (Trace. d.span d.toLong),
      t  l neType = So (t  l neType),
       pAddress = query.cl entContext. pAddress,
      userAgent = query.cl entContext.userAgent,
      queryType = queryType,
      requestProvenance = requestProvenance,
      languageCode = query.cl entContext.languageCode,
      countryCode = query.cl entContext.countryCode,
      requestEndT  Ms = So (T  .now. nM ll seconds),
      servedRequest d = query.features.flatMap(_.getOrElse(ServedRequest dFeature, None)),
      requestJo n d = query.features.flatMap(_.getOrElse(RequestJo n dFeature, None))
    )

    val t et dTo emCand dateMap: Map[Long,  emCand dateW hDeta ls] =
      selectedCand dates.flatMap {
        case  em:  emCand dateW hDeta ls  f  em.cand date. s nstanceOf[BaseT etCand date] =>
          Seq(( em.cand date dLong,  em))
        case module: ModuleCand dateW hDeta ls
             f module.cand dates. adOpt on.ex sts(_.cand date. s nstanceOf[BaseT etCand date]) =>
          module.cand dates.map( em => ( em.cand date dLong,  em))
        case _ => Seq.empty
      }.toMap

    val user dTo emCand dateMap: Map[Long,  emCand dateW hDeta ls] =
      selectedCand dates.flatMap {
        case module: ModuleCand dateW hDeta ls
             f module.cand dates.forall(_.cand date. s nstanceOf[BaseUserCand date]) =>
          module.cand dates.map {  em =>
            ( em.cand date dLong,  em)
          }
        case _ => Seq.empty
      }.toMap

    response. nstruct ons.z pW h ndex
      .collect {
        case (AddEntr esT  l ne nstruct on(entr es),  ndex) =>
          entr es.collect {
            case entry: T et em  f entry.promoted tadata. sDef ned =>
              val promotedT etDeta ls = PromotedT etDeta lsMarshaller(entry,  ndex)
              Seq(
                thr ft.Entry nfo(
                   d = entry. d,
                  pos  on =  ndex.shortValue(),
                  entry d = entry.entry dent f er,
                  entryType = thr ft.EntryType.PromotedT et,
                  sort ndex = entry.sort ndex,
                  vert calS ze = So (1),
                  d splayType = So (entry.d splayType.toStr ng),
                  deta ls = So (thr ft. emDeta ls.PromotedT etDeta ls(promotedT etDeta ls))
                )
              )
            case entry: T et em =>
              val cand date = t et dTo emCand dateMap(entry. d)
              val t etDeta ls = T etDeta lsMarshaller(entry, cand date)
              Seq(
                thr ft.Entry nfo(
                   d = cand date.cand date dLong,
                  pos  on =  ndex.shortValue(),
                  entry d = entry.entry dent f er,
                  entryType = thr ft.EntryType.T et,
                  sort ndex = entry.sort ndex,
                  vert calS ze = So (1),
                  score = cand date.features.getOrElse(ScoreFeature, None),
                  d splayType = So (entry.d splayType.toStr ng),
                  deta ls = So (thr ft. emDeta ls.T etDeta ls(t etDeta ls))
                )
              )
            case module: T  l neModule
                 f module.entryNa space.toStr ng == WhoToFollowCand dateDecorator.EntryNa spaceStr ng =>
              module. ems.collect {
                case Module em(entry: User em, _, _) =>
                  val cand date = user dTo emCand dateMap(entry. d)
                  val whoToFollowDeta ls = WhoToFollowDeta lsMarshaller(entry, cand date)
                  thr ft.Entry nfo(
                     d = entry. d,
                    pos  on =  ndex.shortValue(),
                    entry d = module.entry dent f er,
                    entryType = thr ft.EntryType.WhoToFollowModule,
                    sort ndex = module.sort ndex,
                    score = cand date.features.getOrElse(ScoreFeature, None),
                    d splayType = So (entry.d splayType.toStr ng),
                    deta ls = So (thr ft. emDeta ls.WhoToFollowDeta ls(whoToFollowDeta ls))
                  )
              }
            case module: T  l neModule
                 f module.entryNa space.toStr ng == WhoToSubscr beCand dateDecorator.EntryNa spaceStr ng =>
              module. ems.collect {
                case Module em(entry: User em, _, _) =>
                  val cand date = user dTo emCand dateMap(entry. d)
                  val whoToSubscr beDeta ls = WhoToFollowDeta lsMarshaller(entry, cand date)
                  thr ft.Entry nfo(
                     d = entry. d,
                    pos  on =  ndex.shortValue(),
                    entry d = module.entry dent f er,
                    entryType = thr ft.EntryType.WhoToSubscr beModule,
                    sort ndex = module.sort ndex,
                    score = cand date.features.getOrElse(ScoreFeature, None),
                    d splayType = So (entry.d splayType.toStr ng),
                    deta ls = So (thr ft. emDeta ls.WhoToFollowDeta ls(whoToSubscr beDeta ls))
                  )
              }
            case module: T  l neModule
                 f module.sort ndex. sDef ned && module. ems. adOpt on.ex sts(
                  _. em. s nstanceOf[T et em]) =>
              module. ems.collect {
                case Module em(entry: T et em, _, _) =>
                  val cand date = t et dTo emCand dateMap(entry. d)
                  thr ft.Entry nfo(
                     d = entry. d,
                    pos  on =  ndex.shortValue(),
                    entry d = module.entry dent f er,
                    entryType = thr ft.EntryType.Conversat onModule,
                    sort ndex = module.sort ndex,
                    score = cand date.features.getOrElse(ScoreFeature, None),
                    d splayType = So (entry.d splayType.toStr ng)
                  )
              }
            case _ => Seq.empty
          }.flatten
        // Ot r  nstruct ons
        case _ => Seq.empty[thr ft.Entry nfo]
      }.flatten.map { entry nfo =>
        thr ft.ServedEntry(
          entry = So (entry nfo),
          request = request nfo
        )
      }
  }

  overr de val logP pel nePubl s r: EventPubl s r[thr ft.ServedEntry] =
    scr beEventPubl s r

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert()
  )
}
