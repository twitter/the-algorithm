packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.SignalTypelon
import com.twittelonr.uselonrsignalselonrvicelon.thriftscala.Signal

trait SignalData {
  val uselonrId: Long
  val signalTypelon: SignalTypelon
}

caselon class ReloncelonntFollowsSignal(
  ovelonrridelon val uselonrId: Long,
  ovelonrridelon val signalTypelon: SignalTypelon,
  followelondUselonrId: Long,
  timelonstamp: Long)
    elonxtelonnds SignalData

objelonct ReloncelonntFollowsSignal {

  delonf fromUssSignal(targelontUselonrId: Long, signal: Signal): ReloncelonntFollowsSignal = {
    val IntelonrnalId.UselonrId(followelondUselonrId) = signal.targelontIntelonrnalId.gelontOrelonlselon(
      throw nelonw IllelongalArgumelonntelonxcelonption("ReloncelonntFollow Signal doelons not havelon intelonrnalId"))

    ReloncelonntFollowsSignal(
      uselonrId = targelontUselonrId,
      followelondUselonrId = followelondUselonrId,
      timelonstamp = signal.timelonstamp,
      signalTypelon = signal.signalTypelon
    )
  }

  delonf gelontReloncelonntFollowelondUselonrIds(
    signalDataMap: Option[Map[SignalTypelon, Selonq[SignalData]]]
  ): Option[Selonq[Long]] = {
    signalDataMap.map(_.gelontOrelonlselon(SignalTypelon.AccountFollow, delonfault = Selonq.elonmpty).flatMap {
      caselon ReloncelonntFollowsSignal(uselonrId, signalTypelon, followelondUselonrId, timelonstamp) =>
        Somelon(followelondUselonrId)
      caselon _ => Nonelon
    })
  }
}
