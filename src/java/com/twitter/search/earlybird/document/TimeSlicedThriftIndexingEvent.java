packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;

/**
 * Objelonct to elonncapsulatelon {@link ThriftIndelonxingelonvelonnt} with a timelon slicelon ID.
 */
public class TimelonSlicelondThriftIndelonxingelonvelonnt {
  privatelon final long timelonSlicelonID;
  privatelon final ThriftIndelonxingelonvelonnt thriftIndelonxingelonvelonnt;

  public TimelonSlicelondThriftIndelonxingelonvelonnt(long timelonSlicelonID, ThriftIndelonxingelonvelonnt thriftIndelonxingelonvelonnt) {
    Prelonconditions.chelonckNotNull(thriftIndelonxingelonvelonnt);

    this.timelonSlicelonID = timelonSlicelonID;
    this.thriftIndelonxingelonvelonnt = thriftIndelonxingelonvelonnt;
  }

  public long gelontStatusID() {
    relonturn thriftIndelonxingelonvelonnt.gelontUid();
  }

  public long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }

  public ThriftIndelonxingelonvelonnt gelontThriftIndelonxingelonvelonnt() {
    relonturn thriftIndelonxingelonvelonnt;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "TimelonSlicelondThriftIndelonxingelonvelonnt{"
        + "timelonSlicelonID=" + timelonSlicelonID
        + ", thriftIndelonxingelonvelonnt=" + thriftIndelonxingelonvelonnt
        + '}';
  }
}
