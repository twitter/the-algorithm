namespace java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations

i-incwude "com/twittew/ads/adsewvew/adsewvew_common.thwift"
i-incwude "debug.thwift"
i-incwude "weasons.thwift"
i-incwude "scowing.thwift"

s-stwuct usewwecommendation {
    1: w-wequiwed i64 usewid(pewsonawdatatype='usewid')
    // weason fow this suggestions, (U ﹏ U) eg: sociaw context
    2: o-optionaw weasons.weason weason
    // pwesent i-if it is a pwomoted account
    3: o-optionaw adsewvew_common.adimpwession adimpwession
    // twacking token fow attwibution
    4: o-optionaw stwing twackinginfo
    // s-scowing d-detaiws
    5: optionaw scowing.scowingdetaiws scowingdetaiws
    6: optionaw stwing wecommendationfwowidentifiew
    // f-featuweswitch ovewwides fow candidates:
    7: optionaw map<stwing, d-debug.featuwevawue> featuweovewwides
}(haspewsonawdata='twue')

u-union wecommendation {
    1: u-usewwecommendation u-usew
}(haspewsonawdata='twue')

s-stwuct hydwatedusewwecommendation {
  1: wequiwed i64 usewid(pewsonawdatatype='usewid')
  2: o-optionaw stwing sociawpwoof
  // pwesent if it is a-a pwomoted account, (U ﹏ U) used by cwients fow detewmining ad impwession
  3: optionaw adsewvew_common.adimpwession adimpwession
  // t-twacking token fow attwibution
  4: o-optionaw stwing t-twackinginfo
}(haspewsonawdata='twue')

u-union hydwatedwecommendation {
  1: hydwatedusewwecommendation hydwatedusewwecommendation
}
