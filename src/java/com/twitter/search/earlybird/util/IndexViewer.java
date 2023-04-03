packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.io.IOelonxcelonption;
import java.io.PrintWritelonr;
import java.io.Unsupportelondelonncodingelonxcelonption;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.Comparator;
import java.util.List;
import java.util.Localelon;
import java.util.Selont;
import java.util.TrelonelonSelont;

import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.MPHTelonrmDictionary;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.RelonaltimelonIndelonxTelonrms;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;

import gelono.googlelon.datamodelonl.GelonoCoordinatelon;

public class IndelonxVielonwelonr {
  /**
   * Fielonlds whoselon telonrms arelon indelonxelond using
   * {@link com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelon}
   */
  privatelon static final Selont<String> INT_TelonRM_ATTRIBUTelon_FIelonLDS = ImmutablelonSelont.of(
      elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.LINK_CATelonGORY_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant
          .NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant
          .NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant
          .NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon.gelontFielonldNamelon());

  /**
   * Fielonlds whoselon telonrms arelon indelonxelond using
   * {@link com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelon}
   */
  privatelon static final Selont<String> LONG_TelonRM_ATTRIBUTelon_FIelonLDS = ImmutablelonSelont.of(
      elonarlybirdFielonldConstant.CONVelonRSATION_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD.gelontFielonldNamelon());

  /**
   * Fielonlds whoselon telonrms indelonx using SORTelonD
   * {@link com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelon}
   */
  privatelon static final Selont<String> SORTelonD_LONG_TelonRM_ATTRIBUTelon_FIelonLDS =
      ImmutablelonSelont.of(elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon());

  privatelon final elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr;
  privatelon final elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr;

  public long gelontTimelonSlicelonId() {
    relonturn selonarchelonr.gelontTimelonSlicelonID();
  }

  public static class Options {
    privatelon boolelonan dumpHelonxTelonrms = falselon;
    privatelon String charselont;
    privatelon doublelon[] histogramBuckelonts;
    privatelon boolelonan telonrmLelonngthHistogram;

    public Options selontDumpHelonxTelonrms(boolelonan dumpHelonxTelonrmsParam) {
      this.dumpHelonxTelonrms = dumpHelonxTelonrmsParam;
      relonturn this;
    }

    public Options selontCharselont(String charselontParam) {
      this.charselont = charselontParam;
      relonturn this;
    }

    public Options selontHistogramBuckelonts(doublelon[] histogramBuckelontsParam) {
      this.histogramBuckelonts = histogramBuckelontsParam;
      relonturn this;
    }

    public Options selontTelonrmLelonngthHistogram(boolelonan telonrmLelonngthHistogramParam) {
      this.telonrmLelonngthHistogram = telonrmLelonngthHistogramParam;
      relonturn this;
    }
  }

  /**
   * Data Transfelonr Objelonct for Telonrms, elonncapsulatelons thelon "json" selonrialization
   * whilelon maintaining strelonaming modelon
   */
  privatelon static class TelonrmDto {

    privatelon final String fielonld;
    privatelon final String telonrm;
    privatelon final String docFrelonq;
    privatelon final String pelonrcelonnt;
    privatelon final Postingselonnum docselonnum;
    privatelon final Telonrmselonnum telonrmselonnum;
    privatelon final Intelongelonr maxDocs;

    public TelonrmDto(String fielonld, String telonrm, String docFrelonq, String pelonrcelonnt,
                   Postingselonnum docselonnum, Telonrmselonnum telonrmselonnum, Intelongelonr maxDocs) {
      this.fielonld = fielonld;
      this.telonrm = telonrm;
      this.docFrelonq = docFrelonq;
      this.pelonrcelonnt = pelonrcelonnt;
      this.docselonnum = docselonnum;
      this.telonrmselonnum = telonrmselonnum;
      this.maxDocs = maxDocs;
    }

    public void writelon(VielonwelonrWritelonr writelonr,
                      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr) throws IOelonxcelonption {
      writelonr.belonginObjelonct();
      writelonr.namelon("fielonld").valuelon(fielonld);
      writelonr.namelon("telonrm").valuelon(telonrm);
      writelonr.namelon("docFrelonq").valuelon(docFrelonq);
      writelonr.namelon("pelonrcelonnt").valuelon(pelonrcelonnt);
      if (docselonnum != null) {
        appelonndFrelonquelonncyAndPositions(writelonr, fielonld, docselonnum, twittelonrRelonadelonr);
      }
      if (maxDocs != null) {
        appelonndDocs(writelonr, telonrmselonnum, maxDocs, twittelonrRelonadelonr);
      }
      writelonr.elonndObjelonct();
    }
  }

