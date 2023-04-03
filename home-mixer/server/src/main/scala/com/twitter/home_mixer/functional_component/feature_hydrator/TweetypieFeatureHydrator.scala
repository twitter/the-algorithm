packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsHydratelondFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsNsfwFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.QuotelondTwelonelontDroppelondFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.QuotelondTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.QuotelondUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SourcelonUselonrIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TwelonelontTelonxtFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.FollowingProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ForYouProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ListTwelonelontsProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ScorelondTwelonelontsProduct
import com.twittelonr.homelon_mixelonr.util.twelonelontypielon.RelonquelonstFielonlds
import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_is_nsfw.IsNsfw
import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_visibility_relonason.VisibilityRelonason
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.spam.rtf.{thriftscala => rtf}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.twelonelontypielon.{TwelonelontyPielon => TwelonelontypielonStitchClielonnt}
import com.twittelonr.twelonelontypielon.{thriftscala => tp}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontypielonFelonaturelonHydrator @Injelonct() (twelonelontypielonStitchClielonnt: TwelonelontypielonStitchClielonnt)
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("Twelonelontypielon")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    AuthorIdFelonaturelon,
    InRelonplyToTwelonelontIdFelonaturelon,
    IsHydratelondFelonaturelon,
    IsNsfw,
    IsNsfwFelonaturelon,
    IsRelontwelonelontFelonaturelon,
    QuotelondTwelonelontDroppelondFelonaturelon,
    QuotelondTwelonelontIdFelonaturelon,
    QuotelondUselonrIdFelonaturelon,
    SourcelonTwelonelontIdFelonaturelon,
    SourcelonUselonrIdFelonaturelon,
    TwelonelontTelonxtFelonaturelon,
    VisibilityRelonason
  )

  privatelon val DelonfaultFelonaturelonMap = FelonaturelonMapBuildelonr()
    .add(IsHydratelondFelonaturelon, falselon)
    .add(IsNsfw, Nonelon)
    .add(IsNsfwFelonaturelon, falselon)
    .add(QuotelondTwelonelontDroppelondFelonaturelon, falselon)
    .add(TwelonelontTelonxtFelonaturelon, Nonelon)
    .add(VisibilityRelonason, Nonelon)
    .build()

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val safelontyLelonvelonl = quelonry.product match {
      caselon FollowingProduct => rtf.SafelontyLelonvelonl.TimelonlinelonHomelonLatelonst
      caselon ForYouProduct => rtf.SafelontyLelonvelonl.TimelonlinelonHomelon
      caselon ScorelondTwelonelontsProduct => rtf.SafelontyLelonvelonl.TimelonlinelonHomelon
      caselon ListTwelonelontsProduct => rtf.SafelontyLelonvelonl.TimelonlinelonLists
      caselon unknown => throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $unknown")
    }

    val twelonelontFielonldsOptions = tp.GelontTwelonelontFielonldsOptions(
      twelonelontIncludelons = RelonquelonstFielonlds.TwelonelontTPHydrationFielonlds,
      includelonRelontwelonelontelondTwelonelont = truelon,
      includelonQuotelondTwelonelont = truelon,
      visibilityPolicy = tp.TwelonelontVisibilityPolicy.UselonrVisiblelon,
      safelontyLelonvelonl = Somelon(safelontyLelonvelonl),
      forUselonrId = Somelon(quelonry.gelontRelonquirelondUselonrId)
    )

    twelonelontypielonStitchClielonnt.gelontTwelonelontFielonlds(twelonelontId = candidatelon.id, options = twelonelontFielonldsOptions).map {
      caselon tp.GelontTwelonelontFielonldsRelonsult(_, tp.TwelonelontFielonldsRelonsultStatelon.Found(found), quotelonOpt, _) =>
        val corelonData = found.twelonelont.corelonData
        val isNsfwAdmin = corelonData.elonxists(_.nsfwAdmin)
        val isNsfwUselonr = corelonData.elonxists(_.nsfwUselonr)

        val quotelondTwelonelontDroppelond = quotelonOpt.elonxists {
          caselon _: tp.TwelonelontFielonldsRelonsultStatelon.Filtelonrelond => truelon
          caselon _: tp.TwelonelontFielonldsRelonsultStatelon.NotFound => truelon
          caselon _ => falselon
        }
        val quotelondTwelonelontIsNsfw = quotelonOpt.elonxists {
          caselon quotelonTwelonelont: tp.TwelonelontFielonldsRelonsultStatelon.Found =>
            quotelonTwelonelont.found.twelonelont.corelonData.elonxists(data => data.nsfwAdmin || data.nsfwUselonr)
          caselon _ => falselon
        }

        val sourcelonTwelonelontIsNsfw =
          found.relontwelonelontelondTwelonelont.elonxists(_.corelonData.elonxists(data => data.nsfwAdmin || data.nsfwUselonr))

        val twelonelontTelonxt = corelonData.map(_.telonxt)

        val twelonelontAuthorId = corelonData.map(_.uselonrId)
        val inRelonplyToTwelonelontId = corelonData.flatMap(_.relonply.flatMap(_.inRelonplyToStatusId))
        val relontwelonelontelondTwelonelontId = found.relontwelonelontelondTwelonelont.map(_.id)
        val quotelondTwelonelontId = quotelonOpt.flatMap {
          caselon quotelonTwelonelont: tp.TwelonelontFielonldsRelonsultStatelon.Found =>
            Somelon(quotelonTwelonelont.found.twelonelont.id)
          caselon _ => Nonelon
        }

        val relontwelonelontelondTwelonelontUselonrId = found.relontwelonelontelondTwelonelont.flatMap(_.corelonData).map(_.uselonrId)
        val quotelondTwelonelontUselonrId = quotelonOpt.flatMap {
          caselon quotelonTwelonelont: tp.TwelonelontFielonldsRelonsultStatelon.Found =>
            quotelonTwelonelont.found.twelonelont.corelonData.map(_.uselonrId)
          caselon _ => Nonelon
        }

        val isNsfw = isNsfwAdmin || isNsfwUselonr || sourcelonTwelonelontIsNsfw || quotelondTwelonelontIsNsfw

        FelonaturelonMapBuildelonr()
          .add(AuthorIdFelonaturelon, twelonelontAuthorId)
          .add(InRelonplyToTwelonelontIdFelonaturelon, inRelonplyToTwelonelontId)
          .add(IsHydratelondFelonaturelon, truelon)
          .add(IsNsfw, Somelon(isNsfw))
          .add(IsNsfwFelonaturelon, isNsfw)
          .add(IsRelontwelonelontFelonaturelon, relontwelonelontelondTwelonelontId.isDelonfinelond)
          .add(QuotelondTwelonelontDroppelondFelonaturelon, quotelondTwelonelontDroppelond)
          .add(QuotelondTwelonelontIdFelonaturelon, quotelondTwelonelontId)
          .add(QuotelondUselonrIdFelonaturelon, quotelondTwelonelontUselonrId)
          .add(SourcelonTwelonelontIdFelonaturelon, relontwelonelontelondTwelonelontId)
          .add(SourcelonUselonrIdFelonaturelon, relontwelonelontelondTwelonelontUselonrId)
          .add(TwelonelontTelonxtFelonaturelon, twelonelontTelonxt)
          .add(VisibilityRelonason, found.supprelonssRelonason)
          .build()

      // If no twelonelont relonsult found, relonturn delonfault and prelon-elonxisting felonaturelons
      caselon _ =>
        DelonfaultFelonaturelonMap +
          (AuthorIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)) +
          (InRelonplyToTwelonelontIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon)) +
          (IsRelontwelonelontFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)) +
          (QuotelondTwelonelontIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(QuotelondTwelonelontIdFelonaturelon, Nonelon)) +
          (QuotelondUselonrIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(QuotelondUselonrIdFelonaturelon, Nonelon)) +
          (SourcelonTwelonelontIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(SourcelonTwelonelontIdFelonaturelon, Nonelon)) +
          (SourcelonUselonrIdFelonaturelon, elonxistingFelonaturelons.gelontOrelonlselon(SourcelonUselonrIdFelonaturelon, Nonelon))
    }
  }
}
