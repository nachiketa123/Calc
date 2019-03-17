package com.example.nachu.calc;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageButton imgB,imgC,swipe;
    boolean flag;
    static int ARROW_RIGHT=R.drawable.swipe_arrow;
    static int ARROW_LEFT=R.drawable.swipe_arrow2;
    LinearLayout sinLayout,lnLayout,img_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sinLayout = findViewById(R.id.sinLayout);
        lnLayout = findViewById(R.id.lnLayout);
        img_layout=findViewById(R.id.img_layout);
        tv=findViewById(R.id.tv);
        imgB=findViewById(R.id.delete);
        imgC=findViewById(R.id.clear);
        swipe=findViewById(R.id.swipe);
        tv.setMovementMethod(new ScrollingMovementMethod());
        int orientation = this.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            flag=true;
            hide();
            img_layout.setOrientation(LinearLayout.VERTICAL);
            }
        else{flag=true;show();}
        tv.addTextChangedListener(new TextWatcher() {
            int a,countDot=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Log.d("mytag","beforeTextChanged s:"+s+" start:"+start+" count:"+count+" after:"+after);
            a=0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    //Log.d("mytag", "OnTextChanged" + s + " start:" + start + " count:" + count);
                    String str = s.toString();
                    if(str.length()>1) {
                        if (str.charAt(str.length()- 2) == '+' || str.charAt(str.length() - 2) == '.'|| str.charAt(str.length() - 2) == '-' || str.charAt(str.length() - 2) == 'x' || str.charAt(str.length() - 2) == '/')
                        {
                            //Log.d("mytag", "In If");
                            a = 1;
                        }
                    }
                    if(str.length()==0)countDot=0;
                    if(str.length()>=1)
                    {
                        if(str.charAt(str.length()-1)=='.'){
                            countDot++;
                        }
                        if(str.charAt(str.length()-1)=='('||str.charAt(str.length()-1)==')'||str.charAt(str.length()-1)=='+'||str.charAt(str.length()-1)=='-'||str.charAt(str.length()-1)=='x'||str.charAt(str.length()-1)=='/')countDot=0;

                    }
                }catch(Exception e){//Log.d("mytag","arrrrr222"+e);
                                   }
            }
            boolean flag=false;
            @Override
            public void afterTextChanged(Editable s) {
                if(flag)return;
                flag=true;
               // Log.d("mytag","afterTextChanged");
                try {

                    if (a == 1) {String str = s.toString();
                        if (str.charAt(str.length()-1) == '+' || str.charAt(str.length() - 1) == '.'|| str.charAt(str.length()-1) == '-' || str.charAt(str.length()-1) == 'x' || str.charAt(str.length()-1) == '/') {
                            //Log.d("mytag", "chala....");
                            str=str.replace(str.charAt(str.length() - 2), str.charAt(str.length()-1));
                            str=str.substring(0,str.length()-1);
                            //Log.d("mytag", "str:"+str);
                            tv.setText(str);
                        }
                    }
                    if(countDot>1){tv.append("(Invalid Input)");
                    countDot=0;
                   // Log.d("mytag","string:");
                    }
                }catch (Exception e){//Log.d("mytag","arrr..."+e);
                                     }
                flag=false;
            }
        });
        imgB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv.setText("");
                return true;
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
            hide();
        }
        else
        {
            img_layout.setOrientation(LinearLayout.HORIZONTAL);
            if(img_layout.getOrientation()==LinearLayout.HORIZONTAL)//Log.d("mytag","horizontal");
            show();
        }

    }

    public void process(View v)
    {
      switch(v.getId())
      {
          case R.id.clear:
              tv.setText("");
          case R.id.delete:
              String str= tv.getText().toString();
              try {
                  if(str.contains("(Invalid Input)")){str=str.replace("(Invalid Input)","");
                      str=str.substring(0,str.length()-14);
                      Log.d("mytag","str:"+str);}
                  if (str.charAt(str.length() - 1) == '(') {
                      if ((str.charAt(str.length() - 2) >= 'a' && str.charAt(str.length() - 2) <= 'z') || str.charAt(str.length() - 2) == ' ')
                          tv.setText(str.substring(0, str.length() - 4));
                      else
                          tv.setText(str.substring(0, str.length() - 1));
                  } else {
                      tv.setText(str.substring(0, str.length() - 1));
                  }
              }catch(Exception e){
                  if(str.length()>0){tv.setText(str.substring(0, str.length() - 1));}
              }
              break;
          case R.id.sin:
              tv.append("sin(");
              break;
          case R.id.cos:
              tv.append("cos(");
              break;
          case R.id.tan:
              tv.append("tan(");
              break;
          case R.id.pi:
              tv.append("π");
              break;
          case R.id.fact:
              tv.append("!");
              break;
          case R.id.ln:
              tv.append("ln (");
              break;
          case R.id.log:
              tv.append("log(");
              break;
          case R.id.exp:
              tv.append("e");
              break;
          case R.id.cap:
              tv.append("^");
              break;
          case R.id.root:
              tv.append("√");
              break;
          case R.id.one:
              tv.append("1");
              break;
          case R.id.two:
              tv.append("2");
              break;
          case R.id.three:
              tv.append("3");
              break;
          case R.id.four:
              tv.append("4");
              break;
          case R.id.five:
              tv.append("5");
              break;
          case R.id.six:
              tv.append("6");
              break;
          case R.id.seven:
              tv.append("7");
              break;
          case R.id.eight:
              tv.append("8");
              break;
          case R.id.nine:
              tv.append("9");
              break;
          case R.id.zero:
              tv.append("0");
              break;
          case R.id.dot:
              tv.append(".");
              break;
          case R.id.divide:
              tv.append("/");
              break;
          case R.id.multiply:
              tv.append("x");
              break;
          case R.id.plus:
              tv.append("+");
              break;
          case R.id.minus:
              tv.append("-");
              break;
          case R.id.brOpen:
              tv.append("(");
              break;
          case R.id.brClose:
              tv.append(")");
              break;
          case R.id.swipe:
              show();
      }
    }
    public void action(View v)
    {
        //Log.d("mytag","in onClick=");
        try {
            CalcLogic obj = new CalcLogic(tv.getText().toString());
            double x = obj.evalExpression();
            while(obj.getCh()!=-1)
            {
                if(obj.eatChar(')')){}
                else x*=obj.eval_Unarys_And_Nested_Exp();
            }
             tv.setText(""+x);
        }catch(Exception e)
        {
          //  Log.d("mytag","errorMsg"+e);
        }
    }


    public void hide()
    {

           sinLayout.setVisibility(View.GONE);
           lnLayout.setVisibility(View.GONE);
           swipe.setImageResource(MainActivity.ARROW_RIGHT);

    }
    public void show()
    {
        if(flag) {
            sinLayout.setVisibility(View.VISIBLE);
            lnLayout.setVisibility(View.VISIBLE);
            swipe.setImageResource(MainActivity.ARROW_LEFT);
            flag=false;
        }
        else
        {
            hide();
            flag=true;
        }
    }

}
