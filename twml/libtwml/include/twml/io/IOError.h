#pragma once

# nclude <twml/Error.h>

na space twml {
na space  o {

class  OError : publ c twml::Error {
  publ c:
    enum Status {
      OUT_OF_RANGE = 1,
      WRONG_MAG C = 2,
      WRONG_HEADER = 3,
      ERROR_HEADER_CHECKSUM = 4,
       NVAL D_METHOD = 5,
      US NG_RESERVED = 6,
      ERROR_HEADER_EXTRA_F ELD_CHECKSUM = 7,
      CANT_F T_OUTPUT = 8,
      SPL T_F LE = 9,
      BLOCK_S ZE_TOO_LARGE = 10,
      SOURCE_LARGER_THAN_DEST NAT ON = 11,
      DEST NAT ON_LARGER_THAN_CAPAC TY = 12,
      HEADER_FLAG_M SMATCH = 13,
      NOT_ENOUGH_ NPUT = 14,
      ERROR_SOURCE_BLOCK_CHECKSUM = 15,
      COMPRESSED_DATA_V OLAT ON = 16,
      ERROR_DEST NAT ON_BLOCK_CHECKSUM = 17,
      EMPTY_RECORD = 18,
      MALFORMED_MEMORY_RECORD = 19,
      UNSUPPORTED_OUTPUT_TYPE = 20,
      OTHER_ERROR
    };

     OError(Status status);

    Status status() const {
      return m_status;
    }

  pr vate:
    Status m_status;
};

}
}
