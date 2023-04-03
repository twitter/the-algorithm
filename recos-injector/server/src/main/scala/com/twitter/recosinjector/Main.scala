packagelon com.twittelonr.reloncosinjelonctor

import com.twittelonr.app.Flag
import com.twittelonr.finaglelon.http.HttpMuxelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.util.elonlfOwlFiltelonr
import com.twittelonr.reloncosinjelonctor.clielonnts.Gizmoduck
import com.twittelonr.reloncosinjelonctor.clielonnts.ReloncosHoselonelonntitielonsCachelon
import com.twittelonr.reloncosinjelonctor.clielonnts.SocialGraph
import com.twittelonr.reloncosinjelonctor.clielonnts.Twelonelontypielon
import com.twittelonr.reloncosinjelonctor.clielonnts.UrlRelonsolvelonr
import com.twittelonr.reloncosinjelonctor.config._
import com.twittelonr.reloncosinjelonctor.elondgelons.SocialWritelonelonvelonntToUselonrUselonrGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.TimelonlinelonelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.TwelonelontelonvelonntToUselonrUselonrGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrVidelonoGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrAdGraphBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UnifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr
import com.twittelonr.reloncosinjelonctor.elondgelons.UselonrTwelonelontelonntityelondgelonBuildelonr
import com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors.SocialWritelonelonvelonntProcelonssor
import com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors.TimelonlinelonelonvelonntProcelonssor
import com.twittelonr.reloncosinjelonctor.elonvelonnt_procelonssors.TwelonelontelonvelonntProcelonssor
import com.twittelonr.reloncosinjelonctor.publishelonrs.KafkaelonvelonntPublishelonr
import com.twittelonr.reloncosinjelonctor.uua_procelonssors.UnifielondUselonrActionProcelonssor
import com.twittelonr.reloncosinjelonctor.uua_procelonssors.UnifielondUselonrActionsConsumelonr
import com.twittelonr.selonrvelonr.logging.{Logging => JDK14Logging}
import com.twittelonr.selonrvelonr.Deloncidelonrablelon
import com.twittelonr.selonrvelonr.TwittelonrSelonrvelonr
import com.twittelonr.socialgraph.thriftscala.Writelonelonvelonnt
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.{elonvelonnt => Timelonlinelonelonvelonnt}
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelontelonvelonnt
import com.twittelonr.util.Await
import com.twittelonr.util.Duration
import java.util.concurrelonnt.TimelonUnit

