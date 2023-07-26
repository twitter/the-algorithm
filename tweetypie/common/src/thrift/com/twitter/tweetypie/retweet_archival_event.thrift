namespace java com.twittew.tweetypie.thwiftjava
namespace py gen.twittew.tweetypie.wetweet_awchivaw_event
#@namespace s-scawa com.twittew.tweetypie.thwiftscawa
#@namespace s-stwato c-com.twittew.tweetypie
n-nyamespace w-wb tweetypie
nyamespace g-go tweetypie

/**
 * t-this e-event is pubwished to "wetweet_awchivaw_events" when tweetypie pwocesses an
 * asyncsetwetweetvisibiwitywequest. ( ͡o ω ͡o )
 *
 * t-this is usefuw fow sewvices (intewaction countew, (U ﹏ U) insights t-twack) that need to
 * know w-when the wetweet engagement count of a tweet has been modified d-due to the
 * wetweeting usew being p-put in to ow o-out of suspension ow wead-onwy mode. (///ˬ///✿)
 */
stwuct wetweetawchivawevent {
  // the w-wetweet id affected by this awchivaw event. >w<
  1: wequiwed i64 wetweet_id (pewsonawdatatype = 'tweetid')
  // the s-souwce tweet id fow the wetweet. rawr t-this tweet had i-its wetweet count m-modified. mya
  2: w-wequiwed i64 swc_tweet_id (pewsonawdatatype = 'tweetid')
  3: wequiwed i64 wetweet_usew_id (pewsonawdatatype = 'usewid')
  4: w-wequiwed i64 swc_tweet_usew_id (pewsonawdatatype = 'usewid')
  // appwoximate time in miwwiseconds f-fow when the count modification occuwwed, ^^ based on
  // unix epoch (1 januawy 1970 00:00:00 utc). 😳😳😳 tweetypie w-wiww use the time when it is
  // a-about to send t-the asynchwonous w-wwite wequest to tfwock fow this timestamp. mya
  5: wequiwed i64 timestamp_ms
  // m-mawks if this event i-is fow awchiving(twue) ow unawchiving(fawse) a-action. 😳
  // awchiving i-indicates an engagement c-count decwement occuwwed and unawchiving i-indicates an incwementaw. -.-
  6: optionaw b-boow is_awchiving_action
}(pewsisted='twue', 🥺 haspewsonawdata = 'twue')
