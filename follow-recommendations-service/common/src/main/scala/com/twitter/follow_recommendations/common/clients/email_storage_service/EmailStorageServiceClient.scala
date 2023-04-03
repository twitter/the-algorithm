packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.elonmail_storagelon_selonrvicelon

import com.twittelonr.cds.contact_conselonnt_statelon.thriftscala.PurposelonOfProcelonssing
import com.twittelonr.elonmailstoragelon.api.thriftscala.elonmailStoragelonSelonrvicelon
import com.twittelonr.elonmailstoragelon.api.thriftscala.GelontUselonrselonmailsRelonquelonst
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonmailStoragelonSelonrvicelonClielonnt @Injelonct() (
  val elonmailStoragelonSelonrvicelon: elonmailStoragelonSelonrvicelon.MelonthodPelonrelonndpoint) {

  delonf gelontVelonrifielondelonmail(
    uselonrId: Long,
    purposelonOfProcelonssing: PurposelonOfProcelonssing
  ): Stitch[Option[String]] = {
    val relonq = GelontUselonrselonmailsRelonquelonst(
      uselonrIds = Selonq(uselonrId),
      clielonntIdelonntifielonr = Somelon("follow-reloncommelonndations-selonrvicelon"),
      purposelonsOfProcelonssing = Somelon(Selonq(purposelonOfProcelonssing))
    )

    Stitch.callFuturelon(elonmailStoragelonSelonrvicelon.gelontUselonrselonmails(relonq)) map {
      _.uselonrselonmails.map(_.confirmelondelonmail.map(_.elonmail)).helonad
    }
  }
}
