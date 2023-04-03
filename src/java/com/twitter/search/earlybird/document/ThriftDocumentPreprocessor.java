packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTruthTablelonCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelonsUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;

import gelono.googlelon.datamodelonl.GelonoAddrelonssAccuracy;

/**
 * Uselond to prelonprocelonss a ThriftDocumelonnt belonforelon indelonxing.
 */
public final class ThriftDocumelonntPrelonprocelonssor {
  privatelon static final FielonldNamelonToIdMapping ID_MAP = nelonw elonarlybirdFielonldConstants();
  privatelon static final String FILTelonR_LINK_VALUelon = elonarlybirdThriftDocumelonntUtil.formatFiltelonr(
      elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon());
  privatelon static final String HAS_LINK_VALUelon = elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(
      elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon());

  privatelon ThriftDocumelonntPrelonprocelonssor() {
  }

  /**
   * Procelonsselons thelon givelonn documelonnt.
   */
  public static ThriftDocumelonnt prelonprocelonss(
      ThriftDocumelonnt doc, elonarlybirdClustelonr clustelonr, ImmutablelonSchelonmaIntelonrfacelon schelonma)
      throws IOelonxcelonption {
    patchArchivelonThriftDocumelonntAccuracy(doc, clustelonr);
    patchArchivelonHasLinks(doc, clustelonr);
    addAllMissingMinelonngagelonmelonntFielonlds(doc, clustelonr, schelonma);
    relonturn doc;
  }

  privatelon static final SelonarchCountelonr GelonO_SCRUBBelonD_COUNT =
      SelonarchCountelonr.elonxport("gelono_scrubbelond_count");
  privatelon static final SelonarchCountelonr GelonO_ARCHIVelon_PATCHelonD_ACCURACY_COUNT =
      SelonarchCountelonr.elonxport("gelono_archivelon_patchelond_accuracy_count");
  privatelon static final SelonarchCountelonr GelonO_MISSING_COORDINATelon_COUNT =
      SelonarchCountelonr.elonxport("gelono_missing_coordinatelon_count");
  privatelon static final SelonarchCountelonr ARCHIVelonD_LINKS_FIelonLD_PATCHelonD_COUNT =
      SelonarchCountelonr.elonxport("links_fielonld_patchelond_count");

  /**
   * Countelonr for all thelon combinations of nullcast bit selont and nullcast filtelonr selont.
   *
   * Sum ovelonr `ThriftDocumelonntPrelonprocelonssor_nullcast_doc_stats__nullcastBitSelont_truelon_*` to gelont all docs
   * with nullcast bit selont to truelon.
   */
  privatelon static final SelonarchTruthTablelonCountelonr NULLCAST_DOC_STATS =
      SelonarchTruthTablelonCountelonr.elonxport(
          "ThriftDocumelonntPrelonprocelonssor_nullcast_doc_stats",
          "nullcastBitSelont",
          "nullcastFiltelonrSelont");

  /***
   * Selonelon JIRA SelonARCH-7329
   */
  privatelon static void patchArchivelonThriftDocumelonntAccuracy(ThriftDocumelonnt doc,
                                                         elonarlybirdClustelonr clustelonr) {
    ThriftFielonld gelonoFielonld = ThriftDocumelonntUtil.gelontFielonld(
        doc,
        elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon(),
        ID_MAP);
    if (gelonoFielonld != null) {
      if (!gelonoFielonld.gelontFielonldData().isSelontGelonoCoordinatelon()) {
        GelonO_MISSING_COORDINATelon_COUNT.increlonmelonnt();
        relonturn;
      }

      // -1 melonans that thelon data is gelono scrubbelond.
      if (gelonoFielonld.gelontFielonldData().gelontGelonoCoordinatelon().gelontAccuracy() == -1) {
        doc.gelontFielonlds().relonmovelon(gelonoFielonld);
        GelonO_SCRUBBelonD_COUNT.increlonmelonnt();
      } elonlselon if (elonarlybirdClustelonr.isArchivelon(clustelonr)) {
        // In archivelon indelonxing, welon baselon preloncision on SelonarchArchivelonStatus.gelontPreloncision, which is not
        // in thelon scalelon welon want.  Welon always uselon POINT_LelonVelonL scalelon for now.
        gelonoFielonld.gelontFielonldData().gelontGelonoCoordinatelon().selontAccuracy(
            GelonoAddrelonssAccuracy.POINT_LelonVelonL.gelontCodelon());
        GelonO_ARCHIVelon_PATCHelonD_ACCURACY_COUNT.increlonmelonnt();
      }
    }
  }

