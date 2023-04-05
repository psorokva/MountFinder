# MountFinder <img alt="" width="30px" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/350/snow-capped-mountain_1f3d4-fe0f.png" />

## Find which mountain to visit for your next snowboarding trip!

### **MountFinder** will keep track of all relevant information about ski resorts near Vancouver and will assist the users with selecting the best option based on their preferences.

**Some examples of relevant information:**
- Lift ticket prices *(based on a date)*
- Gear rental availability
- Gear rental prices
- Season pass options
- Distance from home
- Elevation
- Number of runs
- Difficulty of runs
- Snow conditions
- Accessible by transit

### The target user is someone living/staying in Vancouver who is interested in going snowboarding.

### *I love to go snowboarding, but I've always found it very time-consuming to go to the website of each separate mountain resort to find out their prices and other information. This gets even more complicated when you are making plans with a group of people, each person with separate needs and preferences. Having one centralized application that allows you to filter by various preferences will greatly simplify this task.*

## User Stories
- As a user, I want to be able to add multiple new mountains/ski resorts to the existing list
- As a user, I want to be able to see the driving distance from my home (example: UBC) to the ski resort
- As a user, I want to be able to compare ski resort lift ticket price
- As a user, I want to be able to see whether a ski resort offers gear rental options
- As a user, I want to have the option to save my mountain list and to be reminded to save before quitting
- As a user, I want to be given the option to load my mountain list from file

# Instructions for Grader
### To add new mountains to the mountain list (the first required action):
1. Start the application by running Main
2. Select a city from the dropdown (Vancouver) and click "Ok"
3. Select "Add new mountain" option
4. Fill out the form and select "Save mountain". This will add a new mountain to the list.

### To compare two mountains (the second required action):
1. Start the application by running Main
2. Select a city from the dropdown (Vancouver) and click "Ok"
3. Add at least 2 mountains through "Add new mountain" option or select "Load list from file"
4. Select "Compare two mountains"
5. In the new window, select 2 mountains that you want to compare and click "Compare"
- *This will display information of 2 mountains side by side and if price and distance values are
not equal, will change the font to red for higher value and green for lower value.*

### To enter distance to mountain from different city (another action):
1. Start the application by running Main
2. Select a city from the dropdown (Vancouver) and click "Ok"
3. Add a new mountain through "Add new mountain" option or select "Load list from file"
4. Open this mountain and note the distance
5. Change to a different city in the dropdown and click "Ok"
6. Open the same mountain
- *If this mountain already has a distance entered for new city, it will display the new distance.
If distance was not previously saved, it will display a new field where it can be entered and saved.*

### Visual component:
- Visual component is visible after adding new mountain in the Success pop-up.

### To save the state of the application to file:
- Select "Save list to file" option in the menu.

### To load the state of the application from file:
1. Select "Load list from file" option in the menu.

### Phase 4: Task 2
Tue Apr 04 15:38:43 PDT 2023 City set to Vancouver.  
Tue Apr 04 15:38:45 PDT 2023 
Distance to the mountain added.  
Tue Apr 04 15:38:45 PDT 2023
Lift ticket price added.  
Tue Apr 04 15:38:45 PDT 2023
Rentals set to available.  
Tue Apr 04 15:38:45 PDT 2023
Mountain Grouse added to the mountain list.  
Tue Apr 04 15:38:45 PDT 2023
Finished loading mountain Grouse from file.  
Tue Apr 04 15:38:45 PDT 2023
Distance to the mountain added.  
Tue Apr 04 15:38:45 PDT 2023
Lift ticket price added.  
Tue Apr 04 15:38:45 PDT 2023
Rentals set to not available.  
Tue Apr 04 15:38:45 PDT 2023
Mountain Second mountain added to the mountain list.  
Tue Apr 04 15:38:45 PDT 2023
Finished loading mountain Second mountain from file.  
Tue Apr 04 15:39:09 PDT 2023
Lift ticket price added.  
Tue Apr 04 15:39:09 PDT 2023
Rentals set to not available.  
Tue Apr 04 15:39:09 PDT 2023
Distance to the mountain added.  
Tue Apr 04 15:39:09 PDT 2023
Mountain Seymour added to the mountain list.  
Tue Apr 04 15:39:26 PDT 2023
City set to Richmond.  
Tue Apr 04 15:39:37 PDT 2023
Distance to the mountain added.

### Phase 4: Task 3 
#### Refactoring:
- After drawing the diagram, I realized that I could potentially reduce the number of associations
with User, Mountain and MountainList classes. If I had more time to refactor my project I would
try to remove MountainList class and instead keep a list of all Mountains in some other class, 
potentially in Mountain class.
- I would also isolate all behaviour related to interacting with console out of MountFinderApp
and move it to a separate class MountFinderConsole. Then both MountFinderConsole and MountFinderUI
would only be responsible for displaying information and would only have association with MountFinderApp 
and not with MountainList or Mountain.
- After learning about Observer Pattern, I would like to implement it to simplify interactions between classes.
For example, instead of directly refreshing UI components everytime a new mountain is added to the list,
any change related to MountainList would notify observers and the UI would know to update it's state.
- If possible, I would also like to use Observer to trigger reminder to save the state of application
before quitting only if changes to the MountainList were made while running the app. If there were 
no changes to the list, there would be no reminder to save.

## References
[JsonSerializationDemo](https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo) was adapted as the basis for 
the persistence module of this app.  
Event and EventLog classes and related test classes were adapted from
[AlarmSystem](https://github.students.cs.ubc.ca/CPSC210/AlarmSystem).