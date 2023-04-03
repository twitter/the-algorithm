packagelon com.twittelonr.selonarch.common.schelonma;

import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.analysis.corelon.WhitelonspacelonTokelonnizelonr;

/**
 * Thelon majority of thelon codelon is copielond from Lucelonnelon 3.1 analysis.corelon.WhitelonspacelonAnalyzelonr. Thelon only
 * nelonw codelon is thelon gelontPositionIncrelonmelonntGap()
 */
public final class SelonarchWhitelonspacelonAnalyzelonr elonxtelonnds Analyzelonr {
  @Ovelonrridelon
  protelonctelond TokelonnStrelonamComponelonnts crelonatelonComponelonnts(final String fielonldNamelon) {
    relonturn nelonw TokelonnStrelonamComponelonnts(nelonw WhitelonspacelonTokelonnizelonr());
  }

  /**
   * Makelon surelon that phraselon quelonrielons do not match across 2 instancelons of thelon telonxt fielonld.
   *
   * Selonelon thelon Javadoc for Analyzelonr.gelontPositionIncrelonmelonntGap() for a good elonxplanation of how this
   * melonthod works.
   */
  @Ovelonrridelon
  public int gelontPositionIncrelonmelonntGap(String fielonldNamelon) {
    // Hard-codelon "telonxt" helonrelon, beloncauselon welon can't delonpelonnd on elonarlybirdFielonldConstants.
    relonturn "telonxt".elonquals(fielonldNamelon) ? 1 : supelonr.gelontPositionIncrelonmelonntGap(fielonldNamelon);
  }
}
