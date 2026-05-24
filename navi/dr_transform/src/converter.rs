use std::collections::BTreeSet;
use std::fmt::{self, Debug, Display};
use std::fs;

use crate::all_config;
use crate::all_config::AllConfig;
use anyhow::{bail, Context};
use bpr_thrift::data::DataRecord;
use bpr_thrift::prediction_service::BatchPredictionRequest;
use bpr_thrift::tensor::GeneralTensor;
use log::debug;
use ndarray::Array2;
use once_cell::sync::OnceCell;
use ort::tensor::InputTensor;
use prometheus::{HistogramOpts, HistogramVec};
use segdense::mapper::{FeatureMapper, MapReader};
use segdense::segdense_transform_spec_home_recap_2022::{DensificationTransformSpec, Root};
use segdense::util;
use thrift::protocol::{TBinaryInputProtocol, TSerializable};
use thrift::transport::TBufferChannel;

pub fn log_feature_match(
    dr: &DataRecord,
    seg_dense_config: &DensificationTransformSpec,
    dr_type: String,
) {
    // Note the following algorithm matches features from config using linear search.
    // Also the record source is MinDataRecord. This includes only binary and continous features for now.

    for (feature_id, feature_value) in dr.continuous_features.as_ref().unwrap() {
        debug!(
            "{} - Continous Datarecord => Feature ID: {}, Feature value: {}",
            dr_type, feature_id, feature_value
        );
        for input_feature in &seg_dense_config.cont.input_features {
            if input_feature.feature_id == *feature_id {
                debug!("Matching input feature: {:?}", input_feature)
            }
        }
    }

    for feature_id in dr.binary_features.as_ref().unwrap() {
        debug!(
            "{} - Binary Datarecord => Feature ID: {}",
            dr_type, feature_id
        );
        for input_feature in &seg_dense_config.binary.input_features {
            if input_feature.feature_id == *feature_id {
                debug!("Found input feature: {:?}", input_feature)
            }
        }
    }
}

pub fn log_feature_matches(drs: &Vec<DataRecord>, seg_dense_config: &DensificationTransformSpec) {
    for dr in drs {
        log_feature_match(dr, seg_dense_config, String::from("individual"));
    }
}

pub trait Converter: Send + Sync + Debug + 'static + Display {
    fn convert(&self, input: Vec<Vec<u8>>) -> (Vec<InputTensor>, Vec<usize>);
}

#[derive(Debug)]
#[allow(dead_code)]
pub struct BatchPredictionRequestToTorchTensorConverter {
    all_config: AllConfig,
    seg_dense_config: Root,
    all_config_path: String,
    seg_dense_config_path: String,
    feature_mapper: FeatureMapper,
    user_embedding_feature_id: i64,
    user_eng_embedding_feature_id: i64,
    author_embedding_feature_id: i64,
    discrete_features_to_report: BTreeSet<i64>,
    continuous_features_to_report: BTreeSet<i64>,
    discrete_feature_metrics: &'static HistogramVec,
    continuous_feature_metrics: &'static HistogramVec,
}

impl Display for BatchPredictionRequestToTorchTensorConverter {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(
            f,
            "all_config_path: {}, seg_dense_config_path:{}",
            self.all_config_path, self.seg_dense_config_path
        )
    }
}

