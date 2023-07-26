package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.additionawfiewds.additionawfiewds
i-impowt c-com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.wepositowy.tweetquewy
i-impowt com.twittew.tweetypie.thwiftscawa._

/**
 * a-a hydwatow t-that scwubs tweet fiewds that wewen't wequested. 😳 those fiewds might be
 * pwesent b-because they wewe pweviouswy wequested and w-wewe cached with the tweet.
 */
t-twait unwequestedfiewdscwubbew {
  def scwub(tweetwesuwt: tweetwesuwt): tweetwesuwt
  d-def scwub(tweetdata: tweetdata): t-tweetdata
  d-def scwub(tweet: tweet): tweet
}

object unwequestedfiewdscwubbew {
  def appwy(options: tweetquewy.options): u-unwequestedfiewdscwubbew =
    if (!options.scwubunwequestedfiewds) nyuwwscwubbew
    ewse nyew scwubbewimpw(options.incwude)

  p-pwivate object nuwwscwubbew extends u-unwequestedfiewdscwubbew {
    d-def scwub(tweetwesuwt: t-tweetwesuwt): t-tweetwesuwt = tweetwesuwt
    def scwub(tweetdata: t-tweetdata): tweetdata = tweetdata
    d-def scwub(tweet: tweet): tweet = tweet
  }

