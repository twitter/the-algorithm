Overview
========

Visibility Filtering is a centralized rule engine that instructs clients how to alter the display of certain Twitter content on read time. The Visibility Filtering library is responsible for filtering Twitter content to support legal compliance, improve product quality, increase user trust, protect revenue through the use of hard-filtering, visible product treatments, and coarse-grained downranking.

Notice
======

Visibility Filtering library is currently being reviewed and rebuilt, and part of the code has been removed and is not ready to be shared yet. The remaining part of the code needs further review and will be shared once it’s ready. Also code comments have been sanitized.

SafetyLevel
===========

Represents the product context in which the Viewer is requesting to view the Content (e.g. Timeline, Profile).

Features
========

Include safety labels and other metadata of a Tweet, flags set on a User (including the Viewer), relationships between Users (e.g. block, follow), User settings, relationships between Users and Content (e.g. reported for spam).

Action
======

The way the Visibility Framework instructs the client to respond to the Viewer’s request for Content, and can include hard filtering (e.g. Drop), soft filtering (e.g. Labels and Interstitials), ranking clues, etc.

Condition
=========

Returns a boolean when given map of Features. Conditions can be combined to determine if a Rule should return an Action or the default (Allow).

Policy
======

Rules are expressed as a sequence in priority order to create a Visibility Policy. The library has one policy
per SafetyLevel.

RuleEngine
===========

Evaluates the Action for a Request.

SafetyLabel
===========

A primary labeling mechanism for Safety. A labeled entity associates with tweet, user, Direct Message, media, space etc. Safety labels power different ways of remediations (e.g. applying a SafetyLabel that results in tweet interstitial or notice).

SafetyLabelType
===============

Describes a particular policy violation for a given noun instance, and usually leads to reduced visibility of the
labeled entity in product surfaces. There are many deprecated, and experimental safety label types. Labels with these safety label types have no effect on VF. Additionally, some safety label types are not used, and not designed for VF.
