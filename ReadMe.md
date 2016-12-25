# Scenario
We are concerned with build an auction sniper to help win items by having the highest bids at the auction house.

## Auctioning
Auctioning is handled by an auction house that sends events to interested bidders.  An event could be the price on an
item increasing or that the auction on a specific item has been closed and the winner and losers are notified.

Bidders can issue commands to the auction house.  A command could be issuing a higher bid on a specific item,
asking to join on a bidding for an item.

## Auction Sniper
An **auction sniper** watches online auctions and automatically bids slightly higher whenever the price changes, until it
reaches a sto-price or the auction closes.

## Terminologies
- **Item**: something that can be identified and bought.
- **Bidder**: a person or organization that is interested in buying an item.
- **Bid**: a statement that a bidder will pay a given price for an item.
- **Current price**: the current highest bid for the item.
- **Stop price**: the most a bidder is prepared to pay for an item.
- **Auction**: a process for managing bids for an item.
- **Auction house**: an institution that hosts auctions.

# The Setup
The auction sniper will be a Swing application.  It communicates with the auction house through XMPP messages.

# Development Steps
To build the application, we'll be taking the TDD approach of building out incremental features in the framework of
have a continuous end-to-end application.  The other approach would be identifying the individual components of the
application, separately develop each component, and then integrate them together.  While the component way of
development works and would produce testable components (if done via TDD), it leaves the issue of component integration
as a separate concern rather than within the conversation of a full feature increment.  Additionally, the end-to-end
TDD style exposes integration pain points earlier in the development cycle, and, once they are addressed, the
automatic testing would insure that the end-to-end features will continue to work once they are implemented.

## Walking Skeleton
A walking skeleton is the simplest application that implements a simple specific feature.  Implementing the walking
skeleton as the first step is to address integration concerns at the boundaries between code that we own and services
that we use.  This step involves a lot of service exploration and scaffolding.  However, regardless of how you approach
developing the application, this is step is _unavoidable_ if you want to achieve automated testing.

In the scope of this project, the walking skeleton is an auction sniper that can:

- join an auction for a specific item
- loses the bid

To test the walking skeleton, we'll be using WindowLicker / JUnit for testing the GUI and OpenFire as the XMPP server
along with its Smack library:

- [WindowLicker]: WindowLicker r268
- [OpenFire]: OpenFire 4.1.0
- [Smack]: Smack/x 3.2.1
- [JUnit]: JUnit 5

Therefore, the walking skeleton should flush out:

- How to initialize the application through JUnit
- How to test the GUI components using WindowLicker
- How to issue and receive messages using Smack
- How to connect to the XMPP server
- What kind of infrastructure design is necessary to handle these activities.  The infrastructure may change, of course,
as we attempt to add newer features.