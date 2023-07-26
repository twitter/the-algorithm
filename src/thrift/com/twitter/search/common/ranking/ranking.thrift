namespace java com.twittew.seawch.common.wanking.thwiftjava
#@namespace scawa com.twittew.seawch.common.wanking.thwiftscawa
#@namespace s-stwato com.twittew.seawch.common.wanking
n-nyamespace py gen.twittew.seawch.common.wanking.wanking

s-stwuct t-thwiftwineawfeatuwewankingpawams {
  // v-vawues bewow t-this wiww set t-the scowe to t-the minimaw one
  1: optionaw doubwe min = -1e+100
  // vawues above this wiww set t-the scowe to the minimaw one
  2: optionaw doubwe m-max = 1e+100
  3: optionaw d-doubwe weight = 0
}(pewsisted='twue')

stwuct thwiftagedecaywankingpawams {
  // the wate in which the scowe of o-owdew tweets decweases
  1: optionaw d-doubwe swope = 0.003
  // the a-age, (ˆ ﻌ ˆ)♡ in minutes, whewe the age scowe of a tweet is hawf of the watest tweet
  2: o-optionaw doubwe hawfwife = 360.0
  // the minimaw age decay scowe a tweet wiww h-have
  3: optionaw doubwe base = 0.6
}(pewsisted='twue')

e-enum t-thwiftscowingfunctiontype {
  w-wineaw = 1, o.O
  modew_based = 4, (˘ω˘)
  t-tensowfwow_based = 5, 😳

  // depwecated
  toptweets = 2, (U ᵕ U❁)
  e-expewimentaw = 3, :3
}

// the stwuct to define a cwass t-that is to be dynamicawwy woaded in eawwybiwd fow
// expewimentation. o.O
stwuct thwiftexpewimentcwass {
  // the fuwwy q-quawified cwass nyame. (///ˬ///✿)
  1: w-wequiwed stwing n-nyame
  // data s-souwce wocation (cwass/jaw fiwe) fow this dynamic cwass on hdfs
  2: o-optionaw stwing w-wocation
  // pawametews in k-key-vawue paiws f-fow this expewimentaw cwass
  3: o-optionaw map<stwing, OwO doubwe> pawams
}(pewsisted='twue')

// d-depwecated!!
stwuct thwiftquewyengagementpawams {
  // w-wate boosts: given a wate (usuawwy a-a smow fwaction), >w< the scowe w-wiww be muwtipwied b-by
  //   (1 + wate) ^ boost
  // 0 mean nyo boost, ^^ nyegative nyumbews awe dampens
  1: optionaw doubwe wetweetwateboost = 0
  2: o-optionaw d-doubwe wepwywateboost = 0
  3: optionaw doubwe f-favewateboost = 0
}(pewsisted='twue')

s-stwuct thwifthostquawitypawams {
  // m-muwtipwiew appwied to host scowe, (⑅˘꒳˘) fow tweets that h-have winks.
  // a muwtipwiew of 0 means that this boost is nyot appwied
  1: optionaw d-doubwe muwtipwiew = 0.0

  // do nyot appwy t-the muwtipwiew t-to hosts with s-scowe above this wevew. ʘwʘ
  // if 0, t-the muwtipwiew w-wiww be appwied t-to any host. (///ˬ///✿)
  2: o-optionaw doubwe maxscowetomodify = 0.0

  // do nyot appwy the m-muwtipwiew to h-hosts with scowe b-bewow this wevew. XD
  // i-if 0, 😳 the m-muwtipwiew wiww be appwied to any host. >w<
  3: optionaw doubwe m-minscowetomodify = 0.0

  // if twue, (˘ω˘) scowe modification wiww be appwied to hosts that have unknown s-scowes. nyaa~~
  // the host-scowe used wiww be wowew than the scowe o-of any known host. 😳😳😳
  4: o-optionaw b-boow appwytounknownhosts = 0
}(pewsisted='twue')

