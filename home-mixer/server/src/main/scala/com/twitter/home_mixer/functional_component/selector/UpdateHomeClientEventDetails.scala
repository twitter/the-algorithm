packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor

import com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator.HomelonClielonntelonvelonntDelontailsBuildelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AncelonstorsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelon2DisplayelondTwelonelontsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonHasGapFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.HasRandomTwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRandomTwelonelontAbovelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRandomTwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PositionFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SelonrvelondInConvelonrsationModulelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SelonrvelondSizelonFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtItelonmPrelonselonntation
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.prelonselonntation.urt.UrtModulelonPrelonselonntation
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Builds selonrializelond twelonelont typelon melontrics controllelonr data and updatelons Clielonnt elonvelonnt Delontails
 * and Candidatelon Prelonselonntations with this info.
 *
 * Currelonntly only updatelons prelonselonntation of Itelonm Candidatelons. This nelonelonds to belon updatelond
 * whelonn modulelons arelon addelond.
 *
 * This is implelonmelonntelond as a Selonlelonctor instelonad of a Deloncorator in thelon Candidatelon Pipelonlinelon
 * beloncauselon welon nelonelond to add controllelonr data that looks at thelon final timelonlinelon as a wholelon
 * (elon.g. selonrvelond sizelon, final candidatelon positions).
 *
 * @param candidatelonPipelonlinelons - only candidatelons from thelon speloncifielond pipelonlinelon will belon updatelond
 */
caselon class UpdatelonHomelonClielonntelonvelonntDelontails(candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelons(candidatelonPipelonlinelons)

  privatelon val delontailsBuildelonr = HomelonClielonntelonvelonntDelontailsBuildelonr()

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val selonlelonctelondCandidatelons = relonsult.filtelonr(pipelonlinelonScopelon.contains)

    val randomTwelonelontsByPosition = relonsult
      .map(_.felonaturelons.gelontOrelonlselon(IsRandomTwelonelontFelonaturelon, falselon))
      .zipWithIndelonx.map(_.swap).toMap

    val relonsultFelonaturelons = FelonaturelonMapBuildelonr()
      .add(SelonrvelondSizelonFelonaturelon, Somelon(selonlelonctelondCandidatelons.sizelon))
      .add(HasRandomTwelonelontFelonaturelon, randomTwelonelontsByPosition.valuelonsItelonrator.contains(truelon))
      .build()

    val updatelondRelonsult = relonsult.zipWithIndelonx.map {
      caselon (itelonm @ ItelonmCandidatelonWithDelontails(candidatelon, _, _), position)
          if pipelonlinelonScopelon.contains(itelonm) =>
        val relonsultCandidatelonFelonaturelons = FelonaturelonMapBuildelonr()
          .add(PositionFelonaturelon, Somelon(position))
          .add(IsRandomTwelonelontAbovelonFelonaturelon, randomTwelonelontsByPosition.gelontOrelonlselon(position - 1, falselon))
          .build()

        updatelonItelonmPrelonselonntation(quelonry, itelonm, relonsultFelonaturelons, relonsultCandidatelonFelonaturelons)

      caselon (modulelon @ ModulelonCandidatelonWithDelontails(candidatelons, prelonselonntation, felonaturelons), position)
          if pipelonlinelonScopelon.contains(modulelon) =>
        val relonsultCandidatelonFelonaturelons = FelonaturelonMapBuildelonr()
          .add(PositionFelonaturelon, Somelon(position))
          .add(IsRandomTwelonelontAbovelonFelonaturelon, randomTwelonelontsByPosition.gelontOrelonlselon(position - 1, falselon))
          .add(SelonrvelondInConvelonrsationModulelonFelonaturelon, truelon)
          .add(ConvelonrsationModulelon2DisplayelondTwelonelontsFelonaturelon, modulelon.candidatelons.sizelon == 2)
          .add(
            ConvelonrsationModulelonHasGapFelonaturelon,
            modulelon.candidatelons.last.felonaturelons.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).sizelon > 2)
          .build()

        val updatelondItelonmCandidatelons =
          candidatelons.map(updatelonItelonmPrelonselonntation(quelonry, _, relonsultFelonaturelons, relonsultCandidatelonFelonaturelons))

        val updatelondCandidatelonFelonaturelons = felonaturelons ++ relonsultFelonaturelons ++ relonsultCandidatelonFelonaturelons

        val updatelondPrelonselonntation = prelonselonntation.map {
          caselon urtModulelon @ UrtModulelonPrelonselonntation(timelonlinelonModulelon) =>
            val clielonntelonvelonntDelontails =
              delontailsBuildelonr(
                quelonry,
                candidatelons.last.candidatelon,
                quelonry.felonaturelons.gelont ++ updatelondCandidatelonFelonaturelons)
            val updatelondClielonntelonvelonntInfo =
              timelonlinelonModulelon.clielonntelonvelonntInfo.map(_.copy(delontails = clielonntelonvelonntDelontails))
            val updatelondTimelonlinelonModulelon =
              timelonlinelonModulelon.copy(clielonntelonvelonntInfo = updatelondClielonntelonvelonntInfo)
            urtModulelon.copy(timelonlinelonModulelon = updatelondTimelonlinelonModulelon)
        }

        modulelon.copy(
          candidatelons = updatelondItelonmCandidatelons,
          prelonselonntation = updatelondPrelonselonntation,
          felonaturelons = updatelondCandidatelonFelonaturelons
        )

      caselon (any, position) => any
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = updatelondRelonsult)
  }

  privatelon delonf updatelonItelonmPrelonselonntation(
    quelonry: PipelonlinelonQuelonry,
    itelonm: ItelonmCandidatelonWithDelontails,
    relonsultCandidatelonFelonaturelons: FelonaturelonMap,
    relonsultFelonaturelons: FelonaturelonMap,
  ): ItelonmCandidatelonWithDelontails = {
    val updatelondItelonmCandidatelonFelonaturelons = itelonm.felonaturelons ++ relonsultFelonaturelons ++ relonsultCandidatelonFelonaturelons

    val updatelondPrelonselonntation = itelonm.prelonselonntation.map {
      caselon urtItelonm @ UrtItelonmPrelonselonntation(timelonlinelonItelonm: TwelonelontItelonm, _) =>
        val clielonntelonvelonntDelontails =
          delontailsBuildelonr(quelonry, itelonm.candidatelon, quelonry.felonaturelons.gelont ++ updatelondItelonmCandidatelonFelonaturelons)
        val updatelondClielonntelonvelonntInfo =
          timelonlinelonItelonm.clielonntelonvelonntInfo.map(_.copy(delontails = clielonntelonvelonntDelontails))
        val updatelondTimelonlinelonItelonm = timelonlinelonItelonm.copy(clielonntelonvelonntInfo = updatelondClielonntelonvelonntInfo)
        urtItelonm.copy(timelonlinelonItelonm = updatelondTimelonlinelonItelonm)
      caselon any => any
    }
    itelonm.copy(prelonselonntation = updatelondPrelonselonntation, felonaturelons = updatelondItelonmCandidatelonFelonaturelons)
  }
}
