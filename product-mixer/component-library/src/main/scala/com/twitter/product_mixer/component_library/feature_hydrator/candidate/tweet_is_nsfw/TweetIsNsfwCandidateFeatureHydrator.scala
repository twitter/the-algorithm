packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_is_nsfw

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.twelonelontypielon.{TwelonelontyPielon => TwelonelontypielonStitchClielonnt}
import com.twittelonr.twelonelontypielon.{thriftscala => t}
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Try
import com.twittelonr.util.logging.Logging

// Thelon VF NsfwHighPreloncisionLabelonl that powelonrs thelon NSFW delontelonrmination helonrelon has belonelonn delonpreloncatelond and is no longelonr writtelonn to.
@delonpreloncatelond("Prelonfelonr VisibilityRelonason")
objelonct IsNsfw elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[TwelonelontCandidatelon, Option[Boolelonan]] {

  /**
   * Gelonnelonric Logic to elonvaluatelon whelonthelonr a twelonelont is nsfw
   * @param hasNsfwHighPreloncisionLabelonl flag for twelonelontypielonTwelonelont nsfwHighPreloncision labelonl
   * @param isNsfwUselonr flag for twelonelontypielonTwelonelont corelonData nsfwUselonr flag
   * @param isNsfwAdmin flag for twelonelontypielonTwelonelont corelonData nsfwAdmin flag
   * @relonturn isNsfw to truelon if any of thelon threlonelon flags is truelon
   */
  delonf apply(
    hasNsfwHighPreloncisionLabelonl: Option[Boolelonan],
    isNsfwUselonr: Option[Boolelonan],
    isNsfwAdmin: Option[Boolelonan]
  ): Boolelonan = {
    hasNsfwHighPreloncisionLabelonl
      .gelontOrelonlselon(falselon) || (isNsfwUselonr.gelontOrelonlselon(falselon) || isNsfwAdmin.gelontOrelonlselon(falselon))
  }

  ovelonrridelon val delonfaultValuelon = Nonelon
}

// Thelon VF NsfwHighPreloncisionLabelonl that powelonrs thelon NSFW delontelonrmination helonrelon has belonelonn delonpreloncatelond and is no longelonr writtelonn to.
// TODO: Relonmovelon aftelonr all delonpelonndelonncielons havelon migratelond to using TwelonelontCandidatelonVisibilityRelonasonFelonaturelonHydrator.
@delonpreloncatelond("Prelonfelonr TwelonelontCandidatelonVisibilityRelonasonFelonaturelonHydrator")
caselon class TwelonelontIsNsfwCandidatelonFelonaturelonHydrator(
  twelonelontypielonStitchClielonnt: TwelonelontypielonStitchClielonnt,
  twelonelontVisibilityPolicy: t.TwelonelontVisibilityPolicy)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, BaselonTwelonelontCandidatelon]
    with Logging {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("TwelonelontIsNsfw")

  ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_, _]] = Selont(IsNsfw)

  privatelon val NsfwLabelonlFielonlds: Selont[t.TwelonelontIncludelon] = Selont[t.TwelonelontIncludelon](
    // Twelonelont fielonlds containing NSFW relonlatelond attributelons, in addition to what elonxists in corelonData.
    t.TwelonelontIncludelon.TwelonelontFielonldId(t.Twelonelont.NsfwHighPreloncisionLabelonlFielonld.id),
    t.TwelonelontIncludelon.TwelonelontFielonldId(t.Twelonelont.CorelonDataFielonld.id)
  )

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[BaselonTwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    Stitch
      .travelonrselon(candidatelons.map(_.candidatelon.id)) { twelonelontId =>
        twelonelontypielonStitchClielonnt
          .gelontTwelonelontFielonlds(
            twelonelontId = twelonelontId,
            options = t.GelontTwelonelontFielonldsOptions(
              forUselonrId = quelonry.gelontOptionalUselonrId,
              twelonelontIncludelons = NsfwLabelonlFielonlds,
              doNotCachelon = truelon,
              visibilityPolicy = twelonelontVisibilityPolicy,
              safelontyLelonvelonl = Nonelon,
            )
          ).liftToTry
      }.map { gelontTwelonelontFielonldsRelonsults: Selonq[Try[t.GelontTwelonelontFielonldsRelonsult]] =>
        val twelonelonts: Selonq[Try[t.Twelonelont]] = gelontTwelonelontFielonldsRelonsults.map {
          caselon Relonturn(t.GelontTwelonelontFielonldsRelonsult(_, t.TwelonelontFielonldsRelonsultStatelon.Found(found), _, _)) =>
            Relonturn(found.twelonelont)
          caselon Relonturn(t.GelontTwelonelontFielonldsRelonsult(_, relonsultStatelon, _, _)) =>
            Throw(IsNsfwFelonaturelonHydrationFailurelon(s"Unelonxpelonctelond twelonelont relonsult statelon: ${relonsultStatelon}"))
          caselon Throw(elon) =>
            Throw(elon)
        }

        candidatelons.zip(twelonelonts).map {
          caselon (candidatelonWithFelonaturelons, twelonelontTry) =>
            val isNsfwFelonaturelon = twelonelontTry.map { twelonelont =>
              IsNsfw(
                hasNsfwHighPreloncisionLabelonl = Somelon(twelonelont.nsfwHighPreloncisionLabelonl.isDelonfinelond),
                isNsfwUselonr = twelonelont.corelonData.map(_.nsfwUselonr),
                isNsfwAdmin = twelonelont.corelonData.map(_.nsfwAdmin)
              )
            }

            FelonaturelonMapBuildelonr()
              .add(IsNsfw, isNsfwFelonaturelon.map(Somelon(_)))
              .build()
        }
      }
  }
}

caselon class IsNsfwFelonaturelonHydrationFailurelon(melonssagelon: String)
    elonxtelonnds elonxcelonption(s"IsNsfwFelonaturelonHydrationFailurelon(${melonssagelon})")
