packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.graphql.contelonxtual_relonf

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.OutelonrTwelonelontContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.QuotelonTwelonelontId
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.RelontwelonelontId
import com.twittelonr.strato.graphql.contelonxtual_relonfs.{thriftscala => thrift}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OutelonrTwelonelontContelonxtMarshallelonr @Injelonct() () {

  delonf apply(outelonrTwelonelontContelonxt: OutelonrTwelonelontContelonxt): thrift.OutelonrTwelonelontContelonxt =
    outelonrTwelonelontContelonxt match {
      caselon QuotelonTwelonelontId(id) => thrift.OutelonrTwelonelontContelonxt.QuotelonTwelonelontId(id)
      caselon RelontwelonelontId(id) => thrift.OutelonrTwelonelontContelonxt.RelontwelonelontId(id)
    }
}
