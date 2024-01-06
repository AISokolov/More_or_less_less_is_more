Faculty of Mathematics, Natural Science and Information Technologies

More or less, less is more
Write a Java program that allows playing the game ”More or less, less is more.” The game
consists of a two-dimensional field of size n×n. Each element is a button with a number.
Game Initialization:
* Numbers on the buttons are set to a random digit between 1 and 9.
* The target value is displayed above the field.
* The current sum of numbers on the buttons is displayed below the field.
* The number of moves until the end of the game is displayed in the top right corner
above the field.

Playing:

The player selects a button (with a pre-defined digit 1 - 9) to choose possible candidates for the next move.
Initially, Previous is invalid. The player starts by choosing any button (Current)
on the field, allowing the selection of all rows and columns divisible by the chosen
button’s digit.

1. The player can choose a button at the intersection of possible rows and columns between Previous and Current. This choice swaps the Current value to Previous, and Current becomes the digit value of the chosen button
(P revious = Current; Current = New value).
2. Increase the total sum by the value of Current. The selected button becomes
inactive.
3. Decrement the move counter and repeat from step 1 until the move counter
exceeds 0.
