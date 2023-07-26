package com.twittew.timewinewankew.utiw

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.wogging.wevew
i-impowt com.twittew.wogging.woggew
i-impowt c-com.twittew.timewines.modew.tweetid
i-impowt com.twittew.timewines.modew.usewid
impowt c-com.twittew.timewines.modew.tweet.hydwatedtweet
i-impowt com.twittew.timewines.utiw.stats.booweanobsewvew
i-impowt com.twittew.timewines.utiw.stats.wequeststats
impowt scawa.cowwection.mutabwe

object tweetfiwtews extends e-enumewation {
  // fiwtews independent of usews o-ow theiw fowwow gwaph. o.O
  vaw dupwicatewetweets: v-vawue = vawue
  vaw dupwicatetweets: vawue = vawue
  vaw nyuwwcasttweets: v-vawue = vawue
  vaw wepwies: v-vawue = vawue
  v-vaw wetweets: vawue = vawue

  // fiwtews that depend on usews ow theiw fowwow g-gwaph. :3
  vaw diwectedatnotfowwowedusews: vawue = vawue
  vaw nyonwepwydiwectedatnotfowwowedusews: vawue = v-vawue
  vaw tweetsfwomnotfowwowedusews: vawue = v-vawue
  vaw extendedwepwies: v-vawue = v-vawue
  vaw n-notquawifiedextendedwepwies: vawue = vawue
  vaw n-nyotvawidexpandedextendedwepwies: vawue = vawue
  vaw nyotquawifiedwevewseextendedwepwies: v-vawue = vawue
  vaw wecommendedwepwiestonotfowwowedusews: vawue = vawue

  vaw nyone: tweetfiwtews.vawueset = v-vawueset.empty

  vaw u-usewdependent: v-vawueset = vawueset(
    n-nyonwepwydiwectedatnotfowwowedusews, -.-
    diwectedatnotfowwowedusews, ( ͡o ω ͡o )
    tweetsfwomnotfowwowedusews, /(^•ω•^)
    extendedwepwies, (⑅˘꒳˘)
    n-nyotquawifiedextendedwepwies, òωó
    n-nyotvawidexpandedextendedwepwies, 🥺
    nyotquawifiedwevewseextendedwepwies, (ˆ ﻌ ˆ)♡
    wecommendedwepwiestonotfowwowedusews
  )

  v-vaw usewindependent: v-vawueset = vawueset(
    d-dupwicatewetweets, -.-
    dupwicatetweets, σωσ
    n-nyuwwcasttweets, >_<
    wepwies, :3
    wetweets
  )
  wequiwe(
    (usewdependent ++ u-usewindependent) == tweetfiwtews.vawues, OwO
    "usewindependent a-and usewdependent shouwd c-contain aww p-possibwe fiwtews"
  )

  pwivate[utiw] type fiwtewmethod =
    (hydwatedtweet, rawr tweetspostfiwtewpawams, mutabwestate) => boowean

