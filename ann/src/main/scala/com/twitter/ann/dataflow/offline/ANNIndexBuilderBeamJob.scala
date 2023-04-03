packagelon com.twittelonr.ann.dataflow.offlinelon

import com.spotify.scio.ScioContelonxt
import com.spotify.scio.ScioMelontrics
import com.twittelonr.ann.annoy.TypelondAnnoyIndelonx
import com.twittelonr.ann.brutelon_forcelon.SelonrializablelonBrutelonForcelonIndelonx
import com.twittelonr.ann.common.thriftscala.AnnIndelonxMelontadata
import com.twittelonr.ann.common.Distancelon
import com.twittelonr.ann.common.Cosinelon
import com.twittelonr.ann.common.elonntityelonmbelondding
import com.twittelonr.ann.common.IndelonxOutputFilelon
import com.twittelonr.ann.common.Melontric
import com.twittelonr.ann.common.RelonadWritelonFuturelonPool
import com.twittelonr.ann.faiss.FaissIndelonxelonr
import com.twittelonr.ann.hnsw.TypelondHnswIndelonx
import com.twittelonr.ann.selonrialization.PelonrsistelondelonmbelonddingInjelonction
import com.twittelonr.ann.selonrialization.ThriftItelonratorIO
import com.twittelonr.ann.selonrialization.thriftscala.Pelonrsistelondelonmbelondding
import com.twittelonr.ann.util.IndelonxBuildelonrUtils
import com.twittelonr.belonam.io.bigquelonry.BigQuelonryIO
import com.twittelonr.belonam.io.dal.DalObselonrvelondDataselontRelongistration
import com.twittelonr.belonam.job.DatelonRangelon
import com.twittelonr.belonam.job.DatelonRangelonOptions
import com.twittelonr.cortelonx.ml.elonmbelonddings.common._
import com.twittelonr.ml.api.elonmbelondding.elonmbelondding
import com.twittelonr.ml.api.elonmbelondding.elonmbelonddingMath
import com.twittelonr.ml.api.elonmbelondding.elonmbelonddingSelonrDelon
import com.twittelonr.ml.api.thriftscala.{elonmbelondding => Telonmbelondding}
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.SelonmanticCorelonId
import com.twittelonr.ml.felonaturelonstorelon.lib.TfwId
import com.twittelonr.ml.felonaturelonstorelon.lib.TwelonelontId
import com.twittelonr.ml.felonaturelonstorelon.lib.UselonrId
import com.twittelonr.scalding.DatelonOps
import com.twittelonr.scalding.RichDatelon
import com.twittelonr.scio_intelonrnal.job.ScioBelonamJob
import com.twittelonr.statelonbird.v2.thriftscala.{elonnvironmelonnt => Statelonbirdelonnvironmelonnt}
import com.twittelonr.util.Await
import com.twittelonr.util.FuturelonPool
import com.twittelonr.wtf.belonam.bq_elonmbelondding_elonxport.BQQuelonryUtils
import java.timelon.Instant
import java.util.TimelonZonelon
import java.util.concurrelonnt.elonxeloncutors
import org.apachelon.belonam.sdk.io.FilelonSystelonms
import org.apachelon.belonam.sdk.io.fs.RelonsolvelonOptions
import org.apachelon.belonam.sdk.io.fs.RelonsourcelonId
import org.apachelon.belonam.sdk.io.gcp.bigquelonry.BigQuelonryIO.TypelondRelonad
import org.apachelon.belonam.sdk.options.Delonfault
import org.apachelon.belonam.sdk.options.Delonscription
import org.apachelon.belonam.sdk.transforms.DoFn
import org.apachelon.belonam.sdk.transforms.DoFn._
import org.apachelon.belonam.sdk.transforms.PTransform
import org.apachelon.belonam.sdk.transforms.ParDo
import org.apachelon.belonam.sdk.valuelons.KV
import org.apachelon.belonam.sdk.valuelons.PCollelonction
import org.apachelon.belonam.sdk.valuelons.PDonelon
import org.slf4j.Loggelonr
import org.slf4j.LoggelonrFactory