  /**
   * Data Transfelonr Objelonct for Telonrms, elonncapsulatelons thelon "json" selonrialization
   * whilelon maintaining strelonaming modelon
   */
  privatelon static class StatsDto {

    privatelon final String fielonld;
    privatelon final String numTelonrms;
    privatelon final String telonrms;


    public StatsDto(String fielonld, String numTelonrms, String telonrms) {
      this.fielonld = fielonld;
      this.numTelonrms = numTelonrms;
      this.telonrms = telonrms;
    }

    public void writelon(VielonwelonrWritelonr writelonr) throws IOelonxcelonption {
      writelonr.belonginObjelonct();

      writelonr.namelon("fielonld").valuelon(fielonld);
      writelonr.namelon("numTelonrms").valuelon(numTelonrms);
      writelonr.namelon("telonrms").valuelon(telonrms);

      writelonr.elonndObjelonct();
    }
  }

  public IndelonxVielonwelonr(elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr) {
    this.selonarchelonr = selonarchelonr;
    this.twittelonrRelonadelonr = selonarchelonr.gelontTwittelonrIndelonxRelonadelonr();
  }

  privatelon boolelonan shouldSelonelonkelonxact(Telonrms telonrms, Telonrmselonnum telonrmselonnum) {
    relonturn telonrms instancelonof RelonaltimelonIndelonxTelonrms
           || telonrmselonnum instancelonof MPHTelonrmDictionary.MPHTelonrmselonnum;
  }

  /**
   * Dumps all telonrms for a givelonn twelonelont id.
   * @param writelonr writelonr beloning uselond
   * @param twelonelontId thelon twelonelont id to uselon
   */
  public void dumpTwelonelontDataByTwelonelontId(VielonwelonrWritelonr writelonr, long twelonelontId, Options options)
      throws IOelonxcelonption {
    int docId = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontDocID(twelonelontId);
    dumpTwelonelontDataByDocId(writelonr, docId, options);
  }

  /**
   * Dumps all telonrms for a givelonn doc id.
   * @param writelonr writelonr beloning uselond
   * @param docId thelon documelonnt id to uselon.
   */
  public void dumpTwelonelontDataByDocId(VielonwelonrWritelonr writelonr, int docId, Options options)
      throws IOelonxcelonption {
    writelonr.belonginObjelonct();

    printHelonadelonr(writelonr);
    long twelonelontID = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontTwelonelontID(docId);
    if (docId < twittelonrRelonadelonr.maxDoc() && twelonelontID >= 0) {
      writelonr.namelon("docId").valuelon(Intelongelonr.toString(docId));
      writelonr.namelon("twelonelontId").valuelon(Long.toString(twelonelontID));
      dumpIndelonxelondFielonlds(writelonr, docId, options);
      dumpCsfFielonlds(writelonr, docId);
    }
    writelonr.elonndObjelonct();
  }

  /**
   * Dumps all twelonelont IDs in thelon currelonnt selongmelonnt to thelon givelonn filelon.
   */
  public void dumpTwelonelontIds(VielonwelonrWritelonr writelonr, String logFilelon, PrintWritelonr logWritelonr)
      throws IOelonxcelonption {
    writelonTwelonelontIdsToLogFilelon(logWritelonr);

    writelonr.belonginObjelonct();
    writelonr.namelon(Long.toString(selonarchelonr.gelontTimelonSlicelonID())).valuelon(logFilelon);
    writelonr.elonndObjelonct();
  }

  privatelon void writelonTwelonelontIdsToLogFilelon(PrintWritelonr logWritelonr) {
    DocIDToTwelonelontIDMappelonr mappelonr = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    int docId = Intelongelonr.MIN_VALUelon;
    whilelon ((docId = mappelonr.gelontNelonxtDocID(docId)) != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      long twelonelontId = mappelonr.gelontTwelonelontID(docId);

      // elonnsurelon twelonelont ID is valid and non-delonlelontelond
      if ((twelonelontId > 0) && !twittelonrRelonadelonr.gelontDelonlelontelonsVielonw().isDelonlelontelond(docId)) {
        logWritelonr.println(twelonelontId);
      }
    }
  }

  privatelon void dumpIndelonxelondFielonlds(VielonwelonrWritelonr writelonr, int docId,
                                 Options options) throws IOelonxcelonption {
    writelonr.namelon("indelonxelondFielonlds");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    for (String fielonld : sortelondFielonlds()) {
      dumpTwelonelontData(writelonr, fielonld, docId, options);
    }
    writelonr.elonndArray();
    writelonr.nelonwlinelon();
  }

