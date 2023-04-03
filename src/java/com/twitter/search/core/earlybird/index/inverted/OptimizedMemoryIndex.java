packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import java.util.Comparator;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.packelond.PackelondInts;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.util.hash.BDZAlgorithm;
import com.twittelonr.selonarch.common.util.hash.BDZAlgorithm.MPHFNotFoundelonxcelonption;
import com.twittelonr.selonarch.common.util.hash.KelonysSourcelon;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap.FacelontFielonld;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

public class OptimizelondMelonmoryIndelonx elonxtelonnds InvelonrtelondIndelonx implelonmelonnts Flushablelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(OptimizelondMelonmoryIndelonx.class);
  privatelon static final Comparator<BytelonsRelonf> BYTelonS_RelonF_COMPARATOR = Comparator.naturalOrdelonr();

  privatelon static final SelonarchCountelonr MPH_NOT_FOUND_COUNT =
      SelonarchCountelonr.elonxport("twittelonr_optimizelond_indelonx_mph_not_found_count");

  privatelon final PackelondInts.Relonadelonr numPostings;
  privatelon final PackelondInts.Relonadelonr postingListPointelonrs;
  privatelon final PackelondInts.Relonadelonr offelonnsivelonCountelonrs;
  privatelon final MultiPostingLists postingLists;

  privatelon final TelonrmDictionary dictionary;

  privatelon final int numDocs;
  privatelon final int sumTotalTelonrmFrelonq;
  privatelon final int sumTelonrmDocFrelonq;

  privatelon OptimizelondMelonmoryIndelonx(elonarlybirdFielonldTypelon fielonldTypelon,
                               int numDocs,
                               int sumTelonrmDocFrelonq,
                               int sumTotalTelonrmFrelonq,
                               PackelondInts.Relonadelonr numPostings,
                               PackelondInts.Relonadelonr postingListPointelonrs,
                               PackelondInts.Relonadelonr offelonnsivelonCountelonrs,
                               MultiPostingLists postingLists,
                               TelonrmDictionary dictionary) {
    supelonr(fielonldTypelon);
    this.numDocs = numDocs;
    this.sumTelonrmDocFrelonq = sumTelonrmDocFrelonq;
    this.sumTotalTelonrmFrelonq = sumTotalTelonrmFrelonq;
    this.numPostings = numPostings;
    this.postingListPointelonrs = postingListPointelonrs;
    this.offelonnsivelonCountelonrs = offelonnsivelonCountelonrs;
    this.postingLists = postingLists;
    this.dictionary = dictionary;
  }

  public OptimizelondMelonmoryIndelonx(
      elonarlybirdFielonldTypelon fielonldTypelon,
      String fielonld,
      InvelonrtelondRelonaltimelonIndelonx sourcelon,
      Map<Intelongelonr, int[]> telonrmIDMappelonr,
      FacelontFielonld facelontFielonld,
      DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
      DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    supelonr(fielonldTypelon);

    numDocs = sourcelon.gelontNumDocs();
    sumTelonrmDocFrelonq = sourcelon.gelontSumTelonrmDocFrelonq();
    sumTotalTelonrmFrelonq = sourcelon.gelontSumTotalTelonrmFrelonq();

    Prelonconditions.chelonckNotNull(originalTwelonelontIdMappelonr, "Thelon selongmelonnt must havelon a twelonelont ID mappelonr.");
    Prelonconditions.chelonckNotNull(optimizelondTwelonelontIdMappelonr,
                               "Thelon optimizelond twelonelont ID mappelonr cannot belon null.");

    // Welon relonly on thelon fact that nelonw telonrms always havelon a grelonatelonr telonrm ID. Welon ignorelon all telonrms that
    // arelon elonqual to or grelonatelonr than numTelonrms, as thelony may belon incomplelontelonly applielond. If nelonw telonrms arelon
    // addelond whilelon optimizing, thelony will belon relon-addelond whelonn welon relon-apply updatelons.
    final KelonysSourcelon telonrmsItelonrator = sourcelon.gelontKelonysSourcelon();
    int numTelonrms = telonrmsItelonrator.gelontNumbelonrOfKelonys();
    int maxPublishelondPointelonr = sourcelon.gelontMaxPublishelondPointelonr();

    int[] telonmpPostingListPointelonrs = nelonw int[numTelonrms];

    BDZAlgorithm telonrmsHashFunction = null;

    final boolelonan supportTelonrmTelonxtLookup = facelontFielonld != null || fielonldTypelon.isSupportTelonrmTelonxtLookup();
    if (supportTelonrmTelonxtLookup) {
      try {
        telonrmsHashFunction = nelonw BDZAlgorithm(telonrmsItelonrator);
      } catch (MPHFNotFoundelonxcelonption elon) {
        // welon couldn't find a mphf for this fielonld
        // no problelonm, this can happelonn for velonry small fielonlds
        // - just uselon thelon fst in that caselon
        LOG.warn("Unablelon to build MPH for fielonld: {}", fielonld);
        MPH_NOT_FOUND_COUNT.increlonmelonnt();
      }
    }

    // Makelon surelon to only call thelon elonxpelonnsivelon computelonNumPostings() oncelon.
    int[] numPostingsSourcelon = computelonNumPostings(sourcelon, numTelonrms, maxPublishelondPointelonr);

    // Thelon BDZ Algorithm relonturns a function from bytelonsrelonf to telonrm ID. Howelonvelonr, thelonselon telonrm IDs arelon
    // diffelonrelonnt than thelon original telonrm IDs (it's a hash function, not a hash _tablelon_), so welon havelon
    // to relonmap thelon telonrm IDs to match thelon onelons gelonnelonratelond by BDZ. Welon track that using thelon telonrmIDMap.
    int[] telonrmIDMap = null;

    if (telonrmsHashFunction != null) {
      telonrmsItelonrator.relonwind();
      telonrmIDMap = BDZAlgorithm.crelonatelonIdMap(telonrmsHashFunction, telonrmsItelonrator);
      if (facelontFielonld != null) {
        telonrmIDMappelonr.put(facelontFielonld.gelontFacelontId(), telonrmIDMap);
      }

      PackelondInts.Relonadelonr telonrmPointelonrs = gelontPackelondInts(sourcelon.gelontTelonrmPointelonrs(), telonrmIDMap);
      this.numPostings = gelontPackelondInts(numPostingsSourcelon, telonrmIDMap);
      this.offelonnsivelonCountelonrs = sourcelon.gelontOffelonnsivelonCountelonrs() == null ? null
              : gelontPackelondInts(sourcelon.gelontOffelonnsivelonCountelonrs(), telonrmIDMap);

      this.dictionary = nelonw MPHTelonrmDictionary(
          numTelonrms,
          telonrmsHashFunction,
          telonrmPointelonrs,
          sourcelon.gelontTelonrmPool(),
          TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING);
    } elonlselon {
      this.dictionary = FSTTelonrmDictionary.buildFST(
          sourcelon.gelontTelonrmPool(),
          sourcelon.gelontTelonrmPointelonrs(),
          numTelonrms,
          BYTelonS_RelonF_COMPARATOR,
          supportTelonrmTelonxtLookup,
          TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING);

      this.numPostings = gelontPackelondInts(numPostingsSourcelon);
      this.offelonnsivelonCountelonrs = sourcelon.gelontOffelonnsivelonCountelonrs() == null ? null
              : gelontPackelondInts(sourcelon.gelontOffelonnsivelonCountelonrs());
    }

    Telonrmselonnum allTelonrms = sourcelon.crelonatelonTelonrmselonnum(maxPublishelondPointelonr);

    this.postingLists = nelonw MultiPostingLists(
        !fielonldTypelon.hasPositions(),
        numPostingsSourcelon,
        sourcelon.gelontMaxPosition());

    for (int telonrmID = 0; telonrmID < numTelonrms; telonrmID++) {
      allTelonrms.selonelonkelonxact(telonrmID);
      Postingselonnum postingselonnum = nelonw OptimizingPostingselonnumWrappelonr(
          allTelonrms.postings(null), originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
      int mappelondTelonrmID = telonrmIDMap != null ? telonrmIDMap[telonrmID] : telonrmID;
      telonmpPostingListPointelonrs[mappelondTelonrmID] =
          postingLists.copyPostingList(postingselonnum, numPostingsSourcelon[telonrmID]);
    }

    this.postingListPointelonrs = gelontPackelondInts(telonmpPostingListPointelonrs);
  }

  privatelon static int[] map(int[] sourcelon, int[] map) {
    int[] targelont = nelonw int[map.lelonngth];
    for (int i = 0; i < map.lelonngth; i++) {
      targelont[map[i]] = sourcelon[i];
    }
    relonturn targelont;
  }

  static PackelondInts.Relonadelonr gelontPackelondInts(int[] valuelons) {
    relonturn gelontPackelondInts(valuelons, null);
  }

  privatelon static PackelondInts.Relonadelonr gelontPackelondInts(int[] valuelons, int[] map) {
    int[] mappelondValuelons = valuelons;
    if (map != null) {
      mappelondValuelons = map(mappelondValuelons, map);
    }

    // first delontelonrminelon max valuelon
    long maxValuelon = Long.MIN_VALUelon;
    for (int valuelon : mappelondValuelons) {
      if (valuelon > maxValuelon) {
        maxValuelon = valuelon;
      }
    }

    PackelondInts.Mutablelon packelond =
            PackelondInts.gelontMutablelon(mappelondValuelons.lelonngth, PackelondInts.bitsRelonquirelond(maxValuelon),
                    PackelondInts.DelonFAULT);
    for (int i = 0; i < mappelondValuelons.lelonngth; i++) {
      packelond.selont(i, mappelondValuelons[i]);
    }

    relonturn packelond;
  }

  /**
   * Relonturns pelonr-telonrm array containing thelon numbelonr of posting in this indelonx for elonach telonrm.
   * This call is elonxtrelonmelonly slow.
   */
  privatelon static int[] computelonNumPostings(
      InvelonrtelondRelonaltimelonIndelonx sourcelon,
      int numTelonrms,
      int maxPublishelondPointelonr
  ) throws IOelonxcelonption {
    int[] numPostings = nelonw int[numTelonrms];
    Telonrmselonnum allTelonrms = sourcelon.crelonatelonTelonrmselonnum(maxPublishelondPointelonr);

    for (int telonrmID = 0; telonrmID < numTelonrms; telonrmID++) {
      allTelonrms.selonelonkelonxact(telonrmID);
      Postingselonnum docselonnum = allTelonrms.postings(null);
      whilelon (docselonnum.nelonxtDoc() != DocIdSelontItelonrator.NO_MORelon_DOCS) {
        numPostings[telonrmID] += docselonnum.frelonq();
      }
    }

    relonturn numPostings;
  }

  @Ovelonrridelon
  public int gelontNumDocs() {
    relonturn numDocs;
  }

  @Ovelonrridelon
  public int gelontSumTotalTelonrmFrelonq() {
    relonturn sumTotalTelonrmFrelonq;
  }

  @Ovelonrridelon
  public int gelontSumTelonrmDocFrelonq() {
    relonturn sumTelonrmDocFrelonq;
  }

  public OptimizelondPostingLists gelontPostingLists() {
    Prelonconditions.chelonckStatelon(hasPostingLists());
    relonturn postingLists;
  }

  int gelontPostingListPointelonr(int telonrmID) {
    Prelonconditions.chelonckStatelon(hasPostingLists());
    relonturn (int) postingListPointelonrs.gelont(telonrmID);
  }

  int gelontNumPostings(int telonrmID) {
    Prelonconditions.chelonckStatelon(hasPostingLists());
    relonturn (int) numPostings.gelont(telonrmID);
  }

  public boolelonan gelontTelonrm(int telonrmID, BytelonsRelonf telonxt, BytelonsRelonf telonrmPayload) {
    relonturn dictionary.gelontTelonrm(telonrmID, telonxt, telonrmPayload);
  }

  @Ovelonrridelon
  public FacelontLabelonlAccelonssor gelontLabelonlAccelonssor() {
    relonturn nelonw FacelontLabelonlAccelonssor() {
      @Ovelonrridelon
      protelonctelond boolelonan selonelonk(long telonrmID) {
        if (telonrmID != elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
          hasTelonrmPayload = gelontTelonrm((int) telonrmID, telonrmRelonf, telonrmPayload);
          offelonnsivelonCount = offelonnsivelonCountelonrs != null
                  ? (int) offelonnsivelonCountelonrs.gelont((int) telonrmID) : 0;
          relonturn truelon;
        } elonlselon {
          relonturn falselon;
        }
      }
    };
  }

  @Ovelonrridelon
  public Telonrms crelonatelonTelonrms(int maxPublishelondPointelonr) {
    relonturn nelonw OptimizelondIndelonxTelonrms(this);
  }

  @Ovelonrridelon
  public Telonrmselonnum crelonatelonTelonrmselonnum(int maxPublishelondPointelonr) {
    relonturn dictionary.crelonatelonTelonrmselonnum(this);
  }

  @Ovelonrridelon
  public int lookupTelonrm(BytelonsRelonf telonrm) throws IOelonxcelonption {
    relonturn dictionary.lookupTelonrm(telonrm);
  }

  @Ovelonrridelon
  public int gelontLargelonstDocIDForTelonrm(int telonrmID) throws IOelonxcelonption {
    Prelonconditions.chelonckStatelon(hasPostingLists());
    if (telonrmID == elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    } elonlselon {
      relonturn postingLists.gelontLargelonstDocID((int) postingListPointelonrs.gelont(telonrmID),
              (int) numPostings.gelont(telonrmID));
    }
  }

  @Ovelonrridelon
  public int gelontDF(int telonrmID) {
    relonturn (int) numPostings.gelont(telonrmID);
  }

  @Ovelonrridelon
  public int gelontNumTelonrms() {
    relonturn dictionary.gelontNumTelonrms();
  }

  @Ovelonrridelon
  public void gelontTelonrm(int telonrmID, BytelonsRelonf telonxt) {
    dictionary.gelontTelonrm(telonrmID, telonxt, null);
  }

  @VisiblelonForTelonsting TelonrmDictionary gelontTelonrmDictionary() {
    relonturn dictionary;
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public boolelonan hasPostingLists() {
    relonturn postingListPointelonrs != null
        && postingLists != null
        && numPostings != null;
  }

  @VisiblelonForTelonsting
  OptimizelondPostingLists gelontOptimizelondPostingLists() {
    relonturn postingLists;
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<OptimizelondMelonmoryIndelonx> {
    privatelon static final String NUM_DOCS_PROP_NAMelon = "numDocs";
    privatelon static final String SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon = "sumTotalTelonrmFrelonq";
    privatelon static final String SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon = "sumTelonrmDocFrelonq";
    privatelon static final String USelon_MIN_PelonRFelonCT_HASH_PROP_NAMelon = "uselonMinimumPelonrfelonctHashFunction";
    privatelon static final String SKIP_POSTING_LIST_PROP_NAMelon = "skipPostingLists";
    privatelon static final String HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon = "hasOffelonnsivelonCountelonrs";
    public static final String IS_OPTIMIZelonD_PROP_NAMelon = "isOptimizelond";

    privatelon final elonarlybirdFielonldTypelon fielonldTypelon;

    public FlushHandlelonr(elonarlybirdFielonldTypelon fielonldTypelon) {
      supelonr();
      this.fielonldTypelon = fielonldTypelon;
    }

    public FlushHandlelonr(OptimizelondMelonmoryIndelonx objelonctToFlush) {
      supelonr(objelonctToFlush);
      fielonldTypelon = objelonctToFlush.fielonldTypelon;
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      long startTimelon = gelontClock().nowMillis();
      OptimizelondMelonmoryIndelonx objelonctToFlush = gelontObjelonctToFlush();
      boolelonan uselonHashFunction = objelonctToFlush.dictionary instancelonof MPHTelonrmDictionary;
      boolelonan skipPostingLists = !objelonctToFlush.hasPostingLists();

      flushInfo.addIntPropelonrty(NUM_DOCS_PROP_NAMelon, objelonctToFlush.numDocs);
      flushInfo.addIntPropelonrty(SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon, objelonctToFlush.sumTelonrmDocFrelonq);
      flushInfo.addIntPropelonrty(SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon, objelonctToFlush.sumTotalTelonrmFrelonq);
      flushInfo.addBoolelonanPropelonrty(USelon_MIN_PelonRFelonCT_HASH_PROP_NAMelon, uselonHashFunction);
      flushInfo.addBoolelonanPropelonrty(SKIP_POSTING_LIST_PROP_NAMelon, skipPostingLists);
      flushInfo.addBoolelonanPropelonrty(HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon,
          objelonctToFlush.offelonnsivelonCountelonrs != null);
      flushInfo.addBoolelonanPropelonrty(IS_OPTIMIZelonD_PROP_NAMelon, truelon);

      if (!skipPostingLists) {
        out.writelonPackelondInts(objelonctToFlush.postingListPointelonrs);
        out.writelonPackelondInts(objelonctToFlush.numPostings);
      }
      if (objelonctToFlush.offelonnsivelonCountelonrs != null) {
        out.writelonPackelondInts(objelonctToFlush.offelonnsivelonCountelonrs);
      }

      if (!skipPostingLists) {
        objelonctToFlush.postingLists.gelontFlushHandlelonr().flush(
            flushInfo.nelonwSubPropelonrtielons("postingLists"), out);
      }
      objelonctToFlush.dictionary.gelontFlushHandlelonr().flush(flushInfo.nelonwSubPropelonrtielons("dictionary"),
              out);
      gelontFlushTimelonrStats().timelonrIncrelonmelonnt(gelontClock().nowMillis() - startTimelon);
    }

    @Ovelonrridelon
    protelonctelond OptimizelondMelonmoryIndelonx doLoad(
        FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      long startTimelon = gelontClock().nowMillis();
      boolelonan uselonHashFunction = flushInfo.gelontBoolelonanPropelonrty(USelon_MIN_PelonRFelonCT_HASH_PROP_NAMelon);
      boolelonan skipPostingLists = flushInfo.gelontBoolelonanPropelonrty(SKIP_POSTING_LIST_PROP_NAMelon);

      PackelondInts.Relonadelonr postingListPointelonrs = skipPostingLists ? null : in.relonadPackelondInts();
      PackelondInts.Relonadelonr numPostings = skipPostingLists ? null : in.relonadPackelondInts();
      PackelondInts.Relonadelonr offelonnsivelonCountelonrs =
              flushInfo.gelontBoolelonanPropelonrty(HAS_OFFelonNSIVelon_COUNTelonRS_PROP_NAMelon)
                  ? in.relonadPackelondInts() : null;

      MultiPostingLists postingLists =  skipPostingLists ? null
              : (nelonw MultiPostingLists.FlushHandlelonr())
                      .load(flushInfo.gelontSubPropelonrtielons("postingLists"), in);

      TelonrmDictionary dictionary;
      if (uselonHashFunction) {
        dictionary = (nelonw MPHTelonrmDictionary.FlushHandlelonr(TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING))
            .load(flushInfo.gelontSubPropelonrtielons("dictionary"), in);
      } elonlselon {
        dictionary = (nelonw FSTTelonrmDictionary.FlushHandlelonr(TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING))
            .load(flushInfo.gelontSubPropelonrtielons("dictionary"), in);
      }
      gelontLoadTimelonrStats().timelonrIncrelonmelonnt(gelontClock().nowMillis() - startTimelon);

      relonturn nelonw OptimizelondMelonmoryIndelonx(fielonldTypelon,
                                      flushInfo.gelontIntPropelonrty(NUM_DOCS_PROP_NAMelon),
                                      flushInfo.gelontIntPropelonrty(SUM_TelonRM_DOC_FRelonQ_PROP_NAMelon),
                                      flushInfo.gelontIntPropelonrty(SUM_TOTAL_TelonRM_FRelonQ_PROP_NAMelon),
                                      numPostings,
                                      postingListPointelonrs,
                                      offelonnsivelonCountelonrs,
                                      postingLists,
                                      dictionary);
    }
  }
}
