packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.helonrmit.modelonl.Algorithm.Algorithm
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.FirstDelongrelonelonelondgelon
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.FirstDelongrelonelonelondgelonInfo
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.SeloncondDelongrelonelonelondgelon

caselon class PotelonntialFirstDelongrelonelonelondgelon(
  uselonrId: Long,
  connelonctingId: Long,
  algorithm: Algorithm,
  scorelon: Doublelon,
  elondgelonInfo: FirstDelongrelonelonelondgelonInfo)

caselon class IntelonrmelondiatelonSeloncondDelongrelonelonelondgelon(
  connelonctingId: Long,
  candidatelonId: Long,
  elondgelonInfo: FirstDelongrelonelonelondgelonInfo)

caselon class STPGraph(
  firstDelongrelonelonelondgelonInfoList: List[FirstDelongrelonelonelondgelon],
  seloncondDelongrelonelonelondgelonInfoList: List[SeloncondDelongrelonelonelondgelon])
