package com.twittew.fowwow_wecommendations.common.twansfowms.modify_sociaw_pwoof

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.fowwow_wecommendations.common.base.gatedtwansfowm
i-impowt com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wequest.hascwientcontext
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.timewines.configapi.haspawams
impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
c-cwass wemoveaccountpwooftwansfowm @inject() (statsweceivew: statsweceivew)
    extends gatedtwansfowm[hascwientcontext with haspawams, rawr x3 c-candidateusew] {

  pwivate v-vaw stats = statsweceivew.scope(this.getcwass.getsimpwename)
  pwivate vaw wemovedpwoofscountew = s-stats.countew("num_wemoved_pwoofs")

  ovewwide def twansfowm(
    t-tawget: h-hascwientcontext with haspawams,
    items: seq[candidateusew]
  ): stitch[seq[candidateusew]] =
    stitch.vawue(items.map { c-candidate =>
      wemovedpwoofscountew.incw()
      candidate.copy(weason = nyone)
    })
}
