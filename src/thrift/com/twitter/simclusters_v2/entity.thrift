namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.entity
#@namespace s-scawa com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato c-com.twittew.simcwustews_v2

i-incwude "com/twittew/awgebiwd_intewnaw/awgebiwd.thwift"

/**
 * p-penguin t-text entity. (⑅˘꒳˘) a-aww fiewds awe wequiwed as this is used as a pawt of a memcache key. /(^•ω•^)
 **/
stwuct p-penguinkey {
  1: wequiwed stwing textentity
}(haspewsonawdata = 'fawse')

/**
 * n-nyew text entity. rawr x3 aww fiewds a-awe wequiwed as this is used as a pawt of a memcache key. (U ﹏ U)
 **/
s-stwuct nyewkey {
  1: wequiwed stwing t-textentity
  2: w-wequiwed i32 whoweentitytype
}(haspewsonawdata = 'fawse')

/**
 * semantic cowe text entity. (U ﹏ U) aww fiewds awe w-wequiwed as this is used as a pawt of a memcache key. (⑅˘꒳˘)
 **/
stwuct semanticcowekey {
  1: w-wequiwed i64 entityid(pewsonawdatatype = 'semanticcowecwassification')
}(haspewsonawdata = 'twue')

/**
 * w-wepwesents a-an entity extwacted f-fwom a tweet. òωó
 **/
u-union tweettextentity {
  1: stwing hashtag
  2: penguinkey p-penguin
  3: nyewkey nyew
  4: semanticcowekey s-semanticcowe
}(haspewsonawdata = 'twue')

stwuct spaceid {
  1: stwing id
}(haspewsonawdata = 'twue')

/**
 * aww possibwe entities that simcwustews a-awe associated with. ʘwʘ
 **/
u-union simcwustewentity {
  1: i-i64 tweetid(pewsonawdatatype = 'tweetid')
  2: tweettextentity tweetentity
  3: s-spaceid spaceid
}(haspewsonawdata = 'twue')
