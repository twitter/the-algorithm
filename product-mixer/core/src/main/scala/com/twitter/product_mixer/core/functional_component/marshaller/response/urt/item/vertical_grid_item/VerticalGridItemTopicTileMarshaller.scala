packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.velonrtical_grid_itelonm

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.UrlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.velonrtical_grid_itelonm.VelonrticalGridItelonmTopicTilelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class VelonrticalGridItelonmTopicTilelonMarshallelonr @Injelonct() (
  stylelonMarshallelonr: VelonrticalGridItelonmTilelonStylelonMarshallelonr,
  functionalityTypelonMarshallelonr: VelonrticalGridItelonmTopicFunctionalityTypelonMarshallelonr,
  urlMarshallelonr: UrlMarshallelonr) {

  delonf apply(velonrticalGridItelonmTopicTilelon: VelonrticalGridItelonmTopicTilelon): urt.VelonrticalGridItelonmContelonnt =
    urt.VelonrticalGridItelonmContelonnt.TopicTilelon(
      urt.VelonrticalGridItelonmTopicTilelon(
        topicId = velonrticalGridItelonmTopicTilelon.id.toString,
        stylelon = velonrticalGridItelonmTopicTilelon.stylelon
          .map(stylelonMarshallelonr(_)).gelontOrelonlselon(urt.VelonrticalGridItelonmTilelonStylelon.SinglelonStatelonDelonfault),
        functionalityTypelon = velonrticalGridItelonmTopicTilelon.functionalityTypelon
          .map(functionalityTypelonMarshallelonr(_)).gelontOrelonlselon(
            urt.VelonrticalGridItelonmTopicFunctionalityTypelon.Pivot),
        url = velonrticalGridItelonmTopicTilelon.url.map(urlMarshallelonr(_))
      )
    )

}
