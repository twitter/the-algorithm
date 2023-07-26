package com.twittew.home_mixew.utiw.eawwybiwd

impowt c-com.twittew.seawch.common.constants.{thwiftscawa => s-scc}
impowt c-com.twittew.seawch.common.featuwes.{thwiftscawa => s-sc}
impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant
i-impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant._
i-impowt com.twittew.seawch.common.utiw.wang.thwiftwanguageutiw
impowt com.twittew.seawch.eawwybiwd.{thwiftscawa => eb}
impowt com.twittew.timewines.eawwybiwd.common.utiws.innetwowkengagement

object eawwybiwdwesponseutiw {

  p-pwivate[eawwybiwd] vaw mentions: stwing = "mentions"
  p-pwivate[eawwybiwd] vaw hashtags: s-stwing = "hashtags"
  pwivate vaw chawstowemovefwommentions: set[chaw] = "@".toset
  pwivate v-vaw chawstowemovefwomhashtags: set[chaw] = "#".toset

  // d-defauwt vawue of s-settings of thwifttweetfeatuwes. :3
  pwivate[eawwybiwd] vaw defauwteawwybiwdfeatuwes: sc.thwifttweetfeatuwes = sc.thwifttweetfeatuwes()
  pwivate[eawwybiwd] v-vaw defauwtcount = 0
  pwivate[eawwybiwd] vaw defauwtwanguage = 0
  pwivate[eawwybiwd] v-vaw defauwtscowe = 0.0

  pwivate[eawwybiwd] d-def gettweetcountbyauthowid(
    s-seawchwesuwts: s-seq[eb.thwiftseawchwesuwt]
  ): m-map[wong, OwO int] = {
    seawchwesuwts
      .gwoupby { wesuwt =>
        w-wesuwt.metadata.map(_.fwomusewid).getowewse(0w)
      }.mapvawues(_.size).withdefauwtvawue(0)
  }

  pwivate[eawwybiwd] def getwanguage(uiwanguagecode: o-option[stwing]): option[scc.thwiftwanguage] = {
    uiwanguagecode.fwatmap { wanguagecode =>
      scc.thwiftwanguage.get(thwiftwanguageutiw.getthwiftwanguageof(wanguagecode).getvawue)
    }
  }

  pwivate def g-getmentions(wesuwt: eb.thwiftseawchwesuwt): seq[stwing] = {
    v-vaw facetwabews = w-wesuwt.metadata.fwatmap(_.facetwabews).getowewse(seq.empty)
    g-getfacets(facetwabews, mentions, mya chawstowemovefwommentions)
  }

  pwivate d-def gethashtags(wesuwt: e-eb.thwiftseawchwesuwt): seq[stwing] = {
    v-vaw facetwabews = w-wesuwt.metadata.fwatmap(_.facetwabews).getowewse(seq.empty)
    getfacets(facetwabews, (˘ω˘) h-hashtags, o.O chawstowemovefwomhashtags)
  }

  p-pwivate def getfacets(
    facetwabews: s-seq[eb.thwiftfacetwabew], (✿oωo)
    facetname: stwing, (ˆ ﻌ ˆ)♡
    c-chawstowemove: set[chaw]
  ): s-seq[stwing] = {
    f-facetwabews.fiwtew(_.fiewdname == facetname).map(_.wabew.fiwtewnot(chawstowemove))
  }

  pwivate def isusewmentioned(
    scweenname: option[stwing], ^^;;
    mentions: seq[stwing], OwO
    mentionsinsouwcetweet: seq[stwing]
  ): b-boowean =
    i-isusewmentioned(scweenname, 🥺 mentions) || isusewmentioned(scweenname, mya m-mentionsinsouwcetweet)

  p-pwivate def isusewmentioned(
    s-scweenname: option[stwing], 😳
    mentions: seq[stwing]
  ): boowean = {
    scweenname
      .exists { scweenname => m-mentions.exists(_.equawsignowecase(scweenname)) }
  }

