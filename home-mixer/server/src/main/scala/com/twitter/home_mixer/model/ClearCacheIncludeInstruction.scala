package com.twittew.home_mixew.modew

impowt com.twittew.home_mixew.modew.wequest.devicecontext.wequestcontext
i-impowt c-com.twittew.home_mixew.modew.wequest.hasdevicecontext
i-impowt c-com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwt.buiwdew.incwudeinstwuction
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewinemoduwe
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.tweet.tweetitem
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt c-com.twittew.timewines.configapi.fsboundedpawam
impowt com.twittew.timewines.configapi.fspawam

/**
 * incwude a c-cweaw cache timewine instwuction w-when we satisfy these cwitewia:
 * - wequest pwovenance is "puww t-to wefwesh"
 * - atweast ny nyon-ad t-tweet entwies i-in the wesponse
 *
 * this is to ensuwe that we have sufficient nyew content t-to justify jumping usews to the
 * top of the nyew timewines wesponse and don't a-add unnecessawy woad to backend s-systems
 */
case c-cwass cweawcacheincwudeinstwuction(
  e-enabwepawam: f-fspawam[boowean], ʘwʘ
  minentwiespawam: fsboundedpawam[int])
    e-extends incwudeinstwuction[pipewinequewy with hasdevicecontext] {

  o-ovewwide def appwy(
    quewy: pipewinequewy with hasdevicecontext, σωσ
    entwies: seq[timewineentwy]
  ): boowean = {
    v-vaw enabwed = quewy.pawams(enabwepawam)

    vaw p-ptw =
      quewy.devicecontext.fwatmap(_.wequestcontextvawue).contains(wequestcontext.puwwtowefwesh)

    v-vaw m-mintweets = quewy.pawams(minentwiespawam) <= entwies.cowwect {
      case item: tweetitem if item.pwomotedmetadata.isempty => 1
      c-case moduwe: t-timewinemoduwe if moduwe.items.head.item.isinstanceof[tweetitem] =>
        m-moduwe.items.size
    }.sum

    e-enabwed && ptw && mintweets
  }
}
