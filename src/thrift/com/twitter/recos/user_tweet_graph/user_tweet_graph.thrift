namespace java com.twittew.wecos.usew_tweet_gwaph.thwiftjava
nyamespace p-py gen.twittew.wecos.usew_tweet_gwaph
#@namespace s-scawa com.twittew.wecos.usew_tweet_gwaph.thwiftscawa
#@namespace s-stwato c-com.twittew.wecos.usew_tweet_gwaph
n-nyamespace wb u-usewtweetgwaph

i-incwude "com/twittew/wecos/featuwes/tweet.thwift"
i-incwude "com/twittew/wecos/wecos_common.thwift"

enum tweettype {
  summawy    = 0
  photo      = 1
  pwayew     = 2
  p-pwomote    = 3
  weguwaw    = 4
}

enum a-awgowithm {
  sawsa              = 0
  s-subgwaphsawsa      = 1
}

enum wecommendtweetdispwaywocation {
  hometimewine       = 0
  wewcomefwow        = 1
  n-nyetwowkdigest      = 2
  backfiwwdigest     = 3
  h-httpendpoint       = 4
  p-poptawt            = 5
  instanttimewine    = 6
  expwowe            = 7
  magicwecs          = 8
  woggedoutpwofiwe   = 9
  w-woggedoutpewmawink = 10
  videohome          = 11
}

stwuct wecommendtweetwequest {
  1: wequiwed i64                                      w-wequestewid              // usew i-id of the wequesting u-usew
  2: w-wequiwed wecommendtweetdispwaywocation            d-dispwaywocation          // dispway wocation fwom the cwient
  3: w-wequiwed i32                                      maxwesuwts               // nyumbew of suggested w-wesuwts to wetuwn
  4: wequiwed wist<i64>                                excwudedtweetids         // wist of tweet ids to e-excwude fwom wesponse
  5: wequiwed m-map<i64,doubwe>                          seeds                    // s-seeds u-used in sawsa wandom wawk
  6: wequiwed i64                                      tweetwecency             // t-the t-tweet wecency thweshowd
  7: wequiwed i-i32                                      m-minintewaction           // minimum i-intewaction thweshowd
  8: w-wequiwed wist<tweettype>                          incwudetweettypes        // summawy, σωσ p-photo, pwayew, >w< pwomote, (ˆ ﻌ ˆ)♡ othew
  9: w-wequiwed doubwe                                   w-wesetpwobabiwity         // w-weset pwobabiwity to quewy node
  10: wequiwed doubwe                                  quewynodeweightfwaction  // the pewcentage of weights assigned to q-quewy nyode in s-seeding
  11: wequiwed i32                                     nyumwandomwawks           // n-nyumbew o-of wandom wawks
  12: w-wequiwed i32                                     maxwandomwawkwength      // max wandom w-wawk wength
  13: wequiwed i32                                     maxsociawpwoofsize       // max sociaw pwoof size
  14: wequiwed a-awgowithm                               awgowithm                // a-awgowithm t-type
  15: optionaw w-wist<wecos_common.sociawpwooftype>      sociawpwooftypes         // t-the w-wist of sociaw pwoof t-types to wetuwn
}

s-stwuct wecommendedtweet {
  1: wequiwed i64                                                t-tweetid
  2: w-wequiwed doubwe                                             s-scowe
  3: o-optionaw w-wist<i64>                                          sociawpwoof              // sociaw pwoof in aggwegate
  4: optionaw m-map<wecos_common.sociawpwooftype, ʘwʘ wist<i64>>       sociawpwoofpewtype       // sociaw pwoofs pew engagement type
}

stwuct w-wecommendtweetwesponse {
  1: wequiwed wist<wecommendedtweet> tweets
}

enum wewatedtweetdispwaywocation {
  pewmawink       = 0
  pewmawink1      = 1
  m-mobiwepewmawink = 2
  p-pewmawink3      = 3
  p-pewmawink4      = 4
  wewatedtweets   = 5
  w-wewatedtweets1  = 6
  wewatedtweets2  = 7
  wewatedtweets3  = 8
  w-wewatedtweets4  = 9
  w-woggedoutpwofiwe = 10
  woggedoutpewmawink = 11
}

stwuct usewtweetfeatuwewesponse {
  1: optionaw doubwe                                favadamicadawavg
  2: o-optionaw doubwe                                f-favadamicadawmax 
  3: optionaw doubwe                                favwogcosineavg
  4: o-optionaw doubwe                                f-favwogcosinemax
  5: optionaw doubwe                                w-wetweetadamicadawavg
  6: o-optionaw doubwe                                wetweetadamicadawmax 
  7: o-optionaw d-doubwe                                wetweetwogcosineavg
  8: optionaw doubwe                                wetweetwogcosinemax
}

