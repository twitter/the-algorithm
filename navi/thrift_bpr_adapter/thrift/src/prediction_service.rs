// Autogenerated by Thrift Compiler (0.17.0)
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING

#![allow(unused_imports)]
#![allow(unused_extern_crates)]
#![allow(clippy::too_many_arguments, clippy::type_complexity, clippy::vec_box)]
#![cfg_attr(rustfmt, rustfmt_skip)]


use {
  std::{
    cell::RefCell,
    collections::{BTreeMap, BTreeSet},
    convert::{From, TryFrom},
    default::Default,
    error::Error,
    fmt::{self, Display, Formatter},
    rc::Rc
  },
  thrift::{
    OrderedFloat,
    {ApplicationError, ApplicationErrorKind, ProtocolError, ProtocolErrorKind, TThriftClient},
    protocol::{TFieldIdentifier, TListIdentifier, TMapIdentifier, TMessageIdentifier, TMessageType, TInputProtocol, TOutputProtocol, TSerializable, TSetIdentifier, TStructIdentifier, TType, field_id, verify_expected_message_type, verify_expected_sequence_number, verify_expected_service_call, verify_required_field_exists},
    server::TProcessor,
  },
  super::data
};

//
// PredictionServiceException
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct PredictionServiceException {
  pub description: Option<String>,
}

impl PredictionServiceException {
  pub fn new<F1>(description: F1) -> PredictionServiceException where F1: Into<Option<String>> {
    PredictionServiceException {
      description: description.into(),
    }
  }
}

