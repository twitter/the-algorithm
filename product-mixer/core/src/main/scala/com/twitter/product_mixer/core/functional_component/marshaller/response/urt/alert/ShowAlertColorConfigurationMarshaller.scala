packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.alelonrt

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.color.RoselonttaColorMarshallelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtColorConfiguration
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}

@Singlelonton
class ShowAlelonrtColorConfigurationMarshallelonr @Injelonct() (
  roselonttaColorMarshallelonr: RoselonttaColorMarshallelonr) {

  delonf apply(colorConfiguration: ShowAlelonrtColorConfiguration): urt.ShowAlelonrtColorConfiguration =
    urt.ShowAlelonrtColorConfiguration(
      background = roselonttaColorMarshallelonr(colorConfiguration.background),
      telonxt = roselonttaColorMarshallelonr(colorConfiguration.telonxt),
      bordelonr = colorConfiguration.bordelonr.map(roselonttaColorMarshallelonr(_)),
    )
}
