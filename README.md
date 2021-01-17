# Moral dilemma detector
This project consits of 3 different modules.
## Moral dilemma detector
Application with GUI that uses generated scenarios to detect moral dilemmas. Scenario generator is integrated so that you can generate new scenarios without leaving the app.
### Requirements
+ Installed [JDK 11](https://www.oracle.com/pl/java/technologies/javase-jdk11-downloads.html)
### Usage
The easiest way to run our system on your computer is to download precompiled binaries included in the [latest release.](https://github.com/kamsza/moral-dilema-detector/releases/latest)  
1. Download MoralDilemmaDetector.zip.
2. Unpack it and open MoralDilemmaDetector directory.
3. Run MoralDilemmaDetector.jar by clicking twice on it.

It might take a few seconds. You should see a following screen.

<p align="center">
  <img src="https://user-images.githubusercontent.com/49042374/104850151-b6a9b200-58ed-11eb-8f57-41b52587b9c4.png" width="500">
</p>

You might also run it from the terminal to get debug info. 
```bash
java -jar MoralDilemmaDetector.jar
```  

## Scenario generator
App is responsible for generating road scenarios which can contain moral dilemma for autonomous vehicle. Generated scenarios are saved into ontology file. Application provide also optional feature which enable to create simple visualization of scenario, which can be saved as PNG file.
### Requirements
+ Installed [JDK 11](https://www.oracle.com/pl/java/technologies/javase-jdk11-downloads.html)
### Usage
1. Download precompiled binaries included in the [latest release.](https://github.com/kamsza/moral-dilema-detector/releases/latest)  
2. Unpack generator.zip archive.
3. Run **generator.jar** by clicking twice on it or run it via terminal using following command:

```bash
java -jar generator.jar
```  

## Scenario generator from real data (NDS and Waymo datasets for now)
