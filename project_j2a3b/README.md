# **Money Matters**

## *Making every penny count*

This simple and smart budgeting application is aimed at providing a user friendly interface for students to budget in their day to day life. Specially designed for students, this app can also be used by people in general. The app lets you stay on top of your bills and create budgets you would actually stick to. Keep a track of your daily expenses and see monthly and categorical reports of your expenditures. Be on top of your student loans and rent and keep a tab on your scholarships. This is the all-in-one app you need to be a financially independent student!  

## User Stories
- As a user, I want to be able to add expenses to my budget
- As a user, I want to organise my transactions by different categories 
- As a user, I want to be able to view my due and upcoming bills
- As a user, I want to be able to add income to my budget
- As a user, I want to be able to save my current account transactions to a file
- As a user, I want to be able to load my transactions from file when the application starts

## Instructions for Grader
- You can generate the first required event by adding/deleting an expense to a transaction in the transaction menu
- You can generate the second required event by paying a bill which also adds the same to a transaction (see the change after paying in the transaction menu)
- You can local my visual component, a bar chart, on the dashboard
- You can save the state of my application by simply closing it
- You can reload the state of my application by simply opening it

## Phase 4: Task 2
Made the ExpenseCategory Class robust. The ExpenseCategory.addTransaction() method throws a checked exception if a transaction exceeds balance. The Main.updateCategoryBalance method handles the checked exception.

## Phase 4: Task 3
- To improve cohesion in the Main class, I made a separate class that deals with alert boxes so that the main class does not have to deal with it.
- There was a lot of semantic coupling in getting the add and delete buttons in the main GUI class, as they are supposed to work the same way in all the scenes. To fix this, I made a common getAddButton() and getDeleteButton() methods that take a type parameter to perform appropriate actions.
- There was too much coupling in a lot of methods in the main class to save data. To fix this, I made a common method to save all sort of data, saveData(). Moreover, previously there was one method saving different data to different files. This resulted in a lack of cohesion as one method was doing a lot of things at once. I separated the methods to save data in the MoneyMatters class. 


