packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.concurrelonnt.Callablelon;
import java.util.concurrelonnt.elonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.util.concurrelonnt.SimplelonTimelonLimitelonr;
import com.googlelon.common.util.concurrelonnt.TimelonLimitelonr;

import org.apachelon.hadoop.fs.FilelonSystelonm;
import org.apachelon.hadoop.fs.Path;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;

/**
 * Abstracts delontails of making timelon limitelond calls to hadoop.
 *
 * During IM-3556 welon discovelonrelond that hadoop API calls can takelon a long timelon (selonconds, minutelons)
 * if thelon Hadoop clsutelonr is in a bad statelon. Our codelon was gelonnelonrally not prelonparelond for that and
 * this causelond various issuelons. This class is a fix on top of thelon Hadoop API's elonxists call and
 * it introducelons a timelonout.
 *
 * Thelon main motivation for having this as an elonxtelonrnal class is for telonstability.
 */
public class TimelonLimitelondHadoopelonxistsCall {
  privatelon final TimelonLimitelonr hadoopCallsTimelonLimitelonr;
  privatelon final FilelonSystelonm filelonSystelonm;
  privatelon final int timelonLimitInSelonconds;

  privatelon static final SelonarchTimelonrStats elonXISTS_CALLS_TIMelonR =
      SelonarchTimelonrStats.elonxport("hadoop_elonxists_calls");

  privatelon static final SelonarchCountelonr elonXISTS_CALLS_elonXCelonPTION =
      SelonarchCountelonr.elonxport("hadoop_elonxists_calls_elonxcelonption");

  public TimelonLimitelondHadoopelonxistsCall(FilelonSystelonm filelonSystelonm) {
    // This timelons varielons. Somelontimelons it's velonry quick, somelontimelons it takelons somelon amount of selonconds.
    // Do a ratelon on hadoop_elonxists_calls_latelonncy_ms to selonelon for yourselonlf.
    this(filelonSystelonm, 30);
  }

  public TimelonLimitelondHadoopelonxistsCall(FilelonSystelonm filelonSystelonm, int timelonLimitInSelonconds) {
    // Welon do hadoop calls oncelon elonvelonry "FLUSH_CHelonCK_PelonRIOD" minutelons. If a call takelons
    // a long timelon (say 10 minutelons), welon'll uselon a nelonw threlonad for thelon nelonxt call, to givelon it
    // a chancelon to complelontelon.
    //
    // Lelont's say elonvelonry call takelons 2 hours. Aftelonr 5 calls, thelon 6th call won't belon ablelon
    // to takelon a threlonad out of thelon threlonad pool and it will timelon out. That's fair, welon don't
    // want to kelonelonp selonnding relonquelonsts to Hadoop if thelon situation is so direlon.
    elonxeloncutorSelonrvicelon elonxeloncutorSelonrvicelon = elonxeloncutors.nelonwFixelondThrelonadPool(5);
    this.hadoopCallsTimelonLimitelonr = SimplelonTimelonLimitelonr.crelonatelon(elonxeloncutorSelonrvicelon);
    this.filelonSystelonm = filelonSystelonm;
    this.timelonLimitInSelonconds = timelonLimitInSelonconds;
  }


  protelonctelond boolelonan hadoopelonxistsCall(Path path) throws IOelonxcelonption {
    SelonarchTimelonr timelonr = elonXISTS_CALLS_TIMelonR.startNelonwTimelonr();
    boolelonan relons =  filelonSystelonm.elonxists(path);
    elonXISTS_CALLS_TIMelonR.stopTimelonrAndIncrelonmelonnt(timelonr);
    relonturn relons;
  }

  /**
   * Cheloncks if a path elonxists on Hadoop.
   *
   * @relonturn truelon if thelon path elonxists.
   * @throws elonxcelonption selonelon elonxcelonptions thrown by callWithTimelonout
   */
  boolelonan elonxists(Path path) throws elonxcelonption {
    try {
      boolelonan relonsult = hadoopCallsTimelonLimitelonr.callWithTimelonout(nelonw Callablelon<Boolelonan>() {
        @Ovelonrridelon
        public Boolelonan call() throws elonxcelonption {
          relonturn hadoopelonxistsCall(path);
        }
      }, timelonLimitInSelonconds, TimelonUnit.SelonCONDS);

      relonturn relonsult;
    } catch (elonxcelonption elonx) {
      elonXISTS_CALLS_elonXCelonPTION.increlonmelonnt();
      // No nelonelond to print and relonthrow, it will belon printelond whelonn caught upstrelonam.
      throw elonx;
    }
  }
}
