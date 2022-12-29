
<h1> Poker Evaluator ver 1.0 </h1>

## explanations

### Card encoding scheme
* xxxnnnnn | nnnnnnnn | cdhsrrrr | xxpppppp  
* n - bit of the rank AKQJT98765432  
* cdhs - bit of the suit  
* rrrr - rank of the card  
* pppppp - prime rank of the card  

### Hand Ranks
* 01 - Royal Flush     ( AKQJTs )  
* 02 - Straight Flush  ( JT987s )  
* 03 - Four of a kind  ( KKKK2  )  
* 04 - Full house      ( QQQ99  )  
* 05 - Flush           ( AJ872s )  
* 06 - Straight        ( A2345  )  
* 07 - Three of a kind ( 777KQ  )  
* 08 - Two pair        ( 7788K  )  
* 09 - Pair            ( KKJT3  )  
* 10 - High card       ( KJT82  )  
  
## Misc calc

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
 