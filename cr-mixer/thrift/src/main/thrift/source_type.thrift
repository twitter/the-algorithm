namespace java com.twittew.cw_mixew.thwiftjava
#@namespace scawa c-com.twittew.cw_mixew.thwiftscawa
#@namespace s-stwato c-com.twittew.cw_mixew

// d-due t-to wegacy weason, nyaa~~ s-souwcetype used t-to wepwesent b-both souwcesignawtype and simiwawityenginetype
// hence, :3 you can see sevewaw souwcetype such as u-usewintewestedin, ( ͡o ω ͡o ) hashspace, etc. mya
// moving fowwawd, (///ˬ///✿) s-souwcetype wiww be used fow s-souwcesignawtype onwy. (˘ω˘) eg., tweetfavowite, ^^;; usewfowwow
// we wiww c-cweate a nyew simiwawityenginetype t-to sepawate t-them. (✿oωo) eg., simcwustewsann
enum souwcetype {
  // tweet based souwce signaw
  tweetfavowite       = 0
  w-wetweet             = 1
  twafficattwibution  = 2 // twaffic attwibution wiww be migwated o-ovew in q3
  owiginawtweet       = 3
  wepwy               = 4
  t-tweetshawe          = 5
  g-goodtweetcwick      = 6 // t-totaw dweww t-time > ny seconds aftew cwick on the tweet
  v-videotweetquawityview = 7
  videotweetpwayback50  = 8

  // usewid b-based souwce signaw (incwudes both pwoducew/consumew)
  usewfowwow               = 101
  usewwepeatedpwofiwevisit = 102

  cuwwentusew_depwecated   = 103

  weawgwaphoon             = 104
  f-fowwowwecommendation     = 105

  twiceusewid              = 106
  u-usewtwafficattwibutionpwofiwevisit = 107
  g-goodpwofiwecwick         = 108 // t-totaw dweww time > ny seconds aftew cwick into the pwofiwe page

  // (notification) t-tweet based s-souwce signaw
  nyotificationcwick   = 201

  // (home) t-tweet b-based souwce signaw
  hometweetcwick       = 301
  h-homevideoview        = 302
  homesongbiwdshowmowe = 303

  // t-topic based souwce signaw
  topicfowwow         = 401 // depwecated
  p-popuwawtopic        = 402 // depwecated

  // o-owd cw code
  usewintewestedin    = 501 // d-depwecated
  twiceintewestedin   = 502 // d-depwecated
  mbcg                = 503 // depwecated
  hashspace           = 504 // depwecated

  // owd cw code
  cwustew             = 601 // depwecated

  // seawch b-based souwce s-signaw
  seawchpwofiwecwick  = 701 // depwecated
  s-seawchtweetcwick    = 702 // d-depwecated

  // g-gwaph based souwce
  stwongtiepwediction      = 801 // stp
  twicecwustewsmembews     = 802
  wookawike                = 803 // depwecated
  weawgwaphin              = 804

  // c-cuwwent wequestew usew id. (U ﹏ U) it is onwy used fow scwibing. pwacehowdew vawue
  w-wequestusewid       = 1001
  // cuwwent wequest t-tweet id used in w-wewatedtweet. -.- pwacehowdew v-vawue
  wequesttweetid      = 1002

  // n-nyegative signaws
  t-tweetwepowt = 1101
  t-tweetdontwike = 1102
  t-tweetseefewew = 1103
  accountbwock = 1104
  accountmute = 1105

  // a-aggwegated s-signaws
  tweetaggwegation = 1201
  p-pwoducewaggwegation = 1202
} (pewsisted='twue', ^•ﻌ•^ h-haspewsonawdata='twue')

e-enum simiwawityenginetype {
  simcwustewsann              = 1
  tweetbasedusewtweetgwaph    = 2
  tweetbasedtwhinann          = 3
  f-fowwow2vecann               = 4 // consumewembeddingbasedfowwow2vec
  qig                         = 5
  offwinesimcwustewsann       = 6
  wookawikeutg_depwecated     = 7
  pwoducewbasedusewtweetgwaph = 8
  f-fwsutg_depwecated           = 9
  weawgwaphoonutg_depwecated  = 10
  consumewembeddingbasedtwhinann = 11
  twhincowwabfiwtew           = 12
  twiceutg_depwecated         = 13
  c-consumewembeddingbasedtwotowewann = 14
  t-tweetbasedbetann            = 15
  s-stputg_depwecated           = 16
  uteg                        = 17
  w-womw                        = 18
  consumewsbasedusewtweetgwaph  = 19
  tweetbasedusewvideogwaph    = 20
  c-cewtotopictweet             = 24
  c-consumewsbasedusewadgwaph   = 25
  tweetbasedusewadgwaph       = 26
  skittfgtopictweet           = 27
  consumewbasedwawsann        = 28
  pwoducewbasedusewadgwaph    = 29
  skithighpwecisiontopictweet = 30
  s-skitintewestbwowsewtopictweet = 31
  skitpwoducewbasedtopictweet   = 32
  e-expwowetwipoffwinesimcwustewstweets = 33
  diffusionbasedtweet = 34
  c-consumewsbasedusewvideogwaph  = 35

  // i-in nyetwowk
  eawwybiwdwecencybasedsimiwawityengine = 21
  eawwybiwdmodewbasedsimiwawityengine = 22
  eawwybiwdtensowfwowbasedsimiwawityengine = 23
  // c-composite
  t-tweetbasedunifiedsimiwawityengine    = 1001
  pwoducewbasedunifiedsimiwawityengine = 1002
} (pewsisted='twue')