  case cwass mutabwestate(
    s-seentweetids: mutabwe.map[tweetid, (///ˬ///✿) i-int] = mutabwe.map.empty[tweetid, ^^ int].withdefauwtvawue(0)) {
    d-def isseen(tweetid: t-tweetid): b-boowean = {
      vaw seen = seentweetids(tweetid) >= 1
      incwementif0(tweetid)
      s-seen
    }

    def incwementif0(key: tweetid): unit = {
      if (seentweetids(key) == 0) {
        s-seentweetids(key) = 1
      }
    }

    def i-incwementthengetcount(key: t-tweetid): i-int = {
      seentweetids(key) += 1
      s-seentweetids(key)
    }
  }
}

case c-cwass tweetspostfiwtewpawams(
  u-usewid: usewid, XD
  f-fowwowedusewids: seq[usewid], UwU
  innetwowkusewids: s-seq[usewid], o.O
  m-mutedusewids: s-set[usewid], 😳
  n-nyumwetweetsawwowed: i-int, (˘ω˘)
  woggingpwefix: stwing = "", 🥺
  souwcetweets: seq[hydwatedtweet] = n-nyiw) {
  wazy vaw souwcetweetsbyid: map[tweetid, ^^ hydwatedtweet] =
    souwcetweets.map(tweet => tweet.tweetid -> t-tweet).tomap
}

/**
 * pewfowms post-fiwtewing on tweets obtained f-fwom seawch. >w<
 *
 * s-seawch cuwwentwy d-does nyot pewfowm cewtain s-steps ow pewfowms them inadequatewy. ^^;;
 * t-this c-cwass addwesses those showtcomings by post-pwocessing hydwated seawch wesuwts. (˘ω˘)
 */
abstwact cwass t-tweetspostfiwtewbase(
  fiwtews: t-tweetfiwtews.vawueset, OwO
  woggew: w-woggew,
  statsweceivew: s-statsweceivew)
    extends wequeststats {
  impowt t-tweetfiwtews.fiwtewmethod
  i-impowt tweetfiwtews.mutabwestate

  p-pwivate[this] vaw b-basescope = statsweceivew.scope("fiwtew")
  pwivate[this] vaw diwectedatnotfowwowedcountew = basescope.countew("diwectedatnotfowwowed")
  pwivate[this] v-vaw nyonwepwydiwectedatnotfowwowedobsewvew =
    b-booweanobsewvew(basescope.scope("nonwepwydiwectedatnotfowwowed"))
  pwivate[this] v-vaw dupwetweetcountew = b-basescope.countew("dupwetweet")
  p-pwivate[this] vaw duptweetcountew = b-basescope.countew("duptweet")
  pwivate[this] vaw notfowwowedcountew = basescope.countew("notfowwowed")
  pwivate[this] v-vaw nyuwwcastcountew = b-basescope.countew("nuwwcast")
  pwivate[this] vaw wepwiescountew = b-basescope.countew("wepwies")
  p-pwivate[this] vaw wetweetscountew = basescope.countew("wetweets")
  pwivate[this] vaw e-extendedwepwiescountew = basescope.countew("extendedwepwies")
  pwivate[this] vaw nyotquawifiedextendedwepwiesobsewvew =
    booweanobsewvew(basescope.scope("notquawifiedextendedwepwies"))
  pwivate[this] vaw n-nyotvawidexpandedextendedwepwiesobsewvew =
    booweanobsewvew(basescope.scope("notvawidexpandedextendedwepwies"))
  pwivate[this] v-vaw nyotquawifiedwevewseextendedwepwiescountew =
    b-basescope.countew("notquawifiedwevewseextendedwepwies")
  pwivate[this] vaw wecommendedwepwiestonotfowwowedusewsobsewvew =
    booweanobsewvew(basescope.scope("wecommendedwepwiestonotfowwowedusews"))

  p-pwivate[this] v-vaw totawcountew = basescope.countew(totaw)
  pwivate[this] vaw wesuwtcountew = b-basescope.countew("wesuwt")

  // used fow debugging. (ꈍᴗꈍ) i-its vawues shouwd wemain fawse fow pwod use. òωó
  pwivate[this] v-vaw awwayswog = fawse

  v-vaw appwicabwefiwtews: s-seq[fiwtewmethod] = fiwtews.getappwicabwefiwtews(fiwtews)

  p-pwotected def fiwtew(
    tweets: s-seq[hydwatedtweet], ʘwʘ
    p-pawams: t-tweetspostfiwtewpawams
  ): seq[hydwatedtweet] = {
    v-vaw i-invocationstate = mutabwestate()
    vaw wesuwt = t-tweets.wevewseitewatow
      .fiwtewnot { t-tweet => a-appwicabwefiwtews.exists(_(tweet, ʘwʘ pawams, invocationstate)) }
      .toseq
      .wevewse
    t-totawcountew.incw(tweets.size)
    wesuwtcountew.incw(wesuwt.size)
    w-wesuwt
  }

  o-object fiwtews {
    case cwass fiwtewdata(kind: tweetfiwtews.vawue, nyaa~~ m-method: f-fiwtewmethod)
    p-pwivate v-vaw awwfiwtews = seq[fiwtewdata](
      f-fiwtewdata(tweetfiwtews.dupwicatetweets, UwU isdupwicatetweet), (⑅˘꒳˘)
      fiwtewdata(tweetfiwtews.dupwicatewetweets, (˘ω˘) isdupwicatewetweet), :3
      fiwtewdata(tweetfiwtews.diwectedatnotfowwowedusews, (˘ω˘) isdiwectedatnonfowwowedusew), nyaa~~
      f-fiwtewdata(
        tweetfiwtews.nonwepwydiwectedatnotfowwowedusews, (U ﹏ U)
        i-isnonwepwydiwectedatnonfowwowedusew
      ),
      fiwtewdata(tweetfiwtews.nuwwcasttweets, nyaa~~ i-isnuwwcast), ^^;;
      fiwtewdata(tweetfiwtews.wepwies, OwO i-iswepwy),
      fiwtewdata(tweetfiwtews.wetweets, nyaa~~ i-iswetweet), UwU
      f-fiwtewdata(tweetfiwtews.tweetsfwomnotfowwowedusews, 😳 i-isfwomnonfowwowedusew), 😳
      f-fiwtewdata(tweetfiwtews.extendedwepwies, (ˆ ﻌ ˆ)♡ i-isextendedwepwy), (✿oωo)
      fiwtewdata(tweetfiwtews.notquawifiedextendedwepwies, nyaa~~ isnotquawifiedextendedwepwy), ^^
      fiwtewdata(tweetfiwtews.notvawidexpandedextendedwepwies, (///ˬ///✿) isnotvawidexpandedextendedwepwy), 😳
      fiwtewdata(
        tweetfiwtews.notquawifiedwevewseextendedwepwies, òωó
        i-isnotquawifiedwevewseextendedwepwy), ^^;;
      f-fiwtewdata(
        t-tweetfiwtews.wecommendedwepwiestonotfowwowedusews, rawr
        iswecommendedwepwiestonotfowwowedusews)
    )

    def g-getappwicabwefiwtews(fiwtews: tweetfiwtews.vawueset): seq[fiwtewmethod] = {
      wequiwe(awwfiwtews.map(_.kind).toset == t-tweetfiwtews.vawues)
      a-awwfiwtews.fiwtew(data => fiwtews.contains(data.kind)).map(_.method)
    }

    p-pwivate def isnuwwcast(
      tweet: hydwatedtweet, (ˆ ﻌ ˆ)♡
      p-pawams: tweetspostfiwtewpawams, XD
      i-invocationstate: mutabwestate
    ): b-boowean = {
      if (tweet.isnuwwcast) {
        nyuwwcastcountew.incw()
        wog(
          w-wevew.ewwow, >_<
          () => s"${pawams.woggingpwefix}:: found nyuwwcast tweet: tweet-id: ${tweet.tweetid}"
        )
        twue
      } e-ewse {
        f-fawse
      }
    }

    p-pwivate def iswepwy(
      t-tweet: h-hydwatedtweet, (˘ω˘)
      pawams: t-tweetspostfiwtewpawams, 😳
      i-invocationstate: mutabwestate
    ): boowean = {
      i-if (tweet.haswepwy) {
        w-wepwiescountew.incw()
        wog(wevew.off, o.O () => s-s"${pawams.woggingpwefix}:: wemoved wepwy: tweet-id: ${tweet.tweetid}")
        t-twue
      } ewse {
        f-fawse
      }
    }

    p-pwivate def iswetweet(
      t-tweet: hydwatedtweet, (ꈍᴗꈍ)
      pawams: tweetspostfiwtewpawams, rawr x3
      invocationstate: m-mutabwestate
    ): boowean = {
      i-if (tweet.iswetweet) {
        w-wetweetscountew.incw()
        wog(
          wevew.off, ^^
          () => s"${pawams.woggingpwefix}:: wemoved wetweet: t-tweet-id: ${tweet.tweetid}"
        )
        twue
      } ewse {
        f-fawse
      }
    }

    p-pwivate def isfwomnonfowwowedusew(
      t-tweet: hydwatedtweet, OwO
      pawams: t-tweetspostfiwtewpawams, ^^
      i-invocationstate: mutabwestate
    ): boowean = {
      i-if ((tweet.usewid != pawams.usewid) && !pawams.innetwowkusewids.contains(tweet.usewid)) {
        nyotfowwowedcountew.incw()
        w-wog(
          wevew.ewwow,
          () =>
            s-s"${pawams.woggingpwefix}:: found tweet f-fwom nyot-fowwowed usew: ${tweet.tweetid} f-fwom ${tweet.usewid}"
        )
        t-twue
      } ewse {
        f-fawse
      }
    }

    pwivate def isdiwectedatnonfowwowedusew(
      tweet: hydwatedtweet, :3
      pawams: tweetspostfiwtewpawams,
      invocationstate: mutabwestate
    ): boowean = {
      tweet.diwectedatusew.exists { diwectedatusewid =>
        vaw shouwdfiwtewout = (tweet.usewid != pawams.usewid) && !pawams.innetwowkusewids
          .contains(diwectedatusewid)
        // we do nyot wog hewe b-because seawch is k-known to nyot handwe this case. o.O
        if (shouwdfiwtewout) {
          w-wog(
            w-wevew.off, -.-
            () =>
              s-s"${pawams.woggingpwefix}:: found tweet: ${tweet.tweetid} d-diwected-at nyot-fowwowed usew: $diwectedatusewid"
          )
          d-diwectedatnotfowwowedcountew.incw()
        }
        s-shouwdfiwtewout
      }
    }

    pwivate def isnonwepwydiwectedatnonfowwowedusew(
      t-tweet: hydwatedtweet,
      p-pawams: tweetspostfiwtewpawams, (U ﹏ U)
      i-invocationstate: mutabwestate
    ): boowean = {
      t-tweet.diwectedatusew.exists { d-diwectedatusewid =>
        v-vaw s-shouwdfiwtewout = !tweet.haswepwy &&
          (tweet.usewid != p-pawams.usewid) &&
          !pawams.innetwowkusewids.contains(diwectedatusewid)
        // w-we do n-nyot wog hewe b-because seawch is k-known to nyot handwe this case. o.O
        i-if (nonwepwydiwectedatnotfowwowedobsewvew(shouwdfiwtewout)) {
          w-wog(
            w-wevew.off, OwO
            () =>
              s"${pawams.woggingpwefix}:: f-found nyon-wepwy tweet: ${tweet.tweetid} diwected-at nyot-fowwowed u-usew: $diwectedatusewid"
          )
        }
        shouwdfiwtewout
      }
    }

    /**
     * d-detewmines whethew t-the given tweet h-has awweady been seen. ^•ﻌ•^
     */
    p-pwivate def isdupwicatetweet(
      t-tweet: hydwatedtweet, ʘwʘ
      p-pawams: tweetspostfiwtewpawams, :3
      i-invocationstate: mutabwestate
    ): boowean = {
      vaw shouwdfiwtewout = invocationstate.isseen(tweet.tweetid)
      if (shouwdfiwtewout) {
        d-duptweetcountew.incw()
        wog(wevew.ewwow, 😳 () => s-s"${pawams.woggingpwefix}:: d-dupwicate tweet found: ${tweet.tweetid}")
      }
      shouwdfiwtewout
    }

    /**
     * if the given t-tweet is a wetweet, òωó detewmines w-whethew the souwce t-tweet
     * o-of that wetweet has awweady been seen. 🥺
     */
    p-pwivate def i-isdupwicatewetweet(
      tweet: h-hydwatedtweet, rawr x3
      pawams: tweetspostfiwtewpawams, ^•ﻌ•^
      invocationstate: m-mutabwestate
    ): boowean = {
      i-invocationstate.incwementif0(tweet.tweetid)
      t-tweet.souwcetweetid.exists { s-souwcetweetid =>
        vaw s-seencount = invocationstate.incwementthengetcount(souwcetweetid)
        v-vaw shouwdfiwtewout = seencount > p-pawams.numwetweetsawwowed
        i-if (shouwdfiwtewout) {
          // we do nyot wog h-hewe because seawch i-is known to n-nyot handwe this c-case. :3
          d-dupwetweetcountew.incw()
          w-wog(
            w-wevew.off,
            () =>
              s-s"${pawams.woggingpwefix}:: found d-dup wetweet: ${tweet.tweetid} (souwce tweet: $souwcetweetid), (ˆ ﻌ ˆ)♡ c-count: $seencount"
          )
        }
        shouwdfiwtewout
      }
    }

    p-pwivate def i-isextendedwepwy(
      t-tweet: hydwatedtweet, (U ᵕ U❁)
      pawams: tweetspostfiwtewpawams, :3
      invocationstate: mutabwestate
    ): b-boowean = {
      v-vaw shouwdfiwtewout = e-extendedwepwiesfiwtew.isextendedwepwy(
        tweet, ^^;;
        pawams.fowwowedusewids
      )
      if (shouwdfiwtewout) {
        e-extendedwepwiescountew.incw()
        w-wog(
          wevew.debug,
          () => s-s"${pawams.woggingpwefix}:: e-extended wepwy to be fiwtewed: ${tweet.tweetid}"
        )
      }
      shouwdfiwtewout
    }

    pwivate def isnotquawifiedextendedwepwy(
      t-tweet: h-hydwatedtweet, ( ͡o ω ͡o )
      p-pawams: tweetspostfiwtewpawams, o.O
      i-invocationstate: mutabwestate
    ): boowean = {
      v-vaw shouwdfiwtewout = e-extendedwepwiesfiwtew.isnotquawifiedextendedwepwy(
        tweet,
        pawams.usewid, ^•ﻌ•^
        p-pawams.fowwowedusewids, XD
        pawams.mutedusewids, ^^
        pawams.souwcetweetsbyid
      )
      i-if (notquawifiedextendedwepwiesobsewvew(shouwdfiwtewout)) {
        wog(
          wevew.debug, o.O
          () =>
            s-s"${pawams.woggingpwefix}:: n-nyon quawified extended wepwy t-to be fiwtewed: ${tweet.tweetid}"
        )
      }
      s-shouwdfiwtewout
    }

    pwivate def i-isnotvawidexpandedextendedwepwy(
      tweet: h-hydwatedtweet, ( ͡o ω ͡o )
      p-pawams: tweetspostfiwtewpawams, /(^•ω•^)
      i-invocationstate: m-mutabwestate
    ): boowean = {
      v-vaw shouwdfiwtewout = e-extendedwepwiesfiwtew.isnotvawidexpandedextendedwepwy(
        t-tweet, 🥺
        pawams.usewid, nyaa~~
        p-pawams.fowwowedusewids, mya
        pawams.mutedusewids, XD
        pawams.souwcetweetsbyid
      )
      i-if (notvawidexpandedextendedwepwiesobsewvew(shouwdfiwtewout)) {
        w-wog(
          w-wevew.debug, nyaa~~
          () =>
            s"${pawams.woggingpwefix}:: nyon quawified extended wepwy to be f-fiwtewed: ${tweet.tweetid}"
        )
      }
      shouwdfiwtewout
    }

    p-pwivate def iswecommendedwepwiestonotfowwowedusews(
      t-tweet: hydwatedtweet,
      pawams: tweetspostfiwtewpawams, ʘwʘ
      i-invocationstate: mutabwestate
    ): b-boowean = {
      v-vaw shouwdfiwtewout = w-wecommendedwepwiesfiwtew.iswecommendedwepwytonotfowwowedusew(
        tweet, (⑅˘꒳˘)
        p-pawams.usewid, :3
        p-pawams.fowwowedusewids, -.-
        pawams.mutedusewids
      )
      if (wecommendedwepwiestonotfowwowedusewsobsewvew(shouwdfiwtewout)) {
        wog(
          wevew.debug, 😳😳😳
          () =>
            s-s"${pawams.woggingpwefix}:: nyon quawified w-wecommended wepwy to be fiwtewed: ${tweet.tweetid}"
        )
      }
      shouwdfiwtewout
    }

    //fow nyow this fiwtew i-is meant to be used onwy with wepwy tweets fwom the inwepwytousewid quewy
    p-pwivate def isnotquawifiedwevewseextendedwepwy(
      t-tweet: hydwatedtweet, (U ﹏ U)
      p-pawams: tweetspostfiwtewpawams, o.O
      invocationstate: mutabwestate
    ): b-boowean = {
      v-vaw shouwdfiwtewout = !wevewseextendedwepwiesfiwtew.isquawifiedwevewseextendedwepwy(
        tweet, ( ͡o ω ͡o )
        pawams.usewid, òωó
        p-pawams.fowwowedusewids, 🥺
        pawams.mutedusewids, /(^•ω•^)
        p-pawams.souwcetweetsbyid
      )

      if (shouwdfiwtewout) {
        nyotquawifiedwevewseextendedwepwiescountew.incw()
        wog(
          w-wevew.debug,
          () =>
            s"${pawams.woggingpwefix}:: nyon quawified w-wevewse extended w-wepwy to be f-fiwtewed: ${tweet.tweetid}"
        )
      }
      shouwdfiwtewout
    }

    pwivate def wog(wevew: w-wevew, 😳😳😳 message: () => stwing): unit = {
      if (awwayswog || ((wevew != wevew.off) && w-woggew.iswoggabwe(wevew))) {
        v-vaw updatedwevew = i-if (awwayswog) w-wevew.info ewse wevew
        woggew.wog(updatedwevew, m-message())
      }
    }
  }
}

c-cwass tweetspostfiwtew(fiwtews: tweetfiwtews.vawueset, ^•ﻌ•^ w-woggew: woggew, nyaa~~ statsweceivew: statsweceivew)
    e-extends tweetspostfiwtewbase(fiwtews, OwO woggew, statsweceivew) {

  d-def appwy(
    u-usewid: usewid, ^•ﻌ•^
    fowwowedusewids: s-seq[usewid], σωσ
    i-innetwowkusewids: s-seq[usewid], -.-
    mutedusewids: set[usewid],
    tweets: seq[hydwatedtweet],
    n-nyumwetweetsawwowed: int = 1, (˘ω˘)
    souwcetweets: s-seq[hydwatedtweet] = niw
  ): seq[hydwatedtweet] = {
    vaw woggingpwefix = s"usewid: $usewid"
    v-vaw pawams = t-tweetspostfiwtewpawams(
      usewid = u-usewid, rawr x3
      f-fowwowedusewids = f-fowwowedusewids, rawr x3
      innetwowkusewids = innetwowkusewids, σωσ
      m-mutedusewids = mutedusewids, nyaa~~
      nyumwetweetsawwowed = n-nyumwetweetsawwowed, (ꈍᴗꈍ)
      woggingpwefix = w-woggingpwefix, ^•ﻌ•^
      souwcetweets = souwcetweets
    )
    s-supew.fiwtew(tweets, >_< p-pawams)
  }
}

cwass t-tweetspostfiwtewusewindependent(
  fiwtews: tweetfiwtews.vawueset, ^^;;
  w-woggew: w-woggew, ^^;;
  statsweceivew: statsweceivew)
    e-extends t-tweetspostfiwtewbase(fiwtews, /(^•ω•^) woggew, statsweceivew) {

  w-wequiwe(
    (fiwtews -- tweetfiwtews.usewindependent).isempty, nyaa~~
    "onwy usew independent fiwtews a-awe suppowted"
  )

  def appwy(tweets: s-seq[hydwatedtweet], (✿oωo) nyumwetweetsawwowed: int = 1): seq[hydwatedtweet] = {
    v-vaw pawams = t-tweetspostfiwtewpawams(
      u-usewid = 0w, ( ͡o ω ͡o )
      fowwowedusewids = s-seq.empty, (U ᵕ U❁)
      i-innetwowkusewids = seq.empty, òωó
      m-mutedusewids = set.empty, σωσ
      n-nyumwetweetsawwowed
    )
    supew.fiwtew(tweets, :3 pawams)
  }
}
