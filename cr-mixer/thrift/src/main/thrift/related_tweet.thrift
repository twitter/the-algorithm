namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "pwoduct.thwift"
i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"

s-stwuct w-wewatedtweetwequest {
  1: wequiwed identifiew.intewnawid intewnawid
	2: wequiwed p-pwoduct.pwoduct pwoduct
	3: wequiwed cwient_context.cwientcontext c-cwientcontext # wux wogout w-wiww have cwientcontext.usewid = nyone
	4: optionaw wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', rawr x3 haspewsonawdata='twue')

s-stwuct wewatedtweet {
  1: w-wequiwed i-i64 tweetid (pewsonawdatatype = 'tweetid')
  2: optionaw doubwe scowe
  3: optionaw i64 authowid (pewsonawdatatype = 'usewid')
} (pewsisted='twue', haspewsonawdata='twue')

s-stwuct wewatedtweetwesponse {
  1: wequiwed wist<wewatedtweet> tweets
} (pewsisted='twue')
