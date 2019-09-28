# HelixSnake

<p>The game is played controlling a moving snake in 25*60 area. There are four letters which are A (Adenine),
C (Cytosine), G (Guanine) and T (Thymine) in the game area. The snake starts with 3 letters assigned
randomly out of four letter (A, C, G, T). When the snake eats these letters, it becomes longer. If the snake
bumps into a wall or its own body the game is over.
  
  There are twenty amino acids found in proteins. Amino acids are represented with codons. Each codon
contains 3 letter of the DNA coding units T, C, A and G. All 64 possible 3-letter combinations of T, C, A and
G are used either to encode one of these amino acids or as one of the three stop codons that signals the
end of a sequence.</p>

<b>Detail Information</b>
<ul>
<li>The snake starts with 3 letters assigned randomly out of four letter (A, C, G, T).</li>

<li>There are 3 letters randomly generated in the game area at the start. When the snake eats a
letter, a new letter is generated in the game area to maintain starting number of letters.</li>

<li>The snake is moved by the player using arrow keys. The snake is kept as Single Linked
List (SLL), so it is not able to move back. Every move take half of a second.</li>
<li>When the snake eats a letter, the letter is added to the front (head) of the snake.</li>
<li>When the snake eats a letter, the player earns 5 points. The player is also earns extra points when
the snake completes an amino-acid codon that was given in the input file.</li>
<li>All amino acids with their corresponding codons and points are given in “aminoacids.txt” file and this
information is added to a Multi Linked List (MLL).</li>
</ul>

<img src="/Gameplay.gif" width=1647 />
