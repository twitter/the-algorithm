namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace p-py gen.twittew.tweetypie.tweet
n-nyamespace w-wb tweetypie
// s-specific nyamespace t-to avoid gowang ciwcuwaw impowt
nyamespace go tweetypie.tweet

incwude "com/twittew/eschewbiwd/tweet_annotation.thwift"
i-incwude "com/twittew/expandodo/cawds.thwift"
incwude "com/twittew/content-heawth/toxicwepwyfiwtew/fiwtewed_wepwy_detaiws.thwift"
incwude "com/twittew/datapwoducts/enwichments_pwofiwegeo.thwift"
incwude "com/twittew/geoduck/pubwic/thwiftv1/geoduck_common.thwift"
i-incwude "com/twittew/mediasewvices/commons/mediacommon.thwift"
incwude "com/twittew/mediasewvices/commons/mediainfowmation.thwift"
i-incwude "com/twittew/tweetypie/api_fiewds.thwift"
incwude "com/twittew/tweetypie/edit_contwow.thwift"
incwude "com/twittew/tweetypie/media_entity.thwift"
incwude "com/twittew/tweetypie/note_tweet.thwift"
i-incwude "com/twittew/sewvice/scawecwow/gen/tiewed_actions.thwift"
incwude "com/twittew/spam/wtf/safety_wabew.thwift"
i-incwude "com/twittew/timewines/sewf_thwead/sewf_thwead.thwift"
i-incwude "com/twittew/tseng/withhowding/withhowding.thwift"
incwude "com/twittew/tweet_pivots/tweet_pivots.thwift"
incwude "com/twittew/tweetypie/geo/tweet_wocation_info.thwift"
incwude "com/twittew/tweetypie/media/media_wef.thwift"
incwude "unified_cawds_contwact.thwift"
i-incwude "com/twittew/tweetypie/cweative-entity-enwichments/cweative_entity_enwichments.thwift"
incwude "com/twittew/tweetypie/unmentions/unmentions.thwift"

/**
 * ids awe annotated with theiw cowwesponding s-space fow stwato. 😳😳😳
 */

/**
 * a wepwy is data a-about a tweet i-in wesponse to a-anothew tweet ow a-a
 * usew. σωσ
 *
 * this stwuct wiww be pwesent if:
 * 1. t-this tweet is a wepwy to anothew tweet, -.- o-ow
 * 2. 🥺 this tweet is diwected at a usew (the tweet's text begins with
 *    an @mention). >w<
 */
stwuct wepwy {
  /**
   * t-the id of the tweet that t-this tweet is w-wepwying to.
   *
   * t-this fiewd wiww be missing fow diwected-at tweets (tweets w-whose
   * text b-begins with an @mention) that a-awe nyot wepwying t-to anothew
   * tweet. (///ˬ///✿)
   */
  1: o-optionaw i64 in_wepwy_to_status_id (stwato.space = "tweet", UwU s-stwato.name = "inwepwytostatus", ( ͡o ω ͡o ) pewsonawdatatype = 'tweetid', (ˆ ﻌ ˆ)♡ tweeteditawwowed='fawse')

  /**
   * the usew to w-whom this tweet is diwected.
   *
   * i-if in_wepwy_to_status_id is set, ^^;; this fiewd i-is the authow o-of that tweet. (U ᵕ U❁)
   * if in_wepwy_to_status_id is nyot set, XD this fiewd is the usew mentioned at
   * the beginning of the tweet. (ꈍᴗꈍ)
   */
  2: i-i64 i-in_wepwy_to_usew_id (stwato.space = "usew", -.- stwato.name = "inwepwytousew", >_< p-pewsonawdatatype = 'usewid')

  /**
   * t-the cuwwent u-usewname of in_wepwy_to_usew_id. (ˆ ﻌ ˆ)♡
   *
   * this fiewd is nyot set when gizmoduck w-wetuwns a faiwuwe to tweetypie. ( ͡o ω ͡o )
   */
  3: optionaw stwing in_wepwy_to_scween_name (pewsonawdatatype = 'usewname')
}(pewsisted='twue', rawr x3 haspewsonawdata = 'twue')

/**
 * i-incwudes infowmation about t-the usew a t-tweet is diwected a-at (when a tweet
 * begins with @mention). òωó
 *
 * t-tweets with a d-diwectedatusew a-awe dewivewed to u-usews who fowwow both the
 * authow and the diwectedatusew. 😳 n-nyowmawwy t-the diwectedatusew w-wiww be t-the same
 * as w-wepwy.in_wepwy_to_usew_id, (ˆ ﻌ ˆ)♡ but wiww be diffewent if the tweet's a-authow
 * weawwanges the @mentions in a wepwy.
 */
stwuct diwectedatusew {
  1: i64 usew_id (stwato.space = "usew", 🥺 stwato.name = "usew", ^^ p-pewsonawdatatype = 'usewid')
  2: stwing scween_name (pewsonawdatatype = 'usewname')
}(pewsisted='twue', /(^•ω•^) haspewsonawdata = 'twue')

/**
 * a-a shawe is d-data about the s-souwce tweet of a wetweet. o.O
 *
 * s-shawe was the intewnaw nyame fow t-the wetweet featuwe. òωó
 */
s-stwuct shawe {
  /**
   * the id of the owiginaw tweet that was wetweeted. XD
   *
   * this is awways a t-tweet and nyevew a wetweet (unwike p-pawent_status_id). rawr x3
   */
  1: i64 souwce_status_id (stwato.space = "tweet", (˘ω˘) s-stwato.name = "souwcestatus", :3 p-pewsonawdatatype = 'tweetid')

  /*
   * the usew id of the owiginaw t-tweet's authow. (U ᵕ U❁)
   */
  2: i-i64 souwce_usew_id (stwato.space = "usew", rawr s-stwato.name = "souwceusew", OwO p-pewsonawdatatype = 'usewid')

  /**
   * the id of the tweet that the usew wetweeted. ʘwʘ
   *
   * o-often this i-is the same as souwce_status_id, XD b-but it is diffewent when a
   * u-usew wetweets via a-anothew wetweet. rawr x3 fow exampwe, OwO u-usew a posts tweet id 1, nyaa~~
   * usew b wetweets it, ʘwʘ cweating tweet 2. nyaa~~ if usew usew c-c sees b's wetweet a-and
   * wetweets it, (U ﹏ U) the wesuwt is anothew w-wetweet of tweet i-id 1, (///ˬ///✿) with the pawent
   * status id of tweet 2. :3
   */
  3: i64 p-pawent_status_id (stwato.space = "tweet", stwato.name = "pawentstatus", (˘ω˘) pewsonawdatatype = 'tweetid')
}(pewsisted='twue', haspewsonawdata = 'twue')

/**
 * a w-wecowd mapping a showtened uww (usuawwy t.co) to a-a wong uww, 😳 and a-a pwettified
 * dispway text. 😳😳😳 this is simiwaw to data found in u-uwwentity, ʘwʘ and may w-wepwace that
 * data in the futuwe. (⑅˘꒳˘)
 */
stwuct showteneduww {
  /**
   * s-showtened t.co uww.
   */
  1: s-stwing showt_uww (pewsonawdatatype = 'showtuww')

  /**
   * owiginaw, nyaa~~ fuww-wength uww. (U ﹏ U)
   */
  2: s-stwing wong_uww (pewsonawdatatype = 'wonguww')

  /**
   * t-twuncated v-vewsion of expanded uww that d-does nyot incwude pwotocow and is
   * w-wimited to 27 c-chawactews. ʘwʘ
   */
  3: s-stwing dispway_text (pewsonawdatatype = 'wonguww')
}(pewsisted='twue', (ꈍᴗꈍ) h-haspewsonawdata = 'twue')

/**
 * a-a quotedtweet is data about a tweet wefewenced w-within anothew t-tweet. :3
 *
 * q-quotedtweet is incwuded if tweet.quotedtweetfiewd is wequested, ( ͡o ω ͡o ) a-and the
 * winked-to tweet is pubwic a-and visibwe a-at the time that the winking tweet
 * is hydwated, rawr x3 which can be d-duwing wwite-time o-ow watew aftew a-a cache-miss
 * w-wead. since winked-to tweets can b-be deweted, rawr x3 and usews can become
 * suspended, mya deactivated, nyaa~~ ow pwotected, (///ˬ///✿) the pwesence of this v-vawue is nyot a
 * guawantee that t-the quoted tweet is stiww pubwic a-and visibwe. ^^
 *
 * because a-a tweet quoting anothew tweet may n-nyot wequiwe a p-pewmawink uww in
 * t-the tweet's t-text, OwO the uwws i-in showteneduww may be usefuw to cwients that
 * wequiwe maintaining a wegacy-wendewing of the tweet's text with t-the pewmawink. :3
 * s-see showteneduww f-fow detaiws. ^^ cwients shouwd a-avoid weading pewmawink whenevew
 * possibwe and pwefew the quotedtweet's t-tweet_id a-and usew_id instead.
 *
 * we a-awways popuwate the pewmawink on tweet hydwation u-unwess thewe awe p-pawtiaw
 * hydwation ewwows ow i-innew quoted tweet i-is fiwtewed due to visibiwity wuwes. (✿oωo)
 *
 */
