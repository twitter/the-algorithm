namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.intewests
#@namespace s-scawa com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato c-com.twittew.simcwustews_v2

/**
 * a-aww of the s-scowes bewow assume t-that the knownfow v-vectow fow each cwustew is awweady
 * of unit w2 nyowm i.e. (U ﹏ U) sum of squawes i-is 1. o.O 
 **/
stwuct usewtointewestedincwustewscowes {
  // dot p-pwoduct of usew's binawy fowwow v-vectow with knownfow vectow fow this cwustew
  // tip: by defauwt, mya u-use this scowe ow favscowe. XD 
  1: o-optionaw doubwe f-fowwowscowe(pewsonawdatatype = 'countoffowwowewsandfowwowees')

  // fiwst compute fowwowscowe as defined above
  // then compute w-w2 nyowm of the vectow of these scowes fow this cwustew
  // divide by that. òωó
  // e-essentiawwy the mowe peopwe a-awe intewested i-in this cwustew, (˘ω˘) t-the wowew this s-scowe gets
  // tip: use this scowe if youw u-use case nyeeds to penawize cwustews that a wot o-of othew 
  // usews awe awso intewested in
  2: optionaw doubwe fowwowscowecwustewnowmawizedonwy(pewsonawdatatype = 'countoffowwowewsandfowwowees')

  // dot pwoduct o-of usew's pwoducew nyowmawized f-fowwow vectow a-and knownfow v-vectow fow this cwustew
  // i.e. :3 i^th entwy in the nyowmawized f-fowwow vectow = 1.0/sqwt(numbew o-of fowwowews of usew i)
  // tip: u-use this scowe i-if youw use case nyeeds to penawize c-cwustews whewe the usews known f-fow
  // that cwustew awe popuwaw. OwO 
  3: optionaw d-doubwe fowwowscowepwoducewnowmawizedonwy(pewsonawdatatype = 'countoffowwowewsandfowwowees')

  // fiwst compute f-fowwowscowepwoducewnowmawizedonwy
  // then c-compute w2 nyowm o-of the vectow of these scowes fow this cwustew
  // divide by that.
  // essentiawwy the mowe peopwe awe intewested i-in this c-cwustew, mya the wowew this scowe gets
  // t-tip: use t-this scowe if youw u-use case nyeeds to penawize both cwustews that a wot of othew
  // u-usews awe intewested in, (˘ω˘) as weww as cwustews whewe the usews known fow that c-cwustew awe 
  // popuwaw. o.O
  4: o-optionaw doubwe f-fowwowscowecwustewandpwoducewnowmawized(pewsonawdatatype = 'countoffowwowewsandfowwowees')

  // d-dot pwoduct of usew's favscowehawfwife100days v-vectow with knownfow v-vectow fow t-this cwustew 
  // t-tip: by defauwt, (✿oωo) use this scowe ow fowwowscowe. (ˆ ﻌ ˆ)♡ 
  5: o-optionaw d-doubwe favscowe(pewsonawdatatype = 'engagementspubwic')

  // f-fiwst compute f-favscowe as defined a-above
  // then compute w2 nyowm of the vectow of these scowes f-fow this cwustew
  // divide by that. ^^;;
  // essentiawwy the mowe peopwe awe intewested in this c-cwustew, OwO the wowew this scowe gets
  // tip: use this scowe if y-youw use case nyeeds t-to penawize c-cwustews that a wot of othew 
  // u-usews awe awso intewested in
  6: o-optionaw doubwe f-favscowecwustewnowmawizedonwy(pewsonawdatatype = 'engagementspubwic')

  // dot pwoduct of usew's favscowehawfwife100daysnowmawizedbyneighbowfavewsw2 vectow with 
  // knownfow vectow fow t-this cwustew
  // tip: use this s-scowe if youw use case nyeeds t-to penawize cwustews w-whewe the usews known fow
  // that cwustew a-awe popuwaw. 🥺 
  7: o-optionaw doubwe favscowepwoducewnowmawizedonwy(pewsonawdatatype = 'engagementspubwic')

  // f-fiwst compute favscowepwoducewnowmawizedonwy a-as defined above
  // then compute w2 nyowm of the vectow of these s-scowes fow this c-cwustew
  // divide b-by that. mya
  // essentiawwy the m-mowe peopwe awe i-intewested in this cwustew, 😳 the w-wowew this scowe gets
  // tip: use this scowe if youw use case nyeeds to penawize b-both cwustews t-that a wot of othew
  // usews awe intewested i-in, òωó as weww as c-cwustews whewe the usews known fow that cwustew awe 
  // popuwaw. /(^•ω•^)
  8: o-optionaw doubwe favscowecwustewandpwoducewnowmawized(pewsonawdatatype = 'engagementspubwic')

  // wist of usews who'we known fow this c-cwustew as weww as awe being fowwowed by the usew. -.-
  9: o-optionaw w-wist<i64> usewsbeingfowwowed(pewsonawdatatype = 'usewid')
 
  // wist of usews who'we known fow this cwustew as w-weww as wewe faved a-at some point by the usew. òωó 
  10: optionaw wist<i64> usewsthatwewefaved(pewsonawdatatype = 'usewid')

  // a p-pwetty cwose uppew bound on the n-nyumbew of usews who awe intewested in this cwustew. /(^•ω•^) 
  // usefuw t-to know if this is a nyiche community o-ow a popuwaw t-topic. /(^•ω•^) 
  11: optionaw i32 n-nyumusewsintewestedinthiscwustewuppewbound

  // dot pwoduct of u-usew's wogfavscowe v-vectow with k-knownfow vectow fow this cwustew 
  // t-tip: this s-scowe is undew expewimentations
  12: optionaw d-doubwe wogfavscowe(pewsonawdatatype = 'engagementspubwic')

  // f-fiwst compute wogfavscowe a-as defined above
  // then compute w2 n-nyowm of the vectow of these scowes f-fow this cwustew
  // d-divide by that. 😳
  // essentiawwy the mowe peopwe awe i-intewested in this c-cwustew, the w-wowew this scowe g-gets
  // tip: this scowe is undew e-expewimentations
  13: optionaw doubwe wogfavscowecwustewnowmawizedonwy(pewsonawdatatype = 'engagementspubwic')

  // actuaw count of nyumbew of usews who'we k-known fow this cwustew as weww a-as awe being fowwowed by the usew. :3
  14: o-optionaw i32 nyumusewsbeingfowwowed

  // a-actuaw count of nyumbew of usews w-who'we known f-fow this cwustew a-as weww as wewe f-faved at some p-point by the usew. 
  15: optionaw i32 nyumusewsthatwewefaved
}(pewsisted = 'twue', (U ᵕ U❁) haspewsonawdata = 'twue')

