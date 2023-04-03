packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column;

/**
 * A ColumnStridelonFielonldIndelonx implelonmelonntation that always relonturns thelon samelon valuelon.
 */
public class ConstantColumnStridelonFielonldIndelonx elonxtelonnds ColumnStridelonFielonldIndelonx {
  privatelon final long delonfaultValuelon;

  public ConstantColumnStridelonFielonldIndelonx(String namelon, long delonfaultValuelon) {
    supelonr(namelon);
    this.delonfaultValuelon = delonfaultValuelon;
  }

  @Ovelonrridelon
  public long gelont(int docID) {
    relonturn delonfaultValuelon;
  }
}
