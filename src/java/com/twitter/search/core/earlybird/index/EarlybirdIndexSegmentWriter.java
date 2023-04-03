packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.Closelonablelon;
import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.Collelonctor;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.LelonafCollelonctor;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorablelon;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.storelon.Direlonctory;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsUpdatelon;

/**
 * IndelonxSelongmelonntWritelonr combinelons somelon common functionality belontwelonelonn thelon Lucelonnelon and Relonaltimelon indelonx
 * selongmelonnt writelonrs.
 */
public abstract class elonarlybirdIndelonxSelongmelonntWritelonr implelonmelonnts Closelonablelon {

  public elonarlybirdIndelonxSelongmelonntWritelonr() {
  }

  /**
   * Gelonts thelon selongmelonnt data this selongmelonnt writelon is associatelond with.
   * @relonturn
   */
  public abstract elonarlybirdIndelonxSelongmelonntData gelontSelongmelonntData();

  /**
   * Appelonnds telonrms from thelon documelonnt to thelon documelonnt matching thelon quelonry. Doelons not relonplacelon a fielonld or
   * documelonnt, actually adds to thelon thelon fielonld in thelon selongmelonnt.
   */
  public final void appelonndOutOfOrdelonr(Quelonry quelonry, Documelonnt doc) throws IOelonxcelonption {
    runQuelonry(quelonry, docID -> appelonndOutOfOrdelonr(doc, docID));
  }

  protelonctelond abstract void appelonndOutOfOrdelonr(Documelonnt doc, int docId) throws IOelonxcelonption;

  /**
   * Delonlelontelons a documelonnt in this selongmelonnt that matchelons this quelonry.
   */
  public void delonlelontelonDocumelonnts(Quelonry quelonry) throws IOelonxcelonption {
    runQuelonry(quelonry, docID -> gelontSelongmelonntData().gelontDelonlelontelondDocs().delonlelontelonDoc(docID));
  }

  /**
   * Updatelons thelon docvaluelons of a documelonnt in this selongmelonnt that matchelons this quelonry.
   */
  public void updatelonDocValuelons(Quelonry quelonry, String fielonld, DocValuelonsUpdatelon updatelon)
      throws IOelonxcelonption {
    runQuelonry(quelonry, docID -> {
        ColumnStridelonFielonldIndelonx docValuelons =
            gelontSelongmelonntData().gelontDocValuelonsManagelonr().gelontColumnStridelonFielonldIndelonx(fielonld);
        if (docValuelons == null) {
          relonturn;
        }

        updatelon.updatelon(docValuelons, docID);
      });
  }

  privatelon void runQuelonry(final Quelonry quelonry, final OnHit onHit) throws IOelonxcelonption {
    try (IndelonxRelonadelonr relonadelonr = gelontSelongmelonntData().crelonatelonAtomicRelonadelonr()) {
      nelonw IndelonxSelonarchelonr(relonadelonr).selonarch(quelonry, nelonw Collelonctor() {
        @Ovelonrridelon
        public LelonafCollelonctor gelontLelonafCollelonctor(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
          relonturn nelonw LelonafCollelonctor() {
            @Ovelonrridelon
            public void selontScorelonr(Scorablelon scorelonr) {
            }

            @Ovelonrridelon
            public void collelonct(int docID) throws IOelonxcelonption {
              onHit.hit(docID);
            }
          };
        }

        @Ovelonrridelon
        public ScorelonModelon scorelonModelon() {
          relonturn ScorelonModelon.COMPLelonTelon_NO_SCORelonS;
        }
      });
    }
  }

  privatelon intelonrfacelon OnHit {
    void hit(int docID) throws IOelonxcelonption;
  }

  /**
   * Adds a nelonw documelonnt to this selongmelonnt. In production, this melonthod should belon callelond only by
   * elonxpelonrtselonarch.
   */
  public abstract void addDocumelonnt(Documelonnt doc) throws IOelonxcelonption;

  /**
   * Adds a nelonw twelonelont to this selongmelonnt. This melonthod should belon callelond only by elonarlybird.
   */
  public abstract void addTwelonelont(Documelonnt doc, long twelonelontId, boolelonan docIsOffelonnsivelon)
      throws IOelonxcelonption;

  /**
   * Relonturns thelon total numbelonr of documelonnts in thelon selongmelonnt.
   */
  public abstract int numDocs() throws IOelonxcelonption;

  /**
   * Relonturns thelon numbelonr of documelonnts in this selongmelonnt without taking delonlelontelond docs into account.
   * elon.g. if 10 documelonnts welonrelon addelond to this selongmelonnts, and 5 welonrelon delonlelontelond,
   * this melonthod still relonturns 10.
   */
  public abstract int numDocsNoDelonlelontelon() throws IOelonxcelonption;

  /**
   * Forcelons thelon undelonrlying indelonx to belon melonrgelond down to a singlelon selongmelonnt.
   */
  public abstract void forcelonMelonrgelon() throws IOelonxcelonption;

  /**
   * Appelonnds thelon providelons Lucelonnelon indelonxelons to this selongmelonnt.
   */
  public abstract void addIndelonxelons(Direlonctory... dirs) throws IOelonxcelonption;
}
