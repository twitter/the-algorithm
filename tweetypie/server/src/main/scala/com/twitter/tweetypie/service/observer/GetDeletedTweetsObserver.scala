package com.twittew.tweetypie
package s-sewvice
package o-obsewvew

impowt c-com.twittew.sewvo.exception.thwiftscawa.cwientewwow
i-impowt c-com.twittew.tweetypie.thwiftscawa.getdewetedtweetwesuwt
i-impowt c-com.twittew.tweetypie.thwiftscawa.getdewetedtweetswequest

p-pwivate[sewvice] object getdewetedtweetsobsewvew {
  type type = obsewveexchange[getdewetedtweetswequest, mya seq[getdewetedtweetwesuwt]]

  d-def obsewveexchange(stats: statsweceivew): effect[type] = {
    vaw wesuwtstatestats = wesuwtstatestats(stats)

    e-effect {
      case (wequest, mya w-wesponse) =>
        wesponse match {
          case wetuwn(_) | t-thwow(cwientewwow(_)) =>
            wesuwtstatestats.success(wequest.tweetids.size)
          c-case thwow(_) =>
            w-wesuwtstatestats.faiwed(wequest.tweetids.size)
        }
    }
  }
}