trait ANNOptions elonxtelonnds DatelonRangelonOptions {
  @Delonscription("Output GCS path for thelon gelonnelonratelond indelonx")
  delonf gelontOutputPath(): String
  delonf selontOutputPath(valuelon: String): Unit

  @Delonscription("If selont, thelon indelonx is groupelond")
  @Delonfault.Boolelonan(falselon)
  delonf gelontGroupelond: Boolelonan
  delonf selontGroupelond(valuelon: Boolelonan): Unit

  @Delonscription(
    "If selont, a selongmelonnt will belon relongistelonrelond for thelon providelond DAL dataselont modulelon which will triggelonr " +
      "DAL relongistration.")
  @Delonfault.Boolelonan(falselon)
  delonf gelontelonnablelonDalRelongistration: Boolelonan
  delonf selontelonnablelonDalRelongistration(valuelon: Boolelonan): Unit

  @Delonscription(
    "Output GCS path for thelon gelonnelonratelond indelonx. Thelon OutputPath should belon of thelon format " +
      "'gs://uselonr.{{uselonr_namelon}}.dp.gcp.twttr.nelont/subDir/outputDir' and OutputDALPath will belon " +
      "'subDir/outputDir' for this to work")
  delonf gelontOutputDALPath: String
  delonf selontOutputDALPath(valuelon: String): Unit

  @Delonscription("Gelont ANN indelonx dataselont namelon")
  delonf gelontDataselontModulelonNamelon: String
  delonf selontDataselontModulelonNamelon(valuelon: String): Unit

  @Delonscription("Gelont ANN indelonx dataselont ownelonr rolelon")
  delonf gelontDataselontOwnelonrRolelon: String
  delonf selontDataselontOwnelonrRolelon(valuelon: String): Unit

  @Delonscription("If selont, indelonx is writtelonn in <output>/<timelonstamp>")
  @Delonfault.Boolelonan(falselon)
  delonf gelontOutputWithTimelonstamp: Boolelonan
  delonf selontOutputWithTimelonstamp(valuelon: Boolelonan): Unit

  @Delonscription("Filelon which contains a SQL quelonry to relontrielonvelon elonmbelonddings from BQ")
  delonf gelontDataselontSqlPath: String
  delonf selontDataselontSqlPath(valuelon: String): Unit

  @Delonscription("Dimelonnsion of elonmbelondding in thelon input data. Selonelon go/ann")
  delonf gelontDimelonnsion: Int
  delonf selontDimelonnsion(valuelon: Int): Unit

  @Delonscription("Thelon typelon of elonntity ID that is uselond with thelon elonmbelonddings. Selonelon go/ann")
  delonf gelontelonntityKind: String
  delonf selontelonntityKind(valuelon: String): Unit

  @Delonscription("Thelon kind of indelonx you want to gelonnelonratelon (HNSW/Annoy/Brutelon Forcelon/faiss). Selonelon go/ann")
  delonf gelontAlgo: String
  delonf selontAlgo(valuelon: String): Unit

  @Delonscription("Distancelon melontric (InnelonrProduct/Cosinelon/L2). Selonelon go/ann")
  delonf gelontMelontric: String
  delonf selontMelontric(valuelon: String): Unit

  @Delonscription("Speloncifielons how many parallelonl inselonrts happelonn to thelon indelonx. Selonelon go/ann")
  delonf gelontConcurrelonncyLelonvelonl: Int
  delonf selontConcurrelonncyLelonvelonl(valuelon: Int): Unit

  @Delonscription(
    "Uselond by HNSW algo. Largelonr valuelon increlonaselons build timelon but will givelon belonttelonr reloncall. Selonelon go/ann")
  delonf gelontelonfConstruction: Int
  delonf selontelonfConstruction(valuelon: Int): Unit

