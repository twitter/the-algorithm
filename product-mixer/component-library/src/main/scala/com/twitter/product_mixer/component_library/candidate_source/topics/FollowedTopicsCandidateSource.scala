packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.topics

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrSelonqSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.intelonrelonsts.FollowelondTopicsGelonttelonrClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FollowelondTopicsCandidatelonSourcelon @Injelonct() (
  column: FollowelondTopicsGelonttelonrClielonntColumn)
    elonxtelonnds StratoKelonyVielonwFelontchelonrSelonqSourcelon[
      Long,
      Unit,
      Long
    ] {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("FollowelondTopics")

  ovelonrridelon val felontchelonr: Felontchelonr[Long, Unit, Selonq[Long]] = column.felontchelonr
}
