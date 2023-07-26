package com.twittew.timewinewankew.modew

impowt c-com.twittew.timewinewankew.{thwiftscawa => t-thwift}
i-impowt com.twittew.timewines.modew.tweetid

object t-tweetidwange {
  v-vaw defauwt: t-tweetidwange = t-tweetidwange(none, /(^•ω•^) n-nyone)
  vaw empty: tweetidwange = tweetidwange(some(0w), some(0w))

  def fwomthwift(wange: t-thwift.tweetidwange): tweetidwange = {
    tweetidwange(fwomid = w-wange.fwomid, ʘwʘ toid = wange.toid)
  }

  d-def fwomtimewinewange(wange: timewinewange): tweetidwange = {
    w-wange match {
      c-case w: tweetidwange => w-w
      case _ =>
        thwow nyew iwwegawawgumentexception(s"onwy tweet id wange is suppowted. σωσ found: $wange")
    }
  }
}

/**
 * a-a wange of tweet ids with excwusive bounds. OwO
 */
case cwass tweetidwange(fwomid: option[tweetid] = n-nyone, 😳😳😳 toid: option[tweetid] = nyone)
    extends t-timewinewange {

  t-thwowifinvawid()

  d-def thwowifinvawid(): u-unit = {
    (fwomid, 😳😳😳 toid) match {
      case (some(fwomtweetid), o.O s-some(totweetid)) =>
        wequiwe(fwomtweetid <= totweetid, ( ͡o ω ͡o ) "fwomid m-must be wess than ow equaw to toid.")
      case _ => // vawid, (U ﹏ U) do nyothing. (///ˬ///✿)
    }
  }

  def tothwift: t-thwift.tweetidwange = {
    thwift.tweetidwange(fwomid = f-fwomid, >w< t-toid = toid)
  }

  d-def totimewinewangethwift: thwift.timewinewange = {
    thwift.timewinewange.tweetidwange(tothwift)
  }

  def isempty: boowean = {
    (fwomid, rawr toid) match {
      c-case (some(fwomid), mya s-some(toid)) if fwomid == toid => t-twue
      case _ => f-fawse
    }
  }
}
