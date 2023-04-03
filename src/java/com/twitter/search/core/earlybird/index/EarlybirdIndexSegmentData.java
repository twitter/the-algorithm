packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.HashMap;
import java.util.Itelonrator;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.DirelonctoryRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.IndelonxWritelonrConfig;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.storelon.Direlonctory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.AbstractFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountingArrayWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonBytelonIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsFactory;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.DelonlelontelondDocs;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondRelonaltimelonIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.OptimizelondMelonmoryIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.TelonrmPointelonrelonncoding;

/**
 * Baselon class that relonfelonrelonncelons data structurelons belonlonging to an elonarlybird selongmelonnt.
 */
public abstract class elonarlybirdIndelonxSelongmelonntData implelonmelonnts Flushablelon {
  /**
   * This class has a map which contains a snapshot of max publishelond pointelonrs, to distinguish thelon
   * documelonnts in thelon skip lists that arelon fully indelonxelond, and safelon to relonturn to selonarchelonrs and thoselon
   * that arelon in progrelonss and should not belon relonturnelond to selonarchelonrs. Selonelon
   * "elonarlybird Indelonxing Latelonncy Delonsign Documelonnt"
   * for rationalelon and delonsign.
   *
   * It also has thelon smallelonstDocID, which delontelonrminelons thelon smallelonst assignelond doc ID in thelon twelonelont ID
   * mappelonr that is safelon to travelonrselon.
   *
   * Thelon pointelonr map and smallelonstDocID nelonelond to belon updatelond atomically. Selonelon SelonARCH-27650.
   */
  public static class SyncData {
    privatelon final Map<InvelonrtelondIndelonx, Intelongelonr> indelonxPointelonrs;
    privatelon final int smallelonstDocID;

    public SyncData(Map<InvelonrtelondIndelonx, Intelongelonr> indelonxPointelonrs, int smallelonstDocID) {
      this.indelonxPointelonrs = indelonxPointelonrs;
      this.smallelonstDocID = smallelonstDocID;
    }

    public Map<InvelonrtelondIndelonx, Intelongelonr> gelontIndelonxPointelonrs() {
      relonturn indelonxPointelonrs;
    }

    public int gelontSmallelonstDocID() {
      relonturn smallelonstDocID;
    }
  }

  privatelon volatilelon SyncData syncData;

  privatelon final int maxSelongmelonntSizelon;
  privatelon final long timelonSlicelonID;

  privatelon final ConcurrelonntHashMap<String, QuelonryCachelonRelonsultForSelongmelonnt> quelonryCachelonMap =
      nelonw ConcurrelonntHashMap<>();
  privatelon final AbstractFacelontCountingArray facelontCountingArray;
  privatelon final boolelonan isOptimizelond;
  privatelon final ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap;
  privatelon final ConcurrelonntHashMap<String, ColumnStridelonBytelonIndelonx> normsMap;

  privatelon final Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs;
  privatelon final FacelontIDMap facelontIDMap;

  privatelon final Schelonma schelonma;
  privatelon final DocValuelonsManagelonr docValuelonsManagelonr;

  privatelon final DelonlelontelondDocs delonlelontelondDocs;

  privatelon final DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr;
  privatelon final TimelonMappelonr timelonMappelonr;

  static LelonafRelonadelonr gelontLelonafRelonadelonrFromOptimizelondDirelonctory(Direlonctory direlonctory) throws IOelonxcelonption {
    List<LelonafRelonadelonrContelonxt> lelonavelons = DirelonctoryRelonadelonr.opelonn(direlonctory).gelontContelonxt().lelonavelons();
    int lelonavelonsSizelon = lelonavelons.sizelon();
    Prelonconditions.chelonckStatelon(1 == lelonavelonsSizelon,
        "elonxpelonctelond onelon lelonaf relonadelonr in direlonctory %s, but found %s", direlonctory, lelonavelonsSizelon);
    relonturn lelonavelons.gelont(0).relonadelonr();
  }

