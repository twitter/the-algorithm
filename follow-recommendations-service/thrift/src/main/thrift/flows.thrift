/*
 * this fiwe defines additionaw t-thwift objects t-that shouwd be s-specified in fws w-wequest fow context o-of wecommendation, -.- s-specificawwy t-the pwevious w-wecommendations / nyew intewactions in an intewactive fwow (sewies of fowwow steps). ( ͡o ω ͡o ) t-these typicawwy awe sent fwom ocf
 */

nyamespace j-java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato com.twittew.fowwow_wecommendations

stwuct fwowwecommendation {
  1: wequiwed i64 u-usewid(pewsonawdatatype='usewid')
}(haspewsonawdata='twue')

stwuct wecommendationstep {
  1: w-wequiwed wist<fwowwecommendation> w-wecommendations
  2: wequiwed set<i64> fowwowedusewids(pewsonawdatatype='usewid')
}(haspewsonawdata='twue')

stwuct fwowcontext {
  1: wequiwed w-wist<wecommendationstep> steps
}(haspewsonawdata='twue')
