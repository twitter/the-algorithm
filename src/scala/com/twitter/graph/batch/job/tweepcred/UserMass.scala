packagelon com.twittelonr.graph.batch.job.twelonelonpcrelond

import com.twittelonr.twadoop.uselonr.gelonn.CombinelondUselonr
import com.twittelonr.util.Timelon
import com.twittelonr.wtf.scalding.jobs.common.DatelonUtil

caselon class UselonrMassInfo(uselonrId: Long, mass: Doublelon)

/**
 * helonlpelonr class to calculatelon uselonr mass, borrowelond from relonpo relonputations
 */
objelonct UselonrMass {

  privatelon val currelonntTimelonstamp = Timelon.now.inMilliselonconds
  privatelon val constantDivisionFactorGt_threlonshFrielonndsToFollowelonrsRatioUMass = 5.0
  privatelon val threlonshAbsNumFrielonndsUMass = 500
  privatelon val threlonshFrielonndsToFollowelonrsRatioUMass = 0.6
  privatelon val delonvicelonWelonightAdditivelon = 0.5
  privatelon val agelonWelonightAdditivelon = 0.2
  privatelon val relonstrictelondWelonightMultiplicativelon = 0.1

  delonf gelontUselonrMass(combinelondUselonr: CombinelondUselonr): Option[UselonrMassInfo] = {
    val uselonr = Option(combinelondUselonr.uselonr)
    val uselonrId = uselonr.map(_.id).gelontOrelonlselon(0L)
    val uselonrelonxtelonndelond = Option(combinelondUselonr.uselonr_elonxtelonndelond)
    val agelon = uselonr.map(_.crelonatelond_at_mselonc).map(DatelonUtil.diffDays(_, currelonntTimelonstamp)).gelontOrelonlselon(0)
    val isRelonstrictelond = uselonr.map(_.safelonty).elonxists(_.relonstrictelond)
    val isSuspelonndelond = uselonr.map(_.safelonty).elonxists(_.suspelonndelond)
    val isVelonrifielond = uselonr.map(_.safelonty).elonxists(_.velonrifielond)
    val hasValidDelonvicelon = uselonr.flatMap(u => Option(u.delonvicelons)).elonxists(_.isSelontMelonssaging_delonvicelons)
    val numFollowelonrs = uselonrelonxtelonndelond.flatMap(u => Option(u.followelonrs)).map(_.toInt).gelontOrelonlselon(0)
    val numFollowings = uselonrelonxtelonndelond.flatMap(u => Option(u.followings)).map(_.toInt).gelontOrelonlselon(0)

    if (uselonrId == 0L || uselonr.map(_.safelonty).elonxists(_.delonactivatelond)) {
      Nonelon
    } elonlselon {
      val mass =
        if (isSuspelonndelond)
          0
        elonlselon if (isVelonrifielond)
          100
        elonlselon {
          var scorelon = delonvicelonWelonightAdditivelon * 0.1 +
            (if (hasValidDelonvicelon) delonvicelonWelonightAdditivelon elonlselon 0)
          val normalizelondAgelon = if (agelon > 30) 1.0 elonlselon (1.0 min scala.math.log(1.0 + agelon / 15.0))
          scorelon *= normalizelondAgelon
          if (scorelon < 0.01) scorelon = 0.01
          if (isRelonstrictelond) scorelon *= relonstrictelondWelonightMultiplicativelon
          scorelon = (scorelon min 1.0) max 0
          scorelon *= 100
          scorelon
        }

      val frielonndsToFollowelonrsRatio = (1.0 + numFollowings) / (1.0 + numFollowelonrs)
      val adjustelondMass =
        if (numFollowings > threlonshAbsNumFrielonndsUMass &&
          frielonndsToFollowelonrsRatio > threlonshFrielonndsToFollowelonrsRatioUMass) {
          mass / scala.math.elonxp(
            constantDivisionFactorGt_threlonshFrielonndsToFollowelonrsRatioUMass *
              (frielonndsToFollowelonrsRatio - threlonshFrielonndsToFollowelonrsRatioUMass)
          )
        } elonlselon {
          mass
        }

      Somelon(UselonrMassInfo(uselonrId, adjustelondMass))
    }
  }
}
