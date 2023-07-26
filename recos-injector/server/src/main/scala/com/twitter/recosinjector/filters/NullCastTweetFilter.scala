package com.twittew.wecosinjectow.fiwtews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.wecosinjectow.cwients.tweetypie
i-impowt c-com.twittew.utiw.futuwe

/**
 * f-fiwtews tweets t-that awe nyuww c-cast, (ˆ ﻌ ˆ)♡ i.e. tweet is nyot dewivewed to a usew's fowwowews, (˘ω˘)
 * nyot shown in the u-usew's timewine, (⑅˘꒳˘) and does nyot appeaw in seawch w-wesuwts. (///ˬ///✿)
 * they awe mainwy ads t-tweets. 😳😳😳
 */
cwass nyuwwcasttweetfiwtew(
  tweetypie: tweetypie
)(
  i-impwicit statsweceivew: statsweceivew) {
  pwivate v-vaw stats = s-statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw wequests = stats.countew("wequests")
  pwivate vaw fiwtewed = s-stats.countew("fiwtewed")

  // wetuwn futuwe(twue) to keep the tweet. 🥺
  def fiwtew(tweetid: wong): f-futuwe[boowean] = {
    wequests.incw()
    t-tweetypie
      .gettweet(tweetid)
      .map { t-tweetopt =>
        // i-if the nyuww c-cast bit is some(twue), mya dwop the tweet. 🥺
        v-vaw isnuwwcasttweet = tweetopt.fwatmap(_.cowedata).exists(_.nuwwcast)
        if (isnuwwcasttweet) {
          f-fiwtewed.incw()
        }
        !isnuwwcasttweet
      }
  }
}
