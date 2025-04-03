[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/NJdesGoS)

Name : Kshitij Vispute ID: 2022A7PS0372G Email : f20220372@goa.bits-pilani.ac.in

Name : Aditya Nair ID: 2021B2A72364G Email : f20212364@goa.bits-pilani.ac.in
# JournalApp

This is a journal app that allows you to record your journal entries. You can add, edit, and delete entries. You can also view all entries.

## Table of Contents

### 1. Navigation using the nav graph actions
The navigation of the app is handled using the nav graph actions. The nav graph is defined in the `res/navigation/nav_graph.xml` file. The app's navigation is defined in the `res/navigation/nav_graph.xml` file. The app's navigation is handled by the `NavController` which is defined in the `Activity` class. The `NavController` is used to navigate between the different fragments of the app. The navigation is handled by the `NavController` which is defined in the `Activity` class. The `NavController` is used to navigate between the different fragments of the app.

### 2. Database modification
The database was modified to include the new columns mentioned above. The database was tested to ensure that the modifications were correct. The database was tested using the `RoomDatabase` class. The `RoomDatabase` class was used to create a database instance. The database was tested using the `RoomDatabase` class. The `RoomDatabase` class was used to create a database instance.

### 3. DELETE button
A DELETE button was added to the menu bar of the `EntryDetailsFragment` view. The button was added using the `onCreateOptionsMenu` method. The button was added using the `onCreateOptionsMenu` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event is handled by a confirmation dialog. The confirmation dialog is displayed using the `AlertDialog` class. The confirmation dialog is displayed using the `AlertDialog` class. If the user confirms the deletion, the entry is deleted using the `delete` method of the `JournalRepository` class. If the user confirms the deletion, the entry is deleted using the `delete` method of the `JournalRepository` class.

### 4. SHARE button
A SHARE button was added to the menu bar of the `EntryDetailsFragment` view. The button was added using the `onCreateOptionsMenu` method. The button was added using the `onCreateOptionsMenu` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event is handled by a message creation dialog. The message creation dialog is displayed using the `AlertDialog` class. The message creation dialog is displayed using the `AlertDialog` class. The message creation dialog creates a plain text message. The message creation dialog creates a plain text message. The message is shared using the `Intent` class. The message is shared using the `Intent` class. The message is shared using the `Intent` class.

### 5. INFO button
The INFO button on the `EntryList` fragment menu bar was modified to lead the user to the webpage of the author of The Atomic Habits: https://jamesclear.com/atomic-habits by launching the default browser or a webview within the app. The button was modified using the `onCreateOptionsMenu` method. The button was modified using the `onCreateOptionsMenu` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event was handled by the `onOptionsItemSelected` method. The button's click event is handled by an `Intent` to launch the default browser or a webview within the app. The button's click event is handled by an `Intent` to launch the default browser or a webview within the app.

### 6. Accessibility
The app was tested using Talk Back. The app was tested using Talk Back. The app was tested using the Accessibility Scanner. The app was tested using the Accessibility Scanner. The app was tested using Espresso test cases that also check for accessibility. The app was tested using Espresso test cases that also check for accessibility. The app's accessibility was tested using the Accessibility Scanner. The app's accessibility was tested using the Accessibility Scanner. The app's accessibility was tested using Espresso test cases that also check for accessibility. The app's accessibility was tested using Espresso test cases that also check for accessibility.
