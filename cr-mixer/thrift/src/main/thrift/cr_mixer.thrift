namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "ads.thwift"
incwude "candidate_genewation_key.thwift"
i-incwude "pwoduct.thwift"
i-incwude "pwoduct_context.thwift"
i-incwude "vawidation.thwift"
incwude "metwic_tags.thwift"
i-incwude "wewated_tweet.thwift"
incwude "uteg.thwift"
incwude "fws_based_tweet.thwift"
incwude "wewated_video_tweet.thwift"
incwude "topic_tweet.thwift"

i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
incwude "com/twittew/timewines/wendew/wesponse.thwift"
incwude "finatwa-thwift/finatwa_thwift_exceptions.thwift"
i-incwude "com/twittew/stwato/gwaphqw/swice.thwift"

stwuct cwmixewtweetwequest {
	1: w-wequiwed cwient_context.cwientcontext cwientcontext
	2: wequiwed pwoduct.pwoduct pwoduct
	# p-pwoduct-specific pawametews s-shouwd be pwaced i-in the pwoduct context
	3: optionaw pwoduct_context.pwoductcontext pwoductcontext
	4: optionaw w-wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', 🥺 haspewsonawdata='twue')

stwuct tweetwecommendation {
  1: wequiwed i-i64 tweetid (pewsonawdatatype = 'tweetid')
  2: wequiwed doubwe s-scowe
  3: optionaw w-wist<metwic_tags.metwictag> m-metwictags
  # 4: t-the authow of the tweet candidate. òωó to be used b-by content-mixew to unbwock the hydwa expewiment.
  4: o-optionaw i64 authowid (pewsonawdatatype = 'usewid')
  # 5: extwa info about candidate genewation. (ˆ ﻌ ˆ)♡ to be used by content-mixew t-to unbwock the hydwa expewiment. -.-
  5: optionaw c-candidate_genewation_key.candidategenewationkey c-candidategenewationkey
  # 1001: t-the watest timestamp of fav signaws. :3 if nyuww, the candidate i-is nyot genewated f-fwom fav signaws
  1001: o-optionaw i64 watestsouwcesignawtimestampinmiwwis(pewsonawdatatype = 'pubwictimestamp')
} (pewsisted='twue', ʘwʘ h-haspewsonawdata = 'twue')

stwuct cwmixewtweetwesponse {
 1: w-wequiwed wist<tweetwecommendation> t-tweets
} (pewsisted='twue')

sewvice cwmixew {
  cwmixewtweetwesponse g-gettweetwecommendations(1: cwmixewtweetwequest w-wequest) thwows (
    # vawidation e-ewwows - the d-detaiws of which wiww be wepowted to cwients on faiwuwe
    1: vawidation.vawidationexceptionwist vawidationewwows;
    # sewvew e-ewwows - the d-detaiws of which wiww nyot be wepowted t-to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow sewvewewwow
  )

  # g-getwewatedtweetsfowquewytweet and getwewatedtweetsfowquewyauthow do vewy simiwaw things
  # w-we can mewge these two endpoints into one unified endpoint
  wewated_tweet.wewatedtweetwesponse getwewatedtweetsfowquewytweet(1: w-wewated_tweet.wewatedtweetwequest wequest) thwows (
    # v-vawidation e-ewwows - the d-detaiws of which wiww be wepowted t-to cwients o-on faiwuwe
    1: v-vawidation.vawidationexceptionwist v-vawidationewwows;
    # sewvew ewwows - the d-detaiws of which w-wiww nyot be wepowted t-to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow
  )

  wewated_tweet.wewatedtweetwesponse getwewatedtweetsfowquewyauthow(1: wewated_tweet.wewatedtweetwequest w-wequest) thwows (
    # vawidation ewwows - the detaiws of which wiww be wepowted to cwients o-on faiwuwe
    1: vawidation.vawidationexceptionwist vawidationewwows;
    # sewvew ewwows - t-the detaiws o-of which wiww nyot b-be wepowted to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow sewvewewwow
  )

  u-uteg.utegtweetwesponse g-getutegtweetwecommendations(1: uteg.utegtweetwequest wequest) thwows (
    # vawidation ewwows - the detaiws of w-which wiww be wepowted to cwients o-on faiwuwe
    1: vawidation.vawidationexceptionwist v-vawidationewwows;
    # s-sewvew ewwows - the detaiws of which wiww nyot be w-wepowted to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow sewvewewwow
  )

  f-fws_based_tweet.fwstweetwesponse g-getfwsbasedtweetwecommendations(1: fws_based_tweet.fwstweetwequest wequest) thwows (
     # vawidation ewwows - t-the detaiws o-of which wiww b-be wepowted to cwients on faiwuwe
     1: v-vawidation.vawidationexceptionwist v-vawidationewwows;
     # sewvew ewwows - t-the detaiws of which wiww nyot be wepowted to cwients
     2: finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow
  )

  w-wewated_video_tweet.wewatedvideotweetwesponse getwewatedvideotweetsfowquewytweet(1: wewated_video_tweet.wewatedvideotweetwequest wequest) t-thwows (
      # v-vawidation ewwows - the detaiws of which wiww be wepowted t-to cwients on faiwuwe
      1: vawidation.vawidationexceptionwist vawidationewwows;
      # sewvew ewwows - the d-detaiws of which wiww nyot be wepowted to cwients
      2: f-finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow
  )

  ads.adswesponse getadswecommendations(1: ads.adswequest wequest) t-thwows (
    # v-vawidation ewwows - the detaiws of which wiww be wepowted t-to cwients on faiwuwe
    1: vawidation.vawidationexceptionwist v-vawidationewwows;
    # sewvew ewwows - the detaiws of which wiww n-nyot be wepowted to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow
  )

  topic_tweet.topictweetwesponse gettopictweetwecommendations(1: t-topic_tweet.topictweetwequest wequest) t-thwows (
    # v-vawidation ewwows - t-the detaiws of which wiww b-be wepowted to c-cwients on faiwuwe
    1: vawidation.vawidationexceptionwist vawidationewwows;
    # s-sewvew ewwows - t-the detaiws o-of which wiww nyot be wepowted to cwients
    2: f-finatwa_thwift_exceptions.sewvewewwow sewvewewwow
  )
}
