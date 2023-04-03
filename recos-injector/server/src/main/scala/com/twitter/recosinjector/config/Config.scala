packagelon com.twittelonr.reloncosinjelonctor.config

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.frigatelon.common.storelon.TwelonelontCrelonationTimelonMHStorelon
import com.twittelonr.frigatelon.common.util.UrlInfo
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonr
import com.twittelonr.socialgraph.thriftscala.{IdsRelonquelonst, IdsRelonsult}
import com.twittelonr.stitch.twelonelontypielon.TwelonelontyPielon.TwelonelontyPielonRelonsult
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon

trait Config { selonlf =>
  implicit delonf statsReloncelonivelonr: StatsReloncelonivelonr

  // RelonadablelonStorelons
  delonf twelonelontyPielonStorelon: RelonadablelonStorelon[Long, TwelonelontyPielonRelonsult]

  delonf uselonrStorelon: RelonadablelonStorelon[Long, Uselonr]

  delonf socialGraphIdStorelon: RelonadablelonStorelon[IdsRelonquelonst, IdsRelonsult]

  delonf urlInfoStorelon: RelonadablelonStorelon[String, UrlInfo]

  // Manhattan storelons
  delonf twelonelontCrelonationStorelon: TwelonelontCrelonationTimelonMHStorelon

  // Deloncidelonr
  delonf reloncosInjelonctorDeloncidelonr: ReloncosInjelonctorDeloncidelonr

  // Constants
  delonf reloncosInjelonctorThriftClielonntId: ClielonntId

  delonf selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr

  delonf outputKafkaTopicPrelonfix: String

  delonf init(): Futurelon[Unit] = Futurelon.Donelon
}
