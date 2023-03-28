# Battle Simulation
This is an iterative simulation of two fighting teams on a simple 2d map.


# Overwiev
There are neutral and team units, each one having it's unique features.
Some units are stationary, some can move across the map. They interact with each other when in contact.


User can set some parameters of the simulation and choose what information will be stored in a result text file.
The simulation has no animations, just a simple GUI to set options and start the simulation.

## Parameters
1) ### Size of the Map's edge
2) ### Number of Iterations
3) ### Amount of Infantry
4) ### Amount of Tanks
5) ### Amount of Mobile Bases
6) ### Amount of Stationary Bases
7) ### Amount of Traps


## Units
1) ### Army Unit
    - belongs to one of the two teams
    - moves across the map
    - two available types: tank, infantry
    - each type has different stats: health, damage, ammunition
    - deals and takes damage when trying to access field occupied by other team's ArmyUnit
    - can collect supplies from Base or Moving Base: ammunition and health
    - dies if hp reaches 0
2) ### Moving Base
    - moving, neutral unit
    - moves across the map and delivers supplies to Army Units
    - has limited amount of supplies
    - can be resupplied by stationary Base
    - can be destroyed by Trap
3) ### Base
    - stationary, neutral unit
    - provides supplies to Moving Base and Army Unit
    - cannot be destroyed
4) ### Trap
    - stationary, neutral unit
    - deals damage to any Army Unit that reaches its field
    - destroys Moving Base that reaches its field
    - has limited number of uses
    
# Showcase

https://user-images.githubusercontent.com/72346218/228390346-c1e37e78-aa4a-4b8e-9839-fb639d791d95.mp4



