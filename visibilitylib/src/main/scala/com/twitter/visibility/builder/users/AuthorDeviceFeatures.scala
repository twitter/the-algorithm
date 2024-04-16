package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.UserDev ceS ce
 mport com.tw ter.v s b l y.features.AuthorHasConf r dEma l
 mport com.tw ter.v s b l y.features.AuthorHasVer f edPhone

class AuthorDev ceFeatures(userDev ceS ce: UserDev ceS ce, statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("author_dev ce_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val authorHasConf r dEma lRequests =
    scopedStatsRece ver.scope(AuthorHasConf r dEma l.na ).counter("requests")
  pr vate[t ] val authorHasVer f edPhoneRequests =
    scopedStatsRece ver.scope(AuthorHasVer f edPhone.na ).counter("requests")

  def forAuthor(author: User): FeatureMapBu lder => FeatureMapBu lder = forAuthor d(author. d)

  def forAuthor d(author d: Long): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hFeature(AuthorHasConf r dEma l, authorHasConf r dEma l(author d))
      .w hFeature(AuthorHasVer f edPhone, authorHasVer f edPhone(author d))
  }

  def authorHasConf r dEma l(author d: Long): St ch[Boolean] = {
    authorHasConf r dEma lRequests. ncr()
    userDev ceS ce.hasConf r dEma l(author d)
  }

  def authorHasVer f edPhone(author d: Long): St ch[Boolean] = {
    authorHasVer f edPhoneRequests. ncr()
    userDev ceS ce.hasConf r dPhone(author d)
  }
}