  /**
   * Crelonatelons a nelonw SelongmelonntData instancelon using thelon providelond data.
   */
  public elonarlybirdIndelonxSelongmelonntData(
      int maxSelongmelonntSizelon,
      long timelonSlicelonID,
      Schelonma schelonma,
      boolelonan isOptimizelond,
      int smallelonstDocID,
      ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
      ConcurrelonntHashMap<String, ColumnStridelonBytelonIndelonx> normsMap,
      AbstractFacelontCountingArray facelontCountingArray,
      DocValuelonsManagelonr docValuelonsManagelonr,
      Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs,
      FacelontIDMap facelontIDMap,
      DelonlelontelondDocs delonlelontelondDocs,
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
      TimelonMappelonr timelonMappelonr) {
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    this.timelonSlicelonID = timelonSlicelonID;
    this.schelonma = schelonma;
    this.isOptimizelond = isOptimizelond;
    this.facelontCountingArray = facelontCountingArray;
    this.pelonrFielonldMap = pelonrFielonldMap;
    this.syncData = nelonw SyncData(buildIndelonxPointelonrs(), smallelonstDocID);
    this.normsMap = normsMap;
    this.docValuelonsManagelonr = docValuelonsManagelonr;
    this.facelontLabelonlProvidelonrs = facelontLabelonlProvidelonrs;
    this.facelontIDMap = facelontIDMap;
    this.delonlelontelondDocs = delonlelontelondDocs;
    this.docIdToTwelonelontIdMappelonr = docIdToTwelonelontIdMappelonr;
    this.timelonMappelonr = timelonMappelonr;

    Prelonconditions.chelonckNotNull(schelonma);
  }

  public final Schelonma gelontSchelonma() {
    relonturn schelonma;
  }

  /**
   * Relonturns all {@link elonarlybirdIndelonxelonxtelonnsionsData} instancelons containelond in this selongmelonnt.
   * Sincelon indelonx elonxtelonnsions arelon optional, thelon relonturnelond map might belon null or elonmpty.
   */
  public abstract <S elonxtelonnds elonarlybirdIndelonxelonxtelonnsionsData> S gelontIndelonxelonxtelonnsionsData();

  public DocIDToTwelonelontIDMappelonr gelontDocIDToTwelonelontIDMappelonr() {
    relonturn docIdToTwelonelontIdMappelonr;
  }

  public TimelonMappelonr gelontTimelonMappelonr() {
    relonturn timelonMappelonr;
  }

  public final DocValuelonsManagelonr gelontDocValuelonsManagelonr() {
    relonturn docValuelonsManagelonr;
  }

  public Map<String, FacelontLabelonlProvidelonr> gelontFacelontLabelonlProvidelonrs() {
    relonturn facelontLabelonlProvidelonrs;
  }

  public FacelontIDMap gelontFacelontIDMap() {
    relonturn facelontIDMap;
  }

  /**
   * Relonturns thelon QuelonryCachelonRelonsult for thelon givelonn filtelonr for this selongmelonnt.
   */
  public QuelonryCachelonRelonsultForSelongmelonnt gelontQuelonryCachelonRelonsult(String quelonryCachelonFiltelonrNamelon) {
    relonturn quelonryCachelonMap.gelont(quelonryCachelonFiltelonrNamelon);
  }

  public long gelontQuelonryCachelonsCardinality() {
    relonturn quelonryCachelonMap.valuelons().strelonam().mapToLong(q -> q.gelontCardinality()).sum();
  }

  /**
   * Gelont cachelon cardinality for elonach quelonry cachelon.
   * @relonturn
   */
  public List<Pair<String, Long>> gelontPelonrQuelonryCachelonCardinality() {
    ArrayList<Pair<String, Long>> relonsult = nelonw ArrayList<>();

    quelonryCachelonMap.forelonach((cachelonNamelon, quelonryCachelonRelonsult) -> {
      relonsult.add(Pair.of(cachelonNamelon, quelonryCachelonRelonsult.gelontCardinality()));
    });
    relonturn relonsult;
  }

