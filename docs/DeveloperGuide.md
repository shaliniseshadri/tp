---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `GameEntryListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `GameEntry` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-W13-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="600"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `GameBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a game entry).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `GameBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `GameBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the game book data i.e., all `GameEntry` objects (which are contained in a `UniqueGameEntryList` object).
* stores the currently 'selected' `GameEntry` objects (e.g., results of a search query) as a separate _filtered_ list 
  which is exposed to outsiders as an unmodifiable `ObservableList<GameEntry>` that can be 'observed' e.g. the UI can 
  be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they 
  should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component helps save Game Book data and User Preferences to a json file after every use, and read them from a json file while restarting the app. 
* The `Storage` component also plays a key role in supporting other functions such as the analysis of average profits. 
* It inherits from both `GameBookStorage` and `UserPrefsStorage`, which means it can be treated as either one.
* It depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature
The below provides a step-by-step break down of the mechanism for adding a game entry. Assume that the user has already
launched `GameBook` and the app has loaded data from storage.
1. The user inputs `add /g Poker /s 50 /e 85 /dur 40m /loc Resort World Sentosa Casino /dur 50m /date 21/10/2021 15:10`
which calls upon `LogicManager#execute()`.
2. `GameBookParser` parses the command and returns an `AddCommand`.
3. `AddCommand` is executed.

### Edit feature
Editing a game entry requires user input from the CLI. The `GameBook` parser will check the validity of the input. It
is valid if
* The list of games currently displayed is not empty, and the chosen index is a valid index.
* At least one field is chosen to be edited.
* The formats of all fields entered, such as game type, start amount, end amount, location etc must be in the correct format.

The below provides a step-by-step break down of the mechanism for adding a game entry. Assume that the user has already
launched `GameBook` and the app has loaded data from storage.
* Step 1: The user inputs a command, such as `add /g Poker /s 50 /e 85 /dur 40m /loc Resort World Sentosa Casino 
/dur 50m /date 21/10/2021 15:10` which calls upon `LogicManager#execute()`
* Step 2: `GameBookParser` and `AddCommandParser` parses the command. If it is valid, a new `GameEntry` object is created,
followed by an `AddCommand` object containing the `GameEntry`.
* Step 3: `LogicManager#execute()` calls upon `AddCommand#execute()`. Within `AddCommand#execute()`, `ModelManager#addGameEntry()`
is called, which in turn calls `GameBook#addGameEntry()`. This then calls `GameEntryList#add()`, which adds the new game
entry to a `List` and sorts it by date.
* Step 4: `AddCommand#execute()` then encapsulates the result of the command execution in a new `CommandResult` object 
to its caller. The caller, we recall from Step 3, is `LogicManager#execute()`.
* Step 5: To update the storage list, `LogicManager#execute()` then calls `StorageManager#saveGameBook(ReadOnlyGameBook)`,
which then calls its overloaded method `StorageManager#saveGameBook(ReadOnlyGameBook, Path)`, which calls
`JsonGameBookStorage#saveGameBook(ReadOnlyGameBook, Path)`
* Step 6: Abstracting away the remaining storage details, the new list of game entries is saved in local storage.
* Step 7: The updated list is reflected in GUI, together with feedback to the user retrieved from the `CommandResult`
objet from Step 4.
TODO: Check whether LogicManager#execute() require argument type + how much details are necessary]
The following sequence diagram shows how the `add` operation works:
[TODO]
The following activity diagram summarizes what happens when a user executes the `add` command.
[TODO]


Assume that the user has already launched `GameBook` and the app has loaded data from storage. Assume also that the 
current game entry list is not empty, and contains the following game entries.
1. `{Game type: Poker, Start amount: 12.34, End amount: 56.78, Duration: NIL, Date played: 22/10/21 23:59, Location: Sentosa, Tags: [smoking, late-night, drunk]}`
2. `{Game type: Roulette, Start amount: 12.34, End amount: 65.87, Duration: 120, Date played: 22/10/21, Location: Sentosa, Tags: [smoking, late-night, drunk]}`
3. `{Game type: Poker, Start amount: 12.34, End amount: 56.78, Duration: NIL, Date played: 22/09/21, Location: John's house, Tags: [friends]}`
4. `{Game type: Blackjack, Start amount: 12.34, End amount: 56.78, Duration: 25, Date played: 22/10/21 22:00, Location: Sentosa, Tags: [late-night, drunk]}`

