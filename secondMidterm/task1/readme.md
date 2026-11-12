# Bucket
 
## Опис (МК)
 
Да се имплементира класа `Bucket` за репрезентација на „кофа“ за складирање на објекти во облак. Секој објект е дефиниран со клуч (пр. `photos/2024/12/image.jpg`). Клучот на објектот е составен од префикс (`photos/2024/12`) и име на објектот/датотеката (пр. `image.jpg`). Префиксот дефинира логичка структура на директориуми и поддиректориуми во кои се наоѓа објектот.
 
За класата да се имплементираат:
 
* конструктор `Bucket(String name)`
* метод `void addObject(String key)` – метод за додавање на нов објект во кофата со клуч `key`. При додавање, објектите со заеднички префикси се групираат (погледнете ги тест примерите).
* метод `void removeObject(String key)` – метод за бришење на постоечки објект од кофата со клуч `key`
  * При бришење на постоечки објект, да се направи чистење и на префиксите за тој објект (односно да не се оставаат префикси во кофата без датотеки во нив)
* метод `String toString()` – кој ќе ги испечати информациите за сите објекти во кофата. Да се користи вовлекување при печатење на објектите кои имаат префикс составен од повеќе делови одделени со `/`.
## Description (EN)
 
Implement a class `Bucket` that represents a “bucket” for storing objects in the cloud. Each object is defined by a key (e.g., `photos/2024/12/image.jpg`). The object key consists of a prefix (`photos/2024/12`) and the object/file name (e.g., `image.jpg`). The prefix defines a logical directory and subdirectory structure in which the object is located.
 
For the class, implement the following:
 
* constructor `Bucket(String name)`
* method `void addObject(String key)` – adds a new object to the bucket with the given `key`. When adding objects, objects with the same prefixes are grouped (see example test cases).
* method `void removeObject(String key)` – removes an existing object from the bucket with the given `key`
  * When removing an object, the corresponding prefixes must also be cleaned up (i.e., no prefixes without files should remain in the bucket)
* method `String toString()` – prints information about all objects in the bucket. Indentation should be used when printing objects whose prefixes contain several parts delimited by `/`.
---
 
## Тест примери / Test cases
 
### Тест 1
 
**Input**
```
ADD test/test2/test3/stefan.txt
PRINT
REMOVE test/test2/test3/stefan.txt
PRINT
```
 
**Expected output**
```
bucket/
    test/
        test2/
            test3/
                stefan.txt
bucket/
```
 
### Тест 2
 
**Input**
```
ADD docs/2024/exam1.pdf
ADD docs/2024/exam2.pdf
ADD docs/2023/notes.txt
PRINT
REMOVE docs/2024/exam1.pdf
PRINT
REMOVE docs/2024/exam2.pdf
PRINT
```
 
**Expected output**
```
bucket/
    docs/
        2024/
            exam1.pdf
            exam2.pdf
        2023/
            notes.txt
bucket/
    docs/
        2024/
            exam2.pdf
        2023/
            notes.txt
bucket/
    docs/
        2023/
            notes.txt
```
 
### Тест 3
 
**Input**
```
ADD stefan.txt
ADD ana.txt
ADD gjorgji.txt
ADD dejan.txt
ADD ivan.txt
PRINT
```
 
**Expected output**
```
bucket/
    stefan.txt
    ana.txt
    gjorgji.txt
    dejan.txt
    ivan.txt
```
 
### Тест 4
 
**Input**
```
ADD stefan.txt
ADD ana.txt
ADD gjorgji.txt
ADD dejan.txt
ADD ivan.txt
REMOVE dejan.txt
PRINT
```
 
**Expected output**
```
bucket/
    stefan.txt
    ana.txt
    gjorgji.txt
    ivan.txt
```
 
### Тест 5
 
**Input**
```
ADD root.txt
ADD folder/file.txt
ADD folder/subfolder/data.bin
PRINT
```
 
**Expected output**
```
bucket/
    root.txt
    folder/
        file.txt
        subfolder/
            data.bin
```
 
### Тест 6
 
**Input**
```
ADD root.txt
ADD folder/file.txt
ADD folder/subfolder/data.bin
PRINT
REMOVE folder/subfolder/data.bin
PRINT
```
 
**Expected output**
```
bucket/
    root.txt
    folder/
        file.txt
        subfolder/
            data.bin
bucket/
    root.txt
    folder/
        file.txt
```
 
### Тест 7
 
**Input**
```
ADD root.txt
ADD folder/file.txt
ADD folder/subfolder/data.bin
PRINT
REMOVE folder/file.txt
PRINT
```
 
**Expected output**
```
bucket/
    root.txt
    folder/
        file.txt
        subfolder/
            data.bin
bucket/
    root.txt
    folder/
        subfolder/
            data.bin
```
 
### Тест 8
 
**Input**
```
ADD photos/2024/01/a.jpg
ADD photos/2024/01/b.jpg
ADD photos/2024/02/c.jpg
ADD docs/readme.md
PRINT
```
 
**Expected output**
```
bucket/
    photos/
        2024/
            01/
                a.jpg
                b.jpg
            02/
                c.jpg
    docs/
        readme.md
```
 
### Тест 9
 
**Input**
```
ADD photos/2024/01/a.jpg
ADD photos/2024/01/b.jpg
ADD photos/2024/02/c.jpg
ADD docs/readme.md
PRINT
REMOVE photos/2024/01/a.jpg
REMOVE photos/2024/01/b.jpg
PRINT
```
 
**Expected output**
```
bucket/
    photos/
        2024/
            01/
                a.jpg
                b.jpg
            02/
                c.jpg
    docs/
        readme.md
bucket/
    photos/
        2024/
            02/
                c.jpg
    docs/
        readme.md
```
