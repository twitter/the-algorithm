packagelon com.twittelonr.selonarch.common.relonlelonvancelon;

import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicLong;
import java.util.strelonam.Collelonctors;

import scala.runtimelon.BoxelondUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Selonts;
import com.googlelon.common.util.concurrelonnt.ThrelonadFactoryBuildelonr;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.ThriftMux;
import com.twittelonr.finaglelon.buildelonr.ClielonntBuildelonr;
import com.twittelonr.finaglelon.buildelonr.ClielonntConfig;
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr;
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsClielonntBuildelonr;
import com.twittelonr.finaglelon.stats.DelonfaultStatsReloncelonivelonr;
import com.twittelonr.finaglelon.thrift.ThriftClielonntRelonquelonst;
import com.twittelonr.selonarch.common.melontrics.RelonlelonvancelonStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.trelonnds.plus.Modulelon;
import com.twittelonr.trelonnds.plus.TrelonndsPlusRelonquelonst;
import com.twittelonr.trelonnds.plus.TrelonndsPlusRelonsponselon;
import com.twittelonr.trelonnds.selonrvicelon.gelonn.Location;
import com.twittelonr.trelonnds.trelonnding_contelonnt.thriftjava.TrelonndingContelonntSelonrvicelon;
import com.twittelonr.trelonnds.trelonnds_melontadata.thriftjava.TrelonndsMelontadataSelonrvicelon;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.Try;

/**
 * Managelons trelonnds data relontrielonvelond from trelonnds thrift API and pelonrform automatic relonfrelonsh.
 */
public final class TrelonndsThriftDataSelonrvicelonManagelonr {
  privatelon static final Loggelonr LOG =
    LoggelonrFactory.gelontLoggelonr(TrelonndsThriftDataSelonrvicelonManagelonr.class.gelontNamelon());

  privatelon static final int DelonFAULT_TIMelon_TO_KILL_SelonC = 60;

  @VisiblelonForTelonsting
  protelonctelond static final Map<String, String> DelonFAULT_TRelonNDS_PARAMS_MAP = ImmutablelonMap.of(
      "MAX_ITelonMS_TO_RelonTURN", "10");   // welon only takelon top 10 for elonach woelonid.

  @VisiblelonForTelonsting
  protelonctelond static final int MAX_TRelonNDS_PelonR_WOelonID = 10;

  privatelon final Duration relonquelonstTimelonout;
  privatelon final Duration relonfrelonshDelonlayDuration;
  privatelon final Duration relonloadIntelonrvalDuration;
  privatelon final int numRelontrielons;

  // a list of trelonnds cachelon welon want to updatelon
  privatelon final List<NGramCachelon> trelonndsCachelonList;

  privatelon final SelonarchCountelonr gelontAvailablelonSuccelonssCountelonr =
      RelonlelonvancelonStats.elonxportLong("trelonnds_elonxtractor_gelont_availablelon_succelonss");
  privatelon final SelonarchCountelonr gelontAvailablelonFailurelonCountelonr =
      RelonlelonvancelonStats.elonxportLong("trelonnds_elonxtractor_gelont_availablelon_failurelon");
  privatelon final SelonarchCountelonr gelontTrelonndsSuccelonssCountelonr =
      RelonlelonvancelonStats.elonxportLong("trelonnds_elonxtractor_succelonss_felontch");
  privatelon final SelonarchCountelonr gelontTrelonndsFailurelonCountelonr =
      RelonlelonvancelonStats.elonxportLong("trelonnds_elonxtractor_failelond_felontch");
  privatelon final SelonarchCountelonr updatelonFailurelonCountelonr =
      RelonlelonvancelonStats.elonxportLong("trelonnds_elonxtractor_failelond_updatelon");

  privatelon final SelonrvicelonIdelonntifielonr selonrvicelonIdelonntifielonr;
  privatelon SchelondulelondelonxeloncutorSelonrvicelon schelondulelonr;


  @VisiblelonForTelonsting
  protelonctelond Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> contelonntSelonrvicelon;
  protelonctelond TrelonndingContelonntSelonrvicelon.SelonrvicelonToClielonnt contelonntClielonnt;
  protelonctelond Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> melontadataSelonrvicelon;
  protelonctelond TrelonndsMelontadataSelonrvicelon.SelonrvicelonToClielonnt melontadataClielonnt;

  @VisiblelonForTelonsting
  protelonctelond TrelonndsUpdatelonr trelonndsUpdatelonr;