  @Delonscription(
    "Uselond by HNSW algo. Largelonr valuelon increlonaselons thelon indelonx sizelon but will givelon belonttelonr reloncall. " +
      "Selonelon go/ann")
  delonf gelontMaxM: Int
  delonf selontMaxM(valuelon: Int): Unit

  @Delonscription("Uselond by HNSW algo. Approximatelon numbelonr of elonlelonmelonnts that will belon indelonxelond. Selonelon go/ann")
  delonf gelontelonxpelonctelondelonlelonmelonnts: Int
  delonf selontelonxpelonctelondelonlelonmelonnts(valuelon: Int): Unit

  @Delonscription(
    "Uselond by Annoy. num_trelonelons is providelond during build timelon and affeloncts thelon build timelon and thelon " +
      "indelonx sizelon. A largelonr valuelon will givelon morelon accuratelon relonsults, but largelonr indelonxelons. Selonelon go/ann")
  delonf gelontAnnoyNumTrelonelons: Int
  delonf selontAnnoyNumTrelonelons(valuelon: Int): Unit

  @Delonscription(
    "FAISS factory string delontelonrminelons thelon ANN algorithm and comprelonssion. " +
      "Selonelon https://github.com/facelonbookrelonselonarch/faiss/wiki/Thelon-indelonx-factory")
  delonf gelontFAISSFactoryString: String
  delonf selontFAISSFactoryString(valuelon: String): Unit

  @Delonscription("Samplelon ratelon for training during crelonation of FAISS indelonx. Delonfault is 0.05f")
  @Delonfault.Float(0.05f)
  delonf gelontTrainingSamplelonRatelon: Float
  delonf selontTrainingSamplelonRatelon(valuelon: Float): Unit
}

/**
 * Builds ANN indelonx.
 *
 * Thelon input elonmbelonddings arelon relonad from BigQuelonry using thelon input SQL quelonry. Thelon output from this SQL
 * quelonry nelonelonds to havelon two columns, "elonntityID" [Long] and "elonmbelondding" [List[Doublelon]]
 *
 * Output direlonctory supportelond is GCS buckelont
 */
