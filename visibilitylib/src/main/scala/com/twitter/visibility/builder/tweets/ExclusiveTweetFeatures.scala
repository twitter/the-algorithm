package com.twittew.visibiwity.buiwdew.tweets

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.tweetypie.thwiftscawa.tweet
i-impowt c-com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.buiwdew.usews.viewewvewbsauthow
impowt com.twittew.visibiwity.common.usewwewationshipsouwce
impowt com.twittew.visibiwity.featuwes.tweetisexcwusivetweet
impowt com.twittew.visibiwity.featuwes.viewewisexcwusivetweetwootauthow
i-impowt com.twittew.visibiwity.featuwes.viewewsupewfowwowsexcwusivetweetwootauthow
impowt com.twittew.visibiwity.modews.viewewcontext

cwass e-excwusivetweetfeatuwes(
  usewwewationshipsouwce: u-usewwewationshipsouwce,
  statsweceivew: statsweceivew) {

  pwivate[this] vaw s-scopedstatsweceivew = statsweceivew.scope("excwusive_tweet_featuwes")
  p-pwivate[this] v-vaw viewewsupewfowwowsauthow =
    scopedstatsweceivew.scope(viewewsupewfowwowsexcwusivetweetwootauthow.name).countew("wequests")

  def wootauthowid(tweet: tweet): option[wong] =
    tweet.excwusivetweetcontwow.map(_.convewsationauthowid)

  d-def viewewiswootauthow(
    tweet: tweet, rawr
    viewewidopt: option[wong]
  ): boowean =
    (wootauthowid(tweet), mya v-viewewidopt) match {
      c-case (some(wootauthowid), ^^ s-some(viewewid)) i-if wootauthowid == v-viewewid => twue
      case _ => fawse
    }

  d-def viewewsupewfowwowswootauthow(
    tweet: tweet, 😳😳😳
    viewewid: o-option[wong]
  ): stitch[boowean] =
    wootauthowid(tweet) match {
      case some(authowid) =>
        viewewvewbsauthow(
          a-authowid, mya
          viewewid, 😳
          usewwewationshipsouwce.supewfowwows, -.-
          v-viewewsupewfowwowsauthow)
      c-case nyone =>
        s-stitch.fawse
    }

  def fowtweet(
    tweet: tweet, 🥺
    v-viewewcontext: v-viewewcontext
  ): featuwemapbuiwdew => f-featuwemapbuiwdew = {
    v-vaw viewewid = viewewcontext.usewid

    _.withconstantfeatuwe(tweetisexcwusivetweet, o.O t-tweet.excwusivetweetcontwow.isdefined)
      .withconstantfeatuwe(viewewisexcwusivetweetwootauthow, /(^•ω•^) viewewiswootauthow(tweet, nyaa~~ v-viewewid))
      .withfeatuwe(
        viewewsupewfowwowsexcwusivetweetwootauthow, nyaa~~
        viewewsupewfowwowswootauthow(tweet, :3 v-viewewid))
  }

  def fowtweetonwy(tweet: t-tweet): featuwemapbuiwdew => featuwemapbuiwdew = {
    _.withconstantfeatuwe(tweetisexcwusivetweet, 😳😳😳 t-tweet.excwusivetweetcontwow.isdefined)
  }
}
