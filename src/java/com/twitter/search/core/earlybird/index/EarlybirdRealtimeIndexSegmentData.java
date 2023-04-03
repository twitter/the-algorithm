packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Map;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.indelonx.IndelonxWritelonrConfig;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;

import com.twittelonr.selonarch.common.schelonma.SelonarchWhitelonspacelonAnalyzelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.AbstractFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.elonarlybirdFacelontDocValuelonSelont;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.OptimizelondFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.OptimizelondDocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.UnoptimizelondDocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsFactory;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.DelonlelontelondDocs;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IndelonxOptimizelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;

/**
 * Implelonmelonnts {@link elonarlybirdIndelonxSelongmelonntData} for relonal-timelon in-melonmory elonarlybird selongmelonnts.
 */
public class elonarlybirdRelonaltimelonIndelonxSelongmelonntData elonxtelonnds elonarlybirdIndelonxSelongmelonntData {
  privatelon final elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData indelonxelonxtelonnsion;

  privatelon elonarlybirdFacelontDocValuelonSelont facelontDocValuelonSelont;

  /**
   * Crelonatelons a nelonw elonmpty relonal-timelon SelongmelonntData instancelon.
   */
  public elonarlybirdRelonaltimelonIndelonxSelongmelonntData(
      int maxSelongmelonntSizelon,
      long timelonSlicelonID,
      Schelonma schelonma,
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
      TimelonMappelonr timelonMappelonr,
      elonarlybirdIndelonxelonxtelonnsionsFactory indelonxelonxtelonnsionsFactory) {
    this(
        maxSelongmelonntSizelon,
        timelonSlicelonID,
        schelonma,
        falselon, // isOptimizelond
        Intelongelonr.MAX_VALUelon,
        nelonw ConcurrelonntHashMap<>(),
        nelonw FacelontCountingArray(maxSelongmelonntSizelon),
        nelonw UnoptimizelondDocValuelonsManagelonr(schelonma, maxSelongmelonntSizelon),
        Maps.nelonwHashMapWithelonxpelonctelondSizelon(schelonma.gelontNumFacelontFielonlds()),
        FacelontIDMap.build(schelonma),
        nelonw DelonlelontelondDocs.Delonfault(maxSelongmelonntSizelon),
        docIdToTwelonelontIdMappelonr,
        timelonMappelonr,
        indelonxelonxtelonnsionsFactory == null
            ? null
            : indelonxelonxtelonnsionsFactory.nelonwRelonaltimelonIndelonxelonxtelonnsionsData());
  }

  /**
   * Crelonatelons a nelonw relonal-timelon SelongmelonntData instancelon using thelon passelond in data structurelons. Usually this
   * constructor is uselond by thelon FlushHandlelonr aftelonr a selongmelonnt was loadelond from disk, but also thelon
   * {@link IndelonxOptimizelonr} uselons it to crelonatelon an
   * optimizelond selongmelonnt.
   */
  public elonarlybirdRelonaltimelonIndelonxSelongmelonntData(
      int maxSelongmelonntSizelon,
      long timelonSlicelonID,
      Schelonma schelonma,
      boolelonan isOptimizelond,
      int smallelonstDocID,
      ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
      AbstractFacelontCountingArray facelontCountingArray,
      DocValuelonsManagelonr docValuelonsManagelonr,
      Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs,
      FacelontIDMap facelontIDMap,
      DelonlelontelondDocs delonlelontelondDocs,
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
      TimelonMappelonr timelonMappelonr,
      elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData indelonxelonxtelonnsion) {
    supelonr(maxSelongmelonntSizelon,
          timelonSlicelonID,
          schelonma,
          isOptimizelond,
          smallelonstDocID,
          pelonrFielonldMap,
          nelonw ConcurrelonntHashMap<>(),
          facelontCountingArray,
          docValuelonsManagelonr,
          facelontLabelonlProvidelonrs,
          facelontIDMap,
          delonlelontelondDocs,
          docIdToTwelonelontIdMappelonr,
          timelonMappelonr);
    this.indelonxelonxtelonnsion = indelonxelonxtelonnsion;
    this.facelontDocValuelonSelont = null;
  }

