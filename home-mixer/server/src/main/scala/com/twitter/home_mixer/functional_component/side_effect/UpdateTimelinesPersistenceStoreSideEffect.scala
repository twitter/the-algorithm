package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.Ho Features. sT etPrev ewFeature
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_subscr be_module.WhoToSubscr beCand dateDecorator
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ReplaceEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowCover nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.pers stence.EntryW h em ds
 mport com.tw ter.t  l nem xer.cl ents.pers stence. em ds
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseBatc sCl ent
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseV3
 mport com.tw ter.t  l nes.pers stence.thr ftscala.T etScoreV1
 mport com.tw ter.t  l nes.pers stence.{thr ftscala => pers stence}
 mport com.tw ter.t  l neserv ce.model.T  l neQuery
 mport com.tw ter.t  l neserv ce.model.T  l neQueryOpt ons
 mport com.tw ter.t  l neserv ce.model.T etScore
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.ut l.T  
 mport com.tw ter.{t  l neserv ce => tls}
 mport javax. nject. nject
 mport javax. nject.S ngleton

object UpdateT  l nesPers stenceStoreS deEffect {
  val Empty em ds =  em ds(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None)
}

/**
 * S de effect that updates t  T  l nes Pers stence Store (Manhattan) w h t  entr es be ng returned.
 */
