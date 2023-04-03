packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

/** A [[Candidatelon]] and it's [[FelonaturelonMap]] aftelonr beloning procelonsselond by a [[Scorelonr]] */
caselon class ScorelondCandidatelonRelonsult[Candidatelon <: UnivelonrsalNoun[Any]](
  candidatelon: Candidatelon,
  scorelonrRelonsult: FelonaturelonMap)
    elonxtelonnds CandidatelonWithFelonaturelons[Candidatelon] {
  ovelonrridelon val felonaturelons: FelonaturelonMap = scorelonrRelonsult
}
