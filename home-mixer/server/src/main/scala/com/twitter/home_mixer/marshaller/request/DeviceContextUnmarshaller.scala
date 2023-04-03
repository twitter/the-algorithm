packagelon com.twittelonr.homelon_mixelonr.marshallelonr.relonquelonst

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class DelonvicelonContelonxtUnmarshallelonr @Injelonct() () {

  delonf apply(delonvicelonContelonxt: t.DelonvicelonContelonxt): DelonvicelonContelonxt = {
    DelonvicelonContelonxt(
      isPolling = delonvicelonContelonxt.isPolling,
      relonquelonstContelonxt = delonvicelonContelonxt.relonquelonstContelonxt,
      latelonstControlAvailablelon = delonvicelonContelonxt.latelonstControlAvailablelon,
      autoplayelonnablelond = delonvicelonContelonxt.autoplayelonnablelond
    )
  }
}
