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


## References
[JsonSerializationDemo](https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo) was adapted as the basis for 
the persistence module of this app.