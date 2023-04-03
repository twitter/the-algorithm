packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_twelonelontypielon

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.spam.rtf.thriftscala.SafelontyLelonvelonl
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.twelonelontypielon.{TwelonelontyPielon => TwelonelontypielonStitchClielonnt}
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontVisibilityPolicy
import com.twittelonr.twelonelontypielon.{thriftscala => TP}

// Candidatelon Felonaturelons
objelonct IsCommunityTwelonelontFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]

// Twelonelontypielon VF Felonaturelons
objelonct HasTakelondownFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct HasTakelondownForLocalelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsHydratelondFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsNarrowcastFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsNsfwAdminFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsNsfwFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsNsfwUselonrFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct IsNullcastFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct QuotelondTwelonelontDroppelondFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct QuotelondTwelonelontHasTakelondownFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct QuotelondTwelonelontHasTakelondownForLocalelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct QuotelondTwelonelontIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
objelonct SourcelonTwelonelontHasTakelondownFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct SourcelonTwelonelontHasTakelondownForLocalelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct TakelondownCountryCodelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selont[String]]
objelonct IsRelonplyFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
objelonct InRelonplyToFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
objelonct IsRelontwelonelontFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]

objelonct TwelonelontTwelonelontypielonCandidatelonFelonaturelonHydrator {
  val CorelonTwelonelontFielonlds: Selont[TP.TwelonelontIncludelon] = Selont[TP.TwelonelontIncludelon](
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.IdFielonld.id),
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.CorelonDataFielonld.id)
  )

  val NsfwLabelonlFielonlds: Selont[TP.TwelonelontIncludelon] = Selont[TP.TwelonelontIncludelon](
    // Twelonelont fielonlds containing NSFW relonlatelond attributelons, in addition to what elonxists in corelonData.
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.NsfwHighReloncallLabelonlFielonld.id),
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.NsfwHighPreloncisionLabelonlFielonld.id),
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.NsfaHighReloncallLabelonlFielonld.id)
  )

  val SafelontyLabelonlFielonlds: Selont[TP.TwelonelontIncludelon] = Selont[TP.TwelonelontIncludelon](
    // Twelonelont fielonlds containing RTF labelonls for abuselon and spam.
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.SpamLabelonlFielonld.id),
    TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.AbusivelonLabelonlFielonld.id)
  )

  val OrganicTwelonelontTPHydrationFielonlds: Selont[TP.TwelonelontIncludelon] = CorelonTwelonelontFielonlds ++
    NsfwLabelonlFielonlds ++
    SafelontyLabelonlFielonlds ++
    Selont(
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.TakelondownCountryCodelonsFielonld.id),
      // QTs imply a TwelonelontyPielon -> SGS relonquelonst delonpelonndelonncy
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.QuotelondTwelonelontFielonld.id),
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.elonschelonrbirdelonntityAnnotationsFielonld.id),
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.CommunitielonsFielonld.id),
      // Fielonld relonquirelond for delontelonrmining if a Twelonelont was crelonatelond via Nelonws Camelonra.
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.ComposelonrSourcelonFielonld.id)
    )

  val InjelonctelondTwelonelontTPHydrationFielonlds: Selont[TP.TwelonelontIncludelon] =
    OrganicTwelonelontTPHydrationFielonlds ++ Selont(
      // Melonntions imply a TwelonelontyPielon -> Gizmoduck relonquelonst delonpelonndelonncy
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.MelonntionsFielonld.id),
      TP.TwelonelontIncludelon.TwelonelontFielonldId(TP.Twelonelont.HashtagsFielonld.id)
    )

  val DelonfaultFelonaturelonMap = FelonaturelonMapBuildelonr()
    .add(IsNsfwAdminFelonaturelon, falselon)
    .add(IsNsfwUselonrFelonaturelon, falselon)
    .add(IsNsfwFelonaturelon, falselon)
    .add(IsNullcastFelonaturelon, falselon)
    .add(IsNarrowcastFelonaturelon, falselon)
    .add(HasTakelondownFelonaturelon, falselon)
    .add(IsCommunityTwelonelontFelonaturelon, falselon)
    .add(TakelondownCountryCodelonsFelonaturelon, Selont.elonmpty: Selont[String])
    .add(IsHydratelondFelonaturelon, falselon)
    .add(HasTakelondownForLocalelonFelonaturelon, falselon)
    .add(QuotelondTwelonelontDroppelondFelonaturelon, falselon)
    .add(SourcelonTwelonelontHasTakelondownFelonaturelon, falselon)
    .add(QuotelondTwelonelontHasTakelondownFelonaturelon, falselon)
    .add(SourcelonTwelonelontHasTakelondownForLocalelonFelonaturelon, falselon)
    .add(QuotelondTwelonelontHasTakelondownForLocalelonFelonaturelon, falselon)
    .add(IsRelonplyFelonaturelon, falselon)
    .add(InRelonplyToFelonaturelon, Nonelon)
    .add(IsRelontwelonelontFelonaturelon, falselon)
    .build()
}

