package com.tw ter.v s b l y. nterfaces.dms

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.Dm d
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d.{Dm d => DmContent d}
 mport com.tw ter.v s b l y.models.SafetyLevel.D rect ssages
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules.Reason.Deact vatedAuthor
 mport com.tw ter.v s b l y.rules.Reason.ErasedAuthor
 mport com.tw ter.v s b l y.rules.Reason.Nsfw

object DmV s b l yL brary {
  type Type = DmV s b l yRequest => St ch[DmV s b l yResponse]

  case class DmV s b l yRequest(
    dm d: Dm d,
    dmAuthorUser d: User d,
    v e rContext: V e rContext)

  case class DmV s b l yResponse( s ssageNsfw: Boolean)

  val DefaultSafetyLevel: SafetyLevel = D rect ssages

  def apply(
    v s b l yL brary: V s b l yL brary,
    stratoCl ent: StratoCl ent,
    userS ce: UserS ce,
    enableVfFeatureHydrat on nSh m: Gate[Un ] = Gate.False
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val authorFeatures = new AuthorFeatures(userS ce, l braryStatsRece ver)

    { r: DmV s b l yRequest =>
      vfEng neCounter. ncr()

      val content d = DmContent d(r.dm d)
      val dmAuthorUser d = r.dmAuthorUser d
      val  sVfFeatureHydrat onEnabled = enableVfFeatureHydrat on nSh m()

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(authorFeatures.forAuthor d(dmAuthorUser d))
        )

      val resp =  f ( sVfFeatureHydrat onEnabled) {
        FeatureMap.resolve(featureMap, l braryStatsRece ver).flatMap { resolvedFeatureMap =>
          v s b l yL brary.runRuleEng ne(
            content d,
            resolvedFeatureMap,
            r.v e rContext,
            DefaultSafetyLevel
          )
        }
      } else {
        v s b l yL brary
          .runRuleEng ne(
            content d,
            featureMap,
            r.v e rContext,
            DefaultSafetyLevel
          )
      }

      resp.map(bu ldResponse)
    }
  }

  pr vate[t ] def bu ldResponse(v s b l yResult: V s b l yResult) =
    v s b l yResult.verd ct match {
      case Drop(Nsfw | ErasedAuthor | Deact vatedAuthor, _) =>
        DmV s b l yResponse( s ssageNsfw = true)
      case _ =>
        DmV s b l yResponse( s ssageNsfw = false)
    }

}
