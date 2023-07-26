package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.swice.buiwdew

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.swice.swiceitem
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

twait s-shouwdincwude[-quewy <: p-pipewinequewy] {
  d-def appwy(quewy: q-quewy, (U ᵕ U❁) items: seq[swiceitem]): b-boowean
}

object a-awwaysincwude e-extends shouwdincwude[pipewinequewy] {
  ovewwide def appwy(quewy: pipewinequewy, -.- entwies: seq[swiceitem]): b-boowean = twue
}

object incwudeonnonempty e-extends shouwdincwude[pipewinequewy] {
  ovewwide def appwy(quewy: p-pipewinequewy, ^^;; entwies: seq[swiceitem]): boowean = entwies.nonempty
}
