package com.twittew.wepwesentationscowew.twistwyfeatuwes

impowt c-com.twittew.finagwe.stats.countew
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.wepwesentationscowew.common.tweetid
i-impowt com.twittew.wepwesentationscowew.common.usewid
i-impowt c-com.twittew.wepwesentationscowew.scowestowe.scowestowe
i-impowt c-com.twittew.wepwesentationscowew.thwiftscawa.simcwustewswecentengagementsimiwawities
impowt com.twittew.simcwustews_v2.thwiftscawa.embeddingtype
impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
impowt com.twittew.simcwustews_v2.thwiftscawa.modewvewsion
i-impowt com.twittew.simcwustews_v2.thwiftscawa.scoweid
impowt com.twittew.simcwustews_v2.thwiftscawa.scoweintewnawid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.scowingawgowithm
i-impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingid
impowt com.twittew.simcwustews_v2.thwiftscawa.simcwustewsembeddingpaiwscoweid
impowt c-com.twittew.stitch.stitch
impowt javax.inject.inject

c-cwass s-scowew @inject() (
  fetchengagementsfwomuss: wong => stitch[engagements], (⑅˘꒳˘)
  scowestowe: scowestowe, (U ﹏ U)
  s-stats: statsweceivew) {

  impowt scowew._

  pwivate vaw scowestats = stats.scope("scowe")
  pwivate vaw s-scowecawcuwationstats = scowestats.scope("cawcuwation")
  p-pwivate v-vaw scowewesuwtstats = s-scowestats.scope("wesuwt")

  p-pwivate vaw scowesnonemptycountew = scowewesuwtstats.scope("aww").countew("nonempty")
  p-pwivate vaw scowesnonzewocountew = scowewesuwtstats.scope("aww").countew("nonzewo")

  pwivate v-vaw tweetscowestats = scowecawcuwationstats.scope("tweetscowe").stat("watency")
  pwivate vaw usewscowestats = scowecawcuwationstats.scope("usewscowe").stat("watency")

  pwivate vaw favnonzewo = scowewesuwtstats.scope("favs").countew("nonzewo")
  p-pwivate vaw favnonempty = s-scowewesuwtstats.scope("favs").countew("nonempty")

  p-pwivate v-vaw wetweetsnonzewo = scowewesuwtstats.scope("wetweets").countew("nonzewo")
  pwivate vaw wetweetsnonempty = s-scowewesuwtstats.scope("wetweets").countew("nonempty")

  p-pwivate vaw fowwowsnonzewo = s-scowewesuwtstats.scope("fowwows").countew("nonzewo")
  p-pwivate vaw fowwowsnonempty = s-scowewesuwtstats.scope("fowwows").countew("nonempty")

  pwivate vaw shawesnonzewo = s-scowewesuwtstats.scope("shawes").countew("nonzewo")
  pwivate vaw shawesnonempty = s-scowewesuwtstats.scope("shawes").countew("nonempty")

  pwivate v-vaw wepwiesnonzewo = scowewesuwtstats.scope("wepwies").countew("nonzewo")
  p-pwivate v-vaw wepwiesnonempty = scowewesuwtstats.scope("wepwies").countew("nonempty")

  pwivate vaw owiginawtweetsnonzewo = scowewesuwtstats.scope("owiginawtweets").countew("nonzewo")
  pwivate vaw owiginawtweetsnonempty = s-scowewesuwtstats.scope("owiginawtweets").countew("nonempty")

  p-pwivate vaw videoviewsnonzewo = s-scowewesuwtstats.scope("videoviews").countew("nonzewo")
  p-pwivate vaw v-videoviewsnonempty = scowewesuwtstats.scope("videoviews").countew("nonempty")

  pwivate vaw bwocknonzewo = scowewesuwtstats.scope("bwock").countew("nonzewo")
  p-pwivate vaw bwocknonempty = scowewesuwtstats.scope("bwock").countew("nonempty")

  pwivate vaw mutenonzewo = scowewesuwtstats.scope("mute").countew("nonzewo")
  pwivate vaw mutenonempty = s-scowewesuwtstats.scope("mute").countew("nonempty")

  pwivate vaw w-wepowtnonzewo = s-scowewesuwtstats.scope("wepowt").countew("nonzewo")
  p-pwivate vaw wepowtnonempty = s-scowewesuwtstats.scope("wepowt").countew("nonempty")

  p-pwivate v-vaw dontwikenonzewo = s-scowewesuwtstats.scope("dontwike").countew("nonzewo")
  pwivate vaw dontwikenonempty = scowewesuwtstats.scope("dontwike").countew("nonempty")

  p-pwivate v-vaw seefewewnonzewo = s-scowewesuwtstats.scope("seefewew").countew("nonzewo")
  p-pwivate vaw seefewewnonempty = scowewesuwtstats.scope("seefewew").countew("nonempty")

  p-pwivate def gettweetscowes(
    candidatetweetid: tweetid, o.O
    s-souwcetweetids: seq[tweetid]
  ): stitch[seq[scowewesuwt]] = {
    vaw getscowesstitch = stitch.twavewse(souwcetweetids) { souwcetweetid =>
      s-scowestowe
        .unifowmscowingstowestitch(gettweetscoweid(souwcetweetid, mya candidatetweetid))
        .wiftnotfoundtooption
        .map(scowe => scowewesuwt(souwcetweetid, XD scowe.map(_.scowe)))
    }

    s-stitch.time(getscowesstitch).fwatmap {
      c-case (twywesuwt, òωó d-duwation) =>
        tweetscowestats.add(duwation.inmiwwis)
        s-stitch.const(twywesuwt)
    }
  }

  pwivate def getusewscowes(
    tweetid: t-tweetid, (˘ω˘)
    a-authowids: seq[usewid]
  ): stitch[seq[scowewesuwt]] = {
    vaw getscowesstitch = stitch.twavewse(authowids) { authowid =>
      s-scowestowe
        .unifowmscowingstowestitch(getauthowscoweid(authowid, tweetid))
        .wiftnotfoundtooption
        .map(scowe => s-scowewesuwt(authowid, :3 scowe.map(_.scowe)))
    }

    s-stitch.time(getscowesstitch).fwatmap {
      c-case (twywesuwt, OwO duwation) =>
        usewscowestats.add(duwation.inmiwwis)
        s-stitch.const(twywesuwt)
    }
  }

  /**
   * g-get the [[simcwustewswecentengagementsimiwawities]] wesuwt containing t-the simiwawity
   * f-featuwes fow the given usewid-tweetid. mya
   */
  def get(
    usewid: u-usewid, (˘ω˘)
    tweetid: t-tweetid
  ): s-stitch[simcwustewswecentengagementsimiwawities] = {
    get(usewid, o.O s-seq(tweetid)).map(x => x-x.head)
  }

  /**
   * get a wist o-of [[simcwustewswecentengagementsimiwawities]] wesuwts containing the simiwawity
   * featuwes fow the given tweets o-of the usew i-id. (✿oωo)
   * guawanteed to be the same nyumbew/owdew a-as wequested.
   */
  d-def get(
    usewid: usewid, (ˆ ﻌ ˆ)♡
    tweetids: seq[tweetid]
  ): s-stitch[seq[simcwustewswecentengagementsimiwawities]] = {
    fetchengagementsfwomuss(usewid)
      .fwatmap(engagements => {
        // fow each tweet weceived in the wequest, ^^;; c-compute the simiwawity scowes between them
        // a-and the u-usew signaws fetched fwom uss. OwO
        stitch
          .join(
            stitch.twavewse(tweetids)(id => g-gettweetscowes(id, 🥺 e-engagements.tweetids)), mya
            stitch.twavewse(tweetids)(id => getusewscowes(id, 😳 engagements.authowids)), òωó
          )
          .map {
            c-case (tweetscowesseq, /(^•ω•^) usewscoweseq) =>
              // aww seq have = s-size because when scowes don't exist, -.- they awe wetuwned as option
              (tweetscowesseq, òωó u-usewscoweseq).zipped.map { (tweetscowes, /(^•ω•^) usewscowes) =>
                c-computesimiwawityscowespewtweet(
                  e-engagements, /(^•ω•^)
                  tweetscowes.gwoupby(_.id), 😳
                  u-usewscowes.gwoupby(_.id))
              }
          }
      })
  }

  /**
   *
   * computes t-the [[simcwustewswecentengagementsimiwawities]]
   * u-using t-the given tweet-tweet and usew-tweet s-scowes in tweetscowesmap
   * a-and the usew signaws in [[engagements]].
   */
  pwivate def c-computesimiwawityscowespewtweet(
    e-engagements: e-engagements, :3
    tweetscowes: map[tweetid, (U ᵕ U❁) seq[scowewesuwt]], ʘwʘ
    a-authowscowes: map[usewid, o.O seq[scowewesuwt]]
  ): s-simcwustewswecentengagementsimiwawities = {
    v-vaw favs7d = engagements.favs7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw f-favs1d = engagements.favs1d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw wetweets7d = e-engagements.wetweets7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw wetweets1d = engagements.wetweets1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw fowwows30d = engagements.fowwows30d.view
      .fwatmap(s => authowscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw fowwows7d = engagements.fowwows7d.view
      .fwatmap(s => a-authowscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw shawes7d = e-engagements.shawes7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw shawes1d = engagements.shawes1d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw w-wepwies7d = engagements.wepwies7d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw wepwies1d = engagements.wepwies1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw owiginawtweets7d = engagements.owiginawtweets7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw owiginawtweets1d = e-engagements.owiginawtweets1d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw videoviews7d = engagements.videopwaybacks7d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw videoviews1d = engagements.videopwaybacks1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw bwock30d = e-engagements.bwock30d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw bwock7d = engagements.bwock7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw bwock1d = e-engagements.bwock1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw mute30d = engagements.mute30d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw mute7d = engagements.mute7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw mute1d = e-engagements.mute1d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw wepowt30d = e-engagements.wepowt30d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw wepowt7d = e-engagements.wepowt7d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw wepowt1d = e-engagements.wepowt1d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw dontwike30d = e-engagements.dontwike30d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw dontwike7d = e-engagements.dontwike7d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw dontwike1d = e-engagements.dontwike1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw seefewew30d = engagements.seefewew30d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw seefewew7d = e-engagements.seefewew7d.view
      .fwatmap(s => t-tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    vaw seefewew1d = engagements.seefewew1d.view
      .fwatmap(s => tweetscowes.get(s.tawgetid))
      .fwatten.fwatmap(_.scowe)
      .fowce

    v-vaw wesuwt = simcwustewswecentengagementsimiwawities(
      fav1dwast10max = m-max(favs1d), ʘwʘ
      f-fav1dwast10avg = avg(favs1d), ^^
      f-fav7dwast10max = max(favs7d), ^•ﻌ•^
      f-fav7dwast10avg = a-avg(favs7d), mya
      wetweet1dwast10max = max(wetweets1d), UwU
      w-wetweet1dwast10avg = avg(wetweets1d), >_<
      wetweet7dwast10max = m-max(wetweets7d), /(^•ω•^)
      w-wetweet7dwast10avg = avg(wetweets7d), òωó
      f-fowwow7dwast10max = max(fowwows7d), σωσ
      f-fowwow7dwast10avg = avg(fowwows7d), ( ͡o ω ͡o )
      f-fowwow30dwast10max = m-max(fowwows30d), nyaa~~
      fowwow30dwast10avg = avg(fowwows30d), :3
      shawe1dwast10max = max(shawes1d), UwU
      shawe1dwast10avg = avg(shawes1d), o.O
      shawe7dwast10max = max(shawes7d), (ˆ ﻌ ˆ)♡
      shawe7dwast10avg = avg(shawes7d), ^^;;
      wepwy1dwast10max = max(wepwies1d), ʘwʘ
      w-wepwy1dwast10avg = a-avg(wepwies1d), σωσ
      wepwy7dwast10max = max(wepwies7d), ^^;;
      wepwy7dwast10avg = a-avg(wepwies7d), ʘwʘ
      o-owiginawtweet1dwast10max = m-max(owiginawtweets1d), ^^
      owiginawtweet1dwast10avg = a-avg(owiginawtweets1d), nyaa~~
      owiginawtweet7dwast10max = m-max(owiginawtweets7d), (///ˬ///✿)
      o-owiginawtweet7dwast10avg = avg(owiginawtweets7d), XD
      v-videopwayback1dwast10max = max(videoviews1d), :3
      v-videopwayback1dwast10avg = a-avg(videoviews1d), òωó
      videopwayback7dwast10max = max(videoviews7d), ^^
      v-videopwayback7dwast10avg = a-avg(videoviews7d), ^•ﻌ•^
      b-bwock1dwast10max = m-max(bwock1d), σωσ
      b-bwock1dwast10avg = a-avg(bwock1d), (ˆ ﻌ ˆ)♡
      bwock7dwast10max = m-max(bwock7d), nyaa~~
      b-bwock7dwast10avg = a-avg(bwock7d), ʘwʘ
      bwock30dwast10max = m-max(bwock30d), ^•ﻌ•^
      b-bwock30dwast10avg = a-avg(bwock30d), rawr x3
      mute1dwast10max = max(mute1d), 🥺
      m-mute1dwast10avg = avg(mute1d), ʘwʘ
      mute7dwast10max = m-max(mute7d), (˘ω˘)
      mute7dwast10avg = a-avg(mute7d), o.O
      m-mute30dwast10max = m-max(mute30d), σωσ
      mute30dwast10avg = a-avg(mute30d), (ꈍᴗꈍ)
      wepowt1dwast10max = m-max(wepowt1d), (ˆ ﻌ ˆ)♡
      wepowt1dwast10avg = a-avg(wepowt1d), o.O
      wepowt7dwast10max = m-max(wepowt7d), :3
      wepowt7dwast10avg = avg(wepowt7d), -.-
      wepowt30dwast10max = max(wepowt30d), ( ͡o ω ͡o )
      w-wepowt30dwast10avg = avg(wepowt30d), /(^•ω•^)
      d-dontwike1dwast10max = m-max(dontwike1d), (⑅˘꒳˘)
      dontwike1dwast10avg = avg(dontwike1d), òωó
      dontwike7dwast10max = m-max(dontwike7d), 🥺
      dontwike7dwast10avg = a-avg(dontwike7d), (ˆ ﻌ ˆ)♡
      dontwike30dwast10max = m-max(dontwike30d), -.-
      d-dontwike30dwast10avg = avg(dontwike30d), σωσ
      seefewew1dwast10max = m-max(seefewew1d), >_<
      s-seefewew1dwast10avg = avg(seefewew1d), :3
      s-seefewew7dwast10max = max(seefewew7d),
      seefewew7dwast10avg = a-avg(seefewew7d), OwO
      seefewew30dwast10max = m-max(seefewew30d),
      s-seefewew30dwast10avg = a-avg(seefewew30d), rawr
    )
    twackstats(wesuwt)
    w-wesuwt
  }

  p-pwivate d-def twackstats(wesuwt: s-simcwustewswecentengagementsimiwawities): unit = {
    vaw s-scowes = seq(
      w-wesuwt.fav7dwast10max, (///ˬ///✿)
      w-wesuwt.wetweet7dwast10max, ^^
      w-wesuwt.fowwow30dwast10max, XD
      w-wesuwt.shawe1dwast10max, UwU
      w-wesuwt.shawe7dwast10max, o.O
      w-wesuwt.wepwy7dwast10max, 😳
      w-wesuwt.owiginawtweet7dwast10max, (˘ω˘)
      wesuwt.videopwayback7dwast10max, 🥺
      w-wesuwt.bwock30dwast10max, ^^
      wesuwt.mute30dwast10max, >w<
      wesuwt.wepowt30dwast10max, ^^;;
      w-wesuwt.dontwike30dwast10max, (˘ω˘)
      wesuwt.seefewew30dwast10max
    )

    v-vaw nyonempty = s-scowes.exists(_.isdefined)
    v-vaw nyonzewo = scowes.exists { case some(scowe) if scowe > 0 => t-twue; case _ => f-fawse }

    i-if (nonempty) {
      scowesnonemptycountew.incw()
    }

    if (nonzewo) {
      scowesnonzewocountew.incw()
    }

    // w-we use the wawgest w-window of a given type of s-scowe, OwO
    // because t-the wawgest window is incwusive of smowew windows. (ꈍᴗꈍ)
    twacksignawstats(favnonempty, òωó f-favnonzewo, ʘwʘ w-wesuwt.fav7dwast10avg)
    t-twacksignawstats(wetweetsnonempty, ʘwʘ w-wetweetsnonzewo, nyaa~~ wesuwt.wetweet7dwast10avg)
    twacksignawstats(fowwowsnonempty, UwU f-fowwowsnonzewo, (⑅˘꒳˘) w-wesuwt.fowwow30dwast10avg)
    twacksignawstats(shawesnonempty, (˘ω˘) shawesnonzewo, :3 w-wesuwt.shawe7dwast10avg)
    twacksignawstats(wepwiesnonempty, (˘ω˘) wepwiesnonzewo, nyaa~~ w-wesuwt.wepwy7dwast10avg)
    twacksignawstats(owiginawtweetsnonempty, (U ﹏ U) o-owiginawtweetsnonzewo, nyaa~~ w-wesuwt.owiginawtweet7dwast10avg)
    twacksignawstats(videoviewsnonempty, ^^;; v-videoviewsnonzewo, OwO wesuwt.videopwayback7dwast10avg)
    t-twacksignawstats(bwocknonempty, nyaa~~ bwocknonzewo, UwU w-wesuwt.bwock30dwast10avg)
    twacksignawstats(mutenonempty, 😳 mutenonzewo, w-wesuwt.mute30dwast10avg)
    t-twacksignawstats(wepowtnonempty, 😳 w-wepowtnonzewo, (ˆ ﻌ ˆ)♡ w-wesuwt.wepowt30dwast10avg)
    twacksignawstats(dontwikenonempty, (✿oωo) d-dontwikenonzewo, nyaa~~ w-wesuwt.dontwike30dwast10avg)
    t-twacksignawstats(seefewewnonempty, ^^ seefewewnonzewo, (///ˬ///✿) w-wesuwt.seefewew30dwast10avg)
  }

  pwivate def twacksignawstats(nonempty: c-countew, 😳 n-nyonzewo: countew, s-scowe: option[doubwe]): unit = {
    if (scowe.nonempty) {
      nyonempty.incw()

      if (scowe.get > 0)
        nyonzewo.incw()
    }
  }
}

o-object scowew {
  def avg(s: t-twavewsabwe[doubwe]): o-option[doubwe] =
    if (s.isempty) nyone ewse some(s.sum / s-s.size)
  def max(s: twavewsabwe[doubwe]): o-option[doubwe] =
    i-if (s.isempty) n-nyone ewse s-some(s.fowdweft(0.0d) { (cuww, òωó _max) => m-math.max(cuww, ^^;; _max) })

  pwivate def getauthowscoweid(
    usewid: usewid, rawr
    tweetid: t-tweetid
  ) = {
    scoweid(
      a-awgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, (ˆ ﻌ ˆ)♡
      intewnawid = scoweintewnawid.simcwustewsembeddingpaiwscoweid(
        simcwustewsembeddingpaiwscoweid(
          s-simcwustewsembeddingid(
            intewnawid = intewnawid.usewid(usewid), XD
            modewvewsion = modewvewsion.modew20m145k2020, >_<
            e-embeddingtype = e-embeddingtype.favbasedpwoducew
          ), (˘ω˘)
          simcwustewsembeddingid(
            i-intewnawid = intewnawid.tweetid(tweetid), 😳
            modewvewsion = m-modewvewsion.modew20m145k2020, o.O
            e-embeddingtype = embeddingtype.wogfavbasedtweet
          )
        ))
    )
  }

  p-pwivate def gettweetscoweid(
    s-souwcetweetid: tweetid, (ꈍᴗꈍ)
    candidatetweetid: tweetid
  ) = {
    s-scoweid(
      awgowithm = scowingawgowithm.paiwembeddingcosinesimiwawity, rawr x3
      i-intewnawid = s-scoweintewnawid.simcwustewsembeddingpaiwscoweid(
        s-simcwustewsembeddingpaiwscoweid(
          simcwustewsembeddingid(
            intewnawid = intewnawid.tweetid(souwcetweetid), ^^
            m-modewvewsion = modewvewsion.modew20m145k2020, OwO
            embeddingtype = embeddingtype.wogfavwongestw2embeddingtweet
          ), ^^
          simcwustewsembeddingid(
            intewnawid = i-intewnawid.tweetid(candidatetweetid), :3
            m-modewvewsion = m-modewvewsion.modew20m145k2020, o.O
            e-embeddingtype = embeddingtype.wogfavbasedtweet
          )
        ))
    )
  }
}
