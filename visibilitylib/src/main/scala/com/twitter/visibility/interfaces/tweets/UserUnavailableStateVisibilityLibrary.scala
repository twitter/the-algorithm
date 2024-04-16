package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.users.UserUnava lableFeatures
 mport com.tw ter.v s b l y.common.act ons.converter.scala.DropReasonConverter
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.T et s nnerQuotedT et
 mport com.tw ter.v s b l y.features.T et sRet et
 mport com.tw ter.v s b l y.generators.Local zed nterst  alGenerator
 mport com.tw ter.v s b l y.generators.TombstoneGenerator
 mport com.tw ter.v s b l y.models.Content d.UserUnava lableState
 mport com.tw ter.v s b l y.models.UserUnava lableStateEnum
 mport com.tw ter.v s b l y.rules.Drop
 mport com.tw ter.v s b l y.rules. nterst  al
 mport com.tw ter.v s b l y.rules.Reason
 mport com.tw ter.v s b l y.rules.Tombstone
 mport com.tw ter.v s b l y.thr ftscala.UserV s b l yResult

object UserUnava lableStateV s b l yL brary {
  type Type = UserUnava lableStateV s b l yRequest => St ch[V s b l yResult]

  def apply(
    v s b l yL brary: V s b l yL brary,
    dec der: Dec der,
    tombstoneGenerator: TombstoneGenerator,
     nterst  alGenerator: Local zed nterst  alGenerator
  ): Type = {
    val l braryStatsRece ver = v s b l yL brary.statsRece ver.scope("user_unava lable_v s_l brary")
    val defaultDropScope = v s b l yL brary.statsRece ver.scope("default_drop")
    val vfEng neCounter = l braryStatsRece ver.counter("vf_eng ne_requests")

    val userUnava lableFeatures = UserUnava lableFeatures(l braryStatsRece ver)
    val v s b l yDec derGates = V s b l yDec derGates(dec der)

    { r: UserUnava lableStateV s b l yRequest =>
      vfEng neCounter. ncr()
      val content d = UserUnava lableState(r.t et d)

      val featureMap =
        v s b l yL brary.featureMapBu lder(
          Seq(
            _.w hConstantFeature(T et s nnerQuotedT et, r. s nnerQuotedT et),
            _.w hConstantFeature(T et sRet et, r. sRet et),
            userUnava lableFeatures.forState(r.userUnava lableState)
          )
        )

      val language = r.v e rContext.requestLanguageCode.getOrElse("en")

      val reason = v s b l yL brary
        .runRuleEng ne(
          content d,
          featureMap,
          r.v e rContext,
          r.safetyLevel
        ).map(defaultToDrop(r.userUnava lableState, defaultDropScope))
        .map(tombstoneGenerator(_, language))
        .map(v s b l yResult => {
           f (v s b l yDec derGates.enableLocal zed nterst  al nUserStateL brary()) {
             nterst  alGenerator(v s b l yResult, language)
          } else {
            v s b l yResult
          }
        })

      reason
    }
  }

  def defaultToDrop(
    userUnava lableState: UserUnava lableStateEnum,
    defaultDropScope: StatsRece ver
  )(
    result: V s b l yResult
  ): V s b l yResult =
    result.verd ct match {
      case _: Drop | _: Tombstone => result

      case _:  nterst  al => result
      case _ =>
        result.copy(verd ct =
          Drop(userUnava lableStateToDropReason(userUnava lableState, defaultDropScope)))
    }

  pr vate[t ] def userUnava lableStateToDropReason(
    userUnava lableState: UserUnava lableStateEnum,
    stats: StatsRece ver
  ): Reason =
    userUnava lableState match {
      case UserUnava lableStateEnum.Erased =>
        stats.counter("erased"). ncr()
        Reason.ErasedAuthor
      case UserUnava lableStateEnum.Protected =>
        stats.counter("protected"). ncr()
        Reason.ProtectedAuthor
      case UserUnava lableStateEnum.Offboarded =>
        stats.counter("offboarded"). ncr()
        Reason.OffboardedAuthor
      case UserUnava lableStateEnum.AuthorBlocksV e r =>
        stats.counter("author_blocks_v e r"). ncr()
        Reason.AuthorBlocksV e r
      case UserUnava lableStateEnum.Suspended =>
        stats.counter("suspended_author"). ncr()
        Reason.SuspendedAuthor
      case UserUnava lableStateEnum.Deact vated =>
        stats.counter("deact vated_author"). ncr()
        Reason.Deact vatedAuthor
      case UserUnava lableStateEnum.F ltered(result) =>
        stats.counter("f ltered"). ncr()
        userV s b l yResultToDropReason(result, stats.scope("f ltered"))
      case UserUnava lableStateEnum.Unava lable =>
        stats.counter("unspec f ed"). ncr()
        Reason.Unspec f ed
      case _ =>
        stats.counter("unknown"). ncr()
        stats.scope("unknown").counter(userUnava lableState.na ). ncr()
        Reason.Unspec f ed
    }

  pr vate[t ] def userV s b l yResultToDropReason(
    result: UserV s b l yResult,
    stats: StatsRece ver
  ): Reason =
    result.act on
      .flatMap(DropReasonConverter.fromAct on)
      .map { dropReason =>
        val reason = Reason.fromDropReason(dropReason)
        stats.counter(reason.na ). ncr()
        reason
      }.getOrElse {
        stats.counter("empty")
        Reason.Unspec f ed
      }
}
