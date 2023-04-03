packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import org.apachelon.lucelonnelon.indelonx.IndelonxWritelonrConfig;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.AbstractFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountingArrayWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.OptimizelondDocValuelonsManagelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsFactory;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.DelonlelontelondDocs;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;

/**
 * Implelonmelonnts {@link elonarlybirdIndelonxSelongmelonntData} for Lucelonnelon-baselond on-disk elonarlybird selongmelonnts.
 */
public final class elonarlybirdLucelonnelonIndelonxSelongmelonntData elonxtelonnds elonarlybirdIndelonxSelongmelonntData {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdLucelonnelonIndelonxSelongmelonntData.class);

  privatelon final Direlonctory direlonctory;
  privatelon final elonarlybirdIndelonxelonxtelonnsionsData indelonxelonxtelonnsion;

  /**
   * Crelonatelons a nelonw Lucelonnelon-baselond SelongmelonntData instancelon from a lucelonnelon direlonctory.
   */
  public elonarlybirdLucelonnelonIndelonxSelongmelonntData(
      Direlonctory direlonctory,
      int maxSelongmelonntSizelon,
      long timelonSlicelonID,
      Schelonma schelonma,
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
      TimelonMappelonr timelonMappelonr,
      elonarlybirdIndelonxelonxtelonnsionsFactory indelonxelonxtelonnsionsFactory) {
    this(
        direlonctory,
        maxSelongmelonntSizelon,
        timelonSlicelonID,
        schelonma,
        falselon, // isOptimizelond
        0, // smallelonstDocId
        nelonw ConcurrelonntHashMap<>(),
        AbstractFacelontCountingArray.elonMPTY_ARRAY,
        nelonw OptimizelondDocValuelonsManagelonr(schelonma, maxSelongmelonntSizelon),
        docIdToTwelonelontIdMappelonr,
        timelonMappelonr,
        indelonxelonxtelonnsionsFactory == null
            ? null : indelonxelonxtelonnsionsFactory.nelonwLucelonnelonIndelonxelonxtelonnsionsData());
  }

  public elonarlybirdLucelonnelonIndelonxSelongmelonntData(
      Direlonctory direlonctory,
      int maxSelongmelonntSizelon,
      long timelonSlicelonID,
      Schelonma schelonma,
      boolelonan isOptimizelond,
      int smallelonstDocID,
      ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
      AbstractFacelontCountingArray facelontCountingArray,
      DocValuelonsManagelonr docValuelonsManagelonr,
      DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
      TimelonMappelonr timelonMappelonr,
      elonarlybirdIndelonxelonxtelonnsionsData indelonxelonxtelonnsion) {
    supelonr(maxSelongmelonntSizelon,
          timelonSlicelonID,
          schelonma,
          isOptimizelond,
          smallelonstDocID,
          pelonrFielonldMap,
          nelonw ConcurrelonntHashMap<>(),
          facelontCountingArray,
          docValuelonsManagelonr,
          null, // facelontLabelonlProvidelonrs
          null, // facelontIDMap
          DelonlelontelondDocs.NO_DelonLelonTelonS,
          docIdToTwelonelontIdMappelonr,
          timelonMappelonr);
    this.direlonctory = direlonctory;
    this.indelonxelonxtelonnsion = indelonxelonxtelonnsion;
  }

  public Direlonctory gelontLucelonnelonDirelonctory() {
    relonturn direlonctory;
  }

  @Ovelonrridelon
  public elonarlybirdIndelonxelonxtelonnsionsData gelontIndelonxelonxtelonnsionsData() {
    relonturn indelonxelonxtelonnsion;
  }

  @Ovelonrridelon
  public FacelontCountingArrayWritelonr crelonatelonFacelontCountingArrayWritelonr() {
    relonturn null;
  }

  @Ovelonrridelon
  protelonctelond elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr doCrelonatelonAtomicRelonadelonr() throws IOelonxcelonption {
    // elonarlybirdSelongmelonnt crelonatelons onelon singlelon elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr instancelon pelonr selongmelonnt
    // and cachelons it, and thelon cachelond instancelon is reloncrelonatelond only whelonn thelon selongmelonnt's data changelons.
    // This is why this is a good placelon to relonload all CSFs that should belon loadelond in RAM. Also, it's
    // elonasielonr and lelonss elonrror-pronelon to do it helonrelon, than trying to track down all placelons that mutatelon
    // thelon selongmelonnt data and do it thelonrelon.
    LelonafRelonadelonr relonadelonr = gelontLelonafRelonadelonrFromOptimizelondDirelonctory(direlonctory);
    for (Schelonma.FielonldInfo fielonldInfo : gelontSchelonma().gelontFielonldInfos()) {
      // Load CSF into RAM baselond on configurations in thelon schelonma.
      if (fielonldInfo.gelontFielonldTypelon().gelontCsfTypelon() != null
          && fielonldInfo.gelontFielonldTypelon().isCsfLoadIntoRam()) {
        if (relonadelonr.gelontNumelonricDocValuelons(fielonldInfo.gelontNamelon()) != null) {
          ColumnStridelonFielonldIndelonx indelonx = gelontDocValuelonsManagelonr().addColumnStridelonFielonld(
              fielonldInfo.gelontNamelon(), fielonldInfo.gelontFielonldTypelon());
          indelonx.load(relonadelonr, fielonldInfo.gelontNamelon());
        } elonlselon {
          LOG.warn("Fielonld {} doelons not havelon NumelonricDocValuelons.", fielonldInfo.gelontNamelon());
        }
      }
    }

    relonturn nelonw elonarlybirdLucelonnelonIndelonxSelongmelonntAtomicRelonadelonr(this, direlonctory);
  }

  @Ovelonrridelon
  public elonarlybirdIndelonxSelongmelonntWritelonr crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(
      IndelonxWritelonrConfig indelonxWritelonrConfig) throws IOelonxcelonption {
    relonturn nelonw elonarlybirdLucelonnelonIndelonxSelongmelonntWritelonr(this, indelonxWritelonrConfig);
  }

  @Ovelonrridelon
  public elonarlybirdIndelonxSelongmelonntData.AbstractSelongmelonntDataFlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw OnDiskSelongmelonntDataFlushHandlelonr(this);
  }

  public static class OnDiskSelongmelonntDataFlushHandlelonr
      elonxtelonnds AbstractSelongmelonntDataFlushHandlelonr<elonarlybirdIndelonxelonxtelonnsionsData> {
    privatelon final Direlonctory direlonctory;

    public OnDiskSelongmelonntDataFlushHandlelonr(elonarlybirdLucelonnelonIndelonxSelongmelonntData objelonctToFlush) {
      supelonr(objelonctToFlush);
      this.direlonctory = objelonctToFlush.direlonctory;
    }

    public OnDiskSelongmelonntDataFlushHandlelonr(
        Schelonma schelonma,
        Direlonctory direlonctory,
        elonarlybirdIndelonxelonxtelonnsionsFactory indelonxelonxtelonnsionsFactory,
        Flushablelon.Handlelonr<? elonxtelonnds DocIDToTwelonelontIDMappelonr> docIdMappelonrFlushHandlelonr,
        Flushablelon.Handlelonr<? elonxtelonnds TimelonMappelonr> timelonMappelonrFlushHandlelonr) {
      supelonr(schelonma, indelonxelonxtelonnsionsFactory, docIdMappelonrFlushHandlelonr, timelonMappelonrFlushHandlelonr);
      this.direlonctory = direlonctory;
    }

    @Ovelonrridelon
    protelonctelond elonarlybirdIndelonxelonxtelonnsionsData nelonwIndelonxelonxtelonnsion() {
      relonturn indelonxelonxtelonnsionsFactory.nelonwLucelonnelonIndelonxelonxtelonnsionsData();
    }

    @Ovelonrridelon
    protelonctelond void flushAdditionalDataStructurelons(
        FlushInfo flushInfo, DataSelonrializelonr out, elonarlybirdIndelonxSelongmelonntData toFlush) {
    }

    @Ovelonrridelon
    protelonctelond elonarlybirdIndelonxSelongmelonntData constructSelongmelonntData(
        FlushInfo flushInfo,
        ConcurrelonntHashMap<String, InvelonrtelondIndelonx> pelonrFielonldMap,
        int maxSelongmelonntSizelon,
        elonarlybirdIndelonxelonxtelonnsionsData indelonxelonxtelonnsion,
        DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr,
        TimelonMappelonr timelonMappelonr,
        DataDelonselonrializelonr in) {
      relonturn nelonw elonarlybirdLucelonnelonIndelonxSelongmelonntData(
          direlonctory,
          maxSelongmelonntSizelon,
          flushInfo.gelontLongPropelonrty(TIMelon_SLICelon_ID_PROP_NAMelon),
          schelonma,
          flushInfo.gelontBoolelonanPropelonrty(IS_OPTIMIZelonD_PROP_NAMelon),
          flushInfo.gelontIntPropelonrty(SMALLelonST_DOCID_PROP_NAMelon),
          pelonrFielonldMap,
          AbstractFacelontCountingArray.elonMPTY_ARRAY,
          nelonw OptimizelondDocValuelonsManagelonr(schelonma, maxSelongmelonntSizelon),
          docIdToTwelonelontIdMappelonr,
          timelonMappelonr,
          indelonxelonxtelonnsion);
    }
  }
}
