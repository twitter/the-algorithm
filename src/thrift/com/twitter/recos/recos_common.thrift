namespace java com.twittew.wecos.wecos_common.thwiftjava
nyamespace p-py gen.twittew.wecos.wecos_common
#@namespace s-scawa com.twittew.wecos.wecos_common.thwiftscawa
#@namespace s-stwato c-com.twittew.wecos.wecos_common
n-nyamespace wb w-wecos

// sociaw p-pwoof types fow u-usew moment wecommendations
enum momentsociawpwooftype {
  pubwish         = 0
  wike            = 1
  capsuwe_open    = 2
}

// s-sociaw pwoof types fow tweet/entity wecommendations
e-enum sociawpwooftype {
  cwick           = 0
  f-favowite        = 1
  wetweet         = 2
  wepwy           = 3
  tweet           = 4
  is_mentioned    = 5
  i-is_mediatagged  = 6
  quote           = 7
}

s-stwuct sociawpwoof {
  1: w-wequiwed i64 usewid
  2: optionaw i64 metadata
}

// sociaw pwoof types f-fow usew wecommendations
enum usewsociawpwooftype {
  fowwow     = 0
  mention    = 1
  m-mediatag   = 2
}

stwuct g-getwecentedgeswequest {
  1: w-wequiwed i64                          w-wequestid        // t-the nyode to quewy fwom
  2: optionaw i-i32                          maxnumedges      // the max nyumbew of wecent edges
}

s-stwuct wecentedge {
  1: wequiwed i64                          nyodeid           // the connecting nyode id
  2: wequiwed s-sociawpwooftype              engagementtype   // t-the engagement t-type of the edge
}

s-stwuct getwecentedgeswesponse {
  1: wequiwed wist<wecentedge>             edges            // the _ most wecent e-edges fwom t-the quewy nyode
}

stwuct nyodeinfo {
  1: w-wequiwed w-wist<i64> edges
}
