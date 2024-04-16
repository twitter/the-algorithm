use std::fmt::D splay;

/**
 * Custom error
 */
#[der ve(Debug)]
pub enum SegDenseError {
     oError(std:: o::Error),
    Json(serde_json::Error),
    JsonM ss ngRoot,
    JsonM ss ngObject,
    JsonM ss ngArray,
    JsonArrayS ze,
    JsonM ss ng nputFeature,
}

 mpl D splay for SegDenseError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            SegDenseError:: oError( o_error) => wr e!(f, "{}",  o_error),
            SegDenseError::Json(serde_json) => wr e!(f, "{}", serde_json),
            SegDenseError::JsonM ss ngRoot => {
                wr e!(f, "{}", "SegDense JSON: Root Node note found!")
            }
            SegDenseError::JsonM ss ngObject => {
                wr e!(f, "{}", "SegDense JSON: Object note found!")
            }
            SegDenseError::JsonM ss ngArray => {
                wr e!(f, "{}", "SegDense JSON: Array Node note found!")
            }
            SegDenseError::JsonArrayS ze => {
                wr e!(f, "{}", "SegDense JSON: Array s ze not as expected!")
            }
            SegDenseError::JsonM ss ng nputFeature => {
                wr e!(f, "{}", "SegDense JSON: M ss ng  nput feature!")
            }
        }
    }
}

 mpl std::error::Error for SegDenseError {}

 mpl From<std:: o::Error> for SegDenseError {
    fn from(err: std:: o::Error) -> Self {
        SegDenseError:: oError(err)
    }
}

 mpl From<serde_json::Error> for SegDenseError {
    fn from(err: serde_json::Error) -> Self {
        SegDenseError::Json(err)
    }
}
