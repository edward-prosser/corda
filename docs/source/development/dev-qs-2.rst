Going with the flow
===================

Having created states that can be modified by transactions, next you must create the transactions that will make changes. An important thing to note about the structure of Corda is that states **cannot be updated** states are archived, marked historic and new states replace them. You'll see what we mean shortly.

Corda includes library of subflows, subflows are predefined flows designed to be invoked as part of a larger transaction. To read more about the subflows available, go <here>.

~~Flow~~
--------

1. First off let's find where the flow should live in the structure. Create a new file in there called: **flibble.flow**

2. When building the flow, first make the following imports:

.. codeblock:: kotlin

  import net.corda.core.contracts.Command;
  import net.corda.core.identity.Party;
  import net.corda.core.transactions.SignedTransaction;
  import net.corda.core.transactions.TransactionBuilder;

These imports apply required Corda function. **info here please**.

3. Next, you'll need to write some transaction metadata as shown below:

.. codeblock:: kotlin

  @InitiatingFlow
  @StartableByRPC
  class IOUFlow(val iouValue: Int,
                val otherParty: Party) : FlowLogic<Unit>() {

The **@initiatingFlow** and **@StartableByRPC** tags do ...(something?)... and the class definition of the flow contains values relating to the state it references, while containing the reference to the **FlowLogic** Corda core class that your flow must extend.


4. After writing the metadata and class definition, the flow requires transaction logic. The transaction logic example below walks through the basic steps of a transaction: retrieving the notary, creating transaction components, **some more words**

.. codeblock:: kotlin

      /** The flow logic is encapsulated within the call() method. */
      @Suspendable
      override fun call() {
          // We retrieve the notary identity from the network map.
          val notary = serviceHub.networkMapCache.notaryIdentities[0]

          // We create the transaction components.
          val outputState = IOUState(iouValue, ourIdentity, otherParty)
          val command = Command(TemplateContract.Commands.Action(), ourIdentity.owningKey)

          // We create a transaction builder and add the components.
          val txBuilder = TransactionBuilder(notary = notary)
                  .addOutputState(outputState, TemplateContract.ID)
                  .addCommand(command)

          // We sign the transaction.
          val signedTx = serviceHub.signInitialTransaction(txBuilder)

          // Creating a session with the other party.
          val otherPartySession = initiateFlow(otherParty)

          // We finalise the transaction and then send it to the counterparty.
          subFlow(FinalityFlow(signedTx, otherPartySession))
      }
  }

5. Save your flow. In a real implementation flow tests can help with writing more complex flows. To get a better understanding of flows and flow tests see <some docs here>.
