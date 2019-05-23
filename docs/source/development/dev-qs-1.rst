Making a state of things
========================

States are a building block of Corda. States are the things that get stored on nodes where your CorDapp will run, they're structures that are modified by transactions. Defining states is an important first step in your business model. States extent **someclassname**, which contains the list of participants the state is relevant to. Kinda self-referential.

Writing the state
-----------------

1. If you look at the skeleton CorDapp, you'll see the following structure:

.. code-block:: java

  folder
    - file or something whatever

This is where states live. States are thing you want to save and be modified by doing stuff on your blockchain things. Word. Create a file called:  **doodad.state**

2. Ok so you want states to be pretty simple probably I imagine so maybe try writing your state kinda like this:**except not this because IOU sucks**

.. code-block:: kotlin

  import net.corda.core.identity.Party

  class IOUState(val value: Int,
                 val lender: Party,
                 val borrower: Party) : ContractState {
      override val participants get() = listOf(lender, borrower)
  }

This state defines the borrowing and lending entities as Parties on the Corda network, and overrides participants to return a list of the two parties.

3. Save your new state. In most CorDapps, a state will need an associated contract that defines rules for the state as it changes over time. For the purposes of this quickstart we can make do without a contract, but if you'd like to learn more, see <writing contracts> or <cordapp architecture>.
