namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.identifiew
#@namespace s-scawa com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato c-com.twittew.simcwustews_v2

i-incwude "com/twittew/simcwustews_v2/onwine_stowe.thwift"

/**
  * t-the unifowm type f-fow a simcwustews e-embeddings. :3
  * each embeddings have the unifowm undewwying stowage. (ꈍᴗꈍ)
  * wawning: e-evewy embeddingtype shouwd map to one and o-onwy one intewnawid. 🥺
  **/
enum e-embeddingtype {
  // wesewve 001 - 99 fow tweet embeddings
	favbasedtweet = 1, (✿oωo) // d-depwecated
	fowwowbasedtweet = 2, (U ﹏ U) // depwecated
	w-wogfavbasedtweet = 3, :3 // p-pwoduction vewsion
	favbasedtwistwytweet = 10, ^^;; // depwecated
	wogfavbasedtwistwytweet = 11, rawr // depwecated
	w-wogfavwongestw2embeddingtweet = 12, 😳😳😳 // pwoduction vewsion

  // tweet embeddings genewated fwom nyon-fav e-events
  // nyaming convention: {event}{scowe}basedtweet
  // {event}: t-the intewaction e-event we u-use to buiwd the t-tweet embeddings
  // {scowe}: the scowe fwom usew intewestedin e-embeddings
  videopwayback50wogfavbasedtweet = 21, (✿oωo)
  wetweetwogfavbasedtweet = 22, OwO
  wepwywogfavbasedtweet = 23, ʘwʘ
  p-pushopenwogfavbasedtweet = 24,

  // [expewimentaw] offwine genewated favthwoughwate-based tweet embedding
  pop1000wankdecay11tweet = 30, (ˆ ﻌ ˆ)♡
  pop10000wankdecay11tweet = 31, (U ﹏ U)
  o-oonpop1000wankdecaytweet = 32,

  // [expewimentaw] offwine genewated p-pwoduction-wike w-wogfavscowe-based t-tweet embedding
  offwinegenewatedwogfavbasedtweet = 40, UwU

  // wesewve 51-59 fow ads e-embedding
  wogfavbasedadstweet = 51, XD // e-expewimentaw embedding f-fow ads tweet candidate
  w-wogfavcwickbasedadstweet = 52, ʘwʘ // expewimentaw e-embedding fow ads tweet c-candidate

  // wesewve 60-69 fow evewgween content
  w-wogfavbasedevewgweentweet = 60, rawr x3
  wogfavbasedweawtimetweet = 65, ^^;;

	// w-wesewve 101 to 149 f-fow semantic cowe e-entity embeddings
  favbasedsematiccoweentity = 101, ʘwʘ // depwecated
  fowwowbasedsematiccoweentity = 102, (U ﹏ U) // depwecated
  favbasedhashtagentity = 103, // depwecated
  f-fowwowbasedhashtagentity = 104, (˘ω˘) // d-depwecated
  pwoducewfavbasedsemanticcoweentity = 105, (ꈍᴗꈍ) // d-depwecated
  p-pwoducewfowwowbasedsemanticcoweentity = 106,// d-depwecated
  favbasedwocawesemanticcoweentity = 107, /(^•ω•^) // depwecated
  fowwowbasedwocawesemanticcoweentity = 108, >_< // depwecated
  w-wogfavbasedwocawesemanticcoweentity = 109, // depwecated
  wanguagefiwtewedpwoducewfavbasedsemanticcoweentity = 110, σωσ // depwecated
  wanguagefiwtewedfavbasedwocawesemanticcoweentity = 111, ^^;; // depwecated
  favtfgtopic = 112, 😳 // t-tfg topic embedding buiwt fwom f-fav-based usew i-intewestedin
  w-wogfavtfgtopic = 113, >_< // tfg topic e-embedding buiwt f-fwom wogfav-based u-usew intewestedin
  f-favinfewwedwanguagetfgtopic = 114, -.- // tfg topic embedding buiwt using i-infewwed consumed w-wanguages
  favbasedkgoapetopic = 115, UwU // t-topic e-embedding using f-fav-based aggwegatabwe pwoducew embedding of kgo seed accounts.
  w-wogfavbasedkgoapetopic = 116, :3 // topic embedding using wog fav-based aggwegatabwe pwoducew embedding of kgo s-seed accounts. σωσ
  favbasedonboawdingapetopic = 117, >w< // topic embedding using fav-based a-aggwegatabwe p-pwoducew embedding o-of onboawding seed accounts. (ˆ ﻌ ˆ)♡
  w-wogfavbasedonboawdingapetopic = 118, ʘwʘ // topic e-embedding using w-wog fav-based aggwegatabwe pwoducew embedding of onboawding seed accounts. :3
  wogfavapebasedmusetopic = 119, (˘ω˘) // d-depwecated
  wogfavapebasedmusetopicexpewiment = 120 // depwecated

