# CSC131-Logic Tutorial	
A simple to use educational tool for creating and manipulating simple logic circuits.

## Team - Brownies	

|Members|	
|:------:|	
| Erick Carias |	
| Akshay Gautam |	
| Ivan Medina |	
| Moises Pantoja |	

## Features to be added	

|#|Features|	
|:------:|:----:|	
|1| Add more logic gates |	
|2| Ability to name saves |	
|3| Ability to use saves in other circuits |	

## Design Document	

### Glossary	

|Word Bank| Definition|	
|:------:|:----:|	
|Element:| Refers to a component that can be displayed on the screen such as an and-gate, or-gate, not-gate, button, or light bulb. |	
|Circuit grid| Refers to the 10x10 grid in which the user can interact with and create logic circuits on. |	

### Introduction:	
This software is primarily designed for those who have completed a course in discrete mathematics and want to implement and simulate simple logic circuits using a variety of different logic gates. The software will run on devices using the Android operating system	

### User Interface:	
When the program initially launches the user will be able to choose between a sandbox mode and a tutorial mode from a main menu screen and from that point on the program will set the activity appropriately. The entire UI will constrain the device to landscape mode on a 10x10 grid of squares. The left most column of the grid will contain a scrolling view of buttons needed to interact with the program. These buttons will include "Move", "Delete","Clear","Save", and "Load". Also included in the scrollable button bar will be an "Add" button that, when pressed, will open a separate menu that will contain all of the elements that can be added to the screen to create circuits. 

### Adding/Removing Elements:	
The user will have the option to add 9 different types of elements to the circuit grid. These element types are And gates, Or gates, Not gates,NAND gates, XNOR gates, XOR gates, NOR gates, switches, and Light Bulbs. In order to add a particular element to the circuit grid, the user must choose the element that they want to add from the UI menu. After the element has been selected, then the user can touch any part of the screen to begin dragging the selected element around the grid until they choose a grid square to drop it in. When the element is dragged around the screen, each grid square that the user drags over will be individually highlighted to indicate where the element will be placed if they were to let go of the screen in that specific moment. Once the user has dropped an element the system will check if the selected grid square is populated by another element. If the grid square is populated by another element then an error message will be shown to let the user know that two elements cannot be placed in the same grid square, thus the user will need to repeat the process of adding an element until an empty grid square is selected. Otherwise, if the grid square selected by the user is empty the user selected element is placed in the valid grid square and the placed element will contain no connections to any other elements on the grid. If the user wants to clear the entire screen, then they will have the option to select the clear button from the UI menu. This button will remove all elements that are displayed on the screen. Otherwise, to remove an individual element from the grid, the user must first press on the UI button which contains the text “Delete”. After the delete button is selected, the user must select an item in the circuit grid to delete and one of the following will occur:  	

| User Cases | Adding/Removing Elements|	
|:------:|:----:|	
| **Case:** &nbsp;| grid square is selected that is already empty |	
|**System Response**| nothing will happen and the system will remain in delete mode |	
| **Case:**| selected grid square has an element that contains connections to a different item |	
|**System Responses**| that item and its connections will be deleted.|	
| **Case:**| the selected grid square only contains an element with no connections |	
|**System Responses**| that specific element will be removed from memory. |
#### Notes:
* The delete button is not a toggle button. That is, when the button is pressed and an item is selected to delete, the item will be deleted and the delete state will be disabled.


### Moving Elements:	
Moving elements displayed on the circuit grid is simple and easy to do. To enable the move mode, the user must first select the "Move" button from the on screen UI menu. Once selected, the user may then select any one element on the circuit grid they would like to move and drag and drop that selected element on the circuit grid. When the selected gate is dropped on a grid square one of the following will occur: 	

| User Cases | Moving Elements|	
|:------:|:----:|	
| **Case:** &nbsp;|  grid square is populated |	
|**System Response**|the selected element will be snapped back to its original location.|	
| **Case:**| grid square is populated by the menu|	
|**System Responses**|the selected element will be snapped back to its original location.|	
| **Case:**| grid square is empty and passes previous checks|	
|**System Responses**| the selected element and its connected wires will be moved.|	
#### Notes:	
* If the element being moved contains wires to either its input(s) or output connectors, then when the element is moved, the wires will	
be dragged around with the object aswell.	
* The move button is not a toggle button. When the user selects the move button and moves an element, the move state will be disable after the moved element is placed back on the grid. 


