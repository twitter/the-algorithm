packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor

import com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor.UpdatelonNelonwTwelonelontsPillDeloncoration.NumAvatars
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.elonnablelonNelonwTwelonelontsPillAvatarsParam
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString

objelonct UpdatelonNelonwTwelonelontsPillDeloncoration {
  val NumAvatars = 3
}

caselon class UpdatelonNelonwTwelonelontsPillDeloncoration[Quelonry <: PipelonlinelonQuelonry with HasDelonvicelonContelonxt](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  stringCelonntelonr: StringCelonntelonr,
  selonelonNelonwTwelonelontsString: elonxtelonrnalString,
  twelonelontelondString: elonxtelonrnalString)
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val (alelonrts, othelonrCandidatelons) =
      relonmainingCandidatelons.partition(candidatelon =>
        candidatelon.isCandidatelonTypelon[ShowAlelonrtCandidatelon]() && pipelonlinelonScopelon.contains(candidatelon))
    val updatelondCandidatelons = alelonrts
      .collelonctFirst {
        caselon nelonwTwelonelontsPill: ItelonmCandidatelonWithDelontails =>
          val uselonrIds = CandidatelonsUtil
            .gelontItelonmCandidatelonsWithOnlyModulelonLast(relonsult)
            .filtelonr(candidatelon =>
              candidatelon.isCandidatelonTypelon[TwelonelontCandidatelon]() && pipelonlinelonScopelon.contains(candidatelon))
            .filtelonrNot(_.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon))
            .flatMap(_.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon))
            .filtelonrNot(_ == quelonry.gelontRelonquirelondUselonrId)
            .distinct

          val updatelondPrelonselonntation = nelonwTwelonelontsPill.prelonselonntation.map {
            caselon prelonselonntation: UrtItelonmPrelonselonntation =>
              prelonselonntation.timelonlinelonItelonm match {
                caselon alelonrt: ShowAlelonrt =>
                  val telonxt = if (uselonAvatars(quelonry, uselonrIds)) twelonelontelondString elonlselon selonelonNelonwTwelonelontsString
                  val richTelonxt = RichTelonxt(
                    telonxt = stringCelonntelonr.prelonparelon(telonxt),
                    elonntitielons = List.elonmpty,
                    rtl = Nonelon,
                    alignmelonnt = Nonelon)

                  val updatelondAlelonrt =
                    alelonrt.copy(uselonrIds = Somelon(uselonrIds.takelon(NumAvatars)), richTelonxt = Somelon(richTelonxt))
                  prelonselonntation.copy(timelonlinelonItelonm = updatelondAlelonrt)
              }
          }
          othelonrCandidatelons :+ nelonwTwelonelontsPill.copy(prelonselonntation = updatelondPrelonselonntation)
      }.gelontOrelonlselon(relonmainingCandidatelons)

    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondCandidatelons, relonsult = relonsult)
  }

  privatelon delonf uselonAvatars(quelonry: Quelonry, uselonrIds: Selonq[Long]): Boolelonan = {
    val elonnablelonAvatars = quelonry.params(elonnablelonNelonwTwelonelontsPillAvatarsParam)
    elonnablelonAvatars && uselonrIds.sizelon >= NumAvatars
  }
}