The below provides a step-by-step break down of the mechanism for adding a game entry.
1. The user inputs `edit 1 /g Mahjong` which calls upon which calls upon `MainWindow#executeCommand()`.
2. `MainWindow#executeCommand()` passes the user's input to `LogicManager#execute()` to process.
3. `LogicManager#execute()` calls `GameBookParser#parse()` to parse the input.
4. `GameBookParser#parse()` parses the input and returns a `EditCommand`.
5. `GameBookParser` parses the command and returns an `EditCommand`.
6. `LogicManger#execute()` executes `EditCommand` by calling `EditCommand#execute()`.
7. `LogicManager#execute()` selects the `GameEntry` to be edited, creates an edited copy of it, and calls `ModelManager#setGameEntry()`
   to replace the original `GameEntry` with the edited one. It then returns a `CommandResult` to `LogicManager#execute()`.
8. `LogicManager#execute()` calls `Storage` to store the new game entry list and returns `CommandResult` to `MainWindow#executeCommand()`.
9. `MainWindow#executeCommand()` executes `resultDisplay#setFeedbackToUser()` to display the message from `CommandResult` to the user.

### Deleting a Game Entry
Deleting a game entry requires user input from the CLI. The user should obtain the index of the game entry to be deleted from `GameEntryListPanel`, which will show a list of game entries previously added by the user. The format of input should be `delete [INDEX]`. `GameBookParser` will check for the validity of the input. It
is valid if
* The index specified by the user is bigger than 0 and smaller or equal to the number of game entries in the list. 

The below provides a step-by-step break down of the mechanism for deleting a game entry. Assume that the user has already
launched `GameBook` and the app has loaded data from storage. Assume also that the current game entry list contains more than 1 game entry.
1. The user inputs `delete 1` which calls upon `MainWindow#executeCommand()`. 
2. `MainWindow#executeCommand()` passes the user's input to `LogicManager#execute()` to process.
3. `LogicManager#execute()` calls `GameBookParser#parse()` to parse the input.
4. `GameBookParser#parse()` parses the input and returns a `DeleteCommand`.
5. `LogicManger#execute()` executes `DeleteCommand` by calling `DeleteCommand#execute()`.
6. `DeleteCommand#execute()` calls `ModelManager#deleteGameEntry()` to delete the game entry from the game entry 
  list and returns a `CommandResult`to `LogicManager#execute()`.
7. `LogicManager#execute()` calls `Storage` to store the new game entry list and returns `CommandResult` to `MainWindow#executeCommand()`.
8. `MainWindow#executeCommand()` executes `resultDisplay#setFeedbackToUser()` to display the message from `CommandResult` to the user.

### Graphical Analysis of Average Profits by Date 

#### Proposed Implementation

The graphical feature is facilitated by the `GraphPanel` and `StatsByDate` classes along with the `MainWindow` class. 
It is implemented using the JavaFX `LineChart` and `XYSeries` Classes.

`GraphPanel` currently supports three methods:
* `drawGraph()` - gets the HashMap with the dates and average profits, sorts them, and adds them to the series and 
  the line chart
* `updateList()` - reassigns the value of the new GameEntry list to the current GameEntry list
* `clearList()` - clears the existing series from the line chart

In addition, the following method from StatsByDate is also used:
* `StatsByDate#getStats()` - returns a HashMap with the dates and average profits

#### Mechanism:
* A `GraphPanel` object is created and initialised in the main window using the filtered list from `Storage`
 `drawGraph()` is called on the graph panel to draw the graph based on existing entries as the user starts the app.
* When the user enters a command, `executeCommand(String commandText)` in MainWindow is run during which 
  `clearList()` is called on the graphPanel object
  to clear the existing series after which the command is executed.
* Before returning the result, `updateList()` is called on the graphPanel object to update the value of the 
  modified list of game entries.
* This results in a new series being created with `StatsByDate#getStats()`, when it is called on the updated list 
  value to generate a new graph.
