packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrtelonndpointOptions
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class UrtelonndpointOptionsMarshallelonr @Injelonct() () {

  delonf apply(urtelonndpointOptions: UrtelonndpointOptions): urt.UrtelonndpointOptions =
    urt.UrtelonndpointOptions(
      relonquelonstParams = urtelonndpointOptions.relonquelonstParams,
      titlelon = urtelonndpointOptions.titlelon,
      cachelonId = urtelonndpointOptions.cachelonId,
      subtitlelon = urtelonndpointOptions.subtitlelon
    )
}
