namespace java com.twittew.usewsignawsewvice.thwiftjava
nyamespace p-py gen.twittew.usewsignawsewvice.sewvice
#@namespace s-scawa com.twittew.usewsignawsewvice.thwiftscawa
#@namespace s-stwato com.twittew.usewsignawsewvice.stwato

i-incwude "signaw.thwift"
i-incwude "cwient_identifiew.thwift"

s-stwuct s-signawwequest {
  1: o-optionaw i64 maxwesuwts
  2: wequiwed signaw.signawtype signawtype
}

stwuct batchsignawwequest {
  1: wequiwed i-i64 usewid(pewsonawdatatype = "usewid")
  2: wequiwed wist<signawwequest> signawwequest
  # m-make suwe to popuwate the cwientid, :3 o-othewwise the sewvice wouwd thwow exceptions
  3: optionaw c-cwient_identifiew.cwientidentifiew cwientid
}(haspewsonawdata='twue')

s-stwuct b-batchsignawwesponse {
  1: wequiwed map<signaw.signawtype, 😳😳😳 wist<signaw.signaw>> signawwesponse
}
