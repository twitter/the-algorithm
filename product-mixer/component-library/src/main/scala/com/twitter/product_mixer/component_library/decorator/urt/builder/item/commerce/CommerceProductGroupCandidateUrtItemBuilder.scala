packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.commelonrcelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.commelonrcelon.CommelonrcelonProductGroupCandidatelonUrtItelonmBuildelonr.CommelonrcelonProductGroupClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CommelonrcelonProductGroupCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon.CommelonrcelonProductGroupItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct CommelonrcelonProductGroupCandidatelonUrtItelonmBuildelonr {
  val CommelonrcelonProductGroupClielonntelonvelonntInfoelonlelonmelonnt: String = "commelonrcelon-product-group"
}

caselon class CommelonrcelonProductGroupCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, CommelonrcelonProductGroupCandidatelon],
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, CommelonrcelonProductGroupCandidatelon]
  ]) elonxtelonnds CandidatelonUrtelonntryBuildelonr[
      Quelonry,
      CommelonrcelonProductGroupCandidatelon,
      CommelonrcelonProductGroupItelonm
    ] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: CommelonrcelonProductGroupCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): CommelonrcelonProductGroupItelonm =
    CommelonrcelonProductGroupItelonm(
      id = candidatelon.id,
      sortIndelonx = Nonelon,
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        candidatelon,
        candidatelonFelonaturelons,
        Somelon(CommelonrcelonProductGroupClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, candidatelon, candidatelonFelonaturelons))
    )
}