impl TSerializable for PredictionServiceException {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionServiceException> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<String> = Some("".to_owned());
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = i_prot.read_string()?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    let ret = PredictionServiceException {
      description: f_1,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionServiceException");
    o_prot.write_struct_begin(&struct_ident)?;
    if let Some(ref fld_var) = self.description {
      o_prot.write_field_begin(&TFieldIdentifier::new("description", TType::String, 1))?;
      o_prot.write_string(fld_var)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

impl Default for PredictionServiceException {
  fn default() -> Self {
    PredictionServiceException{
      description: Some("".to_owned()),
    }
  }
}

impl Error for PredictionServiceException {}

impl From<PredictionServiceException> for thrift::Error {
  fn from(e: PredictionServiceException) -> Self {
    thrift::Error::User(Box::new(e))
  }
}

impl Display for PredictionServiceException {
  fn fmt(&self, f: &mut Formatter) -> fmt::Result {
    write!(f, "remote service threw PredictionServiceException")
  }
}

//
// PredictionRequest
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct PredictionRequest {
  pub features: data::DataRecord,
}

impl PredictionRequest {
  pub fn new(features: data::DataRecord) -> PredictionRequest {
    PredictionRequest {
      features,
    }
  }
}

impl TSerializable for PredictionRequest {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionRequest> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<data::DataRecord> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("PredictionRequest.features", &f_1)?;
    let ret = PredictionRequest {
      features: f_1.expect("auto-generated code should have checked for presence of required fields"),
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionRequest");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("features", TType::Struct, 1))?;
    self.features.write_to_out_protocol(o_prot)?;
    o_prot.write_field_end()?;
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// PredictionResponse
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct PredictionResponse {
  pub prediction: data::DataRecord,
}

impl PredictionResponse {
  pub fn new(prediction: data::DataRecord) -> PredictionResponse {
    PredictionResponse {
      prediction,
    }
  }
}

impl TSerializable for PredictionResponse {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionResponse> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<data::DataRecord> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("PredictionResponse.prediction", &f_1)?;
    let ret = PredictionResponse {
      prediction: f_1.expect("auto-generated code should have checked for presence of required fields"),
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionResponse");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("prediction", TType::Struct, 1))?;
    self.prediction.write_to_out_protocol(o_prot)?;
    o_prot.write_field_end()?;
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// BatchPredictionRequest
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct BatchPredictionRequest {
  pub individual_features_list: Vec<data::DataRecord>,
  pub common_features: Option<data::DataRecord>,
}

impl BatchPredictionRequest {
  pub fn new<F2>(individual_features_list: Vec<data::DataRecord>, common_features: F2) -> BatchPredictionRequest where F2: Into<Option<data::DataRecord>> {
    BatchPredictionRequest {
      individual_features_list,
      common_features: common_features.into(),
    }
  }
}

impl TSerializable for BatchPredictionRequest {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<BatchPredictionRequest> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<Vec<data::DataRecord>> = None;
    let mut f_2: Option<data::DataRecord> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let list_ident = i_prot.read_list_begin()?;
          let mut val: Vec<data::DataRecord> = Vec::with_capacity(list_ident.size as usize);
          for _ in 0..list_ident.size {
            let list_elem_0 = data::DataRecord::read_from_in_protocol(i_prot)?;
            val.push(list_elem_0);
          }
          i_prot.read_list_end()?;
          f_1 = Some(val);
        },
        2 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_2 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("BatchPredictionRequest.individual_features_list", &f_1)?;
    let ret = BatchPredictionRequest {
      individual_features_list: f_1.expect("auto-generated code should have checked for presence of required fields"),
      common_features: f_2,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("BatchPredictionRequest");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("individualFeaturesList", TType::List, 1))?;
    o_prot.write_list_begin(&TListIdentifier::new(TType::Struct, self.individual_features_list.len() as i32))?;
    for e in &self.individual_features_list {
      e.write_to_out_protocol(o_prot)?;
    }
    o_prot.write_list_end()?;
    o_prot.write_field_end()?;
    if let Some(ref fld_var) = self.common_features {
      o_prot.write_field_begin(&TFieldIdentifier::new("commonFeatures", TType::Struct, 2))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// BatchPredictionResponse
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct BatchPredictionResponse {
  pub predictions: Vec<data::DataRecord>,
}

impl BatchPredictionResponse {
  pub fn new(predictions: Vec<data::DataRecord>) -> BatchPredictionResponse {
    BatchPredictionResponse {
      predictions,
    }
  }
}

impl TSerializable for BatchPredictionResponse {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<BatchPredictionResponse> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<Vec<data::DataRecord>> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let list_ident = i_prot.read_list_begin()?;
          let mut val: Vec<data::DataRecord> = Vec::with_capacity(list_ident.size as usize);
          for _ in 0..list_ident.size {
            let list_elem_1 = data::DataRecord::read_from_in_protocol(i_prot)?;
            val.push(list_elem_1);
          }
          i_prot.read_list_end()?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("BatchPredictionResponse.predictions", &f_1)?;
    let ret = BatchPredictionResponse {
      predictions: f_1.expect("auto-generated code should have checked for presence of required fields"),
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("BatchPredictionResponse");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("predictions", TType::List, 1))?;
    o_prot.write_list_begin(&TListIdentifier::new(TType::Struct, self.predictions.len() as i32))?;
    for e in &self.predictions {
      e.write_to_out_protocol(o_prot)?;
    }
    o_prot.write_list_end()?;
    o_prot.write_field_end()?;
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// DataRecordPair
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct DataRecordPair {
  pub first: Option<data::DataRecord>,
  pub second: Option<data::DataRecord>,
}

impl DataRecordPair {
  pub fn new<F1, F2>(first: F1, second: F2) -> DataRecordPair where F1: Into<Option<data::DataRecord>>, F2: Into<Option<data::DataRecord>> {
    DataRecordPair {
      first: first.into(),
      second: second.into(),
    }
  }
}

impl TSerializable for DataRecordPair {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<DataRecordPair> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<data::DataRecord> = None;
    let mut f_2: Option<data::DataRecord> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        2 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_2 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    let ret = DataRecordPair {
      first: f_1,
      second: f_2,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("DataRecordPair");
    o_prot.write_struct_begin(&struct_ident)?;
    if let Some(ref fld_var) = self.first {
      o_prot.write_field_begin(&TFieldIdentifier::new("first", TType::Struct, 1))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.second {
      o_prot.write_field_begin(&TFieldIdentifier::new("second", TType::Struct, 2))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

impl Default for DataRecordPair {
  fn default() -> Self {
    DataRecordPair{
      first: None,
      second: None,
    }
  }
}

//
// PredictionTrainingExample
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
pub struct PredictionTrainingExample {
  pub features: Option<data::DataRecord>,
  pub features_for_pairwise_learning: Option<DataRecordPair>,
  pub compact_features: Option<data::CompactDataRecord>,
  pub compressed_data_record: Option<Vec<u8>>,
}

impl PredictionTrainingExample {
  pub fn new<F1, F2, F3, F4>(features: F1, features_for_pairwise_learning: F2, compact_features: F3, compressed_data_record: F4) -> PredictionTrainingExample where F1: Into<Option<data::DataRecord>>, F2: Into<Option<DataRecordPair>>, F3: Into<Option<data::CompactDataRecord>>, F4: Into<Option<Vec<u8>>> {
    PredictionTrainingExample {
      features: features.into(),
      features_for_pairwise_learning: features_for_pairwise_learning.into(),
      compact_features: compact_features.into(),
      compressed_data_record: compressed_data_record.into(),
    }
  }
}

impl TSerializable for PredictionTrainingExample {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionTrainingExample> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<data::DataRecord> = None;
    let mut f_2: Option<DataRecordPair> = None;
    let mut f_3: Option<data::CompactDataRecord> = None;
    let mut f_4: Option<Vec<u8>> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = data::DataRecord::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        2 => {
          let val = DataRecordPair::read_from_in_protocol(i_prot)?;
          f_2 = Some(val);
        },
        3 => {
          let val = data::CompactDataRecord::read_from_in_protocol(i_prot)?;
          f_3 = Some(val);
        },
        4 => {
          let val = i_prot.read_bytes()?;
          f_4 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    let ret = PredictionTrainingExample {
      features: f_1,
      features_for_pairwise_learning: f_2,
      compact_features: f_3,
      compressed_data_record: f_4,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionTrainingExample");
    o_prot.write_struct_begin(&struct_ident)?;
    if let Some(ref fld_var) = self.features {
      o_prot.write_field_begin(&TFieldIdentifier::new("features", TType::Struct, 1))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.features_for_pairwise_learning {
      o_prot.write_field_begin(&TFieldIdentifier::new("featuresForPairwiseLearning", TType::Struct, 2))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.compact_features {
      o_prot.write_field_begin(&TFieldIdentifier::new("compactFeatures", TType::Struct, 3))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.compressed_data_record {
      o_prot.write_field_begin(&TFieldIdentifier::new("compressedDataRecord", TType::String, 4))?;
      o_prot.write_bytes(fld_var)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

impl Default for PredictionTrainingExample {
  fn default() -> Self {
    PredictionTrainingExample{
      features: None,
      features_for_pairwise_learning: None,
      compact_features: None,
      compressed_data_record: Some(Vec::new()),
    }
  }
}

//
// PredictionService service client
//

pub trait TPredictionServiceSyncClient {
  fn get_prediction(&mut self, request: PredictionRequest) -> thrift::Result<PredictionResponse>;
  fn get_batch_prediction(&mut self, batch_request: BatchPredictionRequest) -> thrift::Result<BatchPredictionResponse>;
}

pub trait TPredictionServiceSyncClientMarker {}

pub struct PredictionServiceSyncClient<IP, OP> where IP: TInputProtocol, OP: TOutputProtocol {
  _i_prot: IP,
  _o_prot: OP,
  _sequence_number: i32,
}

impl <IP, OP> PredictionServiceSyncClient<IP, OP> where IP: TInputProtocol, OP: TOutputProtocol {
  pub fn new(input_protocol: IP, output_protocol: OP) -> PredictionServiceSyncClient<IP, OP> {
    PredictionServiceSyncClient { _i_prot: input_protocol, _o_prot: output_protocol, _sequence_number: 0 }
  }
}

impl <IP, OP> TThriftClient for PredictionServiceSyncClient<IP, OP> where IP: TInputProtocol, OP: TOutputProtocol {
  fn i_prot_mut(&mut self) -> &mut dyn TInputProtocol { &mut self._i_prot }
  fn o_prot_mut(&mut self) -> &mut dyn TOutputProtocol { &mut self._o_prot }
  fn sequence_number(&self) -> i32 { self._sequence_number }
  fn increment_sequence_number(&mut self) -> i32 { self._sequence_number += 1; self._sequence_number }
}

impl <IP, OP> TPredictionServiceSyncClientMarker for PredictionServiceSyncClient<IP, OP> where IP: TInputProtocol, OP: TOutputProtocol {}

impl <C: TThriftClient + TPredictionServiceSyncClientMarker> TPredictionServiceSyncClient for C {
  fn get_prediction(&mut self, request: PredictionRequest) -> thrift::Result<PredictionResponse> {
    (
      {
        self.increment_sequence_number();
        let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Call, self.sequence_number());
        let call_args = PredictionServiceGetPredictionArgs { request };
        self.o_prot_mut().write_message_begin(&message_ident)?;
        call_args.write_to_out_protocol(self.o_prot_mut())?;
        self.o_prot_mut().write_message_end()?;
        self.o_prot_mut().flush()
      }
    )?;
    {
      let message_ident = self.i_prot_mut().read_message_begin()?;
      verify_expected_sequence_number(self.sequence_number(), message_ident.sequence_number)?;
      verify_expected_service_call("getPrediction", &message_ident.name)?;
      if message_ident.message_type == TMessageType::Exception {
        let remote_error = thrift::Error::read_application_error_from_in_protocol(self.i_prot_mut())?;
        self.i_prot_mut().read_message_end()?;
        return Err(thrift::Error::Application(remote_error))
      }
      verify_expected_message_type(TMessageType::Reply, message_ident.message_type)?;
      let result = PredictionServiceGetPredictionResult::read_from_in_protocol(self.i_prot_mut())?;
      self.i_prot_mut().read_message_end()?;
      result.ok_or()
    }
  }
  fn get_batch_prediction(&mut self, batch_request: BatchPredictionRequest) -> thrift::Result<BatchPredictionResponse> {
    (
      {
        self.increment_sequence_number();
        let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Call, self.sequence_number());
        let call_args = PredictionServiceGetBatchPredictionArgs { batch_request };
        self.o_prot_mut().write_message_begin(&message_ident)?;
        call_args.write_to_out_protocol(self.o_prot_mut())?;
        self.o_prot_mut().write_message_end()?;
        self.o_prot_mut().flush()
      }
    )?;
    {
      let message_ident = self.i_prot_mut().read_message_begin()?;
      verify_expected_sequence_number(self.sequence_number(), message_ident.sequence_number)?;
      verify_expected_service_call("getBatchPrediction", &message_ident.name)?;
      if message_ident.message_type == TMessageType::Exception {
        let remote_error = thrift::Error::read_application_error_from_in_protocol(self.i_prot_mut())?;
        self.i_prot_mut().read_message_end()?;
        return Err(thrift::Error::Application(remote_error))
      }
      verify_expected_message_type(TMessageType::Reply, message_ident.message_type)?;
      let result = PredictionServiceGetBatchPredictionResult::read_from_in_protocol(self.i_prot_mut())?;
      self.i_prot_mut().read_message_end()?;
      result.ok_or()
    }
  }
}

//
// PredictionService service processor
//

pub trait PredictionServiceSyncHandler {
  fn handle_get_prediction(&self, request: PredictionRequest) -> thrift::Result<PredictionResponse>;
  fn handle_get_batch_prediction(&self, batch_request: BatchPredictionRequest) -> thrift::Result<BatchPredictionResponse>;
}

pub struct PredictionServiceSyncProcessor<H: PredictionServiceSyncHandler> {
  handler: H,
}

impl <H: PredictionServiceSyncHandler> PredictionServiceSyncProcessor<H> {
  pub fn new(handler: H) -> PredictionServiceSyncProcessor<H> {
    PredictionServiceSyncProcessor {
      handler,
    }
  }
  fn process_get_prediction(&self, incoming_sequence_number: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    TPredictionServiceProcessFunctions::process_get_prediction(&self.handler, incoming_sequence_number, i_prot, o_prot)
  }
  fn process_get_batch_prediction(&self, incoming_sequence_number: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    TPredictionServiceProcessFunctions::process_get_batch_prediction(&self.handler, incoming_sequence_number, i_prot, o_prot)
  }
}

pub struct TPredictionServiceProcessFunctions;

impl TPredictionServiceProcessFunctions {
  pub fn process_get_prediction<H: PredictionServiceSyncHandler>(handler: &H, incoming_sequence_number: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let args = PredictionServiceGetPredictionArgs::read_from_in_protocol(i_prot)?;
    match handler.handle_get_prediction(args.request) {
      Ok(handler_return) => {
        let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Reply, incoming_sequence_number);
        o_prot.write_message_begin(&message_ident)?;
        let ret = PredictionServiceGetPredictionResult { result_value: Some(handler_return), prediction_service_exception: None };
        ret.write_to_out_protocol(o_prot)?;
        o_prot.write_message_end()?;
        o_prot.flush()
      },
      Err(e) => {
        match e {
          thrift::Error::User(usr_err) => {
            if usr_err.downcast_ref::<PredictionServiceException>().is_some() {
              let err = usr_err.downcast::<PredictionServiceException>().expect("downcast already checked");
              let ret_err = PredictionServiceGetPredictionResult{ result_value: None, prediction_service_exception: Some(*err) };
              let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Reply, incoming_sequence_number);
              o_prot.write_message_begin(&message_ident)?;
              ret_err.write_to_out_protocol(o_prot)?;
              o_prot.write_message_end()?;
              o_prot.flush()
            } else {
              let ret_err = {
                ApplicationError::new(
                  ApplicationErrorKind::Unknown,
                  usr_err.to_string()
                )
              };
              let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Exception, incoming_sequence_number);
              o_prot.write_message_begin(&message_ident)?;
              thrift::Error::write_application_error_to_out_protocol(&ret_err, o_prot)?;
              o_prot.write_message_end()?;
              o_prot.flush()
            }
          },
          thrift::Error::Application(app_err) => {
            let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Exception, incoming_sequence_number);
            o_prot.write_message_begin(&message_ident)?;
            thrift::Error::write_application_error_to_out_protocol(&app_err, o_prot)?;
            o_prot.write_message_end()?;
            o_prot.flush()
          },
          _ => {
            let ret_err = {
              ApplicationError::new(
                ApplicationErrorKind::Unknown,
                e.to_string()
              )
            };
            let message_ident = TMessageIdentifier::new("getPrediction", TMessageType::Exception, incoming_sequence_number);
            o_prot.write_message_begin(&message_ident)?;
            thrift::Error::write_application_error_to_out_protocol(&ret_err, o_prot)?;
            o_prot.write_message_end()?;
            o_prot.flush()
          },
        }
      },
    }
  }
  pub fn process_get_batch_prediction<H: PredictionServiceSyncHandler>(handler: &H, incoming_sequence_number: i32, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let args = PredictionServiceGetBatchPredictionArgs::read_from_in_protocol(i_prot)?;
    match handler.handle_get_batch_prediction(args.batch_request) {
      Ok(handler_return) => {
        let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Reply, incoming_sequence_number);
        o_prot.write_message_begin(&message_ident)?;
        let ret = PredictionServiceGetBatchPredictionResult { result_value: Some(handler_return), prediction_service_exception: None };
        ret.write_to_out_protocol(o_prot)?;
        o_prot.write_message_end()?;
        o_prot.flush()
      },
      Err(e) => {
        match e {
          thrift::Error::User(usr_err) => {
            if usr_err.downcast_ref::<PredictionServiceException>().is_some() {
              let err = usr_err.downcast::<PredictionServiceException>().expect("downcast already checked");
              let ret_err = PredictionServiceGetBatchPredictionResult{ result_value: None, prediction_service_exception: Some(*err) };
              let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Reply, incoming_sequence_number);
              o_prot.write_message_begin(&message_ident)?;
              ret_err.write_to_out_protocol(o_prot)?;
              o_prot.write_message_end()?;
              o_prot.flush()
            } else {
              let ret_err = {
                ApplicationError::new(
                  ApplicationErrorKind::Unknown,
                  usr_err.to_string()
                )
              };
              let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Exception, incoming_sequence_number);
              o_prot.write_message_begin(&message_ident)?;
              thrift::Error::write_application_error_to_out_protocol(&ret_err, o_prot)?;
              o_prot.write_message_end()?;
              o_prot.flush()
            }
          },
          thrift::Error::Application(app_err) => {
            let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Exception, incoming_sequence_number);
            o_prot.write_message_begin(&message_ident)?;
            thrift::Error::write_application_error_to_out_protocol(&app_err, o_prot)?;
            o_prot.write_message_end()?;
            o_prot.flush()
          },
          _ => {
            let ret_err = {
              ApplicationError::new(
                ApplicationErrorKind::Unknown,
                e.to_string()
              )
            };
            let message_ident = TMessageIdentifier::new("getBatchPrediction", TMessageType::Exception, incoming_sequence_number);
            o_prot.write_message_begin(&message_ident)?;
            thrift::Error::write_application_error_to_out_protocol(&ret_err, o_prot)?;
            o_prot.write_message_end()?;
            o_prot.flush()
          },
        }
      },
    }
  }
}

impl <H: PredictionServiceSyncHandler> TProcessor for PredictionServiceSyncProcessor<H> {
  fn process(&self, i_prot: &mut dyn TInputProtocol, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let message_ident = i_prot.read_message_begin()?;
    let res = match &*message_ident.name {
      "getPrediction" => {
        self.process_get_prediction(message_ident.sequence_number, i_prot, o_prot)
      },
      "getBatchPrediction" => {
        self.process_get_batch_prediction(message_ident.sequence_number, i_prot, o_prot)
      },
      method => {
        Err(
          thrift::Error::Application(
            ApplicationError::new(
              ApplicationErrorKind::UnknownMethod,
              format!("unknown method {}", method)
            )
          )
        )
      },
    };
    thrift::server::handle_process_result(&message_ident, res, o_prot)
  }
}

//
// PredictionServiceGetPredictionArgs
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
struct PredictionServiceGetPredictionArgs {
  request: PredictionRequest,
}

impl PredictionServiceGetPredictionArgs {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionServiceGetPredictionArgs> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<PredictionRequest> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = PredictionRequest::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("PredictionServiceGetPredictionArgs.request", &f_1)?;
    let ret = PredictionServiceGetPredictionArgs {
      request: f_1.expect("auto-generated code should have checked for presence of required fields"),
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("getPrediction_args");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("request", TType::Struct, 1))?;
    self.request.write_to_out_protocol(o_prot)?;
    o_prot.write_field_end()?;
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// PredictionServiceGetPredictionResult
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
struct PredictionServiceGetPredictionResult {
  result_value: Option<PredictionResponse>,
  prediction_service_exception: Option<PredictionServiceException>,
}

impl PredictionServiceGetPredictionResult {
  fn ok_or(self) -> thrift::Result<PredictionResponse> {
    if self.prediction_service_exception.is_some() {
      Err(thrift::Error::User(Box::new(self.prediction_service_exception.unwrap())))
    } else if self.result_value.is_some() {
      Ok(self.result_value.unwrap())
    } else {
      Err(
        thrift::Error::Application(
          ApplicationError::new(
            ApplicationErrorKind::MissingResult,
            "no result received for PredictionServiceGetPrediction"
          )
        )
      )
    }
  }
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionServiceGetPredictionResult> {
    i_prot.read_struct_begin()?;
    let mut f_0: Option<PredictionResponse> = None;
    let mut f_1: Option<PredictionServiceException> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        0 => {
          let val = PredictionResponse::read_from_in_protocol(i_prot)?;
          f_0 = Some(val);
        },
        1 => {
          let val = PredictionServiceException::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    let ret = PredictionServiceGetPredictionResult {
      result_value: f_0,
      prediction_service_exception: f_1,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionServiceGetPredictionResult");
    o_prot.write_struct_begin(&struct_ident)?;
    if let Some(ref fld_var) = self.result_value {
      o_prot.write_field_begin(&TFieldIdentifier::new("result_value", TType::Struct, 0))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.prediction_service_exception {
      o_prot.write_field_begin(&TFieldIdentifier::new("predictionServiceException", TType::Struct, 1))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// PredictionServiceGetBatchPredictionArgs
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
struct PredictionServiceGetBatchPredictionArgs {
  batch_request: BatchPredictionRequest,
}

impl PredictionServiceGetBatchPredictionArgs {
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionServiceGetBatchPredictionArgs> {
    i_prot.read_struct_begin()?;
    let mut f_1: Option<BatchPredictionRequest> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        1 => {
          let val = BatchPredictionRequest::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    verify_required_field_exists("PredictionServiceGetBatchPredictionArgs.batch_request", &f_1)?;
    let ret = PredictionServiceGetBatchPredictionArgs {
      batch_request: f_1.expect("auto-generated code should have checked for presence of required fields"),
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("getBatchPrediction_args");
    o_prot.write_struct_begin(&struct_ident)?;
    o_prot.write_field_begin(&TFieldIdentifier::new("batchRequest", TType::Struct, 1))?;
    self.batch_request.write_to_out_protocol(o_prot)?;
    o_prot.write_field_end()?;
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

//
// PredictionServiceGetBatchPredictionResult
//

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
struct PredictionServiceGetBatchPredictionResult {
  result_value: Option<BatchPredictionResponse>,
  prediction_service_exception: Option<PredictionServiceException>,
}

impl PredictionServiceGetBatchPredictionResult {
  fn ok_or(self) -> thrift::Result<BatchPredictionResponse> {
    if self.prediction_service_exception.is_some() {
      Err(thrift::Error::User(Box::new(self.prediction_service_exception.unwrap())))
    } else if self.result_value.is_some() {
      Ok(self.result_value.unwrap())
    } else {
      Err(
        thrift::Error::Application(
          ApplicationError::new(
            ApplicationErrorKind::MissingResult,
            "no result received for PredictionServiceGetBatchPrediction"
          )
        )
      )
    }
  }
  fn read_from_in_protocol(i_prot: &mut dyn TInputProtocol) -> thrift::Result<PredictionServiceGetBatchPredictionResult> {
    i_prot.read_struct_begin()?;
    let mut f_0: Option<BatchPredictionResponse> = None;
    let mut f_1: Option<PredictionServiceException> = None;
    loop {
      let field_ident = i_prot.read_field_begin()?;
      if field_ident.field_type == TType::Stop {
        break;
      }
      let field_id = field_id(&field_ident)?;
      match field_id {
        0 => {
          let val = BatchPredictionResponse::read_from_in_protocol(i_prot)?;
          f_0 = Some(val);
        },
        1 => {
          let val = PredictionServiceException::read_from_in_protocol(i_prot)?;
          f_1 = Some(val);
        },
        _ => {
          i_prot.skip(field_ident.field_type)?;
        },
      };
      i_prot.read_field_end()?;
    }
    i_prot.read_struct_end()?;
    let ret = PredictionServiceGetBatchPredictionResult {
      result_value: f_0,
      prediction_service_exception: f_1,
    };
    Ok(ret)
  }
  fn write_to_out_protocol(&self, o_prot: &mut dyn TOutputProtocol) -> thrift::Result<()> {
    let struct_ident = TStructIdentifier::new("PredictionServiceGetBatchPredictionResult");
    o_prot.write_struct_begin(&struct_ident)?;
    if let Some(ref fld_var) = self.result_value {
      o_prot.write_field_begin(&TFieldIdentifier::new("result_value", TType::Struct, 0))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    if let Some(ref fld_var) = self.prediction_service_exception {
      o_prot.write_field_begin(&TFieldIdentifier::new("predictionServiceException", TType::Struct, 1))?;
      fld_var.write_to_out_protocol(o_prot)?;
      o_prot.write_field_end()?
    }
    o_prot.write_field_stop()?;
    o_prot.write_struct_end()
  }
}

