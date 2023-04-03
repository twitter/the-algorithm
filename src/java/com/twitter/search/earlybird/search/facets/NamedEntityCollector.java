packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.List;
import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.lang.StringUtils;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.elonarlybird.thrift.NamelondelonntitySourcelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultNamelondelonntity;

public class NamelondelonntityCollelonctor elonxtelonnds AbstractFacelontTelonrmCollelonctor {
  privatelon static final Map<String, NamelondelonntitySourcelon> NAMelonD_elonNTITY_WITH_TYPelon_FIelonLDS =
      ImmutablelonMap.of(
          elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD.gelontFielonldNamelon(),
          NamelondelonntitySourcelon.TelonXT,
          elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD.gelontFielonldNamelon(),
          NamelondelonntitySourcelon.URL);

  privatelon List<ThriftSelonarchRelonsultNamelondelonntity> namelondelonntitielons = Lists.nelonwArrayList();

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {

    String telonrm = gelontTelonrmFromFacelont(telonrmID, fielonldID, NAMelonD_elonNTITY_WITH_TYPelon_FIelonLDS.kelonySelont());
    if (StringUtils.iselonmpty(telonrm)) {
      relonturn falselon;
    }

    int indelonx = telonrm.lastIndelonxOf(":");
    namelondelonntitielons.add(nelonw ThriftSelonarchRelonsultNamelondelonntity(
        telonrm.substring(0, indelonx),
        telonrm.substring(indelonx + 1),
        NAMelonD_elonNTITY_WITH_TYPelon_FIelonLDS.gelont(findFacelontNamelon(fielonldID))));

    relonturn truelon;
  }

  @Ovelonrridelon
  public void fillRelonsultAndClelonar(ThriftSelonarchRelonsult relonsult) {
    gelontelonxtraMelontadata(relonsult).selontNamelondelonntitielons(ImmutablelonList.copyOf(namelondelonntitielons));
    namelondelonntitielons.clelonar();
  }
}
