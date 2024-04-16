package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.QuotedT etFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.QuotedT etRelat onsh p
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.UserUnava lableStateEnum
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.Reason.AuthorBlocksV e r
 mport com.tw ter.v s b l y.rules.Reason.Deact vatedAuthor
 mport com.tw ter.v s b l y.rules.Reason.ErasedAuthor
 mport com.tw ter.v s b l y.rules.Reason.OffboardedAuthor
 mport com.tw ter.v s b l y.rules.Reason.ProtectedAuthor
 mport com.tw ter.v s b l y.rules.Reason.SuspendedAuthor
 mport com.tw ter.v s b l y.rules.Reason.V e rBlocksAuthor
 mport com.tw ter.v s b l y.rules.Reason.V e rHardMutedAuthor
 mport com.tw ter.v s b l y.rules.Reason.V e rMutesAuthor
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.ut ls.Sh mUt ls

case class T etAndAuthor(t et d: Long, author d: Long)

case class QuotedT etV s b l yRequest(
  quotedT et: T etAndAuthor,
  outerT et: T etAndAuthor,
  v e rContext: V e rContext,
  safetyLevel: SafetyLevel)

object QuotedT etV s b l yL brary {

  type Type = QuotedT etV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    userS ce: UserS ce,
    userRelat onsh pS ce: UserRelat onsh pS ce,
    dec der: Dec der,
    userStateV s b l yL brary: UserUnava lableStateV s b l yL brary.Type,
    enableVfFeatureHydrat on: Gate[Un ] = Gate.False
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val v s b l yDec derGates = V s b l yDec derGates(dec der)
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    {
      case QuotedT etV s b l yRequest(quotedT et, outerT et, v e rContext, safetyLevel) =>
        vfEng neCounter. ncr()
        val content d = QuotedT etRelat onsh p(
          outer = outerT et.t et d,
           nner = quotedT et.t et d
        )

        val  nnerAuthor d = quotedT et.author d
        val outerAuthor d = outerT et.author d
        val v e r d = v e rContext.user d
        val  sFeatureHydrat on nSh mEnabled = enableVfFeatureHydrat on()

        val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)
        val v e rFeatures = new V e rFeatures(userS ce, l braryStatsRece ver)
        val relat onsh pFeatures =
          new Relat onsh pFeatures(userRelat onsh pS ce, l braryStatsRece ver)
        val quotedT etFeatures =
          new QuotedT etFeatures(relat onsh pFeatures, l braryStatsRece ver)

        val featureMap = v s b l yL brary.featureMapBu lder(
          Seq(
            v e rFeatures.forV e rContext(v e rContext),
            authorFeatures.forAuthor d( nnerAuthor d),
            relat onsh pFeatures.forAuthor d( nnerAuthor d, v e r d),
            quotedT etFeatures.forOuterAuthor(outerAuthor d,  nnerAuthor d)
          )
        )

        val resp =  f ( sFeatureHydrat on nSh mEnabled) {
          val evaluat onContext = Prov dedEvaluat onContext. njectRunt  Rules ntoEvaluat onContext(
            evaluat onContext = Evaluat onContext(
              SafetyLevel.QuotedT etRules,
              v s b l yL brary.getParams(v e rContext, SafetyLevel.QuotedT etRules),
              v s b l yL brary.statsRece ver)
          )

          val preF lteredFeatureMap =
            Sh mUt ls.preF lterFeatureMap(
              featureMap,
              SafetyLevel.QuotedT etRules,
              content d,
              evaluat onContext)

          FeatureMap.resolve(preF lteredFeatureMap, l braryStatsRece ver).flatMap {
            resolvedFeatureMap =>
              v s b l yL brary
                .runRuleEng ne(
                  content d,
                  resolvedFeatureMap,
                  v e rContext,
                  SafetyLevel.QuotedT etRules
                )
          }
        } else {
          v s b l yL brary
            .runRuleEng ne(
              content d,
              featureMap,
              v e rContext,
              SafetyLevel.QuotedT etRules
            )
        }

        resp.flatMap { v sResult =>
          val userStateOpt = v sResult.verd ct match {
            case Drop(Deact vatedAuthor, _) => So (UserUnava lableStateEnum.Deact vated)
            case Drop(OffboardedAuthor, _) => So (UserUnava lableStateEnum.Offboarded)
            case Drop(ErasedAuthor, _) => So (UserUnava lableStateEnum.Erased)
            case Drop(ProtectedAuthor, _) => So (UserUnava lableStateEnum.Protected)
            case Drop(SuspendedAuthor, _) => So (UserUnava lableStateEnum.Suspended)
            case Drop(AuthorBlocksV e r, _) => So (UserUnava lableStateEnum.AuthorBlocksV e r)
            case Drop(V e rBlocksAuthor, _) => So (UserUnava lableStateEnum.V e rBlocksAuthor)
            case Drop(V e rMutesAuthor, _) => So (UserUnava lableStateEnum.V e rMutesAuthor)
            case Drop(V e rHardMutedAuthor, _) => So (UserUnava lableStateEnum.V e rMutesAuthor)
            case _ => None
          }

          userStateOpt
            .map(userState =>
              userStateV s b l yL brary(
                UserUnava lableStateV s b l yRequest(
                  safetyLevel,
                  quotedT et.t et d,
                  v e rContext,
                  userState,
                   sRet et = false,
                   s nnerQuotedT et = true,
                ))).getOrElse(St ch.value(v sResult))
        }
    }
  }
}
