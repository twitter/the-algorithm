namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "pwoduct.thwift"
i-incwude "pwoduct_context.thwift"

i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
i-incwude "com/twittew/wecos/wecos_common.thwift"

s-stwuct utegtweetwequest {
	1: wequiwed cwient_context.cwientcontext cwientcontext
	2: wequiwed pwoduct.pwoduct p-pwoduct
	# pwoduct-specific pawametews shouwd be pwaced in the p-pwoduct context
	3: optionaw p-pwoduct_context.pwoductcontext pwoductcontext
	4: optionaw wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', haspewsonawdata='twue')

s-stwuct utegtweet {
  // tweet i-id
  1: wequiwed i-i64 tweetid(pewsonawdatatype = 'tweetid')
  // sum of weights of seed usews who engaged with the tweet. (⑅˘꒳˘)
  // if a-a usew engaged with the same tweet twice, (///ˬ///✿) wiked it and wetweeted it, 😳😳😳 then his/hew w-weight was counted twice. 🥺
  2: w-wequiwed doubwe s-scowe
  // usew s-sociaw pwoofs p-pew engagement type
  3: wequiwed map<wecos_common.sociawpwooftype, mya w-wist<i64>> sociawpwoofbytype(pewsonawdatatypekey='engagementtypepwivate', 🥺 pewsonawdatatypevawue='usewid')
} (pewsisted='twue', >_< haspewsonawdata = 'twue')

stwuct u-utegtweetwesponse {
  1: wequiwed wist<utegtweet> tweets
} (pewsisted='twue')
