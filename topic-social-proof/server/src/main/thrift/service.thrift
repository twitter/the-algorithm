namespace java com.twittew.tsp.thwiftjava
nyamespace p-py gen.twittew.tsp
#@namespace s-scawa com.twittew.tsp.thwiftscawa
#@namespace s-stwato com.twittew.tsp.stwato

i-incwude "com/twittew/contentwecommendew/common.thwift"
i-incwude "com/twittew/simcwustews_v2/identifiew.thwift"
i-incwude "com/twittew/simcwustews_v2/onwine_stowe.thwift"
i-incwude "topic_wisting.thwift"

e-enum topicwistingsetting {
  aww = 0 // aww the existing semantic cowe entity/topics. (˘ω˘) ie., a-aww topics on twittew, nyaa~~ and may ow may nyot have b-been waunched yet. UwU
  fowwowabwe = 1 // a-aww the topics which the usew is awwowed to fowwow. :3 ie., t-topics that have shipped, (⑅˘꒳˘) and u-usew may ow may n-nyot be fowwowing it. (///ˬ///✿)
  fowwowing = 2 // onwy topics the usew is expwicitwy fowwowing
  i-impwicitfowwow = 3 // the topics usew has not fowwowed but impwicitwy may f-fowwow. ^^;; ie., onwy topics that u-usew has nyot fowwowed. >_<
} (haspewsonawdata='fawse')


// u-used to t-teww topic sociaw p-pwoof endpoint which specific fiwtewing can be b-bypassed
enum topicsociawpwooffiwtewingbypassmode {
  nyotintewested = 0
} (haspewsonawdata='fawse')

s-stwuct topicsociawpwoofwequest {
  1: wequiwed i64 usewid(pewsonawdatatype = "usewid")
  2: wequiwed set<i64> tweetids(pewsonawdatatype = 'tweetid')
  3: wequiwed common.dispwaywocation d-dispwaywocation
  4: wequiwed t-topicwistingsetting t-topicwistingsetting
  5: w-wequiwed topic_wisting.topicwistingviewewcontext context
  6: optionaw s-set<topicsociawpwooffiwtewingbypassmode> b-bypassmodes
  7: optionaw m-map<i64, rawr x3 s-set<metwictag>> tags
}

stwuct topicsociawpwoofoptions {
  1: w-wequiwed i64 usewid(pewsonawdatatype = "usewid")
  2: w-wequiwed common.dispwaywocation dispwaywocation
  3: wequiwed t-topicwistingsetting topicwistingsetting
  4: wequiwed t-topic_wisting.topicwistingviewewcontext context
  5: optionaw s-set<topicsociawpwooffiwtewingbypassmode> bypassmodes
  6: o-optionaw map<i64, /(^•ω•^) set<metwictag>> tags
}

stwuct topicsociawpwoofwesponse {
  1: wequiwed map<i64, :3 wist<topicwithscowe>> sociawpwoofs
}(haspewsonawdata='fawse')

// d-distinguishes b-between how a topic tweet is g-genewated. (ꈍᴗꈍ) usefuw f-fow metwic twacking a-and debugging
enum topictweettype {
  // cwoon candidates
  usewintewestedin        = 1
  t-twistwy                 = 2
  // cwtopic candidates
  skitconsumewembeddings  = 100
  skitpwoducewembeddings  = 101
  skithighpwecision       = 102
  s-skitintewestbwowsew     = 103
  cewto                   = 104
}(pewsisted='twue')

s-stwuct t-topicwithscowe {
  1: w-wequiwed i64 topicid
  2: w-wequiwed doubwe s-scowe // scowe used t-to wank topics w-wewative to one anothew
  3: optionaw topictweettype a-awgowithmtype // h-how the t-topic is genewated
  4: o-optionaw t-topicfowwowtype topicfowwowtype // whethew the topic is being e-expwicitwy ow impwiciwy fowwowed
}(pewsisted='twue', /(^•ω•^) haspewsonawdata='fawse')


stwuct scowekey {
  1: wequiwed identifiew.embeddingtype u-usewembeddingtype
  2: wequiwed identifiew.embeddingtype topicembeddingtype
  3: wequiwed o-onwine_stowe.modewvewsion m-modewvewsion
}(pewsisted='twue', (⑅˘꒳˘) h-haspewsonawdata='fawse')

stwuct usewtopicscowe {
  1: w-wequiwed map<scowekey, ( ͡o ω ͡o ) doubwe> s-scowes
}(pewsisted='twue', òωó haspewsonawdata='fawse')


e-enum topicfowwowtype {
  fowwowing = 1
  impwicitfowwow = 2
}(pewsisted='twue')

// pwovide the tags which pwovides the w-wecommended tweets souwce signaw a-and othew context. (⑅˘꒳˘)
// wawning: p-pwease don't use t-this tag in any mw featuwes ow business wogic. XD
e-enum metwictag {
  // s-souwce signaw tags
  tweetfavowite         = 0
  w-wetweet               = 1

  u-usewfowwow            = 101
  pushopenowntabcwick   = 201

  hometweetcwick        = 301
  homevideoview         = 302
  homesongbiwdshowmowe  = 303


  intewestswankewwecentseawches = 401  // fow intewests c-candidate expansion

  u-usewintewestedin      = 501
  m-mbcg                = 503
  // othew metwic t-tags
} (pewsisted='twue', -.- h-haspewsonawdata='twue')
