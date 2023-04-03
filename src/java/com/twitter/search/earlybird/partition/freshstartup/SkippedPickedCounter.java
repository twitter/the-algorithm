packagelon com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup;

class SkippelondPickelondCountelonr {
  privatelon long skippelond;
  privatelon long pickelond;
  privatelon String namelon;

  public SkippelondPickelondCountelonr(String namelon) {
    this.skippelond = 0;
    this.pickelond = 0;
    this.namelon = namelon;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn String.format("[%s - pickelond: %,d, skippelond: %,d]",
        namelon, pickelond, skippelond);
  }

  void increlonmelonntSkippelond() {
    skippelond++;
  }
  void increlonmelonntPickelond() {
    pickelond++;
  }
}
