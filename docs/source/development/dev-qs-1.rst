.. highlight:: kotlin
.. raw:: html

    <script type="text/javascript" src="../_static/jquery.js"></script>
    <script type="text/javascript" src="../_static/codesets.js"></script>

Making a state of things
========================

States are a central building block of Corda, they are structures for recording information, and individual state instances are stored on the nodes where CorDapps are run. State classes are defined in CorDapps running on the node. To define states is an important first step in your business model.

States extend **someclassname**, which contains the list of participants the state is relevant to, meaning all states are linked to parties involved in transactions.

Writing the state
-----------------

1. If you look at the skeleton CorDapp, you'll see the following structure:

  .. code-block:: kotlin

    folder
    - file or something whatever

  This is where the state definitions for your CorDapp are stored. Create a file called:  **doodad.state**

2. To define a state you must first include the ``import net.corda.core.identity.Party`` library.

  .. container:: codeset

    .. code-block:: kotlin

      import net.corda.core.identity.Party

      class IOUState(val value: Int,
                     val lender: Party,
                     val borrower: Party) : ContractState {
          override val participants get() = listOf(lender, borrower)
      }


  This state defines the borrowing and lending entities as Parties on the Corda network, and overrides participants to return a list of the two parties.

3. Save your new state definition. In most CorDapps, a state will need an associated contract that defines rules for the state as it changes over time. For the purposes of this quickstart we've included a basic contract to run with this state. You can find it in the **XXYYZZ.contract** file. If you'd like to learn more about contracts, see <writing contracts> or <cordapp architecture>.
