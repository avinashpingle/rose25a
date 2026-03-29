Feature: Understanding parameterization in cucumber


Scenario:
Passing two arguments to the step of this scenario
	Given I have numbers 7.8 and 61
	When I add them
	Then display the result
	
#Cucumber doesn't support boolean and char data types

Scenario:
Passing String as arguments
	Given I have "Watermelon and Berries"
	Then display fruit name
	
	
Scenario:
Passing list of arguments
	Given I have following fruits:
		|Mango|Yellow|12|
		|Apple|Green|4|
		|Orange|Orange|6|
		|Banana|Red|8|
		|Strawberry|Red|30|
	Then display list of fruits
	
Scenario Outline:
	Given I have a <fruit_name>
	And I have <qty> of it
	Then display fruit name and its qty

Examples:
	|fruit_name|qty|
	|Mango|12|
	|Apple|40|
	|Orange|20|
	|Banana|6|
	|Strawberry|60|
	

Scenario Outline:
This is data driven test case where I am accepting data from excel

	Given Read fruit data from <row_num>
	Then display fruit name and its qty
	
Examples:
	|row_num|
	|1|
	|2|
	|3|
	|4|
	|5|
	|6|
	|7|
	|8|
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	