stwuct quotedtweet {
  1: i64 tweet_id (stwato.space = "tweet", 😳 s-stwato.name = "tweet", (///ˬ///✿) p-pewsonawdatatype = 'tweetid')
  2: i-i64 usew_id (stwato.space = "usew", (///ˬ///✿) stwato.name = "usew", (U ﹏ U) p-pewsonawdatatype = 'usewid')
  3: o-optionaw showteneduww pewmawink // u-uwws to a-access the quoted-tweet
}(pewsisted='twue', òωó haspewsonawdata = 'twue')

/**
 * a-a contwibutow is a-a usew who has access to anothew u-usew's account. :3
 */
stwuct contwibutow {
  1: i64 usew_id (stwato.space = "usew", (⑅˘꒳˘) s-stwato.name = "usew", 😳😳😳 pewsonawdatatype = 'usewid')
  2: o-optionaw s-stwing scween_name (pewsonawdatatype = 'usewname')// nyot set o-on gizmoduck faiwuwe
}(pewsisted='twue', ʘwʘ haspewsonawdata = 'twue')

s-stwuct geocoowdinates {
  1: d-doubwe watitude (pewsonawdatatype = 'gpscoowdinates')
  2: doubwe w-wongitude (pewsonawdatatype = 'gpscoowdinates')
  3: i32 geo_pwecision = 0 (pewsonawdatatype = 'gpscoowdinates')

  /**
   * whethew ow nyot make the coowdinates p-pubwic. OwO
   *
   * this pawametew is nyeeded b-because coowdinates a-awe nyot typicawwy pubwished
   * b-by the authow. >_< if fawse: a-a tweet has geo c-coowdinates shawed but nyot make
   * it pubwic. /(^•ω•^)
   */
  4: boow d-dispway = 1
}(pewsisted='twue', (˘ω˘) haspewsonawdata = 'twue')

enum pwacetype {
  u-unknown = 0
  c-countwy = 1
  admin = 2
  city = 3
  n-nyeighbowhood = 4
  poi = 5
}

e-enum pwacenametype {
  n-nyowmaw = 0
  a-abbweviation = 1
  synonym = 2
}

stwuct pwacename {
  1: stwing nyame
  2: stwing wanguage = ""
  3: pwacenametype type
  4: boow pwefewwed
}(pewsisted='twue', >w< haspewsonawdata='fawse')

/**
 * a pwace is the physicaw and powiticaw pwopewties of a w-wocation on eawth. ^•ﻌ•^
 */
s-stwuct pwace {
  /**
   * geo sewvice identifiew. ʘwʘ
   */
  1: stwing id (pewsonawdatatype = 'pubwishedpwecisewocationtweet, OwO p-pubwishedcoawsewocationtweet')

  /**
   * g-gwanuwawity o-of pwace. nyaa~~
   */
  2: pwacetype type

  /**
   * t-the nyame of this pwace c-composed with i-its pawent wocations. nyaa~~
   *
   * fow exampwe, XD the f-fuww nyame fow "bwookwyn" wouwd b-be "bwookwyn, o.O nyy". t-this
   * nyame is wetuwned in the wanguage s-specified by
   * g-gettweetoptions.wanguage_tag. òωó
   */
  3: s-stwing f-fuww_name (pewsonawdatatype = 'infewwedwocation')

  /**
   * t-the best nyame f-fow this pwace as d-detewmined by g-geoduck heuwistics. (⑅˘꒳˘)
   *
   * t-this nyame is wetuwned i-in the wanguage s-specified by
   * g-gettweetoptions.wanguage_tag. o.O
   *
   * @see com.twittew.geoduck.utiw.pwimitives.bestpwacenamematchingfiwtew
   */
  4: stwing n-nyame (pewsonawdatatype = 'pubwishedpwecisewocationtweet, (ˆ ﻌ ˆ)♡ pubwishedcoawsewocationtweet')

  /**
   * awbitwawy k-key/vawue data fwom the geoduck p-pwaceattwibutes f-fow this pwace. (⑅˘꒳˘)
   */
  5: m-map<stwing, (U ᵕ U❁) stwing> attwibutes (pewsonawdatatypekey = 'postawcode')

  7: s-set<pwacename> nyames

  /**
   * t-the iso 3166-1 awpha-2 c-code fow the countwy containing t-this pwace. >w<
   */
  9: optionaw stwing countwy_code (pewsonawdatatype = 'pubwishedcoawsewocationtweet')

  /**
   * the best nyame fow the countwy c-containing this pwace as detewmined b-by
   * g-geoduck heuwistics. OwO
   *
   * this nyame is wetuwned in the wanguage specified b-by
   * gettweetoptions.wanguage_tag. >w<
   */
  10: optionaw stwing c-countwy_name (pewsonawdatatype = 'pubwishedcoawsewocationtweet')

  /**
   * a-a simpwified powygon t-that encompasses the pwace's geometwy. ^^;;
   */
  11: o-optionaw w-wist<geocoowdinates> bounding_box

  /**
   * an u-unowdewed wist of geo sewvice identifiews fow p-pwaces that contain this
   * one f-fwom the most i-immediate pawent u-up to the countwy. >w<
   */
  12: optionaw set<stwing> c-containews (pewsonawdatatype = 'pubwishedcoawsewocationtweet')

  /**
   * a-a centwoid-wike c-coowdinate that i-is within the geometwy of the pwace. σωσ
   */
  13: o-optionaw geocoowdinates c-centwoid

  /**
   * w-weason t-this pwace i-is being suppwessed f-fwom dispway.
   *
   * t-this f-fiewd is pwesent when we pweviouswy h-had a pwace fow this id, (˘ω˘) but a-awe
   * nyow choosing nyot to h-hydwate it and i-instead pwoviding f-fake pwace metadata
   * awong with a weason fow nyot incwuding p-pwace infowmation. òωó
   */
  14: o-optionaw geoduck_common.withhewdweason w-withhewdweason
}(pewsisted='twue', (ꈍᴗꈍ) haspewsonawdata='twue')

/**
 * a uwwentity is the position a-and content o-of a t.co showtened uww in the
 * t-tweet's text. (ꈍᴗꈍ)
 *
 * i-if tawon wetuwns an ewwow to tweetypie duwing tweet hydwation, òωó t-the
 * uwwentity w-wiww be o-omitted fwom the w-wesponse. (U ᵕ U❁) uwwentities awe nyot incwuded
 * fow n-nyon-t.co-wwapped u-uwws found in owdew tweets, /(^•ω•^) fow spam and usew s-safety
 * weasons. :3
*/
stwuct uwwentity {
  /**
   * the position o-of this entity's fiwst chawactew, rawr i-in zewo-indexed u-unicode
   * code points. (ˆ ﻌ ˆ)♡
   */
  1: i-i16 fwom_index

  /**
   * t-the position aftew this entity's w-wast chawactew, ^^;; in zewo-indexed u-unicode
   * c-code points. (⑅˘꒳˘)
   */
  2: i-i16 to_index

  /**
   * s-showtened t.co uww. rawr x3
   */
  3: s-stwing uww (pewsonawdatatype = 'showtuww')

  /**
   * o-owiginaw, ʘwʘ f-fuww-wength uww. (ꈍᴗꈍ)
   *
   * this f-fiewd wiww awways be pwesent on uww entities w-wetuwned by
   * t-tweetypie; it is o-optionaw as an impwementation awtifact. /(^•ω•^)
   */
  4: optionaw stwing expanded (pewsonawdatatype = 'wonguww')

  /**
   * t-twuncated vewsion of expanded u-uww that d-does nyot incwude pwotocow and is
   * wimited to 27 c-chawactews. (✿oωo)
   *
   * this f-fiewd wiww awways b-be pwesent on u-uww entities wetuwned b-by
   * tweetypie; i-it is optionaw as an impwementation awtifact. ^^;;
   */
  5: optionaw stwing dispway (pewsonawdatatype = 'wonguww')

  6: optionaw i-i64 cwick_count (pewsonawdatatype = 'countoftweetentitiescwicked')
}(pewsisted = 'twue', (˘ω˘) haspewsonawdata = 'twue')

/**
 * a-a mentionentity is the position and content of a mention, 😳😳😳 (the "@"
 * c-chawactew fowwowed by the nyame of anothew vawid usew) in a tweet's text. ^^
 *
 * i-if gizmoduck w-wetuwns an ewwow to tweetypie d-duwing tweet hydwation that
 * mentionentity w-wiww be omitted f-fwom the wesponse.
 */
stwuct mentionentity {
  /**
   * t-the position of this entity's f-fiwst chawactew ("@"), /(^•ω•^) in zewo-indexed
   * unicode code points. >_<
   */
  1: i-i16 fwom_index

  /**
   * the position aftew this entity's w-wast chawactew, (ꈍᴗꈍ) i-in zewo-indexed u-unicode
   * code points. (ꈍᴗꈍ)
   */
  2: i16 to_index

  /**
   * c-contents of the mention without the weading "@". mya
   */
  3: stwing s-scween_name (pewsonawdatatype = 'usewname')

  /**
   * u-usew id o-of the cuwwent u-usew with the mentioned scween nyame. :3
   *
   * in the cuwwent impwementation u-usew i-id does nyot nyecessawiwy identify the
   * usew w-who was owiginawwy mentioned when the tweet w-was cweated, 😳😳😳 onwy the
   * usew who owns the mentioned s-scween nyame a-at the time of hydwation. /(^•ω•^) if a-a
   * mentioned u-usew changes theiw s-scween nyame and a second usew takes the owd
   * n-nyame, -.- this fiewd identifies the second usew. UwU
   *
   * this f-fiewd wiww awways be pwesent on mention entities wetuwned by
   * t-tweetypie; i-it is optionaw a-as an impwementation a-awtifact. (U ﹏ U)
   */
  4: o-optionaw i64 usew_id (stwato.space = "usew", ^^ s-stwato.name = "usew", 😳 pewsonawdatatype = 'usewid')

  /**
   * dispway nyame o-of the cuwwent usew with the m-mentioned scween nyame. (˘ω˘)
   *
   * see usew_id fow c-caveats about w-which usew's nyame is used hewe. /(^•ω•^) t-this fiewd
   * wiww awways be p-pwesent on mention e-entities wetuwned by tweetypie; i-it is
   * optionaw a-as an impwementation awtifact. (˘ω˘)
   */
  5: o-optionaw stwing nyame (pewsonawdatatype = 'dispwayname')

  /**
   * indicates if the usew wefewwed t-to by this mentionentity has b-been unmentioned
   * fwom the convewsation. (✿oωo)  i-if this fiewd is s-set to twue, (U ﹏ U) the f-fwomindex and toindex
   * fiewds w-wiww have a v-vawue of 0. (U ﹏ U)
   *
   * @depwecated isunmentioned i-is nyo wongew being popuwated
   */
  6: o-optionaw boow isunmentioned (pewsonawdatatype = 'contentpwivacysettings')
}(pewsisted = 'twue', (ˆ ﻌ ˆ)♡ h-haspewsonawdata = 'twue')

/**
  * a-a wist of usews that awe mentioned in the tweet and have a bwocking
  * w-wewationship w-with the tweet authow. /(^•ω•^) mentions fow these usews wiww be unwinked
  * i-in the tweet. XD
  */
stwuct b-bwockingunmentions {
  1: o-optionaw wist<i64> unmentioned_usew_ids (stwato.space = 'usew', (ˆ ﻌ ˆ)♡ stwato.name = 'usews', XD pewsonawdatatype = 'usewid')
}(pewsisted = 'twue', mya haspewsonawdata = 'twue', OwO stwato.gwaphqw.typename = 'bwockingunmentions')

/**
  * a-a wist of usews that awe mentioned in the t-tweet and have indicated they d-do nyot want
  * t-to be mentioned via theiw mention s-settings. XD mentions f-fow these u-usews wiww be unwinked
  * i-in the t-tweet by twittew o-owned and opewated cwients. ( ͡o ω ͡o )
  */
stwuct settingsunmentions {
  1: optionaw wist<i64> unmentioned_usew_ids (stwato.space = 'usew', (ꈍᴗꈍ) stwato.name = 'usews', mya p-pewsonawdatatype = 'usewid')
}(pewsisted = 'twue', 😳 haspewsonawdata = 'twue', (ˆ ﻌ ˆ)♡ s-stwato.gwaphqw.typename = 'settingsunmentions')

/**
 * a-a hashtagentity i-is the position a-and content of a-a hashtag (a tewm stawting
 * with "#") in a tweet's text. ^•ﻌ•^
 */
stwuct hashtagentity {
  /**
   * t-the position of t-this entity's fiwst chawactew ("#"), 😳😳😳 in zewo-indexed
   * unicode c-code points. (///ˬ///✿)
   */
  1: i-i16 fwom_index

  /**
   * t-the position aftew this entity's wast chawactew, 🥺 i-in zewo-indexed unicode
   * code points. ^^
   */
  2: i-i16 t-to_index

  /**
   * contents of the hashtag without t-the weading "#".
   */
  3: stwing text (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, (ˆ ﻌ ˆ)♡ p-pubwictweetentitiesandmetadata')
}(pewsisted = 'twue', mya h-haspewsonawdata = 'twue')

/**
 * a cashtagentity i-is the p-position and c-content of a cashtag (a t-tewm stawting
 * w-with "$") i-in a tweet's text.
 */
stwuct c-cashtagentity {
  /**
   * t-the position of this e-entity's fiwst chawactew, OwO in zewo-indexed unicode
   * c-code points. /(^•ω•^)
   */
  1: i16 fwom_index

  /**
   * t-the position aftew this e-entity's wast c-chawactew, /(^•ω•^) in zewo-indexed unicode
   * code points. rawr
   */
  2: i-i16 to_index

  /**
   * contents of the cashtag w-without the weading "$"
   */
  3: s-stwing text (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, XD pubwictweetentitiesandmetadata')
}(pewsisted = 'twue', ʘwʘ haspewsonawdata = 'twue')

e-enum mediatagtype {
  usew = 0
  w-wesewved_1 = 1
  wesewved_2 = 2
  w-wesewved_3 = 3
  wesewved_4 = 4
}

stwuct mediatag {
  1: m-mediatagtype t-tag_type
  2: optionaw i64 usew_id (stwato.space = "usew", :3 s-stwato.name = "usew", σωσ  p-pewsonawdatatype = 'usewid')
  3: optionaw stwing scween_name (pewsonawdatatype = 'usewname')
  4: o-optionaw s-stwing nyame (pewsonawdatatype = 'dispwayname')
}(pewsisted='twue', /(^•ω•^) h-haspewsonawdata = 'twue')

s-stwuct tweetmediatags {
  1: map<mediacommon.mediaid, (ˆ ﻌ ˆ)♡ wist<mediatag>> tag_map
}(pewsisted='twue', (U ﹏ U) haspewsonawdata = 'twue')

/**
 * a usewmention is a usew wefewence n-nyot stowed i-in the tweet text. >_<
 *
 * @depwecated w-was used o-onwy in wepwyaddwesses
 */
s-stwuct u-usewmention {
  1: i64 usew_id (stwato.space = "usew", >_< s-stwato.name = "usew", o.O pewsonawdatatype = 'usewid')
  2: o-optionaw stwing scween_name (pewsonawdatatype = 'usewname')
  3: o-optionaw stwing n-nyame (pewsonawdatatype = 'dispwayname')
}(pewsisted='twue', (ꈍᴗꈍ) haspewsonawdata = 'twue')

/**
 * wepwyaddwesses is a wist of wepwy e-entities which awe stowed outside of the
 * text. /(^•ω•^)
 *
 * @depwecated
 */
s-stwuct wepwyaddwesses {
  1: w-wist<usewmention> u-usews = []
}(pewsisted='twue', OwO haspewsonawdata = 'twue')

/**
 * s-scheduwinginfo i-is metadata a-about tweets cweated by the t-tweet scheduwing
 * s-sewvice. σωσ
 */
//
stwuct scheduwinginfo {
  /**
   * i-id of the cowwesponding s-scheduwed tweet b-befowe it was cweated a-as a weaw
   * tweet. XD
   */
  1: i-i64 scheduwed_tweet_id (pewsonawdatatype = 'tweetid')
}(pewsisted='twue', rawr x3 haspewsonawdata = 'twue')

/**
 * @depwecated
 */
enum suggesttype {
  w-wtf_cawd    =  0
  wowwd_cup   =  1
  wtd_cawd    =  2
  nyews_cawd   =  3
  wesewved_4  =  4
  wesewved_5  =  5
  wesewved_6  =  6
  wesewved_7  =  7
  wesewved_8  =  8
  w-wesewved_9  =  9
  wesewved_10 = 10
  wesewved_11 = 11
}

/**
 * @depwecated
 */
enum twittewsuggestsvisibiwitytype {
  /**
   * awways pubwic to evewyone
   */
  pubwic = 1

  /**
   * inhewits v-visibiwity wuwes of pewsonawized_fow_usew_id.
   */
  westwicted = 2

  /**
   * o-onwy visibwe to pewsonawized_fow_usew_id (and a-authow). (ˆ ﻌ ˆ)♡
   */
  pwivate = 3
}

/**
 * twittewsuggestinfo i-is detaiws about a synthetic tweet g-genewated by an eawwy
 * vewsion o-of twittew s-suggests.
 *
 * @depwecated
 */
stwuct twittewsuggestinfo {
  1: suggesttype suggest_type
  2: twittewsuggestsvisibiwitytype v-visibiwity_type
  3: optionaw i64 pewsonawized_fow_usew_id (stwato.space = "usew", XD stwato.name = "pewsonawizedfowusew", (˘ω˘) pewsonawdatatype = 'usewid')
  4: o-optionaw i64 dispway_timestamp_secs (pewsonawdatatype = 'pubwictimestamp')
}(pewsisted='twue', h-haspewsonawdata = 'twue')

/**
 * a devicesouwce c-contains infowmation about t-the cwient appwication f-fwom which
 * a tweet was sent. mya
 *
 * this i-infowmation is stowed in passbiwd. ^^ the devewopew t-that owns a cwient
 * appwication pwovides this infowmation on https://apps.twittew.com. (U ᵕ U❁)
 */
s-stwuct devicesouwce {

  /**
   * t-the id of the cwient in the n-nyow depwecated d-device_souwces mysqw tabwe. rawr x3
   *
   * t-today this vawue wiww awways be 0. (ˆ ﻌ ˆ)♡
   *
   * @depwecated use cwient_app_id
   */
  1: wequiwed i-i64 id (pewsonawdatatype = 'appid')

  /**
   * i-identifiew fow the cwient in t-the fowmat "oauth:<cwient_app_id>"
   */
  2: s-stwing pawametew

  /**
   * identifiew f-fow the cwient in the fowmat "oauth:<cwient_app_id>"
   */
  3: stwing intewnaw_name

  /**
   * d-devewopew-pwovided nyame of the cwient a-appwication. (U ﹏ U)
   */
  4: s-stwing nyame

  /**
   * devewopew-pwovided pubwicwy accessibwe h-home page fow the cwient
   * appwication. mya
   */
  5: stwing uww

  /**
   * htmw fwagment with a wink to the cwient-pwovided u-uww
   */
  6: s-stwing dispway

  /**
   * this fiewd is mawked o-optionaw fow b-backwawds compatibiwity but wiww a-awways
   * be popuwated by tweetypie. OwO
   */
  7: optionaw i64 cwient_app_id (pewsonawdatatype = 'appid')
}(pewsisted='twue', (ꈍᴗꈍ) haspewsonawdata = 'twue')

/**
 * a nyawwowcast w-westwicts dewivewy of a tweet geogwaphicawwy. XD
 *
 * nyawwowcasts awwow muwti-nationaw advewtisews t-to cweate geo-wewevant c-content
 * f-fwom a centwaw handwe that is onwy dewivewed to to fowwowews i-in a
 * pawticuwaw c-countwy ow s-set of countwies. 🥺
 */
stwuct nyawwowcast {
  2: w-wist<stwing> wocation = [] (pewsonawdatatype = 'pubwishedcoawsewocationtweet')
}(pewsisted='twue', 😳😳😳 haspewsonawdata = 'twue')

/**
 * s-statuscounts is a summawy of e-engagement metwics fow a tweet. >w<
 *
 * t-these metwics awe woaded fwom tfwock.
 */
s-stwuct statuscounts {

  /**
   * nyumbew of times t-this tweet h-has been wetweeted. nyaa~~
   *
   * this n-nyumbew may nyot m-match the wist of usews who h-have wetweeted because it
   * incwudes w-wetweets fwom pwotected a-and suspended usews w-who awe nyot wisted. :3
   */
  1: optionaw i64 w-wetweet_count (pewsonawdatatype = 'countofpwivatewetweets, UwU countofpubwicwetweets', (✿oωo) stwato.json.numbews.type = 'int53')

  /**
   * nyumbew of diwect wepwies to this tweet. OwO
   *
   * this nyumbew does nyot incwude w-wepwies to wepwies. ʘwʘ
   */
  2: optionaw i64 w-wepwy_count (pewsonawdatatype = 'countofpwivatewepwies, XD countofpubwicwepwies', (ˆ ﻌ ˆ)♡ s-stwato.json.numbews.type = 'int53')

  /**
   * nyumbew of favowites this tweet h-has weceived. σωσ
   *
   * this nyumbew may nyot match t-the wist of usews who have favowited a tweet
   * b-because it incwudes favowites fwom pwotected a-and suspended usews who awe
   * not wisted. rawr x3
   */
  3: o-optionaw i-i64 favowite_count (pewsonawdatatype = 'countofpwivatewikes, rawr countofpubwicwikes', 🥺 stwato.json.numbews.type = 'int53')

  /**
   * @depwecated
   */
  4: o-optionaw i-i64 unique_usews_impwessed_count (stwato.json.numbews.type = 'int53')

  /**
   * nyumbew o-of wepwies to this t-tweet incwuding wepwies to wepwies. :3
   *
   * @depwecated
   */
  5: optionaw i-i64 descendent_wepwy_count (pewsonawdatatype = 'countofpwivatewepwies, :3 countofpubwicwepwies', >w< stwato.json.numbews.type = 'int53')

  /**
   * nyumbew of times t-this tweet has been quote tweeted. :3
   *
   * this nyumbew may nyot m-match the wist o-of usews who h-have quote tweeted because it
   * incwudes quote tweets fwom pwotected a-and suspended usews who a-awe nyot wisted. 🥺
   */
  6: optionaw i-i64 quote_count (pewsonawdatatype = 'countofpwivatewetweets, ^^;; c-countofpubwicwetweets', rawr stwato.json.numbews.type = 'int53')

  /**
   * numbew of bookmawks this tweet has weceived. ^^
   */
  7: optionaw i64 bookmawk_count (pewsonawdatatype = 'countofpwivatewikes', mya s-stwato.json.numbews.type = 'int53')

}(pewsisted='twue', mya h-haspewsonawdata = 'twue', (U ﹏ U) stwato.gwaphqw.typename='statuscounts')

/**
 * a is a-a tweet's pwopewties fwom one usew's point of view. ( ͡o ω ͡o )
 */
s-stwuct s-statuspewspective {
  1: i-i64 usew_id (stwato.space = "usew", 🥺 s-stwato.name = "usew", σωσ p-pewsonawdatatype = 'usewid')

  /**
   * w-whethew usew_id has favowited this tweet. (///ˬ///✿)
   */
  2: b-boow favowited

  /**
   * w-whethew u-usew_id has w-wetweeted this tweet. (⑅˘꒳˘)
   */
  3: b-boow wetweeted

  /**
   * i-if usew_id has wetweeted t-this tweet, OwO w-wetweet_id identifies t-that tweet. ^^
   */
  4: optionaw i64 wetweet_id (stwato.space = "tweet", rawr stwato.name = "wetweet", XD p-pewsonawdatatype = 'tweetid')

  /**
   * whethew usew_id has wepowted this t-tweet as spam, ( ͡o ω ͡o ) offensive, 😳😳😳 ow othewwise
   * o-objectionabwe. (ˆ ﻌ ˆ)♡
   */
  5: b-boow wepowted

  /**
   * whethew usew_id has bookmawked this tweet. mya
   */
  6: o-optionaw b-boow bookmawked
}(pewsisted='twue', ( ͡o ω ͡o ) haspewsonawdata = 'twue')

/**
 * a-a wanguage i-is a guess about the human wanguage of a tweet's text. ^^
 *
 * w-wanguage is detewmined b-by twittewwanguageidentifiew fwom the
 * com.twittew.common.text p-package (commonwy c-cawwed "penguin"). OwO
 */
stwuct wanguage {
  /**
   * wanguage code in b-bcp-47 fowmat. 😳
   */
  1: wequiwed stwing wanguage (pewsonawdatatype = 'infewwedwanguage')

  /**
   * wanguage diwection. /(^•ω•^)
   */
  2: boow wight_to_weft

  /**
   * c-confidence wevew of the detected wanguage. >w<
   */
  3: d-doubwe c-confidence = 1.0

  /**
   * othew p-possibwe wanguages and theiw c-confidence wevews. >w<
   */
  4: o-optionaw map<stwing, (✿oωo) d-doubwe> othew_candidates
}(pewsisted='twue', h-haspewsonawdata = 'twue')

/**
 * a-a suppwementawwanguage is a guess about the h-human wanguage of a-a tweet's
 * text. (///ˬ///✿)
 *
 * s-suppwementawwanguage is typicawwy detewmined b-by a thiwd-pawty t-twanswation
 * s-sewvice. (ꈍᴗꈍ) it is onwy stowed w-when the sewvice d-detects a diffewent w-wanguage
 * t-than twittewwanguageidentifiew. /(^•ω•^)
 *
 * @depwecated 2020-07-08 n-nyo wongew popuwated. (✿oωo)
 */
stwuct s-suppwementawwanguage {
  /**
   * wanguage code i-in bcp-47 fowmat.
   */
  1: wequiwed s-stwing wanguage (pewsonawdatatype = 'infewwedwanguage')
}(pewsisted='twue', nyaa~~ haspewsonawdata = 'twue')

/**
 * a spamwabew is a cowwection o-of spam actions f-fow a tweet. (ꈍᴗꈍ)
 *
 * absence of a-a spamwabew indicates t-that no action nyeeds to be taken
 */
stwuct s-spamwabew {
  /**
   * f-fiwtew t-this content at w-wendew-time
   *
   * @depwecated 2014-05-19 u-use f-fiwtew_wendews
   */
  1: boow spam = 0

  2: o-optionaw set<tiewed_actions.tiewedactionwesuwt> actions;
}(pewsisted='twue')


/**
 * the avaiwabwe types of spam signaw
 *
 * @depwecated
 */
enum s-spamsignawtype {
  m-mention           = 1
  seawch            = 2
  stweaming         = 4
  # obsowete home_timewine = 3
  # obsowete nyotification  = 5
  # o-obsowete convewsation  = 6
  # obsowete c-cweation      = 7
  wesewved_vawue_8  = 8
  wesewved_vawue_9  = 9
  w-wesewved_vawue_10 = 10
}

/**
 * @depwecated
 * cawdbindingvawues i-is a-a cowwection of k-key-vawue paiws used to wendew a cawd. o.O
 */
stwuct cawdbindingvawues {
  1: w-wist<cawds.cawd2immediatevawue> paiws = []
}(pewsisted='twue')

/**
 * a-a cawdwefewence is a mechanism f-fow expwicitwy associating a cawd with a
 * tweet. ^^;;
 */
s-stwuct cawdwefewence {
  /**
   * w-wink to the cawd to associate with a t-tweet. σωσ
   *
   * this uwi may wefewence e-eithew a cawd stowed in the cawd sewvice, òωó ow
   * anothew wesouwce, (ꈍᴗꈍ) such as a cwawwed web page uww. ʘwʘ this v-vawue supewcedes
   * a-any uww pwesent i-in tweet t-text.
   */
  1: stwing cawd_uwi
}(pewsisted='twue')

/**
 * a tweetpivot i-is a semantic entity wewated to a tweet. ^^;;
 *
 * tweetpivots a-awe used to d-diwect to the usew t-to anothew wewated w-wocation. mya fow
 * exampwe, XD a "see mowe about <name>" ui ewement that takes t-the usew to <uww>
 * w-when cwicked. /(^•ω•^)
 */
stwuct tweetpivot {
  1: wequiwed tweet_annotation.tweetentityannotation annotation
  2: w-wequiwed tweet_pivots.tweetpivotdata data
}(pewsisted='twue')

s-stwuct tweetpivots {
  1: w-wequiwed w-wist<tweetpivot> tweet_pivots
}(pewsisted='twue')

stwuct eschewbiwdentityannotations {
  1: wist<tweet_annotation.tweetentityannotation> entity_annotations
}(pewsisted='twue')

stwuct textwange {
  /**
   * t-the incwusive index of the stawt o-of the wange, nyaa~~ in zewo-indexed unicode
   * code points.
   */
  1: w-wequiwed i32 fwom_index

  /**
   * t-the excwusive index of the end of the w-wange, (U ᵕ U❁) in zewo-indexed u-unicode
   * c-code points.
   */
  2: w-wequiwed i-i32 to_index
}(pewsisted='twue')

stwuct tweetcowedata {
  1: i-i64 usew_id (stwato.space = "usew", òωó s-stwato.name = "usew", σωσ pewsonawdatatype = 'usewid', ^^;; t-tweeteditawwowed='fawse')

  /**
   * the body of the tweet consisting o-of the usew-suppwied dispwayabwe m-message
   * a-and:
   * - an optionaw pwefix wist o-of @mentions
   * - a-an optionaw suffix attachment uww. (˘ω˘)
   *
   * the indices f-fwom visibwe_text_wange s-specify t-the substwing of t-text indended
   * to be dispwayed, whose wength is wimited to 140 d-dispway chawactews. òωó  nyote
   * that the visibwe s-substwing may be wongew than 140 chawactews d-due to htmw
   * entity encoding of &, UwU <, and > . 😳😳😳

   * fow wetweets t-the text is that of the owiginaw t-tweet, (⑅˘꒳˘) pwepended w-with "wt
   * @usewname: " a-and twuncated to 140 chawactews. nyaa~~
   */
  2: s-stwing text (pewsonawdatatype = 'pwivatetweets, :3 p-pubwictweets')

  /**
   * the cwient f-fwom which t-this tweet was c-cweated
   *
   * t-the fowmat of this vawue is oauth:<cwient i-id>. nyaa~~
   */
  3: s-stwing c-cweated_via (pewsonawdatatype = 'cwienttype')

  /**
   * time t-this tweet was cweated. :3
   *
   * this vawue is seconds since the unix epoch. :3 fow tweets with s-snowfwake ids
   * t-this vawue is wedundant, ^•ﻌ•^ since a-a miwwisecond-pwecision timestamp is pawt
   * o-of the id.
   */
  4: i-i64 cweated_at_secs

  /**
   * p-pwesent when t-this tweet is a wepwy to anothew t-tweet ow anothew usew. o.O
   */
  5: optionaw w-wepwy wepwy

  /**
   * p-pwesent when a tweet begins with an @mention ow has metadata i-indicating the diwected-at u-usew. -.-
   */
  6: optionaw diwectedatusew diwected_at_usew

  /**
   * p-pwesent when this tweet is a-a wetweet. 🥺
   */
  7: optionaw shawe shawe

  /**
   * w-whethew thewe is a takedown c-countwy code ow takedown weason s-set fow this s-specific tweet. :3
   *
   * see takedown_countwy_codes fow the countwies w-whewe the takedown is active. /(^•ω•^)  (depwecated)
   * see takedown_weasons f-fow a-a wist of weasons w-why the tweet is taken down. 😳😳😳
   *
   * has_takedown wiww be set to twue if eithew this specific t-tweet ow the authow has a
   * takedown active. (✿oωo)
   */
  8: boow h-has_takedown = 0

  /**
   * w-whethew this tweet might be nyot-safe-fow-wowk, nyaa~~ judged by the tweet a-authow. (˘ω˘)
   *
   * u-usews can fwag theiw own accounts as nyot-safe-fow-wowk in account
   * pwefewences b-by sewecting "mawk media i-i tweet as containing matewiaw that
   * may b-be sensitive" and e-each tweet cweated aftew that p-point wiww have
   * t-this fwag set. rawr x3
   *
   * the v-vawue can awso be updated aftew t-tweet cweate t-time via the
   * u-update_possibwy_sensitive_tweet m-method. 🥺
   */
  9: b-boow nysfw_usew = 0

  /**
   * whethew this t-tweet might be n-not-safe-fow-wowk, (ˆ ﻌ ˆ)♡ judged by an intewnaw twittew
   * s-suppowt agent. XD
   *
   * this tweet vawue o-owiginates fwom the usew's nysfw_admin fwag at
   * tweet cweate time but can be updated aftewwawds using the
   * u-update_possibwy_sensitive_tweet method.
   */
  10: b-boow nysfw_admin = 0

  /**
   * when nyuwwcast i-is twue a-a tweet is nyot dewivewed to a usew's f-fowwowews, (˘ω˘) nyot
   * shown i-in the usew's timewine, UwU and does n-nyot appeaw in seawch wesuwts. (U ᵕ U❁)
   *
   * this is pwimawiwy used to cweate tweets that can be used as ads without
   * b-bwoadcasting them to an advewtisew's fowwowews. :3
   */
  11: b-boow nyuwwcast = 0 (tweeteditawwowed='fawse')

  /**
   * nyawwowcast w-wimits dewivewy of a tweet to fowwowews in specific geogwaphic
   * wegions. :3
   */
  12: optionaw nyawwowcast nyawwowcast (tweeteditawwowed='fawse')

  /**
   * the impwession id of t-the ad fwom which t-this tweet was c-cweated. ^•ﻌ•^
   *
   * this is set w-when a usew wetweets o-ow wepwies t-to a pwomoted tweet. 🥺 it is
   * used to attwibute t-the "eawned" exposuwe o-of an advewtisement. /(^•ω•^)
   */
  13: optionaw i-i64 twacking_id (pewsonawdatatype = 'impwessionid', σωσ t-tweeteditawwowed='fawse')

  /**
   * a-a shawed i-identifiew a-among aww the tweets in the wepwy c-chain fow a singwe
   * t-tweet. >_<
   *
   * t-the convewsation i-id is t-the id of the t-tweet that stawted t-the convewsation. (ꈍᴗꈍ)
   */
  14: o-optionaw i64 convewsation_id (stwato.space = "tweet", (⑅˘꒳˘) s-stwato.name = "convewsation", >_< p-pewsonawdatatype = 'tweetid')

  /**
   * whethew this tweet has media of any type. (U ﹏ U)
   *
   * m-media can be in the fowm of media e-entities, ʘwʘ media cawds, rawr x3 ow uwws in the
   * t-tweet text that w-wink to media pawtnews. ^•ﻌ•^
   *
   * @see m-mediaindexhewpew
   */
  15: optionaw boow h-has_media

  /**
   * s-suppowted fow wegacy cwients to associate a wocation with a tweet. (✿oωo)
   *
   * twittew owned c-cwients must use pwace_id west api pawam fow geo-tagging. (///ˬ///✿)
   *
   * @depwecated u-use pwace_id w-west api pawam
   */
  16: optionaw g-geocoowdinates c-coowdinates (pewsonawdatatype = 'gpscoowdinates', (⑅˘꒳˘) t-tweeteditawwowed='fawse')

  /**
   * t-the wocation w-whewe a t-tweet was sent fwom. ( ͡o ω ͡o )
   *
   * pwace i-is eithew pubwished in api wequest expwicitwy o-ow impwicitwy wevewse
   * geocoded f-fwom api wat/won coowdinates p-pawams. XD
   *
   * t-tweetypie impwementation nyotes:
   *  - cuwwentwy, :3 i-if both pwace_id and coowdinates awe specified, (⑅˘꒳˘) c-coowdinates
   *    t-takes p-pwecedence in g-geo-tagging. 😳 i.e.: pwace wetuwned w-wgc(coowdinates)
   *    s-sets t-the pwace_id fiewd. -.-
   *  - pwace_id i-is wevewse geocoded on wwite-path. (U ﹏ U)
   */
  17: optionaw stwing pwace_id (pewsonawdatatype = 'pubwishedpwecisewocationtweet, (U ﹏ U) pubwishedcoawsewocationtweet')
}(pewsisted='twue', /(^•ω•^) haspewsonawdata = 'twue', tweeteditawwowed='fawse')

/**
 * wist of community id's the tweet b-bewongs to. >_<
 */
s-stwuct communities {
  1: wequiwed wist<i64> community_ids (pewsonawdatatype = 'engagementid')
}(pewsisted='twue')

/**
 * tweet m-metadata that i-is pwesent on extended tweets, (˘ω˘) a tweet whose totaw text wength i-is gweatew
 * than t-the cwassic wimit of 140 chawactews. (U ᵕ U❁)
 */
s-stwuct e-extendedtweetmetadata {
  /**
   * @depwecated was dispway_count
   */
  1: i-i32 unused1 = 0

  /**
   * the i-index, rawr in unicode c-code points, (U ﹏ U) at which the tweet text shouwd be twuncated
   * f-fow wendewing in a-a pubwic api backwawds-compatibwe m-mode. ʘwʘ  once twuncated t-to this
   * point, (ꈍᴗꈍ) the t-text shouwd be a-appended with an e-ewwipsis, (U ᵕ U❁) a space, :3 a-and the showt_uww
   * fwom sewf_pewmawink. (ꈍᴗꈍ)  t-the wesuwting text m-must confowm to the 140 dispway gwyph
   * wimit.
   */
  2: wequiwed i32 api_compatibwe_twuncation_index

  /**
   * @depwecated was defauwt_dispway_twuncation_index
   */
  3: i-i32 unused3 = 0

  /**
   * @depwecated w-was is_wong_fowm
   */
  4: b-boow unused4 = 0

  /**
   * @depwecated was pweview_wange
   */
  5: optionaw textwange unused5

  /**
   * @depwecated w-was extended_pweview_wange
   */
  6: o-optionaw t-textwange unused6
}(pewsisted='twue')

/**
 * @depwecated use t-twansientcweatecontext i-instead
 */
enum tweetcweatecontextkey {
  pewiscope_is_wive    = 0, nyaa~~
  p-pewiscope_cweatow_id = 1
}

/**
 * d-diwectedatusewmetadata i-is a tweetypie-intewnaw s-stwuctuwe that can b-be used to stowe m-metadata about
 * a diwected-at usew on the tweet. ^•ﻌ•^
 *
 * nyote: absence of this fiewd does nyot i-impwy the tweet does nyot have a-a diwectedatusew, σωσ s-see
 * tweet.diwectedatusewmetadata fow mowe infowmation. (˘ω˘)
 */
stwuct diwectedatusewmetadata {
  /**
   * i-id o-of the usew a tweet is diwected-at. ^•ﻌ•^
   */
  1: o-optionaw i64 usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', σωσ haspewsonawdata = 'twue')

/**
 * t-tweet metadata that may be pwesent on tweets in a sewf-thwead (tweetstowm). ^^;;
 *
 * a-a sewf-thwead is a twee of sewf-wepwies that may eithew:
 * 1. begin a-as a wepwy to anothew u-usew's tweet (cawwed a-a nyon-woot s-sewf-thwead) ow
 * 2. 😳 stand awone (cawwed w-woot sewf-thwead). /(^•ω•^)
 *
 * nyote t-that nyot aww sewf-thweads have sewfthweadmetadata. ( ͡o ω ͡o )
 */
s-stwuct sewfthweadmetadata {
  /**
   * a s-shawed identifiew a-among aww the tweets in the sewf-thwead (tweetstowm). ^^
   *
   * the tweetstowm i-id is the id of the tweet that stawted the sewf thwead. /(^•ω•^)
   *
   * if the id matches the tweet's convewsation_id t-then it is a woot s-sewf-thwead, ^^ othewwise it is
   * a nyon-woot sewf-thwead. 😳
   */
  1: wequiwed i64 id (pewsonawdatatype = 'tweetid')

  /**
   * i-indicates if the tweet with this sewfthweadmetadata i-is a weaf i-in the sewf-thwead t-twee. 😳
   * t-this fwag might be used to encouwage the authow to extend theiw tweetstowm at the end.
   */
  2: b-boow isweaf = 0
}(pewsisted='twue', òωó h-haspewsonawdata = 'twue')

/**
 * c-composew f-fwow used to cweate this tweet. nyaa~~ u-unwess using the news camewa (go/newscamewa)
 * f-fwow, (///ˬ///✿) this shouwd be `standawd`. mya
 *
 * when set to `camewa`, ^•ﻌ•^ cwients a-awe expected t-to dispway the t-tweet with a d-diffewent ui
 * to emphasize attached m-media. XD
 */
e-enum composewsouwce {
  standawd = 1
  camewa = 2
}


/**
 * the c-convewsation ownew a-and usews in invited_usew_ids can wepwy
 **/
stwuct convewsationcontwowbyinvitation {
  1: w-wequiwed wist<i64> invited_usew_ids (pewsonawdatatype = 'usewid')
  2: w-wequiwed i-i64 convewsation_tweet_authow_id (pewsonawdatatype = 'usewid')
  3: o-optionaw boow invite_via_mention
}(pewsisted='twue', (⑅˘꒳˘) haspewsonawdata = 'twue')

/**
 * the convewsation ownew, -.- usews in invited_usew_ids, ^^ a-and usews who the c-convewsation ownew fowwows can wepwy
 **/
stwuct c-convewsationcontwowcommunity {
  1: wequiwed wist<i64> i-invited_usew_ids (pewsonawdatatype = 'usewid')
  2: w-wequiwed i-i64 convewsation_tweet_authow_id (pewsonawdatatype = 'usewid')
  3: o-optionaw b-boow invite_via_mention
}(pewsisted='twue', rawr haspewsonawdata = 'twue')

/**
 * t-the convewsation ownew, o.O usews in invited_usew_ids, >w< and usews who fowwows the convewsation o-ownew can wepwy
 **/
stwuct convewsationcontwowfowwowews {
 1: w-wequiwed w-wist<i64> invited_usew_ids (pewsonawdatatype = 'usewid')
 2: wequiwed i-i64 convewsation_tweet_authow_id (pewsonawdatatype = 'usewid')
 3: optionaw boow invite_via_mention
}(pewsisted='twue', σωσ haspewsonawdata = 'twue')

/**
* this tweet metadata c-captuwes westwictions o-on who i-is awwowed to w-wepwy in a convewsation. rawr
*/
union convewsationcontwow {

  1: convewsationcontwowcommunity community

  2: convewsationcontwowbyinvitation b-byinvitation

  3: convewsationcontwowfowwowews fowwowews
}(pewsisted='twue', (U ﹏ U) h-haspewsonawdata = 'twue')

// t-this tweet m-metadata shows the excwusivity o-of a tweet and is used to detewmine
// whethew wepwies / visibiwity of a tweet is wimited
stwuct excwusivetweetcontwow {
  1: wequiwed i64 convewsation_authow_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', (˘ω˘) haspewsonawdata = 'twue')

/**
 * tweet metadata f-fow a twusted fwiends tweet. 😳
 *
 * a twusted f-fwiends tweet i-is a tweet whose visibiwity is w-westwicted to membews
 * o-of an authow-specified wist. XD
 *
 * wepwies to a twusted f-fwiends tweet wiww i-inhewit a copy of this metadata fwom
 * the w-woot tweet. ʘwʘ
 */
s-stwuct twustedfwiendscontwow {
  /**
   * t-the id o-of the twusted fwiends wist whose m-membews can view this tweet.
   */
  1: wequiwed i-i64 twusted_fwiends_wist_id (pewsonawdatatype = 'twustedfwiendswistmetadata')
}(pewsisted='twue', /(^•ω•^) h-haspewsonawdata = 'twue')

enum cowwabinvitationstatus {
  p-pending = 0
  accepted = 1
  w-wejected = 2
}

/**
 * wepwesents a usew who has been invited to cowwabowate on a c-cowwabtweet, UwU associated with whethew
 * t-they have accepted ow wejected c-cowwabowation
 */
stwuct invitedcowwabowatow {
  1: w-wequiwed i64 cowwabowatow_usew_id (pewsonawdatatype = 'usewid')
  2: wequiwed cowwabinvitationstatus cowwab_invitation_status
}(pewsisted='twue', UwU h-haspewsonawdata='twue')

/**
 * pwesent i-if tweet is a-a cowwabinvitation a-awaiting pubwishing, ^•ﻌ•^ stowes wist of invited c-cowwabowatows
 */
s-stwuct cowwabinvitation {
  1: w-wequiwed wist<invitedcowwabowatow> i-invited_cowwabowatows
}(pewsisted='twue', (ꈍᴗꈍ) haspewsonawdata='twue')

/**
 * p-pwesent i-if tweet is a-a pubwished cowwabtweet, ^^ s-stowes w-wist of cowwabowatows
 */
stwuct cowwabtweet {
  1: w-wequiwed wist<i64> c-cowwabowatow_usew_ids (pewsonawdatatype = 'usewid')
}(pewsisted='twue', XD haspewsonawdata='twue')

/**
 * cowwabtweets tweat m-muwtipwe usews a-as co-authows o-ow "cowwabowatows" of a singwe "cowwab t-tweet". UwU
 *
 * w-when cweating a cowwab tweet, ^^ t-the owiginaw a-authow wiww begin by cweating a c-cowwabinvitation which
 * is sent t-to anothew cowwabowatow t-to accept o-ow weject cowwabowation. :3 i-if and when othew
 * cowwabowatows have accepted, (U ﹏ U) t-the cowwabinvitation is wepwaced b-by a cowwabtweet which is pubwished
 * p-pubwicwy a-and fanned out to fowwowews of a-aww cowwabowatows. UwU a-a cowwabinvitation wiww be hidden fwom
 * anyone e-except the wist o-of cowwabowatows using vf. 🥺 the cowwabtweet wiww then be fanned out wike
 * a weguwaw tweet to the pwofiwes and combined audiences of aww cowwabowatows. (✿oωo)
 *
 * a tweet wepwesenting a cowwabtweet o-ow cowwabinvitation i-is denoted b-by the pwesence o-of a
 * cowwabcontwow fiewd on a tweet. 😳😳😳
 */
u-union cowwabcontwow {
  1: c-cowwabinvitation c-cowwab_invitation
  2: c-cowwabtweet cowwab_tweet
}(pewsisted='twue', (⑅˘꒳˘) haspewsonawdata='twue')

/**
 * a tweet is a message that bewongs to a twittew usew. mya
 *
 * t-the tweet s-stwuct wepwaces t-the depwecated s-status stwuct. OwO aww fiewds except
 * i-id awe optionaw. /(^•ω•^)
 *
 * this stwuct suppowts the additionaw fiewds fwexibwe schema. 😳😳😳 additionaw f-fiewds awe
 * defined stawting f-fwom fiewd 101. ^^;;
 *
 * t-the guidewines fow adding a nyew additionaw fiewd:
 * 1. ( ͡o ω ͡o ) i-it's wequiwed to define the a-additionaw fiewd as an optionaw stwuct. ^•ﻌ•^
 *    inside t-the stwuct, OwO define optionaw ow non-optionaw f-fiewd(s) accowding
 *    to youw n-nyeeds. rawr
 * 2. if you have sevewaw i-immutabwe piece o-of data that awe awways accessed
 *    togethew, nyaa~~ you shouwd d-define them in the same stwuct fow bettew stowage
 *    wocawity. 🥺
 * 3. if youw data modew has sevewaw mutabwe pieces, OwO a-and diffewent p-piece can
 *    be updated i-in a cwose succession, ^•ﻌ•^ you shouwd g-gwoup them into
 *    s-sepawate s-stwucts and each stwuct contains one mutabwe piece. (ˆ ﻌ ˆ)♡
 */
s-stwuct tweet {
  /**
  * the pwimawy key fow a tweet. /(^•ω•^)
  *
  * a tweet's i-id is assigned b-by the tweet sewvice a-at cweation t-time. ʘwʘ since
  * 2010-11-04 tweet i-ids have been genewated using s-snowfwake. ʘwʘ pwiow t-to this
  * ids wewe assigned sequentiawwy by mysqw a-autoincwement. :3
  */
  1: i-i64 i-id (pewsonawdatatype = 'tweetid')

  /**
   * t-the essentiaw pwopewties o-of a tweet. ^^
   *
   * this fiewd wiww awways be pwesent o-on tweets wetuwned b-by tweetypie. :3 i-it is
   * mawked optionaw so an empty tweet can be pwovided to w-wwite additionaw
   * f-fiewds. 🥺
   */
  2: o-optionaw tweetcowedata c-cowe_data

  /**
   * uwws extwacted f-fwom the t-tweet's text. :3
   */
  3: o-optionaw wist<uwwentity> uwws

  /**
   * m-mentions extwacted fwom the tweet's text. rawr
   */
  4: o-optionaw wist<mentionentity> mentions

  /**
   * hashtags e-extwacted fwom the tweet's text. UwU
   */
  5: optionaw w-wist<hashtagentity> hashtags

  /**
   * c-cashtags extwacted f-fwom the tweet's t-text
   */
  6: o-optionaw wist<cashtagentity> cashtags

  7: optionaw wist<media_entity.mediaentity> m-media

  /**
   * pwace identified by tweet.cowe_data.pwace_id. ^•ﻌ•^
   */
  10: optionaw pwace pwace

  11: o-optionaw quotedtweet q-quoted_tweet

  /**
   * the w-wist of countwies w-whewe this t-tweet wiww nyot be shown. (U ﹏ U)
   *
   * t-this fiewd contains c-countwies fow both the tweet and the usew, (ˆ ﻌ ˆ)♡ so it may
   * c-contain vawues even if has_takedown is fawse. 😳
   *
   * @depwecated, >w< u-use fiewd 30 takedown_weasons w-which incwudes the same infowmation and mowe
   */
  12: o-optionaw wist<stwing> t-takedown_countwy_codes (pewsonawdatatype = 'contentwestwictionstatus')

  /**
   * intewaction m-metwics fow this t-tweet. 🥺
   *
   * i-incwuded when one of gettweetoptions.woad_wetweet_count, 😳
   * gettweetoptions.woad_wepwy_count, nyaa~~ ow gettweetoptions.woad_favowite_count
   * is set. (˘ω˘) this can be missing in a pawtiaw wesponse i-if the tfwock wequest
   * faiws. mya
   */
  13: optionaw statuscounts c-counts

  /**
   * pwopewties o-of the cwient f-fwom which the tweet was sent. òωó
   *
   * t-this c-can be missing in a pawtiaw wesponse if the passbiwd wequest faiws. (U ﹏ U)
   */
  14: o-optionaw devicesouwce device_souwce

  /**
   * p-pwopewties of this tweet fwom the point of view o-of
   * gettweetoptions.fow_usew_id. (U ﹏ U)
   *
   * this fiewd is incwuded o-onwy when fow_usew_id is p-pwovided and
   * i-incwude_pewspective == twue this can be missing in a pawtiaw wesponse if
   * t-the timewine sewvice w-wequest faiws.
   */
  15: o-optionaw statuspewspective pewspective

  /**
   * vewsion 1 cawds. >_<
   *
   * t-this fiewd is incwuded o-onwy when gettweetoptions.incwude_cawds == twue. nyaa~~
   */
  16: o-optionaw wist<cawds.cawd> cawds

  /**
   * vewsion 2 c-cawds. 😳😳😳
   *
   * this fiewd i-is incwuded o-onwy incwuded when gettweetoptions.incwude_cawds
   * == twue and gettweetoptions.cawds_pwatfowm_key is set to vawid v-vawue. nyaa~~
   */
  17: optionaw cawds.cawd2 cawd2

  /**
   * human w-wanguage of t-tweet text as detewmined b-by twittewwanguageidentifiew. -.-
   */
  18: optionaw wanguage w-wanguage

  /**
   * @depwecated
   */
  19: optionaw map<spamsignawtype, 😳😳😳 spamwabew> spam_wabews

  /**
   * u-usew wesponsibwe fow cweating t-this tweet when i-it is nyot the s-same as the
   * cowe_data.usew_id.
   *
   * t-this i-is sensitive i-infowmation and m-must nyot be shawed extewnawwy (via u-ui, ^•ﻌ•^
   * api, UwU ow stweaming) e-except to the the o-ownew of the tweet
   * (cowe_data.usew_id) ow a contwibutow to the ownew's account. (ˆ ﻌ ˆ)♡
   */
  20: optionaw contwibutow c-contwibutow

  // obsowete 21: optionaw wist<topicwabew> t-topic_wabews

  22: o-optionaw enwichments_pwofiwegeo.pwofiwegeoenwichment pwofiwe_geo_enwichment

  // maps extension nyame to vawue; onwy popuwated if the wequest contained an e-extension on tweets. XD
  // o-obsowete 24: o-optionaw m-map<stwing, (⑅˘꒳˘) binawy> e-extensions

  /**
   * d-depwecated. /(^•ω•^)
   * semantic e-entities that awe wewated t-to this tweet. (U ᵕ U❁)
   */
  25: optionaw t-tweetpivots tweet_pivots

  /**
   * @depwecated
   * s-stwato t-tweet extensions s-suppowt has moved t-to biwdhewd. ʘwʘ
   *
   * i-intewnaw thwift cwients shouwd quewy s-stwato cowumns diwectwy and
   * nyot wewy upon ext/*.tweet cowumns w-which awe designed to sewve
   * cwient apis. OwO
   */
  26: o-optionaw b-binawy extensions_wepwy

  /**
   * has the w-wequesting usew muted the convewsation w-wefewwed t-to by
   * `convewsation_id`? when this fiewd i-is absent, (✿oωo) the convewsation may
   * o-ow may nyot b-be muted. (///ˬ///✿) use the `incwude_convewsation_muted` f-fiewd in
   * gettweetoptions to wequest this fiewd. (✿oωo)
   *
   * if this fiewd has a vawue, σωσ the vawue a-appwies to the usew in the
   * `fow_usew_id` f-fiewd of the wequesting `gettweetoptions`. ʘwʘ
   */
  27: optionaw b-boow convewsation_muted

  /**
   * the usew i-id of the tweet wefewenced by convewsation_id
   *
   * @depwecated w-was convewsation_ownew_id. 😳😳😳 this was nyevew impwemented. ^•ﻌ•^
   */
  28: o-optionaw i64 unused28

  /**
   * h-has this tweet been wemoved fwom its convewsation b-by the c-convewsation o-ownew?
   *
   * @depwecated w-was i-is_wemoved_fwom_convewsation. (˘ω˘) this w-was nyevew impwemented. (U ﹏ U)
   */
  29: optionaw b-boow unused29

  /**
   * a-a wist o-of takedown weasons indicating w-which countwy and weason this tweet was taken down. >w<
   */
  30: o-optionaw wist<withhowding.takedownweason> t-takedown_weasons

  /**
   * @obsowete, XD sewf-thwead metadata is nyow s-stowed in fiewd 151, XD s-sewf_thwead_metadata
   */
  31: optionaw sewf_thwead.sewfthweadinfo s-sewf_thwead_info

  // f-fiewd 32 to 99 a-awe wesewved
  // f-fiewd 100 is used fow fwexibwe schema pwoof of concept
  // additionaw fiewds
  // these fiewds awe stowed in m-manhattan fwexibwe schema
  101: o-optionaw tweetmediatags media_tags
  102: o-optionaw scheduwinginfo s-scheduwing_info

  /**
   * @depwecated
   */
  103: o-optionaw cawdbindingvawues b-binding_vawues

  /**
   * @depwecated
   */
  104: o-optionaw wepwyaddwesses wepwy_addwesses

  /**
   * obsowete, (U ﹏ U) b-but owiginawwy contained infowmation about s-synthetic tweets cweated by the f-fiwst
   * vewsion o-of twittew suggests. (✿oωo)
   *
   * @depwecated
   */
  105: o-optionaw twittewsuggestinfo o-obsowete_twittew_suggest_info

  106: optionaw eschewbiwdentityannotations eschewbiwd_entity_annotations (pewsonawdatatype = 'annotationvawue')

  // @depwecated 2021-07-19
  107: o-optionaw safety_wabew.safetywabew spam_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  108: optionaw safety_wabew.safetywabew abusive_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  109: optionaw safety_wabew.safetywabew wow_quawity_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  110: optionaw safety_wabew.safetywabew n-nysfw_high_pwecision_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  111: o-optionaw safety_wabew.safetywabew n-nysfw_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  112: optionaw s-safety_wabew.safetywabew abusive_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  113: optionaw safety_wabew.safetywabew w-wow_quawity_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  114: o-optionaw safety_wabew.safetywabew pewsona_non_gwata_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  115: o-optionaw s-safety_wabew.safetywabew w-wecommendations_wow_quawity_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  116: o-optionaw safety_wabew.safetywabew expewimentation_wabew (pewsonawdatatype = 'tweetsafetywabews')

  117: o-optionaw tweet_wocation_info.tweetwocationinfo tweet_wocation_info
  118: optionaw cawdwefewence c-cawd_wefewence

  /**
   * @depwecated 2020-07-08 nyo wongew popuwated. ^^;;
   */
  119: optionaw suppwementawwanguage suppwementaw_wanguage

  // f-fiewd 120, (U ﹏ U) additionaw_media_metadata, OwO is depwecated. 😳😳😳
  // fiewd 121, 😳😳😳 media_metadatas, (✿oωo) i-is depwecated

  // u-undew c-cewtain ciwcumstances, UwU incwuding wong fowm tweets, mya w-we cweate and s-stowe a sewf-pewmawink
  // to t-this tweet. rawr x3 in the case of a wong-fowm tweet, /(^•ω•^) t-this wiww be used in a twuncated v-vewsion
  // of the tweet text. >_<
  122: optionaw showteneduww sewf_pewmawink

  // m-metadata that is pwesent on extended t-tweets. :3
  123: optionaw e-extendedtweetmetadata e-extended_tweet_metadata

  // obsowete 124: c-cwosspost_destinations.cwosspostdestinations cwosspost_destinations

  // communities associated w-with a tweet
  125: optionaw communities communities (pewsonawdatatype = 'pwivatetweetentitiesandmetadata', o.O tweeteditawwowed='fawse')

  // if some text at the b-beginning ow end of the tweet shouwd be hidden, UwU t-then this
  // fiewd indicates t-the wange of text t-that shouwd be shown in cwients. (ꈍᴗꈍ)
  126: o-optionaw textwange visibwe_text_wange

  // @depwecated 2021-07-19
  127: o-optionaw safety_wabew.safetywabew spam_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  128: optionaw s-safety_wabew.safetywabew d-dupwicate_content_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  129: optionaw safety_wabew.safetywabew w-wive_wow_quawity_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  130: o-optionaw safety_wabew.safetywabew nysfa_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  131: o-optionaw safety_wabew.safetywabew pdna_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  132: optionaw safety_wabew.safetywabew seawch_bwackwist_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  133: optionaw s-safety_wabew.safetywabew wow_quawity_mention_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  134: optionaw safety_wabew.safetywabew bystandew_abusive_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  135: o-optionaw s-safety_wabew.safetywabew a-automation_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  136: optionaw safety_wabew.safetywabew g-gowe_and_viowence_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  137: o-optionaw safety_wabew.safetywabew u-untwusted_uww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  138: optionaw s-safety_wabew.safetywabew g-gowe_and_viowence_high_wecaww_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  139: optionaw safety_wabew.safetywabew nysfw_video_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  140: optionaw s-safety_wabew.safetywabew nysfw_neaw_pewfect_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  141: o-optionaw safety_wabew.safetywabew automation_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  142: optionaw safety_wabew.safetywabew n-nysfw_cawd_image_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // @depwecated 2021-07-19
  143: optionaw s-safety_wabew.safetywabew d-dupwicate_mention_wabew (pewsonawdatatype = 'tweetsafetywabews')

  // @depwecated 2021-07-19
  144: optionaw s-safety_wabew.safetywabew b-bounce_wabew (pewsonawdatatype = 'tweetsafetywabews')
  // fiewd 145 t-to 150 is wesewved fow safety w-wabews

  /**
   * if this tweet is pawt of a sewf_thwead (tweetstowm) t-then t-this vawue may be s-set. >_<
   * see s-sewfthweadmetadata f-fow detaiws. òωó
   */
  151: o-optionaw s-sewfthweadmetadata sewf_thwead_metadata
  // fiewd 152 has b-been depwecated

  // the composew used to cweate t-this tweet. (ꈍᴗꈍ) eithew via the standawd t-tweet cweatow ow the
  // camewa fwow (go/newscamewa). 😳😳😳
  //
  // nyote: this fiewd is onwy s-set if a cwient p-passed an expwicit c-composewsouwce in the posttweetwequest. ( ͡o ω ͡o )
  // nyews camewa is depwecated and w-we nyo wongew set c-composewsouwce i-in the posttweetwequest s-so nyo nyew tweets wiww
  // have this fiewd. mya
  153: optionaw composewsouwce composew_souwce

  // p-pwesent i-if wepwies a-awe westwicted, UwU see convewsationcontwow fow mowe d-detaiws
  154: optionaw convewsationcontwow convewsation_contwow

  // d-detewmines the supew fowwows w-wequiwements fow being abwe to view a tweet.
  155: optionaw e-excwusivetweetcontwow excwusive_tweet_contwow (tweeteditawwowed='fawse')

  // p-pwesent fow a twusted fwiends tweet, òωó see twustedfwiendscontwow fow mowe detaiws. -.-
  156: o-optionaw twustedfwiendscontwow twusted_fwiends_contwow (tweeteditawwowed='fawse')

  // d-data about edits and editabiwity. :3 s-see editcontwow f-fow mowe detaiws. ^•ﻌ•^
  157: optionaw edit_contwow.editcontwow edit_contwow

  // pwesent fow a cowwabtweet o-ow cowwabinvitation, (˘ω˘) see cowwabcontwow fow mowe detaiws.
  158: optionaw cowwabcontwow cowwab_contwow (tweeteditawwowed='fawse')

  // pwesent fow a 3wd-pawty d-devewopew-buiwt c-cawd. see http://go/devewopew-buiwt-cawds-pwd
  159: optionaw i64 devewopew_buiwt_cawd_id (pewsonawdatatype = 'cawdid')

  // d-data about enwichments attached t-to a tweet. 😳😳😳
  160: o-optionaw c-cweative_entity_enwichments.cweativeentityenwichments cweative_entity_enwichments_fow_tweet

  // this fiewd incwudes summed e-engagements fwom t-the pwevious tweets i-in the edit c-chain. (///ˬ///✿)
  161: optionaw statuscounts pwevious_counts

  // a wist o-of media wefewences, 🥺 i-incwuding infowmation about the souwce tweet fow pasted media. (U ᵕ U❁)
  // pwefew this fiewd to m-media_keys, (˘ω˘) as media_keys is nyot pwesent fow owd tweets ow pasted m-media tweets. UwU
  162: o-optionaw w-wist<media_wef.mediawef> m-media_wefs

  // whethew this tweet is a 'backend tweet' to be wefewenced onwy by the c-cweatives containews sewvice
  // g-go/cea-cc-integwation fow mowe d-detaiws
  163: o-optionaw boow is_cweatives_containew_backend_tweet

  /**
  * aggwegated pewspective of this tweet and aww othew vewsions fwom t-the point of view of the
  * usew s-specified in fow_usew_id. 😳
  *
  * t-this fiewd is i-incwuded onwy w-when fow_usew_id is pwovided and c-can be missing in a pawtiaw wesponse
  * if the t-timewine sewvice w-wequest faiws. :3
  */
  164: o-optionaw api_fiewds.tweetpewspective edit_pewspective

  // v-visibiwity contwows wewated t-to toxic wepwy f-fiwtewing
  // g-go/toxwf fow m-mowe detaiws
  165: optionaw fiwtewed_wepwy_detaiws.fiwtewedwepwydetaiws fiwtewed_wepwy_detaiws

  // the wist of m-mentions that have unmentioned fwom the tweet's associated convewsation
  166: optionaw unmentions.unmentiondata u-unmention_data

  /**
    * a w-wist of usews that wewe mentioned in the tweet a-and have a bwocking
    * w-wewationship w-with the a-authow. mya
    */
  167: optionaw bwockingunmentions bwocking_unmentions

  /**
      * a-a wist of usews that wewe mentioned in the t-tweet and shouwd be unmentioned
      * b-based on t-theiw mention setttings
      */
  168: o-optionaw s-settingsunmentions s-settings_unmentions

  /**
    * a-a nyote associated with this tweet. nyaa~~
    */
  169: o-optionaw nyote_tweet.notetweet n-nyote_tweet

  // fow additionaw f-fiewds, 😳😳😳 t-the nyext avaiwabwe f-fiewd id is 169. ^•ﻌ•^
  // n-nyote: w-when adding a nyew additionaw fiewd, UwU pwease awso update unwequestedfiewdscwubbew.scwubknownfiewds

  /**
   * intewnaw fiewds
   *
   * these fiewds a-awe used by t-tweetypie onwy and shouwd nyot b-be accessed extewnawwy. (ꈍᴗꈍ)
   * t-the fiewd ids awe i-in descending owdew, (⑅˘꒳˘) stawting with `32767`. OwO
   */

  /**
   * pwesent if tweet data i-is pwovided cweatives containew s-sewvice instead o-of tweetypie stowage, UwU
   * with e-encapsuwated t-tweets ow customized d-data.
   */
  32763: o-optionaw i-i64 undewwying_cweatives_containew_id

  /**
   * stowes tweetypie-intewnaw m-metadata about a d-diwectedatusew. OwO
   *
   * a tweet's diwectedatusew i-is hydwated as fowwows:
   * 1. (///ˬ///✿) if this fiewd i-is pwesent, (U ﹏ U) then diwectedatusewmetadata.usewid is the diwected-at usew
   * 2. (⑅˘꒳˘) i-if this fiewd is a-absent, /(^•ω•^) then if t-the tweet has a-a wepwy and has a mention stawting at text
   *    i-index 0 then t-that usew is the diwected-at usew. :3
   *
   * nyote: e-extewnaw cwients s-shouwd use c-cowedata.diwected_at_usew. ( ͡o ω ͡o )
   */
  32764: optionaw d-diwectedatusewmetadata d-diwected_at_usew_metadata

  // wist of takedowns that awe appwied diwectwy to the tweet
  32765: optionaw wist<withhowding.takedownweason> t-tweetypie_onwy_takedown_weasons

  // stowes the media keys used to intewact with the media pwatfowm systems. (ˆ ﻌ ˆ)♡
  // pwefew `media_wefs` w-which w-wiww awways have media data, XD unwike this fiewd which is empty f-fow
  // owdew tweets and tweets with pasted media. :3
  32766: optionaw w-wist<mediacommon.mediakey> m-media_keys

  // f-fiewd 32767 is the wist of takedowns t-that awe appwied diwectwy t-to the tweet
  32767: optionaw w-wist<stwing> tweetypie_onwy_takedown_countwy_codes (pewsonawdatatype = 'contentwestwictionstatus')


  // f-fow intewnaw f-fiewds, σωσ t-the nyext avaiwabwe fiewd id is 32765 (counting d-down)
}(pewsisted='twue', mya h-haspewsonawdata = 'twue')
