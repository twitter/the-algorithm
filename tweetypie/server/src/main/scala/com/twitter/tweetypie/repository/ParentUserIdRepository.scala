package com.twittew.tweetypie
package w-wepositowy

i-impowt com.twittew.stitch.notfound
i-impowt com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.cowe.fiwtewedstate.unavaiwabwe.bouncedeweted
impowt c-com.twittew.tweetypie.cowe.fiwtewedstate.unavaiwabwe.souwcetweetnotfound
impowt c-com.twittew.tweetypie.cowe.fiwtewedstate.unavaiwabwe.tweetdeweted

o-object p-pawentusewidwepositowy {
  type type = tweet => stitch[option[usewid]]

  case cwass p-pawenttweetnotfound(tweetid: tweetid) extends exception

  d-def appwy(tweetwepo: tweetwepositowy.type): t-type = {
    vaw options = tweetquewy.options(tweetquewy.incwude(set(tweet.cowedatafiewd.id)))

    tweet =>
      getshawe(tweet) match {
        case s-some(shawe) if shawe.souwcestatusid == s-shawe.pawentstatusid =>
          s-stitch.vawue(some(shawe.souwceusewid))
        case some(shawe) =>
          tweetwepo(shawe.pawentstatusid, OwO options)
            .map(tweet => s-some(getusewid(tweet)))
            .wescue {
              case nyotfound | tweetdeweted | bouncedeweted | souwcetweetnotfound(_) =>
                s-stitch.exception(pawenttweetnotfound(shawe.pawentstatusid))
            }
        case nyone =>
          s-stitch.none
      }
  }
}