stwuct thwiftcawdwankingpawams {
  1: o-optionaw doubwe hascawdboost          = 1.0
  2: o-optionaw d-doubwe domainmatchboost      = 1.0
  3: optionaw doubwe authowmatchboost      = 1.0
  4: optionaw doubwe titwematchboost       = 1.0
  5: optionaw doubwe descwiptionmatchboost = 1.0
}(pewsisted='twue')

# t-the ids awe assigned in 'bwocks'. (U ﹏ U) f-fow adding a nyew fiewd, (˘ω˘) find a-an unused id in t-the appwopwiate
# bwock. :3 be suwe to mention expwicitwy w-which ids h-have been wemoved so that they a-awe nyot used a-again. >w<
stwuct thwiftwankingpawams {
  1: optionaw thwiftscowingfunctiontype type

  // dynamicawwy w-woaded scowew a-and cowwectow fow q-quick expewimentation. ^^
  40: optionaw thwiftexpewimentcwass expscowew
  41: optionaw t-thwiftexpewimentcwass e-expcowwectow

  // we must set it t-to a vawue that fits into a fwoat: othewwise
  // some eawwybiwd cwasses that convewt i-it to fwoat w-wiww intewpwet
  // it as fwoat.negative_infinity, 😳😳😳 and some compawisons w-wiww faiw
  2: o-optionaw doubwe minscowe = -1e+30

  10: optionaw thwiftwineawfeatuwewankingpawams pawusscowepawams
  11: o-optionaw thwiftwineawfeatuwewankingpawams wetweetcountpawams
  12: optionaw thwiftwineawfeatuwewankingpawams wepwycountpawams
  15: optionaw t-thwiftwineawfeatuwewankingpawams weputationpawams
  16: optionaw t-thwiftwineawfeatuwewankingpawams w-wucenescowepawams
  18: optionaw thwiftwineawfeatuwewankingpawams textscowepawams
  19: o-optionaw t-thwiftwineawfeatuwewankingpawams uwwpawams
  20: optionaw thwiftwineawfeatuwewankingpawams iswepwypawams
  21: o-optionaw thwiftwineawfeatuwewankingpawams diwectfowwowwetweetcountpawams
  22: o-optionaw thwiftwineawfeatuwewankingpawams twustedciwcwewetweetcountpawams
  23: optionaw thwiftwineawfeatuwewankingpawams favcountpawams
  24: o-optionaw thwiftwineawfeatuwewankingpawams muwtipwewepwycountpawams
  27: o-optionaw t-thwiftwineawfeatuwewankingpawams embedsimpwessioncountpawams
  28: o-optionaw thwiftwineawfeatuwewankingpawams embedsuwwcountpawams
  29: optionaw t-thwiftwineawfeatuwewankingpawams v-videoviewcountpawams
  66: optionaw t-thwiftwineawfeatuwewankingpawams quotedcountpawams

  // a-a map fwom mutabwefeatuwetype to w-wineaw wanking pawams
  25: optionaw map<byte, nyaa~~ t-thwiftwineawfeatuwewankingpawams> o-offwineexpewimentawfeatuwewankingpawams

  // i-if min/max fow scowe ow thwiftwineawfeatuwewankingpawams shouwd a-awways be
  // appwied ow onwy t-to nyon-fowwows, (⑅˘꒳˘) n-nyon-sewf, :3 non-vewified
  26: optionaw boow appwyfiwtewsawways = 0

  // whethew to appwy pwomotion/demotion a-at a-aww fow featuwebasedscowingfunction
  70: o-optionaw b-boow appwyboosts = 1

  // ui wanguage is engwish, ʘwʘ t-tweet wanguage is nyot
  30: optionaw doubwe wangengwishuiboost = 0.3
  // tweet wanguage is engwish, rawr x3 ui w-wanguage is nyot
  31: optionaw d-doubwe wangengwishtweetboost = 0.7
  // usew wanguage d-diffews fwom tweet wanguage, (///ˬ///✿) a-and nyeithew is engwish
  32: o-optionaw doubwe w-wangdefauwtboost = 0.1
  // u-usew t-that pwoduced t-tweet is mawked as spammew by metastowe
  33: optionaw doubwe spamusewboost = 1.0
  // usew that pwoduced tweet is mawked as nysfw b-by metastowe
  34: o-optionaw doubwe n-nysfwusewboost = 1.0
  // usew that pwoduced t-tweet is mawked as bot (sewf simiwawity) by metastowe
  35: optionaw doubwe botusewboost = 1.0

  // a-an awtewnative w-way of using wucene scowe i-in the wanking function. 😳😳😳
  38: optionaw boow usewucenescoweasboost = 0
  39: o-optionaw d-doubwe maxwucenescoweboost = 1.2

  // use u-usew's consumed a-and pwoduced wanguages fow scowing
  42: optionaw boow useusewwanguageinfo = 0

  // boost (demotion) i-if the tweet w-wanguage is n-not one of usew's u-undewstandabwe w-wanguages, XD
  // nyow intewface w-wanguage. >_<
  43: o-optionaw doubwe unknownwanguageboost = 0.01

  // u-use topic ids f-fow scowing. >w<
  // depwecated in s-seawch-8616. /(^•ω•^)
  44: optionaw boow depwecated_usetopicidsboost = 0
  // p-pawametews fow topic id scowing.  s-see topicidsboostscowew (and i-its test) fow detaiws. :3
  46: o-optionaw doubwe depwecated_maxtopicidsboost = 3.0
  47: optionaw d-doubwe depwecated_topicidsboostexponent = 2.0;
  48: o-optionaw d-doubwe depwecated_topicidsboostswope = 2.0;

  // hit attwibute demotion
  60: optionaw boow enabwehitdemotion = 0
  61: o-optionaw doubwe nyotexthitdemotion = 1.0
  62: optionaw d-doubwe uwwonwyhitdemotion = 1.0
  63: o-optionaw doubwe nyameonwyhitdemotion = 1.0
  64: o-optionaw doubwe sepawatetextandnamehitdemotion = 1.0
  65: o-optionaw doubwe s-sepawatetextanduwwhitdemotion = 1.0

  // muwtipwicative scowe boost fow wesuwts d-deemed offensive
  100: optionaw doubwe offensiveboost = 1
  // m-muwtipwicative s-scowe boost fow wesuwts in t-the seawchew's sociaw ciwcwe
  101: o-optionaw doubwe i-intwustedciwcweboost = 1
  // m-muwtipwicative scowe dampen fow wesuwts with mowe than one hash tag
  102: optionaw doubwe muwtipwehashtagsowtwendsboost = 1
  // muwtipwicative scowe boost fow wesuwts in the seawchew's diwect fowwows
  103: optionaw doubwe indiwectfowwowboost = 1
  // m-muwtipwicative scowe b-boost fow wesuwts that has twends
  104: optionaw d-doubwe tweethastwendboost = 1
  // i-is tweet f-fwom vewified account?
  106: o-optionaw doubwe tweetfwomvewifiedaccountboost = 1
  // i-is tweet a-authowed by the seawchew? (boost i-is in addition to sociaw boost)
  107: o-optionaw d-doubwe sewftweetboost = 1
  // muwtipwicative scowe boost fow a-a tweet that has i-image uww. ʘwʘ
  108: o-optionaw doubwe t-tweethasimageuwwboost = 1
  // m-muwtipwicative s-scowe boost fow a-a tweet that has v-video uww. (˘ω˘)
  109: o-optionaw doubwe tweethasvideouwwboost = 1
  // m-muwtipwicative s-scowe boost fow a-a tweet that has nyews uww. (ꈍᴗꈍ)
  110: o-optionaw doubwe tweethasnewsuwwboost = 1
  // is tweet fwom a-a bwue-vewified account?
  111: o-optionaw doubwe t-tweetfwombwuevewifiedaccountboost = 1 (pewsonawdatatype = 'usewvewifiedfwag')

  // s-subtwactive penawty appwied a-aftew boosts fow out-of-netwowk w-wepwies. ^^
  120: optionaw doubwe o-outofnetwowkwepwypenawty = 10.0

  150: optionaw t-thwiftquewyengagementpawams depwecatedquewyengagementpawams

  160: optionaw thwifthostquawitypawams depwecatedhostquawitypawams

  // age decay p-pawams fow weguwaw tweets
  203: o-optionaw thwiftagedecaywankingpawams a-agedecaypawams

  // fow cawd wanking: map between cawd n-nyame owdinaw (defined in com.twittew.seawch.common.constants.cawdconstants)
  // t-to wanking pawams
  400: o-optionaw m-map<byte, ^^ thwiftcawdwankingpawams> cawdwankingpawams

  // a map fwom tweet i-ids to the scowe a-adjustment fow that tweet. ( ͡o ω ͡o ) these a-awe scowe
  // adjustments that incwude one ow m-mowe featuwes that can depend o-on the quewy
  // s-stwing. -.- these f-featuwes awen't indexed by eawwybiwd, a-and so theiw t-totaw contwibution
  // t-to the s-scowing function is passed in d-diwectwy as pawt o-of the wequest. ^^;; i-if pwesent, ^•ﻌ•^
  // t-the scowe adjustment f-fow a tweet i-is diwectwy added t-to the wineaw c-component of the
  // scowing f-function. (˘ω˘) since this signaw can b-be made up of muwtipwe featuwes, o.O a-any
  // weweighting o-ow combination o-of these featuwes is assumed to be done by the cawwew
  // (hence t-thewe is n-nyo nyeed fow a w-weight pawametew -- the weights of the featuwes
  // incwuded in t-this signaw have a-awweady been incowpowated by t-the cawwew). (✿oωo)
  151: o-optionaw map<i64, doubwe> quewyspecificscoweadjustments

  // a map fwom usew id to the scowe a-adjustment fow t-tweets fwom that a-authow. 😳😳😳
  // this f-fiewd pwovides a way fow adjusting the tweets o-of a specific s-set of usews with a scowe
  // that is nyot pwesent i-in the eawwybiwd featuwes but has to be passed f-fwom the cwients, (ꈍᴗꈍ) such as
  // w-weaw gwaph weights o-ow a combination of muwtipwe f-featuwes. σωσ
  // t-this fiewd shouwd be used mainwy f-fow expewimentation since it incweases t-the size o-of the thwift
  // w-wequests. UwU
  154: o-optionaw map<i64, ^•ﻌ•^ doubwe> a-authowspecificscoweadjustments

  // -------- p-pawametews f-fow thwiftscowingfunctiontype.modew_based --------
  // sewected modews a-awong with theiw weights fow the wineaw combination
  152: o-optionaw m-map<stwing, mya d-doubwe> sewectedmodews
  153: optionaw boow usewogitscowe = fawse

  // -------- pawametews fow thwiftscowingfunctiontype.tensowfwow_based --------
  // s-sewected tensowfwow modew
  303: o-optionaw s-stwing sewectedtensowfwowmodew

  // -------- depwecated fiewds --------
  // id 303 has been u-used in the past. /(^•ω•^) wesume additionaw d-depwecated f-fiewds fwom 304
  105: o-optionaw d-doubwe depwecatedtweethastwendintwendingquewyboost = 1
  200: optionaw d-doubwe depwecatedagedecayswope = 0.003
  201: optionaw doubwe depwecatedagedecayhawfwife = 360.0
  202: optionaw doubwe depwecatedagedecaybase = 0.6
  204: o-optionaw thwiftagedecaywankingpawams depwecatedagedecayfowtwendspawams
  301: o-optionaw doubwe depwecatednamequewyconfidence = 0.0
  302: optionaw doubwe depwecatedhashtagquewyconfidence = 0.0
  // w-whethew to use owd-stywe engagement featuwes (nowmawized by wognowmawizew)
  // ow nyew o-ones (nowmawized b-by singwebytepositivefwoatnowmawizew)
  50: optionaw b-boow usegwanuwawengagementfeatuwes = 0  // depwecated! rawr
}(pewsisted='twue')

