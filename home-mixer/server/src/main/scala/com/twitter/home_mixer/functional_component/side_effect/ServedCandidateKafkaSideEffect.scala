packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelonadFromCachelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PrelondictionRelonquelonstIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SelonrvelondIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SelonrvelondRelonquelonstIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.StrelonamToKafkaFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct SelonrvelondCandidatelonKafkaSidelonelonffelonct {

  delonf elonxtractCandidatelons(
    quelonry: PipelonlinelonQuelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    sourcelonIdelonntifielonrs: Selont[CandidatelonPipelonlinelonIdelonntifielonr]
  ): Selonq[ItelonmCandidatelonWithDelontails] = {
    val selonrvelondRelonquelonstIdOpt =
      quelonry.felonaturelons.gelontOrelonlselon(FelonaturelonMap.elonmpty).gelontOrelonlselon(SelonrvelondRelonquelonstIdFelonaturelon, Nonelon)

    selonlelonctelondCandidatelons.itelonrator
      .filtelonr(candidatelon => sourcelonIdelonntifielonrs.contains(candidatelon.sourcelon))
      .flatMap {
        caselon itelonm: ItelonmCandidatelonWithDelontails => Selonq(itelonm)
        caselon modulelon: ModulelonCandidatelonWithDelontails => modulelon.candidatelons
      }
      .filtelonr(candidatelon => candidatelon.felonaturelons.gelontOrelonlselon(StrelonamToKafkaFelonaturelon, falselon))
      .map { candidatelon =>
        val selonrvelondId =
          if (candidatelon.felonaturelons.gelontOrelonlselon(IsRelonadFromCachelonFelonaturelon, falselon) &&
            selonrvelondRelonquelonstIdOpt.nonelonmpty)
            selonrvelondRelonquelonstIdOpt
          elonlselon
            candidatelon.felonaturelons.gelontOrelonlselon(PrelondictionRelonquelonstIdFelonaturelon, Nonelon)

        candidatelon.copy(felonaturelons = candidatelon.felonaturelons + (SelonrvelondIdFelonaturelon, selonrvelondId))
      }.toSelonq
      // delonduplicatelon by (twelonelontId, uselonrId, selonrvelondId)
      .groupBy { candidatelon =>
        (
          candidatelon.candidatelonIdLong,
          quelonry.gelontRelonquirelondUselonrId,
          candidatelon.felonaturelons.gelontOrelonlselon(SelonrvelondIdFelonaturelon, Nonelon))
      }.valuelons.map(_.helonad).toSelonq
  }
}
