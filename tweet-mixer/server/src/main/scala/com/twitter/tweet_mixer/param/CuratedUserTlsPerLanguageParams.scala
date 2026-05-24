package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSParam

object CuratedUserTlsPerLanguageParams {
  object CuratedUserTlsPerLanguageTweetsEnable
      extends FSParam[Boolean](
        name = "curated_user_tls_per_language_tweets_enable",
        default = false
      )

  object CuratedUserTlsPerLanguageTweetsAuthorListParam
      extends FSParam[Seq[Long]](
        name = "curated_user_tls_per_language_tweets_author_list_param",
        Seq.empty
      )

  val booleanFSOverrides = Seq(CuratedUserTlsPerLanguageTweetsEnable)

  val longSeqFSOverrides = Seq(CuratedUserTlsPerLanguageTweetsAuthorListParam)
}
