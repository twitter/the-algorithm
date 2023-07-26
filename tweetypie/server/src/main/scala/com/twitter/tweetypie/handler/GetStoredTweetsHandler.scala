package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.stitch.stitch
impowt c-com.twittew.tweetypie.cowe.stowedtweetwesuwt._
i-impowt com.twittew.tweetypie.cowe.stowedtweetwesuwt
i-impowt c-com.twittew.tweetypie.cowe.tweetwesuwt
i-impowt com.twittew.tweetypie.fiewdid
i-impowt com.twittew.tweetypie.futuweawwow
impowt com.twittew.tweetypie.wepositowy.cachecontwow
impowt com.twittew.tweetypie.wepositowy.tweetquewy
i-impowt com.twittew.tweetypie.wepositowy.tweetwesuwtwepositowy
impowt c-com.twittew.tweetypie.thwiftscawa.{bouncedeweted => bouncedewetedstate}
i-impowt com.twittew.tweetypie.thwiftscawa.{fowceadded => fowceaddedstate}
impowt com.twittew.tweetypie.thwiftscawa.getstowedtweetswequest
i-impowt com.twittew.tweetypie.thwiftscawa.getstowedtweetsoptions
impowt com.twittew.tweetypie.thwiftscawa.getstowedtweetswesuwt
i-impowt com.twittew.tweetypie.thwiftscawa.{hawddeweted => h-hawddewetedstate}
impowt com.twittew.tweetypie.thwiftscawa.{notfound => nyotfoundstate}
impowt com.twittew.tweetypie.thwiftscawa.{softdeweted => s-softdewetedstate}
impowt com.twittew.tweetypie.thwiftscawa.statuscounts
impowt com.twittew.tweetypie.thwiftscawa.stowedtweetewwow
impowt c-com.twittew.tweetypie.thwiftscawa.stowedtweetinfo
impowt com.twittew.tweetypie.thwiftscawa.stowedtweetstate
i-impowt com.twittew.tweetypie.thwiftscawa.{undeweted => u-undewetedstate}

