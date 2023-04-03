packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap

/** [[Candidatelon]] and it's FelonaturelonMap */
trait CandidatelonWithFelonaturelons[+Candidatelon <: UnivelonrsalNoun[Any]] {
  val candidatelon: Candidatelon
  val felonaturelons: FelonaturelonMap
}

objelonct CandidatelonWithFelonaturelons {
  delonf unapply[Candidatelon <: UnivelonrsalNoun[Any]](
    candidatelonWithFelonaturelons: CandidatelonWithFelonaturelons[Candidatelon]
  ): Option[(Candidatelon, FelonaturelonMap)] =
    Somelon(
      (candidatelonWithFelonaturelons.candidatelon, candidatelonWithFelonaturelons.felonaturelons)
    )
}
