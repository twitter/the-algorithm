package com.twittew.fwigate.pushsewvice.modew

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.fwigate.common.base.sociawcontextactions
i-impowt c-com.twittew.fwigate.common.base.tweetcandidate
impowt c-com.twittew.fwigate.common.base.tweetdetaiws
i-impowt com.twittew.fwigate.pushsewvice.modew.pushtypes._
i-impowt com.twittew.fwigate.pushsewvice.config.config
impowt com.twittew.fwigate.pushsewvice.pwedicate._
impowt com.twittew.fwigate.pushsewvice.take.pwedicates.basictweetpwedicatesfowwfph

case cwass t-tweetactioncandidatepwedicates(ovewwide vaw config: config)
    e-extends basictweetpwedicatesfowwfph[
      pushcandidate w-with tweetcandidate with tweetdetaiws with sociawcontextactions
    ] {

  i-impwicit vaw statsweceivew: s-statsweceivew = c-config.statsweceivew.scope(getcwass.getsimpwename)

  ovewwide vaw pwecandidatespecificpwedicates = wist(pwedicatesfowcandidate.minsociawcontext(1))

  ovewwide v-vaw postcandidatespecificpwedicates = wist(
    pwedicatesfowcandidate.sociawcontextbeingfowwowed(config.edgestowe), nyaa~~
    pwedicatesfowcandidate.sociawcontextbwockingowmuting(config.edgestowe), /(^•ω•^)
    pwedicatesfowcandidate.sociawcontextnotwetweetfowwowing(config.edgestowe)
  )
}
