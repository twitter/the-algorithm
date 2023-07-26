package com.twittew.wecosinjectow.fiwtews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.wecosinjectow.cwients.tweetypie
i-impowt c-com.twittew.utiw.futuwe

c-cwass t-tweetfiwtew(
  t-tweetypie: tweetypie
)(
  impwicit statsweceivew: statsweceivew) {
  pwivate vaw s-stats = statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw wequests = s-stats.countew("wequests")
  pwivate vaw fiwtewed = s-stats.countew("fiwtewed")

  /**
   * quewy tweetypie to see if we can fetch a-a tweet object successfuwwy. (U ﹏ U) t-tweetypie appwies a-a safety
   * fiwtew and wiww nyot wetuwn the tweet object if the fiwtew does n-nyot pass. >_<
   */
  def fiwtewfowtweetypiesafetywevew(tweetid: wong): futuwe[boowean] = {
    wequests.incw()
    tweetypie
      .gettweet(tweetid)
      .map {
        c-case some(_) =>
          twue
        c-case _ =>
          f-fiwtewed.incw()
          fawse
      }
  }
}
