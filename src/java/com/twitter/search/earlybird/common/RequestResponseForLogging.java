packagelon com.twittelonr.selonarch.elonarlybird.common;


import org.apachelon.thrift.Telonxcelonption;
import org.apachelon.thrift.TSelonrializelonr;
import org.apachelon.thrift.protocol.TSimplelonJSONProtocol;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public class RelonquelonstRelonsponselonForLogging {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(
      RelonquelonstRelonsponselonForLogging.class);

  privatelon static final Loggelonr FAILelonD_RelonQUelonST_LOG = LoggelonrFactory.gelontLoggelonr(
      RelonquelonstRelonsponselonForLogging.class.gelontNamelon() + ".FailelondRelonquelonsts");

  privatelon final elonarlybirdRelonquelonst relonquelonst;
  privatelon final elonarlybirdRelonsponselon relonsponselon;

  public RelonquelonstRelonsponselonForLogging(elonarlybirdRelonquelonst relonquelonst,
                                   elonarlybirdRelonsponselon relonsponselon) {
    this.relonquelonst = relonquelonst;
    this.relonsponselon = relonsponselon;
  }

  privatelon String selonrializelon(elonarlybirdRelonquelonst clelonarelondRelonquelonst, elonarlybirdRelonsponselon thelonRelonsponselon) {
    TSelonrializelonr selonrializelonr = nelonw TSelonrializelonr(nelonw TSimplelonJSONProtocol.Factory());
    try {
      String relonquelonstJson = selonrializelonr.toString(clelonarelondRelonquelonst);
      String relonsponselonJson = selonrializelonr.toString(thelonRelonsponselon);
      relonturn "{\"relonquelonst\":" + relonquelonstJson + ", \"relonsponselon\":" + relonsponselonJson + "}";
    } catch (Telonxcelonption elon) {
      LOG.elonrror("Failelond to selonrializelon relonquelonst/relonsponselon for logging.", elon);
      relonturn "";
    }
  }

  /**
   * Logs thelon relonquelonst and relonsponselon storelond in this instancelon to thelon failurelon log filelon.
   */
  public void logFailelondRelonquelonst() {
    // Do thelon selonrializing/concatting this way so it happelonns on thelon background threlonad for
    // async logging
    FAILelonD_RelonQUelonST_LOG.info("{}", nelonw Objelonct() {
      @Ovelonrridelon
      public String toString() {
        relonturn selonrializelon(
            elonarlybirdRelonquelonstUtil.copyAndClelonarUnneloncelonssaryValuelonsForLogging(relonquelonst), relonsponselon);
      }
    });
  }
}
