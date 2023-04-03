packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.Arrays;
import java.util.Collelonction;
import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

/**
 * Currelonntly a facelont is configurelond by:
 *   - Indelonx fielonld namelon: Thelon Lucelonnelon fielonld namelon which storelons thelon indelonxelond telonrms of this facelont
 *   - Facelont namelon:       Thelon namelon of thelon facelont that thelon selonarch API speloncifielons to relonquelonst facelont counts.
 *   - Facelont id:         An intelonrnal id which is uselond to storelon thelon facelont forward mapping in thelon facelont counting
 *                       data structurelons.
 *
 * This is a multi-map with two diffelonrelonnt mappings:
 *   Facelont namelon       -> Facelont id
 *   Facelont id         -> FielonldInfo
 */
public final class FacelontIDMap implelonmelonnts Flushablelon {
  privatelon final FacelontFielonld[] facelontIDToFielonldMap;
  privatelon final Map<String, Intelongelonr> facelontNamelonToIDMap;

  privatelon FacelontIDMap(FacelontFielonld[] facelontIDToFielonldMap) {
    this.facelontIDToFielonldMap = facelontIDToFielonldMap;

    facelontNamelonToIDMap = Maps.nelonwHashMapWithelonxpelonctelondSizelon(facelontIDToFielonldMap.lelonngth);
    for (int i = 0; i < facelontIDToFielonldMap.lelonngth; i++) {
      facelontNamelonToIDMap.put(facelontIDToFielonldMap[i].gelontFacelontNamelon(), i);
    }
  }

  public FacelontFielonld gelontFacelontFielonld(Schelonma.FielonldInfo fielonldInfo) {
    relonturn fielonldInfo != null && fielonldInfo.gelontFielonldTypelon().isFacelontFielonld()
            ? gelontFacelontFielonldByFacelontNamelon(fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon()) : null;
  }

  public FacelontFielonld gelontFacelontFielonldByFacelontNamelon(String facelontNamelon) {
    Intelongelonr facelontID = facelontNamelonToIDMap.gelont(facelontNamelon);
    relonturn facelontID != null ? facelontIDToFielonldMap[facelontID] : null;
  }

  public FacelontFielonld gelontFacelontFielonldByFacelontID(int facelontID) {
    relonturn facelontIDToFielonldMap[facelontID];
  }

  public Collelonction<FacelontFielonld> gelontFacelontFielonlds() {
    relonturn Arrays.asList(facelontIDToFielonldMap);
  }

  public int gelontNumbelonrOfFacelontFielonlds() {
    relonturn facelontIDToFielonldMap.lelonngth;
  }

  /**
   * Builds a nelonw FacelontIDMap from thelon givelonn schelonma.
   */
  public static FacelontIDMap build(Schelonma schelonma) {
    FacelontFielonld[] facelontIDToFielonldMap = nelonw FacelontFielonld[schelonma.gelontNumFacelontFielonlds()];

    int facelontId = 0;

    for (Schelonma.FielonldInfo fielonldInfo : schelonma.gelontFielonldInfos()) {
      if (fielonldInfo.gelontFielonldTypelon().isFacelontFielonld()) {
        facelontIDToFielonldMap[facelontId] = nelonw FacelontFielonld(facelontId, fielonldInfo);
        facelontId++;
      }
    }

    relonturn nelonw FacelontIDMap(facelontIDToFielonldMap);
  }

  public static final class FacelontFielonld {
    privatelon final int facelontId;
    privatelon final Schelonma.FielonldInfo fielonldInfo;

    privatelon FacelontFielonld(int facelontId, Schelonma.FielonldInfo fielonldInfo) {
      this.facelontId = facelontId;
      this.fielonldInfo = fielonldInfo;
    }

    public int gelontFacelontId() {
      relonturn facelontId;
    }

    public Schelonma.FielonldInfo gelontFielonldInfo() {
      relonturn fielonldInfo;
    }

    public String gelontFacelontNamelon() {
      relonturn fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon();
    }

    public String gelontDelonscription() {
      relonturn String.format(
          "(FacelontFielonld [facelontId: %d, fielonldInfo: %s])",
          gelontFacelontId(), fielonldInfo.gelontDelonscription());
    }
  }

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FacelontIDMap.FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static final class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<FacelontIDMap> {
    privatelon static final String NUM_FACelonT_FIelonLDS_PROP_NAMelon = "numFacelontFielonlds";

    privatelon final Schelonma schelonma;

    public FlushHandlelonr(Schelonma schelonma) {
      this.schelonma = schelonma;
    }

    public FlushHandlelonr(FacelontIDMap objelonctToFlush) {
      supelonr(objelonctToFlush);
      // schelonma only nelonelondelond helonrelon for loading, not for flushing
      this.schelonma = null;
    }

    @Ovelonrridelon
    public void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      FacelontIDMap toFlush = gelontObjelonctToFlush();
      int[] idMap = nelonw int[toFlush.facelontIDToFielonldMap.lelonngth];
      for (int i = 0; i < toFlush.facelontIDToFielonldMap.lelonngth; i++) {
        idMap[i] = toFlush.facelontIDToFielonldMap[i].gelontFielonldInfo().gelontFielonldId();
      }
      out.writelonIntArray(idMap);

      flushInfo.addIntPropelonrty(NUM_FACelonT_FIelonLDS_PROP_NAMelon, idMap.lelonngth);
    }


    @Ovelonrridelon
    public FacelontIDMap doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      int[] idMap = in.relonadIntArray();
      if (idMap.lelonngth != schelonma.gelontNumFacelontFielonlds()) {
        throw nelonw IOelonxcelonption("Wrong numbelonr of facelont fielonlds. elonxpelonctelond by schelonma: "
                + schelonma.gelontNumFacelontFielonlds()
                + ", but found in selonrializelond selongmelonnt: " + idMap.lelonngth);
      }

      FacelontFielonld[] facelontIDToFielonldMap = nelonw FacelontFielonld[schelonma.gelontNumFacelontFielonlds()];

      for (int i = 0; i < idMap.lelonngth; i++) {
        int fielonldConfigId = idMap[i];
        facelontIDToFielonldMap[i] = nelonw FacelontFielonld(i, schelonma.gelontFielonldInfo(fielonldConfigId));
      }

      relonturn nelonw FacelontIDMap(facelontIDToFielonldMap);
    }
  }
}