  /**
   * Relonturns an instancelon of TrelonndsThriftDataSelonrvicelonManagelonr.
   * @param selonrvicelonIdelonntifielonr Thelon selonrvicelon that wants to call
   * into Trelonnd's selonrvicelons.
   * @param numRelontrielons Thelon numbelonr of relontrielons in thelon elonvelonnt of
   * relonquelonst failurelons.
   * @param relonquelonstTimelonout Thelon amount of timelon welon wait belonforelon welon considelonr a
   * a relonquelonst as failelond.
   * @param initTrelonndsCachelonDelonlay How long to wait belonforelon thelon initial
   * filling of thelon Trelonnds cachelon in milliselonconds.
   * @param relonloadIntelonrval How oftelonn to relonfrelonsh thelon cachelon with updatelond trelonnds.
   * @param trelonndsCachelonList Thelon cachelon of trelonnds.
   * @relonturn An instancelon of TrelonndsThriftDataSelonrvicelonManagelonr configurelond
   * with relonspelonct to thelon params providelond.
   */
  public static TrelonndsThriftDataSelonrvicelonManagelonr nelonwInstancelon(
      SelonrvicelonIdelonntifielonr selonrvicelonIdelonntifielonr,
      int numRelontrielons,
      Duration relonquelonstTimelonout,
      Duration initTrelonndsCachelonDelonlay,
      Duration relonloadIntelonrval,
      List<NGramCachelon> trelonndsCachelonList) {
    relonturn nelonw TrelonndsThriftDataSelonrvicelonManagelonr(
        selonrvicelonIdelonntifielonr,
        numRelontrielons,
        relonquelonstTimelonout,
        initTrelonndsCachelonDelonlay,
        relonloadIntelonrval,
        trelonndsCachelonList);
  }

  /**
   * Relonsumelon auto relonfrelonsh. Always callelond in constructor. Can belon invokelond aftelonr a
   * stopAuthRelonfrelonsh call to relonsumelon auto relonfrelonshing. Invoking it aftelonr shutDown is undelonfinelond.
   */
  public synchronizelond void startAutoRelonfrelonsh() {
    if (schelondulelonr == null) {
      schelondulelonr = elonxeloncutors.nelonwSinglelonThrelonadSchelondulelondelonxeloncutor(
          nelonw ThrelonadFactoryBuildelonr().selontDaelonmon(truelon).selontNamelonFormat(
              "trelonnds-data-relonfrelonshelonr[%d]").build());
      schelondulelonr.schelondulelonAtFixelondRatelon(
          trelonndsUpdatelonr,
          relonfrelonshDelonlayDuration.inSelonconds(),
          relonloadIntelonrvalDuration.inSelonconds(),
          TimelonUnit.SelonCONDS);
    }
  }

  /**
   * Stop auto relonfrelonsh. Wait for thelon currelonnt elonxeloncution threlonad to finish.
   * This is a blocking call.
   */
  public synchronizelond void stopAutoRelonfrelonsh() {
    if (schelondulelonr != null) {
      schelondulelonr.shutdown(); // Disablelon nelonw tasks from beloning submittelond
      try {
        // Wait a whilelon for elonxisting tasks to telonrminatelon
        if (!schelondulelonr.awaitTelonrmination(DelonFAULT_TIMelon_TO_KILL_SelonC, TimelonUnit.SelonCONDS)) {
          schelondulelonr.shutdownNow(); // Cancelonl currelonntly elonxeloncuting tasks
          // Wait a whilelon for tasks to relonspond to beloning cancelonllelond
          if (!schelondulelonr.awaitTelonrmination(DelonFAULT_TIMelon_TO_KILL_SelonC, TimelonUnit.SelonCONDS)) {
            LOG.info("elonxeloncutor threlonad pool did not telonrminatelon.");
          }
        }
      } catch (Intelonrruptelondelonxcelonption ielon) {
        // (Relon-)Cancelonl if currelonnt threlonad also intelonrruptelond
        schelondulelonr.shutdownNow();
        // Prelonselonrvelon intelonrrupt status
        Threlonad.currelonntThrelonad().intelonrrupt();
      }
      schelondulelonr = null;
    }
  }

  /** Shuts down thelon managelonr. */
  public void shutDown() {
    stopAutoRelonfrelonsh();
    // clelonar thelon cachelon
    for (NGramCachelon cachelon : trelonndsCachelonList) {
      cachelon.clelonar();
    }

    if (contelonntSelonrvicelon != null) {
      contelonntSelonrvicelon.closelon();
    }

    if (melontadataSelonrvicelon != null) {
      melontadataSelonrvicelon.closelon();
    }
  }

