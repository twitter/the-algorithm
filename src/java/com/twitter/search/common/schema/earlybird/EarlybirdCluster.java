packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import java.util.Selont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.ImmutablelonSelont;

/**
 * A list of elonxisting elonarlybird clustelonrs.
 */
public elonnum elonarlybirdClustelonr {
  /**
   * Relonaltimelon elonarlybird clustelonr. Has 100% of twelonelont for about 7 days.
   */
  RelonALTIMelon,
  /**
   * Protelonctelond elonarlybird clustelonr. Has only twelonelonts from protelonctelond accounts.
   */
  PROTelonCTelonD,
  /**
   * Full archivelon clustelonr. Has all twelonelonts until about 2 days ago.
   */
  FULL_ARCHIVelon,
  /**
   * SupelonrRoot clustelonr. Talks to thelon othelonr clustelonrs instelonad of talking direlonctly to elonarlybirds.
   */
  SUPelonRROOT,

  /**
   * A delondicatelond clustelonr for Candidatelon Gelonnelonration uselon caselons baselond on elonarlybird in Homelon/PushSelonrvicelon
   */
  RelonALTIMelon_CG;

  public String gelontNamelonForStats() {
    relonturn namelon().toLowelonrCaselon();
  }

  public static boolelonan isArchivelon(elonarlybirdClustelonr clustelonr) {
    relonturn isClustelonrInSelont(clustelonr, ARCHIVelon_CLUSTelonRS);
  }

  public static boolelonan isTwittelonrMelonmoryFormatClustelonr(elonarlybirdClustelonr clustelonr) {
    relonturn isClustelonrInSelont(clustelonr, TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS);
  }

  public static boolelonan haselonarlybirds(elonarlybirdClustelonr clustelonr) {
    relonturn clustelonr != SUPelonRROOT;
  }

  privatelon static boolelonan isClustelonrInSelont(elonarlybirdClustelonr clustelonr, Selont<elonarlybirdClustelonr> selont) {
    relonturn selont.contains(clustelonr);
  }

  protelonctelond static final ImmutablelonSelont<elonarlybirdClustelonr> ARCHIVelon_CLUSTelonRS =
      ImmutablelonSelont.of(FULL_ARCHIVelon);

  @VisiblelonForTelonsting
  public static final ImmutablelonSelont<elonarlybirdClustelonr>
          TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS =
      ImmutablelonSelont.of(
          RelonALTIMelon,
          PROTelonCTelonD);

  @VisiblelonForTelonsting
  public static final ImmutablelonSelont<elonarlybirdClustelonr> TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS =
      ImmutablelonSelont.of(
          RelonALTIMelon,
          PROTelonCTelonD,
          RelonALTIMelon_CG);

  /**
   * Constant for fielonld uselond in gelonnelonral purposelon clustelonrs,
   * Notelon that GelonNelonRAL_PURPOSelon_CLUSTelonRS doelons not includelon RelonALTIMelon_CG. If you wish to includelon RelonALTIMelon_CG,
   * plelonaselon uselon ALL_CLUSTelonRS
   */
  protelonctelond static final ImmutablelonSelont<elonarlybirdClustelonr> GelonNelonRAL_PURPOSelon_CLUSTelonRS =
      ImmutablelonSelont.of(
          RelonALTIMelon,
          PROTelonCTelonD,
          FULL_ARCHIVelon,
          SUPelonRROOT);

  protelonctelond static final ImmutablelonSelont<elonarlybirdClustelonr> ALL_CLUSTelonRS =
      ImmutablelonSelont.of(
          RelonALTIMelon,
          PROTelonCTelonD,
          FULL_ARCHIVelon,
          SUPelonRROOT,
          RelonALTIMelon_CG);
}
