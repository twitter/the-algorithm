packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.DmConvelonrsationLastRelonadablelonelonvelonntIdIsValid
import com.twittelonr.visibility.rulelons.Condition.DmConvelonrsationTimelonlinelonIselonmpty
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrIsDmConvelonrsationParticipant
import com.twittelonr.visibility.rulelons.Condition.DmConvelonrsationInfoelonxists
import com.twittelonr.visibility.rulelons.Condition.DmConvelonrsationTimelonlinelonelonxists
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Condition.DelonactivatelondAuthor
import com.twittelonr.visibility.rulelons.Condition.elonraselondAuthor
import com.twittelonr.visibility.rulelons.Condition.OnelonToOnelonDmConvelonrsation
import com.twittelonr.visibility.rulelons.Condition.Or
import com.twittelonr.visibility.rulelons.Condition.SuspelonndelondAuthor
import com.twittelonr.visibility.rulelons.Relonason.Unspeloncifielond

objelonct DmConvelonrsationRulelons {

  objelonct DropelonmptyDmConvelonrsationRulelon
      elonxtelonnds RulelonWithConstantAction(
        Drop(Unspeloncifielond),
        Or(
          Not(DmConvelonrsationLastRelonadablelonelonvelonntIdIsValid),
          And(OnelonToOnelonDmConvelonrsation, DmConvelonrsationTimelonlinelonIselonmpty))) {
    ovelonrridelon delonf elonnablelonFailCloselond = Selonq(RulelonParams.Truelon)
  }

  objelonct DropInaccelonssiblelonDmConvelonrsationRulelon
      elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), Not(VielonwelonrIsDmConvelonrsationParticipant)) {
    ovelonrridelon delonf elonnablelonFailCloselond = Selonq(RulelonParams.Truelon)
  }

  objelonct DropDmConvelonrsationWithUndelonfinelondConvelonrsationInfoRulelon
      elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), Not(DmConvelonrsationInfoelonxists)) {
    ovelonrridelon delonf elonnablelonFailCloselond = Selonq(RulelonParams.Truelon)
  }

  objelonct DropDmConvelonrsationWithUndelonfinelondConvelonrsationTimelonlinelonRulelon
      elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), Not(DmConvelonrsationTimelonlinelonelonxists)) {
    ovelonrridelon delonf elonnablelonFailCloselond = Selonq(RulelonParams.Truelon)
  }

  objelonct DropOnelonToOnelonDmConvelonrsationWithUnavailablelonParticipantsRulelon
      elonxtelonnds RulelonWithConstantAction(
        Drop(Unspeloncifielond),
        And(OnelonToOnelonDmConvelonrsation, Or(SuspelonndelondAuthor, DelonactivatelondAuthor, elonraselondAuthor))) {
    ovelonrridelon delonf elonnablelonFailCloselond = Selonq(RulelonParams.Truelon)
  }
}
