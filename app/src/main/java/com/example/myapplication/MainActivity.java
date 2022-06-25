package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static File bookfile;
    public static int co = 0;
    public static String[] recipenames = new String[20];
    String name = "";
    String[][][] recipes = new String[20][4][20];
    int[][] percent = new int[2][20];
    String[][][] book = new String[100][4][100];
    JSONArray bookArray = new JSONArray();
    String[] bookRecipeNames = new String[20];
    String[][] AddRecipeIngredients = new String[100][2];
    int counter = 0;
    String[] bookentry = new String[100];
    int counter2 = 0;
    String[] methodsteps = new String[100];
    String[] items = new String[100];
    int count = 0;
    int yee = 0;
    int id = 0;
    String method = "";
    int recipeId = 0;
    int height = 0;
    int width = 0;
    int cards = 0;
    String step = "";
    int bookentries = 0;
    int counter3 = 0;
    int buttoncheck = 0;
    String[][][] anotherArray = new String[100][100][100];
    int stepcounter = 0;
    String[] steps = new String[100];

    public static String[][][] removeTheElement(String[][][] book, int finalX1) {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (book == null || finalX1 < 0
                || finalX1 >= book.length) {

            return book;
        }

        // Create another array of size one less
        String[][][] anotherArray = new String[book.length - 1][][];

        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < book.length; i++) {

            // if the index is
            // the removal element index
            if (i == finalX1) {
                continue;
            }

            // if the index is not
            // the removal element index
            anotherArray[k++] = book[i];
        }

        // return the resultant array
        return anotherArray;
    }

    static JSONArray removeFromJsonArray(JSONArray jsonArray, int removeIndex) {
        JSONArray _return = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (i != removeIndex) {
                try {
                    _return.put(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return _return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        co++;
        //File book;
        initialiseJSON();
        height = this.getResources().getDisplayMetrics().heightPixels;
        width = this.getResources().getDisplayMetrics().widthPixels;
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcomescreen);
        Button welcome = findViewById(R.id.Welcome);
        welcome.setHeight(height - 1200);
//        RelativeLayout.LayoutParams layoutParams = new LayoutPara
//        welcome.setLayoutParams((56/1080 * width), 100);
        //    welcome.setMaxWidth(width - 1200);
        welcome.setMinHeight(height - 1000);


        try {
            //  String yuh = readFromFile();


            //  System.out.println("string from file: " + yuh);
            bookArray = new JSONArray(readFromFile());
            bookentries = bookArray.length();
            System.out.println("book entries: " + bookentries);
            System.out.println(bookArray);
//            System.out.println(bookArray);
            JSONArray jArray = new JSONArray(readJSONFromAsset());

            for (int i = 0; i < jArray.length(); ++i) {
                String name = jArray.getJSONObject(i).getString("recipeName");
                recipenames[i] = name;
                System.out.println(recipenames[i]);
                System.out.println("name " + name);
                System.out.println("array yuh " + book[0][0][0]);


                JSONArray ingredients = jArray.getJSONObject(i).getJSONArray("recipeIngredients");
                JSONArray quantities = jArray.getJSONObject(i).getJSONArray("quantities");
                for (int j = 0; j < ingredients.length(); j++) {
                    String ingredient = ingredients.getString(j);

                    recipes[i][0][j] = ingredient;
                    String quantity = quantities.getString(j);

                    recipes[i][1][j] = quantity;

                }

            }
            for (int i = 0; i < bookentries; ++i) {
                try {
                    String recipename = bookArray.getJSONObject(i).getString("recipeName");
                    System.out.println(recipename);
                    recipeId = bookArray.getJSONObject(i).getInt("recipeId");
                    bookentry[i] = recipename;
                    bookRecipeNames[i] = recipename;
                } catch (Exception e) {
//                    System.out.println("stupid");
                }

//                System.out.println(bookentry[i]);

//                JSONArray ingredients = bookArray.getJSONObject(i).getJSONArray("recipeIngredients");
//                JSONArray quantities = bookArray.getJSONObject(i).getJSONArray("quantities");
//                for (int j = 0; j < ingredients.length(); j++) {
//                    String ingredient = ingredients.getString(j);
//                    book[i][0][j] = ingredient;
////                    System.out.println("(book[i][0][j] " + book[i][0][j]);
//                    String quantity = quantities.getString(j);
//                    book[i][1][j] = quantity;
////                    System.out.println("book[i][1][j] " + book[i][1][j]);
//
//                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() {
//        System.out.println("Height " + height);
//        System.out.println("width " + width);
        String ret = "";
        InputStream inputStream;
        try {
            inputStream = this.openFileInput("book.json");
           // System.out.println("yuh");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                ret = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

//        String line = null;
//        String path = null;
//        try {
//            FileReader fileReader = new FileReader(bookfile);
//            StringBuffer output = new StringBuffer();
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            while ((line = bufferedReader.readLine()) != null) {
//                output.append(line + "\n");
//            }
//            String response = output.toString();
//            System.out.println("line: " + line);
//
////            String appendedString = line + "boo";
////            System.out.println(appendedString);
////            path = bookfile.getPath();
//            FileWriter fileWriter = new FileWriter(bookfile);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write("{}");
//            System.out.println(bufferedWriter);
//            bufferedWriter.close();
//
////            try {
////                // InputStream is = bufferedReader.readLine();
//////                int size = is.available();
//////                byte[] buffer = new byte[size];
//////                is.read(buffer);
//////                is.close();
//////                String json = new String(buffer, StandardCharsets.UTF_8);
////
//////            } catch (IOException ex) {
//////                ex.printStackTrace();
//////                return null;
//////            }
//            //            System.out.println(line3);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
////        System.out.println("no");
//
//        return path;
//    }
//        try {
//            InputStream is = getAssets().open("recipes.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, StandardCharsets.UTF_8);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//
//    public String readJSONFromAsset2() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("book.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, StandardCharsets.UTF_8);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }

    public void Welcome(View view) {
        System.out.println("co " + co);
        setContentView(R.layout.mainscreen);
        // text.setWidth(width);


    }

    public void initialiseJSON() {
//        File bookfile;
//
        bookfile = this.getFileStreamPath("book.json");
////        System.out.println(bookfile);
//
        if (!bookfile.exists()) {
            bookfile = new File(this.getFilesDir(), "book.json");
            bookfile.setWritable(true);
        }
    }

    public String readJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void FindARecipe(View view) {
        setContentView(R.layout.findarecipe);
        TextView text = findViewById(R.id.recipeFinder);
        text.setMinWidth(width / 3);
        //  TextView textview = findViewById(R.id.recipeFinder2);
        //  textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    }

    public void Add(View view) {
        EditText qu = findViewById(R.id.enterquantity);
        EditText ed = findViewById(R.id.ingredient);

        String item = getString(ed);
        String qty = getString(qu);

        LinearLayout linearlayout = findViewById(R.id.linearlayout);
        LinearLayout linearlayout2 = findViewById(R.id.linearlayout2);
        TextView text = new TextView(this);
        TextView text2 = new TextView(this);

        text.setText(getString(ed));
        text.setId(counter);
        text.setX(30);
        text.setY(100 + 10 * counter);
        text.setVisibility(View.VISIBLE);
        text.setTextSize(20);
        text.setTextColor(Color.BLACK);
        linearlayout2.addView(text);

        text2.setText(getString(qu));
        text2.setId(counter);
        text2.setX(80);
        text2.setY(100 + 10 * counter);
        text2.setVisibility(View.VISIBLE);
        text2.setTextSize(20);
        text2.setTextColor(Color.BLACK);
        linearlayout.addView(text2);


        for (int i = 0; i < 20; i++) {
            int j = 0;
            while (recipes[i][0][j] != null) {


                if (item.equalsIgnoreCase(recipes[i][0][j])) {
                    recipes[i][2][j] = qty;
                    int percent = Integer.parseInt(qty) * 100 / Integer.parseInt(recipes[i][1][j]);
                    if (percent > 100) {
                        percent = 100;

                    }
                    recipes[i][3][j] = String.valueOf(percent);

                }


                j++;
            }


        }
    }

    public String getString(@NonNull EditText ed) {
        return ed.getText().toString();

    }

    @SuppressLint("ResourceType")
    public void Enter(View view) {

        int height = this.getResources().getDisplayMetrics().heightPixels;
        int width = this.getResources().getDisplayMetrics().widthPixels;

        setContentView(R.layout.recipe);

        for (int i = 0; i < 20; i++) {
            int j = 0;
            double total = 0.0;
            while (recipes[i][0][j] != null) {

                if (recipes[i][3][j] == null) {
                    recipes[i][3][j] = "0";
                }
                total = total + Integer.parseInt(recipes[i][3][j]);
                j++;


            }
            percent[0][i] = i;

            percent[1][i] = (int) Math.round(total / j);

        }

        int[] array = new int[20];
        GridLayout grid = findViewById(R.id.gridLayout);

        System.arraycopy(percent[1], 0, array, 0, 20);
        Arrays.sort(new int[][]{array}, Collections.reverseOrder());
        int x = 0;
        for (int rowcount = 0; rowcount < 10; rowcount++) {
            for (int colcount = 0; colcount < 2; colcount++) {

                CardView card = new CardView(this);
                card.setId(x);
                card.setRadius(10);
                card.setCardElevation(10);
                card.setClickable(true);
                card.callOnClick();
                GridLayout.Spec row = GridLayout.spec(rowcount);
                GridLayout.Spec col = GridLayout.spec(colcount);
                grid.getUseDefaultMargins();
                GridLayout.LayoutParams gridP = new GridLayout.LayoutParams(row, col);
                card.setMinimumWidth((width - 100) / 2);
                card.setUseCompatPadding(true);
                card.setMinimumHeight((height - 50) / 5);

//                System.out.println(colcount);
                if (colcount == 1) {

                    card.setLeft((width / 2) + 300);
                }
                // grid.setBackgroundResource(R.drawable.background);
                card.setPadding(100, 100, 100, 100);

                card.setBackgroundResource(R.drawable.background);
                grid.addView(card, gridP);

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView text = new TextView(this);
                text.setTextColor(R.color.black);

                text.setWidth((width - 200) / 2);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text.setGravity(Gravity.CENTER);
                text.setTextSize(25);
                System.out.println("percent " + percent[0][x]);
                text.setText(recipenames[percent[0][x]]);
                text.setTextColor(Color.BLACK);
                text.setPadding(10, 10, 10, 10);
                layout.addView(text);
                TextView p = new TextView(this);
                p.setMaxWidth((width - 300) / 2);
                p.setTextColor(Color.BLACK);
                p.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                p.setGravity(Gravity.CENTER);
                p.setTextSize(25);

                p.setText(String.valueOf(percent[1][x]));


                layout.addView(p);


                card.addView(layout);
                x++;
                int finalX = x - 1;
//                Boop(view);
                //yee = finalX;
                card.setOnClickListener(v -> {

                            System.out.println("clicked");
                            yee = finalX;
                            id = yee + 1;
                            setContentView(R.layout.recipescreen);
                            ImageButton yo = findViewById(R.id.imageButton4);
                            LinearLayout Ingredientlist = findViewById(R.id.ingredientlist);
                            LinearLayout qtylist = findViewById(R.id.qtylist);
                            LinearLayout method = findViewById(R.id.method);
                            JSONArray jArray = null;
                            try {
                                jArray = new JSONArray(readJSONFromAsset());

                                String recipeName = jArray.getJSONObject(finalX).getString("recipeName");
                                JSONArray ingredients = jArray.getJSONObject(finalX).getJSONArray("recipeIngredients");
                                JSONArray quantities = jArray.getJSONObject(finalX).getJSONArray("displayqty");
                                for (int j = 0; j < ingredients.length(); j++) {
                                    String ingredient = ingredients.getString(j);
                                    TextView textView = new TextView(getApplicationContext());
                                    //                 textView.setBackgroundResource(R.drawable.background);
                                    textView.setTextSize(20);
                                    textView.setText(ingredient);
                                    textView.setTextColor(Color.BLACK);
                                    Ingredientlist.addView(textView);
                                    TextView qtytext = new TextView(getApplicationContext());
                                    qtytext.setTextSize(20);
                                    //            qtytext.setBackgroundResource(R.drawable.background);

                                    String quantity = quantities.getString(j);
                                    qtytext.setTextColor(Color.BLACK);
                                    qtytext.setText(quantity);
                                    qtylist.addView(qtytext);
                                }
                                JSONArray methodarray = jArray.getJSONObject(finalX).getJSONArray("method");
                                for (int j = 0; j < methodarray.length(); j++) {
                                    //   method.setPadding(20, 50, 20, 40);
                                    String methodstep = methodarray.getString(j);
                                     methodsteps[j] = methodstep;
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setTextSize(20);
                                  //  System.out.println(methodstep);
                                    textView.setText(methodstep);
                                    textView.setTextColor(Color.BLACK);
                                    textView.setPadding(10, 50, 10, 10);
                                    method.addView(textView);
                                }
                                TextView title = findViewById(R.id.recipeName);
                                title.setText(recipeName);

                            } catch (JSONException e) {
                                e.printStackTrace();


                                yo.bringToFront();


                            }
                        }

                );
            }
        }

    }

//    private void Boop(View view) {
//        System.out.println("boop");
//        setContentView(R.layout.recipeinfo);
//        LinearLayout bigvertical = findViewById(R.id.poo);
//        LinearLayout horizontal = new LinearLayout(this);
//        LinearLayout vertical = new LinearLayout(this);
//
//        LinearLayout ingredients = new LinearLayout(this);
//        LinearLayout quantities = new LinearLayout(this);
//        for (int i = 0; i < 100; i++){
//            TextView ing = new TextView(this);
//            TextView qty = new TextView(this);
//            ing.setText("yuh");
//            qty.setText("pp");
//            ingredients.addView(ing);
//            quantities.addView(qty);
//
//        }
//
//        horizontal.addView(ingredients);
//        horizontal.addView(quantities);
//        bigvertical.addView(horizontal);
//        bigvertical.addView(vertical);
//    }

    public void AddToBook(View view) {
        buttoncheck = 1;
//        System.out.println("AddToBook");
        count++;
        bookentries++;
        cards++;
        System.out.println("method itiao3hdfkg34iohgrofbndkv " + methodsteps[1]);


        try {
            // JSONArray bookArray = new JSONArray(readJSONFromAsset2());
            JSONObject obj = new JSONObject();
            obj.put("recipeName", recipenames[yee]);
//            System.out.println("recipe name: " + recipenames[yee]);
            obj.put("recipeId", id);
//            System.out.println("recipeId: " + id);
//            // System.out.println(recipenames[yee]);

            JSONArray ingredients = new JSONArray();
            //    JSONObject ingredientobj = new JSONObject();

            int ingredientlist = recipes[yee][0].length;
            System.out.println("ingredient list " + ingredientlist);
            JSONObject ingredient = new JSONObject();
            JSONArray quantities = new JSONArray();
            JSONObject quantity = new JSONObject();
            JSONArray method = new JSONArray();
            JSONObject methodstep = new JSONObject();
            int i = 0;
            while (methodsteps[i] != null) {

                methodstep.put("methodstep", methodsteps[i]);

                method.put(methodsteps[i]);
//                System.out.println("method step : " + methodsteps[i]);
//                System.out.println(" method" + method);
                i++;

            }

            //            System.out.println(ingredientlist);
            // adding ingredeints
            for (int counter3 = 0; counter3 < recipes[yee][0].length; counter3++) {
//                System.out.println("book[yee][0][counter3] " + book[yee][0][counter3]);
//                System.out.println("recipes[yee][0][counter3] " + recipes[yee][0][counter3]);
                if (recipes[yee][0][counter3] != null) {
                    //  System.out.println("inside while");
                    book[yee][0][counter3] = recipes[yee][0][counter3];
                    ingredient.put("recipeIngredients", recipes[yee][0][counter3]);
                    quantity.put("recipeQuantity", recipes[yee][1][counter3]);
                    quantities.put(recipes[yee][1][counter3]);
//                System.out.println("recipe ingredient: " + recipes[yee][0][counter3]);
                    ingredients.put(recipes[yee][0][counter3]);

//                    System.out.println("ingredients: " + ingredients);
                }


//                System.out.println(cards);
                bookRecipeNames[cards - 1] = recipenames[yee];
            }
            obj.put("recipeIngredients", ingredients);
            obj.put("displayqty", quantities);
            obj.put("method", method);
            bookArray.put(obj);
            System.out.println(bookArray);

            AddedToBook(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void BacktoRecipes(View view) {
        setContentView(R.layout.recipe);
        Enter(view);
    }

    public void AddNewToBook(View view) {
        count++;
     //   System.out.println("1");

        bookentries++;
        cards++;
        try {
            // JSONArray bookArray = new JSONArray(readJSONFromAsset2());
            JSONObject obj = new JSONObject();
            obj.put("recipeName", name);
          //  System.out.println("4");
//            System.out.println("recipe name: " + recipenames[yee]);
            obj.put("recipeId", bookentries);
           // System.out.println("5");
//            System.out.println("recipeId: " + id);
//            // System.out.println(recipenames[yee]);

            JSONArray ingredients = new JSONArray();
            //    JSONObject ingredientobj = new JSONObject();

            int ingredientlist = AddRecipeIngredients[0].length;
        //    System.out.println("ingredient list " + ingredientlist);
            JSONObject ingredient = new JSONObject();
            JSONArray quantities = new JSONArray();
            JSONObject quantity = new JSONObject();
            JSONArray method = new JSONArray();
            JSONObject methodstep = new JSONObject();
//            System.out.println(ingredientlist);
            //ingredients = jArray
            //ingredient = jObject
            int i = 0;
            while (steps[i] != null) {
                i++;
                methodstep.put("method", steps[i]);
                method.put(methodstep);

            }
            for (int counter3 = 0; counter3 < AddRecipeIngredients[0].length; counter3++) {
//                System.out.println("book[yee][0][counter3] " + book[yee][0][counter3]);
//                System.out.println("recipes[yee][0][counter3] " + recipes[yee][0][counter3]);
                if (AddRecipeIngredients[0][counter3] != null) {
               //     System.out.println("6");
                    //  System.out.println("inside while");
                    ingredient.put("recipeIngredients", AddRecipeIngredients[0][counter3]);
                    quantity.put("displayqty", AddRecipeIngredients[1][counter3]);
                //    System.out.println("7");
//                System.out.println("recipe ingredient: " + recipes[yee][0][counter3]);
                    ingredients.put(AddRecipeIngredients[0][counter3]);
                    quantities.put(AddRecipeIngredients[1][counter3]);


                 //   System.out.println("8");
//                    System.out.println("ingredients: " + ingredients);
                }


//                System.out.println(cards);
                bookRecipeNames[cards - 1] = name;
                System.out.println("9");
            }
            obj.put("recipeIngredients", ingredients);
            obj.put("displayqty", quantities);
            obj.put("method", method);
            bookArray.put(obj);
       //     System.out.println("10");
            System.out.println(bookArray);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("book.json", MODE_PRIVATE));
                outputStreamWriter.write(String.valueOf(bookArray));

                outputStreamWriter.close();
            } catch (IOException e) {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AddedToBook(view);
    }

    private String GetName(EditText text) {
        System.out.println(text.getText().toString());
        return text.getText().toString();
    }

    public void BacktoMain(View view) {
        setContentView(R.layout.mainscreen);
    }

    @SuppressLint("ResourceAsColor")
    public void viewBook(View view) {

//        System.out.println("viewBook");
        setContentView(R.layout.viewbook);
        ImageButton button = findViewById(R.id.BackFromBook);
        if (buttoncheck == 1) {
            button.setOnClickListener(view1 -> setContentView(R.layout.recipe));


        }
        try {
            String[] bookentry = new String[100];
          //  System.out.println(bookentries);
            for (int i = 0; i < bookentries; ++i) {
                String name = bookArray.getJSONObject(i).getString("recipeName");

                bookentry[i] = name;
                bookRecipeNames[i] = name;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.viewbook);
        TextView textview = findViewById(R.id.textView12);
        TextView othertext = findViewById(R.id.textView5);

        if (bookentries == 0) {
            textview.setText("There are no entries");
            textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            othertext.setText(" in your book");
            othertext.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        }
        GridLayout grid = findViewById(R.id.grid);
        int column = 0;
        int row = 0;
        int x = 0;
        for (cards = 0; cards < bookentries; cards++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            CardView bookcard = new CardView(this);
            // bookcard.setId(x);
            bookcard.setRight(200);
            bookcard.setLeft(100);
            bookcard.setRadius(10);
            bookcard.setCardElevation(10);
            bookcard.setClickable(true);
            bookcard.callOnClick();
            GridLayout.Spec ro = GridLayout.spec(row);
//            System.out.println("row: " + row);
            GridLayout.Spec col = GridLayout.spec(column);
//            System.out.println(" column: " + column);
            //   grid.getUseDefaultMargins();
            GridLayout.LayoutParams gridpos = new GridLayout.LayoutParams(ro, col);
            bookcard.setMinimumWidth((width - 100) / 2);
            bookcard.setUseCompatPadding(true);
            bookcard.setMinimumHeight((height - 50) / 5);


            bookcard.setPadding(100, 100, 100, 150);

            bookcard.setBackgroundResource(R.drawable.background);
            grid.addView(bookcard, gridpos);
            TextView text = new TextView(this);
            TextView p = new TextView(this);
            text.setTextColor(getResources().getColor(R.color.black));
//            System.out.println("book recipe name in added book mod" + bookRecipeNames[cards]);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(25);
            text.setText(bookRecipeNames[cards]);
            text.setMinimumWidth((width - 100) / 2);
            text.setMinimumHeight((height - 40) / 10);
            ImageButton button1 = new ImageButton(this);
            button1.setBackgroundColor(Color.TRANSPARENT);
            button1.setImageResource(R.drawable.img_1);
            //  button1.getLayoutParams().height = 10;//set appropriate sizes
            //button1.getLayoutParams().width= 10;
//            button1.requestLayout();
            button1.setMaxHeight(10);
            x++;
            int finalX1 = x - 1;
            button1.setOnClickListener(view12 -> {
               // System.out.println("pp");
               // System.out.println(removeFromJsonArray(bookArray, finalX1));
                bookArray = removeFromJsonArray(bookArray, finalX1);
                bookentries = bookentries - 1;
                viewBook(view);
            });


            TextView text2 = new TextView(this);
            text2.setTextSize(10);
            text2.setText(" ");
            //     text.setBackgroundResource(R.drawable.background);
            layout.addView(text);
         //   System.out.println("layout.addView(text);");
            layout.addView(text2);
            layout.addView(button1);
         //   System.out.println("  layout.addView(button1);");
            bookcard.addView(layout);
          //  System.out.println("bookcard.addview(layout);");
            text.setPadding(10, 10, 10, 10);
            column++;

            if (column == 2) {
                row++;
                column = 0;
            }
            int finalX = x - 1;

            bookcard.setOnClickListener(view13 -> {
            //    System.out.println("finalX = " + finalX);
                setContentView(R.layout.bookrecipe);
                TextView title = findViewById(R.id.recipeName);
                title.setText(bookRecipeNames[finalX]);
            //    System.out.println("clicked");
             //   System.out.println(bookArray);
                yee = finalX;
                id = yee + 1;
                // setContentView(R.layout.recipescreen);
                ImageButton yo = findViewById(R.id.imageButton4);
                LinearLayout Ingredientlist = findViewById(R.id.ingredientlist);
                LinearLayout qtylist = findViewById(R.id.qtylist);
                LinearLayout method = findViewById(R.id.method);
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(readFromFile());
          //          System.out.println(jArray);
                    String recipeName = jArray.getJSONObject(finalX).getString("recipeName");
                    JSONArray ingredients = jArray.getJSONObject(finalX).getJSONArray("recipeIngredients");
                    JSONArray quantities = jArray.getJSONObject(finalX).getJSONArray("displayqty");
                    for (int j = 0; j < ingredients.length(); j++) {
                        String ingredient = ingredients.getString(j);
                        TextView textView = new TextView(getApplicationContext());
                        //                 textView.setBackgroundResource(R.drawable.background);
                        textView.setTextSize(20);
                        textView.setText(ingredient);
                        textView.setTextColor(Color.BLACK);
                        Ingredientlist.addView(textView);
             //           System.out.println(ingredient);
                        TextView qtytext = new TextView(getApplicationContext());
                        qtytext.setTextSize(20);
                        //            qtytext.setBackgroundResource(R.drawable.background);

                        String quantity = quantities.getString(j);
                        qtytext.setText(quantity);
                        qtytext.setTextColor(Color.BLACK);
           //             System.out.println(quantity);
                        qtylist.addView(qtytext);
                    }
                    JSONArray methodarray = jArray.getJSONObject(finalX).getJSONArray("method");
                    for (int j = 0; j < methodarray.length(); j++) {
                        //   method.setPadding(20, 50, 20, 40);
                        String methodstep = methodarray.getString(j);
                        TextView textView = new TextView(getApplicationContext());
                        textView.setTextSize(20);
                 //       System.out.println(methodstep);
                        textView.setText(methodstep);
                        textView.setTextColor(Color.BLACK);
                        textView.setPadding(10, 50, 10, 10);
                        method.addView(textView);
                    }
                    //   TextView title = findViewById(R.id.recipeName);
                    title.setText(recipeName);

                } catch (JSONException e) {
                    e.printStackTrace();


                    yo.bringToFront();


                }
            });
        }

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("book.json", MODE_PRIVATE));
            outputStreamWriter.write(String.valueOf(bookArray));
            System.out.println("bookArray " + bookArray);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FromBookRecipe(View view) {
        setContentView(R.layout.viewbook);
        viewBook(view);
    }

    public void AddedToBook(View view) {
//        System.out.println("bookrecipenames1 " + bookRecipeNames[1]);
//        System.out.println("added to book");

//        try {
//            String[] bookentry = new String[100];
//
//            for (int i = 0; i < bookentries; ++i) {
//                try {
//                    String recipename = bookArray.getJSONObject(i).getString("recipeName");
//                    bookentry[i] = recipename;
//                    bookRecipeNames[i] = recipename;
//                } catch (Exception e) {
////                    System.out.println("stupid");
//                }
//
////                System.out.println(bookentry[i]);
//
//                JSONArray ingredients = bookArray.getJSONObject(i).getJSONArray("recipeIngredients");
//                JSONArray quantities = bookArray.getJSONObject(i).getJSONArray("quantities");
//                for (int j = 0; j < ingredients.length(); j++) {
//                    String ingredient = ingredients.getString(j);
//                    book[i][0][j] = ingredient;
////                    System.out.println("(book[i][0][j] " + book[i][0][j]);
//                    String quantity = quantities.getString(j);
//                    book[i][1][j] = quantity;
////                    System.out.println("book[i][1][j] " + book[i][1][j]);
//
//                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        setContentView(R.layout.viewbook);
        viewBook(view);
//        ImageButton button = findViewById(R.id.BackFromBook);
//        if (buttoncheck == 1) {
//
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    setContentView(R.layout.recipe);
//                    Enter(view);
//
//                }
//            });
//
//
//        }
//        TextView textview = findViewById(R.id.textView12);
//        if (bookentries == 0) {
//            textview.setText("There are no entries in your book");
//        }
//        GridLayout grid = findViewById(R.id.grid);
//        int column = 0;
//        int row = 0;
//        int x = 0;
//        for (cards = 0; cards < bookentries; cards++) {
//
//            CardView bookcard = new CardView(this);
//            // bookcard.setId(x);
//            bookcard.setRight(200);
//            bookcard.setLeft(100);
//            bookcard.setRadius(10);
//            bookcard.setCardElevation(10);
//            bookcard.setClickable(true);
//            bookcard.callOnClick();
//            GridLayout.Spec ro = GridLayout.spec(row);
////            System.out.println("row: " + row);
//            GridLayout.Spec col = GridLayout.spec(column);
//            GridLayout.LayoutParams gridpos = new GridLayout.LayoutParams(ro, col);
//            bookcard.setMinimumWidth((width - 100) / 2);
//            bookcard.setUseCompatPadding(true);
//            bookcard.setMinimumHeight((height - 50) / 3);
//
//
//            bookcard.setPadding(100, 100, 100, 100);
//
//            bookcard.setBackgroundResource(R.drawable.background);
//            grid.addView(bookcard, gridpos);
//            TextView text = new TextView(this);
//            TextView p = new TextView(this);
//            text.setTextColor(getResources().getColor(R.color.black));
////            System.out.println("book recipe name in added book mod" + bookRecipeNames[cards]);
//            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            text.setGravity(Gravity.CENTER);
//            text.setTextSize(25);
//            text.setText(bookRecipeNames[cards]);
//            text.setMinimumWidth((width - 100) / 2);
//            text.setMinimumHeight((height - 40) / 10);
//
//            //     text.setBackgroundResource(R.drawable.background);
//            bookcard.addView(text);
//            text.setPadding(10, 10, 10, 10);
//            column++;
//            x++;
//            if (column == 2) {
//                row++;
//                column = 0;
//            }
//            int finalX = x - 1;
//            bookcard.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    setContentView(R.layout.bookrecipe);
//                    TextView title = findViewById(R.id.textView11);
//                    title.setText(bookRecipeNames[finalX]);
//
//                }
//            });
//        }
    }

    public void BacktoBook(View view) {
        viewBook(view);
    }

    public void AddRecipe(View view) {
        setContentView(R.layout.addrecipe);
        stepcounter = 0;
    }

    @SuppressLint("ResourceAsColor")
    public void AddStep(View view) {
        stepcounter++;
        TextView text = new TextView(this);
        EditText edit = findViewById(R.id.enterStep);

        step = stepcounter + ". " + getStep(edit);
        System.out.println(step);
        steps[stepcounter] = step;

        text.setTextSize(20);
        text.setText(step);
        text.setTextColor(Color.BLACK);
        LinearLayout layout = findViewById(R.id.MethodLayout);
        layout.addView(text);

    }

    private String getStep(EditText edit) {
        return edit.getText().toString();
    }

    @SuppressLint("ResourceAsColor")
    public void AddIngredient(View view) {
        counter3++;
        System.out.println("add");
        LinearLayout QtyLayout = findViewById(R.id.addQtyLinear);
        LinearLayout IngLayout = findViewById(R.id.addingredientlinear);
        EditText ingredient = findViewById(R.id.AddIngredientBox);
        EditText qty = findViewById(R.id.AddQtyBox);
        TextView TextIngredient = new TextView(this);
        TextView TextQty = new TextView(this);
        AddRecipeIngredients[counter3][0] = GetIngredient(ingredient);
        AddRecipeIngredients[counter3][1] = GetQty(qty);
        TextIngredient.setTextColor(Color.BLACK);
//     TextIngredient.setTextColor(R.color.black);
        TextIngredient.setTextSize(20);
        TextQty.setTextColor(Color.BLACK);
        TextQty.setTextSize(20);
        TextIngredient.setText(GetIngredient(ingredient));
        TextQty.setText(GetQty(qty));
        QtyLayout.addView(TextQty);
        IngLayout.addView(TextIngredient);


    }

    public void AddMethod(View view) {
        EditText text = findViewById(R.id.RecipeNameText);
        name = GetName(text);
        System.out.println(name);
        setContentView(R.layout.addmethod);

    }

    private String GetQty(EditText qty) {
        return qty.getText().toString();
    }

    public String GetIngredient(EditText ingredient) {
        return ingredient.getText().toString();
    }
}


//    public void ViewList(View view) {
//        setContentView(R.layout.shoppinglist);
//
//    }
//    private String getShoppingList() {
//        System.out.println("Height " + height);
//        System.out.println("width " + width);
//        String ret = "";
//        InputStream inputStream;
//        try {
//            inputStream = this.openFileInput("List.json");
//            System.out.println("yuh");
//            if (inputStream != null) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString;
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ((receiveString = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(receiveString);
//                }
//
//                ret = stringBuilder.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }

//    public void AddItem(View view) {
//        counter2++;
//        EditText item = findViewById(R.id.item);
//        TextView itemstext = new TextView(this);
//        items[counter2] = String(item);
//        LinearLayout linearlayout2 = findViewById(R.id.linearlayout2);
//        itemstext.setText(String(item));
//        itemstext.setId(counter2);
//        itemstext.setX(30);
//        itemstext.setY(180 + 10 * counter2);
//        itemstext.setVisibility(View.VISIBLE);
//        itemstext.setTextSize(20);
//        itemstext.setTextColor(Color.BLACK);
//        linearlayout2.addView(itemstext);
//    }
//
//    public String String(EditText item) {
//        return item.getText().toString();
//    }
//}