stwuct usewtointewestedincwustews {
  1: wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: wequiwed s-stwing knownfowmodewvewsion
  3: w-wequiwed m-map<i32, ʘwʘ usewtointewestedincwustewscowes> cwustewidtoscowes(pewsonawdatatypekey = 'infewwedintewests')
}(pewsisted="twue", o.O h-haspewsonawdata = 'twue')

stwuct wanguagetocwustews {
  1: wequiwed stwing wanguage
  2: w-wequiwed s-stwing knownfowmodewvewsion
  3: wequiwed map<i32, ʘwʘ u-usewtointewestedincwustewscowes> cwustewidtoscowes(pewsonawdatatypekey = 'infewwedintewests')
}(pewsisted="twue", ^^ haspewsonawdata = 'twue')

s-stwuct cwustewsusewisintewestedin {
  1: w-wequiwed stwing knownfowmodewvewsion
  2: w-wequiwed map<i32, ^•ﻌ•^ u-usewtointewestedincwustewscowes> cwustewidtoscowes(pewsonawdatatypekey = 'infewwedintewests')
}(pewsisted = 'twue', mya haspewsonawdata = 'twue')

stwuct usewtoknownfowcwustews {
  1: wequiwed i-i64 usewid(pewsonawdatatype = 'usewid')
  2: wequiwed s-stwing knownfowmodewvewsion
  3: w-wequiwed m-map<i32, UwU usewtoknownfowcwustewscowes> c-cwustewidtoscowes(pewsonawdatatypekey = 'infewwedintewests')
}(pewsisted="twue", >_< haspewsonawdata = 'twue')

s-stwuct usewtoknownfowcwustewscowes {
  1: o-optionaw doubwe knownfowscowe
}(pewsisted = 'twue', /(^•ω•^) h-haspewsonawdata = 'fawse')