  privatelon TrelonndsThriftDataSelonrvicelonManagelonr(
      SelonrvicelonIdelonntifielonr selonrvicelonIdelonntifielonr,
      int numRelontrielons,
      Duration relonquelonstTimelonoutMS,
      Duration relonfrelonshDelonlayDuration,
      Duration relonloadIntelonrvalDuration,
      List<NGramCachelon> trelonndsCachelonList) {
    this.numRelontrielons = numRelontrielons;
    this.relonquelonstTimelonout = relonquelonstTimelonoutMS;
    this.relonfrelonshDelonlayDuration = relonfrelonshDelonlayDuration;
    this.relonloadIntelonrvalDuration = relonloadIntelonrvalDuration;
    this.selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr;
    this.trelonndsCachelonList = Prelonconditions.chelonckNotNull(trelonndsCachelonList);
    trelonndsUpdatelonr = nelonw TrelonndsUpdatelonr();
    melontadataSelonrvicelon = buildMelontadataSelonrvicelon();
    melontadataClielonnt = buildMelontadataClielonnt(melontadataSelonrvicelon);
    contelonntSelonrvicelon = buildContelonntSelonrvicelon();
    contelonntClielonnt = buildContelonntClielonnt(contelonntSelonrvicelon);
  }

  @VisiblelonForTelonsting
  protelonctelond Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> buildContelonntSelonrvicelon() {
    ClielonntBuildelonr<
        ThriftClielonntRelonquelonst,
        bytelon[], ClielonntConfig.Yelons,
        ClielonntConfig.Yelons,
        ClielonntConfig.Yelons
        >
        buildelonr = ClielonntBuildelonr.gelont()
          .stack(ThriftMux.clielonnt())
          .namelon("trelonnds_thrift_data_selonrvicelon_managelonr_contelonnt")
          .delonst("")
          .relontrielons(numRelontrielons)
          .relonportTo(DelonfaultStatsReloncelonivelonr.gelont())
          .tcpConnelonctTimelonout(relonquelonstTimelonout)
          .relonquelonstTimelonout(relonquelonstTimelonout);
    ClielonntBuildelonr mtlsBuildelonr =
        nelonw MtlsClielonntBuildelonr.MtlsClielonntBuildelonrSyntax<>(buildelonr).mutualTls(selonrvicelonIdelonntifielonr);

    relonturn ClielonntBuildelonr.safelonBuild(mtlsBuildelonr);
  }

  @VisiblelonForTelonsting
  protelonctelond TrelonndingContelonntSelonrvicelon.SelonrvicelonToClielonnt buildContelonntClielonnt(
      Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> selonrvicelon) {
    relonturn nelonw TrelonndingContelonntSelonrvicelon.SelonrvicelonToClielonnt(selonrvicelon);
  }

  @VisiblelonForTelonsting
  protelonctelond Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> buildMelontadataSelonrvicelon() {
    ClielonntBuildelonr<
        ThriftClielonntRelonquelonst,
        bytelon[],
        ClielonntConfig.Yelons,
        ClielonntConfig.Yelons,
        ClielonntConfig.Yelons
        >
        buildelonr = ClielonntBuildelonr.gelont()
          .stack(ThriftMux.clielonnt())
          .namelon("trelonnds_thrift_data_selonrvicelon_managelonr_melontadata")
          .delonst("")
          .relontrielons(numRelontrielons)
          .relonportTo(DelonfaultStatsReloncelonivelonr.gelont())
          .tcpConnelonctTimelonout(relonquelonstTimelonout)
          .relonquelonstTimelonout(relonquelonstTimelonout);
    ClielonntBuildelonr mtlsBuildelonr =
        nelonw MtlsClielonntBuildelonr.MtlsClielonntBuildelonrSyntax<>(buildelonr).mutualTls(selonrvicelonIdelonntifielonr);

    relonturn ClielonntBuildelonr.safelonBuild(mtlsBuildelonr);
  }

  @VisiblelonForTelonsting
  protelonctelond TrelonndsMelontadataSelonrvicelon.SelonrvicelonToClielonnt buildMelontadataClielonnt(
      Selonrvicelon<ThriftClielonntRelonquelonst, bytelon[]> selonrvicelon) {
    relonturn nelonw TrelonndsMelontadataSelonrvicelon.SelonrvicelonToClielonnt(selonrvicelon);
  }

