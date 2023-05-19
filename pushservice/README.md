# Pushservice

Pushservice is the main push recommendation service at Twitter used to generate recommendation-based notifications for users. It currently powers two functionalities:

- RefreshForPushHandler: This handler determines whether to send a recommendation push to a user based on their ID. It generates the best push recommendation item and coordinates with downstream services to deliver it
- SendHandler: This handler determines and manage whether send the push to users based on the given target user details and the provided push recommendation item

## Overview

### RefreshForPushHandler

RefreshForPushHandler follows these steps:

- Building Target and checking eligibility
    - Builds a target user object based on the given user ID
    - Performs target-level filterings to determine if the target is eligible for a recommendation push
- Fetch Candidates
    - Retrieves a list of potential candidates for the push by querying various candidate sources using the target
- Candidate Hydration
    - Hydrates the candidate details with batch calls to different downstream services
- Pre-rank Filtering, also called Light Filtering
    - Filters the hydrated candidates with lightweight RPC calls
- Rank
    - Perform feature hydration for candidates and target user
    - Performs light ranking on candidates
    - Performs heavy ranking on candidates
- Take Step, also called Heavy Filtering
    - Takes the top-ranked candidates one by one and applies heavy filtering until one candidate passes all filter steps
- Send
    - Calls the appropriate downstream service to deliver the eligible candidate as a push and in-app notification to the target user

### SendHandler

SendHandler follows these steps:

- Building Target
    - Builds a target user object based on the given user ID
- Candidate Hydration
    - Hydrates the candidate details with batch calls to different downstream services
- Feature Hydration
    - Perform feature hydration for candidates and target user
- Take Step, also called Heavy Filtering
    - Perform filterings and validation checking for the given candidate
- Send
    - Calls the appropriate downstream service to deliver the given candidate as a push and/or in-app notification to the target user