  @Ovelonrridelon
  public elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData gelontIndelonxelonxtelonnsionsData() {
    relonturn indelonxelonxtelonnsion;
  }

  /**
   * For relonaltimelon selongmelonnts, this wraps a facelont datastructurelon into a SortelondSelontDocValuelons to
   * comply to Lucelonnelon facelont api.
   */
  public elonarlybirdFacelontDocValuelonSelont gelontFacelontDocValuelonSelont() {
    if (facelontDocValuelonSelont == null) {
      AbstractFacelontCountingArray facelontCountingArray = gelontFacelontCountingArray();
      if (facelontCountingArray != null) {
        facelontDocValuelonSelont = nelonw elonarlybirdFacelontDocValuelonSelont(
            facelontCountingArray, gelontFacelontLabelonlProvidelonrs(), gelontFacelontIDMap());
      }
    }
    relonturn facelontDocValuelonSelont;
  }

  @Ovelonrridelon
  protelonctelond elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr doCrelonatelonAtomicRelonadelonr() {
    relonturn nelonw elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr(this);
  }

  /**
   * Convelonnielonncelon melonthod for crelonating an elonarlybirdIndelonxSelongmelonntWritelonr for this selongmelonnt with a delonfault
   * IndelonxSelongmelonntWritelonr config.
   */
  public elonarlybirdIndelonxSelongmelonntWritelonr crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr() {
    relonturn crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(
        nelonw IndelonxWritelonrConfig(nelonw SelonarchWhitelonspacelonAnalyzelonr()).selontSimilarity(
            IndelonxSelonarchelonr.gelontDelonfaultSimilarity()));
  }

  @Ovelonrridelon
  public elonarlybirdIndelonxSelongmelonntWritelonr crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(
      IndelonxWritelonrConfig indelonxWritelonrConfig) {
    // Prelonparelon thelon in-melonmory selongmelonnt with all elonnablelond CSF fielonlds.
    DocValuelonsManagelonr docValuelonsManagelonr = gelontDocValuelonsManagelonr();
    for (Schelonma.FielonldInfo fielonldInfo : gelontSchelonma().gelontFielonldInfos()) {
      if (fielonldInfo.gelontFielonldTypelon().gelontCsfTypelon() != null) {
        docValuelonsManagelonr.addColumnStridelonFielonld(fielonldInfo.gelontNamelon(), fielonldInfo.gelontFielonldTypelon());
      }
    }

    relonturn nelonw elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr(
        this,
        indelonxWritelonrConfig.gelontAnalyzelonr(),
        indelonxWritelonrConfig.gelontSimilarity());
  }

