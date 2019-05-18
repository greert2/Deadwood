# Deadwood-text

To compile:
    
    javac Deadwood.java

To run:

    java Deadwood
    
    
Commands:

* move
  * gives list of adjacent rooms
* move [roomName]
  * moves player to this room, if legal
* where
  * tells players location, gives rundown of all roles, as well as what scene
* who
  * tells you who you are! (name, $, credits)
* take [role name]
  * Gives you the role if it hasn't been taken and you are eligible
* Active?
  * Shows active player. Displays where they are, and their current role, if they have one
* upgrade
  * shows available upgrades (if in the casting office)
* upgrade [money/credits] [desired rank]
  * upgrades the player if they have sufficient funds
  * ex: upgrade credits 2
* act
  * use to act on your role (if you have one)
* rehearse
  * use this to rehearse for your role (increase rehearse chips)
* end
  * use to end your turn