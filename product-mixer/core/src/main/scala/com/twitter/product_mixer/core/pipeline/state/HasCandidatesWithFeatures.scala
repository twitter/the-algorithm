packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

trait HasCandidatelonsWithFelonaturelons[Candidatelon <: UnivelonrsalNoun[Any], T] {
  delonf candidatelonsWithFelonaturelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  delonf updatelonCandidatelonsWithFelonaturelons(nelonwCandidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]): T
}
