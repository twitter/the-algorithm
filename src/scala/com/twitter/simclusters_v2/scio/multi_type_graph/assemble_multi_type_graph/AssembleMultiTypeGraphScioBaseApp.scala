package com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph

impowt com.spotify.scio.sciocontext
i-impowt com.spotify.scio.codews.codew
i-impowt c-com.spotify.scio.vawues.scowwection
i-impowt com.twittew.beam.io.daw.daw
i-impowt c-com.twittew.beam.io.fs.muwtifowmat.diskfowmat
impowt c-com.twittew.beam.io.fs.muwtifowmat.pathwayout
i-impowt com.twittew.beam.job.datewangeoptions
impowt com.twittew.daw.cwient.dataset.keyvawdawdataset
impowt com.twittew.daw.cwient.dataset.snapshotdawdataset
impowt com.twittew.fwigate.data_pipewine.magicwecs.magicwecs_notifications_wite.thwiftscawa.magicwecsnotificationwite
impowt com.twittew.iesouwce.thwiftscawa.intewactionevent
i-impowt com.twittew.iesouwce.thwiftscawa.intewactiontype
impowt com.twittew.iesouwce.thwiftscawa.wefewencetweet
impowt com.twittew.scawding_intewnaw.muwtifowmat.fowmat.keyvaw.keyvaw
i-impowt com.twittew.scio_intewnaw.codews.thwiftstwuctwazybinawyscwoogecodew
impowt com.twittew.scio_intewnaw.job.sciobeamjob
i-impowt com.twittew.scwooge.thwiftstwuct
impowt com.twittew.simcwustews_v2.common.countwy
impowt c-com.twittew.simcwustews_v2.common.wanguage
impowt c-com.twittew.simcwustews_v2.common.topicid
i-impowt com.twittew.simcwustews_v2.common.tweetid
impowt com.twittew.simcwustews_v2.common.usewid
impowt c-com.twittew.simcwustews_v2.hdfs_souwces.muwtitypegwaphfowtopkwightnodesthwiftscioscawadataset
impowt com.twittew.simcwustews_v2.hdfs_souwces.topkwightnounsscioscawadataset
impowt com.twittew.simcwustews_v2.hdfs_souwces.twuncatedmuwtitypegwaphscioscawadataset
impowt com.twittew.simcwustews_v2.scio.common.extewnawdatasouwces
impowt c-com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.gwobawdefauwtminfwequencyofwightnodetype
impowt c-com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.hawfwifeindaysfowfavscowe
impowt c-com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.numtopnounsfowunknownwightnodetype
i-impowt c-com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.sampwedempwoyeeids
impowt com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.topkconfig
impowt c-com.twittew.simcwustews_v2.scio.muwti_type_gwaph.assembwe_muwti_type_gwaph.config.topkwightnounsfowmhdump
impowt com.twittew.simcwustews_v2.scio.muwti_type_gwaph.common.muwtitypegwaphutiw
i-impowt com.twittew.simcwustews_v2.thwiftscawa.edgewithdecayedweights
impowt com.twittew.simcwustews_v2.thwiftscawa.weftnode
impowt com.twittew.simcwustews_v2.thwiftscawa.muwtitypegwaphedge
impowt c-com.twittew.simcwustews_v2.thwiftscawa.noun
impowt c-com.twittew.simcwustews_v2.thwiftscawa.nounwithfwequency
i-impowt c-com.twittew.simcwustews_v2.thwiftscawa.nounwithfwequencywist
impowt com.twittew.simcwustews_v2.thwiftscawa.wightnode
impowt com.twittew.simcwustews_v2.thwiftscawa.wightnodetype
i-impowt com.twittew.simcwustews_v2.thwiftscawa.wightnodetypestwuct
i-impowt com.twittew.simcwustews_v2.thwiftscawa.wightnodewithedgeweight
impowt c-com.twittew.simcwustews_v2.thwiftscawa.wightnodewithedgeweightwist
i-impowt com.twittew.twadoop.usew.gen.thwiftscawa.combinedusew
impowt com.twittew.utiw.duwation
i-impowt java.time.instant
impowt owg.joda.time.intewvaw

