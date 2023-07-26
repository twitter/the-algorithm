package com.twittew.cw_mixew.sewvice

impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt.destination
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt.notificationgwoup

/**
 * n-nyotifications (emaiw, nyaa~~ p-pagewduty, (⑅˘꒳˘) etc) c-can be specific p-pew-awewt but i-it is common fow m-muwtipwe
 * pwoducts to shawe nyotification configuwation. rawr x3
 *
 * ouw configuwation uses onwy emaiw n-nyotifications because sampwemixew is a demonstwation s-sewvice
 * with nyeithew i-intewnaw nyow customew-facing usews. (✿oωo) you wiww wikewy want to u-use a pagewduty
 * destination instead. f-fow exampwe:
 * {{{
 *   c-cwiticaw = destination(pagewdutykey = some("youw-pagewduty-key"))
 * }}}
 *
 *
 * fow mowe infowmation about how to get a pagewduty k-key, (ˆ ﻌ ˆ)♡ see:
 * https://docbiwd.twittew.biz/mon/how-to-guides.htmw?highwight=notificationgwoup#set-up-emaiw-pagewduty-and-swack-notifications
 */
object cwmixewawewtnotificationconfig {
  vaw defauwtnotificationgwoup: n-nyotificationgwoup = nyotificationgwoup(
    w-wawn = d-destination(emaiws = s-seq("no-wepwy@twittew.com")), (˘ω˘)
    c-cwiticaw = destination(emaiws = seq("no-wepwy@twittew.com"))
  )
}
