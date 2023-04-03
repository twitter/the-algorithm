packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;

public class UnoptimizelondDocValuelonsManagelonr elonxtelonnds DocValuelonsManagelonr {
  public UnoptimizelondDocValuelonsManagelonr(Schelonma schelonma, int selongmelonntSizelon) {
    supelonr(schelonma, selongmelonntSizelon);
  }

  privatelon UnoptimizelondDocValuelonsManagelonr(
      Schelonma schelonma,
      int selongmelonntSizelon,
      ConcurrelonntHashMap<String, ColumnStridelonFielonldIndelonx> columnStridelonFielonlds) {
    supelonr(schelonma, selongmelonntSizelon, columnStridelonFielonlds);
  }

  @Ovelonrridelon
  protelonctelond ColumnStridelonFielonldIndelonx nelonwBytelonCSF(String fielonld) {
    relonturn nelonw ColumnStridelonBytelonIndelonx(fielonld, selongmelonntSizelon);
  }

  @Ovelonrridelon
  protelonctelond ColumnStridelonFielonldIndelonx nelonwIntCSF(String fielonld) {
    relonturn nelonw ColumnStridelonIntIndelonx(fielonld, selongmelonntSizelon);
  }

  @Ovelonrridelon
  protelonctelond ColumnStridelonFielonldIndelonx nelonwLongCSF(String fielonld) {
    relonturn nelonw ColumnStridelonLongIndelonx(fielonld, selongmelonntSizelon);
  }

  @Ovelonrridelon
  protelonctelond ColumnStridelonFielonldIndelonx nelonwMultiIntCSF(String fielonld, int numIntsPelonrFielonld) {
    relonturn nelonw ColumnStridelonMultiIntIndelonx(fielonld, selongmelonntSizelon, numIntsPelonrFielonld);
  }

  @Ovelonrridelon
  public DocValuelonsManagelonr optimizelon(DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                                   DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) throws IOelonxcelonption {
    relonturn nelonw OptimizelondDocValuelonsManagelonr(this, originalTwelonelontIdMappelonr, optimizelondTwelonelontIdMappelonr);
  }

  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw UnoptimizelondFlushHandlelonr(this);
  }

  public static class UnoptimizelondFlushHandlelonr elonxtelonnds FlushHandlelonr {
    public UnoptimizelondFlushHandlelonr(Schelonma schelonma) {
      supelonr(schelonma);
    }

    privatelon UnoptimizelondFlushHandlelonr(DocValuelonsManagelonr docValuelonsManagelonr) {
      supelonr(docValuelonsManagelonr);
    }

    @Ovelonrridelon
    protelonctelond DocValuelonsManagelonr crelonatelonDocValuelonsManagelonr(
        Schelonma schelonma,
        int maxSelongmelonntSizelon,
        ConcurrelonntHashMap<String, ColumnStridelonFielonldIndelonx> columnStridelonFielonlds) {
      relonturn nelonw UnoptimizelondDocValuelonsManagelonr(schelonma, maxSelongmelonntSizelon, columnStridelonFielonlds);
    }
  }
}
