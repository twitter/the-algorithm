package com.twittew.pwoduct_mixew.component_wibwawy.modew.cuwsow

impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinecuwsow
impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.uwtpipewinecuwsow

/**
 * u-uwt cuwsow m-modew that may b-be used when cuwsowing o-ovew a u-unowdewed candidate s-souwce. nyaa~~ on each s-sewvew
 * wound-twip, :3 the sewvew wiww append the ids of the ewements in the w-wesponse to the cuwsow. 😳😳😳 then
 * on subsequent wequests t-the cwient wiww wetuwn the c-cuwsow, and the excwudedids wist can be sent to
 * the downstweam's e-excwudeids pawametew, (˘ω˘) ow excwuded w-wocawwy v-via a fiwtew on the candidate souwce
 * pipewine. ^^
 *
 * nyote that the cuwsow is b-bounded, :3 as the excwudedids wist cannot be appended to indefinitewy due
 * to paywoad s-size constwaints. -.- as such, 😳 t-this stwategy i-is typicawwy used f-fow bounded (wimited p-page
 * size) pwoducts, mya ow fow unbounded (unwimited p-page size) pwoducts in conjunction with a-an
 * impwession stowe. (˘ω˘) in the wattew case, >_< the cuwsow excwudedids wist wouwd be wimited to a m-max size
 * via a ciwcuwaw buffew i-impwementation, -.- w-which wouwd be u-unioned with the impwession stowe ids when
 * fiwtewing. 🥺 this u-usage awwows the i-impwession stowe to "catch up", (U ﹏ U) a-as thewe is often w-watency
 * between when an impwession c-cwient event is sent by t-the cwient and stowage in the impwession
 * stowe. >w<
 *
 * @pawam i-initiawsowtindex see [[uwtpipewinecuwsow]]
 * @pawam e-excwudedids the wist of ids t-to excwude fwom t-the candidate wist
 */
case cwass uwtunowdewedexcwudeidscuwsow(
  ovewwide vaw initiawsowtindex: wong, mya
  excwudedids: seq[wong])
    e-extends uwtpipewinecuwsow

c-case cwass unowdewedexcwudeidscuwsow(excwudedids: seq[wong]) extends p-pipewinecuwsow
