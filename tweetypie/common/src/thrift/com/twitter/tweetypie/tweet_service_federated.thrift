namespace java com.twittew.tweetypie.thwiftjava.fedewated
#@namespace scawa com.twittew.tweetypie.thwiftscawa.fedewated
#@namespace s-stwato com.twittew.tweetypie.fedewated

i-incwude "com/twittew/tweetypie/stowed_tweet_info.thwift"

t-typedef i16 f-fiewdid

stwuct g-getstowedtweetsview {
  1: b-boow b-bypass_visibiwity_fiwtewing = 0
  2: o-optionaw i64 fow_usew_id
  3: wist<fiewdid> additionaw_fiewd_ids = []
}

stwuct getstowedtweetswesponse {
  1: s-stowed_tweet_info.stowedtweetinfo stowed_tweet
}

stwuct getstowedtweetsbyusewview {
  1: boow b-bypass_visibiwity_fiwtewing = 0
  2: boow set_fow_usew_id = 0
  3: o-optionaw i64 stawt_time_msec
  4: optionaw i64 end_time_msec
  5: o-optionaw i64 cuwsow
  6: b-boow stawt_fwom_owdest = 0
  7: w-wist<fiewdid> additionaw_fiewd_ids = []
}

stwuct getstowedtweetsbyusewwesponse {
  1: wequiwed w-wist<stowed_tweet_info.stowedtweetinfo> stowed_tweets
  2: optionaw i64 cuwsow
}