s-stwuct cwustewsusewisknownfow {
  1: w-wequiwed stwing knownfowmodewvewsion
  2: wequiwed m-map<i32, òωó usewtoknownfowcwustewscowes> cwustewidtoscowes(pewsonawdatatypekey = 'infewwedintewests')
}(pewsisted = 'twue', σωσ haspewsonawdata = 'twue')

/** t-thwift s-stwuct fow stowing quantiwe b-bounds output by qtweemonoid in awgebiwd */
stwuct q-quantiwebounds {
  1: w-wequiwed d-doubwe wowewbound
  2: wequiwed doubwe uppewbound
}(pewsisted = 'twue', ( ͡o ω ͡o ) haspewsonawdata = 'fawse')

/** t-thwift stwuct giving the detaiws of the d-distwibution o-of a set of doubwes */
stwuct distwibutiondetaiws {
  1: w-wequiwed doubwe mean
  2: o-optionaw doubwe s-standawddeviation
  3: optionaw doubwe min
  4: o-optionaw quantiwebounds p25
  5: optionaw quantiwebounds p-p50
  6: o-optionaw quantiwebounds p75
  7: o-optionaw quantiwebounds p95
  8: o-optionaw d-doubwe max
}(pewsisted = 'twue', nyaa~~ h-haspewsonawdata = 'fawse')

/** nyote that the modewvewsion hewe is specified somewhewe outside, specificawwy, :3 as pawt of the key */
stwuct cwustewneighbow {
  1: wequiwed i32 cwustewid
  /** nyote that fowwowcosinesimiwawity is same as dot pwoduct ovew fowwowscowecwustewnowmawizedonwy
   * s-since those s-scowes fowm a unit vectow **/
  2: optionaw doubwe f-fowwowcosinesimiwawity
  /** n-nyote that favcosinesimiwawity i-is same as dot pwoduct ovew favscowecwustewnowmawizedonwy
   * since t-those scowes fowm a unit vectow **/
  3: o-optionaw d-doubwe favcosinesimiwawity
  /** note that w-wogfavcosinesimiwawity is same a-as dot pwoduct o-ovew wogfavscowecwustewnowmawizedonwy
   * since those scowes fowm a-a unit vectow **/
  4: o-optionaw d-doubwe wogfavcosinesimiwawity
}(pewsisted = 'twue', UwU h-haspewsonawdata = 'fawse')

/** u-usefuw fow s-stowing the wist o-of usews known f-fow a cwustew */
s-stwuct usewwithscowe {
  1: wequiwed i64 usewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed doubwe s-scowe
}(pewsisted="twue", o.O haspewsonawdata = 'twue')

// d-depwecated
stwuct edgecut {
  1: w-wequiwed doubwe kawaii~dges
  2: wequiwed doubwe totawvowume
}(pewsisted = 'twue', (ˆ ﻌ ˆ)♡ h-haspewsonawdata = 'fawse')

stwuct c-cwustewquawity {
  // d-depwecated
  1: o-optionaw edgecut depwecated_unweightededgecut
  // d-depwecated
  2: optionaw e-edgecut depwecated_edgeweightedcut
  // depwecated
  3: o-optionaw edgecut depwecated_nodeandedgeweightedcut

  // c-cowwewation of actuaw weight of (u, ^^;; v) with i(u & v in same cwustew) * scowe(u) * s-scowe(v)
  4: optionaw d-doubwe weightandpwoductofnodescowescowwewation

  // f-fwaction of edges staying inside cwustew divided by totaw edges f-fwom nyodes in the cwustew
  5: o-optionaw doubwe u-unweightedwecaww

  // f-fwaction of edge weights staying inside c-cwustew divided b-by totaw edge weights fwom nyodes i-in the cwustew
  6: optionaw doubwe weightedwecaww

  // totaw e-edges fwom nyodes in the cwustew
  7: o-optionaw d-doubwe unweightedwecawwdenominatow

  // t-totaw edge weights f-fwom nyodes in the c-cwustew
  8: o-optionaw doubwe w-weightedwecawwdenominatow

  // sum of edge weights i-inside cwustew / { #nodes * (#nodes - 1) }
  9: o-optionaw doubwe w-wewativepwecisionnumewatow

  // a-above divided b-by the sum of e-edge weights in t-the totaw gwaph / { n-ny * (n - 1) }
  10: optionaw d-doubwe wewativepwecision
}(pewsisted = 'twue', ʘwʘ haspewsonawdata = 'fawse')

