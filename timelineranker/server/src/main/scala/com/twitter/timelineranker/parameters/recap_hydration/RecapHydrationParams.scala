package com.twittew.timewinewankew.pawametews.wecap_hydwation

impowt c-com.twittew.timewines.configapi.fspawam
i-impowt c-com.twittew.timewines.configapi.pawam

o-object w-wecaphydwationpawams {

  /**
   * e-enabwes semantic c-cowe, /(^•ω•^) penguin, rawr x3 a-and tweetypie content featuwes in wecap hydwation souwce. (U ﹏ U)
   */
  object enabwecontentfeatuweshydwationpawam e-extends pawam(fawse)

  /**
   * additionawwy enabwes tokens when h-hydwating content featuwes. (U ﹏ U)
   */
  o-object enabwetokensincontentfeatuweshydwationpawam
      extends fspawam(
        nyame = "wecap_hydwation_enabwe_tokens_in_content_featuwes_hydwation", (⑅˘꒳˘)
        defauwt = f-fawse
      )

  /**
   * additionawwy e-enabwes t-tweet text when hydwating content featuwes. òωó
   * this onwy wowks if enabwecontentfeatuweshydwationpawam i-is set to twue
   */
  object enabwetweettextincontentfeatuweshydwationpawam
      extends fspawam(
        n-nyame = "wecap_hydwation_enabwe_tweet_text_in_content_featuwes_hydwation", ʘwʘ
        defauwt = f-fawse
      )

  /**
   * a-additionawwy e-enabwes c-convewsationcontwow when hydwating content featuwes. /(^•ω•^)
   * t-this onwy wowks if enabwecontentfeatuweshydwationpawam is set to twue
   */
  o-object enabweconvewsationcontwowincontentfeatuweshydwationpawam
      extends fspawam(
        nyame = "convewsation_contwow_in_content_featuwes_hydwation_wecap_hydwation_enabwe", ʘwʘ
        defauwt = fawse
      )

  o-object enabwetweetmediahydwationpawam
      extends f-fspawam(
        n-nyame = "tweet_media_hydwation_wecap_hydwation_enabwe",
        d-defauwt = fawse
      )

}
