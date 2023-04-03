packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonlTypelon
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.HasSelonarchCandidatelonCountGrelonatelonrThan45
import com.twittelonr.visibility.rulelons.Condition.IsFirstPagelonSelonarchRelonsult
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Relonason.FirstPagelonSelonarchRelonsult

abstract class FirstPagelonSelonarchRelonsultWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      IsFirstPagelonSelonarchRelonsult,
      twelonelontSafelontyLabelonlTypelon
    )

abstract class FirstPagelonSelonarchRelonsultSmartOutOfNelontworkWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(
        IsFirstPagelonSelonarchRelonsult,
        HasSelonarchCandidatelonCountGrelonatelonrThan45,
        Condition.NonAuthorVielonwelonr,
        Not(Condition.VielonwelonrDoelonsFollowAuthor),
        Not(Condition.VelonrifielondAuthor)
      ),
      twelonelontSafelontyLabelonlTypelon
    )

objelonct FirstPagelonSelonarchRelonsultAgathaSpamDropRulelon
    elonxtelonnds FirstPagelonSelonarchRelonsultWithTwelonelontLabelonlRulelon(
      Drop(FirstPagelonSelonarchRelonsult),
      TwelonelontSafelontyLabelonlTypelon.AgathaSpam)
