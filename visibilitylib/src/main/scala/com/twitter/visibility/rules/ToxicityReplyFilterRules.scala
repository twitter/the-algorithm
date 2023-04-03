packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.rulelons.Relonason.Toxicity

objelonct ToxicityRelonplyFiltelonrRulelons {

  selonalelond abstract class ToxicityRelonplyFiltelonrBaselonRulelon(
    action: Action)
      elonxtelonnds RulelonWithConstantAction(
        action = action,
        condition = Condition.ToxrfFiltelonrelondFromAuthorVielonwelonr)

  objelonct ToxicityRelonplyFiltelonrRulelon
      elonxtelonnds ToxicityRelonplyFiltelonrBaselonRulelon(action = Tombstonelon(elonpitaph.Unavailablelon)) {

    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
      RulelonParams.elonnablelonToxicRelonplyFiltelonringConvelonrsationRulelonsParam)
  }

  objelonct ToxicityRelonplyFiltelonrDropNotificationRulelon
      elonxtelonnds ToxicityRelonplyFiltelonrBaselonRulelon(action = Drop(Toxicity)) {

    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
      RulelonParams.elonnablelonToxicRelonplyFiltelonringNotificationsRulelonsParam)
  }
}
