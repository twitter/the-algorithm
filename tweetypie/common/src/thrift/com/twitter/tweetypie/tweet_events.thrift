namespace java com.twittew.tweetypie.thwiftjava
namespace py gen.twittew.tweetypie.tweet_events
#@namespace s-scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace w-wb tweetypie
n-nyamespace go t-tweetypie

incwude "com/twittew/tseng/withhowding/withhowding.thwift"
i-incwude "com/twittew/tweetypie/twansient_context.thwift"
incwude "com/twittew/tweetypie/tweet.thwift"
incwude "com/twittew/tweetypie/tweet_audit.thwift"
incwude "com/twittew/gizmoduck/usew.thwift"

/**
 * safetytype e-encodes the event usew's safety state in an enum s-so downstweam
 * event pwocessows c-can fiwtew events without having to woad the usew. mya
 */
enum safetytype {
  p-pwivate    = 0   // usew.safety.ispwotected
  w-westwicted = 1   // !pwivate && u-usew.safety.suspended
  pubwic     = 2   // !(pwivate || westwicted)
  wesewved0  = 3
  wesewved1  = 4
  w-wesewved2  = 5
  wesewved3  = 6
}

stwuct tweetcweateevent {
  /**
   * the tweet that has b-been cweated. 😳😳😳
   */
  1: tweet.tweet t-tweet

  /**
   * t-the usew w-who owns the cweated t-tweet. OwO
   */
  2: usew.usew usew

  /**
   * t-the tweet being wetweeted. rawr
   */
  3: optionaw t-tweet.tweet souwce_tweet

  /**
   * the usew who owns souwce_tweet. XD
   */
  4: optionaw usew.usew souwce_usew

  /**
   * the u-usew whose tweet ow wetweet is being w-wetweeted.
   *
   * t-this is t-the id of the usew who owns
   * tweet.cowe_data.shawe.pawent_status_id. (U ﹏ U) in many c-cases this wiww b-be the
   * same as souwce_usew.id; i-it is diffewent w-when the tweet is cweated v-via
   * anothew wetweet. (˘ω˘) see the e-expwanation of souwce_usew_id and pawent_usew_id
   * i-in shawe fow exampwes. UwU
   */
  5: o-optionaw i64 wetweet_pawent_usew_id (pewsonawdatatype = 'usewid')

  /**
   * t-the tweet q-quoted in the cweated tweet. >_<
   */
  6: optionaw tweet.tweet quoted_tweet

  /**
   * the usew who owns quoted_tweet. σωσ
   */
  7: o-optionaw usew.usew q-quoted_usew

  /**
   * awbitwawy passthwough m-metadata about t-tweet cweation. 🥺
   *
   * s-see tweetcweatecontextkey fow mowe detaiws about the d-data that may be
   * pwesent hewe. 🥺
   */
  8: optionaw map<tweet.tweetcweatecontextkey, stwing> a-additionaw_context (pewsonawdatatypevawue='usewid')

  /**
   * additionaw wequest a-awguments p-passed thwough t-to consumews. ʘwʘ
   */
  9: optionaw t-twansient_context.twansientcweatecontext t-twansient_context

  /**
  * f-fwag exposing i-if a quoted tweet has been quoted by the usew p-pweviouswy. :3
  **/
  10: o-optionaw b-boow quotew_has_awweady_quoted_tweet
}(pewsisted='twue', (U ﹏ U) h-haspewsonawdata = 'twue')

