packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.slicelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransportMarshallelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.Slicelon
import com.twittelonr.strato.graphql.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SlicelonTransportMarshallelonr @Injelonct() (slicelonItelonmMarshallelonr: SlicelonItelonmMarshallelonr)
    elonxtelonnds TransportMarshallelonr[Slicelon, t.SlicelonRelonsult] {

  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr = TransportMarshallelonrIdelonntifielonr("Slicelon")

  ovelonrridelon delonf apply(slicelon: Slicelon): t.SlicelonRelonsult = {
    t.SlicelonRelonsult.Slicelon(
      t.Slicelon(
        itelonms = slicelon.itelonms.map(slicelonItelonmMarshallelonr(_)),
        slicelonInfo = t.SlicelonInfo(
          prelonviousCursor = slicelon.slicelonInfo.prelonviousCursor,
          nelonxtCursor = slicelon.slicelonInfo.nelonxtCursor
        )
      ))
  }
}