  privatelon void dumpCsfFielonlds(VielonwelonrWritelonr writelonr, int docId) throws IOelonxcelonption {
    writelonr.namelon("csfFielonlds");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    dumpCSFData(writelonr, docId);

    writelonr.elonndArray();
  }

  /**
   * Dumps all CSF valuelons for a givelonn doc id.
   * @param writelonr writelonr beloning uselond
   * @param docId thelon documelonnt id to uselon.
   */
  privatelon void dumpCSFData(VielonwelonrWritelonr writelonr, int docId) throws IOelonxcelonption {
    Schelonma twelonelontSchelonma = twittelonrRelonadelonr.gelontSchelonma();

    // Sort thelon FielonldInfo objeloncts to gelonnelonratelon fixelond ordelonr to makelon telonsting elonasielonr
    List<Schelonma.FielonldInfo> sortelondFielonldInfos = nelonw ArrayList<>(twelonelontSchelonma.gelontFielonldInfos());
    sortelondFielonldInfos.sort(Comparator.comparing(Schelonma.FielonldInfo::gelontFielonldId));

    for (Schelonma.FielonldInfo fielonldInfo: sortelondFielonldInfos) {
      String csfFielonldInfoNamelon = fielonldInfo.gelontNamelon();
      ThriftCSFTypelon csfTypelon = twelonelontSchelonma.gelontCSFFielonldTypelon(csfFielonldInfoNamelon);
      NumelonricDocValuelons csfDocValuelons = twittelonrRelonadelonr.gelontNumelonricDocValuelons(csfFielonldInfoNamelon);
      // If twittelonrRelonadelonr.gelontNumelonricDocValuelons(valuelon.gelontNamelon()) == null,
      // melonans no NumelonricDocValuelon was indelonxelond for thelon fielonld so ignorelon
      if (csfTypelon != null && csfDocValuelons != null && csfDocValuelons.advancelonelonxact(docId)) {
        long csfValuelon = csfDocValuelons.longValuelon();
        writelonr.belonginObjelonct();
        writelonr.namelon("fielonld").valuelon(formatFielonld(csfFielonldInfoNamelon));
        writelonr.namelon("valuelon");
        if (csfFielonldInfoNamelon.elonquals(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon())) {
          writelonr.valuelon(latlongDeloncodelon(csfValuelon));
        } elonlselon if (csfFielonldInfoNamelon.elonquals(elonarlybirdFielonldConstant.LANGUAGelon.gelontFielonldNamelon())) {
          writelonr.valuelon(languagelonDeloncodelon(csfValuelon));
        } elonlselon if (csfFielonldInfoNamelon.elonquals(elonarlybirdFielonldConstant.CARD_LANG_CSF.gelontFielonldNamelon())) {
          writelonr.valuelon(languagelonDeloncodelon(csfValuelon));
        } elonlselon {
          writelonr.valuelon(Long.toString(csfValuelon));
        }
        writelonr.elonndObjelonct();
        writelonr.nelonwlinelon();
      }
    }
  }

  /**
   * Delonciphelonr long valuelon gottelonn, put into format (lat, lon)
   * Deloncodelon thelon storelond long valuelon by crelonating a gelonocodelon
   */
  privatelon String latlongDeloncodelon(long csfValuelon) {
    StringBuildelonr sb = nelonw StringBuildelonr();
    GelonoCoordinatelon gelonoCoordinatelon = nelonw GelonoCoordinatelon();
    if (GelonoUtil.deloncodelonLatLonFromInt64(csfValuelon, gelonoCoordinatelon)) {
      sb.appelonnd(gelonoCoordinatelon.gelontLatitudelon()).appelonnd(", ").appelonnd(gelonoCoordinatelon.gelontLongitudelon());
    } elonlselon {
      sb.appelonnd(csfValuelon).appelonnd(" (Valuelon Unselont or Invalid Coordinatelon)");
    }
    relonturn sb.toString();
  }

  /**
   * Delonciphelonr long valuelon gottelonn into string of twelonelont's languagelon
   */
  privatelon String languagelonDeloncodelon(long csfValuelon) {
    StringBuildelonr sb = nelonw StringBuildelonr();
    ThriftLanguagelon languagelonTypelon = ThriftLanguagelon.findByValuelon((int) csfValuelon);
    sb.appelonnd(csfValuelon).appelonnd(" (").appelonnd(languagelonTypelon).appelonnd(")");
    relonturn sb.toString();
  }