s-stwuct tweetdeweteevent {
  /**
   * the tweet being deweted. (U ﹏ U)
   */
  1: t-tweet.tweet tweet

  /**
   * the usew who owns the deweted tweet. ʘwʘ
   */
  2: optionaw usew.usew usew

  /**
   * whethew this t-tweet was deweted as pawt of usew ewasuwe (the pwocess of deweting t-tweets
   * b-bewonging to deactivated a-accounts). >w<
   *
   * these d-dewetions occuw in high vowume s-spikes and the t-tweets have awweady been made invisibwe
   * extewnawwy. rawr x3 you may wish to pwocess them in batches o-ow offwine. OwO
   */
  3: optionaw b-boow is_usew_ewasuwe

  /**
   * audit infowmation f-fwom the dewetetweetwequest t-that caused this dewetion. ^•ﻌ•^
   *
   * this fiewd i-is used to twack t-the weason fow dewetion in nyon-usew-initiated
   * t-tweet dewetions, >_< w-wike twittew suppowt agents deweting tweets ow spam
   * cweanup. OwO
   */
  4: o-optionaw tweet_audit.auditdewetetweet a-audit

  /**
   * i-id of the usew initiating t-this wequest. >_<
   * i-it couwd be eithew the o-ownew of the tweet ow an admin. (ꈍᴗꈍ)
   * it is used fow scwubbing. >w<
   */
  5: optionaw i-i64 by_usew_id (pewsonawdatatype = 'usewid')

  /**
   * w-whethew this tweet was deweted by an a-admin usew ow n-nyot
   *
   * it is used fow scwubbing. (U ﹏ U)
   */
  6: optionaw boow is_admin_dewete
}(pewsisted='twue', ^^ h-haspewsonawdata = 'twue')

stwuct tweetundeweteevent {
  1: tweet.tweet tweet
  2: optionaw usew.usew usew
  3: o-optionaw tweet.tweet souwce_tweet
  4: optionaw u-usew.usew s-souwce_usew
  5: optionaw i64 wetweet_pawent_usew_id (pewsonawdatatype = 'usewid')
  6: optionaw tweet.tweet quoted_tweet
  7: optionaw u-usew.usew q-quoted_usew
  // timestamp of the dewetion that this undewete i-is wevewsing
  8: optionaw i64 deweted_at_msec
}(pewsisted='twue', (U ﹏ U) h-haspewsonawdata = 'twue')

/**
 * when a usew dewetes the wocation infowmation f-fow theiw tweets, :3 we send one
 * t-tweetscwubgeoevent f-fow evewy tweet fwom which t-the wocation is wemoved. (✿oωo)
 *
 * u-usews cause this b-by sewecting "dewete w-wocation infowmation" in settings ->
 * p-pwivacy. XD
 */
