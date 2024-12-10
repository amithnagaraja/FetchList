# FetchList

Display this list of items to the user based on the following requirements:

Display all the items grouped by "listId"
Sort the results first by "listId" then by "name" when displaying.
Filter out any items where "name" is blank or null.
The final result should be displayed to the user in an easy-to-read list.

End Points: https://fetch-hiring.s3.amazonaws.com/hiring.json

Kotest Integration:
1. Kotest is a descriptive styled unit testing platform that uses junit5
2. Install Kotest Plugin in Android Studio
3. More documentations can be found in https://kotest.io/docs/framework/project-setup.html
4. A green play button appears when open the individual test files and can be used to run the tests 
5. Alternatively use "./gradlew test" from command line to run all test case at once and test report can be found in build/reports/. Look for index.html file under appropriate build flavor.
6. Other way to run is using Android Studio IDE under Build Variants. Make sure to select the appropriate test variant >> Run All Tests