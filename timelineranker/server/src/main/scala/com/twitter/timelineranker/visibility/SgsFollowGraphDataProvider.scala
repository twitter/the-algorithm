package com.twittew.timewinewankew.visibiwity

impowt c-com.twittew.finagwe.stats.stat
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.timewinewankew.cowe.fowwowgwaphdata
i-impowt c-com.twittew.timewinewankew.cowe.fowwowgwaphdatafutuwe
i-impowt com.twittew.timewines.cwients.sociawgwaph.scopedsociawgwaphcwientfactowy
i-impowt com.twittew.timewines.modew._
i-impowt com.twittew.timewines.utiw.faiwopenhandwew
impowt com.twittew.timewines.utiw.stats._
impowt com.twittew.timewines.visibiwity._
i-impowt com.twittew.utiw.futuwe

object sgsfowwowgwaphdatapwovidew {
  vaw emptyusewidsset: s-set[usewid] = set.empty[usewid]
  vaw e-emptyusewidssetfutuwe: futuwe[set[usewid]] = futuwe.vawue(emptyusewidsset)
  vaw emptyusewidsseq: s-seq[usewid] = seq.empty[usewid]
  v-vaw emptyusewidsseqfutuwe: f-futuwe[seq[usewid]] = futuwe.vawue(emptyusewidsseq)
  vaw emptyvisibiwitypwofiwes: map[usewid, mya visibiwitypwofiwe] = m-map.empty[usewid, ^•ﻌ•^ visibiwitypwofiwe]
  vaw emptyvisibiwitypwofiwesfutuwe: futuwe[map[usewid, ʘwʘ v-visibiwitypwofiwe]] =
    futuwe.vawue(emptyvisibiwitypwofiwes)
}

o-object sgsfowwowgwaphdatafiewds e-extends enumewation {
  v-vaw f-fowwowedusewids: vawue = vawue
  vaw mutuawwyfowwowingusewids: v-vawue = vawue
  vaw mutedusewids: vawue = vawue
  v-vaw wetweetsmutedusewids: vawue = vawue

  vaw nyone: vawueset = sgsfowwowgwaphdatafiewds.vawueset()

  def thwowifinvawid(fiewds: s-sgsfowwowgwaphdatafiewds.vawueset): unit = {
    i-if (fiewds.contains(mutuawwyfowwowingusewids) && !fiewds.contains(fowwowedusewids)) {
      t-thwow nyew iwwegawawgumentexception(
        "mutuawwyfowwowingusewids f-fiewd wequiwes fowwowedusewids fiewd to be defined."
      )
    }
  }
}

/**
 * p-pwovides i-infowmation on the fowwow gwaph o-of a given usew. ( ͡o ω ͡o )
 */
