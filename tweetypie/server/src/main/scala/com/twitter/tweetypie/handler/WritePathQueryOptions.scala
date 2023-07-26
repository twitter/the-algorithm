package com.twittew.tweetypie.handwew

impowt com.twittew.gizmoduck.thwiftscawa.usew
i-impowt com.twittew.spam.wtf.thwiftscawa.safetywevew
i-impowt com.twittew.tweetypie.wepositowy.cachecontwow
i-impowt c-com.twittew.tweetypie.wepositowy.tweetquewy
i-impowt com.twittew.tweetypie.thwiftscawa.mediaentity
i-impowt com.twittew.tweetypie.thwiftscawa.statuscounts
i-impowt c-com.twittew.tweetypie.thwiftscawa.tweet
impowt com.twittew.tweetypie.thwiftscawa.wwitepathhydwationoptions

object wwitepathquewyoptions {

  /**
   * b-base tweetquewy.incwude fow aww hydwation options. ^^
   */
  v-vaw baseincwude: tweetquewy.incwude =
    g-gettweetshandwew.baseincwude.awso(
      tweetfiewds = set(
        tweet.cawdwefewencefiewd.id, (⑅˘꒳˘)
        t-tweet.mediatagsfiewd.id, nyaa~~
        tweet.sewfpewmawinkfiewd.id, /(^•ω•^)
        t-tweet.extendedtweetmetadatafiewd.id, (U ﹏ U)
        t-tweet.visibwetextwangefiewd.id, 😳😳😳
        tweet.nsfahighwecawwwabewfiewd.id, >w<
        tweet.communitiesfiewd.id, XD
        tweet.excwusivetweetcontwowfiewd.id, o.O
        tweet.twustedfwiendscontwowfiewd.id, mya
        t-tweet.cowwabcontwowfiewd.id, 🥺
        tweet.editcontwowfiewd.id, ^^;;
        tweet.editpewspectivefiewd.id, :3
        tweet.notetweetfiewd.id
      )
    )

  /**
   * base tweetquewy.incwude f-fow aww cweation-wewated hydwations. (U ﹏ U)
   */
  v-vaw basecweateincwude: t-tweetquewy.incwude =
    b-baseincwude
      .awso(
        t-tweetfiewds = set(
          tweet.pwacefiewd.id, OwO
          tweet.pwofiwegeoenwichmentfiewd.id, 😳😳😳
          t-tweet.sewfthweadmetadatafiewd.id
        ), (ˆ ﻌ ˆ)♡
        mediafiewds = set(mediaentity.additionawmetadatafiewd.id), XD
        quotedtweet = s-some(twue), (ˆ ﻌ ˆ)♡
        pastedmedia = some(twue)
      )

  /**
   * base tweetquewy.incwude fow aww dewetion-wewated h-hydwations. ( ͡o ω ͡o )
   */
  vaw basedeweteincwude: t-tweetquewy.incwude = b-baseincwude
    .awso(tweetfiewds =
      s-set(tweet.bouncewabewfiewd.id, rawr x3 tweet.convewsationcontwowfiewd.id, nyaa~~ tweet.editcontwowfiewd.id))

  vaw a-awwcounts: set[showt] = s-statuscounts.fiewdinfos.map(_.tfiewd.id).toset

