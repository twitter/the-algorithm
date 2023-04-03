packagelon com.twittelonr.homelon_mixelonr.product.for_you

import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => tl}
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.homelon_mixelonr.product.for_you.candidatelon_sourcelon.ScorelondTwelonelontWithConvelonrsationMelontadata
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonFelonaturelonTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.BasicTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncWithelonducationTopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ReloncommelonndationTopicContelonxtFunctionalityTypelon

objelonct ForYouScorelondTwelonelontsRelonsponselonFelonaturelonTransformelonr
    elonxtelonnds CandidatelonFelonaturelonTransformelonr[ScorelondTwelonelontWithConvelonrsationMelontadata] {

  ovelonrridelon val idelonntifielonr: TransformelonrIdelonntifielonr =
    TransformelonrIdelonntifielonr("ForYouScorelondTwelonelontsRelonsponselon")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    AncelonstorsFelonaturelon,
    AuthorIdFelonaturelon,
    ConvelonrsationModulelonIdFelonaturelon,
    ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon,
    DirelonctelondAtUselonrIdFelonaturelon,
    FavoritelondByUselonrIdsFelonaturelon,
    FollowelondByUselonrIdsFelonaturelon,
    InNelontworkFelonaturelon,
    InRelonplyToTwelonelontIdFelonaturelon,
    InRelonplyToUselonrIdFelonaturelon,
    IsRelonadFromCachelonFelonaturelon,
    IsRelontwelonelontFelonaturelon,
    QuotelondTwelonelontIdFelonaturelon,
    QuotelondUselonrIdFelonaturelon,
    ScorelonFelonaturelon,
    SourcelonTwelonelontIdFelonaturelon,
    SourcelonUselonrIdFelonaturelon,
    StrelonamToKafkaFelonaturelon,
    SuggelonstTypelonFelonaturelon,
    TopicContelonxtFunctionalityTypelonFelonaturelon,
    TopicIdSocialContelonxtFelonaturelon
  )

  ovelonrridelon delonf transform(input: ScorelondTwelonelontWithConvelonrsationMelontadata): FelonaturelonMap =
    FelonaturelonMapBuildelonr()
      .add(AncelonstorsFelonaturelon, input.ancelonstors.gelontOrelonlselon(Selonq.elonmpty))
      .add(AuthorIdFelonaturelon, Somelon(input.authorId))
      .add(ConvelonrsationModulelonIdFelonaturelon, input.convelonrsationId)
      .add(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, input.convelonrsationFocalTwelonelontId)
      .add(DirelonctelondAtUselonrIdFelonaturelon, input.direlonctelondAtUselonrId)
      .add(FavoritelondByUselonrIdsFelonaturelon, input.favoritelondByUselonrIds.gelontOrelonlselon(Selonq.elonmpty))
      .add(FollowelondByUselonrIdsFelonaturelon, input.followelondByUselonrIds.gelontOrelonlselon(Selonq.elonmpty))
      .add(InNelontworkFelonaturelon, input.inNelontwork.gelontOrelonlselon(falselon))
      .add(InRelonplyToTwelonelontIdFelonaturelon, input.inRelonplyToTwelonelontId)
      .add(InRelonplyToUselonrIdFelonaturelon, input.inRelonplyToUselonrId)
      .add(IsRelonadFromCachelonFelonaturelon, input.isRelonadFromCachelon.gelontOrelonlselon(falselon))
      .add(IsRelontwelonelontFelonaturelon, input.sourcelonTwelonelontId.isDelonfinelond)
      .add(QuotelondTwelonelontIdFelonaturelon, input.quotelondTwelonelontId)
      .add(QuotelondUselonrIdFelonaturelon, input.quotelondUselonrId)
      .add(ScorelonFelonaturelon, input.scorelon)
      .add(SourcelonTwelonelontIdFelonaturelon, input.sourcelonTwelonelontId)
      .add(SourcelonUselonrIdFelonaturelon, input.sourcelonUselonrId)
      .add(StrelonamToKafkaFelonaturelon, input.strelonamToKafka.gelontOrelonlselon(falselon))
      .add(SuggelonstTypelonFelonaturelon, input.suggelonstTypelon)
      .add(
        TopicContelonxtFunctionalityTypelonFelonaturelon,
        input.topicFunctionalityTypelon.collelonct {
          caselon tl.TopicContelonxtFunctionalityTypelon.Basic => BasicTopicContelonxtFunctionalityTypelon
          caselon tl.TopicContelonxtFunctionalityTypelon.Reloncommelonndation =>
            ReloncommelonndationTopicContelonxtFunctionalityTypelon
          caselon tl.TopicContelonxtFunctionalityTypelon.ReloncWithelonducation =>
            ReloncWithelonducationTopicContelonxtFunctionalityTypelon
        }
      )
      .add(TopicIdSocialContelonxtFelonaturelon, input.topicId)
      .build()
}
