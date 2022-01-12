package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static class Move
    {
        int row, col,score;
    };

    static boolean winning1(char board[][],char player,int rows,int columns){
        int sum=0;
        for(int i = 0;i<rows;i++){
            sum=0;
            for(int j=0 ; j<columns ;j++){
                if(board[i][0]==player){
                    if(board[i][0]==board[i][j]){
                        sum++;
                    }
                }
            }
            if(sum==columns){
                return true;
            }
        }

        for(int i=0;i<rows;i++){
            sum=0;
            for(int j=0 ; j<rows;j++){
                if(board[0][i] == player){
                    if(board[0][i]==board[j][i]){
                        sum++;
                    }
                }
            }
            if(sum == columns){
                return true;
            }
        }

        sum=0;

        for(int i=0;i<rows;i++) {
            if(board[0][0] == player){

                if(board[0][0]==board[i][i]){
                    sum++;
                }
            }

        }

        if(sum==columns)
            return true;




        sum=0;

        for(int i=0,j=rows-1;i<rows;i++,j--){
            if(board[0][columns-1]==player){
                if(board[0][columns-1] == board[i][j]){
                    sum++;
                }
            }
        }
        if(sum==columns)
            return true;

        return false;
    }



    static ArrayList<Move> getAllMoves(char board[][],int rows, int columns){
        ArrayList<Move> moves=new ArrayList<Move>();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                if(board[i][j]=='_'){
                    Move v = new Move();
                    v.row = i;
                    v.col=j;
                    moves.add(v);
                }
            }
        }
        return moves;
    }

    static Move minMax(char board[][],int rows, int columns,char player, int alpha, int beta,int depth){
        ArrayList<Move> allmoves = getAllMoves(board,rows,columns);
        int score = evaluate(board,rows,columns);

        Move m =new Move();

        if (winning1(board, 'O',rows,columns)){
            m.score =-10;
            return m;
        }
        else if(winning1(board, 'X',rows,columns)){
            m.score = 10;
            return m;
        }
        else if(allmoves.size()==0){
            m.score = 0;
            return m;
        }
        else if (depth==0){
            m.score = 0;
            return m;
        }
        ArrayList<Move> moves = new ArrayList<Move>();

        for(int i=0;i<allmoves.size();i++){
            char a = board[allmoves.get(i).row][allmoves.get(i).col];
            Move m1 = new Move();
            m1.row = allmoves.get(i).row;
            m1.col = allmoves.get(i).col;

            board[allmoves.get(i).row][allmoves.get(i).col]=player;
            if(player == 'X'){
                Move v = minMax(board,rows,columns,'O',alpha,beta,depth-1);
                m1.score=v.score;
            }else{
                Move v = minMax(board,rows,columns,'X',alpha,beta,depth-1);
                m1.score = v.score;
            }
            board[m1.row][m1.col]='_';
            moves.add(m1);
        }

        Move bestMov = new Move() ;
        int bestMove = 0;

        if(player == 'X') {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestMov = moves.get(i);
                    bestScore = moves.get(i).score;
                    bestMove=i;
                }
            }
        }else{
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestMov = moves.get(i);
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }

        }

        return bestMov;
        //return moves.get(bestMove);
    }


    static Boolean isMovesLeft(char board[][],int rows, int columns){
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    static int evaluate(char board[][],int rows, int columns) {

        for(int i=0;i<rows;i++){
            int sum=0;
            char winSymbol = ' ';
            for(int j=0;j<columns;j++){
                if(board[i][0] == 'O' || board[i][0] == 'X' ){
                    winSymbol=board[i][0];
                    if(board[i][0] == board[i][j]){
                        sum++;
                    }

                }
            }
            if(sum==columns){
                if(winSymbol=='O')
                    return -10;
                else if (winSymbol=='X')
                    return 10;
            }
        }

        for(int i=0;i<rows;i++){
            int sum=0;
            char winSymbol = ' ';
            for(int j=0;j<columns;j++){
                if(board[0][i] == 'O' || board[0][i] == 'X' ){
                    winSymbol=board[0][i];
                    if(board[0][i] == board[j][i]){
                        sum++;
                    }

                }
            }
            if(sum==columns){
                if(winSymbol=='O')
                    return -10;
                else if (winSymbol=='X')
                    return 10;
            }
        }

        int sum=0;
        char winSymbol=' ';
        for(int i=0;i<rows;i++) {
            if(board[0][0] == 'O' || board[0][0] == 'X' ){
                winSymbol=board[0][0];
                if(board[0][0]==board[i][i]){
                    sum++;
                }
            }

        }

        if(sum==rows){
            if(winSymbol=='O'){
                return -10;
            }else if(winSymbol=='X'){
                return 10;
            }
        }

        winSymbol=' ';
        sum=0;
        for(int i=0,j=rows-1;i<rows;i++,j--){
            if(board[0][columns-1]=='X' || board[0][columns-1]=='O'){
                winSymbol=board[i][j];
                if(board[0][columns-1] == board[i][j]){
                    sum++;
                }
            }
        }

        if(sum==rows){
            if(winSymbol=='O'){
                return -10;
            }else if(winSymbol=='X'){
                return 10;
            }
        }

        return 0;
    }


    static int minimax(char board[][],
                       int depth, Boolean isMax,int rows, int columns){
        int score = evaluate(board,rows,columns);
        if (score == 10)
            return score;
        if (score == -10)
            return score;

        if (isMovesLeft(board,rows,columns) == false)
            return 0;
        if (isMax)
        {
            int best = -1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j]=='_')
                    {
                        board[i][j] = 'X';
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax,rows,columns));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        else
        {
            int best = 1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j] == '_')
                    {
                        board[i][j] = '0';
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax,rows,columns));

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(char board[][],int rows,int columns)
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {

                if (board[i][j] == '_')
                {

                    board[i][j] = 'X';
                    int moveVal = minimax(board, 0, false,rows,columns);

                    board[i][j] = '_';

                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    public static int checkWin(char[][] board, int rows, int columns){

        for(int i=0;i<rows;i++){
            int sum=0;
            char winSymbol = ' ';
            for(int j=0;j<columns;j++){
                if(board[i][0] == 'O' || board[i][0] == 'X' ){
                    winSymbol=board[i][0];
                    if(board[i][0] == board[i][j]){
                        sum++;
                    }

                }
            }
            if(sum==columns){
                if(winSymbol=='O')
                    return 1;
                else if (winSymbol=='X')
                    return 2;
            }
        }


        for(int i=0;i<rows;i++){
            int sum=0;
            char winSymbol = ' ';
            for(int j=0;j<columns;j++){
                if(board[0][i] == 'O' || board[0][i] == 'X' ){
                    winSymbol=board[0][i];
                    if(board[0][i] == board[j][i]){
                        sum++;
                    }

                }
            }
            if(sum==columns){
                if(winSymbol=='O')
                    return 1;
                else if (winSymbol=='X')
                    return 2;
            }
        }

        int sum=0;
        char winSymbol=' ';
        for(int i=0;i<rows;i++) {
            if(board[0][0] == 'O' || board[0][0] == 'X' ){
                winSymbol=board[0][0];
                if(board[0][0]==board[i][i]){
                    sum++;
                }
            }

        }

        if(sum==rows){
            if(winSymbol=='O'){
                return 1;
            }else if(winSymbol=='X'){
                return 2;
            }
        }

        winSymbol=' ';
        sum=0;
        for(int i=0,j=rows-1;i<rows;i++,j--){
            if(board[0][columns-1]=='X' || board[0][columns-1]=='O'){
                winSymbol=board[i][j];
                if(board[0][columns-1] == board[i][j]){
                    sum++;
                }
            }
        }

        if(sum==rows){
            if(winSymbol=='O'){
                return 1;
            }else if(winSymbol=='X'){
                return 2;
            }
        }

        return 0;
    }

    public static void drawBoard(char board[][],int rows, int columns){
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void drawSampleBoard(int[] board,int rows,int columns){
        System.out.println("Sample Board. Input your index according to this!");
        for(int i=0;i<rows*columns;i++){
            System.out.print(board[i]);
            System.out.print("  ");
            if((i+1)%columns==0){
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }

    public static void game(){
        // write your code here

        Scanner myObj1 = new Scanner(System.in);
        System.out.println("Enter number of rows of the board : ");
        int input = myObj1.nextInt();



        int rows = input;

        System.out.println("Enter number of columns : ");
        input = myObj1.nextInt();

        int columns = input;
        char [][] board = new char[rows][columns];

        int[] sampleBoard = new int[rows * columns];

        for(int i=0;i<rows*columns;i++){
            sampleBoard[i]=i+1;
        }

        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                board[i][j]='_';
            }
        }

        while(true) {
            drawSampleBoard(sampleBoard, rows, columns);
            drawBoard(board, rows, columns);

            if(!isMovesLeft(board,rows,columns)){
                System.out.println("Tie!");
                break;
            }

            int row =0;int column =0;
            while(true) {
                Scanner myObj = new Scanner(System.in);
                System.out.println("Input index: ");
                int index = myObj.nextInt();
                index = index - 1;
                row = index / rows;
                column = index % rows;

                if(board[row][column]=='_'){
                    break;
                }
                else{
                    System.out.println("Already filled!");
                }
            }
            board[row][column] = 'O';

            if(winning1(board,'O',rows,columns)){
                drawBoard(board,rows,columns);
                System.out.println("You Win!");
                break;
            }

            Move m =minMax(board,rows,columns,'X',-10000,10000,3);
            board[m.row][m.col]='X';

            if(winning1(board,'X',rows,columns)){
                drawBoard(board,rows,columns);
                System.out.println("You Lose!");
                break;
            }

        }

    }

    public static void main(String[] args) {
            game();
    }


}





