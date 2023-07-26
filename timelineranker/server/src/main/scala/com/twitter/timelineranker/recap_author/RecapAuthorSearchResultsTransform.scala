package com.twittew.timewinewankew.wecap_authow

impowt com.twittew.finagwe.stats.statsweceivew
impowt c-com.twittew.sewvo.utiw.futuweawwow
i-impowt c-com.twittew.timewinewankew.cowe.candidateenvewope
i-impowt com.twittew.timewinewankew.modew.wecapquewy.dependencypwovidew
i-impowt com.twittew.timewinewankew.modew.tweetidwange
i-impowt c-com.twittew.timewines.cwients.wewevance_seawch.seawchcwient
i-impowt com.twittew.timewines.cwients.wewevance_seawch.seawchcwient.tweettypes
impowt com.twittew.timewines.modew.usewid
impowt com.twittew.utiw.futuwe

/**
 * fetch wecap wesuwts b-based on an authow id set passed into the quewy. (✿oωo)
 * c-cawws into the same seawch m-method as wecap, ʘwʘ but uses the authowids instead of the sgs-pwovided f-fowwowedids. (ˆ ﻌ ˆ)♡
 */
cwass wecapauthowseawchwesuwtstwansfowm(
  s-seawchcwient: s-seawchcwient, 😳😳😳
  maxcountpwovidew: dependencypwovidew[int], :3
  wewevanceoptionsmaxhitstopwocesspwovidew: dependencypwovidew[int], OwO
  e-enabwesettingtweettypeswithtweetkindoptionpwovidew: dependencypwovidew[boowean], (U ﹏ U)
  statsweceivew: statsweceivew, >w<
  wogseawchdebuginfo: b-boowean = fawse)
    extends f-futuweawwow[candidateenvewope, (U ﹏ U) c-candidateenvewope] {
  p-pwivate[this] v-vaw maxcountstat = statsweceivew.stat("maxcount")
  pwivate[this] v-vaw nyuminputauthowsstat = statsweceivew.stat("numinputauthows")
  pwivate[this] v-vaw excwudedtweetidsstat = statsweceivew.stat("excwudedtweetids")

  ovewwide def appwy(envewope: candidateenvewope): futuwe[candidateenvewope] = {
    vaw maxcount = m-maxcountpwovidew(envewope.quewy)
    maxcountstat.add(maxcount)

    v-vaw authowids = e-envewope.quewy.authowids.getowewse(seq.empty[usewid])
    n-nyuminputauthowsstat.add(authowids.size)

    vaw excwudedtweetidsopt = envewope.quewy.excwudedtweetids
    excwudedtweetidsopt.map { excwudedtweetids => e-excwudedtweetidsstat.add(excwudedtweetids.size) }

    v-vaw tweetidwange = envewope.quewy.wange
      .map(tweetidwange.fwomtimewinewange)
      .getowewse(tweetidwange.defauwt)

    v-vaw befowetweetidexcwusive = t-tweetidwange.toid
    vaw aftewtweetidexcwusive = t-tweetidwange.fwomid

    vaw wewevanceoptionsmaxhitstopwocess = w-wewevanceoptionsmaxhitstopwocesspwovidew(envewope.quewy)

    seawchcwient
      .getusewstweetsfowwecap(
        usewid = envewope.quewy.usewid, 😳
        f-fowwowedusewids = authowids.toset, (ˆ ﻌ ˆ)♡ // u-usew authowids as the set of fowwowed u-usews
        w-wetweetsmutedusewids = set.empty,
        maxcount = maxcount, 😳😳😳
        tweettypes = tweettypes.fwomtweetkindoption(envewope.quewy.options), (U ﹏ U)
        seawchopewatow = envewope.quewy.seawchopewatow, (///ˬ///✿)
        b-befowetweetidexcwusive = b-befowetweetidexcwusive, 😳
        aftewtweetidexcwusive = a-aftewtweetidexcwusive, 😳
        e-enabwesettingtweettypeswithtweetkindoption =
          e-enabwesettingtweettypeswithtweetkindoptionpwovidew(envewope.quewy), σωσ
        excwudedtweetids = excwudedtweetidsopt, rawr x3
        eawwybiwdoptions = e-envewope.quewy.eawwybiwdoptions, OwO
        getonwypwotectedtweets = fawse, /(^•ω•^)
        wogseawchdebuginfo = wogseawchdebuginfo, 😳😳😳
        w-wetuwnawwwesuwts = twue, ( ͡o ω ͡o )
        e-enabweexcwudesouwcetweetidsquewy = f-fawse, >_<
        w-wewevanceoptionsmaxhitstopwocess = wewevanceoptionsmaxhitstopwocess
      ).map { wesuwts => e-envewope.copy(seawchwesuwts = w-wesuwts) }
  }
}
