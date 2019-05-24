.. highlight:: kotlin
.. raw:: html

  <script type="text/javascript" src="../_static/jquery.js"></script>
  <script type="text/javascript" src="../_static/codesets.js"></script>


CorDapp Quickstart
==================

In this quickstart, you'll set up a CorDapp development environment and code a CorDapp based on a skeleton CorDapp structure.

.. toctree::
  :titlesonly:
  :hidden:
  :maxdepth: 1

  dev-qs-1
  dev-qs-2
  dev-qs-3
  dev-qs-4

Before you begin
----------------

To get set up for developing a CorDapp there are a few things you need to do:

* `Install IntelliJ <1>`_
* `Install Java 8 SDK <1>`_
* `Install Git <1>`_

Setting up your development project
-----------------------------------

After installing IntelliJ, the Java 8 JDK, and Git, you'll need to download the skeleton CorDapp and open it as a project in IntelliJ.

1. Download the skeleton CorDapp from the Corda samples repo by running this command: ``git clone https://github.com/corda/samples``
2. Open IntelliJ, select **Open** and open the ``cordapp-example`` project from the samples directory.
3. Click **File** > **Project Structure** > **Project SDK** > **New** > **JDK**. Select the install directory of your Java 8 SDK.
4. Click **File** > **Project Structure** > **Modules** > **+** > **Import Module**. Select the **cordapp-example** directory and **Open** it.
5. Select **Import module from external model**.
6. Select **Gradle**. Click **Next** > **Finish** > **OK**.

Gradle will now download the project dependencies and index the project.


.. try a one-page solution.


.. 1. dev-qs-1 ----- go over ONE PIECE of structure, and code it (e.g. THIS IS A WHERE THE STATE LIVES IN THE PROJECT AND WHAT IT IS, LET'S MAKE IT)
.. 2. dev-qs-2 ----- repeat last step until all code complete
.. 3. dev-qs-3 ----- running the cordapp, did it work? if not, here are some cheat sheets (properly formatted files) to work from
.. 4. dev-qs-4 ----- here's an offramp to more detailed best practices/node configs and operators info/api docs and reference info for devs