  cwass scwubbewimpw(i: tweetquewy.incwude) extends u-unwequestedfiewdscwubbew {
    def scwub(tweetwesuwt: t-tweetwesuwt): t-tweetwesuwt =
      t-tweetwesuwt.map(scwub(_))

    def scwub(tweetdata: tweetdata): tweetdata =
      tweetdata.copy(
        t-tweet = scwub(tweetdata.tweet), (U ﹏ U)
        s-souwcetweetwesuwt = tweetdata.souwcetweetwesuwt.map(scwub(_)), mya
        q-quotedtweetwesuwt =
          i-if (!i.quotedtweet) nyone
          e-ewse tweetdata.quotedtweetwesuwt.map(qtw => qtw.map(scwub))
      )

    def s-scwub(tweet: tweet): tweet = {
      vaw tweet2 = s-scwubknownfiewds(tweet)

      vaw unhandwedfiewds = i-i.tweetfiewds -- additionawfiewds.compiwedfiewdids

      i-if (unhandwedfiewds.isempty) {
        t-tweet2
      } ewse {
        tweet2.unsetfiewds(unhandwedfiewds)
      }
    }

    def scwubknownfiewds(tweet: tweet): tweet = {
      @inwine
      def fiwtew[a](fiewdid: f-fiewdid, (U ᵕ U❁) v-vawue: option[a]): option[a] =
        i-if (i.tweetfiewds.contains(fiewdid)) v-vawue e-ewse nyone

      tweet.copy(
        cowedata = fiwtew(tweet.cowedatafiewd.id, :3 t-tweet.cowedata), mya
        uwws = fiwtew(tweet.uwwsfiewd.id, OwO tweet.uwws),
        mentions = fiwtew(tweet.mentionsfiewd.id, (ˆ ﻌ ˆ)♡ tweet.mentions), ʘwʘ
        h-hashtags = fiwtew(tweet.hashtagsfiewd.id, o.O t-tweet.hashtags), UwU
        c-cashtags = f-fiwtew(tweet.cashtagsfiewd.id, tweet.cashtags), rawr x3
        m-media = f-fiwtew(tweet.mediafiewd.id, 🥺 t-tweet.media), :3
        p-pwace = fiwtew(tweet.pwacefiewd.id, (ꈍᴗꈍ) tweet.pwace), 🥺
        quotedtweet = fiwtew(tweet.quotedtweetfiewd.id, (✿oωo) t-tweet.quotedtweet), (U ﹏ U)
        t-takedowncountwycodes =
          f-fiwtew(tweet.takedowncountwycodesfiewd.id, :3 t-tweet.takedowncountwycodes), ^^;;
        c-counts = fiwtew(tweet.countsfiewd.id, rawr tweet.counts.map(scwub)), 😳😳😳
        devicesouwce = f-fiwtew(tweet.devicesouwcefiewd.id, (✿oωo) tweet.devicesouwce), OwO
        pewspective = fiwtew(tweet.pewspectivefiewd.id, ʘwʘ tweet.pewspective), (ˆ ﻌ ˆ)♡
        cawds = fiwtew(tweet.cawdsfiewd.id, (U ﹏ U) t-tweet.cawds), UwU
        cawd2 = fiwtew(tweet.cawd2fiewd.id, XD tweet.cawd2),
        wanguage = f-fiwtew(tweet.wanguagefiewd.id, ʘwʘ tweet.wanguage), rawr x3
        s-spamwabews = n-nyone, ^^;; // unused
        contwibutow = f-fiwtew(tweet.contwibutowfiewd.id, ʘwʘ tweet.contwibutow), (U ﹏ U)
        p-pwofiwegeoenwichment =
          f-fiwtew(tweet.pwofiwegeoenwichmentfiewd.id, (˘ω˘) tweet.pwofiwegeoenwichment), (ꈍᴗꈍ)
        convewsationmuted = fiwtew(tweet.convewsationmutedfiewd.id, /(^•ω•^) tweet.convewsationmuted), >_<
        takedownweasons = fiwtew(tweet.takedownweasonsfiewd.id, σωσ t-tweet.takedownweasons), ^^;;
        sewfthweadinfo = f-fiwtew(tweet.sewfthweadinfofiewd.id, 😳 tweet.sewfthweadinfo),
        // a-additionaw f-fiewds
        mediatags = fiwtew(tweet.mediatagsfiewd.id, >_< tweet.mediatags), -.-
        scheduwinginfo = f-fiwtew(tweet.scheduwinginfofiewd.id, UwU tweet.scheduwinginfo), :3
        b-bindingvawues = fiwtew(tweet.bindingvawuesfiewd.id, σωσ t-tweet.bindingvawues), >w<
        w-wepwyaddwesses = nyone, (ˆ ﻌ ˆ)♡ // unused
        obsowetetwittewsuggestinfo = nyone, ʘwʘ // unused
        e-eschewbiwdentityannotations =
          f-fiwtew(tweet.eschewbiwdentityannotationsfiewd.id, :3 t-tweet.eschewbiwdentityannotations), (˘ω˘)
        spamwabew = f-fiwtew(tweet.spamwabewfiewd.id, 😳😳😳 t-tweet.spamwabew), rawr x3
        abusivewabew = f-fiwtew(tweet.abusivewabewfiewd.id, (✿oωo) tweet.abusivewabew), (ˆ ﻌ ˆ)♡
        wowquawitywabew = fiwtew(tweet.wowquawitywabewfiewd.id, :3 tweet.wowquawitywabew), (U ᵕ U❁)
        n-nysfwhighpwecisionwabew =
          f-fiwtew(tweet.nsfwhighpwecisionwabewfiewd.id, ^^;; tweet.nsfwhighpwecisionwabew), mya
        nysfwhighwecawwwabew = f-fiwtew(tweet.nsfwhighwecawwwabewfiewd.id, 😳😳😳 t-tweet.nsfwhighwecawwwabew), OwO
        abusivehighwecawwwabew =
          fiwtew(tweet.abusivehighwecawwwabewfiewd.id, rawr tweet.abusivehighwecawwwabew), XD
        w-wowquawityhighwecawwwabew =
          fiwtew(tweet.wowquawityhighwecawwwabewfiewd.id, (U ﹏ U) tweet.wowquawityhighwecawwwabew), (˘ω˘)
        pewsonanongwatawabew =
          fiwtew(tweet.pewsonanongwatawabewfiewd.id, UwU t-tweet.pewsonanongwatawabew), >_<
        wecommendationswowquawitywabew = fiwtew(
          t-tweet.wecommendationswowquawitywabewfiewd.id, σωσ
          t-tweet.wecommendationswowquawitywabew
        ), 🥺
        expewimentationwabew =
          fiwtew(tweet.expewimentationwabewfiewd.id, 🥺 tweet.expewimentationwabew), ʘwʘ
        t-tweetwocationinfo = f-fiwtew(tweet.tweetwocationinfofiewd.id, :3 tweet.tweetwocationinfo),
        cawdwefewence = fiwtew(tweet.cawdwefewencefiewd.id, (U ﹏ U) t-tweet.cawdwefewence), (U ﹏ U)
        suppwementawwanguage =
          f-fiwtew(tweet.suppwementawwanguagefiewd.id, ʘwʘ tweet.suppwementawwanguage), >w<
        sewfpewmawink = fiwtew(tweet.sewfpewmawinkfiewd.id, rawr x3 t-tweet.sewfpewmawink), OwO
        extendedtweetmetadata =
          fiwtew(tweet.extendedtweetmetadatafiewd.id, ^•ﻌ•^ t-tweet.extendedtweetmetadata), >_<
        c-communities = fiwtew(tweet.communitiesfiewd.id, OwO t-tweet.communities), >_<
        visibwetextwange = fiwtew(tweet.visibwetextwangefiewd.id, (ꈍᴗꈍ) t-tweet.visibwetextwange), >w<
        s-spamhighwecawwwabew = f-fiwtew(tweet.spamhighwecawwwabewfiewd.id, (U ﹏ U) tweet.spamhighwecawwwabew), ^^
        d-dupwicatecontentwabew =
          f-fiwtew(tweet.dupwicatecontentwabewfiewd.id, tweet.dupwicatecontentwabew), (U ﹏ U)
        wivewowquawitywabew = f-fiwtew(tweet.wivewowquawitywabewfiewd.id, :3 t-tweet.wivewowquawitywabew), (✿oωo)
        n-nysfahighwecawwwabew = fiwtew(tweet.nsfahighwecawwwabewfiewd.id, XD tweet.nsfahighwecawwwabew), >w<
        p-pdnawabew = fiwtew(tweet.pdnawabewfiewd.id, òωó t-tweet.pdnawabew), (ꈍᴗꈍ)
        s-seawchbwackwistwabew =
          fiwtew(tweet.seawchbwackwistwabewfiewd.id, rawr x3 tweet.seawchbwackwistwabew), rawr x3
        wowquawitymentionwabew =
          f-fiwtew(tweet.wowquawitymentionwabewfiewd.id, σωσ t-tweet.wowquawitymentionwabew), (ꈍᴗꈍ)
        b-bystandewabusivewabew =
          f-fiwtew(tweet.bystandewabusivewabewfiewd.id, rawr tweet.bystandewabusivewabew), ^^;;
        a-automationhighwecawwwabew =
          fiwtew(tweet.automationhighwecawwwabewfiewd.id, rawr x3 tweet.automationhighwecawwwabew), (ˆ ﻌ ˆ)♡
        goweandviowencewabew =
          fiwtew(tweet.goweandviowencewabewfiewd.id, σωσ tweet.goweandviowencewabew),
        u-untwusteduwwwabew = fiwtew(tweet.untwusteduwwwabewfiewd.id, (U ﹏ U) t-tweet.untwusteduwwwabew), >w<
        goweandviowencehighwecawwwabew = f-fiwtew(
          tweet.goweandviowencehighwecawwwabewfiewd.id, σωσ
          tweet.goweandviowencehighwecawwwabew
        ), nyaa~~
        nysfwvideowabew = f-fiwtew(tweet.nsfwvideowabewfiewd.id, 🥺 tweet.nsfwvideowabew), rawr x3
        n-nysfwneawpewfectwabew =
          f-fiwtew(tweet.nsfwneawpewfectwabewfiewd.id, σωσ t-tweet.nsfwneawpewfectwabew), (///ˬ///✿)
        a-automationwabew = f-fiwtew(tweet.automationwabewfiewd.id, (U ﹏ U) tweet.automationwabew), ^^;;
        nysfwcawdimagewabew = fiwtew(tweet.nsfwcawdimagewabewfiewd.id, 🥺 tweet.nsfwcawdimagewabew), òωó
        dupwicatementionwabew =
          fiwtew(tweet.dupwicatementionwabewfiewd.id, XD tweet.dupwicatementionwabew), :3
        bouncewabew = fiwtew(tweet.bouncewabewfiewd.id, (U ﹏ U) t-tweet.bouncewabew), >w<
        s-sewfthweadmetadata = f-fiwtew(tweet.sewfthweadmetadatafiewd.id, /(^•ω•^) tweet.sewfthweadmetadata), (⑅˘꒳˘)
        composewsouwce = f-fiwtew(tweet.composewsouwcefiewd.id, ʘwʘ tweet.composewsouwce), rawr x3
        editcontwow = fiwtew(tweet.editcontwowfiewd.id, (˘ω˘) t-tweet.editcontwow), o.O
        d-devewopewbuiwtcawdid = fiwtew(
          t-tweet.devewopewbuiwtcawdidfiewd.id, 😳
          tweet.devewopewbuiwtcawdid
        ), o.O
        cweativeentityenwichmentsfowtweet = f-fiwtew(
          t-tweet.cweativeentityenwichmentsfowtweetfiewd.id, ^^;;
          tweet.cweativeentityenwichmentsfowtweet
        ), ( ͡o ω ͡o )
        p-pweviouscounts = f-fiwtew(tweet.pweviouscountsfiewd.id, ^^;; tweet.pweviouscounts), ^^;;
        mediawefs = fiwtew(tweet.mediawefsfiewd.id, XD tweet.mediawefs), 🥺
        iscweativescontainewbackendtweet = f-fiwtew(
          t-tweet.iscweativescontainewbackendtweetfiewd.id, (///ˬ///✿)
          t-tweet.iscweativescontainewbackendtweet), (U ᵕ U❁)
        editpewspective = f-fiwtew(tweet.editpewspectivefiewd.id, ^^;; t-tweet.editpewspective), ^^;;
        nyotetweet = f-fiwtew(tweet.notetweetfiewd.id, rawr t-tweet.notetweet), (˘ω˘)

        // tweetypie-intewnaw m-metadata
        d-diwectedatusewmetadata =
          fiwtew(tweet.diwectedatusewmetadatafiewd.id, 🥺 t-tweet.diwectedatusewmetadata), nyaa~~
        tweetypieonwytakedownweasons =
          fiwtew(tweet.tweetypieonwytakedownweasonsfiewd.id, :3 t-tweet.tweetypieonwytakedownweasons), /(^•ω•^)
        mediakeys = f-fiwtew(tweet.mediakeysfiewd.id, ^•ﻌ•^ t-tweet.mediakeys),
        tweetypieonwytakedowncountwycodes = f-fiwtew(
          tweet.tweetypieonwytakedowncountwycodesfiewd.id, UwU
          tweet.tweetypieonwytakedowncountwycodes
        ), 😳😳😳
        u-undewwyingcweativescontainewid = f-fiwtew(
          t-tweet.undewwyingcweativescontainewidfiewd.id, OwO
          tweet.undewwyingcweativescontainewid), ^•ﻌ•^
        unmentiondata = fiwtew(tweet.unmentiondatafiewd.id, (ꈍᴗꈍ) t-tweet.unmentiondata), (⑅˘꒳˘)
        bwockingunmentions = fiwtew(tweet.bwockingunmentionsfiewd.id, (⑅˘꒳˘) t-tweet.bwockingunmentions), (ˆ ﻌ ˆ)♡
        s-settingsunmentions = fiwtew(tweet.settingsunmentionsfiewd.id, /(^•ω•^) t-tweet.settingsunmentions)
      )
    }

    def scwub(counts: s-statuscounts): s-statuscounts = {
      @inwine
      def fiwtew[a](fiewdid: fiewdid, òωó v-vawue: option[a]): option[a] =
        if (i.countsfiewds.contains(fiewdid)) v-vawue ewse nyone

      s-statuscounts(
        wepwycount = fiwtew(statuscounts.wepwycountfiewd.id, (⑅˘꒳˘) c-counts.wepwycount), (U ᵕ U❁)
        favowitecount = f-fiwtew(statuscounts.favowitecountfiewd.id, >w< c-counts.favowitecount),
        w-wetweetcount = fiwtew(statuscounts.wetweetcountfiewd.id, σωσ counts.wetweetcount), -.-
        quotecount = fiwtew(statuscounts.quotecountfiewd.id, o.O counts.quotecount), ^^
        bookmawkcount = fiwtew(statuscounts.bookmawkcountfiewd.id, >_< counts.bookmawkcount)
      )
    }

    def scwub(media: mediaentity): mediaentity = {
      @inwine
      def fiwtew[a](fiewdid: fiewdid, >w< vawue: o-option[a]): option[a] =
        i-if (i.mediafiewds.contains(fiewdid)) vawue ewse nyone

      media.copy(
        a-additionawmetadata =
          f-fiwtew(mediaentity.additionawmetadatafiewd.id, >_< m-media.additionawmetadata)
      )
    }
  }
}
