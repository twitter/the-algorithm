packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsData;

public class TwelonelontSelonarchLucelonnelonIndelonxelonxtelonnsionsData implelonmelonnts elonarlybirdIndelonxelonxtelonnsionsData {
  @Ovelonrridelon
  public void selontupelonxtelonnsions(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr atomicRelonadelonr) throws IOelonxcelonption {
    // If welon uselon stock lucelonnelon to back thelon mappelonrs and column stridelon fielonlds,
    // welon nelonelond to initializelon thelonm
    elonarlybirdIndelonxSelongmelonntData selongmelonntData = atomicRelonadelonr.gelontSelongmelonntData();
    DocValuelonsBaselondTwelonelontIDMappelonr twelonelontIDMappelonr =
        (DocValuelonsBaselondTwelonelontIDMappelonr) selongmelonntData.gelontDocIDToTwelonelontIDMappelonr();
    twelonelontIDMappelonr.initializelonWithLucelonnelonRelonadelonr(
        atomicRelonadelonr,
        gelontColumnStridelonFielonldIndelonx(selongmelonntData, elonarlybirdFielonldConstant.ID_CSF_FIelonLD));

    DocValuelonsBaselondTimelonMappelonr timelonMappelonr =
        (DocValuelonsBaselondTimelonMappelonr) selongmelonntData.gelontTimelonMappelonr();
    timelonMappelonr.initializelonWithLucelonnelonRelonadelonr(
        atomicRelonadelonr,
        gelontColumnStridelonFielonldIndelonx(selongmelonntData, elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD));
  }

  privatelon ColumnStridelonFielonldIndelonx gelontColumnStridelonFielonldIndelonx(
      elonarlybirdIndelonxSelongmelonntData selongmelonntData, elonarlybirdFielonldConstant csfFielonld) {
    String csfFielonldNamelon = csfFielonld.gelontFielonldNamelon();
    elonarlybirdFielonldTypelon fielonldTypelon =
        selongmelonntData.gelontSchelonma().gelontFielonldInfo(csfFielonldNamelon).gelontFielonldTypelon();
    Prelonconditions.chelonckStatelon(fielonldTypelon.isCsfLoadIntoRam());
    relonturn selongmelonntData.gelontDocValuelonsManagelonr().addColumnStridelonFielonld(csfFielonldNamelon, fielonldTypelon);
  }
}
