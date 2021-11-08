---
layout: page
title: Benjamin's Project Portfolio Page
---

### Project: Game Book

GameBook is a desktop app designed for gamblers to track their gambling games. It is optimized for use via a Command
Line Interface (CLI), but still possesses the benefit of a Graphical User Interface (GUI).

Given below are my contributions to the project.
* **New Feature**: Added functionality to sort the game entries after `add` or `edit`
  * What it does: Automatically sorts the list of game entries by date, with the latest dates appearing at the top.
  * Justification: This feature is very important to the user as it places the most recent games (which users would be
    more interested in) at the top. It also provides a sense of ordering to the app.
  * Highlights: Implementing this feature required an in-depth understanding of the execution pipeline for adding and
    editing game entries, as well as the internal representation of the game entries list in order to figure out the 
    ideal place to implement sorting.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=benjaminlhr&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&tabAuthor=BenjaminLHR&tabRepo=AY2122S1-CS2103T-W13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&zA=BenjaminLHR&zR=AY2122S1-CS2103T-W13-3%2Ftp%5Bmaster%5D&zACS=220.43386537126995&zS=2021-09-17&zFS=&zU=2021-11-04&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)

* **Modifications to existing features**:
  * Refactored Ui components and MainApp (renaming variables and classes and calling the correct new classes/methods) 
    to suit the purpose of GameBook in v1.2 (Pull request [\#47](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/47))
  * Refactored "test/data" files to suit the purpose of GameBook in v1.2 (Pull request [\#71](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/71))
  * Modify behaviour of duplicate entries handling. Instead of throwing an exception when entries with the same
    GAME_NAME and DATE or DATETIME are present, I changed it to only display an alert to user as it is certainly possible to play
    several games with the same GAME_NAME on the same day, and some users may also log several game entries at the same time.
    (Pull request [\#112](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/112))
  
* **Enhancements to existing features**:
  * Added unit tests for Average and Median (Pull request [\#215](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/215))
  * Styled the graph (Pull request [\#137](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/137), [\#142](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/142))
  * Fixed miscellaneous bugs in existing features (Pull request [\#147](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/147),
    [\#212](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/212), [\#213](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/213))

* **Documentation**:
  * User Guide:
    * Made an initial modification of Quick Start & FAQ to suit the purpose of GameBook in v1.1 (Pull request [\#12](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/12))
    * Modified miscellaneous explanations to improve clarity (Pull request [\#212](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/212))
    * Added explanation about auto-capitalization feature of GAME_NAME and LOCATION (Pull request [\#212](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/212))
    * Added several screenshots to illustrate the usage of the app (Pull request [\#212](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/212))
  * Developers' Guide:
    * Updated product scope, NFR and glossary (Pull request [\#29](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/29), [\#34](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/34))
    * Added description of implementation details for adding a game entry. (Pull request [\#145](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/145))
    * Updated UiClassDiagram (Pull request [\#145](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/145))
* **Project management**:
  * Managed release `v1.3(final)` on GitHub.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#113](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/113), [\#114](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/114), [\#136](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/136)
  * Reported 18 bugs and suggestions for another team during PE-D.
