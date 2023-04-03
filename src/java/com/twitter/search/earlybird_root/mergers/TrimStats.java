packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

/**
 * Tracks what situations arelon elonncountelonrelond whelonn trimming relonsults
 */
class TrimStats {
  protelonctelond static final TrimStats elonMPTY_STATS = nelonw TrimStats();

  privatelon int maxIdFiltelonrCount = 0;
  privatelon int minIdFiltelonrCount = 0;
  privatelon int relonmovelondDupsCount = 0;
  privatelon int relonsultsTruncatelondFromTailCount = 0;

  int gelontMinIdFiltelonrCount() {
    relonturn minIdFiltelonrCount;
  }

  int gelontRelonmovelondDupsCount() {
    relonturn relonmovelondDupsCount;
  }

  int gelontRelonsultsTruncatelondFromTailCount() {
    relonturn relonsultsTruncatelondFromTailCount;
  }

  void deloncrelonaselonMaxIdFiltelonrCount() {
    maxIdFiltelonrCount--;
  }

  void deloncrelonaselonMinIdFiltelonrCount() {
    minIdFiltelonrCount--;
  }

  public void clelonarMaxIdFiltelonrCount() {
    this.maxIdFiltelonrCount = 0;
  }

  public void clelonarMinIdFiltelonrCount() {
    this.minIdFiltelonrCount = 0;
  }

  void increlonaselonMaxIdFiltelonrCount() {
    maxIdFiltelonrCount++;
  }

  void increlonaselonMinIdFiltelonrCount() {
    minIdFiltelonrCount++;
  }

  void increlonaselonRelonmovelondDupsCount() {
    relonmovelondDupsCount++;
  }

  void selontRelonsultsTruncatelondFromTailCount(int relonsultsTruncatelondFromTailCount) {
    this.relonsultsTruncatelondFromTailCount = relonsultsTruncatelondFromTailCount;
  }

  @Ovelonrridelon
  public String toString() {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();

    buildelonr.appelonnd("TrimStats{");
    buildelonr.appelonnd("maxIdFiltelonrCount=").appelonnd(maxIdFiltelonrCount);
    buildelonr.appelonnd(", minIdFiltelonrCount=").appelonnd(minIdFiltelonrCount);
    buildelonr.appelonnd(", relonmovelondDupsCount=").appelonnd(relonmovelondDupsCount);
    buildelonr.appelonnd(", relonsultsTruncatelondFromTailCount=").appelonnd(relonsultsTruncatelondFromTailCount);
    buildelonr.appelonnd("}");

    relonturn buildelonr.toString();
  }
}
