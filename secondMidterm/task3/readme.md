# NewsSystem
 
## Опис (МК)
 
За потребите на систем за управување со вести потребно е да се имплементира класа `NewsSystem` која ќе претставува централен пристап до функционалностите на системот.
Во класата `NewsSystem` задолжително треба да се имплементираат следните методи:
 
* `public NewsSystem(List<String> categoryNames, List<String> authorNames)` – конструктор кој врши иницијализација на системот со достапните категории и автори.
  * листа од имиња на категории
  * листа од имиња на автори
* `public void addUser(String username)` – метод за додавање на нов корисник во системот.
* `public void subscribeUserToCategory(String username, String categoryName)` – метод со кој корисник се пријавува на следење на вести од определена категорија.
* `public void unsubscribeUserFromCategory(String username, String categoryName)` – метод со кој корисник се одјавува од следење на вести од определена категорија.
* `public void subscribeUserToAuthor(String username, String authorName)` – метод со кој корисник се пријавува на следење на вести од определен автор.
* `public void unsubscribeUserFromAuthor(String username, String authorName)` – метод со кој корисник се одјавува од следење на вести од определен автор.
* `public void publishArticle(Article article)` – метод за објавување на нова вест во системот.
* `public void printNewsForUser(String username)` – метод кој ги печати сите вести што ги има добиено корисникот со даденото корисничко име, сортирани според времето на објавата во растечки редослед.
## Description (EN)
 
For the needs of a news management system, it is required to implement a class `NewsSystem` that represents a central access point to the system’s functionality.
Within the `NewsSystem` class, the following methods must be implemented:
 
* `public NewsSystem(List<String> categoryNames, List<String> authorNames)` – constructor that initializes the system with the available categories and authors.
  * a list of category names
  * a list of author names
* `public void addUser(String username)` – method for adding a new user to the system.
* `public void subscribeUserToCategory(String username, String categoryName)` – method by which a user subscribes to receive news from a specific category.
* `public void unsubscribeUserFromCategory(String username, String categoryName)` – method by which a user unsubscribes from receiving news from a specific category.
* `public void subscribeUserToAuthor(String username, String authorName)` – method by which a user subscribes to receive news from a specific author.
* `public void unsubscribeUserFromAuthor(String username, String authorName)` – method by which a user unsubscribes from receiving news from a specific author.
* `public void publishArticle(Article article)` – method for publishing a new article in the system.
* `public void printNewsForUser(String username)` – method that prints all news received by the user with the given username, sorted by publication time in ascending order.
---
 
## Тест примери / Test cases
 
### Тест 1
 
**Input**
```
ADD_USER alice
SUBSCRIBE_CATEGORY alice Technology
PUBLISH Technology JohnDoe 2025-01-01T10:00 New_AI_Model_Released
PUBLISH Sports BobBrown 2025-01-01T11:00 Big_Match_Tonight
PRINT alice
```
 
**Expected output**
```
News for user: alice
[2025-01-01T10:00] JohnDoe - Technology
New_AI_Model_Released
```
 
### Тест 2
 
**Input**
```
ADD_USER bob
SUBSCRIBE_AUTHOR bob AliceSmith
PUBLISH Technology AliceSmith 2025-01-01T09:00 AI_in_Healthcare
PUBLISH Technology JohnDoe 2025-01-01T10:00 Java_21_Features
PRINT bob
```
 
**Expected output**
```
News for user: bob
[2025-01-01T09:00] AliceSmith - Technology
AI_in_Healthcare
```
 
### Тест 3
 
**Input**
```
ADD_USER carol
SUBSCRIBE_CATEGORY carol Science
SUBSCRIBE_AUTHOR carol JohnDoe
PUBLISH Science AliceSmith 2025-01-01T08:00 Space_Discovery
PUBLISH Technology JohnDoe 2025-01-01T09:00 JVM_Internals
PUBLISH Sports BobBrown 2025-01-01T10:00 Football_Results
PRINT carol
```
 
**Expected output**
```
News for user: carol
[2025-01-01T08:00] AliceSmith - Science
Space_Discovery
[2025-01-01T09:00] JohnDoe - Technology
JVM_Internals
```
 
### Тест 4
 
