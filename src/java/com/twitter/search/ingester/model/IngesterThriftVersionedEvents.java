packagelon com.twittelonr.selonarch.ingelonstelonr.modelonl;

import java.util.Map;

import com.googlelon.common.primitivelons.Longs;

import com.twittelonr.selonarch.common.delonbug.DelonbugelonvelonntAccumulator;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.partitioning.baselon.Partitionablelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;

/**
 * Wrap of ThriftVelonrsionelondelonvelonnts, makelon it partitionablelon for thelon quelonuelon writelonr.
 */
public class IngelonstelonrThriftVelonrsionelondelonvelonnts elonxtelonnds ThriftVelonrsionelondelonvelonnts
    implelonmelonnts Comparablelon<ThriftVelonrsionelondelonvelonnts>, Partitionablelon, DelonbugelonvelonntAccumulator {

  // Makelon uselonrId fielonld elonasielonr to belon accelonsselond to calculatelon partition numbelonr
  privatelon final long uselonrId;

  public IngelonstelonrThriftVelonrsionelondelonvelonnts(long uselonrId) {
    this.uselonrId = uselonrId;
  }

  public IngelonstelonrThriftVelonrsionelondelonvelonnts(long uselonrId,
                                       Map<Bytelon, ThriftIndelonxingelonvelonnt> velonrsionelondelonvelonnts) {
    supelonr(velonrsionelondelonvelonnts);
    this.uselonrId = uselonrId;
  }

  public IngelonstelonrThriftVelonrsionelondelonvelonnts(long uselonrId, ThriftVelonrsionelondelonvelonnts original) {
    supelonr(original);
    this.uselonrId = uselonrId;
  }

  @Ovelonrridelon
  public int comparelonTo(ThriftVelonrsionelondelonvelonnts o) {
    relonturn Longs.comparelon(gelontId(), o.gelontId());
  }

  @Ovelonrridelon
  public long gelontTwelonelontId() {
    relonturn this.gelontId();
  }

  @Ovelonrridelon
  public long gelontUselonrId() {
    relonturn this.uselonrId;
  }
}
