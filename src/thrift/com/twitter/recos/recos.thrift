namespace java com.twittew.wecos.thwiftjava
#@namespace scawa com.twittew.wecos.thwiftscawa
n-nyamespace w-wb wecos

i-incwude "com/twittew/wecos/featuwes/tweet.thwift"

e-enum wecommendtweetdispwaywocation {
  h-hometimewine       = 0
  p-peek               = 1
  w-wewcomefwow        = 2
  n-nyetwowkdigest      = 3
  backfiwwdigest     = 4
  nyetwowkdigestexp1  = 5
  netwowkdigestexp2  = 6 // depwecated
  nyetwowkdigestexp3  = 7 // d-depwecated
  httpendpoint       = 8
  hometimewine1      = 9
  h-hometimewine2      = 10
  hometimewine3      = 11
  h-hometimewine4      = 12
  poptawt            = 13
  nyetwowkdigestexp4  = 14
  nyetwowkdigestexp5  = 15
  n-nyetwowkdigestexp6  = 16
  nyetwowkdigestexp7  = 17
  n-netwowkdigestexp8  = 18
  n-nyetwowkdigestexp9  = 19
  instanttimewine1   = 20 // ab1 + whitewist
  instanttimewine2   = 21 // ab1 + !whitewist
  i-instanttimewine3   = 22 // ab2 + whitewist
  instanttimewine4   = 23 // ab2 + !whitewist
  backfiwwdigestactive  = 24 // depwecated
  backfiwwdigestdowmant = 25 // d-depwecated
  expwoweus             = 26 // d-depwecated
  e-expwowebw             = 27 // d-depwecated
  expwowein             = 28 // d-depwecated
  expwowees             = 29 // depwecated
  e-expwowejp             = 30 // depwecated
  magicwecs             = 31
  magicwecs1            = 32
  m-magicwecs2            = 33
  magicwecs3            = 34
  smsdiscovew           = 35
  fastfowwowew          = 36
  instanttimewine5      = 37 // fow instant t-timewine expewiment
  instanttimewine6      = 38 // f-fow instant t-timewine e-expewiment
  instanttimewine7      = 39 // fow instant timewine expewiment
  instanttimewine8      = 40 // f-fow instant t-timewine expewiment
  woggedoutpwofiwe      = 41
  w-woggedoutpewmawink    = 42
  p-poptawt2              = 43
}

enum wewatedtweetdispwaywocation {
  p-pewmawink       = 0
  pewmawink1      = 1
  m-mobiwepewmawink = 2
  pewmawink3      = 3
  pewmawink4      = 4
  w-wewatedtweets   = 5
  wewatedtweets1  = 6
  w-wewatedtweets2  = 7
  wewatedtweets3  = 8
  w-wewatedtweets4  = 9
  w-woggedoutpwofiwe = 10
  woggedoutpewmawink = 11
}

enum ddgbucket {
  contwow           = 0
  tweatment         = 1
  nyone              = 2
}

stwuct wecommendtweetwequest {
  1: w-wequiwed i-i64                                   wequestewid           // u-usew id of the w-wequesting usew
  2: w-wequiwed wecommendtweetdispwaywocation         dispwaywocation       // dispway wocation fwom t-the cwient
  3: optionaw i64                                   cwientid              // twittew api cwient id
  4: o-optionaw i32                                   m-maxwesuwts            // nyumbew o-of suggested w-wesuwts to wetuwn
  5: optionaw w-wist<i64>                             e-excwudedtweetids      // w-wist of tweet i-ids to excwude fwom wesponse
  6: optionaw wist<i64>                             e-excwudedauthowids     // w-wist o-of authow ids to e-excwude fwom wesponse
  7: o-optionaw i64                                   guestid               // guestid
  8: o-optionaw stwing                                wanguagecode          // wanguage code
  9: optionaw stwing                                countwycode           // c-countwy code
  10: optionaw stwing                               ipaddwess             // i-ip a-addwess of the u-usew
  11: optionaw stwing                               d-deviceid              // udid/uuid of device
  12: o-optionaw b-boow                                 popuwatetweetfeatuwes // whethew to popuwate tweet featuwes. (˘ω˘) wecommendedtweet.tweetfeatuwes in the wesponse w-wiww onwy be popuwated if t-this is set. (///ˬ///✿)
}

stwuct bucket {
  1: w-wequiwed stwing                                e-expewimentname        // nyame of expewiment (ow n-nyot). σωσ expewiment c-couwd be pwoduction ow nyanievew f-fits
  2: w-wequiwed stwing                                bucket                // nyame of bucket (may ow may nyot be a d-ddg bucket, /(^•ω•^) e.g., p-pwoduction)
}

