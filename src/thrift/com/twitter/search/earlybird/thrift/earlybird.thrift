namespace java com.twittew.seawch.eawwybiwd.thwift
#@namespace scawa c-com.twittew.seawch.eawwybiwd.thwiftscawa
#@namespace s-stwato c-com.twittew.seawch.eawwybiwd
n-nyamespace p-py gen.twittew.seawch.eawwybiwd

i-incwude "com/twittew/ads/adsewvew/adsewvew_common.thwift"
i-incwude "com/twittew/seawch/common/caching/caching.thwift"
i-incwude "com/twittew/seawch/common/constants/quewy.thwift"
incwude "com/twittew/seawch/common/constants/seawch_wanguage.thwift"
incwude "com/twittew/seawch/common/convewsation/convewsation.thwift"
incwude "com/twittew/seawch/common/featuwes/featuwes.thwift"
incwude "com/twittew/seawch/common/indexing/status.thwift"
i-incwude "com/twittew/seawch/common/quewy/seawch.thwift"
incwude "com/twittew/seawch/common/wanking/wanking.thwift"
incwude "com/twittew/seawch/common/wesuwts/expansions.thwift"
i-incwude "com/twittew/seawch/common/wesuwts/highwight.thwift"
incwude "com/twittew/seawch/common/wesuwts/hit_attwibution.thwift"
i-incwude "com/twittew/seawch/common/wesuwts/hits.thwift"
incwude "com/twittew/seawch/common/wesuwts/sociaw.thwift"
incwude "com/twittew/sewvice/spidewduck/gen/metadata_stowe.thwift"
incwude "com/twittew/tweetypie/depwecated.thwift"
i-incwude "com/twittew/tweetypie/tweet.thwift"
incwude "com/twittew/eschewbiwd/tweet_annotation.thwift"

e-enum thwiftseawchwankingmode {
  // g-good owd weawtime seawch mode
  wecency = 0, mya
  // new supew fancy wewevance wanking
  w-wewevance = 1, òωó
  depwecated_discovewy = 2, (U ﹏ U)
  // top tweets wanking mode
  toptweets = 3, (U ﹏ U)
  // wesuwts fwom accounts f-fowwowed by the seawchew
  f-fowwows = 4, >_<

  p-pwace_howdew5 = 5, nyaa~~
  p-pwace_howdew6 = 6, 😳😳😳
}

e-enum thwiftseawchwesuwttype {
  // it's a time-owdewed w-wesuwt. nyaa~~
  wecency = 0, -.-
  // it's a highwy wewevant tweet (aka t-top tweet). 😳😳😳
  wewevance = 1, ^•ﻌ•^
  // top tweet wesuwt type
  popuwaw = 2, UwU
  // pwomoted tweets (ads)
  pwomoted = 3, (ˆ ﻌ ˆ)♡
  // w-wewevance-owdewed (as opposed to time-owdewed) t-tweets g-genewated fwom a v-vawiety of candidates
  wewevance_owdewed = 4, XD

  pwace_howdew5 = 5, (⑅˘꒳˘)
  pwace_howdew6 = 6, /(^•ω•^)
}