  pwivate[eawwybiwd] def isusewsmainwanguage(
    tweetwanguage: scc.thwiftwanguage, òωó
    u-usewwanguages: seq[scc.thwiftwanguage]
  ): b-boowean = {
    (tweetwanguage != s-scc.thwiftwanguage.unknown) && u-usewwanguages.headoption.contains(
      tweetwanguage)
  }

  p-pwivate[eawwybiwd] d-def isusewswanguage(
    t-tweetwanguage: scc.thwiftwanguage, /(^•ω•^)
    u-usewwanguages: seq[scc.thwiftwanguage]
  ): boowean = {
    (tweetwanguage != s-scc.thwiftwanguage.unknown) && u-usewwanguages.contains(tweetwanguage)
  }

  p-pwivate[eawwybiwd] d-def isuiwanguage(
    t-tweetwanguage: scc.thwiftwanguage, -.-
    uiwanguage: option[scc.thwiftwanguage]
  ): boowean = {
    (tweetwanguage != scc.thwiftwanguage.unknown) && u-uiwanguage.contains(tweetwanguage)
  }

  pwivate def getbooweanoptfeatuwe(
    featuwename: eawwybiwdfiewdconstant, òωó
    wesuwtmapopt: o-option[scawa.cowwection.map[int, /(^•ω•^) boowean]], /(^•ω•^)
    defauwtvawue: boowean = fawse, 😳
  ): o-option[boowean] = {
    w-wesuwtmapopt.map {
      _.getowewse(featuwename.getfiewdid, :3 d-defauwtvawue)
    }
  }

  pwivate d-def getdoubweasintoptfeatuwe(
    featuwename: e-eawwybiwdfiewdconstant, (U ᵕ U❁)
    w-wesuwtmapopt: option[scawa.cowwection.map[int, ʘwʘ doubwe]]
  ): option[int] = {
    if (wesuwtmapopt.exists(_.contains(featuwename.getfiewdid)))
      wesuwtmapopt
        .map {
          _.get(featuwename.getfiewdid)
        }
        .fwatmap { d-doubwevawue =>
          doubwevawue.map(_.toint)
        }
    e-ewse
      nyone
  }

  pwivate d-def getintoptfeatuwe(
    f-featuwename: eawwybiwdfiewdconstant, o.O
    wesuwtmapopt: o-option[scawa.cowwection.map[int, ʘwʘ i-int]]
  ): option[int] = {
    if (wesuwtmapopt.exists(_.contains(featuwename.getfiewdid)))
      w-wesuwtmapopt.fwatmap {
        _.get(featuwename.getfiewdid)
      }
    e-ewse
      nyone
  }

  def gettweetthwiftfeatuwesbytweetid(
    seawchewusewid: wong, ^^
    scweenname: option[stwing], ^•ﻌ•^
    u-usewwanguages: s-seq[scc.thwiftwanguage], mya
    u-uiwanguagecode: option[stwing] = n-nyone, UwU
    f-fowwowedusewids: set[wong], >_<
    m-mutuawwyfowwowingusewids: set[wong],
    seawchwesuwts: seq[eb.thwiftseawchwesuwt], /(^•ω•^)
    souwcetweetseawchwesuwts: s-seq[eb.thwiftseawchwesuwt], òωó
  ): m-map[wong, σωσ sc.thwifttweetfeatuwes] = {

    vaw awwseawchwesuwts = seawchwesuwts ++ s-souwcetweetseawchwesuwts
    v-vaw souwcetweetseawchwesuwtbyid =
      souwcetweetseawchwesuwts.map(wesuwt => (wesuwt.id -> wesuwt)).tomap
    vaw innetwowkengagement =
      i-innetwowkengagement(fowwowedusewids.toseq, ( ͡o ω ͡o ) mutuawwyfowwowingusewids, nyaa~~ awwseawchwesuwts)
    seawchwesuwts.map { seawchwesuwt =>
      vaw featuwes = getthwifttweetfeatuwesfwomseawchwesuwt(
        s-seawchewusewid, :3
        scweenname, UwU
        usewwanguages, o.O
        g-getwanguage(uiwanguagecode),
        g-gettweetcountbyauthowid(seawchwesuwts), (ˆ ﻌ ˆ)♡
        fowwowedusewids, ^^;;
        mutuawwyfowwowingusewids, ʘwʘ
        souwcetweetseawchwesuwtbyid, σωσ
        i-innetwowkengagement, ^^;;
        s-seawchwesuwt
      )
      (seawchwesuwt.id -> featuwes)
    }.tomap
  }

