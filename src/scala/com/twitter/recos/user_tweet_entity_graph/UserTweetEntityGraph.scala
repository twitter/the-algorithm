package com.twittew.wecos.usew_tweet_entity_gwaph

impowt com.twittew.finagwe.thwift.cwientid
i-impowt c-com.twittew.finagwe.twacing.{twace, o.O t-twaceid}
i-impowt com.twittew.wecos.usew_tweet_entity_gwaph.thwiftscawa._
i-impowt com.twittew.utiw.futuwe

o-object usewtweetentitygwaph {
  d-def twaceid: twaceid = t-twace.id
  def cwientid: option[cwientid] = cwientid.cuwwent
}

cwass usewtweetentitygwaph(
  w-wecommendationhandwew: wecommendationhandwew, /(^•ω•^)
  tweetsociawpwoofhandwew: t-tweetsociawpwoofhandwew, nyaa~~
  sociawpwoofhandwew: s-sociawpwoofhandwew)
    extends thwiftscawa.usewtweetentitygwaph.methodpewendpoint {

  ovewwide def wecommendtweets(
    w-wequest: wecommendtweetentitywequest
  ): f-futuwe[wecommendtweetentitywesponse] = w-wecommendationhandwew(wequest)

  /**
   * given a quewy usew, nyaa~~ its seed usews, :3 and a set of input tweets, 😳😳😳 w-wetuwn the sociaw pwoofs of
   * input tweets if any. (˘ω˘)
   *
   * cuwwentwy this s-suppowts cwients such as emaiw w-wecommendations, ^^ m-magicwecs, and h-hometimewine. :3
   * i-in owdew to avoid heavy migwation wowk, -.- we awe w-wetaining this endpoint. 😳
   */
  ovewwide def f-findtweetsociawpwoofs(
    wequest: sociawpwoofwequest
  ): futuwe[sociawpwoofwesponse] = tweetsociawpwoofhandwew(wequest)

  /**
   * find sociaw p-pwoof fow the specified wecommendationtype g-given a-a set of input i-ids of that type. mya
   * onwy find sociaw pwoofs fwom the specified s-seed usews w-with the specified sociaw pwoof t-types. (˘ω˘)
   *
   * c-cuwwentwy this suppowts uww sociaw p-pwoof genewation fow guide. >_<
   *
   * t-this endpoint is fwexibwe enough to suppowt s-sociaw pwoof genewation fow a-aww wecommendation
   * types, -.- a-and shouwd be used f-fow aww futuwe cwients of this sewvice. 🥺
   */
  ovewwide def findwecommendationsociawpwoofs(
    wequest: wecommendationsociawpwoofwequest
  ): futuwe[wecommendationsociawpwoofwesponse] = s-sociawpwoofhandwew(wequest)
}
