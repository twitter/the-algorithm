package com.tw ter.ho _m xer.product.scored_t ets.s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sBlueVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sCreatorFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sGoldVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sGrayVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sLegacyVer f edFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Cac dCand dateP pel ne dent f erFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.LastScoredT  stampMsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Perspect veF lteredL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top cContextFunct onal yTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etUrlsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.  ghtedModelScoreFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Top cContextFunct onal yTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.cac .TtlCac 
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cac dScoredT etsS deEffect @ nject() (
  scoredT etsCac : TtlCac [Long, hmt.ScoredT etsResponse])
    extends P pel neResultS deEffect[P pel neQuery, ScoredT etsResponse] {

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("Cac dScoredT ets")

  pr vate val MaxT etsToCac  = 1000

  def bu ldCac dScoredT ets(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hDeta ls]
  ): hmt.ScoredT etsResponse = {
    val t ets = cand dates.map { cand date =>
      val sgsVal dL kedByUser ds =
        cand date.features.getOrElse(SGSVal dL kedByUser dsFeature, Seq.empty)
      val sgsVal dFollo dByUser ds =
        cand date.features.getOrElse(SGSVal dFollo dByUser dsFeature, Seq.empty)
      val perspect veF lteredL kedByUser ds =
        cand date.features.getOrElse(Perspect veF lteredL kedByUser dsFeature, Seq.empty)
      val ancestors = cand date.features.getOrElse(AncestorsFeature, Seq.empty)

      hmt.ScoredT et(
        t et d = cand date.cand date dLong,
        author d = cand date.features.get(Author dFeature).get,
        // Cac  t  model score  nstead of t  f nal score because rescor ng  s per-request
        score = cand date.features.getOrElse(  ghtedModelScoreFeature, None),
        suggestType = cand date.features.getOrElse(SuggestTypeFeature, None),
        s ceT et d = cand date.features.getOrElse(S ceT et dFeature, None),
        s ceUser d = cand date.features.getOrElse(S ceUser dFeature, None),
        quotedT et d = cand date.features.getOrElse(QuotedT et dFeature, None),
        quotedUser d = cand date.features.getOrElse(QuotedUser dFeature, None),
         nReplyToT et d = cand date.features.getOrElse( nReplyToT et dFeature, None),
         nReplyToUser d = cand date.features.getOrElse( nReplyToUser dFeature, None),
        d rectedAtUser d = cand date.features.getOrElse(D rectedAtUser dFeature, None),
         nNetwork = So (cand date.features.getOrElse( nNetworkFeature, true)),
        sgsVal dL kedByUser ds = So (sgsVal dL kedByUser ds),
        sgsVal dFollo dByUser ds = So (sgsVal dFollo dByUser ds),
        top c d = cand date.features.getOrElse(Top c dSoc alContextFeature, None),
        top cFunct onal yType = cand date.features
          .getOrElse(Top cContextFunct onal yTypeFeature, None).map(
            Top cContextFunct onal yTypeMarshaller(_)),
        ancestors =  f (ancestors.nonEmpty) So (ancestors) else None,
         sReadFromCac  = So (true),
        streamToKafka = So (false),
        exclus veConversat onAuthor d = cand date.features
          .getOrElse(Exclus veConversat onAuthor dFeature, None),
        author tadata = So (
          hmt.Author tadata(
            blueVer f ed = cand date.features.getOrElse(Author sBlueVer f edFeature, false),
            goldVer f ed = cand date.features.getOrElse(Author sGoldVer f edFeature, false),
            grayVer f ed = cand date.features.getOrElse(Author sGrayVer f edFeature, false),
            legacyVer f ed = cand date.features.getOrElse(Author sLegacyVer f edFeature, false),
            creator = cand date.features.getOrElse(Author sCreatorFeature, false)
          )),
        lastScoredT  stampMs = cand date.features
          .getOrElse(LastScoredT  stampMsFeature, So (query.queryT  . nM ll seconds)),
        cand dateP pel ne dent f er = cand date.features
          .getOrElse(Cac dCand dateP pel ne dent f erFeature, So (cand date.s ce.na )),
        t etUrls = So (cand date.features.getOrElse(T etUrlsFeature, Seq.empty)),
        perspect veF lteredL kedByUser ds = So (perspect veF lteredL kedByUser ds)
      )
    }

    hmt.ScoredT etsResponse(t ets)
  }

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[P pel neQuery, ScoredT etsResponse]
  ): St ch[Un ] = {
    val cand dates =
      ( nputs.selectedCand dates ++  nputs.rema n ngCand dates ++  nputs.droppedCand dates)
        .f lter(_.features.getOrElse(ScoreFeature, None).ex sts(_ > 0.0))

    val truncatedCand dates =
       f (cand dates.s ze > MaxT etsToCac )
        cand dates
          .sortBy(-_.features.getOrElse(ScoreFeature, None).getOrElse(0.0)).take(MaxT etsToCac )
      else cand dates

     f (truncatedCand dates.nonEmpty) {
      val ttl =  nputs.query.params(Cac dScoredT ets.TTLParam)
      val scoredT ets = bu ldCac dScoredT ets( nputs.query, truncatedCand dates)
      St ch.callFuture(scoredT etsCac .set( nputs.query.getRequ redUser d, scoredT ets, ttl))
    } else St ch.Un 
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.4)
  )
}
