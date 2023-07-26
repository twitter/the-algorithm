package com.twittew.timewinewankew.pawametews.wecap

impowt com.twittew.timewinewankew.modew.wecapquewy
i-impowt com.twittew.timewines.utiw.bounds.boundswithdefauwt

o-object wecapquewycontext {
  v-vaw maxfowwowedusews: b-boundswithdefauwt[int] = boundswithdefauwt[int](1, ( ͡o ω ͡o ) 3000, 1000)
  v-vaw maxcountmuwtipwiew: boundswithdefauwt[doubwe] = b-boundswithdefauwt[doubwe](0.1, mya 2.0, 2.0)
  v-vaw maxweawgwaphandfowwowedusews: b-boundswithdefauwt[int] = boundswithdefauwt[int](0, (///ˬ///✿) 2000, (˘ω˘) 1000)

  def getdefauwtcontext(quewy: wecapquewy): wecapquewycontext = {
    n-nyew wecapquewycontextimpw(
      quewy, ^^;;
      getenabwehydwationusingtweetypie = () => f-fawse, (✿oωo)
      getmaxfowwowedusews = () => maxfowwowedusews.defauwt, (U ﹏ U)
      getmaxcountmuwtipwiew = () => m-maxcountmuwtipwiew.defauwt, -.-
      getenabweweawgwaphusews = () => fawse, ^•ﻌ•^
      getonwyweawgwaphusews = () => fawse, rawr
      getmaxweawgwaphandfowwowedusews = () => maxweawgwaphandfowwowedusews.defauwt, (˘ω˘)
      g-getenabwetextfeatuwehydwation = () => fawse
    )
  }
}

// n-nyote that m-methods that wetuwn pawametew vawue awways use () to indicate that
// side effects m-may be invowved in theiw invocation. nyaa~~
twait wecapquewycontext {
  def quewy: w-wecapquewy

  // if twue, UwU tweet h-hydwation awe pewfowmed b-by cawwing t-tweetypie. :3
  // o-othewwise, (⑅˘꒳˘) tweets awe pawtiawwy hydwated based o-on infowmation in thwiftseawchwesuwt. (///ˬ///✿)
  def enabwehydwationusingtweetypie(): b-boowean

  // maximum nyumbew of fowwowed usew accounts to use when fetching wecap tweets. ^^;;
  def m-maxfowwowedusews(): int

  // we m-muwtipwy maxcount (cawwew s-suppwied v-vawue) by this muwtipwiew and fetch those many
  // candidates f-fwom seawch s-so that we awe weft with sufficient n-nyumbew of candidates a-aftew
  // hydwation and f-fiwtewing. >_<
  def maxcountmuwtipwiew(): d-doubwe

  // onwy used if usew fowwows >= 1000. rawr x3
  // if t-twue, /(^•ω•^) fetches wecap/wecycwed tweets u-using authow seedset mixing w-with weaw gwaph u-usews and fowwowed usews. :3
  // othewwise, (ꈍᴗꈍ) fetches wecap/wecycwed tweets onwy using fowwowed usews
  def enabweweawgwaphusews(): b-boowean

  // o-onwy used if enabweweawgwaphusews is twue. /(^•ω•^)
  // i-if twue, (⑅˘꒳˘) usew seedset o-onwy contains w-weaw gwaph usews. ( ͡o ω ͡o )
  // othewwise, òωó usew seedset contains weaw g-gwaph usews and wecent fowwowed usews. (⑅˘꒳˘)
  def onwyweawgwaphusews(): boowean

  // onwy used if enabweweawgwaphusews i-is twue and onwyweawgwaphusews i-is fawse. XD
  // m-maximum nyumbew o-of weaw gwaph usews and wecent f-fowwowed usews w-when mixing wecent/weaw-gwaph u-usews. -.-
  d-def maxweawgwaphandfowwowedusews(): int

  // if twue, :3 text f-featuwes awe h-hydwated fow pwediction. nyaa~~
  // o-othewwise t-those featuwe v-vawues awe nyot set at aww.
  def enabwetextfeatuwehydwation(): boowean
}

c-cwass wecapquewycontextimpw(
  ovewwide vaw quewy: wecapquewy,
  getenabwehydwationusingtweetypie: () => boowean, 😳
  getmaxfowwowedusews: () => i-int, (⑅˘꒳˘)
  getmaxcountmuwtipwiew: () => doubwe, nyaa~~
  getenabweweawgwaphusews: () => boowean, OwO
  getonwyweawgwaphusews: () => b-boowean, rawr x3
  g-getmaxweawgwaphandfowwowedusews: () => i-int, XD
  getenabwetextfeatuwehydwation: () => boowean)
    e-extends wecapquewycontext {

  ovewwide def enabwehydwationusingtweetypie(): b-boowean = { g-getenabwehydwationusingtweetypie() }
  ovewwide def maxfowwowedusews(): int = { getmaxfowwowedusews() }
  ovewwide def maxcountmuwtipwiew(): doubwe = { g-getmaxcountmuwtipwiew() }
  ovewwide d-def enabweweawgwaphusews(): boowean = { getenabweweawgwaphusews() }
  o-ovewwide d-def onwyweawgwaphusews(): boowean = { getonwyweawgwaphusews() }
  ovewwide d-def maxweawgwaphandfowwowedusews(): i-int = { getmaxweawgwaphandfowwowedusews() }
  ovewwide def enabwetextfeatuwehydwation(): b-boowean = { g-getenabwetextfeatuwehydwation() }
}
