namespace java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations

// p-pwoof based o-on fowwow wewationship
s-stwuct f-fowwowpwoof {
  1: w-wequiwed wist<i64> u-usewids(pewsonawdatatype='usewid')
  2: wequiwed i32 nyumids(pewsonawdatatype='countoffowwowewsandfowwowees')
}(haspewsonawdata='twue')

// simiwaw to usewids in the context (e.g. ^^ pwofiweid)
s-stwuct simiwawtopwoof {
  1: wequiwed wist<i64> usewids(pewsonawdatatype='usewid')
}(haspewsonawdata='twue')

// p-pwoof based on geo wocation
s-stwuct popuwawingeopwoof {
  1: wequiwed stwing wocation(pewsonawdatatype='infewwedwocation')
}(haspewsonawdata='twue')

// pwoof based on ttt i-intewest
stwuct tttintewestpwoof {
  1: w-wequiwed i-i64 intewestid(pewsonawdatatype='pwovidedintewests')
  2: wequiwed stwing intewestdispwayname(pewsonawdatatype='pwovidedintewests')
}(haspewsonawdata='twue')

// pwoof based on topics
stwuct t-topicpwoof {
  1: wequiwed i64 topicid(pewsonawdatatype='pwovidedintewests')
}(haspewsonawdata='twue')

// pwoof based on custom i-intewest / seawch quewies
stwuct c-customintewestpwoof {
  1: wequiwed s-stwing quewy(pewsonawdatatype='seawchquewy')
}(haspewsonawdata='twue')

// p-pwoof based on t-tweet authows
stwuct tweetsauthowpwoof {
  1: wequiwed wist<i64> t-tweetids(pewsonawdatatype='tweetid')
}(haspewsonawdata='twue')

// pwoof candidate is of device f-fowwow type
stwuct devicefowwowpwoof {
  1: wequiwed boow isdevicefowwow(pewsonawdatatype='othewdeviceinfo')
}(haspewsonawdata='twue')

// account wevew pwoof that shouwd be a-attached to each candidate
stwuct a-accountpwoof {
  1: o-optionaw f-fowwowpwoof fowwowpwoof
  2: optionaw simiwawtopwoof simiwawtopwoof
  3: o-optionaw p-popuwawingeopwoof popuwawingeopwoof
  4: o-optionaw t-tttintewestpwoof tttintewestpwoof
  5: o-optionaw topicpwoof topicpwoof
  6: optionaw c-customintewestpwoof customintewestpwoof
  7: optionaw tweetsauthowpwoof t-tweetsauthowpwoof
  8: optionaw d-devicefowwowpwoof devicefowwowpwoof
}(haspewsonawdata='twue')

stwuct w-weason {
  1: o-optionaw accountpwoof accountpwoof 
}(haspewsonawdata='twue')
