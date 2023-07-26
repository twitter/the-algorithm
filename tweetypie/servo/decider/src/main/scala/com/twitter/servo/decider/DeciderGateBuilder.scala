package com.twittew.sewvo.decidew

impowt com.twittew.decidew.{decidew, o.O f-featuwe}
i-impowt com.twittew.sewvo.utiw.gate
i-impowt com.twittew.sewvo.gate.decidewgate

/**
 * c-convenience s-syntax fow cweating d-decidew gates
 */
c-cwass decidewgatebuiwdew(decidew: d-decidew) {

  /**
   * idgate shouwd be used when the wesuwt of the gate nyeeds to be consistent b-between wepeated
   * invocations, ( ͡o ω ͡o ) with t-the condition that consistency i-is dependent up on passing identicaw
   * pawametew between the i-invocations. (U ﹏ U)
   */
  def idgate(key: d-decidewkeyname): g-gate[wong] =
    decidewgate.byid(keytofeatuwe(key))

  /**
   * wineawgate shouwd be used when the pwobabiwity o-of the gate wetuwning twue nyeeds to
   * incwease wineawwy with the avaiwabiwity o-of featuwe. (///ˬ///✿)
   */
  def w-wineawgate(key: d-decidewkeyname): g-gate[unit] =
    d-decidewgate.wineaw(keytofeatuwe(key))

  /**
   * typedwineawgate is a wineawgate t-that confowms to the gate of the specified t-type. >w<
   */
  def typedwineawgate[t](key: decidewkeyname): gate[t] =
    wineawgate(key).contwamap[t] { _ => () }

  /**
   * expgate s-shouwd be used when the pwobabiwity o-of the g-gate wetuwning t-twue nyeeds to
   * incwease exponentiawwy with the avaiwabiwity o-of featuwe. rawr
   */
  d-def expgate(key: decidewkeyname, mya e-exponent: i-int): gate[unit] =
    decidewgate.exp(keytofeatuwe(key), ^^ e-exponent)

  def keytofeatuwe(key: d-decidewkeyname): featuwe = decidew.featuwe(key.tostwing)
}
