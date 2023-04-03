packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.commelonrcelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.commelonrcelon.CommelonrcelonProductCandidatelonUrtItelonmBuildelonr.CommelonrcelonProductClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CommelonrcelonProductCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.commelonrcelon.CommelonrcelonProductItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct CommelonrcelonProductCandidatelonUrtItelonmBuildelonr {
  val CommelonrcelonProductClielonntelonvelonntInfoelonlelonmelonnt: String = "commelonrcelon-product"
}

caselon class CommelonrcelonProductCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, CommelonrcelonProductCandidatelon],
  felonelondbackActionInfoBuildelonr: Option[BaselonFelonelondbackActionInfoBuildelonr[Quelonry, CommelonrcelonProductCandidatelon]])
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[
      Quelonry,
      CommelonrcelonProductCandidatelon,
      CommelonrcelonProductItelonm
    ] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: CommelonrcelonProductCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): CommelonrcelonProductItelonm =
    CommelonrcelonProductItelonm(
      id = candidatelon.id,
      sortIndelonx = Nonelon,
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr(
        quelonry,
        candidatelon,
        candidatelonFelonaturelons,
        Somelon(CommelonrcelonProductClielonntelonvelonntInfoelonlelonmelonnt)),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, candidatelon, candidatelonFelonaturelons))
    )
}
