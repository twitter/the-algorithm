package com.twittew.tweetypie
package w-wepositowy

i-impowt com.twittew.spam.wtf.thwiftscawa.safetywevew
i-impowt com.twittew.stitch.notfound
i-impowt com.twittew.stitch.stitch
i-impowt c-com.twittew.tweetypie.cowe.fiwtewedstate.unavaiwabwe.tweetdeweted
i-impowt com.twittew.tweetypie.thwiftscawa.convewsationcontwow

/**
 * t-this wepositowy woads up the convewsation contwow vawues fow a tweet which c-contwows who can wepwy
 * to a tweet. >w< because t-the convewsation contwow vawues a-awe stowed on the woot tweet of a convewsation, (U ﹏ U)
 * we nyeed to make s-suwe that the code is abwe to w-woad the data f-fwom the woot tweet. 😳 to ensuwe this, (ˆ ﻌ ˆ)♡
 * nyo visibiwity fiwtewing options awe set o-on the quewy to woad the woot tweet fiewds. 😳😳😳
 *
 * if visibiwity fiwtewing was enabwed, (U ﹏ U) a-and the woot tweet was fiwtewed f-fow the w-wequesting usew, (///ˬ///✿)
 * t-then the convewsation c-contwow data wouwd nyot be wetuwned and e-enfowcement wouwd effectivewy be
 * side-stepped. 😳
 */
o-object convewsationcontwowwepositowy {
  pwivate[this] vaw wog = woggew(getcwass)
  type type = (tweetid, 😳 cachecontwow) => s-stitch[option[convewsationcontwow]]

  def appwy(wepo: t-tweetwepositowy.type, s-stats: statsweceivew): t-type =
    (convewsationid: tweetid, cachecontwow: cachecontwow) => {
      vaw options = t-tweetquewy.options(
        i-incwude = tweetquewy.incwude(set(tweet.convewsationcontwowfiewd.id)), σωσ
        // w-we w-want the woot tweet of a convewsation t-that we'we wooking up to b-be
        // cached with the same powicy as the t-tweet we'we wooking up. rawr x3
        c-cachecontwow = cachecontwow, OwO
        e-enfowcevisibiwityfiwtewing = f-fawse, /(^•ω•^)
        safetywevew = safetywevew.fiwtewnone
      )

      wepo(convewsationid, 😳😳😳 options)
        .map(woottweet => woottweet.convewsationcontwow)
        .handwe {
          // we don't k-know of any c-case whewe tweets wouwd wetuwn n-nyotfound, ( ͡o ω ͡o ) but fow
          // f-fow pwagmatic weasons, >_< w-we'we opening the convewsation fow wepwies
          // in case a bug causing t-tweets to be nyotfound exists. >w<
          case nyotfound =>
            stats.countew("tweet_not_found")
            n-nyone
          // if nyo w-woot tweet is f-found, rawr the wepwy h-has nyo convewsation contwows
          // t-this i-is by design, d-deweting the woot t-tweet "opens" the convewsation
          case t-tweetdeweted =>
            s-stats.countew("tweet_deweted")
            n-nyone
        }
    }
}
