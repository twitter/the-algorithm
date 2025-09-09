# Signals for Candidate Sources

## Overview

The candidate sourcing stage within the Twitter Recommendation algorithm serves to significantly narrow down the item size from approximately 1 billion to just a few thousand. This process utilizes Twitter user behavior as the primary input for the algorithm. This document comprehensively enumerates all the signals during the candidate sourcing phase.

| Signals               |  Description                                                          |
| :-------------------- | :-------------------------------------------------------------------- |
| Author Follow         | The accounts which user explicit follows.                             |
| Author Unfollow       | The accounts which user recently unfollows.                           |
| Author Mute           | The accounts which user have muted.                                   |
| Author Block          | The accounts which user have blocked                                  |
| Tweet Favorite        | The tweets which user clicked the like botton.                        | 
| Tweet Unfavorite      | The tweets which user clicked the unlike botton.                      |       
| Retweet               | The tweets which user retweeted                                       |
| Quote Tweet           | The tweets which user retweeted with comments.                        |
| Tweet Reply           | The tweets which user replied.                                        |
| Tweet Share           | The tweets which user clicked the share botton.                       |
| Tweet Bookmark        | The tweets which user clicked the bookmark botton.                    |
| Tweet Click           | The tweets which user clicked and viewed the tweet detail page.       |
| Tweet Video Watch     | The video tweets which user watched certain seconds or percentage.    |
| Tweet Don't like      | The tweets which user clicked "Not interested in this tweet" botton.  |
| Tweet Report          | The tweets which user clicked "Report Tweet" botton.                  |
| Notification Open     | The push notification tweets which user opened.                       |
| Ntab click            | The tweets which user click on the Notifications page.                |               
| User AddressBook      | The author accounts identifiers of the user's addressbook.            | 

## Usage Details

Twitter uses these user signals as training labels and/or ML features in the each candidate sourcing algorithms. The following tables shows how they are used in the each components.

| Signals               | USS                | SimClusters        |  TwHin             |   UTEG             | FRS                |  Light Ranking     |
| :-------------------- | :----------------- | :----------------- | :----------------- | :----------------- | :----------------- | :----------------- | 
| Author Follow         | Features           | Features / Labels  | Features / Labels  | Features           | Features / Labels  | N/A                |
| Author Unfollow       | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Author Mute           | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| Author Block          | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| Tweet Favorite        | Features           | Features           | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| Tweet Unfavorite      | Features           | Features           | N/A                | N/A                | N/A                | N/A                |       
| Retweet               | Features           | N/A                | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| Quote Tweet           | Features           | N/A                | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| Tweet Reply           | Features           | N/A                | Features           | Features           | Features / Labels  | Features           |
| Tweet Share           | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| Tweet Bookmark        | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Tweet Click           | Features           | N/A                | N/A                | N/A                | Features           | Labels             |
| Tweet Video Watch     | Features           | Features           | N/A                | N/A                | N/A                | Labels             |
| Tweet Don't like      | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Tweet Report          | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Notification Open     | Features           | Features           | Features           | N/A                | Features           | N/A                |                       
| Ntab click            | Features           | Features           | Features           | N/A                | Features           | N/A                |
| User AddressBook      | N/A                | N/A                | N/A                | N/A                | Features           | N/A                |