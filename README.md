# ABXTester2016
Configurable ABX test for Android. Convenient when you want to check if a user can determine if an unknown sound (X) is the same as a reference sound (A or B).

Thank you for using the ABX Tester 2016!
If you are unfamiliar with the purpose of an ABX test, please have a look at
https://en.wikipedia.org/wiki/ABX_test

Flow: 
1.The user sets a user name
2. The user listens to the A and B sound for each test round. A number of X sounds are presented as well. Each X sound is either A or B, set randomly at the start of the test. 
3. The user has to decide if the X sound sounds more like A or more like B. The user taps the button next to an X and thereby toggles the answer (A or B).
4. The user press the ”Done” button and moves to the next test round (if there are more sounds to test), otherwise the results screen is displayed, showing the user how many correct answers were achieved. By pressing the ”Send results” button, the user can e-mail the results to the test conductor (you). It is important to set the e-mail address in the ABXTesterApplication class.

Here are some things to keep in mind:

1. Add any sound files you wish to use in the res/raw folder. The app has been tested with WAV and MP3 files.

2. Since Android sorts the files by file name, name the files accordingly. Example: a1_myDrum, a2_myPiano etc if you want to have them in order.

3. There are four modes that you can use, which I’ve chosen to name as follows: 

- allSoundCombinations
Pitches all sounds against each other. Example: If we have the sounds X,Y,Z, then there will be three test rounds: X vs Y, X vs Z, and Y vs Z. 

- firstSoundAgainstAllOther
Pitches the first sound in the raw folder against all the other sounds. Example: If we have the sounds X,Y,Z, then there will be two test rounds: X vs Y and X vs Z.

- orderedSingleOccurrence:
All sounds only occur in one test round, in order. Example: If we have the sounds X,Y,Z,W listed in the raw folder in alphabetical order, then there will be two test rounds: X vs Y and Z vs W. Note that for this test type to work the number of sounds needs to be even.

-randomizedSingleOccurrence:
Same as orderedSingleOccurrence, except that the sounds in each test rounds are randomized. Needs an even number of sounds to work.

The types are stored in the TestTypEnum class. The type that you wish to use can be set in the ABXTesterApplication class.

4. Configurable variables such as xSoundsPerTestRound and emailAddressToSendAnswersTo can be found in the ABXTesterApplication class.

5. The id for each test is a combination of the user name with shuffled letters and a random number between 0 and 1 000 000.

Please feel free to contact me with any questions:
github.com/icontech 

Best regards,

David Sandberg
 
