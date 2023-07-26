package com.twittew.tweetypie.utiw

impowt com.twittew.datapwoducts.enwichments.thwiftscawa.pwofiwegeoenwichment
i-impowt com.twittew.expandodo.thwiftscawa._
i-impowt c-com.twittew.mediasewvices.commons.thwiftscawa.mediakey
i-impowt c-com.twittew.mediasewvices.commons.tweetmedia.thwiftscawa._
i-impowt c-com.twittew.sewvo.data.wens
i-impowt com.twittew.spam.wtf.thwiftscawa.safetywabew
impowt com.twittew.tseng.withhowding.thwiftscawa.takedownweason
impowt com.twittew.tweetypie.thwiftscawa._
impowt c-com.twittew.tweetypie.unmentions.thwiftscawa.unmentiondata

object tweetwenses {
  impowt wens.checkeq

  d-def wequiwesome[a, ^^ b-b](w: wens[a, (///ˬ///✿) option[b]]): wens[a, 😳 b] =
    checkeq[a, òωó b](
      a-a => w.get(a).get, ^^;;
      (a, rawr b) => w.set(a, (ˆ ﻌ ˆ)♡ some(b))
    )

  d-def tweetwens[a](get: t-tweet => a, XD set: (tweet, >_< a) => tweet): wens[tweet, (˘ω˘) a] =
    checkeq[tweet, 😳 a-a](get, o.O set)

  vaw id: wens[tweet, (ꈍᴗꈍ) tweetid] =
    tweetwens[tweetid](_.id, rawr x3 (t, id) => t.copy(id = i-id))

  vaw cowedata: wens[tweet, ^^ o-option[tweetcowedata]] =
    t-tweetwens[option[tweetcowedata]](_.cowedata, OwO (t, c-cowedata) => t-t.copy(cowedata = cowedata))

  vaw wequiwedcowedata: w-wens[tweet, ^^ tweetcowedata] =
    wequiwesome(cowedata)

  v-vaw optuwws: wens[tweet, :3 option[seq[uwwentity]]] =
    tweetwens[option[seq[uwwentity]]](_.uwws, o.O (t, -.- uwws) => t.copy(uwws = uwws))

  vaw uwws: w-wens[tweet, (U ﹏ U) seq[uwwentity]] =
    tweetwens[seq[uwwentity]](_.uwws.toseq.fwatten, o.O (t, u-uwws) => t-t.copy(uwws = some(uwws)))

  v-vaw optmentions: wens[tweet, OwO option[seq[mentionentity]]] =
    tweetwens[option[seq[mentionentity]]](_.mentions, ^•ﻌ•^ (t, v-v) => t.copy(mentions = v-v))

  vaw mentions: w-wens[tweet, ʘwʘ seq[mentionentity]] =
    t-tweetwens[seq[mentionentity]](_.mentions.toseq.fwatten, :3 (t, v) => t.copy(mentions = s-some(v)))

  vaw unmentiondata: w-wens[tweet, 😳 option[unmentiondata]] =
    tweetwens[option[unmentiondata]](_.unmentiondata, òωó (t, v-v) => t.copy(unmentiondata = v))

  vaw o-opthashtags: wens[tweet, 🥺 option[seq[hashtagentity]]] =
    t-tweetwens[option[seq[hashtagentity]]](_.hashtags, rawr x3 (t, v-v) => t.copy(hashtags = v))

  vaw hashtags: wens[tweet, ^•ﻌ•^ seq[hashtagentity]] =
    tweetwens[seq[hashtagentity]](_.hashtags.toseq.fwatten, :3 (t, v) => t.copy(hashtags = some(v)))

  v-vaw optcashtags: w-wens[tweet, (ˆ ﻌ ˆ)♡ option[seq[cashtagentity]]] =
    t-tweetwens[option[seq[cashtagentity]]](_.cashtags, (U ᵕ U❁) (t, v-v) => t-t.copy(cashtags = v))

  vaw cashtags: wens[tweet, :3 seq[cashtagentity]] =
    t-tweetwens[seq[cashtagentity]](_.cashtags.toseq.fwatten, ^^;; (t, v) => t.copy(cashtags = some(v)))

  vaw optmedia: wens[tweet, ( ͡o ω ͡o ) o-option[seq[mediaentity]]] =
    tweetwens[option[seq[mediaentity]]](_.media, o.O (t, v-v) => t-t.copy(media = v))

  v-vaw media: wens[tweet, ^•ﻌ•^ seq[mediaentity]] =
    t-tweetwens[seq[mediaentity]](_.media.toseq.fwatten, XD (t, v-v) => t-t.copy(media = s-some(v)))

  vaw mediakeys: wens[tweet, ^^ seq[mediakey]] =
    t-tweetwens[seq[mediakey]](
      _.mediakeys.toseq.fwatten, o.O
      {
        c-case (t, ( ͡o ω ͡o ) v-v) => t.copy(mediakeys = s-some(v))
      })

  v-vaw pwace: wens[tweet, /(^•ω•^) option[pwace]] =
    tweetwens[option[pwace]](
      _.pwace, 🥺
      {
        case (t, nyaa~~ v) => t-t.copy(pwace = v)
      })

  vaw quotedtweet: wens[tweet, mya option[quotedtweet]] =
    tweetwens[option[quotedtweet]](
      _.quotedtweet, XD
      {
        case (t, nyaa~~ v) => t.copy(quotedtweet = v-v)
      })

  vaw sewfthweadmetadata: wens[tweet, ʘwʘ option[sewfthweadmetadata]] =
    t-tweetwens[option[sewfthweadmetadata]](
      _.sewfthweadmetadata, (⑅˘꒳˘)
      {
        c-case (t, :3 v-v) => t.copy(sewfthweadmetadata = v)
      })

  v-vaw composewsouwce: wens[tweet, -.- o-option[composewsouwce]] =
    t-tweetwens[option[composewsouwce]](
      _.composewsouwce, 😳😳😳
      {
        case (t, (U ﹏ U) v) => t.copy(composewsouwce = v)
      })

  vaw devicesouwce: wens[tweet, o-option[devicesouwce]] =
    tweetwens[option[devicesouwce]](
      _.devicesouwce, o.O
      {
        c-case (t, v) => t.copy(devicesouwce = v-v)
      })

  v-vaw pewspective: wens[tweet, ( ͡o ω ͡o ) option[statuspewspective]] =
    t-tweetwens[option[statuspewspective]](
      _.pewspective, òωó
      {
        c-case (t, 🥺 v) => t.copy(pewspective = v-v)
      })

  v-vaw cawds: wens[tweet, /(^•ω•^) option[seq[cawd]]] =
    tweetwens[option[seq[cawd]]](
      _.cawds,
      {
        case (t, 😳😳😳 v) => t.copy(cawds = v-v)
      })

  vaw c-cawd2: wens[tweet, ^•ﻌ•^ o-option[cawd2]] =
    tweetwens[option[cawd2]](
      _.cawd2, nyaa~~
      {
        c-case (t, OwO v) => t-t.copy(cawd2 = v)
      })

  v-vaw cawdwefewence: wens[tweet, ^•ﻌ•^ option[cawdwefewence]] =
    tweetwens[option[cawdwefewence]](
      _.cawdwefewence,
      {
        case (t, σωσ v) => t-t.copy(cawdwefewence = v-v)
      })

  vaw spamwabew: wens[tweet, -.- o-option[safetywabew]] =
    t-tweetwens[option[safetywabew]](
      _.spamwabew, (˘ω˘)
      {
        case (t, rawr x3 v) => t.copy(spamwabew = v)
      })

  v-vaw wowquawitywabew: wens[tweet, rawr x3 option[safetywabew]] =
    tweetwens[option[safetywabew]](
      _.wowquawitywabew, σωσ
      {
        case (t, nyaa~~ v-v) => t.copy(wowquawitywabew = v)
      })

  vaw nysfwhighpwecisionwabew: w-wens[tweet, (ꈍᴗꈍ) o-option[safetywabew]] =
    tweetwens[option[safetywabew]](
      _.nsfwhighpwecisionwabew, ^•ﻌ•^
      {
        case (t, >_< v) => t.copy(nsfwhighpwecisionwabew = v-v)
      })

  v-vaw bouncewabew: wens[tweet, ^^;; option[safetywabew]] =
    tweetwens[option[safetywabew]](
      _.bouncewabew, ^^;;
      {
        c-case (t, /(^•ω•^) v) => t.copy(bouncewabew = v)
      })

  v-vaw takedowncountwycodes: wens[tweet, nyaa~~ option[seq[stwing]]] =
    tweetwens[option[seq[stwing]]](
      _.takedowncountwycodes, (✿oωo)
      {
        c-case (t, ( ͡o ω ͡o ) v) => t.copy(takedowncountwycodes = v)
      })

  v-vaw t-takedownweasons: wens[tweet, (U ᵕ U❁) option[seq[takedownweason]]] =
    t-tweetwens[option[seq[takedownweason]]](
      _.takedownweasons, òωó
      {
        case (t, σωσ v) => t-t.copy(takedownweasons = v-v)
      })

  v-vaw contwibutow: wens[tweet, :3 o-option[contwibutow]] =
    t-tweetwens[option[contwibutow]](
      _.contwibutow, OwO
      {
        case (t, ^^ v) => t.copy(contwibutow = v-v)
      })

  v-vaw mediatags: w-wens[tweet, (˘ω˘) option[tweetmediatags]] =
    tweetwens[option[tweetmediatags]](
      _.mediatags, OwO
      {
        c-case (t, UwU v) => t.copy(mediatags = v-v)
      })

  v-vaw mediatagmap: wens[tweet, ^•ﻌ•^ map[mediaid, (ꈍᴗꈍ) seq[mediatag]]] =
    t-tweetwens[map[mediaid, /(^•ω•^) s-seq[mediatag]]](
      _.mediatags.map { c-case tweetmediatags(tagmap) => t-tagmap.tomap }.getowewse(map.empty), (U ᵕ U❁)
      (t, v) => {
        v-vaw cweanmap = v.fiwtew { case (_, (✿oωo) tags) => tags.nonempty }
        t.copy(mediatags = if (cweanmap.nonempty) some(tweetmediatags(cweanmap)) e-ewse nyone)
      }
    )

  vaw eschewbiwdentityannotations: w-wens[tweet, OwO option[eschewbiwdentityannotations]] =
    tweetwens[option[eschewbiwdentityannotations]](
      _.eschewbiwdentityannotations, :3
      {
        case (t, nyaa~~ v-v) => t.copy(eschewbiwdentityannotations = v)
      })

  v-vaw communities: wens[tweet, ^•ﻌ•^ option[communities]] =
    t-tweetwens[option[communities]](
      _.communities, ( ͡o ω ͡o )
      {
        case (t, ^^;; v-v) => t.copy(communities = v-v)
      })

  v-vaw tweetypieonwytakedowncountwycodes: w-wens[tweet, mya option[seq[stwing]]] =
    tweetwens[option[seq[stwing]]](
      _.tweetypieonwytakedowncountwycodes, (U ᵕ U❁)
      {
        case (t, v) => t.copy(tweetypieonwytakedowncountwycodes = v)
      })

  vaw tweetypieonwytakedownweasons: w-wens[tweet, o-option[seq[takedownweason]]] =
    t-tweetwens[option[seq[takedownweason]]](
      _.tweetypieonwytakedownweasons, ^•ﻌ•^
      {
        case (t, (U ﹏ U) v) => t-t.copy(tweetypieonwytakedownweasons = v)
      })

  vaw pwofiwegeo: wens[tweet, /(^•ω•^) o-option[pwofiwegeoenwichment]] =
    t-tweetwens[option[pwofiwegeoenwichment]](
      _.pwofiwegeoenwichment, ʘwʘ
      (t, v) => t.copy(pwofiwegeoenwichment = v-v)
    )

  vaw visibwetextwange: wens[tweet, XD o-option[textwange]] =
    t-tweetwens[option[textwange]](
      _.visibwetextwange, (⑅˘꒳˘)
      {
        case (t, nyaa~~ v-v) => t.copy(visibwetextwange = v-v)
      })

  vaw sewfpewmawink: wens[tweet, UwU option[showteneduww]] =
    tweetwens[option[showteneduww]](
      _.sewfpewmawink, (˘ω˘)
      {
        c-case (t, rawr x3 v) => t-t.copy(sewfpewmawink = v-v)
      })

  v-vaw extendedtweetmetadata: w-wens[tweet, (///ˬ///✿) option[extendedtweetmetadata]] =
    t-tweetwens[option[extendedtweetmetadata]](
      _.extendedtweetmetadata, 😳😳😳
      {
        c-case (t, (///ˬ///✿) v) => t.copy(extendedtweetmetadata = v)
      })

  o-object t-tweetcowedata {
    vaw usewid: w-wens[tweetcowedata, ^^;; usewid] = checkeq[tweetcowedata, ^^ u-usewid](
      _.usewid, (///ˬ///✿)
      { (c, -.- v) =>
        // pweases t-the compiwew: h-https://github.com/scawa/bug/issues/9171
        vaw usewid = v-v
        c.copy(usewid = usewid)
      })
    vaw text: wens[tweetcowedata, /(^•ω•^) s-stwing] = checkeq[tweetcowedata, UwU s-stwing](
      _.text, (⑅˘꒳˘)
      { (c, ʘwʘ v-v) =>
        // pweases the compiwew: https://github.com/scawa/bug/issues/9171
        vaw t-text = v
        c.copy(text = text)
      })
    vaw cweatedat: w-wens[tweetcowedata, σωσ t-tweetid] =
      checkeq[tweetcowedata, ^^ w-wong](_.cweatedatsecs, (c, OwO v) => c.copy(cweatedatsecs = v-v))
    vaw c-cweatedvia: wens[tweetcowedata, (ˆ ﻌ ˆ)♡ stwing] =
      checkeq[tweetcowedata, o.O s-stwing](
        _.cweatedvia, (˘ω˘)
        {
          case (c, 😳 v) => c.copy(cweatedvia = v-v)
        })
    v-vaw hastakedown: wens[tweetcowedata, (U ᵕ U❁) b-boowean] =
      checkeq[tweetcowedata, :3 b-boowean](
        _.hastakedown, o.O
        {
          c-case (c, (///ˬ///✿) v) => c-c.copy(hastakedown = v)
        })
    vaw nyuwwcast: wens[tweetcowedata, OwO boowean] =
      checkeq[tweetcowedata, >w< boowean](
        _.nuwwcast, ^^
        {
          case (c, (⑅˘꒳˘) v) => c.copy(nuwwcast = v)
        })
    vaw nysfwusew: wens[tweetcowedata, ʘwʘ boowean] =
      c-checkeq[tweetcowedata, (///ˬ///✿) b-boowean](
        _.nsfwusew, XD
        {
          case (c, v) => c.copy(nsfwusew = v-v)
        })
    v-vaw nysfwadmin: w-wens[tweetcowedata, 😳 boowean] =
      c-checkeq[tweetcowedata, >w< boowean](
        _.nsfwadmin, (˘ω˘)
        {
          c-case (c, nyaa~~ v-v) => c.copy(nsfwadmin = v)
        })
    v-vaw wepwy: wens[tweetcowedata, 😳😳😳 o-option[wepwy]] =
      c-checkeq[tweetcowedata, (U ﹏ U) option[wepwy]](
        _.wepwy, (˘ω˘)
        {
          case (c, :3 v-v) => c.copy(wepwy = v-v)
        })
    v-vaw s-shawe: wens[tweetcowedata, >w< o-option[shawe]] =
      c-checkeq[tweetcowedata, ^^ o-option[shawe]](
        _.shawe, 😳😳😳
        {
          case (c, nyaa~~ v-v) => c.copy(shawe = v-v)
        })
    vaw nyawwowcast: w-wens[tweetcowedata, (⑅˘꒳˘) o-option[nawwowcast]] =
      c-checkeq[tweetcowedata, :3 option[nawwowcast]](
        _.nawwowcast, ʘwʘ
        {
          c-case (c, rawr x3 v) => c.copy(nawwowcast = v)
        })
    v-vaw diwectedatusew: wens[tweetcowedata, (///ˬ///✿) option[diwectedatusew]] =
      c-checkeq[tweetcowedata, 😳😳😳 o-option[diwectedatusew]](
        _.diwectedatusew, XD
        {
          c-case (c, v) => c.copy(diwectedatusew = v-v)
        })
    vaw convewsationid: w-wens[tweetcowedata, >_< option[convewsationid]] =
      c-checkeq[tweetcowedata, >w< option[convewsationid]](
        _.convewsationid, /(^•ω•^)
        {
          c-case (c, :3 v) => c.copy(convewsationid = v)
        })
    vaw pwaceid: wens[tweetcowedata, ʘwʘ option[stwing]] =
      c-checkeq[tweetcowedata, (˘ω˘) option[stwing]](
        _.pwaceid, (ꈍᴗꈍ)
        {
          c-case (c, ^^ v) => c-c.copy(pwaceid = v)
        })
    vaw geocoowdinates: wens[tweetcowedata, ^^ o-option[geocoowdinates]] =
      checkeq[tweetcowedata, ( ͡o ω ͡o ) o-option[geocoowdinates]](
        _.coowdinates, -.-
        (c, ^^;; v-v) => c-c.copy(coowdinates = v)
      )
    vaw twackingid: w-wens[tweetcowedata, ^•ﻌ•^ o-option[tweetid]] =
      checkeq[tweetcowedata, (˘ω˘) o-option[wong]](
        _.twackingid, o.O
        {
          case (c, v) => c.copy(twackingid = v-v)
        })
    vaw hasmedia: w-wens[tweetcowedata, (✿oωo) o-option[boowean]] =
      c-checkeq[tweetcowedata, 😳😳😳 option[boowean]](
        _.hasmedia, (ꈍᴗꈍ)
        {
          c-case (c, σωσ v) => c-c.copy(hasmedia = v-v)
        })
  }

  v-vaw counts: wens[tweet, UwU o-option[statuscounts]] =
    t-tweetwens[option[statuscounts]](
      _.counts, ^•ﻌ•^
      {
        c-case (t, mya v) => t-t.copy(counts = v-v)
      })

  object s-statuscounts {
    v-vaw wetweetcount: w-wens[statuscounts, option[tweetid]] =
      c-checkeq[statuscounts, /(^•ω•^) option[wong]](
        _.wetweetcount, rawr
        (c, nyaa~~ w-wetweetcount) => c.copy(wetweetcount = w-wetweetcount)
      )

    v-vaw wepwycount: w-wens[statuscounts, ( ͡o ω ͡o ) option[tweetid]] =
      checkeq[statuscounts, option[wong]](
        _.wepwycount, σωσ
        (c, (✿oωo) w-wepwycount) => c-c.copy(wepwycount = w-wepwycount)
      )

    vaw favowitecount: wens[statuscounts, (///ˬ///✿) option[tweetid]] =
      c-checkeq[statuscounts, σωσ o-option[wong]](
        _.favowitecount, UwU
        {
          case (c, (⑅˘꒳˘) v) => c-c.copy(favowitecount = v-v)
        })

    vaw quotecount: wens[statuscounts, /(^•ω•^) option[tweetid]] =
      c-checkeq[statuscounts, -.- o-option[wong]](
        _.quotecount,
        {
          c-case (c, (ˆ ﻌ ˆ)♡ v) => c-c.copy(quotecount = v)
        })
  }

  vaw u-usewid: wens[tweet, nyaa~~ u-usewid] = wequiwedcowedata andthen tweetcowedata.usewid
  v-vaw text: wens[tweet, ʘwʘ stwing] = wequiwedcowedata a-andthen tweetcowedata.text
  vaw c-cweatedvia: wens[tweet, :3 s-stwing] = wequiwedcowedata a-andthen tweetcowedata.cweatedvia
  v-vaw cweatedat: wens[tweet, (U ᵕ U❁) c-convewsationid] = wequiwedcowedata a-andthen tweetcowedata.cweatedat
  v-vaw wepwy: w-wens[tweet, (U ﹏ U) option[wepwy]] = w-wequiwedcowedata andthen tweetcowedata.wepwy
  vaw s-shawe: wens[tweet, ^^ o-option[shawe]] = w-wequiwedcowedata andthen t-tweetcowedata.shawe
  vaw nyawwowcast: wens[tweet, òωó o-option[nawwowcast]] =
    w-wequiwedcowedata a-andthen tweetcowedata.nawwowcast
  vaw diwectedatusew: wens[tweet, /(^•ω•^) option[diwectedatusew]] =
    wequiwedcowedata a-andthen tweetcowedata.diwectedatusew
  vaw convewsationid: w-wens[tweet, 😳😳😳 o-option[convewsationid]] =
    wequiwedcowedata andthen tweetcowedata.convewsationid
  v-vaw pwaceid: wens[tweet, :3 o-option[stwing]] = w-wequiwedcowedata a-andthen t-tweetcowedata.pwaceid
  v-vaw geocoowdinates: wens[tweet, (///ˬ///✿) option[geocoowdinates]] =
    wequiwedcowedata andthen t-tweetcowedata.geocoowdinates
  vaw hastakedown: w-wens[tweet, rawr x3 boowean] = wequiwedcowedata andthen tweetcowedata.hastakedown
  v-vaw nysfwadmin: wens[tweet, (U ᵕ U❁) boowean] = wequiwedcowedata andthen tweetcowedata.nsfwadmin
  v-vaw nysfwusew: w-wens[tweet, (⑅˘꒳˘) boowean] = wequiwedcowedata a-andthen tweetcowedata.nsfwusew
  vaw n-nyuwwcast: wens[tweet, (˘ω˘) b-boowean] = wequiwedcowedata a-andthen tweetcowedata.nuwwcast
  vaw twackingid: w-wens[tweet, :3 option[convewsationid]] =
    wequiwedcowedata andthen tweetcowedata.twackingid
  v-vaw hasmedia: wens[tweet, option[boowean]] = wequiwedcowedata a-andthen tweetcowedata.hasmedia

  o-object cashtagentity {
    vaw i-indices: wens[cashtagentity, XD (showt, showt)] =
      checkeq[cashtagentity, >_< (showt, s-showt)](
        t => (t.fwomindex, (✿oωo) t.toindex), (ꈍᴗꈍ)
        (t, v) => t.copy(fwomindex = v._1, XD t-toindex = v._2)
      )
    v-vaw t-text: wens[cashtagentity, :3 s-stwing] =
      checkeq[cashtagentity, mya stwing](_.text, òωó (t, t-text) => t-t.copy(text = text))
  }

  object hashtagentity {
    v-vaw indices: wens[hashtagentity, (showt, nyaa~~ showt)] =
      c-checkeq[hashtagentity, 🥺 (showt, showt)](
        t => (t.fwomindex, -.- t.toindex), 🥺
        (t, (˘ω˘) v-v) => t-t.copy(fwomindex = v._1, òωó toindex = v-v._2)
      )
    v-vaw text: w-wens[hashtagentity, UwU stwing] =
      checkeq[hashtagentity, ^•ﻌ•^ s-stwing](_.text, mya (t, text) => t.copy(text = text))
  }

  o-object mediaentity {
    vaw indices: wens[mediaentity, (✿oωo) (showt, showt)] =
      c-checkeq[mediaentity, XD (showt, s-showt)](
        t-t => (t.fwomindex, :3 t-t.toindex), (U ﹏ U)
        (t, v-v) => t.copy(fwomindex = v-v._1, toindex = v._2)
      )
    vaw mediasizes: w-wens[mediaentity, cowwection.set[mediasize]] =
      c-checkeq[mediaentity, UwU scawa.cowwection.set[mediasize]](
        _.sizes, ʘwʘ
        (m, >w< sizes) => m.copy(sizes = s-sizes)
      )
    v-vaw uww: wens[mediaentity, 😳😳😳 s-stwing] =
      checkeq[mediaentity, rawr s-stwing](
        _.uww, ^•ﻌ•^
        {
          c-case (t, σωσ v) => t.copy(uww = v-v)
        })
    v-vaw mediainfo: wens[mediaentity, :3 o-option[mediainfo]] =
      checkeq[mediaentity, option[mediainfo]](
        _.mediainfo, rawr x3
        {
          case (t, nyaa~~ v) => t-t.copy(mediainfo = v)
        })
  }

  o-object mentionentity {
    vaw indices: w-wens[mentionentity, :3 (showt, s-showt)] =
      c-checkeq[mentionentity, >w< (showt, rawr showt)](
        t => (t.fwomindex, 😳 t-t.toindex), 😳
        (t, v-v) => t.copy(fwomindex = v-v._1, 🥺 toindex = v._2)
      )
    v-vaw scweenname: wens[mentionentity, rawr x3 s-stwing] =
      c-checkeq[mentionentity, ^^ stwing](
        _.scweenname, ( ͡o ω ͡o )
        (t, XD scweenname) => t.copy(scweenname = scweenname)
      )
  }

  object u-uwwentity {
    v-vaw indices: wens[uwwentity, (showt, ^^ showt)] =
      checkeq[uwwentity, (⑅˘꒳˘) (showt, (⑅˘꒳˘) showt)](
        t-t => (t.fwomindex, ^•ﻌ•^ t.toindex), ( ͡o ω ͡o )
        (t, ( ͡o ω ͡o ) v-v) => t-t.copy(fwomindex = v._1, (✿oωo) toindex = v._2)
      )
    vaw uww: wens[uwwentity, 😳😳😳 s-stwing] =
      checkeq[uwwentity, OwO stwing](_.uww, ^^ (t, rawr x3 u-uww) => t.copy(uww = uww))
  }

  o-object c-contwibutow {
    vaw scweenname: w-wens[contwibutow, 🥺 o-option[stwing]] =
      c-checkeq[contwibutow, (ˆ ﻌ ˆ)♡ o-option[stwing]](
        _.scweenname,
        (c, ( ͡o ω ͡o ) s-scweenname) => c-c.copy(scweenname = scweenname)
      )
  }

  object wepwy {
    vaw inwepwytoscweenname: wens[wepwy, >w< option[stwing]] =
      c-checkeq[wepwy, /(^•ω•^) o-option[stwing]](
        _.inwepwytoscweenname, 😳😳😳
        (c, i-inwepwytoscweenname) => c-c.copy(inwepwytoscweenname = i-inwepwytoscweenname)
      )

    v-vaw inwepwytostatusid: wens[wepwy, (U ᵕ U❁) option[tweetid]] =
      checkeq[wepwy, (˘ω˘) option[tweetid]](
        _.inwepwytostatusid, 😳
        (c, (ꈍᴗꈍ) inwepwytostatusid) => c-c.copy(inwepwytostatusid = i-inwepwytostatusid)
      )
  }
}
