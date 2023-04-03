packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tilelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata.BadgelonMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.StandardTilelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class StandardTilelonContelonntMarshallelonr @Injelonct() (
  badgelonMarshallelonr: BadgelonMarshallelonr) {

  delonf apply(standardTilelonContelonnt: StandardTilelonContelonnt): urt.TilelonContelonntStandard =
    urt.TilelonContelonntStandard(
      titlelon = standardTilelonContelonnt.titlelon,
      supportingTelonxt = standardTilelonContelonnt.supportingTelonxt,
      badgelon = standardTilelonContelonnt.badgelon.map(badgelonMarshallelonr(_))
    )
}