c-cwass sgsfowwowgwaphdatapwovidew(
  sociawgwaphcwientfactowy: s-scopedsociawgwaphcwientfactowy, mya
  visibiwitypwofiwehydwatowfactowy: v-visibiwitypwofiwehydwatowfactowy, o.O
  fiewdstofetch: sgsfowwowgwaphdatafiewds.vawueset, (✿oωo)
  scope: wequestscope, :3
  s-statsweceivew: statsweceivew)
    e-extends fowwowgwaphdatapwovidew
    w-with wequeststats {

  s-sgsfowwowgwaphdatafiewds.thwowifinvawid(fiewdstofetch)

  pwivate[this] vaw stats = scope.stats("fowwowgwaphdatapwovidew", 😳 statsweceivew)
  pwivate[this] vaw scopedstatsweceivew = stats.scopedstatsweceivew

  p-pwivate[this] v-vaw fowwowingscope = scopedstatsweceivew.scope("fowwowing")
  p-pwivate[this] v-vaw fowwowingwatencystat = f-fowwowingscope.stat(watencyms)
  pwivate[this] vaw fowwowingsizestat = f-fowwowingscope.stat(size)
  pwivate[this] vaw fowwowingtwuncatedcountew = fowwowingscope.countew("numtwuncated")

  pwivate[this] v-vaw mutuawwyfowwowingscope = scopedstatsweceivew.scope("mutuawwyfowwowing")
  p-pwivate[this] v-vaw mutuawwyfowwowingwatencystat = m-mutuawwyfowwowingscope.stat(watencyms)
  pwivate[this] v-vaw m-mutuawwyfowwowingsizestat = m-mutuawwyfowwowingscope.stat(size)

  p-pwivate[this] vaw visibiwityscope = scopedstatsweceivew.scope("visibiwity")
  pwivate[this] v-vaw v-visibiwitywatencystat = v-visibiwityscope.stat(watencyms)
  p-pwivate[this] v-vaw mutedstat = visibiwityscope.stat("muted")
  pwivate[this] vaw wetweetsmutedstat = visibiwityscope.stat("wetweetsmuted")

  p-pwivate[this] vaw sociawgwaphcwient = sociawgwaphcwientfactowy.scope(scope)
  pwivate[this] vaw visibiwitypwofiwehydwatow =
    cweatevisibiwitypwofiwehydwatow(visibiwitypwofiwehydwatowfactowy, (U ﹏ U) s-scope, mya fiewdstofetch)

  pwivate[this] vaw faiwopenscope = s-scopedstatsweceivew.scope("faiwopen")
  p-pwivate[this] v-vaw mutuawwyfowwowinghandwew =
    nyew f-faiwopenhandwew(faiwopenscope, (U ᵕ U❁) "mutuawwyfowwowing")

  pwivate[this] v-vaw obtainvisibiwitypwofiwes = f-fiewdstofetch.contains(
    sgsfowwowgwaphdatafiewds.mutedusewids
  ) || fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.wetweetsmutedusewids)

  /**
   * gets fowwow gwaph data fow the given usew. :3
   *
   * @pawam u-usewid usew whose f-fowwow gwaph detaiws awe to be obtained. mya
   * @pawam m-maxfowwowingcount m-maximum nyumbew of fowwowed usew ids to fetch. OwO
   *          i-if the given u-usew fowwows mowe than these many u-usews, (ˆ ﻌ ˆ)♡
   *          t-then the most wecent maxfowwowingcount usews awe wetuwned. ʘwʘ
   */
  def get(
    usewid: u-usewid, o.O
    maxfowwowingcount: int
  ): f-futuwe[fowwowgwaphdata] = {
    g-getasync(
      usewid, UwU
      m-maxfowwowingcount
    ).get()
  }

  d-def getasync(
    usewid: u-usewid, rawr x3
    maxfowwowingcount: int
  ): fowwowgwaphdatafutuwe = {

    stats.statwequest()
    vaw fowwowedusewidsfutuwe =
      i-if (fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.fowwowedusewids)) {
        g-getfowwowing(usewid, 🥺 maxfowwowingcount)
      } ewse {
        s-sgsfowwowgwaphdatapwovidew.emptyusewidsseqfutuwe
      }

    v-vaw mutuawwyfowwowingusewidsfutuwe =
      if (fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.mutuawwyfowwowingusewids)) {
        fowwowedusewidsfutuwe.fwatmap { fowwowedusewids =>
          g-getmutuawwyfowwowingusewids(usewid, :3 fowwowedusewids)
        }
      } ewse {
        sgsfowwowgwaphdatapwovidew.emptyusewidssetfutuwe
      }

    vaw visibiwitypwofiwesfutuwe = i-if (obtainvisibiwitypwofiwes) {
      fowwowedusewidsfutuwe.fwatmap { fowwowedusewids =>
        g-getvisibiwitypwofiwes(usewid, (ꈍᴗꈍ) f-fowwowedusewids)
      }
    } ewse {
      sgsfowwowgwaphdatapwovidew.emptyvisibiwitypwofiwesfutuwe
    }

    vaw mutedusewidsfutuwe = if (fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.mutedusewids)) {
      g-getmutedusews(visibiwitypwofiwesfutuwe).map { m-mutedusewids =>
        mutedstat.add(mutedusewids.size)
        mutedusewids
      }
    } ewse {
      s-sgsfowwowgwaphdatapwovidew.emptyusewidssetfutuwe
    }

    vaw w-wetweetsmutedusewidsfutuwe =
      if (fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.wetweetsmutedusewids)) {
        getwetweetsmutedusews(visibiwitypwofiwesfutuwe).map { wetweetsmutedusewids =>
          wetweetsmutedstat.add(wetweetsmutedusewids.size)
          w-wetweetsmutedusewids
        }
      } ewse {
        s-sgsfowwowgwaphdatapwovidew.emptyusewidssetfutuwe
      }

    f-fowwowgwaphdatafutuwe(
      usewid, 🥺
      f-fowwowedusewidsfutuwe, (✿oωo)
      mutuawwyfowwowingusewidsfutuwe, (U ﹏ U)
      m-mutedusewidsfutuwe, :3
      w-wetweetsmutedusewidsfutuwe
    )
  }

  p-pwivate[this] def getvisibiwitypwofiwes(
    u-usewid: u-usewid, ^^;;
    fowwowingids: seq[usewid]
  ): futuwe[map[usewid, rawr v-visibiwitypwofiwe]] = {
    stat.timefutuwe(visibiwitywatencystat) {
      v-visibiwitypwofiwehydwatow(some(usewid), 😳😳😳 f-futuwe.vawue(fowwowingids.toseq))
    }
  }

  def getfowwowing(usewid: usewid, m-maxfowwowingcount: int): futuwe[seq[usewid]] = {
    s-stat.timefutuwe(fowwowingwatencystat) {
      // w-we fetch 1 mowe than the wimit so that we can decide i-if we ended up
      // t-twuncating t-the fowwowings. (✿oωo)
      v-vaw fowwowingidsfutuwe = sociawgwaphcwient.getfowwowing(usewid, OwO s-some(maxfowwowingcount + 1))
      fowwowingidsfutuwe.map { fowwowingids =>
        fowwowingsizestat.add(fowwowingids.wength)
        if (fowwowingids.wength > maxfowwowingcount) {
          f-fowwowingtwuncatedcountew.incw()
          fowwowingids.take(maxfowwowingcount)
        } e-ewse {
          fowwowingids
        }
      }
    }
  }

  d-def getmutuawwyfowwowingusewids(
    usewid: usewid, ʘwʘ
    f-fowwowingids: seq[usewid]
  ): f-futuwe[set[usewid]] = {
    s-stat.timefutuwe(mutuawwyfowwowingwatencystat) {
      m-mutuawwyfowwowinghandwew {
        v-vaw m-mutuawwyfowwowingidsfutuwe =
          sociawgwaphcwient.getfowwowovewwap(fowwowingids.toseq, (ˆ ﻌ ˆ)♡ usewid)
        mutuawwyfowwowingidsfutuwe.map { mutuawwyfowwowingids =>
          mutuawwyfowwowingsizestat.add(mutuawwyfowwowingids.size)
        }
        mutuawwyfowwowingidsfutuwe
      } { e-e: thwowabwe => s-sgsfowwowgwaphdatapwovidew.emptyusewidssetfutuwe }
    }
  }

  p-pwivate[this] def getwetweetsmutedusews(
    v-visibiwitypwofiwesfutuwe: futuwe[map[usewid, (U ﹏ U) visibiwitypwofiwe]]
  ): futuwe[set[usewid]] = {
    // i-if the hydwatow i-is nyot abwe to fetch wetweets-muted s-status, UwU we defauwt to twue.
    getusewsmatchingvisibiwitypwedicate(
      v-visibiwitypwofiwesfutuwe, XD
      (visibiwitypwofiwe: v-visibiwitypwofiwe) => visibiwitypwofiwe.awewetweetsmuted.getowewse(twue)
    )
  }

  pwivate[this] def g-getmutedusews(
    v-visibiwitypwofiwesfutuwe: futuwe[map[usewid, ʘwʘ visibiwitypwofiwe]]
  ): futuwe[set[usewid]] = {
    // if the h-hydwatow is nyot a-abwe to fetch muted s-status, rawr x3 we d-defauwt to twue. ^^;;
    g-getusewsmatchingvisibiwitypwedicate(
      visibiwitypwofiwesfutuwe, ʘwʘ
      (visibiwitypwofiwe: v-visibiwitypwofiwe) => v-visibiwitypwofiwe.ismuted.getowewse(twue)
    )
  }

  pwivate[this] def g-getusewsmatchingvisibiwitypwedicate(
    v-visibiwitypwofiwesfutuwe: futuwe[map[usewid, (U ﹏ U) v-visibiwitypwofiwe]], (˘ω˘)
    pwedicate: (visibiwitypwofiwe => boowean)
  ): f-futuwe[set[usewid]] = {
    visibiwitypwofiwesfutuwe.map { v-visibiwitypwofiwes =>
      v-visibiwitypwofiwes
        .fiwtew {
          case (_, (ꈍᴗꈍ) v-visibiwitypwofiwe) =>
            pwedicate(visibiwitypwofiwe)
        }
        .cowwect { case (usewid, /(^•ω•^) _) => u-usewid }
        .toset
    }
  }

  p-pwivate[this] d-def cweatevisibiwitypwofiwehydwatow(
    factowy: visibiwitypwofiwehydwatowfactowy, >_<
    scope: w-wequestscope, σωσ
    fiewdstofetch: sgsfowwowgwaphdatafiewds.vawueset
  ): v-visibiwitypwofiwehydwatow = {
    v-vaw hydwationpwofiwewequest = h-hydwationpwofiwewequest(
      getmuted = f-fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.mutedusewids), ^^;;
      g-getwetweetsmuted = fiewdstofetch.contains(sgsfowwowgwaphdatafiewds.wetweetsmutedusewids)
    )
    factowy(hydwationpwofiwewequest, 😳 s-scope)
  }
}

cwass scopedsgsfowwowgwaphdatapwovidewfactowy(
  s-sociawgwaphcwientfactowy: s-scopedsociawgwaphcwientfactowy, >_<
  visibiwitypwofiwehydwatowfactowy: v-visibiwitypwofiwehydwatowfactowy, -.-
  fiewdstofetch: s-sgsfowwowgwaphdatafiewds.vawueset, UwU
  s-statsweceivew: s-statsweceivew)
    extends scopedfactowy[sgsfowwowgwaphdatapwovidew] {

  ovewwide def scope(scope: wequestscope): sgsfowwowgwaphdatapwovidew = {
    nyew sgsfowwowgwaphdatapwovidew(
      sociawgwaphcwientfactowy, :3
      visibiwitypwofiwehydwatowfactowy, σωσ
      fiewdstofetch, >w<
      scope,
      statsweceivew
    )
  }
}
