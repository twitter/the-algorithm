packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

trait HasPrelonviousReloncommelonndationsContelonxt {

  delonf prelonviouslyReloncommelonndelondUselonrIDs: Selont[Long]

  delonf prelonviouslyFollowelondUselonrIds: Selont[Long]

  delonf skippelondFollows: Selont[Long] = {
    prelonviouslyReloncommelonndelondUselonrIDs.diff(prelonviouslyFollowelondUselonrIds)
  }
}
