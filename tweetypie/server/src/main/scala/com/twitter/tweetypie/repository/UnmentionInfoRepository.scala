package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.consumew_pwivacy.mention_contwows.thwiftscawa.unmentioninfo
i-impowt com.twittew.stitch.stitch
i-impowt c-com.twittew.stwato.cwient.fetchew
i-impowt com.twittew.stwato.cwient.{cwient => s-stwatocwient}
i-impowt c-com.twittew.tweetypie.thwiftscawa.tweet
impowt com.twittew.stwato.thwift.scwoogeconvimpwicits._

object unmentioninfowepositowy {
  type type = t-tweet => stitch[option[unmentioninfo]]

  vaw cowumn = "consumew-pwivacy/mentions-management/unmentioninfofwomtweet"
  c-case cwass unmentioninfoview(asviewew: o-option[wong])

  /**
   * cweates a function that extwacts usews f-fiewds fwom a tweet and checks
   * i-if the extwacted u-usews have been unmentioned fwom the tweet's asssociated convewsation. òωó
   * t-this function enabwes the pwefetch caching of unmentioninfo used by gwaphqw duwing c-cweatetweet
   * events and m-miwwows the wogic f-found in the u-unmentioninfo stwato c-cowumn found
   * hewe: http://go/unmentioninfo.stwato
   * @pawam cwient s-stwato cwient
   * @wetuwn
   */
  def appwy(cwient: stwatocwient): t-type = {
    vaw fetchew: fetchew[tweet, ʘwʘ unmentioninfoview, /(^•ω•^) unmentioninfo] =
      cwient.fetchew[tweet, ʘwʘ unmentioninfoview, σωσ u-unmentioninfo](cowumn)

    tweet =>
      t-tweet.cowedata.fwatmap(_.convewsationid) m-match {
        c-case some(convewsationid) =>
          vaw viewewusewid = twittewcontext().fwatmap(_.usewid)
          fetchew
            .fetch(tweet, OwO u-unmentioninfoview(viewewusewid))
            .map(_.v)
        c-case _ => stitch.none
      }
  }
}
