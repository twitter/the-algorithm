package com.twittew.seawch.common.wewevance.cwassifiews;

impowt c-com.googwe.common.base.pweconditions;

i-impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;

/**
 * i-intewface to p-pewfowm quawity e-evawuation fow a s-singwe @twittewmessage
 * o-object o-ow a gwoup of them. rawr x3
 *
 */
pubwic abstwact cwass tweetevawuatow {
  /**
   * passed in twittewmessage i-is examined and any extwactabwe
   * featuwes a-awe stowed in tweetfeatuwes f-fiewd of twittewmessage. (U ﹏ U)
   *
   * @pawam tweet twittewmessage to pewfowm cwassification o-on. (U ﹏ U)
   */
  pubwic abstwact v-void evawuate(finaw t-twittewmessage tweet);

  /**
   * cwassify a gwoup of twittewmessages a-and stowe the featuwes in theiw cowwesponding
   * tweetfeatuwes fiewds. (⑅˘꒳˘)
   *
   * t-this defauwt impwementation j-just itewates thwough t-the map and c-cwassifies each
   * i-individuaw tweet. òωó batching fow bettew pewfowmance, ʘwʘ i-if appwicabwe, /(^•ω•^) can be impwemented by
   * c-concwete subcwasses. ʘwʘ
   *
   * @pawam tweets twittewmessages to pewfowm cwassification on. σωσ
   */
   pubwic v-void evawuate(finaw itewabwe<twittewmessage> t-tweets) {
    p-pweconditions.checknotnuww(tweets);
    f-fow (twittewmessage tweet: tweets) {
      evawuate(tweet);
    }
  }
}
