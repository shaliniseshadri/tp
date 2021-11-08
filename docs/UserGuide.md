---
layout: page
title: User Guide
---
Welcome to **GameBook**!

**GameBook** is a desktop app designed for you to track your gambling performance.
With simple commands, you will be able to add your gambling sessions into **GameBook**
and view instant analysis of your gambling records.

If you enjoy casual gambling sessions with your friends and family or love going to the casinos, do try out **GameBook**!

**Table of Contents**

1. [Quick Start](#quick-start)

1. [Terminology](#terminology)
    1. [Syntax and Symbols used in the User Guide](#syntax-and-symbols-used-in-the-user-guide)
    1. [Technical Terms](#technical-terms)
    1. [GameEntry Fields](#gameentry-fields)

1. [Features](#features)
    1. [Adding a game entry](#adding-a-game-entry-add)
    1. [Listing all game entries](#listing-all-game-entries--list)
    1. [Editing a game entry](#editing-a-game-entry--edit)
    1. [Deleting a game entry](#deleting-a-game-delete)
    1. [Finding game entries](#finding-game-entries-find)
    1. [Clearing all game entries](#clearing-all-data-clear)
    1. [Getting Help](#getting-help--help)
    1. [Exiting the program](#exiting-the-program--exit)
    1. [Notes about the flags](#notes-about-flags)
    1. [Saving the data](#saving-the-data)
    1. [Editing the data file](#editing-the-data-file)
    1. [Archiving data files](#archiving-data-files-coming-in-v20)

1. [FAQ](#faq)

1. [Command Summary](#command-summary)
--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure that you have Java 11 or above installed in your computer.
  * To check if you have Java 11 or above installed, open command prompt (search "cmd" on your computer) and type `java -version`.
    The command prompt will tell you if you have Java and which version you have. Proceed to step 2 if you have Java 11 or above installed.
  * If you do not have Java 11 or above installed, please visit the [Java installation guide](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)
    and install Java 11 or above.
2. Download the latest `gamebook.jar` from [here](https://github.com/AY2122S1-CS2103T-W13-3/tp/releases).

3. Copy the JAR file to the folder you want to use as the home folder for **GameBook**. Your gambling records will later be saved in your home folder.

4. Double-click the JAR file to start the app. The GUI similar to below should appear in a few seconds.
   <br>
   ![GUI](images/Ui.png)

5. Type the command in the command box and press “Enter” on your keyboard to execute it. <br>
   Here are some example commands that you can try:
   * **`add /g Poker /s 500 /e 650 /date 2021-06-21 /dur 142 /loc Sentosa Casino`**: <br>
     Adds an entry of Poker where you started with $500 and ended with $650 (played on 20th June 2021 for 142min at 
     Sentosa Casino) into the **GameBook**.
   * **`delete 2`** : Deletes the 2nd entry shown in the current list

  * **`help`** : Shows a list of commands available to use in **GameBook**.

6. Refer to the [Features](#features) section for more details of each command.

--------------------------------------------------------------------------------------------------------------------
## Terminology

### Syntax and Symbols used in the User Guide:

Format | Description
--------|------------------
**`command`** | Used to denote an input command
**:information_source:** | Used to signify important information/notes to the user
**:bulb:** | Used to indicate tips to the user
**:warning:** | Used to warn the user before proceeding with a potentially risky action


### Technical Terms:

Term | Description
--------|------------------
**parameter** | A parameter is a piece of information that the user needs to supply in a command.
**JSON** | Abbreviation of JavaScript Object Notation. Format in which the user's data is stored.


### GameEntry Fields:

Parameter | Description
--------|------------------
**GAME_TYPE** | Refers to the type of the game you wish to record. Eg: Poker, Roulette, Blackjack, etc.
**INITIAL_CASH** | The amount of cash you have at the beginning of a game, up to 2 decimal places. Value should be between -1,000,000,000.00 and 1,000,000,000.00
**FINAL_CASH** | The amount of cash you have at the end of a game, up to 2 decimal places. Value should be between -1,000,000,000.00 and 1,000,000,000.00
**PROFIT** | The overall gain/loss from the game. Effectively, the difference between `FINAL_CASH` and `INITIAL_CASH`, up to 2 decimal places.
**DATE** | The date on which the game was played. Date should be in `yyyy-MM-dd` or `yyyy-MM-dd HH:mm` format. If `DATE` is not specified, it will be taken to be the current time of input.
**DURATION** | The amount of time for which the game was played. Duration should be in `INTh:mm`, `INTh INTm`, `INTh` or `INTm` or `INT` format. <br> <br> Eg: <br> `1:30`, `1h 30m` represents 1 hour 30 minutes <br> `1h`, `60m`, `60` represents 1 hour
**LOCATION** | The place where the game was played
**TAG** | A single word (or dash-separated word) attribute assigned to the game which can be used to categorize the game. <br> Eg: `birthday`, `very-lucky`, etc.

<div markdown="span" class="alert alert-warning">:warning: **Alert:**
Numbers longer than 13 digits (in decimal representation) may be rounded or slightly inaccurate.
</div>

--------------------------------------------------------------------------------------------------------------------


## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format and input:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>

  e.g. In `add /g GAME_TYPE /s INITIAL_CASH /e FINAL_CASH`, GAME_TYPE, INTIIAL_CASH, and FINAL_CASH are
  parameters the user needs to supply. An example is `add /g poker /s 0.01 /e 1.02`.
  <br> <br>

* Items in square brackets are optional.<br>

  e.g. In `add /g GAME_TYPE /p PROFIT [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]`, DATE, DURATION,
  LOCATION and TAGS are optional fields. `add /g poker /p 10.40` and `add /g poker /p 10.40 /date 2021-09-11 21:20 /dur 40` are both deemed
  as correct usages.
  <br> <br>
* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will be ignored.
  e.g. if the command specifies `list 123`, it will be interpreted as `list`
</div>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the displayed list:**<br>

* Displayed list of game entries is implicitly sorted by date.
  * Game entries with later dates are displayed above game entries with earlier dates.
  * If the DATE specified does not contain time, it will be regarded as 00:00 when sorting.
</div>

### Adding a game entry: `add`

Adds a game entry to **GameBook**.<br>

Parameters:<br>
`GAME_TYPE`, `[INITIAL_CASH]`, `[FINAL_CASH]`, `[PROFIT]`, `[DATE]`, `[DURATION]`, `[LOCATION]`, `[TAGS]` <br><br>
Format:<br>
`add /g GAME_TYPE [/s INITIAL_CASH] [/e FINAL_CASH] [/p PROFIT] [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]` <br>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You must specify the amount of money you won or lost in the game. You can choose to input the INITIAL_CASH with the FINAL_CASH or only input the PROFIT. <br>
</div>

* To simplify typing, for GAME_TYPE and LOCATION, the input will be automatically converted such that the first
  character of each word is upper-case and subsequent characters are lower-case. For example, "poker" and "genting casino"
  will be stored within **GameBook** as "Poker" and "Genting Casino" respectively.
* A game entry can have any number of TAGS (including 0). To add multiple tags, follow the format of `/tag TAG_1, TAG_2, TAG_3, ...`.
  * eg. `/tag drunk,lucky`
* Please refer to [GameEntry Fields](#gameentry-Fields) for specific notes on the formats of arguments.



Examples:
* `add /g blackjack /s 12.34 /e -56.78 /date 2021-09-13 /dur 1:23 /loc Marina Bay Sands`<br>
Adds an entry of blackjack where you started with $12.34 and ended with -$56.78 (played on 13th Sept. 2021 for 1hr 23 min
at Marina Bay Sands) to **GameBook**.
* `add /g poker /s 0.01 /e 1.02 /date 2021-09-11 21:20 /dur 3:14 /loc Home`<br>
Adds an entry of poker where you started with $0.01 and ended with $1.02 (played on 11th Sept. 2021 21:20  for 3hr 14 min
at Home) to **GameBook**.
* `add /g poker /p 0.2 /tag run-good`<br>
  Adds an entry of poker where you gained a profit of $0.20 to **GameBook** and tags the entry as "run-good".


<div markdown="block" class="alert alert-info">

**:information_source: Note about alerts:**<br>

* If the specified DATE is in the future, an alert will be shown.
* If an existing entry already has the same GAME_NAME and DATE, an alert will be shown.
  * Two DATEs are the same if they fall on the exact same time (if time is specified for both), or if they fall on the same
  day (if time is not specified for both). 
    * Eg of DATEs regarded as same: `2020-01-01` and `2020-01-01`; `2020-01-01 10:15` and `2020-01-01 10:15`.
    * Eg of DATEs regarded as different: `2020-01-01` and `2020-01-05`; `2020-01-01` and `2020-01-01 07:30`
</div>



### Listing all game entries : `list`

Shows a list of all game entries in **GameBook**.<br>

Format:<br>
`list`


![GUI](images/ListCommand.png)

### Editing a game entry : `edit`

Edits an existing game entry in **GameBook**. <br>

Parameters:<br>
`INDEX`, `[GAME_TYPE]`, `[PROFIT]`, `[DATE]`, `[DURATION]`, `[LOCATION]`, `[TAGS]`<br><br>
Format:<br>
`edit INDEX [/g GAME_TYPE] [/p PROFIT] [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]`

<div markdown="span" class="alert alert-warning">:exclamation: **:Caution:**
You are not allowed to use edit start and end amounts. That is, do not use `/s` and `/e`. Only use profit, `/p`.
</div>

* Edits the game record at the specified `INDEX`. `INDEX` refers to the index of the game within the displayed game list, which
  **must be a positive integer** 1, 2, 3, …​
* **At least one** of the optional fields must be provided.
* Only selected properties of the game record will be edited, all other properties will remain unchanged.
* Edited tags will replace existing tags completely.
* If the selected property was initially empty, it would be updated to be the value the user gave in the flag.
* Updated values will be reflected in the file saved to the disk.
* Please refer to [GameEntry Fields](#gameentry-Fields) for specific notes on the formats of arguments.

Examples:
*  `edit 1 /g roulette /p 1`<br>Changes the type of the 1st game in the list to roulette and the profit to $1.
*  `edit 3 /p 1 /loc John’s house`<br>Changes the location where the 3rd game in the list was played to “John’s house”,
   regardless of whether the initial location was empty or not.

<div markdown="block" class="alert alert-info">

**:information_source: Note about alerts:**<br>

* Alerts are in place to detect if the edited date is in future, or if the edited entry has same GAME_NAME and DATE as an existing entry.
  Refer to the bottom section of "Adding a game entry" for more details.
</div>

### Deleting a game: `delete`

Deletes the game at the specified index.<br>

Parameter: <br>
`INDEX`

Format:<br>
`delete INDEX`

* Deletes the game record at the specified `INDEX`. `INDEX` refers to the index of the game in the displayed game list, which
  **must be a positive integer** 1, 2, 3, …​
* Selected game will also be deleted from the file in the disk.
* Indices of all remaining tasks will be updated.
  * Suppose game record `3` has been deleted, then all game records with `INDEX > 3` will be decremented by 1.

Examples:
* `delete 2`
  <br>deletes the 2nd game in the list.

### Finding game entries: `find`
Lists all the game entries that contain any of the specified keywords.

Parameter:
`KEYWORDS`

Format:<br>
`find KEYWORDS` <br>

* You can specify one or more keywords. 
* If multiple keywords are specified, each keyword must be separated by a whitespace. <br> 

Examples:
* `find poker`<br>shows a list of game entries that contains the keyword "poker" (keyword may be found in the game entry's TAGS, LOCATION, or GAME_TYPE)

![GUI](images/FindCommand.png)
### Clearing all data: `clear`
Clears all game entries.

Format:<br>
`clear`

* After `clear` is entered as a command, a popup window that looks like the following will appear.<br>
  ![Clear Command Image](images/ClearConfirmationWindow.png)<br>
* To confirm clearing all game entries from **GameBook**, you must click on the button [Clear]. Once you click on [Clear],
  all your game entries will be removed.
* If you decided
  not to clear your data, please click on the [X] to close the window.

### Getting help : `help`
Shows a list of commands available or the format of a specific command.

Format:<br>
`help` - shows a list of commands available.<br>
`help add` - shows the format of the command to add a game entry.<br>
`help delete` - shows the format of the command to delete a game entry.<br>
`help edit` - shows the format of the command to edit a game entry.<br>
`help find` - shows the format of the command to find specific game entries. <br>
### Exiting the program : `exit`

Exits the program.

Format:<br> `exit`

### Notes about flags

* the `[/dur DURATION]` flag accepts multiple input formats
  *  `... /dur 123` indicates a duration of 123 minutes.
  *  `... /dur 1:23` indicates a duration of 1 hour, 23 minutes.
  *  `... /dur 1h` indicates a duration of 1 hour.
  *  `... /dur 1h 23m` indicates a duration of 1 hour, 23 minutes.
  *  `... /dur 23m` indicates a duration of 23 minutes.

* Different tags should be separated by `, `.
  *  `... /tag some-tag` tags a game with `some-tag`.
  *  `... /tag some-tag, some-other-tag` tags a game with `some-tag` and `some-other-tag`.
  
* Empty flags are not allowed.
  * All tags need to have a following argument.
  * `... /date /tag` will result in an error message.

### Saving the data

**GameBook** data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

**GameBook** data are saved as a JSON file `[JAR file location]/data/gamebook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, GameBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer (refer to Quick Start) and replace the empty gamebook.json file it creates within the data folder with your `gamebook.json` file in your original computer.

**Q**: Can I edit the data by directly modifying the data file?<br>
**A**: Technically, you can if you follow the exact storage format. However, we strongly advise against it as any
mistakes will cause errors in the app.

**Q**: (Follow up from previous question) What is the storage format of the data file?<br>
**A**: Data is stored in JSON format, with each entry being stored as a JSON object with the keys being `gameEntries`, `startAmount`, `endAmount`, `date`, `durationMinutes`, `location` and `tagged`.
The exact format can be explored by checking out the format of the sample data that GameBook is initialized with. The data file is stored at `/data/gamebook.json`, and the
`data` directory is located in the same directory where you placed the JAR file at.


--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | Format 1:<br>`add /g GAME_TYPE /s INITIAL_CASH /e FINAL_CASH [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]` <br> <br> e.g., <br> `add /g blackjack /s 12.34 /e -56.78 /date 2021-09-13 /dur 1:23 /loc Marina Bay Sands /tag loose,run-good` <br><br>Format 2:<br> `add /g GAME_TYPE /p PROFIT [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]` <br> <br> e.g.,<br> `add /g poker /p 200`
**List** | `list`
**Delete** | `delete INDEX`<br> <br> e.g., `delete 1`
**Edit** | `edit INDEX [/g GAME_TYPE] [/p PROFIT] [/date DATE] [/dur DURATION] [/loc LOCATION] [/tag TAGS]` <br> <br> e.g., <br>`edit 1 /g roulette /p 20` <br> `edit 3  /loc John’s house`
**Find** | `find KEYWORDS`<br><br> e.g., `find tag1 tag2`
**Clear** | `clear`
**Help** | `help`<br> `help add` `help delete` `help edit` `help find`
**Exit** | `exit`