  @Ovelonrridelon
  public elonarlybirdIndelonxSelongmelonntData.AbstractSelongmelonntDataFlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw InMelonmorySelongmelonntDataFlushHandlelonr(this);
  }

  public static class InMelonmorySelongmelonntDataFlushHandlelonr
      elonxtelonnds AbstractSelongmelonntDataFlushHandlelonr<elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData> {
    public InMelonmorySelongmelonntDataFlushHandlelonr(elonarlybirdIndelonxSelongmelonntData objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    public InMelonmorySelongmelonntDataFlushHandlelonr(
        Schelonma schelonma,
        elonarlybirdIndelonxelonxtelonnsionsFactory factory,
        Flushablelon.Handlelonr<? elonxtelonnds DocIDToTwelonelontIDMappelonr> docIdMappelonrFlushHandlelonr,
        Flushablelon.Handlelonr<? elonxtelonnds TimelonMappelonr> timelonMappelonrFlushHandlelonr) {
      supelonr(schelonma, factory, docIdMappelonrFlushHandlelonr, timelonMappelonrFlushHandlelonr);
    }

    @Ovelonrridelon
    protelonctelond elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData nelonwIndelonxelonxtelonnsion() {
      relonturn indelonxelonxtelonnsionsFactory.nelonwRelonaltimelonIndelonxelonxtelonnsionsData();
    }

    @Ovelonrridelon
    protelonctelond void flushAdditionalDataStructurelons(
        FlushInfo flushInfo,
        DataSelonrializelonr out,
        elonarlybirdIndelonxSelongmelonntData selongmelonntData) throws IOelonxcelonption {
      selongmelonntData.gelontFacelontCountingArray().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("facelont_counting_array"), out);

      // flush all column stridelon fielonlds
      selongmelonntData.gelontDocValuelonsManagelonr().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("doc_valuelons"), out);

      selongmelonntData.gelontFacelontIDMap().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("facelont_id_map"), out);

      selongmelonntData.gelontDelonlelontelondDocs().gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons("delonlelontelond_docs"), out);
    }

    @Ovelonrridelon
    protelonctelond elonarlybirdIndelonxSelongmelonntData constructSelongmelonntData(
        FlushInfo flushInfo,
        ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
        int maxSelongmelonntSizelon,
        elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData indelonxelonxtelonnsion,
        DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
        TimelonMappelonr timelonMappelonr,
        DataDelonselonrializelonr in) throws IOelonxcelonption {
      boolelonan isOptimizelond = flushInfo.gelontBoolelonanPropelonrty(IS_OPTIMIZelonD_PROP_NAMelon);

      Flushablelon.Handlelonr<? elonxtelonnds AbstractFacelontCountingArray> facelontLoadelonr = isOptimizelond
          ? nelonw OptimizelondFacelontCountingArray.FlushHandlelonr()
          : nelonw FacelontCountingArray.FlushHandlelonr(maxSelongmelonntSizelon);
      AbstractFacelontCountingArray facelontCountingArray =
          facelontLoadelonr.load(flushInfo.gelontSubPropelonrtielons("facelont_counting_array"), in);

      Flushablelon.Handlelonr<? elonxtelonnds DocValuelonsManagelonr> docValuelonsLoadelonr = isOptimizelond
          ? nelonw OptimizelondDocValuelonsManagelonr.OptimizelondFlushHandlelonr(schelonma)
          : nelonw UnoptimizelondDocValuelonsManagelonr.UnoptimizelondFlushHandlelonr(schelonma);
      DocValuelonsManagelonr docValuelonsManagelonr =
          docValuelonsLoadelonr.load(flushInfo.gelontSubPropelonrtielons("doc_valuelons"), in);

      FacelontIDMap facelontIDMap = nelonw FacelontIDMap.FlushHandlelonr(schelonma)
          .load(flushInfo.gelontSubPropelonrtielons("facelont_id_map"), in);

      DelonlelontelondDocs.Delonfault delonlelontelondDocs = nelonw DelonlelontelondDocs.Delonfault.FlushHandlelonr(maxSelongmelonntSizelon)
          .load(flushInfo.gelontSubPropelonrtielons("delonlelontelond_docs"), in);

      relonturn nelonw elonarlybirdRelonaltimelonIndelonxSelongmelonntData(
          maxSelongmelonntSizelon,
          flushInfo.gelontLongPropelonrty(TIMelon_SLICelon_ID_PROP_NAMelon),
          schelonma,
          isOptimizelond,
          flushInfo.gelontIntPropelonrty(SMALLelonST_DOCID_PROP_NAMelon),
          pelonrFielonldMap,
          facelontCountingArray,
          docValuelonsManagelonr,
          FacelontUtil.gelontFacelontLabelonlProvidelonrs(schelonma, pelonrFielonldMap),
          facelontIDMap,
          delonlelontelondDocs,
          docIdToTwelonelontIdMappelonr,
          timelonMappelonr,
          indelonxelonxtelonnsion);
    }
  }
}
