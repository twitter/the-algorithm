package com.tw ter.v s b l y. nterfaces.push_serv ce

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.t ets.T etFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.StratoT etLabelMaps
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.common._
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.features.T et sS ceT et
 mport com.tw ter.v s b l y. nterfaces.push_serv ce.PushServ ceV s b l yL braryUt l._
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.V e rContext

object T etType extends Enu rat on {
  type T etType = Value
  val OR G NAL, SOURCE, QUOTED = Value
}
 mport com.tw ter.v s b l y. nterfaces.push_serv ce.T etType._

object PushServ ceV s b l yL brary {
  type Type = PushServ ceV s b l yRequest => St ch[PushServ ceV s b l yResponse]

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    stratoCl ent: StratoCl ent,
    enablePar yTest: Gate[Un ] = Gate.False,
    cac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult] = ReadableStore.empty,
    safeCac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult] = ReadableStore.empty,
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Type = {
    val stats = statsRece ver.scope("push_serv ce_vf")
    val cand dateT etCounter = stats.counter("request_cnt")
    val allo dT etCounter = stats.counter("allow_cnt")
    val droppedT etCounter = stats.counter("drop_cnt")
    val fa ledT etCounter = stats.counter("fa l_cnt")
    val authorLabelsEmptyCount = stats.counter("author_labels_empty_cnt")
    val authorLabelsCount = stats.counter("author_labels_cnt")

    val t etLabelMaps = new StratoT etLabelMaps(
      SafetyLabelMapS ce.fromSafetyLabelMapFetc r(
        PushServ ceSafetyLabelMapFetc r(stratoCl ent, stats)))

    val v e rFeatures = new V e rFeatures(UserS ce.empty, stats)
    val t etFeatures = new T etFeatures(t etLabelMaps, stats)
    val authorFeatures = new AuthorFeatures(userS ce, stats)
    val relat onsh pFeatures = new Relat onsh pFeatures(UserRelat onsh pS ce.empty, stats)

    val par yTester = new PushServ ceV s b l yL braryPar y(
      cac dT etyP eStoreV2,
      safeCac dT etyP eStoreV2
    )(statsRece ver)

    def bu ldFeatureMap(
      request: PushServ ceV s b l yRequest,
      t et: T et,
      t etType: T etType,
      author: Opt on[User] = None,
    ): FeatureMap = {
      val author d = author.map(_. d) orElse getAuthor d(t et)
      (author.map(authorFeatures.forAuthor(_)) orElse
        getAuthor d(t et).map(authorFeatures.forAuthor d(_))) match {
        case So (authorV s b l yFeatures) =>
          v s b l yL brary.featureMapBu lder(
            Seq(
              v e rFeatures.forV e rContext(V e rContext.fromContextW hV e r dFallback(None)),
              t etFeatures.forT et(t et),
              authorV s b l yFeatures,
              relat onsh pFeatures.forAuthor d(author d.get, None),
              _.w hConstantFeature(T et s nnerQuotedT et, t etType == QUOTED),
              _.w hConstantFeature(T et sRet et, request. sRet et),
              _.w hConstantFeature(T et sS ceT et, t etType == SOURCE)
            )
          )
        case _ =>
          v s b l yL brary.featureMapBu lder(
            Seq(
              v e rFeatures.forV e rContext(V e rContext.fromContextW hV e r dFallback(None)),
              t etFeatures.forT et(t et),
              _.w hConstantFeature(T et s nnerQuotedT et, t etType == QUOTED),
              _.w hConstantFeature(T et sRet et, request. sRet et),
              _.w hConstantFeature(T et sS ceT et, t etType == SOURCE)
            )
          )
      }
    }

    def runRuleEng neForT et(
      request: PushServ ceV s b l yRequest,
      t et: T et,
      t etType: T etType,
      author: Opt on[User] = None,
    ): St ch[V s b l yResult] = {
      val featureMap = bu ldFeatureMap(request, t et, t etType, author)
      val content d = Content d.T et d(t et. d)
      v s b l yL brary.runRuleEng ne(
        content d,
        featureMap,
        request.v e rContext,
        request.safetyLevel)
    }

    def runRuleEng neForAuthor(
      request: PushServ ceV s b l yRequest,
      t et: T et,
      t etType: T etType,
      author: Opt on[User] = None,
    ): St ch[V s b l yResult] = {
      val featureMap = bu ldFeatureMap(request, t et, t etType, author)
      val author d = author.map(_. d).getOrElse(getAuthor d(t et).get)
      val content d = Content d.User d(author d)
      v s b l yL brary.runRuleEng ne(
        content d,
        featureMap,
        request.v e rContext,
        request.safetyLevel)
    }

    def getAllV s b l yF lters(
      request: PushServ ceV s b l yRequest
    ): St ch[PushServ ceV s b l yResponse] = {
      val t etResult =
        runRuleEng neForT et(request, request.t et, OR G NAL, So (request.author))
      val authorResult =
        runRuleEng neForAuthor(request, request.t et, OR G NAL, So (request.author))
      val s ceT etResult = request.s ceT et
        .map(runRuleEng neForT et(request, _, SOURCE).map(So (_))).getOrElse(St ch.None)
      val quotedT etResult = request.quotedT et
        .map(runRuleEng neForT et(request, _, QUOTED).map(So (_))).getOrElse(St ch.None)

      St ch.jo n(t etResult, authorResult, s ceT etResult, quotedT etResult).map {
        case (t etResult, authorResult, s ceT etResult, quotedT etResult) =>
          PushServ ceV s b l yResponse(
            t etResult,
            authorResult,
            s ceT etResult,
            quotedT etResult)
      }
    }

    { request: PushServ ceV s b l yRequest =>
      cand dateT etCounter. ncr()

      request.author.labels match {
        case So (labels)  f (!labels._1. sEmpty) => authorLabelsCount. ncr()
        case _ => authorLabelsEmptyCount. ncr()
      }

      val response = getAllV s b l yF lters(request)
        .onSuccess { response =>
           f (response.shouldAllow) allo dT etCounter. ncr() else droppedT etCounter. ncr()
        }.onFa lure { _ => fa ledT etCounter. ncr() }

       f (enablePar yTest()) {
        response.applyEffect { resp => St ch.async(par yTester.runPar yTest(request, resp)) }
      } else {
        response
      }

    }
  }
}
