Feature: Auto Process Functionalities

@BVT
Scenario Outline: AUVIKSC001 : Ticket Creation for Stack Switch Port Status Change

Given "ConditionName" : "<TestCase>" : I create ticketId using Juno Alerting ticket API and I verify the response
And I naviagte to ITS portal
And I login to ITS portal
And I naviagte to NOC portal
And I login to Noc portal

Examples:
|TestCase|
|AUVIKCR001|