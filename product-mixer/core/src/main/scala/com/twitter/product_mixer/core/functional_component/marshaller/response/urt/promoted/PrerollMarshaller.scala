packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.Prelonroll
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PrelonrollMarshallelonr @Injelonct() (
  dynamicPrelonrollTypelonMarshallelonr: DynamicPrelonrollTypelonMarshallelonr,
  melondiaInfoMarshallelonr: MelondiaInfoMarshallelonr) {

  delonf apply(prelonroll: Prelonroll): urt.Prelonroll =
    urt.Prelonroll(
      prelonrollId = prelonroll.prelonrollId,
      dynamicPrelonrollTypelon = prelonroll.dynamicPrelonrollTypelon.map(dynamicPrelonrollTypelonMarshallelonr(_)),
      melondiaInfo = prelonroll.melondiaInfo.map(melondiaInfoMarshallelonr(_))
    )
}
