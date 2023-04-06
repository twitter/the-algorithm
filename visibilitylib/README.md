Overview
========

The Visibility Filtering algorithm is a tool that instructs Twitter clients on how to adjust the display of certain content in real-time. This library aims to filter Twitter content to ensure compliance with laws and regulations, enhance product quality, promote user trust, and safeguard revenue. The Visibility Filtering algorithm identifies potentially harmful content that breaches Twitter's policies. This includes content flagged by users or algorithms and content automatically detected by the platform as potentially harmful.

The algorithm employs a multifaceted approach to identify and categorize potentially harmful content on Twitter. It utilizes several concepts such as `SafetyLevel`, `SafetyLabel`, and `SafetyLabelType`. Twitter uses labeling mechanisms to apply various remediation techniques, such as reducing the visibility of flagged content or employing interstitials to alert users of potentially harmful content. This effort is aimed at enhancing the safety and trust of Twitter's platform for all its users.


## Table of Contents

- [Overview](#overview)
  * [Notice](#notice)
  * [Features](#features)
- [Action](#action)
- [Condition](#condition)
- [Policy](#policy)
  * [RuleEngine](#ruleengine)
    + [SafetyLevel](#safetylevel)
    + [SafetyLabel](#safetylabel)
    + [SafetyLabelType](#safetylabeltype)

## Notice
The Visibility Filtering library is currently undergoing a review and reconstruction process, resulting in the removal of certain segments of the code that remain inaccessible. The remaining code necessitates additional review and will be made available upon completion. Furthermore, the code comments have undergone sanitization.

## Features
The Visibility Filtering algorithm uses features and metadata to identify potentially harmful content on Twitter. These features include safety labels attached to Tweets and other entities to indicate potential risks or violations of platform policies. The algorithm considers flags that may be set on individual users, including the Viewer, to highlight potential risks or concerns. Relationships between Users, such as block or follow relationships, may also be considered when filtering content. User settings and relationships between Users and Content, such as spam reports, may also be considered.

## Action
The action refers to how the Visibility Framework instructs the client to respond to a Viewer's request for content. It involves various forms of filtering, such as hard filtering (content removal), soft filtering (adding labels and interstitials to content), or providing ranking clues to adjust the order in which content is displayed.

## Condition
A condition evaluates a given map of features and returns a `boolean` value. For example, the conditions can determine whether a rule should return an action or allow content to be displayed by default.

## Policy
The Visibility Filtering algorithm uses a set of rules to establish a Visibility Policy. The rules are in priority order, and each SafetyLevel has its policy, which determines the actions that the Visibility Framework takes based on the conditions evaluated.

## RuleEngine
The RuleEngine evaluates the requested Action by processing the rules in the Visibility Policy in a specific order.

### SafetyLevel
`SafetyLevel` refers to the product context in which a user requests to view the Content, such as Timeline or Profile. The Visibility Filtering algorithm uses an essential concept to determine the appropriate remediation action to respond to potentially harmful content.

### SafetyLabel
`SafetyLabel` is a critical labeling mechanism used to identify potentially harmful content on Twitter. This mechanism allows the platform to associate a safety label with different entities, including tweets, users, direct messages, media, and spaces. Safety labels power various remediations such as applying a safety label that results in tweet interstitial or notice, reducing the visibility of harmful content, or providing a warning to users of potentially harmful content.

### SafetyLabelType
In the Visibility Filtering algorithm, `SafetyLabelType` refers to a specific policy violation associated with a particular noun instance. This violation leads to a reduction in the visibility of the labeled entity on product surfaces. Some safety label types have no effect on VF as they are deprecated or experimental. Additionally, some safety label types are not intended for use in VF and therefore, are not utilized.