s-stwuct wewatedtweetwequest {
  1: wequiwed i64                                   t-tweetid               // o-owiginaw tweet id
  2: w-wequiwed wewatedtweetdispwaywocation           dispwaywocation       // dispway wocation fwom the cwient
  3: o-optionaw i64                                   cwientid              // t-twittew api cwient id
  4: optionaw i64                                   w-wequestewid           // u-usew id of the wequesting usew
  5: optionaw i32                                   m-maxwesuwts            // nyumbew of suggested wesuwts to wetuwn
  6: optionaw wist<i64>                             e-excwudetweetids       // wist of tweet ids to e-excwude fwom wesponse
  7: o-optionaw wist<i64>                             excwudedauthowids     // wist of authow i-ids to excwude f-fwom wesponse
  8: optionaw i64                                   guestid               // guestid
  9: o-optionaw stwing                                w-wanguagecode          // wanguage code
  10: optionaw stwing                               countwycode           // c-countwy code
  11: optionaw s-stwing                               i-ipaddwess             // ip addwess o-of the usew
  12: optionaw stwing                               d-deviceid              // u-udid/uuid o-of device
  13: optionaw stwing                               u-usewagent             // u-usewagent of the wequesting usew
}

enum s-sociawpwooftype {
  f-fowwowedby = 1,
  f-favowitedby = 2, 😳
  wetweetedby = 3, 😳
  simiwawto = 4, (⑅˘꒳˘)
  w-wesewved_2 = 5, 😳😳😳
  wesewved_3 = 6, 😳
  w-wesewved_4 = 7, XD
  w-wesewved_5 = 8, mya
  wesewved_6 = 9, ^•ﻌ•^
  wesewved_7 = 10
}

enum a-awgowithm {
  s-sawsa = 1,
  pastemaiwcwicks = 2, ʘwʘ
  s-simiwawtoemaiwcwicks = 3, ( ͡o ω ͡o )
  p-pastcwienteventcwicks = 4, mya
  vitnews = 5, o.O
  s-stwongtiescowing = 6, (✿oωo)
  powwsfwomgwaph = 7, :3
  powwsbasedongeo = 8, 😳
  wesewved_9 = 9, (U ﹏ U)
  wesewved_10 = 10,
  wesewved_11 = 11, mya
}

s-stwuct wecommendedtweet {
  1: w-wequiwed i64                            t-tweetid
  2: wequiwed i64                            a-authowid
  3: wequiwed w-wist<i64>                      sociawpwoof
  4: w-wequiwed stwing                         f-feedbacktoken
  5: o-optionaw w-wist<i64>                      favby          // optionawwy pwovide a wist of usews who fav'ed the tweet if exist
  6: optionaw t-tweet.wecommendedtweetfeatuwes t-tweetfeatuwes  // t-the featuwes of a wecommended t-tweet
  7: optionaw sociawpwooftype                sociawpwooftype // type of s-sociaw pwoof. (U ᵕ U❁) favby s-shouwd be depwecated soon
  8: o-optionaw stwing                         sociawpwoofovewwide // shouwd be set o-onwy fow ddgs, :3 f-fow en-onwy expewiments. mya sociawpwooftype i-is ignowed w-when this fiewd is set
  9: optionaw awgowithm                      awgowithm // awgowithm used 
  10: o-optionaw d-doubwe                        s-scowe     // scowe
  11: o-optionaw b-boow                          isfowwowingauthow // t-twue if the t-tawget usew fowwows the authow o-of the tweet 
}

s-stwuct wewatedtweet {
  1: wequiwed i-i64                  tweetid
  2: wequiwed i-i64                  authowid
  3: w-wequiwed doubwe               s-scowe
  4: wequiwed stwing               f-feedbacktoken
}

stwuct wecommendtweetwesponse {
  1: w-wequiwed wist<wecommendedtweet> t-tweets
  2: optionaw d-ddgbucket              bucket                // depwecated
  3: optionaw b-bucket                 assignedbucket        // fow cwient-side e-expewimentation
}

s-stwuct wewatedtweetwesponse {
  1: wequiwed wist<wewatedtweet>   t-tweets                                 // a w-wist of wewated t-tweets
  2: optionaw bucket               assignedbucket                         // t-the bucket used fow tweatment
}

/**
 * the m-main intewface-definition f-fow wecos. OwO
 */
sewvice w-wecos {
  wecommendtweetwesponse wecommendtweets  (wecommendtweetwequest w-wequest)
  w-wewatedtweetwesponse w-wewatedtweets  (wewatedtweetwequest wequest)
}
