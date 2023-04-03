packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonInnelonrQuotelondTwelonelontVielonwelonrBlocksAuthorIntelonrstitialRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonInnelonrQuotelondTwelonelontVielonwelonrMutelonsAuthorIntelonrstitialRulelonParam
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.AuthorBlocksVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.DelonactivatelondAuthor
import com.twittelonr.visibility.rulelons.Condition.elonraselondAuthor
import com.twittelonr.visibility.rulelons.Condition.IsQuotelondInnelonrTwelonelont
import com.twittelonr.visibility.rulelons.Condition.OffboardelondAuthor
import com.twittelonr.visibility.rulelons.Condition.ProtelonctelondAuthor
import com.twittelonr.visibility.rulelons.Condition.Relontwelonelont
import com.twittelonr.visibility.rulelons.Condition.SuspelonndelondAuthor
import com.twittelonr.visibility.rulelons.Condition.UnavailablelonAuthor
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrBlocksAuthor
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrMutelonsAuthor

objelonct UselonrUnavailablelonStatelonTombstonelonRulelons {
  abstract class UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph: elonpitaph, condition: Condition)
      elonxtelonnds RulelonWithConstantAction(Tombstonelon(elonpitaph), condition) {}

  abstract class UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph: elonpitaph, condition: Condition)
      elonxtelonnds RulelonWithConstantAction(Tombstonelon(elonpitaph), And(Relontwelonelont, condition)) {}

  abstract class UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(
    elonpitaph: elonpitaph,
    condition: Condition)
      elonxtelonnds RulelonWithConstantAction(Tombstonelon(elonpitaph), And(IsQuotelondInnelonrTwelonelont, condition))

  abstract class UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon(
    relonason: Relonason,
    condition: Condition)
      elonxtelonnds RulelonWithConstantAction(Intelonrstitial(relonason), And(IsQuotelondInnelonrTwelonelont, condition))

  objelonct SuspelonndelondUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Suspelonndelond, SuspelonndelondAuthor)

  objelonct DelonactivatelondUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Delonactivatelond, DelonactivatelondAuthor)

  objelonct OffBoardelondUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Offboardelond, OffboardelondAuthor)

  objelonct elonraselondUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Delonactivatelond, elonraselondAuthor)

  objelonct ProtelonctelondUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Protelonctelond, ProtelonctelondAuthor)

  objelonct AuthorBlocksVielonwelonrUselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.BlockelondBy, AuthorBlocksVielonwelonr)

  objelonct UselonrUnavailablelonTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonTwelonelontTombstonelonRulelon(elonpitaph.Unavailablelon, UnavailablelonAuthor)

  objelonct SuspelonndelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Suspelonndelond, SuspelonndelondAuthor)

  objelonct DelonactivatelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Delonactivatelond, DelonactivatelondAuthor)

  objelonct OffBoardelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Offboardelond, OffboardelondAuthor)

  objelonct elonraselondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Delonactivatelond, elonraselondAuthor)

  objelonct ProtelonctelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Protelonctelond, ProtelonctelondAuthor)

  objelonct AuthorBlocksVielonwelonrUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.BlockelondBy, AuthorBlocksVielonwelonr)

  objelonct VielonwelonrBlocksAuthorUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Unavailablelon, VielonwelonrBlocksAuthor)

  objelonct VielonwelonrMutelonsAuthorUselonrUnavailablelonRelontwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonRelontwelonelontTombstonelonRulelon(elonpitaph.Unavailablelon, VielonwelonrMutelonsAuthor)

  objelonct SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(elonpitaph.Suspelonndelond, SuspelonndelondAuthor)

  objelonct DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(
        elonpitaph.Delonactivatelond,
        DelonactivatelondAuthor)

  objelonct OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(
        elonpitaph.Offboardelond,
        OffboardelondAuthor)

  objelonct elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(elonpitaph.Delonactivatelond, elonraselondAuthor)

  objelonct ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(elonpitaph.Protelonctelond, ProtelonctelondAuthor)

  objelonct AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontTombstonelonRulelon(
        elonpitaph.BlockelondBy,
        AuthorBlocksVielonwelonr)

  objelonct VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon(
        Relonason.VielonwelonrBlocksAuthor,
        VielonwelonrBlocksAuthor) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonInnelonrQuotelondTwelonelontVielonwelonrBlocksAuthorIntelonrstitialRulelonParam)
  }

  objelonct VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      elonxtelonnds UselonrUnavailablelonStatelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon(
        Relonason.VielonwelonrMutelonsAuthor,
        VielonwelonrMutelonsAuthor) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonInnelonrQuotelondTwelonelontVielonwelonrMutelonsAuthorIntelonrstitialRulelonParam)
  }
}
