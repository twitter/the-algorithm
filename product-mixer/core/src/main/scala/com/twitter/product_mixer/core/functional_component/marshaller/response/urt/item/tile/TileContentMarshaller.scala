packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.tilelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.CallToActionTilelonContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.StandardTilelonContelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.tilelon.TilelonContelonnt
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TilelonContelonntMarshallelonr @Injelonct() (
  standardTilelonContelonntMarshallelonr: StandardTilelonContelonntMarshallelonr,
  callToActionTilelonContelonntMarshallelonr: CallToActionTilelonContelonntMarshallelonr) {

  delonf apply(tilelonContelonnt: TilelonContelonnt): urt.TilelonContelonnt = tilelonContelonnt match {
    caselon tilelonCont: StandardTilelonContelonnt =>
      urt.TilelonContelonnt.Standard(standardTilelonContelonntMarshallelonr(tilelonCont))
    caselon tilelonCont: CallToActionTilelonContelonnt =>
      urt.TilelonContelonnt.CallToAction(callToActionTilelonContelonntMarshallelonr(tilelonCont))
  }
}
