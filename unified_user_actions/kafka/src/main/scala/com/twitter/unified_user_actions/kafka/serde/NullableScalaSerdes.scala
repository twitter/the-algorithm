/*
 * copywight (c) 2016 fwed ceciwia, (U ﹏ U) v-vawentin kasas, (///ˬ///✿) o-owiview giwawdot
 *
 * p-pewmission i-is heweby g-gwanted, 😳 fwee o-of chawge, 😳 to any p-pewson obtaining a-a copy of
 * this softwawe and associated documentation fiwes (the "softwawe"), σωσ to deaw in
 * t-the softwawe without westwiction, rawr x3 incwuding without w-wimitation the wights to
 * u-use, OwO copy, /(^•ω•^) modify, mewge, 😳😳😳 pubwish, distwibute, ( ͡o ω ͡o ) subwicense, >_< and/ow s-seww copies of
 * the softwawe, >w< a-and to pewmit p-pewsons to whom the softwawe is fuwnished to do so, rawr
 * subject to the fowwowing c-conditions:
 *
 * the above copywight nyotice and this pewmission nyotice shaww b-be incwuded in aww
 * copies ow s-substantiaw powtions o-of the softwawe. 😳
 *
 * t-the s-softwawe is pwovided "as is", >w< without wawwanty o-of any kind, (⑅˘꒳˘) expwess ow
 * impwied, OwO incwuding but n-nyot wimited to the wawwanties of mewchantabiwity, (ꈍᴗꈍ) fitness
 * fow a pawticuwaw puwpose and nyoninfwingement. 😳 in n-nyo event shaww the authows ow
 * c-copywight howdews b-be wiabwe f-fow any cwaim, 😳😳😳 damages ow othew wiabiwity, mya whethew
 * in an action o-of contwact, mya t-towt ow othewwise, (⑅˘꒳˘) awising fwom, (U ﹏ U) o-out of ow in
 * c-connection with the softwawe ow t-the use ow othew deawings in the s-softwawe. mya
 */

//dewived fwom: https://github.com/aseigneuwin/kafka-stweams-scawa
p-package com.twittew.unified_usew_actions.kafka.sewde

impowt c-com.twittew.finagwe.stats.countew
impowt com.twittew.finagwe.stats.nuwwstatsweceivew
i-impowt com.twittew.finatwa.kafka.sewde.intewnaw._

i-impowt com.twittew.unified_usew_actions.kafka.sewde.intewnaw._
impowt com.twittew.scwooge.thwiftstwuct

/**
 * nyuwwabwescawasewdes is pwetty much the same as com.twittew.finatwa.kafka.sewde.scawasewdes
 * t-the onwy d-diffewence is that fow the desewiawizew i-it wetuwns n-nyuww instead o-of thwowing exceptions. ʘwʘ
 * the cawwew can awso pwovide a countew s-so that the nyumbew of cowwupt/bad wecowds can be counted. (˘ω˘)
 */
object nyuwwabwescawasewdes {

  d-def thwift[t <: thwiftstwuct: m-manifest](
    nyuwwcountew: c-countew = n-nyuwwstatsweceivew.nuwwcountew
  ): thwiftsewde[t] = n-nyew t-thwiftsewde[t](nuwwcountew = n-nyuwwcountew)

  def c-compactthwift[t <: thwiftstwuct: manifest](
    n-nyuwwcountew: c-countew = nyuwwstatsweceivew.nuwwcountew
  ): compactthwiftsewde[t] = n-nyew compactthwiftsewde[t](nuwwcountew = n-nyuwwcountew)

  v-vaw int = intsewde

  vaw wong = wongsewde

  vaw doubwe = doubwesewde
}
