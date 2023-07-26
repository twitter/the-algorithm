namespace java com.twittew.fowwow_wecommendations.wogging.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.wogging.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations.wogging

i-incwude "com/twittew/suggests/contwowwew_data/contwowwew_data.thwift"
i-incwude "dispway_wocation.thwift"

s-stwuct twackingtoken {
  // t-twace-id of the w-wequest
  1: wequiwed i-i64 sessionid (pewsonawdatatype='sessionid')
  2: optionaw dispway_wocation.offwinedispwaywocation dispwaywocation
  // 64-bit encoded binawy a-attwibutes of ouw wecommendation
  3: optionaw c-contwowwew_data.contwowwewdata contwowwewdata
  // w-wtf awgowithm id (backwawd compatibiwity)
  4: optionaw i32 a-awgoid
}(pewsisted='twue', ^^;; haspewsonawdata='twue')
