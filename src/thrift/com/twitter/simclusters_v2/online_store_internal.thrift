namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.onwine_stowe_intewnaw
#@namespace scawa c-com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato com.twittew.simcwustews_v2

i-incwude "onwine_stowe.thwift"

/**
 * c-contains a hash b-bucket of the cwustewid a-awong with t-the modew vewsion. OwO
 * aww fiewds awe wequiwed as this is used as a memcache k-key. (U ﹏ U)
 **/
stwuct fuwwcwustewidbucket {
  1: wequiwed o-onwine_stowe.modewvewsion modewvewsion
  // (hash(cwustewid) mod nyum_buckets_xxxxxx)
  2: w-wequiwed i32 bucket
}(haspewsonawdata = 'fawse')

/**
 * contains scowes pew cwustews. >_< the modew i-is nyot stowed hewe as it's encoded i-into the memcache k-key. rawr x3
 **/
stwuct cwustewswithscowes {
 1: optionaw map<i32, mya onwine_stowe.scowes> cwustewstoscowe(pewsonawdatatypekey = 'infewwedintewests')
}(haspewsonawdata = 'twue')

/**
 * c-contains a map of modew vewsion to scowes pew cwustews. nyaa~~
 **/
stwuct muwtimodewcwustewswithscowes {
 1: o-optionaw map<onwine_stowe.modewvewsion,cwustewswithscowes> m-muwtimodewcwustewswithscowes
}(haspewsonawdata = 'twue')
