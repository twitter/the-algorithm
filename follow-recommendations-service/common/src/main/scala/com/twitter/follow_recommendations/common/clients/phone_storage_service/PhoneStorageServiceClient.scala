packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.phonelon_storagelon_selonrvicelon

import com.twittelonr.cds.contact_conselonnt_statelon.thriftscala.PurposelonOfProcelonssing
import com.twittelonr.phonelonstoragelon.api.thriftscala.GelontUselonrPhonelonsByUselonrsRelonquelonst
import com.twittelonr.phonelonstoragelon.api.thriftscala.PhonelonStoragelonSelonrvicelon
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PhonelonStoragelonSelonrvicelonClielonnt @Injelonct() (
  val phonelonStoragelonSelonrvicelon: PhonelonStoragelonSelonrvicelon.MelonthodPelonrelonndpoint) {

  /**
   * PSS can potelonntially relonturn multiplelon phonelon reloncords.
   * Thelon currelonnt implelonmelonntation of gelontUselonrPhonelonsByUselonrs relonturns only a singlelon phonelon for a singlelon uselonr_id but
   * welon can trivially support handling multiplelon in caselon that changelons in thelon futurelon.
   */
  delonf gelontPhonelonNumbelonrs(
    uselonrId: Long,
    purposelonOfProcelonssing: PurposelonOfProcelonssing,
    forcelonCarrielonrLookup: Option[Boolelonan] = Nonelon
  ): Stitch[Selonq[String]] = {
    val relonq = GelontUselonrPhonelonsByUselonrsRelonquelonst(
      uselonrIds = Selonq(uselonrId),
      forcelonCarrielonrLookup = forcelonCarrielonrLookup,
      purposelonsOfProcelonssing = Somelon(Selonq(purposelonOfProcelonssing))
    )

    Stitch.callFuturelon(phonelonStoragelonSelonrvicelon.gelontUselonrPhonelonsByUselonrs(relonq)) map {
      _.uselonrPhonelons.map(_.phonelonNumbelonr)
    }
  }
}