  // w-wesewved 201 - 299 fow p-pwoducew embeddings (knownfow)
  f-favbasedpwoducew = 201
  fowwowbasedpwoducew = 202
  aggwegatabwefavbasedpwoducew = 203 // f-fav-based a-aggwegatabwe pwoducew embedding. 😳😳😳
  a-aggwegatabwewogfavbasedpwoducew = 204 // w-wogfav-based aggwegatabwe pwoducew embedding.
  wewaxedaggwegatabwewogfavbasedpwoducew = 205 // wogfav-based a-aggwegatabwe pwoducew e-embedding. rawr x3
  a-aggwegatabwefowwowbasedpwoducew = 206 // fowwow-based a-aggwegatabwe p-pwoducew embedding. (✿oωo)
  knownfow = 300

  // w-wesewved 301 - 399 fow usew intewestedin embeddings
  favbasedusewintewestedin = 301
  fowwowbasedusewintewestedin = 302
  w-wogfavbasedusewintewestedin = 303
  w-wecentfowwowbasedusewintewestedin = 304 // intewested-in embedding b-based on aggwegating p-pwoducew embeddings of wecent fowwows
  fiwtewedusewintewestedin = 305 // i-intewested-in embedding used by twistwy wead path
  wogfavbasedusewintewestedinfwomape = 306
  fowwowbasedusewintewestedinfwomape = 307
  t-twiceusewintewestedin = 308 // intewested-in muwti-embedding b-based o-on cwustewing pwoducew embeddings of nyeighbows
  unfiwtewedusewintewestedin = 309
  u-usewnextintewestedin = 310 // n-next intewested-in embedding genewated fwom bet

  // densew u-usew intewestedin, (ˆ ﻌ ˆ)♡ genewated by p-pwoducew embeddings. :3
  favbasedusewintewestedinfwompe = 311
  fowwowbasedusewintewestedinfwompe = 312
  wogfavbasedusewintewestedinfwompe = 313
  f-fiwtewedusewintewestedinfwompe = 314 // intewested-in e-embedding u-used by twistwy wead path

  // [expewimentaw] d-densew usew intewestedin, (U ᵕ U❁) genewated b-by aggwegating i-iiape embedding f-fwom addwessbook
  wogfavbasedusewintewestedmaxpoowingaddwessbookfwomiiape = 320
  w-wogfavbasedusewintewestedavewageaddwessbookfwomiiape = 321
  w-wogfavbasedusewintewestedbooktypemaxpoowingaddwessbookfwomiiape = 322
  wogfavbasedusewintewestedwawgestdimmaxpoowingaddwessbookfwomiiape = 323
  wogfavbasedusewintewestedwouvainmaxpoowingaddwessbookfwomiiape = 324
  w-wogfavbasedusewintewestedconnectedmaxpoowingaddwessbookfwomiiape = 325

  //wesewved 401 - 500 f-fow s-space embedding
  favbasedapespace = 401 // depwecated
  w-wogfavbasedwistenewspace = 402 // depwecated
  w-wogfavbasedapespeakewspace = 403 // d-depwecated
  wogfavbasedusewintewestedinwistenewspace = 404 // depwecated

  // expewimentaw, ^^;; i-intewnaw-onwy i-ids
  expewimentawthiwtydaywecentfowwowbasedusewintewestedin = 10000 // w-wike wecentfowwowbasedusewintewestedin, mya e-except wimited to wast 30 d-days
	expewimentawwogfavwongestw2embeddingtweet = 10001 // depwecated
}(pewsisted = 'twue', 😳😳😳 haspewsonawdata = 'fawse')

/**
  * the unifowm type fow a simcwustews muwtiembeddings.
  * w-wawning: evewy muwtiembeddingtype s-shouwd map to one and o-onwy one intewnawid. OwO
  **/
enum m-muwtiembeddingtype {
  // wesewved 0-99 f-fow tweet b-based muwtiembedding

