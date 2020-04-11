package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText Date, Price, Item;
    Button Add, Sp;
    TextView balance;
    DBManagement db;
    TableLayout history;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Item = findViewById(R.id.Item);
        Add = findViewById(R.id.Add);
        Sp = findViewById(R.id.Sub);
        Price = findViewById(R.id.Price);
        balance = findViewById(R.id.balance);
        Date = findViewById(R.id.Date);
        history = (TableLayout) findViewById(R.id.tableHistory);
        db = new DBManagement(this);
        AddTransaction();
        spHistory();
    }

    public void AddTransaction(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        Model model = new Model();
                        model.mDate =  Date.getText().toString();
                        model.mItem = Item.getText().toString();
                        model.mPrice = price;
                        boolean result = db.createTransaction(model);

                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        spHistory();
                        ClearText();
                    }
                }
        );

        Sp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString()) * -1;
                        Model model = new Model();
                        model.mDate =  Date.getText().toString();
                        model.mItem = Item.getText().toString();
                        model.mPrice = price;
                        boolean result = db.createTransaction(model);

                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        spHistory();
                        ClearText();
                    }
                }
        );
    }


    public void spHistory(){
        Cursor result = db.pullData();
        StringBuffer buffer = new StringBuffer();
        ClearTable();
        Double balance = 0.00;

        while(result.moveToNext()){
            TableRow tr = new TableRow(this);
            TableRow.LayoutParams columnLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            columnLayout.weight = 1;

            TextView date = new TextView(this);
            date.setLayoutParams(columnLayout);
            date.setText(result.getString(2));
            tr.addView(date);

            TextView priceView = new TextView(this);
            priceView.setLayoutParams(columnLayout);
            priceView.setText(result.getString(3));
            tr.addView(priceView);

            TextView item = new TextView(this);
            item.setLayoutParams(columnLayout);
            item.setText(result.getString(1));
            tr.addView(item);

            history.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            double price = Double.parseDouble(result.getString(3));
            balance += price;
        }

        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
    }

    public void ClearText(){
        MainActivity.this.Date.setText("");
        MainActivity.this.Price.setText("");
        MainActivity.this.Item.setText("");
    }

    public void ClearTable(){
        int count = history.getChildCount();
        for (int i = 1; i < count; i++) {
            history.removeViewAt(1);
        }
    }
}