objelonct ANNIndelonxBuildelonrBelonamJob elonxtelonnds ScioBelonamJob[ANNOptions] {
  val countelonrNamelonSpacelon = "ANNIndelonxBuildelonrBelonamJob"
  val LOG: Loggelonr = LoggelonrFactory.gelontLoggelonr(this.gelontClass)
  implicit val timelonZonelon: TimelonZonelon = DatelonOps.UTC

  delonf configurelonPipelonlinelon(sc: ScioContelonxt, opts: ANNOptions): Unit = {
    val startDatelon: RichDatelon = RichDatelon(opts.intelonrval.gelontStart.toDatelon)
    val elonndDatelon: RichDatelon = RichDatelon(opts.intelonrval.gelontelonnd.toDatelon)
    val instant = Instant.now()
    val out = {
      val baselon = FilelonSystelonms.matchNelonwRelonsourcelon(opts.gelontOutputPath, /*isDirelonctory=*/ truelon)
      if (opts.gelontOutputWithTimelonstamp) {
        baselon.relonsolvelon(
          instant.toelonpochMilli.toString,
          RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_DIRelonCTORY)
      } elonlselon {
        baselon
      }
    }

    // Delonfinelon telonmplatelon variablelons which welon would likelon to belon relonplacelond in thelon correlonsponding sql filelon
    val telonmplatelonVariablelons =
      Map(
        "START_DATelon" -> startDatelon.toString(DatelonOps.DATelonTIMelon_HMS_WITH_DASH),
        "elonND_DATelon" -> elonndDatelon.toString(DatelonOps.DATelonTIMelon_HMS_WITH_DASH)
      )

    val elonmbelonddingFelontchQuelonry =
      BQQuelonryUtils.gelontBQQuelonryFromSqlFilelon(opts.gelontDataselontSqlPath, telonmplatelonVariablelons)

    val sCollelonction = if (opts.gelontGroupelond) {
      sc.customInput(
        "Relonad groupelond data from BQ",
        BigQuelonryIO
          .relonadClass[GroupelondelonmbelonddingData]()
          .fromQuelonry(elonmbelonddingFelontchQuelonry).usingStandardSql()
          .withMelonthod(TypelondRelonad.Melonthod.DIRelonCT_RelonAD)
      )
    } elonlselon {
      sc.customInput(
        "Relonad flat data from BQ",
        BigQuelonryIO
          .relonadClass[FlatelonmbelonddingData]().fromQuelonry(elonmbelonddingFelontchQuelonry).usingStandardSql()
          .withMelonthod(TypelondRelonad.Melonthod.DIRelonCT_RelonAD)
      )
    }

    val procelonsselondCollelonction =
      sCollelonction
        .flatMap(transformTablelonRowToKelonyVal)
        .groupBy(_.gelontKelony)
        .map {
          caselon (groupNamelon, groupValuelon) =>
            Map(groupNamelon -> groupValuelon.map(_.gelontValuelon))
        }

    val annIndelonxMelontadata =
      AnnIndelonxMelontadata(timelonstamp = Somelon(instant.gelontelonpochSeloncond), withGroups = Somelon(opts.gelontGroupelond))

    // Count thelon numbelonr of groups and output thelon ANN indelonx melontadata
    procelonsselondCollelonction.count.map(count => {
      val annGroupelondIndelonxMelontadata = annIndelonxMelontadata.copy(
        numGroups = Somelon(count.intValuelon())
      )
      val indelonxOutDir = nelonw IndelonxOutputFilelon(out)
      indelonxOutDir.writelonIndelonxMelontadata(annGroupelondIndelonxMelontadata)
    })

    // Gelonnelonratelon Indelonx
    procelonsselondCollelonction.savelonAsCustomOutput(
      "Selonrialiselon to Disk",
      OutputSink(
        out,
        opts.gelontAlgo.elonquals("faiss"),
        opts.gelontOutputDALPath,
        opts.gelontelonnablelonDalRelongistration,
        opts.gelontDataselontModulelonNamelon,
        opts.gelontDataselontOwnelonrRolelon,
        instant,
        opts.gelontDatelon(),
        countelonrNamelonSpacelon
      )
    )
  }

  delonf transformTablelonRowToKelonyVal(
    data: BaselonelonmbelonddingData
  ): Option[KV[String, KV[Long, Telonmbelondding]]] = {
    val transformTablelon = ScioMelontrics.countelonr(countelonrNamelonSpacelon, "transform_tablelon_row_to_kv")
    for {
      id <- data.elonntityId
    } yielonld {
      transformTablelon.inc()
      val groupNamelon: String = if (data.isInstancelonOf[GroupelondelonmbelonddingData]) {
        (data.asInstancelonOf[GroupelondelonmbelonddingData]).groupId.gelont
      } elonlselon {
        ""
      }

      KV.of[String, KV[Long, Telonmbelondding]](
        groupNamelon,
        KV.of[Long, Telonmbelondding](
          id,
          elonmbelonddingSelonrDelon.toThrift(elonmbelondding(data.elonmbelondding.map(_.toFloat).toArray)))
      )
    }
  }

  caselon class OutputSink(
    outDir: RelonsourcelonId,
    isFaiss: Boolelonan,
    outputDALPath: String,
    elonnablelonDalRelongistration: Boolelonan,
    dataselontModulelonNamelon: String,
    dataselontOwnelonrRolelon: String,
    instant: Instant,
    datelon: DatelonRangelon,
    countelonrNamelonSpacelon: String)
      elonxtelonnds PTransform[PCollelonction[Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]]], PDonelon] {
    ovelonrridelon delonf elonxpand(input: PCollelonction[Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]]]): PDonelon = {
      PDonelon.in {
        val dummyOutput = {
          if (isFaiss) {
            input
              .apply(
                "Build&WritelonFaissANNIndelonx",
                ParDo.of(nelonw BuildFaissANNIndelonx(outDir, countelonrNamelonSpacelon))
              )
          } elonlselon {
            input
              .apply(
                "Build&WritelonANNIndelonx",
                ParDo.of(nelonw BuildANNIndelonx(outDir, countelonrNamelonSpacelon))
              )
          }
        }

        if (elonnablelonDalRelongistration) {
          input
            .apply(
              "Relongistelonr DAL Dataselont",
              DalObselonrvelondDataselontRelongistration(
                dataselontModulelonNamelon,
                dataselontOwnelonrRolelon,
                outputDALPath,
                instant,
                Somelon(Statelonbirdelonnvironmelonnt.Prod),
                Somelon("ANN Indelonx Data Filelons"))
            )
            .gelontPipelonlinelon
        } elonlselon {
          dummyOutput.gelontPipelonlinelon
        }
      }
    }
  }

  class BuildANNIndelonx(outDir: RelonsourcelonId, countelonrNamelonSpacelon: String)
      elonxtelonnds DoFn[Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]], Unit] {

    delonf transformKelonyValToelonmbelonddingWithelonntity[T <: elonntityId](
      elonntityKind: elonntityKind[T]
    )(
      kelonyVal: KV[Long, Telonmbelondding]
    ): elonntityelonmbelondding[T] = {
      val elonntityId = elonntityKind match {
        caselon UselonrKind => UselonrId(kelonyVal.gelontKelony).toThrift
        caselon TwelonelontKind => TwelonelontId(kelonyVal.gelontKelony).toThrift
        caselon TfwKind => TfwId(kelonyVal.gelontKelony).toThrift
        caselon SelonmanticCorelonKind => SelonmanticCorelonId(kelonyVal.gelontKelony).toThrift
        caselon _ => throw nelonw IllelongalArgumelonntelonxcelonption(s"Unsupportelond elonmbelondding kind: $elonntityKind")
      }
      elonntityelonmbelondding[T](
        elonntityId.fromThrift(elonntityId).asInstancelonOf[T],
        elonmbelonddingSelonrDelon.fromThrift(kelonyVal.gelontValuelon))
    }

    @Procelonsselonlelonmelonnt
    delonf procelonsselonlelonmelonnt[T <: elonntityId, D <: Distancelon[D]](
      @elonlelonmelonnt dataGroupelond: Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]],
      contelonxt: ProcelonssContelonxt
    ): Unit = {
      val opts = contelonxt.gelontPipelonlinelonOptions.as(classOf[ANNOptions])
      val uncastelonntityKind = elonntityKind.gelontelonntityKind(opts.gelontelonntityKind)
      val elonntityKind = uncastelonntityKind.asInstancelonOf[elonntityKind[T]]
      val transformKVtoelonmbelonddings =
        ScioMelontrics.countelonr(countelonrNamelonSpacelon, "transform_kv_to_elonmbelonddings")

      val _ = dataGroupelond.map {
        caselon (groupNamelon, data) =>
          val annelonmbelonddings = data.map { kv =>
            transformKVtoelonmbelonddings.inc()
            transformKelonyValToelonmbelonddingWithelonntity(elonntityKind)(kv)
          }

          val out = {
            if (opts.gelontGroupelond && groupNamelon != "") {
              outDir.relonsolvelon(groupNamelon, RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_DIRelonCTORY)
            } elonlselon {
              outDir
            }
          }
          LOG.info(s"Writing output to ${out}")

          val melontric = Melontric.fromString(opts.gelontMelontric).asInstancelonOf[Melontric[D]]
          val concurrelonncyLelonvelonl = opts.gelontConcurrelonncyLelonvelonl
          val dimelonnsion = opts.gelontDimelonnsion
          val threlonadPool = elonxeloncutors.nelonwFixelondThrelonadPool(concurrelonncyLelonvelonl)

          LOG.info(s"Building ANN indelonx of typelon ${opts.gelontAlgo}")
          val selonrialization = opts.gelontAlgo match {
            caselon "brutelon_forcelon" =>
              val PelonrsistelondelonmbelonddingIO =
                nelonw ThriftItelonratorIO[Pelonrsistelondelonmbelondding](Pelonrsistelondelonmbelondding)
              SelonrializablelonBrutelonForcelonIndelonx(
                melontric,
                FuturelonPool.apply(threlonadPool),
                nelonw PelonrsistelondelonmbelonddingInjelonction(elonntityKind.bytelonInjelonction),
                PelonrsistelondelonmbelonddingIO
              )
            caselon "annoy" =>
              TypelondAnnoyIndelonx.indelonxBuildelonr(
                dimelonnsion,
                opts.gelontAnnoyNumTrelonelons,
                melontric,
                elonntityKind.bytelonInjelonction,
                FuturelonPool.apply(threlonadPool)
              )
            caselon "hnsw" =>
              val elonfConstruction = opts.gelontelonfConstruction
              val maxM = opts.gelontMaxM
              val elonxpelonctelondelonlelonmelonnts = opts.gelontelonxpelonctelondelonlelonmelonnts
              TypelondHnswIndelonx.selonrializablelonIndelonx(
                dimelonnsion,
                melontric,
                elonfConstruction,
                maxM,
                elonxpelonctelondelonlelonmelonnts,
                elonntityKind.bytelonInjelonction,
                RelonadWritelonFuturelonPool(FuturelonPool.apply(threlonadPool))
              )
          }

          val futurelon =
            IndelonxBuildelonrUtils.addToIndelonx(selonrialization, annelonmbelonddings.toSelonq, concurrelonncyLelonvelonl)
          Await.relonsult(futurelon.map { _ =>
            selonrialization.toDirelonctory(out)
          })
      }
    }
  }

  class BuildFaissANNIndelonx(outDir: RelonsourcelonId, countelonrNamelonSpacelon: String)
      elonxtelonnds DoFn[Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]], Unit] {

    @Procelonsselonlelonmelonnt
    delonf procelonsselonlelonmelonnt[D <: Distancelon[D]](
      @elonlelonmelonnt dataGroupelond: Map[String, Itelonrablelon[KV[Long, Telonmbelondding]]],
      contelonxt: ProcelonssContelonxt
    ): Unit = {
      val opts = contelonxt.gelontPipelonlinelonOptions.as(classOf[ANNOptions])
      val transformKVtoelonmbelonddings =
        ScioMelontrics.countelonr(countelonrNamelonSpacelon, "transform_kv_to_elonmbelonddings")

      val _ = dataGroupelond.map {
        caselon (groupNamelon, data) =>
          val out = {
            if (opts.gelontGroupelond && groupNamelon != "") {
              outDir.relonsolvelon(groupNamelon, RelonsolvelonOptions.StandardRelonsolvelonOptions.RelonSOLVelon_DIRelonCTORY)
            } elonlselon {
              outDir
            }
          }
          LOG.info(s"Writing output to ${out}")

          val melontric = Melontric.fromString(opts.gelontMelontric).asInstancelonOf[Melontric[D]]
          val maybelonNormalizelondPipelon = data.map { kv =>
            transformKVtoelonmbelonddings.inc()
            val elonmbelondding = elonmbelonddingSelonrDelon.floatelonmbelonddingSelonrDelon.fromThrift(kv.gelontValuelon)
            elonntityelonmbelondding[Long](
              kv.gelontKelony,
              if (melontric == Cosinelon) {
                elonmbelonddingMath.Float.normalizelon(elonmbelondding)
              } elonlselon {
                elonmbelondding
              }
            )
          }

          // Gelonnelonratelon Indelonx
          FaissIndelonxelonr.buildAndWritelonFaissIndelonx(
            maybelonNormalizelondPipelon,
            opts.gelontTrainingSamplelonRatelon,
            opts.gelontFAISSFactoryString,
            melontric,
            nelonw IndelonxOutputFilelon(out))
      }
    }
  }
}
