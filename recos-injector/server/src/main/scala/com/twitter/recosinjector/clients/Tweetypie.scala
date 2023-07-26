package com.twittew.wecosinjectow.cwients

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.stitch.tweetypie.tweetypie.{tweetypieexception, rawr x3 t-tweetypiewesuwt}
i-impowt c-com.twittew.stowehaus.weadabwestowe
i-impowt com.twittew.tweetypie.thwiftscawa.tweet
i-impowt com.twittew.utiw.futuwe

cwass tweetypie(
  tweetypiestowe: weadabwestowe[wong, nyaa~~ tweetypiewesuwt]
)(
  i-impwicit statsweceivew: statsweceivew) {
  pwivate vaw stats = s-statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw f-faiwuwestats = stats.scope("gettweetfaiwuwe")

  def gettweet(tweetid: wong): f-futuwe[option[tweet]] = {
    tweetypiestowe
      .get(tweetid)
      .map { _.map(_.tweet) }
      .wescue {
        c-case e: tweetypieexception =>
          // u-usuawwy wesuwts fwom twying to quewy a pwotected ow unsafe tweet
          faiwuwestats.scope("tweetypieexception").countew(e.wesuwt.tweetstate.tostwing).incw()
          f-futuwe.none
        case e =>
          faiwuwestats.countew(e.getcwass.getsimpwename).incw()
          futuwe.none
      }
  }
}
