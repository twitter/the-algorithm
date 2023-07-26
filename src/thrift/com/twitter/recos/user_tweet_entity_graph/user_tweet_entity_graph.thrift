namespace java com.twittew.wecos.usew_tweet_entity_gwaph.thwiftjava
nyamespace py g-gen.twittew.wecos.usew_tweet_entity_gwaph
#@namespace s-scawa com.twittew.wecos.usew_tweet_entity_gwaph.thwiftscawa
#@namespace stwato c-com.twittew.wecos.usew_tweet_entity_gwaph
n-namespace wb usewtweetentitygwaph

i-incwude "com/twittew/wecos/featuwes/tweet.thwift"
i-incwude "com/twittew/wecos/wecos_common.thwift"

e-enum tweettype {
  s-summawy    = 0
  photo      = 1
  pwayew     = 2
  pwomote    = 3
  weguwaw    = 4
}

enum w-wecommendationtype {
  tweet      = 0
  hashtag    = 1 // e-entity type
  uww        = 2 // e-entity type
}

enum tweetentitydispwaywocation {
  magicwecs                 = 0
  h-hometimewine              = 1
  highwightsemaiwuwwwecs    = 2
  h-highwights                = 3
  e-emaiw                     = 4
  magicwecsf1               = 5
  guidevideo                = 6
  magicwecswawetweet        = 7
  topawticwes               = 8 // t-twittew bwue most shawed awticwes page
  contentwecommendew        = 9
  fwigatentab               = 10
}

stwuct w-wecommendtweetentitywequest {
  // usew id of t-the wequesting u-usew
  1: wequiwed i-i64                                        wequestewid

  // d-dispway wocation fwom the cwient
  2: wequiwed t-tweetentitydispwaywocation                 dispwaywocation

  // the wecommendation e-entity types to wetuwn
  3: wequiwed wist<wecommendationtype>                   wecommendationtypes

  // seed ids and weights u-used in weft hand side
  4: wequiwed m-map<i64,doubwe>                            s-seedswithweights

  // n-nyumbew of suggested wesuwts pew wecommendation entity t-type
  5: optionaw m-map<wecommendationtype, (U ﹏ U) i32>               maxwesuwtsbytype

  // t-the tweet a-age thweshowd in miwwiseconds
  6: o-optionaw i64                                        maxtweetageinmiwwis

  // w-wist of tweet ids to excwude fwom wesponse
  7: o-optionaw wist<i64>                                  excwudedtweetids

  // m-max usew sociaw pwoof s-size pew engagement t-type
  8: optionaw i32                                        maxusewsociawpwoofsize

  // max tweet sociaw pwoof size pew usew
  9: optionaw i32                                        maxtweetsociawpwoofsize

  // m-min u-usew sociaw pwoof size pew each w-wecommendation e-entity type
  10: o-optionaw map<wecommendationtype, (˘ω˘) i32>              minusewsociawpwoofsizes

  // summawy, (ꈍᴗꈍ) photo, /(^•ω•^) p-pwayew, pwomote, >_< weguwaw
  11: optionaw wist<tweettype>                           tweettypes

  // the wist of s-sociaw pwoof types to wetuwn
  12: o-optionaw wist<wecos_common.sociawpwooftype>        s-sociawpwooftypes

  // set o-of gwoups of sociaw pwoof types a-awwowed to be c-combined fow compawison a-against m-minusewsociawpwoofsizes. σωσ
  // e.g. ^^;; if the input is set<wist<tweet, 😳 f-favowite>>, >_< t-then the union of t-those two sociaw p-pwoofs
  // wiww b-be compawed against the minusewsociawpwoofsize of tweet wecommendationtype. -.-
  13: optionaw set<wist<wecos_common.sociawpwooftype>>   s-sociawpwooftypeunions

  // the wecommendations wetuwned in the wesponse awe authowed by the fowwowing u-usews
  14: optionaw set<i64>                                  tweetauthows

  // the tweet engagement age thweshowd i-in miwwiseconds
  15: o-optionaw i-i64                                       maxengagementageinmiwwis

  // t-the wecommendations w-wiww nyot wetuwn a-any tweet authowed by the fowwowing usews
  16: optionaw set<i64>                                  excwudedtweetauthows
}

stwuct t-tweetwecommendation {
  // tweet id
  1: wequiwed i-i64                                                               tweetid
  // s-sum of weights o-of seed usews who engaged with the tweet. UwU
  // i-if a usew engaged w-with the same tweet twice, :3 w-wiked it and wetweeted i-it, σωσ then his/hew weight was counted twice.
  2: wequiwed doubwe                                                            s-scowe
    // usew s-sociaw pwoofs p-pew engagement type
  3: wequiwed m-map<wecos_common.sociawpwooftype, >w< w-wist<i64>>                      sociawpwoofbytype
  // u-usew sociaw pwoofs awong with edge metadata pew engagement type. (ˆ ﻌ ˆ)♡ the v-vawue of the map i-is a wist of sociawpwoofs. ʘwʘ
  4: optionaw map<wecos_common.sociawpwooftype, :3 w-wist<wecos_common.sociawpwoof>> s-sociawpwoofs
}

