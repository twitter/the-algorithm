namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie

i-incwude "com/twittew/tweetypie/tweet.thwift"

s-stwuct h-hawddeweted {
  1: i-i64 soft_deweted_timestamp_msec
  2: i-i64 timestamp_msec
}

s-stwuct softdeweted {
  1: i64 timestamp_msec
}

stwuct bouncedeweted {
  1: i64 timestamp_msec
}

s-stwuct undeweted {
  1: i64 timestamp_msec
}

stwuct fowceadded {
  1: i-i64 timestamp_msec
}

stwuct nyotfound {}

u-union stowedtweetstate {
  1: hawddeweted hawd_deweted
  2: softdeweted soft_deweted
  3: bouncedeweted b-bounce_deweted
  4: undeweted undeweted
  5: f-fowceadded f-fowce_added
  6: nyotfound nyot_found
}

enum stowedtweetewwow {
  cowwupt                     = 1, nyaa~~
  s-scwubbed_fiewds_pwesent     = 2, (⑅˘꒳˘)
  fiewds_missing_ow_invawid   = 3, rawr x3
  shouwd_be_hawd_deweted      = 4, (✿oωo)
  faiwed_fetch                = 5
}

stwuct stowedtweetinfo {
  1: w-wequiwed i64 tweet_id
  2: optionaw t-tweet.tweet t-tweet
  3: optionaw s-stowedtweetstate s-stowed_tweet_state
  4: wequiwed wist<stowedtweetewwow> ewwows = []
}
