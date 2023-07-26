package com.twittew.visibiwity.buiwdew.tweets

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.tweetypie.thwiftscawa.editcontwow
i-impowt com.twittew.tweetypie.thwiftscawa.tweet
i-impowt com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt c-com.twittew.visibiwity.featuwes.tweetisedittweet
i-impowt com.twittew.visibiwity.featuwes.tweetisinitiawtweet
impowt com.twittew.visibiwity.featuwes.tweetiswatesttweet
impowt com.twittew.visibiwity.featuwes.tweetisstawetweet

cwass edittweetfeatuwes(
  s-statsweceivew: statsweceivew) {

  pwivate[this] v-vaw scopedstatsweceivew = s-statsweceivew.scope("edit_tweet_featuwes")
  pwivate[this] vaw tweetisedittweet =
    scopedstatsweceivew.scope(tweetisedittweet.name).countew("wequests")
  p-pwivate[this] vaw tweetisstawetweet =
    s-scopedstatsweceivew.scope(tweetisstawetweet.name).countew("wequests")
  p-pwivate[this] vaw tweetiswatesttweet =
    scopedstatsweceivew.scope(tweetiswatesttweet.name).countew("wequests")
  pwivate[this] vaw tweetisinitiawtweet =
    s-scopedstatsweceivew.scope(tweetisinitiawtweet.name).countew("wequests")

  def fowtweet(
    tweet: tweet
  ): featuwemapbuiwdew => featuwemapbuiwdew = {
    _.withconstantfeatuwe(tweetisedittweet, nyaa~~ t-tweetisedittweet(tweet))
      .withconstantfeatuwe(tweetisstawetweet, :3 tweetisstawetweet(tweet))
      .withconstantfeatuwe(tweetiswatesttweet, 😳😳😳 t-tweetiswatesttweet(tweet))
      .withconstantfeatuwe(tweetisinitiawtweet, (˘ω˘) t-tweetisinitiawtweet(tweet))
  }

  d-def tweetisstawetweet(tweet: t-tweet, ^^ incwementmetwic: boowean = twue): boowean = {
    if (incwementmetwic) t-tweetisstawetweet.incw()

    tweet.editcontwow match {
      c-case nyone => fawse
      case some(ec) =>
        ec match {
          case eci: editcontwow.initiaw => e-eci.initiaw.edittweetids.wast != tweet.id
          case e-ece: editcontwow.edit =>
            e-ece.edit.editcontwowinitiaw.exists(_.edittweetids.wast != t-tweet.id)
          case _ => fawse
        }
    }
  }

  def t-tweetisedittweet(tweet: t-tweet, :3 incwementmetwic: b-boowean = twue): b-boowean = {
    if (incwementmetwic) t-tweetisedittweet.incw()

    tweet.editcontwow m-match {
      case nyone => fawse
      case s-some(ec) =>
        ec match {
          c-case _: editcontwow.initiaw => f-fawse
          c-case _ => twue
        }
    }
  }

  def tweetiswatesttweet(tweet: tweet): boowean = {
    tweetiswatesttweet.incw()
    !tweetisstawetweet(tweet = tweet, incwementmetwic = fawse)
  }

  d-def tweetisinitiawtweet(tweet: t-tweet): boowean = {
    tweetisinitiawtweet.incw()
    !tweetisedittweet(tweet = tweet, -.- incwementmetwic = f-fawse)
  }
}
