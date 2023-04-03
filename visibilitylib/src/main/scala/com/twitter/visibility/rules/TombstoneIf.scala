packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.IsFocalTwelonelont
import com.twittelonr.visibility.rulelons.Condition.Not

objelonct TombstonelonIf {

  objelonct AuthorIsProtelonctelond
      elonxtelonnds RulelonWithConstantAction(
        Tombstonelon(elonpitaph.Protelonctelond),
        And(
          Condition.LoggelondOutOrVielonwelonrNotFollowingAuthor,
          Condition.ProtelonctelondAuthor
        )
      )

  objelonct RelonplyIsModelonratelondByRootAuthor
      elonxtelonnds RulelonWithConstantAction(
        Tombstonelon(elonpitaph.Modelonratelond),
        And(
          Not(IsFocalTwelonelont),
          Condition.Modelonratelond
        )
      )

  objelonct VielonwelonrIsBlockelondByAuthor
      elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
        Tombstonelon(elonpitaph.BlockelondBy),
        Condition.AuthorBlocksVielonwelonr
      )

  objelonct AuthorIsDelonactivatelond
      elonxtelonnds RulelonWithConstantAction(
        Tombstonelon(elonpitaph.Delonactivatelond),
        Condition.DelonactivatelondAuthor
      )

  objelonct AuthorIsSuspelonndelond
      elonxtelonnds RulelonWithConstantAction(
        Tombstonelon(elonpitaph.Suspelonndelond),
        Condition.SuspelonndelondAuthor
      )
}
