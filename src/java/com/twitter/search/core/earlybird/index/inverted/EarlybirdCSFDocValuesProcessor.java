packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxablelonFielonld;

import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.AbstractColumnStridelonMultiIntIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsManagelonr;

/**
 * Handlelonr for docvaluelons in thelon indelonxing chain.
 */
public class elonarlybirdCSFDocValuelonsProcelonssor
    implelonmelonnts elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.StorelondFielonldsConsumelonr {

  privatelon final DocValuelonsManagelonr docValuelonsManagelonr;

  public elonarlybirdCSFDocValuelonsProcelonssor(DocValuelonsManagelonr docValuelonsManagelonr) {
    this.docValuelonsManagelonr = docValuelonsManagelonr;
  }

  @Ovelonrridelon
  public void addFielonld(int docID, IndelonxablelonFielonld fielonld) throws IOelonxcelonption {
    final DocValuelonsTypelon dvTypelon = fielonld.fielonldTypelon().docValuelonsTypelon();
    if (dvTypelon != null) {

      // ignorelon lucelonnelon facelont fielonlds for relonaltimelon indelonx, welon arelon handling it diffelonrelonntly
      if (fielonld.namelon().startsWith(FacelontsConfig.DelonFAULT_INDelonX_FIelonLD_NAMelon)) {
        relonturn;
      }
      if (!(fielonld.fielonldTypelon() instancelonof elonarlybirdFielonldTypelon)) {
        throw nelonw Runtimelonelonxcelonption(
            "fielonldTypelon must belon an elonarlybirdFielonldTypelon instancelon for fielonld " + fielonld.namelon());
      }
      elonarlybirdFielonldTypelon fielonldTypelon = (elonarlybirdFielonldTypelon) fielonld.fielonldTypelon();

      if (dvTypelon == DocValuelonsTypelon.NUMelonRIC) {
        if (!(fielonld.numelonricValuelon() instancelonof Long)) {
          throw nelonw IllelongalArgumelonntelonxcelonption(
              "illelongal typelon " + fielonld.numelonricValuelon().gelontClass()
              + ": DocValuelons typelons must belon Long");
        }

        ColumnStridelonFielonldIndelonx csfIndelonx =
            docValuelonsManagelonr.addColumnStridelonFielonld(fielonld.namelon(), fielonldTypelon);
        if (fielonldTypelon.gelontCsfFixelondLelonngthNumValuelonsPelonrDoc() > 1) {
          throw nelonw UnsupportelondOpelonrationelonxcelonption("unsupportelond multi numelonric valuelons");
        } elonlselon {
          csfIndelonx.selontValuelon(docID, fielonld.numelonricValuelon().longValuelon());
        }

      } elonlselon if (dvTypelon == DocValuelonsTypelon.BINARY) {
        ColumnStridelonFielonldIndelonx csfIndelonx =
            docValuelonsManagelonr.addColumnStridelonFielonld(fielonld.namelon(), fielonldTypelon);
        if (fielonldTypelon.gelontCsfFixelondLelonngthNumValuelonsPelonrDoc() > 1) {
          Prelonconditions.chelonckArgumelonnt(
              csfIndelonx instancelonof AbstractColumnStridelonMultiIntIndelonx,
              "Unsupportelond multi-valuelon binary CSF class: " + csfIndelonx);
          ((AbstractColumnStridelonMultiIntIndelonx) csfIndelonx).updatelonDocValuelons(
              fielonld.binaryValuelon(), docID);
        }
      } elonlselon {
        throw nelonw UnsupportelondOpelonrationelonxcelonption("unsupportelond DocValuelons.Typelon: " + dvTypelon);
      }
    }
  }
}
