packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.timelonlinelons.configapi.DurationConvelonrsion
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.HasDurationConvelonrsion
import com.twittelonr.util.Duration

objelonct SocialProofelonnforcelondCandidatelonSourcelonParams {
  caselon objelonct MustCallSgs
      elonxtelonnds FSParam[Boolelonan]("social_proof_elonnforcelond_candidatelon_sourcelon_must_call_sgs", truelon)

  caselon objelonct CallSgsCachelondColumn
      elonxtelonnds FSParam[Boolelonan](
        "social_proof_elonnforcelond_candidatelon_sourcelon_call_sgs_cachelond_column",
        falselon)

  caselon objelonct QuelonryIntelonrselonctionIdsNum
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "social_proof_elonnforcelond_candidatelon_sourcelon_quelonry_intelonrselonction_ids_num",
        delonfault = 3,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct MaxNumCandidatelonsToAnnotatelon
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "social_proof_elonnforcelond_candidatelon_sourcelon_max_num_candidatelons_to_annotatelon",
        delonfault = 50,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct GfsIntelonrselonctionIdsNum
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "social_proof_elonnforcelond_candidatelon_sourcelon_gfs_intelonrselonction_ids_num",
        delonfault = 3,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct SgsIntelonrselonctionIdsNum
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "social_proof_elonnforcelond_candidatelon_sourcelon_sgs_intelonrselonction_ids_num",
        delonfault = 10,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  caselon objelonct GfsLagDurationInDays
      elonxtelonnds FSBoundelondParam[Duration](
        namelon = "social_proof_elonnforcelond_candidatelon_sourcelon_gfs_lag_duration_in_days",
        delonfault = 14.days,
        min = 1.days,
        max = 60.days)
      with HasDurationConvelonrsion {
    ovelonrridelon val durationConvelonrsion: DurationConvelonrsion = DurationConvelonrsion.FromDays
  }
}
