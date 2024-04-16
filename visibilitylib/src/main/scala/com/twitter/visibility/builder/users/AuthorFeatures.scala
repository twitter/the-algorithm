package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.g zmoduck.thr ftscala.Labels
 mport com.tw ter.g zmoduck.thr ftscala.Prof le
 mport com.tw ter.g zmoduck.thr ftscala.Safety
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features._

class AuthorFeatures(userS ce: UserS ce, statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("author_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val authorUserLabels =
    scopedStatsRece ver.scope(AuthorUserLabels.na ).counter("requests")
  pr vate[t ] val author sSuspended =
    scopedStatsRece ver.scope(Author sSuspended.na ).counter("requests")
  pr vate[t ] val author sProtected =
    scopedStatsRece ver.scope(Author sProtected.na ).counter("requests")
  pr vate[t ] val author sDeact vated =
    scopedStatsRece ver.scope(Author sDeact vated.na ).counter("requests")
  pr vate[t ] val author sErased =
    scopedStatsRece ver.scope(Author sErased.na ).counter("requests")
  pr vate[t ] val author sOffboarded =
    scopedStatsRece ver.scope(Author sOffboarded.na ).counter("requests")
  pr vate[t ] val author sNsfwUser =
    scopedStatsRece ver.scope(Author sNsfwUser.na ).counter("requests")
  pr vate[t ] val author sNsfwAdm n =
    scopedStatsRece ver.scope(Author sNsfwAdm n.na ).counter("requests")
  pr vate[t ] val authorTakedownReasons =
    scopedStatsRece ver.scope(AuthorTakedownReasons.na ).counter("requests")
  pr vate[t ] val authorHasDefaultProf le mage =
    scopedStatsRece ver.scope(AuthorHasDefaultProf le mage.na ).counter("requests")
  pr vate[t ] val authorAccountAge =
    scopedStatsRece ver.scope(AuthorAccountAge.na ).counter("requests")
  pr vate[t ] val author sVer f ed =
    scopedStatsRece ver.scope(Author sVer f ed.na ).counter("requests")
  pr vate[t ] val authorScreenNa  =
    scopedStatsRece ver.scope(AuthorScreenNa .na ).counter("requests")
  pr vate[t ] val author sBlueVer f ed =
    scopedStatsRece ver.scope(Author sBlueVer f ed.na ).counter("requests")

  def forAuthor(author: User): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hConstantFeature(Author d, Set(author. d))
      .w hConstantFeature(AuthorUserLabels, authorUserLabels(author))
      .w hConstantFeature(Author sProtected, author sProtected(author))
      .w hConstantFeature(Author sSuspended, author sSuspended(author))
      .w hConstantFeature(Author sDeact vated, author sDeact vated(author))
      .w hConstantFeature(Author sErased, author sErased(author))
      .w hConstantFeature(Author sOffboarded, author sOffboarded(author))
      .w hConstantFeature(AuthorTakedownReasons, authorTakedownReasons(author))
      .w hConstantFeature(AuthorHasDefaultProf le mage, authorHasDefaultProf le mage(author))
      .w hConstantFeature(AuthorAccountAge, authorAccountAge(author))
      .w hConstantFeature(Author sNsfwUser, author sNsfwUser(author))
      .w hConstantFeature(Author sNsfwAdm n, author sNsfwAdm n(author))
      .w hConstantFeature(Author sVer f ed, author sVer f ed(author))
      .w hConstantFeature(AuthorScreenNa , authorScreenNa (author))
      .w hConstantFeature(Author sBlueVer f ed, author sBlueVer f ed(author))
  }

  def forAuthorNoDefaults(author: User): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hConstantFeature(Author d, Set(author. d))
      .w hConstantFeature(AuthorUserLabels, authorUserLabelsOpt(author))
      .w hConstantFeature(Author sProtected, author sProtectedOpt(author))
      .w hConstantFeature(Author sSuspended, author sSuspendedOpt(author))
      .w hConstantFeature(Author sDeact vated, author sDeact vatedOpt(author))
      .w hConstantFeature(Author sErased, author sErasedOpt(author))
      .w hConstantFeature(Author sOffboarded, author sOffboarded(author))
      .w hConstantFeature(AuthorTakedownReasons, authorTakedownReasons(author))
      .w hConstantFeature(AuthorHasDefaultProf le mage, authorHasDefaultProf le mage(author))
      .w hConstantFeature(AuthorAccountAge, authorAccountAge(author))
      .w hConstantFeature(Author sNsfwUser, author sNsfwUserOpt(author))
      .w hConstantFeature(Author sNsfwAdm n, author sNsfwAdm nOpt(author))
      .w hConstantFeature(Author sVer f ed, author sVer f edOpt(author))
      .w hConstantFeature(AuthorScreenNa , authorScreenNa (author))
      .w hConstantFeature(Author sBlueVer f ed, author sBlueVer f ed(author))
  }

  def forAuthor d(author d: Long): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hConstantFeature(Author d, Set(author d))
      .w hFeature(AuthorUserLabels, authorUserLabels(author d))
      .w hFeature(Author sProtected, author sProtected(author d))
      .w hFeature(Author sSuspended, author sSuspended(author d))
      .w hFeature(Author sDeact vated, author sDeact vated(author d))
      .w hFeature(Author sErased, author sErased(author d))
      .w hFeature(Author sOffboarded, author sOffboarded(author d))
      .w hFeature(AuthorTakedownReasons, authorTakedownReasons(author d))
      .w hFeature(AuthorHasDefaultProf le mage, authorHasDefaultProf le mage(author d))
      .w hFeature(AuthorAccountAge, authorAccountAge(author d))
      .w hFeature(Author sNsfwUser, author sNsfwUser(author d))
      .w hFeature(Author sNsfwAdm n, author sNsfwAdm n(author d))
      .w hFeature(Author sVer f ed, author sVer f ed(author d))
      .w hFeature(AuthorScreenNa , authorScreenNa (author d))
      .w hFeature(Author sBlueVer f ed, author sBlueVer f ed(author d))
  }

  def forNoAuthor(): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(Author d, Set.empty[Long])
      .w hConstantFeature(AuthorUserLabels, Seq.empty)
      .w hConstantFeature(Author sProtected, false)
      .w hConstantFeature(Author sSuspended, false)
      .w hConstantFeature(Author sDeact vated, false)
      .w hConstantFeature(Author sErased, false)
      .w hConstantFeature(Author sOffboarded, false)
      .w hConstantFeature(AuthorTakedownReasons, Seq.empty)
      .w hConstantFeature(AuthorHasDefaultProf le mage, false)
      .w hConstantFeature(AuthorAccountAge, Durat on.Zero)
      .w hConstantFeature(Author sNsfwUser, false)
      .w hConstantFeature(Author sNsfwAdm n, false)
      .w hConstantFeature(Author sVer f ed, false)
      .w hConstantFeature(Author sBlueVer f ed, false)
  }

  def authorUserLabels(author: User): Seq[Label] =
    authorUserLabels(author.labels)

  def author sSuspended(author d: Long): St ch[Boolean] =
    userS ce.getSafety(author d).map(safety => author sSuspended(So (safety)))

  def author sSuspendedOpt(author: User): Opt on[Boolean] = {
    author sSuspended. ncr()
    author.safety.map(_.suspended)
  }

  pr vate def author sSuspended(safety: Opt on[Safety]): Boolean = {
    author sSuspended. ncr()
    safety.ex sts(_.suspended)
  }

  def author sProtected(author: User): Boolean =
    author sProtected(author.safety)

  def author sDeact vated(author d: Long): St ch[Boolean] =
    userS ce.getSafety(author d).map(safety => author sDeact vated(So (safety)))

  def author sDeact vatedOpt(author: User): Opt on[Boolean] = {
    author sDeact vated. ncr()
    author.safety.map(_.deact vated)
  }

  pr vate def author sDeact vated(safety: Opt on[Safety]): Boolean = {
    author sDeact vated. ncr()
    safety.ex sts(_.deact vated)
  }

  def author sErased(author: User): Boolean = {
    author sErased. ncr()
    author.safety.ex sts(_.erased)
  }

  def author sOffboarded(author d: Long): St ch[Boolean] = {
    userS ce.getSafety(author d).map(safety => author sOffboarded(So (safety)))
  }

  def author sNsfwUser(author: User): Boolean = {
    author sNsfwUser(author.safety)
  }

  def author sNsfwUser(author d: Long): St ch[Boolean] = {
    userS ce.getSafety(author d).map(safety => author sNsfwUser(So (safety)))
  }

  def author sNsfwUser(safety: Opt on[Safety]): Boolean = {
    author sNsfwUser. ncr()
    safety.ex sts(_.nsfwUser)
  }

  def author sNsfwAdm nOpt(author: User): Opt on[Boolean] = {
    author sNsfwAdm n. ncr()
    author.safety.map(_.nsfwAdm n)
  }

  def authorTakedownReasons(author d: Long): St ch[Seq[TakedownReason]] = {
    authorTakedownReasons. ncr()
    userS ce.getTakedownReasons(author d)
  }

  def authorHasDefaultProf le mage(author d: Long): St ch[Boolean] =
    userS ce.getProf le(author d).map(prof le => authorHasDefaultProf le mage(So (prof le)))

  def authorAccountAge(author d: Long): St ch[Durat on] =
    userS ce.getCreatedAtMsec(author d).map(authorAccountAgeFromT  stamp)

  def author sVer f ed(author d: Long): St ch[Boolean] =
    userS ce.getSafety(author d).map(safety => author sVer f ed(So (safety)))

  def author sVer f edOpt(author: User): Opt on[Boolean] = {
    author sVer f ed. ncr()
    author.safety.map(_.ver f ed)
  }

  pr vate def author sVer f ed(safety: Opt on[Safety]): Boolean = {
    author sVer f ed. ncr()
    safety.ex sts(_.ver f ed)
  }

  def authorScreenNa (author: User): Opt on[Str ng] = {
    authorScreenNa . ncr()
    author.prof le.map(_.screenNa )
  }

  def authorScreenNa (author d: Long): St ch[Str ng] = {
    authorScreenNa . ncr()
    userS ce.getProf le(author d).map(prof le => prof le.screenNa )
  }
}