s-stwuct tweetscwubgeoevent {
  1: i64 t-tweet_id (pewsonawdatatype = 'tweetid')
  2: i-i64 u-usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', >w< haspewsonawdata = 'twue')

/**
 * when a-a usew dewetes the wocation infowmation f-fow theiw t-tweets, òωó we send one
 * usewscwubgeoevent with the max tweet i-id that was scwubbed (in a-addition t-to
 * sending m-muwtipwe tweetscwubgeoevents as d-descwibed above). (ꈍᴗꈍ)
 *
 * usews cause this by sewecting "dewete wocation infowmation" in settings ->
 * p-pwivacy. rawr x3 this additionaw event i-is sent to maintain backwawds c-compatibiwity
 * with hosebiwd. rawr x3
 */
s-stwuct usewscwubgeoevent {
  1: i64 usew_id (pewsonawdatatype = 'usewid')
  2: i-i64 max_tweet_id (pewsonawdatatype = 'tweetid')
}(pewsisted='twue', σωσ h-haspewsonawdata = 'twue')

s-stwuct tweettakedownevent {
  1: i-i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: i-i64 usew_id (pewsonawdatatype = 'usewid')
  // this is the compwete wist of takedown countwy codes fow the tweet, (ꈍᴗꈍ)
  // incwuding nyanievew m-modifications w-wewe made to twiggew t-this event. rawr
  // @depwecated pwefew takedown_weasons o-once tweetypie-4329 depwoyed
  3: wist<stwing> takedown_countwy_codes = []
  // t-this is t-the compwete wist of takedown w-weasons fow the tweet, ^^;;
  // incwuding nyanievew m-modifications wewe m-made to twiggew this event. rawr x3
  4: w-wist<withhowding.takedownweason> t-takedown_weasons = []
}(pewsisted='twue', (ˆ ﻌ ˆ)♡ haspewsonawdata = 'twue')

stwuct additionawfiewdupdateevent {
  // onwy contains the tweet id and m-modified ow nyewwy a-added fiewds o-on that tweet. σωσ
  // u-unchanged f-fiewds and tweet cowe data awe omitted. (U ﹏ U)
  1: t-tweet.tweet u-updated_fiewds
  2: optionaw i-i64 usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', >w< h-haspewsonawdata = 'twue')

stwuct additionawfiewddeweteevent {
  // a-a map fwom tweet id to deweted fiewd ids
  // e-each event wiww onwy contain o-one tweet. σωσ
  1: m-map<i64, nyaa~~ wist<i16>> deweted_fiewds (pewsonawdatatypekey='tweetid')
  2: o-optionaw i64 usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', haspewsonawdata = 'twue')

// t-this event is onwy w-wogged to scwibe n-nyot sent to eventbus
stwuct tweetmediatagevent {
  1: i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: i-i64 usew_id (pewsonawdatatype = 'usewid')
  3: set<i64> t-tagged_usew_ids (pewsonawdatatype = 'usewid')
  4: o-optionaw i64 timestamp_ms
}(pewsisted='twue', 🥺 h-haspewsonawdata = 'twue')

stwuct tweetpossibwysensitiveupdateevent {
  1: i-i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: i-i64 usew_id (pewsonawdatatype = 'usewid')
  // the bewow two fiewds contain the w-wesuwts of the update. rawr x3
  3: boow nysfw_admin
  4: b-boow nsfw_usew
}(pewsisted='twue', σωσ h-haspewsonawdata = 'twue')

stwuct quotedtweetdeweteevent {
  1: i-i64 quoting_tweet_id (pewsonawdatatype = 'tweetid')
  2: i64 quoting_usew_id (pewsonawdatatype = 'usewid')
  3: i-i64 quoted_tweet_id (pewsonawdatatype = 'tweetid')
  4: i-i64 q-quoted_usew_id (pewsonawdatatype = 'usewid')
}(pewsisted='twue', (///ˬ///✿) haspewsonawdata = 'twue')

stwuct quotedtweettakedownevent {
  1: i64 quoting_tweet_id (pewsonawdatatype = 'tweetid')
  2: i64 quoting_usew_id (pewsonawdatatype = 'usewid')
  3: i64 quoted_tweet_id (pewsonawdatatype = 'tweetid')
  4: i64 quoted_usew_id (pewsonawdatatype = 'usewid')
  // this is the compwete wist of takedown countwy c-codes fow the tweet, (U ﹏ U)
  // i-incwuding nyanievew modifications wewe m-made to twiggew t-this event. ^^;;
  // @depwecated pwefew t-takedown_weasons
  5: wist<stwing> t-takedown_countwy_codes = []
  // this is t-the compwete wist o-of takedown weasons fow the t-tweet, 🥺
  // incwuding nyanievew m-modifications wewe m-made to twiggew this event. òωó
  6: wist<withhowding.takedownweason> t-takedown_weasons = []
}(pewsisted='twue', XD haspewsonawdata = 'twue')

u-union t-tweeteventdata {
  1:  t-tweetcweateevent t-tweet_cweate_event
  2:  t-tweetdeweteevent t-tweet_dewete_event
  3:  a-additionawfiewdupdateevent a-additionaw_fiewd_update_event
  4:  additionawfiewddeweteevent a-additionaw_fiewd_dewete_event
  5:  t-tweetundeweteevent t-tweet_undewete_event
  6:  tweetscwubgeoevent t-tweet_scwub_geo_event
  7:  tweettakedownevent tweet_takedown_event
  8:  u-usewscwubgeoevent usew_scwub_geo_event
  9:  t-tweetpossibwysensitiveupdateevent t-tweet_possibwy_sensitive_update_event
  10: quotedtweetdeweteevent q-quoted_tweet_dewete_event
  11: quotedtweettakedownevent quoted_tweet_takedown_event
}(pewsisted='twue', :3 haspewsonawdata = 'twue')

/**
 * @depwecated
 */
s-stwuct checksum {
  1: i32 checksum
}(pewsisted='twue')

s-stwuct tweeteventfwags {
  /**
   * @depwecated w-was dawk_fow_sewvice. (U ﹏ U)
   */
  1: wist<stwing> u-unused1 = []

  2: i64 timestamp_ms

  3: optionaw safetytype safety_type

  /**
   * @depwecated was checksum. >w<
   */
  4: o-optionaw checksum unused4
}(pewsisted='twue')

/**
 * a-a tweetevent i-is a nyotification pubwished to the tweet_events stweam. /(^•ω•^)
 */
s-stwuct tweetevent {
  1: tweeteventdata d-data
  2: t-tweeteventfwags f-fwags
}(pewsisted='twue', (⑅˘꒳˘) haspewsonawdata = 'twue')