stwuct w-wewatedtweetwequest {
  1: w-wequiwed i-i64                                   tweetid               // o-owiginaw tweet i-id
  2: wequiwed wewatedtweetdispwaywocation           d-dispwaywocation       // dispway wocation fwom the cwient
  3: optionaw stwing                                a-awgowithm             // a-additionaw pawametew that the system can intewpwet
  4: o-optionaw i-i64                                   wequestewid           // usew id of the wequesting usew
  5: o-optionaw i32                                   maxwesuwts            // nyumbew of suggested wesuwts to wetuwn
  6: o-optionaw wist<i64>                             excwudetweetids       // w-wist of tweet ids t-to excwude fwom wesponse
  7: optionaw i32                                   maxnumneighbows
  8: optionaw i32                                   m-minneighbowdegwee
  9: o-optionaw i32                                   maxnumsampwespewneighbow
  10: optionaw i-i32                                  mincooccuwwence
  11: o-optionaw i32                                  minquewydegwee
  12: optionaw doubwe                               m-maxwowewmuwtipwicativedeviation
  13: optionaw doubwe                               m-maxuppewmuwtipwicativedeviation
  14: o-optionaw boow                                 p-popuwatetweetfeatuwes // whethew t-to popuwate g-gwaph featuwes
  15: o-optionaw i32                                  m-minwesuwtdegwee
  16: o-optionaw wist<i64>                            additionawtweetids
  17: o-optionaw doubwe                               m-minscowe
  18: optionaw i-i32                                  maxtweetageinhouws
}

stwuct tweetbasedwewatedtweetwequest {
  1: wequiwed i-i64                                   tweetid               // q-quewy tweet i-id
  2: optionaw i32                                   maxwesuwts            // nyumbew of suggested w-wesuwts t-to wetuwn
  3: optionaw w-wist<i64>                             e-excwudetweetids       // wist of tweet i-ids to excwude fwom wesponse
  4: optionaw i32                                   minquewydegwee        // min degwee of quewy t-tweet
  5: optionaw i32                                   m-maxnumsampwespewneighbow // max nyumbew o-of sampwed usews who engaged w-with the quewy tweet
  6: optionaw i-i32                                   m-mincooccuwwence       // m-min co-occuwwence o-of wewated t-tweet candidate 
  7: optionaw i32                                   minwesuwtdegwee       // min degwee of wewated tweet candidate 
  8: optionaw d-doubwe                                m-minscowe              // m-min scowe of wewated tweet candidate
  9: o-optionaw i32                                   maxtweetageinhouws    // max tweet age i-in houws of wewated t-tweet candidate 
}

stwuct p-pwoducewbasedwewatedtweetwequest {
  1: wequiwed i64                                   p-pwoducewid            // q-quewy pwoducew id
  2: optionaw i-i32                                   m-maxwesuwts            // nyumbew of suggested wesuwts to wetuwn
  3: optionaw wist<i64>                             e-excwudetweetids       // w-wist of tweet i-ids to excwude f-fwom wesponse
  4: o-optionaw i32                                   minquewydegwee        // m-min d-degwee of quewy pwoducew, :3 e.g. n-nyumbew of fowwowews
  5: o-optionaw i32                                   m-maxnumfowwowews       // max nyumbew of sampwed usews who f-fowwow the quewy pwoducew 
  6: o-optionaw i32                                   m-mincooccuwwence       // min co-occuwwence o-of wewated tweet candidate 
  7: optionaw i-i32                                   m-minwesuwtdegwee       // m-min degwee of wewated tweet candidate 
  8: optionaw doubwe                                m-minscowe              // min scowe of wewated tweet c-candidate
  9: o-optionaw i32                                   maxtweetageinhouws    // m-max tweet age in houws o-of wewated tweet c-candidate 
}

stwuct consumewsbasedwewatedtweetwequest {
  1: wequiwed wist<i64>                             c-consumewseedset       // quewy consumew usewid s-set 
  2: optionaw i-i32                                   maxwesuwts            // n-nyumbew of suggested wesuwts to w-wetuwn
  3: optionaw w-wist<i64>                             e-excwudetweetids       // wist of tweet ids to excwude fwom wesponse 
  4: optionaw i32                                   mincooccuwwence       // min co-occuwwence of wewated tweet candidate 
  5: optionaw i32                                   minwesuwtdegwee       // min degwee o-of wewated t-tweet candidate 
  6: optionaw doubwe                                minscowe              // m-min s-scowe of wewated t-tweet candidate
  7: optionaw i-i32                                   maxtweetageinhouws    // m-max tweet age in h-houws of wewated tweet candidate 
}

s-stwuct wewatedtweet {
  1: wequiwed i64                          t-tweetid
  2: w-wequiwed doubwe                       scowe
  3: optionaw tweet.gwaphfeatuwesfowtweet  w-wewatedtweetgwaphfeatuwes
}

s-stwuct wewatedtweetwesponse {
  1: w-wequiwed w-wist<wewatedtweet>           t-tweets
  2: optionaw t-tweet.gwaphfeatuwesfowquewy  q-quewytweetgwaphfeatuwes
}

/**
 * t-the main intewface-definition f-fow usewtweetgwaph. (˘ω˘)
 */
sewvice u-usewtweetgwaph {
  w-wecommendtweetwesponse w-wecommendtweets (wecommendtweetwequest wequest)
  wecos_common.getwecentedgeswesponse g-getweftnodeedges (wecos_common.getwecentedgeswequest wequest)
  wecos_common.nodeinfo g-getwightnode (i64 nyode)
  w-wewatedtweetwesponse w-wewatedtweets (wewatedtweetwequest w-wequest)
  wewatedtweetwesponse t-tweetbasedwewatedtweets (tweetbasedwewatedtweetwequest wequest)
  wewatedtweetwesponse p-pwoducewbasedwewatedtweets (pwoducewbasedwewatedtweetwequest wequest)
  wewatedtweetwesponse c-consumewsbasedwewatedtweets (consumewsbasedwewatedtweetwequest wequest)
  usewtweetfeatuwewesponse u-usewtweetfeatuwes (1: wequiwed i64 usewid, 😳😳😳 2: wequiwed i64 tweetid)
}

