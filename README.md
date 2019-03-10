# Connect 6
The game was developed as a result of my programming exam at the end of the semester. On the one hand, one can play the classic board game as introduced in 2005 at the conference *Advances in Computer Games*. On the other hand, a *torus* variant of the game has been implemented, which allows results beyond the game board ends.

## Playmaking and Rules
The game is for two to four players who compete against each other. The players place in each valid turn two of their tokens marked *P1*, *P2*, *P3* and *P4* on the quadratic *N x N* board. On each free field only one token is allowed. The amount of token for the players are not limited. The first player, who has placed at least six of his tokens in a continously horizontal, vertical or diagonal direction, wins the game.

In a standard game board, the board edges are completed, so no placement outside the board is allowed and thus marked as invalid. In a torus game board, the outermost fields are neighbours to their opposite outermost fields, thus the edges are open. When a player tries to put his token outside the board at the field with row number *i* and column number *j*, the token will automatically placed on the field *i % N* and *j % N*.

To start the game, one will need three important command line arguments. As aforementioned, the first one is the board type. Secondly, the amount of rows or columns is selected. These need to fulfill the requirement of *17 < N < 21* and *N* needs to be even. In this version, only field sizes of *18 x 18* and *20 x 20* are implemented. The last one is the amount of players participating in the game. This could be 2, 3 or 4 players. All of the given arguments have to be seperated by whitespaces.

### Initialization
Standard game board:
`
java Main standard 20 2
`

Torus game board:
`
java Main torus 20 2
`

## Commands
To play this game via command line, you can use the following commands. Note that *<>* is used in the following examples to clarify the format of the command. These characters aren't used while playing.

### place
The place command always places two tokens of the active player on a free field of the game board.
```
place <row number 1>;<column number 1>;<row number 2>;<column number 2>
```

### rowprint
The rowprint command returns a certain row of the game board.
```
rowprint <row number>
```

### colprint
The colprint command returns a certain column of the game board.
```
colprint <column number>
```

### print
The print command returns the current game board with the given token placements.
```
print
```

### state
The state command returns the current state of a field on the game board.
```
state <row number>;<column number>
```

### reset
The reset command resets the game board of the given type and player 1 starts placing tokens.
```
reset
```

### quit
The quit command exits the program
```
quit
```

## Interaction
An example of an interaction in a standard game board. The *>* character with a following whitespace is used to clarify the user input.
```
> place 6;3;6;8
OK
> place 3;2;1;7
OK
> place 6;4;6;7
OK
> reset
OK
> place 6;3;6;8
OK
> place 3;2;1;7
OK
> place 6;4;6;7
OK
> place 6;9;6;2
OK
> place 6;5;6;6
P1 wins
> rowprint 6
** ** P2 P1 P1 P1 P1 P1 P1 P2 ** ** ** ** ** ** ** **
> quit
```







