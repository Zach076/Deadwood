# Building
Just run make from the command line.

# Running
You can call
```
./run.sh
```
or run 
```
java -classpath ./build DeadWood 2
```
from the command line. 2 being the number of players.

# About the Design
For Assignment 3 we ended up in a really good place. The design for Assignment 2 kept the view separate from the model/controller, 
meaning we could hook up the new View without any changes to model/controller. We used the text view to play the game and test things 
as we built out our new view. We divided the work by buttons/interactions and Display. Overall this part of the project went very smooth. 
We used Model View Controller, but kept the Model/Controller together and kept the view separate. We also utilized Observables to make 
handling of events easy.