### Connecting Elements (General Rules):	
In order to connect two elements the program must not be running any other UI button functions. If this is true then the user may freely tap and hold any element (excluding the light bulb) to drag wires directly from an output port of that selected element. When a user tap and holds to initiate a wire, the phone will briefly vibrate to indicate to the user that the wiring mode as been enabled. Once a wire begins to drag from the selected elements output port then the user may drag it to an open input port location on the grid. A few things to note about wiring elements. Open input ports on elements are marked by an empty black circle on the element port location. Ports that are occupied are marked with a green circle on the elements port location. An element may have up to 16 output wires but can only have one input wire connected to it at one time. When the user drag and drops a wire at a grid square one of the following will occur:	

| User Cases | Connecting Elements |	
|:------:|:----:|	
| **Case:** &nbsp;|  grid square is not populated by any element |	
|**System Response**| the wire will not be drawn and the connection will be unsuccessful. |	
| **Case:**| the grid square is populated by an element but all the ports are in use|	
|**System Responses**| the wire will not be drawn and the connection will be unsuccessful.|	
| **Case:**| the grid square is populated by an element and there is an open port |	
|**System Responses**| wire will be drawn to that port and the connection will be successful and this is marked by a green circle on the port signifying that it is being used.|	


#### Switch to Gate Connection:  	
Connecting switches to gate elements follows the same process outlined above. the one exception to a switch element is that it has no input ports. This means that an element cannot wire itself to a switch, only a switch can wire itself to multiple elements.	


#### Gate to Gate Connection:  	
In order to connect two gates, the general rules outlined above apply.	

#### Light Bulb Connection:  	
In order to connect a light builb to the circuit an element must be connected to it. The lightbulb will not be able to produce connections between elements, this means that the user must start the wiring at any other element on the circuit then connect that wire to a light bulb falling the general rules outlined above.	

#### Deleting Connections:  	
Because it is difficult to constrain a visual wire connection to a specific grid square, for the sake of simplicity, to delete connections the user must delete the entire element with which contains the connection(s) they want to delete, then re-establish the element and its connection(s) excluding the connection which was to be originally deleted.  	

#### Notes:  	
* Switches may have as many outgoing connections as the user wants/the 10x10 circuit grid allows.	
* And-gates and Or-gates may only have two input connections but up to 16 output connection.	
* Not-gates may only have one input connection and one output connection.	
* The output connector of any gate cannot be connected directly to a switch. If the user attempts to connect the output of a gate to a switch the connection will be unsuccessful.	
* Light bulbs may only have one connection to its input connector.	
* Light bulbs wires cannot be directly dragged from a light bulb, only elements can drag wires to a light bulb.	

### Saving/Loading a Circuit Schematic:	
To save a schematic, the user must press on the save button. After the save button is selected, a new screen will appear where the user will be able to name that schematic to a specified save name they type in. To load a schematic, the user may press the load button and a new menu screen will show which saves are available to load. Then, the user can select any of the saves they have on file to load a schematic. When the user is entering a save name, the entered name can not be empty. If the entered save name is empty, then current schematic will not be saved and the user will be notified of this. Also, if the user enters a save name that is identical to a schematic that has already been saved, then the current schematic will not be saved and the user will have to input a unique name in order to save the schematic.	

#### Notes:	
* If the user loads a saved schematic while there is an unsaved circuit on the screen, the unsaved circuit will be lost once the saved schematic is loaded.	
* When a user saves a schematic, the state that the schematic was in when the save button was pressed will be saved,. For example, if the schematic to be saved contains switches that were on when the schematic was saved, when the user reloads that specific schematic, the switches will be in an on state. 	

### Running Simulations:	
To evaluate/run a circuit, the system must not be performing any other UI menu button functions. If no other functions are being performed then the user can directly single tap a switch on the circuit grid and the system will evaluate the circuit. Based on the evaluation of the circuit the connected light bulb will be either in an on or off state.	

### UI Button Presses:	
The user has the ability to press different UI buttons sequentially without any errors occurring. If the user does press certain UI buttons sequentially then the software will be left in the state of whichever button was pressed last. For example, if the user were first to select the “Add Switch” button and then soon after selected the “Add And-Gate” button, the software will be left in an add and gate mode where the user can add an and gate to the circuit grid. 
