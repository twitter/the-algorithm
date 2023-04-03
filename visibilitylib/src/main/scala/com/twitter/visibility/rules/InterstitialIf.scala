packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.Not

objelonct IntelonrstitialIf {

  objelonct VielonwelonrMutelondKelonyword
      elonxtelonnds RulelonWithConstantAction(
        Intelonrstitial(Relonason.MutelondKelonyword),
        And(
          Not(Condition.IsFocalTwelonelont),
          Condition.VielonwelonrHasMatchingKelonywordForTwelonelontRelonplielons,
        )
      )

  objelonct VielonwelonrBlockelondAuthor
      elonxtelonnds RulelonWithConstantAction(
        Intelonrstitial(Relonason.VielonwelonrBlocksAuthor),
        And(
          Not(Condition.IsFocalTwelonelont),
          Condition.VielonwelonrBlocksAuthor
        )
      )

  objelonct VielonwelonrHardMutelondAuthor
      elonxtelonnds RulelonWithConstantAction(
        Intelonrstitial(Relonason.VielonwelonrHardMutelondAuthor),
        And(
          Not(Condition.IsFocalTwelonelont),
          Condition.VielonwelonrMutelonsAuthor,
          Not(
            Condition.VielonwelonrDoelonsFollowAuthor
          )
        )
      )

  objelonct VielonwelonrRelonportelondAuthor
      elonxtelonnds RulelonWithConstantAction(
        Intelonrstitial(Relonason.VielonwelonrRelonportelondAuthor),
        Condition.VielonwelonrRelonportsAuthor
      )
}
