namespace java com.twittew.wecos.usew_video_gwaph.thwiftjava
nyamespace p-py gen.twittew.wecos.usew_video_gwaph
#@namespace s-scawa com.twittew.wecos.usew_video_gwaph.thwiftscawa
#@namespace s-stwato c-com.twittew.wecos.usew_video_gwaph
n-nyamespace wb u-usewvideogwaph

i-incwude "com/twittew/wecos/featuwes/tweet.thwift"
i-incwude "com/twittew/wecos/wecos_common.thwift"


stwuct tweetbasedwewatedtweetwequest {
  1: wequiwed i64                                   tweetid               // quewy t-tweet id
  2: optionaw i32                                   maxwesuwts            // n-nyumbew of suggested wesuwts t-to wetuwn
  3: optionaw wist<i64>                             excwudetweetids       // wist of t-tweet ids to excwude fwom wesponse
  4: o-optionaw i-i32                                   minquewydegwee        // min degwee of quewy tweet
  5: optionaw i32                                   m-maxnumsampwespewneighbow // max nyumbew of sampwed usews who engaged with the quewy t-tweet
  6: optionaw i32                                   m-mincooccuwwence       // m-min co-occuwwence o-of wewated t-tweet candidate 
  7: optionaw i32                                   m-minwesuwtdegwee       // min degwee of wewated tweet candidate 
  8: o-optionaw doubwe                                minscowe              // min scowe of wewated tweet candidate
  9: o-optionaw i32                                   maxtweetageinhouws    // max tweet a-age in houws of w-wewated tweet c-candidate 
}

stwuct pwoducewbasedwewatedtweetwequest {
  1: wequiwed i64                                   p-pwoducewid            // q-quewy pwoducew id
  2: optionaw i-i32                                   m-maxwesuwts            // nyumbew of suggested w-wesuwts to wetuwn
  3: o-optionaw wist<i64>                             excwudetweetids       // wist of tweet ids to excwude f-fwom wesponse
  4: optionaw i-i32                                   minquewydegwee        // m-min degwee of quewy p-pwoducew, (///ˬ///✿) e.g. nyumbew of fowwowews
  5: optionaw i32                                   maxnumfowwowews       // max nyumbew of sampwed usews w-who fowwow the q-quewy pwoducew 
  6: optionaw i32                                   m-mincooccuwwence       // m-min c-co-occuwwence of wewated tweet candidate 
  7: optionaw i32                                   m-minwesuwtdegwee       // min degwee of wewated tweet candidate 
  8: optionaw doubwe                                m-minscowe              // min s-scowe of wewated t-tweet candidate
  9: o-optionaw i32                                   m-maxtweetageinhouws    // max t-tweet age in h-houws of wewated t-tweet candidate 
}

stwuct consumewsbasedwewatedtweetwequest {
  1: wequiwed wist<i64>                             c-consumewseedset       // q-quewy c-consumew usewid s-set
  2: optionaw i-i32                                   maxwesuwts            // nyumbew of suggested wesuwts t-to wetuwn
  3: optionaw wist<i64>                             excwudetweetids       // wist of tweet ids to excwude fwom wesponse
  4: optionaw i-i32                                   mincooccuwwence       // min co-occuwwence of wewated tweet c-candidate 
  5: o-optionaw i32                                   m-minwesuwtdegwee       // min degwee o-of wewated tweet candidate  
  6: o-optionaw d-doubwe                                minscowe              // min scowe of wewated tweet candidate
  7: optionaw i32                                   m-maxtweetageinhouws    // max tweet age i-in houws of wewated tweet candidate
}

s-stwuct wewatedtweet {
  1: w-wequiwed i64                          tweetid
  2: wequiwed doubwe                       s-scowe
  3: o-optionaw tweet.gwaphfeatuwesfowtweet  wewatedtweetgwaphfeatuwes
}

s-stwuct w-wewatedtweetwesponse {
  1: wequiwed wist<wewatedtweet>           tweets
  2: optionaw tweet.gwaphfeatuwesfowquewy  q-quewytweetgwaphfeatuwes
}

/**
 * t-the main intewface-definition f-fow usewvideogwaph. ^^;;
 */
sewvice u-usewvideogwaph {
  w-wewatedtweetwesponse tweetbasedwewatedtweets (tweetbasedwewatedtweetwequest w-wequest)
  wewatedtweetwesponse pwoducewbasedwewatedtweets (pwoducewbasedwewatedtweetwequest wequest)
  wewatedtweetwesponse consumewsbasedwewatedtweets (consumewsbasedwewatedtweetwequest wequest)
}

