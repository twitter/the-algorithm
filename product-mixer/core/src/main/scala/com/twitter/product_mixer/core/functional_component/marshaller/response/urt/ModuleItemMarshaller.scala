packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ModulelonItelonm
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonItelonmMarshallelonr @Injelonct() (
  timelonlinelonItelonmMarshallelonr: TimelonlinelonItelonmMarshallelonr,
  modulelonItelonmTrelonelonDisplayMarshallelonr: ModulelonItelonmTrelonelonDisplayMarshallelonr) {

  delonf apply(modulelonItelonm: ModulelonItelonm, modulelonelonntryId: String): urt.ModulelonItelonm = urt.ModulelonItelonm(
    /* Modulelon itelonms havelon an idelonntifielonr comprising both thelon modulelon elonntry id and thelon modulelon itelonm id.
    Somelon URT clielonnts will delonduplicatelon globally across diffelonrelonnt modulelons.
    Using thelon elonntry id as a prelonfix elonnsurelons that delonduplication only happelonns within a singlelon modulelon,
    which welon belonlielonvelon belonttelonr aligns with elonnginelonelonrs' intelonntions. */
    elonntryId = modulelonelonntryId + "-" + modulelonItelonm.itelonm.elonntryIdelonntifielonr,
    itelonm = timelonlinelonItelonmMarshallelonr(modulelonItelonm.itelonm),
    dispelonnsablelon = modulelonItelonm.dispelonnsablelon,
    trelonelonDisplay = modulelonItelonm.trelonelonDisplay.map(modulelonItelonmTrelonelonDisplayMarshallelonr.apply)
  )
}