  pwivate[eawwybiwd] def g-getthwifttweetfeatuwesfwomseawchwesuwt(
    seawchewusewid: w-wong, ʘwʘ
    scweenname: option[stwing], ^^
    usewwanguages: s-seq[scc.thwiftwanguage], nyaa~~
    uiwanguage: option[scc.thwiftwanguage], (///ˬ///✿)
    t-tweetcountbyauthowid: m-map[wong, XD int],
    fowwowedusewids: s-set[wong], :3
    mutuawwyfowwowingusewids: s-set[wong], òωó
    s-souwcetweetseawchwesuwtbyid: m-map[wong, ^^ eb.thwiftseawchwesuwt],
    i-innetwowkengagement: i-innetwowkengagement, ^•ﻌ•^
    seawchwesuwt: eb.thwiftseawchwesuwt, σωσ
  ): s-sc.thwifttweetfeatuwes = {
    v-vaw appwyfeatuwes = (appwyusewindependentfeatuwes(
      s-seawchwesuwt
    )(_)).andthen(
      appwyusewdependentfeatuwes(
        seawchewusewid, (ˆ ﻌ ˆ)♡
        s-scweenname, nyaa~~
        usewwanguages, ʘwʘ
        u-uiwanguage, ^•ﻌ•^
        t-tweetcountbyauthowid, rawr x3
        fowwowedusewids, 🥺
        mutuawwyfowwowingusewids, ʘwʘ
        souwcetweetseawchwesuwtbyid, (˘ω˘)
        i-innetwowkengagement, o.O
        s-seawchwesuwt
      )(_)
    )
    v-vaw tweetfeatuwes = s-seawchwesuwt.tweetfeatuwes.getowewse(defauwteawwybiwdfeatuwes)
    appwyfeatuwes(tweetfeatuwes)
  }

  p-pwivate[eawwybiwd] def appwyusewindependentfeatuwes(
    wesuwt: eb.thwiftseawchwesuwt
  )(
    thwifttweetfeatuwes: sc.thwifttweetfeatuwes
  ): sc.thwifttweetfeatuwes = {

    v-vaw featuwes = wesuwt.metadata
      .map { m-metadata =>
        vaw i-iswetweet = metadata.iswetweet.getowewse(fawse)
        vaw iswepwy = m-metadata.iswepwy.getowewse(fawse)

        // facets.
        v-vaw mentions = g-getmentions(wesuwt)
        v-vaw hashtags = g-gethashtags(wesuwt)

        v-vaw seawchwesuwtschemafeatuwes = metadata.extwametadata.fwatmap(_.featuwes)
        vaw booweanseawchwesuwtschemafeatuwes = seawchwesuwtschemafeatuwes.fwatmap(_.boowvawues)
        vaw intseawchwesuwtschemafeatuwes = seawchwesuwtschemafeatuwes.fwatmap(_.intvawues)
        v-vaw d-doubweseawchwesuwtschemafeatuwes = s-seawchwesuwtschemafeatuwes.fwatmap(_.doubwevawues)

        thwifttweetfeatuwes.copy(
          // i-info about the tweet. σωσ
          iswetweet = iswetweet, (ꈍᴗꈍ)
          i-isoffensive = m-metadata.isoffensive.getowewse(fawse), (ˆ ﻌ ˆ)♡
          iswepwy = i-iswepwy, o.O
          fwomvewifiedaccount = metadata.fwomvewifiedaccount.getowewse(fawse), :3
          c-cawdtype = metadata.cawdtype, -.-
          s-signatuwe = metadata.signatuwe, ( ͡o ω ͡o )
          w-wanguage = m-metadata.wanguage, /(^•ω•^)
          isauthownsfw = metadata.isusewnsfw.getowewse(fawse), (⑅˘꒳˘)
          isauthowbot = metadata.isusewbot.getowewse(fawse), òωó
          i-isauthowspam = m-metadata.isusewspam.getowewse(fawse), 🥺
          i-issensitivecontent =
            m-metadata.extwametadata.fwatmap(_.issensitivecontent).getowewse(fawse), (ˆ ﻌ ˆ)♡
          i-isauthowpwofiweegg = metadata.extwametadata.fwatmap(_.pwofiweiseggfwag).getowewse(fawse), -.-
          isauthownew = m-metadata.extwametadata.fwatmap(_.isusewnewfwag).getowewse(fawse), σωσ
          w-winkwanguage = metadata.extwametadata.fwatmap(_.winkwanguage).getowewse(defauwtwanguage), >_<
          // i-info about tweet c-content/media. :3
          hascawd = m-metadata.hascawd.getowewse(fawse), OwO
          hasimage = metadata.hasimage.getowewse(fawse), rawr
          hasnews = m-metadata.hasnews.getowewse(fawse), (///ˬ///✿)
          hasvideo = metadata.hasvideo.getowewse(fawse), ^^
          h-hasconsumewvideo = m-metadata.hasconsumewvideo.getowewse(fawse), XD
          haspwovideo = m-metadata.haspwovideo.getowewse(fawse), UwU
          hasvine = metadata.hasvine.getowewse(fawse), o.O
          haspewiscope = m-metadata.haspewiscope.getowewse(fawse),
          h-hasnativevideo = m-metadata.hasnativevideo.getowewse(fawse), 😳
          hasnativeimage = metadata.hasnativeimage.getowewse(fawse), (˘ω˘)
          haswink = metadata.haswink.getowewse(fawse), 🥺
          hasvisibwewink = m-metadata.hasvisibwewink.getowewse(fawse), ^^
          hastwend = metadata.hastwend.getowewse(fawse), >w<
          hasmuwtipwehashtagsowtwends = m-metadata.hasmuwtipwehashtagsowtwends.getowewse(fawse), ^^;;
          h-hasquote = metadata.extwametadata.fwatmap(_.hasquote), (˘ω˘)
          u-uwwswist = metadata.tweetuwws.map {
            _.map(_.owiginawuww)
          }, OwO
          h-hasmuwtipwemedia =
            m-metadata.extwametadata.fwatmap(_.hasmuwtipwemediafwag).getowewse(fawse), (ꈍᴗꈍ)
          visibwetokenwatio = getintoptfeatuwe(visibwe_token_watio, òωó i-intseawchwesuwtschemafeatuwes), ʘwʘ
          // vawious counts.
          f-favcount = m-metadata.favcount.getowewse(defauwtcount), ʘwʘ
          wepwycount = m-metadata.wepwycount.getowewse(defauwtcount), nyaa~~
          wetweetcount = m-metadata.wetweetcount.getowewse(defauwtcount), UwU
          q-quotecount = m-metadata.extwametadata.fwatmap(_.quotedcount), (⑅˘꒳˘)
          embedsimpwessioncount = metadata.embedsimpwessioncount.getowewse(defauwtcount), (˘ω˘)
          embedsuwwcount = metadata.embedsuwwcount.getowewse(defauwtcount), :3
          videoviewcount = metadata.videoviewcount.getowewse(defauwtcount),
          nyummentions = metadata.extwametadata.fwatmap(_.nummentions).getowewse(defauwtcount),
          nyumhashtags = metadata.extwametadata.fwatmap(_.numhashtags).getowewse(defauwtcount),
          favcountv2 = metadata.extwametadata.fwatmap(_.favcountv2), (˘ω˘)
          w-wepwycountv2 = m-metadata.extwametadata.fwatmap(_.wepwycountv2), nyaa~~
          wetweetcountv2 = metadata.extwametadata.fwatmap(_.wetweetcountv2), (U ﹏ U)
          w-weightedfavowitecount = metadata.extwametadata.fwatmap(_.weightedfavcount), nyaa~~
          w-weightedwepwycount = m-metadata.extwametadata.fwatmap(_.weightedwepwycount),
          weightedwetweetcount = m-metadata.extwametadata.fwatmap(_.weightedwetweetcount), ^^;;
          weightedquotecount = m-metadata.extwametadata.fwatmap(_.weightedquotecount), OwO
          e-embedsimpwessioncountv2 =
            getdoubweasintoptfeatuwe(embeds_impwession_count_v2, nyaa~~ d-doubweseawchwesuwtschemafeatuwes), UwU
          embedsuwwcountv2 =
            g-getdoubweasintoptfeatuwe(embeds_uww_count_v2, 😳 d-doubweseawchwesuwtschemafeatuwes), 😳
          decayedfavowitecount =
            getdoubweasintoptfeatuwe(decayed_favowite_count, (ˆ ﻌ ˆ)♡ d-doubweseawchwesuwtschemafeatuwes), (✿oωo)
          d-decayedwetweetcount =
            g-getdoubweasintoptfeatuwe(decayed_wetweet_count, nyaa~~ d-doubweseawchwesuwtschemafeatuwes), ^^
          d-decayedwepwycount =
            g-getdoubweasintoptfeatuwe(decayed_wepwy_count, d-doubweseawchwesuwtschemafeatuwes), (///ˬ///✿)
          d-decayedquotecount =
            g-getdoubweasintoptfeatuwe(decayed_quote_count, 😳 doubweseawchwesuwtschemafeatuwes), òωó
          f-fakefavowitecount =
            g-getdoubweasintoptfeatuwe(fake_favowite_count, d-doubweseawchwesuwtschemafeatuwes), ^^;;
          fakewetweetcount =
            g-getdoubweasintoptfeatuwe(fake_wetweet_count, rawr doubweseawchwesuwtschemafeatuwes),
          fakewepwycount =
            getdoubweasintoptfeatuwe(fake_wepwy_count, (ˆ ﻌ ˆ)♡ d-doubweseawchwesuwtschemafeatuwes), XD
          fakequotecount =
            g-getdoubweasintoptfeatuwe(fake_quote_count, >_< d-doubweseawchwesuwtschemafeatuwes),
          // s-scowes. (˘ω˘)
          textscowe = m-metadata.textscowe.getowewse(defauwtscowe), 😳
          eawwybiwdscowe = m-metadata.scowe.getowewse(defauwtscowe),
          pawusscowe = metadata.pawusscowe.getowewse(defauwtscowe), o.O
          u-usewwep = metadata.usewwep.getowewse(defauwtscowe), (ꈍᴗꈍ)
          pbwockscowe = m-metadata.extwametadata.fwatmap(_.pbwockscowe), rawr x3
          toxicityscowe = metadata.extwametadata.fwatmap(_.toxicityscowe), ^^
          pspammytweetscowe = metadata.extwametadata.fwatmap(_.pspammytweetscowe), OwO
          p-pwepowtedtweetscowe = metadata.extwametadata.fwatmap(_.pwepowtedtweetscowe), ^^
          p-pspammytweetcontent = m-metadata.extwametadata.fwatmap(_.spammytweetcontentscowe), :3
          // safety signaws
          wabewabusivefwag =
            g-getbooweanoptfeatuwe(wabew_abusive_fwag, o.O booweanseawchwesuwtschemafeatuwes), -.-
          w-wabewabusivehiwcwfwag =
            getbooweanoptfeatuwe(wabew_abusive_hi_wcw_fwag, (U ﹏ U) booweanseawchwesuwtschemafeatuwes), o.O
          w-wabewdupcontentfwag =
            getbooweanoptfeatuwe(wabew_dup_content_fwag, OwO b-booweanseawchwesuwtschemafeatuwes), ^•ﻌ•^
          wabewnsfwhipwcfwag =
            getbooweanoptfeatuwe(wabew_nsfw_hi_pwc_fwag, ʘwʘ b-booweanseawchwesuwtschemafeatuwes), :3
          w-wabewnsfwhiwcwfwag =
            getbooweanoptfeatuwe(wabew_nsfw_hi_wcw_fwag, 😳 b-booweanseawchwesuwtschemafeatuwes), òωó
          wabewspamfwag = getbooweanoptfeatuwe(wabew_spam_fwag, 🥺 b-booweanseawchwesuwtschemafeatuwes),
          wabewspamhiwcwfwag =
            g-getbooweanoptfeatuwe(wabew_spam_hi_wcw_fwag, rawr x3 b-booweanseawchwesuwtschemafeatuwes),
          // p-pewiscope featuwes
          pewiscopeexists =
            g-getbooweanoptfeatuwe(pewiscope_exists, ^•ﻌ•^ b-booweanseawchwesuwtschemafeatuwes), :3
          p-pewiscopehasbeenfeatuwed =
            g-getbooweanoptfeatuwe(pewiscope_has_been_featuwed, booweanseawchwesuwtschemafeatuwes), (ˆ ﻌ ˆ)♡
          p-pewiscopeiscuwwentwyfeatuwed = g-getbooweanoptfeatuwe(
            p-pewiscope_is_cuwwentwy_featuwed, (U ᵕ U❁)
            b-booweanseawchwesuwtschemafeatuwes), :3
          p-pewiscopeisfwomquawitysouwce = g-getbooweanoptfeatuwe(
            p-pewiscope_is_fwom_quawity_souwce, ^^;;
            b-booweanseawchwesuwtschemafeatuwes), ( ͡o ω ͡o )
          pewiscopeiswive =
            g-getbooweanoptfeatuwe(pewiscope_is_wive, o.O booweanseawchwesuwtschemafeatuwes), ^•ﻌ•^
          // w-wast engagement featuwes
          wastfavsincecweationhws =
            g-getintoptfeatuwe(wast_favowite_since_cweation_hws, i-intseawchwesuwtschemafeatuwes), XD
          w-wastwetweetsincecweationhws =
            getintoptfeatuwe(wast_wetweet_since_cweation_hws, ^^ intseawchwesuwtschemafeatuwes), o.O
          wastwepwysincecweationhws =
            g-getintoptfeatuwe(wast_wepwy_since_cweation_hws, ( ͡o ω ͡o ) i-intseawchwesuwtschemafeatuwes),
          w-wastquotesincecweationhws =
            getintoptfeatuwe(wast_quote_since_cweation_hws, /(^•ω•^) intseawchwesuwtschemafeatuwes), 🥺
          wikedbyusewids = m-metadata.extwametadata.fwatmap(_.wikedbyusewids), nyaa~~
          m-mentionswist = if (mentions.nonempty) some(mentions) e-ewse n-nyone, mya
          hashtagswist = if (hashtags.nonempty) some(hashtags) e-ewse nyone, XD
          iscomposewsouwcecamewa =
            g-getbooweanoptfeatuwe(composew_souwce_is_camewa_fwag, nyaa~~ b-booweanseawchwesuwtschemafeatuwes), ʘwʘ
        )
      }
      .getowewse(thwifttweetfeatuwes)

    f-featuwes
  }

  pwivate def appwyusewdependentfeatuwes(
    s-seawchewusewid: w-wong, (⑅˘꒳˘)
    scweenname: option[stwing], :3
    usewwanguages: seq[scc.thwiftwanguage], -.-
    u-uiwanguage: option[scc.thwiftwanguage], 😳😳😳
    tweetcountbyauthowid: m-map[wong, (U ﹏ U) int],
    f-fowwowedusewids: s-set[wong], o.O
    mutuawwyfowwowingusewids: s-set[wong], ( ͡o ω ͡o )
    s-souwcetweetseawchwesuwtbyid: map[wong, òωó e-eb.thwiftseawchwesuwt], 🥺
    innetwowkengagement: i-innetwowkengagement, /(^•ω•^)
    w-wesuwt: e-eb.thwiftseawchwesuwt
  )(
    t-thwifttweetfeatuwes: sc.thwifttweetfeatuwes
  ): s-sc.thwifttweetfeatuwes = {
    w-wesuwt.metadata
      .map { m-metadata =>
        vaw iswetweet = m-metadata.iswetweet.getowewse(fawse)
        vaw souwcetweet =
          if (iswetweet) s-souwcetweetseawchwesuwtbyid.get(metadata.shawedstatusid)
          e-ewse n-nyone
        vaw mentionsinsouwcetweet = souwcetweet.map(getmentions).getowewse(seq.empty)

        vaw iswepwy = metadata.iswepwy.getowewse(fawse)
        v-vaw wepwytoseawchew = iswepwy && (metadata.wefewencedtweetauthowid == s-seawchewusewid)
        v-vaw wepwyothew = iswepwy && !wepwytoseawchew
        vaw wetweetothew = i-iswetweet && (metadata.wefewencedtweetauthowid != seawchewusewid)
        v-vaw tweetwanguage = m-metadata.wanguage.getowewse(scc.thwiftwanguage.unknown)

        v-vaw wefewencedtweetauthowid =
          i-if (metadata.wefewencedtweetauthowid > 0) s-some(metadata.wefewencedtweetauthowid) ewse nyone
        vaw inwepwytousewid = if (!iswetweet) w-wefewencedtweetauthowid ewse nyone

        t-thwifttweetfeatuwes.copy(
          // info about the tweet.
          fwomseawchew = m-metadata.fwomusewid == seawchewusewid, 😳😳😳
          pwobabwyfwomfowwowedauthow = fowwowedusewids.contains(metadata.fwomusewid), ^•ﻌ•^
          fwommutuawfowwow = m-mutuawwyfowwowingusewids.contains(metadata.fwomusewid), nyaa~~
          w-wepwyseawchew = wepwytoseawchew, OwO
          w-wepwyothew = wepwyothew, ^•ﻌ•^
          wetweetothew = wetweetothew, σωσ
          m-mentionseawchew = i-isusewmentioned(scweenname, -.- getmentions(wesuwt), (˘ω˘) m-mentionsinsouwcetweet), rawr x3
          // info about tweet c-content/media. rawr x3
          matchesseawchewmainwang = isusewsmainwanguage(tweetwanguage, σωσ usewwanguages), nyaa~~
          m-matchesseawchewwangs = isusewswanguage(tweetwanguage, (ꈍᴗꈍ) usewwanguages), ^•ﻌ•^
          m-matchesuiwang = i-isuiwanguage(tweetwanguage, >_< uiwanguage), ^^;;
          // v-vawious counts. ^^;;
          pwevusewtweetengagement =
            m-metadata.extwametadata.fwatmap(_.pwevusewtweetengagement).getowewse(defauwtcount), /(^•ω•^)
          tweetcountfwomusewinsnapshot = tweetcountbyauthowid(metadata.fwomusewid), nyaa~~
          bidiwectionawwepwycount = innetwowkengagement.bidiwectionawwepwycounts(wesuwt.id), (✿oωo)
          u-unidiwectionawwepwycount = i-innetwowkengagement.unidiwectionawwepwycounts(wesuwt.id), ( ͡o ω ͡o )
          b-bidiwectionawwetweetcount = i-innetwowkengagement.bidiwectionawwetweetcounts(wesuwt.id), (U ᵕ U❁)
          unidiwectionawwetweetcount = innetwowkengagement.unidiwectionawwetweetcounts(wesuwt.id), òωó
          c-convewsationcount = i-innetwowkengagement.descendantwepwycounts(wesuwt.id), σωσ
          diwectedatusewidisinfiwstdegwee =
            if (iswepwy) i-inwepwytousewid.map(fowwowedusewids.contains) ewse nyone, :3
        )
      }
      .getowewse(thwifttweetfeatuwes)
  }
}
