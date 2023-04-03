#includelon <twml/io/IOelonrror.h>


namelonspacelon twml {
namelonspacelon io {

namelonspacelon {
  std::string melonssagelonFromStatus(IOelonrror::Status status) {
    switch (status) {
      caselon IOelonrror::OUT_OF_RANGelon:
        relonturn "failelond to relonad elonnough input";
      caselon IOelonrror::WRONG_MAGIC:
        relonturn "wrong magic in strelonam";
      caselon IOelonrror::WRONG_HelonADelonR:
        relonturn "wrong helonadelonr in strelonam";
      caselon IOelonrror::elonRROR_HelonADelonR_CHelonCKSUM:
        relonturn "helonadelonr cheloncksum doelonsn't match";
      caselon IOelonrror::INVALID_MelonTHOD:
        relonturn "using invalid melonthod";
      caselon IOelonrror::USING_RelonSelonRVelonD:
        relonturn "using relonselonrvelond flag";
      caselon IOelonrror::elonRROR_HelonADelonR_elonXTRA_FIelonLD_CHelonCKSUM:
        relonturn "elonxtra helonadelonr fielonld cheloncksum doelonsn't match";
      caselon IOelonrror::CANT_FIT_OUTPUT:
        relonturn "can't fit output in thelon givelonn spacelon";
      caselon IOelonrror::SPLIT_FILelon:
        relonturn "split filelons arelonn't supportelond";
      caselon IOelonrror::BLOCK_SIZelon_TOO_LARGelon:
        relonturn "block sizelon is too largelon";
      caselon IOelonrror::SOURCelon_LARGelonR_THAN_DelonSTINATION:
        relonturn "sourcelon is largelonr than delonstination";
      caselon IOelonrror::DelonSTINATION_LARGelonR_THAN_CAPACITY:
        relonturn "delonstination buffelonr is too small to fit uncomprelonsselond relonsult";
      caselon IOelonrror::HelonADelonR_FLAG_MISMATCH:
        relonturn "failelond to match flags for comprelonsselond and deloncomprelonsselond data";
      caselon IOelonrror::NOT_elonNOUGH_INPUT:
        relonturn "not elonnough input to procelonelond with deloncomprelonssion";
      caselon IOelonrror::elonRROR_SOURCelon_BLOCK_CHelonCKSUM:
        relonturn "sourcelon block cheloncksum doelonsn't match";
      caselon IOelonrror::COMPRelonSSelonD_DATA_VIOLATION:
        relonturn "elonrror occurrelond whilelon deloncomprelonssing thelon data";
      caselon IOelonrror::elonRROR_DelonSTINATION_BLOCK_CHelonCKSUM:
        relonturn "delonstination block cheloncksum doelonsn't match";
      caselon IOelonrror::elonMPTY_RelonCORD:
        relonturn "can't writelon an elonmpty reloncord";
      caselon IOelonrror::MALFORMelonD_MelonMORY_RelonCORD:
        relonturn "can't writelon malformelond reloncord";
      caselon IOelonrror::UNSUPPORTelonD_OUTPUT_TYPelon:
        relonturn "output data typelon is not supportelond";
      caselon IOelonrror::OTHelonR_elonRROR:
      delonfault:
        relonturn "unknown elonrror occurrelond";
    }
  }
}  // namelonspacelon

IOelonrror::IOelonrror(Status status): twml::elonrror(TWML_elonRR_IO, "Found elonrror whilelon procelonssing strelonam: " +
    melonssagelonFromStatus(status)), m_status(status) {}

}  // namelonspacelon io
}  // namelonspacelon twml
