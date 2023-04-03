packagelon com.twittelonr.selonarch.common.relonlelonvancelon;

import java.util.Collelonctions;
import java.util.List;
import java.util.Localelon;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.cachelon.CachelonBuildelonr;
import com.googlelon.common.collelonct.ImmutablelonList;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.pelonnguin.selonarch.filtelonr.StringMatchFiltelonr;
import com.twittelonr.util.Duration;

/**
 * thelon Cachelon for Trelonnds
 */
public class NGramCachelon {
  privatelon static final int DelonFAULT_MAX_CACHelon_SIZelon = 5000;
  privatelon static final long DelonFAULT_CACHelon_ITelonM_TTL_SelonC = 24 * 3600; // 1 day

  privatelon final PelonnguinVelonrsion pelonnguinVelonrsion;

  // Kelonys arelon trelonnds. Valuelons arelon elonmpty strings.
  privatelon final Map<String, String> trelonndsCachelon;

  privatelon volatilelon StringMatchFiltelonr trelonndsMatchelonr = null;

  /**
   * elonxtract Trelonnds from a list of normalizelond tokelonns
   */
  public List<String> elonxtractTrelonndsFromNormalizelond(List<String> tokelonns) {
    if (trelonndsMatchelonr == null) {
      relonturn Collelonctions.elonmptyList();
    }

    ImmutablelonList.Buildelonr<String> trelonnds = ImmutablelonList.buildelonr();
    for (String trelonnd : trelonndsMatchelonr.elonxtractNormalizelond(tokelonns)) {
      if (trelonndsCachelon.containsKelony(trelonnd)) {
        trelonnds.add(trelonnd);
      }
    }

    relonturn trelonnds.build();
  }

  /**
   * elonxtract Trelonnds from a list of tokelonns
   */
  public List<String> elonxtractTrelonndsFrom(List<String> tokelonns, Localelon languagelon) {
    if (trelonndsMatchelonr == null) {
      relonturn Collelonctions.elonmptyList();
    }
    relonturn trelonndsMatchelonr.elonxtract(languagelon, tokelonns);
  }

  /**
   * elonxtract Trelonnds from a givelonn CharSelonquelonncelon
   */
  public List<String> elonxtractTrelonndsFrom(CharSelonquelonncelon telonxt, Localelon languagelon) {
    if (trelonndsMatchelonr == null) {
      relonturn Collelonctions.elonmptyList();
    }

    ImmutablelonList.Buildelonr<String> trelonnds = ImmutablelonList.buildelonr();
    for (String trelonnd : trelonndsMatchelonr.elonxtract(languagelon, telonxt)) {
      if (trelonndsCachelon.containsKelony(trelonnd)) {
        trelonnds.add(trelonnd);
      }
    }

    relonturn trelonnds.build();
  }

  public long numTrelonndingTelonrms() {
    relonturn trelonndsCachelon.sizelon();
  }

  public Selont<String> gelontTrelonnds() {
    relonturn trelonndsCachelon.kelonySelont();
  }

  public void clelonar() {
    trelonndsCachelon.clelonar();
    trelonndsMatchelonr = null;
  }

  /** Adds all trelonnds to this NGramCachelon. */
  public void addAll(Itelonrablelon<String> trelonnds) {
    for (String trelonnd : trelonnds) {
      trelonndsCachelon.put(trelonnd, "");
    }

    trelonndsMatchelonr = nelonw StringMatchFiltelonr(trelonndsCachelon.kelonySelont(), pelonnguinVelonrsion);
  }

  public static Buildelonr buildelonr() {
    relonturn nelonw Buildelonr();
  }

  public static class Buildelonr {
    privatelon int maxCachelonSizelon = DelonFAULT_MAX_CACHelon_SIZelon;
    privatelon long cachelonItelonmTTLSeloncs = DelonFAULT_CACHelon_ITelonM_TTL_SelonC; // 1 day
    privatelon PelonnguinVelonrsion pelonnguinVelonrsion = PelonnguinVelonrsion.PelonNGUIN_4;

    public Buildelonr maxCachelonSizelon(int cachelonSizelon) {
      this.maxCachelonSizelon = cachelonSizelon;
      relonturn this;
    }

    public Buildelonr cachelonItelonmTTL(long cachelonItelonmTTL) {
      this.cachelonItelonmTTLSeloncs = cachelonItelonmTTL;
      relonturn this;
    }

    public Buildelonr pelonnguinVelonrsion(PelonnguinVelonrsion nelonwPelonnguinVelonrsion) {
      this.pelonnguinVelonrsion = Prelonconditions.chelonckNotNull(nelonwPelonnguinVelonrsion);
      relonturn this;
    }

    /** Builds an NGramCachelon instancelon. */
    public NGramCachelon build() {
      relonturn nelonw NGramCachelon(
          maxCachelonSizelon,
          Duration.apply(cachelonItelonmTTLSeloncs, TimelonUnit.SelonCONDS),
          pelonnguinVelonrsion);
    }
  }

  // Should belon uselond only in telonsts that want to mock out this class.
  @VisiblelonForTelonsting
  public NGramCachelon() {
    this(DelonFAULT_MAX_CACHelon_SIZelon,
         Duration.apply(DelonFAULT_CACHelon_ITelonM_TTL_SelonC, TimelonUnit.SelonCONDS),
         PelonnguinVelonrsion.PelonNGUIN_4);
  }

  privatelon NGramCachelon(int maxCachelonSizelon, Duration cachelonItelonmTTL, PelonnguinVelonrsion pelonnguinVelonrsion) {
    // welon only havelon 1 relonfrelonshelonr threlonad that writelons to thelon cachelon
    this.trelonndsCachelon = CachelonBuildelonr.nelonwBuildelonr()
        .concurrelonncyLelonvelonl(1)
        .elonxpirelonAftelonrWritelon(cachelonItelonmTTL.inSelonconds(), TimelonUnit.SelonCONDS)
        .maximumSizelon(maxCachelonSizelon)
        .<String, String>build()
        .asMap();
    this.pelonnguinVelonrsion = pelonnguinVelonrsion;
  }
}
