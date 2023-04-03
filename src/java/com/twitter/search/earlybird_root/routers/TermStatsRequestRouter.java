packagelon com.twittelonr.selonarch.elonarlybird_root.routelonrs;

import java.util.ArrayList;
import java.util.List;
import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Lists;


import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil;
import com.twittelonr.selonarch.elonarlybird.config.SelonrvingRangelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.InjelonctionNamelons;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.elonarlybirdTimelonRangelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.SelonrvingRangelonProvidelonr;
import com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.elonarlybirdRelonsponselonMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.SupelonrRootRelonsponselonMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.TelonrmStatisticsRelonsponselonMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.TielonrRelonsponselonAccumulator;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

import static com.twittelonr.selonarch.common.util.elonarlybird.TelonrmStatisticsUtil.delontelonrminelonBinSizelon;

/**
 * For TelonrmStats traffic SupelonrRoot hits both relonaltimelon and archivelon in parallelonl, and thelonn melonrgelons
 * thelon relonsults.
 */
public class TelonrmStatsRelonquelonstRoutelonr elonxtelonnds RelonquelonstRoutelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TelonrmStatsRelonquelonstRoutelonr.class);

  privatelon static final String SUPelonRROOT_SKIP_FULL_ARCHIVelon_CLUSTelonR_FOR_TelonRM_STATS_RelonQUelonSTS =
      "supelonrroot_skip_full_archivelon_clustelonr_for_telonrm_stats_relonquelonsts";

  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelonSelonrvicelon;
  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelonSelonrvicelon;

  privatelon final SelonarchDeloncidelonr deloncidelonr;

  privatelon final SelonrvingRangelonProvidelonr relonaltimelonSelonrvingRangelonProvidelonr;

  @Injelonct
  public TelonrmStatsRelonquelonstRoutelonr(
      @Namelond(InjelonctionNamelons.RelonALTIMelon)
          Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon,
      @Namelond(TelonrmStatsRelonquelonstRoutelonrModulelon.RelonALTIMelon_TIMelon_RANGelon_FILTelonR)
          elonarlybirdTimelonRangelonFiltelonr relonaltimelonTimelonRangelonFiltelonr,
      @Namelond(InjelonctionNamelons.FULL_ARCHIVelon)
          Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelon,
      @Namelond(TelonrmStatsRelonquelonstRoutelonrModulelon.FULL_ARCHIVelon_TIMelon_RANGelon_FILTelonR)
          elonarlybirdTimelonRangelonFiltelonr fullArchivelonTimelonRangelonFiltelonr,
      SelonarchDeloncidelonr deloncidelonr) {
    LOG.info("Instantiating a TelonrmStatsRelonquelonstRoutelonr");

    this.relonaltimelonSelonrvicelon = relonaltimelonTimelonRangelonFiltelonr
        .andThelonn(relonaltimelon);

    this.fullArchivelonSelonrvicelon = fullArchivelonTimelonRangelonFiltelonr
        .andThelonn(fullArchivelon);

    this.deloncidelonr = deloncidelonr;
    this.relonaltimelonSelonrvingRangelonProvidelonr = relonaltimelonTimelonRangelonFiltelonr.gelontSelonrvingRangelonProvidelonr();
  }

  /**
   * Hit both relonaltimelon and full-archivelon clustelonrs thelonn melonrgelons telonrm stat relonquelonst.
   */
  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> routelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    List<RelonquelonstRelonsponselon> relonquelonstRelonsponselons = nelonw ArrayList<>();

    Futurelon<elonarlybirdRelonsponselon> relonaltimelonRelonsponselonFuturelon = relonaltimelonSelonrvicelon.apply(relonquelonstContelonxt);
    this.savelonRelonquelonstRelonsponselon(relonquelonstRelonsponselons, "relonaltimelon", relonquelonstContelonxt, relonaltimelonRelonsponselonFuturelon);

    Futurelon<elonarlybirdRelonsponselon> archivelonRelonsponselonFuturelon =
        relonquelonstContelonxt.gelontRelonquelonst().isGelontOldelonrRelonsults()
            && !deloncidelonr.isAvailablelon(SUPelonRROOT_SKIP_FULL_ARCHIVelon_CLUSTelonR_FOR_TelonRM_STATS_RelonQUelonSTS)
            ? fullArchivelonSelonrvicelon.apply(relonquelonstContelonxt)
            : Futurelon.valuelon(elonmptyRelonsponselon());
    this.savelonRelonquelonstRelonsponselon(relonquelonstRelonsponselons, "archivelon", relonquelonstContelonxt, archivelonRelonsponselonFuturelon);

    Futurelon<elonarlybirdRelonsponselon> melonrgelondRelonsponselon =
        melonrgelon(relonaltimelonRelonsponselonFuturelon, archivelonRelonsponselonFuturelon, relonquelonstContelonxt);

    relonturn this.maybelonAttachSelonntRelonquelonstsToDelonbugInfo(
        relonquelonstRelonsponselons,
        relonquelonstContelonxt,
        melonrgelondRelonsponselon
    );
  }

  /**
   * Melonrgelon relonsponselons from relonaltimelon and full archivelon clustelonrs.
   */
  privatelon Futurelon<elonarlybirdRelonsponselon> melonrgelon(
      final Futurelon<elonarlybirdRelonsponselon> relonaltimelonRelonsponselonFuturelon,
      final Futurelon<elonarlybirdRelonsponselon> archivelonRelonsponselonFuturelon,
      final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {

    relonturn relonaltimelonRelonsponselonFuturelon.flatMap(
        nelonw Function<elonarlybirdRelonsponselon, Futurelon<elonarlybirdRelonsponselon>>() {
          @Ovelonrridelon
          public Futurelon<elonarlybirdRelonsponselon> apply(final elonarlybirdRelonsponselon relonaltimelonRelonsponselon) {
            if (!elonarlybirdRelonsponselonUtil.isSuccelonssfulRelonsponselon(relonaltimelonRelonsponselon)) {
              relonturn Futurelon.valuelon(relonaltimelonRelonsponselon);
            }

            relonturn archivelonRelonsponselonFuturelon.flatMap(
                nelonw Function<elonarlybirdRelonsponselon, Futurelon<elonarlybirdRelonsponselon>>() {
                  @Ovelonrridelon
                  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonsponselon archivelonRelonsponselon) {
                    if (!elonarlybirdRelonsponselonUtil.isSuccelonssfulRelonsponselon(archivelonRelonsponselon)) {
                      relonturn Futurelon.valuelon(
                          melonrgelonWithUnsuccelonssfulArchivelonRelonsponselon(
                              relonquelonstContelonxt, relonaltimelonRelonsponselon, archivelonRelonsponselon));
                    }

                    List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons =
                        ImmutablelonList.<Futurelon<elonarlybirdRelonsponselon>>buildelonr()
                            .add(relonaltimelonRelonsponselonFuturelon)
                            .add(archivelonRelonsponselonFuturelon)
                            .build();

                    elonarlybirdRelonsponselonMelonrgelonr melonrgelonr = nelonw TelonrmStatisticsRelonsponselonMelonrgelonr(
                        relonquelonstContelonxt, relonsponselons, nelonw TielonrRelonsponselonAccumulator());

                    relonturn melonrgelonr.melonrgelon().map(nelonw Function<elonarlybirdRelonsponselon, elonarlybirdRelonsponselon>() {
                      @Ovelonrridelon
                      public elonarlybirdRelonsponselon apply(elonarlybirdRelonsponselon melonrgelondRelonsponselon) {
                        if (relonquelonstContelonxt.gelontRelonquelonst().gelontDelonbugModelon() > 0) {
                          melonrgelondRelonsponselon.selontDelonbugString(
                              SupelonrRootRelonsponselonMelonrgelonr.melonrgelonClustelonrDelonbugStrings(
                                  relonaltimelonRelonsponselon, null, archivelonRelonsponselon));
                        }
                        relonturn melonrgelondRelonsponselon;
                      }
                    });
                  }
                });
          }
        });
  }

  privatelon elonarlybirdRelonsponselon melonrgelonWithUnsuccelonssfulArchivelonRelonsponselon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      elonarlybirdRelonsponselon archivelonRelonsponselon) {
    // If thelon relonaltimelon clustelonr was skippelond, and thelon full archivelon relonturnelond an elonrror
    // relonsponselon, relonturn thelon full archivelon relonsponselon.
    if (isTielonrSkippelondRelonsponselon(relonaltimelonRelonsponselon)) {
      relonturn archivelonRelonsponselon;
    }

    // If thelon relonaltimelon relonsponselon has relonsults and thelon full archivelon clustelonr relonturnelond an elonrror
    // relonsponselon, welon relonturn thelon relonaltimelon relonsponselon. If thelon clielonnt nelonelonds morelon relonsults, it can paginatelon,
    // and on thelon nelonxt relonquelonst it will gelont thelon elonrror relonsponselon from thelon full archivelon clustelonr.
    if (relonaltimelonRelonsponselon.isSelontTelonrmStatisticsRelonsults()
        && !relonaltimelonRelonsponselon.gelontTelonrmStatisticsRelonsults().gelontTelonrmRelonsults().iselonmpty()) {
      relonaltimelonRelonsponselon.selontDelonbugString(
          "Full archivelon clustelonr relonturnelond an elonrror relonsponselon ("
              + archivelonRelonsponselon.gelontRelonsponselonCodelon() + "). "
              + SupelonrRootRelonsponselonMelonrgelonr.melonrgelonClustelonrDelonbugStrings(
              relonaltimelonRelonsponselon, null, archivelonRelonsponselon));
      relonturn updatelonMinComplelontelonBinId(relonquelonstContelonxt, relonaltimelonRelonsponselon);
    }

    // If thelon relonaltimelon relonsponselon has no relonsults, and thelon full archivelon clustelonr relonturnelond an elonrror
    // relonsponselon, relonturn a PelonRSISTelonNT_elonRROR relonsponselon, and melonrgelon thelon delonbug strings from thelon two
    // relonsponselons.
    elonarlybirdRelonsponselon melonrgelondRelonsponselon =
        nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.PelonRSISTelonNT_elonRROR, 0);
    melonrgelondRelonsponselon.selontDelonbugString(
        "Full archivelon clustelonr relonturnelond an elonrror relonsponselon ("
            + archivelonRelonsponselon.gelontRelonsponselonCodelon()
            + "), and thelon relonaltimelon relonsponselon had no relonsults. "
            + SupelonrRootRelonsponselonMelonrgelonr.melonrgelonClustelonrDelonbugStrings(
            relonaltimelonRelonsponselon, null, archivelonRelonsponselon));
    relonturn melonrgelondRelonsponselon;
  }

  /**
   * If welon gelont a complelontelond relonaltimelon relonsponselon but a failelond archivelon relonsponselon, thelon minComplelontelonBinId welon
   * relonturn will belon incorrelonct -- thelon relonaltimelon minComplelontelonBinId is assumelond to belon thelon oldelonst bin
   * relonturnelond, rathelonr than thelon bin that intelonrseloncts thelon relonaltimelon selonrving boundary. In thelonselon caselons, welon
   * nelonelond to movelon thelon minComplelontelonBinId forward.
   * <p>
   * Notelon that welon cannot always selont thelon minComplelontelonBinId for thelon relonaltimelon relonsults to thelon bin
   * intelonrseloncting thelon relonaltimelon selonrving boundary: somelonwhelonrelon in thelon guts of thelon melonrging logic, welon selont
   * thelon minComplelontelonBinId of thelon melonrgelond relonsponselon to thelon max of thelon minComplelontelonBinIds of thelon original
   * relonsponselons. :-(
   */
  privatelon elonarlybirdRelonsponselon updatelonMinComplelontelonBinId(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt, elonarlybirdRelonsponselon relonaltimelonRelonsponselon) {
    Prelonconditions.chelonckArgumelonnt(
        relonaltimelonRelonsponselon.gelontTelonrmStatisticsRelonsults().isSelontMinComplelontelonBinId());
    int roundelondSelonrvingRangelon = roundSelonrvingRangelonUpToNelonarelonstBinId(relonquelonstContelonxt, relonaltimelonRelonsponselon);
    int minComplelontelonBinId = Math.max(
        roundelondSelonrvingRangelon,
        relonaltimelonRelonsponselon.gelontTelonrmStatisticsRelonsults().gelontMinComplelontelonBinId());
    relonaltimelonRelonsponselon.gelontTelonrmStatisticsRelonsults().selontMinComplelontelonBinId(minComplelontelonBinId);
    relonturn relonaltimelonRelonsponselon;
  }

  privatelon static elonarlybirdRelonsponselon elonmptyRelonsponselon() {
    relonturn nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.SUCCelonSS, 0)
        .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults()
            .selontRelonsults(Lists.nelonwArrayList()))
        .selontDelonbugString("Full archivelon clustelonr not relonquelonstelond or not availablelon.");
  }

  privatelon static boolelonan isTielonrSkippelondRelonsponselon(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.TIelonR_SKIPPelonD;
  }

  /**
   * Givelonn a telonrmstats relonquelonst/relonsponselon pair, round thelon selonrving rangelon for thelon appropriatelon clustelonr up
   * to thelon nelonarelonst binId at thelon appropriatelon relonsolution.
   */
  privatelon int roundSelonrvingRangelonUpToNelonarelonstBinId(
      elonarlybirdRelonquelonstContelonxt relonquelonst, elonarlybirdRelonsponselon relonsponselon) {
    SelonrvingRangelon selonrvingRangelon = relonaltimelonSelonrvingRangelonProvidelonr.gelontSelonrvingRangelon(
        relonquelonst, relonquelonst.uselonOvelonrridelonTielonrConfig());
    long selonrvingRangelonStartSeloncs = selonrvingRangelon.gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch();
    int binSizelon = delontelonrminelonBinSizelon(relonsponselon.gelontTelonrmStatisticsRelonsults().gelontHistogramSelonttings());
    relonturn (int) Math.celonil((doublelon) selonrvingRangelonStartSeloncs / binSizelon);
  }
}