  /**
   * Updatelons thelon QuelonryCachelonRelonsult storelond for thelon givelonn filtelonr for this selongmelonnt
   */
  public QuelonryCachelonRelonsultForSelongmelonnt updatelonQuelonryCachelonRelonsult(
      String quelonryCachelonFiltelonrNamelon, QuelonryCachelonRelonsultForSelongmelonnt quelonryCachelonRelonsultForSelongmelonnt) {
    relonturn quelonryCachelonMap.put(quelonryCachelonFiltelonrNamelon, quelonryCachelonRelonsultForSelongmelonnt);
  }

  /**
   * Subclasselons arelon allowelond to relonturn null helonrelon to disablelon writing to a FacelontCountingArray.
   */
  public FacelontCountingArrayWritelonr crelonatelonFacelontCountingArrayWritelonr() {
    relonturn gelontFacelontCountingArray() != null
        ? nelonw FacelontCountingArrayWritelonr(gelontFacelontCountingArray()) : null;
  }

  public int gelontMaxSelongmelonntSizelon() {
    relonturn maxSelongmelonntSizelon;
  }

  public long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }

  public void updatelonSmallelonstDocID(int smallelonstDocID) {
    // Atomic swap
    syncData = nelonw SyncData(Collelonctions.unmodifiablelonMap(buildIndelonxPointelonrs()), smallelonstDocID);
  }

  privatelon Map<InvelonrtelondIndelonx, Intelongelonr> buildIndelonxPointelonrs() {
    Map<InvelonrtelondIndelonx, Intelongelonr> nelonwIndelonxPointelonrs = nelonw HashMap<>();
    for (InvelonrtelondIndelonx indelonx : pelonrFielonldMap.valuelons()) {
      if (indelonx.hasMaxPublishelondPointelonr()) {
        nelonwIndelonxPointelonrs.put(indelonx, indelonx.gelontMaxPublishelondPointelonr());
      }
    }

    relonturn nelonwIndelonxPointelonrs;
  }

  public SyncData gelontSyncData() {
    relonturn syncData;
  }

  public AbstractFacelontCountingArray gelontFacelontCountingArray() {
    relonturn facelontCountingArray;
  }

  public void addFielonld(String fielonldNamelon, InvelonrtelondIndelonx fielonld) {
    pelonrFielonldMap.put(fielonldNamelon, fielonld);
  }

  public Map<String, InvelonrtelondIndelonx> gelontPelonrFielonldMap() {
    relonturn Collelonctions.unmodifiablelonMap(pelonrFielonldMap);
  }

  public InvelonrtelondIndelonx gelontFielonldIndelonx(String fielonldNamelon) {
    relonturn pelonrFielonldMap.gelont(fielonldNamelon);
  }

  public Map<String, ColumnStridelonBytelonIndelonx> gelontNormsMap() {
    relonturn Collelonctions.unmodifiablelonMap(normsMap);
  }

  public DelonlelontelondDocs gelontDelonlelontelondDocs() {
    relonturn delonlelontelondDocs;
  }

  /**
   * Relonturns thelon norms indelonx for thelon givelonn fielonld namelon.
   */
  public ColumnStridelonBytelonIndelonx gelontNormIndelonx(String fielonldNamelon) {
    relonturn normsMap == null ? null : normsMap.gelont(fielonldNamelon);
  }

  /**
   * Relonturns thelon norms indelonx for thelon givelonn fielonld namelon, add if not elonxist.
   */
  public ColumnStridelonBytelonIndelonx crelonatelonNormIndelonx(String fielonldNamelon) {
    if (normsMap == null) {
      relonturn null;
    }
    ColumnStridelonBytelonIndelonx csf = normsMap.gelont(fielonldNamelon);
    if (csf == null) {
      csf = nelonw ColumnStridelonBytelonIndelonx(fielonldNamelon, maxSelongmelonntSizelon);
      normsMap.put(fielonldNamelon, csf);
    }
    relonturn csf;
  }

  /**
   * Flushelons this selongmelonnt to disk.
   */
  public void flushSelongmelonnt(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
    gelontFlushHandlelonr().flush(flushInfo, out);
  }

  public final boolelonan isOptimizelond() {
    relonturn this.isOptimizelond;
  }

  /**
   * Relonturns a nelonw atomic relonadelonr for this selongmelonnt.
   */
  public elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr crelonatelonAtomicRelonadelonr() throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = doCrelonatelonAtomicRelonadelonr();
    elonarlybirdIndelonxelonxtelonnsionsData indelonxelonxtelonnsion = gelontIndelonxelonxtelonnsionsData();
    if (indelonxelonxtelonnsion != null) {
      indelonxelonxtelonnsion.selontupelonxtelonnsions(relonadelonr);
    }
    relonturn relonadelonr;
  }

  /**
   * Crelonatelons a nelonw atomic relonadelonr for this selongmelonnt.
   */
  protelonctelond abstract elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr doCrelonatelonAtomicRelonadelonr() throws IOelonxcelonption;

  /**
   * Crelonatelons a nelonw selongmelonnt writelonr for this selongmelonnt.
   */
  public abstract elonarlybirdIndelonxSelongmelonntWritelonr crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(
      IndelonxWritelonrConfig indelonxWritelonrConfig) throws IOelonxcelonption;

  public abstract static class AbstractSelongmelonntDataFlushHandlelonr
      <S elonxtelonnds elonarlybirdIndelonxelonxtelonnsionsData>
      elonxtelonnds Flushablelon.Handlelonr<elonarlybirdIndelonxSelongmelonntData> {
    protelonctelond static final String MAX_SelonGMelonNT_SIZelon_PROP_NAMelon = "maxSelongmelonntSizelon";
    protelonctelond static final String TIMelon_SLICelon_ID_PROP_NAMelon = "timelon_slicelon_id";
    protelonctelond static final String SMALLelonST_DOCID_PROP_NAMelon = "smallelonstDocID";
    protelonctelond static final String DOC_ID_MAPPelonR_SUBPROPS_NAMelon = "doc_id_mappelonr";
    protelonctelond static final String TIMelon_MAPPelonR_SUBPROPS_NAMelon = "timelon_mappelonr";
    public static final String IS_OPTIMIZelonD_PROP_NAMelon = "isOptimizelond";

    // Abstract melonthods child classelons should implelonmelonnt:
    // 1. How to additional data structurelons
    protelonctelond abstract void flushAdditionalDataStructurelons(
        FlushInfo flushInfo, DataSelonrializelonr out, elonarlybirdIndelonxSelongmelonntData toFlush)
            throws IOelonxcelonption;

    // 2. Load additional data structurelons and construct SelongmelonntData.
    // Common data structurelons should belon passelond into this melonthod to avoid codelon duplication.
    // Subclasselons should load additional data structurelons and construct a SelongmelonntData.
    protelonctelond abstract elonarlybirdIndelonxSelongmelonntData constructSelongmelonntData(
        FlushInfo flushInfo,
        ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
        int maxSelongmelonntSizelon,
        S indelonxelonxtelonnsion,
        DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
        TimelonMappelonr timelonMappelonr,
        DataDelonselonrializelonr in) throws IOelonxcelonption;

    protelonctelond abstract S nelonwIndelonxelonxtelonnsion();

    protelonctelond final Schelonma schelonma;
    protelonctelond final elonarlybirdIndelonxelonxtelonnsionsFactory indelonxelonxtelonnsionsFactory;
    privatelon final Flushablelon.Handlelonr<? elonxtelonnds DocIDToTwelonelontIDMappelonr> docIdMappelonrFlushHandlelonr;
    privatelon final Flushablelon.Handlelonr<? elonxtelonnds TimelonMappelonr> timelonMappelonrFlushHandlelonr;

    public AbstractSelongmelonntDataFlushHandlelonr(
        Schelonma schelonma,
        elonarlybirdIndelonxelonxtelonnsionsFactory indelonxelonxtelonnsionsFactory,
        Flushablelon.Handlelonr<? elonxtelonnds DocIDToTwelonelontIDMappelonr> docIdMappelonrFlushHandlelonr,
        Flushablelon.Handlelonr<? elonxtelonnds TimelonMappelonr> timelonMappelonrFlushHandlelonr) {
      supelonr();
      this.schelonma = schelonma;
      this.indelonxelonxtelonnsionsFactory = indelonxelonxtelonnsionsFactory;
      this.docIdMappelonrFlushHandlelonr = docIdMappelonrFlushHandlelonr;
      this.timelonMappelonrFlushHandlelonr = timelonMappelonrFlushHandlelonr;
    }

    public AbstractSelongmelonntDataFlushHandlelonr(elonarlybirdIndelonxSelongmelonntData objelonctToFlush) {
      supelonr(objelonctToFlush);
      this.schelonma = objelonctToFlush.schelonma;
      this.indelonxelonxtelonnsionsFactory = null; // factory only nelonelondelond for loading SelongmelonntData from disk
      this.docIdMappelonrFlushHandlelonr = null; // docIdMappelonrFlushHandlelonr nelonelondelond only for loading data
      this.timelonMappelonrFlushHandlelonr = null; // timelonMappelonrFlushHandlelonr nelonelondelond only for loading data
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
        throws IOelonxcelonption {
      elonarlybirdIndelonxSelongmelonntData selongmelonntData = gelontObjelonctToFlush();

      Prelonconditions.chelonckStatelon(selongmelonntData.docIdToTwelonelontIdMappelonr instancelonof Flushablelon);
      ((Flushablelon) selongmelonntData.docIdToTwelonelontIdMappelonr).gelontFlushHandlelonr().flush(
          flushInfo.nelonwSubPropelonrtielons(DOC_ID_MAPPelonR_SUBPROPS_NAMelon), out);

      if (selongmelonntData.timelonMappelonr != null) {
        selongmelonntData.timelonMappelonr.gelontFlushHandlelonr()
            .flush(flushInfo.nelonwSubPropelonrtielons(TIMelon_MAPPelonR_SUBPROPS_NAMelon), out);
      }

      flushInfo.addBoolelonanPropelonrty(IS_OPTIMIZelonD_PROP_NAMelon, selongmelonntData.isOptimizelond());
      flushInfo.addIntPropelonrty(MAX_SelonGMelonNT_SIZelon_PROP_NAMelon, selongmelonntData.gelontMaxSelongmelonntSizelon());
      flushInfo.addLongPropelonrty(TIMelon_SLICelon_ID_PROP_NAMelon, selongmelonntData.gelontTimelonSlicelonID());
      flushInfo.addIntPropelonrty(SMALLelonST_DOCID_PROP_NAMelon,
                               selongmelonntData.gelontSyncData().gelontSmallelonstDocID());

      flushIndelonxelons(flushInfo, out, selongmelonntData);

      // Flush clustelonr speloncific data structurelons:
      // FacelontCountingArray, TwelonelontIDMappelonr, LatLonMappelonr, and TimelonMappelonr
      flushAdditionalDataStructurelons(flushInfo, out, selongmelonntData);
    }

    privatelon void flushIndelonxelons(
        FlushInfo flushInfo,
        DataSelonrializelonr out,
        elonarlybirdIndelonxSelongmelonntData selongmelonntData) throws IOelonxcelonption {
      Map<String, InvelonrtelondIndelonx> pelonrFielonldMap = selongmelonntData.gelontPelonrFielonldMap();
      FlushInfo fielonldProps = flushInfo.nelonwSubPropelonrtielons("fielonlds");
      long sizelonBelonforelonFlush = out.lelonngth();
      for (Map.elonntry<String, InvelonrtelondIndelonx> elonntry : pelonrFielonldMap.elonntrySelont()) {
        String fielonldNamelon = elonntry.gelontKelony();
        elonntry.gelontValuelon().gelontFlushHandlelonr().flush(fielonldProps.nelonwSubPropelonrtielons(fielonldNamelon), out);
      }
      fielonldProps.selontSizelonInBytelons(out.lelonngth() - sizelonBelonforelonFlush);
    }

    @Ovelonrridelon
    protelonctelond elonarlybirdIndelonxSelongmelonntData doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in)
        throws IOelonxcelonption {
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr = docIdMappelonrFlushHandlelonr.load(
          flushInfo.gelontSubPropelonrtielons(DOC_ID_MAPPelonR_SUBPROPS_NAMelon), in);

      FlushInfo timelonMappelonrFlushInfo = flushInfo.gelontSubPropelonrtielons(TIMelon_MAPPelonR_SUBPROPS_NAMelon);
      TimelonMappelonr timelonMappelonr =
          timelonMappelonrFlushInfo != null ? timelonMappelonrFlushHandlelonr.load(timelonMappelonrFlushInfo, in) : null;

      final int maxSelongmelonntSizelon = flushInfo.gelontIntPropelonrty(MAX_SelonGMelonNT_SIZelon_PROP_NAMelon);
      ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap = loadIndelonxelons(flushInfo, in);
      relonturn constructSelongmelonntData(
          flushInfo,
          pelonrFielonldMap,
          maxSelongmelonntSizelon,
          nelonwIndelonxelonxtelonnsion(),
          docIdToTwelonelontIdMappelonr,
          timelonMappelonr,
          in);
    }

    // Movelon this melonthod into elonarlybirdRelonaltimelonIndelonxSelongmelonntData (carelonful,
    // welon may nelonelond to increlonmelonnt FlushVelonrsion beloncauselon elonarlybirdLucelonnelonIndelonxSelongmelonntData
    // currelonntly has thelon 'fielonlds' subpropelonrty in its FlushInfo as welonll)
    privatelon ConcurrelonntHashMap<String, InvelonrtelondIndelonx> loadIndelonxelons(
        FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap = nelonw ConcurrelonntHashMap<>();

      FlushInfo fielonldProps = flushInfo.gelontSubPropelonrtielons("fielonlds");
      Itelonrator<String> fielonldItelonrator = fielonldProps.gelontKelonyItelonrator();
      whilelon (fielonldItelonrator.hasNelonxt()) {
        String fielonldNamelon = fielonldItelonrator.nelonxt();
        elonarlybirdFielonldTypelon fielonldTypelon = schelonma.gelontFielonldInfo(fielonldNamelon).gelontFielonldTypelon();
        FlushInfo subProp = fielonldProps.gelontSubPropelonrtielons(fielonldNamelon);
        boolelonan isOptimizelond = subProp.gelontBoolelonanPropelonrty(
            OptimizelondMelonmoryIndelonx.FlushHandlelonr.IS_OPTIMIZelonD_PROP_NAMelon);
        final InvelonrtelondIndelonx invelonrtelondIndelonx;
        if (isOptimizelond) {
          if (!fielonldTypelon.beloncomelonsImmutablelon()) {
            throw nelonw IOelonxcelonption("Trielond to load an optimizelond fielonld that is not immutablelon: "
                + fielonldNamelon);
          }
          invelonrtelondIndelonx = (nelonw OptimizelondMelonmoryIndelonx.FlushHandlelonr(fielonldTypelon)).load(subProp, in);
        } elonlselon {
          invelonrtelondIndelonx = (nelonw InvelonrtelondRelonaltimelonIndelonx.FlushHandlelonr(
                               fielonldTypelon, TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING))
              .load(subProp, in);
        }
        pelonrFielonldMap.put(fielonldNamelon, invelonrtelondIndelonx);
      }
      relonturn pelonrFielonldMap;
    }
  }

  public int numDocs() {
    relonturn docIdToTwelonelontIdMappelonr.gelontNumDocs();
  }
}
