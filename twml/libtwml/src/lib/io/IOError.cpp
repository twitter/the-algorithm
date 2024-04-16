# nclude <twml/ o/ OError.h>


na space twml {
na space  o {

na space {
  std::str ng  ssageFromStatus( OError::Status status) {
    sw ch (status) {
      case  OError::OUT_OF_RANGE:
        return "fa led to read enough  nput";
      case  OError::WRONG_MAG C:
        return "wrong mag c  n stream";
      case  OError::WRONG_HEADER:
        return "wrong  ader  n stream";
      case  OError::ERROR_HEADER_CHECKSUM:
        return " ader c cksum doesn't match";
      case  OError:: NVAL D_METHOD:
        return "us ng  nval d  thod";
      case  OError::US NG_RESERVED:
        return "us ng reserved flag";
      case  OError::ERROR_HEADER_EXTRA_F ELD_CHECKSUM:
        return "extra  ader f eld c cksum doesn't match";
      case  OError::CANT_F T_OUTPUT:
        return "can't f  output  n t  g ven space";
      case  OError::SPL T_F LE:
        return "spl  f les aren't supported";
      case  OError::BLOCK_S ZE_TOO_LARGE:
        return "block s ze  s too large";
      case  OError::SOURCE_LARGER_THAN_DEST NAT ON:
        return "s ce  s larger than dest nat on";
      case  OError::DEST NAT ON_LARGER_THAN_CAPAC TY:
        return "dest nat on buffer  s too small to f  uncompressed result";
      case  OError::HEADER_FLAG_M SMATCH:
        return "fa led to match flags for compressed and decompressed data";
      case  OError::NOT_ENOUGH_ NPUT:
        return "not enough  nput to proceed w h decompress on";
      case  OError::ERROR_SOURCE_BLOCK_CHECKSUM:
        return "s ce block c cksum doesn't match";
      case  OError::COMPRESSED_DATA_V OLAT ON:
        return "error occurred wh le decompress ng t  data";
      case  OError::ERROR_DEST NAT ON_BLOCK_CHECKSUM:
        return "dest nat on block c cksum doesn't match";
      case  OError::EMPTY_RECORD:
        return "can't wr e an empty record";
      case  OError::MALFORMED_MEMORY_RECORD:
        return "can't wr e malfor d record";
      case  OError::UNSUPPORTED_OUTPUT_TYPE:
        return "output data type  s not supported";
      case  OError::OTHER_ERROR:
      default:
        return "unknown error occurred";
    }
  }
}  // na space

 OError:: OError(Status status): twml::Error(TWML_ERR_ O, "Found error wh le process ng stream: " +
     ssageFromStatus(status)), m_status(status) {}

}  // na space  o
}  // na space twml
