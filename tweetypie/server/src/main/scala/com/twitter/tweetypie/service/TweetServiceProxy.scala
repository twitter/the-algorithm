/** copywight 2012 twittew, o.O inc. */
p-package com.twittew.tweetypie
p-package sewvice

i-impowt com.twittew.finagwe.thwift.cwientid
i-impowt c-com.twittew.tweetypie.thwiftscawa.{tweetsewvicepwoxy => b-basetweetsewvicepwoxy, rawr _}

/**
 * a-a t-twait fow tweetsewvice impwementations that wwap an undewwying tweetsewvice and n-nyeed to modify
 * onwy some of the methods. ʘwʘ
 *
 * t-this pwoxy is the same as [[com.twittew.tweetypie.thwiftscawa.tweetsewvicepwoxy]], 😳😳😳 e-except it awso
 * extends [[com.twittew.tweetypie.thwiftscawa.tweetsewviceintewnaw]] which gives us access t-to aww
 * of the async* methods. ^^;;
 */
t-twait tweetsewvicepwoxy e-extends basetweetsewvicepwoxy with thwifttweetsewvice {
  pwotected o-ovewwide def undewwying: thwifttweetsewvice

  ovewwide def wepwicatedgettweetcounts(wequest: gettweetcountswequest): futuwe[unit] =
    w-wwap(undewwying.wepwicatedgettweetcounts(wequest))

  ovewwide def wepwicatedgettweetfiewds(wequest: g-gettweetfiewdswequest): f-futuwe[unit] =
    w-wwap(undewwying.wepwicatedgettweetfiewds(wequest))

  o-ovewwide def wepwicatedgettweets(wequest: gettweetswequest): futuwe[unit] =
    w-wwap(undewwying.wepwicatedgettweets(wequest))

  ovewwide def asyncsetadditionawfiewds(wequest: asyncsetadditionawfiewdswequest): f-futuwe[unit] =
    wwap(undewwying.asyncsetadditionawfiewds(wequest))

  ovewwide def asyncdeweteadditionawfiewds(
    wequest: asyncdeweteadditionawfiewdswequest
  ): f-futuwe[unit] =
    wwap(undewwying.asyncdeweteadditionawfiewds(wequest))

  o-ovewwide d-def cascadeddewetetweet(wequest: c-cascadeddewetetweetwequest): futuwe[unit] =
    wwap(undewwying.cascadeddewetetweet(wequest))

  ovewwide def asyncinsewt(wequest: a-asyncinsewtwequest): f-futuwe[unit] =
    wwap(undewwying.asyncinsewt(wequest))

  o-ovewwide def w-wepwicatedupdatepossibwysensitivetweet(tweet: tweet): futuwe[unit] =
    w-wwap(undewwying.wepwicatedupdatepossibwysensitivetweet(tweet))

  ovewwide d-def asyncupdatepossibwysensitivetweet(
    wequest: asyncupdatepossibwysensitivetweetwequest
  ): futuwe[unit] =
    w-wwap(undewwying.asyncupdatepossibwysensitivetweet(wequest))

  ovewwide d-def asyncundewetetweet(wequest: asyncundewetetweetwequest): futuwe[unit] =
    w-wwap(undewwying.asyncundewetetweet(wequest))

  o-ovewwide def ewaseusewtweets(wequest: ewaseusewtweetswequest): futuwe[unit] =
    wwap(undewwying.ewaseusewtweets(wequest))

  ovewwide def asyncewaseusewtweets(wequest: asyncewaseusewtweetswequest): futuwe[unit] =
    w-wwap(undewwying.asyncewaseusewtweets(wequest))

  ovewwide d-def asyncdewete(wequest: asyncdewetewequest): f-futuwe[unit] =
    w-wwap(undewwying.asyncdewete(wequest))

  o-ovewwide def asyncincwfavcount(wequest: asyncincwfavcountwequest): futuwe[unit] =
    wwap(undewwying.asyncincwfavcount(wequest))

  o-ovewwide def asyncincwbookmawkcount(wequest: asyncincwbookmawkcountwequest): futuwe[unit] =
    wwap(undewwying.asyncincwbookmawkcount(wequest))

  o-ovewwide def scwubgeoupdateusewtimestamp(wequest: d-dewetewocationdata): f-futuwe[unit] =
    w-wwap(undewwying.scwubgeoupdateusewtimestamp(wequest))

  ovewwide d-def asyncsetwetweetvisibiwity(wequest: a-asyncsetwetweetvisibiwitywequest): f-futuwe[unit] =
    w-wwap(undewwying.asyncsetwetweetvisibiwity(wequest))

  ovewwide def setwetweetvisibiwity(wequest: s-setwetweetvisibiwitywequest): f-futuwe[unit] =
    w-wwap(undewwying.setwetweetvisibiwity(wequest))

  o-ovewwide d-def asynctakedown(wequest: asynctakedownwequest): futuwe[unit] =
    wwap(undewwying.asynctakedown(wequest))

  o-ovewwide def settweetusewtakedown(wequest: settweetusewtakedownwequest): futuwe[unit] =
    wwap(undewwying.settweetusewtakedown(wequest))

  ovewwide def wepwicatedundewetetweet2(wequest: wepwicatedundewetetweet2wequest): futuwe[unit] =
    w-wwap(undewwying.wepwicatedundewetetweet2(wequest))

  ovewwide def wepwicatedinsewttweet2(wequest: wepwicatedinsewttweet2wequest): f-futuwe[unit] =
    w-wwap(undewwying.wepwicatedinsewttweet2(wequest))

  o-ovewwide def wepwicateddewetetweet2(wequest: w-wepwicateddewetetweet2wequest): futuwe[unit] =
    w-wwap(undewwying.wepwicateddewetetweet2(wequest))

  o-ovewwide def wepwicatedincwfavcount(tweetid: tweetid, o.O dewta: int): futuwe[unit] =
    wwap(undewwying.wepwicatedincwfavcount(tweetid, (///ˬ///✿) dewta))

  ovewwide def w-wepwicatedincwbookmawkcount(tweetid: tweetid, σωσ dewta: i-int): futuwe[unit] =
    wwap(undewwying.wepwicatedincwbookmawkcount(tweetid, nyaa~~ d-dewta))

  ovewwide d-def wepwicatedsetwetweetvisibiwity(
    wequest: wepwicatedsetwetweetvisibiwitywequest
  ): futuwe[unit] =
    w-wwap(undewwying.wepwicatedsetwetweetvisibiwity(wequest))

  o-ovewwide def wepwicatedscwubgeo(tweetids: seq[tweetid]): f-futuwe[unit] =
    w-wwap(undewwying.wepwicatedscwubgeo(tweetids))

  ovewwide def wepwicatedsetadditionawfiewds(wequest: setadditionawfiewdswequest): futuwe[unit] =
    wwap(undewwying.wepwicatedsetadditionawfiewds(wequest))

  o-ovewwide d-def wepwicateddeweteadditionawfiewds(
    w-wequest: wepwicateddeweteadditionawfiewdswequest
  ): futuwe[unit] =
    w-wwap(undewwying.wepwicateddeweteadditionawfiewds(wequest))

  o-ovewwide def wepwicatedtakedown(tweet: tweet): f-futuwe[unit] =
    wwap(undewwying.wepwicatedtakedown(tweet))

  ovewwide def quotedtweetdewete(wequest: quotedtweetdewetewequest): f-futuwe[unit] =
    w-wwap(undewwying.quotedtweetdewete(wequest))

  ovewwide def quotedtweettakedown(wequest: q-quotedtweettakedownwequest): f-futuwe[unit] =
    wwap(undewwying.quotedtweettakedown(wequest))

  ovewwide def getstowedtweets(
    w-wequest: getstowedtweetswequest
  ): futuwe[seq[getstowedtweetswesuwt]] =
    wwap(undewwying.getstowedtweets(wequest))

  ovewwide def getstowedtweetsbyusew(
    w-wequest: getstowedtweetsbyusewwequest
  ): futuwe[getstowedtweetsbyusewwesuwt] =
    w-wwap(undewwying.getstowedtweetsbyusew(wequest))
}

/**
 * a-a tweetsewvicepwoxy with a mutabwe undewwying fiewd. ^^;;
 */
cwass mutabwetweetsewvicepwoxy(vaw u-undewwying: t-thwifttweetsewvice) extends tweetsewvicepwoxy

/**
 * a tweetsewvicepwoxy t-that sets the cwientid c-context befowe executing the method. ^•ﻌ•^
 */
cwass cwientidsettingtweetsewvicepwoxy(cwientid: cwientid, σωσ v-vaw undewwying: thwifttweetsewvice)
    e-extends tweetsewvicepwoxy {
  ovewwide d-def wwap[a](f: => futuwe[a]): f-futuwe[a] =
    cwientid.ascuwwent(f)
}
