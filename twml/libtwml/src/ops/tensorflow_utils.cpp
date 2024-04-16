# nclude "tensorflow_ut ls.h"
# nclude <str ng>
# nclude <vector>

twml::Tensor TFTensor_to_twml_tensor(Tensor & nput) {
   nt nd ms =  nput.d ms();
  std::vector<u nt64_t> d ms(nd ms);
  std::vector<u nt64_t> str des(nd ms);
  for ( nt   = 0;   < nd ms;  ++) {
    d ms[ ] =  nput.d m_s ze( );
  }
  u nt64_t str de = 1;
  for ( nt   = nd ms-1;   >= 0;  --) {
    str des[ ] = str de;
    str de *= d ms[ ];
  }

  sw ch ( nput.dtype()) {
    case DT_ NT8:
      return twml::Tensor( nput.flat< nt8>().data(), d ms, str des, TWML_TYPE_ NT8);
    case DT_U NT8:
      return twml::Tensor( nput.flat<u nt8>().data(), d ms, str des, TWML_TYPE_U NT8);
    case DT_ NT32:
      return twml::Tensor( nput.flat< nt32>().data(), d ms, str des, TWML_TYPE_ NT32);
    case DT_ NT64:
      return twml::Tensor( nput.flat< nt64>().data(), d ms, str des, TWML_TYPE_ NT64);
    case DT_FLOAT:
      return twml::Tensor( nput.flat<float>().data(), d ms, str des, TWML_TYPE_FLOAT);
    case DT_DOUBLE:
      return twml::Tensor( nput.flat<double>().data(), d ms, str des, TWML_TYPE_DOUBLE);
    case DT_BOOL:
      return twml::Tensor( nput.flat<bool>().data(), d ms, str des, TWML_TYPE_BOOL);
    case DT_STR NG:
      return twml::Tensor( nput.flat<str ng>().data(), d ms, str des, TWML_TYPE_STR NG);
    default:
      throw twml::Error(TWML_ERR_TYPE, "Unknown tensor data type.");
      break;
  }
}

const twml::Tensor TFTensor_to_twml_tensor(const Tensor & nput) {
  // TODO: def ne so  type of constant tensor, wh ch should be used for  nputs to force not
  // chang ng
  return TFTensor_to_twml_tensor(const_cast<Tensor&>( nput));
}

twml::RawTensor TFTensor_to_twml_raw_tensor(Tensor & nput) {
   nt nd ms =  nput.d ms();
  std::vector<u nt64_t> d ms(nd ms);
  std::vector<u nt64_t> str des(nd ms);
  for ( nt   = 0;   < nd ms;  ++) {
    d ms[ ] =  nput.d m_s ze( );
  }
  u nt64_t str de = 1;
  for ( nt   = nd ms-1;   >= 0;  --) {
    str des[ ] = str de;
    str de *= d ms[ ];
  }

  sw ch ( nput.dtype()) {
    case DT_ NT8:
      return twml::RawTensor( nput.flat< nt8>().data(), d ms, str des, TWML_TYPE_ NT8, false,  nput.flat< nt8>().s ze());
    case DT_U NT8:
      return twml::RawTensor( nput.flat<u nt8>().data(), d ms, str des, TWML_TYPE_U NT8, false,  nput.flat<u nt8>().s ze());
    case DT_ NT32:
      return twml::RawTensor( nput.flat< nt32>().data(), d ms, str des, TWML_TYPE_ NT32, false,  nput.flat< nt32>().s ze());
    case DT_ NT64:
      return twml::RawTensor( nput.flat< nt64>().data(), d ms, str des, TWML_TYPE_ NT64, false,  nput.flat< nt64>().s ze());
    case DT_FLOAT:
      return twml::RawTensor( nput.flat<float>().data(), d ms, str des, TWML_TYPE_FLOAT, false,  nput.flat<float>().s ze());
    case DT_DOUBLE:
      return twml::RawTensor( nput.flat<double>().data(), d ms, str des, TWML_TYPE_DOUBLE, false,  nput.flat<double>().s ze());
    case DT_BOOL:
      return twml::RawTensor( nput.flat<bool>().data(), d ms, str des, TWML_TYPE_BOOL, false,  nput.flat<bool>().s ze());
    case DT_STR NG:
      return twml::RawTensor( nput.flat<str ng>().data(), d ms, str des, TWML_TYPE_STR NG, false,  nput.flat<str ng>().s ze());
    default:
      throw twml::Error(TWML_ERR_TYPE, "Unknown tensor data type.");
      break;
  }
}

const twml::RawTensor TFTensor_to_twml_raw_tensor(const Tensor & nput) {
  // TODO: def ne so  type of constant tensor, wh ch should be used for  nputs to force not
  // chang ng
  return TFTensor_to_twml_raw_tensor(const_cast<Tensor&>( nput));
}
