package com.example.hpe006.braintrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton ;
    ImageView brain;
    TextView pointsTextView;
    Button choiceButton1;
    Button choiceButton2;
    Button choiceButton3;
    Button choiceButton4;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    TextView qTextView;
    TextView resultTextView;
    int score = 0;
    int totalTurns = 0;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;

    public void playAgain(View view){

        score = 0;
        totalTurns = 0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        choiceButton1.setEnabled(true);
        choiceButton2.setEnabled(true);
        choiceButton3.setEnabled(true);
        choiceButton4.setEnabled(true);

        newQuestion();

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.hpe006.braintrainer", Context.MODE_PRIVATE);


        playAgainButton.setVisibility(View.INVISIBLE);

        new CountDownTimer(30100,1000){


            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {

                int hScore = sharedPreferences.getInt("hscore",0);
                int turnsOnHscore = sharedPreferences.getInt("turnsOnHscore",0);

                if(score> hScore){
                    sharedPreferences.edit().putInt("hscore",score).apply();
                    sharedPreferences.edit().putInt("turnsOnHscore",totalTurns).apply();
                    resultTextView.setText("Great Job!!  " + Integer.toString(score) + "/" + Integer.toString(totalTurns) +"\n"
                                        + "You've beaten the highest score");
                }

                else{
                    resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(totalTurns) + "\n"
                            + "Highest Score: "+  Integer.toString(hScore) + "/" + Integer.toString(turnsOnHscore));
                }

                timerTextView.setText("0s");
                playAgainButton.setVisibility(View.VISIBLE);
                choiceButton1.setEnabled(false);
                choiceButton2.setEnabled(false);
                choiceButton3.setEnabled(false);
                choiceButton4.setEnabled(false);
            }
        }.start();
    }


    public void newQuestion(){
        Random any = new Random();

        int a = any.nextInt(30) + 16;
        int b = any.nextInt(30) + 4;
        int c = any.nextInt(2);
        int incorrectAnswer;

        locationOfCorrectAnswer = any.nextInt(4);

        answers.clear();

        if(c==0) {

            qTextView.setText(Integer.toString(a) + " + " + Integer.toString(b) + " ?");

            for (int i = 0; i < 4; i++) {

                if (i == locationOfCorrectAnswer) {
                    answers.add(a + b);
                } else {
                    incorrectAnswer = any.nextInt(60) + 16;

                    while (incorrectAnswer == a + b) {
                        incorrectAnswer = any.nextInt(60) + 16;
                    }

                    answers.add(incorrectAnswer);
                }
            }
        }

        else {

            qTextView.setText(Integer.toString(a) + " - " + Integer.toString(b) + " ?");

            for (int i = 0; i < 4; i++) {

                if (i == locationOfCorrectAnswer) {
                    answers.add(a - b);
                } else {
                    incorrectAnswer = any.nextInt(55) - 16;

                    while (incorrectAnswer == a - b) {
                        incorrectAnswer = any.nextInt(55) - 16;
                    }

                    answers.add(incorrectAnswer);
                }
            }

        }



        choiceButton1.setText(Integer.toString(answers.get(0)));
        choiceButton2.setText(Integer.toString(answers.get(1)));
        choiceButton3.setText(Integer.toString(answers.get(2)));
        choiceButton4.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view){

        totalTurns++;

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct");
        }
        else {
            resultTextView.setText("Incorrect");
        }

        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(totalTurns));
        newQuestion();
    }

    public void go(View view){
        goButton.setVisibility(View.INVISIBLE);
        brain.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.hpe006.braintrainer", Context.MODE_PRIVATE);

        //int hScore = sharedPreferences.getInt("hscore",0);

        goButton = (Button) findViewById(R.id.goButton);
        brain = (ImageView) findViewById(R.id.brain);
        qTextView = (TextView) findViewById(R.id.qTextView);
        choiceButton1 = (Button)findViewById(R.id.choiceButton1);
        choiceButton2 = (Button)findViewById(R.id.choiceButton2);
        choiceButton3 = (Button)findViewById(R.id.choiceButton3);
        choiceButton4 = (Button)findViewById(R.id.choiceButton4);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);

    }
}
