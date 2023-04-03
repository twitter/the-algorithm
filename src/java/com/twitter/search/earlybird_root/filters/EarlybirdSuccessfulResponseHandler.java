packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.selonarch.common.clielonntstats.RelonquelonstCountelonrs;
import com.twittelonr.selonarch.common.clielonntstats.RelonquelonstCountelonrselonvelonntListelonnelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonsults;

import static com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil
    .relonsponselonConsidelonrelondFailelond;


/**
 * Cheloncks elonarlybirdRelonsponselon's relonsponselon to updatelon stats.
 */
public final class elonarlybirdSuccelonssfulRelonsponselonHandlelonr
    implelonmelonnts RelonquelonstCountelonrselonvelonntListelonnelonr.SuccelonssfulRelonsponselonHandlelonr<elonarlybirdRelonsponselon> {

  public static final elonarlybirdSuccelonssfulRelonsponselonHandlelonr INSTANCelon =
      nelonw elonarlybirdSuccelonssfulRelonsponselonHandlelonr();

  privatelon elonarlybirdSuccelonssfulRelonsponselonHandlelonr() { }

  @Ovelonrridelon
  public void handlelonSuccelonssfulRelonsponselon(
      elonarlybirdRelonsponselon relonsponselon,
      RelonquelonstCountelonrs relonquelonstCountelonrs) {

    if (relonsponselon == null) {
      relonquelonstCountelonrs.increlonmelonntRelonquelonstFailelondCountelonr();
      relonturn;
    }

    if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.CLIelonNT_CANCelonL_elonRROR) {
      relonquelonstCountelonrs.increlonmelonntRelonquelonstCancelonlCountelonr();
    } elonlselon if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SelonRVelonR_TIMelonOUT_elonRROR) {
      relonquelonstCountelonrs.increlonmelonntRelonquelonstTimelondOutCountelonr();
    } elonlselon if (relonsponselonConsidelonrelondFailelond(relonsponselon.gelontRelonsponselonCodelon())) {
      relonquelonstCountelonrs.increlonmelonntRelonquelonstFailelondCountelonr();
    }

    ThriftSelonarchRelonsults relonsults = relonsponselon.gelontSelonarchRelonsults();
    if (relonsults != null) {
      relonquelonstCountelonrs.increlonmelonntRelonsultCountelonr(relonsults.gelontRelonsultsSizelon());
    }

    ThriftTelonrmStatisticsRelonsults telonrmStats = relonsponselon.gelontTelonrmStatisticsRelonsults();
    if (telonrmStats != null) {
      relonquelonstCountelonrs.increlonmelonntRelonsultCountelonr(telonrmStats.gelontTelonrmRelonsultsSizelon());
    }
  }

}