e-enum t-thwiftsociawfiwtewtype {
  // fiwtew onwy usews t-that the seawchew i-is diwectwy fowwowing. (U ᵕ U❁)
  fowwows = 0, ʘwʘ
  // f-fiwtew onwy usews that awe in seawchew's s-sociaw ciwcwe of twust. OwO
  twusted = 1,
  // f-fiwtew both fowwows and twusted. (✿oωo)
  a-aww = 2, (///ˬ///✿)

  pwace_howdew3 = 3, (✿oωo)
  p-pwace_howdew4 = 4, σωσ

}

e-enum thwifttweetsouwce {
  ///// enums set by eawwybiwd
  weawtime_cwustew = 1, ʘwʘ
  fuww_awchive_cwustew = 2, 😳😳😳
  weawtime_pwotected_cwustew = 4, ^•ﻌ•^

  ///// enums set inside bwendew
  a-adsewvew = 0, (˘ω˘)
  // f-fwom top nyews seawch, (U ﹏ U) onwy u-used in univewsaw s-seawch
  top_news = 3, >w<
  // speciaw t-tweets incwuded just fow eventpawwot. XD
  fowce_incwuded = 5, XD
  // fwom content w-wecommendew
  // fwom topic to tweet path
  content_wecs_topic_to_tweet = 6, (U ﹏ U)
  // used fow h-hydwating qig tweets (go/qig)
  qig = 8, (✿oωo)
  // used f-fow toptweets w-wanking mode
  t-top_tweet = 9, ^^;;
  // used fow expewimentaw c-candidate s-souwces
  expewimentaw = 7,
  // f-fwom scanw s-sewvice
  scanw = 10, (U ﹏ U)

  pwace_howdew11 = 11, OwO
  pwace_howdew12 = 12
}

e-enum nyamedentitysouwce {
  t-text = 0, 😳😳😳
  uww = 1,

  p-pwace_howdew2 = 2, 😳😳😳
  p-pwace_howdew3 = 3, (✿oωo)
  p-pwace_howdew4 = 4, UwU
}

enum expewimentcwustew {
  exp0 = 0, mya // s-send wequests to the eawwybiwd-weawtime-exp0 cwustew
  pwace_howdew1 = 1, rawr x3
  pwace_howdew2 = 2, /(^•ω•^)
}

enum audiospacestate {
   wunning = 0, >_<
   ended = 1, :3

   pwace_howdew2 = 2, o.O
   pwace_howdew3 = 3, UwU
   p-pwace_howdew4 = 4, (ꈍᴗꈍ)
   pwace_howdew5 = 5, >_<
}

// contains aww scowing and w-wewevance-fiwtewing w-wewated contwows a-and options fow eawwybiwd. òωó
s-stwuct thwiftseawchwewevanceoptions {
  // nyext a-avaiwabwe fiewd i-id: 31 and nyote that 45 and 50 have been used awweady

  2: optionaw boow fiwtewdups = 0         // fiwtew out d-dupwicate seawch wesuwts
  26: o-optionaw boow keepdupwithhighewscowe = 1 // k-keep t-the dupwicate tweet with the highew scowe

  3: o-optionaw boow p-pwoximityscowing = 0   // whethew t-to do pwoximity s-scowing ow nyot
  4: optionaw i32 maxconsecutivesameusew  // fiwtew consecutive wesuwts fwom t-the same usew
  5: o-optionaw wanking.thwiftwankingpawams w-wankingpawams  // composed b-by bwendew
  // d-depwecated in favow of the maxhitstopwocess in c-cowwectowpawams
  6: optionaw i32 maxhitstopwocess // when to eawwy-tewminate f-fow wewevance
  7: o-optionaw stwing expewimentname      // nyani w-wewevance expewiment i-is wunning
  8: optionaw stwing expewimentbucket    // nyani b-bucket the usew is in; ddg defauwts to hawd-coded 'contwow'
  9: optionaw boow intewpwetsinceid = 1   // w-whethew to intewpwet since_id opewatow

  24: o-optionaw i-i32 maxhitspewusew // ovewwides thwiftseawchquewy.maxhitspewusew

  // onwy used b-by discovewy f-fow capping diwect fowwow tweets
  10: optionaw i32 maxconsecutivediwectfowwows

  // n-nyote - the owdewbywewevance f-fwag is cwiticaw to undewstanding how mewging
  // and twimming w-wowks in wewevance mode in the s-seawch woot. (ꈍᴗꈍ)
  //
  // w-when owdewbywewevance is twue, wesuwts a-awe twimmed in scowe-owdew. 😳😳😳  this m-means the
  // c-cwient wiww get t-the top wesuwts fwom (maxhitstopwocess * n-nyumhashpawtitions) h-hits, ( ͡o ω ͡o )
  // owdewed by scowe. mya
  //
  // w-when owdewbywewevance i-is fawse, UwU w-wesuwts awe twimmed in id-owdew.  this means t-the
  // cwient wiww get the top w-wesuwts fwom a-an appwoximation of maxhitstopwocess hits
  // (acwoss the entiwe c-cowpus). òωó  these w-wesuwts owdewed b-by id. -.-
  14: optionaw b-boow owdewbywewevance = 0

  // max bwending c-count fow wesuwts wetuwned due to fwom:usew wewwites
  16: optionaw i32 maxusewbwendcount

  // the weight f-fow pwoximity phwases genewated w-whiwe twanswating the sewiawized q-quewy to the
  // wucene quewy. :3
  19: o-optionaw doubwe pwoximityphwaseweight = 1.0
  20: o-optionaw i-i32 pwoximityphwaseswop = 255

  // o-ovewwide the w-weights of seawchabwe f-fiewds. ^•ﻌ•^
  // nyegative weight means the the fiewd is nyot enabwed fow seawch by defauwt, (˘ω˘)
  // but if it i-is (e.g., by annotation), 😳😳😳 t-the absowute v-vawue of the weight shaww b-be
  // used (if the annotation does nyot specify a weight). (///ˬ///✿)
  21: o-optionaw map<stwing, d-doubwe> fiewdweightmapovewwide

  // whethew d-disabwe the coowdination in the wewwitten d-disjunction quewy, t-tewm quewy and phwase quewy
  // t-the detaiws c-can be found in wucenevisitow
  22: optionaw boow depwecated_disabwecoowd = 0

  // woot onwy. 🥺 w-wetuwns aww wesuwts s-seen by woot t-to the cwient without t-twimming
  // i-if set to twue. (U ᵕ U❁)
  23: optionaw b-boow wetuwnawwwesuwts

  // d-depwecated: aww v2 countews wiww b-be used expwicitwy i-in the scowing function and
  // w-wetuwned in theiw own fiewd (in eithew metadata o-ow featuwe map in wesponse). (˘ω˘)
  25: o-optionaw b-boow useengagementcountewsv2 = 0

  // -------- pewsonawization-wewated w-wewevance options --------
  // take speciaw c-cawe with t-these options when w-weasoning about caching.

  // depwecated in seawch-8616. UwU
  45: o-optionaw map<i32, 😳 doubwe> depwecated_topicidweights

  // cowwect h-hit attwibution o-on quewies and wikedbyusewidfiwtew64-enhanced q-quewies to
  // get wikedbyusewids w-wist in metadata f-fiewd. :3
  // nyote: this fwag has nyo affect o-on fwomusewidfiwtew64. mya
  50: optionaw boow cowwectfiewdhitattwibutions = 0

  // whethew to cowwect a-aww hits w-wegawdwess of theiw scowe with wewevanceawwcowwectow. nyaa~~
  27: o-optionaw boow usewewevanceawwcowwectow = 0

  // o-ovewwide f-featuwes of s-specific tweets befowe the tweets awe scowed. 😳😳😳  
  28: optionaw map<i64, ^•ﻌ•^ featuwes.thwiftseawchwesuwtfeatuwes> pewtweetfeatuwesovewwide

  // ovewwide featuwes of aww tweets fwom specific usews befowe the tweets awe scowed. UwU 
  29: optionaw map<i64, (ꈍᴗꈍ) featuwes.thwiftseawchwesuwtfeatuwes> p-pewusewfeatuwesovewwide

  // o-ovewwide featuwes of aww tweets befowe t-the tweets awe s-scowed.
  30: o-optionaw featuwes.thwiftseawchwesuwtfeatuwes gwobawfeatuwesovewwide
}(pewsisted='twue')

// f-facets types that may h-have diffewent w-wanking pawametews. (⑅˘꒳˘)
enum thwiftfacettype {
  d-defauwt = 0, OwO
  mentions_facet = 1, UwU
  h-hashtags_facet = 2, OwO
  // d-depwecated in seawch-13708
  depwecated_named_entities_facet = 3, (///ˬ///✿)
  s-stocks_facet = 4, (U ﹏ U)
  v-videos_facet = 5, (⑅˘꒳˘)
  i-images_facet = 6, /(^•ω•^)
  n-nyews_facet = 7, :3
  wanguages_facet = 8,
  s-souwces_facet = 9, ( ͡o ω ͡o )
  t-twimg_facet = 10, (ˆ ﻌ ˆ)♡
  fwom_usew_id_facet = 11, XD
  d-depwecated_topic_ids_facet = 12, :3
  w-wetweets_facet = 13, σωσ
  w-winks_facet = 14, mya

  pwace_howdew15 = 15, -.-
  p-pwace_howdew16 = 16, :3
}

s-stwuct thwiftseawchdebugoptions {
  // make e-eawwybiwd onwy scowe and wetuwn t-tweets (specified by tweet id) hewe, rawr wegawdwess
  // i-if they have a hit fow t-the cuwwent quewy o-ow nyot. >_<
  1: o-optionaw set<i64> statusids;

  // a-assowted stwuctuwes to pass in d-debug options. -.-
  2: optionaw map<stwing, :3 s-stwing> stwingmap;
  3: o-optionaw map<stwing, XD doubwe> vawuemap;
  4: optionaw wist<doubwe> vawuewist;
}(pewsisted='twue')

// t-these options contwow nyani m-metadata wiww b-be wetuwned by eawwybiwd fow each seawch wesuwt
// in the thwiftseawchwesuwtmetadata s-stwuct. ^^  these options awe c-cuwwentwy mostwy s-suppowted by
// a-abstwactwewevancecowwectow and pawtiawwy in seawchwesuwtscowwectow. rawr  m-most awe t-twue by defauwt to
// pwesewve b-backwawds compatibiwity, (///ˬ///✿) but can be disabwed as n-nyecessawy to optimize seawches w-wetuwning
// many w-wesuwts (such a-as discovew). ^^;;
stwuct thwiftseawchwesuwtmetadataoptions {
  // i-if t-twue, :3 fiwws in t-the tweetuwws fiewd i-in thwiftseawchwesuwtmetadata. :3
  // popuwated b-by abstwactwewevancecowwectow. ( ͡o ω ͡o )
  1: o-optionaw boow g-gettweetuwws = 1

  // i-if twue, (✿oωo) f-fiwws in the w-wesuwtwocation f-fiewd in thwiftseawchwesuwtmetadata. UwU
  // p-popuwated by abstwactwewevancecowwectow. ( ͡o ω ͡o )
  2: o-optionaw boow getwesuwtwocation = 1
  
  // d-depwecated in seawch-8616. o.O
  3: o-optionaw boow d-depwecated_gettopicids = 1

  // i-if twue, rawr fiwws in the wucenescowe fiewd in thwiftseawchwesuwtmetadata. (ꈍᴗꈍ)
  // popuwated by wineawscowingfunction. mya
  4: o-optionaw b-boow getwucenescowe = 0

  // depwecated b-but used to be fow offwine featuwe vawues fow static index
  5: o-optionaw b-boow depwecated_getexpfeatuwevawues = 0

  // if twue, wiww omit a-aww featuwes d-dewivabwe fwom packedfeatuwes, mya and set packedfeatuwes
  // instead. UwU
  6: o-optionaw b-boow depwecated_usepackedfeatuwes = 0

  // if t-twue, ^^;; fiwws shawedstatusid. -.- f-fow wepwies this is the in-wepwy-to s-status id and f-fow
  // wetweets this is the wetweet souwce status i-id.
  // awso fiwws in the the iswetweet and i-iswepwy fwags. XD
  7: optionaw boow g-getinwepwytostatusid = 0

  // i-if twue, nyaa~~ fiwws wefewencedtweetauthowid. (ꈍᴗꈍ) a-awso fiwws i-in the the iswetweet and iswepwy f-fwags. ^^;;
  8: optionaw boow g-getwefewencedtweetauthowid = 0

  // i-if twue, :3 fiwws m-media bits (video/vine/pewiscope/etc.)
  9: o-optionaw boow getmediabits = 0

  // if twue, (///ˬ///✿) wiww w-wetuwn aww defined f-featuwes in t-the packed featuwes. /(^•ω•^)  this fwag d-does nyot covew
  // the above defined featuwes. σωσ
  10: o-optionaw b-boow getawwfeatuwes = 0

  // i-if twue, >w< wiww wetuwn aww featuwes as thwiftseawchwesuwtfeatuwes fowmat. (ˆ ﻌ ˆ)♡
  11: optionaw boow wetuwnseawchwesuwtfeatuwes = 0

  // i-if the cwient caches some featuwes s-schemas, rawr x3 cwient c-can indicate its cache schemas thwough
  // t-this fiewd based on (vewsion, checksum). -.-
  12: optionaw w-wist<featuwes.thwiftseawchfeatuweschemaspecifiew> f-featuweschemasavaiwabweincwient

  // s-specific featuwe i-ids to wetuwn fow w-wecency wequests. (ˆ ﻌ ˆ)♡ popuwated in seawchwesuwtfeatuwes. /(^•ω•^)
  // vawues must be ids o-of csf fiewds fwom eawwybiwdfiewdconstants. (⑅˘꒳˘)
  13: o-optionaw wist<i32> wequestedfeatuweids

  // if twue, (˘ω˘) fiwws in the nyamedentities f-fiewd in thwiftseawchwesuwtextwametadata
  14: optionaw boow getnamedentities = 0

  // if twue, ^•ﻌ•^ fiwws in the e-entityannotations f-fiewd in thwiftseawchwesuwtextwametadata
  15: optionaw boow g-getentityannotations = 0

  // if twue, o.O fiwws in the fwomusewid f-fiewd in the thwiftseawchwesuwtextwametadata
  16: o-optionaw boow getfwomusewid = 0

  // i-if twue, (⑅˘꒳˘) fiwws in the s-spaces fiewd in the thwiftseawchwesuwtextwametadata
  17: optionaw boow getspaces = 0

  18: o-optionaw boow getexcwusiveconvewsationauthowid = 0
}(pewsisted='twue')


// thwiftseawchquewy d-descwibes a-an eawwybiwd s-seawch wequest, σωσ which typicawwy consists
// of t-these pawts:
//  - a quewy to wetwieve hits
//  - wewevance options to scowe hits
//  - a-a cowwectow t-to cowwect h-hits and pwocess i-into seawch wesuwts
// nyote that this stwuct is u-used in both thwiftbwendewwequest a-and eawwybiwdwequest.
// most fiewds awe nyot s-set when this stwuct is embedded in thwiftbwendewwequest, >_< a-and
// awe fiwwed in by the bwendew b-befowe sending to e-eawwybiwd. ʘwʘ
stwuct thwiftseawchquewy {
  // n-nyext a-avaiwabwe fiewd i-id: 42

  // -------- section zewo: things used o-onwy by the bwendew --------
  // see seawchquaw-2398
  // these f-fiewds awe used by the bwendew and cwients of the bwendew, (✿oωo) but n-nyot by eawwybiwd.

  // b-bwendew u-use onwy
  // t-the waw un-pawsed u-usew seawch quewy. o.O
  6: optionaw s-stwing wawquewy(pewsonawdatatype = 'seawchquewy')

  // bwendew use onwy
  // w-wanguage of the wawquewy. 😳
  18: o-optionaw stwing quewywang(pewsonawdatatype = 'infewwedwanguage')

  // bwendew u-use onwy
  // n-nyani page of wesuwts to wetuwn, nyaa~~ i-indexed fwom 1. XD
  7: optionaw i32 p-page = 1

  // b-bwendew use onwy
  // nyumbew o-of wesuwts to skip (fow p-pagination). ^^;;  indexed fwom 0. /(^•ω•^)
  2: o-optionaw i32 depwecated_wesuwtoffset = 0


  // -------- section one: wetwievaw options --------
  // t-these options contwow the quewy t-that wiww be used to wetwieve documents / hits. >_<

  // t-the pawsed q-quewy twee, (U ﹏ U) sewiawized t-to a stwing. 😳😳😳  westwicts t-the seawch wesuwts t-to
  // tweets matching this q-quewy.
  1: optionaw stwing sewiawizedquewy(pewsonawdatatype = 'seawchquewy')

  // w-westwicts the seawch wesuwts t-to tweets having t-this minimum tweep cwed, XD out of 100. OwO
  5: optionaw i32 mintweepcwedfiwtew = -1

  // westwicts t-the seawch wesuwts t-to tweets fwom these usews. (U ᵕ U❁)
  34: optionaw wist<i64> fwomusewidfiwtew64(pewsonawdatatype = 'pwivateaccountsfowwowing, (⑅˘꒳˘) p-pubwicaccountsfowwowing')
  // westwicts t-the seawch wesuwts t-to tweets wiked by these usews. UwU
  40: optionaw wist<i64> wikedbyusewidfiwtew64(pewsonawdatatype = 'pwivateaccountsfowwowing, 😳😳😳 p-pubwicaccountsfowwowing')

  // if seawchstatusids awe pwesent, mya e-eawwybiwd wiww ignowe the sewiawizedquewy c-compwetewy
  // a-and simpwy scowe each o-of seawchstatusids, 🥺 a-awso bypassing f-featuwes w-wike dupwicate
  // f-fiwtewing and e-eawwy tewmination. ^^
  // impowtant: this means that it is possibwe to get scowes equaw to scowingfunction.skip_hit, -.-
  // f-fow wesuwts s-skipped by t-the scowing function. ^^
  31: o-optionaw s-set<i64> seawchstatusids

  35: o-optionaw set<i64> depwecated_eventcwustewidsfiwtew

  41: optionaw map<stwing, o.O wist<i64>> nyameddisjunctionmap

  // -------- s-section two: h-hit cowwectow options --------
  // these options contwow nani hits wiww be cowwected b-by the hit c-cowwectow. σωσ
  // w-whethew we want to cowwect and wetuwn pew-fiewd h-hit attwibutions is set in wewevanceoptions. ^•ﻌ•^
  // see seawch-2784
  // n-nyumbew o-of wesuwts to wetuwn (aftew offset/page cowwection). 😳
  // t-this is ignowed when s-seawchstatusids i-is set. nyaa~~
  3: wequiwed i32 nyumwesuwts

  // m-maximum n-nyumbew of hits t-to pwocess by t-the cowwectow. ^•ﻌ•^
  // d-depwecated i-in favow of the maxhitstopwocess i-in cowwectowpawams
  4: o-optionaw i32 maxhitstopwocess = 1000

  // c-cowwect hit counts fow these time pewiods (in m-miwwiseconds). >_<
  30: optionaw w-wist<i64> hitcountbuckets

  // if set, (⑅˘꒳˘) eawwybiwd w-wiww awso wetuwn t-the facet wabews of the specified facet fiewds
  // i-in wesuwt tweets. ^^
  33: optionaw wist<stwing> f-facetfiewdnames

  // o-options contwowwing which seawch wesuwt m-metadata is w-wetuwned. :3
  36: optionaw thwiftseawchwesuwtmetadataoptions w-wesuwtmetadataoptions

  // cowwection wewated pawams
  38: o-optionaw s-seawch.cowwectowpawams cowwectowpawams

  // w-whethew t-to cowwect convewsation ids
  39: optionaw b-boow cowwectconvewsationid = 0

  // -------- s-section t-thwee: wewevance o-options --------
  // these options contwow wewevance scowing and anti-gaming. 😳

  // wanking mode (wecency m-means time-owdewed w-wanking with n-nyo wewevance). (˘ω˘)
  8: o-optionaw t-thwiftseawchwankingmode w-wankingmode = thwiftseawchwankingmode.wecency

  // w-wewevance s-scowing options. >w<
  9: optionaw t-thwiftseawchwewevanceoptions w-wewevanceoptions

  // wimits the nyumbew of hits t-that can be contwibuted by the same usew, 😳 fow a-anti-gaming. ^^;;
  // set to -1 to d-disabwe the anti-gaming f-fiwtew. rawr x3  this is ignowed w-when seawchstatusids
  // i-is set. òωó
  11: o-optionaw i32 maxhitspewusew = 3

  // d-disabwes anti-gaming f-fiwtew checks fow any tweets t-that exceed this tweepcwed. ^^;;
  12: o-optionaw i32 m-maxtweepcwedfowantigaming = 65

  // -------- pewsonawization-wewated w-wewevance options --------
  // t-take speciaw cawe with these options when w-weasoning about caching.  aww of these
  // options, :3 if set, (ꈍᴗꈍ) wiww bypass the cache with the exception of uiwang w-which is the
  // onwy fowm of pewsonawization awwowed fow caching. 😳😳😳

  // usew id of seawchew. :3  this is used fow w-wewevance, ʘwʘ and wiww be used fow wetwievaw
  // b-by the pwotected tweets index. :3  i-if set, OwO quewy wiww nyot be cached. mya
  20: optionaw i-i64 seawchewid(pewsonawdatatype = 'usewid')

  // bwoom fiwtew c-containing twusted usew ids. σωσ  i-if set, quewy wiww n-nyot be cached. (⑅˘꒳˘)
  10: optionaw binawy twustedfiwtew(pewsonawdatatype = 'usewid')

  // b-bwoom fiwtew containing diwect fowwow usew ids. (˘ω˘)  if set, q-quewy wiww nyot be cached. >w<
  16: o-optionaw binawy diwectfowwowfiwtew(pewsonawdatatype = 'usewid, ( ͡o ω ͡o ) p-pwivateaccountsfowwowing, ^^;; pubwicaccountsfowwowing')

  // u-ui w-wanguage fwom the seawchew's pwofiwe settings. (✿oωo)
  14: o-optionaw stwing uiwang(pewsonawdatatype = 'genewawsettings')

  // confidence o-of the undewstandabiwity of diffewent wanguages fow this usew. (✿oωo)
  // uiwang fiewd a-above is tweated a-as a usewwang with a confidence o-of 1.0. (⑅˘꒳˘)
  28: o-optionaw map<seawch_wanguage.thwiftwanguage, -.- doubwe> usewwangs(pewsonawdatatypekey = 'infewwedwanguage')

  // a-an awtewnative to fwomusewidfiwtew64 that wewies on the wewevance bwoom fiwtews
  // f-fow usew f-fiwtewing. XD  nyot cuwwentwy used i-in pwoduction. òωó  o-onwy suppowted fow weawtime
  // s-seawches. :3
  // if set, (///ˬ///✿) eawwybiwd expects both twustedfiwtew a-and diwectfowwowfiwtew to awso be set.
  17: o-optionaw t-thwiftsociawfiwtewtype sociawfiwtewtype

  // -------- section f-fouw: debug options, òωó fowgotten featuwes --------

  // eawwybiwd seawch debug options. UwU
  19: optionaw thwiftseawchdebugoptions debugoptions

  // o-ovewwides the q-quewy time fow debugging. >w<
  29: o-optionaw i64 timestampmsecs = 0

  // s-suppowt fow this featuwe h-has been wemoved and this fiewd is weft fow backwawds compatibiwity
  // (and to detect impwopew usage by cwients w-when it is set). ʘwʘ
  25: optionaw wist<stwing> depwecated_itewativequewies

  // specifies a wucene q-quewy that w-wiww onwy be used i-if sewiawizedquewy is nyot set, /(^•ω•^)
  // fow debugging. (⑅˘꒳˘)  nyot cuwwentwy u-used in pwoduction.
  27: o-optionaw stwing w-wucenequewy(pewsonawdatatype = 'seawchquewy')

  // this fiewd is d-depwecated and is nyot used by e-eawwybiwds when pwocessing the q-quewy. (ˆ ﻌ ˆ)♡
  21: optionaw i32 depwecated_mindocstopwocess = 0
}(pewsisted='twue', OwO h-haspewsonawdata = 'twue')


stwuct thwiftfacetwabew {
  1: w-wequiwed stwing fiewdname
  2: w-wequiwed s-stwing wabew
  // the numbew of t-times this facet h-has shown up in tweets with offensive w-wowds. ^^;;
  3: optionaw i32 o-offensivecount = 0

  // onwy fiwwed f-fow twimg f-facets
  4: optionaw stwing nyativephotouww
}(pewsisted='twue')

stwuct thwiftseawchwesuwtgeowocation {
  1: o-optionaw doubwe watitude(pewsonawdatatype = 'gpscoowdinates')
  2: optionaw doubwe wongitude(pewsonawdatatype = 'gpscoowdinates')
  3: optionaw doubwe distancekm
}(pewsisted='twue', (///ˬ///✿) haspewsonawdata = 'twue')

// contains an expanded u-uww and media type fwom the uww facet fiewds i-in eawwybiwd. ^•ﻌ•^
// nyote: thwift c-copied fwom status.thwift with unused fiewds wenamed. rawr
s-stwuct thwiftseawchwesuwtuww {
  // nyext avaiwabwe fiewd i-id: 6. ^^;;  fiewds 2-4 wemoved. òωó

  // nyote: this i-is actuawwy the expanded uww.  wename aftew depwecated f-fiewds awe wemoved. σωσ
  1: wequiwed stwing o-owiginawuww

  // m-media type of the uww. 😳😳😳
  5: optionaw metadata_stowe.mediatypes m-mediatype
}(pewsisted='twue')

s-stwuct thwiftseawchwesuwtnamedentity {
  1: wequiwed s-stwing canonicawname
  2: wequiwed s-stwing entitytype
  3: wequiwed nyamedentitysouwce souwce
}(pewsisted='twue')

s-stwuct thwiftseawchwesuwtaudiospace {
  1: wequiwed stwing id
  2: wequiwed audiospacestate s-state
}(pewsisted='twue')

// even mowe metadata
stwuct thwiftseawchwesuwtextwametadata {
  // nyext avaiwabwe f-fiewd id: 49

  1: o-optionaw doubwe u-usewwangscowe
  2: optionaw boow hasdiffewentwang
  3: optionaw b-boow hasengwishtweetanddiffewentuiwang
  4: optionaw boow hasengwishuianddiffewenttweetwang
  5: o-optionaw i32 quotedcount
  6: o-optionaw doubwe q-quewyspecificscowe
  7: optionaw boow hasquote
  29: optionaw i64 quotedtweetid
  30: optionaw i-i64 quotedusewid
  31: o-optionaw seawch_wanguage.thwiftwanguage cawdwang
  8: o-optionaw i64 convewsationid
  9: optionaw boow issensitivecontent
  10: optionaw b-boow hasmuwtipwemediafwag
  11: o-optionaw boow pwofiweiseggfwag
  12: o-optionaw boow i-isusewnewfwag
  26: o-optionaw d-doubwe authowspecificscowe
  28: optionaw boow iscomposewsouwcecamewa

  // t-tempowawy v-v2 engagement c-countews, (///ˬ///✿) owiginaw o-ones in t-thwiftseawchwesuwtmetadata h-has wog()
  // appwied o-on them and then c-convewted to i-int in thwift, ^•ﻌ•^ which is effectivewy a pwematuwe
  // d-discwetization. 😳😳😳 it doesn't affect the scowing i-inside eawwybiwd but fow scowing and mw twaining
  // o-outside e-eawwybiwd, (U ᵕ U❁) they wewe bad. (U ﹏ U) these nyewwy added ones stowes a pwopew v-vawue of these
  // c-counts. σωσ this awso pwovides a-an easiew twansition t-to v2 countew when eawwybiwd is eventuawwy
  // weady to c-consume them fwom d-dw
  // see seawchquaw-9536, (˘ω˘) seawch-11181
  18: optionaw i32 wetweetcountv2
  19: optionaw i32 f-favcountv2
  20: o-optionaw i32 wepwycountv2
  // tweepcwed weighted vewsion of vawious e-engagement counts
  22: optionaw i32 weightedwetweetcount
  23: optionaw i32 weightedwepwycount
  24: optionaw i-i32 weightedfavcount
  25: optionaw i32 weightedquotecount

  // 2 bits - 0, ^^ 1, 2, 3+
  13: o-optionaw i32 nyummentions
  14: o-optionaw i32 numhashtags

  // 1 b-byte - 256 possibwe wanguages
  15: o-optionaw i-i32 winkwanguage
  // 6 b-bits - 64 p-possibwe vawues
  16: o-optionaw i32 pwevusewtweetengagement

  17: optionaw featuwes.thwiftseawchwesuwtfeatuwes f-featuwes

  // i-if the thwiftseawchquewy.wikedbyusewidfiwtew64 and t-thwiftseawchwewevanceoptions.cowwectfiewdhitattwibutions 
  // fiewds awe set, ^^ t-then this fiewd w-wiww contain the w-wist of aww usews in the quewy t-that wiked this t-tweet. (✿oωo)
  // othewwise, /(^•ω•^) t-this fiewd i-is nyot set. -.-
  27: o-optionaw wist<i64> wikedbyusewids


  // d-depwecated. ʘwʘ see seawchquaw-10321
  21: o-optionaw d-doubwe dopaminenonpewsonawizedscowe

  32: optionaw wist<thwiftseawchwesuwtnamedentity> nyamedentities
  33: o-optionaw w-wist<tweet_annotation.tweetentityannotation> entityannotations

  // h-heawth m-modew scowes fwom hmw
  34: optionaw doubwe toxicityscowe // (go/toxicity)
  35: o-optionaw doubwe p-pbwockscowe // (go/pbwock)
  36: o-optionaw doubwe e-expewimentawheawthmodewscowe1
  37: o-optionaw d-doubwe expewimentawheawthmodewscowe2
  38: optionaw doubwe expewimentawheawthmodewscowe3
  39: o-optionaw doubwe expewimentawheawthmodewscowe4

  40: optionaw i64 diwectedatusewid

  // heawth m-modew scowes fwom h-hmw (cont.)
  41: optionaw doubwe pspammytweetscowe // (go/pspammytweet)
  42: optionaw doubwe p-pwepowtedtweetscowe // (go/pwepowtedtweet)
  43: o-optionaw doubwe spammytweetcontentscowe // (go/spammy-tweet-content)
  // it is p-popuwated by wooking up usew tabwe a-and it is onwy a-avaiwabwe in a-awchive eawwybiwds wesponse
  44: optionaw boow isusewpwotected
  45: o-optionaw wist<thwiftseawchwesuwtaudiospace> s-spaces

  46: optionaw i64 excwusiveconvewsationauthowid
  47: o-optionaw stwing cawduwi
  48: optionaw boow fwombwuevewifiedaccount(pewsonawdatatype = 'usewvewifiedfwag')
}(pewsisted='twue')

// s-some basic metadata about a s-seawch wesuwt. XD  usefuw fow we-sowting, (U ᵕ U❁) fiwtewing, /(^•ω•^) e-etc.
//
// nyote: do nyot add n-nyew fiewd!!
// stop adding nyew fiewds to this stwuct, XD aww nyew fiewds shouwd go to
// thwiftseawchwesuwtextwametadata (vm-1897), ^•ﻌ•^ ow thewe wiww b-be pewfowmance i-issues in pwoduction. ( ͡o ω ͡o )
s-stwuct thwiftseawchwesuwtmetadata {
  // n-nyext avaiwabwe fiewd id: 86

  // -------- basic s-scowing metadata --------

  // when wesuwttype is wecency most scowing metadata w-wiww nyot be a-avaiwabwe. (U ﹏ U)
  1: w-wequiwed thwiftseawchwesuwttype w-wesuwttype

  // wewevance scowe computed fow this wesuwt. /(^•ω•^)
  3: optionaw doubwe s-scowe

  // twue i-if the wesuwt was skipped by the scowing function. 🥺  onwy set when t-the cowwect-aww
  // wesuwts c-cowwectow was used - i-in othew cases s-skipped wesuwts awe not wetuwned. rawr
  // the scowe wiww be scowingfunction.skip_hit when skipped is twue. :3
  43: o-optionaw boow skipped

  // optionawwy a-a wucene-stywe expwanation fow this wesuwt
  5: optionaw s-stwing expwanation


  // -------- nyetwowk-based s-scowing metadata --------

  // found the tweet in the twusted c-ciwcwe. σωσ
  6: o-optionaw boow istwusted

  // f-found t-the tweet in t-the diwect fowwows. òωó
  8: optionaw b-boow isfowwow

  // t-twue if the fwomusewid of t-this tweet was whitewisted by the dup / antigaming f-fiwtew. ^•ﻌ•^
  // this typicawwy i-indicates the wesuwt w-was fwom a tweet that matched a-a fwomusewid q-quewy. (U ᵕ U❁)
  9: optionaw boow dontfiwtewusew


  // -------- common document metadata --------

  // u-usew id of the a-authow. òωó  when iswetweet i-is twue, ^^ t-this is the usew id of the wetweetew
  // and nyot that of the o-owiginaw tweet. 😳😳😳
  7: optionaw i64 fwomusewid = 0

  // w-when iswetweet (ow packed featuwes equivawent) i-is twue, rawr x3 this is the status id of the
  // owiginaw tweet. ^^;; w-when iswepwy and getwepwysouwce a-awe twue, :3 this i-is the status id o-of the
  // owiginaw tweet. (✿oωo) in a-aww othew ciwcumstances t-this is 0. XD
  40: optionaw i-i64 shawedstatusid = 0

  // when h-hascawd (ow p-packed featuwes e-equivawent) is twue, (///ˬ///✿) this is one o-of seawchcawdtype. o.O
  49: o-optionaw i-i8 cawdtype = 0

  // -------- extended document m-metadata --------
  // this is additionaw metadata fwom facet fiewds and cowumn stwide fiewds. σωσ
  // w-wetuwn of t-these fiewds is contwowwed by t-thwiftseawchwesuwtmetadataoptions to
  // awwow fow fine-gwained c-contwow ovew when t-these fiewds a-awe wetuwned, òωó as a-an
  // optimization fow seawches w-wetuwning a wawge quantity of wesuwts. (///ˬ///✿)

  // w-wucene component o-of the wewevance scowe. :3  onwy wetuwned when
  // thwiftseawchwesuwtmetadataoptions.getwucenescowe i-is twue. mya
  31: optionaw doubwe w-wucenescowe = 0.0

  // uwws found in the tweet. ^^  o-onwy wetuwned when
  // thwiftseawchwesuwtmetadataoptions.gettweetuwws i-is twue. (˘ω˘)
  18: optionaw wist<thwiftseawchwesuwtuww> tweetuwws

  // depwecated i-in seawch-8616. -.-
  36: optionaw wist<i32> d-depwecated_topicids

  // facets a-avaiwabwe in t-this tweet, XD this wiww onwy be fiwwed if
  // thwiftseawchquewy.facetfiewdnames i-is set in the wequest. rawr
  22: optionaw wist<thwiftfacetwabew> f-facetwabews

  // the w-wocation of the w-wesuwt, >_< and the distance to it fwom the centew of the quewy
  // wocation. :3  onwy wetuwned when t-thwiftseawchwesuwtmetadataoptions.getwesuwtwocation is twue. :3
  35: optionaw thwiftseawchwesuwtgeowocation w-wesuwtwocation

  // p-pew fiewd hit attwibution. XD
  55: optionaw hit_attwibution.fiewdhitattwibution fiewdhitattwibution

  // whethew t-this has geowocation_type:geotag h-hit
  57: optionaw boow geotaghit = 0

  // the usew id of the a-authow of the souwce/wefewenced tweet (the tweet o-one wepwied
  // to, ( ͡o ω ͡o ) wetweeted and possibwy quoted, rawr x3 e-etc.) (seawch-8561)
  // onwy w-wetuwned when thwiftseawchwesuwtmetadataoptions.getwefewencedtweetauthowid is t-twue. (⑅˘꒳˘)
  60: optionaw i-i64 wefewencedtweetauthowid = 0

  // whethew t-this tweet has cewtain types o-of media. UwU
  // o-onwy wetuwned when t-thwiftseawchwesuwtmetadataoptions.getmediabits i-is twue. (˘ω˘)
  // "native v-video" is eithew consumew, (˘ω˘) p-pwo, rawr vine, ow p-pewiscope. nyaa~~
  // "native image" is an image hosted o-on pic.twittew.com. 😳😳😳
  62: optionaw b-boow hasconsumewvideo
  63: optionaw boow haspwovideo
  64: optionaw boow hasvine
  65: optionaw boow haspewiscope
  66: optionaw boow hasnativevideo
  67: o-optionaw boow hasnativeimage

  // p-packed featuwes fow this wesuwt. ^^;; t-this fiewd i-is nyevew popuwated. >w<
  50: optionaw s-status.packedfeatuwes depwecated_packedfeatuwes

  // t-the featuwes stowed i-in eawwybiwd

  // fwom integew 0 fwom eawwybiwdfeatuweconfiguwation:
  16: optionaw boow iswetweet
  71: optionaw boow issewftweet
  10: o-optionaw boow isoffensive
  11: optionaw b-boow haswink
  12: optionaw boow h-hastwend
  13: optionaw boow iswepwy
  14: optionaw boow hasmuwtipwehashtagsowtwends
  23: optionaw boow fwomvewifiedaccount
  // static text quawity scowe. ʘwʘ  this is actuawwy an int between 0 a-and 100. XD
  30: o-optionaw doubwe t-textscowe
  51: optionaw seawch_wanguage.thwiftwanguage w-wanguage

  // f-fwom integew 1 f-fwom eawwybiwdfeatuweconfiguwation:
  52: optionaw boow hasimage
  53: o-optionaw boow hasvideo
  28: o-optionaw boow hasnews
  48: o-optionaw b-boow hascawd
  61: o-optionaw boow h-hasvisibwewink
  // t-tweep cwed aka usew wep.  t-this is actuawwy a-an int between 0 a-and 100. (ˆ ﻌ ˆ)♡
  32: o-optionaw doubwe u-usewwep
  24: o-optionaw boow isusewspam
  25: optionaw b-boow isusewnsfw
  26: o-optionaw b-boow isusewbot
  54: o-optionaw boow isusewantisociaw

  // fwom integew 2 fwom eawwybiwdfeatuweconfiguwation:

  // w-wetweet, >_< fav, wepwy, >_< embeds c-counts, ʘwʘ and video view counts awe appwoximate o-onwy. rawr
  // nyote t-that wetweetcount, nyaa~~ f-favcount and wepwycount a-awe nyot owiginaw u-unnowmawized vawues, >w<
  // but aftew a wog2() function fow histowicaw weason, (ˆ ﻌ ˆ)♡ this woses us some g-gwanuwawity. :3
  // fow mowe accuwate counts, OwO use {wetweet, fav, mya w-wepwy}countv2 in e-extwametadata. /(^•ω•^)
  2: optionaw i32 w-wetweetcount
  33: o-optionaw i32 f-favcount
  34: o-optionaw i32 wepwycount
  58: o-optionaw i32 embedsimpwessioncount
  59: o-optionaw i-i32 embedsuwwcount
  68: optionaw i32 videoviewcount

  // p-pawus scowe. nyaa~~  this i-is actuawwy an int between 0 and 100.
  29: o-optionaw d-doubwe pawusscowe

  // extwa f-featuwe data, (˘ω˘) aww nyew featuwe fiewds you want t-to wetuwn fwom e-eawwybiwd shouwd g-go into
  // this o-one, (ꈍᴗꈍ) the outew one is awways w-weaching its wimit o-of the nyumbew o-of fiewds jvm can
  // comfowtabwy s-suppowt!!
  86: optionaw thwiftseawchwesuwtextwametadata extwametadata

  // integew 3 is omitted, >w< see expfeatuwevawues above fow mowe detaiws. nyaa~~

  // fwom integew 4 fwom eawwybiwdfeatuweconfiguwation:
  // s-signatuwe, (✿oωo) fow d-dupwicate detection and wemovaw. (⑅˘꒳˘)
  4: optionaw i32 signatuwe

  // -------- things used onwy b-by the bwendew --------

  // s-sociaw pwoof of the tweet, (ˆ ﻌ ˆ)♡ fow nyetwowk discovewy. òωó
  // d-do nyot use t-these fiewds outside of nyetwowk d-discovewy. -.-
  41: o-optionaw wist<i64> wetweetedusewids64
  42: o-optionaw wist<i64> wepwyusewids64

  // s-sociaw connection b-between the seawch usew and this wesuwt. 😳😳😳
  19: optionaw s-sociaw.thwiftsociawcontext s-sociawcontext

  // u-used by wewevancetimewineseawchwowkfwow, rawr x3 w-whethew a tweet shouwd b-be highwighted o-ow nyot
  46: optionaw b-boow highwightwesuwt

  // u-used by wewevancetimewineseawchwowkfwow, 😳 the highwight context o-of the highwighted t-tweet
  47: optionaw highwight.thwifthighwightcontext highwightcontext

  // the penguin vewsion used to tokenize t-the tweets b-by the sewving eawwybiwd index a-as defined
  // in com.twittew.common.text.vewsion.penguinvewsion
  56: optionaw i8 penguinvewsion

  69: o-optionaw b-boow isnuwwcast

  // t-this is the nyowmawized w-watio(0.00 to 1.00) o-of nyth token(stawting befowe 140) divided b-by
  // nyumtokens a-and then nyowmawized i-into 16 p-positions(4 bits) b-but on a scawe o-of 0 to 100% as
  // we unnowmawize it fow you
  70: optionaw doubwe tokenat140dividedbynumtokensbucket

}(pewsisted='twue')

// quewy wevew wesuwt s-stats. 🥺
// nyext id: 20
stwuct t-thwiftseawchwesuwtswewevancestats {
  1: o-optionaw i32 nyumscowed = 0
  // skipped documents count, (⑅˘꒳˘) t-they wewe a-awso scowed but theiw scowes got i-ignowed (skipped), (✿oωo) nyote that this i-is diffewent
  // fwom nyumwesuwtsskipped in the thwiftseawchwesuwts. 😳
  2: optionaw i-i32 nyumskipped = 0
  3: optionaw i32 nyumskippedfowantigaming = 0
  4: optionaw i32 numskippedfowwowweputation = 0
  5: optionaw i32 nyumskippedfowwowtextscowe = 0
  6: optionaw i32 nyumskippedfowsociawfiwtew = 0
  7: o-optionaw i32 n-nyumskippedfowwowfinawscowe = 0
  8: o-optionaw i32 o-owdestscowedtweetageinseconds = 0

  // mowe countews fow vawious f-featuwes. mya
  9:  optionaw i32 n-nyumfwomdiwectfowwows = 0
  10: optionaw i32 nyumfwomtwustedciwcwe = 0
  11: optionaw i-i32 nyumwepwies = 0
  12: o-optionaw i32 nyumwepwiestwusted = 0
  13: o-optionaw i32 nyumwepwiesoutofnetwowk = 0
  14: optionaw i-i32 nyumsewftweets = 0
  15: optionaw i32 nyumwithmedia = 0
  16: optionaw i32 nyumwithnews = 0
  17: optionaw i32 nyumspamusew = 0
  18: optionaw i-i32 nyumoffensive = 0
  19: o-optionaw i32 nyumbot = 0
}(pewsisted='twue')

// pew wesuwt debug info. (U ﹏ U)
stwuct thwiftseawchwesuwtdebuginfo {
  1: optionaw stwing hostname
  2: o-optionaw stwing cwustewname
  3: optionaw i32 p-pawtitionid
  4: o-optionaw stwing t-tiewname
}(pewsisted='twue')

stwuct t-thwiftseawchwesuwt {
  // nyext avaiwabwe fiewd id: 22

  // wesuwt status id. 😳
  1: wequiwed i64 id

  // t-tweetypie status o-of the seawch wesuwt
  7: o-optionaw d-depwecated.status tweetypiestatus
  19: o-optionaw tweet.tweet t-tweetypietweet  // v2 stwuct

  // if the seawch wesuwt is a wetweet, 🥺 t-this fiewd c-contains the souwce t-tweetypie s-status. -.-
  10: optionaw depwecated.status s-souwcetweetypiestatus
  20: o-optionaw tweet.tweet souwcetweetypietweet  // v2 stwuct

  // if the seawch w-wesuwt is a quote t-tweet, (ˆ ﻌ ˆ)♡ this fiewd contains the quoted tweetypie status. >_<
  17: o-optionaw depwecated.status quotedtweetypiestatus
  21: o-optionaw t-tweet.tweet quotedtweetypietweet  // v-v2 stwuct

  // additionaw metadata about a seawch wesuwt. rawr
  5: optionaw thwiftseawchwesuwtmetadata metadata

  // h-hit highwights fow vawious p-pawts of this tweet
  // fow tweet text
  6: o-optionaw wist<hits.thwifthits> hithighwights
  // f-fow the titwe a-and descwiption i-in the cawd expando. rawr x3
  12: o-optionaw w-wist<hits.thwifthits> cawdtitwehithighwights
  13: o-optionaw wist<hits.thwifthits> cawddescwiptionhithighwights

  // expansion types, OwO if expandwesuwt == f-fawse, nyaa~~ the expansions set shouwd be i-ignowed. 😳
  8: o-optionaw boow expandwesuwt = 0
  9: o-optionaw set<expansions.thwifttweetexpansiontype> expansions

  // onwy set if this is a pwomoted tweet
  11: o-optionaw adsewvew_common.adimpwession a-adimpwession

  // w-whewe t-this tweet is fwom
  // since thwiftseawchwesuwt used nyot onwy as an eawwybiwd wesponse, UwU but awso an intewnaw
  // d-data twansfew object of bwendew, ʘwʘ the vawue o-of this fiewd is m-mutabwe in bwendew, 🥺 n-nyot
  // nyecessawiwy wefwecting e-eawwybiwd wesponse. 🥺
  14: optionaw thwifttweetsouwce tweetsouwce

  // the featuwes of a tweet used fow wewevance timewine
  // this fiewd is popuwated by b-bwendew in wewevancetimewineseawchwowkfwow
  15: optionaw featuwes.thwifttweetfeatuwes tweetfeatuwes

  // t-the c-convewsation context of a tweet
  16: o-optionaw c-convewsation.thwiftconvewsationcontext convewsationcontext

  // pew-wesuwt debugging i-info that's p-pewsisted acwoss mewges. òωó
  18: optionaw thwiftseawchwesuwtdebuginfo d-debuginfo
}(pewsisted='twue')

e-enum thwiftfacetwankingmode {
  c-count = 0, 🥺
  f-fiwtew_with_tewm_statistics = 1, ʘwʘ
}

stwuct thwiftfacetfiewdwequest {
  // n-nyext avaiwabwe fiewd id: 4
  1: wequiwed s-stwing fiewdname
  2: o-optionaw i32 nyumwesuwts = 5

  // use f-facetwankingoptions i-in thwiftfacetwequest instead
  3: optionaw thwiftfacetwankingmode wankingmode = t-thwiftfacetwankingmode.count
}(pewsisted='twue')

stwuct t-thwiftfacetwequest {
  // nyext a-avaiwabwe fiewd id: 7
  1: optionaw wist<thwiftfacetfiewdwequest> f-facetfiewds
  5: optionaw wanking.thwiftfacetwankingoptions facetwankingoptions
  6: optionaw boow usingquewycache = 0
}(pewsisted='twue')

stwuct t-thwifttewmwequest {
  1: optionaw stwing fiewdname = "text"
  2: w-wequiwed s-stwing tewm
}(pewsisted='twue')

e-enum thwifthistogwamgwanuwawitytype {
  minutes = 0
  houws = 1, XD
  d-days = 2, OwO
  c-custom = 3, ʘwʘ

  pwace_howdew4 = 4, :3
  p-pwace_howdew5 = 5, nyaa~~
}

s-stwuct thwifthistogwamsettings {
  1: w-wequiwed thwifthistogwamgwanuwawitytype g-gwanuwawity
  2: o-optionaw i-i32 nyumbins = 60
  3: o-optionaw i32 sampwingwate = 1
  4: optionaw i-i32 binsizeinseconds   // the b-bin size, >w< onwy used if gwanuwawity is set to c-custom. (U ᵕ U❁)
}(pewsisted='twue')

// n-nyext id is 4
stwuct t-thwifttewmstatisticswequest {
  1: optionaw w-wist<thwifttewmwequest> t-tewmwequests
  2: optionaw t-thwifthistogwamsettings h-histogwamsettings
  // if this is set t-to twue, :3 even if thewe is nyo t-tewmwequests above, (ˆ ﻌ ˆ)♡ s-so wong as the h-histogwamsettings
  // i-is set, o.O eawwybiwd wiww wetuwn a nyuww->thwifttewmwesuwts entwy in the t-tewmwesuwts map, rawr x3 containing
  // t-the gwobaw tweet count histogwam f-fow cuwwent quewy, (U ᵕ U❁) w-which is the nyumbew of tweets m-matching this
  // q-quewy in diffewent minutes/houws/days.
  3: optionaw boow i-incwudegwobawcounts = 0
  // w-when this is set, (✿oωo) the backgwound facets caww does anothew seawch in owdew to find the best
  // wepwesentative tweet fow a given tewm wequest, /(^•ω•^) the wepwesentative t-tweet is stowed i-in the
  // metadata o-of the tewmstats w-wesuwt
  4: optionaw boow scowetweetsfowwepwesentatives = 0
}(pewsisted='twue')

// n-nyext i-id is 12
stwuct t-thwiftfacetcountmetadata {
  // t-this is the id of the fiwst tweet in the index that contained this facet
  1: optionaw i-i64 statusid = -1

  // whethew t-the tweet w-with the above s-statusid is nysfw, o.O fwom an antisociaw u-usew, (U ᵕ U❁)
  // mawked as sensitive content, 🥺 etc. òωó
  10: optionaw boow statuspossibwysensitive

  // t-the id of the usew who sent t-the tweet above - o-onwy wetuwned if
  // statusid is wetuwned too
  // nyote: fow n-nyative photos we may nyot be a-abwe to detewmine the usew, ʘwʘ
  // even though the s-statusid can be wetuwned. rawr x3 this is because the statusid
  // c-can be detewmined fwom t-the uww, >_< but the usew can't a-and the tweet may
  // n-nyot be in the index anymowe. (˘ω˘) in this case statusid wouwd b-be set but
  // twittewusewid wouwd nyot. ^•ﻌ•^
  2: optionaw i64 twittewusewid = -1

  // the wanguage of the tweet above. (✿oωo)
  8: optionaw s-seawch_wanguage.thwiftwanguage s-statuswanguage

  // optionawwy w-whitewist the fwomusewid fwom d-dup/twittewusewid f-fiwtewing
  3: o-optionaw boow dontfiwtewusew = 0;

  // if this f-facet is a native photo we wetuwn fow convenience the
  // twimg uww
  4: optionaw s-stwing nyativephotouww

  // o-optionawwy wetuwns s-some debug i-infowmation about this facet
  5: o-optionaw stwing expwanation

  // t-the cweated_at v-vawue fow the tweet fwom statusid - onwy wetuwned
  // i-if statusid i-is wetuwned t-too
  6: optionaw i-i64 cweated_at

  // t-the maximum tweepcwed of the hits that c-contained this f-facet
  7: optionaw i-i32 maxtweepcwed

  // whethew this facet wesuwt is fowce insewted, ( ͡o ω ͡o ) i-instead o-of owganicawwy wetuwned f-fwom seawch. (˘ω˘)
  // this fiewd i-is onwy used in bwendew to m-mawk the fowce-insewted f-facet wesuwts
  // (fwom w-wecent tweets, etc). >w<
  11: optionaw boow fowceinsewted = 0
}(pewsisted='twue')

s-stwuct thwifttewmwesuwts {
  1: wequiwed i32 totawcount
  2: optionaw w-wist<i32> histogwambins
  3: optionaw thwiftfacetcountmetadata metadata
}(pewsisted='twue')

s-stwuct thwifttewmstatisticswesuwts {
  1: wequiwed m-map<thwifttewmwequest,thwifttewmwesuwts> tewmwesuwts
  2: o-optionaw thwifthistogwamsettings h-histogwamsettings
  // i-if histogwamsettings a-awe set, (⑅˘꒳˘) this wiww have a wist of t-thwifthistogwamsettings.numbins binids, (U ᵕ U❁)
  // that the cowwesponding histogwambins in thwifttewmwesuwts w-wiww have c-counts fow. OwO
  // t-the binids wiww c-cowwespond to t-the times of the hits matching the d-dwiving seawch q-quewy fow this
  // tewm statistics wequest. òωó
  // if thewe wewe n-nyo hits matching the seawch quewy, ^•ﻌ•^ nyumbins binids w-wiww be wetuwned, 😳😳😳 but the
  // v-vawues of the binids wiww nyot meaningfuwwy c-cowwespond to anything wewated t-to the quewy, o.O and
  // shouwd nyot b-be used. :3 such c-cases can be identified b-by thwiftseawchwesuwts.numhitspwocessed being
  // set to 0 in the wesponse, ^•ﻌ•^ and the wesponse nyot being eawwy tewminated. >w<
  3: optionaw w-wist<i32> binids
  // if set, :3 this id indicates t-the id of the minimum (owdest) b-bin that has been c-compwetewy seawched,
  // even i-if the quewy was e-eawwy tewminated. (✿oωo) if nyot set nyo bin was seawched fuwwy, rawr ow n-nyo histogwam
  // was wequested.
  // n-note that if e.g. UwU a quewy onwy matches a b-bin pawtiawwy (due to e.g. (⑅˘꒳˘) a since o-opewatow) the bin
  // is stiww c-considewed fuwwy s-seawched if the quewy did nyot eawwy tewminate. σωσ
  4: optionaw i32 mincompwetebinid
}(pewsisted='twue')

s-stwuct t-thwiftfacetcount {
  // t-the text of the facet
  1: wequiwed stwing f-facetwabew

  // depwecated; c-cuwwentwy matches weightedcount f-fow backwawds-compatibiwity weasons
  2: optionaw i32 facetcount

  // t-the simpwe count of tweets t-that contained t-this facet, (///ˬ///✿) without any
  // weighting appwied
  7: optionaw i32 simpwecount

  // a-a weighted vewsion of the count, (˘ω˘) using signaws w-wike tweepcwed, ^•ﻌ•^ p-pawus, etc. ʘwʘ
  8: o-optionaw i32 weightedcount

  // t-the nyumbew of times this facet occuwwed i-in tweets matching the backgwound q-quewy
  // using t-the tewm statistics a-api - onwy set if fiwtew_with_tewm_statistics w-was used
  3: o-optionaw i32 b-backgwoundcount

  // t-the wewevance scowe that w-was computed fow this facet if fiwtew_with_tewm_statistics
  // w-was used
  4: optionaw d-doubwe scowe

  // a countew fow how often this facet was penawized
  5: o-optionaw i32 penawtycount

  6: optionaw thwiftfacetcountmetadata metadata
}(pewsisted='twue')

// wist of facet w-wabews and counts f-fow a given facet fiewd, 😳 the
// totaw count fow this fiewd, òωó and a quawity scowe fow this fiewd
stwuct thwiftfacetfiewdwesuwts {
  1: w-wequiwed w-wist<thwiftfacetcount> t-topfacets
  2: w-wequiwed i-i32 totawcount
  3: o-optionaw doubwe scowequawity
  4: o-optionaw i32 totawscowe
  5: o-optionaw i32 totawpenawty

  // t-the watio of the tweet wanguage i-in the tweets w-with this facet f-fiewd, a map fwom t-the wanguage
  // n-nyame to a nyumbew between (0.0, ( ͡o ω ͡o ) 1.0]. onwy w-wanguages with watio highew than 0.1 wiww be incwuded. :3
  6: optionaw m-map<seawch_wanguage.thwiftwanguage, (ˆ ﻌ ˆ)♡ doubwe> wanguagehistogwam
}

s-stwuct thwiftfacetwesuwts {
  1: w-wequiwed map<stwing, XD thwiftfacetfiewdwesuwts> f-facetfiewds
  2: optionaw i-i32 backgwoundnumhits
  // w-wetuwns optionawwy a w-wist of usew ids that shouwd nyot g-get fiwtewed
  // o-out by things wike antigaming f-fiwtews, :3 because these usews wewe expwicitwy
  // quewied fow
  // n-nyote that thwiftfacetcountmetadata w-wetuwns awweady dontfiwtewusew
  // fow f-facet wequests in which case this w-wist is nyot nyeeded. nyaa~~ howevew, i-it
  // is nyeeded fow subsequent t-tewm statistics quewies, 😳😳😳 wewe u-usew id wookups
  // awe pewfowmed, (⑅˘꒳˘) but a diffewent b-backgwound q-quewy is used. ^^
  3: o-optionaw set<i64> u-usewidwhitewist
}

s-stwuct t-thwiftseawchwesuwts {
  // nyext a-avaiwabwe fiewd i-id: 23
  1: wequiwed w-wist<thwiftseawchwesuwt> wesuwts = []

  // (seawch-11950): n-nyow wesuwtoffset is depwecated, 🥺 so thewe is n-nyo use in nyumwesuwtsskipped t-too. OwO
  9: optionaw i32 depwecated_numwesuwtsskipped

  // n-nyumbew o-of docs that matched the quewy and w-wewe pwocessed. ^^
  7: o-optionaw i-i32 numhitspwocessed

  // w-wange of status ids seawched, nyaa~~ fwom max id to min id (both incwusive). ^^
  // these may be unset in case t-that the seawch quewy contained i-id ow time
  // opewatows that w-wewe compwetewy out of wange fow t-the given index. (✿oωo)
  10: o-optionaw i64 maxseawchedstatusid
  11: o-optionaw i64 minseawchedstatusid

  // t-time wange that was seawched (both incwusive). ^^
  19: o-optionaw i32 maxseawchedtimesinceepoch
  20: optionaw i-i32 minseawchedtimesinceepoch

  12: optionaw t-thwiftseawchwesuwtswewevancestats w-wewevancestats

  // o-ovewaww quawity of this seawch w-wesuwt set
  13: optionaw doubwe scowe = -1.0
  18: optionaw d-doubwe nysfwwatio = 0.0

  // the count of hit documents in each wanguage. òωó
  14: optionaw map<seawch_wanguage.thwiftwanguage, (⑅˘꒳˘) i32> wanguagehistogwam

  // hit counts pew time p-pewiod:
  // the k-key is a time cutoff in miwwiseconds (e.g. (U ﹏ U) 60000 m-msecs ago). OwO
  // t-the vawue is the numbew of hits that awe mowe wecent than the c-cutoff. (///ˬ///✿)
  15: o-optionaw map<i64, o.O i32> hitcounts

  // t-the totaw c-cost fow this q-quewy
  16: optionaw d-doubwe quewycost

  // set to nyon-0 if this q-quewy was tewminated eawwy (eithew due to a timeout, (ꈍᴗꈍ) ow exceeded q-quewy cost)
  // when getting this wesponse fwom a singwe eawwybiwd, this wiww be set to 1, -.- if t-the quewy
  // tewminated eawwy. òωó
  // when getting this wesponse f-fwom a seawch w-woot, OwO this shouwd b-be set to the nyumbew of individuaw
  // eawwybiwd w-wequests that w-wewe tewminated e-eawwy. (U ﹏ U)
  17: optionaw i32 nyumpawtitionseawwytewminated

  // if thwiftseawchwesuwts w-wetuwns featuwes in featuwes.thwiftseawchwesuwtfeatuwe f-fowmat, ^^;; this
  // fiewd wouwd define the schema of the featuwes. ^^;;
  // i-if the eawwybiwd schema is a-awweady in the cwient cached schemas i-indicated i-in the wequest, XD then
  // seawchfeatuweschema w-wouwd onwy have (vewsion, OwO checksum) i-infowmation. (U ﹏ U)
  //
  // notice that eawwybiwd woot onwy sends one s-schema back to the supewwoot even though eawwybiwd
  // w-woot might weceive muwtipwe v-vewsion of s-schemas. >w<
  //
  // eawwybiwd woots' s-schema mewge/choose wogic w-when wetuwning wesuwts to supewwoot:
  // . >w< pick t-the most occuwwed v-vewsioned schema and wetuwn the s-schema to the s-supewwoot
  // . (ˆ ﻌ ˆ)♡ if the supewwoot a-awweady caches the schema, (ꈍᴗꈍ) onwy send the vewsion infowmation back
  //
  // supewwoots' schema m-mewge/choose wogic when wetuwning wesuwts to cwients:
  // . 😳😳😳 pick the schema based o-on the owdew o-of: weawtime > p-pwotected > awchive
  // . because o-of the above o-owdewing, mya it is possibwe that awchive e-eawwybiwd schema with a nyew f-fwush
  //   v-vewsion (with nyew bit featuwes) might be wost to owdew weawtime e-eawwybiwd schema; t-this is
  //   considewed to to be wawe and a-acceptabwe because one weawtime e-eawwybiwd depwoy w-wouwd fix it
  21: o-optionaw featuwes.thwiftseawchfeatuweschema f-featuweschema

  // how wong it t-took to scowe the wesuwts in eawwybiwd (in n-nanoseconds). (˘ω˘) the nyumbew of wesuwts
  // that wewe scowed s-shouwd be s-set in nyumhitspwocessed. (✿oωo)
  // expected t-to onwy b-be set fow wequests t-that actuawwy d-do scowing (i.e. (ˆ ﻌ ˆ)♡ w-wewevance and toptweets). (ˆ ﻌ ˆ)♡
  22: optionaw i64 s-scowingtimenanos

  8: optionaw i32 depwecated_numdocspwocessed
}

// n-nyote: eawwybiwd nyo wongew w-wespects this fiewd, nyaa~~ as it does nyot contain statuses. :3
// bwendew shouwd wespect i-it. (✿oωo)
enum eawwybiwdwetuwnstatustype {
  n-nyo_status = 0
  // d-depwecated
  depwecated_basic_status = 1, (✿oωo)
  // depwecated
  depwecated_seawch_status = 2, (⑅˘꒳˘)
  t-tweetypie_status = 3, >_<

  p-pwace_howdew4 = 4, >_<
  p-pwace_howdew5 = 5, ʘwʘ
}

s-stwuct adjustedwequestpawams {
  // nyext avaiwabwe fiewd id: 4

  // adjusted vawue fow eawwybiwdwequest.seawchquewy.numwesuwts. (U ﹏ U)
  1: o-optionaw i32 n-numwesuwts

  // a-adjusted vawue fow eawwybiwdwequest.seawchquewy.maxhitstopwocess and
  // eawwybiwdwequest.seawchquewy.wewevanceoptions.maxhitstopwocess. ^^
  2: o-optionaw i32 maxhitstopwocess

  // adjusted vawue fow eawwybiwdwequest.seawchquewy.wewevanceoptions.wetuwnawwwesuwts
  3: o-optionaw boow wetuwnawwwesuwts
}

stwuct e-eawwybiwdwequest {
  // nyext avaiwabwe fiewd id: 36

  // -------- c-common wequest options --------
  // these f-fiewds contain options wespected by aww kinds of eawwybiwd w-wequests. >_<

  // seawch quewy containing genewaw e-eawwybiwd wetwievaw and hit cowwection o-options. OwO
  // a-awso contains the options specific to seawch wequests. 😳
  1: wequiwed thwiftseawchquewy s-seawchquewy

  // common wpc infowmation - cwient hostname and wequest id. (U ᵕ U❁)
  12: optionaw stwing cwienthost
  13: o-optionaw s-stwing cwientwequestid

  // a stwing identifying the cwient t-that initiated the wequest. 😳😳😳
  // e-ex: macaw-seawch.pwod, w-webfowaww.pwod, -.- w-webfowaww.staging. (U ᵕ U❁)
  // the intention is to twack the woad we get fwom e-each cwient, -.- a-and eventuawwy enfowce
  // p-pew-cwient q-qps quotas, (U ﹏ U) but this fiewd couwd awso be used to awwow access t-to cewtain f-featuwes
  // onwy to cewtain cwients, ^^ etc.
  21: optionaw stwing cwientid

  // the time (in miwwis s-since epoch) when the eawwybiwd cwient issued this wequest. UwU
  // c-can be used t-to estimate wequest t-timeout time, c-captuwing in-twansit time fow the wequest. o.O
  23: optionaw i64 cwientwequesttimems

  // caching p-pawametews used by eawwybiwd w-woots. ^^
  24: optionaw caching.cachingpawams c-cachingpawams

  // d-depwecated. 🥺 see seawch-2784
  // eawwybiwd wequests wiww be eawwy tewminated in a-a best-effowt way to pwevent them f-fwom
  // exceeding t-the given t-timeout. 😳  if timeout i-is <= 0 this eawwy tewmination c-cwitewia is
  // disabwed. (⑅˘꒳˘)
  17: optionaw i32 t-timeoutms = -1

  // d-depwecated. >w< s-see seawch-2784
  // eawwybiwd wequests wiww b-be eawwy tewminated in a best-effowt w-way to pwevent t-them fwom
  // e-exceeding the g-given quewy cost. >_<  if maxquewycost <= 0 this eawwy tewmination c-cwitewia
  // is disabwed.
  20: optionaw doubwe maxquewycost = -1


  // -------- wequest-type s-specific options --------
  // t-these fiewds contain options fow one specific kind o-of wequest. rawr x3  i-if one of these o-options
  // is s-set the wequest wiww be considewed to be the appwopwiate t-type of wequest. >_<

  // options fow facet c-counting wequests. XD
  11: optionaw t-thwiftfacetwequest f-facetwequest

  // o-options f-fow tewm statistics w-wequests. mya
  14: o-optionaw thwifttewmstatisticswequest tewmstatisticswequest


  // -------- debug options --------
  // u-used fow debugging o-onwy. (///ˬ///✿)

  // debug mode, OwO 0 fow nyo d-debug infowmation. mya
  15: o-optionaw i-i8 debugmode = 0

  // c-can be u-used to pass extwa debug awguments to eawwybiwd. OwO
  34: optionaw eawwybiwddebugoptions debugoptions

  // s-seawches a-a specific segment by time swice i-id if set and s-segment id is > 0. :3
  22: optionaw i-i64 seawchsegmentid

  // -------- things used onwy by the bwendew --------
  // t-these fiewds awe used by the b-bwendew and cwients o-of the bwendew, òωó but nyot b-by eawwybiwd. OwO

  // s-specifies nyani k-kind of status o-object to wetuwn, OwO i-if any. (U ᵕ U❁)
  7: optionaw eawwybiwdwetuwnstatustype w-wetuwnstatustype


  // -------- t-things used by the woots --------
  // these f-fiewds awe nyot in use by eawwybiwds themsewves, mya b-but awe in use by eawwybiwd woots
  // (and theiw cwients).
  // t-these fiewds w-wive hewe since w-we cuwwentwy weuse t-the same thwift wequest and wesponse stwucts
  // f-fow both e-eawwybiwds and eawwybiwd woots, UwU and couwd potentiawwy b-be moved out i-if we wewe to
  // i-intwoduce sepawate wequest / w-wesponse stwucts s-specificawwy fow the woots. /(^•ω•^)

  // we have a thweshowd fow how many hash pawtition wequests nyeed to succeed a-at the woot wevew
  // in owdew fow the eawwybiwd woot wequest to be considewed successfuw. UwU
  // each type ow eawwybiwd q-quewies (e.g. w-wewevance, UwU ow tewm statistics) has a pwedefined defauwt
  // t-thweshowd vawue (e.g. /(^•ω•^) 90% ow hash pawtitions nyeed to succeed f-fow a wecency quewy). XD
  // t-the c-cwient can optionawwy set the thweshowd v-vawue to be something othew t-than the defauwt, ^^;;
  // by setting t-this fiewd t-to a vawue in the w-wange of 0 (excwusive) t-to 1 (incwusive). nyaa~~
  // if this vawue is s-set outside of t-the (0, mya 1] wange, (✿oωo) a cwient_ewwow eawwybiwdwesponsecode wiww
  // b-be wetuwned. rawr
  25: o-optionaw doubwe successfuwwesponsethweshowd

  // whewe does the quewy come fwom?
  26: optionaw q-quewy.thwiftquewysouwce q-quewysouwce

  // whethew to get awchive w-wesuwts this fwag is advisowy. -.- a-a wequest may stiww be westwicted fwom
  // getting wequwts f-fwom the awchive based on the wequesting cwient, σωσ q-quewy souwce, mya wequested
  // time/id wange, ^•ﻌ•^ etc.
  27: o-optionaw b-boow getowdewwesuwts

  // the wist of usews fowwowed by the cuwwent usew. nyaa~~
  // u-used to westwict t-the vawues in t-the fwomusewidfiwtew64 f-fiewd when sending a wequest
  // to the p-pwotectected cwustew. 🥺
  28: o-optionaw w-wist<i64> f-fowwowedusewids

  // t-the adjusted pawametews fow the pwotected wequest. (✿oωo)
  29: optionaw adjustedwequestpawams adjustedpwotectedwequestpawams

  // the adjusted p-pawametews fow the fuww awchive w-wequest. rawr
  30: o-optionaw adjustedwequestpawams adjustedfuwwawchivewequestpawams

  // w-wetuwn onwy t-the pwotected t-tweets. (ˆ ﻌ ˆ)♡ this fwag is used by the supewwoot to wetuwn wewevance
  // wesuwts that c-contain onwy pwotected tweets. ^^;;
  31: optionaw boow g-getpwotectedtweetsonwy

  // t-tokenize sewiawized quewies with t-the appwopwiate pengin vewsion(s). OwO
  // onwy has an effect on s-supewwoot. mya
  32: o-optionaw boow wetokenizesewiawizedquewy

  // fwag t-to ignowe tweets that awe vewy wecent and couwd b-be incompwetewy i-indexed. (⑅˘꒳˘)
  // i-if fawse, (U ﹏ U) wiww awwow quewies to see wesuwts that m-may viowate impwicit s-stweaming
  // g-guawantees a-and wiww seawch t-tweets that have b-been pawtiawwy indexed. (U ﹏ U)
  // s-see go/indexing-watency f-fow mowe detaiws. XD when enabwed, OwO p-pwevents seeing tweets
  // that awe wess t-than 15 seconds o-owd (ow a simiwawwy c-configuwed t-thweshowd). (///ˬ///✿)
  // m-may be set to f-fawse unwess expwicitwy set to twue. XD
  33: optionaw b-boow skipvewywecenttweets = 1

  // s-setting a-an expewimentaw c-cwustew wiww wewoute t-twaffic at the weawtime woot w-wayew to an expewimentaw
  // e-eawwybiwd cwustew. σωσ this wiww have n-nyo impact if set on wequests to anywhewe othew t-than weawtime w-woot. (///ˬ///✿)
  35: optionaw expewimentcwustew e-expewimentcwustewtouse

  // c-caps nyumbew of wesuwts wetuwned by woots aftew mewging wesuwts fwom diffewent e-eawwybiwd pawtitions/cwustews. 😳 
  // i-if not set, rawr x3 t-thwiftseawchquewy.numwesuwts ow cowwectowpawams.numwesuwtstowetuwn w-wiww be used to cap wesuwts. 😳 
  // this pawametew wiww be ignowed if thwiftwewevanceoptions.wetuwnawwwesuwts i-is set to twue. ^^;;
  36: optionaw i32 nyumwesuwtstowetuwnatwoot
}

enum eawwybiwdwesponsecode {
  success = 0, òωó
  pawtition_not_found = 1, >w<
  p-pawtition_disabwed = 2, >w<
  t-twansient_ewwow = 3, òωó
  p-pewsistent_ewwow = 4, 😳😳😳
  cwient_ewwow = 5, ( ͡o ω ͡o )
  pawtition_skipped = 6, o.O
  // wequest was queued up on the sewvew fow so wong that it timed o-out, UwU and was nyot
  // exekawaii~d at aww. rawr
  s-sewvew_timeout_ewwow = 7, mya
  tiew_skipped = 8, (✿oωo)
  // nyot enough pawtitions wetuwned a-a successfuw wesponse. ( ͡o ω ͡o ) the mewged wesponse wiww h-have pawtition
  // counts and eawwy tewmination info set, nyaa~~ but w-wiww nyot have seawch wesuwts. (///ˬ///✿)
  too_many_pawtitions_faiwed_ewwow = 9, 😳😳😳
  // cwient w-went ovew its quota, UwU and the w-wequest was thwottwed. 🥺
  quota_exceeded_ewwow = 10, (///ˬ///✿)
  // cwient's wequest is bwocked based on s-seawch infwa's p-powicy. (⑅˘꒳˘) seawch infwa c-can can bwock c-cwient's
  // w-wequests based on the quewy souwce o-of the wequest. (✿oωo)
  wequest_bwocked_ewwow = 11, òωó

  cwient_cancew_ewwow = 12, ^^

  cwient_bwocked_by_tiew_ewwow = 13, rawr

  pwace_howdew_2015_09_21 = 14,
}

// a wecowded wequest and wesponse. ^^;;
stwuct eawwybiwdwequestwesponse {
  // w-whewe did we send this wequest to. (ˆ ﻌ ˆ)♡
  1: optionaw s-stwing sentto;
  2: optionaw e-eawwybiwdwequest w-wequest;
  // this can't be an e-eawwybiwdwesponse, (⑅˘꒳˘) because the t-thwift compiwew f-fow python
  // d-doesn't awwow cycwic wefewences a-and we have some p-python utiwities t-that wiww faiw. ( ͡o ω ͡o )
  3: optionaw stwing wesponse;
}

stwuct eawwybiwddebuginfo {
  1: optionaw stwing h-host
  2: o-optionaw stwing pawsedquewy
  3: o-optionaw stwing w-wucenequewy
  // wequests sent t-to dependent sewvices. 🥺 f-fow exampwe, s-supewwoot sends to weawtime woot, ^^;;
  // awchive woot, o.O etc.
  4: o-optionaw wist<eawwybiwdwequestwesponse> sentwequests;
  // s-segment wevew debug info (eg. rawr hitspewsegment, (⑅˘꒳˘) max/minseawchedtime e-etc.)
  5: optionaw wist<stwing> c-cowwectowdebuginfo
  6: optionaw wist<stwing> tewmstatisticsdebuginfo
}

stwuct eawwybiwddebugoptions {
  1: optionaw boow incwudecowwectowdebuginfo
}

stwuct t-tiewwesponse {
  1: optionaw eawwybiwdwesponsecode tiewwesponsecode
  2: o-optionaw i-i32 nyumpawtitions
  3: o-optionaw i32 nyumsuccessfuwpawtitions
}

s-stwuct eawwybiwdsewvewstats {
  // the hostname o-of the eawwybiwd t-that pwocessed t-this wequest. 😳
  1: o-optionaw stwing h-hostname

  // the pawtition t-to which this e-eawwybiwd bewongs. nyaa~~
  2: o-optionaw i-i32 pawtition

  // cuwwent eawwybiwd qps. ^•ﻌ•^
  // eawwybiwds shouwd s-set this fiewd a-at the end of a-a wequest (not at the stawt). (⑅˘꒳˘) this w-wouwd give
  // woots a mowe u-up-to-date view of the woad on the eawwybiwds. σωσ
  3: optionaw i64 c-cuwwentqps

  // t-the time the w-wequest waited in t-the queue befowe e-eawwybiwd stawted p-pwocessing i-it. (U ᵕ U❁)
  // this does nyot incwude t-the time spent in the finagwe queue: it's the time between the moment
  // e-eawwybiwd w-weceived the wequest, o.O and the moment it stawted p-pwocessing the wequest. >w<
  4: optionaw i64 queuetimemiwwis

  // the avewage wequest time in t-the queue befowe e-eawwybiwd stawted p-pwocessing it. (///ˬ///✿)
  // t-this does n-not incwude the time that wequests spent in the f-finagwe queue: i-it's the avewage time
  // between t-the moment eawwybiwd w-weceived i-its wequests, :3 and the moment it s-stawted pwocessing t-them. ^^;;
  5: optionaw i64 avewagequeuetimemiwwis

  // cuwwent avewage pew-wequest watency as p-pewceived by eawwybiwd. òωó
  6: optionaw i64 avewagewatencymicwos

  // the tiew to w-which this eawwybiwd b-bewongs.
  7: optionaw stwing t-tiewname
}

stwuct eawwybiwdwesponse {
  // n-nyext avaiwabwe f-fiewd id: 17
  1: o-optionaw thwiftseawchwesuwts s-seawchwesuwts
  5: o-optionaw thwiftfacetwesuwts facetwesuwts
  6: o-optionaw thwifttewmstatisticswesuwts tewmstatisticswesuwts
  2: w-wequiwed eawwybiwdwesponsecode w-wesponsecode
  3: w-wequiwed i64 wesponsetime
  7: optionaw i64 wesponsetimemicwos
  // fiewds bewow w-wiww onwy be wetuwned if debug > 1 i-in the wequest. nyaa~~
  4: optionaw stwing debugstwing
  8: optionaw eawwybiwddebuginfo debuginfo

  // onwy exists fow mewged eawwybiwd wesponse. /(^•ω•^)
  10: o-optionaw i-i32 nyumpawtitions
  11: optionaw i32 nyumsuccessfuwpawtitions
  // o-onwy exists fow mewged eawwybiwd wesponse fwom muwtipwe tiews. 😳
  13: optionaw w-wist<tiewwesponse> p-pewtiewwesponse

  // t-totaw nyumbew of segments t-that wewe s-seawched. òωó pawtiawwy seawched segments awe fuwwy counted. (⑅˘꒳˘)
  // e.g. i-if we seawched 1 segment fuwwy, ^•ﻌ•^ and eawwy tewminated hawf way t-thwough the second
  // s-segment, o.O this fiewd shouwd be set to 2.
  15: optionaw i32 nyumseawchedsegments

  // w-whethew the wequest e-eawwy tewminated, σωσ if so, the t-tewmination weason. 😳
  12: o-optionaw seawch.eawwytewminationinfo eawwytewminationinfo

  // whethew t-this wesponse is fwom cache. (ˆ ﻌ ˆ)♡
  14: optionaw boow c-cachehit

  // stats used by w-woots to detewmine i-if we shouwd g-go into degwaded m-mode. (///ˬ///✿)
  16: optionaw e-eawwybiwdsewvewstats e-eawwybiwdsewvewstats
}

e-enum eawwybiwdstatuscode {
  stawting = 0, (///ˬ///✿)
  cuwwent = 1, >_<
  s-stopping = 2, XD
  u-unheawthy = 3, (U ﹏ U)
  bwackwisted = 4, ( ͡o ω ͡o )

  pwace_howdew5 = 5, ^•ﻌ•^
  p-pwace_howdew6 = 6, 😳
}

s-stwuct eawwybiwdstatuswesponse {
  1: w-wequiwed eawwybiwdstatuscode c-code
  2: wequiwed i64 awivesince
  3: o-optionaw s-stwing message
}

sewvice eawwybiwdsewvice {
  stwing getname(), (ˆ ﻌ ˆ)♡
  e-eawwybiwdstatuswesponse g-getstatus(), (ˆ ﻌ ˆ)♡
  eawwybiwdwesponse seawch( 1: e-eawwybiwdwequest wequest )
}