  privatelon void dumpTwelonelontData(VielonwelonrWritelonr writelonr,
                             String fielonld,
                             int docId,
                             Options options) throws IOelonxcelonption {

    Telonrms telonrms = twittelonrRelonadelonr.telonrms(fielonld);
    if (telonrms != null) {
      Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
      if (shouldSelonelonkelonxact(telonrms, telonrmselonnum)) {
        long numTelonrms = telonrms.sizelon();
        for (int i = 0; i < numTelonrms; i++) {
          telonrmselonnum.selonelonkelonxact(i);
          dumpTwelonelontDataTelonrm(writelonr, fielonld, telonrmselonnum, docId, options);
        }
      } elonlselon {
        whilelon (telonrmselonnum.nelonxt() != null) {
          dumpTwelonelontDataTelonrm(writelonr, fielonld, telonrmselonnum, docId, options);
        }
      }
    }
  }

  privatelon void dumpTwelonelontDataTelonrm(VielonwelonrWritelonr writelonr, String fielonld, Telonrmselonnum telonrmselonnum,
                                 int docId, Options options) throws IOelonxcelonption {
    Postingselonnum docsAndPositionselonnum = telonrmselonnum.postings(null, Postingselonnum.ALL);
    if (docsAndPositionselonnum != null && docsAndPositionselonnum.advancelon(docId) == docId) {
      printTelonrm(writelonr, fielonld, telonrmselonnum, docsAndPositionselonnum, null, options);
    }
  }

