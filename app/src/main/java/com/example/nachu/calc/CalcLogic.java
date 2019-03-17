package com.example.nachu.calc;


import android.util.Log;

public class CalcLogic {
    private int count=1,flag=0;
        private int pos=-1,ch;
        String exp;

    public CalcLogic(String exp) {
        if(exp.contains("π"))
        {
            exp=exp.replaceAll("π","("+String.valueOf(Math.PI)+")");
        }
        if(exp.contains("e"))
        {
            exp=exp.replaceAll("e","("+String.valueOf(Math.E)+")");
        }
        this.exp = exp;
        nextChar();
    }

    public int getCh() {
        return ch;
    }

    public boolean eatChar(char ch) {
            while (ch == ' ') nextChar();
            if (this.ch == ch) {
                nextChar();
                return true;
            }
            return false;
        }

        public void prevChar()
        {
          ch=(--pos>=0)?exp.charAt(pos):-1;
        }
        public void nextChar()
        {
            ch=(++pos<exp.length())?exp.charAt(pos):-1;
        }


        public double evalExpression() {
            //Log.d("mytag","in evalExpression");
            double x = eval_Multiply_Divide();
            while (true) {
                if (eatChar('+')) x += eval_Multiply_Divide();
                else if (eatChar('-')) x -= eval_Multiply_Divide();
                else if(eatChar('(')){x*=eval_Unarys_And_Nested_Exp();eatChar(')');}
                else return x;

            }
        }

        public double eval_Multiply_Divide()
        {
            //Log.d("mytag","in eval_Multi_divide");
            double x = eval_Unarys_And_Nested_Exp();
            while(true) {
                if (eatChar('x')) x *= eval_Unarys_And_Nested_Exp();
                else if (eatChar('/')) x /= eval_Unarys_And_Nested_Exp();
                else return x;
            }
        }
        public double eval_Unarys_And_Nested_Exp() {
            double x;
           // Log.d("mytag",""+(++count));
            if (eatChar('+')) return eval_Unarys_And_Nested_Exp();  //for Unary +
            else if (eatChar('-')) return -eval_Unarys_And_Nested_Exp(); //for Unary -

            int startPos = pos;          //for substring() function used below
            //Log.d("mytag","pos="+pos+"startPos="+startPos);
            if (eatChar('(')) {
                x = evalExpression();
                eatChar(')');
                //Log.d("mytag","ch="+(char)ch);
            } else if ((ch >= '0' && ch <= '9') || ch == '.')   //for numbers and decimal
            {
                while ((ch >= '0' && ch <= '9') || ch == '.') {
                    nextChar();
                }
                //Log.d("mytag","here "+pos);
                x = Double.parseDouble(exp.substring(startPos, pos));
            } else if ((ch >= 'a' && ch <= 'z' && ch!='e') || ch == '√' || ch == '!') {
                //Log.d("mytag","flag:"+(++flag));
                startPos = pos;
                while ((ch >= 'a' && ch <= 'z') || ch == '√' || ch == '!'||ch=='(') nextChar();
                String func = exp.substring(startPos, pos);
                //Log.d("mytag",func+"  ch:"+(char)ch);
                if(func.contains("(("))func=func.replace("((","(");
                x = evalExpression();
                switch (func) {
                    case "sin(":
                        x = Math.sin(x);
                        //Log.d("mytag","in sin() x="+x);
                        eatChar(')');
                        break;
                    case "cos(":
                        x = Math.cos(x);
                        eatChar(')');
                        break;
                    case "tan(":
                        x = Math.tan(x);
                        eatChar(')');
                        break;
                    case "ln (":
                        x = Math.log(x);
                        eatChar(')');
                        break;
                    case "log(":
                        x = Math.log10(x);
                        eatChar(')');
                        break;
                    case "√":
                        x = Math.sqrt(x);
                        eatChar(')');
                        break;
                }
            } else
                throw new RuntimeException("Arrrr... error ocuured" + (char) ch);
            if (eatChar('^')) {
                x = Math.pow(x, eval_Unarys_And_Nested_Exp());
            }
            else if (eatChar('!')) {
                x = factorialOf(x);
            }

            return x;
        }

        public double factorialOf(double x)
        {
            if(x==1)
                return 1;
            else
                if(x==0)
                    return 1;
            else
            {
                x*=factorialOf(x-1);
                return x;
            }
        }
}
