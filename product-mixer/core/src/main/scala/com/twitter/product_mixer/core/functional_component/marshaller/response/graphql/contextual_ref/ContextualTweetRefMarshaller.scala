packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.graphql.contelonxtual_relonf

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.ContelonxtualTwelonelontRelonf
import com.twittelonr.strato.graphql.contelonxtual_relonfs.{thriftscala => thrift}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ContelonxtualTwelonelontRelonfMarshallelonr @Injelonct() (
  twelonelontHydrationContelonxtMarshallelonr: TwelonelontHydrationContelonxtMarshallelonr) {

  delonf apply(contelonxtualTwelonelontRelonf: ContelonxtualTwelonelontRelonf): thrift.ContelonxtualTwelonelontRelonf =
    thrift.ContelonxtualTwelonelontRelonf(
      id = contelonxtualTwelonelontRelonf.id,
      hydrationContelonxt =
        contelonxtualTwelonelontRelonf.hydrationContelonxt.map(twelonelontHydrationContelonxtMarshallelonr(_)))
}
