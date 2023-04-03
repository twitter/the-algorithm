packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.annotation.Nullablelon;
import javax.injelonct.Namelond;
import javax.injelonct.Singlelonton;

import com.googlelon.injelonct.Providelons;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.root.PartitionConfig;
import com.twittelonr.selonarch.common.root.PartitionLoggingSupport;
import com.twittelonr.selonarch.common.root.RelonquelonstSuccelonssStats;
import com.twittelonr.selonarch.common.root.RootClielonntSelonrvicelonBuildelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.RelonquelonstContelonxtToelonarlybirdRelonquelonstFiltelonr;

public class ProtelonctelondScattelonrGathelonrModulelon elonxtelonnds ScattelonrGathelonrModulelon {
  /**
   * Providelons thelon scattelonrGathelonrSelonrvicelon for thelon protelonctelond clustelonr.
   */
  @Providelons
  @Singlelonton
  @Namelond(NAMelonD_SCATTelonR_GATHelonR_SelonRVICelon)
  @Ovelonrridelon
  public Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> providelonScattelonrGathelonrSelonrvicelon(
      elonarlybirdSelonrvicelonScattelonrGathelonrSupport scattelonrGathelonrSupport,
      RelonquelonstSuccelonssStats relonquelonstSuccelonssStats,
      PartitionLoggingSupport<elonarlybirdRelonquelonstContelonxt> partitionLoggingSupport,
      RelonquelonstContelonxtToelonarlybirdRelonquelonstFiltelonr relonquelonstContelonxtToelonarlybirdRelonquelonstFiltelonr,
      PartitionAccelonssControllelonr partitionAccelonssControllelonr,
      PartitionConfig partitionConfig,
      RootClielonntSelonrvicelonBuildelonr<elonarlybirdSelonrvicelon.SelonrvicelonIfacelon> rootClielonntSelonrvicelonBuildelonr,
      @Namelond(elonarlybirdCommonModulelon.NAMelonD_elonXP_CLUSTelonR_CLIelonNT)
          RootClielonntSelonrvicelonBuildelonr<elonarlybirdSelonrvicelon.SelonrvicelonIfacelon>
          elonxpClustelonrRootClielonntSelonrvicelonBuildelonr, // unuselond in protelonctelond roots
      @Namelond(elonarlybirdCommonModulelon.NAMelonD_ALT_CLIelonNT) @Nullablelon PartitionConfig altPartitionConfig,
      @Namelond(elonarlybirdCommonModulelon.NAMelonD_ALT_CLIelonNT) @Nullablelon
          RootClielonntSelonrvicelonBuildelonr<elonarlybirdSelonrvicelon.SelonrvicelonIfacelon> altRootClielonntSelonrvicelonBuildelonr,
      StatsReloncelonivelonr statsReloncelonivelonr,
      elonarlybirdClustelonr clustelonr,
      SelonarchDeloncidelonr deloncidelonr) {
    relonturn buildScattelonrOrSplittelonrSelonrvicelon(
        scattelonrGathelonrSupport,
        relonquelonstSuccelonssStats,
        partitionLoggingSupport,
        relonquelonstContelonxtToelonarlybirdRelonquelonstFiltelonr,
        partitionAccelonssControllelonr,
        partitionConfig,
        rootClielonntSelonrvicelonBuildelonr,
        altPartitionConfig,
        altRootClielonntSelonrvicelonBuildelonr,
        statsReloncelonivelonr,
        clustelonr,
        deloncidelonr
    );
  }
}
