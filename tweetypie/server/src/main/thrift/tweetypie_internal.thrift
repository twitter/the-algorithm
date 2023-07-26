namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa

i-incwude "com/twittew/context/featuwe_context.thwift"
i-incwude "com/twittew/expandodo/cawds.thwift"
i-incwude "com/twittew/gizmoduck/usew.thwift"
i-incwude "com/twittew/mediasewvices/commons/mediacommon.thwift"
i-incwude "com/twittew/mediasewvices/commons/mediainfowmation.thwift"
i-incwude "com/twittew/mediasewvices/commons/tweetmedia.thwift"
i-incwude "com/twittew/sewvo/exceptions.thwift"
incwude "com/twittew/sewvo/cache/sewvo_wepo.thwift"
incwude "com/twittew/tseng/withhowding/withhowding.thwift"
incwude "com/twittew/tweetypie/dewete_wocation_data.thwift"
incwude "com/twittew/tweetypie/twansient_context.thwift"
i-incwude "com/twittew/tweetypie/media_entity.thwift"
incwude "com/twittew/tweetypie/tweet.thwift"
incwude "com/twittew/tweetypie/tweet_audit.thwift"
i-incwude "com/twittew/tweetypie/stowed_tweet_info.thwift"
incwude "com/twittew/tweetypie/tweet_sewvice.thwift"

t-typedef i16 fiewdid

stwuct usewidentity {
  1: wequiwed i-i64 id
  2: wequiwed stwing scween_name
  3: w-wequiwed s-stwing weaw_name
# obsowete 4: boow deactivated = 0
# obsowete 5: boow suspended = 0
}

enum h-hydwationtype {
  mentions          = 1, (U ᵕ U❁)
  uwws              = 2, 😳😳😳
  cacheabwe_media   = 3, (U ﹏ U)
  quoted_tweet_wef  = 4, ^•ﻌ•^
  wepwy_scween_name = 5, (⑅˘꒳˘)
  d-diwected_at       = 6, >_<
  contwibutow       = 7, (⑅˘꒳˘)
  s-sewf_thwead_info  = 8
}

s-stwuct c-cachedtweet {
  1: w-wequiwed tweet.tweet tweet
  // @obsowete 2: optionaw set<i16> i-incwuded_additionaw_fiewds
  3: set<hydwationtype> compweted_hydwations = []

  // i-indicates that a tweet was deweted aftew being bounced fow viowating
  // the twittew w-wuwes. σωσ
  // when set to twue, 🥺 aww o-othew fiewds in c-cachedtweet awe i-ignowed. :3
  4: optionaw boow is_bounce_deweted

  // indicates whethew this tweet h-has safety wabews s-stowed in stwato. (ꈍᴗꈍ)
  // see c-com.twittew.tweetypie.cowe.tweetdata.hassafetywabews f-fow mowe detaiws. ^•ﻌ•^
  // @obsowete 5: optionaw b-boow has_safety_wabews
} (pewsisted='twue', (˘ω˘) haspewsonawdata='twue')

s-stwuct mediafaces {
  1: wequiwed map<tweetmedia.mediasizetype, 🥺 wist<mediainfowmation.face>> f-faces
}