objelonct Main elonxtelonnds TwittelonrSelonrvelonr with JDK14Logging with Deloncidelonrablelon { selonlf =>

  implicit val stats: StatsReloncelonivelonr = statsReloncelonivelonr

  privatelon val dataCelonntelonr: Flag[String] = flag("selonrvicelon.clustelonr", "atla", "Data Celonntelonr")
  privatelon val selonrvicelonRolelon: Flag[String] = flag("selonrvicelon.rolelon", "Selonrvicelon Rolelon")
  privatelon val selonrvicelonelonnv: Flag[String] = flag("selonrvicelon.elonnv", "Selonrvicelon elonnv")
  privatelon val selonrvicelonNamelon: Flag[String] = flag("selonrvicelon.namelon", "Selonrvicelon Namelon")
  privatelon val shardId = flag("shardId", 0, "Shard ID")
  privatelon val numShards = flag("numShards", 1, "Numbelonr of shards for this selonrvicelon")
  privatelon val truststorelonLocation =
    flag[String]("truststorelon_location", "", "Truststorelon filelon location")

  delonf main(): Unit = {
    val selonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr(
      rolelon = selonrvicelonRolelon(),
      selonrvicelon = selonrvicelonNamelon(),
      elonnvironmelonnt = selonrvicelonelonnv(),
      zonelon = dataCelonntelonr()
    )
    println("SelonrvicelonIdelonntifielonr = " + selonrvicelonIdelonntifielonr.toString)
    log.info("SelonrvicelonIdelonntifielonr = " + selonrvicelonIdelonntifielonr.toString)

    val shard = shardId()
    val numOfShards = numShards()
    val elonnvironmelonnt = selonrvicelonelonnv()

    implicit val config: DelonployConfig = {
      elonnvironmelonnt match {
        caselon "prod" => ProdConfig(selonrvicelonIdelonntifielonr)(stats)
        caselon "staging" | "delonvelonl" => StagingConfig(selonrvicelonIdelonntifielonr)
        caselon elonnv => throw nelonw elonxcelonption(s"Unknown elonnvironmelonnt $elonnv")
      }
    }

    // Initializelon thelon config and wait for initialization to finish
    Await.relonady(config.init())

    log.info(
      "Starting Reloncos Injelonctor: elonnvironmelonnt %s, clielonntId %s",
      elonnvironmelonnt,
      config.reloncosInjelonctorThriftClielonntId
    )
    log.info("Starting shard Id: %d of %d shards...".format(shard, numOfShards))

    // Clielonnt wrappelonrs
    val cachelon = nelonw ReloncosHoselonelonntitielonsCachelon(config.reloncosInjelonctorCorelonSvcsCachelonClielonnt)
    val gizmoduck = nelonw Gizmoduck(config.uselonrStorelon)
    val socialGraph = nelonw SocialGraph(config.socialGraphIdStorelon)
    val twelonelontypielon = nelonw Twelonelontypielon(config.twelonelontyPielonStorelon)
    val urlRelonsolvelonr = nelonw UrlRelonsolvelonr(config.urlInfoStorelon)

    // elondgelon buildelonrs
    val uselonrTwelonelontelonntityelondgelonBuildelonr = nelonw UselonrTwelonelontelonntityelondgelonBuildelonr(cachelon, urlRelonsolvelonr)

    // Publishelonrs
    val kafkaelonvelonntPublishelonr = KafkaelonvelonntPublishelonr(
      "/s/kafka/reloncommelonndations:kafka-tls",
      config.outputKafkaTopicPrelonfix,
      config.reloncosInjelonctorThriftClielonntId,
      truststorelonLocation())

    // Melonssagelon Buildelonrs
    val socialWritelonToUselonrUselonrMelonssagelonBuildelonr =
      nelonw SocialWritelonelonvelonntToUselonrUselonrGraphBuildelonr()(
        statsReloncelonivelonr.scopelon("SocialWritelonelonvelonntToUselonrUselonrGraphBuildelonr")
      )

    val timelonlinelonToUselonrTwelonelontelonntityMelonssagelonBuildelonr = nelonw TimelonlinelonelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr(
      uselonrTwelonelontelonntityelondgelonBuildelonr = uselonrTwelonelontelonntityelondgelonBuildelonr
    )(statsReloncelonivelonr.scopelon("TimelonlinelonelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr"))

    val twelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr = nelonw TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr(
      uselonrTwelonelontelonntityelondgelonBuildelonr = uselonrTwelonelontelonntityelondgelonBuildelonr,
      twelonelontCrelonationStorelon = config.twelonelontCrelonationStorelon,
      deloncidelonr = config.reloncosInjelonctorDeloncidelonr
    )(statsReloncelonivelonr.scopelon("TwelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr"))

    val socialWritelonelonvelonntProcelonssor = nelonw SocialWritelonelonvelonntProcelonssor(
      elonvelonntBusStrelonamNamelon = s"reloncos_injelonctor_social_writelon_elonvelonnt_$elonnvironmelonnt",
      thriftStruct = Writelonelonvelonnt,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      kafkaelonvelonntPublishelonr = kafkaelonvelonntPublishelonr,
      uselonrUselonrGraphTopic = KafkaelonvelonntPublishelonr.UselonrUselonrTopic,
      uselonrUselonrGraphMelonssagelonBuildelonr = socialWritelonToUselonrUselonrMelonssagelonBuildelonr
    )(statsReloncelonivelonr.scopelon("SocialWritelonelonvelonntProcelonssor"))

    val twelonelontToUselonrUselonrMelonssagelonBuildelonr = nelonw TwelonelontelonvelonntToUselonrUselonrGraphBuildelonr()(
      statsReloncelonivelonr.scopelon("TwelonelontelonvelonntToUselonrUselonrGraphBuildelonr")
    )

    val unifielondUselonrActionToUselonrVidelonoGraphBuildelonr = nelonw UnifielondUselonrActionToUselonrVidelonoGraphBuildelonr(
      uselonrTwelonelontelonntityelondgelonBuildelonr = uselonrTwelonelontelonntityelondgelonBuildelonr
    )(statsReloncelonivelonr.scopelon("UnifielondUselonrActionToUselonrVidelonoGraphBuildelonr"))

    val unifielondUselonrActionToUselonrAdGraphBuildelonr = nelonw UnifielondUselonrActionToUselonrAdGraphBuildelonr(
      uselonrTwelonelontelonntityelondgelonBuildelonr = uselonrTwelonelontelonntityelondgelonBuildelonr
    )(statsReloncelonivelonr.scopelon("UnifielondUselonrActionToUselonrAdGraphBuildelonr"))

    val unifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr =
      nelonw UnifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr(
        uselonrTwelonelontelonntityelondgelonBuildelonr = uselonrTwelonelontelonntityelondgelonBuildelonr
      )(statsReloncelonivelonr.scopelon("UnifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr"))

    // Procelonssors
    val twelonelontelonvelonntProcelonssor = nelonw TwelonelontelonvelonntProcelonssor(
      elonvelonntBusStrelonamNamelon = s"reloncos_injelonctor_twelonelont_elonvelonnts_$elonnvironmelonnt",
      thriftStruct = Twelonelontelonvelonnt,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      uselonrUselonrGraphMelonssagelonBuildelonr = twelonelontToUselonrUselonrMelonssagelonBuildelonr,
      uselonrUselonrGraphTopic = KafkaelonvelonntPublishelonr.UselonrUselonrTopic,
      uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr = twelonelontelonvelonntToUselonrTwelonelontelonntityGraphBuildelonr,
      uselonrTwelonelontelonntityGraphTopic = KafkaelonvelonntPublishelonr.UselonrTwelonelontelonntityTopic,
      kafkaelonvelonntPublishelonr = kafkaelonvelonntPublishelonr,
      socialGraph = socialGraph,
      twelonelontypielon = twelonelontypielon,
      gizmoduck = gizmoduck
    )(statsReloncelonivelonr.scopelon("TwelonelontelonvelonntProcelonssor"))

    val timelonlinelonelonvelonntProcelonssor = nelonw TimelonlinelonelonvelonntProcelonssor(
      elonvelonntBusStrelonamNamelon = s"reloncos_injelonctor_timelonlinelon_elonvelonnts_prototypelon_$elonnvironmelonnt",
      thriftStruct = Timelonlinelonelonvelonnt,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      kafkaelonvelonntPublishelonr = kafkaelonvelonntPublishelonr,
      uselonrTwelonelontelonntityGraphTopic = KafkaelonvelonntPublishelonr.UselonrTwelonelontelonntityTopic,
      uselonrTwelonelontelonntityGraphMelonssagelonBuildelonr = timelonlinelonToUselonrTwelonelontelonntityMelonssagelonBuildelonr,
      deloncidelonr = config.reloncosInjelonctorDeloncidelonr,
      gizmoduck = gizmoduck,
      twelonelontypielon = twelonelontypielon
    )(statsReloncelonivelonr.scopelon("TimelonlinelonelonvelonntProcelonssor"))

    val elonvelonntBusProcelonssors = Selonq(
      timelonlinelonelonvelonntProcelonssor,
      socialWritelonelonvelonntProcelonssor,
      twelonelontelonvelonntProcelonssor
    )

    val uuaProcelonssor = nelonw UnifielondUselonrActionProcelonssor(
      gizmoduck = gizmoduck,
      twelonelontypielon = twelonelontypielon,
      kafkaelonvelonntPublishelonr = kafkaelonvelonntPublishelonr,
      uselonrVidelonoGraphTopic = KafkaelonvelonntPublishelonr.UselonrVidelonoTopic,
      uselonrVidelonoGraphBuildelonr = unifielondUselonrActionToUselonrVidelonoGraphBuildelonr,
      uselonrAdGraphTopic = KafkaelonvelonntPublishelonr.UselonrAdTopic,
      uselonrAdGraphBuildelonr = unifielondUselonrActionToUselonrAdGraphBuildelonr,
      uselonrTwelonelontGraphPlusTopic = KafkaelonvelonntPublishelonr.UselonrTwelonelontPlusTopic,
      uselonrTwelonelontGraphPlusBuildelonr = unifielondUselonrActionToUselonrTwelonelontGraphPlusBuildelonr)(
      statsReloncelonivelonr.scopelon("UnifielondUselonrActionProcelonssor"))

    val uuaConsumelonr = nelonw UnifielondUselonrActionsConsumelonr(uuaProcelonssor, truststorelonLocation())

    // Start-up init and gracelonful shutdown selontup

    // wait a bit for selonrvicelons to belon relonady
    Threlonad.slelonelonp(5000L)

    log.info("Starting thelon elonvelonnt procelonssors")
    elonvelonntBusProcelonssors.forelonach(_.start())

    log.info("Starting thelon uua procelonssors")
    uuaConsumelonr.atLelonastOncelonProcelonssor.start()

    this.addAdminRoutelon(elonlfOwlFiltelonr.gelontPostbackRoutelon())

    onelonxit {
      log.info("Shutting down thelon elonvelonnt procelonssors")
      elonvelonntBusProcelonssors.forelonach(_.stop())
      log.info("Shutting down thelon uua procelonssors")
      uuaConsumelonr.atLelonastOncelonProcelonssor.closelon()
      log.info("donelon elonxit")
    }

    // Wait on thelon thriftSelonrvelonr so that shutdownTimelonout is relonspelonctelond.
    Await.relonsult(adminHttpSelonrvelonr)
  }
}
