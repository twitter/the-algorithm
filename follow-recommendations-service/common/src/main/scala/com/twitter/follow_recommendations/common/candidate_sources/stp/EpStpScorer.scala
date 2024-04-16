package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.b ject on.thr ft.B naryThr ftCodec
 mport com.tw ter.relevance.ep_model.scorer.EPScorer
 mport com.tw ter.relevance.ep_model.scorer.ScorerUt l
 mport com.tw ter.relevance.ep_model.thr ft
 mport com.tw ter.relevance.ep_model.thr ftscala.EPScor ngOpt ons
 mport com.tw ter.relevance.ep_model.thr ftscala.EPScor ngRequest
 mport com.tw ter.relevance.ep_model.thr ftscala.EPScor ngResponse
 mport com.tw ter.relevance.ep_model.thr ftscala.Record
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._
 mport scala.ut l.Success

case class ScoredResponse(score: Double, featuresBreakdown: Opt on[Str ng] = None)

/**
 * STP ML ranker tra ned us ng pre tor c ML fra work
 */
@S ngleton
class EpStpScorer @ nject() (epScorer: EPScorer) {
  pr vate def getScore(responses: L st[EPScor ngResponse]): Opt on[ScoredResponse] =
    responses. adOpt on
      .flatMap { response =>
        response.scores.flatMap {
          _. adOpt on.map(score => ScoredResponse(ScorerUt l.normal ze(score)))
        }
      }

  def getScoredResponse(
    record: Record,
    deta ls: Boolean = false
  ): St ch[Opt on[ScoredResponse]] = {
    val scor ngOpt ons = EPScor ngOpt ons(
      addFeaturesBreakDown = deta ls,
      addTransfor r nter d ateRecords = deta ls
    )
    val request = EPScor ngRequest(auxFeatures = So (Seq(record)), opt ons = So (scor ngOpt ons))

    St ch.callFuture(
      B naryThr ftCodec[thr ft.EPScor ngRequest]
        . nvert(B naryScalaCodec(EPScor ngRequest).apply(request))
        .map { thr ftRequest: thr ft.EPScor ngRequest =>
          val responsesF = epScorer
            .score(L st(thr ftRequest).asJava)
            .map(
              _.asScala.toL st
                .map(response =>
                  B naryScalaCodec(EPScor ngResponse)
                    . nvert(B naryThr ftCodec[thr ft.EPScor ngResponse].apply(response)))
                .collect { case Success(response) => response }
            )
          responsesF.map(getScore)
        }
        .getOrElse(Future(None)))
  }
}

object EpStpScorer {
  val W hFeaturesBreakDown = false
}
