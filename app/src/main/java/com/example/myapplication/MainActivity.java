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
    String[][][] recipes = new String[20][4][20];
   public static String[] recipenames = new String[20];
    int[][] percent = new int[2][20];
    String[][][] book = new String[100][4][100];
    JSONArray bookArray = new JSONArray();
    String[] bookRecipeNames = new String[20];
    int counter = 0;
    String[] bookentry = new String[100];
    int counter2 = 0;
    String[] items = new String[100];
    int count = 0;
    int yee = 0;
    int id = 0;
   int recipeId = 0;
    int height = 0;
    int width = 0;
    int cards = 0;
    int bookentries = 0;
    int buttoncheck = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        co++;
        //File book;
        initialiseJSON();
        height = this.getResources().getDisplayMetrics().heightPixels;
        width = this.getResources().getDisplayMetrics().widthPixels;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcomescreen);
        Button welcome = (Button) findViewById(R.id.Welcome);
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
        System.out.println("Height " + height );
        System.out.println("width " + width);
        String ret = "";
        InputStream inputStream;
        try {
            inputStream = this.openFileInput("book.json");
            System.out.println("yuh");
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
        text.setMinWidth(width/3);
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
                card.setMinimumHeight((height - 50) / 3);

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
                text.setPadding(10, 10, 10, 10);
                layout.addView(text);
                TextView p = new TextView(this);
                p.setMaxWidth((width - 300) / 2);
                p.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                p.setGravity(Gravity.CENTER);
                p.setTextSize(25);

                p.setText(String.valueOf(percent[1][x]));


                layout.addView(p);


                card.addView(layout);
                x++;
                int finalX = x - 1;

                //yee = finalX;
                card.setOnClickListener(view1 -> {
                    yee = finalX;
                    id = yee + 1;
                    setContentView(R.layout.recipescreen);
                    ImageButton yo = findViewById(R.id.imageButton4);
                    yo.bringToFront();
                    TextView title = findViewById(R.id.textView10);
//                        System.out.println(recipenames[percent[0][finalX]]);
                    String name = String.valueOf(recipenames[percent[0][finalX]]);
                    title.setText(name);


                });
            }
        }
    }

    public void AddToBook(View view) {
        buttoncheck = 1;
//        System.out.println("AddToBook");
        count++;
        bookentries++;
        cards++;
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
//            System.out.println(ingredientlist);
            // adding ingredeints
            for (int counter3 = 0; counter3 < recipes[yee][0].length; counter3++) {
//                System.out.println("book[yee][0][counter3] " + book[yee][0][counter3]);
//                System.out.println("recipes[yee][0][counter3] " + recipes[yee][0][counter3]);
                if (recipes[yee][0][counter3] != null) {
                    //  System.out.println("inside while");
                    book[yee][0][counter3] = recipes[yee][0][counter3];
                    ingredient.put("recipeIngredients", recipes[yee][0][counter3]);
//                System.out.println("recipe ingredient: " + recipes[yee][0][counter3]);
                    ingredients.put(recipes[yee][0][counter3]);
//                    System.out.println("ingredients: " + ingredients);
                }


//                System.out.println(cards);
                bookRecipeNames[cards - 1] = recipenames[yee];

//                //  System.out.println("bookrecipenames: " + bookRecipeNames[cards]);
//                System.out.println(ingredients);
            }
            obj.put("recipeIngredients", ingredients);
            bookArray.put(obj);
            System.out.println(bookArray);
//            //  System.out.println();
////            System.out.println(obj);

////            System.out.println(jArray);\
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("book.json", MODE_PRIVATE));
                outputStreamWriter.write(String.valueOf(bookArray));

                outputStreamWriter.close();
            } catch (IOException e) {

//           FileWriter fileWriter = new FileWriter(bookfile);
//            System.out.println("added to book| fileWriter" + fileWriter);
//            System.out.println("added to book| bookfile: " + bookfile);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            System.out.println("added to book| bufferedWriter: " + bufferedWriter);
//            bufferedWriter.write(obj.toString());
//            bufferedWriter.write("");
//            System.out.println("added to book | obj.toString() " + obj);
//        }
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AddedToBook(view);
    }


    public void BacktoRecipes(View view) {
        setContentView(R.layout.recipe);
        Enter(view);
    }

    public void BacktoMain(View view) {
        setContentView(R.layout.mainscreen);
    }

    public void viewBook(View view) {
//        System.out.println("viewBook");
        setContentView(R.layout.viewbook);
        ImageButton button = findViewById(R.id.BackFromBook);
        if (buttoncheck == 1) {
            button.setOnClickListener(view1 -> setContentView(R.layout.recipe));


        }
        try {
            String[] bookentry = new String[100];
           // bookentries = cards + 1;
            System.out.println(bookentries);
            for (int i = 0; i < bookentries; ++i) {
                String name = bookArray.getJSONObject(i).getString("recipeName");

                bookentry[i] = name;
                bookRecipeNames[i] = name;


//                System.out.println(bookentry[i]);

//                JSONArray ingredients = bookArray.getJSONObject(i).getJSONArray("recipeIngredients");
//                JSONArray quantities = bookArray.getJSONObject(i).getJSONArray("quantities");

//                for (int j = 0; j < ingredients.length(); j++) {
//                    String ingredient = ingredients.getString(j);
//                    if (ingredient == null) {
//                        ingredient = " ";
//                        if (ingredient != " ") {
//                            book[i][0][j] = ingredient;
//                        }
//
//                    }
//
//
////                    System.out.println("(book[i][0][j] " + book[i][0][j]);
//                    String quantity = quantities.getString(j);
//                    book[i][1][j] = quantity;
//                    System.out.println("book[i][1][j] " + book[i][1][j]);

                }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.viewbook);
        TextView textview = findViewById(R.id.textView12);
        if (bookentries == 0) {
            textview.setText("There are no entries in your book");
        }
        GridLayout grid = findViewById(R.id.grid);
        int column = 0;
        int row = 0;
        int x = 0;
        for (cards = 0; cards < bookentries; cards++) {

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
            bookcard.setMinimumHeight((height - 50) / 3);


            bookcard.setPadding(100, 100, 100, 100);

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

            //     text.setBackgroundResource(R.drawable.background);
            bookcard.addView(text);
            text.setPadding(10, 10, 10, 10);
            column++;
            x++;
            if (column == 2) {
                row++;
                column = 0;
            }
            int finalX = x - 1;
            bookcard.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    setContentView(R.layout.bookrecipe);
                    TextView title = findViewById(R.id.bookrecipetitle);
                    title.setText(bookRecipeNames[finalX]);

                }
            });
        }
    }

    public void AddedToBook(View view) {
//        System.out.println("bookrecipenames1 " + bookRecipeNames[1]);
//        System.out.println("added to book");

        try {
            String[] bookentry = new String[100];

            for (int i = 0; i < bookentries; ++i) {
                try {
                    String recipename = bookArray.getJSONObject(i).getString("recipeName");
                    bookentry[i] = recipename;
                    bookRecipeNames[i] = recipename;
                } catch (Exception e) {
//                    System.out.println("stupid");
                }

//                System.out.println(bookentry[i]);

                JSONArray ingredients = bookArray.getJSONObject(i).getJSONArray("recipeIngredients");
                JSONArray quantities = bookArray.getJSONObject(i).getJSONArray("quantities");
                for (int j = 0; j < ingredients.length(); j++) {
                    String ingredient = ingredients.getString(j);
                    book[i][0][j] = ingredient;
//                    System.out.println("(book[i][0][j] " + book[i][0][j]);
                    String quantity = quantities.getString(j);
                    book[i][1][j] = quantity;
//                    System.out.println("book[i][1][j] " + book[i][1][j]);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.viewbook);
        ImageButton button = findViewById(R.id.BackFromBook);
        if (buttoncheck == 1) {

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    setContentView(R.layout.recipe);
                    Enter(view);

                }
            });


        }
        TextView textview = findViewById(R.id.textView12);
        if (bookentries == 0) {
            textview.setText("There are no entries in your book");
        }
        GridLayout grid = findViewById(R.id.grid);
        int column = 0;
        int row = 0;
        int x = 0;
        for (cards = 0; cards < bookentries; cards++) {

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
            GridLayout.LayoutParams gridpos = new GridLayout.LayoutParams(ro, col);
            bookcard.setMinimumWidth((width - 100) / 2);
            bookcard.setUseCompatPadding(true);
            bookcard.setMinimumHeight((height - 50) / 3);


            bookcard.setPadding(100, 100, 100, 100);

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

            //     text.setBackgroundResource(R.drawable.background);
            bookcard.addView(text);
            text.setPadding(10, 10, 10, 10);
            column++;
            x++;
            if (column == 2) {
                row++;
                column = 0;
            }
            int finalX = x - 1;
            bookcard.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    setContentView(R.layout.bookrecipe);
                    TextView title = findViewById(R.id.bookrecipetitle);
                    title.setText(bookRecipeNames[finalX]);

                }
            });
        }
    }

    public void BacktoBook(View view) {
        AddedToBook(view);
    }

    public void AddRecipe(View view) {
        setContentView(R.layout.addrecipe);
    }

    public void ViewList(View view) {
        setContentView(R.layout.shoppinglist);
    }

    public void AddItem(View view) {
        counter2++;
        EditText item = findViewById(R.id.item);
        TextView itemstext = new TextView(this);
        items[counter2] = String(item);
        LinearLayout linearlayout2 = findViewById(R.id.linearlayout2);
        itemstext.setText(String(item));
        itemstext.setId(counter2);
        itemstext.setX(30);
        itemstext.setY(180 + 10 * counter2);
        itemstext.setVisibility(View.VISIBLE);
        itemstext.setTextSize(20);
        itemstext.setTextColor(Color.BLACK);
        linearlayout2.addView(itemstext);
    }

    public String String(EditText item) {
        return item.getText().toString();
    }
}