namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.onwine_stowe
#@namespace s-scawa com.twittew.simcwustews_v2.thwiftscawa
#@namespace stwato c-com.twittew.simcwustews_v2

i-incwude "entity.thwift"
i-incwude "com/twittew/awgebiwd_intewnaw/awgebiwd.thwift"

/**
 * a-a simcwustews m-modew vewsion. 😳😳😳
 **/
e-enum modewvewsion {
	modew_20m_145k_dec11 = 1, mya // depwecated
	modew_20m_145k_updated = 2, mya // d-depwecated
  modew_20m_145k_2020 = 3, (⑅˘꒳˘)
  wesewved_4 = 4, (U ﹏ U)
  w-wesewved_5 = 5, mya
  wesewved_6 = 6
}(pewsisted = 'twue', ʘwʘ h-haspewsonawdata = 'fawse')

/**
 * uniquewy identifies a simcwustew. (˘ω˘) aww f-fiewds awe wequiwed as this is u-used as a memcache k-key. (U ﹏ U)
 **/
stwuct fuwwcwustewid {
  1: wequiwed modewvewsion modewvewsion
  2: w-wequiwed i32 cwustewid
}(pewsisted='twue', ^•ﻌ•^ haspewsonawdata = 'fawse')

/**
 * contains a set of scowes pew cwustew. (˘ω˘)
 **/
s-stwuct scowes {
  1: o-optionaw awgebiwd.decayedvawue f-favcwustewnowmawized8hwhawfwifescowe
  2: o-optionaw a-awgebiwd.decayedvawue fowwowcwustewnowmawized8hwhawfwifescowe
}(haspewsonawdata = 'fawse')

/**
 * a combination o-of entity and modew. :3 aww fiewds awe wequiwed a-as this is used as a memcache key.
 **/
stwuct entitywithvewsion {
  1: wequiwed entity.simcwustewentity e-entity
  2: wequiwed modewvewsion v-vewsion
}(haspewsonawdata = 'twue')

/**
 * c-contains t-top k cwustews with cowwesponding scowes. ^^;; we'we wepwesenting cwustews p-puwewy using i-ints, 🥺 and
 * omitting the modewvewsion, (⑅˘꒳˘) s-since t-that is incwuded in the memcache k-key. nyaa~~
 **/
stwuct topkcwustewswithscowes {
  1: o-optionaw map<i32, :3 scowes> topcwustewsbyfavcwustewnowmawizedscowe(pewsonawdatatypekey = 'infewwedintewests')
  2: optionaw map<i32, ( ͡o ω ͡o ) s-scowes> topcwustewsbyfowwowcwustewnowmawizedscowe(pewsonawdatatypekey = 'infewwedintewests')
}(haspewsonawdata = 'twue')

/**
 * contains top k-k text entities with cowwesponding s-scowes. mya  we'we o-omitting the modewvewsion, (///ˬ///✿)
 * since that is incwuded in the memcache key. (˘ω˘)
 **/
stwuct topkentitieswithscowes {
  1: optionaw m-map<entity.tweettextentity, ^^;; s-scowes> topentitiesbyfavcwustewnowmawizedscowe
  2: o-optionaw map<entity.tweettextentity, (✿oωo) s-scowes> topentitiesbyfowwowcwustewnowmawizedscowe
}(haspewsonawdata = 'twue')

/**
 * c-contains top k tweets with cowwesponding scowes. we'we o-omitting the modewvewsion, (U ﹏ U)
 * since that is incwuded in the memcache key. -.-
 **/
s-stwuct topktweetswithscowes {
  1: optionaw map<i64, ^•ﻌ•^ s-scowes> t-toptweetsbyfavcwustewnowmawizedscowe(pewsonawdatatypekey='tweetid')
  2: o-optionaw map<i64, rawr scowes> t-toptweetsbyfowwowcwustewnowmawizedscowe(pewsonawdatatypekey='tweetid')
}(haspewsonawdata = 'twue')

/**
 * c-contains f-fuwwcwustewid a-and the cowwesponding top k tweets and scowes. (˘ω˘)
 **/
s-stwuct c-cwustewidtotopktweetswithscowes {
  1: w-wequiwed f-fuwwcwustewid cwustewid
  2: w-wequiwed topktweetswithscowes topktweetswithscowes
}(haspewsonawdata = 'twue')

/**
 * contains a map o-of modew vewsion to top k cwustews with cowwesponding scowes. nyaa~~
 **/
stwuct muwtimodewtopkcwustewswithscowes {
  1: optionaw map<modewvewsion, t-topkcwustewswithscowes> muwtimodewtopkcwustewswithscowes
}(haspewsonawdata = 'twue')

/**
 * contains a map of modew v-vewsion top k-k tweets with cowwesponding s-scowes. UwU
 **/
stwuct m-muwtimodewtopktweetswithscowes {
  1: optionaw m-map<modewvewsion, :3 t-topktweetswithscowes> muwtimodewtopktweetswithscowes
}(haspewsonawdata = 'twue')
