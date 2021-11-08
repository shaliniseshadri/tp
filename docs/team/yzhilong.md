---
layout: page
title: Zhi Long's Project Portfolio Page
---

### Project: Game Book

Game Book is a game tracking app for recreational gambling games players. Game Book helps users track their revenues and losses across different games and generates meaningful statistics that aid them in understanding their gaming records. It is designed for users who prefer Command Line Interface (CLI).

Given below are my contributions to the project.
* Refactored model and related subclasses from AB3 to required classes for Game Book ([#119](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/119), [#74](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/74)).
    * Replaced person class with GameEntry class.
    * Updated `Model` to support GameEntry classes in `v1.2` and subsequently refactored it in `v1.3`, reducing SLAP violations.
    * Allow duplicate game entries in a game book, unlike in AB3 where people with the same identity are not allowed.
* **Feature Enhancements**:
    * Wrote additional tests for existing features ([#218](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/218), [#135](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/135))
    * Shortened argument flags for easier typing of commands. ([#96](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/96))
    * Remove ability to edit start and end amounts of game entries.
        * Profit can be specified by using start and end amounts, or with profit flag.
        * Not clear whether to change start or end amount if user edits a game's profit (and likewise for a game entry specified with profit only).
        * Removed because we are no longer implementing profit percentage.
* **Bug fixes**:
    * Fix lacking warnings ([#204](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/204), [#205](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/205))
    * Fix parser not detecting invalid flags ([#225](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/225))
    * Fix invalid durations ([#227](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/237))

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=w13-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=yzhilong&tabRepo=AY2122S1-CS2103T-W13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false)
* **Project management**:
    * Released `v1.3(trial)`

* **Documentation**:
    * User guide:
        * Added FAQ on storage format.
        * Minor cosmetic changes to where format of arguments are specified
    * Developer Guide:
        * Added sequence and activity diagrams for `edit` and `find` feature.
        * Added implementation details and use cases for `edit` feature.
        * Added table of contents to subsections.
        * Added manual testing instructions for saving data.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [#126](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/126)
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S1-CS2103-F10-4/tp/issues/167), [2](https://github.com/AY2122S1-CS2103-F10-4/tp/issues/178), [3](https://github.com/AY2122S1-CS2103-F10-4/tp/issues/179))
