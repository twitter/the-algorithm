/**
 * this fiwe contains definitions f-fow twansient, mya p-passthwough s-stwuctuwed data. >w<
 *
 * i-if you nyeed t-to add stwuctuwed d-data that t-tweetypie accepts i-in a wequest
 * and passes the data thwough to one ow mowe backends (eg. nyaa~~ eventbus), (✿oωo) t-this
 * is the pwace to put it. ʘwʘ tweetypie m-may ow may nyot inspect the data a-and
 * awtew the behaviow based on it, (ˆ ﻌ ˆ)♡ but it won't change it. 😳😳😳
 */

n-nyamespace java com.twittew.tweetypie.thwiftjava
#@namespace s-scawa com.twittew.tweetypie.thwiftscawa
#@namespace s-stwato com.twittew.tweetypie
nyamespace py gen.twittew.tweetypie.twansient_context
nyamespace wb tweetypie
n-nyamespace go tweetypie

incwude "com/twittew/tweetypie/tweet.thwift"

enum batchcomposemode {
  /**
   * this is the fiwst tweet i-in a batch. :3
   */
  batch_fiwst = 1

  /**
   * t-this is any of t-the subsequent t-tweets in a batch. OwO
   */
  b-batch_subsequent = 2
}

/**
 * data suppwied at tweet c-cweation time that is nyot sewved by tweetypie, (U ﹏ U) b-but
 * is passed thwough to consumews of the tweet_events eventbus stweam as pawt
 * of tweetcweateevent. >w<
 * this i-is diffewent fwom additionaw_context i-in that t-tweetypie
 * inspects t-this data as weww, (U ﹏ U) and we pwefew stwucts ovew stwings. 😳
 * i-if adding a nyew f-fiewd that wiww be passed thwough t-to eventbus, (ˆ ﻌ ˆ)♡ p-pwefew this
 * ovew additionaw_context. 😳😳😳
 */
s-stwuct twansientcweatecontext {
  /**
   * i-indicates whethew a tweet was cweated using a-a batch composew, (U ﹏ U) and if so
   * p-position of a tweet within t-the batch. (///ˬ///✿)
   *
   * a-a vawue of 'none' indicates that the tweet was nyot cweated in a batch. 😳
   *
   * mowe info: https://docs.googwe.com/document/d/1dj9k0kzxpzhk0v-nsekt0cadovyvi8sh9eseia2edw4/edit
   */
  1: o-optionaw batchcomposemode b-batch_compose

  /**
   * indicates i-if the tweet contains a-a wive pewiscope s-stweaming video. 😳
   *
   * this enabwes pewiscope wivefowwow. σωσ
   */
  2: o-optionaw boow pewiscope_is_wive

  /**
   * indicates the usewid of the wive pewiscope stweaming v-video. rawr x3
   *
   * this enabwes pewiscope w-wivefowwow. OwO
   */
  3: o-optionaw i64 pewiscope_cweatow_id (pewsonawdatatype='usewid')
}(pewsisted='twue', /(^•ω•^) h-haspewsonawdata='twue')
