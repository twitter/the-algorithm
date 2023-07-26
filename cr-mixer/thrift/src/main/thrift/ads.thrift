namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "pwoduct.thwift"
i-incwude "pwoduct_context.thwift"

i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
i-incwude "com/twittew/ads/schema/shawed.thwift"

s-stwuct adswequest {
	1: wequiwed cwient_context.cwientcontext cwientcontext
	2: wequiwed pwoduct.pwoduct p-pwoduct
	# pwoduct-specific pawametews s-shouwd be pwaced in the pwoduct c-context
	3: optionaw pwoduct_context.pwoductcontext pwoductcontext
	4: optionaw w-wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', rawr x3 haspewsonawdata='twue')

s-stwuct a-adswesponse {
  1: wequiwed wist<adtweetwecommendation> ads
} (pewsisted='twue')

stwuct adtweetwecommendation {
  1: wequiwed i-i64 tweetid (pewsonawdatatype = 'tweetid')
  2: wequiwed doubwe scowe
  3: optionaw wist<wineiteminfo> wineitems

} (pewsisted='twue')

s-stwuct wineiteminfo {
  1: w-wequiwed i64 w-wineitemid (pewsonawdatatype = 'wineitemid')
  2: w-wequiwed shawed.wineitemobjective w-wineitemobjective
} (pewsisted='twue')
