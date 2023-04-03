packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.graphql.contelonxtual_relonf

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.rtf.safelonty_lelonvelonl.SafelontyLelonvelonlMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.contelonxtual_relonf.TwelonelontHydrationContelonxt
import com.twittelonr.strato.graphql.contelonxtual_relonfs.{thriftscala => thrift}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontHydrationContelonxtMarshallelonr @Injelonct() (
  safelontyLelonvelonlMarshallelonr: SafelontyLelonvelonlMarshallelonr,
  outelonrTwelonelontContelonxtMarshallelonr: OutelonrTwelonelontContelonxtMarshallelonr) {

  delonf apply(twelonelontHydrationContelonxt: TwelonelontHydrationContelonxt): thrift.TwelonelontHydrationContelonxt =
    thrift.TwelonelontHydrationContelonxt(
      safelontyLelonvelonlOvelonrridelon = twelonelontHydrationContelonxt.safelontyLelonvelonlOvelonrridelon.map(safelontyLelonvelonlMarshallelonr(_)),
      outelonrTwelonelontContelonxt =
        twelonelontHydrationContelonxt.outelonrTwelonelontContelonxt.map(outelonrTwelonelontContelonxtMarshallelonr(_))
    )
}
