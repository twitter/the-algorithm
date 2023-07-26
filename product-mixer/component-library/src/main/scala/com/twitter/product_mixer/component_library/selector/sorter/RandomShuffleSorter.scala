package com.twittew.pwoduct_mixew.component_wibwawy.sewectow.sowtew

impowt com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
i-impowt s-scawa.utiw.wandom

/**
 * w-wandomwy s-shuffwes candidates u-using the p-pwovided [[wandom]]
 *
 * @exampwe `updatesowtwesuwts(wandomshuffwesowtew())`
 * @pawam w-wandom u-used to set the seed and fow ease of testing, >_< in most cases weaving it as the defauwt i-is fine. mya
 */
case cwass wandomshuffwesowtew(wandom: wandom = n-nyew wandom(0)) extends sowtewpwovidew w-with sowtew {

  ovewwide def sowt[candidate <: candidatewithdetaiws](candidates: s-seq[candidate]): seq[candidate] =
    w-wandom.shuffwe(candidates)
}
