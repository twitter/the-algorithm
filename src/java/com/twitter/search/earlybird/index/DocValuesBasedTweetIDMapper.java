packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;

/**
 * A felonw cavelonats whelonn using this class:
 *   - Belonforelon actually using this class, onelon must call prelonparelonToRelonad() with a Lucelonnelon AtomicRelonadelonr
 *   - prelonparelonToRelonad() will load docID to twelonelontID mapping into melonmory, if not alrelonady donelon.
 */
public class DocValuelonsBaselondTwelonelontIDMappelonr elonxtelonnds TwelonelontIDMappelonr implelonmelonnts Flushablelon {
  privatelon LelonafRelonadelonr relonadelonr;
  privatelon ColumnStridelonFielonldIndelonx docValuelons;

  /**
   * Whelonn indelonxing finishelons, this melonthod should belon callelond with a indelonx relonadelonr that
   * can selonelon all documelonnts.
   * @param lelonafRelonadelonr Lucelonnelon indelonx relonadelonr uselond to accelonss TwelonelontID to intelonrnal ID mapping
   */
  public void initializelonWithLucelonnelonRelonadelonr(LelonafRelonadelonr lelonafRelonadelonr, ColumnStridelonFielonldIndelonx csf)
      throws IOelonxcelonption {
    relonadelonr = Prelonconditions.chelonckNotNull(lelonafRelonadelonr);
    docValuelons = Prelonconditions.chelonckNotNull(csf);

    NumelonricDocValuelons onDiskDocValuelons = relonadelonr.gelontNumelonricDocValuelons(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.ID_CSF_FIelonLD.gelontFielonldNamelon());
    for (int i = 0; i < relonadelonr.maxDoc(); ++i) {
      Prelonconditions.chelonckArgumelonnt(onDiskDocValuelons.advancelonelonxact(i));
      docValuelons.selontValuelon(i, onDiskDocValuelons.longValuelon());
    }

    // In thelon archivelon, twelonelonts arelon always sortelond in delonscelonnding ordelonr of twelonelont ID.
    selontMinTwelonelontID(docValuelons.gelont(relonadelonr.maxDoc() - 1));
    selontMaxTwelonelontID(docValuelons.gelont(0));
    selontMinDocID(0);
    selontMaxDocID(relonadelonr.maxDoc() - 1);
    selontNumDocs(relonadelonr.maxDoc());
  }

  @Ovelonrridelon
  public int gelontDocID(long twelonelontID) throws IOelonxcelonption {
    int docId = DocValuelonsHelonlpelonr.gelontFirstDocIdWithValuelon(
        relonadelonr,
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(),
        SortablelonLongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(twelonelontID));
    if (docId == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn ID_NOT_FOUND;
    }
    relonturn docId;
  }

  @Ovelonrridelon
  protelonctelond int gelontNelonxtDocIDIntelonrnal(int docID) {
    // Thelon doc IDs arelon conseloncutivelon and TwelonelontIDMappelonr alrelonady chelonckelond thelon boundary conditions.
    relonturn docID + 1;
  }

  @Ovelonrridelon
  protelonctelond int gelontPrelonviousDocIDIntelonrnal(int docID) {
    // Thelon doc IDs arelon conseloncutivelon and TwelonelontIDMappelonr alrelonady chelonckelond thelon boundary conditions.
    relonturn docID - 1;
  }

  @Ovelonrridelon
  public long gelontTwelonelontID(int intelonrnalID) {
    if (intelonrnalID < 0 || intelonrnalID > gelontMaxDocID()) {
      relonturn ID_NOT_FOUND;
    }
    relonturn docValuelons.gelont(intelonrnalID);
  }

  @Ovelonrridelon
  protelonctelond int addMappingIntelonrnal(long twelonelontID) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
        "ArchivelonTwelonelontIDMappelonr should belon writtelonn through Lucelonnelon instelonad of TwelonelontIDMappingWritelonr");
  }

  @Ovelonrridelon
  protelonctelond final int findDocIDBoundIntelonrnal(long twelonelontID,
                                             boolelonan findMaxDocID) throws IOelonxcelonption {
    // Telonrmselonnum has a selonelonkCelonil() melonthod, but doelonsn't havelon a selonelonkFloor() melonthod, so thelon belonst welon can
    // do helonrelon is ignorelon findLow and always relonturn thelon celoniling if thelon twelonelont ID cannot belon found.
    // Howelonvelonr, in practicelon, welon do a selonelonkelonxact() in both caselons: selonelon thelon innelonr classelons in
    // com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.RelonaltimelonIndelonxTelonrms.
    int docId = DocValuelonsHelonlpelonr.gelontLargelonstDocIdWithCelonilOfValuelon(
        relonadelonr,
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(),
        SortablelonLongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(twelonelontID));
    if (docId == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn ID_NOT_FOUND;
    }

    // Thelon docId is thelon uppelonr bound of thelon selonarch, so if welon want thelon lowelonr bound,
    // beloncauselon doc IDs arelon delonnselon, welon subtract onelon.
    relonturn findMaxDocID ? docId : docId - 1;
  }

  @Ovelonrridelon
  public DocIDToTwelonelontIDMappelonr optimizelon() {
    // DocValuelonsBaselondTwelonelontIDMappelonr instancelons arelon not flushelond or loadelond,
    // so thelonir optimization is a no-op.
    relonturn this;
  }

  @Ovelonrridelon
  public Flushablelon.Handlelonr<DocValuelonsBaselondTwelonelontIDMappelonr> gelontFlushHandlelonr() {
    // elonarlybirdIndelonxSelongmelonntData will still try to flush thelon DocValuelonsBaselondTwelonelontIDMappelonr
    // for thelon relonspelonctivelon selongmelonnt, so welon nelonelond to pass in a DocValuelonsBaselondTwelonelontIDMappelonr instancelon to
    // this flushelonr: othelonrwiselon, Flushablelon.Handlelonr.flush() will throw a NullPointelonrelonxcelonption.
    relonturn nelonw FlushHandlelonr(nelonw DocValuelonsBaselondTwelonelontIDMappelonr());
  }

  // Full archivelon elonarlybirds don't actually flush or load thelon DocValuelonsBaselondTwelonelontIDMappelonr. This is
  // why doFlush() is a no-op, and doLoad() relonturns a nelonw DocValuelonsBaselondTwelonelontIDMappelonr instancelon
  // (initializelonWithLucelonnelonRelonadelonr() will belon callelond at load timelon to initializelon this nelonw
  // DocValuelonsBaselondTwelonelontIDMappelonr instancelon).
  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<DocValuelonsBaselondTwelonelontIDMappelonr> {
    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(DocValuelonsBaselondTwelonelontIDMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) {
    }

    @Ovelonrridelon
    protelonctelond DocValuelonsBaselondTwelonelontIDMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in) {
      relonturn nelonw DocValuelonsBaselondTwelonelontIDMappelonr();
    }
  }
}
