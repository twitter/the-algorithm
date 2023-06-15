use log::debug;
use std::fs;

use serde_json::{Map, Value};

use crate::error::SegDenseError;
use crate::mapper::{FeatureInfo, FeatureMapper, MapWriter};
use crate::segdense_transform_spec_home_recap_2022::{self as seg_dense, InputFeature};

pub fn load_config(file_name: &str) -> Result<seg_dense::Root, SegDenseError> {
    let json_str = fs::read_to_string(file_name)?;
    // &format!("Unable to load segdense file {}", file_name));
    let seg_dense_config = parse(&json_str)?;
    // &format!("Unable to parse segdense file {}", file_name));
    Ok(seg_dense_config)
}

pub fn parse(json_str: &str) -> Result<seg_dense::Root, SegDenseError> {
    let root: seg_dense::Root = serde_json::from_str(json_str)?;
    Ok(root)
}

/**
 * Given a json string containing a seg dense schema create a feature mapper
 * which is essentially:
 *
 *   {feature-id -> (Tensor Index, Index of feature within the tensor)}
 *
 *   Feature id : 64 bit hash of the feature name used in DataRecords.
 *
 *   Tensor Index : A vector of tensors is passed to the model. Tensor
 *     index refers to the tensor this feature is part of.
 *
 *   Index of feature in tensor : The tensors are vectors, the index of
 *     feature is the position to put the feature value.
 *
 * There are many assumptions made in this function that is very model specific.
 * These assumptions are called out below and need to be schematized eventually.
 *
 * Call this once for each segdense schema and cache the FeatureMapper.
 */
pub fn safe_load_config(json_str: &str) -> Result<FeatureMapper, SegDenseError> {
    let root = parse(json_str)?;
    load_from_parsed_config(root)
}

// Perf note : make 'root' un-owned
pub fn load_from_parsed_config(root: seg_dense::Root) -> Result<FeatureMapper, SegDenseError> {
    let v = root.input_features_map;

    // Do error check
    let map: Map<String, Value> = match v {
        Value::Object(map) => map,
        _ => return Err(SegDenseError::JsonMissingObject),
    };

    let mut fm: FeatureMapper = FeatureMapper::new();

    let items = map.values();

    // Perf : Consider a way to avoid clone here
    for item in items.cloned() {
        let mut vec = match item {
            Value::Array(v) => v,
            _ => return Err(SegDenseError::JsonMissingArray),
        };

        if vec.len() != 1 {
            return Err(SegDenseError::JsonArraySize);
        }

        let val = vec.pop().unwrap();

        let input_feature: seg_dense::InputFeature = serde_json::from_value(val)?;
        let feature_id = input_feature.feature_id;
        let feature_info = to_feature_info(&input_feature);

        match feature_info {
            Some(info) => {
                debug!("{:?}", info);
                fm.set(feature_id, info)
            }
            None => (),
        }
    }

    Ok(fm)
}
#[allow(dead_code)]
fn add_feature_info_to_mapper(
    feature_mapper: &mut FeatureMapper,
    input_features: &Vec<InputFeature>,
) {
    for input_feature in input_features.iter() {
        let feature_id = input_feature.feature_id;
        let feature_info = to_feature_info(input_feature);

        match feature_info {
            Some(info) => {
                debug!("{:?}", info);
                feature_mapper.set(feature_id, info)
            }
            None => (),
        }
    }
}

pub fn to_feature_info(input_feature: &seg_dense::InputFeature) -> Option<FeatureInfo> {
    if input_feature.maybe_exclude {
        return None;
    }

    // This part needs to be schema driven
    //
    //   tensor index : Which of these tensors this feature is part of
    //      [Continious, Binary, Discrete, User_embedding, user_eng_embedding, author_embedding]
    //      Note that this order is fixed/hardcoded here, and need to be schematized
    //
    let tensor_idx: i8 = match input_feature.feature_id {
        // user.timelines.twhin_user_follow_embeddings.twhin_user_follow_embeddings
        // Feature name is mapped to a feature-id value. The hardcoded values below correspond to a specific feature name.
        -2550691008059411095 => 3,

        // user.timelines.twhin_user_engagement_embeddings.twhin_user_engagement_embeddings
        5390650078733277231 => 4,

        // original_author.timelines.twhin_author_follow_embeddings.twhin_author_follow_embeddings
        3223956748566688423 => 5,

        _ => match input_feature.feature_type {
            //   feature_type : src/thrift/com/twitter/ml/api/data.thrift
            //       BINARY = 1, CONTINUOUS = 2, DISCRETE = 3,
            //    Map to slots in [Continious, Binary, Discrete, ..]
            1 => 1,
            2 => 0,
            3 => 2,
            _ => -1,
        },
    };

    if input_feature.index < 0 {
        return None;
    }

    // Handle this case later
    if tensor_idx == -1 {
        return None;
    }

    Some(FeatureInfo {
        tensor_index: tensor_idx,
        index_within_tensor: input_feature.index,
    })
}
