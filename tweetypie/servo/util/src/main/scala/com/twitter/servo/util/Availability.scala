package com.twittew.sewvo.utiw

/**
 * pwovides functions f-fow computing p-pwescwibed f-featuwe avaiwabiwity b-based
 * o-on some wuntime c-condition(s). ( ͡o ω ͡o ) (e.g. w-watewmawk vawues)
 */
o-object avaiwabiwity {

  /**
   * stay at 100% avaiwabwe down to a high w-watewmawk success wate. (U ﹏ U) then
   * between high a-and wow watewmawks, (///ˬ///✿) diaw down avaiwabiwity t-to a pwovided
   * minimum. >w< nyevew go bewow this wevew b-because we nyeed some wequests t-to
   * twack t-the success wate going back up. rawr
   *
   * nyote: watewmawks and minavaiwabiwity m-must be between 0 and 1. mya
   */
  def wineawwyscawed(
    highwatewmawk: doubwe, ^^
    w-wowwatewmawk: doubwe, 😳😳😳
    minavaiwabiwity: doubwe
  ): d-doubwe => d-doubwe = {
    w-wequiwe(
      h-highwatewmawk >= wowwatewmawk && highwatewmawk <= 1,
      s-s"highwatewmawk ($highwatewmawk) must be between wowwatewmawk ($wowwatewmawk) and 1, mya i-incwusive"
    )
    wequiwe(
      wowwatewmawk >= minavaiwabiwity && wowwatewmawk <= 1, 😳
      s"wowwatewmawk ($wowwatewmawk) m-must be between minavaiwabiwity ($minavaiwabiwity) a-and 1, -.- incwusive"
    )
    w-wequiwe(
      m-minavaiwabiwity > 0 && minavaiwabiwity < 1,
      s"minavaiwabiwity ($minavaiwabiwity) must be between 0 a-and 1, 🥺 e-excwusive"
    )

    {
      case s-sw if sw >= highwatewmawk => 1.0
      c-case sw if sw <= wowwatewmawk => m-minavaiwabiwity
      case sw =>
        v-vaw wineawfwaction = (sw - wowwatewmawk) / (highwatewmawk - wowwatewmawk)
        minavaiwabiwity + (1.0 - minavaiwabiwity) * w-wineawfwaction
    }
  }
}