class TwelonelontTwelonelontypielonCandidatelonFelonaturelonHydrator(
  twelonelontypielonStitchClielonnt: TwelonelontypielonStitchClielonnt,
  safelontyLelonvelonlPrelondicatelon: PipelonlinelonQuelonry => SafelontyLelonvelonl)
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, BaselonTwelonelontCandidatelon] {

  import TwelonelontTwelonelontypielonCandidatelonFelonaturelonHydrator._

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] =
    Selont(
      IsNsfwFelonaturelon,
      IsNsfwAdminFelonaturelon,
      IsNsfwUselonrFelonaturelon,
      IsNullcastFelonaturelon,
      IsNarrowcastFelonaturelon,
      HasTakelondownFelonaturelon,
      IsCommunityTwelonelontFelonaturelon,
      TakelondownCountryCodelonsFelonaturelon,
      IsHydratelondFelonaturelon,
      HasTakelondownForLocalelonFelonaturelon,
      QuotelondTwelonelontDroppelondFelonaturelon,
      SourcelonTwelonelontHasTakelondownFelonaturelon,
      QuotelondTwelonelontHasTakelondownFelonaturelon,
      SourcelonTwelonelontHasTakelondownForLocalelonFelonaturelon,
      QuotelondTwelonelontHasTakelondownForLocalelonFelonaturelon,
      IsRelonplyFelonaturelon,
      InRelonplyToFelonaturelon,
      IsRelontwelonelontFelonaturelon
    )

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("TwelonelontTwelonelontypielon")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: BaselonTwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val countryCodelon = quelonry.gelontCountryCodelon.gelontOrelonlselon("")

    twelonelontypielonStitchClielonnt
      .gelontTwelonelontFielonlds(
        twelonelontId = candidatelon.id,
        options = TP.GelontTwelonelontFielonldsOptions(
          twelonelontIncludelons = OrganicTwelonelontTPHydrationFielonlds,
          includelonRelontwelonelontelondTwelonelont = truelon,
          includelonQuotelondTwelonelont = truelon,
          visibilityPolicy = TwelonelontVisibilityPolicy.UselonrVisiblelon,
          safelontyLelonvelonl = Somelon(safelontyLelonvelonlPrelondicatelon(quelonry))
        )
      ).map {
        caselon TP.GelontTwelonelontFielonldsRelonsult(_, TP.TwelonelontFielonldsRelonsultStatelon.Found(found), quotelonOpt, _) =>
          val corelonData = found.twelonelont.corelonData
          val isNsfwAdmin = corelonData.elonxists(_.nsfwAdmin)
          val isNsfwUselonr = corelonData.elonxists(_.nsfwUselonr)
          val hasTakelondown = corelonData.elonxists(_.hasTakelondown)
          val isRelonply = corelonData.elonxists(_.relonply.nonelonmpty)
          val ancelonstorId = corelonData.flatMap(_.relonply).flatMap(_.inRelonplyToStatusId)
          val isRelontwelonelont = corelonData.elonxists(_.sharelon.nonelonmpty)
          val takelondownCountryCodelons =
            found.twelonelont.takelondownCountryCodelons.gelontOrelonlselon(Selonq.elonmpty).map(_.toLowelonrCaselon).toSelont

          val quotelondTwelonelontDroppelond = quotelonOpt.elonxists {
            caselon _: TP.TwelonelontFielonldsRelonsultStatelon.Filtelonrelond =>
              truelon
            caselon _: TP.TwelonelontFielonldsRelonsultStatelon.NotFound =>
              truelon
            caselon _ => falselon
          }
          val quotelondTwelonelontIsNsfw = quotelonOpt.elonxists {
            caselon quotelonTwelonelont: TP.TwelonelontFielonldsRelonsultStatelon.Found =>
              quotelonTwelonelont.found.twelonelont.corelonData.elonxists(data => data.nsfwAdmin || data.nsfwUselonr)
            caselon _ => falselon
          }
          val quotelondTwelonelontHasTakelondown = quotelonOpt.elonxists {
            caselon quotelonTwelonelont: TP.TwelonelontFielonldsRelonsultStatelon.Found =>
              quotelonTwelonelont.found.twelonelont.corelonData.elonxists(_.hasTakelondown)
            caselon _ => falselon
          }
          val quotelondTwelonelontTakelondownCountryCodelons = quotelonOpt
            .collelonct {
              caselon quotelonTwelonelont: TP.TwelonelontFielonldsRelonsultStatelon.Found =>
                quotelonTwelonelont.found.twelonelont.takelondownCountryCodelons
                  .gelontOrelonlselon(Selonq.elonmpty).map(_.toLowelonrCaselon).toSelont
            }.gelontOrelonlselon(Selont.elonmpty[String])

          val sourcelonTwelonelontIsNsfw =
            found.relontwelonelontelondTwelonelont.elonxists(_.corelonData.elonxists(data => data.nsfwAdmin || data.nsfwUselonr))
          val sourcelonTwelonelontHasTakelondown =
            found.relontwelonelontelondTwelonelont.elonxists(_.corelonData.elonxists(_.hasTakelondown))
          val sourcelonTwelonelontTakelondownCountryCodelons = found.relontwelonelontelondTwelonelont
            .map { sourcelonTwelonelont: TP.Twelonelont =>
              sourcelonTwelonelont.takelondownCountryCodelons.gelontOrelonlselon(Selonq.elonmpty).map(_.toLowelonrCaselon).toSelont
            }.gelontOrelonlselon(Selont.elonmpty)

          FelonaturelonMapBuildelonr()
            .add(IsNsfwAdminFelonaturelon, isNsfwAdmin)
            .add(IsNsfwUselonrFelonaturelon, isNsfwUselonr)
            .add(IsNsfwFelonaturelon, isNsfwAdmin || isNsfwUselonr || sourcelonTwelonelontIsNsfw || quotelondTwelonelontIsNsfw)
            .add(IsNullcastFelonaturelon, corelonData.elonxists(_.nullcast))
            .add(IsNarrowcastFelonaturelon, corelonData.elonxists(_.narrowcast.nonelonmpty))
            .add(HasTakelondownFelonaturelon, hasTakelondown)
            .add(
              HasTakelondownForLocalelonFelonaturelon,
              hasTakelondownForLocalelon(hasTakelondown, countryCodelon, takelondownCountryCodelons))
            .add(QuotelondTwelonelontDroppelondFelonaturelon, quotelondTwelonelontDroppelond)
            .add(SourcelonTwelonelontHasTakelondownFelonaturelon, sourcelonTwelonelontHasTakelondown)
            .add(QuotelondTwelonelontHasTakelondownFelonaturelon, quotelondTwelonelontHasTakelondown)
            .add(
              SourcelonTwelonelontHasTakelondownForLocalelonFelonaturelon,
              hasTakelondownForLocalelon(
                sourcelonTwelonelontHasTakelondown,
                countryCodelon,
                sourcelonTwelonelontTakelondownCountryCodelons))
            .add(
              QuotelondTwelonelontHasTakelondownForLocalelonFelonaturelon,
              hasTakelondownForLocalelon(
                quotelondTwelonelontHasTakelondown,
                countryCodelon,
                quotelondTwelonelontTakelondownCountryCodelons))
            .add(IsCommunityTwelonelontFelonaturelon, found.twelonelont.communitielons.elonxists(_.communityIds.nonelonmpty))
            .add(
              TakelondownCountryCodelonsFelonaturelon,
              found.twelonelont.takelondownCountryCodelons.gelontOrelonlselon(Selonq.elonmpty).map(_.toLowelonrCaselon).toSelont)
            .add(IsHydratelondFelonaturelon, truelon)
            .add(IsRelonplyFelonaturelon, isRelonply)
            .add(InRelonplyToFelonaturelon, ancelonstorId)
            .add(IsRelontwelonelontFelonaturelon, isRelontwelonelont)
            .build()

        // If no twelonelont relonsult found, relonturn delonfault felonaturelons
        caselon _ =>
          DelonfaultFelonaturelonMap
      }
  }

  privatelon delonf hasTakelondownForLocalelon(
    hasTakelondown: Boolelonan,
    countryCodelon: String,
    takelondownCountryCodelons: Selont[String]
  ) = hasTakelondown && takelondownCountryCodelons.contains(countryCodelon)
}
