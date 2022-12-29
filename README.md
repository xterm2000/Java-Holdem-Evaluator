<<<<<<< HEAD
# Poker Evaluator ver 1.0

# explanations 
# Card encoding scheme
xxxnnnnn | nnnnnnnn | cdhsrrrr | xxpppppp
n - bit of the rank akqjt98765432
cdhs - bit of the suit
rrrr - rank of the card 
pppppp - prime rank of the card
=======
# poker evaluator v1.0
>>>>>>> 4fe0388c43fcdf967e38e3254f5206a7be3caad3

## Card encoding scheme
* xxxnnnnn | nnnnnnnn | cdhsrrrr | xxpppppp </br>
* n - bit of the rank AKQJT98765432 </br>
* cdhs - bit of the suit </br>
* rrrr - rank of the card  </br>
* pppppp - prime rank of the card </br>

## Hand Ranks
* 01 - Royal Flush     ( AKQJTs ) </br>
* 02 - Straight Flush  ( JT987s ) </br>
* 03 - Four of a kind  ( KKKK2  ) </br>
* 04 - Full house      ( QQQ99  ) </br>
* 05 - Flush           ( AJ872s ) </br>
* 06 - Straight        ( A2345  ) </br>
* 07 - Three of a kind ( 777KQ  ) </br>
* 08 - Two pair        ( 7788K  ) </br>
* 09 - Pair            ( KKJT3  ) </br>
* 10 - High card       ( KJT82  ) </br>

## Misc calc
<p>
Straights: 
xxx1 1111 0000 0000 
xxx0 1111 1000 0000
...
xxx0 0000 0001 1111
xxx1 0000 0000 1111

4111 - Five High SF (Bicycle)
31   - Six high SF
62   - Seven high SF 
124  - Eight high SF
248  - Nine high SF
496  - Ten high SF
992  - Jack high SF
1984 - Queen high SF
3968 - King high SF
7936 - Royal flush

    
</p>
