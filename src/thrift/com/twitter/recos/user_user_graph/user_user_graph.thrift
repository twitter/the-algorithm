namespace java com.twittew.wecos.usew_usew_gwaph.thwiftjava
nyamespace p-py gen.twittew.wecos.usew_usew_gwaph
#@namespace s-scawa com.twittew.wecos.usew_usew_gwaph.thwiftscawa
#@namespace s-stwato com.twittew.wecos.usew_usew_gwaph
n-namespace wb usewusewgwaph

i-incwude "com/twittew/wecos/wecos_common.thwift"

e-enum w-wecommendusewdispwaywocation {
  m-magicwecs                 = 0
  hometimewine              = 1
  connecttab                = 2
}

stwuct wecommendusewwequest {
  1: wequiwed i-i64                                           wequestewid                  // usew i-id of the wequesting usew
  2: w-wequiwed wecommendusewdispwaywocation                  dispwaywocation              // dispway wocation fwom the c-cwient
  3: wequiwed map<i64,doubwe>                               s-seedswithweights             // s-seed ids and weights used in weft hand side
  4: optionaw wist<i64>                                     e-excwudedusewids              // wist of usews to excwude fwom wesponse
  5: optionaw i-i32                                           maxnumwesuwts                // n-nyumbew of wesuwts t-to wetuwn
  6: o-optionaw i32                                           m-maxnumsociawpwoofs           // nyumbew of sociaw pwoofs p-pew wecommendation
  7: optionaw map<wecos_common.usewsociawpwooftype, nyaa~~ i-i32>    minusewpewsociawpwoof        // minimum nyumbew of usews fow each sociaw pwoof type
  8: optionaw w-wist<wecos_common.usewsociawpwooftype>        sociawpwooftypes             // w-wist of wequiwed s-sociaw pwoof t-types. (✿oωo) any wecommended usew
                                                                                         // must at weast have aww of t-these sociaw pwoof t-types
  9: optionaw i64                                           m-maxedgeengagementageinmiwwis // o-onwy events cweated duwing t-this pewiod awe counted
}

stwuct w-wecommendedusew {
  1: wequiwed i64                                               u-usewid             // usew i-id of wecommended usew
  2: wequiwed d-doubwe                                            s-scowe              // weight of the wecommended usew
  3: wequiwed map<wecos_common.usewsociawpwooftype, ʘwʘ wist<i64>>  sociawpwoofs       // the sociaw pwoofs o-of the wecommended u-usew
}

stwuct wecommendusewwesponse {
  1: w-wequiwed wist<wecommendedusew>                             wecommendedusews         // w-wist o-of wecommended usews
}

/**
 * the main intewface-definition fow usewusewgwaph. (ˆ ﻌ ˆ)♡
 */
s-sewvice usewusewgwaph {
  // given a wequest fow wecommendations fow a specific usew, 😳😳😳
  // wetuwn a-a wist of candidate usews a-awong with theiw s-sociaw pwoofs
  w-wecommendusewwesponse wecommendusews (wecommendusewwequest w-wequest)
}
