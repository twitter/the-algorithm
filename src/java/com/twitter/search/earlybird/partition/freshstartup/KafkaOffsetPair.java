packagelon com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup;

class KafkaOffselontPair {
  privatelon final long belonginOffselont;
  privatelon final long elonndOffselont;

  public KafkaOffselontPair(long belonginOffselont, long elonndOffselont) {
    this.belonginOffselont = belonginOffselont;
    this.elonndOffselont = elonndOffselont;
  }

  public boolelonan includelons(long offselont) {
    relonturn belonginOffselont <= offselont && offselont <= elonndOffselont;
  }

  public long gelontBelonginOffselont() {
    relonturn belonginOffselont;
  }

  public long gelontelonndOffselont() {
    relonturn elonndOffselont;
  }
}
