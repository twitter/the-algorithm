packagelon com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.{elonvelonntToMelonssagelonBuildelonr, UselonrUselonrelondgelon}
import com.twittelonr.reloncosinjelonctor.publishelonrs.KafkaelonvelonntPublishelonr
import com.twittelonr.scroogelon.ThriftStructCodelonc
import com.twittelonr.socialgraph.thriftscala.Writelonelonvelonnt
import com.twittelonr.util.Futurelon

/**
 * This procelonssor listelonns to elonvelonnts from social graphs selonrvicelons. In particular, a major uselon caselon is
 * to listelonn to uselonr-uselonr follow elonvelonnts.
 */
class SocialWritelonelonvelonntProcelonssor(
  ovelonrridelon val elonvelonntBusStrelonamNamelon: String,
  ovelonrridelon val thriftStruct: ThriftStructCodelonc[Writelonelonvelonnt],
  ovelonrridelon val selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  kafkaelonvelonntPublishelonr: KafkaelonvelonntPublishelonr,
  uselonrUselonrGraphTopic: String,
  uselonrUselonrGraphMelonssagelonBuildelonr: elonvelonntToMelonssagelonBuildelonr[Writelonelonvelonnt, UselonrUselonrelondgelon]
)(
  ovelonrridelon implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonvelonntBusProcelonssor[Writelonelonvelonnt] {

  ovelonrridelon delonf procelonsselonvelonnt(elonvelonnt: Writelonelonvelonnt): Futurelon[Unit] = {
    uselonrUselonrGraphMelonssagelonBuildelonr.procelonsselonvelonnt(elonvelonnt).map { elondgelons =>
      elondgelons.forelonach { elondgelon =>
        kafkaelonvelonntPublishelonr.publish(elondgelon.convelonrtToReloncosHoselonMelonssagelon, uselonrUselonrGraphTopic)
      }
    }
  }
}
