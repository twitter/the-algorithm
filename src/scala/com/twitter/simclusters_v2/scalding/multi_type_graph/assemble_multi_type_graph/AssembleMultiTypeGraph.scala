package com.twittew.simcwustews_v2.scawding
package m-muwti_type_gwaph.assembwe_muwti_type_gwaph

impowt c-com.twittew.bijection.scwooge.binawyscawacodec
i-impowt com.twittew.scawding_intewnaw.job.wequiwedbinawycompawatows.owdsew
impowt c-com.twittew.scawding.typed.typedpipe
i-impowt c-com.twittew.scawding.{datewange, ^^;; d-days, (˘ω˘) stat, uniqueid}
i-impowt com.twittew.scawding_intewnaw.dawv2.daw
impowt com.twittew.simcwustews_v2.scawding.embedding.common.extewnawdatasouwces
impowt com.twittew.simcwustews_v2.thwiftscawa.{
  weftnode, OwO
  n-nyoun,
  wightnode, (ꈍᴗꈍ)
  wightnodetype, òωó
  wightnodewithedgeweight
}
i-impowt java.utiw.timezone
impowt com.twittew.iesouwce.thwiftscawa.{intewactionevent, i-intewactiontype, ʘwʘ wefewencetweet}
impowt com.twittew.simcwustews_v2.common.{countwy, ʘwʘ w-wanguage, nyaa~~ topicid, tweetid, UwU usewid}
i-impowt com.twittew.usewsouwce.snapshot.combined.usewsouwcescawadataset
i-impowt com.twittew.fwigate.data_pipewine.magicwecs.magicwecs_notifications_wite.thwiftscawa.magicwecsnotificationwite
impowt com.twittew.twadoop.usew.gen.thwiftscawa.combinedusew

object assembwemuwtitypegwaph {
  impowt config._

  i-impwicit vaw nyounowdewing: owdewing[noun] = new owdewing[noun] {
    // we d-define an owdewing fow each nyoun t-type as specified i-in simcwustews_v2/muwti_type_gwaph.thwift
    // p-pwease make s-suwe we don't wemove anything hewe that's stiww a-a pawt of the union nyoun thwift and
    // vice v-vewsa, (⑅˘꒳˘) if we add a nyew nyoun type to thwift, (˘ω˘) an owdewing fow it nyeeds to added hewe as weww. :3
    d-def nyountypeowdew(noun: nyoun): i-int = nyoun m-match {
      c-case _: nyoun.usewid => 0
      case _: nyoun.countwy => 1
      case _: nyoun.wanguage => 2
      case _: nyoun.quewy => 3
      c-case _: nyoun.topicid => 4
      c-case _: nyoun.tweetid => 5
    }

    ovewwide d-def compawe(x: n-nyoun, (˘ω˘) y: nyoun): int = (x, nyaa~~ y) m-match {
      case (noun.usewid(a), (U ﹏ U) nyoun.usewid(b)) => a-a compawe b
      case (noun.countwy(a), nyaa~~ nyoun.countwy(b)) => a-a compawe b
      case (noun.wanguage(a), ^^;; n-nyoun.wanguage(b)) => a compawe b-b
      case (noun.quewy(a), OwO n-nyoun.quewy(b)) => a compawe b
      case (noun.topicid(a), nyaa~~ nyoun.topicid(b)) => a compawe b
      case (noun.tweetid(a), UwU n-nyoun.tweetid(b)) => a-a compawe b
      case (nouna, 😳 n-nyounb) => n-nyountypeowdew(nouna) c-compawe nyountypeowdew(nounb)
    }
  }
  impwicit vaw wightnodetypeowdewing: o-owdewing[wightnodetype] = owdsew[wightnodetype]

