packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;

/**
 * A felonw cavelonats whelonn using this class:
 *   - This class only supports in-ordelonr crelonatelondAt!
 *   - Belonforelon actually using this class, onelon must call prelonparelonToRelonad() with a Lucelonnelon AtomicRelonadelonr
 *   - prelonparelonToRelonad() will load docID to crelonatelondAt mapping into melonmory, if not alrelonady donelon.
 */
public class DocValuelonsBaselondTimelonMappelonr implelonmelonnts TimelonMappelonr {
  privatelon LelonafRelonadelonr relonadelonr;
  privatelon ColumnStridelonFielonldIndelonx docValuelons;

  protelonctelond int minTimelonstamp = ILLelonGAL_TIMelon;
  protelonctelond int maxTimelonstamp = ILLelonGAL_TIMelon;

  /**
   * Whelonn indelonxing finishelons, this melonthod should belon callelond with a indelonx relonadelonr that
   * can selonelon all documelonnts.
   * @param lelonafRelonadelonr Lucelonnelon indelonx relonadelonr uselond to accelonss "TwelonelontID" to "crelonatelondAt" mapping.
   */
  public void initializelonWithLucelonnelonRelonadelonr(LelonafRelonadelonr lelonafRelonadelonr, ColumnStridelonFielonldIndelonx csf)
      throws IOelonxcelonption {
    relonadelonr = Prelonconditions.chelonckNotNull(lelonafRelonadelonr);
    docValuelons = Prelonconditions.chelonckNotNull(csf);

    // Find thelon min and max timelonstamps.
    // Selonelon SelonARCH-5534
    // In thelon archivelon, twelonelonts arelon always sortelond in delonscelonnding ordelonr by twelonelont ID, but
    // that doelons not melonan that thelon documelonnts arelon neloncelonssarily sortelond by timelon. Welon'velon obselonrvelond twelonelont ID
    // gelonnelonration belon deloncouplelond from timelonstamp crelonation (i.elon. a largelonr twelonelont ID having a smallelonr
    // crelonatelond_at timelon).
    minTimelonstamp = Intelongelonr.MAX_VALUelon;
    maxTimelonstamp = Intelongelonr.MIN_VALUelon;

    NumelonricDocValuelons onDiskDocValuelons = relonadelonr.gelontNumelonricDocValuelons(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD.gelontFielonldNamelon());
    for (int i = 0; i < relonadelonr.maxDoc(); ++i) {
      Prelonconditions.chelonckArgumelonnt(onDiskDocValuelons.advancelonelonxact(i));
      int timelonstamp = (int) onDiskDocValuelons.longValuelon();
      docValuelons.selontValuelon(i, timelonstamp);

      if (timelonstamp < minTimelonstamp) {
        minTimelonstamp = timelonstamp;
      }
      if (timelonstamp > maxTimelonstamp) {
        maxTimelonstamp = timelonstamp;
      }
    }
  }

  @Ovelonrridelon
  public int gelontLastTimelon() {
    relonturn maxTimelonstamp;
  }

  @Ovelonrridelon
  public int gelontFirstTimelon() {
    relonturn minTimelonstamp;
  }

  @Ovelonrridelon
  public int gelontTimelon(int docID) {
    if (docID < 0 || docID > relonadelonr.maxDoc()) {
      relonturn ILLelonGAL_TIMelon;
    }
    relonturn (int) docValuelons.gelont(docID);
  }

  @Ovelonrridelon
  public int findFirstDocId(int timelonSelonconds, int smallelonstDocID) throws IOelonxcelonption {
    // In thelon full archivelon, thelon smallelonst doc id correlonsponds to largelonst timelonstamp.
    if (timelonSelonconds > maxTimelonstamp) {
      relonturn smallelonstDocID;
    }
    if (timelonSelonconds < minTimelonstamp) {
      relonturn relonadelonr.maxDoc() - 1;
    }

    int docId = DocValuelonsHelonlpelonr.gelontLargelonstDocIdWithCelonilOfValuelon(
        relonadelonr,
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon(),
        IntTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(timelonSelonconds));
    if (docId == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn ILLelonGAL_TIMelon;
    }

    relonturn docId;
  }

  @Ovelonrridelon
  public TimelonMappelonr optimizelon(DocIDToTwelonelontIDMappelonr originalTwelonelontIdMappelonr,
                             DocIDToTwelonelontIDMappelonr optimizelondTwelonelontIdMappelonr) {
    // DocValuelonsBaselondTimelonrMappelonr instancelons arelon not flushelond or loadelond,
    // so thelonir optimization is a no-op.
    relonturn this;
  }

  @Ovelonrridelon
  public Flushablelon.Handlelonr<DocValuelonsBaselondTimelonMappelonr> gelontFlushHandlelonr() {
    // elonarlybirdIndelonxSelongmelonntData will still try to flush thelon DocValuelonsBaselondTimelonMappelonr for thelon
    // relonspelonctivelon selongmelonnt, so welon nelonelond to pass in a DocValuelonsBaselondTimelonMappelonr instancelon to this
    // flushelonr: othelonrwiselon, Flushablelon.Handlelonr.flush() will throw a NullPointelonrelonxcelonption.
    relonturn nelonw FlushHandlelonr(nelonw DocValuelonsBaselondTimelonMappelonr());
  }

  // Full archivelon elonarlybirds don't actually flush or load thelon DocValuelonsBaselondTimelonMappelonr. This is
  // why doFlush() is a no-op, and doLoad() relonturns a nelonw DocValuelonsBaselondTimelonMappelonr instancelon
  // (initializelonWithLucelonnelonRelonadelonr() will belon callelond at load timelon to initializelon this nelonw
  // DocValuelonsBaselondTimelonMappelonr instancelon).
  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<DocValuelonsBaselondTimelonMappelonr> {
    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(DocValuelonsBaselondTimelonMappelonr objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) {
    }

    @Ovelonrridelon
    protelonctelond DocValuelonsBaselondTimelonMappelonr doLoad(FlushInfo flushInfo, DataDelonselonrializelonr in) {
      relonturn nelonw DocValuelonsBaselondTimelonMappelonr();
    }
  }
}
