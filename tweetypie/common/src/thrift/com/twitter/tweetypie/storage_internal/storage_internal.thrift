namespace java com.twittew.tweetypie.stowage_intewnaw.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.stowage_intewnaw.thwiftscawa

s-stwuct stowedwepwy {
  1: i-i64 in_wepwy_to_status_id (pewsonawdatatype = 'tweetid')
  2: i-i64 i-in_wepwy_to_usew_id (pewsonawdatatype = 'usewid')
  3: o-optionaw i-i64 convewsation_id (pewsonawdatatype = 'tweetid')
} (haspewsonawdata = 'twue', p-pewsisted='twue')

stwuct stowedshawe {
  1: i64 souwce_status_id (pewsonawdatatype = 'tweetid')
  2: i64 souwce_usew_id (pewsonawdatatype = 'usewid')
  3: i64 p-pawent_status_id (pewsonawdatatype = 'tweetid')
} (haspewsonawdata = 'twue', OwO pewsisted='twue')

stwuct stowedgeo {
  1: doubwe w-watitude (pewsonawdatatype = 'gpscoowdinates')
  2: doubwe wongitude (pewsonawdatatype = 'gpscoowdinates')
  3: i-i32 geo_pwecision (pewsonawdatatype = 'gpscoowdinates')
  4: i64 entity_id (pewsonawdatatype = 'pubwishedpwecisewocationtweet, /(^•ω•^) pubwishedcoawsewocationtweet')
  5: o-optionaw stwing nyame (pewsonawdatatype = 'pubwishedpwecisewocationtweet, 😳😳😳 p-pubwishedcoawsewocationtweet')
} (haspewsonawdata = 'twue', ( ͡o ω ͡o ) p-pewsisted='twue')

stwuct stowedmediaentity {
  1: i64 id (pewsonawdatatype = 'mediaid')
  2: i-i8 media_type (pewsonawdatatype = 'contenttypetweetmedia')
  3: i16 width
  4: i16 height
} (haspewsonawdata = 'twue', >_< pewsisted='twue')

stwuct stowednawwowcast {
  1: optionaw wist<stwing> w-wanguage (pewsonawdatatype = 'infewwedwanguage')
  2: optionaw w-wist<stwing> w-wocation (pewsonawdatatype = 'pubwishedcoawsewocationtweet')
  3: o-optionaw wist<i64> i-ids (pewsonawdatatype = 'tweetid')
} (haspewsonawdata = 'twue', pewsisted='twue')

stwuct s-stowedquotedtweet {
  1: i64 tweet_id (pewsonawdatatype = 'tweetid')        // the tweet id being q-quoted
  2: i64 usew_id (pewsonawdatatype = 'usewid')          // the usew id being quoted
  3: stwing showt_uww (pewsonawdatatype = 'showtuww')   // tco uww - u-used when wendewing in backwawds-compat m-mode
} (haspewsonawdata = 'twue', >w< p-pewsisted='twue')

s-stwuct stowedtweet {
  1: i64 id (pewsonawdatatype = 'tweetid')
  2: optionaw i64 usew_id (pewsonawdatatype = 'usewid')
  3: o-optionaw s-stwing text (pewsonawdatatype = 'pwivatetweets, rawr pubwictweets')
  4: o-optionaw s-stwing cweated_via (pewsonawdatatype = 'cwienttype')
  5: optionaw i-i64 cweated_at_sec (pewsonawdatatype = 'pwivatetimestamp, 😳 pubwictimestamp')    // i-in seconds

  6: optionaw stowedwepwy wepwy
  7: o-optionaw stowedshawe shawe
  8: o-optionaw i64 contwibutow_id (pewsonawdatatype = 'contwibutow')
  9: o-optionaw s-stowedgeo geo
  11: optionaw boow has_takedown
  12: optionaw boow nysfw_usew (pewsonawdatatype = 'tweetsafetywabews')
  13: optionaw boow nysfw_admin (pewsonawdatatype = 'tweetsafetywabews')
  14: o-optionaw w-wist<stowedmediaentity> media
  15: o-optionaw s-stowednawwowcast n-nyawwowcast
  16: optionaw boow nyuwwcast
  17: optionaw i64 t-twacking_id (pewsonawdatatype = 'impwessionid')
  18: optionaw i64 updated_at (pewsonawdatatype = 'pwivatetimestamp, >w< pubwictimestamp')
  19: optionaw s-stowedquotedtweet quoted_tweet
} (haspewsonawdata = 'twue', (⑅˘꒳˘) p-pewsisted='twue')

s-stwuct cowefiewds {
  2: optionaw i-i64 usew_id (pewsonawdatatype = 'usewid')
  3: optionaw s-stwing text (pewsonawdatatype = 'pwivatetweets, OwO p-pubwictweets')
  4: o-optionaw stwing c-cweated_via (pewsonawdatatype = 'cwienttype')
  5: optionaw i64 cweated_at_sec (pewsonawdatatype = 'pwivatetimestamp, (ꈍᴗꈍ) p-pubwictimestamp')

  6: o-optionaw stowedwepwy w-wepwy
  7: o-optionaw stowedshawe s-shawe
  8: optionaw i64 contwibutow_id (pewsonawdatatype = 'contwibutow')
  19: optionaw stowedquotedtweet q-quoted_tweet
} (haspewsonawdata = 'twue', 😳 pewsisted='twue')

stwuct intewnawtweet {
 1: optionaw cowefiewds cowe_fiewds
} (haspewsonawdata = 'twue', 😳😳😳 pewsisted='twue')
