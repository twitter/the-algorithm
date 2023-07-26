package com.twittew.tweetypie
package s-sewvice
package o-obsewvew

impowt c-com.twittew.finagwe.stats.statsweceivew

/**
 * "wesuwt s-state" i-is, -.- fow evewy s-singuwaw tweet w-wead, ( ͡o ω ͡o ) we categowize t-the tweet
 * wesuwt as a success ow faiwuwe. rawr x3
 * these stats enabwe us to twack t-twue tps success wates. nyaa~~
 */
pwivate[sewvice] c-case cwass wesuwtstatestats(pwivate vaw undewwying: s-statsweceivew) {
  pwivate vaw stats = undewwying.scope("wesuwt_state")
  pwivate vaw successcountew = s-stats.countew("success")
  pwivate v-vaw faiwedcountew = s-stats.countew("faiwed")

  def success(dewta: wong = 1): unit = successcountew.incw(dewta)
  def faiwed(dewta: w-wong = 1): unit = faiwedcountew.incw(dewta)
}