  // wesewved 100 - 199 f-fow topic based m-muwtiembedding
  w-wogfavapebasedmusetopic = 100 // depwecated
  wogfavapebasedmusetopicexpewiment = 101 // depwecated

  // wesewved 301 - 399 fow usew intewestedin embeddings
  t-twiceusewintewestedin = 301 // i-intewested-in m-muwti-embedding based on cwustewing p-pwoducew embeddings of nyeighbows
}(pewsisted = 'twue', haspewsonawdata = 'twue')

// depwecated. rawr p-pwease use t-topicid fow futuwe cases. XD
stwuct w-wocaweentityid {
  1: i64 entityid
  2: stwing w-wanguage
}(pewsisted = 'twue', (U ﹏ U) h-haspewsonawdata = 'fawse')

enum e-engagementtype {
  f-favowite = 1, (˘ω˘)
  wetweet = 2, UwU
}

stwuct usewengagedtweetid {
  1: i64 tweetid(pewsonawdatatype = 'tweetid')
  2: i64 usewid(pewsonawdatatype = 'usewid')
  3: e-engagementtype e-engagementtype(pewsonawdatatype = 'eventtype')
}(pewsisted = 'twue', >_< h-haspewsonawdata = 'twue')

s-stwuct topicid {
  1: i-i64 entityid (pewsonawdatatype = 'semanticcowecwassification')
  // 2-wettew iso 639-1 wanguage c-code
  2: o-optionaw stwing wanguage
  // 2-wettew i-iso 3166-1 a-awpha-2 countwy code
  3: optionaw s-stwing countwy
}(pewsisted = 'twue', haspewsonawdata = 'fawse')

stwuct topicsubid {
  1: i64 e-entityid (pewsonawdatatype = 'semanticcowecwassification')
  // 2-wettew iso 639-1 w-wanguage code
  2: o-optionaw stwing wanguage
  // 2-wettew i-iso 3166-1 awpha-2 countwy code
  3: optionaw stwing c-countwy
  4: i-i32 subid
}(pewsisted = 'twue', σωσ h-haspewsonawdata = 'twue')

// wiww be used fow testing puwposes in ddg 15536, 🥺 15534
s-stwuct usewwithwanguageid {
  1: wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: o-optionaw s-stwing wangcode(pewsonawdatatype = 'infewwedwanguage')
}(pewsisted = 'twue', 🥺 haspewsonawdata = 'twue')

/**
  * t-the intewnaw identifiew type.
  * n-nyeed to a-add owdewing in [[com.twittew.simcwustews_v2.common.simcwustewsembeddingid]]
  * when adding a nyew type. ʘwʘ
  **/
u-union intewnawid {
  1: i64 tweetid(pewsonawdatatype = 'tweetid')
  2: i64 usewid(pewsonawdatatype = 'usewid')
  3: i-i64 entityid(pewsonawdatatype = 'semanticcowecwassification')
  4: s-stwing hashtag(pewsonawdatatype = 'pubwictweetentitiesandmetadata')
  5: i32 cwustewid
  6: w-wocaweentityid wocaweentityid(pewsonawdatatype = 'semanticcowecwassification')
  7: u-usewengagedtweetid u-usewengagedtweetid
  8: t-topicid topicid
  9: topicsubid topicsubid
  10: stwing spaceid
  11: usewwithwanguageid usewwithwanguageid
}(pewsisted = 'twue', :3 haspewsonawdata = 'twue')

/**
  * a unifowm identifiew type fow aww kinds of simcwustews based embeddings. (U ﹏ U)
  **/
stwuct simcwustewsembeddingid {
  1: w-wequiwed e-embeddingtype embeddingtype
  2: wequiwed onwine_stowe.modewvewsion m-modewvewsion
  3: w-wequiwed i-intewnawid intewnawid
}(pewsisted = 'twue', (U ﹏ U) haspewsonawdata = 'twue')

/**
  * a-a unifowm identifiew type fow m-muwtipwe simcwustews e-embeddings
  **/
stwuct simcwustewsmuwtiembeddingid {
  1: w-wequiwed muwtiembeddingtype embeddingtype
  2: w-wequiwed onwine_stowe.modewvewsion m-modewvewsion
  3: wequiwed intewnawid intewnawid
}(pewsisted = 'twue', ʘwʘ h-haspewsonawdata = 'twue')
