packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonAuthorBlocksVielonwelonrDropRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonInnelonrQuotelondTwelonelontVielonwelonrBlocksAuthorIntelonrstitialRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonInnelonrQuotelondTwelonelontVielonwelonrMutelonsAuthorIntelonrstitialRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonTimelonlinelonHomelonPromotelondTwelonelontHelonalthelonnforcelonmelonntRulelons
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonVielonwelonrIsSoftUselonrDropRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.PromotelondTwelonelontHelonalthelonnforcelonmelonntHoldback
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.IsQuotelondInnelonrTwelonelont
import com.twittelonr.visibility.rulelons.Condition.NonAuthorVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Condition.Relontwelonelont
import com.twittelonr.visibility.rulelons.Condition.SoftVielonwelonr
import com.twittelonr.visibility.rulelons.Relonason._

objelonct DropAllRulelon
    elonxtelonnds AlwaysActRulelon(
      Drop(Unspeloncifielond)
    )

objelonct AllowAllRulelon
    elonxtelonnds AlwaysActRulelon(
      Allow
    )

objelonct TelonstRulelon
    elonxtelonnds AlwaysActRulelon(
      Drop(Unspeloncifielond)
    )

objelonct DelonactivatelondAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(DelonactivatelondAuthor),
      Condition.DelonactivatelondAuthor
    )

objelonct elonraselondAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(elonraselondAuthor),
      Condition.elonraselondAuthor
    )

objelonct OffboardelondAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(OffboardelondAuthor),
      Condition.OffboardelondAuthor
    )

objelonct DropNsfwUselonrAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Nsfw),
      Condition.NsfwUselonrAuthor
    )

objelonct DropNsfwUselonrAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon
    elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchRulelon(
      Drop(Nsfw),
      Condition.NsfwUselonrAuthor
    )

objelonct IntelonrstitialNsfwUselonrAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Nsfw),
      Condition.NsfwUselonrAuthor
    )

objelonct DropNsfwAdminAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Nsfw),
      Condition.NsfwAdminAuthor
    )

objelonct DropNsfwAdminAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon
    elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchRulelon(
      Drop(Nsfw),
      Condition.NsfwAdminAuthor
    )

objelonct IntelonrstitialNsfwAdminAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Nsfw),
      Condition.NsfwAdminAuthor
    )

objelonct ProtelonctelondAuthorDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.ProtelonctelondAuthor),
      And(Condition.LoggelondOutOrVielonwelonrNotFollowingAuthor, Condition.ProtelonctelondAuthor)
    )

objelonct ProtelonctelondAuthorTombstonelonRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.Protelonctelond),
      And(Condition.LoggelondOutOrVielonwelonrNotFollowingAuthor, Condition.ProtelonctelondAuthor)
    )

objelonct DropAllProtelonctelondAuthorRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.ProtelonctelondAuthor),
      Condition.ProtelonctelondAuthor
    ) {
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(RulelonParams.Truelon)
}

objelonct ProtelonctelondQuotelonTwelonelontAuthorRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.ProtelonctelondAuthor),
      And(Condition.OutelonrAuthorNotFollowingAuthor, Condition.ProtelonctelondAuthor)
    )

objelonct DropProtelonctelondVielonwelonrIfPrelonselonntRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.Unspeloncifielond),
      And(Condition.LoggelondInVielonwelonr, Condition.ProtelonctelondVielonwelonr)
    ) {
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(RulelonParams.Truelon)
}

objelonct SuspelonndelondAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(SuspelonndelondAuthor),
      Condition.SuspelonndelondAuthor
    )

objelonct SuspelonndelondVielonwelonrRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Unspeloncifielond),
      Condition.SuspelonndelondVielonwelonr
    )

objelonct DelonactivatelondVielonwelonrRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Unspeloncifielond),
      Condition.DelonactivatelondVielonwelonr
    )

objelonct VielonwelonrIsUnmelonntionelondRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrIsUnmelonntionelond),
      Condition.VielonwelonrIsUnmelonntionelond
    )

abstract class AuthorBlocksVielonwelonrRulelon(ovelonrridelon val action: Action)
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      action,
      Condition.AuthorBlocksVielonwelonr
    )

objelonct AuthorBlocksVielonwelonrDropRulelon
    elonxtelonnds AuthorBlocksVielonwelonrRulelon(
      Drop(Relonason.AuthorBlocksVielonwelonr)
    )

objelonct DeloncidelonrablelonAuthorBlocksVielonwelonrDropRulelon
    elonxtelonnds AuthorBlocksVielonwelonrRulelon(
      Drop(Relonason.AuthorBlocksVielonwelonr)
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonAuthorBlocksVielonwelonrDropRulelonParam)
}

objelonct AuthorBlocksVielonwelonrTombstonelonRulelon
    elonxtelonnds AuthorBlocksVielonwelonrRulelon(
      Tombstonelon(elonpitaph.BlockelondBy)
    )

objelonct VielonwelonrBlocksAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrBlocksAuthor),
      Condition.VielonwelonrBlocksAuthor
    )

objelonct VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon
    elonxtelonnds VielonwelonrOptInBlockingOnSelonarchRulelon(
      Drop(Relonason.VielonwelonrBlocksAuthor),
      Condition.VielonwelonrBlocksAuthor
    )

objelonct VielonwelonrMutelonsAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrMutelonsAuthor),
      Condition.VielonwelonrMutelonsAuthor
    )

objelonct VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon
    elonxtelonnds VielonwelonrOptInBlockingOnSelonarchRulelon(
      Drop(Relonason.VielonwelonrMutelonsAuthor),
      Condition.VielonwelonrMutelonsAuthor
    )

objelonct AuthorBlocksOutelonrAuthorRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.AuthorBlocksVielonwelonr),
      And(Not(Condition.IsSelonlfQuotelon), Condition.AuthorBlocksOutelonrAuthor)
    )

objelonct VielonwelonrMutelonsAndDoelonsNotFollowAuthorRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrHardMutelondAuthor),
      And(Condition.VielonwelonrMutelonsAuthor, Not(Condition.VielonwelonrDoelonsFollowAuthor))
    )

objelonct AuthorBlocksVielonwelonrUnspeloncifielondRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.Unspeloncifielond),
      Condition.AuthorBlocksVielonwelonr
    )

objelonct VielonwelonrHasMatchingMutelondKelonywordForNotificationsRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.MutelondKelonyword),
      Condition.VielonwelonrHasMatchingKelonywordForNotifications
    )

objelonct VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.MutelondKelonyword),
      Condition.VielonwelonrHasMatchingKelonywordForHomelonTimelonlinelon
    )

trait HasPromotelondTwelonelontHelonalthelonnforcelonmelonnt elonxtelonnds WithGatelon {
  ovelonrridelon delonf holdbacks: Selonq[RulelonParam[Boolelonan]] = Selonq(PromotelondTwelonelontHelonalthelonnforcelonmelonntHoldback)
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonTimelonlinelonHomelonPromotelondTwelonelontHelonalthelonnforcelonmelonntRulelons)
}

objelonct VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonPromotelondTwelonelontRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.MutelondKelonyword),
      Condition.VielonwelonrHasMatchingKelonywordForHomelonTimelonlinelon
    )
    with HasPromotelondTwelonelontHelonalthelonnforcelonmelonnt

objelonct VielonwelonrHasMatchingMutelondKelonywordForTwelonelontRelonplielonsRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.MutelondKelonyword),
      Condition.VielonwelonrHasMatchingKelonywordForTwelonelontRelonplielons
    )

objelonct MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.MutelondKelonyword),
      Condition.VielonwelonrHasMatchingKelonywordForTwelonelontRelonplielons
    )

objelonct MutelondKelonywordForQuotelondTwelonelontTwelonelontDelontailIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.MutelondKelonyword),
      And(Condition.IsQuotelondInnelonrTwelonelont, Condition.VielonwelonrHasMatchingKelonywordForTwelonelontRelonplielons)
    )

objelonct VielonwelonrMutelonsAuthorIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.VielonwelonrMutelonsAuthor),
      Condition.VielonwelonrMutelonsAuthor
    )

objelonct VielonwelonrMutelonsAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.VielonwelonrMutelonsAuthor),
      And(Condition.VielonwelonrMutelonsAuthor, IsQuotelondInnelonrTwelonelont)
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonInnelonrQuotelondTwelonelontVielonwelonrMutelonsAuthorIntelonrstitialRulelonParam)
}

objelonct VielonwelonrMutelonsAuthorHomelonTimelonlinelonPromotelondTwelonelontRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrMutelonsAuthor),
      Condition.VielonwelonrMutelonsAuthor
    )
    with HasPromotelondTwelonelontHelonalthelonnforcelonmelonnt

objelonct VielonwelonrBlocksAuthorIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.VielonwelonrBlocksAuthor),
      Condition.VielonwelonrBlocksAuthor
    )

objelonct VielonwelonrBlocksAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.VielonwelonrBlocksAuthor),
      And(Condition.VielonwelonrBlocksAuthor, IsQuotelondInnelonrTwelonelont)
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonInnelonrQuotelondTwelonelontVielonwelonrBlocksAuthorIntelonrstitialRulelonParam)
}

objelonct VielonwelonrBlocksAuthorHomelonTimelonlinelonPromotelondTwelonelontRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Drop(Relonason.VielonwelonrBlocksAuthor),
      Condition.VielonwelonrBlocksAuthor
    )
    with HasPromotelondTwelonelontHelonalthelonnforcelonmelonnt

objelonct VielonwelonrRelonportsAuthorIntelonrstitialRulelon
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      Intelonrstitial(Relonason.VielonwelonrRelonportelondAuthor),
      Condition.VielonwelonrRelonportsAuthor
    )

objelonct VielonwelonrIsAuthorDropRulelon
    elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), Not(NonAuthorVielonwelonr))

objelonct VielonwelonrIsNotAuthorDropRulelon elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), NonAuthorVielonwelonr)

objelonct RelontwelonelontDropRulelon elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), Relontwelonelont)

objelonct VielonwelonrIsSoftUselonrDropRulelon elonxtelonnds RulelonWithConstantAction(Drop(VielonwelonrIsSoftUselonr), SoftVielonwelonr) {

  ovelonrridelon val elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonVielonwelonrIsSoftUselonrDropRulelonParam)
}
