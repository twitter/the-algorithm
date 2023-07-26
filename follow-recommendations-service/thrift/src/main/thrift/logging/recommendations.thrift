namespace java com.twittew.fowwow_wecommendations.wogging.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.wogging.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations.wogging

i-incwude "com/twittew/ads/adsewvew/adsewvew_common.thwift"
i-incwude "weasons.thwift"
incwude "twacking.thwift"
i-incwude "scowing.thwift"

// o-offwine equaw o-of usewwecommendation
s-stwuct offwineusewwecommendation {
    1: wequiwed i64 usewid(pewsonawdatatype='usewid')
    // weason f-fow this suggestions, >_< eg: sociaw context
    2: o-optionaw weasons.weason weason
    // p-pwesent if it is a pwomoted account
    3: optionaw adsewvew_common.adimpwession a-adimpwession
  // twacking t-token (unsewiawized) f-fow attwibution
  4: optionaw twacking.twackingtoken twackingtoken
    // scowing detaiws
    5: o-optionaw scowing.scowingdetaiws scowingdetaiws
}(pewsisted='twue', rawr x3 haspewsonawdata='twue')

// offwine e-equaw of wecommendation
union offwinewecommendation {
    1: o-offwineusewwecommendation u-usew
}(pewsisted='twue', mya h-haspewsonawdata='twue')
