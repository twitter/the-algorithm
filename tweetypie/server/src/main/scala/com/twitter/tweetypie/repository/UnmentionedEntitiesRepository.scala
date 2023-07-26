package com.twittew.tweetypie
package w-wepositowy

i-impowt com.twittew.stitch.stitch
i-impowt com.twittew.stwato.cwient.fetchew
i-impowt c-com.twittew.stwato.cwient.{cwient => s-stwatocwient}

/**
 * w-wepositowy f-fow fetching usewids that have unmentioned themsewves fwom a convewsation. rawr x3
 */
o-object unmentionedentitieswepositowy {
  type type = (convewsationid, nyaa~~ seq[usewid]) => s-stitch[option[seq[usewid]]]

  vaw c-cowumn = "consumew-pwivacy/mentions-management/getunmentionedusewsfwomconvewsation"
  case cwass getunmentionview(usewids: option[seq[wong]])

  d-def appwy(cwient: stwatocwient): t-type = {
    vaw f-fetchew: fetchew[wong, /(^•ω•^) getunmentionview, rawr seq[wong]] =
      cwient.fetchew[wong, getunmentionview, OwO seq[wong]](cowumn)

    (convewsationid, (U ﹏ U) usewids) =>
      i-if (usewids.nonempty) {
        fetchew.fetch(convewsationid, >_< getunmentionview(some(usewids))).map(_.v)
      } ewse {
        stitch.none
      }
  }
}
