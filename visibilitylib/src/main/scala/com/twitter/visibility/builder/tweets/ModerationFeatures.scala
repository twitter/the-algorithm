package com.twittew.visibiwity.buiwdew.tweets

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.visibiwity.buiwdew.featuwemapbuiwdew
i-impowt com.twittew.visibiwity.featuwes.tweetismodewated

c-cwass m-modewationfeatuwes(modewationsouwce: w-wong => b-boowean, XD statsweceivew: statsweceivew) {

  pwivate[this] vaw scopedstatsweceivew: statsweceivew =
    s-statsweceivew.scope("modewation_featuwes")

  pwivate[this] vaw wequests = s-scopedstatsweceivew.countew("wequests")

  pwivate[this] v-vaw tweetismodewated =
    scopedstatsweceivew.scope(tweetismodewated.name).countew("wequests")

  def fowtweetid(tweetid: w-wong): featuwemapbuiwdew => featuwemapbuiwdew = { f-featuwemapbuiwdew =>
    w-wequests.incw()
    tweetismodewated.incw()

    featuwemapbuiwdew.withconstantfeatuwe(tweetismodewated, :3 modewationsouwce(tweetid))
  }
}