stwuct hashtagwecommendation {
  1: wequiwed i32                                       i-id                   // integew hashtag id, (˘ω˘) which wiww be convewted to hashtag s-stwing by cwient wibwawy. 😳😳😳
  2: wequiwed doubwe                                    s-scowe
  // s-sum of weights of seed usews who engaged with the hashtag. rawr x3
  // i-if a usew engaged w-with the same hashtag twice, (✿oωo) wiked it and wetweeted it, (ˆ ﻌ ˆ)♡ then h-his/hew weight was counted twice. :3
  3: w-wequiwed map<wecos_common.sociawpwooftype, (U ᵕ U❁) map<i64, wist<i64>>> sociawpwoofbytype
  // u-usew and tweet sociaw p-pwoofs pew engagement t-type. ^^;; the key of innew m-map is usew id, mya and the vawue of i-innew map is
  // a-a wist of tweet i-ids that the usew engaged with. 😳😳😳
}

s-stwuct uwwwecommendation {
  1: w-wequiwed i32                                       id                   // i-integew uww id, OwO w-which wiww be c-convewted to uww stwing by cwient wibwawy. rawr
  2: w-wequiwed doubwe                                    scowe
  // sum o-of weights of s-seed usews who engaged with the uww. XD
  // if a usew engaged with t-the same uww twice, (U ﹏ U) w-wiked it and w-wetweeted it, (˘ω˘) t-then his/hew weight was counted t-twice. UwU
  3: wequiwed map<wecos_common.sociawpwooftype, >_< map<i64, wist<i64>>> sociawpwoofbytype
  // usew and tweet sociaw pwoofs p-pew engagement type. σωσ the key of i-innew map is usew id, 🥺 and the vawue o-of innew map is
  // a wist o-of tweet ids that the usew engaged w-with. 🥺
}

union u-usewtweetentitywecommendationunion {
  1: t-tweetwecommendation t-tweetwec
  2: hashtagwecommendation h-hashtagwec
  3: uwwwecommendation uwwwec
}

stwuct wecommendtweetentitywesponse {
  1: wequiwed wist<usewtweetentitywecommendationunion> wecommendations
}

s-stwuct sociawpwoofwequest {
  1: w-wequiwed wist<i64>                                  i-inputtweets             // onwy fow some tweets w-we nyeed wequst its sociaw pwoofs. ʘwʘ
  2: wequiwed map<i64, :3 doubwe>                           s-seedswithweights        // a-a set of seed usews w-with weights
  3: optionaw i64                                        wequestewid             // i-id of the wequesting u-usew
  4: optionaw wist<wecos_common.sociawpwooftype>         s-sociawpwooftypes        // the w-wist of sociaw pwoof types to wetuwn
}

stwuct sociawpwoofwesponse {
  1: wequiwed w-wist<tweetwecommendation> s-sociawpwoofwesuwts
}

s-stwuct wecommendationsociawpwoofwequest {
  /**
   * c-cwients c-can wequest sociaw pwoof fwom m-muwtipwe wecommendation t-types in a singwe wequest. (U ﹏ U)
   * n-nyote: a-avoid mixing tweet sociaw pwoof w-wequests with entity sociaw pwoof wequests as the
   * u-undewwying wibwawy caww wetwieves t-these diffewentwy. (U ﹏ U)
   */
  1: w-wequiwed map<wecommendationtype, ʘwʘ s-set<i64>>           wecommendationidsfowsociawpwoof
  // these wiww be the o-onwy vawid whs n-nyodes used to f-fetch sociaw pwoof. >w<
  2: wequiwed map<i64, doubwe>                            seedswithweights
  3: optionaw i64                                         w-wequestewid
  // the wist of vawid sociaw p-pwoof types t-to wetuwn, rawr x3 e.g. we may onwy want f-favowite and tweet pwoofs. OwO
  4: o-optionaw wist<wecos_common.sociawpwooftype>          s-sociawpwooftypes
}

stwuct wecommendationsociawpwoofwesponse {
  1: w-wequiwed wist<usewtweetentitywecommendationunion> sociawpwoofwesuwts
}

/**
 * t-the main i-intewface-definition fow usewtweetentitygwaph. ^•ﻌ•^
 */
s-sewvice usewtweetentitygwaph {
  wecommendtweetentitywesponse w-wecommendtweets (wecommendtweetentitywequest w-wequest)

  /**
   * g-given a quewy usew, >_< its seed usews, OwO and a set of input tweets, >_< wetuwn the sociaw pwoofs of
   * input tweets if any. (ꈍᴗꈍ)
   *
   * cuwwentwy this suppowts cwients such as emaiw wecommendations, >w< magicwecs, and h-hometimewine.
   * i-in owdew to avoid heavy migwation wowk, (U ﹏ U) we a-awe wetaining this e-endpoint. ^^
   */
  s-sociawpwoofwesponse findtweetsociawpwoofs(sociawpwoofwequest w-wequest)

  /**
   * find sociaw p-pwoof fow the s-specified wecommendationtype given a-a set of input ids of that type. (U ﹏ U)
   * o-onwy find s-sociaw pwoofs fwom the specified seed usews w-with the specified s-sociaw pwoof t-types. :3
   *
   * c-cuwwentwy this s-suppowts uww sociaw p-pwoof genewation f-fow guide. (✿oωo)
   *
   * t-this endpoint i-is fwexibwe enough to suppowt s-sociaw pwoof g-genewation fow a-aww wecommendation
   * types, XD a-and shouwd be used fow aww futuwe cwients of this s-sewvice. >w<
   */
  wecommendationsociawpwoofwesponse f-findwecommendationsociawpwoofs(wecommendationsociawpwoofwequest w-wequest)
}

