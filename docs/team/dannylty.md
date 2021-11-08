---
layout: page
title: Tianyi's Project Portfolio Page
---

## Project: Game Book

GameBook is a desktop app designed for gamblers to track their gambling games. It is optimized for use via a Command
Line Interface (CLI), but still possesses the benefit of a Graphical User Interface (GUI).

Our project is a brown-field project. We adapted from the existing 
AddressBook3 ([GitHub Link](https://github.com/nus-cs2103-AY2122S1/tp)) application to build our software.


Below lists my major contributions to the project.

Code Contributed: [RepoSense Link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=dannylty&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=dannylty&tabRepo=AY2122S1-CS2103T-W13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

### **Team-based Tasks**
  * Set up the organisation group and repository.
  * Set up links to code coverage tools and CI.
  * Provided an inspiration for the context of the project.
  * Participated in reviewing of PRs.
### **Enhancements and Adaptation from AddressBook**
  * Handled the commands unit of the software
    * `add`, `delete`, `edit`, `list`, and `exit` commands for initial adaptation in v1.2.
  [[#41](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/41)].
    * Refactored `/tag` parameter. [[#98](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/98)]
    * `find` command for adaptation in v1.3. [[#113](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/113)]
  * Refactored the macroscopic structure of components. [[#126](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/126)]
    * Enforced separation and abstraction between Model and Logic components.
    * Enforced dependency-inversion principle by making both Parser and Model subclasses depend on
    an abstraction.
    * Enforced further information hiding between interacting components of Parser and Model.
    * A large scale refactoring given that the test suite depended on many of the changed components.
  * Continuously managed and updated test suite. [[#66](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/66/files),
[#75](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/75), [#220](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/220)]

### **Documentation**
* **User Guide**
  * Adjusted parameters to `edit` command.
  * Although not included in the PDF, created a sidebar for navigation after much toil through HTML and CSS technology.
  * Participated in review, proofreading, and general editing.
* **Developer Guide**
  * Updated `Logic` component. Adapted sequence diagrams from AddressBook3 to our version.
  * Add use cases
  * Created a sidebar for navigation
  * Participated in review, proofreading, and general editing.

### Review/Mentoring Contributions
* **Reviewed Pull Requests**
  * [Link](https://github.com/AY2122S1-CS2103T-W13-3/tp/pulls?q=is%3Apr+reviewed-by%3Adannylty+) to PRs approved by me
  * PRs with significant feedback: [[#91](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/91),
  [#175](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/175),
  [#216](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/216),
  [#225](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/225),
  [#226](https://github.com/AY2122S1-CS2103T-W13-3/tp/pull/226)]
  * Provided tips on concepts including, but not limited to, Git, GitHub, Gradle.