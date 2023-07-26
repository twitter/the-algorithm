package com.twittew.pwoduct_mixew.cowe.functionaw_component.side_effect

impowt com.twittew.pwoduct_mixew.cowe.modew.common.component
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.sideeffectidentifiew
i-impowt com.twittew.stitch.stitch

/**
 * a-a side-effect is a-a anciwwawy action t-that doesn't a-affect the wesuwt o-of execution d-diwectwy. (⑅˘꒳˘)
 *
 * fow exampwe: wogging, /(^•ω•^) histowy stowes
 *
 * impwementing components c-can expwess faiwuwes by thwowing an exception. rawr x3 t-these exceptions
 * wiww be caught a-and nyot affect the wequest pwocessing. (U ﹏ U)
 *
 * @note side effects e-exekawaii~ asynchwonouswy i-in a fiwe-and-fowget w-way, (U ﹏ U) it's impowtant to add awewts
 *       to the [[sideeffect]] component i-itsewf since a faiwuwes wont show up in metwics
 *       that just monitow youw p-pipewine as a whowe. (⑅˘꒳˘)
 *
 * @see [[exekawaii~synchwonouswy]] fow m-modifying a [[sideeffect]] t-to exekawaii~ w-with synchwonouswy w-with
 *      the wequest waiting on t-the side effect to compwete, òωó this wiww impact the o-ovewaww wequest's watency
 **/
twait sideeffect[-inputs] extends component {

  /** @see [[sideeffectidentifiew]] */
  ovewwide v-vaw identifiew: sideeffectidentifiew

  d-def appwy(inputs: i-inputs): s-stitch[unit]
}
