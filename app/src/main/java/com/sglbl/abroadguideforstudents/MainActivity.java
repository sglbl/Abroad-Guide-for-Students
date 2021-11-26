package com.sglbl.abroadguideforstudents;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.widget.Toast;

@SuppressWarnings("UnusedAssignment")
@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int boardSize = 8;
    private int level = 1;
    private char turn = 'x'; // Whose turn.
    private int how_many_player = 1;
    private AppCompatButton[][] buttons = new AppCompatButton[boardSize][boardSize];
    private AppCompatButton oldMove,oldMove2;
    private TextView label;
    private Button reset, undo, load, save;

    //MAIN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent(); //for info transferring between activities.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            boardSize = Integer.parseInt(intent.getStringExtra(Main.EXTRA_NUMB));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        if( intent.getStringExtra(Main.EXTRA_TEXT).equals("One Player Game"))
            how_many_player = 1;
        else if(intent.getStringExtra(Main.EXTRA_TEXT).equals("Two Players Game") )
            how_many_player = 2;

        label = (TextView)findViewById(R.id.text);

        addIDs();
    } //End of onCreate function.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i;
        i = getMenuInflater();
        i.inflate(R.menu.dots_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            if( deleteFile("hexgame.txt") ) {
                Toast.makeText(this, "File succesfully deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
            else
                Toast.makeText(this, "There is no file already", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.difficulty) {
            level = 1;
            if(how_many_player==1) Toast.makeText(this, "Difficulty set to easy", Toast.LENGTH_SHORT).show();
            if(how_many_player==2) Toast.makeText(this, "Game type isn't 1 player game", Toast.LENGTH_SHORT).show();

        }

        if(item.getItemId() == R.id.difficulty2) {
            level = 2;
            if(how_many_player==1) Toast.makeText(this, "Difficulty set to medium", Toast.LENGTH_SHORT).show();
            if(how_many_player==2) Toast.makeText(this, "Game type isn't 1 player game", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public void addIDs(){
//
//            buttonIdCode = getResources().getIdentifier(btn, "id", getPackageName());
//            buttons[i][j] = ((AppCompatButton) findViewById(buttonIdCode));
//            buttons[i][j].setOnClickListener(this);
//            buttons[i][j].setText(".");
//            buttons[i][j].setTextColor( ContextCompat.getColor(MainActivity.this, android.R.color.transparent ) );
//

        label = (TextView)findViewById(R.id.text);
        reset = (Button)findViewById(R.id.bReset);
        undo = (Button)findViewById(R.id.bUndo);
        load = (Button)findViewById(R.id.bLoad);
        save = (Button)findViewById(R.id.bSave);
        fourButtonClick();

    }   //End of setOnClick method

    public void fourButtonClick(){
        reset.setOnClickListener(new View.OnClickListener() {
            GradientDrawable background_without_border;
            @Override
            public void onClick(View v) {   //When RESET clicked
                for(int i=0; i<boardSize; i++)
                    for(int j=0; j<boardSize; j++){
                        background_without_border = (GradientDrawable) buttons[i][j].getBackground();
                        background_without_border.setColor( ContextCompat.getColor(MainActivity.this, R.color.gray1) );
                        buttons[i][j].setText(".");
                    }
                turn = 'x';
                label.setText(turn + "'s turn");
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            GradientDrawable background_without_border, background_without_border2;
            @Override
            public void onClick(View v) {   //When UNDO clicked
                // do sth
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile("hexgame.txt");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("hexgame.txt");     //Writing board to a file called hexgame.txt
            }
        });

    }

    @Override
    public void onClick(View v) {
        // call some methods
    }

    public void writeToFile(String file){   //Writing to file
        int y=-1, x=-1, y2=-1, x2=-1;
        BufferedWriter bufferString;
        try {
            bufferString= new BufferedWriter( new FileWriter(getFilesDir() + "/" + file) );
            System.out.println(getFilesDir());
            bufferString.write( Integer.toString(boardSize) );      /*Writing boardSize */ bufferString.write("\n");
            bufferString.write( Integer.toString(how_many_player) ); /*Writing how many player game */ bufferString.write("\n");
            bufferString.write( Character.toString(turn) );   /*Writing whose turn */ bufferString.write("\n");

            for(int i=0; i<boardSize; i++){ //Writing cell states
                for(int j=0; j<boardSize; j++){
                    bufferString.write((String) buttons[i][j].getText());
                }
                bufferString.write("\n");
            }

            bufferString.write( Character.toString((char)(x + 65)) + (y+1) + oldMove.getText() );   //Writing old move(For ex:  A1x)
            bufferString.write("\n");
            if(how_many_player==1)
                bufferString.write( Character.toString((char)(x2 + 65)) + (y2+1) + oldMove2.getText() );   //Writing old move2(computers move too)
            Toast.makeText(getApplicationContext(),"File saved to the device.",Toast.LENGTH_SHORT).show();
            bufferString.close();
        }  //End of try
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error in opening file, make sure file permission is granted.",Toast.LENGTH_SHORT).show();
        }   //End of catch
    }  //End of method

    public void readFromFile(String file){
        GradientDrawable background_without_border;
        String readedLine = ".";
        BufferedReader bufferString;
        int oldboardSize = boardSize;
        try {
            bufferString= new BufferedReader( new FileReader(getFilesDir() + "/" + file) );
            readedLine = bufferString.readLine();   //Reading boardSize
            if     (readedLine.length() == 1)    boardSize = (int)readedLine.charAt(0) - 48;
            else if(readedLine.length() == 2)    boardSize = ((int)readedLine.charAt(0) - 48)*10 + ((int)readedLine.charAt(1) -48);

            readedLine = bufferString.readLine();   //Reading how many player game
            how_many_player = (int)readedLine.charAt(0)-48;

            readedLine = bufferString.readLine();   //Reading whose turn
            turn = readedLine.charAt(0);
            bufferString.close();
            Toast.makeText(getApplicationContext(),"File loaded from device.",Toast.LENGTH_SHORT).show();
        }  //End of try
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error in reading file, make sure file permission is granted and board was saved.",Toast.LENGTH_SHORT).show();
        }   //End of catch
    }      //End of method



} //End of class