impl BatchPredictionRequestToTorchTensorConverter {
    pub fn new(
        model_dir: &str,
        model_version: &str,
        reporting_feature_ids: Vec<(i64, &str)>,
        register_metric_fn: Option<impl Fn(&HistogramVec)>,
    ) -> anyhow::Result<BatchPredictionRequestToTorchTensorConverter> {
        let all_config_path = format!("{}/{}/all_config.json", model_dir, model_version);
        let seg_dense_config_path = format!(
            "{}/{}/segdense_transform_spec_home_recap_2022.json",
            model_dir, model_version
        );
        let seg_dense_config = util::load_config(&seg_dense_config_path)?;
        let all_config = all_config::parse(
            &fs::read_to_string(&all_config_path)
                .with_context(|| "error loading all_config.json - ")?,
        )?;

        let feature_mapper = util::load_from_parsed_config(seg_dense_config.clone())?;

        let user_embedding_feature_id = Self::get_feature_id(
            &all_config
                .train_data
                .seg_dense_schema
                .renamed_features
                .user_embedding,
            &seg_dense_config,
        );
        let user_eng_embedding_feature_id = Self::get_feature_id(
            &all_config
                .train_data
                .seg_dense_schema
                .renamed_features
                .user_eng_embedding,
            &seg_dense_config,
        );
        let author_embedding_feature_id = Self::get_feature_id(
            &all_config
                .train_data
                .seg_dense_schema
                .renamed_features
                .author_embedding,
            &seg_dense_config,
        );
        static METRICS: OnceCell<(HistogramVec, HistogramVec)> = OnceCell::new();
        let (discrete_feature_metrics, continuous_feature_metrics) = METRICS.get_or_init(|| {
            let discrete = HistogramVec::new(
                HistogramOpts::new(":navi:feature_id:discrete", "Discrete Feature ID values")
                    .buckets(Vec::from(&[
                        0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0,
                        120.0, 130.0, 140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0,
                        300.0, 500.0, 1000.0, 10000.0, 100000.0,
                    ] as &'static [f64])),
                &["feature_id"],
            )
            .expect("metric cannot be created");
            let continuous = HistogramVec::new(
                HistogramOpts::new(
                    ":navi:feature_id:continuous",
                    "continuous Feature ID values",
                )
                .buckets(Vec::from(&[
                    0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0, 120.0,
                    130.0, 140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0,
                    1000.0, 10000.0, 100000.0,
                ] as &'static [f64])),
                &["feature_id"],
            )
            .expect("metric cannot be created");
            register_metric_fn.map(|r| {
                r(&discrete);
                r(&continuous);
            });
            (discrete, continuous)
        });

        let mut discrete_features_to_report = BTreeSet::new();
        let mut continuous_features_to_report = BTreeSet::new();

        for (feature_id, feature_type) in reporting_feature_ids.iter() {
            match *feature_type {
                "discrete" => discrete_features_to_report.insert(feature_id.clone()),
                "continuous" => continuous_features_to_report.insert(feature_id.clone()),
                _ => bail!(
                    "Invalid feature type {} for reporting metrics!",
                    feature_type
                ),
            };
        }

        Ok(BatchPredictionRequestToTorchTensorConverter {
            all_config,
            seg_dense_config,
            all_config_path,
            seg_dense_config_path,
            feature_mapper,
            user_embedding_feature_id,
            user_eng_embedding_feature_id,
            author_embedding_feature_id,
            discrete_features_to_report,
            continuous_features_to_report,
            discrete_feature_metrics,
            continuous_feature_metrics,
        })
    }

    fn get_feature_id(feature_name: &str, seg_dense_config: &Root) -> i64 {
        // given a feature name, we get the complex feature type id
        for feature in &seg_dense_config.complex_feature_type_transform_spec {
            if feature.full_feature_name == feature_name {
                return feature.feature_id;
            }
        }
        -1
    }

    fn parse_batch_prediction_request(bytes: Vec<u8>) -> BatchPredictionRequest {
        // parse batch prediction request into a struct from byte array repr.
        let mut bc = TBufferChannel::with_capacity(bytes.len(), 0);
        bc.set_readable_bytes(&bytes);
        let mut protocol = TBinaryInputProtocol::new(bc, true);
        BatchPredictionRequest::read_from_in_protocol(&mut protocol).unwrap()
    }

    fn get_embedding_tensors(
        &self,
        bprs: &[BatchPredictionRequest],
        feature_id: i64,
        batch_size: &[usize],
    ) -> Array2<f32> {
        // given an embedding feature id, extract the float tensor array into tensors.
        let cols: usize = 200;
        let rows: usize = batch_size[batch_size.len() - 1];
        let total_size = rows * cols;

        let mut working_set = vec![0 as f32; total_size];
        let mut bpr_start = 0;
        for (bpr, &bpr_end) in bprs.iter().zip(batch_size) {
            if bpr.common_features.is_some() {
                if bpr.common_features.as_ref().unwrap().tensors.is_some() {
                    if bpr
                        .common_features
                        .as_ref()
                        .unwrap()
                        .tensors
                        .as_ref()
                        .unwrap()
                        .contains_key(&feature_id)
                    {
                        let source_tensor = bpr
                            .common_features
                            .as_ref()
                            .unwrap()
                            .tensors
                            .as_ref()
                            .unwrap()
                            .get(&feature_id)
                            .unwrap();
                        let tensor = match source_tensor {
                            GeneralTensor::FloatTensor(float_tensor) =>
                            //Tensor::of_slice(
                            {
                                float_tensor
                                    .floats
                                    .iter()
                                    .map(|x| x.into_inner() as f32)
                                    .collect::<Vec<_>>()
                            }
                            _ => vec![0 as f32; cols],
                        };

                        // since the tensor is found in common feature, add it in all batches
                        for row in bpr_start..bpr_end {
                            for col in 0..cols {
                                working_set[row * cols + col] = tensor[col];
                            }
                        }
                    }
                }
            }
            // find the feature in individual feature list and add to corresponding batch.
            for (index, datarecord) in bpr.individual_features_list.iter().enumerate() {
                if datarecord.tensors.is_some()
                    && datarecord
                        .tensors
                        .as_ref()
                        .unwrap()
                        .contains_key(&feature_id)
                {
                    let source_tensor = datarecord
                        .tensors
                        .as_ref()
                        .unwrap()
                        .get(&feature_id)
                        .unwrap();
                    let tensor = match source_tensor {
                        GeneralTensor::FloatTensor(float_tensor) => float_tensor
                            .floats
                            .iter()
                            .map(|x| x.into_inner() as f32)
                            .collect::<Vec<_>>(),
                        _ => vec![0 as f32; cols],
                    };
                    for col in 0..cols {
                        working_set[(bpr_start + index) * cols + col] = tensor[col];
                    }
                }
            }
            bpr_start = bpr_end;
        }
        Array2::<f32>::from_shape_vec([rows, cols], working_set).unwrap()
    }

    // Todo : Refactor, create a generic version with different type and field accessors
    //   Example paramterize and then instiantiate the following
    //           (FLOAT --> FLOAT, DataRecord.continuous_feature)
    //           (BOOL --> INT64, DataRecord.binary_feature)
    //           (INT64 --> INT64, DataRecord.discrete_feature)
    fn get_continuous(&self, bprs: &[BatchPredictionRequest], batch_ends: &[usize]) -> InputTensor {
        // These need to be part of model schema
        let rows: usize = batch_ends[batch_ends.len() - 1];
        let cols: usize = 5293;
        let full_size: usize = rows * cols;
        let default_val = f32::NAN;

        let mut tensor = vec![default_val; full_size];

        let mut bpr_start = 0;
        for (bpr, &bpr_end) in bprs.iter().zip(batch_ends) {
            // Common features
            if bpr.common_features.is_some()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .continuous_features
                    .is_some()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .continuous_features
                    .as_ref()
                    .unwrap();

                for feature in common_features {
                    match self.feature_mapper.get(feature.0) {
                        Some(f_info) => {
                            let idx = f_info.index_within_tensor as usize;
                            if idx < cols {
                                // Set value in each row
                                for r in bpr_start..bpr_end {
                                    let flat_index: usize = r * cols + idx;
                                    tensor[flat_index] = feature.1.into_inner() as f32;
                                }
                            }
                        }
                        None => (),
                    }
                    if self.continuous_features_to_report.contains(feature.0) {
                        self.continuous_feature_metrics
                            .with_label_values(&[feature.0.to_string().as_str()])
                            .observe(feature.1.into_inner())
                    } else if self.discrete_features_to_report.contains(feature.0) {
                        self.discrete_feature_metrics
                            .with_label_values(&[feature.0.to_string().as_str()])
                            .observe(feature.1.into_inner())
                    }
                }
            }

            // Process the batch of datarecords
            for r in bpr_start..bpr_end {
                let dr: &DataRecord =
                    &bpr.individual_features_list[usize::try_from(r - bpr_start).unwrap()];
                if dr.continuous_features.is_some() {
                    for feature in dr.continuous_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature.0) {
                            Some(f_info) => {
                                let idx = f_info.index_within_tensor as usize;
                                let flat_index: usize = r * cols + idx;
                                if flat_index < tensor.len() && idx < cols {
                                    tensor[flat_index] = feature.1.into_inner() as f32;
                                }
                            }
                            None => (),
                        }
                        if self.continuous_features_to_report.contains(feature.0) {
                            self.continuous_feature_metrics
                                .with_label_values(&[feature.0.to_string().as_str()])
                                .observe(feature.1.into_inner() as f64)
                        } else if self.discrete_features_to_report.contains(feature.0) {
                            self.discrete_feature_metrics
                                .with_label_values(&[feature.0.to_string().as_str()])
                                .observe(feature.1.into_inner() as f64)
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }

        InputTensor::FloatTensor(
            Array2::<f32>::from_shape_vec([rows, cols], tensor)
                .unwrap()
                .into_dyn(),
        )
    }

    fn get_binary(&self, bprs: &[BatchPredictionRequest], batch_ends: &[usize]) -> InputTensor {
        // These need to be part of model schema
        let rows: usize = batch_ends[batch_ends.len() - 1];
        let cols: usize = 149;
        let full_size: usize = rows * cols;
        let default_val: i64 = 0;

        let mut v = vec![default_val; full_size];

        let mut bpr_start = 0;
        for (bpr, &bpr_end) in bprs.iter().zip(batch_ends) {
            // Common features
            if bpr.common_features.is_some()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .binary_features
                    .is_some()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .binary_features
                    .as_ref()
                    .unwrap();

                for feature in common_features {
                    match self.feature_mapper.get(feature) {
                        Some(f_info) => {
                            let idx = f_info.index_within_tensor as usize;
                            if idx < cols {
                                // Set value in each row
                                for r in bpr_start..bpr_end {
                                    let flat_index: usize = r * cols + idx;
                                    v[flat_index] = 1;
                                }
                            }
                        }
                        None => (),
                    }
                }
            }

            // Process the batch of datarecords
            for r in bpr_start..bpr_end {
                let dr: &DataRecord = &bpr.individual_features_list[r - bpr_start];
                if dr.binary_features.is_some() {
                    for feature in dr.binary_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature) {
                            Some(f_info) => {
                                let idx = f_info.index_within_tensor as usize;
                                let flat_index: usize = r * cols + idx;
                                v[flat_index] = 1;
                            }
                            None => (),
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }
        InputTensor::Int64Tensor(
            Array2::<i64>::from_shape_vec([rows, cols], v)
                .unwrap()
                .into_dyn(),
        )
    }

    #[allow(dead_code)]
    fn get_discrete(&self, bprs: &[BatchPredictionRequest], batch_ends: &[usize]) -> InputTensor {
        // These need to be part of model schema
        let rows: usize = batch_ends[batch_ends.len() - 1];
        let cols: usize = 320;
        let full_size: usize = rows * cols;
        let default_val: i64 = 0;

        let mut v = vec![default_val; full_size];

        let mut bpr_start = 0;
        for (bpr, &bpr_end) in bprs.iter().zip(batch_ends) {
            // Common features
            if bpr.common_features.is_some()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .discrete_features
                    .is_some()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .discrete_features
                    .as_ref()
                    .unwrap();

                for feature in common_features {
                    match self.feature_mapper.get(feature.0) {
                        Some(f_info) => {
                            let idx = f_info.index_within_tensor as usize;
                            if idx < cols {
                                // Set value in each row
                                for r in bpr_start..bpr_end {
                                    let flat_index: usize = r * cols + idx;
                                    v[flat_index] = *feature.1;
                                }
                            }
                        }
                        None => (),
                    }
                    if self.discrete_features_to_report.contains(feature.0) {
                        self.discrete_feature_metrics
                            .with_label_values(&[feature.0.to_string().as_str()])
                            .observe(*feature.1 as f64)
                    }
                }
            }

            // Process the batch of datarecords
            for r in bpr_start..bpr_end {
                let dr: &DataRecord = &bpr.individual_features_list[usize::try_from(r).unwrap()];
                if dr.discrete_features.is_some() {
                    for feature in dr.discrete_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature.0) {
                            Some(f_info) => {
                                let idx = f_info.index_within_tensor as usize;
                                let flat_index: usize = r * cols + idx;
                                if flat_index < v.len() && idx < cols {
                                    v[flat_index] = *feature.1;
                                }
                            }
                            None => (),
                        }
                        if self.discrete_features_to_report.contains(feature.0) {
                            self.discrete_feature_metrics
                                .with_label_values(&[feature.0.to_string().as_str()])
                                .observe(*feature.1 as f64)
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }
        InputTensor::Int64Tensor(
            Array2::<i64>::from_shape_vec([rows, cols], v)
                .unwrap()
                .into_dyn(),
        )
    }

    fn get_user_embedding(
        &self,
        bprs: &[BatchPredictionRequest],
        batch_ends: &[usize],
    ) -> InputTensor {
        InputTensor::FloatTensor(
            self.get_embedding_tensors(bprs, self.user_embedding_feature_id, batch_ends)
                .into_dyn(),
        )
    }

    fn get_eng_embedding(
        &self,
        bpr: &[BatchPredictionRequest],
        batch_ends: &[usize],
    ) -> InputTensor {
        InputTensor::FloatTensor(
            self.get_embedding_tensors(bpr, self.user_eng_embedding_feature_id, batch_ends)
                .into_dyn(),
        )
    }

    fn get_author_embedding(
        &self,
        bpr: &[BatchPredictionRequest],
        batch_ends: &[usize],
    ) -> InputTensor {
        InputTensor::FloatTensor(
            self.get_embedding_tensors(bpr, self.author_embedding_feature_id, batch_ends)
                .into_dyn(),
        )
    }
}

impl Converter for BatchPredictionRequestToTorchTensorConverter {
    fn convert(&self, batched_bytes: Vec<Vec<u8>>) -> (Vec<InputTensor>, Vec<usize>) {
        let bprs = batched_bytes
            .into_iter()
            .map(|bytes| {
                BatchPredictionRequestToTorchTensorConverter::parse_batch_prediction_request(bytes)
            })
            .collect::<Vec<_>>();
        let batch_ends = bprs
            .iter()
            .map(|bpr| bpr.individual_features_list.len())
            .scan(0usize, |acc, e| {
                //running total
                *acc = *acc + e;
                Some(*acc)
            })
            .collect::<Vec<_>>();

        let t1 = self.get_continuous(&bprs, &batch_ends);
        let t2 = self.get_binary(&bprs, &batch_ends);
        //let _t3 = self.get_discrete(&bprs, &batch_ends);
        let t4 = self.get_user_embedding(&bprs, &batch_ends);
        let t5 = self.get_eng_embedding(&bprs, &batch_ends);
        let t6 = self.get_author_embedding(&bprs, &batch_ends);

        (vec![t1, t2, t4, t5, t6], batch_ends)
    }
}
