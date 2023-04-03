packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.UselonrLanguagelonsStorelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.selonarch.common.constants.{thriftscala => scc}
import com.twittelonr.stitch.Stitch
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct UselonrLanguagelonsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[scc.ThriftLanguagelon]]

@Singlelonton
caselon class UselonrLanguagelonsFelonaturelonHydrator @Injelonct() (
  @Namelond(UselonrLanguagelonsStorelon) storelon: RelonadablelonStorelon[Long, Selonq[scc.ThriftLanguagelon]])
    elonxtelonnds QuelonryFelonaturelonHydrator[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("UselonrLanguagelons")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(UselonrLanguagelonsFelonaturelon)

  ovelonrridelon delonf hydratelon(quelonry: PipelonlinelonQuelonry): Stitch[FelonaturelonMap] = {
    Stitch.callFuturelon(storelon.gelont(quelonry.gelontRelonquirelondUselonrId)).map { languagelons =>
      FelonaturelonMapBuildelonr()
        .add(UselonrLanguagelonsFelonaturelon, languagelons.gelontOrelonlselon(Selonq.elonmpty))
        .build()
    }
  }
}