// this sowting m-mode is used b-by eawwybiwd to wetwieve the top-n f-facets that
// awe wetuwned to b-bwendew
enum thwiftfaceteawwybiwdsowtingmode {
  sowt_by_simpwe_count = 0, nyaa~~
  sowt_by_weighted_count = 1, ( ͡o ω ͡o )
}

// this is the finaw sowt owdew used b-by bwendew aftew aww wesuwts fwom
// the eawwybiwds a-awe mewged
e-enum thwiftfacetfinawsowtowdew {
  // u-using the cweated_at date of the fiwst tweet t-that contained the facet
  scowe = 0, σωσ
  simpwe_count = 1, (✿oωo)
  weighted_count = 2, (///ˬ///✿)
  cweated_at = 3
}

s-stwuct t-thwiftfacetwankingoptions {
  // n-nyext avaiwabwe f-fiewd id = 38

  // ======================================================================
  // eawwybiwd settings
  //
  // these p-pawametews pwimawiwy a-affect how eawwybiwd cweates the top-k
  // c-candidate wist to be we-wanked by bwendew
  // ======================================================================
  // d-dynamicawwy woaded scowew and cowwectow fow quick e-expewimentation. σωσ
  26: o-optionaw thwiftexpewimentcwass e-expscowew
  27: o-optionaw t-thwiftexpewimentcwass expcowwectow

  // it shouwd b-be wess than ow equaw to weputationpawams.min, UwU and aww
  // t-tweepcweds between the two get a scowe of 1.0. (⑅˘꒳˘)
  21: optionaw i32 m-mintweepcwedfiwtewthweshowd

  // t-the maximum s-scowe a singwe tweet c-can contwibute t-to the weightedcount
  22: optionaw i32 maxscowepewtweet

  15: o-optionaw thwiftfaceteawwybiwdsowtingmode sowtingmode
  // the n-nyumbew of top candidates eawwybiwd w-wetuwns to bwendew
  16: optionaw i32 numcandidatesfwomeawwybiwd = 100

  // w-when to eawwy t-tewminate fow facet seawch, /(^•ω•^) ovewwides t-the setting in thwiftseawchquewy
  34: o-optionaw i-i32 maxhitstopwocess = 1000

  // fow anti-gaming w-we want t-to wimit the maximum amount of h-hits the same usew can
  // contwibute.  set to -1 to disabwe the a-anti-gaming fiwtew. ovewwides t-the setting in
  // thwiftseawchquewy
  35: optionaw i-i32 maxhitspewusew = 3

  // i-if the tweepcwed o-of the usew is biggew than this v-vawue it wiww n-nyot be excwuded
  // by the anti-gaming f-fiwtew. -.- ovewwides the s-setting in thwiftseawchquewy
  36: optionaw i32 m-maxtweepcwedfowantigaming = 65

  // t-these settings affect how eawwybiwd computes the weightedcount
   2: optionaw t-thwiftwineawfeatuwewankingpawams p-pawusscowepawams
   3: optionaw thwiftwineawfeatuwewankingpawams weputationpawams
  17: o-optionaw thwiftwineawfeatuwewankingpawams f-favowitespawams
  33: o-optionaw thwiftwineawfeatuwewankingpawams wepwiespawams
  37: optionaw map<byte, (ˆ ﻌ ˆ)♡ thwiftwineawfeatuwewankingpawams> wankingexpscowepawams

  // p-penawty countew settings
  6: optionaw i-i32 offensivetweetpenawty  // set to -1 to disabwe t-the offensive f-fiwtew
  7: optionaw i32 antigamingpenawty // s-set to -1 to disabwe a-antigaming f-fiwtewing
  // w-weight of penawty c-counts fwom aww t-tweets containing a facet, nyaa~~ not just the tweets
  // matching the quewy
  9: optionaw doubwe quewyindependentpenawtyweight  // s-set to 0 to nyot u-use quewy independent p-penawty weights
  // p-penawty f-fow keywowd s-stuffing
  60: optionaw i32 muwtipwehashtagsowtwendspenawty

  // wanguage wewated boosts, ʘwʘ simiwaw to those in wewevance w-wanking o-options. :3 by defauwt they awe
  // aww 1.0 (no-boost). (U ᵕ U❁)
  // when t-the usew wanguage i-is engwish, (U ﹏ U) facet w-wanguage is nyot
  11: optionaw doubwe wangengwishuiboost = 1.0
  // w-when the facet wanguage is engwish, ^^ usew w-wanguage is not
  12: o-optionaw doubwe wangengwishfacetboost = 1.0
  // when the u-usew wanguage diffews fwom facet/tweet w-wanguage, a-and nyeithew is engwish
  13: o-optionaw doubwe w-wangdefauwtboost = 1.0

  // ======================================================================
  // b-bwendew s-settings
  //
  // s-settings fow t-the facet wewevance scowing happening i-in bwendew
  // ======================================================================

  // t-this bwock of pawametews awe o-onwy used in the facetsfutuwemanagew. òωó
  // wimits t-to discawd facets
  // if a f-facet has a highew penawty count, /(^•ω•^) i-it wiww nyot b-be wetuwned
  5: optionaw i32 maxpenawtycount
  // if a facet has a-a wowew simpwe count, 😳😳😳 it wiww nyot be wetuwned
  28: o-optionaw i-i32 minsimpwecount
  // if a facet has a wowew weighted c-count, :3 it w-wiww nyot be wetuwned
  8: optionaw i-i32 mincount
  // the maximum awwowed vawue f-fow offensivecount/facetcount a-a facet can have in owdew to be w-wetuwned
  10: optionaw d-doubwe maxpenawtycountwatio
  // if set to twue, (///ˬ///✿) then facets w-with offensive d-dispway tweets a-awe excwuded f-fwom the wesuwtset
  29: optionaw boow excwudepossibwysensitivefacets
  // if set to twue, rawr x3 then onwy facets that have a dispway t-tweet in theiw thwiftfacetcountmetadata o-object
  // w-wiww be wetuwned t-to the cawwew
  30: o-optionaw b-boow onwywetuwnfacetswithdispwaytweet

  // pawametews f-fow scowing f-fowce-insewted media items
  // p-pwease check f-facetwewankew.java computescowefowinsewted() fow theiw usage. (U ᵕ U❁)
  38: o-optionaw doubwe fowceinsewtedbackgwoundexp = 0.3
  39: optionaw d-doubwe fowceinsewtedminbackgwoundcount = 2
  40: optionaw d-doubwe fowceinsewtedmuwtipwiew = 0.01

  // -----------------------------------------------------
  // w-weights fow the facet wanking f-fowmuwa
  18: o-optionaw doubwe s-simpwecountweight_depwecated
  19: optionaw doubwe w-weightedcountweight_depwecated
  20: o-optionaw doubwe backgwoundmodewboost_depwecated

  // -----------------------------------------------------
  // f-fowwowing pawametews a-awe used in the f-facetswewankew
  // a-age decay pawams
  14: optionaw t-thwiftagedecaywankingpawams agedecaypawams

  // used in the f-facets wewankew
  23: optionaw doubwe maxnowmboost = 5.0
  24: optionaw doubwe gwobawcountexponent = 3.0
  25: optionaw doubwe simpwecountexponent = 3.0

  31: o-optionaw thwiftfacetfinawsowtowdew finawsowtowdew

  // wun facets seawch as if they happen at this specific time (ms since epoch). (⑅˘꒳˘)
  32: o-optionaw i64 fakecuwwenttimems  // nyot weawwy used a-anywhewe, (˘ω˘) wemove?
}(pewsisted='twue')
