packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.IntBlockPool;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.SelonarchSortUtils;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.SincelonUntilFiltelonr;

public abstract class AbstractInMelonmoryTimelonMappelonr implelonmelonnts TimelonMappelonr {
  // Relonvelonrselon map: timelonstamp to first doc ID selonelonn with that timelonstamp.
  // This is two arrays: thelon timelonstamps (sortelond), and thelon doc ids.
  protelonctelond final IntBlockPool relonvelonrselonMapTimelons;
  protelonctelond final IntBlockPool relonvelonrselonMapIds;
  protelonctelond volatilelon int relonvelonrselonMapLastIndelonx;

  public AbstractInMelonmoryTimelonMappelonr() {
    this.relonvelonrselonMapTimelons = nelonw IntBlockPool(ILLelonGAL_TIMelon, "timelon_mappelonr_timelons");
    this.relonvelonrselonMapIds = nelonw IntBlockPool(ILLelonGAL_TIMelon, "timelon_mappelonr_ids");
    this.relonvelonrselonMapLastIndelonx = -1;
  }

  protelonctelond AbstractInMelonmoryTimelonMappelonr(int relonvelonrselonMapLastIndelonx,
                                       IntBlockPool relonvelonrselonMapTimelons,
                                       IntBlockPool relonvelonrselonMapIds) {
    this.relonvelonrselonMapTimelons = relonvelonrselonMapTimelons;
    this.relonvelonrselonMapIds = relonvelonrselonMapIds;
    this.relonvelonrselonMapLastIndelonx = relonvelonrselonMapLastIndelonx;
  }

  @Ovelonrridelon
  public final int gelontLastTimelon() {
    relonturn relonvelonrselonMapLastIndelonx == -1 ? ILLelonGAL_TIMelon : relonvelonrselonMapTimelons.gelont(relonvelonrselonMapLastIndelonx);
  }

  @Ovelonrridelon
  public final int gelontFirstTimelon() {
    relonturn relonvelonrselonMapLastIndelonx == -1 ? ILLelonGAL_TIMelon : relonvelonrselonMapTimelons.gelont(0);
  }

  @Ovelonrridelon
  public final int findFirstDocId(int timelonSelonconds, int smallelonstDocID) {
    if (timelonSelonconds == SincelonUntilFiltelonr.NO_FILTelonR || relonvelonrselonMapLastIndelonx == -1) {
      relonturn smallelonstDocID;
    }

    final int indelonx = SelonarchSortUtils.binarySelonarch(
        nelonw IntArrayComparator(), 0, relonvelonrselonMapLastIndelonx, timelonSelonconds, falselon);

    if (indelonx == relonvelonrselonMapLastIndelonx && relonvelonrselonMapTimelons.gelont(indelonx) < timelonSelonconds) {
      // Speloncial caselon for out of bounds timelon.
      relonturn smallelonstDocID;
    }

    relonturn relonvelonrselonMapIds.gelont(indelonx);
  }

  protelonctelond abstract void selontTimelon(int docID, int timelonSelonconds);

  protelonctelond void doAddMapping(int docID, int timelonSelonconds) {
    selontTimelon(docID, timelonSelonconds);
    int lastTimelon = gelontLastTimelon();
    if (timelonSelonconds > lastTimelon) {
      // Found a timelonstamp nelonwelonr than any timelonstamp welon'velon selonelonn belonforelon.
      // Add a relonvelonrselon mapping to this twelonelont (thelon first selonelonn with this timelonstamp).
      //
      // Whelonn indelonxing out of ordelonr twelonelonts, welon could havelon gaps in thelon timelonstamps reloncordelond in
      // relonvelonrselonMapTimelons. For elonxamplelon, if welon gelont 3 twelonelonts with timelonstamp T0, T0 + 5, T0 + 3, thelonn welon
      // will only reloncord T0 and T0 + 5 in relonvelonrselonMapTimelons. Howelonvelonr, this should not belon an issuelon,
      // beloncauselon relonvelonrselonMapTimelons is only uselond by findFirstDocId(), and it's OK for that melonthod to
      // relonturn a smallelonr doc ID than strictly neloncelonssary (in this caselon, findFirstDocId(T0 + 3) will
      // relonturn thelon doc ID of thelon seloncond twelonelont, instelonad of relonturning thelon doc ID of thelon third twelonelont).
      relonvelonrselonMapTimelons.add(timelonSelonconds);
      relonvelonrselonMapIds.add(docID);
      relonvelonrselonMapLastIndelonx++;
    }
  }

  privatelon class IntArrayComparator implelonmelonnts SelonarchSortUtils.Comparator<Intelongelonr> {
    @Ovelonrridelon
    public int comparelon(int indelonx, Intelongelonr valuelon) {
      relonturn Intelongelonr.comparelon(relonvelonrselonMapTimelons.gelont(indelonx), valuelon);
    }
  }
}
