packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.FSRulelonParams.CardUriRootDomainDelonnyListParam
import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonCardUriRootDomainCardDelonnylistRulelon
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonCommunityNonMelonmbelonrPollCardRulelon
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonCommunityNonMelonmbelonrPollCardRulelonFailCloselond
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.CardUriHasRootDomain
import com.twittelonr.visibility.rulelons.Condition.CommunityTwelonelontCommunityVisiblelon
import com.twittelonr.visibility.rulelons.Condition.IsPollCard
import com.twittelonr.visibility.rulelons.Condition.LoggelondOutOrVielonwelonrNotFollowingAuthor
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Condition.Or
import com.twittelonr.visibility.rulelons.Condition.ProtelonctelondAuthor
import com.twittelonr.visibility.rulelons.Condition.TwelonelontIsCommunityTwelonelont
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrIsCommunityMelonmbelonr

objelonct DropProtelonctelondAuthorPollCardRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.ProtelonctelondAuthor),
      And(
        IsPollCard,
        ProtelonctelondAuthor,
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
      )
    )

objelonct DropCardUriRootDomainDelonnylistRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.Unspeloncifielond),
      And(CardUriHasRootDomain(CardUriRootDomainDelonnyListParam))
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonCardUriRootDomainCardDelonnylistRulelon)
}

objelonct DropCommunityNonMelonmbelonrPollCardRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.CommunityNotAMelonmbelonr),
      And(
        IsPollCard,
        TwelonelontIsCommunityTwelonelont,
        Or(
          Not(VielonwelonrIsCommunityMelonmbelonr),
          Not(CommunityTwelonelontCommunityVisiblelon),
        )
      ),
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonCommunityNonMelonmbelonrPollCardRulelon)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonCommunityNonMelonmbelonrPollCardRulelonFailCloselond)
}