@S ngleton
class UpdateT  l nesPers stenceStoreS deEffect @ nject() (
  t  l neResponseBatc sCl ent: T  l neResponseBatc sCl ent[T  l neResponseV3])
    extends P pel neResultS deEffect[P pel neQuery, T  l ne] {

  overr de val  dent f er: S deEffect dent f er =
    S deEffect dent f er("UpdateT  l nesPers stenceStore")

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[P pel neQuery, T  l ne]
  ): St ch[Un ] = {
     f ( nputs.response. nstruct ons.nonEmpty) {
      val t  l neK nd =  nputs.query.product match {
        case Follow ngProduct => T  l neK nd.ho Latest
        case For Product => T  l neK nd.ho 
        case ot r => throw new UnsupportedOperat onExcept on(s"Unknown product: $ot r")
      }
      val t  l neQuery = T  l neQuery(
         d =  nputs.query.getRequ redUser d,
        k nd = t  l neK nd,
        opt ons = T  l neQueryOpt ons(
          contextualUser d =  nputs.query.getOpt onalUser d,
          dev ceContext = tls.Dev ceContext.empty.copy(
            userAgent =  nputs.query.cl entContext.userAgent,
            cl entApp d =  nputs.query.cl entContext.app d)
        )
      )

      val t et dTo emCand dateMap: Map[Long,  emCand dateW hDeta ls] =
         nputs.selectedCand dates.flatMap {
          case  em:  emCand dateW hDeta ls  f  em.cand date. d. s nstanceOf[Long] =>
            Seq(( em.cand date dLong,  em))
          case module: ModuleCand dateW hDeta ls
               f module.cand dates. adOpt on.ex sts(_.cand date. d. s nstanceOf[Long]) =>
            module.cand dates.map( em => ( em.cand date dLong,  em))
          case _ => Seq.empty
        }.toMap

      val entr es =  nputs.response. nstruct ons.collect {
        case AddEntr esT  l ne nstruct on(entr es) =>
          entr es.collect {
            //  ncludes t ets, t et prev ews, and promoted t ets
            case entry: T et em  f entry.sort ndex. sDef ned => {
              Seq(
                bu ldT etEntryW h em ds(
                  t et dTo emCand dateMap(entry. d),
                  entry.sort ndex.get
                ))
            }
            // t et conversat on modules are flattened to  nd v dual t ets  n t  pers stence store
            case module: T  l neModule
                 f module.sort ndex. sDef ned && module. ems. adOpt on.ex sts(
                  _. em. s nstanceOf[T et em]) =>
              module. ems.map {  em =>
                bu ldT etEntryW h em ds(
                  t et dTo emCand dateMap( em. em. d.as nstanceOf[Long]),
                  module.sort ndex.get)
              }
            case module: T  l neModule
                 f module.sort ndex. sDef ned && module.entryNa space.toStr ng == WhoToFollowCand dateDecorator.EntryNa spaceStr ng =>
              val user ds = module. ems
                .map( em =>
                  UpdateT  l nesPers stenceStoreS deEffect.Empty em ds.copy(user d =
                    So ( em. em. d.as nstanceOf[Long])))
              Seq(
                EntryW h em ds(
                  ent y dType = Ent y dType.WhoToFollow,
                  sort ndex = module.sort ndex.get,
                  s ze = module. ems.s ze.toShort,
                   em ds = So (user ds)
                ))
            case module: T  l neModule
                 f module.sort ndex. sDef ned && module.entryNa space.toStr ng == WhoToSubscr beCand dateDecorator.EntryNa spaceStr ng =>
              val user ds = module. ems
                .map( em =>
                  UpdateT  l nesPers stenceStoreS deEffect.Empty em ds.copy(user d =
                    So ( em. em. d.as nstanceOf[Long])))
              Seq(
                EntryW h em ds(
                  ent y dType = Ent y dType.WhoToSubscr be,
                  sort ndex = module.sort ndex.get,
                  s ze = module. ems.s ze.toShort,
                   em ds = So (user ds)
                ))
          }.flatten
        case ShowCover nstruct on(cover) =>
          Seq(
            EntryW h em ds(
              ent y dType = Ent y dType.Prompt,
              sort ndex = cover.sort ndex.get,
              s ze = 1,
               em ds = None
            )
          )
        case ReplaceEntryT  l ne nstruct on(entry) =>
          val na spaceLength = T et em.T etEntryNa space.toStr ng.length
          Seq(
            EntryW h em ds(
              ent y dType = Ent y dType.T et,
              sort ndex = entry.sort ndex.get,
              s ze = 1,
               em ds = So (
                Seq(
                   em ds(
                    t et d =
                      entry.entry dToReplace.map(e => e.substr ng(na spaceLength + 1).toLong),
                    s ceT et d = None,
                    quoteT et d = None,
                    s ceAuthor d = None,
                    quoteAuthor d = None,
                     nReplyToT et d = None,
                     nReplyToAuthor d = None,
                    semant cCore d = None,
                    art cle d = None,
                    hasRelevancePrompt = None,
                    promptData = None,
                    t etScore = None,
                    entry dToReplace = entry.entry dToReplace,
                    t etReact veData = None,
                    user d = None
                  )
                ))
            )
          )

      }.flatten

      val response = T  l neResponseV3(
        cl entPlatform = t  l neQuery.cl entPlatform,
        servedT   = T  .now,
        requestType = requestTypeFromQuery( nputs.query),
        entr es = entr es)

      St ch.callFuture(t  l neResponseBatc sCl ent. nsertResponse(t  l neQuery, response))
    } else St ch.Un 
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.8)
  )

  pr vate def bu ldT etEntryW h em ds(
    cand date:  emCand dateW hDeta ls,
    sort ndex: Long
  ): EntryW h em ds = {
    val features = cand date.features
    val s ceAuthor d =
       f (features.getOrElse( sRet etFeature, false)) features.getOrElse(S ceUser dFeature, None)
      else features.getOrElse(Author dFeature, None)
    val quoteAuthor d =
       f (features.getOrElse(QuotedT et dFeature, None).nonEmpty)
        features.getOrElse(S ceUser dFeature, None)
      else None
    val t etScore = features.getOrElse(ScoreFeature, None).map { score =>
      T etScore.fromThr ft(pers stence.T etScore.T etScoreV1(T etScoreV1(score)))
    }

    val  em ds =  em ds(
      t et d = So (cand date.cand date dLong),
      s ceT et d = features.getOrElse(S ceT et dFeature, None),
      quoteT et d = features.getOrElse(QuotedT et dFeature, None),
      s ceAuthor d = s ceAuthor d,
      quoteAuthor d = quoteAuthor d,
       nReplyToT et d = features.getOrElse( nReplyToT et dFeature, None),
       nReplyToAuthor d = features.getOrElse(D rectedAtUser dFeature, None),
      semant cCore d = features.getOrElse(Semant cCore dFeature, None),
      art cle d = None,
      hasRelevancePrompt = None,
      promptData = None,
      t etScore = t etScore,
      entry dToReplace = None,
      t etReact veData = None,
      user d = None
    )

    val  sPrev ew = features.getOrElse( sT etPrev ewFeature, default = false)
    val ent yType =  f ( sPrev ew) Ent y dType.T etPrev ew else Ent y dType.T et

    EntryW h em ds(
      ent y dType = ent yType,
      sort ndex = sort ndex,
      s ze = 1.toShort,
       em ds = So (Seq( em ds))
    )
  }

  pr vate def requestTypeFromQuery(query: P pel neQuery): pers stence.RequestType = {
    val features = query.features.getOrElse(FeatureMap.empty)

    val featureToRequestType = Seq(
      (Poll ngFeature, pers stence.RequestType.Poll ng),
      (Get n  alFeature, pers stence.RequestType. n  al),
      (GetNe rFeature, pers stence.RequestType.Ne r),
      (GetM ddleFeature, pers stence.RequestType.M ddle),
      (GetOlderFeature, pers stence.RequestType.Older)
    )

    featureToRequestType
      .collectF rst {
        case (feature, requestType)  f features.getOrElse(feature, false) => requestType
      }.getOrElse(pers stence.RequestType.Ot r)
  }
}
