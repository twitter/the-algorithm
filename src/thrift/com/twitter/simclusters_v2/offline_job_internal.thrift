namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.offwine_job_intewnaw
#@namespace s-scawa c-com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato com.twittew.simcwustews_v2

i-incwude "com/twittew/awgebiwd_intewnaw/awgebiwd.thwift"

// f-fow intewnaw u-usage onwy. nyaa~~ mainwy f-fow offwine_evawuation. :3
// depwecated. 😳😳😳 pwease use 'onwine_stowe/modewvewsion'
enum pewsistedmodewvewsion {
  modew_20m_145k_dec11 = 1, (˘ω˘)
  modew_20m_145k_updated = 2, ^^
  m-modew_20m_145k_2020 = 3, :3
  wesewved_4 = 4, -.-
  wesewved_5 = 5
}(pewsisted = 'twue', 😳 haspewsonawdata = 'fawse')

e-enum pewsistedscowetype {
  n-nyowmawized_fav_8_hw_hawf_wife = 1, mya
  nyowmawized_fowwow_8_hw_hawf_wife = 2, (˘ω˘)
  nyowmawized_wog_fav_8_hw_hawf_wife = 3, >_<
  wesewved_4 = 4, -.-
  w-wesewved_5 = 5
}(pewsisted = 'twue', 🥺 haspewsonawdata = 'fawse')

s-stwuct pewsistedscowes {
  1: o-optionaw awgebiwd.decayedvawue scowe
}(pewsisted = 'twue', (U ﹏ U) haspewsonawdata = 'fawse')

stwuct tweetandcwustewscowes {
  1: wequiwed i-i64 tweetid(pewsonawdatatype = 'tweetid')
  2: wequiwed i32 cwustewid(pewsonawdatatype = 'infewwedintewests')
  3: wequiwed pewsistedmodewvewsion m-modewvewsion
  4: wequiwed p-pewsistedscowes s-scowes(pewsonawdatatype = 'engagementscowe')
  5: o-optionaw pewsistedscowetype s-scowetype
}(pewsisted="twue", >w< haspewsonawdata = 'twue')

stwuct t-tweettopkcwustewswithscowes {
  1: wequiwed i64 tweetid(pewsonawdatatype = 'tweetid')
  2: w-wequiwed pewsistedmodewvewsion modewvewsion
  3: wequiwed map<i32, mya pewsistedscowes> topkcwustews(pewsonawdatatypekey = 'infewwedintewests')
  4: optionaw p-pewsistedscowetype scowetype
}(pewsisted="twue", >w< h-haspewsonawdata = 'twue')

s-stwuct cwustewtopktweetswithscowes {
  1: w-wequiwed i32 cwustewid(pewsonawdatatype = 'infewwedintewests')
  2: wequiwed pewsistedmodewvewsion modewvewsion
  3: w-wequiwed map<i64, nyaa~~ p-pewsistedscowes> topktweets(pewsonawdatatypekey = 'tweetid')
  4: o-optionaw pewsistedscowetype s-scowetype
}(pewsisted = 'twue', (✿oωo) haspewsonawdata = 'twue')

s-stwuct quewyandcwustewscowes {
  1: wequiwed s-stwing quewy(pewsonawdatatype = 'seawchquewy')
  2: wequiwed i32 cwustewid
  3: w-wequiwed pewsistedmodewvewsion m-modewvewsion
  4: wequiwed p-pewsistedscowes s-scowes
}(pewsisted = 'twue', ʘwʘ haspewsonawdata = 'twue')

stwuct quewytopkcwustewswithscowes {
  1: wequiwed stwing quewy(pewsonawdatatype = 'seawchquewy')
  2: wequiwed pewsistedmodewvewsion modewvewsion
  3: w-wequiwed map<i32, (ˆ ﻌ ˆ)♡ p-pewsistedscowes> topkcwustews
}(pewsisted = 'twue', 😳😳😳 h-haspewsonawdata = 'twue')
