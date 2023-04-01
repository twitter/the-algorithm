#!/usr/bin/python

from org.apache.pig.scripting import Pig


SCRIPT_ROOT = "udfs/dist/lib/"
INPUT_ROOT = "input/"
OUTPUT_ROOT = "output"

relations = {"savehide": ("UserQueryCache", "link"),
             "inbox_account_comment": ("UserQueryCache", "comment"),
             "inbox_account_message": ("UserQueryCache", "message"),
             "moderatorinbox": ("SubredditQueryCache", "message"),
             "vote_account_link": ("UserQueryCache", "link"),
            }

####### Pig script fragments
load_things = """
things =
LOAD '$THINGS'
    USING PigStorage()
    AS (id:long,
        ups:int,
        downs:int,
        deleted:chararray,
        spam:chararray,
        timestamp:double);
"""

make_things_items = """
items =
FOREACH things GENERATE *;
"""

load_rels = """
items =
LOAD '$RELS'
    USING PigStorage()
    AS (id:long,
        thing1_id:long,
        thing2_id:long,
        name:chararray,
        timestamp:double);
"""


load_and_map_data = """
data =
LOAD '$DATA'
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
"""

add_unread = """
SPLIT items_with_data
    INTO inbox IF 1 == 1,
         unread IF (chararray)data#'new' == 't';

inbox_with_relname =
FOREACH inbox GENERATE '$RELATION' AS relation, *;

unread_with_relname =
FOREACH unread GENERATE '$RELATION:unread' AS relation, *;

rels_with_relname =
UNION ONSCHEMA inbox_with_relname,
               unread_with_relname;
"""

add_relname = """
rels_with_relname =
FOREACH items GENERATE '$RELATION' AS relation, *;
"""

generate_rel_items = """
minimal_things =
FOREACH things GENERATE id, deleted;

joined =
JOIN rels_with_relname BY thing2_id LEFT OUTER,
     minimal_things BY id;

only_valid =
FILTER joined BY minimal_things::id IS NOT NULL AND
                 deleted == 'f';

potential_columns =
FOREACH only_valid
    GENERATE com.reddit.pig.MAKE_ROWKEY(relation, name, thing1_id) AS rowkey,
             com.reddit.pig.MAKE_THING2_FULLNAME(relation, thing2_id) AS colkey,
             timestamp AS value;
"""

store_top_1000_per_rowkey = """
non_null =
FILTER potential_columns BY rowkey IS NOT NULL AND colkey IS NOT NULL;

grouped =
GROUP non_null BY rowkey;

limited =
FOREACH grouped {
    sorted = ORDER non_null BY value DESC;
    limited = LIMIT sorted 1000;
    GENERATE group AS rowkey, FLATTEN(limited.(colkey, value));
};

jsonified =
FOREACH limited GENERATE rowkey,
                         colkey,
                         com.reddit.pig.TO_JSON(value);

STORE jsonified INTO '$OUTPUT' USING PigStorage();
"""

###### run the jobs
# register the reddit udfs
Pig.registerJar(SCRIPT_ROOT + "reddit-pig-udfs.jar")

# process rels
for rel, (cf, thing2_type) in relations.iteritems():
    # build source for a script
    script = "SET default_parallel 10;"
    script += load_rels
    if "inbox" in rel:
        script += load_and_map_data
        script += add_unread
    else:
        script += add_relname
    script += load_things
    script += generate_rel_items
    script += store_top_1000_per_rowkey

    # run it
    compiled = Pig.compile(script)
    bound = compiled.bind({
        "RELS": INPUT_ROOT + rel + ".dump",
        "DATA": INPUT_ROOT + rel + "-data.dump",
        "THINGS": INPUT_ROOT + thing2_type + ".dump",
        "RELATION": rel,
        "OUTPUT": "/".join((OUTPUT_ROOT, cf, rel)),
    })
    bound.runSingle()

# rebuild message-based queries (just get_sent right now)
if False:
    script = "SET default_parallel 10;"
    script += load_things
    script += make_things_items
    script += load_and_map_data
    script += """
    non_null =
    FILTER items_with_data BY data#'author_id' IS NOT NULL;

    potential_columns =
    FOREACH non_null
    GENERATE
        CONCAT('sent.', com.reddit.pig.TO_36(data#'author_id')) AS rowkey,
        com.reddit.pig.MAKE_FULLNAME('message', id) AS colkey,
        timestamp AS value;
    """
    script += store_top_1000_per_rowkey
    compiled = Pig.compile(script)
    bound = compiled.bind({
        "THINGS": INPUT_ROOT + "message.dump",
        "DATA": INPUT_ROOT + "message-data.dump",
        "OUTPUT": "/".join((OUTPUT_ROOT, "UserQueryCache", "sent")),
    })
    result = bound.runSingle()

# rebuild comment-based queries
if True:
    script = "SET default_parallel 10;"
    script += load_things
    script += make_things_items
    script += load_and_map_data
    script += """
    SPLIT items_with_data INTO
        spam_comments IF spam == 't',
        ham_comments IF spam == 'f';

    ham_comments_with_name =
    FOREACH ham_comments GENERATE 'sr_comments' AS name, *;

    reported_comments =
    FILTER ham_comments BY (int)data#'reported' > 0;

    reported_comments_with_name =
    FOREACH reported_comments GENERATE 'reported_comments' AS name, *;

    spam_comments_with_name =
    FOREACH spam_comments GENERATE 'spam_comments' AS name, *;

    comments_with_name =
    UNION ONSCHEMA ham_comments_with_name,
                   reported_comments_with_name,
                   spam_comments_with_name;

    potential_columns =
    FOREACH comments_with_name GENERATE
        CONCAT(name, CONCAT('.', com.reddit.pig.TO_36(data#'sr_id'))) AS rowkey,
        com.reddit.pig.MAKE_FULLNAME('comment', id) AS colkey,
        timestamp AS value;
    """
    script += store_top_1000_per_rowkey
    compiled = Pig.compile(script)
    bound = compiled.bind({
        "THINGS": INPUT_ROOT + "comment.dump",
        "DATA": INPUT_ROOT + "comment-data.dump",
        "OUTPUT": "/".join((OUTPUT_ROOT, "SubredditQueryCache", "comment")),
    })
    result = bound.runSingle()
