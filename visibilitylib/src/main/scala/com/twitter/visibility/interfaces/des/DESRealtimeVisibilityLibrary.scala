package com.tw ter.v s b l y. nterfaces.des

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.Commun yT etFeaturesV2
 mport com.tw ter.v s b l y.bu lder.t ets.Ed T etFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.Exclus veT etFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.N lT etLabelMaps
 mport com.tw ter.v s b l y.bu lder.t ets.TrustedFr endsFeatures
 mport com.tw ter.v s b l y.bu lder.t ets.T etFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.Commun  esS ce
 mport com.tw ter.v s b l y.common.TrustedFr endsS ce
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.Allow
 mport com.tw ter.v s b l y.{thr ftscala => vfthr ft}

case class DESRealt  V s b l yRequest(t et: T et, author: User, v e r: Opt on[User])

object DESRealt  V s b l yL brary {
  type Type = DESRealt  V s b l yRequest => St ch[vfthr ft.Act on]

  pr vate[t ] val safetyLevel = SafetyLevel.DesRealt  

  def apply(v s b l yL brary: V s b l yL brary): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val t etFeatures = new T etFeatures(N lT etLabelMaps, l braryStatsRece ver)

    val authorFeatures = new AuthorFeatures(UserS ce.empty, l braryStatsRece ver)
    val v e rFeatures = new V e rFeatures(UserS ce.empty, l braryStatsRece ver)
    val commun yT etFeatures = new Commun yT etFeaturesV2(Commun  esS ce.empty)
    val exclus veT etFeatures =
      new Exclus veT etFeatures(UserRelat onsh pS ce.empty, l braryStatsRece ver)
    val trustedFr endsT etFeatures = new TrustedFr endsFeatures(TrustedFr endsS ce.empty)
    val ed T etFeatures = new Ed T etFeatures(l braryStatsRece ver)

    { request: DESRealt  V s b l yRequest =>
      vfEng neCounter. ncr()

      val t et = request.t et
      val author = request.author
      val v e r = request.v e r
      val v e rContext = V e rContext.fromContext

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            t etFeatures.forT etW houtSafetyLabels(t et),
            authorFeatures.forAuthorNoDefaults(author),
            v e rFeatures.forV e rNoDefaults(v e r),
            commun yT etFeatures.forT etOnly(t et),
            exclus veT etFeatures.forT etOnly(t et),
            trustedFr endsT etFeatures.forT etOnly(t et),
            ed T etFeatures.forT et(t et),
          )
        )

      val t etResult = v s b l yL brary.runRuleEng ne(
        Content d.T et d(t et. d),
        featureMap,
        v e rContext,
        safetyLevel
      )
      val authorResult = v s b l yL brary.runRuleEng ne(
        Content d.User d(author. d),
        featureMap,
        v e rContext,
        safetyLevel
      )

      St ch.jo n(t etResult, authorResult).map {
        case (t etResult, authorResult) =>  rgeResults(t etResult, authorResult)
      }
    }
  }

  def  rgeResults(
    t etResult: V s b l yResult,
    authorResult: V s b l yResult,
  ): vfthr ft.Act on = {
    Set(t etResult.verd ct, authorResult.verd ct)
      .f nd {
        case Allow => false
        case _ => true
      }
      .map(_.toAct onThr ft())
      .getOrElse(Allow.toAct onThr ft())
  }
}
