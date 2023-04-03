packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

trait HasCandidatelons[Candidatelon <: UnivelonrsalNoun[Any], T] {
  delonf candidatelons: Selonq[Candidatelon]
  delonf updatelonCandidatelons(nelonwCandidatelons: Selonq[Candidatelon]): T
}
