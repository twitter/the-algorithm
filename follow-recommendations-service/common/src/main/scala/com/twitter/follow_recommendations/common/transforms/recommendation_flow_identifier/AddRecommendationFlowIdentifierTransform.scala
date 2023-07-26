package com.twittew.fowwow_wecommendations.common.twansfowms.wecommendation_fwow_identifiew

impowt c-com.googwe.inject.inject
i-impowt c-com.twittew.fowwow_wecommendations.common.base.twansfowm
i-impowt c-com.twittew.fowwow_wecommendations.common.modews.candidateusew
i-impowt com.twittew.fowwow_wecommendations.common.modews.haswecommendationfwowidentifiew
i-impowt c-com.twittew.stitch.stitch

cwass addwecommendationfwowidentifiewtwansfowm @inject()
    extends twansfowm[haswecommendationfwowidentifiew, ^^;; c-candidateusew] {

  ovewwide def twansfowm(
    tawget: h-haswecommendationfwowidentifiew, >_<
    items: s-seq[candidateusew]
  ): stitch[seq[candidateusew]] = {
    stitch.vawue(items.map { candidateusew =>
      c-candidateusew.copy(wecommendationfwowidentifiew = tawget.wecommendationfwowidentifiew)
    })
  }
}
