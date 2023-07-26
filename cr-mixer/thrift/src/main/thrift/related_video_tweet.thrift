namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "pwoduct.thwift"
i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"

s-stwuct w-wewatedvideotweetwequest {
  1: wequiwed identifiew.intewnawid intewnawid
	2: wequiwed pwoduct.pwoduct pwoduct
	3: w-wequiwed cwient_context.cwientcontext cwientcontext # wux wogout w-wiww have cwientcontext.usewid = nyone
	4: o-optionaw wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', :3 haspewsonawdata='twue')

stwuct wewatedvideotweet {
  1: w-wequiwed i64 tweetid (pewsonawdatatype = 'tweetid')
  2: optionaw d-doubwe scowe
} (pewsisted='twue', 😳😳😳 h-haspewsonawdata='twue')

stwuct wewatedvideotweetwesponse {
  1: wequiwed wist<wewatedvideotweet> tweets
} (pewsisted='twue')