o-object g-getstowedtweetshandwew {
  type type = futuweawwow[getstowedtweetswequest, OwO s-seq[getstowedtweetswesuwt]]

  def appwy(tweetwepo: tweetwesuwtwepositowy.type): type = {
    f-futuweawwow[getstowedtweetswequest, 😳 seq[getstowedtweetswesuwt]] { wequest =>
      vaw wequestoptions: getstowedtweetsoptions =
        w-wequest.options.getowewse(getstowedtweetsoptions())
      vaw quewyoptions = t-totweetquewyoptions(wequestoptions)

      v-vaw wesuwt = s-stitch
        .twavewse(wequest.tweetids) { tweetid =>
          tweetwepo(tweetid, 😳😳😳 quewyoptions)
            .map(tostowedtweetinfo)
            .map(getstowedtweetswesuwt(_))
            .handwe {
              c-case _ =>
                g-getstowedtweetswesuwt(
                  stowedtweetinfo(
                    tweetid = tweetid, (˘ω˘)
                    e-ewwows = s-seq(stowedtweetewwow.faiwedfetch)
                  )
                )
            }
        }

      stitch.wun(wesuwt)
    }
  }

  p-pwivate def totweetquewyoptions(options: g-getstowedtweetsoptions): tweetquewy.options = {
    vaw countsfiewds: s-set[fiewdid] = set(
      s-statuscounts.favowitecountfiewd.id, ʘwʘ
      statuscounts.wepwycountfiewd.id, ( ͡o ω ͡o )
      s-statuscounts.wetweetcountfiewd.id, o.O
      s-statuscounts.quotecountfiewd.id
    )

    tweetquewy.options(
      incwude = gettweetshandwew.baseincwude.awso(
        tweetfiewds = set(tweet.countsfiewd.id) ++ options.additionawfiewdids, >w<
        countsfiewds = c-countsfiewds
      ), 😳
      c-cachecontwow = cachecontwow.nocache, 🥺
      e-enfowcevisibiwityfiwtewing = !options.bypassvisibiwityfiwtewing, rawr x3
      f-fowusewid = o-options.fowusewid, o.O
      wequiwesouwcetweet = fawse, rawr
      fetchstowedtweets = twue
    )
  }

  p-pwivate def tostowedtweetinfo(tweetwesuwt: tweetwesuwt): stowedtweetinfo = {
    def twanswateewwows(ewwows: seq[stowedtweetwesuwt.ewwow]): s-seq[stowedtweetewwow] = {
      ewwows.map {
        c-case stowedtweetwesuwt.ewwow.cowwupt => s-stowedtweetewwow.cowwupt
        c-case stowedtweetwesuwt.ewwow.fiewdsmissingowinvawid =>
          s-stowedtweetewwow.fiewdsmissingowinvawid
        c-case s-stowedtweetwesuwt.ewwow.scwubbedfiewdspwesent => s-stowedtweetewwow.scwubbedfiewdspwesent
        case stowedtweetwesuwt.ewwow.shouwdbehawddeweted => stowedtweetewwow.shouwdbehawddeweted
      }
    }

    v-vaw t-tweetdata = tweetwesuwt.vawue

    t-tweetdata.stowedtweetwesuwt m-match {
      case s-some(stowedtweetwesuwt) => {
        vaw (tweet, ʘwʘ stowedtweetstate, 😳😳😳 ewwows) = s-stowedtweetwesuwt match {
          case pwesent(ewwows, ^^;; _) => (some(tweetdata.tweet), o.O nyone, twanswateewwows(ewwows))
          case hawddeweted(softdewetedatmsec, (///ˬ///✿) hawddewetedatmsec) =>
            (
              s-some(tweetdata.tweet),
              some(
                stowedtweetstate.hawddeweted(
                  hawddewetedstate(softdewetedatmsec, σωσ h-hawddewetedatmsec))),
              s-seq()
            )
          c-case softdeweted(softdewetedatmsec, nyaa~~ ewwows, _) =>
            (
              s-some(tweetdata.tweet), ^^;;
              some(stowedtweetstate.softdeweted(softdewetedstate(softdewetedatmsec))), ^•ﻌ•^
              t-twanswateewwows(ewwows)
            )
          c-case bouncedeweted(dewetedatmsec, σωσ ewwows, _) =>
            (
              some(tweetdata.tweet), -.-
              some(stowedtweetstate.bouncedeweted(bouncedewetedstate(dewetedatmsec))), ^^;;
              twanswateewwows(ewwows)
            )
          case undeweted(undewetedatmsec, XD e-ewwows, 🥺 _) =>
            (
              some(tweetdata.tweet), òωó
              s-some(stowedtweetstate.undeweted(undewetedstate(undewetedatmsec))), (ˆ ﻌ ˆ)♡
              twanswateewwows(ewwows)
            )
          c-case fowceadded(addedatmsec, -.- e-ewwows, :3 _) =>
            (
              some(tweetdata.tweet), ʘwʘ
              some(stowedtweetstate.fowceadded(fowceaddedstate(addedatmsec))), 🥺
              t-twanswateewwows(ewwows)
            )
          c-case faiwed(ewwows) => (none, >_< nyone, ʘwʘ twanswateewwows(ewwows))
          c-case n-notfound => (none, (˘ω˘) some(stowedtweetstate.notfound(notfoundstate())), (✿oωo) seq())
        }

        stowedtweetinfo(
          tweetid = t-tweetdata.tweet.id, (///ˬ///✿)
          t-tweet = tweet.map(sanitizenuwwmediafiewds), rawr x3
          s-stowedtweetstate = stowedtweetstate, -.-
          e-ewwows = e-ewwows
        )
      }

      case nyone =>
        s-stowedtweetinfo(
          tweetid = tweetdata.tweet.id, ^^
          tweet = some(sanitizenuwwmediafiewds(tweetdata.tweet))
        )
    }
  }

  pwivate d-def sanitizenuwwmediafiewds(tweet: t-tweet): tweet = {
    // some media fiewds awe i-initiawized as `nuww` a-at the stowage wayew. (⑅˘꒳˘)
    // if the tweet is meant to be h-hawd deweted, nyaa~~ ow is nyot hydwated fow
    // some othew weason but the media entities s-stiww exist, /(^•ω•^) we sanitize
    // these fiewds t-to awwow sewiawization. (U ﹏ U)
    t-tweet.copy(media = tweet.media.map(_.map { mediaentity =>
      mediaentity.copy(
        u-uww = o-option(mediaentity.uww).getowewse(""), 😳😳😳
        mediauww = option(mediaentity.mediauww).getowewse(""), >w<
        mediauwwhttps = option(mediaentity.mediauwwhttps).getowewse(""), XD
        dispwayuww = option(mediaentity.dispwayuww).getowewse(""), o.O
        e-expandeduww = option(mediaentity.expandeduww).getowewse(""), mya
      )
    }))
  }
}
