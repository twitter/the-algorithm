package com.tw ter.ho _m xer.product.scored_t ets.s de_effect

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Follo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RequestJo n dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .Scr beScoredCand datesFlag
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.EnableScr beScoredCand datesParam
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beLogEventS deEffect
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * S de effect that logs scored cand dates from scor ng p pel nes
 */
@S ngleton
class Scr beScoredCand datesS deEffect @ nject() (
  @Flag(Scr beScoredCand datesFlag) enableScr beScoredCand dates: Boolean,
  eventBusPubl s r: EventPubl s r[t.ScoredCand date])
    extends Scr beLogEventS deEffect[
      t.ScoredCand date,
      ScoredT etsQuery,
      ScoredT etsResponse
    ]
    w h P pel neResultS deEffect.Cond  onally[
      ScoredT etsQuery,
      ScoredT etsResponse
    ]
    w h Logg ng {

  overr de val  dent f er: S deEffect dent f er =
    S deEffect dent f er("Scr beScoredCand dates")

  overr de def only f(
    query: ScoredT etsQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ScoredT etsResponse
  ): Boolean = enableScr beScoredCand dates && query.params(EnableScr beScoredCand datesParam)

  /**
   * Bu ld t  log events from query, select ons and response
   *
   * @param query               P pel neQuery
   * @param selectedCand dates  Result after Selectors are executed
   * @param rema n ngCand dates Cand dates wh ch  re not selected
   * @param droppedCand dates   Cand dates dropped dur ng select on
   * @param response            Result after Unmarshall ng
   *
   * @return LogEvent  n thr ft
   */
  overr de def bu ldLogEvents(
    query: ScoredT etsQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ScoredT etsResponse
  ): Seq[t.ScoredCand date] = {
    val returned = (selectedCand dates ++ rema n ngCand dates).map(toThr ft(_, query, false))
    val dropped = droppedCand dates.map(toThr ft(_, query, true))
    returned ++ dropped
  }

  pr vate def toThr ft(
    cand date: Cand dateW hDeta ls,
    query: ScoredT etsQuery,
     sDropped: Boolean
  ): t.ScoredCand date = {
    t.ScoredCand date(
      t et d = cand date.cand date dLong,
      v e r d = query.getOpt onalUser d,
      author d = cand date.features.getOrElse(Author dFeature, None),
      trace d = So (Trace. d.trace d.toLong),
      requestJo n d = query.features.flatMap(_.getOrElse(RequestJo n dFeature, None)),
      score = cand date.features.getOrElse(ScoreFeature, None),
      suggestType = cand date.features.getOrElse(SuggestTypeFeature, None).map(_.na ),
       s nNetwork = cand date.features.getTry(From nNetworkS ceFeature).toOpt on,
       nReplyToT et d = cand date.features.getOrElse( nReplyToT et dFeature, None),
       nReplyToUser d = cand date.features.getOrElse( nReplyToUser dFeature, None),
      quotedT et d = cand date.features.getOrElse(QuotedT et dFeature, None),
      quotedUser d = cand date.features.getOrElse(QuotedUser dFeature, None),
      d rectedAtUser d = cand date.features.getOrElse(D rectedAtUser dFeature, None),
      favor edByUser ds = convertSeqFeature(cand date, Favor edByUser dsFeature),
      follo dByUser ds = convertSeqFeature(cand date, Follo dByUser dsFeature),
      ancestors = convertSeqFeature(cand date, AncestorsFeature),
      requestT  Ms = So (query.queryT  . nM ll seconds),
      cand dateP pel ne dent f er =
        cand date.features.getTry(Cand dateP pel nes).toOpt on.map(_. ad.na ),
      earlyb rdScore = cand date.features.getOrElse(Earlyb rdScoreFeature, None),
       sDropped = So ( sDropped)
    )
  }

  pr vate def convertSeqFeature[T](
    cand dateW hDeta ls: Cand dateW hDeta ls,
    feature: Feature[_, Seq[T]]
  ): Opt on[Seq[T]] =
    Opt on(
      cand dateW hDeta ls.features
        .getOrElse(feature, Seq.empty)).f lter(_.nonEmpty)

  overr de val logP pel nePubl s r: EventPubl s r[t.ScoredCand date] = eventBusPubl s r
}
