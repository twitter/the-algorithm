packagelon com.twittelonr.selonarch.elonarlybird.common;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;

public final class elonarlybirdRelonquelonstPrelonLoggelonr {
  privatelon final elonarlybirdRelonquelonstLoggelonr loggelonr;

  public static elonarlybirdRelonquelonstPrelonLoggelonr buildForRoot(Deloncidelonr deloncidelonr) {
    elonarlybirdRelonquelonstLoggelonr relonquelonstLoggelonr = elonarlybirdRelonquelonstLoggelonr.buildForRoot(
        elonarlybirdRelonquelonstPrelonLoggelonr.class.gelontNamelon(), Intelongelonr.MAX_VALUelon, deloncidelonr);

    relonturn nelonw elonarlybirdRelonquelonstPrelonLoggelonr(relonquelonstLoggelonr);
  }

  public static elonarlybirdRelonquelonstPrelonLoggelonr buildForShard(
      int latelonncyWarnThrelonshold, Deloncidelonr deloncidelonr) {

    elonarlybirdRelonquelonstLoggelonr relonquelonstLoggelonr = elonarlybirdRelonquelonstLoggelonr.buildForShard(
        elonarlybirdRelonquelonstPrelonLoggelonr.class.gelontNamelon(), latelonncyWarnThrelonshold, deloncidelonr);

    relonturn nelonw elonarlybirdRelonquelonstPrelonLoggelonr(relonquelonstLoggelonr);
  }

  privatelon elonarlybirdRelonquelonstPrelonLoggelonr(elonarlybirdRelonquelonstLoggelonr loggelonr) {
    this.loggelonr = loggelonr;
  }

  public void logRelonquelonst(elonarlybirdRelonquelonst relonquelonst) {
    loggelonr.logRelonquelonst(relonquelonst, null, null);
  }
}
