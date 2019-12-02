# Middleware
This is the repository for the middleware/Sync Module which will interact with the server. The goal of this module is to,
(1) Set up offline syncing between mobile clients and the back-end server so that health workers can collect data in offline mode and sync with the server when the internet connection is re-established. It also tracks failures in data upload during poor or jittery network connections or high latency connections to minimize data loss.
(2) Patient record syncing between different devices registered to the same clinic location so that all patients are shared between health workers working at a clinic
(3) Allow for a camp like setting where registration/intake is done by one health worker and the subsequent actions are performed by other health workers through patient record syncing (requires a consistent internet connection)
(4) Allows for health workers to be logged in on multiple devices.

The behaviour of the module is similar to Gmail, which is continuously syncing with back end servers to send and receive emails. By default the module is set to sync mobile clients every 15 minutes (this can be modified) and during specific user initiated actions. 
A complete description of the architecture and tech stack is available on the Intelehealth Wiki: https://intelehealthwiki.atlassian.net/wiki/spaces/INTELEHEAL/pages/13697089/Middleware+Sync+Module.

For a full description of the Intelehealth platform please refer to: http://wiki.intelehealth.io/

In case of any questions or difficulties you can reach out to us through our Slack channel:
https://join.slack.com/t/intelehealthcommunity/shared_invite/enQtODQ1MzE4NDg2NDk5LWY2NThjN2YwNWY3YzA2MTcyNWJhYjg1NzEyZmNkMDFkNDNhMjlkYWE3OTZhYzJkMzE4MjA5MjE5N2Y4MzMxY2Q.
