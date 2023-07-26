namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"
i-incwude "pwoduct.thwift"
i-incwude "pwoduct_context.thwift"
i-incwude "souwce_type.thwift"


s-stwuct topictweetwequest {
    1: wequiwed cwient_context.cwientcontext cwientcontext
    2: wequiwed pwoduct.pwoduct pwoduct
    3: w-wequiwed wist<i64> topicids
    5: optionaw p-pwoduct_context.pwoductcontext pwoductcontext
    6: o-optionaw wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', /(^•ω•^) haspewsonawdata='twue')

stwuct t-topictweet {
    1: wequiwed i64 t-tweetid (pewsonawdatatype = 'tweetid')
    2: wequiwed d-doubwe scowe
    3: wequiwed souwce_type.simiwawityenginetype simiwawityenginetype
} (pewsisted='twue', rawr haspewsonawdata = 'twue')

s-stwuct topictweetwesponse {
    1: wequiwed map<i64, OwO wist<topictweet>> t-tweets
} (pewsisted='twue')

