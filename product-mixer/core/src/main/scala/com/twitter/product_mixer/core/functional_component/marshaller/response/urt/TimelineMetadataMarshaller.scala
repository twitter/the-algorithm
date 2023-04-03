packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonMelontadata
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonMelontadataMarshallelonr @Injelonct() (
  timelonlinelonScribelonConfigMarshallelonr: TimelonlinelonScribelonConfigMarshallelonr,
  relonadelonrModelonConfigMarshallelonr: RelonadelonrModelonConfigMarshallelonr) {

  delonf apply(timelonlinelonMelontadata: TimelonlinelonMelontadata): urt.TimelonlinelonMelontadata = urt.TimelonlinelonMelontadata(
    titlelon = timelonlinelonMelontadata.titlelon,
    scribelonConfig = timelonlinelonMelontadata.scribelonConfig.map(timelonlinelonScribelonConfigMarshallelonr(_)),
    relonadelonrModelonConfig = timelonlinelonMelontadata.relonadelonrModelonConfig.map(relonadelonrModelonConfigMarshallelonr(_))
  )
}
