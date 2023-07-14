use std::io;
use thiserror::Error;

/**
 * Custom error
 */
#[derive(Debug, Error)]
pub enum SegDenseError {
    #[error("{error}")]
    IoError { error: io::Error },
    #[error("{error}")]
    Json { error: serde_json::Error },
    #[error("SegDense JSON: Root Node note found!")]
    JsonMissingRoot,
    #[error("SegDense JSON: Object note found!")]
    JsonMissingObject,
    #[error("SegDense JSON: Array Node note found!")]
    JsonMissingArray,
    #[error("SegDense JSON: Array size not as expected!")]
    JsonArraySize,
    #[error("SegDense JSON: Missing input feature!")]
    JsonMissingInputFeature,
}

impl From<io::Error> for SegDenseError {
    fn from(error: io::Error) -> Self {
        SegDenseError::IoError { error }
    }
}

impl From<serde_json::Error> for SegDenseError {
    fn from(error: serde_json::Error) -> Self {
        SegDenseError::Json { error }
    }
}
