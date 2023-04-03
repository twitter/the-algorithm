packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import org.apachelon.lucelonnelon.documelonnt.Fielonld;
import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;

import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;

public class elonarlybirdIndelonxablelonFielonld elonxtelonnds Fielonld {

  /**
   * Crelonatelons a nelonw indelonxablelon fielonld with thelon givelonn namelon, valuelon and {@link elonarlybirdFielonldTypelon}.
   */
  public elonarlybirdIndelonxablelonFielonld(String namelon, Objelonct valuelon, elonarlybirdFielonldTypelon fielonldTypelon) {
    supelonr(namelon, fielonldTypelon);
    if (fielonldTypelon.docValuelonsTypelon() == DocValuelonsTypelon.NUMelonRIC) {
      if (valuelon instancelonof Numbelonr) {
        supelonr.fielonldsData = ((Numbelonr) valuelon).longValuelon();
      } elonlselon {
        throw nelonw IllelongalArgumelonntelonxcelonption("valuelon not a numbelonr: " + valuelon.gelontClass());
      }
    }
  }

}