* These steps repeat for every command entered by the user until the user exits the app.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedGameBook`. It extends `GameBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedGameBook#commit()` — Saves the current address book state in its history.
* `VersionedGameBook#undo()` — Restores the previous address book state from its history.
* `VersionedGameBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitGameBook()`, `Model#undoGameBook()` and `Model#redoGameBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedGameBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitGameBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitGameBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitGameBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoGameBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial GameBook state, then there are no previous GameBook states to restore. The `undo` command uses `Model#canUndoGameBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoGameBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone GameBook states to restore. The `redo` command uses `Model#canRedoGameBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitGameBook()`, `Model#undoGameBook()` or `Model#redoGameBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitGameBook()`. Since the `currentStatePointer` is not pointing at the end of the `gameBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to track earnings/losses across various casino games
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage gambling earnings/losses faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | add a new game entry         |                  |
| `* * *`  | new user                                   | delete my game entries         | clear entries with erroneous data                 |
| `* * *`  | new user                                   | log my gambling statistics on a per game basis    | keep track of my spending                 |
| `* * *`  | new user                                   | input any type of game         | include any game I want instead of choosing from preset list of games                 |
| `* * *`  | new user                                   | save my net earnings and losses         | view data from my previous games                 |
| `* * *`  | forgetful user                             | edit my previous game entries         | add details I missed out on previously                 |
| `* *`    | user who frequents multiple gambling locations   | input location I played at               | organize and sort my data by location                                                                       |
| `* *`    | user who does not like scrolling                | find game entries using relevant keywords                | view my data quickly                                  |
| `* *`    | expert user                | see analysis of my game statistics               | evaluate my game performance                                   |
| `* *`    | expert user who gambles at different locations                | see analysis of my performance at different locations                | discern the strength of players there                                   |
| `* *`    | detail-oriented user                | add notes to certain game entries                | read them when reviewing my games                                   |
| `* *`    | user who vlogs                | View an aesthetically pleasant UI                | show it to my audience in my videos                                   |
| `* *`    | user who has tracked my previous gambling history                | see which games I have the highest win ratios in                | hone my skills in more profitable games and avoid games I am weaker at                                   |
| `* *`    | user who is easily affected be emotions                | tag games which I made emotional decisions in                | understand how it has affected my earnings                                   |
| `* *`    | user who gambles frequently                | see the statistics on my expenditure                | justify my gambling habits to my family, that it is not an addiction                              |
| `*`      | user who gambles against friends frequently | track how good my friends are at gambling           | avoid betting large amounts when playing against stronger friends                                                 |
| `*`      | user who switches between computers frequently | backup my data securely           | easily create copies of it to other computers                                                 |
| `*`      | user whose hard drive is almost full | specify where the app data is stored           |                                                  |
| `*`      | user who is social gambler | add my gambling friends to my contacts           | share my statistics with them                                                 |
| `*`      | busy user | see how much time I spend on each game           | utilize my time better                                                 |
| `*`      | busy user | see the profit per unit time analysis of different games           | decide which game to play to maximize rate of earnings                                                 |
| `*`      | expert user | see the statistics of games with specific tags           | evaluate my performance on games with the selected tags                                                 |
| `*`      | expert user | compare statistics across different days of the week           | evaluate my performance on different days                                                 |
| `*`      | thrillseeking user | see mean and variance calculations for different games           | choose the one with the highest variance to have fun                                                 |
| `*`      | user who frequents places with an entry fee | add costs such as entry-fee into the overall calculation           | get a more accurate view of my profits                                                 |
| `*`      | organized user | save different filters or sort conditions           | quickly view custom selections that are important to me                                                 |
| `*`      | user who gambles with my friend | keep track of who I owed money to           | pay them back all at once                                                 |
| `*`      | user who loves alcohol | tags games that I played when I am drinking           | see how alcohols affect my performance                                                |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `GameBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add an entry**

**MSS**

1. User enters a new entry
2. GameBook adds in the entry

    Use case ends.

**Extensions**

* 1a. User entered the entry in an incorrect format.
    * 1a1. GameBook shows an error message.
  
      Use case resumes at step 1.

**Use case: Delete an entry**

**MSS**

1.  User requests to list entries
2.  GameBook shows a list of entries
3.  User requests to delete a specific entry in the list
4.  GameBook deletes the entry

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. GameBook shows an error message.

      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 entries without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. A novice should be able to grasp the basic functionalities of the system without too much difficulty.
5. A user should be able to easily back up data.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a game entry

1. Deleting a game entry while all game entries are being shown

   1. Prerequisites: The list of game entries is shown by default, or the `list` command is used to list all game entries. 

   1. Test case: `delete 1`<br>
      Expected: First game entry is deleted from the list. Details of the deleted contact shown in the status message. 

   1. Test case: `delete 0`<br>
      Expected: No game entry is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

_{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
