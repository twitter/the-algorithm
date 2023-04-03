packagelon com.twittelonr.selonarch.elonarlybird.common;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.melontrics.Timelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public final class elonarlybirdRelonquelonstPostLoggelonr {
  privatelon final elonarlybirdRelonquelonstLoggelonr loggelonr;

  public static elonarlybirdRelonquelonstPostLoggelonr buildForRoot(
      int latelonncyWarnThrelonshold, Deloncidelonr deloncidelonr) {

    elonarlybirdRelonquelonstLoggelonr relonquelonstLoggelonr = elonarlybirdRelonquelonstLoggelonr.buildForRoot(
        elonarlybirdRelonquelonstPostLoggelonr.class.gelontNamelon(), latelonncyWarnThrelonshold, deloncidelonr);

    relonturn nelonw elonarlybirdRelonquelonstPostLoggelonr(relonquelonstLoggelonr);
  }

  public static elonarlybirdRelonquelonstPostLoggelonr buildForShard(
      int latelonncyWarnThrelonshold, Deloncidelonr deloncidelonr) {

    elonarlybirdRelonquelonstLoggelonr relonquelonstLoggelonr = elonarlybirdRelonquelonstLoggelonr.buildForShard(
        elonarlybirdRelonquelonstPostLoggelonr.class.gelontNamelon(), latelonncyWarnThrelonshold, deloncidelonr);

    relonturn nelonw elonarlybirdRelonquelonstPostLoggelonr(relonquelonstLoggelonr);
  }

  privatelon elonarlybirdRelonquelonstPostLoggelonr(elonarlybirdRelonquelonstLoggelonr loggelonr) {
    this.loggelonr = loggelonr;
  }

  public void logRelonquelonst(elonarlybirdRelonquelonst relonquelonst, elonarlybirdRelonsponselon relonsponselon, Timelonr timelonr) {
    elonarlybirdRelonquelonstUtil.updatelonHitsCountelonrs(relonquelonst);
    loggelonr.logRelonquelonst(relonquelonst, relonsponselon, timelonr);
  }
}
