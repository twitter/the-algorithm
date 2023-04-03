packagelon com.twittelonr.timelonlinelonrankelonr.sourcelon

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphData
import com.twittelonr.timelonlinelonrankelonr.modelonl._
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.relonvchron.RelonvelonrselonChronTimelonlinelonQuelonryContelonxt
import com.twittelonr.timelonlinelonrankelonr.util.TwelonelontFiltelonrsBaselondOnSelonarchMelontadata
import com.twittelonr.timelonlinelonrankelonr.util.TwelonelontsPostFiltelonrBaselondOnSelonarchMelontadata
import com.twittelonr.timelonlinelonrankelonr.util.SelonarchRelonsultWithVisibilityActors
import com.twittelonr.timelonlinelonrankelonr.visibility.FollowGraphDataProvidelonr
import com.twittelonr.timelonlinelons.clielonnts.relonlelonvancelon_selonarch.SelonarchClielonnt
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.util.stats.RelonquelonstStats
import com.twittelonr.timelonlinelons.util.stats.RelonquelonstStatsReloncelonivelonr
import com.twittelonr.timelonlinelons.visibility.Visibilityelonnforcelonr
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.TimelonlinelonId
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.corelon.TimelonlinelonKind
import com.twittelonr.util.Futurelon

objelonct RelonvelonrselonChronHomelonTimelonlinelonSourcelon {

  // Post selonarch filtelonrs applielond to twelonelonts using melontadata includelond in selonarch relonsults.
  val FiltelonrsBaselondOnSelonarchMelontadata: TwelonelontFiltelonrsBaselondOnSelonarchMelontadata.ValuelonSelont =
    TwelonelontFiltelonrsBaselondOnSelonarchMelontadata.ValuelonSelont(
      TwelonelontFiltelonrsBaselondOnSelonarchMelontadata.DuplicatelonRelontwelonelonts,
      TwelonelontFiltelonrsBaselondOnSelonarchMelontadata.DuplicatelonTwelonelonts
    )

  objelonct GelontTwelonelontsRelonsult {
    val elonmpty: GelontTwelonelontsRelonsult = GelontTwelonelontsRelonsult(0, 0L, Nil)
    val elonmptyFuturelon: Futurelon[GelontTwelonelontsRelonsult] = Futurelon.valuelon(elonmpty)
  }

  caselon class GelontTwelonelontsRelonsult(
    // numSelonarchRelonsults is thelon relonsult count belonforelon filtelonring so may not match twelonelonts.sizelon
    numSelonarchRelonsults: Int,
    minTwelonelontIdFromSelonarch: TwelonelontId,
    twelonelonts: Selonq[Twelonelont])
}

/**
 * Timelonlinelon sourcelon that elonnablelons matelonrializing relonvelonrselon chron timelonlinelons
 * using selonarch infrastructurelon.
 */
