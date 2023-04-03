packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.felonaturelons.AuthorMutelonsVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.BoolelonanFelonaturelonCondition
import com.twittelonr.visibility.rulelons.Condition.ProtelonctelondVielonwelonr
import com.twittelonr.visibility.rulelons.Relonason.Unspeloncifielond

objelonct FollowelonrRelonlations {

  caselon objelonct AuthorMutelonsVielonwelonrFelonaturelon elonxtelonnds BoolelonanFelonaturelonCondition(AuthorMutelonsVielonwelonr)

  objelonct AuthorMutelonsVielonwelonrRulelon
      elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
        action = Drop(Unspeloncifielond),
        condition = AuthorMutelonsVielonwelonrFelonaturelon)

  objelonct ProtelonctelondVielonwelonrRulelon
      elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(action = Drop(Unspeloncifielond), condition = ProtelonctelondVielonwelonr)

}
