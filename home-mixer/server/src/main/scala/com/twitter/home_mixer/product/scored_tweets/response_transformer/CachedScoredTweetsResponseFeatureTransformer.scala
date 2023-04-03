packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.relonsponselon_transformelonr

import com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelons.TopicContelonxtFunctionalityTypelonUnmarshallelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AncelonstorsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIsBluelonVelonrifielondFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.CachelondCandidatelonPipelonlinelonIdelonntifielonrFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.DirelonctelondAtUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FavoritelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FollowelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelonadFromCachelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.LastScorelondTimelonstampMsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.QuotelondTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.QuotelondUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicContelonxtFunctionalityTypelonFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TopicIdSocialContelonxtFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TwelonelontUrlsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.WelonightelondModelonlScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.{thriftscala => hmt}
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonFelonaturelonTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr

objelonct CachelondScorelondTwelonelontsRelonsponselonFelonaturelonTransformelonr
    elonxtelonnds CandidatelonFelonaturelonTransformelonr[hmt.CachelondScorelondTwelonelont] {

  ovelonrridelon val idelonntifielonr: TransformelonrIdelonntifielonr =
    TransformelonrIdelonntifielonr("CachelondScorelondTwelonelontsRelonsponselon")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    AncelonstorsFelonaturelon,
    AuthorIdFelonaturelon,
    AuthorIsBluelonVelonrifielondFelonaturelon,
    CachelondCandidatelonPipelonlinelonIdelonntifielonrFelonaturelon,
    DirelonctelondAtUselonrIdFelonaturelon,
    FavoritelondByUselonrIdsFelonaturelon,
    FollowelondByUselonrIdsFelonaturelon,
    InNelontworkFelonaturelon,
    InRelonplyToTwelonelontIdFelonaturelon,
    InRelonplyToUselonrIdFelonaturelon,
    IsRelonadFromCachelonFelonaturelon,
    IsRelontwelonelontFelonaturelon,
    LastScorelondTimelonstampMsFelonaturelon,
    QuotelondTwelonelontIdFelonaturelon,
    QuotelondUselonrIdFelonaturelon,
    ScorelonFelonaturelon,
    SourcelonTwelonelontIdFelonaturelon,
    SourcelonUselonrIdFelonaturelon,
    SuggelonstTypelonFelonaturelon,
    TopicContelonxtFunctionalityTypelonFelonaturelon,
    TopicIdSocialContelonxtFelonaturelon,
    TwelonelontUrlsFelonaturelon,
    WelonightelondModelonlScorelonFelonaturelon
  )

  ovelonrridelon delonf transform(candidatelon: hmt.CachelondScorelondTwelonelont): FelonaturelonMap =
    FelonaturelonMapBuildelonr()
      .add(AncelonstorsFelonaturelon, candidatelon.ancelonstors.gelontOrelonlselon(Selonq.elonmpty))
      .add(AuthorIdFelonaturelon, candidatelon.uselonrId)
      .add(AuthorIsBluelonVelonrifielondFelonaturelon, candidatelon.authorIsBluelonVelonrifielond.gelontOrelonlselon(falselon))
      .add(CachelondCandidatelonPipelonlinelonIdelonntifielonrFelonaturelon, candidatelon.candidatelonPipelonlinelonIdelonntifielonr)
      .add(DirelonctelondAtUselonrIdFelonaturelon, candidatelon.direlonctelondAtUselonrId)
      .add(FavoritelondByUselonrIdsFelonaturelon, candidatelon.favoritelondByUselonrIds.gelontOrelonlselon(Selonq.elonmpty))
      .add(FollowelondByUselonrIdsFelonaturelon, candidatelon.followelondByUselonrIds.gelontOrelonlselon(Selonq.elonmpty))
      .add(InNelontworkFelonaturelon, candidatelon.isInNelontwork.gelontOrelonlselon(falselon))
      .add(InRelonplyToTwelonelontIdFelonaturelon, candidatelon.inRelonplyToTwelonelontId)
      .add(InRelonplyToUselonrIdFelonaturelon, candidatelon.inRelonplyToUselonrId)
      .add(IsRelonadFromCachelonFelonaturelon, truelon)
      .add(IsRelontwelonelontFelonaturelon, candidatelon.isRelontwelonelont.gelontOrelonlselon(falselon))
      .add(LastScorelondTimelonstampMsFelonaturelon, candidatelon.lastScorelondTimelonstampMs)
      .add(QuotelondTwelonelontIdFelonaturelon, candidatelon.quotelondTwelonelontId)
      .add(QuotelondUselonrIdFelonaturelon, candidatelon.quotelondUselonrId)
      .add(ScorelonFelonaturelon, candidatelon.scorelon)
      .add(SourcelonTwelonelontIdFelonaturelon, candidatelon.sourcelonTwelonelontId)
      .add(SourcelonUselonrIdFelonaturelon, candidatelon.sourcelonUselonrId)
      .add(SuggelonstTypelonFelonaturelon, candidatelon.suggelonstTypelon)
      .add(
        TopicContelonxtFunctionalityTypelonFelonaturelon,
        candidatelon.topicFunctionalityTypelon.map(TopicContelonxtFunctionalityTypelonUnmarshallelonr(_)))
      .add(TopicIdSocialContelonxtFelonaturelon, candidatelon.topicId)
      .add(TwelonelontUrlsFelonaturelon, candidatelon.urlsList.gelontOrelonlselon(Selonq.elonmpty))
      .add(WelonightelondModelonlScorelonFelonaturelon, candidatelon.scorelon)
      .build()
}