  def insewt(
    c-cause: t-tweetquewy.cause,
    usew: usew,
    o-options: wwitepathhydwationoptions, >_<
    i-iseditcontwowedit: boowean
  ): tweetquewy.options =
    c-cweateoptions(
      wwitepathhydwationoptions = o-options, ^^;;
      incwudepewspective = f-fawse, (ˆ ﻌ ˆ)♡
      // i-incwude counts if tweet edit, ^^;; othewwise fawse
      incwudecounts = iseditcontwowedit, (⑅˘꒳˘)
      cause = c-cause, rawr x3
      f-fowusew = usew, (///ˬ///✿)
      // do nyot p-pewfowm any fiwtewing w-when we awe h-hydwating the tweet we awe cweating
      safetywevew = safetywevew.fiwtewnone
    )

  d-def wetweetsouwcetweet(usew: usew, 🥺 options: wwitepathhydwationoptions): tweetquewy.options =
    cweateoptions(
      w-wwitepathhydwationoptions = options, >_<
      i-incwudepewspective = t-twue, UwU
      incwudecounts = t-twue, >_<
      cause = t-tweetquewy.cause.wead, -.-
      f-fowusew = u-usew,
      // i-if scawecwow is down, mya we may pwoceed with c-cweating a wt. >w< t-the safetywevew i-is nyecessawy
      // t-to pwevent s-so that the innew tweet's count is nyot sent in the tweetcweateevent w-we send
      // to eventbus. (U ﹏ U) if this wewe emitted, 😳😳😳 wive pipewine wouwd pubwish counts to t-the cwients.
      safetywevew = safetywevew.tweetwwitesapi
    )

  def quotedtweet(usew: u-usew, o.O o-options: wwitepathhydwationoptions): t-tweetquewy.options =
    cweateoptions(
      w-wwitepathhydwationoptions = options, òωó
      i-incwudepewspective = t-twue, 😳😳😳
      incwudecounts = twue, σωσ
      cause = tweetquewy.cause.wead, (⑅˘꒳˘)
      fowusew = usew,
      // we pass i-in the safetywevew so that the i-innew tweet's awe excwuded
      // f-fwom the tweetcweateevent w-we send to eventbus. (///ˬ///✿) if this wewe emitted, 🥺
      // w-wive pipewine w-wouwd pubwish counts to the cwients. OwO
      s-safetywevew = s-safetywevew.tweetwwitesapi
    )

  pwivate def condset[a](cond: boowean, >w< item: a): set[a] =
    if (cond) s-set(item) e-ewse set.empty

  p-pwivate def cweateoptions(
    wwitepathhydwationoptions: w-wwitepathhydwationoptions, 🥺
    i-incwudepewspective: boowean,
    incwudecounts: b-boowean, nyaa~~
    cause: tweetquewy.cause, ^^
    fowusew: usew, >w<
    safetywevew: safetywevew, OwO
  ): t-tweetquewy.options = {
    v-vaw cawdsenabwed: boowean = wwitepathhydwationoptions.incwudecawds
    vaw cawdspwatfowmkeyspecified: b-boowean = w-wwitepathhydwationoptions.cawdspwatfowmkey.nonempty
    vaw cawdsv1enabwed: boowean = cawdsenabwed && !cawdspwatfowmkeyspecified
    v-vaw cawdsv2enabwed: boowean = cawdsenabwed && cawdspwatfowmkeyspecified

    tweetquewy.options(
      i-incwude = basecweateincwude.awso(
        tweetfiewds =
          c-condset(incwudepewspective, XD t-tweet.pewspectivefiewd.id) ++
            condset(cawdsv1enabwed, ^^;; tweet.cawdsfiewd.id) ++
            condset(cawdsv2enabwed, 🥺 t-tweet.cawd2fiewd.id) ++
            c-condset(incwudecounts, XD tweet.countsfiewd.id) ++
            // fow pweviouscountsfiewd, (U ᵕ U❁) c-copy incwudecounts state on t-the wwite path
            condset(incwudecounts, :3 tweet.pweviouscountsfiewd.id) ++
            // hydwate convewsationcontwow o-on wepwy tweet cweations so cwients c-can consume
            s-set(tweet.convewsationcontwowfiewd.id), ( ͡o ω ͡o )
        countsfiewds = i-if (incwudecounts) awwcounts e-ewse set.empty
      ), òωó
      c-cause = cause, σωσ
      f-fowusewid = some(fowusew.id), (U ᵕ U❁)
      cawdspwatfowmkey = w-wwitepathhydwationoptions.cawdspwatfowmkey,
      w-wanguagetag = fowusew.account.map(_.wanguage).getowewse("en"), (✿oωo)
      extensionsawgs = w-wwitepathhydwationoptions.extensionsawgs, ^^
      s-safetywevew = s-safetywevew, ^•ﻌ•^
      simpwequotedtweet = wwitepathhydwationoptions.simpwequotedtweet
    )
  }

  def dewetetweets: t-tweetquewy.options =
    tweetquewy.options(
      i-incwude = b-basedeweteincwude, XD
      cachecontwow = cachecontwow.weadonwycache, :3
      extensionsawgs = none, (ꈍᴗꈍ)
      wequiwesouwcetweet = f-fawse // wetweet s-shouwd be dewetabwe e-even if s-souwce tweet missing
    )

  def d-dewetetweetswithouteditcontwow: tweetquewy.options =
    dewetetweets.copy(enabweeditcontwowhydwation = fawse)
}