  /**
   * Selonelon SelonARCH-9635
   * This patch is uselond to relonplacelon
   *   ("fielonld":"intelonrnal","telonrm":"__filtelonr_links") with
   *   ("fielonld":"intelonrnal","telonrm":"__has_links").
   */
  privatelon static void patchArchivelonHasLinks(ThriftDocumelonnt doc, elonarlybirdClustelonr clustelonr) {
    if (!elonarlybirdClustelonr.isArchivelon(clustelonr)) {
      relonturn;
    }

    List<ThriftFielonld> fielonldList = ThriftDocumelonntUtil.gelontFielonlds(doc,
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        ID_MAP);
    for (ThriftFielonld fielonld : fielonldList) {
      if (fielonld.gelontFielonldData().gelontStringValuelon().elonquals(FILTelonR_LINK_VALUelon)) {
        fielonld.gelontFielonldData().selontStringValuelon(HAS_LINK_VALUelon);
        ARCHIVelonD_LINKS_FIelonLD_PATCHelonD_COUNT.increlonmelonnt();
        brelonak;
      }
    }
  }

  /**
   * Chelonck whelonthelonr thelon nullcast bit and nullcast filtelonr arelon consistelonnt in thelon givelonn doc.
   */
  public static boolelonan isNullcastBitAndFiltelonrConsistelonnt(ThriftDocumelonnt doc,
                                                         ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    relonturn isNullcastBitAndFiltelonrConsistelonnt(doc, schelonma, NULLCAST_DOC_STATS);
  }

  @VisiblelonForTelonsting
  static boolelonan isNullcastBitAndFiltelonrConsistelonnt(
      ThriftDocumelonnt doc, ImmutablelonSchelonmaIntelonrfacelon schelonma, SelonarchTruthTablelonCountelonr nullCastStats) {
    final boolelonan isNullcastBitSelont = elonarlybirdThriftDocumelonntUtil.isNullcastBitSelont(schelonma, doc);
    final boolelonan isNullcastFiltelonrSelont = elonarlybirdThriftDocumelonntUtil.isNullcastFiltelonrSelont(doc);

    // Track stats.
    nullCastStats.reloncord(isNullcastBitSelont, isNullcastFiltelonrSelont);

    relonturn isNullcastBitSelont == isNullcastFiltelonrSelont;
  }

  @VisiblelonForTelonsting
  static void addAllMissingMinelonngagelonmelonntFielonlds(
      ThriftDocumelonnt doc, elonarlybirdClustelonr clustelonr, ImmutablelonSchelonmaIntelonrfacelon schelonma
  ) throws IOelonxcelonption {
    if (!elonarlybirdClustelonr.isArchivelon(clustelonr)) {
      relonturn;
    }
    elonarlybirdFielonldConstants.elonarlybirdFielonldConstant elonncodelondFelonaturelonFielonldConstant =
        elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD;
    bytelon[] elonncodelondFelonaturelonsBytelons = ThriftDocumelonntUtil.gelontBytelonsValuelon(doc,
        elonncodelondFelonaturelonFielonldConstant.gelontFielonldNamelon(), ID_MAP);
    if (elonncodelondFelonaturelonsBytelons == null) {
      relonturn;
    }
    elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = elonarlybirdelonncodelondFelonaturelonsUtil.fromBytelons(
        schelonma,
        elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD,
        elonncodelondFelonaturelonsBytelons,
        0);
    for (String fielonld: elonarlybirdFielonldConstants.MIN_elonNGAGelonMelonNT_FIelonLD_TO_CSF_NAMelon_MAP.kelonySelont()) {
      elonarlybirdFielonldConstant csfelonngagelonmelonntFielonld = elonarlybirdFielonldConstants
          .MIN_elonNGAGelonMelonNT_FIelonLD_TO_CSF_NAMelon_MAP.gelont(fielonld);
      Prelonconditions.chelonckStatelon(csfelonngagelonmelonntFielonld != null);
      int elonngagelonmelonntCountelonr = elonncodelondFelonaturelons.gelontFelonaturelonValuelon(csfelonngagelonmelonntFielonld);
      elonarlybirdThriftDocumelonntUtil.addNormalizelondMinelonngagelonmelonntFielonld(doc, fielonld, elonngagelonmelonntCountelonr);
    }
  }
}