**Input**
```
ADD_USER david
SUBSCRIBE_CATEGORY david Health
PUBLISH Health JaneMiller 2025-01-01T08:00 New_Vaccine
UNSUBSCRIBE_CATEGORY david Health
PUBLISH Health JaneMiller 2025-01-01T09:00 Health_Conference
PRINT david
```
 
**Expected output**
```
News for user: david
[2025-01-01T08:00] JaneMiller - Health
New_Vaccine
```
 
### Тест 5
 
**Input**
```
ADD_USER eva
SUBSCRIBE_AUTHOR eva MartinFowler
PUBLISH Technology MartinFowler 2025-01-01T07:00 Refactoring_Tips
UNSUBSCRIBE_AUTHOR eva MartinFowler
PUBLISH Technology MartinFowler 2025-01-01T08:00 Microservices
PRINT eva
```
 
**Expected output**
```
News for user: eva
[2025-01-01T07:00] MartinFowler - Technology
Refactoring_Tips
```
 
### Тест 6
 
**Input**
```
ADD_USER frank
SUBSCRIBE_CATEGORY frank Business
PUBLISH Business JohnDoe 2025-01-01T12:00 Market_Closes
PUBLISH Business JohnDoe 2025-01-01T09:00 Stocks_Open
PUBLISH Business JohnDoe 2025-01-01T11:00 Midday_Update
PRINT frank
```
 
**Expected output**
```
News for user: frank
[2025-01-01T09:00] JohnDoe - Business
Stocks_Open
[2025-01-01T11:00] JohnDoe - Business
Midday_Update
[2025-01-01T12:00] JohnDoe - Business
Market_Closes
```
 
### Тест 7
 
**Input**
```
ADD_USER anna
ADD_USER mark
SUBSCRIBE_CATEGORY anna Travel
SUBSCRIBE_AUTHOR mark BobBrown
PUBLISH Travel BobBrown 2025-01-01T08:00 Paris_Guide
PUBLISH Sports BobBrown 2025-01-01T09:00 Tennis_Final
PRINT anna
PRINT mark
```
 
**Expected output**
```
News for user: anna
[2025-01-01T08:00] BobBrown - Travel
Paris_Guide
News for user: mark
[2025-01-01T08:00] BobBrown - Travel
Paris_Guide
[2025-01-01T09:00] BobBrown - Sports
Tennis_Final
```
 
### Тест 8
 
**Input**
```
ADD_USER nick
SUBSCRIBE_CATEGORY nick Technology
SUBSCRIBE_AUTHOR nick JohnDoe
PUBLISH Technology JohnDoe 2025-01-01T10:00 Cloud_Trends
PRINT nick
```
 
**Expected output**
```
News for user: nick
[2025-01-01T10:00] JohnDoe - Technology
Cloud_Trends
```
 
### Тест 9
 
**Input**
```
ADD_USER oliver
PUBLISH Sports BobBrown 2025-01-01T10:00 Match_Report
PRINT oliver
```
 
**Expected output**
```
News for user: oliver
```
 
### Тест 10
 
**Input**
```
ADD_USER user1
ADD_USER user2
SUBSCRIBE_CATEGORY user1 Technology
SUBSCRIBE_AUTHOR user1 AliceSmith
SUBSCRIBE_CATEGORY user2 Sports
PUBLISH Technology AliceSmith 2025-01-01T08:00 AI_News
PUBLISH Technology JohnDoe 2025-01-01T09:00 Java_Update
PUBLISH Sports BobBrown 2025-01-01T10:00 Championship
PUBLISH Science AliceSmith 2025-01-01T11:00 Quantum_Breakthrough
PRINT user1
PRINT user2
```
 
**Expected output**
```
News for user: user1
[2025-01-01T08:00] AliceSmith - Technology
AI_News
[2025-01-01T09:00] JohnDoe - Technology
Java_Update
[2025-01-01T11:00] AliceSmith - Science
Quantum_Breakthrough
News for user: user2
[2025-01-01T10:00] BobBrown - Sports
Championship
```
 
### Тест 11
 