  /**
   * Updatelonr that felontchelons availablelon woelonids and correlonsponding trelonnding telonrms.
   */
  @VisiblelonForTelonsting
  protelonctelond class TrelonndsUpdatelonr implelonmelonnts Runnablelon {
    @Ovelonrridelon
    public void run() {
      populatelonCachelonFromTrelonndsSelonrvicelon();
    }

    privatelon Futurelon<BoxelondUnit> populatelonCachelonFromTrelonndsSelonrvicelon() {
      long startTimelon = Systelonm.currelonntTimelonMillis();
      AtomicLong numTrelonndsReloncelonivelond = nelonw AtomicLong(0);
      relonturn melontadataClielonnt.gelontAvailablelon().flatMap(locations -> {
        if (locations == null) {
          gelontAvailablelonFailurelonCountelonr.increlonmelonnt();
          LOG.warn("Failelond to gelont woelonids from trelonnds.");
          relonturn Futurelon.valuelon(BoxelondUnit.UNIT);
        }
        gelontAvailablelonSuccelonssCountelonr.increlonmelonnt();
        relonturn populatelonCachelonFromTrelonndLocations(locations, numTrelonndsReloncelonivelond);
      }).onFailurelon(throwablelon -> {
        LOG.info("Updatelon failelond", throwablelon);
        updatelonFailurelonCountelonr.increlonmelonnt();
        relonturn BoxelondUnit.UNIT;
      }).elonnsurelon(() -> {
        logRelonfrelonshStatus(startTimelon, numTrelonndsReloncelonivelond);
        relonturn BoxelondUnit.UNIT;
      });
    }

    privatelon Futurelon<BoxelondUnit> populatelonCachelonFromTrelonndLocations(
        List<Location> locations,
        AtomicLong numTrelonndsReloncelonivelond) {
      List<Futurelon<TrelonndsPlusRelonsponselon>> trelonndsPlusFuturelons = locations.strelonam()
          .map(location -> makelonTrelonndsPlusRelonquelonst(location))
          .collelonct(Collelonctors.toList());

      Futurelon<List<Try<TrelonndsPlusRelonsponselon>>> trelonndsPlusFuturelon =
          Futurelon.collelonctToTry(trelonndsPlusFuturelons);
      relonturn trelonndsPlusFuturelon.map(tryRelonsponselons -> {
        populatelonCachelonFromRelonsponselons(tryRelonsponselons, numTrelonndsReloncelonivelond);
        relonturn BoxelondUnit.UNIT;
      });
    }

    privatelon Futurelon<TrelonndsPlusRelonsponselon> makelonTrelonndsPlusRelonquelonst(Location location) {
      TrelonndsPlusRelonquelonst relonquelonst = nelonw TrelonndsPlusRelonquelonst()
          .selontWoelonid(location.gelontWoelonid())
          .selontMaxTrelonnds(MAX_TRelonNDS_PelonR_WOelonID);
      long startTimelon = Systelonm.currelonntTimelonMillis();
      relonturn contelonntClielonnt.gelontTrelonndsPlus(relonquelonst)
          .onSuccelonss(relonsponselon -> {
            gelontTrelonndsSuccelonssCountelonr.increlonmelonnt();
            relonturn BoxelondUnit.UNIT;
          }).onFailurelon(throwablelon -> {
            gelontTrelonndsFailurelonCountelonr.increlonmelonnt();
            relonturn BoxelondUnit.UNIT;
          });
    }

    privatelon void populatelonCachelonFromRelonsponselons(
        List<Try<TrelonndsPlusRelonsponselon>> tryRelonsponselons,
        AtomicLong numTrelonndsReloncelonivelond) {
      Selont<String> trelonndStrings = Selonts.nelonwHashSelont();

      for (Try<TrelonndsPlusRelonsponselon> tryRelonsponselon : tryRelonsponselons) {
        if (tryRelonsponselon.isThrow()) {
          LOG.warn("Failelond to felontch trelonnds:" + tryRelonsponselon.toString());
          continuelon;
        }

        TrelonndsPlusRelonsponselon trelonndsPlusRelonsponselon = tryRelonsponselon.gelont();
        numTrelonndsReloncelonivelond.addAndGelont(trelonndsPlusRelonsponselon.modulelons.sizelon());
        for (Modulelon modulelon : trelonndsPlusRelonsponselon.modulelons) {
          trelonndStrings.add(modulelon.gelontTrelonnd().namelon);
        }
      }

      for (NGramCachelon cachelon : trelonndsCachelonList) {
        cachelon.addAll(trelonndStrings);
      }
    }
  }

  privatelon void logRelonfrelonshStatus(long startTimelon, AtomicLong numTrelonndsReloncelonivelond) {
    LOG.info(String.format("Relonfrelonsh donelon in [%dms] :\nfelontchSuccelonss[%d] felontchFailurelon[%d] "
            + "updatelonFailurelon[%d] num trelonnds reloncelonivelond [%d]",
        Systelonm.currelonntTimelonMillis() - startTimelon,
        gelontTrelonndsSuccelonssCountelonr.gelont(),
        gelontTrelonndsFailurelonCountelonr.gelont(),
        updatelonFailurelonCountelonr.gelont(),
        numTrelonndsReloncelonivelond.gelont()));
  }
}
