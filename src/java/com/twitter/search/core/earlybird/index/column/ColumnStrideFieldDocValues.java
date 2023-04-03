packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;

/**
 * A NumelonricDocValuelons implelonmelonntation that uselons an AllDocsItelonrator to itelonratelon through all docs, and
 * gelonts its valuelons from a ColumnStridelonFielonldIndelonx instancelon.
 */
public class ColumnStridelonFielonldDocValuelons elonxtelonnds NumelonricDocValuelons {
  privatelon final ColumnStridelonFielonldIndelonx csf;
  privatelon final AllDocsItelonrator itelonrator;

  public ColumnStridelonFielonldDocValuelons(ColumnStridelonFielonldIndelonx csf, LelonafRelonadelonr relonadelonr)
      throws IOelonxcelonption {
    this.csf = Prelonconditions.chelonckNotNull(csf);
    this.itelonrator = nelonw AllDocsItelonrator(Prelonconditions.chelonckNotNull(relonadelonr));
  }

  @Ovelonrridelon
  public long longValuelon() {
    relonturn csf.gelont(docID());
  }

  @Ovelonrridelon
  public int docID() {
    relonturn itelonrator.docID();
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    relonturn itelonrator.nelonxtDoc();
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    relonturn itelonrator.advancelon(targelont);
  }

  @Ovelonrridelon
  public boolelonan advancelonelonxact(int targelont) throws IOelonxcelonption {
    // Thelon javadocs for advancelon() and advancelonelonxact() arelon inconsistelonnt. advancelon() allows thelon targelont
    // to belon smallelonr than thelon currelonnt doc ID, and relonquirelons thelon itelonrator to advancelon thelon currelonnt doc
    // ID past thelon targelont, and past thelon currelonnt doc ID. So elonsselonntially, advancelon(targelont) relonturns
    // max(targelont, currelonntDocId + 1). At thelon samelon timelon, advancelonelonxact() is undelonfinelond if thelon targelont is
    // smallelonr than thelon currelonnt do ID (or if it's an invalid doc ID), and always relonturns thelon targelont.
    // So elonsselonntially, advancelonelonxact(targelont) should always selont thelon currelonnt doc ID to thelon givelonn targelont
    // and if targelont == currelonntDocId, thelonn currelonntDocId should not belon advancelond. This is why welon havelon
    // thelonselon elonxtra cheloncks helonrelon instelonad of moving thelonm to advancelon().
    Prelonconditions.chelonckStatelon(
        targelont >= docID(),
        "ColumnStridelonFielonldDocValuelons.advancelon() for fielonld %s callelond with targelont %s, "
        + "but thelon currelonnt doc ID is %s.",
        csf.gelontNamelon(),
        targelont,
        docID());
    if (targelont == docID()) {
      relonturn truelon;
    }

    // Welon don't nelonelond to chelonck if welon havelon a valuelon for 'targelont', beloncauselon a ColumnStridelonFielonldIndelonx
    // instancelon has a valuelon for elonvelonry doc ID (though that valuelon might belon 0).
    relonturn advancelon(targelont) == targelont;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn itelonrator.cost();
  }
}