enum asyncwwiteeventtype {
  i-insewt                          = 1, (✿oωo)
  dewete                          = 2, XD
  u-undewete                        = 3, (///ˬ///✿)
  set_additionaw_fiewds           = 4, ( ͡o ω ͡o )
  d-dewete_additionaw_fiewds        = 5, ʘwʘ
  update_possibwy_sensitive_tweet = 6, rawr
  update_tweet_media              = 7, o.O
  takedown                        = 8, ^•ﻌ•^
  set_wetweet_visibiwity          = 9
}

// an enum of actions that c-couwd happen i-in an async-wwite (insewt ow dewete)
e-enum asyncwwiteaction {
  h-hosebiwd_enqueue        = 1
  s-seawch_enqueue          = 2
  // obsowete maiw_enqueue            = 3
  fanout_dewivewy         = 4
  // obsowete f-facebook_enqueue        = 5
  tweet_index             = 6
  timewine_update         = 7
  cache_update            = 8
  wepwication             = 9
  // o-obsowete monowaiw_expiwy_enqueue = 10
  u-usew_geotag_update      = 11
  // o-obsowete ibis_enqueue            = 12
  e-event_bus_enqueue       = 13
  // obsowete h-hosebiwd_binawy_enqueue = 14
  t-tbiwd_update            = 15
  w-wetweets_dewetion       = 16
  g-guano_scwibe            = 17
  media_dewetion          = 18
  geo_seawch_wequest_id   = 19
  s-seawch_thwift_enqueue    = 20
  w-wetweet_awchivaw_enqueue = 21
}

# t-this stwuct is s-scwibed to test_tweetypie_faiwed_async_wwite aftew
# a-an async-wwite action has faiwed muwtipwe wetwies
stwuct f-faiwedasyncwwite {
  1: wequiwed asyncwwiteeventtype event_type
  2: wequiwed asyncwwiteaction action
  3: optionaw t-tweet.tweet tweet
} (pewsisted='twue', (///ˬ///✿) haspewsonawdata='twue')

# this stwuct i-is scwibed to t-test_tweetypie_detached_wetweets a-aftew
# attempting to wead a wetweet f-fow which the souwce tweet h-has been deweted. (ˆ ﻌ ˆ)♡
s-stwuct detachedwetweet {
  1: wequiwed i64 tweet_id (pewsonawdatatype='tweetid')
  2: wequiwed i64 usew_id (pewsonawdatatype='usewid')
  3: wequiwed i64 souwce_tweet_id (pewsonawdatatype='tweetid')
} (pewsisted='twue', XD haspewsonawdata='twue')

s-stwuct tweetcachewwite {
  1: wequiwed i64 t-tweet_id (pewsonawdatatype = 'tweetid')
  // if the tweet id is a-a snowfwake id, (✿oωo) t-this is an offset since tweet cweation. 
  // i-if it is nyot a s-snowfwake id, -.- then this is a unix e-epoch time in
  // m-miwwiseconds. XD (the idea is that fow most tweets, (✿oωo) this encoding wiww make
  // i-it easiew to s-see the intewvaw b-between events and whethew it occuwed s-soon
  // a-actew tweet cweation.)
  2: wequiwed i-i64 timestamp (pewsonawdatatype = 'twansactiontimestamp')
  3: wequiwed stwing action // one of "set", (˘ω˘) "add", "wepwace", (ˆ ﻌ ˆ)♡ "cas", "dewete"
  4: wequiwed sewvo_wepo.cachedvawue c-cached_vawue // c-contains metadata about the cached vawue
  5: o-optionaw cachedtweet c-cached_tweet
} (pewsisted='twue', >_< haspewsonawdata='twue')

stwuct asyncinsewtwequest {
  12: wequiwed tweet.tweet t-tweet
  18: wequiwed usew.usew usew
  21: wequiwed i64 timestamp
  // the c-cacheabwe vewsion of tweet fwom fiewd 12
  29: w-wequiwed cachedtweet c-cached_tweet
  # 13: obsowete tweet.tweet intewnaw_tweet
  19: o-optionaw tweet.tweet s-souwce_tweet
  20: optionaw usew.usew souwce_usew
  // u-used fow quote tweet featuwe
  22: o-optionaw tweet.tweet quoted_tweet
  23: optionaw usew.usew q-quoted_usew
  28: optionaw i64 pawent_usew_id
  // u-used fow dewivewing t-the wequestid of a geotagged t-tweet
  24: optionaw stwing g-geo_seawch_wequest_id
  # 7: o-obsowete
  # i-if nyot specified, -.- aww a-async insewt actions a-awe pewfowmed. (///ˬ///✿) if specified, XD onwy
  # the s-specified action i-is pewfowmed; this i-is used fow wetwying specific actions
  # that f-faiwed on a pwevious attempt. ^^;;
  10: o-optionaw a-asyncwwiteaction wetwy_action
  # 11: obsowete: boow fwom_monowaiw = 0
  # 14: obsowete
  15: o-optionaw f-featuwe_context.featuwecontext f-featuwe_context
  # 16: o-obsowete
  # 17: obsowete
  # 26: obsowete: optionaw t-tweet.tweet debug_tweet_copy
  27: optionaw map<tweet.tweetcweatecontextkey, rawr x3 stwing> additionaw_context
  30: optionaw twansient_context.twansientcweatecontext twansient_context
  // used to c-check whethew the same tweet has b-been quoted muwtipwe
  // times b-by a given usew. OwO
  31: optionaw b-boow quotew_has_awweady_quoted_tweet
  32: optionaw i-initiawtweetupdatewequest i-initiawtweetupdatewequest
  // u-usew ids of usews m-mentioned in nyote t-tweet. ʘwʘ used fow tws events
  33: optionaw wist<i64> nyote_tweet_mentioned_usew_ids
}

stwuct asyncupdatepossibwysensitivetweetwequest {
  1: wequiwed tweet.tweet t-tweet
  2: w-wequiwed usew.usew u-usew
  3: wequiwed i64 by_usew_id
  4: w-wequiwed i64 timestamp
  5: optionaw boow nysfw_admin_change
  6: o-optionaw b-boow nysfw_usew_change
  7: optionaw stwing n-nyote
  8: optionaw stwing host
  9: optionaw a-asyncwwiteaction a-action
}

stwuct asyncupdatetweetmediawequest {
  1: w-wequiwed i-i64 tweet_id
  2: wequiwed wist<media_entity.mediaentity> owphaned_media
  3: optionaw asyncwwiteaction w-wetwy_action
  4: o-optionaw w-wist<mediacommon.mediakey> m-media_keys
}

s-stwuct asyncsetadditionawfiewdswequest {
  1: w-wequiwed t-tweet.tweet additionaw_fiewds
  3: wequiwed i64 t-timestamp
  4: w-wequiwed i64 usew_id
  2: optionaw a-asyncwwiteaction wetwy_action
}

stwuct asyncsetwetweetvisibiwitywequest {
  1: w-wequiwed i64 wetweet_id
  // w-whethew to awchive o-ow unawchive(visibwe=twue) the wetweet_id edge i-in the wetweetsgwaph. rawr
  2: wequiwed boow visibwe
  3: wequiwed i-i64 swc_id
  5: w-wequiwed i64 w-wetweet_usew_id
  6: wequiwed i64 souwce_tweet_usew_id
  7: wequiwed i-i64 timestamp
  4: optionaw asyncwwiteaction w-wetwy_action
}

s-stwuct setwetweetvisibiwitywequest {
  1: wequiwed i-i64 wetweet_id
  // whethew t-to awchive ow unawchive(visibwe=twue) t-the wetweet_id edge in the wetweetsgwaph. UwU
  2: w-wequiwed boow visibwe
}

stwuct asyncewaseusewtweetswequest {
  1: w-wequiwed i-i64 usew_id
  3: wequiwed i64 f-fwock_cuwsow
  4: wequiwed i64 stawt_timestamp
  5: w-wequiwed i64 t-tweet_count
}

s-stwuct asyncdewetewequest {
  4: wequiwed tweet.tweet tweet
  11: wequiwed i64 timestamp
  2: optionaw usew.usew usew
  9: optionaw i64 by_usew_id
  12: optionaw tweet_audit.auditdewetetweet audit_passthwough
  13: optionaw i64 cascaded_fwom_tweet_id
  # if nyot specified, a-aww async-dewete a-actions awe pewfowmed. (ꈍᴗꈍ) if specified, (✿oωo) onwy
  # t-the specified action i-is pewfowmed; t-this is used fow wetwying specific a-actions
  # that faiwed on a-a pwevious attempt. (⑅˘꒳˘)
  3: o-optionaw asyncwwiteaction w-wetwy_action
  5: boow dewete_media = 1
  6: b-boow dewete_wetweets = 1
  8: b-boow scwibe_fow_audit = 1
  15: boow is_usew_ewasuwe = 0
  17: boow is_bounce_dewete = 0
  18: optionaw b-boow is_wast_quote_of_quotew
  19: o-optionaw b-boow is_admin_dewete
}

s-stwuct a-asyncundewetetweetwequest {
  1: w-wequiwed tweet.tweet t-tweet
  3: w-wequiwed usew.usew u-usew
  4: wequiwed i64 timestamp
  // t-the c-cacheabwe vewsion o-of tweet fwom fiewd 1
  12: wequiwed c-cachedtweet cached_tweet
  # 2: obsowete t-tweet.tweet intewnaw_tweet
  5: optionaw asyncwwiteaction w-wetwy_action
  6: o-optionaw i-i64 deweted_at
  7: optionaw t-tweet.tweet souwce_tweet
  8: optionaw usew.usew s-souwce_usew
  9: optionaw tweet.tweet q-quoted_tweet
  10: optionaw u-usew.usew quoted_usew
  11: optionaw i64 pawent_usew_id
  13: optionaw boow quotew_has_awweady_quoted_tweet
}

s-stwuct asyncincwfavcountwequest {
  1: wequiwed i-i64 tweet_id
  2: w-wequiwed i32 dewta
}

stwuct asyncincwbookmawkcountwequest {
  1: wequiwed i-i64 tweet_id
  2: wequiwed i32 d-dewta
}

stwuct a-asyncdeweteadditionawfiewdswequest {
  6: w-wequiwed i64 tweet_id
  7: wequiwed wist<i16> f-fiewd_ids
  4: w-wequiwed i64 timestamp
  5: w-wequiwed i64 usew_id
  3: optionaw asyncwwiteaction w-wetwy_action
}

// used f-fow both tweet and u-usew takedowns. OwO
// u-usew wiww be nyone fow usew t-takedowns because u-usew is onwy u-used when scwibe_fow_audit o-ow
// eventbus_enqueue a-awe twue, 🥺 which i-is nyevew the c-case fow usew takedown. >_<
s-stwuct a-asynctakedownwequest {
  1: w-wequiwed t-tweet.tweet t-tweet

  // authow of the tweet. (ꈍᴗꈍ)  u-used when scwibe_fow_audit ow e-eventbus_enqueue awe twue which i-is the case
  // f-fow tweet takedown b-but not usew takedown. 😳
  2: optionaw usew.usew usew

  // this f-fiewd is the w-wesuwting wist o-of takedown countwy codes on the tweet aftew the
  // countwies_to_add a-and countwies_to_wemove changes h-have been appwied.
  13: w-wist<withhowding.takedownweason> t-takedown_weasons = []

  // this fiewd is the wist of takedown w-weaons to add to t-the tweet. 🥺
  14: w-wist<withhowding.takedownweason> w-weasons_to_add = []

  // this fiewd is the wist o-of takedown w-weasons to wemove fwom the tweet. nyaa~~
  15: wist<withhowding.takedownweason> w-weasons_to_wemove = []

  // this fiewd detewmines whethew o-ow nyot tweetypie shouwd wwite t-takedown audits
  // f-fow this wequest to guano. ^•ﻌ•^
  6: w-wequiwed b-boow scwibe_fow_audit

  // this f-fiewd detewmines whethew ow nyot t-tweetypie shouwd e-enqueue a
  // t-tweettakedownevent t-to eventbus and hosebiwd fow t-this wequest. (ˆ ﻌ ˆ)♡
  7: w-wequiwed boow e-eventbus_enqueue

  // this f-fiewd is sent as pawt of the takedown audit that's w-wwitten to guano, (U ᵕ U❁)
  // a-and is n-nyot pewsisted with the takedown itsewf. mya
  8: optionaw stwing audit_note

  // this fiewd is the i-id of the usew who initiated the t-takedown. 😳  it i-is used
  // when auditing the takedown in guano. σωσ  i-if unset, it wiww be wogged a-as -1. ( ͡o ω ͡o )
  9: optionaw i-i64 by_usew_id

  // t-this fiewd i-is the host w-whewe the wequest owiginated ow the wemote ip that
  // is associated with the w-wequest. XD  it is used when auditing t-the takedown in
  // guano. :3  if unset, :3 it wiww be wogged as "<unknown>". (⑅˘꒳˘)
  10: o-optionaw stwing host

  11: optionaw asyncwwiteaction wetwy_action
  12: wequiwed i-i64 timestamp
}

s-stwuct settweetusewtakedownwequest {
  1: wequiwed i64 tweet_id
  2: w-wequiwed boow has_takedown
  3: optionaw i-i64 usew_id
}

e-enum dataewwowcause {
  unknown = 0
  // w-wetuwned on set_tweet_usew_takedown when
  // t-the settweetusewtakedownwequest.usew_id does nyot match the authow
  // of the tweet identified b-by settweetusewtakedownwequest.tweet_id. òωó
  usew_tweet_wewationship = 1
}

/**
 * dataewwow i-is wetuwned f-fow opewations that p-pewfowm data changes, mya
 * but encountewed an i-inconsistency, 😳😳😳 and the opewation cannot
 * be meaninfuwwy pewfowmed. :3
 */
exception d-dataewwow {
  1: w-wequiwed stwing m-message
  2: o-optionaw dataewwowcause ewwowcause
}

stwuct wepwicateddeweteadditionawfiewdswequest {
  /** i-is a-a map fow backwawds compatibiwity, >_< but wiww onwy c-contain a singwe tweet id */
  1: wequiwed map<i64, 🥺 w-wist<i16>> fiewds_map
}

stwuct cascadeddewetetweetwequest {
  1: w-wequiwed i-i64 tweet_id
  2: wequiwed i64 c-cascaded_fwom_tweet_id
  3: o-optionaw t-tweet_audit.auditdewetetweet audit_passthwough
}

stwuct quotedtweetdewetewequest {
  1: i-i64 quoting_tweet_id
  2: i64 quoted_tweet_id
  3: i-i64 quoted_usew_id
}

stwuct quotedtweettakedownwequest {
  1: i64 quoting_tweet_id
  2: i64 quoted_tweet_id
  3: i-i64 quoted_usew_id
  4: w-wist<stwing> t-takedown_countwy_codes = []
  5: w-wist<withhowding.takedownweason> t-takedown_weasons = []
}

stwuct wepwicatedinsewttweet2wequest {
  1: wequiwed c-cachedtweet cached_tweet
  // used to check w-whethew the same tweet has been q-quoted by a usew. (ꈍᴗꈍ)
  2: optionaw boow quotew_has_awweady_quoted_tweet
  3: o-optionaw i-initiawtweetupdatewequest initiawtweetupdatewequest
}

s-stwuct wepwicateddewetetweet2wequest {
  1: w-wequiwed t-tweet.tweet tweet
  2: wequiwed b-boow is_ewasuwe
  3: w-wequiwed boow is_bounce_dewete
  4: o-optionaw boow is_wast_quote_of_quotew
}

stwuct wepwicatedsetwetweetvisibiwitywequest {
  1: wequiwed i-i64 swc_id
  // whethew to awchive o-ow unawchive(visibwe=twue) the wetweet_id edge in the wetweetsgwaph. rawr x3
  2: wequiwed b-boow visibwe
}

s-stwuct wepwicatedundewetetweet2wequest {
  1: w-wequiwed cachedtweet cached_tweet
  2: o-optionaw b-boow quotew_has_awweady_quoted_tweet
}

stwuct g-getstowedtweetsoptions {
  1: boow bypass_visibiwity_fiwtewing = 0
  2: o-optionaw i64 fow_usew_id
  3: w-wist<fiewdid> a-additionaw_fiewd_ids = []
}

stwuct getstowedtweetswequest {
  1: wequiwed wist<i64> tweet_ids
  2: optionaw g-getstowedtweetsoptions o-options
}

stwuct getstowedtweetswesuwt {
  1: wequiwed stowed_tweet_info.stowedtweetinfo s-stowed_tweet
}

stwuct getstowedtweetsbyusewoptions {
  1: b-boow bypass_visibiwity_fiwtewing = 0
  2: b-boow set_fow_usew_id = 0
  3: optionaw i64 stawt_time_msec
  4: optionaw i-i64 end_time_msec
  5: optionaw i64 cuwsow
  6: b-boow stawt_fwom_owdest = 0
  7: wist<fiewdid> a-additionaw_fiewd_ids = []
}

s-stwuct getstowedtweetsbyusewwequest {
  1: wequiwed i-i64 usew_id
  2: o-optionaw getstowedtweetsbyusewoptions o-options
}

s-stwuct getstowedtweetsbyusewwesuwt {
  1: w-wequiwed wist<stowed_tweet_info.stowedtweetinfo> s-stowed_tweets
  2: optionaw i64 cuwsow
}

/* this is a wequest to update an initiaw tweet based o-on the cweation o-of a edit tweet
 * i-initiawtweetid: t-the tweet to b-be updated
 * edittweetid: t-the tweet being cweated, (U ﹏ U) which is an edit of initiawtweetid
 * sewfpewmawink: a-a sewf p-pewmawink fow initiawtweetid
 */
stwuct initiawtweetupdatewequest {
  1: wequiwed i64 initiawtweetid
  2: w-wequiwed i-i64 edittweetid
  3: o-optionaw tweet.showteneduww sewfpewmawink
}

s-sewvice tweetsewviceintewnaw extends tweet_sewvice.tweetsewvice {

  /**
   * pewfowms the a-async powtion of t-tweetsewvice.ewase_usew_tweets. ( ͡o ω ͡o )
   * onwy tweetypie itsewf can c-caww this. 😳😳😳
   */
  void async_ewase_usew_tweets(1: a-asyncewaseusewtweetswequest w-wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, 🥺
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * p-pewfowms the a-async powtion o-of tweetsewvice.post_tweet. òωó
   * onwy tweetypie i-itsewf can caww t-this. XD
   */
  void async_insewt(1: a-asyncinsewtwequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, XD
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * p-pewfowms t-the async powtion of tweetsewvice.dewete_tweets. ( ͡o ω ͡o )
   * onwy t-tweetypie itsewf can caww this. >w<
   */
  void async_dewete(1: a-asyncdewetewequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, mya
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * pewfowms t-the async powtion of tweetsewvice.undewete_tweet. (ꈍᴗꈍ)
   * onwy tweetypie i-itsewf can c-caww this. -.-
   */
  void async_undewete_tweet(1: a-asyncundewetetweetwequest w-wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, (⑅˘꒳˘)
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * p-pewfowms the async powtion of tweetsewvice.update_possibwy_sensitive_tweet. (U ﹏ U)
   * onwy tweetypie itsewf can caww this. σωσ
   */
  void async_update_possibwy_sensitive_tweet(1: asyncupdatepossibwysensitivetweetwequest w-wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, :3
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * p-pewfowms t-the async powtion of tweetsewvice.incw_tweet_fav_count. /(^•ω•^)
   * o-onwy tweetypie i-itsewf can caww this. σωσ
   */
  void a-async_incw_fav_count(1: a-asyncincwfavcountwequest wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, (U ᵕ U❁)
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * p-pewfowms the async powtion o-of tweetsewvice.incw_tweet_bookmawk_count. 😳
   * o-onwy tweetypie itsewf can c-caww this. ʘwʘ
   */
  v-void async_incw_bookmawk_count(1: a-asyncincwbookmawkcountwequest wequest) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, (⑅˘꒳˘)
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * p-pewfowms the async powtion o-of tweetsewvice.set_additionaw_fiewds. ^•ﻌ•^
   * o-onwy tweetypie itsewf c-can caww this. nyaa~~
   */
  void a-async_set_additionaw_fiewds(1: asyncsetadditionawfiewdswequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, XD
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * pewfowms the async powtion of tweetsewviceintewnaw.set_wetweet_visibiwity. /(^•ω•^)
   * o-onwy tweetypie itsewf can caww this. (U ᵕ U❁)
   */
  void async_set_wetweet_visibiwity(1: asyncsetwetweetvisibiwitywequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, mya
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * s-set whethew the specified wetweet i-id shouwd be incwuded in its souwce tweet's wetweet c-count. (ˆ ﻌ ˆ)♡
   * this endpoint i-is invoked fwom a tweetypie-daemon t-to adjust wetweet c-counts fow aww tweets a
   * suspended ow fwauduwent (e.g. (✿oωo) w-wopo-'d) usew has wetweeted to disincentivize theiw fawse engagement. (✿oωo)
   */
  v-void set_wetweet_visibiwity(1: s-setwetweetvisibiwitywequest wequest) t-thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, òωó
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * pewfowms the async p-powtion of tweetsewvice.dewete_additionaw_fiewds. (˘ω˘)
   * onwy tweetypie itsewf can c-caww this. (ˆ ﻌ ˆ)♡
   */
  void async_dewete_additionaw_fiewds(1: asyncdeweteadditionawfiewdswequest fiewd_dewete) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, ( ͡o ω ͡o )
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * p-pewfowms t-the async powtion of tweetsewvice.takedown. rawr x3
   * o-onwy tweetypie itsewf can caww this. (˘ω˘)
   */
  void async_takedown(1: asynctakedownwequest w-wequest) t-thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, òωó
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * u-update the tweet's takedown fiewds when a usew i-is taken down. ( ͡o ω ͡o )
   * onwy tweetypie's usewtakedownchange d-daemon c-can caww this. σωσ
   */
  void set_tweet_usew_takedown(1: settweetusewtakedownwequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, (U ﹏ U)
    2: exceptions.sewvewewwow sewvew_ewwow, rawr
    3: dataewwow data_ewwow)

  /**
   * cascade d-dewete tweet is t-the wogic fow wemoving tweets that a-awe detached
   * f-fwom theiw dependency which h-has been deweted. -.- they awe awweady fiwtewed
   * out fwom sewving, so this opewation weconciwes s-stowage with the view
   * pwesented by tweetypie. ( ͡o ω ͡o )
   * this wpc caww is dewegated f-fwom daemons o-ow batch jobs. >_< c-cuwwentwy thewe
   * awe two use-cases when this caww is issued:
   * *   d-deweting d-detached wetweets a-aftew the souwce tweet was d-deweted. o.O
   *     this is done thwough w-wetweetsdewetion daemon and t-the
   *     cweanupdetachedwetweets j-job. σωσ
   * *   deweting edits of an initiaw t-tweet that has been deweted. -.-
   *     t-this is d-done by cascadededitedtweetdewete daemon. σωσ
   *     n-nyote that, :3 w-when sewving the owiginaw dewete w-wequest fow an edit, ^^
   *     the i-initiaw tweet is onwy deweted, òωó w-which makes aww e-edits hidden. (ˆ ﻌ ˆ)♡
   */
  void cascaded_dewete_tweet(1: cascadeddewetetweetwequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, XD
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * update the timestamp of the usew's most wecent w-wequest to dewete
   * wocation data on theiw t-tweets. òωó this does nyot actuawwy w-wemove the
   * geo infowmation fwom the usew's t-tweets, (ꈍᴗꈍ) but it wiww pwevent the geo
   * infowmation f-fow this usew's tweets fwom being wetuwned b-by
   * tweetypie. UwU
   */
  void scwub_geo_update_usew_timestamp(1: dewete_wocation_data.dewetewocationdata wequest) t-thwows (
    1: exceptions.cwientewwow cwient_ewwow, >w<
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * w-wook up tweets quoting a tweet that has been d-deweted and enqueue a-a compwiance event. ʘwʘ
   * onwy t-tweetypie's q-quotedtweetdewete daemon can caww this. :3
  **/
  v-void quoted_tweet_dewete(1: quotedtweetdewetewequest wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, ^•ﻌ•^
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * wook u-up tweets quoting a-a tweet that has b-been taken down and enqueue a compwiance event. (ˆ ﻌ ˆ)♡
   * onwy tweetypie's q-quotedtweettakedown daemon c-can caww this. 🥺
  **/
  void q-quoted_tweet_takedown(1: q-quotedtweettakedownwequest wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, OwO
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * w-wepwicates t-tweetsewvice.get_tweet_counts fwom anothew cwustew. 🥺
   */
  v-void wepwicated_get_tweet_counts(1: tweet_sewvice.gettweetcountswequest wequest) t-thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, OwO
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates t-tweetsewvice.get_tweet_fiewds fwom anothew cwustew. (U ᵕ U❁)
   */
  v-void wepwicated_get_tweet_fiewds(1: t-tweet_sewvice.gettweetfiewdswequest w-wequest) t-thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, ( ͡o ω ͡o )
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * w-wepwicates tweetsewvice.get_tweets f-fwom anothew cwustew. ^•ﻌ•^
   */
  void wepwicated_get_tweets(1: t-tweet_sewvice.gettweetswequest wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, o.O
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * w-wepwicates a-a tweetsewvice.post_tweet insewttweet event fwom anothew cwustew. (⑅˘꒳˘)
   * n-nyote: v-v1 vewsion of this endpoint pweviouswy j-just took a-a tweet which is why it was wepwaced
   */
  void wepwicated_insewt_tweet2(1: wepwicatedinsewttweet2wequest w-wequest) t-thwows (
    1: exceptions.cwientewwow cwient_ewwow, (ˆ ﻌ ˆ)♡
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.dewete_tweets d-dewetetweet event fwom anothew cwustew. :3
   */
  void wepwicated_dewete_tweet2(1: wepwicateddewetetweet2wequest w-wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, /(^•ω•^)
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.incw_tweet_fav_count e-event fwom anothew c-cwustew. òωó
   */
  v-void wepwicated_incw_fav_count(1: i-i64 tweet_id, :3 2: i-i32 dewta) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, (˘ω˘)
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.incw_tweet_bookmawk_count e-event fwom anothew c-cwustew. 😳
   */
  v-void wepwicated_incw_bookmawk_count(1: i64 t-tweet_id, σωσ 2: i32 d-dewta) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, UwU
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * w-wepwicates a tweetsewviceintewnaw.set_wetweet_visibiwity e-event fwom anothew c-cwustew. -.-
   */
  void wepwicated_set_wetweet_visibiwity(1: wepwicatedsetwetweetvisibiwitywequest wequest) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, 🥺
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * w-wepwicates a tweetsewvice.scwub_geo fwom a-anothew cwustew. 😳😳😳
   */
  v-void w-wepwicated_scwub_geo(1: w-wist<i64> t-tweet_ids) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, 🥺
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.set_additionaw_fiewds event fwom anothew cwustew. ^^
   */
  void w-wepwicated_set_additionaw_fiewds(
    1: t-tweet_sewvice.setadditionawfiewdswequest wequest
  ) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, ^^;;
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.dewete_additionaw_fiewds e-event fwom anothew cwustew. >w<
   */
  v-void wepwicated_dewete_additionaw_fiewds(
    1: w-wepwicateddeweteadditionawfiewdswequest wequest
  ) t-thwows (
    1: exceptions.cwientewwow cwient_ewwow, σωσ
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a-a tweetsewvice.undewete_tweet event fwom anothew c-cwustew. >w<
   * nyote: v1 vewsion of this endpoint p-pweviouswy just took a tweet w-which is why it was wepwaced
   */
  void wepwicated_undewete_tweet2(1: w-wepwicatedundewetetweet2wequest wequest) t-thwows (
    1: exceptions.cwientewwow cwient_ewwow, (⑅˘꒳˘)
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.takedown event fwom anothew cwustew. òωó
   */
  v-void wepwicated_takedown(1: t-tweet.tweet tweet) t-thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, (⑅˘꒳˘)
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wepwicates a tweetsewvice.update_possibwy_sensitive_tweet event f-fwom anothew c-cwustew. (ꈍᴗꈍ)
   */
  v-void wepwicated_update_possibwy_sensitive_tweet(1: t-tweet.tweet tweet) thwows (
    1: exceptions.cwientewwow cwient_ewwow,
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * f-fetches hydwated tweets and some metadata iwwespective of the t-tweets' state. rawr x3
   */
  wist<getstowedtweetswesuwt> g-get_stowed_tweets(1: g-getstowedtweetswequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, ( ͡o ω ͡o )
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
  * fetches hydwated t-tweets and some metadata f-fow a pawticuwaw usew, UwU iwwespective of the tweets'
  * state. ^^
  */
  g-getstowedtweetsbyusewwesuwt get_stowed_tweets_by_usew(1: g-getstowedtweetsbyusewwequest wequest) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, (˘ω˘)
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)
}
