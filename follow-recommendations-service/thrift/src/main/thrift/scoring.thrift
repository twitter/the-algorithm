namespace java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations

i-incwude "com/twittew/mw/api/data.thwift"

s-stwuct c-candidatesouwcedetaiws {
  1: o-optionaw map<stwing, nyaa~~ d-doubwe> c-candidatesouwcescowes
  2: optionaw i32 pwimawysouwce
  3: optionaw map<stwing, i-i32> candidatesouwcewanks
}(haspewsonawdata='fawse')

stwuct scowe {
  1: wequiwed d-doubwe vawue
  2: optionaw stwing w-wankewid
  3: optionaw stwing scowetype
}(haspewsonawdata='fawse')

// contains (1) t-the mw-based heavy wankew a-and scowe (2) s-scowes and wankews in pwoducew expewiment fwamewowk
stwuct scowes {
  1: wequiwed w-wist<scowe> scowes
  2: optionaw stwing sewectedwankewid
  3: wequiwed boow isinpwoducewscowingexpewiment
}(haspewsonawdata='fawse')

stwuct w-wankinginfo {
  1: optionaw scowes s-scowes
  2: optionaw i-i32 wank
}(haspewsonawdata='fawse')

// t-this encapsuwates a-aww infowmation wewated to the wanking pwocess f-fwom genewation to scowing
stwuct scowingdetaiws {
    1: o-optionaw candidatesouwcedetaiws candidatesouwcedetaiws
    2: optionaw doubwe scowe
    3: optionaw data.datawecowd datawecowd
    4: o-optionaw wist<stwing> wankewids
    5: o-optionaw d-debugdatawecowd d-debugdatawecowd // this fiewd is nyot wogged as it's onwy used f-fow debugging
    6: o-optionaw map<stwing, (✿oωo) wankinginfo> i-infopewwankingstage  // scowing a-and wanking info pew wanking s-stage
}(haspewsonawdata='twue')

// exactwy t-the same as a data wecowd, ʘwʘ except that we stowe t-the featuwe nyame instead of the i-id
stwuct debugdatawecowd {
  1: optionaw set<stwing> b-binawyfeatuwes;                     // s-stowes binawy featuwes
  2: optionaw map<stwing, (ˆ ﻌ ˆ)♡ doubwe> continuousfeatuwes;         // stowes continuous featuwes
  3: o-optionaw map<stwing, 😳😳😳 i-i64> discwetefeatuwes;              // s-stowes discwete f-featuwes
  4: o-optionaw map<stwing, :3 stwing> stwingfeatuwes;             // stowes stwing featuwes
  5: o-optionaw map<stwing, OwO set<stwing>> spawsebinawyfeatuwes;  // stowes spawse binawy featuwes
  6: o-optionaw map<stwing, (U ﹏ U) map<stwing, d-doubwe>> s-spawsecontinuousfeatuwes; // s-spawse continuous f-featuwes
}(haspewsonawdata='twue')
