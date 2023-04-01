# Feature

`r2.config.feature` is reddit's feature flagging API. It lets us quickly
switch on and off features for specific segments of users and requests.

It's inspired by Etsy's feature framework, at
https://github.com/etsy/feature - if you're looking to add to this, you may
want to check there first to see if there's learning to be had. There almost
certainly is.

## Use

Using the feature API is simple. At its core:

```python

from r2.config import feature

if feature.is_enabled('some_flag'):
    result = do_new_thing()
else:
    result = do_old_thing()
```

Or in a mako template:

```html

% if feature.is_enabled('some_flag'):
  <strong>New thing!</strong>
% else:
  <span>Old thing.</span>
% endif
```


Along with a component in live_config, currently as an "on" or "off" symbol or JSON:

```ini

# Completely On
feature_some_flag = on

# Completely Off
feature_some_flag = off

# On for admin
feature_some_flag = {"admin": true}

# On for employees
feature_some_flag = {"employee": true}

# On for gold users
feature_some_flag = {"gold": true}

# On for users with the beta preference enabled
feature_some_flag = {"beta": true}

# On for logged in users
feature_some_flag = {"loggedin": true}

# On for logged out users
feature_some_flag = {"loggedout": true}

# On by URL, like ?feature=public_flag_name
feature_some_flag = {"url": "public_flag_name"}

# On by group of users
feature_some_flag = {"users": ["umbrae", "ajacksified"]}

# On when viewing certain subreddits
feature_some_flag = {"subreddits": ["wtf", "aww"]}

# On by subdomain
feature_some_flag = {"subdomains": ["beta"]}

# On by OAuth client IDs
feature_some_flag = {"oauth_clients": ["xyzABC123"]}

# On for a percentage of loggedin users (0 being no users, 100 being all of them)
feature_some_flag = {"percent_loggedin": 25}

# On for a percentage of loggedout users (0 being no users, 100 being all of them)
# N.B: This is based on the value of the `loid` cookie, if there is no `loid`
# cookie the feature will be off.
# The `loid` cookie is currently set in JavaScript, so you can't expect it to
# exist on the first visit or in requests made by API clients.
feature_some_flag = {"percent_loggedout": 25}

# For both admin and a group of users
feature_some_flag = {"admin": true, "users": ["user1", "user2"]}
```

Since we're currently overloading live_config, each feature flag should be
prepended with `feature_` in the config. We may choose to make a live-updating
features block in the future.

You can also use feature flags to define A/B-type experiments.  Logically,
experiments are separated into two parts.  First, there is an *eligibility
check* to determine if the user is allowed to be a part of the experiment;
eligibility is determined by the same selectors as above with the exception of
`percent_loggedin` and `percent_loggedout` which would be redundant.  
Secondly, eligible users are either *bucketed* into a variant or *excluded*
(because the summed percentage of all variants is less than 100).  `is_enabled`
will return False for users who are non-eligible, fall into a control group, or
are excluded; for anyone for whom this is true, you should call `variant` to
find the specific variant they fall into.

In code, this looks something like this:

```python
from r2.config import feature

if feature.is_enabled('some_flag'):
    variant = feature.variant('some_flag')
    if variant == 'test_something':
        do_new_thing()
    elif variant == 'test_something_else':
        do_other_new_thing()
    else:
        raise NotImplementedError('unknown variant %s for some_flag' % variant)
else:
    do_old_thing()
```

with a live_config option defining the experiment parameters:

```ini
# loggedin only experiment with two test variants
feature_some_flag = {"experiment": {"loggedin": true, "experiment_id": 12345, "variants": {"test_something": 5.5, "test_something_else": 10}}}

# Or with custom control group sizes:
feature_some_flag = {"experiment": {"loggedin": true, "experiment_id": 12345, "variants": {"test_something": 5.5, "test_something_else": 10, "control_1": 20, "control_2": 20}}}

# these can be mixed and matched with other selectors (and will OR)
# this will enable the flag for gold users, and then run an experiment for other logged in users
feature_some_flag = {"gold": true, "experiment": {"loggedin": true, "experiment_id": 12345, "variants": {"test_something": 5.5, "test_something_else": 10, "control_1": 20, "control_2": 20}}}
```

If only one non-control variant is defined (an A/A/B test), the code can be
simplified a little bit:

```python
from r2.config import feature

if feature.is_enabled('some_flag'):
    do_new_thing()
else:
    do_old_thing()
```

The experiment dict has a few fields:

* **experiment_id** -- an integer.  While the feature name needs to be unique
  across all currently-defined feature flags, the experiment id should be
  unique across all time.  This allows the data team to uniquely identify
  experiments while looking at historical data.
* **variants** -- a dictionary mapping variant names to percentages.  The
  percent indicates roughly how many eligible users will be chosen to be a part
  of that variant.  Percentages should not exceed 100/n, where n is the number
  of variants.  The number of variants should not change over the course of the
  experiment, but the percentages allocated each can.  Percentages can be
  specified to the tenths of percentages.  If not defined, two control
  groups ("control_1" and "control_2") at 10% each will be automatically added
  to the variants.
* **enabled** -- a boolean, defaulting to true.  Set to false to temporarily
  disable an experiment while still keeping its definition around.

Since it's useful to be able to force bucketing for testing purposes, you can
specify a variant with a secondary syntax for a few flag conditions:

```ini
# ?feature=some_flag_something will force the "test_something" variant and
# ?feature=some_flag_something_else will force "test_something_else"
feature_some_flag = {"url": {"some_flag_something": "test_something", "some_flag_something_else": "test_something_else"}}
```

## When should I use this?

This is useful for a whole lot of reasons.

* To admin-launch something to the company for review before it goes live to
  everyone, and staging isn't a good fit.

* To release something to third party devs and mods before it goes live

* To gradually add traffic to something that may have serious
  impact on load

* To guard something that you might need to quickly turn off for some reason
  or another. Load shedding, security, etc.


## Style guidelines

Copied essentially wholesale from Etsy's guidelines:

To make it easier to push features through the life cycle there are a
few coding guidelines to observe.

First, the feature name argument to the Feature method (`is_enabled`) should
always be a string literal. This will make it easier to find all the places
that a particular feature is checked. If you find yourself creating feature
names at run time and then checking them, you’re probably abusing the Feature
system. Chances are in such a case you don’t really want to be using the
Feature API but rather simply driving your code with some plain old config
data.

Second, the results of the Feature methods should not be cached, such
as by calling `feature.is_enabled` once and storing the result in an
instance variable of some controller. The Feature machinery already
caches the results of the computation it does so it should already be
plenty fast to simply call `feature.is_enabled` whenever needed. This
will again aid in finding the places that depend on a particular feature.

Third, as a check that you’re using the Feature API properly, whenever
you have an if block whose test is a call to `feature.is_enabled`,
make sure that it would make sense to either remove the check and keep
the code or to delete the check and the code together. There shouldn’t
be bits of code within a block guarded by an is_enabled check that
needs to be salvaged if the feature is removed.
