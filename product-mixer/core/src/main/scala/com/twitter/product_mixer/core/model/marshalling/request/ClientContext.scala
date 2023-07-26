package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wequest

impowt com.fastewxmw.jackson.annotation.jsonignowe
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewine_faiwuwe.badwequest
impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewine_faiwuwe.pipewinefaiwuwe

/**
 * c-cwientcontext c-contains fiewds w-wewated to t-the cwient making t-the wequest. 😳
 */
c-case cwass cwientcontext(
  usewid: option[wong], 😳😳😳
  guestid: option[wong], mya
  guestidads: option[wong], mya
  g-guestidmawketing: option[wong], (⑅˘꒳˘)
  appid: o-option[wong], (U ﹏ U)
  ipaddwess: o-option[stwing], mya
  usewagent: option[stwing], ʘwʘ
  countwycode: option[stwing], (˘ω˘)
  wanguagecode: o-option[stwing], (U ﹏ U)
  istwoffice: o-option[boowean], ^•ﻌ•^
  u-usewwowes: option[set[stwing]], (˘ω˘)
  deviceid: option[stwing], :3
  mobiwedeviceid: option[stwing], ^^;;
  m-mobiwedeviceadid: option[stwing], 🥺
  wimitadtwacking: option[boowean])

object cwientcontext {
  vaw e-empty: cwientcontext = cwientcontext(
    u-usewid = n-nyone, (⑅˘꒳˘)
    guestid = n-nyone, nyaa~~
    g-guestidads = none, :3
    guestidmawketing = nyone, ( ͡o ω ͡o )
    a-appid = nyone, mya
    ipaddwess = nyone, (///ˬ///✿)
    u-usewagent = nyone, (˘ω˘)
    countwycode = nyone, ^^;;
    wanguagecode = nyone, (✿oωo)
    istwoffice = nyone, (U ﹏ U)
    u-usewwowes = nyone, -.-
    deviceid = n-nyone, ^•ﻌ•^
    m-mobiwedeviceid = n-nyone, rawr
    mobiwedeviceadid = nyone, (˘ω˘)
    wimitadtwacking = nyone
  )
}

/**
 * hascwientcontext i-indicates that a-a wequest has [[cwientcontext]] and adds hewpew f-functions fow
 * a-accessing [[cwientcontext]] fiewds. nyaa~~
 */
twait h-hascwientcontext {
  def cwientcontext: c-cwientcontext

  /**
   * getwequiwedusewid wetuwns a usewid a-and thwow if it's missing. UwU
   *
   * @note w-wogged out wequests awe disabwed b-by defauwt so t-this is safe fow most pwoducts
   */
  @jsonignowe /** jackson twies to sewiawize this method, :3 thwowing an exception fow guest pwoducts */
  d-def g-getwequiwedusewid: wong = cwientcontext.usewid.getowewse(
    thwow p-pipewinefaiwuwe(badwequest, (⑅˘꒳˘) "missing w-wequiwed f-fiewd: usewid"))

  /**
   * getoptionawusewid wetuwns a usewid if one is set
   */
  d-def getoptionawusewid: option[wong] = cwientcontext.usewid

  /**
   * getusewidwoggedoutsuppowt wetuwns a usewid and fawws b-back to 0 if nyone is set
   */
  d-def getusewidwoggedoutsuppowt: w-wong = cwientcontext.usewid.getowewse(0w)

  /**
   * g-getusewowguestid wetuwns a-a usewid ow a-a guestid if no u-usewid has been s-set
   */
  def getusewowguestid: option[wong] = c-cwientcontext.usewid.owewse(cwientcontext.guestid)

  /**
   * g-getcountwycode w-wetuwns a countwy c-code if one is s-set
   */
  def getcountwycode: option[stwing] = cwientcontext.countwycode

  /**
   * g-getwanguagecode wetuwns a wanguage code if one is set
   */
  def getwanguagecode: option[stwing] = c-cwientcontext.wanguagecode

  /**
   * iswoggedout wetuwns twue if the usew is wogged o-out (no usewid p-pwesent). (///ˬ///✿)
   *
   * @note t-this can be usefuw in c-conjunction with [[getusewidwoggedoutsuppowt]]
   */
  def iswoggedout: b-boowean = c-cwientcontext.usewid.isempty
}
