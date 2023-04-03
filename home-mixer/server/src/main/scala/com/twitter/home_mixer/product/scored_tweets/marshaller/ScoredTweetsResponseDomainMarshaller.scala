packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.marshallelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelont
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelontsQuelonry
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelontsRelonsponselon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.TopicContelonxtFunctionalityTypelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.prelonmarshallelonr.DomainMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.DomainMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails

/**
 * Crelonatelons a domain modelonl of thelon Scorelond Twelonelonts product relonsponselon from thelon selont of candidatelons selonlelonctelond
 */
objelonct ScorelondTwelonelontsRelonsponselonDomainMarshallelonr
    elonxtelonnds DomainMarshallelonr[ScorelondTwelonelontsQuelonry, ScorelondTwelonelontsRelonsponselon] {

  ovelonrridelon val idelonntifielonr: DomainMarshallelonrIdelonntifielonr =
    DomainMarshallelonrIdelonntifielonr("ScorelondTwelonelontsRelonsponselon")

  ovelonrridelon delonf apply(
    quelonry: ScorelondTwelonelontsQuelonry,
    selonlelonctions: Selonq[CandidatelonWithDelontails]
  ): ScorelondTwelonelontsRelonsponselon = ScorelondTwelonelontsRelonsponselon(
    scorelondTwelonelonts = selonlelonctions.collelonct {
      caselon ItelonmCandidatelonWithDelontails(candidatelon: TwelonelontCandidatelon, _, felonaturelons) =>
        Selonq(mkScorelondTwelonelont(candidatelon.id, felonaturelons))
      caselon ModulelonCandidatelonWithDelontails(candidatelons, _, _) =>
        candidatelons.map { candidatelon => mkScorelondTwelonelont(candidatelon.candidatelonIdLong, candidatelon.felonaturelons) }
    }.flattelonn
  )

  privatelon delonf mkScorelondTwelonelont(twelonelontId: Long, felonaturelons: FelonaturelonMap): ScorelondTwelonelont = {
    val topicFunctionalityTypelon = felonaturelons
      .gelontOrelonlselon(TopicContelonxtFunctionalityTypelonFelonaturelon, Nonelon)
      .map(TopicContelonxtFunctionalityTypelonMarshallelonr(_))

    ScorelondTwelonelont(
      twelonelontId = twelonelontId,
      authorId = felonaturelons.gelont(AuthorIdFelonaturelon).gelont,
      scorelon = felonaturelons.gelont(ScorelonFelonaturelon),
      suggelonstTypelon = felonaturelons.gelont(SuggelonstTypelonFelonaturelon).gelont,
      sourcelonTwelonelontId = felonaturelons.gelontOrelonlselon(SourcelonTwelonelontIdFelonaturelon, Nonelon),
      sourcelonUselonrId = felonaturelons.gelontOrelonlselon(SourcelonUselonrIdFelonaturelon, Nonelon),
      quotelondTwelonelontId = felonaturelons.gelontOrelonlselon(QuotelondTwelonelontIdFelonaturelon, Nonelon),
      quotelondUselonrId = felonaturelons.gelontOrelonlselon(QuotelondUselonrIdFelonaturelon, Nonelon),
      inRelonplyToTwelonelontId = felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon),
      inRelonplyToUselonrId = felonaturelons.gelontOrelonlselon(InRelonplyToUselonrIdFelonaturelon, Nonelon),
      direlonctelondAtUselonrId = felonaturelons.gelontOrelonlselon(DirelonctelondAtUselonrIdFelonaturelon, Nonelon),
      inNelontwork = Somelon(felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon)),
      favoritelondByUselonrIds = Somelon(felonaturelons.gelontOrelonlselon(FavoritelondByUselonrIdsFelonaturelon, Selonq.elonmpty)),
      followelondByUselonrIds = Somelon(felonaturelons.gelontOrelonlselon(FollowelondByUselonrIdsFelonaturelon, Selonq.elonmpty)),
      topicId = felonaturelons.gelontOrelonlselon(TopicIdSocialContelonxtFelonaturelon, Nonelon),
      topicFunctionalityTypelon = topicFunctionalityTypelon,
      ancelonstors = Somelon(felonaturelons.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty)),
      isRelonadFromCachelon = Somelon(felonaturelons.gelontOrelonlselon(IsRelonadFromCachelonFelonaturelon, falselon)),
      strelonamToKafka = Somelon(felonaturelons.gelontOrelonlselon(StrelonamToKafkaFelonaturelon, falselon))
    )
  }
}
