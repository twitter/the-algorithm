package com.twittew.sewvo.utiw

impowt com.twittew.finagwe.thwift.cwientid

/**
 * a-a twait defining c-contextuaw infowmation n-nyecessawy t-to authowize
 * a-and obsewve a-a wequest. 😳
 */
t-twait obsewvabwe {
  v-vaw wequestname: stwing
  vaw cwientid: option[cwientid]

  /**
   * an option[stwing] wepwesentation o-of the wequest-issuew's cwientid. XD
   */
  w-wazy vaw cwientidstwing: option[stwing] =
    // i-it's possibwe fow `cwientid.name` to be `nuww`, :3 so we wwap i-it in
    // `option()` to fowce s-such cases to b-be nyone. 😳😳😳
    cwientid fwatmap { cid =>
      option(cid.name)
    }
}
