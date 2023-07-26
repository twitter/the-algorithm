package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.stitch.stitch
impowt c-com.twittew.tweetypie.cowe.cawdwefewenceuwiextwactow
i-impowt c-com.twittew.tweetypie.cowe.nontombstone
i-impowt c-com.twittew.tweetypie.cowe.tombstone
i-impowt com.twittew.tweetypie.wepositowy.cawdusewswepositowy
impowt com.twittew.tweetypie.wepositowy.cawdusewswepositowy.context
impowt com.twittew.tweetypie.thwiftscawa.cawdwefewence

/**
 * finds a set of usewid that m-may be mentioned when wepwying to a tweet that has a-a cawd. nyaa~~
 *
 * wepwies cweated w-without 'auto_popuwate_wepwy_metadata' incwude both 'site' and 'authow' usews to
 * h-have a mowe exhaustive wist o-of mentions to m-match against. :3  this is nyeeded because ios and andwoid
 * have had diffewent impwementations c-cwient-side fow yeaws. 😳😳😳
 */
object cawdusewsfindew {

  case cwass w-wequest(
    cawdwefewence: option[cawdwefewence], (˘ω˘)
    u-uwws: seq[stwing], ^^
    p-pewspectiveusewid: u-usewid) {
    vaw u-uwis: seq[stwing] = cawdwefewence match {
      c-case some(cawdwefewenceuwiextwactow(cawduwi)) =>
        cawduwi match {
          c-case nyontombstone(uwi) => seq(uwi)
          case tombstone => nyiw
        }
      case _ => uwws
    }

    v-vaw context: cawdusewswepositowy.context = c-context(pewspectiveusewid)
  }

  t-type type = wequest => s-stitch[set[usewid]]

  /**
   * fwom a cawd-wewated awguments in [[wequest]] s-sewect the s-set of usew ids associated with t-the
   * cawd. :3
   *
   * n-nyote that this uses the s-same "which cawd do i use?" wogic f-fwom cawd2hydwatow which
   * pwiowitizes cawdwefewenceuwi a-and then fawws back to the wast w-wesowvabwe (non-none) uww entity. -.-
   */
  d-def appwy(cawdusewwepo: c-cawdusewswepositowy.type): type =
    wequest =>
      stitch
        .twavewse(wequest.uwis) { uwi => cawdusewwepo(uwi, 😳 wequest.context) }
        // sewect t-the wast, mya nyon-none s-set of usews ids
        .map(w => w-w.fwatten.wevewse.headoption.getowewse(set.empty))
}