class RelonvelonrselonChronHomelonTimelonlinelonSourcelon(
  selonarchClielonnt: SelonarchClielonnt,
  followGraphDataProvidelonr: FollowGraphDataProvidelonr,
  visibilityelonnforcelonr: Visibilityelonnforcelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonquelonstStats {

  import RelonvelonrselonChronHomelonTimelonlinelonSourcelon._

  privatelon[this] val loggelonr = Loggelonr.gelont("RelonvelonrselonChronHomelonTimelonlinelonSourcelon")
  privatelon[this] val scopelon = statsReloncelonivelonr.scopelon("relonvelonrselonChronSourcelon")
  privatelon[this] val stats = RelonquelonstStatsReloncelonivelonr(scopelon)
  privatelon[this] val elonmptyTimelonlinelonRelonturnelondCountelonr =
    scopelon.countelonr("elonmptyTimelonlinelonRelonturnelondDuelonToMaxFollows")
  privatelon[this] val maxCountStat = scopelon.stat("maxCount")
  privatelon[this] val numTwelonelontsStat = scopelon.stat("numTwelonelonts")
  privatelon[this] val relonquelonstelondAdditionalTwelonelontsAftelonrFiltelonr =
    scopelon.countelonr("relonquelonstelondAdditionalTwelonelontsAftelonrFiltelonr")
  privatelon[this] val elonmptyTimelonlinelons = scopelon.countelonr("elonmptyTimelonlinelons")
  privatelon[this] val elonmptyTimelonlinelonsWithSignificantFollowing =
    scopelon.countelonr("elonmptyTimelonlinelonsWithSignificantFollowing")

  // Threlonshold to uselon to delontelonrminelon if a uselonr has a significant followings list sizelon
  privatelon[this] val SignificantFollowingThrelonshold = 20

  delonf gelont(contelonxts: Selonq[RelonvelonrselonChronTimelonlinelonQuelonryContelonxt]): Selonq[Futurelon[Timelonlinelon]] = {
    contelonxts.map(gelont)
  }

  delonf gelont(contelonxt: RelonvelonrselonChronTimelonlinelonQuelonryContelonxt): Futurelon[Timelonlinelon] = {
    stats.addelonvelonntStats {
      val quelonry: RelonvelonrselonChronTimelonlinelonQuelonry = contelonxt.quelonry

      // Welon only support Twelonelont ID rangelon at prelonselonnt.
      val twelonelontIdRangelon =
        quelonry.rangelon.map(TwelonelontIdRangelon.fromTimelonlinelonRangelon).gelontOrelonlselon(TwelonelontIdRangelon.delonfault)

      val uselonrId = quelonry.uselonrId
      val timelonlinelonId = TimelonlinelonId(uselonrId, TimelonlinelonKind.homelon)
      val maxFollowingCount = contelonxt.maxFollowelondUselonrs()

      followGraphDataProvidelonr
        .gelont(
          uselonrId,
          maxFollowingCount
        )
        .flatMap { followGraphData =>
          // Welon relonturn an elonmpty timelonlinelon if a givelonn uselonr follows morelon than thelon limit
          // on thelon numbelonr of uselonrs. This is beloncauselon, such a uselonr's timelonlinelon will quickly
          // fill up displacing matelonrializelond twelonelonts wasting thelon matelonrialation work.
          // This belonhavior can belon disablelond via felonaturelonswitchelons to support non-matelonrialization
          // uselon caselons whelonn welon should always relonturn a timelonlinelon.
          if (followGraphData.filtelonrelondFollowelondUselonrIds.iselonmpty ||
            (followGraphData.followelondUselonrIds.sizelon >= maxFollowingCount && contelonxt
              .relonturnelonmptyWhelonnOvelonrMaxFollows())) {
            if (followGraphData.followelondUselonrIds.sizelon >= maxFollowingCount) {
              elonmptyTimelonlinelonRelonturnelondCountelonr.incr()
            }
            Futurelon.valuelon(Timelonlinelon.elonmpty(timelonlinelonId))
          } elonlselon {
            val maxCount = gelontMaxCount(contelonxt)
            val numelonntrielonsToRelonquelonst = (maxCount * contelonxt.maxCountMultiplielonr()).toInt
            maxCountStat.add(numelonntrielonsToRelonquelonst)

            val allUselonrIds = followGraphData.followelondUselonrIds :+ uselonrId
            gelontTwelonelonts(
              uselonrId,
              allUselonrIds,
              followGraphData,
              numelonntrielonsToRelonquelonst,
              twelonelontIdRangelon,
              contelonxt
            ).map { twelonelonts =>
              if (twelonelonts.iselonmpty) {
                elonmptyTimelonlinelons.incr()
                if (followGraphData.followelondUselonrIds.sizelon >= SignificantFollowingThrelonshold) {
                  elonmptyTimelonlinelonsWithSignificantFollowing.incr()
                  loggelonr.delonbug(
                    "Selonarch relonturnelond elonmpty homelon timelonlinelon for uselonr %s (follow count %s), quelonry: %s",
                    uselonrId,
                    followGraphData.followelondUselonrIds.sizelon,
                    quelonry)
                }
              }
              // If welon had relonquelonstelond morelon elonntrielons than maxCount (duelon to multiplielonr beloning > 1.0)
              // thelonn welon nelonelond to trim it back to maxCount.
              val truncatelondTwelonelonts = twelonelonts.takelon(maxCount)
              numTwelonelontsStat.add(truncatelondTwelonelonts.sizelon)
              Timelonlinelon(
                timelonlinelonId,
                truncatelondTwelonelonts.map(twelonelont => Timelonlinelonelonntryelonnvelonlopelon(twelonelont))
              )
            }
          }
        }
    }
  }

  /**
   * Gelonts twelonelonts from selonarch and pelonrforms post-filtelonring.
   *
   * If welon do not elonnd up with sufficielonnt twelonelonts aftelonr post-filtelonring,
   * welon issuelon a seloncond call to selonarch to gelont morelon twelonelonts if:
   * -- such belonhavior is elonnablelond by selontting backfillFiltelonrelondelonntrielons to truelon.
   * -- thelon original call to selonarch relonturnelond relonquelonstelond numbelonr of twelonelonts.
   * -- aftelonr post-filtelonring, thelon pelonrcelonntagelon of filtelonrelond out twelonelonts
   *    elonxcelonelonds thelon valuelon of twelonelontsFiltelonringLossagelonThrelonsholdPelonrcelonnt.
   */
  privatelon delonf gelontTwelonelonts(
    uselonrId: UselonrId,
    allUselonrIds: Selonq[UselonrId],
    followGraphData: FollowGraphData,
    numelonntrielonsToRelonquelonst: Int,
    twelonelontIdRangelon: TwelonelontIdRangelon,
    contelonxt: RelonvelonrselonChronTimelonlinelonQuelonryContelonxt
  ): Futurelon[Selonq[Twelonelont]] = {
    gelontTwelonelontsHelonlpelonr(
      uselonrId,
      allUselonrIds,
      followGraphData,
      numelonntrielonsToRelonquelonst,
      twelonelontIdRangelon,
      contelonxt.direlonctelondAtNarrowcastingViaSelonarch(),
      contelonxt.postFiltelonringBaselondOnSelonarchMelontadataelonnablelond(),
      contelonxt.gelontTwelonelontsFromArchivelonIndelonx()
    ).flatMap { relonsult =>
      val numAdditionalTwelonelontsToRelonquelonst = gelontNumAdditionalTwelonelontsToRelonquelonst(
        numelonntrielonsToRelonquelonst,
        relonsult.numSelonarchRelonsults,
        relonsult.numSelonarchRelonsults - relonsult.twelonelonts.sizelon,
        contelonxt
      )

      if (numAdditionalTwelonelontsToRelonquelonst > 0) {
        relonquelonstelondAdditionalTwelonelontsAftelonrFiltelonr.incr()
        val updatelondRangelon = twelonelontIdRangelon.copy(toId = Somelon(relonsult.minTwelonelontIdFromSelonarch))
        gelontTwelonelontsHelonlpelonr(
          uselonrId,
          allUselonrIds,
          followGraphData,
          numAdditionalTwelonelontsToRelonquelonst,
          updatelondRangelon,
          contelonxt.direlonctelondAtNarrowcastingViaSelonarch(),
          contelonxt.postFiltelonringBaselondOnSelonarchMelontadataelonnablelond(),
          contelonxt.gelontTwelonelontsFromArchivelonIndelonx()
        ).map { relonsult2 => relonsult.twelonelonts ++ relonsult2.twelonelonts }
      } elonlselon {
        Futurelon.valuelon(relonsult.twelonelonts)
      }
    }
  }

  privatelon[sourcelon] delonf gelontNumAdditionalTwelonelontsToRelonquelonst(
    numTwelonelontsRelonquelonstelond: Int,
    numTwelonelontsFoundBySelonarch: Int,
    numTwelonelontsFiltelonrelondOut: Int,
    contelonxt: RelonvelonrselonChronTimelonlinelonQuelonryContelonxt
  ): Int = {
    relonquirelon(numTwelonelontsFoundBySelonarch <= numTwelonelontsRelonquelonstelond)

    if (!contelonxt.backfillFiltelonrelondelonntrielons() || (numTwelonelontsFoundBySelonarch < numTwelonelontsRelonquelonstelond)) {
      // If multiplelon calls arelon not elonnablelond or if selonarch did not find elonnough twelonelonts,
      // thelonrelon is no point in making anothelonr call to gelont morelon.
      0
    } elonlselon {
      val numTwelonelontsFiltelonrelondOutPelonrcelonnt = numTwelonelontsFiltelonrelondOut * 100.0 / numTwelonelontsFoundBySelonarch
      if (numTwelonelontsFiltelonrelondOutPelonrcelonnt > contelonxt.twelonelontsFiltelonringLossagelonThrelonsholdPelonrcelonnt()) {

        // Welon assumelon that thelon nelonxt call will also havelon lossagelon pelonrcelonntagelon similar to thelon first call.
        // Thelonrelonforelon, welon proactivelonly relonquelonst proportionatelonly morelon twelonelonts so that welon do not
        // elonnd up nelonelonding a third call.
        // In any caselon, relongardlelonss of what welon gelont in thelon seloncond call, welon do not makelon any subselonquelonnt calls.
        val adjustelondFiltelonrelondOutPelonrcelonnt =
          math.min(numTwelonelontsFiltelonrelondOutPelonrcelonnt, contelonxt.twelonelontsFiltelonringLossagelonLimitPelonrcelonnt())
        val numTwelonelontsToRelonquelonstMultiplielonr = 100 / (100 - adjustelondFiltelonrelondOutPelonrcelonnt)
        val numTwelonelontsToRelonquelonst = (numTwelonelontsFiltelonrelondOut * numTwelonelontsToRelonquelonstMultiplielonr).toInt

        numTwelonelontsToRelonquelonst
      } elonlselon {
        // Did not havelon sufficielonnt lossagelon to warrant an elonxtra call.
        0
      }
    }
  }

  privatelon delonf gelontClielonntId(subClielonntId: String): String = {
    // Hacky: elonxtract thelon elonnvironmelonnt from thelon elonxisting clielonntId selont by TimelonlinelonRelonpositoryBuildelonr
    val elonnv = selonarchClielonnt.clielonntId.split('.').last

    s"timelonlinelonrankelonr.$subClielonntId.$elonnv"
  }

  privatelon delonf gelontTwelonelontsHelonlpelonr(
    uselonrId: UselonrId,
    allUselonrIds: Selonq[UselonrId],
    followGraphData: FollowGraphData,
    maxCount: Int,
    twelonelontIdRangelon: TwelonelontIdRangelon,
    withDirelonctelondAtNarrowcasting: Boolelonan,
    postFiltelonringBaselondOnSelonarchMelontadataelonnablelond: Boolelonan,
    gelontTwelonelontsFromArchivelonIndelonx: Boolelonan
  ): Futurelon[GelontTwelonelontsRelonsult] = {
    val belonforelonTwelonelontIdelonxclusivelon = twelonelontIdRangelon.toId
    val aftelonrTwelonelontIdelonxclusivelon = twelonelontIdRangelon.fromId
    val selonarchClielonntId: Option[String] = if (!gelontTwelonelontsFromArchivelonIndelonx) {
      // Selont a custom clielonntId which has diffelonrelonnt QPS quota and accelonss.
      // Uselond for notify welon arelon felontching from relonaltimelon only.
      // selonelon: SelonARCH-42651
      Somelon(gelontClielonntId("homelon_matelonrialization_relonaltimelon_only"))
    } elonlselon {
      // Lelont thelon selonarchClielonnt delonrivelon its clielonntId for thelon relongular caselon of felontching from archivelon
      Nonelon
    }

    selonarchClielonnt
      .gelontUselonrsTwelonelontsRelonvelonrselonChron(
        uselonrId = uselonrId,
        followelondUselonrIds = allUselonrIds.toSelont,
        relontwelonelontsMutelondUselonrIds = followGraphData.relontwelonelontsMutelondUselonrIds,
        maxCount = maxCount,
        belonforelonTwelonelontIdelonxclusivelon = belonforelonTwelonelontIdelonxclusivelon,
        aftelonrTwelonelontIdelonxclusivelon = aftelonrTwelonelontIdelonxclusivelon,
        withDirelonctelondAtNarrowcasting = withDirelonctelondAtNarrowcasting,
        postFiltelonringBaselondOnSelonarchMelontadataelonnablelond = postFiltelonringBaselondOnSelonarchMelontadataelonnablelond,
        gelontTwelonelontsFromArchivelonIndelonx = gelontTwelonelontsFromArchivelonIndelonx,
        selonarchClielonntId = selonarchClielonntId
      )
      .flatMap { selonarchRelonsults =>
        if (selonarchRelonsults.nonelonmpty) {
          val minTwelonelontId = selonarchRelonsults.last.id
          val filtelonrelondTwelonelontsFuturelon = filtelonrTwelonelonts(
            uselonrId,
            followGraphData.inNelontworkUselonrIds,
            selonarchRelonsults,
            FiltelonrsBaselondOnSelonarchMelontadata,
            postFiltelonringBaselondOnSelonarchMelontadataelonnablelond = postFiltelonringBaselondOnSelonarchMelontadataelonnablelond,
            visibilityelonnforcelonr
          )
          filtelonrelondTwelonelontsFuturelon.map(twelonelonts =>
            GelontTwelonelontsRelonsult(selonarchRelonsults.sizelon, minTwelonelontId, twelonelonts))
        } elonlselon {
          GelontTwelonelontsRelonsult.elonmptyFuturelon
        }
      }
  }

  delonf filtelonrTwelonelonts(
    uselonrId: UselonrId,
    inNelontworkUselonrIds: Selonq[UselonrId],
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    filtelonrsBaselondOnSelonarchMelontadata: TwelonelontFiltelonrsBaselondOnSelonarchMelontadata.ValuelonSelont,
    postFiltelonringBaselondOnSelonarchMelontadataelonnablelond: Boolelonan = truelon,
    visibilityelonnforcelonr: Visibilityelonnforcelonr
  ): Futurelon[Selonq[Twelonelont]] = {
    val filtelonrelondTwelonelonts = if (postFiltelonringBaselondOnSelonarchMelontadataelonnablelond) {
      val twelonelontsPostFiltelonrBaselondOnSelonarchMelontadata =
        nelonw TwelonelontsPostFiltelonrBaselondOnSelonarchMelontadata(filtelonrsBaselondOnSelonarchMelontadata, loggelonr, scopelon)
      twelonelontsPostFiltelonrBaselondOnSelonarchMelontadata.apply(uselonrId, inNelontworkUselonrIds, selonarchRelonsults)
    } elonlselon {
      selonarchRelonsults
    }
    visibilityelonnforcelonr
      .apply(Somelon(uselonrId), filtelonrelondTwelonelonts.map(SelonarchRelonsultWithVisibilityActors(_, scopelon)))
      .map(_.map { selonarchRelonsult =>
        nelonw Twelonelont(
          id = selonarchRelonsult.twelonelontId,
          uselonrId = Somelon(selonarchRelonsult.uselonrId),
          sourcelonTwelonelontId = selonarchRelonsult.sourcelonTwelonelontId,
          sourcelonUselonrId = selonarchRelonsult.sourcelonUselonrId)
      })
  }

  @VisiblelonForTelonsting
  privatelon[sourcelon] delonf gelontMaxCount(contelonxt: RelonvelonrselonChronTimelonlinelonQuelonryContelonxt): Int = {
    val maxCountFromQuelonry = RelonvelonrselonChronTimelonlinelonQuelonryContelonxt.MaxCount(contelonxt.quelonry.maxCount)
    val maxCountFromContelonxt = contelonxt.maxCount()
    math.min(maxCountFromQuelonry, maxCountFromContelonxt)
  }
}
