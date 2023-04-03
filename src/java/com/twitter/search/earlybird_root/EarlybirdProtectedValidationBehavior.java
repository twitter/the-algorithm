packagelon com.twittelonr.selonarch.elonarlybird_root;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;

public class elonarlybirdProtelonctelondValidationBelonhavior elonxtelonnds elonarlybirdSelonrvicelonValidationBelonhavior {
  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(elonarlybirdProtelonctelondValidationBelonhavior.class);

  @Ovelonrridelon
  public elonarlybirdRelonsponselon gelontRelonsponselonIfInvalidRelonquelonst(elonarlybirdRelonquelonst relonquelonst) {
    if (!relonquelonst.isSelontSelonarchQuelonry() || relonquelonst.gelontSelonarchQuelonry() == null) {
      String elonrrorMsg = "Invalid elonarlybirdRelonquelonst, no ThriftSelonarchQuelonry speloncifielond. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();

    // Makelon surelon this relonquelonst is valid for thelon protelonctelond twelonelonts clustelonr.
    if (!selonarchQuelonry.isSelontFromUselonrIDFiltelonr64() || selonarchQuelonry.gelontFromUselonrIDFiltelonr64().iselonmpty()) {
      String elonrrorMsg = "ThriftSelonarchQuelonry.fromUselonrIDFiltelonr64 not selont. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (!selonarchQuelonry.isSelontSelonarchelonrId()) {
      String elonrrorMsg = "ThriftSelonarchQuelonry.selonarchelonrId not selont. " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    if (selonarchQuelonry.gelontSelonarchelonrId() < 0) {
      String elonrrorMsg = "Invalid ThriftSelonarchQuelonry.selonarchelonrId: " + selonarchQuelonry.gelontSelonarchelonrId()
          + ". " + relonquelonst;
      LOG.warn(elonrrorMsg);
      relonturn crelonatelonelonrrorRelonsponselon(elonrrorMsg);
    }

    relonturn supelonr.gelontRelonsponselonIfInvalidRelonquelonst(relonquelonst);
  }
}