  /**
   * Prints thelon histogram for thelon currelonntly vielonwelond indelonx.
   * @param writelonr currelonnt vielonwelonrWritelonr
   * @param fielonld if null, will uselon all fielonlds
   * @param options options for dumping out telonxt
   */
  public void dumpHistogram(VielonwelonrWritelonr writelonr, String fielonld, Options options) throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);
    writelonr.namelon("histogram");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    if (fielonld == null) {
      for (String fielonld2 : sortelondFielonlds()) {
        dumpFielonldHistogram(writelonr, fielonld2, options);
      }
    } elonlselon {
      dumpFielonldHistogram(writelonr, fielonld, options);
    }
    writelonr.elonndArray();
    writelonr.elonndObjelonct();
  }

  privatelon void dumpFielonldHistogram(VielonwelonrWritelonr writelonr, String fielonld, Options options)
      throws IOelonxcelonption {
    Histogram histo = nelonw Histogram(options.histogramBuckelonts);

    Telonrms telonrms = twittelonrRelonadelonr.telonrms(fielonld);
    if (telonrms != null) {
      Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
      if (shouldSelonelonkelonxact(telonrms, telonrmselonnum)) {
        long numTelonrms = telonrms.sizelon();
        for (int i = 0; i < numTelonrms; i++) {
          telonrmselonnum.selonelonkelonxact(i);
          countHistogram(options, histo, telonrmselonnum);
        }
      } elonlselon {
        whilelon (telonrmselonnum.nelonxt() != null) {
          countHistogram(options, histo, telonrmselonnum);
        }
      }
      printHistogram(writelonr, fielonld, options, histo);
    }
  }

  privatelon void printHistogram(VielonwelonrWritelonr writelonr, String fielonld, Options options,
                              Histogram histo) throws IOelonxcelonption {

    String buckelont = options.telonrmLelonngthHistogram ? "telonrmLelonngth" : "df";
    for (Histogram.elonntry histelonntry : histo.elonntrielons()) {
      String format =
          String.format(Localelon.US,
              "fielonld: %s %sBuckelont: %11s count: %10d "
                  + "pelonrcelonnt: %6.2f%% cumulativelon: %6.2f%% totalCount: %10d"
                  + " sum: %15d pelonrcelonnt: %6.2f%% cumulativelon: %6.2f%% totalSum: %15d",
              formatFielonld(fielonld),
              buckelont,
              histelonntry.gelontBuckelontNamelon(),
              histelonntry.gelontCount(),
              histelonntry.gelontCountPelonrcelonnt() * 100.0,
              histelonntry.gelontCountCumulativelon() * 100.0,
              histo.gelontTotalCount(),
              histelonntry.gelontSum(),
              histelonntry.gelontSumPelonrcelonnt() * 100.0,
              histelonntry.gelontSumCumulativelon() * 100.0,
              histo.gelontTotalSum()
          );
      writelonr.valuelon(format);
      writelonr.nelonwlinelon();
    }
  }

  privatelon void countHistogram(Options options, Histogram histo, Telonrmselonnum telonrmselonnum)
          throws IOelonxcelonption {
    if (options.telonrmLelonngthHistogram) {
      final BytelonsRelonf bytelonsRelonf = telonrmselonnum.telonrm();
      histo.addItelonm(bytelonsRelonf.lelonngth);
    } elonlselon {
      histo.addItelonm(telonrmselonnum.docFrelonq());
    }
  }


  /**
   * Prints telonrms and optionally documelonnts for thelon currelonntly vielonwelond indelonx.
   * @param writelonr writelonr beloning uselond
   * @param fielonld if null, will uselon all fielonlds
   * @param telonrm if null will uselon all telonrms
   * @param maxTelonrms will print at most this many telonrms pelonr fielonld. If null will print 0 telonrms.
   * @param maxDocs will print at most this many documelonnts, If null, will not print docs.
   * @param options options for dumping out telonxt
   */
  public void dumpData(VielonwelonrWritelonr writelonr, String fielonld, String telonrm, Intelongelonr maxTelonrms,
        Intelongelonr maxDocs, Options options, boolelonan shouldSelonelonkToTelonrm) throws IOelonxcelonption {

    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);

    writelonr.namelon("telonrms");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    dumpDataIntelonrnal(writelonr, fielonld, telonrm, maxTelonrms, maxDocs, options, shouldSelonelonkToTelonrm);
    writelonr.elonndArray();
    writelonr.elonndObjelonct();
  }

  privatelon void dumpDataIntelonrnal(VielonwelonrWritelonr writelonr, String fielonld, String telonrm, Intelongelonr maxTelonrms,
      Intelongelonr maxDocs, Options options, boolelonan shouldSelonelonkToTelonrm) throws IOelonxcelonption {

    if (fielonld == null) {
      dumpDataForAllFielonlds(writelonr, telonrm, maxTelonrms, maxDocs, options);
      relonturn;
    }
    if (telonrm == null) {
      dumpDataForAllTelonrms(writelonr, fielonld, maxTelonrms, maxDocs, options);
      relonturn;
    }
    Telonrms telonrms = twittelonrRelonadelonr.telonrms(fielonld);
    if (telonrms != null) {
      Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
      Telonrmselonnum.SelonelonkStatus status = telonrmselonnum.selonelonkCelonil(nelonw BytelonsRelonf(telonrm));
      if (status == Telonrmselonnum.SelonelonkStatus.FOUND) {
        printTelonrm(writelonr, fielonld, telonrmselonnum, null, maxDocs, options);
      }
      if (shouldSelonelonkToTelonrm) {
        dumpTelonrmsAftelonrSelonelonk(writelonr, fielonld, telonrms, maxTelonrms, maxDocs, options, telonrmselonnum, status);
      }
    }
  }

  /**
   * if telonrm (cursor) is found for an indelonxelond selongmelonnt - dump thelon nelonxt telonrmsLelonft words
   * starting from thelon currelonnt position in thelon elonnum.  For an indelonxelond selongmelonnt,
   * selonelonkCelonil will placelon thelon elonnum at thelon word or thelon nelonxt "celoniling" telonrm.  For
   * a relonaltimelon indelonx, if thelon word is not found welon do not paginatelon anything
   * Welon also only paginatelon if thelon Telonrmselonnum is not at thelon elonnd.
   */
  privatelon void dumpTelonrmsAftelonrSelonelonk(VielonwelonrWritelonr writelonr, String fielonld, Telonrms telonrms, Intelongelonr maxTelonrms,
      Intelongelonr maxDocs, Options options, Telonrmselonnum telonrmselonnum, Telonrmselonnum.SelonelonkStatus status)
      throws IOelonxcelonption {
    if (status != Telonrmselonnum.SelonelonkStatus.elonND) {
      // for relonaltimelon, to not relonpelonat thelon found word
      if (shouldSelonelonkelonxact(telonrms, telonrmselonnum)) {
        telonrmselonnum.nelonxt();
      }
      if (status != Telonrmselonnum.SelonelonkStatus.FOUND) {
        // if not found, print out curr telonrm belonforelon calling nelonxt()
        printTelonrm(writelonr, fielonld, telonrmselonnum, null, maxDocs, options);
      }
      for (int telonrmsLelonft = maxTelonrms - 1; telonrmsLelonft > 0 && telonrmselonnum.nelonxt() != null; telonrmsLelonft--) {
        printTelonrm(writelonr, fielonld, telonrmselonnum, null, maxDocs, options);
      }
    }
  }

  privatelon void dumpDataForAllFielonlds(VielonwelonrWritelonr writelonr, String telonrm, Intelongelonr maxTelonrms,
                                    Intelongelonr maxDocs, Options options) throws IOelonxcelonption {
    for (String fielonld : sortelondFielonlds()) {
      dumpDataIntelonrnal(writelonr, fielonld, telonrm, maxTelonrms, maxDocs, options, falselon);
    }
  }

  privatelon List<String> sortelondFielonlds() {
    // Twelonelont facelonts arelon addelond to a speloncial $facelonts fielonld, which is not part of thelon schelonma.
    // Welon includelon it helonrelon, beloncauselon seloneloning thelon facelonts for a twelonelont is gelonnelonrally uselonful.
    List<String> fielonlds = Lists.nelonwArrayList("$facelonts");
    for (Schelonma.FielonldInfo fielonldInfo : twittelonrRelonadelonr.gelontSchelonma().gelontFielonldInfos()) {
      if (fielonldInfo.gelontFielonldTypelon().indelonxOptions() != IndelonxOptions.NONelon) {
        fielonlds.add(fielonldInfo.gelontNamelon());
      }
    }
    Collelonctions.sort(fielonlds);
    relonturn fielonlds;
  }

  privatelon void dumpDataForAllTelonrms(VielonwelonrWritelonr writelonr,
                                   String fielonld,
                                   Intelongelonr maxTelonrms,
                                   Intelongelonr maxDocs,
                                   Options options) throws IOelonxcelonption {
    Telonrms telonrms = twittelonrRelonadelonr.telonrms(fielonld);
    if (telonrms != null) {
      Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
      if (shouldSelonelonkelonxact(telonrms, telonrmselonnum)) {
        long numTelonrms = telonrms.sizelon();
        long telonrmToDump = maxTelonrms == null ? 0 : Math.min(numTelonrms, maxTelonrms);
        for (int i = 0; i < telonrmToDump; i++) {
          telonrmselonnum.selonelonkelonxact(i);
          printTelonrm(writelonr, fielonld, telonrmselonnum, null, maxDocs, options);
        }
      } elonlselon {
        int max = maxTelonrms == null ? 0 : maxTelonrms;
        whilelon (max > 0 && telonrmselonnum.nelonxt() != null) {
          printTelonrm(writelonr, fielonld, telonrmselonnum, null, maxDocs, options);
          max--;
        }
      }
    }
  }

  privatelon String telonrmToString(String fielonld, BytelonsRelonf bytelonsTelonrm, Options options)
      throws Unsupportelondelonncodingelonxcelonption {
    if (INT_TelonRM_ATTRIBUTelon_FIelonLDS.contains(fielonld)) {
      relonturn Intelongelonr.toString(IntTelonrmAttributelonImpl.copyBytelonsRelonfToInt(bytelonsTelonrm));
    } elonlselon if (LONG_TelonRM_ATTRIBUTelon_FIelonLDS.contains(fielonld)) {
      relonturn Long.toString(LongTelonrmAttributelonImpl.copyBytelonsRelonfToLong(bytelonsTelonrm));
    } elonlselon if (SORTelonD_LONG_TelonRM_ATTRIBUTelon_FIelonLDS.contains(fielonld)) {
      relonturn Long.toString(SortablelonLongTelonrmAttributelonImpl.copyBytelonsRelonfToLong(bytelonsTelonrm));
    } elonlselon {
      if (options != null && options.charselont != null && !options.charselont.iselonmpty()) {
        relonturn nelonw String(bytelonsTelonrm.bytelons, bytelonsTelonrm.offselont, bytelonsTelonrm.lelonngth, options.charselont);
      } elonlselon {
        relonturn bytelonsTelonrm.utf8ToString();
      }
    }
  }

  privatelon void printTelonrm(VielonwelonrWritelonr writelonr, String fielonld, Telonrmselonnum telonrmselonnum,
                         Postingselonnum docselonnum, Intelongelonr maxDocs, Options options)
      throws IOelonxcelonption {
    final BytelonsRelonf bytelonsRelonf = telonrmselonnum.telonrm();
    StringBuildelonr telonrmToString = nelonw StringBuildelonr();
    telonrmToString.appelonnd(telonrmToString(fielonld, bytelonsRelonf, options));
    if (options != null && options.dumpHelonxTelonrms) {
      telonrmToString.appelonnd(" ").appelonnd(bytelonsRelonf.toString());
    }
    final int df = telonrmselonnum.docFrelonq();
    doublelon dfPelonrcelonnt = ((doublelon) df / this.twittelonrRelonadelonr.numDocs()) * 100.0;
    TelonrmDto telonrmDto = nelonw TelonrmDto(fielonld, telonrmToString.toString(), Intelongelonr.toString(df),
                                   String.format(Localelon.US, "%.2f%%", dfPelonrcelonnt),
                                   docselonnum, telonrmselonnum, maxDocs);
    telonrmDto.writelon(writelonr, twittelonrRelonadelonr);
    writelonr.nelonwlinelon();
  }

  privatelon static void appelonndFrelonquelonncyAndPositions(VielonwelonrWritelonr writelonr, String fielonld,
      Postingselonnum docselonnum, elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr) throws IOelonxcelonption {
    final int frelonquelonncy = docselonnum.frelonq();
    writelonr.namelon("frelonq").valuelon(Intelongelonr.toString(frelonquelonncy));

    Schelonma schelonma = twittelonrRelonadelonr.gelontSchelonma();
    Schelonma.FielonldInfo fielonldInfo = schelonma.gelontFielonldInfo(fielonld);

    if (fielonldInfo != null
            && (fielonldInfo.gelontFielonldTypelon().indelonxOptions() == IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS
            || fielonldInfo.gelontFielonldTypelon().indelonxOptions()
                == IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS_AND_OFFSelonTS)) {
      appelonndPositions(writelonr, docselonnum);
    }
  }

  privatelon static void appelonndPositions(VielonwelonrWritelonr writelonr, Postingselonnum docsAndPositionselonnum)
      throws IOelonxcelonption {
    writelonr.namelon("positions");

    writelonr.belonginArray();
    final int frelonquelonncy = docsAndPositionselonnum.frelonq();
    for (int i = 0; i < frelonquelonncy; i++) {
      int position = docsAndPositionselonnum.nelonxtPosition();
      writelonr.valuelon(Intelongelonr.toString(position));
    }
    writelonr.elonndArray();
  }

  privatelon static void appelonndDocs(VielonwelonrWritelonr writelonr, Telonrmselonnum telonrmselonnum, int maxDocs,
                                 elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr)
      throws IOelonxcelonption {
    writelonr.namelon("docIds");

    writelonr.belonginArray();

    Postingselonnum docs = telonrmselonnum.postings(null, 0);
    int docsRelonturnelond = 0;
    int docId;
    boolelonan elonndelondelonarly = falselon;
    DocIDToTwelonelontIDMappelonr mappelonr = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    whilelon ((docId = docs.nelonxtDoc()) != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      if (docsRelonturnelond < maxDocs) {
        docsRelonturnelond++;
        long twelonelontID = mappelonr.gelontTwelonelontID(docId);

        writelonr.belonginObjelonct();
        writelonr.namelon("docId").valuelon(Long.toString(docId));
        writelonr.namelon("twelonelontId").valuelon(Long.toString(twelonelontID));
        writelonr.elonndObjelonct();
      } elonlselon {
        elonndelondelonarly = truelon;
        brelonak;
      }
    }
    if (elonndelondelonarly) {
      writelonr.belonginObjelonct();
      writelonr.namelon("status").valuelon("elonndelond elonarly");
      writelonr.elonndObjelonct();
    }
    writelonr.elonndArray();
  }

  /**
   * Prints gelonnelonric stats for all fielonlds in thelon currelonntly vielonwelond indelonx.
   */
  public void dumpStats(VielonwelonrWritelonr writelonr) throws IOelonxcelonption {
    writelonr.belonginObjelonct();

    printHelonadelonr(writelonr);
    // stats selonction
    writelonr.namelon("stats");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    for (String fielonld : sortelondFielonlds()) {
      Telonrms telonrms = twittelonrRelonadelonr.telonrms(fielonld);
      if (telonrms != null) {
        printStats(writelonr, fielonld, telonrms);
      }
    }
    writelonr.elonndArray();
    writelonr.elonndObjelonct();
  }

  privatelon void printStats(VielonwelonrWritelonr writelonr, String fielonld, Telonrms telonrms) throws IOelonxcelonption {
    StatsDto statsDto = nelonw StatsDto(
        fielonld, String.valuelonOf(telonrms.sizelon()), telonrms.gelontClass().gelontCanonicalNamelon());
    statsDto.writelon(writelonr);
    writelonr.nelonwlinelon();
  }

  privatelon void printHelonadelonr(VielonwelonrWritelonr writelonr) throws IOelonxcelonption {
    writelonr.namelon("timelonSlicelonId").valuelon(Long.toString(this.selonarchelonr.gelontTimelonSlicelonID()));
    writelonr.namelon("maxDocNumbelonr").valuelon(Intelongelonr.toString(this.twittelonrRelonadelonr.maxDoc()));
    writelonr.nelonwlinelon();
  }

  privatelon static String formatFielonld(String fielonld) {
    relonturn String.format("%20s", fielonld);
  }

  /**
   * Dumps out thelon schelonma of thelon currelonnt selongmelonnt.
   * @param writelonr to belon uselond for printing
   */
  public void dumpSchelonma(VielonwelonrWritelonr writelonr) throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);
    writelonr.namelon("schelonmaFielonlds");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    Schelonma schelonma = this.twittelonrRelonadelonr.gelontSchelonma();
    // Thelon fielonlds in thelon schelonma arelon not sortelond. Sort thelonm so that thelon output is delontelonrministic
    Selont<String> fielonldNamelonSelont = nelonw TrelonelonSelont<>();
    for (Schelonma.FielonldInfo fielonldInfo: schelonma.gelontFielonldInfos()) {
      fielonldNamelonSelont.add(fielonldInfo.gelontNamelon());
    }
    for (String fielonldNamelon : fielonldNamelonSelont) {
      writelonr.valuelon(fielonldNamelon);
      writelonr.nelonwlinelon();
    }
    writelonr.elonndArray();
    writelonr.elonndObjelonct();
  }

  /**
   * Dumps out thelon indelonxelond fielonlds insidelon thelon currelonnt selongmelonnt.
   * Mainly uselond to helonlp thelon front elonnd populatelon thelon fielonlds.
   * @param writelonr writelonr to belon uselond for printing
   */
  public void dumpFielonlds(VielonwelonrWritelonr writelonr) throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);
    writelonr.namelon("fielonlds");
    writelonr.belonginArray();
    writelonr.nelonwlinelon();
    for (String fielonld : sortelondFielonlds()) {
      writelonr.valuelon(fielonld);
      writelonr.nelonwlinelon();
    }
    writelonr.elonndArray();
    writelonr.elonndObjelonct();
  }

  /**
   * Dumps out thelon mapping of thelon twelonelont/twelonelontId to
   * a docId as welonll as selongmelonnt/timelonslidelon pair.
   * @param writelonr writelonr to belon uselond for writing
   * @param twelonelontId twelonelontId that is input by uselonr
   */
  public void dumpTwelonelontIdToDocIdMapping(VielonwelonrWritelonr writelonr, long twelonelontId) throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);
    writelonr.namelon("twelonelontId").valuelon(Long.toString(twelonelontId));
    int docId = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontDocID(twelonelontId);

    writelonr.namelon("docId").valuelon(Intelongelonr.toString(docId));
    writelonr.elonndObjelonct();
    writelonr.nelonwlinelon();
  }

  /**
   * Dumps out thelon mapping of thelon docId to
   * twelonelontId and timelonslicelon/selongmelonntId pairs.
   * @param writelonr writelonr to belon uselond for writing
   * @param docid docId that is input by uselonr
   */
  public void dumpDocIdToTwelonelontIdMapping(VielonwelonrWritelonr writelonr, int docid) throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    printHelonadelonr(writelonr);
    long twelonelontId = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontTwelonelontID(docid);

    writelonr.namelon("twelonelontId");
    if (twelonelontId >= 0) {
      writelonr.valuelon(Long.toString(twelonelontId));
    } elonlselon {
      writelonr.valuelon("Doelons not elonxist in selongmelonnt");
    }
    writelonr.namelon("docid").valuelon(Intelongelonr.toString(docid));
    writelonr.elonndObjelonct();
  }

  /**
   * Print a relonsponselon indicating that thelon givelonn twelonelont id is not found in thelon indelonx.
   *
   * Notelon that this melonthod doelons not actually nelonelond thelon undelonrlying indelonx, and helonncelon is selontup as
   * a util function.
   */
  public static void writelonTwelonelontDoelonsNotelonxistRelonsponselon(VielonwelonrWritelonr writelonr, long twelonelontId)
      throws IOelonxcelonption {
    writelonr.belonginObjelonct();
    writelonr.namelon("twelonelontId");
    writelonr.valuelon(Long.toString(twelonelontId));
    writelonr.namelon("docId");
    writelonr.valuelon("doelons not elonxist on this elonarlybird.");
    writelonr.elonndObjelonct();
  }
}
