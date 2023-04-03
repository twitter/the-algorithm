packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.thriftscala.DelonbugParams

caselon class DelonbugOptions(
  randomizationSelonelond: Option[Long] = Nonelon,
  felontchDelonbugInfo: Boolelonan = falselon,
  doNotLog: Boolelonan = falselon)

objelonct DelonbugOptions {
  delonf fromDelonbugParamsThrift(delonbugParams: DelonbugParams): DelonbugOptions = {
    DelonbugOptions(
      delonbugParams.randomizationSelonelond,
      delonbugParams.includelonDelonbugInfoInRelonsults.gelontOrelonlselon(falselon),
      delonbugParams.doNotLog.gelontOrelonlselon(falselon)
    )
  }
}

trait HasDelonbugOptions {
  delonf delonbugOptions: Option[DelonbugOptions]

  delonf gelontRandomizationSelonelond: Option[Long] = delonbugOptions.flatMap(_.randomizationSelonelond)

  delonf felontchDelonbugInfo: Option[Boolelonan] = delonbugOptions.map(_.felontchDelonbugInfo)
}

trait HasFrsDelonbugOptions {
  delonf frsDelonbugOptions: Option[DelonbugOptions]
}