/**
* t-this stwuct is the vawue of t-the cwustewdetaiws k-key-vawue dataset.
* t-the key is (modewvewsion, σωσ cwustewid)
**/
stwuct cwustewdetaiws {
  1: w-wequiwed i-i32 nyumusewswithanynonzewoscowe
  2: w-wequiwed i32 nyumusewswithnonzewofowwowscowe
  3: wequiwed i32 nyumusewswithnonzewofavscowe
  4: optionaw d-distwibutiondetaiws f-fowwowscowedistwibutiondetaiws
  5: optionaw distwibutiondetaiws f-favscowedistwibutiondetaiws
  6: o-optionaw wist<usewwithscowe> knownfowusewsandscowes
  7: optionaw wist<cwustewneighbow> n-nyeighbowcwustews
  // f-fwaction o-of usews who'we k-known fow this cwustew who'we mawked nysfw_usew i-in usewsouwce
  8: o-optionaw doubwe fwactionknownfowmawkednsfwusew
  // the m-majow wanguages that this cwustew's known_fows have a-as theiw "wanguage" fiewd in
  // u-usewsouwce, ^^;; a-and the fwactions
  9: optionaw m-map<stwing, ʘwʘ doubwe> w-wanguagetofwactiondevicewanguage
  // the m-majow countwy codes that this cwustew's k-known_fows h-have as theiw "account_countwy_code"
  // f-fiewd i-in usewsouwce, ^^ and the fwactions
  10: o-optionaw m-map<stwing, nyaa~~ doubwe> c-countwycodetofwactionknownfowwithcountwycode
  11: optionaw c-cwustewquawity quawitymeasuwedonsimsgwaph
  12: optionaw distwibutiondetaiws w-wogfavscowedistwibutiondetaiws
  // f-fwaction of w-wanguages this cwustew's known_fows pwoduce based on nyani penguin_usew_wanguages dataset infews
  13: o-optionaw map<stwing, (///ˬ///✿) doubwe> w-wanguagetofwactioninfewwedwanguage
}(pewsisted="twue", XD h-haspewsonawdata = 'twue')

stwuct sampwededge {
  1: wequiwed i64 fowwowewid(pewsonawdatatype = 'usewid')
  2: w-wequiwed i64 fowwoweeid(pewsonawdatatype = 'usewid')
  3: o-optionaw doubwe f-favwtiffowwowedge
  4: o-optionaw d-doubwe favwtiffavedge
  5: optionaw d-doubwe fowwowscowetocwustew
  6: optionaw doubwe favscowetocwustew
  7: optionaw doubwe pwedictedfowwowscowe
  8: o-optionaw doubwe pwedictedfavscowe
}(pewsisted="twue", :3 h-haspewsonawdata = 'twue')

/**
* the key hewe is (modewvewsion, cwustewid)
**/
stwuct bipawtitecwustewquawity {
  1: o-optionaw doubwe incwustewfowwowedges
  2: optionaw doubwe incwustewfavedges
  3: optionaw doubwe favwtsumofincwustewfowwowedges
  4: o-optionaw d-doubwe favwtsumofincwustewfavedges
  5: optionaw d-doubwe outgoingfowwowedges
  6: optionaw doubwe outgoingfavedges
  7: o-optionaw d-doubwe favwtsumofoutgoingfowwowedges
  8: optionaw d-doubwe favwtsumofoutgoingfavedges
  9: optionaw d-doubwe incomingfowwowedges
  10: optionaw doubwe incomingfavedges
  11: optionaw d-doubwe favwtsumofincomingfowwowedges
  12: optionaw doubwe favwtsumofincomingfavedges
  13: o-optionaw i32 i-intewestedinsize
  14: o-optionaw wist<sampwededge> sampwededges
  15: o-optionaw i32 knownfowsize
  16: optionaw doubwe cowwewationoffavwtiffowwowwithpwedictedfowwow
  17: optionaw d-doubwe cowwewationoffavwtiffavwithpwedictedfav
  18: o-optionaw d-doubwe wewativepwecisionusingfavwtiffav
  19: o-optionaw doubwe avewagepwecisionofwhowegwaphusingfavwtiffav
}(pewsisted="twue", haspewsonawdata = 'twue')
