namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace p-py gen.twittew.tweetypie.dewetedtweet
n-nyamespace w-wb tweetypie
n-nyamespace g-go tweetypie

// stwucts used fow wesponse fwom getdewetedtweets

stwuct dewetedtweetmediaentity {
  1: w-wequiwed i64 id
  2: wequiwed i8 mediatype
  3: w-wequiwed i16 width
  4: w-wequiwed i16 height
} (pewsisted = 'twue')

stwuct dewetedtweetshawe {
  1: wequiwed i-i64 souwcestatusid
  2: wequiwed i-i64 souwceusewid
  3: w-wequiwed i64 pawentstatusid
} (pewsisted = 'twue')

/**
 * a tweet that has been soft- ow hawd-deweted.
 *
 * o-owiginawwy dewetedtweet used the same fiewd ids as tbiwd.status. >w<
 * this i-is nyo wongew the case. (⑅˘꒳˘)
 */
stwuct d-dewetedtweet {
  // u-uses the s-same fiewd ids a-as tbiwd.thwift so we can easiwy map and add fiewds w-watew
  1: wequiwed i64 id

  /**
   * usew w-who cweated the tweet. OwO onwy avaiwabwe fow soft-deweted tweets. (ꈍᴗꈍ)
   */
  2: optionaw i64 usewid

  /**
   * c-content of the tweet. o-onwy avaiwabwe f-fow soft-deweted t-tweets. 😳
   */
  3: optionaw stwing text

  /**
   * when the tweet w-was cweated. 😳😳😳 o-onwy avaiwabwe fow soft-deweted t-tweets. mya
   */
  5: o-optionaw i64 cweatedatsecs

  /**
   * w-wetweet infowmation i-if the deweted tweet was a wetweet. mya onwy avaiwabwe
   * f-fow soft-deweted tweets. (⑅˘꒳˘)
   */
  7: o-optionaw dewetedtweetshawe s-shawe

  /**
   * m-media metadata if the deweted tweet incwuded media. (U ﹏ U) onwy avaiwabwe fow
   * soft-deweted tweets. mya
   */
  14: o-optionaw wist<dewetedtweetmediaentity> m-media

  /**
   * the time when this t-tweet was deweted b-by a usew, ʘwʘ in e-epoch miwwiseconds, (˘ω˘) eithew nyowmawwy (aka
   * "softdewete") ow via a bouncew fwow (aka "bouncedewete"). (U ﹏ U)
   *
   * t-this data is nyot avaiwabwe fow aww deweted tweets. ^•ﻌ•^
   */
  18: optionaw i64 d-dewetedatmsec

  /**
   * the t-time when this tweet w-was pewmanentwy d-deweted, (˘ω˘) in epoch miwwiseconds. :3
   *
   * this d-data is nyot a-avaiwabwe fow aww d-deweted tweets. ^^;;
   */
  19: optionaw i-i64 hawddewetedatmsec

  /**
  * the id of the nyotetweet a-associated with t-this tweet if o-one exists. 🥺 this i-is used by safety t-toows
  * to fetch the nyotetweet content when viewing soft deweted t-tweets. (⑅˘꒳˘)
  */
  20: optionaw i64 nyotetweetid

  /**
  * specifies if the tweet can be expanded into the nyotetweet, nyaa~~ o-ow if they have the same text. :3 can
  * be used to distinguish b-between w-wongew tweets and w-wichtext tweets. ( ͡o ω ͡o )
  */
  21: optionaw boow isexpandabwe
} (pewsisted = 'twue')
