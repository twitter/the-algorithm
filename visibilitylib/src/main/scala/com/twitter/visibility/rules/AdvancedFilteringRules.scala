packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.gizmoduck.thriftscala.MelonntionFiltelonr.Following
import com.twittelonr.visibility.felonaturelons.VielonwelonrMelonntionFiltelonr
import com.twittelonr.visibility.rulelons.Condition._
import com.twittelonr.visibility.rulelons.Relonason.Unspeloncifielond

objelonct NoConfirmelondelonmailRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        VielonwelonrFiltelonrsNoConfirmelondelonmail,
        Not(AuthorHasConfirmelondelonmail)
      )
    )

objelonct NoConfirmelondPhonelonRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        VielonwelonrFiltelonrsNoConfirmelondPhonelon,
        Not(AuthorHasVelonrifielondPhonelon)
      )
    )

objelonct NoDelonfaultProfilelonImagelonRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        VielonwelonrFiltelonrsDelonfaultProfilelonImagelon,
        AuthorHasDelonfaultProfilelonImagelon
      )
    )

objelonct NoNelonwUselonrsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        AuthorIsNelonwAccount
      )
    )

objelonct NoNotFollowelondByRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        VielonwelonrFiltelonrsNotFollowelondBy,
        Not(AuthorDoelonsFollowVielonwelonr)
      )
    )

objelonct OnlyPelonoplelonIFollowRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        Not(VielonwelonrDoelonsFollowAuthor),
        elonquals(VielonwelonrMelonntionFiltelonr, Following),
        Not(NotificationIsOnCommunityTwelonelont)
      )
    )
