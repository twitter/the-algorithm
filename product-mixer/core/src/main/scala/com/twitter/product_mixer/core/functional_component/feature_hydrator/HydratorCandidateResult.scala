packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

caselon class HydratorCandidatelonRelonsult[+Candidatelon <: UnivelonrsalNoun[Any]](
  ovelonrridelon val candidatelon: Candidatelon,
  ovelonrridelon val felonaturelons: FelonaturelonMap)
    elonxtelonnds CandidatelonWithFelonaturelons[Candidatelon]