**Input**
```
ADD_USER alice
SUBSCRIBE_CATEGORY alice Technology
PUBLISH Technology JohnDoe 2025-01-01T08:00 Tech_1
SUBSCRIBE_CATEGORY alice Science
PUBLISH Science AliceSmith 2025-01-01T09:00 Science_1
UNSUBSCRIBE_CATEGORY alice Technology
PUBLISH Technology JohnDoe 2025-01-01T10:00 Tech_2
PUBLISH Science AliceSmith 2025-01-01T11:00 Science_2
PRINT alice
```
 
**Expected output**
```
News for user: alice
[2025-01-01T08:00] JohnDoe - Technology
Tech_1
[2025-01-01T09:00] AliceSmith - Science
Science_1
[2025-01-01T11:00] AliceSmith - Science
Science_2
```
 
### Тест 12
 
**Input**
```
ADD_USER bob
SUBSCRIBE_AUTHOR bob MartinFowler
PUBLISH Technology MartinFowler 2025-01-01T07:00 Refactoring
UNSUBSCRIBE_AUTHOR bob MartinFowler
PUBLISH Technology MartinFowler 2025-01-01T08:00 Clean_Code
SUBSCRIBE_AUTHOR bob MartinFowler
PUBLISH Technology MartinFowler 2025-01-01T09:00 Design_Patterns
PRINT bob
```
 
**Expected output**
```
News for user: bob
[2025-01-01T07:00] MartinFowler - Technology
Refactoring
[2025-01-01T09:00] MartinFowler - Technology
Design_Patterns
```
 
### Тест 13
 
**Input**
```
ADD_USER carol
SUBSCRIBE_CATEGORY carol Business
PUBLISH Business JohnDoe 2025-01-01T08:00 Market_Open
SUBSCRIBE_AUTHOR carol AliceSmith
PUBLISH Technology AliceSmith 2025-01-01T09:00 AI_Trends
UNSUBSCRIBE_CATEGORY carol Business
PUBLISH Business JohnDoe 2025-01-01T10:00 Market_Close
PRINT carol
```
 
**Expected output**
```
News for user: carol
[2025-01-01T08:00] JohnDoe - Business
Market_Open
[2025-01-01T09:00] AliceSmith - Technology
AI_Trends
```
 
### Тест 14
 
**Input**
```
ADD_USER david
SUBSCRIBE_CATEGORY david Sports
UNSUBSCRIBE_CATEGORY david Sports
SUBSCRIBE_CATEGORY david Sports
PUBLISH Sports BobBrown 2025-01-01T09:00 Match_Review
UNSUBSCRIBE_CATEGORY david Sports
PUBLISH Sports BobBrown 2025-01-01T10:00 Injury_Report
PRINT david
```
 
**Expected output**
```
News for user: david
[2025-01-01T09:00] BobBrown - Sports
Match_Review
```
 
### Тест 15
 
**Input**
```
ADD_USER frank
SUBSCRIBE_CATEGORY frank Technology
SUBSCRIBE_CATEGORY frank Science
PUBLISH Technology JohnDoe 2025-01-01T07:00 Tech_A
PUBLISH Science AliceSmith 2025-01-01T08:00 Sci_A
PUBLISH Technology JohnDoe 2025-01-01T09:00 Tech_B
UNSUBSCRIBE_CATEGORY frank Technology
PUBLISH Technology JohnDoe 2025-01-01T10:00 Tech_C
PUBLISH Science AliceSmith 2025-01-01T11:00 Sci_B
PRINT frank
```
 
**Expected output**
```
News for user: frank
[2025-01-01T07:00] JohnDoe - Technology
Tech_A
[2025-01-01T08:00] AliceSmith - Science
Sci_A
[2025-01-01T09:00] JohnDoe - Technology
Tech_B
[2025-01-01T11:00] AliceSmith - Science
Sci_B
```
 
### Тест 16
 
**Input**
```
ADD_USER maria
SUBSCRIBE_CATEGORY maria Health
SUBSCRIBE_AUTHOR maria JaneMiller
PUBLISH Health JaneMiller 2025-01-01T08:00 Health_1
UNSUBSCRIBE_CATEGORY maria Health
PUBLISH Health JaneMiller 2025-01-01T09:00 Health_2
PRINT maria
```
 
**Expected output**
```
News for user: maria
[2025-01-01T08:00] JaneMiller - Health
Health_1
[2025-01-01T09:00] JaneMiller - Health
Health_2
```
