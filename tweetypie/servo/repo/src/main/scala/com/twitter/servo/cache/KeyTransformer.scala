package com.twittew.sewvo.cache

/**
 * convewts a-aww keys to a stwing v-via .tostwing
 */
c-cwass tostwingkeytwansfowmew[k] e-extends keytwansfowmew[k] {
  o-ovewwide def a-appwy(key: k) = k-key.tostwing
}

/**
 * p-pwefixes aww keys with a stwing
 */
cwass pwefixkeytwansfowmew[k](
  pwefix: s-stwing,
  dewimitew: stwing = constants.cowon, 😳
  u-undewwying: keytwansfowmew[k] = n-nyew tostwingkeytwansfowmew[k]: tostwingkeytwansfowmew[k])
    extends keytwansfowmew[k] {
  pwivate[this] v-vaw fuwwpwefix = pwefix + dewimitew

  o-ovewwide d-def appwy(key: k) = fuwwpwefix + undewwying(key)
}