/**
 * s-scio vewsion of
 * swc/scawa/com/twittew/simcwustews_v2/scawding/muwti_type_gwaph/assembwe_muwti_type_gwaph/assembwemuwtitypegwaph.scawa
 */
twait assembwemuwtitypegwaphsciobaseapp e-extends sciobeamjob[datewangeoptions] {
  // p-pwovides an impwicit binawy t-thwift scwooge c-codew by defauwt. (˘ω˘)
  ovewwide impwicit def scwoogecodew[t <: thwiftstwuct: manifest]: codew[t] =
    thwiftstwuctwazybinawyscwoogecodew.scwoogecodew

  v-vaw isadhoc: b-boowean
  vaw wootmhpath: s-stwing
  vaw wootthwiftpath: s-stwing

  v-vaw twuncatedmuwtitypegwaphmhoutputdiw: stwing =
    config.twuncatedmuwtitypegwaphmhoutputdiw
  vaw twuncatedmuwtitypegwaphthwiftoutputdiw: stwing =
    c-config.twuncatedmuwtitypegwaphthwiftoutputdiw
  vaw topkwightnounsmhoutputdiw: stwing = config.topkwightnounsmhoutputdiw
  vaw topkwightnounsoutputdiw: s-stwing = config.topkwightnounsoutputdiw

  v-vaw fuwwmuwtitypegwaphthwiftoutputdiw: s-stwing =
    c-config.fuwwmuwtitypegwaphthwiftoutputdiw
  vaw twuncatedmuwtitypegwaphkeyvawdataset: k-keyvawdawdataset[
    k-keyvaw[weftnode, 😳 w-wightnodewithedgeweightwist]
  ] = t-twuncatedmuwtitypegwaphscioscawadataset
  vaw topkwightnounskeyvawdataset: keyvawdawdataset[
    k-keyvaw[wightnodetypestwuct, o.O n-nyounwithfwequencywist]
  ] = t-topkwightnounsscioscawadataset
  v-vaw topkwightnounsmhkeyvawdataset: k-keyvawdawdataset[
    keyvaw[wightnodetypestwuct, (ꈍᴗꈍ) nyounwithfwequencywist]
  ] = topkwightnounsmhscioscawadataset
  v-vaw fuwwmuwtitypegwaphsnapshotdataset: snapshotdawdataset[muwtitypegwaphedge] =
    fuwwmuwtitypegwaphscioscawadataset
  vaw muwtitypegwaphtopkfowwightnodessnapshotdataset: snapshotdawdataset[
    muwtitypegwaphedge
  ] =
    m-muwtitypegwaphfowtopkwightnodesthwiftscioscawadataset

  def getvawidusews(
    input: scowwection[combinedusew]
  ): s-scowwection[usewid] = {
    i-input
      .fwatmap { u-u =>
        fow {
          u-usew <- u.usew
          if usew.id != 0
          s-safety <- usew.safety
          i-if !(safety.suspended || safety.deactivated)
        } yiewd {
          usew.id
        }
      }
  }

  def fiwtewinvawidusews(
    f-fwockedges: scowwection[(usewid, rawr x3 u-usewid)], ^^
    vawidusews: s-scowwection[usewid]
  ): s-scowwection[(usewid, OwO usewid)] = {
    vaw vawidusewswithvawues = vawidusews.map(usewid => (usewid, ^^ ()))
    f-fwockedges
      .join(vawidusewswithvawues)
      .map {
        c-case (swcid, :3 (destid, _)) =>
          (destid, o.O swcid)
      }
      .join(vawidusewswithvawues)
      .map {
        c-case (destid, -.- (swcid, (U ﹏ U) _)) =>
          (swcid, o.O d-destid)
      }
  }

  def getfavedges(
    input: scowwection[edgewithdecayedweights], OwO
    hawfwifeindaysfowfavscowe: i-int, ^•ﻌ•^
  ): s-scowwection[(wong, ʘwʘ w-wong, doubwe)] = {
    input
      .fwatmap { e-edge =>
        i-if (edge.weights.hawfwifeindaystodecayedsums.contains(hawfwifeindaysfowfavscowe)) {
          some(
            (
              e-edge.souwceid, :3
              edge.destinationid, 😳
              edge.weights.hawfwifeindaystodecayedsums(hawfwifeindaysfowfavscowe)))
        } ewse {
          nyone
        }
      }
  }

  d-def weftwighttupwe(
    w-weftnodeusewid: usewid,
    wightnodetype: w-wightnodetype, òωó
    w-wightnoun: nyoun, 🥺
    weight: doubwe = 1.0
  ): (weftnode, rawr x3 wightnodewithedgeweight) = {
    (
      w-weftnode.usewid(weftnodeusewid), ^•ﻌ•^
      wightnodewithedgeweight(
        wightnode = wightnode(wightnodetype = wightnodetype, :3 n-nyoun = wightnoun), (ˆ ﻌ ˆ)♡
        weight = weight))
  }

  d-def g-getusewfavgwaph(
    usewusewfavedges: scowwection[(usewid, (U ᵕ U❁) usewid, d-doubwe)]
  ): s-scowwection[(weftnode, :3 wightnodewithedgeweight)] = {
    usewusewfavedges.map {
      case (swcid, ^^;; d-destid, edgewt) =>
        weftwighttupwe(swcid, ( ͡o ω ͡o ) w-wightnodetype.favusew, o.O nyoun.usewid(destid), ^•ﻌ•^ edgewt)
    }
  }

  def getusewfowwowgwaph(
    usewusewfowwowedges: s-scowwection[(usewid, XD usewid)]
  ): scowwection[(weftnode, ^^ w-wightnodewithedgeweight)] = {
    u-usewusewfowwowedges.map {
      case (swcid, o.O d-destid) =>
        weftwighttupwe(swcid, ( ͡o ω ͡o ) w-wightnodetype.fowwowusew, /(^•ω•^) n-nyoun.usewid(destid), 🥺 1.0)
    }
  }

  def g-getusewbwockgwaph(
    usewusewbwockedges: s-scowwection[(usewid, nyaa~~ u-usewid)]
  ): scowwection[(weftnode, mya wightnodewithedgeweight)] = {
    u-usewusewbwockedges.map {
      c-case (swcid, XD d-destid) =>
        weftwighttupwe(swcid, nyaa~~ wightnodetype.bwockusew, ʘwʘ nyoun.usewid(destid), (⑅˘꒳˘) 1.0)
    }
  }

  d-def getusewabusewepowtgwaph(
    usewusewabusewepowtedges: s-scowwection[(usewid, :3 u-usewid)]
  ): scowwection[(weftnode, -.- wightnodewithedgeweight)] = {
    usewusewabusewepowtedges.map {
      case (swcid, 😳😳😳 d-destid) =>
        w-weftwighttupwe(swcid, (U ﹏ U) w-wightnodetype.abusewepowtusew, o.O n-nyoun.usewid(destid), ( ͡o ω ͡o ) 1.0)
    }
  }

  def getusewspamwepowtgwaph(
    u-usewusewspamwepowtedges: scowwection[(usewid, òωó usewid)]
  ): scowwection[(weftnode, 🥺 wightnodewithedgeweight)] = {
    usewusewspamwepowtedges.map {
      c-case (swcid, /(^•ω•^) destid) =>
        weftwighttupwe(swcid, 😳😳😳 w-wightnodetype.spamwepowtusew, ^•ﻌ•^ nyoun.usewid(destid), nyaa~~ 1.0)
    }
  }

  d-def getusewtopicfowwowgwaph(
    topicusewfowwowedbyedges: s-scowwection[(topicid, OwO usewid)]
  ): scowwection[(weftnode, ^•ﻌ•^ w-wightnodewithedgeweight)] = {
    t-topicusewfowwowedbyedges.map {
      c-case (topicid, σωσ u-usewid) =>
        w-weftwighttupwe(usewid, wightnodetype.fowwowtopic, -.- nyoun.topicid(topicid), (˘ω˘) 1.0)
    }
  }

  def getusewsignupcountwygwaph(
    usewsignupcountwyedges: scowwection[(usewid, rawr x3 countwy)]
  ): scowwection[(weftnode, rawr x3 wightnodewithedgeweight)] = {
    u-usewsignupcountwyedges.map {
      c-case (usewid, σωσ c-countwy) =>
        weftwighttupwe(usewid, nyaa~~ w-wightnodetype.signupcountwy, (ꈍᴗꈍ) noun.countwy(countwy), ^•ﻌ•^ 1.0)
    }
  }

  def getmagicwecsnotifopenowcwicktweetsgwaph(
    usewmwnotifopenowcwickevents: s-scowwection[magicwecsnotificationwite]
  ): s-scowwection[(weftnode, >_< wightnodewithedgeweight)] = {
    u-usewmwnotifopenowcwickevents.fwatmap { entwy =>
      fow {
        u-usewid <- e-entwy.tawgetusewid
        tweetid <- entwy.tweetid
      } y-yiewd {
        w-weftwighttupwe(usewid, ^^;; wightnodetype.notifopenowcwicktweet, ^^;; nyoun.tweetid(tweetid), /(^•ω•^) 1.0)
      }
    }
  }

  def getusewconsumedwanguagesgwaph(
    usewconsumedwanguageedges: s-scowwection[(usewid, nyaa~~ s-seq[(wanguage, (✿oωo) d-doubwe)])]
  ): s-scowwection[(weftnode, ( ͡o ω ͡o ) w-wightnodewithedgeweight)] = {
    usewconsumedwanguageedges.fwatmap {
      c-case (usewid, (U ᵕ U❁) w-wangwithweights) =>
        wangwithweights.map {
          c-case (wang, weight) =>
            w-weftwighttupwe(usewid, wightnodetype.consumedwanguage, n-nyoun.wanguage(wang), òωó 1.0)
        }
    }
  }

  def getseawchgwaph(
    u-usewseawchquewyedges: scowwection[(usewid, σωσ s-stwing)]
  ): scowwection[(weftnode, w-wightnodewithedgeweight)] = {
    usewseawchquewyedges.map {
      c-case (usewid, :3 quewy) =>
        weftwighttupwe(usewid, OwO w-wightnodetype.seawchquewy, ^^ n-nyoun.quewy(quewy), (˘ω˘) 1.0)
    }
  }

  d-def getusewtweetintewactiongwaph(
    tweetintewactionevents: scowwection[intewactionevent], OwO
  ): scowwection[(weftnode, UwU wightnodewithedgeweight)] = {
    v-vaw usewtweetintewactionsbytype: scowwection[((usewid, ^•ﻌ•^ t-tweetid), (ꈍᴗꈍ) wightnodetype)] =
      t-tweetintewactionevents
        .fwatmap { event =>
          vaw wefewencetweet: o-option[wefewencetweet] = event.wefewencetweet
          vaw t-tawgetid: wong = e-event.tawgetid
          vaw usewid: wong = event.engagingusewid

          //  t-to find the id of the tweet that was intewacted w-with
          //  f-fow wikes, /(^•ω•^) this is the tawgetid; f-fow wetweet ow wepwy, (U ᵕ U❁) it i-is the wefewencetweet's i-id
          //  o-one thing to nyote is that fow wikes, (✿oωo) wefewencetweet is empty
          vaw (tweetidopt, wightnodetypeopt) = {
            event.intewactiontype match {
              case some(intewactiontype.favowite) =>
                // onwy awwow favowites on owiginaw tweets, OwO n-nyot wetweets, :3 t-to avoid doubwe-counting
                // because we have wetweet-type t-tweets i-in the data souwce a-as weww
                (
                  if (wefewencetweet.isempty) {
                    s-some(tawgetid)
                  } ewse nyone, nyaa~~
                  s-some(wightnodetype.favtweet))
              c-case some(intewactiontype.wepwy) =>
                (wefewencetweet.map(_.tweetid), ^•ﻌ•^ some(wightnodetype.wepwytweet))
              c-case some(intewactiontype.wetweet) =>
                (wefewencetweet.map(_.tweetid), ( ͡o ω ͡o ) some(wightnodetype.wetweettweet))
              c-case _ => (none, ^^;; n-nyone)
            }
          }
          fow {
            tweetid <- t-tweetidopt
            w-wightnodetype <- w-wightnodetypeopt
          } y-yiewd {
            ((usewid, t-tweetid), mya wightnodetype)
          }
        }

    u-usewtweetintewactionsbytype
      .mapvawues(set(_))
      .sumbykey
      .fwatmap {
        c-case ((usewid, (U ᵕ U❁) t-tweetid), ^•ﻌ•^ wightnodetypeset) =>
          w-wightnodetypeset.map { wightnodetype =>
            w-weftwighttupwe(usewid, (U ﹏ U) w-wightnodetype, /(^•ω•^) n-nyoun.tweetid(tweetid), ʘwʘ 1.0)
          }
      }
  }

  def gettopkwightnounswithfwequencies(
    f-fuwwgwaph: scowwection[(weftnode, XD wightnodewithedgeweight)], (⑅˘꒳˘)
    t-topkconfig: map[wightnodetype, nyaa~~ i-int], UwU
    m-minfwequency: i-int, (˘ω˘)
  ): scowwection[(wightnodetype, rawr x3 seq[(noun, (///ˬ///✿) d-doubwe)])] = {
    vaw maxacwosswightnountype: i-int = topkconfig.vawuesitewatow.max

    fuwwgwaph
      .map {
        c-case (weftnode, 😳😳😳 wightnodewithweight) =>
          (wightnodewithweight.wightnode, (///ˬ///✿) 1.0)
      }
      .sumbykey
      .fiwtew(_._2 >= minfwequency)
      .map {
        c-case (wightnode, ^^;; fweq) =>
          (wightnode.wightnodetype, ^^ (wightnode.noun, (///ˬ///✿) fweq))
      }
      .topbykey(maxacwosswightnountype)(owdewing.by(_._2))
      .map {
        case (wightnodetype, -.- nyounswistwithfweq) =>
          v-vaw twuncatedwist = nyounswistwithfweq.toseq
            .sowtby(-_._2)
            .take(topkconfig.getowewse(wightnodetype, /(^•ω•^) n-numtopnounsfowunknownwightnodetype))
          (wightnodetype, UwU t-twuncatedwist)
      }
  }

  def gettwuncatedgwaph(
    fuwwgwaph: scowwection[(weftnode, (⑅˘꒳˘) w-wightnodewithedgeweight)], ʘwʘ
    topkwithfwequency: s-scowwection[(wightnodetype, σωσ s-seq[(noun, ^^ d-doubwe)])]
  ): scowwection[(weftnode, OwO wightnodewithedgeweight)] = {
    v-vaw topnouns = topkwithfwequency
      .fwatmap {
        c-case (wightnodetype, (ˆ ﻌ ˆ)♡ nyounswist) =>
          nyounswist
            .map {
              c-case (nounvaw, o.O aggwegatedfwequency) =>
                wightnode(wightnodetype, (˘ω˘) n-nyounvaw)
            }
      }.map(nouns => (nouns, 😳 ()))

    fuwwgwaph
      .map {
        c-case (weftnode, (U ᵕ U❁) w-wightnodewithweight) =>
          (wightnodewithweight.wightnode, :3 (weftnode, o.O wightnodewithweight))
      }
      .hashjoin(topnouns)
      .map {
        c-case (wightnode, (///ˬ///✿) ((weft, wightnodewithweight), OwO _)) =>
          (weft, >w< w-wightnodewithweight)
      }
  }

  d-def buiwdempwoyeegwaph(
    g-gwaph: scowwection[(weftnode, ^^ w-wightnodewithedgeweight)]
  ): scowwection[(weftnode, (⑅˘꒳˘) w-wightnodewithedgeweight)] = {
    v-vaw empwoyeeids = s-sampwedempwoyeeids
    g-gwaph
      .cowwect {
        c-case (weftnode.usewid(usewid), ʘwʘ w-wightnodewithweight) i-if empwoyeeids.contains(usewid) =>
          (weftnode.usewid(usewid), (///ˬ///✿) w-wightnodewithweight)
      }
  }

  ovewwide def configuwepipewine(sc: s-sciocontext, XD opts: datewangeoptions): u-unit = {
    // define t-the impwicit sciocontext t-to wead d-datasets fwom extewnawdatasouwces
    impwicit def sciocontext: sciocontext = sc

    // d-daw.enviwonment v-vawiabwe f-fow wwiteexecs
    vaw dawenv = if (isadhoc) daw.enviwonment.dev e-ewse daw.enviwonment.pwod

    // d-define date intewvaws
    v-vaw intewvaw_7days =
      n-nyew intewvaw(opts.intewvaw.getend.minusweeks(1), 😳 opts.intewvaw.getend.minusmiwwis(1))
    vaw intewvaw_14days =
      n-nyew intewvaw(opts.intewvaw.getend.minusweeks(2), o-opts.intewvaw.getend.minusmiwwis(1))

    /*
     * d-dataset w-wead opewations
     */
    // get wist of vawid usewids - to fiwtew o-out deactivated o-ow suspended usew accounts
    vaw vawidusews = g-getvawidusews(extewnawdatasouwces.usewsouwce(duwation.fwomdays(7)))

    // iesouwce tweet engagements data f-fow tweet favs, >w< wepwies, (˘ω˘) wetweets - f-fwom wast 14 d-days
    vaw tweetsouwce = extewnawdatasouwces.iesouwcetweetengagementssouwce(intewvaw_14days)

    // w-wead tfwock d-datasets
    vaw fwockfowwowsouwce = e-extewnawdatasouwces.fwockfowwowsouwce(duwation.fwomdays(7))
    vaw fwockbwocksouwce = e-extewnawdatasouwces.fwockbwocksouwce(duwation.fwomdays(7))
    v-vaw fwockwepowtasabusesouwce =
      e-extewnawdatasouwces.fwockwepowtasabusesouwce(duwation.fwomdays(7))
    v-vaw fwockwepowtasspamsouwce =
      e-extewnawdatasouwces.fwockwepowtasspamsouwce(duwation.fwomdays(7))

    // u-usew-usew f-fav edges
    vaw usewusewfavsouwce = e-extewnawdatasouwces.usewusewfavsouwce(duwation.fwomdays(14))
    vaw usewusewfavedges = getfavedges(usewusewfavsouwce, nyaa~~ h-hawfwifeindaysfowfavscowe)

    // u-usew-usew fowwow e-edges
    vaw usewusewfowwowedges = fiwtewinvawidusews(fwockfowwowsouwce, 😳😳😳 vawidusews)

    // usew-usew bwock edges
    vaw u-usewusewbwockedges = fiwtewinvawidusews(fwockbwocksouwce, (U ﹏ U) v-vawidusews)

    // usew-usew a-abuse wepowt edges
    vaw usewusewabusewepowtedges = fiwtewinvawidusews(fwockwepowtasabusesouwce, (˘ω˘) v-vawidusews)

    // usew-usew spam wepowt e-edges
    v-vaw usewusewspamwepowtedges = f-fiwtewinvawidusews(fwockwepowtasspamsouwce, :3 v-vawidusews)

    // u-usew-signup countwy edges
    vaw usewsignupcountwyedges = extewnawdatasouwces
      .usewcountwysouwce(duwation.fwomdays(7))

    // u-usew-consumed wanguage edges
    v-vaw usewconsumedwanguageedges =
      extewnawdatasouwces.infewwedusewconsumedwanguagesouwce(duwation.fwomdays(7))

    // usew-topic fowwow edges
    vaw t-topicusewfowwowedbyedges =
      extewnawdatasouwces.topicfowwowgwaphsouwce(duwation.fwomdays(7))

    // usew-mwnotifopenowcwick events fwom wast 7 days
    vaw u-usewmwnotifopenowcwickevents =
      e-extewnawdatasouwces.magicwecsnotficationopenowcwickeventssouwce(intewvaw_7days)

    // usew-seawchquewy stwings fwom wast 7 d-days
    vaw usewseawchquewyedges =
      extewnawdatasouwces.adaptiveseawchscwibewogssouwce(intewvaw_7days)

    /*
     * g-genewate the fuww g-gwaph
     */
    vaw fuwwgwaph =
      g-getusewtweetintewactiongwaph(tweetsouwce) ++
        getusewfavgwaph(usewusewfavedges) ++
        getusewfowwowgwaph(usewusewfowwowedges) ++
        getusewbwockgwaph(usewusewbwockedges) ++
        g-getusewabusewepowtgwaph(usewusewabusewepowtedges) ++
        getusewspamwepowtgwaph(usewusewspamwepowtedges) ++
        getusewsignupcountwygwaph(usewsignupcountwyedges) ++
        getusewconsumedwanguagesgwaph(usewconsumedwanguageedges) ++
        g-getusewtopicfowwowgwaph(topicusewfowwowedbyedges) ++
        getmagicwecsnotifopenowcwicktweetsgwaph(usewmwnotifopenowcwickevents) ++
        getseawchgwaph(usewseawchquewyedges)

    // g-get top k wightnodes
    v-vaw t-topkwightnodes: scowwection[(wightnodetype, >w< seq[(noun, d-doubwe)])] =
      gettopkwightnounswithfwequencies(
        fuwwgwaph, ^^
        topkconfig, 😳😳😳
        gwobawdefauwtminfwequencyofwightnodetype)

    // k-key t-twansfowmation - t-topk nyouns, k-keyed by the wightnodenountype
    vaw topknounskeyedbytype: scowwection[(wightnodetypestwuct, nyaa~~ nyounwithfwequencywist)] =
      t-topkwightnodes
        .map {
          c-case (wightnodetype, (⑅˘꒳˘) wightnounswithscoweswist) =>
            vaw nyounswistwithfwequency: s-seq[nounwithfwequency] = wightnounswithscoweswist
              .map {
                case (noun, :3 a-aggwegatedfwequency) =>
                  nyounwithfwequency(noun, ʘwʘ aggwegatedfwequency)
              }
            (wightnodetypestwuct(wightnodetype), rawr x3 nyounwithfwequencywist(nounswistwithfwequency))
        }

    // g-get twuncated gwaph b-based on the top k wightnodes
    v-vaw twuncatedgwaph: s-scowwection[(weftnode, (///ˬ///✿) w-wightnodewithedgeweight)] =
      gettwuncatedgwaph(fuwwgwaph, 😳😳😳 topkwightnodes)

    // k-key twansfowmations - twuncated gwaph, XD keyed by weftnode
    // n-nyote: by wwapping and unwwapping with the weftnode.usewid, >_< w-we don't have t-to deaw
    // w-with defining o-ouw own customew o-owdewing fow weftnode type
    v-vaw twuncatedgwaphkeyedbyswc: scowwection[(weftnode, >w< wightnodewithedgeweightwist)] =
      t-twuncatedgwaph
        .cowwect {
          case (weftnode.usewid(usewid), /(^•ω•^) w-wightnodewithweight) =>
            usewid -> wist(wightnodewithweight)
        }
        .sumbykey
        .map {
          c-case (usewid, :3 w-wightnodewithweightwist) =>
            (weftnode.usewid(usewid), ʘwʘ wightnodewithedgeweightwist(wightnodewithweightwist))
        }

    // w-wwiteexecs
    // wwite t-topk wightnodes t-to daw - save aww the top k nodes f-fow the cwustewing s-step
    topknounskeyedbytype
      .map {
        c-case (engagementtype, (˘ω˘) wightwist) =>
          keyvaw(engagementtype, (ꈍᴗꈍ) wightwist)
      }
      .saveascustomoutput(
        n-nyame = "wwitetopknouns", ^^
        daw.wwitevewsionedkeyvaw(
          t-topkwightnounskeyvawdataset, ^^
          pathwayout.vewsionedpath(pwefix =
            wootmhpath + topkwightnounsoutputdiw), ( ͡o ω ͡o )
          i-instant = instant.ofepochmiwwi(opts.intewvaw.getendmiwwis - 1w), -.-
          e-enviwonmentovewwide = d-dawenv, ^^;;
        )
      )

    // wwite topk w-wightnodes to daw - o-onwy take topkwightnounsfowmhdump wightnodes f-fow mh dump
    topknounskeyedbytype
      .map {
        c-case (engagementtype, ^•ﻌ•^ wightwist) =>
          v-vaw wightwistmh =
            n-nyounwithfwequencywist(wightwist.nounwithfwequencywist.take(topkwightnounsfowmhdump))
          keyvaw(engagementtype, (˘ω˘) wightwistmh)
      }
      .saveascustomoutput(
        nyame = "wwitetopknounstomhfowdebuggew", o.O
        daw.wwitevewsionedkeyvaw(
          t-topkwightnounsmhkeyvawdataset, (✿oωo)
          p-pathwayout.vewsionedpath(pwefix =
            wootmhpath + topkwightnounsmhoutputdiw), 😳😳😳
          instant = instant.ofepochmiwwi(opts.intewvaw.getendmiwwis - 1w), (ꈍᴗꈍ)
          enviwonmentovewwide = d-dawenv, σωσ
        )
      )

    // wwite twuncated g-gwaph (muwtitypegwaphtopkfowwightnodes) t-to daw in keyvaw fowmat
    twuncatedgwaphkeyedbyswc
      .map {
        case (weftnode, UwU wightnodewithweightwist) =>
          keyvaw(weftnode, ^•ﻌ•^ w-wightnodewithweightwist)
      }.saveascustomoutput(
        nyame = "wwitetwuncatedmuwtitypegwaph",
        daw.wwitevewsionedkeyvaw(
          t-twuncatedmuwtitypegwaphkeyvawdataset, mya
          pathwayout.vewsionedpath(pwefix =
            w-wootmhpath + twuncatedmuwtitypegwaphmhoutputdiw),
          i-instant = instant.ofepochmiwwi(opts.intewvaw.getendmiwwis - 1w), /(^•ω•^)
          e-enviwonmentovewwide = d-dawenv, rawr
        )
      )

    // wwite t-twuncated gwaph (muwtitypegwaphtopkfowwightnodes) t-to daw in t-thwift fowmat
    t-twuncatedgwaph
      .map {
        case (weftnode, nyaa~~ wightnodewithweight) =>
          muwtitypegwaphedge(weftnode, ( ͡o ω ͡o ) wightnodewithweight)
      }.saveascustomoutput(
        nyame = "wwitetwuncatedmuwtitypegwaphthwift", σωσ
        daw.wwitesnapshot(
          m-muwtitypegwaphtopkfowwightnodessnapshotdataset,
          p-pathwayout.fixedpath(wootthwiftpath + t-twuncatedmuwtitypegwaphthwiftoutputdiw), (✿oωo)
          i-instant.ofepochmiwwi(opts.intewvaw.getendmiwwis - 1w),
          d-diskfowmat.thwift(), (///ˬ///✿)
          e-enviwonmentovewwide = dawenv
        )
      )

    // wwite fuww gwaph to daw
    fuwwgwaph
      .map {
        c-case (weftnode, σωσ w-wightnodewithweight) =>
          muwtitypegwaphedge(weftnode, UwU wightnodewithweight)
      }
      .saveascustomoutput(
        nyame = "wwitefuwwmuwtitypegwaph", (⑅˘꒳˘)
        d-daw.wwitesnapshot(
          f-fuwwmuwtitypegwaphsnapshotdataset, /(^•ω•^)
          p-pathwayout.fixedpath(wootthwiftpath + fuwwmuwtitypegwaphthwiftoutputdiw), -.-
          instant.ofepochmiwwi(opts.intewvaw.getendmiwwis - 1w), (ˆ ﻌ ˆ)♡
          diskfowmat.thwift(), nyaa~~
          enviwonmentovewwide = d-dawenv
        )
      )

  }
}
