-- Backfill data for CommentParticipationByAccount CF.

%default SCRIPT_ROOT 'udfs/dist/lib'
%default INPUT 'input'
%default OUTPUT 'output'


REGISTER '$SCRIPT_ROOT/reddit-pig-udfs.jar';


items =
LOAD '$INPUT/comment.dump'
    USING PigStorage()
    AS (id:long,
        ups:int,
        downs:int,
        deleted:chararray,
        spam:chararray,
        timestamp:double);


data =
LOAD '$INPUT/comment-data.dump'
    USING PigStorage()
    AS (id:long,
        key:chararray,
        value);


grouped_with_data =
COGROUP items BY id, data BY id;


items_with_data =
FOREACH grouped_with_data
    GENERATE FLATTEN(items),
             com.reddit.pig.MAKE_MAP(data.(key, value)) AS data;


comments_unfiltered =
FOREACH items_with_data
    GENERATE (long)data#'link_id' as link_id,
             (long)data#'author_id' as author_id;


link_x_author_full =
FILTER comments_unfiltered
    BY link_id IS NOT NULL AND
       author_id IS NOT NULL;


link_x_author =
DISTINCT link_x_author_full;


columns =
FOREACH link_x_author
    GENERATE com.reddit.pig.TO_36(author_id) AS rowkey,
             com.reddit.pig.TO_36(link_id) AS name,
             '';


STORE columns INTO '$OUTPUT/CommentParticipationByAccount/';
