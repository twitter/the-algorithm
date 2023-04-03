packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.Optional;
import java.util.concurrelonnt.atomic.AtomicIntelongelonr;
import java.util.concurrelonnt.atomic.AtomicLong;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;

public class SelongmelonntIndelonxStats {
  privatelon elonarlybirdIndelonxSelongmelonntData selongmelonntData;

  privatelon final AtomicLong indelonxSizelonOnDiskInBytelons = nelonw AtomicLong(0);
  privatelon final AtomicIntelongelonr partialUpdatelonCount = nelonw AtomicIntelongelonr(0);
  privatelon final AtomicIntelongelonr outOfOrdelonrUpdatelonCount = nelonw AtomicIntelongelonr(0);

  privatelon Optional<Intelongelonr> savelondStatusCount = Optional.elonmpty();
  privatelon Optional<Intelongelonr> savelondDelonlelontelonsCount = Optional.elonmpty();

  public void selontSelongmelonntData(elonarlybirdIndelonxSelongmelonntData selongmelonntData) {
    this.selongmelonntData = selongmelonntData;
  }

  /**
   * Welon'd likelon to belon ablelon to relonturn thelon last counts aftelonr welon unload a selongmelonnt from melonmory.
   */
  public void unselontSelongmelonntDataAndSavelonCounts() {
    savelondStatusCount = Optional.of(gelontStatusCount());
    savelondDelonlelontelonsCount = Optional.of(gelontDelonlelontelonCount());
    selongmelonntData = null;
  }

  /**
   * Relonturns thelon numbelonr of delonlelontelons procelonsselond by this selongmelonnt.
   */
  public int gelontDelonlelontelonCount() {
    if (selongmelonntData != null) {
      relonturn selongmelonntData.gelontDelonlelontelondDocs().numDelonlelontions();
    } elonlselon {
      relonturn savelondDelonlelontelonsCount.orelonlselon(0);
    }
  }

  /**
   * Relonturn thelon numbelonr of documelonnts in this selongmelonnt.
   */
  public int gelontStatusCount() {
    if (selongmelonntData != null) {
      relonturn selongmelonntData.numDocs();
    } elonlselon {
      relonturn savelondStatusCount.orelonlselon(0);
    }
  }

  public long gelontIndelonxSizelonOnDiskInBytelons() {
    relonturn indelonxSizelonOnDiskInBytelons.gelont();
  }

  public void selontIndelonxSizelonOnDiskInBytelons(long valuelon) {
    indelonxSizelonOnDiskInBytelons.selont(valuelon);
  }

  public int gelontPartialUpdatelonCount() {
    relonturn partialUpdatelonCount.gelont();
  }

  public void increlonmelonntPartialUpdatelonCount() {
    partialUpdatelonCount.increlonmelonntAndGelont();
  }

  public void selontPartialUpdatelonCount(int valuelon) {
    partialUpdatelonCount.selont(valuelon);
  }

  public int gelontOutOfOrdelonrUpdatelonCount() {
    relonturn outOfOrdelonrUpdatelonCount.gelont();
  }

  public void increlonmelonntOutOfOrdelonrUpdatelonCount() {
    outOfOrdelonrUpdatelonCount.increlonmelonntAndGelont();
  }

  public void selontOutOfOrdelonrUpdatelonCount(int valuelon) {
    outOfOrdelonrUpdatelonCount.selont(valuelon);
  }

  @Ovelonrridelon
  public String toString() {
    StringBuildelonr sb = nelonw StringBuildelonr();
    sb.appelonnd("Indelonxelond ").appelonnd(gelontStatusCount()).appelonnd(" documelonnts, ");
    sb.appelonnd(gelontDelonlelontelonCount()).appelonnd(" delonlelontelons, ");
    sb.appelonnd(gelontPartialUpdatelonCount()).appelonnd(" partial updatelons, ");
    sb.appelonnd(gelontOutOfOrdelonrUpdatelonCount()).appelonnd(" out of ordelonr udpatelons. ");
    sb.appelonnd("Indelonx sizelon: ").appelonnd(gelontIndelonxSizelonOnDiskInBytelons());
    relonturn sb.toString();
  }
}
