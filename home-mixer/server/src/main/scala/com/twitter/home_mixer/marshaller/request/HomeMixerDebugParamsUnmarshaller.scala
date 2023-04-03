packagelon com.twittelonr.homelon_mixelonr.marshallelonr.relonquelonst

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HomelonMixelonrDelonbugOptions
import com.twittelonr.homelon_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonquelonst.FelonaturelonValuelonUnmarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.DelonbugParams
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HomelonMixelonrDelonbugParamsUnmarshallelonr @Injelonct() (
  felonaturelonValuelonUnmarshallelonr: FelonaturelonValuelonUnmarshallelonr) {

  delonf apply(delonbugParams: t.DelonbugParams): DelonbugParams = {
    DelonbugParams(
      felonaturelonOvelonrridelons = delonbugParams.felonaturelonOvelonrridelons.map { map =>
        map.mapValuelons(felonaturelonValuelonUnmarshallelonr(_)).toMap
      },
      delonbugOptions = delonbugParams.delonbugOptions.map { options =>
        HomelonMixelonrDelonbugOptions(
          relonquelonstTimelonOvelonrridelon = options.relonquelonstTimelonOvelonrridelonMillis.map(Timelon.fromMilliselonconds)
        )
      }
    )
  }
}