  impwicit vaw wightnodetypewithnounowdewing: owdewing[wightnode] =
    n-nyew owdewing[wightnode] {
      ovewwide def c-compawe(x: wightnode, 😳 y-y: wightnode): i-int = {
        owdewing
          .tupwe2(wightnodetypeowdewing, (ˆ ﻌ ˆ)♡ n-nyounowdewing)
          .compawe((x.wightnodetype, (✿oωo) x-x.noun), nyaa~~ (y.wightnodetype, ^^ y-y.noun))
      }
    }

  d-def getusewtweetintewactiongwaph(
    tweetintewactionevents: typedpipe[intewactionevent],
  )(
    impwicit uniqueid: u-uniqueid
  ): t-typedpipe[(weftnode, (///ˬ///✿) w-wightnodewithedgeweight)] = {
    v-vaw n-nyumusewtweetintewactionentwies = stat("num_usew_tweet_intewaction_entwies")
    vaw nyumdistinctusewtweetintewactionentwies = stat("num_distinct_usew_tweet_intewaction_entwies")
    v-vaw nyumfavedtweets = stat("num_faved_tweets")
    vaw numwepwiedtweets = stat("num_wepwied_tweets")
    vaw nyumwetweetedtweets = stat("num_wetweeted_tweets")
    v-vaw usewtweetintewactionsbytype: typedpipe[((usewid, 😳 wightnodetype), òωó t-tweetid)] =
      t-tweetintewactionevents
        .fwatmap { e-event =>
          vaw wefewencetweet: o-option[wefewencetweet] = event.wefewencetweet
          v-vaw t-tawgetid: wong = event.tawgetid
          vaw usewid: wong = event.engagingusewid

          //  to find the id of the tweet that w-was intewacted with
          //  f-fow wikes, ^^;; this is the tawgetid; f-fow wetweet o-ow wepwy, rawr it is the wefewencetweet's id
          //  o-one thing t-to nyote is that fow wikes, (ˆ ﻌ ˆ)♡ wefewencetweet i-is empty
          vaw (tweetidopt, XD w-wightnodetypeopt) = {
            event.intewactiontype match {
              case some(intewactiontype.favowite) =>
                // o-onwy awwow f-favowites on o-owiginaw tweets, >_< not wetweets, (˘ω˘) to a-avoid doubwe-counting
                // b-because we have wetweet-type t-tweets in the data souwce as weww
                (
                  if (wefewencetweet.isempty) {
                    nyumfavedtweets.inc()
                    s-some(tawgetid)
                  } e-ewse none, 😳
                  some(wightnodetype.favtweet))
              c-case some(intewactiontype.wepwy) =>
                n-nyumwepwiedtweets.inc()
                (wefewencetweet.map(_.tweetid), o.O some(wightnodetype.wepwytweet))
              case some(intewactiontype.wetweet) =>
                nyumwetweetedtweets.inc()
                (wefewencetweet.map(_.tweetid), (ꈍᴗꈍ) s-some(wightnodetype.wetweettweet))
              case _ => (none, rawr x3 nyone)
            }
          }
          fow {
            tweetid <- t-tweetidopt
            wightnodetype <- wightnodetypeopt
          } y-yiewd {
            n-nyumusewtweetintewactionentwies.inc()
            ((usewid, ^^ wightnodetype), OwO tweetid)
          }
        }

    usewtweetintewactionsbytype
      .mapvawues(set(_))
      .sumbykey
      .fwatmap {
        c-case ((usewid, ^^ wightnodetype), :3 tweetidset) =>
          t-tweetidset.map { tweetid =>
            nyumdistinctusewtweetintewactionentwies.inc()
            (
              weftnode.usewid(usewid), o.O
              w-wightnodewithedgeweight(
                wightnode = w-wightnode(wightnodetype = wightnodetype, -.- nyoun = nyoun.tweetid(tweetid)), (U ﹏ U)
                weight = 1.0))
          }
      }
  }

  d-def getusewfavgwaph(
    usewusewfavedges: t-typedpipe[(usewid, o.O u-usewid, OwO doubwe)]
  )(
    i-impwicit uniqueid: uniqueid
  ): t-typedpipe[(weftnode, ^•ﻌ•^ w-wightnodewithedgeweight)] = {
    v-vaw nyuminputfavedges = stat("num_input_fav_edges")
    u-usewusewfavedges.map {
      case (swcid, ʘwʘ d-destid, :3 edgewt) =>
        nyuminputfavedges.inc()
        (
          w-weftnode.usewid(swcid), 😳
          w-wightnodewithedgeweight(
            w-wightnode =
              wightnode(wightnodetype = wightnodetype.favusew, òωó n-nyoun = nyoun.usewid(destid)),
            weight = edgewt))
    }
  }

  def g-getusewfowwowgwaph(
    u-usewusewfowwowedges: typedpipe[(usewid, 🥺 usewid)]
  )(
    impwicit uniqueid: u-uniqueid
  ): t-typedpipe[(weftnode, rawr x3 w-wightnodewithedgeweight)] = {
    v-vaw nyumfwockfowwowedges = s-stat("num_fwock_fowwow_edges")
    usewusewfowwowedges.map {
      case (swcid, ^•ﻌ•^ destid) =>
        nyumfwockfowwowedges.inc()
        (
          weftnode.usewid(swcid), :3
          w-wightnodewithedgeweight(
            wightnode =
              w-wightnode(wightnodetype = wightnodetype.fowwowusew, (ˆ ﻌ ˆ)♡ noun = n-nyoun.usewid(destid)), (U ᵕ U❁)
            weight = 1.0))
    }
  }

  d-def getusewbwockgwaph(
    usewusewbwockedges: t-typedpipe[(usewid, :3 u-usewid)]
  )(
    i-impwicit u-uniqueid: uniqueid
  ): t-typedpipe[(weftnode, ^^;; wightnodewithedgeweight)] = {
    vaw nyumfwockbwockedges = stat("num_fwock_bwock_edges")
    usewusewbwockedges.map {
      case (swcid, ( ͡o ω ͡o ) destid) =>
        nyumfwockbwockedges.inc()
        (
          w-weftnode.usewid(swcid),
          w-wightnodewithedgeweight(
            w-wightnode =
              wightnode(wightnodetype = w-wightnodetype.bwockusew, o.O nyoun = nyoun.usewid(destid)), ^•ﻌ•^
            weight = 1.0))
    }
  }

  d-def getusewabusewepowtgwaph(
    u-usewusewabusewepowtedges: typedpipe[(usewid, XD u-usewid)]
  )(
    impwicit uniqueid: uniqueid
  ): t-typedpipe[(weftnode, ^^ w-wightnodewithedgeweight)] = {
    vaw n-nyumfwockabuseedges = s-stat("num_fwock_abuse_edges")
    usewusewabusewepowtedges.map {
      case (swcid, o.O destid) =>
        nyumfwockabuseedges.inc()
        (
          w-weftnode.usewid(swcid), ( ͡o ω ͡o )
          w-wightnodewithedgeweight(
            w-wightnode =
              w-wightnode(wightnodetype = w-wightnodetype.abusewepowtusew, /(^•ω•^) noun = nyoun.usewid(destid)),
            w-weight = 1.0))
    }
  }

  d-def fiwtewinvawidusews(
    f-fwockedges: t-typedpipe[(usewid, 🥺 usewid)], nyaa~~
    v-vawidusews: typedpipe[usewid]
  ): typedpipe[(usewid, mya u-usewid)] = {
    fwockedges
      .join(vawidusews.askeys)
      //      .withweducews(10000)
      .map {
        c-case (swcid, XD (destid, _)) =>
          (destid, nyaa~~ s-swcid)
      }
      .join(vawidusews.askeys)
      //      .withweducews(10000)
      .map {
        case (destid, ʘwʘ (swcid, (⑅˘꒳˘) _)) =>
          (swcid, :3 d-destid)
      }
  }

  def getusewspamwepowtgwaph(
    usewusewspamwepowtedges: t-typedpipe[(usewid, -.- u-usewid)]
  )(
    i-impwicit uniqueid: uniqueid
  ): typedpipe[(weftnode, 😳😳😳 wightnodewithedgeweight)] = {
    v-vaw nyumfwockspamedges = stat("num_fwock_spam_edges")
    usewusewspamwepowtedges.map {
      c-case (swcid, (U ﹏ U) d-destid) =>
        nyumfwockspamedges.inc()
        (
          w-weftnode.usewid(swcid), o.O
          wightnodewithedgeweight(
            w-wightnode =
              w-wightnode(wightnodetype = wightnodetype.spamwepowtusew, ( ͡o ω ͡o ) nyoun = nyoun.usewid(destid)), òωó
            weight = 1.0))
    }
  }

  d-def getusewtopicfowwowgwaph(
    topicusewfowwowedbyedges: typedpipe[(topicid, 🥺 u-usewid)]
  )(
    i-impwicit uniqueid: uniqueid
  ): t-typedpipe[(weftnode, /(^•ω•^) wightnodewithedgeweight)] = {
    v-vaw nyumtfgedges = s-stat("num_tfg_edges")
    t-topicusewfowwowedbyedges.map {
      case (topicid, 😳😳😳 usewid) =>
        nyumtfgedges.inc()
        (
          weftnode.usewid(usewid), ^•ﻌ•^
          wightnodewithedgeweight(
            wightnode =
              wightnode(wightnodetype = wightnodetype.fowwowtopic, nyaa~~ nyoun = noun.topicid(topicid)), OwO
            weight = 1.0)
        )
    }
  }

  def getusewsignupcountwygwaph(
    usewsignupcountwyedges: typedpipe[(usewid, ^•ﻌ•^ (countwy, wanguage))]
  )(
    i-impwicit uniqueid: u-uniqueid
  ): typedpipe[(weftnode, σωσ wightnodewithedgeweight)] = {
    v-vaw n-nyumusewsouwceentwieswead = s-stat("num_usew_souwce_entwies")
    usewsignupcountwyedges.map {
      c-case (usewid, -.- (countwy, (˘ω˘) wang)) =>
        n-nyumusewsouwceentwieswead.inc()
        (
          w-weftnode.usewid(usewid), rawr x3
          wightnodewithedgeweight(
            w-wightnode =
              wightnode(wightnodetype = w-wightnodetype.signupcountwy, rawr x3 n-nyoun = nyoun.countwy(countwy)), σωσ
            weight = 1.0))
    }
  }

  d-def getmagicwecsnotifopenowcwicktweetsgwaph(
    u-usewmwnotifopenowcwickevents: t-typedpipe[magicwecsnotificationwite]
  )(
    i-impwicit uniqueid: u-uniqueid
  ): t-typedpipe[(weftnode, nyaa~~ w-wightnodewithedgeweight)] = {
    v-vaw nyumnotifopenowcwickentwies = s-stat("num_notif_open_ow_cwick")
    usewmwnotifopenowcwickevents.fwatmap { e-entwy =>
      n-nyumnotifopenowcwickentwies.inc()
      f-fow {
        usewid <- e-entwy.tawgetusewid
        tweetid <- entwy.tweetid
      } yiewd {
        (
          w-weftnode.usewid(usewid), (ꈍᴗꈍ)
          wightnodewithedgeweight(
            wightnode = w-wightnode(
              w-wightnodetype = w-wightnodetype.notifopenowcwicktweet, ^•ﻌ•^
              nyoun = n-nyoun.tweetid(tweetid)), >_<
            weight = 1.0))
      }
    }
  }

  d-def getusewconsumedwanguagesgwaph(
    u-usewconsumedwanguageedges: typedpipe[(usewid, ^^;; seq[(wanguage, ^^;; d-doubwe)])]
  )(
    impwicit uniqueid: uniqueid
  ): typedpipe[(weftnode, /(^•ω•^) wightnodewithedgeweight)] = {
    v-vaw nyumpenguinsouwceentwieswead = s-stat("num_penguin_souwce_entwies")
    u-usewconsumedwanguageedges.fwatmap {
      case (usewid, nyaa~~ wangwithweights) =>
        nyumpenguinsouwceentwieswead.inc()
        wangwithweights.map {
          c-case (wang, (✿oωo) weight) =>
            (
              w-weftnode.usewid(usewid), ( ͡o ω ͡o )
              w-wightnodewithedgeweight(
                w-wightnode = wightnode(
                  wightnodetype = w-wightnodetype.consumedwanguage,
                  n-nyoun = nyoun.wanguage(wang)), (U ᵕ U❁)
                weight = weight))
        }
    }
  }

  d-def getseawchgwaph(
    usewseawchquewyedges: t-typedpipe[(usewid, òωó stwing)]
  )(
    impwicit u-uniqueid: u-uniqueid
  ): t-typedpipe[(weftnode, σωσ wightnodewithedgeweight)] = {
    v-vaw nyumseawchquewies = stat("num_seawch_quewies")
    u-usewseawchquewyedges.map {
      case (usewid, :3 q-quewy) =>
        nyumseawchquewies.inc()
        (
          w-weftnode.usewid(usewid), OwO
          wightnodewithedgeweight(
            w-wightnode =
              w-wightnode(wightnodetype = w-wightnodetype.seawchquewy, ^^ n-nyoun = nyoun.quewy(quewy)), (˘ω˘)
            w-weight = 1.0))
    }
  }

  d-def buiwdempwoyeegwaph(
    f-fuwwgwaph: typedpipe[(weftnode, OwO w-wightnodewithedgeweight)]
  )(
    impwicit uniqueid: u-uniqueid
  ): typedpipe[(weftnode, w-wightnodewithedgeweight)] = {
    vaw n-nyumempwoyeeedges = s-stat("num_empwoyee_edges")
    v-vaw empwoyeeids = config.sampwedempwoyeeids
    fuwwgwaph
      .cowwect {
        case (weftnode.usewid(usewid), UwU w-wightnodewithweight) i-if empwoyeeids.contains(usewid) =>
          n-nyumempwoyeeedges.inc()
          (weftnode.usewid(usewid), ^•ﻌ•^ wightnodewithweight)
      }
  }

  def gettwuncatedgwaph(
    fuwwgwaph: typedpipe[(weftnode, (ꈍᴗꈍ) w-wightnodewithedgeweight)], /(^•ω•^)
    t-topkwithfwequency: typedpipe[(wightnodetype, (U ᵕ U❁) s-seq[(noun, (✿oωo) doubwe)])]
  )(
    impwicit u-uniqueid: uniqueid
  ): typedpipe[(weftnode, OwO wightnodewithedgeweight)] = {
    v-vaw nyumentwiestwuncatedgwaph = s-stat("num_entwies_twuncated_gwaph")
    vaw n-nyumtopktwuncatednouns = s-stat("num_topk_twuncated_nouns")

    impwicit vaw wightnodesew: wightnode => a-awway[byte] = b-binawyscawacodec(wightnode)
    vaw topnouns: typedpipe[wightnode] = t-topkwithfwequency
      .fwatmap {
        case (wightnodetype, :3 nyounswist) =>
          n-nyounswist
            .map {
              case (nounvaw, nyaa~~ a-aggwegatedfwequency) =>
                n-nyumtopktwuncatednouns.inc()
                wightnode(wightnodetype, ^•ﻌ•^ nyounvaw)
            }
      }

    f-fuwwgwaph
      .map {
        c-case (weftnode, ( ͡o ω ͡o ) wightnodewithweight) =>
          (wightnodewithweight.wightnode, ^^;; (weftnode, mya w-wightnodewithweight))
      }
      .sketch(weducews = 5000)
      .join(topnouns.askeys.totypedpipe)
      .map {
        case (wightnode, ((weft, (U ᵕ U❁) w-wightnodewithweight), _)) =>
          n-nyumentwiestwuncatedgwaph.inc()
          (weft, ^•ﻌ•^ w-wightnodewithweight)
      }
  }

  d-def gettopkwightnounswithfwequencies(
    fuwwgwaph: t-typedpipe[(weftnode, (U ﹏ U) w-wightnodewithedgeweight)], /(^•ω•^)
    t-topkconfig: map[wightnodetype, ʘwʘ i-int], XD
    minfwequency: int
  )(
    impwicit u-uniqueid: u-uniqueid
  ): typedpipe[(wightnodetype, s-seq[(noun, (⑅˘꒳˘) doubwe)])] = {
    vaw maxacwosswightnountype: int = topkconfig.vawuesitewatow.max
    fuwwgwaph
      .map {
        c-case (weftnode, nyaa~~ wightnodewithweight) =>
          (wightnodewithweight.wightnode, UwU 1.0)
      }
      .sumbykey
      //      .withweducews(20000)
      .totypedpipe
      .fiwtew(_._2 >= m-minfwequency)
      .map {
        c-case (wightnode, (˘ω˘) fweq) =>
          (wightnode.wightnodetype, rawr x3 (wightnode.noun, (///ˬ///✿) fweq))
      }
      .gwoup(wightnodetypeowdewing)
      // n-nyote: if maxacwosswightnountype is >15m, 😳😳😳 it might w-wesuwt in oom o-on weducew
      .sowtedwevewsetake(maxacwosswightnountype)(owdewing.by(_._2))
      // a-an awtewnative t-to using g-gwoup fowwowed by sowtedwevewsetake is to define topkmonoids, (///ˬ///✿)
      // one fow e-each wightnodetype to get the m-most fwequent wightnouns
      .map {
        case (wightnodetype, ^^;; nyounswistwithfweq) =>
          vaw twuncatedwist = n-nyounswistwithfweq
            .sowtby(-_._2)
            .take(topkconfig.getowewse(wightnodetype, ^^ nyumtopnounsfowunknownwightnodetype))
          (wightnodetype, (///ˬ///✿) twuncatedwist)
      }
  }

  def getvawidusews(
    usewsouwce: typedpipe[combinedusew]
  )(
    i-impwicit u-uniqueid: uniqueid
  ): typedpipe[usewid] = {
    v-vaw nyumvawidusews = stat("num_vawid_usews")
    usewsouwce
      .fwatmap { u-u =>
        f-fow {
          usew <- u.usew
          i-if usew.id != 0
          safety <- u-usew.safety
          if !(safety.suspended || safety.deactivated)
        } yiewd {
          nyumvawidusews.inc()
          usew.id
        }
      }
  }

  def g-getfuwwgwaph(
  )(
    impwicit datewange: datewange, -.-
    t-timezone: t-timezone, /(^•ω•^)
    u-uniqueid: uniqueid
  ): typedpipe[(weftnode, UwU wightnodewithedgeweight)] = {

    // w-wist of vawid usewids - to fiwtew out deactivated ow suspended usew accounts
    v-vaw usewsouwce: t-typedpipe[combinedusew] =
      d-daw
        .weadmostwecentsnapshotnoowdewthan(usewsouwcescawadataset, d-days(7)).totypedpipe
    vaw vawidusews: typedpipe[usewid] = g-getvawidusews(usewsouwce).fowcetodisk

    //dataset w-wead opewations

    // iesouwce tweet engagements d-data fow tweet favs, (⑅˘꒳˘) wepwies, wetweets - fwom w-wast 14 days
    vaw tweetsouwce: typedpipe[intewactionevent] =
      e-extewnawdatasouwces.iesouwcetweetengagementssouwce(datewange =
        d-datewange(datewange.end - days(14), ʘwʘ d-datewange.end))

    // u-usew-usew f-fav edges
    vaw usewusewfavedges: typedpipe[(usewid, σωσ u-usewid, doubwe)] =
      extewnawdatasouwces.getfavedges(hawfwifeindaysfowfavscowe)

    // u-usew-usew fowwow edges
    vaw usewusewfowwowedges: typedpipe[(usewid, ^^ u-usewid)] =
      f-fiwtewinvawidusews(extewnawdatasouwces.fwockfowwowssouwce, OwO v-vawidusews)

    // u-usew-usew bwock e-edges
    vaw usewusewbwockedges: typedpipe[(usewid, (ˆ ﻌ ˆ)♡ u-usewid)] =
      fiwtewinvawidusews(extewnawdatasouwces.fwockbwockssouwce, o.O vawidusews)

    // u-usew-usew abuse wepowt edges
    v-vaw usewusewabusewepowtedges: typedpipe[(usewid, (˘ω˘) usewid)] =
      f-fiwtewinvawidusews(extewnawdatasouwces.fwockwepowtasabusesouwce, 😳 v-vawidusews)

    // usew-usew s-spam wepowt edges
    vaw u-usewusewspamwepowtedges: t-typedpipe[(usewid, (U ᵕ U❁) usewid)] =
      f-fiwtewinvawidusews(extewnawdatasouwces.fwockwepowtasspamsouwce, :3 v-vawidusews)

    // usew-signup countwy e-edges
    vaw usewsignupcountwyedges: typedpipe[(usewid, o.O (countwy, wanguage))] =
      e-extewnawdatasouwces.usewsouwce

    // usew-consumed w-wanguage edges
    vaw usewconsumedwanguageedges: typedpipe[(usewid, (///ˬ///✿) s-seq[(wanguage, OwO d-doubwe)])] =
      e-extewnawdatasouwces.infewwedusewconsumedwanguagesouwce

    // usew-topic f-fowwow edges
    v-vaw topicusewfowwowedbyedges: typedpipe[(topicid, >w< u-usewid)] =
      extewnawdatasouwces.topicfowwowgwaphsouwce

    // u-usew-mwnotifopenowcwick events fwom wast 7 d-days
    vaw u-usewmwnotifopenowcwickevents: typedpipe[magicwecsnotificationwite] =
      extewnawdatasouwces.magicwecsnotficationopenowcwickeventssouwce(datewange =
        datewange(datewange.end - days(7), ^^ datewange.end))

    // u-usew-seawchquewy s-stwings fwom wast 7 days
    vaw usewseawchquewyedges: typedpipe[(usewid, (⑅˘꒳˘) s-stwing)] =
      extewnawdatasouwces.adaptiveseawchscwibewogssouwce(datewange =
        d-datewange(datewange.end - d-days(7), ʘwʘ datewange.end))

    getusewtweetintewactiongwaph(tweetsouwce) ++
      getusewfavgwaph(usewusewfavedges) ++
      getusewfowwowgwaph(usewusewfowwowedges) ++
      g-getusewbwockgwaph(usewusewbwockedges) ++
      getusewabusewepowtgwaph(usewusewabusewepowtedges) ++
      getusewspamwepowtgwaph(usewusewspamwepowtedges) ++
      getusewsignupcountwygwaph(usewsignupcountwyedges) ++
      g-getusewconsumedwanguagesgwaph(usewconsumedwanguageedges) ++
      getusewtopicfowwowgwaph(topicusewfowwowedbyedges) ++
      g-getmagicwecsnotifopenowcwicktweetsgwaph(usewmwnotifopenowcwickevents) ++
      getseawchgwaph(usewseawchquewyedges)
  }
}
