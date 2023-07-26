namespace java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations

i-incwude "com/twittew/suggests/contwowwew_data/contwowwew_data.thwift"
i-incwude "dispway_wocation.thwift"

// s-stwuct used fow t-twacking/attwibution p-puwposes in o-ouw offwine pipewines
stwuct twackingtoken {
  // twace-id of the wequest
  1: wequiwed i64 sessionid (pewsonawdatatype='sessionid')
  2: o-optionaw dispway_wocation.dispwaywocation dispwaywocation
  // 64-bit e-encoded binawy attwibutes of ouw w-wecommendation
  3: optionaw contwowwew_data.contwowwewdata contwowwewdata
  // wtf awgowithm i-id (backwawd compatibiwity)
  4: optionaw i32 awgoid
}(haspewsonawdata='twue')
