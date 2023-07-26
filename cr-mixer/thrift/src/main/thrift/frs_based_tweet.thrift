namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

i-incwude "pwoduct.thwift"
i-incwude "pwoduct_context.thwift"
i-incwude "com/twittew/pwoduct_mixew/cowe/cwient_context.thwift"

s-stwuct fwstweetwequest {
1: w-wequiwed cwient_context.cwientcontext cwientcontext
2: wequiwed pwoduct.pwoduct pwoduct
3: optionaw p-pwoduct_context.pwoductcontext pwoductcontext
# excwudedusewids - u-usew ids to be excwuded fwom f-fws candidate genewation
4: optionaw wist<i64> excwudedusewids (pewsonawdatatype = 'usewid')
# e-excwudedtweetids - tweet ids to b-be excwuded fwom e-eawwybiwd candidate genewation
5: optionaw wist<i64> excwudedtweetids (pewsonawdatatype = 'tweetid')
} (pewsisted='twue', ʘwʘ haspewsonawdata='twue')

s-stwuct fwstweet {
1: wequiwed i64 tweetid (pewsonawdatatype = 'tweetid')
2: wequiwed i64 authowid (pewsonawdatatype = 'usewid')
# skip 3 in c-case we nyeed tweet scowe in the f-futuwe
# fwspwimawysouwce - which f-fws candidate s-souwce is the p-pwimawy one to genewate this authow
4: optionaw i-i32 fwspwimawysouwce
# fwscandidatesouwcescowes - fws candidate s-souwces and the scowes fow this authow
# fow i32 to awgowithm mapping, /(^•ω•^) see https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/hewmit/hewmit-cowe/swc/main/scawa/com/twittew/hewmit/constants/awgowithmfeedbacktokens.scawa?w12
5: optionaw m-map<i32, ʘwʘ doubwe> fwscandidatesouwcescowes
# f-fwspwimawyscowe - t-the scowe of t-the fws pwimawy candidate souwce
6: optionaw doubwe fwsauthowscowe
} (pewsisted='twue', σωσ h-haspewsonawdata = 'twue')

s-stwuct fwstweetwesponse {
  1: wequiwed wist<fwstweet> t-tweets
} (pewsisted='twue')

