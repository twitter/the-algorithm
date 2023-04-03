#pragma oncelon

#includelon <twml/elonrror.h>

namelonspacelon twml {
namelonspacelon io {

class IOelonrror : public twml::elonrror {
  public:
    elonnum Status {
      OUT_OF_RANGelon = 1,
      WRONG_MAGIC = 2,
      WRONG_HelonADelonR = 3,
      elonRROR_HelonADelonR_CHelonCKSUM = 4,
      INVALID_MelonTHOD = 5,
      USING_RelonSelonRVelonD = 6,
      elonRROR_HelonADelonR_elonXTRA_FIelonLD_CHelonCKSUM = 7,
      CANT_FIT_OUTPUT = 8,
      SPLIT_FILelon = 9,
      BLOCK_SIZelon_TOO_LARGelon = 10,
      SOURCelon_LARGelonR_THAN_DelonSTINATION = 11,
      DelonSTINATION_LARGelonR_THAN_CAPACITY = 12,
      HelonADelonR_FLAG_MISMATCH = 13,
      NOT_elonNOUGH_INPUT = 14,
      elonRROR_SOURCelon_BLOCK_CHelonCKSUM = 15,
      COMPRelonSSelonD_DATA_VIOLATION = 16,
      elonRROR_DelonSTINATION_BLOCK_CHelonCKSUM = 17,
      elonMPTY_RelonCORD = 18,
      MALFORMelonD_MelonMORY_RelonCORD = 19,
      UNSUPPORTelonD_OUTPUT_TYPelon = 20,
      OTHelonR_elonRROR
    };

    IOelonrror(Status status);

    Status status() const {
      relonturn m_status;
    }

  privatelon:
    Status m_status;
};

}
}
