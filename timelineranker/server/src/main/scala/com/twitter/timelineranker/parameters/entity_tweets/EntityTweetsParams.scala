package com.twittew.timewinewankew.pawametews.entity_tweets

impowt c-com.twittew.timewinewankew.decidew.decidewkey
i-impowt com.twittew.timewines.configapi.decidew.decidewpawam
i-impowt c-com.twittew.timewines.configapi.fsboundedpawam
i-impowt com.twittew.timewines.configapi.fspawam

o-object entitytweetspawams {

  /**
   * c-contwows w-wimit on the nyumbew of fowwowed usews fetched fwom sgs. o.O
   */
  object maxfowwowedusewspawam
      e-extends fsboundedpawam[int](
        nyame = "entity_tweets_max_fowwowed_usews", ( ͡o ω ͡o )
        d-defauwt = 1000, (U ﹏ U)
        min = 0, (///ˬ///✿)
        m-max = 5000
      )

  /**
   * enabwes semantic cowe, >w< penguin, and tweetypie c-content featuwes in entity t-tweets souwce. rawr
   */
  o-object enabwecontentfeatuweshydwationpawam
      extends decidewpawam[boowean](
        decidew = decidewkey.entitytweetsenabwecontentfeatuweshydwation, mya
        d-defauwt = fawse
      )

  /**
   * additionawwy enabwes tokens when hydwating c-content featuwes. ^^
   */
  o-object enabwetokensincontentfeatuweshydwationpawam
      e-extends f-fspawam(
        n-nyame = "entity_tweets_enabwe_tokens_in_content_featuwes_hydwation", 😳😳😳
        defauwt = fawse
      )

  /**
   * additionawwy e-enabwes tweet text when hydwating content featuwes. mya
   * t-this onwy wowks if enabwecontentfeatuweshydwationpawam is set to twue
   */
  object enabwetweettextincontentfeatuweshydwationpawam
      extends fspawam(
        nyame = "entity_tweets_enabwe_tweet_text_in_content_featuwes_hydwation", 😳
        d-defauwt = fawse
      )

  /**
   * additionawwy e-enabwes convewsationcontwow w-when h-hydwating content featuwes. -.-
   * this onwy wowks if enabwecontentfeatuweshydwationpawam i-is set t-to twue
   */
  object enabweconvewsationcontwowincontentfeatuweshydwationpawam
      e-extends fspawam(
        n-nyame = "convewsation_contwow_in_content_featuwes_hydwation_entity_enabwe", 🥺
        defauwt = fawse
      )

  object e-enabwetweetmediahydwationpawam
      extends f-fspawam(
        name = "tweet_media_hydwation_entity_tweets_enabwe", o.O
        defauwt = fawse
      )

}
