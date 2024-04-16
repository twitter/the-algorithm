package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.user_result.UserV s b l yResult lper
 mport com.tw ter.v s b l y.features.AuthorBlocksV e r
 mport com.tw ter.v s b l y.features.Author sDeact vated
 mport com.tw ter.v s b l y.features.Author sErased
 mport com.tw ter.v s b l y.features.Author sOffboarded
 mport com.tw ter.v s b l y.features.Author sProtected
 mport com.tw ter.v s b l y.features.Author sSuspended
 mport com.tw ter.v s b l y.features.Author sUnava lable
 mport com.tw ter.v s b l y.features.V e rBlocksAuthor
 mport com.tw ter.v s b l y.features.V e rMutesAuthor
 mport com.tw ter.v s b l y.models.UserUnava lableStateEnum

case class UserUnava lableFeatures(statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("user_unava lable_features")
  pr vate[t ] val suspendedAuthorStats = scopedStatsRece ver.scope("suspended_author")
  pr vate[t ] val deact vatedAuthorStats = scopedStatsRece ver.scope("deact vated_author")
  pr vate[t ] val offboardedAuthorStats = scopedStatsRece ver.scope("offboarded_author")
  pr vate[t ] val erasedAuthorStats = scopedStatsRece ver.scope("erased_author")
  pr vate[t ] val protectedAuthorStats = scopedStatsRece ver.scope("protected_author")
  pr vate[t ] val authorBlocksV e rStats = scopedStatsRece ver.scope("author_blocks_v e r")
  pr vate[t ] val v e rBlocksAuthorStats = scopedStatsRece ver.scope("v e r_blocks_author")
  pr vate[t ] val v e rMutesAuthorStats = scopedStatsRece ver.scope("v e r_mutes_author")
  pr vate[t ] val unava lableStats = scopedStatsRece ver.scope("unava lable")

  def forState(state: UserUnava lableStateEnum): FeatureMapBu lder => FeatureMapBu lder = {
    bu lder =>
      bu lder
        .w hConstantFeature(Author sSuspended,  sSuspended(state))
        .w hConstantFeature(Author sDeact vated,  sDeact vated(state))
        .w hConstantFeature(Author sOffboarded,  sOffboarded(state))
        .w hConstantFeature(Author sErased,  sErased(state))
        .w hConstantFeature(Author sProtected,  sProtected(state))
        .w hConstantFeature(AuthorBlocksV e r, authorBlocksV e r(state))
        .w hConstantFeature(V e rBlocksAuthor, v e rBlocksAuthor(state))
        .w hConstantFeature(V e rMutesAuthor, v e rMutesAuthor(state))
        .w hConstantFeature(Author sUnava lable,  sUnava lable(state))
  }

  pr vate[t ] def  sSuspended(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Suspended =>
        suspendedAuthorStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropSuspendedAuthor(result) =>
        suspendedAuthorStats.counter(). ncr()
        suspendedAuthorStats.counter("f ltered"). ncr()
        true
      case _ => false
    }

  pr vate[t ] def  sDeact vated(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Deact vated =>
        deact vatedAuthorStats.counter(). ncr()
        true
      case _ => false
    }

  pr vate[t ] def  sOffboarded(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Offboarded =>
        offboardedAuthorStats.counter(). ncr()
        true
      case _ => false
    }

  pr vate[t ] def  sErased(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Erased =>
        erasedAuthorStats.counter(). ncr()
        true
      case _ => false
    }

  pr vate[t ] def  sProtected(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Protected =>
        protectedAuthorStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropProtectedAuthor(result) =>
        protectedAuthorStats.counter(). ncr()
        protectedAuthorStats.counter("f ltered"). ncr()
        true
      case _ => false
    }

  pr vate[t ] def authorBlocksV e r(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.AuthorBlocksV e r =>
        authorBlocksV e rStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropAuthorBlocksV e r(result) =>
        authorBlocksV e rStats.counter(). ncr()
        authorBlocksV e rStats.counter("f ltered"). ncr()
        true
      case _ => false
    }

  pr vate[t ] def v e rBlocksAuthor(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.V e rBlocksAuthor =>
        v e rBlocksAuthorStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropV e rBlocksAuthor(result) =>
        v e rBlocksAuthorStats.counter(). ncr()
        v e rBlocksAuthorStats.counter("f ltered"). ncr()
        true
      case _ => false
    }

  pr vate[t ] def v e rMutesAuthor(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.V e rMutesAuthor =>
        v e rMutesAuthorStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropV e rMutesAuthor(result) =>
        v e rMutesAuthorStats.counter(). ncr()
        v e rMutesAuthorStats.counter("f ltered"). ncr()
        true
      case _ => false
    }

  pr vate[t ] def  sUnava lable(state: UserUnava lableStateEnum): Boolean =
    state match {
      case UserUnava lableStateEnum.Unava lable =>
        unava lableStats.counter(). ncr()
        true
      case UserUnava lableStateEnum.F ltered(result)
           f UserV s b l yResult lper. sDropUnspec f edAuthor(result) =>
        unava lableStats.counter(). ncr()
        unava lableStats.counter("f ltered"). ncr()
        true
      case _ => false
    }
}
