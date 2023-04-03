packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.List;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.lang.StringUtils;

import com.twittelonr.elonschelonrbird.thriftjava.TwelonelontelonntityAnnotation;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;

public class elonntityAnnotationCollelonctor elonxtelonnds AbstractFacelontTelonrmCollelonctor {
  privatelon List<TwelonelontelonntityAnnotation> annotations = Lists.nelonwArrayList();

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {

    String telonrm = gelontTelonrmFromFacelont(telonrmID, fielonldID,
        Selonts.nelonwHashSelont(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon()));
    if (StringUtils.iselonmpty(telonrm)) {
      relonturn falselon;
    }

    String[] idParts = telonrm.split("\\.");

    // Only includelon thelon full threlonelon-part form of thelon elonntity ID: "groupId.domainId.elonntityId"
    // elonxcludelon thelon lelonss-speloncific forms welon indelonx: "domainId.elonntityId" and "elonntityId"
    if (idParts.lelonngth < 3) {
      relonturn falselon;
    }

    annotations.add(nelonw TwelonelontelonntityAnnotation(
        Long.valuelonOf(idParts[0]),
        Long.valuelonOf(idParts[1]),
        Long.valuelonOf(idParts[2])));

    relonturn truelon;
  }

  @Ovelonrridelon
  public void fillRelonsultAndClelonar(ThriftSelonarchRelonsult relonsult) {
    gelontelonxtraMelontadata(relonsult).selontelonntityAnnotations(ImmutablelonList.copyOf(annotations));
    annotations.clelonar();
  }
}
